package com.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.domain.User;
import com.project.vo.user.QueryUserVO;
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
}
