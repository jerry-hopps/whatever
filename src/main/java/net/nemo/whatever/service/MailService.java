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
		// 创建一个有具体连接信息的Properties对象
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", this.protocal);
		props.setProperty("mail.pop3.host", this.mailServer);

		// 使用Properties对象获得Session对象
		Session session = Session.getInstance(props);
		session.setDebug(false);

		// 利用Session对象获得Store对象，并连接pop3服务器
		this.store = session.getStore();
		this.store.connect(this.mailServer, user, pwd);
	}

	public Message[] receiveMessage() throws Exception{
		// 获得邮箱内的邮件夹Folder对象，以"读-写"打开
		Folder folder = this.store.getFolder("inbox");
		try{
			folder.open(Folder.READ_WRITE);
			// 搜索发件人为主题为"测试1"的邮件
			SearchTerm st = new SubjectTerm("聊天记录");
			int count = folder.getMessageCount();
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
			for(int i =0; i< msgs.length; i++){
				Part part = (Part) msgs[i];
				if(part.isMimeType("multipart/*")){
					Multipart multipart = (Multipart) part.getContent();   
		            int counts = multipart.getCount();   
		            for (int j = 0; j < counts; j++) {   
		                System.out.println(multipart.getBodyPart(j).getContent());   
		            }
				}
				System.out.println("----------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			service.disconnect();
		}
	}
}
