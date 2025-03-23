package com.project.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.Message;
import com.project.dto.message.MessageDTO;
import com.project.vo.message.QueryMessageVO;
import com.project.vo.message.QueryNoReadMessageVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface MessageService extends IService<Message> {

    /**
     * 查询聊天信息
     */
    List<QueryMessageVO> queryMessage(Long userId);

    /**
     * 存储私聊消息
     */
    boolean insertDirectMessage(MessageDTO messageDTO);

    /**
     * 消息已读
     */
    Boolean isRead(Long userId);

    /**
     * 查询最新消息列表
     */
    List<QueryNoReadMessageVO> queryNoReadList();

    /**
     * 查询未读消息总数
     */
    Integer queryNoReadTotal();

    /**
     * 上传图片
     */
    String uploadImage(MultipartFile file);
}
