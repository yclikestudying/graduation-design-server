package com.project.vo.activity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class QueryActivityVO implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 群聊名称
     */
    private String activityName;

    /**
     * 群聊描述
     */
    private String activityDescription;

    /**
     * 最大人数
     */
    private Integer activityMaxPeople;

    /**
     * 群聊当前人数
     */
    private Integer currentPeople;

    /**
     * 群聊图片
     */
    private String activityPhoto;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
