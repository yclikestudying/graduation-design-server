package com.project.service;

import com.project.vo.article.QueryArticleVO;
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

    /**
     * 设置用户为管理员
     */
    boolean settingAdmin(Long userId);

    /**
     * 分页查询管理员
     */
    Map<String, Object> queryAdmin(Integer current, Integer size);

    /**
     * 模糊查询管理员
     */
    Map<String, Object> queryLikeAdmin(String keyword, Integer current, Integer size);

    /**
     * 设置管理员为普通用户
     */
    boolean settingUser(Long userId);

    /**
     * 分页查询动态
     */
    Map<String, Object> queryArticle(Integer current, Integer size);
}
