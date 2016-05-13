package net.nemo.whatever.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.nemo.whatever.exception.BusinessException;

@Controller
public class UserController {
	
	@RequestMapping("/login.html")
	public ModelAndView login() throws Exception{
		ModelAndView mav = new ModelAndView("login");
		return mav;
	}
	
	@RequestMapping(value="/checkLogin.json", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkLogin(String username, String password) throws Exception{
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		try{
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			Subject currentUser = SecurityUtils.getSubject();
			
			if(!currentUser.isAuthenticated()){
				token.setRememberMe(true);
				currentUser.login(token);
			}
		}catch(Exception ex){
			throw new BusinessException(ex.getMessage());
		}
		
		result.put("success", true);
		return result;
	}
	
	
	@RequestMapping(value="/logout.json", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> logout(){
		Map<String, Object> result = new HashMap<String, Object>();
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		result.put("success", true);
		return result;
	}
}
