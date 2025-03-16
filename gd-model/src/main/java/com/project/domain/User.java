package com.project.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户表
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户手机号码
     */
    private String userPhone;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 用户头像地址
     */
    private String userAvatar;

    /**
     * 0-女 1-男 2-暂无
     */
    private Integer userGender;

    /**
     * 用户生日
     */
    private String userBirthday;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户所在地
     */
    private String userLocation;

    /**
     * 用户家乡
     */
    private String userHometown;

    /**
     * 用户专业
     */
    private String userProfession;

    /**
     * 用户标签
     */
    private String userTags;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 用户权限(user,admin)
     */
    private String userRole;

    /**
     * 0-存在 1-删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}