package net.nemo.whatever.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;

public class StringUtil {

	public static List<String> findFirstMatch(String pattern, String str) {
		List<String> matches = null;

		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(str);

		if (m.find()) {
			matches = new ArrayList<String>();
			for (int i = 1; i <= m.groupCount(); i++) {
				matches.add(m.group(i));
			}
		}
		return matches;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> json2Map(String jsonStr) {
		return (Map<String, Object>) JSON.parse(jsonStr);
	}

	public static String getUserAgentViewName(HttpServletRequest request, String viewName) {
		String browserDetails = request.getHeader("User-Agent");
		String userAgent = browserDetails;

		String os = "";

		// =================OS=======================
		if (userAgent.toLowerCase().indexOf("android") >= 0) {
			os = "mobile";
		} else if (userAgent.toLowerCase().indexOf("iphone") >= 0) {
			os = "ios";
		} else if (userAgent.toLowerCase().indexOf("windows") >= 0) {
			os = "web";
		} else if (userAgent.toLowerCase().indexOf("mac") >= 0) {
			os = "web";
		} else if (userAgent.toLowerCase().indexOf("x11") >= 0) {
			os = "web";
		} else {
			os = "web";
		}
		
		return os + "/" + viewName;
	}

}
