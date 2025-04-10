package com.project.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.vo.user.UserVO;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper {
    /**
     * 查询普通用户
     */
    Page<UserVO> queryUser(Page<UserVO> page);

    /**
     * 模糊查询普通用户
     */
    Page<UserVO> queryLikeUser(Page<UserVO> page, @Param("keyword") String keyword);
}
