package net.nemo.whatever.service;

import java.util.HashMap;
import java.util.Map;

import net.nemo.whatever.util.HttpUtil;
import net.nemo.whatever.util.StringUtil;
import org.springframework.stereotype.Service;

@Service
public class WechatService {
	
	public final static String CORP_ID = "wx6ccf61a87accb57d";
	public final static String CORP_SECRET = "MxI3TT0gpuPbOdJUkcj-YA1tlmWotSho23iys0aSCp3QBVXbdtnCMYZcY3p5haWx";
	
	public final static String ROOT_PATH_QYAPI = "https://qyapi.weixin.qq.com/cgi-bin/";
	public final static String PATH_GET_USER_INFO = ROOT_PATH_QYAPI + "user/getuserinfo?access_token=%s&code=%s";
	public final static String PATH_CONVERT_TO_OPENID = ROOT_PATH_QYAPI + "user/convert_to_openid?access_token=%s";
	public final static String PATH_GET_ACCESS_TOKEN = ROOT_PATH_QYAPI + "gettoken?corpid=%s&corpsecret=%s";
	
	public String getAccessToken(){
		String url = String.format(PATH_GET_ACCESS_TOKEN, CORP_ID, CORP_SECRET);
		return (String)StringUtil.json2Map(HttpUtil.get(url)).get("access_token");
	}

	public String getOpenId(String code){
		return userID2OpenID(getUserID(code));
	}
	
	public String getUserID(String code){
		String url = String.format(PATH_GET_USER_INFO, getAccessToken(), code);
		return (String)StringUtil.json2Map(HttpUtil.get(url)).get("UserId");
	}
	
	@SuppressWarnings("serial")
	public String userID2OpenID(final String userID){
		String url = String.format(PATH_CONVERT_TO_OPENID, getAccessToken());
		Map<String, String> params = new HashMap<String, String>(){{
			put("userid", userID);
		}};
		return StringUtil.json2Map(HttpUtil.post(url, params)).get("openid").toString();
	}
	
}
