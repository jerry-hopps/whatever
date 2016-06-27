package net.nemo.whatever.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.nemo.whatever.entity.Chat;
import net.nemo.whatever.entity.Message;
import net.nemo.whatever.entity.User;
import net.nemo.whatever.service.ChatService;
import net.nemo.whatever.service.MessageService;
import net.nemo.whatever.util.StringUtil;

@Controller
@RequestMapping("/chat")
public class ChatController {
	
	private Logger logger = Logger.getLogger(ChatController.class);
	
	@Autowired
	private ChatService chatService;
	
	@Autowired
	private MessageService messageSercice;

	@RequestMapping("/list.html")
	public ModelAndView chatList(HttpServletRequest request){
		logger.info("Request /list.html");
		
		ModelAndView mav = new ModelAndView(StringUtil.getUserAgentViewName(request,"chat/list"));
		User currentUser = (User)SecurityUtils.getSubject().getSession().getAttribute("currentUser");
		List<Chat> chats = this.chatService.listChats(currentUser.getId());
		mav.addObject("chats", chats);
		
		return mav;
	}
	
	@RequestMapping(value="/{chat_id}.html",method=RequestMethod.GET)
	public ModelAndView messages(HttpServletRequest request, @PathVariable("chat_id") Integer chatId){
		ModelAndView mav = new ModelAndView(StringUtil.getUserAgentViewName(request,"message/list"));
		User currentUser = (User)SecurityUtils.getSubject().getSession().getAttribute("currentUser");
		List<Message> messages = this.messageSercice.findMessages(chatId, currentUser.getId());
		mav.addObject("messages", messages);
		Map<String, String> attachments = this.messageSercice.findAttachmentPaths(chatId, messages);
		mav.addObject("attachments", attachments);
		mav.addObject("receiver", currentUser.getName());
		mav.addObject("receiver_id", currentUser.getId());
		mav.addObject("chat", this.chatService.findById(chatId));
		return mav;
	}
	
	@RequestMapping(value = "/list.json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> chatList() {
		Map<String, Object> result = new HashMap<String, Object>();
		
		User currentUser = (User)SecurityUtils.getSubject().getSession().getAttribute("currentUser");
		List<Chat> chats = this.chatService.listChats(currentUser==null? 1: currentUser.getId());
		result.put("chats", chats);
		result.put("success", true);
		return result;
	}
	
	@RequestMapping(value = "/{chat_id}.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> messages(@PathVariable("chat_id") Integer chatId) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		User currentUser = (User)SecurityUtils.getSubject().getSession().getAttribute("currentUser");
		List<Message> messages = this.messageSercice.findMessages(chatId, currentUser.getId());

		result.put("success", true);
		return result;
	}
	
}
