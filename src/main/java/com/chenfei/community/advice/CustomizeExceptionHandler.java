package com.chenfei.community.advice;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.chenfei.community.dto.ResultDTO;
import com.chenfei.community.exception.CustomizeErrorCode;
import com.chenfei.community.exception.CustomizeException;

@ControllerAdvice
public class CustomizeExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	ModelAndView handle(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		String contentType = request.getContentType();
		ResultDTO resultDTO ;
		if("application/json".equals(contentType)) {
			//返回json
			if(e instanceof CustomizeException) {
				
				resultDTO = ResultDTO.errorOf((CustomizeException) e) ;
				
			}else {
				resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR) ;
			}
			try {
				response.setContentType("application/json;charset=utf-8");
				response.setStatus(200);
				response.setCharacterEncoding("utf-8");
				PrintWriter printWriter = response.getWriter() ;
				printWriter.write(JSON.toJSONString(resultDTO));
				printWriter.close();
				
			} catch (IOException e1) {
			}
			return null ;
			
		}else {
			//错误页面跳转
			if(e instanceof CustomizeException) {
				model.addAttribute("message", e.getMessage());
			}else {
				model.addAttribute("message", CustomizeErrorCode.SYSTEM_ERROR.getMessage());
			}
			return new ModelAndView("error");
		}
		
		
	}

}
