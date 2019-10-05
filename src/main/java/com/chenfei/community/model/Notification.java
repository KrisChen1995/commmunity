package com.chenfei.community.model;

import lombok.Data;

@Data
public class Notification {
	private Long id ;
	private Long gmtCreate ;
	private Long notifier ;
	private Long receiver ;
	private Long outerId ;
	private int type ;
	private int status ;
}
