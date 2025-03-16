package com.project.utils;


import com.project.domain.User;
import com.project.vo.user.UserVO;

/**
 * 存储用户信息
 */
public class UserContext {
    private static final ThreadLocal<UserVO> USER = new ThreadLocal<>();

    /**
     * 存储 user
     */
    public static void setUser(UserVO user) {
        USER.set(user);
    }

    /**
     * 获取 user
     */
    public static UserVO getUser() {
        return USER.get();
    }

    /**
     * 清除，避免内存泄漏
     */
    public static void clear() {
        USER.remove();
    }

    /**
     * 获取用户id
     */
    public static Long getUserId() {
        return getUser().getId();
    }
}
