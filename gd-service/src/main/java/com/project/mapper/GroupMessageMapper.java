package com.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.domain.GroupMessage;
import com.project.vo.message.QueryGroupMessageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupMessageMapper extends BaseMapper<GroupMessage> {

    /**
     * 查询群聊消息
     */
    List<QueryGroupMessageVO> queryGroupMessage(@Param("activityId") Long activityId);
}
