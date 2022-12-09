package com.yao.forum.provider;


import com.alibaba.fastjson.JSON;
import com.yao.forum.dto.AccessTokenDTO;
import com.yao.forum.dto.GithubUSer;
import okhttp3.*;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class GitHubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println(string);
            String token = string.split("&")[0].split("=")[1];
            System.out.println("token = " + token);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUSer getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
//        方法二
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization", "token " + accessToken)
                .build();
       /* Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();*/
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUSer githubUSer = JSON.parseObject(string, GithubUSer.class);
            return githubUSer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
