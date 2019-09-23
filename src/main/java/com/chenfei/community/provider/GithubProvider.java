package com.chenfei.community.provider;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.chenfei.community.dto.AccessTokenDTO;
import com.chenfei.community.dto.GithubUser;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class GithubProvider {
	
	public String getAccessToken(AccessTokenDTO accessTokenDTO) {
			
		MediaType mediatype = MediaType.get("application/json; charset=utf-8");
		OkHttpClient client = new OkHttpClient();
    	RequestBody body = RequestBody.create(mediatype, JSON.toJSONString(accessTokenDTO));
    	Request request = new Request.Builder()
    				.url("https://github.com/login/oauth/access_token")
    				.post(body)
    				.build();
    	try (Response response = client.newCall(request).execute()) {
    	String string =  response.body().string();
//    	System.out.println(string);
    	String token = string.split("&")[0].split("=")[1];
//    	System.out.println(token);
    	return token ;
    	} catch (Exception e) {
    		e.printStackTrace();
		}
    	return null ;
	}
	
	public GithubUser getUser(String accessToken) {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
			      .url("https://api.github.com/user?access_token=" + accessToken)
			      .build();
		try {
			Response response = client.newCall(request).execute();
			String string =  response.body().string();
			GithubUser githubUser = JSON.parseObject(string, GithubUser.class) ;
			return githubUser ;
		} catch (IOException e) {
		}
		return null ;
	}
}
