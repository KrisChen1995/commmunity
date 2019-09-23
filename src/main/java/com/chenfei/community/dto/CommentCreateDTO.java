package com.chenfei.community.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {
	
	private Long parentId ;
	private Long Commentator ;
	private Integer type ;
	private String content ;
}
