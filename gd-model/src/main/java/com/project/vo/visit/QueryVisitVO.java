package com.project.vo.visit;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 查寝访客记录
 */
@Data
public class QueryVisitVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 头像
     */
    private String userAvatar;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 简介
     */
    private String userProfile;

    /**
     * 访问时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date visitTime;
}
