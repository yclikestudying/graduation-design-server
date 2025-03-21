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

    /**
     * 查询关注数量
     */
    Integer friendCount(Long userId);

    /**
     * 查询粉丝数量
     */
    Integer fansCount(Long userId);

    /**
     * 查询互关数量
     */
    Integer eachCount(Long userId);
}
