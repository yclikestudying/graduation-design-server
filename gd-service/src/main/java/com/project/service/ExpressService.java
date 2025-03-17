package com.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.Express;

public interface ExpressService extends IService<Express> {
    /**
     * 发布跑腿任务
     */
    boolean upload(String text);
}
