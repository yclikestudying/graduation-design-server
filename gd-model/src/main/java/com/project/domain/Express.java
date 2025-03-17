package com.project.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName express
 */
@TableName(value ="express")
@Data
public class Express implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 发表用户id
     */
    private Long userId;

    /**
     * 代取内容文本
     */
    private String expressContent;

    /**
     * 发布时间
     */
    private Date createTime;

    /**
     * 1-同意，0-拒绝
     */
    private Integer isShow;

    /**
     * 0-存在 1-已删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}