package com.yao.forum.dto;

import lombok.Data;

@Data
public class GithubUSer {
    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;

    @Override
    public String toString() {
        return "GithubUSer{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", bio='" + bio + '\'' +
                '}';
    }

}
