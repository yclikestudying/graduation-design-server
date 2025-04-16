package com.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.PrivateSetting;

public interface PrivateSettingService extends IService<PrivateSetting> {
    /**
     * 设置用户默认系统隐私设置
     */
    boolean privateSetting(Long userId);

    /**
     * 查询当前用户系统隐私设置
     */
    PrivateSetting querySetting(Long userId);

    /**
     * 设置隐私
     */
    boolean setting(Object key, Object value);
}
