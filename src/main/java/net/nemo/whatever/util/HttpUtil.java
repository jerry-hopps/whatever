package net.nemo.whatever.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

public class HttpUtil {

	public static String get(String url) {
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

		HttpGet httpGet = new HttpGet(url);
		try {
			// 执行get请求
			HttpResponse httpResponse = closeableHttpClient.execute(httpGet);
			// 获取响应消息实体
			HttpEntity entity = httpResponse.getEntity();
			// 判断响应实体是否为空
			if (entity != null) {
				return EntityUtils.toString(entity);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭流并释放资源
				closeableHttpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String post(String url, Map<String, String> params) {
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

		try {
			HttpPost post = postForm(url, params);
			HttpResponse httpResponse = closeableHttpClient.execute(post);

			// 获取响应消息实体
			HttpEntity entity = httpResponse.getEntity();
			// 判断响应实体是否为空
			if (entity != null) {
				return EntityUtils.toString(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭流并释放资源
				closeableHttpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	private static HttpPost postForm(String url, Map<String, String> params) throws Exception{
		HttpPost httpost = new HttpPost(url);

		if (params != null && !params.isEmpty()) {
			String json = JSON.toJSONString(params);
			HttpEntity entity = new ByteArrayEntity(json.getBytes("UTF-8"));
			httpost.setEntity(entity);
		}
		return httpost;
	}
}
