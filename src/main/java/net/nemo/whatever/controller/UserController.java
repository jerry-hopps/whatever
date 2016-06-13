package net.nemo.whatever.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.nemo.whatever.entity.User;
import net.nemo.whatever.exception.BusinessException;
import net.nemo.whatever.service.UserService;
import net.nemo.whatever.util.DESCoder;

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
	public ModelAndView login() throws Exception {
		ModelAndView  mav = new ModelAndView("user/login");
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
