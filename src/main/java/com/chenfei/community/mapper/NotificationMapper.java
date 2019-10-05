package com.chenfei.community.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.chenfei.community.model.Notification;
import com.chenfei.community.model.User;

public interface NotificationMapper {
	@Insert("insert into notification (notifier,receiver,gmt_create,outerId,type,status) values (#{notifier},#{receiver},#{gmtCreate},#{outerId},#{type},#{status})")
	void insert(Notification notification);
	
	@Select("select * from notification where id = #{id}")
	Notification getNotifications(@Param("id") Long id);
	
	@Select("select * from notification where id = #{id}")
	User findById(@Param("id") Long id);
	
	@Select("select * from notification where account_id = #{accountId}")
	User findByAccountId(@Param("accountId") String accountId);

	@Update("update notification set name=#{name},token=#{token},gmt_modified=#{gmtModified},avatar_url=#{avatarUrl} where id= #{id}")
	void update(User User);
	List<User> getAllUsers(@Param("ids") List<Long> ids);
	
	@Select("select count(1) from notification where receiver = #{userId}")
	Integer countByUserId(@Param("userId") Long userId);
	
	@Select("select * from notification where receiver = #{userId} limit #{offset}, #{size}")
	List<Notification> queryByUserId(@Param(value="userId") Long userId ,@Param(value="offset") Integer offset, @Param(value="size") Integer size);
	
}
