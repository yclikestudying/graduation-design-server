package com.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.domain.Message;
import com.project.vo.message.QueryGroupMessageVO;
import com.project.vo.message.QueryMessageVO;
import com.project.vo.message.QueryNoReadMessageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageMapper extends BaseMapper<Message> {
    /**
     * 查询聊天记录
     */
    List<QueryMessageVO> queryMessage(@Param("userId") Long userId, @Param("myId") Long myId);

    /**
     * 查询最新的一条消息
     */
    QueryNoReadMessageVO queryNoReadMessage(@Param("id") Long id, @Param("userId") Long userId);
}
