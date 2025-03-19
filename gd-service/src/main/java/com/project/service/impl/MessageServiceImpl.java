package com.project.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.Message;
import com.project.mapper.MessageMapper;
import com.project.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
    implements MessageService {

}




