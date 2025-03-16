package com.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.Friend;

public interface FriendService extends IService<Friend> {
    /**
     * 添加关注
     */
    boolean add(Long followerId, Long followedId);
}
