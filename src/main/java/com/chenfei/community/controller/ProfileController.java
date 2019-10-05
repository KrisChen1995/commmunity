package com.chenfei.community.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.chenfei.community.dto.PaginationDTO;
import com.chenfei.community.model.User;
import com.chenfei.community.service.NotificationService;
import com.chenfei.community.service.QuestionService;

@Controller
public class ProfileController {
	
	@Autowired
	private QuestionService questionService ;
	
	@Autowired
	private NotificationService notificationService ;
	
	@GetMapping("/profile/{action}")
	public String profile(@PathVariable(name="action") String action,Model model,
										HttpServletRequest request,
										@RequestParam(name="page", defaultValue="1") Integer page, 
										@RequestParam(name="size", defaultValue="5") Integer size) {
		User user = (User)request.getSession().getAttribute("user"); 
		if(user == null) {
			return "redirect:/";
		}
		
		if("questions".equals(action)) {
			model.addAttribute("section", "questions");
			model.addAttribute("sectionName", "我的问题");
			PaginationDTO paginationDTO = questionService.list(user.getId() ,page, size) ;
			model.addAttribute("paginationDTO", paginationDTO);
		}else if("replies".equals(action)) {
			
			PaginationDTO notificationDTO =  notificationService.list(user.getId(), page, size);
			model.addAttribute("notificationDTO", notificationDTO);
			model.addAttribute("section", "replies");
			model.addAttribute("sectionName", "最新回复");
		}
		
		
		return "profile" ;
	}
	
}
