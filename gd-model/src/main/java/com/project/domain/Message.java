package com.project.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 聊天消息表
 * @TableName message
 */
@TableName(value ="message")
@Data
public class Message implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 发送者id
     */
    private Long sendUserId;

    /**
     * 接收者id
     */
    private Long acceptUserId;

    /**
     * 消息
     */
    private String messageContent;

    /**
     * 类型（文本，图片，文件）
     */
    private String messageType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 0-未读，1-已读
     */
    private Integer isRead;

    /**
     * 0-存在，1-删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}