package com.project.dto.ws;

import lombok.Data;

import java.io.Serializable;

@Data
public class WebSocketDTO implements Serializable {
    /**
     * 需要处理的类型
     */
    private String type;

    /**
     * 数据
     */
    private String data;
}
