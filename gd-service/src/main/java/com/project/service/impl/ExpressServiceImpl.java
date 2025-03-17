package com.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.Express;
import com.project.mapper.ExpressMapper;
import com.project.service.ExpressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class ExpressServiceImpl extends ServiceImpl<ExpressMapper, Express>
        implements ExpressService {
    @Resource
    private ExpressMapper expressMapper;
}
