package com.project.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName article
 */
@TableName(value ="article")
@Data
public class Article implements Serializable {
    /**
     * 动态id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 发表用户id
     */
    private Long userId;

    /**
     * 动态内容
     */
    private String articleContent;

    /**
     * 动态图片
     */
    private String articlePhotos;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 1-同意，0-拒绝
     */
    private Integer isShow;

    /**
     * 0-存在 1-删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}