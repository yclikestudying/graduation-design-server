package com.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.domain.Friend;
import org.apache.ibatis.annotations.Param;

public interface FriendMapper extends BaseMapper<Friend> {
    /**
     * 查询互关数量
     */
    Integer eachCount(@Param("userId") Long userId);
}
