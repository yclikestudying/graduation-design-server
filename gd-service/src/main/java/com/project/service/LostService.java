package com.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.Lost;
import com.project.dto.lost.UploadRequest;
import org.springframework.web.multipart.MultipartFile;

public interface LostService extends IService<Lost> {
    /**
     * 上传寻物启事（携带图片）
     */
    boolean uplaod(MultipartFile file, String type, String title, String description);

    /**
     * 上传寻物启事（不携带图片）
     */
    boolean uploadNoFile(UploadRequest uploadRequest);
}
