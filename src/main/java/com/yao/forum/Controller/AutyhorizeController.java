package com.yao.forum.Controller;

import com.yao.forum.dto.AccessTokenDTO;
import com.yao.forum.dto.GithubUSer;
import com.yao.forum.exception.CustomizeErrorCode;
import com.yao.forum.exception.CustomizeException;
import com.yao.forum.mapper.UserExtMapper;
import com.yao.forum.mapper.UserMapper;
import com.yao.forum.model.User;
import com.yao.forum.model.UserExample;
import com.yao.forum.provider.GitHubProvider;
import com.yao.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Controller
public class AutyhorizeController {
    @Autowired
    private GitHubProvider gitHubProvider;
    @Autowired
    UserService userService;
    @Resource
    UserMapper userMapper;
    @Resource
    UserExtMapper userExtMapper;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GithubUSer githubUSer = gitHubProvider.getUser(accessToken);
        System.out.println("user = " + githubUSer);

        if (githubUSer != null && githubUSer.getId() != null) {
            User user = new User();
            user.setName(githubUSer.getName());
            user.setAccountId(String.valueOf(githubUSer.getId()));
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAvatarUrl(githubUSer.getAvatarUrl());
            //判断用户是否在数据库中存在,存在就更新，不存在直接插入
            userService.creatOrUpdate(user);
//            userMapper.insert(user);
            //登录成功，token写到cookie
            response.addCookie(new Cookie("token", token));
            return "redirect:/";
        } else {
            //登录失败，重新登录
            return "redirect:/";
        }
    }


    @GetMapping("login")
    public String login() {
        return "login";
    }
    @GetMapping("register")
    public String register() {
        return "register";
    }


    //登录
    @GetMapping("login/signin")
    public String login(
                        @RequestParam(name = "name",required = false) String name,
                        @RequestParam(name = "password",required = false) String password,
                        HttpServletResponse response) {

            User user = userExtMapper.selectUserByName(name);
            if (user.getPassword().equals(password)){
                //登录成功，token写到cookie
                String token = user.getToken();
                Cookie token1 = new Cookie("token", token);
                token1.setPath("/");
                response.addCookie(token1);
                return "redirect:/";
            }else {
                throw new CustomizeException(CustomizeErrorCode.LOGIN_ERROR);
            }

    }


    //注册
    @PostMapping("login/register")
    public String login(
                        @RequestParam(name = "name",required = false) String name,
                        @RequestParam(name = "password",required = false) String password,
                        Model model,
                        HttpServletResponse response) {
        User user = new User();
        UserExample userExample = new UserExample();
        userExample.createCriteria().andNameEqualTo(name);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size()!=0){
            throw new CustomizeException(CustomizeErrorCode.NAME_REPEAT);
        }
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        user.setName(name);
        user.setPassword(password);
        user.setAvatarUrl("/images/avatar.jpg");
        user.setGmtCreate(System.currentTimeMillis());
        userMapper.insert(user);
        return "login";
    }





    //退出登录
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";

    }

}
