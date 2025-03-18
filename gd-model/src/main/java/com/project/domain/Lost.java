package com.project.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName lost
 */
@TableName(value ="lost")
@Data
public class Lost implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 发布用户id
     */
    private Long userId;

    /**
     * 丢失物品类型（失物招领、寻物启事）
     */
    private String lostType;

    /**
     * 丢失物品名称
     */
    private String lostName;

    /**
     * 丢失物品描述
     */
    private String lostDescription;

    /**
     * 丢失物品图片
     */
    private String lostPhoto;

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