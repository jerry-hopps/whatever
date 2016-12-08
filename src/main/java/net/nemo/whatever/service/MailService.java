package net.nemo.whatever.service;

import java.util.Map;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

@Service
public class MailService {

	@Value("${mail.server.pop3}")
	private String mailServer;
	@Value("${mail.protocal}")
	private String protocal;
	@Value("${mail.user}")
	private String user;
	@Value("${mail.pwd}")
	private String pwd;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private VelocityEngine velocityEngine;

	private Store store;
	private Folder folder;

	public MailService(String mailServer, String protocal, String user, String pwd) {
		this.mailServer = mailServer;
		this.protocal = protocal;
		this.user = user;
		this.pwd = pwd;
	}

	public void connect() throws Exception {
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", this.protocal);
		props.setProperty("mail.pop3.host", this.mailServer);

		Session session = Session.getInstance(props);
		session.setDebug(false);

		this.store = session.getStore();
		this.store.connect(this.mailServer, user, pwd);
	}

	public void disconnect() {
		try {
			this.folder.close(true);
			this.store.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Message[] receiveMessage() throws Exception {
		this.folder = this.store.getFolder("inbox");
		try {
			this.folder.open(Folder.READ_WRITE);
			int count = this.folder.getMessageCount();
			SearchTerm st = new SubjectTerm("聊天记录");
			return folder.search(st, this.folder.getMessages(count - 50 > 0 ? count - 50 : 1, count));
		} catch (Exception e) {
			return new Message[] {};
		}
	}

	public void sendMessage(String from, String to, String subject, String content) throws Exception {
		MimeMessage mailMessage = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);

		messageHelper.setTo(to);
		messageHelper.setFrom(from);
		messageHelper.setSubject(subject);
		messageHelper.setText(content, true);

		mailSender.send(mailMessage);
	}
	
	public void sendMessageWithTemplate(String from, String to, String subject, String template, Map<String, Object> model) throws Exception {
		String result = null;  
        try {  
            result = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, template, "GBK", model);
        } catch (Exception e) {
        	throw e;
        }  
        sendMessage(from, to, subject, result);
	}
}
