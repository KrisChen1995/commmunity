package com.chenfei.community.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chenfei.community.dto.PaginationDTO;
import com.chenfei.community.service.QuestionService;

@Controller
public class IndexController {
	

	@Autowired
	private QuestionService questionService ;
	
	@GetMapping("/")
	public String index(HttpServletRequest request,Model model, 
						@RequestParam(name="page", defaultValue="1") Integer page, 
						@RequestParam(name="size", defaultValue="7") Integer size,
						@RequestParam(name="search", required=false) String search){
		
		PaginationDTO paginationDTO = questionService.queryList(search, page, size);
		if(paginationDTO != null) {
			model.addAttribute("paginationDTO", paginationDTO);
		}
		if(StringUtils.isNotBlank(search)) {
			model.addAttribute("search",search);
		}
		return "index";
		
	}
}
