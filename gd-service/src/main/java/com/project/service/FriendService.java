package com.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.Friend;

public interface FriendService extends IService<Friend> {
    /**
     * 添加关注
     */
    boolean add(Long followerId, Long followedId);

    /**
     * 取消关注
     */
    boolean cancel(Long followerId, Long followeeId);

    /**
     * 查询用户是否被关注
     * @param userId 用户id
     */
    boolean queryFriend(Long userId);
}
