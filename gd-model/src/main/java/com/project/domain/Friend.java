package com.project.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 关系表
 * @TableName friend
 */
@TableName(value ="friend")
@Data
public class Friend implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 关注者id
     */
    @TableField(value = "follower_id")
    private Long followerId;

    /**
     * 被关注者id
     */
    @TableField(value = "followee_id")
    private Long followeeId;

    /**
     * 建立关系时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 0-存在，1-删除
     */
    @TableField(value = "is_delete")
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}