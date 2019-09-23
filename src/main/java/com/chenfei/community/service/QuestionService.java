package com.chenfei.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenfei.community.dto.PaginationDTO;
import com.chenfei.community.dto.QuestionDTO;
import com.chenfei.community.exception.CustomizeErrorCode;
import com.chenfei.community.exception.CustomizeException;
import com.chenfei.community.mapper.QuestionMapper;
import com.chenfei.community.mapper.UserMapper;
import com.chenfei.community.model.Question;
import com.chenfei.community.model.User;

@Service
public class QuestionService {
	
	@Autowired
	private UserMapper userMapper ;
	
	@Autowired
	private QuestionMapper questionMapper ;
	
	public PaginationDTO queryList(Integer page, Integer size) {
		
		PaginationDTO paginationDTO = new PaginationDTO();
		Integer totalPage ;
		Integer totalCount = questionMapper.count();
		if(totalCount % size == 0) {
			totalPage = totalCount / size  ;
		}else {
			totalPage = totalCount /size + 1 ;
		}
	
		if(page < 1) {
			page = 1 ;
		}
		if(page > totalPage) {
			page = totalPage ;
		}
		
		paginationDTO.setPagination(totalPage, page);
		
		
		//分页   size*(page-1)
		Integer offset = size*(page-1) ;
		List<Question> questions = questionMapper.queryList(offset,size);
		List<QuestionDTO> questionsDTOList = new ArrayList<QuestionDTO>();
		
		for(Question question : questions) {
			User user = userMapper.findById(question.getCreator());
			QuestionDTO questionDTO = new QuestionDTO();
			//将question的属性复制给questionDTO,questionDTO中必须存在question的全部属性，不存在则不会处理
			BeanUtils.copyProperties(question, questionDTO);
			questionDTO.setUser(user);
			questionsDTOList.add(questionDTO);
		}
		paginationDTO.setQuestionsDTO(questionsDTOList);
		
		return paginationDTO ;
	}

	public PaginationDTO list(Long userId, Integer page, Integer size) {
		PaginationDTO paginationDTO = new PaginationDTO();
		Integer totalPage ;
		Integer totalCount = questionMapper.countByUserId(userId);
		if(totalCount % size == 0) {
			totalPage = totalCount / size  ;
		}else {
			totalPage = totalCount /size + 1 ;
		}
	
		if(page < 1) {
			page = 1 ;
		}
		if(page > totalPage) {
			page = totalPage ;
		}
		
		paginationDTO.setPagination(totalPage, page);
		//分页   size*(page-1)
		Integer offset = size*(page-1) ;
		List<Question> questions = questionMapper.queryByUserId(userId, offset, size);
		List<QuestionDTO> questionsDTOList = new ArrayList<QuestionDTO>();
		
		for(Question question : questions) {
			User user = userMapper.findById(question.getCreator());
			QuestionDTO questionDTO = new QuestionDTO();
			//将question的属性复制给questionDTO,questionDTO中必须存在question的全部属性，不存在则不会处理
			BeanUtils.copyProperties(question, questionDTO);
			questionDTO.setUser(user);
			questionsDTOList.add(questionDTO);
		}
		paginationDTO.setQuestionsDTO(questionsDTOList);
		
		return paginationDTO ;
		
	}

	public QuestionDTO getQuestionByid(Long id) {
		Question question = questionMapper.getQuestionById(id);
		if(question == null) {
			throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
		}
		QuestionDTO questionDTO = new QuestionDTO();
		BeanUtils.copyProperties(question, questionDTO);
		User user = userMapper.findById(question.getCreator());
		questionDTO.setUser(user);
		return questionDTO ;
		
	}

	public void createOrUpdate(Question question) {
		if(question.getId() == null) {
			question.setGmtCreate(System.currentTimeMillis());
			question.setGmtModified(question.getGmtCreate());
			question.setLikeCount(0);
			question.setViewCount(0);
			question.setCommentCount(0);
			questionMapper.insert(question);
		}
		else {
			question.setGmtModified(System.currentTimeMillis());
			int updated = questionMapper.update(question);
			if(updated != 1) {
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
			}
		}
		
	}

	public void  getViewCount(Long id) {
		Question question = questionMapper.getQuestionById(id);
		int viewCount = question.getViewCount();
		question.setViewCount(viewCount + 1);
		questionMapper.updateViewCount(question);
		
	}

}
