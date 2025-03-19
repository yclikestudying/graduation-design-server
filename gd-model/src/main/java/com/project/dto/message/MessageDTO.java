package com.project.dto.message;

import lombok.Data;

@Data
public class MessageDTO {
    /**
     * 发送者id
     */
    private Long sendUserId;

    /**
     * 发送者名称
     */
    private String userName;

    /**
     * 接收者id
     */
    private Long acceptUserId;

    /**
     * 消息内容
     */
    private String messageContent;

    /**
     * 消息类型
     */
    private String messageType;
}
