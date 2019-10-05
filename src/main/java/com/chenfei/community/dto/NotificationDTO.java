package com.chenfei.community.dto;

import com.chenfei.community.model.User;

import lombok.Data;

@Data
public class NotificationDTO {
	private Long id ;
	private Long gmtCreate ;
	private Long notifier ;
	private Long receiver ;
	private Long outerId ;
	private int type ;
	private int status ;
	private User user ;
}
