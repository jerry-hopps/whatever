package net.nemo.whatever.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeUtility;

import net.nemo.whatever.entity.Chat;
import net.nemo.whatever.entity.ChatMessage;
import net.nemo.whatever.entity.ChatMessageType;

public class MailMessageConverter {
	public static Chat fromMailMessages(Message message) {
		Chat chat = new Chat();
		try {
			chat.setDateTime(message.getSentDate());
			chat.setGroupChat(isGroupChat(message.getSubject()));
			chat.setChatOwner(getMessageOwner(message.getSubject()));
			chat.setReceiver(getMessageReceiver(message.getSubject()));

			List<ChatMessage> chatMessages = new ArrayList<ChatMessage>();
			List<String> attachementPaths = new ArrayList<String>();
			Part part = (Part) message;
			if (part.isMimeType("multipart/*")) {
				Multipart multipart = (Multipart) part.getContent();
				int counts = multipart.getCount();
				String receiver = null;
				for (int j = 0; j < counts; j++) {

					ChatMessage chatMessage = new ChatMessage();
					chatMessage.setReceiver(chat.getReceiver());

					BodyPart bodyPart = multipart.getBodyPart(j);
					if (bodyPart.isMimeType("multipart/*")) {
						saveAttachments(attachementPaths, bodyPart);
					} else {
						chatMessages.addAll(parseMessageBody(receiver, bodyPart.getContent()));
					}
				}
			}
			chat.setChatMessages(chatMessages);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return chat;
	}

	public static void main(String[] args) {
		String newline = System.getProperty("line.separator");
		String content = "Dear:" + newline + "微信群\"石家人儿\"的聊天记录如下:" + newline + "—————2016-4-13—————" + newline + ""
				+ newline + "石尉 18:10" + newline + "图片1（可在附件中查看）" + newline + "" + newline + "" + newline + "" + newline
				+ "" + newline + "" + newline + "" + newline + "发自我的 iPhone";

		System.out.println(content);
		System.out.println("-----------------------------------");
		System.out.println(parseMessageBody("tony", content));
	}
	
	public static List<String> findFirstMatch(String pattern, String str){
		List<String> matches = null;
		
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(str);
		
		if(m.find()){
			matches = new ArrayList<String>();
			for(int i=1; i<= m.groupCount(); i++){
				matches.add(m.group(i));
			}
		}
		return matches;
	}

	public static List<ChatMessage> parseMessageBody(String receiver, Object content) {
		String[] patterns = new String[]{ "(\\d{4}-\\d{1,2}-\\d{1,2})", "([\u4e00-\u9fa5]{2}) (\\d+:\\d+)", "图片(\\d+)（可在附件中查看）"};
		
		List<ChatMessage> messages = new ArrayList<ChatMessage>();
		
		String[] lines = content.toString().split(System.getProperty("line.separator"));
		String sender = null, date = null, time = null;
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			if (!"".equals(line)) {
				List<String> matches = null;
				
				//Date of the chat
				if(null != (matches = findFirstMatch(patterns[0], line))){
					date = matches.get(0);
				}
				//Name:Time of the message
				else if(null != (matches = findFirstMatch(patterns[1], line))){
					sender = matches.get(0);
					time = matches.get(1);
				}
				//Image content of the message
				else if(null != (matches = findFirstMatch(patterns[2], line))){
					ChatMessage chatMessage = new ChatMessage();
					chatMessage.setContent(matches.get(0));
					chatMessage.setType(ChatMessageType.IMAGE);
					chatMessage.setReceiver(receiver);
					chatMessage.setSender(sender);
					chatMessage.setTime(parseDate(date+" "+time));
					
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
					chatMessage.setReceiver(receiver);
					chatMessage.setSender(sender);
					chatMessage.setTime(parseDate(date+" "+time));
					
					messages.add(chatMessage);
					
					time=null;
					sender=null;
				}
			}
		}

		return messages;
	}

	public static void saveAttachments(List<String> fileNames, Part part) throws Exception {
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
	
	public static Date parseDate(String str){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-dd hh:mm");
		try{
			return simpleDateFormat.parse(str);
		}catch(Exception e){
			return null;
		}
	}
	
	public static String formatDate(Date date){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-dd hh:mm");
		try{
			return simpleDateFormat.format(date);
		}catch(Exception e){
			return null;
		}
		
	}

	private static boolean isGroupChat(String messageSubject) {
		return true;
	}

	private static String getMessageOwner(String messageSubject) {
		return "Tony";
	}

	private static String getMessageReceiver(String messageSubject) {
		return "Tony";
	}
}
