package com.project.vo.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class QueryGroupMessageVO implements Serializable {
    /**
     * 群聊消息表id
     */
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
     * 发送用户名称
     */
    private String userName;

    /**
     * 发送用户头像
     */
    private String userAvatar;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;
}
