package net.nemo.whatever.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import net.nemo.whatever.service.WechatService;
import net.nemo.whatever.util.DESCoder;
import net.nemo.whatever.util.StringUtil;

@Controller
public class UserController {
	
	private Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private WechatService wechatService;
	
	@RequestMapping("/")
	public ModelAndView index() throws Exception {
		ModelAndView  mav = new ModelAndView("redirect:/chat/list.html");
		return mav;
	}

	@RequestMapping("/login.html")
	public ModelAndView login(HttpServletRequest request, @RequestParam(value="source", required=false) String source, @RequestParam(value="openid", required=false) String openid) throws Exception {
		ModelAndView  mav = null;
		if(source==null){
			mav = new ModelAndView(StringUtil.getUserAgentViewName(request,"user/login"));
			mav.addObject("openid", openid);
		}
		else if("wechat".equals(source)){
			String redirectURI = String.format("%s%s/wechat_callback.html", getURLBase(request), request.getContextPath());
			String redirectURL = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=wechat#wechat_redirect", WechatService.CORP_ID, redirectURI);
			
			logger.info(String.format("Redirecting to %s", redirectURL));
			mav = new ModelAndView("redirect:"+redirectURL);
		}
		return mav;
	}
	
	@RequestMapping("/wechat_callback.html")
	public ModelAndView wechatCallback(HttpServletRequest request, @RequestParam(value="code", required=false) String code) throws Exception {
		ModelAndView  mav = new ModelAndView();
		
		String openId = this.wechatService.getOpenId(code);
		User user = this.userService.findByOpenId(openId);
		if(user!=null){
			logger.info(String.format("Found user in DB, login automactically and then redirect to %s", "/chat/list.html"));
			login(user.getEmail(), user.getPassword());
			mav.setViewName("redirect:/chat/list.html");
		}
		else{
			logger.info(String.format("Use not bound with Wechat account yet, render login page and bind with /user/login.html?openid=%s", openId));
			mav.setViewName("redirect:/login.html?openid="+openId);
		}
		return mav;
	}

	@RequestMapping("/logout.html")
	public ModelAndView logout(HttpServletRequest request) {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		ModelAndView mav = new ModelAndView(StringUtil.getUserAgentViewName(request,"user/login"));
		return mav;
	}

	@RequestMapping("/register/{id}/{token}.html")
	public ModelAndView register(HttpServletRequest request, @PathVariable("id") Integer id, @PathVariable("token") String token) {
		ModelAndView mav = null;

		String encryptedStr = token.trim();
		String key = this.userService.findUserById(id).getPassword().trim();
		
		try {
			String email = new String(DESCoder.decrypt(DESCoder.decryptBASE64(encryptedStr), key));
			mav = new ModelAndView(StringUtil.getUserAgentViewName(request,"user/register"));
			mav.addObject("email", email);
		} catch (Exception e) {
			mav = new ModelAndView(StringUtil.getUserAgentViewName(request,"user/invalidtoken"));
		}

		return mav;
	}

	@RequestMapping(value = "/setPassword.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> setPassword(String username, String password) {
		Map<String, Object> result = new HashMap<String, Object>();

		User user = this.userService.findByEmail(username);
		user.setPassword(password);
		user.setStatus(2);
		this.userService.update(user);

		result.put("success", true);
		return result;
	}

	@RequestMapping(value = "/checkLogin.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkLogin(String username, String password, String openid) throws Exception {
		logger.info(String.format("Clien is trying to log in with username: %s, password: %s", username, password));
		
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			User user = login(username, password);
			if(null!=openid){
				user.setOpenId(openid);
				this.userService.update(user);
			}
			result.put("success", true);
		}catch (Exception ex) {
			throw new BusinessException(ex.getMessage());
		}
		return result;
	}
	
	private User login(String username, String password) throws Exception{
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject currentUser = SecurityUtils.getSubject();

		token.setRememberMe(true);
		currentUser.login(token);
		User loginUser = userService.findByEmail(username);
		currentUser.getSession().setAttribute("currentUser", loginUser);
		logger.info(String.format("User logged in: ", loginUser));
		
		return loginUser;
	}
	
	public String getURLBase(HttpServletRequest request) throws Exception {
	    URL requestURL = new URL(request.getRequestURL().toString());
	    String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
	    return requestURL.getProtocol() + "://" + requestURL.getHost() + port;

	}
}
