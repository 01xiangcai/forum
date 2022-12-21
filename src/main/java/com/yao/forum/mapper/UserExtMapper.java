package com.yao.forum.mapper;

import com.yao.forum.model.User;

public interface UserExtMapper {
    User selectUserByName(String name);
}