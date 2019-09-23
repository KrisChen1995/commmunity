package com.chenfei.community.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chenfei.community.dto.CommentCreateDTO;
import com.chenfei.community.dto.CommentDTO;
import com.chenfei.community.dto.ResultDTO;
import com.chenfei.community.enums.CommentTypeEnum;
import com.chenfei.community.exception.CustomizeErrorCode;
import com.chenfei.community.model.Comment;
import com.chenfei.community.model.User;
import com.chenfei.community.service.CommentService;

@Controller
public class CommentController {
	
	@Autowired
	private CommentService commentService ;
	
	@ResponseBody
	@RequestMapping(value="/comment", method=RequestMethod.POST)
	public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
						HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute("user");
		if(user == null) {
			return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
		}
		
		if(commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
			return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
		}
		Comment comment = new Comment();
		comment.setParentId(commentCreateDTO.getParentId());
		comment.setType(commentCreateDTO.getType());
		comment.setCommentator(user.getId());
		comment.setContent(commentCreateDTO.getContent());
		comment.setLikeCount(0);
		comment.setGmtCreate(System.currentTimeMillis());
		comment.setGmtModified(System.currentTimeMillis());
		commentService.insert(comment);
		return ResultDTO.okOf() ;
	}
	
	@ResponseBody
	@RequestMapping(value="/comment/{id}", method=RequestMethod.GET)
	public ResultDTO<List<CommentDTO>> comments(@PathVariable("id") Long id) {
		
		List<CommentDTO> commentsDTO = commentService.getComment(id, CommentTypeEnum.COMMENT.getType());
		return ResultDTO.okOf(commentsDTO);
	}
}
