package com.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.Likes;

public interface LikesService extends IService<Likes> {
    /**
     * 点赞
     */
    boolean like(Long articleId);

    /**
     * 取消点赞
     */
    boolean cancelLike(Long articleId);

    /**
     * 根据动态id查询动态点赞数
     */
    Integer queryLikeCount(Long articleId);

    /**
     * 查询用户是否对动态进行点赞
     */
    boolean queryUserLike(Long userId, Long articleId);
}
