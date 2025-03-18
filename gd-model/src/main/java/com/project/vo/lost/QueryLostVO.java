package com.project.vo.lost;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class QueryLostVO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 发布用户id
     */
    private Long userId;

    /**
     * 丢失物品类型（失物招领、寻物启事）
     */
    private String lostType;

    /**
     * 丢失物品名称
     */
    private String lostName;

    /**
     * 丢失物品描述
     */
    private String lostDescription;

    /**
     * 丢失物品图片
     */
    private String lostPhoto;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
