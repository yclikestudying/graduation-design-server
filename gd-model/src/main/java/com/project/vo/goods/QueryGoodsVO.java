package com.project.vo.goods;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class QueryGoodsVO implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 发表用户id
     */
    private Long userId;

    /**
     * 发表用户名称
     */
    private String userName;

    /**
     * 发表用户头像
     */
    private String userAvatar;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
