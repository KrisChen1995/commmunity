package com.chenfei.community.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.chenfei.community.dto.FileDTO;
import com.chenfei.community.provider.UcloudProvider;

@Controller
public class FileController {
	
	@Autowired
	private UcloudProvider ucloudProvider ;
	
	@ResponseBody
	@RequestMapping("/file/upload")
	public FileDTO uploadImage(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request ;
		MultipartFile file =  multipartRequest.getFile("editormd-image-file");
		
		try {
			String fileName = ucloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());

			FileDTO fileDTO = new FileDTO();
			fileDTO.setSuccess(1);
			fileDTO.setUrl(fileName);
			return fileDTO ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		FileDTO fileDTO = new FileDTO() ;
		fileDTO.setSuccess(1);
		fileDTO.setUrl("/images");
		return fileDTO ;
	} 
}
