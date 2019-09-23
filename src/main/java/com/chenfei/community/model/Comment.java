package com.chenfei.community.model;

import lombok.Data;

@Data
public class Comment {
	
	private Long id ;
	private Long parentId ;
	private Long gmtCreate ;
	private Long gmtModified ;
	private Long commentator ;
	private Integer type ;
	private Integer likeCount ;
	private String content ;
	private Integer commentCount ;

}
