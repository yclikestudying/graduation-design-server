package com.project.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 隐私表
 * @TableName private_setting
 */
@TableName(value ="private_setting")
@Data
public class PrivateSetting implements Serializable {
    /**
     * 隐私表主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 主页动态隐私设置
     */
    private Integer articleSetting;

    /**
     * 主页物品隐私设置
     */
    private Integer goodsSetting;

    /**
     * 主页跑腿隐私设置
     */
    private Integer expressSetting;

    /**
     * 主页寻物启事隐私设置
     */
    private Integer lostSetting;

    /**
     * 主页群聊隐私设置
     */
    private Integer activitySetting;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}