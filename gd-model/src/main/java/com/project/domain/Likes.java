package com.project.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 点赞表
 * @TableName likes
 */
@TableName(value ="likes")
@Data
public class Likes implements Serializable {
    /**
     * 点赞表主键
     */
    @TableId(type = IdType.AUTO)
    private Long likeId;

    /**
     * 点赞动态id
     */
    private Long likeArticleId;

    /**
     * 点赞用户id
     */
    private Long likeUserId;

    /**
     * 点赞时间
     */
    private Date likeTime;

    /**
     * 0-未删除 1-已删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}