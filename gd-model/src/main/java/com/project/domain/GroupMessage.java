package com.project.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 群聊消息表
 * @TableName group_message
 */
@TableName(value ="group_message")
@Data
public class GroupMessage implements Serializable {
    /**
     * 群聊消息表id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 群id 关联 activity表id
     */
    private Long activityId;

    /**
     * 发送用户id 关联 user表id
     */
    private Long sendUserId;

    /**
     * 消息类型（text/image/file）
     */
    private Object messageType;

    /**
     * 消息内容
     */
    private String messageContent;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 0-存在，1-删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}