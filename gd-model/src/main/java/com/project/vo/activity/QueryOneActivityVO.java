package com.project.vo.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class QueryOneActivityVO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 发布用户id
     */
    private Long userId;

    /**
     * 发布用户头像
     */
    private String userAvatar;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 最大人数
     */
    private Integer activityMaxPeople;

    /**
     * 活动当前人数
     */
    private Integer currentPeople;

    /**
     * 活动图片
     */
    private String activityPhoto;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
