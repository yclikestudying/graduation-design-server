package com.project.constant;

public class RedisConstant {
    // 项目名称前缀
    public static final String PROJECT_NAME = "campusMarket:";
    // token 前缀
    public static final String USER_TOKEN = "user:token:";
    // 用户个人信息前缀
    public static final String USER_INFO = "user:info:";
    // 用户动态前缀
    public static final String USER_ARTICLE = "user:article:";

    public static String getRedisKey(String key, Long userId) {
        return PROJECT_NAME + key + userId;
    }
}
