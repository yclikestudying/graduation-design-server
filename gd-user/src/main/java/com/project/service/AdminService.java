package com.project.service;

import com.project.vo.user.UserVO;

import java.util.Map;

public interface AdminService {
    /**
     * 管理员登录
     */
    Map<String, Object> login(String userPhone, String userPassword);
}
