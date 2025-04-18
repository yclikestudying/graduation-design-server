package com.project.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.vo.user.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper {
    /**
     * 查询普通用户
     */
    Page<UserVO> queryUser(Page<UserVO> page);

    /**
     * 模糊查询普通用户
     */
    Page<UserVO> queryLikeUser(Page<UserVO> page, @Param("keyword") String keyword);

    /**
     * 删除单个用户
     */
    boolean deleteUser(@Param("userId") Long userId);

    /**
     * 批量删除用户
     */
    boolean deleteUserBatch(@Param("userIdList") List<Long> userIdList);

    /**
     * 查询用户权限
     */
    String getUserRole(@Param("userId") Long userId);

    /**
     * 设置成管理员
     */
    boolean settingAdmin(Long userId);

    /**
     * 分页查询管理员
     */
    Page<UserVO> queryAdmin(Page<UserVO> page);

    /**
     * 模糊查询管理员
     */
    Page<UserVO> queryLikeAdmin(Page<UserVO> page, @Param("keyword") String keyword);

    /**
     * 设置成普通用户
     */
    boolean settingUser(@Param("userId") Long userId);
}
