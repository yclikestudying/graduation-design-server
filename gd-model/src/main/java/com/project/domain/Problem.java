package com.project.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;

/**
 * 问题反馈表
 * @TableName problem
 */
@TableName(value ="problem")
@Data
public class Problem implements Serializable {
    /**
     * 问题反馈表id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 反馈用户id
     */
    private Long userId;

    /**
     * 问题内容
     */
    private String problemContent;

    /**
     * 问题图片
     */
    private String problemPhoto;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 0-未处理，1-已处理
     */
    private Integer status;

    /**
     * 0-存在，1-删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}