package com.project.dto.message;

import lombok.Data;

@Data
public class GroupMessageDTO {
    /**
     * 群id
     */
    private Long activityId;

    /**
     * 发送者id
     */
    private Long sendUserId;

    /**
     * 消息类型
     */
    private String messageType;

    /**
     * 消息内容
     */
    private String messageContent;
}
