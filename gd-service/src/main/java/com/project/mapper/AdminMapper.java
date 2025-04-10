package com.project.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.vo.user.UserVO;

public interface AdminMapper {
    /**
     * 查询普通用户
     */
    Page<UserVO> queryUser(Page<UserVO> page);
}
