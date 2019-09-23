package com.chenfei.community.dto;

import com.chenfei.community.model.User;

import lombok.Data;

@Data
public class QuestionDTO {
	private Long id ;
	private String name ;
	private String accountId ;
	private String token ;
	private String bio ;
	private Long gmtCreate ;
	private Long gmtModified ;
	private String avatarUrl ;
	private String title ;
	private String description ;
	private Long creator ;
	private Integer commentCount ;
	private Integer viewCount ;
	private Integer likeCount ;
	private String tag ;
	private User user ;
}
