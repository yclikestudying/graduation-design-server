package com.project.vo.express;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class QueryExpressVO implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 发表用户id
     */
    private Long userId;

    /**
     * 发表用户头像
     */
    private String userAvatar;

    /**
     * 发表用户名称
     */
    private String userName;

    /**
     * 代取内容文本
     */
    private String expressContent;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
