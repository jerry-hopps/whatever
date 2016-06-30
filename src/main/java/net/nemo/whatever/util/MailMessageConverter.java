package net.nemo.whatever.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.nemo.whatever.entity.Attachment;
import net.nemo.whatever.entity.Chat;
import net.nemo.whatever.entity.ChatMessageType;
import net.nemo.whatever.entity.Message;
import net.nemo.whatever.entity.User;

public class MailMessageConverter {
	
	public final static String CHAT_DATE_PATTERN = "(\\d{4}-\\d{1,2}-\\d{1,2})";
	public final static String SENDER_TIME_PATTERN = "([a-zA-Z0-9\u4e00-\u9fa5]+) (\\d+:\\d+)";
	public final static String IMAGE_MSG_PATTERN = "图片(\\d+)（可在附件中查看）";
	public final static String LINK_MSG_PATTERN = "\\[(.*) : (http[s]?://.*)]";
	public final static String GROUP_CHAT_TITLE_PATTERN = "微信群\"([a-zA-Z0-9\u4e00-\u9fa5]+)\"的聊天记录";
	public final static String DIALOG_CHAT_TITLE_PATTERN = "\"([a-zA-Z0-9\u4e00-\u9fa5]+)\"和\"([a-zA-Z0-9\u4e00-\u9fa5]+)\"的聊天记录";
	
	public static String FILE_STORE_PATH = "/var/lib/tomcat6/webapps/ROOT/images";
	
//	static{
//		try{
//			String rootPath = new File(MailMessageConverter.class.getResource("").toURI()).getParent().replace("WEB-INF/classes/net/nemo/whatever", "");
//			FILE_STORE_PATH = StringUtils.join(new String[]{rootPath, "static", "images"}, System.getProperty("file.separator"));
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
	
	public static Chat fromMailMessage(javax.mail.Message message) {
		Chat chat = new Chat();
		try {
			chat.setGroupChat(isGroupChat(message.getSubject()));
			chat.setChatOwner(getMessageOwner(message.getSubject()));
			chat.setReceiver(getMessageReceiver(message.getSubject(), (InternetAddress) message.getFrom()[0]));

			List<Message> messages = new ArrayList<Message>();
			List<Attachment> attachments = new ArrayList<Attachment>();

			Part part = (Part) message;
			if (part.isMimeType("multipart/*")) {
				Multipart multipart = (Multipart) part.getContent();
				int counts = multipart.getCount();
				for (int j = 0; j < counts; j++) {
					BodyPart bodyPart = multipart.getBodyPart(j);
					if (bodyPart.isMimeType("multipart/*")) {
						saveAttachments(attachments, bodyPart);
					} else {
						messages.addAll(parseMessageBody(bodyPart.getContent()));
					}
				}
			}
			chat.setAttachments(attachments);
			
			for(Message msg : messages){
				if(ChatMessageType.IMAGE == msg.getType()){
					for(Attachment att : attachments){
						if(att.getFileName().endsWith(msg.getContent())){
							msg.setContent(att.getFileName());
							break;
						}
					}
				}
			}
			chat.setMessages(messages);
			
			message.setFlag(Flags.Flag.DELETED, true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return chat;
	}

	private static List<Message> parseMessageBody(Object content) {
		List<Message> messages = new ArrayList<Message>();

		String[] lines = content.toString().split(System.getProperty("line.separator"));
		String sender = null, date = null, time = null;
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i].replaceAll("\\r", "");
			if (!"".equals(line)) {
				List<String> matches = null;

				// Date of the chat
				if (null != (matches = StringUtil.findFirstMatch(CHAT_DATE_PATTERN, line))) {
					date = matches.get(0);
				}
				// Name:Time of the message
				else if (null != (matches = StringUtil.findFirstMatch(SENDER_TIME_PATTERN, line))) {
					sender = matches.get(0);
					time = matches.get(1);
				}
				// Image content of the message
				else if (null != (matches = StringUtil.findFirstMatch(IMAGE_MSG_PATTERN, line))) {
					Message chatMessage = new Message();
					chatMessage.setContent(String.format("__%s.png", matches.get(0)));
					chatMessage.setType(ChatMessageType.IMAGE);
					chatMessage.setSender(sender);
					chatMessage.setTime(DateUtil.parseDate(date + " " + time));

					messages.add(chatMessage);

					time = null;
					sender = null;
				} else if (null != (matches = StringUtil.findFirstMatch(LINK_MSG_PATTERN, line))) {
					Message chatMessage = new Message();
					chatMessage.setContent(getLinkPreviewSegement(line));
					chatMessage.setType(ChatMessageType.LINK);
					chatMessage.setSender(sender);
					chatMessage.setTime(DateUtil.parseDate(date + " " + time));

					messages.add(chatMessage);

					time = null;
					sender = null;
				}
				// Text content of the message
				else {
					if (time == null && sender == null)
						continue;

					Message chatMessage = new Message();
					chatMessage.setContent(line);
					chatMessage.setType(ChatMessageType.TEXT);
					chatMessage.setSender(sender);
					chatMessage.setTime(DateUtil.parseDate(date + " " + time));

					messages.add(chatMessage);

					time = null;
					sender = null;
				}
			}
		}

		return messages;
	}

	private static void saveAttachments(List<Attachment> attachements, Part part) throws Exception {
		String fileName = "";
		if (part.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) part.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				BodyPart mPart = mp.getBodyPart(i);
				String disposition = mPart.getDisposition();
				if ((disposition != null)
						&& ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE)))) {
					fileName = mPart.getFileName();
					if (fileName.toLowerCase().indexOf("gb2312") != -1) {
						fileName = MimeUtility.decodeText(fileName);
					}
					String timestampedName = getTimestampedName(fileName);
					Attachment attachment = new Attachment(timestampedName, String.format("%s%s%s", DateUtil.formatDate(new Date(), "yyyyMMdd"), System.getProperty("file.separator"), timestampedName));
					attachements.add(attachment);
					saveFile(attachment, mPart.getInputStream());
				} else if (mPart.isMimeType("multipart/*")) {
					saveAttachments(attachements, mPart);
				} else {
					fileName = mPart.getFileName();
					if ((fileName != null) && (fileName.toLowerCase().indexOf("GB2312") != -1)) {
						fileName = MimeUtility.decodeText(fileName);
						String timestampedName = getTimestampedName(fileName);
						Attachment attachment = new Attachment(timestampedName, String.format("%s%s%s", DateUtil.formatDate(new Date(), "yyyyMMdd"), System.getProperty("file.separator"), timestampedName));
						attachements.add(attachment);
						saveFile(attachment, mPart.getInputStream());
					}
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			saveAttachments(attachements, (Part) part.getContent());
		}
	}

	private static String getTimestampedName(String fileName) {
		Date now = new Date();
		return String.format("%d_%s", now.getTime(), fileName);
	}

	private static void saveFile(Attachment attachment, InputStream stream) throws Exception{
		File dir = new File(FILE_STORE_PATH + System.getProperty("file.separator") + DateUtil.formatDate(new Date(), "yyyyMMdd"));
		if(!dir.exists()){
			dir.mkdir();
		}
		
		File storefile = new File(FILE_STORE_PATH + System.getProperty("file.separator") + attachment.getPath());
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(storefile));
			bis = new BufferedInputStream(stream);
			int c;
			while ((c = bis.read()) != -1) {
				bos.write(c);
				bos.flush();
			}
			System.out.println("File saved to : " + storefile.getAbsolutePath());
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new Exception("文件保存失败!");
		} finally {
			bos.close();
			bis.close();
		}
	}

	private static boolean isGroupChat(String messageSubject) {
		List<String> matches = StringUtil.findFirstMatch(GROUP_CHAT_TITLE_PATTERN, messageSubject);
		return matches != null;
	}

	private static String getMessageOwner(String messageSubject) {
		List<String> groupMatches = StringUtil.findFirstMatch(GROUP_CHAT_TITLE_PATTERN, messageSubject);
		List<String> dialogMatches = StringUtil.findFirstMatch(DIALOG_CHAT_TITLE_PATTERN, messageSubject);
		return groupMatches == null ? dialogMatches.get(0) : groupMatches.get(0);
	}

	private static User getMessageReceiver(String messageSubject, InternetAddress from) {
		List<String> dialogMatches = StringUtil.findFirstMatch(DIALOG_CHAT_TITLE_PATTERN, messageSubject);
		String name = dialogMatches != null ? dialogMatches.get(1) : "";
		String email = from.getAddress().toString();
		return new User(name, email);
	}
	
	private static String getLinkPreviewSegement(String line){
		String[] aa = line.replace("[", "").replace("]", "").split(" : ");
		String imgSrc = getLinkImage(aa[1]);
		
		String template = "<a class=\"fragment\" href=\"%s\">"
				+ "<img src =\"%s\" width=\"116px\" height=\"116px\"/>"
				+ "<h3>%s</h3>"
				+ "<p class=\"text\"></p></a>";
		
		return String.format(template, aa[1], imgSrc, aa[0]);
	}
	
	private static String getLinkImage(String url) {
		Document doc;
		try {
			doc = Jsoup.connect(url).get();

			Elements links = doc.select("img");
			for (Element link : links) {
				String src = link.attr("data-src") == null ? link.attr("src") : link.attr("data-src");
				if (src != null && !"".equals(src.trim())) {
					return src;
				}
			}
			return "/whatever/static/img/link.jpg";
		} catch (IOException e) {
			return "/whatever/static/img/link.jpg";
		}
	}
}
