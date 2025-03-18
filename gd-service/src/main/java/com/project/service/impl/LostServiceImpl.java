package com.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.Goods;
import com.project.domain.Lost;
import com.project.dto.lost.UploadRequest;
import com.project.exception.BusinessExceptionHandler;
import com.project.mapper.LostMapper;
import com.project.service.LostService;
import com.project.utils.Upload;
import com.project.utils.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Service
@Slf4j
public class LostServiceImpl extends ServiceImpl<LostMapper, Lost>
        implements LostService {
    @Resource
    private LostMapper lostMapper;

    /**
     * 上传寻物启事（携带图片）
     */
    @Override
    public boolean uplaod(MultipartFile file, String type, String title, String description) {
        // 获取自己id
        Long userId = UserContext.getUserId();
        // 校验参数
        if (StringUtils.isAnyBlank(type, title, description)) {
            log.error("上传寻物启事 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }
        // 上传阿里云
        String link = null;
        try {
            link = Upload.uploadAvatar(file, "lost");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Lost lost = new Lost();
        lost.setUserId(userId);
        lost.setLostPhoto(link);
        lost.setLostType(type);
        lost.setLostName(title);
        lost.setLostDescription(description);
        try {
            return lostMapper.insert(lost) > 0;
        } catch (Exception e) {
            log.error("上传寻物启事 -----> 插入数据库错误");
            throw new RuntimeException(e);
        }
    }

    /**
     * 上传寻物启事（不携带图片）
     */
    @Override
    public boolean uploadNoFile(UploadRequest uploadRequest) {
        // 获取参数
        String lostType = uploadRequest.getLostType();
        String lostName = uploadRequest.getLostName();
        String lostDescription = uploadRequest.getLostDescription();
        // 校验参数
        if (StringUtils.isAnyBlank(lostType, lostName, lostDescription)) {
            log.error("上传寻物启事 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }
        // 获取自己id
        Long userId = UserContext.getUserId();
        // 保存数据库
        Lost lost = new Lost();
        lost.setUserId(userId);
        lost.setLostType(lostType);
        lost.setLostName(lostName);
        lost.setLostDescription(lostDescription);
        try {
            return lostMapper.insert(lost) > 0;
        } catch (Exception e) {
            log.error("上传寻物启事 -----> 插入数据库错误");
            throw new RuntimeException(e);
        }
    }
}
