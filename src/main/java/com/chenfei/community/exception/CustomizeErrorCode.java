package com.chenfei.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
	
	QUESTION_NOT_FOUND(2001, "你找的问题不存在,换个试试"),
	TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论任何回复"),
	NO_LOGIN(2003, "未登录不能进行评论,请先登录"),
	SYSTEM_ERROR(2004, "服务器冒烟了,要不然你稍后再试试??"),
	TYPE_PARAM_WRONG(2005, "评论类型错误或者不存在"),
	COMMENT_NOT_FOUND(2006,"你回复的评论不存在"),
	CONTENT_IS_EMPTY(2007,"输入内容不能为空"),
	FILE_UPLOAD_FAIL(2008,"图片上传失败")
	;
	
	
	private String message ;
	private Integer code ;
	
	CustomizeErrorCode(Integer code, String message){
		this.code = code ;
		this.message = message ;
	}
	
	@Override
	public String getMessage() {
		return message ;
	}
	
	@Override
	public Integer getCode() {
		return code;
	}
	

	
}
