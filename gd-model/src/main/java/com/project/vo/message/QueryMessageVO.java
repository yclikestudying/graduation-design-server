package com.project.vo.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class QueryMessageVO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 发送者id
     */
    private Long sendUserId;

    /**
     * 发送者头像
     */
    private String userAvatar;

    /**
     * 发送者名称
     */
    private String userName;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
