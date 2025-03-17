package com.project.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName goods
 */
@TableName(value ="goods")
@Data
public class Goods implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 发表用户id
     */
    private Long userId;

    /**
     * 商品名称
     */
    private String goodsTitle;

    /**
     * 商品内容
     */
    private String goodsContent;

    /**
     * 商品图片
     */
    private String goodsPhotos;

    /**
     * 商品原价格
     */
    private Integer goodsOldPrice;

    /**
     * 商品新价格
     */
    private Integer goodsPrice;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 1-同意，0-拒绝
     */
    private Integer isShow;

    /**
     * 0-存在 1-已删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}