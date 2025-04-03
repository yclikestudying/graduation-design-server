package com.project.vo.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class QueryArticleVO implements Serializable {
    /**
     * 动态id
     */
    private Long id;

    /**
     * 发表用户id
     */
    private Long userId;

    /**
     * 发表用户名称
     */
    private String userName;

    /**
     * 发表用户头像
     */
    private String userAvatar;

    /**
     * 动态内容
     */
    private String articleContent;

    /**
     * 动态图片
     */
    private String articlePhotos;

    /**
     * 点赞数量
     */
    private Integer likeCount;

    /**
     * 自己对当前动态是否进行点赞
     */
    private boolean isLike;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
