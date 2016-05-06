package net.nemo.whatever.util;

import java.util.ArrayList;
import java.util.List;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;

import net.nemo.whatever.entity.Chat;
import net.nemo.whatever.entity.ChatMessage;
import net.nemo.whatever.entity.ChatMessageType;

public class MailMessageConverter {
	
	public final static String CHAT_DATE_PATTERN = "(\\d{4}-\\d{1,2}-\\d{1,2})";
	public final static String SENDER_TIME_PATTERN = "([a-zA-Z0-9\u4e00-\u9fa5]+) (\\d+:\\d+)";
	public final static String IMAGE_MSG_PATTERN = "图片(\\d+)（可在附件中查看）";
	public final static String GROUP_CHAT_TITLE_PATTERN = "微信群\"([a-zA-Z0-9\u4e00-\u9fa5]+)\"的聊天记录";
	public final static String DIALOG_CHAT_TITLE_PATTERN = "\"([a-zA-Z0-9\u4e00-\u9fa5]+)\"和\"([a-zA-Z0-9\u4e00-\u9fa5]+)\"的聊天记录";
	
	public static Chat fromMailMessage(Message message) {
		Chat chat = new Chat();
		try {
			chat.setDateTime(message.getSentDate());
			chat.setGroupChat(isGroupChat(message.getSubject()));
			chat.setChatOwner(getMessageOwner(message.getSubject()));
			chat.setReceiverName(getMessageReceiverName(message.getSubject()));
			chat.setReceiverEmail(((InternetAddress)message.getFrom()[0]).getAddress().toString());

			List<ChatMessage> chatMessages = new ArrayList<ChatMessage>();
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
						chatMessages.addAll(parseMessageBody(bodyPart.getContent()));
					}
				}
			}
			chat.setChatMessages(chatMessages);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return chat;
	}

	private static List<ChatMessage> parseMessageBody(Object content) {
		List<ChatMessage> messages = new ArrayList<ChatMessage>();

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
					ChatMessage chatMessage = new ChatMessage();
					chatMessage.setContent(matches.get(0));
					chatMessage.setType(ChatMessageType.IMAGE);
					chatMessage.setSender(sender);
					chatMessage.setTime(DateUtil.parseDate(date+" "+time));
					
					messages.add(chatMessage);
					
					time=null;
					sender=null;
				}
				//Text content of the message
				else{
					if(time==null && sender == null) continue;
					
					ChatMessage chatMessage = new ChatMessage();
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

	private static String getMessageReceiverName(String messageSubject) {
		List<String> dialogMatches = StringUtil.findFirstMatch(DIALOG_CHAT_TITLE_PATTERN, messageSubject);
		return dialogMatches != null ? dialogMatches.get(1) : "";
	}
}
