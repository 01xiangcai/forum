package com.yao.forum.mapper;


import com.yao.forum.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User finfByToken(@Param("token") String token);

    @Select("select id,name,account_id,token,gmt_create,gmt_modified,avatar_url from user where id= #{id}")
    User finfById(@Param("id")Integer id);

    @Select("select * from user where ACCOUNT_ID = #{id}")
    User finfByAccountId(@Param("id")String id);

    @Update("update user set name=#{name} ,token=#{token} ,avatar_url=#{avatarUrl} ,gmt_modified=#{gmtModified}  where id=#{id}")
    void update(User user);
}
