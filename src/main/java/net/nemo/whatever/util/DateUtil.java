package net.nemo.whatever.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static Date parseDate(String str){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-dd HH:mm");
		try{
			return simpleDateFormat.parse(str);
		}catch(Exception e){
			return null;
		}
	}
	
	public static String formatDate(Date date){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-dd HH:mm");
		try{
			return simpleDateFormat.format(date);
		}catch(Exception e){
			return null;
		}
		
	}
}
