package net.nemo.whatever.controller;

import net.nemo.whatever.entity.Message;
import net.nemo.whatever.entity.User;
import net.nemo.whatever.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tonyshi on 2016/12/13.
 */

@Controller
@RequestMapping("/message")
public class MessageContoller {

    @Autowired
    private MessageService messageService;

    private final static Map<String, Integer> MESSAGE_TYPES = new HashMap<String, Integer>(){{
        put("link", 2);
        put("photo", 1);
    }};

    @RequestMapping(value = "/{type}/tags.json", method = RequestMethod.GET)
    @ResponseBody
    public List<String> messageTags(HttpSession session, @PathVariable("type") String type){
        User currentUser = (User)session.getAttribute("currentUser");
        return this.messageService.findAllTags(currentUser, MESSAGE_TYPES.get(type));
    }

    @RequestMapping(value = "/tags.json", method = {RequestMethod.DELETE, RequestMethod.POST})
    @ResponseBody
    public void updateOrDeleteTag(HttpServletRequest request,
                        @RequestParam(value = "message_id", required = false) Integer messageId,
                        @RequestParam(value = "tagname", required = false) String tagName) throws UnsupportedEncodingException {
        if("POST".equals(request.getMethod())){
            this.messageService.addTagForMessage(messageId, URLDecoder.decode(tagName, "UTF-8"));
        }
        else if("DELETE".equals(request.getMethod())){
            this.messageService.deleteTag(messageId, URLDecoder.decode(tagName, "UTF-8"));
        }
    }

    @RequestMapping(value = "/type.json", method = RequestMethod.GET)
    @ResponseBody
    public List<Message> allMessages(HttpSession session, @RequestParam("tagname") String tagName, @RequestParam("type") String type) throws UnsupportedEncodingException {
        User currentUser = (User)session.getAttribute("currentUser");
        return this.messageService.findMessageByTypeAndType(currentUser, URLDecoder.decode(tagName, "UTF-8"), MESSAGE_TYPES.get(type));
    }
}
