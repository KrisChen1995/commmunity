package com.chenfei.community.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenfei.community.dto.CommentDTO;
import com.chenfei.community.enums.CommentTypeEnum;
import com.chenfei.community.exception.CustomizeErrorCode;
import com.chenfei.community.exception.CustomizeException;
import com.chenfei.community.mapper.CommentMapper;
import com.chenfei.community.mapper.QuestionMapper;
import com.chenfei.community.mapper.UserMapper;
import com.chenfei.community.model.Comment;
import com.chenfei.community.model.Question;
import com.chenfei.community.model.User;


@Service
public class CommentService {
	
	@Autowired
	private CommentMapper commentMapper ;
	
	@Autowired
	private QuestionMapper questionMapper ;
	
	@Autowired
	private UserMapper userMapper ;
	//处理事务注解
	@Transactional
	public void insert(Comment comment) {
		if(comment.getParentId() == null || comment.getParentId() == 0) {
			throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
		}
		if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
			throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
		}
		
		if(comment.getType() == CommentTypeEnum.COMMENT.getType()) {
			//回复评论
			Comment dbComment = commentMapper.getCommentById(comment.getParentId());
			if(dbComment == null) {
				throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
			}
			commentMapper.insert(comment);
			
		}else {
			//回复问题
			Question question = questionMapper.getQuestionById(comment.getParentId());
			if(question == null) {
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
			}
			commentMapper.insert(comment);
			Integer commentCount = commentMapper.countByparentId(comment.getParentId());
			question.setCommentCount(commentCount);
			questionMapper.updateCommentCount(question);
		}
		
	}


	public List<CommentDTO> getComment(Long id, Integer type) {
		List<Comment> comments = commentMapper.getComment(id, type);
		if(comments.size() == 0) {
			return new ArrayList<CommentDTO>() ;
		}
		//获取去重的评论人
		List<Long> commentators = comments.stream().map(Comment :: getCommentator).distinct().collect(Collectors.toList());
		
		//获取评论人转换成map
		List<User> users = userMapper.getAllUsers(commentators) ;
		Map<Long,User> usersMap= users.stream().collect(Collectors.toMap(User :: getId, user -> user));
		
		//转换comment为commentDTO
		List<CommentDTO> commentDTOs = comments.stream().map(comment -> {
			CommentDTO commentDTO = new CommentDTO();
			BeanUtils.copyProperties(comment, commentDTO);
			commentDTO.setUser(usersMap.get(comment.getCommentator()));
			return commentDTO ;
		}).collect(Collectors.toList());
		
		return commentDTOs ;
	}
}
