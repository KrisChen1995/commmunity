package com.chenfei.community.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PaginationDTO {
	private List<QuestionDTO> questionsDTO ;
	private boolean hasPage ; //是否还有前一页
	private boolean hasFirstPage ; //是否还有第一页
	private boolean hasNextPage ; //是否还有下一页
	private boolean hasEndPage ; //是否还有最后一页
	
	private Integer currentPage ; //当前页码
	private List<Integer> currentPages = new ArrayList<Integer>(); //当前页展示的所有页
	private Integer totalPage ;
	public void setPagination(Integer totalPage, Integer page) {
		this.totalPage = totalPage ;
		
		this.currentPage = page ;
		
		
		currentPages.add(page) ;
		for(int i = 1 ; i < 3 ; i ++) {
			if(page-i>0) {
				currentPages.add(0, page-i);
			}
			if(page + i <= totalPage) {
				currentPages.add(page + i) ;
			}
		}
		
		//是否展示上一页
		if(page == 1) {
			hasPage = false ;
		}else {
			hasPage = true ;
		}
		
		//是否展示下一页
		if(page == totalPage) {
			hasNextPage = false ;
		}else {
			hasNextPage = true ;
		}
		
		//是否展示第一页
		if(currentPages.contains(1)) {
			hasFirstPage = false ;
		}else {
			hasFirstPage = true ;
		}
		
		//是否展示最后一页
		if(currentPages.contains(totalPage)) {
			hasEndPage = false ;
		}else {
			hasEndPage = true ;
		}
	}
	
}
