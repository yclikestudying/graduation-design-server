package com.project.vo.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserVO implements Serializable {
    /**
     * 用户id
     */
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 用户权限(user,admin)
     */
    private String userRole;
}
