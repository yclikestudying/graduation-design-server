package com.project.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.Message;
import com.project.vo.message.QueryMessageVO;

import java.util.List;

public interface MessageService extends IService<Message> {

    /**
     * 查询聊天信息
     */
    List<QueryMessageVO> queryMessage(Long userId);
}
