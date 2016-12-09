import net.nemo.whatever.db.mapper.ChatMapper;
import net.nemo.whatever.entity.Chat;
import net.nemo.whatever.entity.User;
import net.nemo.whatever.service.ChatService;
import org.apache.struts.mock.MockServletContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * Created by tonyshi on 2016/12/9.
 */
public class Test {
    public static void main(String[] args) throws  Exception{
        XmlWebApplicationContext applicationContext = new XmlWebApplicationContext();
        applicationContext.setConfigLocation("file:/data/java/whatever/src/main/resources/application.xml");
        applicationContext.setServletContext(new MockServletContext());
        applicationContext.refresh();

        Chat chat = new Chat();
        chat.setChatOwner("石苏");
        chat.setReceiver(new User(1, null, null, null, null));

        ChatService chatService = (ChatService) applicationContext.getBean("chatService");
        chatService.addChat(chat);

        System.exit(0);
    }
}
