package com.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.Friend;
import com.project.exception.BusinessExceptionHandler;
import com.project.mapper.FriendMapper;
import com.project.service.FriendService;
import com.project.utils.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
@Slf4j
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend>
        implements FriendService {
    @Resource
    private FriendMapper friendMapper;

    @Override
    public boolean add(Long followerId, Long followeeId) {
        // 不能关注自己
        if (Objects.equals(followerId, followeeId)) {
            log.error("添加关注----->不能关注自己");
            throw new BusinessExceptionHandler(400, "参数错误");
        }
        // 查询数据库中是否已有
        Friend one = friendMapper.selectOne(new QueryWrapper<Friend>().eq("follower_id", followerId)
                .eq("followee_id", followeeId));
        if (one != null) {
            log.error("添加关注----->不能重复关注");
            throw new BusinessExceptionHandler(400, "用户已关注");
        }
        // 保存到数据库
        Friend friend = new Friend();
        friend.setFollowerId(followerId);
        friend.setFolloweeId(followeeId);
        try {
            return friendMapper.insert(friend) > 0;
        } catch (Exception e) {
            log.error("添加关注----->数据库报错");
            throw new RuntimeException(e);
        }
    }

    /**
     * 取消关注
     */
    @Override
    public boolean cancel(Long followerId, Long followeeId) {
        // 查询数据库中是否已有
        Friend one = friendMapper.selectOne(new QueryWrapper<Friend>().eq("follower_id", followerId)
                .eq("followee_id", followeeId));
        if (one == null) {
            log.error("取消关注----->用户还未关注");
            throw new BusinessExceptionHandler(400, "用户还未关注");
        }
        try {
            return friendMapper.delete(new QueryWrapper<Friend>().eq("follower_id", followerId)
                    .eq("followee_id", followeeId)) > 0;
        } catch (Exception e) {
            log.error("取消关注----->数据库报错");
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询用户是否被关注
     *
     * @param currentUserId 用户id
     */
    @Override
    public boolean queryFriend(Long currentUserId) {
        // 获取我自己的id
        Long userId = UserContext.getUserId();
        // 校验传递的id
        if (currentUserId <= 0) {
            log.error("查询用户是否被关注 -----> id错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }
        // 查询数据库记录
        return friendMapper.selectOne(new QueryWrapper<Friend>()
                .eq("follower_id", userId)
                .eq("followee_id", currentUserId)) != null;
    }

    /**
     * 查询我的关注数量
     */
    @Override
    public Integer friendCount(Long userId) {
        try {
            return friendMapper.selectCount(new QueryWrapper<Friend>().eq("follower_id", userId));
        } catch (Exception e) {
            log.error("查询我的关注数量 -----> 数据库查询失败");
            throw new RuntimeException(e);
        }
    }
}
