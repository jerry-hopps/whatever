package net.nemo.whatever.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import net.nemo.whatever.entity.Chat;
import net.nemo.whatever.entity.Message;
import net.nemo.whatever.service.ChatService;
import net.nemo.whatever.service.MessageService;

@Controller
@RequestMapping("/chat")
public class ChatController {
	
	@Autowired
	private ChatService chatService;
	
	@Autowired
	private MessageService messageSercice;

	@RequestMapping("/list.html")
	public ModelAndView chatList(){
		ModelAndView mav = new ModelAndView("chat/list");
		
		List<Chat> chats = this.chatService.listChats(1);
		mav.addObject("chats", chats);
		
		return mav;
	}
	
	@RequestMapping(value="/{chat_id}.html",method=RequestMethod.GET)
	public ModelAndView messages(@PathVariable("chat_id") Integer chatId){
		ModelAndView mav = new ModelAndView("message/list");
		List<Message> messages = this.messageSercice.findMessages(chatId, 1);
		mav.addObject("messages", messages);
		mav.addObject("receiver", "Tony");
		mav.addObject("chat", this.chatService.findById(chatId));
		return mav;
	}
}
