package com.project.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 活动关系表
 * @TableName activity_relation
 */
@TableName(value ="activity_relation")
@Data
public class ActivityRelation implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 加入时间
     */
    private Date joinTime;

    /**
     * 0-存在，1-已删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}