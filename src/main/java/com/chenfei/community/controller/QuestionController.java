package com.chenfei.community.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.chenfei.community.dto.CommentDTO;
import com.chenfei.community.dto.QuestionDTO;
import com.chenfei.community.enums.CommentTypeEnum;
import com.chenfei.community.service.CommentService;
import com.chenfei.community.service.QuestionService;

@Controller
public class QuestionController {
	
	
	@Autowired
	QuestionService questionService ;
	
	@Autowired
	CommentService commentService ;
	
	@GetMapping("/question/{id}")
	public String question(@PathVariable("id") Long id ,
							Model model,
							HttpServletRequest request) {
		//累加阅读数
		questionService.getViewCount(id);
		QuestionDTO questionDTO = questionService.getQuestionByid(id);
		List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
		List<CommentDTO> comments = commentService.getComment(id, CommentTypeEnum.QUESTION.getType());
		model.addAttribute("questionDTO", questionDTO);
		model.addAttribute("comments", comments);
		model.addAttribute("relatedQuestions", relatedQuestions);
		return "question";
	}
}
