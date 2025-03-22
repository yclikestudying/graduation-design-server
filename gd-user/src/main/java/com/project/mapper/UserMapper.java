package com.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.domain.User;
import com.project.vo.user.QueryUserVO;
import com.project.vo.visit.QueryVisitVO;
import org.apache.ibatis.annotations.Param;

import javax.management.Query;
import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    /**
     * 用户名模糊查询用户
     *
     * @param keyword 关键字
     * @param userId 用户id
     */
    List<QueryUserVO> queryUser(@Param("keyword") String keyword, @Param("userId") Long userId);

    /**
     * 头像地址保存至avatar表
     */
    boolean saveAvatar(@Param("userId") Long userId, @Param("avatar") String avatar);

    /**
     * 查询关注用户id
     */
    List<Long> queryFriendIds(@Param("userId") Long userId);

    /**
     * 查询关注用户
     */
    List<QueryUserVO> queryFriend(@Param("idList") List<Long> idList);

    /**
     * 查询粉丝用户id
     */
    List<Long> queryFansIds(@Param("userId") Long userId);

    /**
     * 查询粉丝用户
     */
    List<QueryUserVO> queryFans(@Param("idList") List<Long> idList);

    /**
     * 查询互关用户id
     */
    List<Long> queryEach(@Param("userId") Long userId);

    /**
     * 删除之前的访客记录
     */
    boolean deleteVisit(@Param("userId") Long userId, @Param("myId") Long myId);

    /**
     * 添加访客记录
     */
    boolean addVisit(@Param("userId") Long userId, @Param("myId") Long myId);

    /**
     * 查询我的访客记录
     */
    List<QueryVisitVO> queryVisit(@Param("userid") Long userId);
}
