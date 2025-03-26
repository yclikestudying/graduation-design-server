package com.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.GroupMessage;
import com.project.dto.message.GroupMessageDTO;
import com.project.vo.message.QueryGroupMessageVO;

import java.util.List;

public interface GroupMessageService extends IService<GroupMessage> {

    /**
     * 查询群聊消息
     */
    List<QueryGroupMessageVO> queryGroupMessage(Long activityId);

    /**
     * 存储群消息
     */
    boolean insertGroupMessage(GroupMessageDTO groupMessageDTO);
}
