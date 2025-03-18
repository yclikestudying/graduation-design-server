package com.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.Lost;
import com.project.dto.lost.UploadRequest;
import com.project.vo.lost.QueryLostVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LostService extends IService<Lost> {
    /**
     * 上传寻物启事（携带图片）
     */
    boolean uplaod(MultipartFile file, String type, String title, String description);

    /**
     * 上传寻物启事（不携带图片）
     */
    boolean uploadNoFile(UploadRequest uploadRequest);

    /**
     * 查询用户寻物启事
     */
    List<QueryLostVO> queryLost(Long userId);

    /**
     * 查询所有寻物启事
     */
    List<QueryLostVO> queryAllLost();

    /**
     * 关键字模糊查询寻物启事
     */
    List<QueryLostVO> queryLostByKeyword(String keyword);

    /**
     * 根据id删除寻物启事
     */
    boolean deleteLost(Long lostId);
}
