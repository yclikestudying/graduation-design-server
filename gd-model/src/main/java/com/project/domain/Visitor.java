package com.project.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 访客记录表
 * @TableName visitor
 */
@TableName(value ="visitor")
@Data
public class Visitor implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 访问者id
     */
    private Long visitorId;

    /**
     * 被访问者id
     */
    private Long visitedId;

    /**
     * 访问时间
     */
    private Date visitTime;

    /**
     * 0-存在，1-删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}