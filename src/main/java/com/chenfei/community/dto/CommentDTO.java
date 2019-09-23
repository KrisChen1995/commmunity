package com.chenfei.community.dto;

import com.chenfei.community.model.User;

import lombok.Data;

@Data
public class CommentDTO {
	private Long id ;
	private Long parentId ;
	private Long gmtCreate ;
	private Long gmtModified ;
	private Long commentator ;
	private Integer type ;
	private Integer likeCount ;
	private String content ;
	private Integer commentCount ;
	private User user ;
}
