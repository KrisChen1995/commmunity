package com.chenfei.community.model;

import lombok.Data;

@Data
public class Question {
	private Long id ;
	private String title ;
	private String description ;
	private Long gmtCreate ;
	private Long gmtModified ;
	private Long creator ;
	private Integer commentCount ;
	private Integer viewCount ;
	private Integer likeCount ;
	private String tag ;
}
