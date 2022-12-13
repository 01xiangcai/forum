package com.yao.forum.service;

import com.yao.forum.mapper.UserMapper;
import com.yao.forum.model.User;
import com.yao.forum.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    @Resource
    UserMapper userMapper;
    public void creatOrUpdate(User user) {
        //根据传进来的user中的accountId取数据库用户
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size()==0){
            //不存在，直接插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else {
            //存在用户，更新数据
            User dbUser = users.get(0);
            User updateUser = new User();
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setToken(user.getToken());
            updateUser.setName(user.getName());
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser,example);
        }
    }
}
