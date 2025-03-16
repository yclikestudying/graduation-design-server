package com.project.vo.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryUserVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 头像
     */
    private String userAvatar;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 简介
     */
    private String userProfile;
}
