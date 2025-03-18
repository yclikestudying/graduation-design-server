package com.project.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 活动表
 * @TableName activity
 */
@TableName(value ="activity")
@Data
public class Activity implements Serializable {
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
     * 活动名称
     */
    private String activityName;

    /**
     * 活动描述
     */
    private String activityDescription;

    /**
     * 最大人数
     */
    private Integer activityMaxPeople;

    /**
     * 活动图片
     */
    private String activityPhoto;

    /**
     * 发布时间
     */
    private Date createTime;

    /**
     * 1-显示，0-隐藏
     */
    private Integer isShow;

    /**
     * 0-存在，1-已删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}