package com.yao.forum.service;

import com.yao.forum.mapper.UserMapper;
import com.yao.forum.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    public void creatOrUpdate(User user) {
        //根据传进来的user中的accountId取数据库用户
        User dbUser = userMapper.finfByAccountId(user.getAccountId());
        if (dbUser==null){
            //不存在，直接插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else {
            //存在用户，更新数据
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setToken(user.getToken());
            dbUser.setName(user.getName());
            userMapper.update(dbUser);
        }
    }
}
