package com.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.domain.Message;
import com.project.vo.message.QueryMessageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageMapper extends BaseMapper<Message> {
    /**
     * 查询聊天记录
     */
    List<QueryMessageVO> queryMessage(@Param("userId") Long userId, @Param("myId") Long myId);
}
