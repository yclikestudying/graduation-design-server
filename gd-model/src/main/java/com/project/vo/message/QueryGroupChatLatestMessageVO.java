package com.project.vo.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class QueryGroupChatLatestMessageVO implements Serializable {
    /**
     * 群id 关联 activity表id
     */
    private Long activityId;

    /**
     * 群名称
     */
    private String activityName;

    /**
     * 群头像
     */
    private String activityPhoto;

    /**
     * 发送用户id 关联 user表id
     */
    private Long sendUserId;

    /**
     * 发送用户名称
     */
    private String userName;

    /**
     * 发送消息类型（text/image/file）
     */
    private Object messageType;

    /**
     * 发送消息内容
     */
    private String messageContent;

    /**
     * 未读消息数量
     */
    private Integer noReadMessageCount;

    /**
     * 发送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;
}
