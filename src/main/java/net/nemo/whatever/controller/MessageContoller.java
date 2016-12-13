package net.nemo.whatever.controller;

import net.nemo.whatever.entity.Message;
import net.nemo.whatever.entity.User;
import net.nemo.whatever.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by tonyshi on 2016/12/13.
 */

@Controller
@RequestMapping("/message")
public class MessageContoller {

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/link/tags.json", method = RequestMethod.GET)
    @ResponseBody
    public List<String> allMessages(HttpSession session){
        User currentUser = (User)session.getAttribute("currentUser");
        return this.messageService.findAllLinkTags(currentUser);
    }

    @RequestMapping(value = "/links.json", method = RequestMethod.GET)
    @ResponseBody
    public List<Message> allMessages(HttpSession session, @RequestParam("tagname") String tagName){
        User currentUser = (User)session.getAttribute("currentUser");
        return this.messageService.findLinkMessageByType(currentUser, tagName);
    }
}
