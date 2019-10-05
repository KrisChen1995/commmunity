package com.chenfei.community.controller;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chenfei.community.dto.QuestionDTO;
import com.chenfei.community.model.Question;
import com.chenfei.community.model.User;
import com.chenfei.community.service.QuestionService;

@Controller
public class PublishController {

	
	@Autowired
	private QuestionService questionService ;

	
	@GetMapping("/publish/{id}")
	public String edit(@PathVariable("id") Long id,Model model) {
		QuestionDTO question = questionService.getQuestionByid(id);
		model.addAttribute("title", question.getTitle());
		model.addAttribute("description", question.getDescription());
		model.addAttribute("tag", question.getTag());
		model.addAttribute("id", question.getId());
		return "publish";
	}
	@GetMapping("/publish")
	public String publish() {
		return "publish";
	}
	
	@PostMapping("/publish")
	public String doPublish(@RequestParam(value="title", required=false) String title,
							@RequestParam(value="description",required=false) String description,
							@RequestParam(value="tag", required=false) String tags,
							@RequestParam(value="id", required=false) Long id,
							HttpServletRequest request,
							Model model) {
		model.addAttribute("title", title);
		model.addAttribute("description", description);
		model.addAttribute("tag", tags);
		if(title==null || title=="") {
			model.addAttribute("error", "标题不能为空");
			return "publish";
		}
		if(description==null || description=="") {
			model.addAttribute("error", "问题补充不能为空");
			return "publish";
		}
		if(tags == null || tags=="") {
			model.addAttribute("error", "标签不能为空");
			return "publish";
		}
		User user = (User)request.getSession().getAttribute("user"); 
		if(user == null) {
			model.addAttribute("error","用户未登录");
			return "publish";
		}
		
		Question question = new Question();
		question.setTitle(title);
		question.setDescription(description);
		question.setTag(tags.toLowerCase());
		question.setCreator(user.getId());
		question.setGmtCreate(System.currentTimeMillis());
		question.setGmtModified(question.getGmtCreate());
		question.setId(id);
		questionService.createOrUpdate(question);
		return "redirect:/";
		
	}
}
