package net.nemo.whatever.service;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailService {

	private String mailServer;
	private String protocal;
	private String user;
	private String pwd;

	private JavaMailSender mailSender;

	private Store store;
	private Folder folder;

	public MailService() {
	}

	public MailService(String mailServer, String protocal, String user, String pwd) {
		this.mailServer = mailServer;
		this.protocal = protocal;
		this.user = user;
		this.pwd = pwd;
	}

	public String getMailServer() {
		return mailServer;
	}

	public void setMailServer(String mailServer) {
		this.mailServer = mailServer;
	}

	public String getProtocal() {
		return protocal;
	}

	public void setProtocal(String protocal) {
		this.protocal = protocal;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
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

	public void disconnect() {
		try {
			this.folder.close(true);
			this.store.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
