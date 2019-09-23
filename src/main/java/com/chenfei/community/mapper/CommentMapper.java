package com.chenfei.community.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.chenfei.community.model.Comment;

public interface CommentMapper {
	
	@Insert("insert into comment (parent_id,gmt_create,gmt_modified,commentator,type,like_count,content,comment_count) values (#{parentId},#{gmtCreate},#{gmtModified},#{commentator},#{type},#{likeCount},#{content},#{commentCount})")
	void insert(Comment comment);
	
	@Update("update comment set comment_count = #{commentCount} where id = #{id}")
	Integer count(Comment comment);
	
	@Select("select count(1) from comment where parent_id= #{parentId}")
	Integer countByparentId(@Param("parentId") Long parentId);
	
	@Select("select * from comment where id= #{id}")
	Comment getCommentById(@Param("id") Long id);
	
	@Select("select * from comment where parent_id= #{parentId} and type=#{type} order by gmt_create desc")
	List<Comment> getComment(@Param("parentId") Long parentId, @Param("type") Integer type);
	
	@Select("select * from comment where parent_id= #{prentId}")

	@Update("update comment set parent_id=#{parentId},gmt_create=#{gmtCreate},gmt_modified=#{gmtModified},commentator=#{commentator},type=#{type},like_count=#{likeCount},content=#{content} where id=#{id}")
	int update(Comment comment);
	
	@Delete("delete from comment where id = #{id}")
	int delete(@Param("id") Long id);
}
