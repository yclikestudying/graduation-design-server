package com.project.service;

import com.project.vo.user.UserVO;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface AdminService {
    /**
     * 查询普通用户
     */
    Map<String, Object> queryUser(Integer current, Integer size);

    /**
     * 模糊查询普通用户
     */
    Map<String, Object> queryLikeUser(String keyword, Integer current, Integer size);

    /**
     * 删除单个用户
     */
    boolean deleteUser(Long userId);

    /**
     * 批量删除用户
     */
    boolean deleteUserBatch(List<Long> userIdList);
}
