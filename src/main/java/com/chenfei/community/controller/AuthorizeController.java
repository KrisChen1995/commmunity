package com.chenfei.community.controller;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chenfei.community.dto.AccessTokenDTO;
import com.chenfei.community.dto.GithubUser;
import com.chenfei.community.model.User;
import com.chenfei.community.provider.GithubProvider;
import com.chenfei.community.service.UserService;

@Controller
public class AuthorizeController {
	
	@Autowired
	GithubProvider githubProvider ;
	
	@Autowired
	private UserService userService ;
	
	@Value("${github.client.id}")
	private String clientId ;
	
	@Value("${github.client_secret}")
	private String clientSecret ;
	
	@Value("${github.redirect_uri}")
	private String redirectUri ;
	
	@GetMapping("/callback")
	public String callback(@RequestParam(name="code") String code,
						   @RequestParam(name="state") String state,
						   HttpServletRequest request,
						   HttpServletResponse response) {
		AccessTokenDTO accessTokenDTO = new AccessTokenDTO() ;
		accessTokenDTO.setClient_id(clientId);
		accessTokenDTO.setClient_secret(clientSecret);
		accessTokenDTO.setCode(code);
		accessTokenDTO.setRedirect_uri(redirectUri);
		accessTokenDTO.setState(state);
		String accessToken = githubProvider.getAccessToken(accessTokenDTO) ;
		GithubUser githubUser = githubProvider.getUser(accessToken);
		if(githubUser != null && githubUser.getId() !=null) {
			User user = new User() ;
			String token = UUID.randomUUID().toString();
			user.setToken(token);
			user.setName(githubUser.getName());
			user.setAccountId(String.valueOf(githubUser.getId()));
			user.setBio(githubUser.getBio());
			user.setAvatarUrl(githubUser.getAvatarUrl());
			userService.createOrUpdate(user);
			response.addCookie(new Cookie("token", token));
			return "redirect:/";
		} else {
			//登录失败，重新登录
			return "redirect:/";
		}
	}
	
	@GetMapping("/logout")
	public String logou(HttpServletRequest request,
						HttpServletResponse response){
		request.getSession().removeAttribute("user");
		Cookie cookie = new Cookie("token",null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return "redirect:/";
	}

}
