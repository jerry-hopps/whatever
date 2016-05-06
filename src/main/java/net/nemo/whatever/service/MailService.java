package net.nemo.whatever.service;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

import net.nemo.whatever.util.MailMessageConverter;

public class MailService {

	private String mailServer;
	private String protocal;
	private String user;
	private String pwd;
	
	private Store store;

	public MailService(String mailServer, String protocal, String user, String pwd) {
		this.mailServer = mailServer;
		this.protocal = protocal;
		this.user = user;
		this.pwd = pwd;
	}
	
	public void connect() throws Exception{
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", this.protocal);
		props.setProperty("mail.pop3.host", this.mailServer);

		Session session = Session.getInstance(props);
		session.setDebug(false);
		
		this.store = session.getStore();
		this.store.connect(this.mailServer, user, pwd);
	}

	public Message[] receiveMessage() throws Exception{
		Folder folder = this.store.getFolder("inbox");
		try{
			folder.open(Folder.READ_WRITE);	
			int count = folder.getMessageCount();
			SearchTerm st = new SubjectTerm("聊天记录");
			return folder.search(st, folder.getMessages(count-100>0?count-50:0,count));
		}catch(Exception e){
			e.printStackTrace();
			return new Message[]{};
		}finally{
			//folder.close(false);
		}
	}
	
	public void disconnect(){
		try {
			this.store.close();
		} catch (Exception e) {
			
		}
	}
	
	public static void main(String[] args) {
		MailService service = new MailService("pop.163.com", "pop3", "still0007", "tonyshi1A");
		try {
			service.connect();
			
			Message[] msgs = service.receiveMessage();
			//for(int i =0; i< msgs.length; i++){
				MailMessageConverter.fromMailMessages(msgs[0]);
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			service.disconnect();
		}
	}
}
