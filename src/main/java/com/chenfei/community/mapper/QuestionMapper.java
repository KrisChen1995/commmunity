package com.chenfei.community.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.chenfei.community.dto.QuestionQueryDTO;
import com.chenfei.community.model.Question;


public interface QuestionMapper {
	
	@Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
	void insert(Question question);
	
	@Select("select * from question order by gmt_create desc limit #{offset}, #{size} ")
	List<Question> queryList(@Param(value="offset") Integer offset, @Param(value="size") Integer size);
	
	@Select("select count(1) from question")
	Integer count();
	
	@Select("select * from question where creator= #{userId} limit #{offset}, #{size}")
	List<Question> queryByUserId(@Param(value="userId") Long userId ,@Param(value="offset") Integer offset, @Param(value="size") Integer size);
	
	@Select("select count(1) from question where creator= #{userId}")
	Integer countByUserId(@Param("userId") Long userId);
	
	@Select("select * from question where id= #{id}")
	Question getQuestionById(@Param("id") Long id);

	@Update("update question set title=#{title}, description=#{description}, gmt_modified=#{gmtModified}, tag=#{tag} where id=#{id}")
	int update(Question question);
	
	@Update ("update question set comment_count= #{commentCount} where id=#{id}")
	int updateCommentCount(Question question);

	@Update("update question set view_count= #{viewCount} where id=#{id}")
	int updateViewCount(Question question);
	
	@Select("select * from question where id!=#{id} and tag regexp #{tag}")
	List<Question> selectRelated (Question question);

	
}
