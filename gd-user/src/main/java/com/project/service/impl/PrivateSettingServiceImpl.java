package com.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.PrivateSetting;
import com.project.exception.BusinessExceptionHandler;
import com.project.mapper.PrivateSettingMapper;
import com.project.service.PrivateSettingService;
import com.project.utils.UserContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class PrivateSettingServiceImpl extends ServiceImpl<PrivateSettingMapper, PrivateSetting>
        implements PrivateSettingService {
    @Resource
    private PrivateSettingMapper privateSettingMapper;

    /**
     * 设置用户默认系统隐私设置
     */
    @Override
    public boolean privateSetting(Long userId) {
        PrivateSetting privateSetting = new PrivateSetting();
        privateSetting.setUserId(userId);
        return privateSettingMapper.insert(privateSetting) > 0;
    }

    /**
     * 查询当前用户系统隐私设置
     */
    @Override
    public PrivateSetting querySetting(Long userId) {
        // 校验用户id
        if (userId == null) {
            userId = UserContext.getUserId();
        }

        // 查询当前用户的隐私设置
        return privateSettingMapper.selectOne(new QueryWrapper<PrivateSetting>()
                .eq("user_id", userId));
    }

    /**
     * 设置隐私
     */
    @Override
    public boolean setting(Object key, Object value) {
        // 校验参数
        if ("".equals(key)) {
            log.error("设置隐私 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }
        if ((Integer) value != 0 && (Integer) value != 1) {
            log.error("设置隐私 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }

        // 获取当前用户的id
        Long userId = UserContext.getUserId();

        if (Objects.equals(key, "article_setting")) {
            this.update(new UpdateWrapper<PrivateSetting>().set("article_setting", value).eq("user_id", userId));
        } else if (Objects.equals(key, "goods_setting")) {
            this.update(new UpdateWrapper<PrivateSetting>().set("goods_setting", value).eq("user_id", userId));
        } else if (Objects.equals(key, "express_setting")) {
            this.update(new UpdateWrapper<PrivateSetting>().set("express_setting", value).eq("user_id", userId));
        } else if (Objects.equals(key, "lost_setting")) {
            this.update(new UpdateWrapper<PrivateSetting>().set("lost_setting", value).eq("user_id", userId));
        } else {
            this.update(new UpdateWrapper<PrivateSetting>().set("activity_setting", value).eq("user_id", userId));
        }
        return true;
    }
}




