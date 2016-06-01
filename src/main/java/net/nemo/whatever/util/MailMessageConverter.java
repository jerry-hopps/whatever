package net.nemo.whatever.util;

import java.util.ArrayList;
import java.util.List;

import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;

import net.nemo.whatever.entity.Chat;
import net.nemo.whatever.entity.Message;
import net.nemo.whatever.entity.ChatMessageType;
import net.nemo.whatever.entity.User;

public class MailMessageConverter {
	
	public final static String CHAT_DATE_PATTERN = "(\\d{4}-\\d{1,2}-\\d{1,2})";
	public final static String SENDER_TIME_PATTERN = "([a-zA-Z0-9\u4e00-\u9fa5]+) (\\d+:\\d+)";
	public final static String IMAGE_MSG_PATTERN = "图片(\\d+)（可在附件中查看）";
	public final static String LINK_MSG_PATTERN = "\\[(.*) : (http[s]?://.*)]";
	public final static String GROUP_CHAT_TITLE_PATTERN = "微信群\"([a-zA-Z0-9\u4e00-\u9fa5]+)\"的聊天记录";
	public final static String DIALOG_CHAT_TITLE_PATTERN = "\"([a-zA-Z0-9\u4e00-\u9fa5]+)\"和\"([a-zA-Z0-9\u4e00-\u9fa5]+)\"的聊天记录";
	
	public static Chat fromMailMessage(javax.mail.Message message) {
		Chat chat = new Chat();
		try {
			chat.setGroupChat(isGroupChat(message.getSubject()));
			chat.setChatOwner(getMessageOwner(message.getSubject()));
			chat.setReceiver(getMessageReceiver(message.getSubject(), (InternetAddress)message.getFrom()[0]));

			List<Message> messages = new ArrayList<Message>();
			List<String> attachementPaths = new ArrayList<String>();
			
			Part part = (Part) message;
			if (part.isMimeType("multipart/*")) {
				Multipart multipart = (Multipart) part.getContent();
				int counts = multipart.getCount();
				for (int j = 0; j < counts; j++) {
					BodyPart bodyPart = multipart.getBodyPart(j);
					if (bodyPart.isMimeType("multipart/*")) {
						saveAttachments(attachementPaths, bodyPart);
					} else {
						messages.addAll(parseMessageBody(bodyPart.getContent()));
					}
				}
			}
			chat.setMessages(messages);
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
				
				//Date of the chat
				if(null != (matches = StringUtil.findFirstMatch(CHAT_DATE_PATTERN, line))){
					date = matches.get(0);
				}
				//Name:Time of the message
				else if(null != (matches = StringUtil.findFirstMatch(SENDER_TIME_PATTERN, line))){
					sender = matches.get(0);
					time = matches.get(1);
				}
				//Image content of the message
				else if(null != (matches = StringUtil.findFirstMatch(IMAGE_MSG_PATTERN, line))){
					Message chatMessage = new Message();
					chatMessage.setContent(matches.get(0));
					chatMessage.setType(ChatMessageType.IMAGE);
					chatMessage.setSender(sender);
					chatMessage.setTime(DateUtil.parseDate(date+" "+time));
					
					messages.add(chatMessage);
					
					time=null;
					sender=null;
				}
				else if(null != (matches = StringUtil.findFirstMatch(LINK_MSG_PATTERN, line))){
					Message chatMessage = new Message();
					chatMessage.setContent(line);
					chatMessage.setType(ChatMessageType.LINK);
					chatMessage.setSender(sender);
					chatMessage.setTime(DateUtil.parseDate(date+" "+time));
					
					messages.add(chatMessage);
					
					time=null;
					sender=null;
				}
				//Text content of the message
				else{
					if(time==null && sender == null) continue;
					
					Message chatMessage = new Message();
					chatMessage.setContent(line);
					chatMessage.setType(ChatMessageType.TEXT);
					chatMessage.setSender(sender);
					chatMessage.setTime(DateUtil.parseDate(date+" "+time));
					
					messages.add(chatMessage);
					
					time=null;
					sender=null;
				}
			}
		}

		return messages;
	}

	private static void saveAttachments(List<String> fileNames, Part part) throws Exception {
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
					fileNames.add(fileName);
				} else if (mPart.isMimeType("multipart/*")) {
					saveAttachments(fileNames, mPart);
				} else {
					fileName = mPart.getFileName();
					if ((fileName != null) && (fileName.toLowerCase().indexOf("GB2312") != -1)) {
						fileName = MimeUtility.decodeText(fileName);
						fileNames.add(fileName);
					}
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			saveAttachments(fileNames, (Part) part.getContent());
		}
	}

	private static boolean isGroupChat(String messageSubject) {
		List<String> matches = StringUtil.findFirstMatch(GROUP_CHAT_TITLE_PATTERN, messageSubject);
		return matches != null;
	}

	private static String getMessageOwner(String messageSubject) {
		List<String> groupMatches = StringUtil.findFirstMatch(GROUP_CHAT_TITLE_PATTERN, messageSubject);
		List<String> dialogMatches = StringUtil.findFirstMatch(DIALOG_CHAT_TITLE_PATTERN, messageSubject);
		return groupMatches == null ? dialogMatches.get(0): groupMatches.get(0);
	}

	private static User getMessageReceiver(String messageSubject, InternetAddress from) {
		List<String> dialogMatches = StringUtil.findFirstMatch(DIALOG_CHAT_TITLE_PATTERN, messageSubject);
		String name = dialogMatches != null ? dialogMatches.get(1) : "";
		String email = from.getAddress().toString();
		return new User(name, email);
	}
	
	public static void main(String[] args) {
		String linkText = "[我用滴滴打车,一触即达，送你券，为每一次及时出发买单 : http://pay.xiaojukeji.com/veyron/market_entry/hbrob/gethongbao?id=VLJJvSCE201606011804513239319813&sign=9561b6ab044407ffd50e599a95f1837f]";
		System.out.println(StringUtil.findFirstMatch("\\[(.*) : (http[s]?://.*)]", linkText));
	}
}
