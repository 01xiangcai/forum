package com.yao.forum.mapper;

import com.yao.forum.model.Question;
import org.apache.ibatis.annotations.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    public void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("SELECT COUNT(1) FROM question")
    Integer count();

    @Select("select * from question where creator=#{userId} limit #{offset},#{size}")
    List<Question> listByUserId( @Param(value = "userId")Integer userId, @Param(value = "offset")Integer offset,  @Param(value = "size")Integer size);

    @Select("SELECT COUNT(1) FROM question where creator = #{userId}")
    Integer countByUserId(@Param(value = "userId")Integer userId);

    @Select("select * from question where id=#{id} ")
    Question getQuestionById(@Param(value = "id")Integer id);

    @Update("update question set title=#{title},description=#{description},tag=#{tag},gmt_modified=#{gmtModified} where id =#{id}")
    void update(Question question);
}




