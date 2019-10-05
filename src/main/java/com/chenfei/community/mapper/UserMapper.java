package com.chenfei.community.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.chenfei.community.dto.QuestionQueryDTO;
import com.chenfei.community.model.Question;
import com.chenfei.community.model.User;

public interface UserMapper {
	@Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,bio,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{bio},#{avatarUrl})")
	void insert(User user);
	
	@Select("select * from user where token = #{token}")
	User findByToken(@Param("token") String Token);
	
	@Select("select * from user where id = #{id}")
	User findById(@Param("id") Long id);
	
	@Select("select * from user where account_id = #{accountId}")
	User findByAccountId(@Param("accountId") String accountId);

	@Update("update user set name=#{name},token=#{token},gmt_modified=#{gmtModified},avatar_url=#{avatarUrl} where id= #{id}")
	void update(User User);
	
	List<User> getAllUsers(@Param("ids") List<Long> ids);

	Integer countBySearch(QuestionQueryDTO questionQueryDTO);

	List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);

	
	
}
