package net.nemo.whatever.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.nemo.whatever.entity.Chat;
import net.nemo.whatever.service.ChatService;

@Controller
@RequestMapping("/chat")
public class ChatController {
	
	@Autowired
	private ChatService chatService;

	@RequestMapping("/list.html")
	public ModelAndView chatList(){
		ModelAndView mav = new ModelAndView("chat/list");
		
		List<Chat> chats = this.chatService.listChats(1);
		
		for(Chat chat : chats){
			System.out.println(chat);
		}
		
		return mav;
	}
}
