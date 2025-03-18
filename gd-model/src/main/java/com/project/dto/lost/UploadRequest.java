package com.project.dto.lost;

import lombok.Data;


@Data
public class UploadRequest {
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
}
