package net.nemo.whatever.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import net.nemo.whatever.entity.Chat;
import net.nemo.whatever.entity.Message;
import net.nemo.whatever.entity.User;
import net.nemo.whatever.service.ChatService;
import net.nemo.whatever.service.MessageService;

@Controller
@RequestMapping("/chat")
public class ChatController {
	
	private Logger logger = Logger.getLogger(ChatController.class);
	
	@Autowired
	private ChatService chatService;
	
	@Autowired
	private MessageService messageSercice;

	@RequestMapping("/list.html")
	public ModelAndView chatList(){
		logger.info("Request /list.html");
		
		ModelAndView mav = new ModelAndView("chat/list");
		User currentUser = (User)SecurityUtils.getSubject().getSession().getAttribute("currentUser");
		List<Chat> chats = this.chatService.listChats(currentUser.getId());
		mav.addObject("chats", chats);
		
		return mav;
	}
	
	@RequestMapping(value="/{chat_id}.html",method=RequestMethod.GET)
	public ModelAndView messages(@PathVariable("chat_id") Integer chatId){
		ModelAndView mav = new ModelAndView("message/list");
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
}
