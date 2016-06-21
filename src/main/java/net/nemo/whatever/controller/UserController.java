package net.nemo.whatever.controller;

import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.standard.Copies;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.nemo.whatever.entity.User;
import net.nemo.whatever.exception.BusinessException;
import net.nemo.whatever.service.UserService;
import net.nemo.whatever.util.DESCoder;
import net.nemo.whatever.util.HttpUtil;
import net.nemo.whatever.util.StringUtil;

@Controller
public class UserController {
	
	private Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@RequestMapping("/")
	public ModelAndView index() throws Exception {
		ModelAndView  mav = new ModelAndView("redirect:/chat/list.html");
		return mav;
	}

	@RequestMapping("/login.html")
	public ModelAndView login(@RequestParam(value="source", required=false) String source) throws Exception {
		ModelAndView  mav = null;
		if(source==null){
			mav = new ModelAndView("user/login");
		}
		else if("wechat".equals(source)){
			String corpId = "wx6ccf61a87accb57d";
			String redirectURI = "http://www.ileqi.com.cn:8080/whatever/wechat_callback.html";
			String redirectURL = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=wechat#wechat_redirect", corpId, redirectURI);
			mav = new ModelAndView("redirect:"+redirectURL);
		}
		return mav;
	}
	
	@RequestMapping("/wechat_callback.html")
	public ModelAndView wechatCallback(@RequestParam(value="code", required=false) String code) throws Exception {
		String url = String.format("https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=%s&code=%s", "DrJMoeFLyXneoxDl7x-Ef76wPUJqZ_kBGqJF1QEwox03y7NpK6X98DbMVSCEhehu", code);
		final String userid = (String)StringUtil.json2Map(HttpUtil.get(url)).get("UserId");
		
		url = String.format("https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_openid?access_token=%s", "DrJMoeFLyXneoxDl7x-Ef76wPUJqZ_kBGqJF1QEwox03y7NpK6X98DbMVSCEhehu");
		Map<String, String> params = new HashMap<String, String>(){{
			put("userid", userid);
		}};
		String openid = StringUtil.json2Map(HttpUtil.post(url, params)).get("openid").toString();
		
		ModelAndView  mav = new ModelAndView("user/login");
		mav.addObject("openid", openid);
		return mav;
	}

	@RequestMapping("/logout.html")
	public ModelAndView logout() {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		ModelAndView mav = new ModelAndView("user/login");
		return mav;
	}

	@RequestMapping("/register/{id}/{token}.html")
	public ModelAndView register(@PathVariable("id") Integer id, @PathVariable("token") String token) {
		ModelAndView mav = null;

		String encryptedStr = token.trim();
		String key = this.userService.findUserById(id).getPassword().trim();
		
		try {
			String email = new String(DESCoder.decrypt(DESCoder.decryptBASE64(encryptedStr), key));
			mav = new ModelAndView("user/register");
			mav.addObject("email", email);
		} catch (Exception e) {
			mav = new ModelAndView("user/invalidtoken");
		}

		return mav;
	}

	@RequestMapping(value = "/setPassword.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> setPassword(String username, String password) {
		Map<String, Object> result = new HashMap<String, Object>();

		User user = this.userService.findByEmail(username);
		user.setPassword(password);
		this.userService.updatePassword(user);
		user.setStatus(2);
		this.userService.updateStatus(user);

		result.put("success", true);
		return result;
	}

	@RequestMapping(value = "/checkLogin.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkLogin(String username, String password) throws Exception {
		logger.info(String.format("Clien is trying to log in with username: %s, password: %s", username, password));
		
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			Subject currentUser = SecurityUtils.getSubject();

			token.setRememberMe(true);
			currentUser.login(token);
			User loginUser = userService.findByEmail(username);
			currentUser.getSession().setAttribute("currentUser", loginUser);
			logger.info(String.format("User logged in: ", loginUser));
		} catch (Exception ex) {
			logger.error(String.format("User failed logging in "), ex);
			throw new BusinessException(ex.getMessage());
		}

		result.put("success", true);
		return result;
	}
}
