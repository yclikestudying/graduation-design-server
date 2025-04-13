package com.project.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.Goods;
import com.project.domain.Lost;
import com.project.dto.lost.UploadRequest;
import com.project.exception.BusinessExceptionHandler;
import com.project.mapper.LostMapper;
import com.project.service.LostService;
import com.project.utils.Upload;
import com.project.utils.UserContext;
import com.project.vo.goods.QueryGoodsVO;
import com.project.vo.lost.QueryLostVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 查询用户寻物启事
     */
    @Override
    public List<QueryLostVO> queryLost(Long userId) {
        // 校验id
        userId = checkUserId(userId);
        // 查询数据库记录
        try {
            return lostMapper.queryLost(userId, null);
        } catch (Exception e) {
            log.error("查询用户寻物启事 -----> 数据库查询失败");
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询所有寻物启事
     */
    @Override
    public List<QueryLostVO> queryAllLost() {
        // 查询数据库记录
        try {
            return lostMapper.queryLost(null, null);
        } catch (Exception e) {
            log.error("查询所有寻物启事 -----> 数据库查询失败");
            throw new RuntimeException(e);
        }
    }

    /**
     * 关键字模糊查询寻物启事
     */
    @Override
    public List<QueryLostVO> queryLostByKeyword(String keyword) {
        // 校验参数
        if (StringUtils.isBlank(keyword)) {
            log.error("关键字模糊查询寻物启事 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }
        // 查询数据库记录
        try {
            return lostMapper.queryLost(null, keyword);
        } catch (Exception e) {
            log.error("关键字模糊查询寻物启事 -----> 数据库查询失败");
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据id删除寻物启事
     */
    @Override
    public boolean deleteLost(Long lostId) {
        // 校验参数
        if (lostId <= 0) {
            log.error("根据id删除寻物启事----->参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }
        // 删除数据库动态记录
        int result;
        try {
            result = lostMapper.deleteById(lostId);
        } catch (Exception e) {
            log.error("根据id删除寻物启事----->数据库删除失败");
            throw new RuntimeException(e);
        }
        if (result == 0) {
            log.error("根据id删除寻物启事 -----> 不存在");
            throw new BusinessExceptionHandler(400, "不存在");
        }
        return result > 0;
    }

    /**
     * 分页查询寻物启事
     */
    @Override
    public Map<String, Object> queryLostByPage(Integer current, Integer size) {
        // 校验参数
        if (current <= 0 || size <= 0) {
            log.error("分页查询寻物启事 -----> 分页参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }

        // 进行查询
        Page<QueryLostVO> page = new Page<>(current, size);
        Page<QueryLostVO> queryGoodsVOPage = lostMapper.queryLostByPage(page);
        Map<String, Object> map = new HashMap<>();
        map.put("lost", queryGoodsVOPage.getRecords());
        map.put("total", queryGoodsVOPage.getTotal());
        return map;
    }

    /**
     * 批量删除寻物启事
     */
    @Override
    public boolean deleteLostBatch(List<Long> lostIdList) {
        // 参数校验
        if (lostIdList.isEmpty()) {
            log.error("批量删除寻物启事 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }

        // 执行操作
        return lostMapper.deleteBatchIds(lostIdList) > 0;
    }

    /**
     * 按时间搜索寻物启事
     */
    @Override
    public Map<String, Object> queryLostByTime(String time, Integer current, Integer size) {
        // 校验参数
        if (StringUtils.isBlank(time)) {
            log.error("按时间搜索寻物启事 -----> 参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }

        // 数据库查询
        Page<QueryLostVO> page = new Page<>(current, size);
        Page<QueryLostVO> queryArticleVOPage = lostMapper.queryGoodsByTime(page, time);
        Map<String, Object> map = new HashMap<>();
        map.put("lost", queryArticleVOPage.getRecords());
        map.put("total", queryArticleVOPage.getTotal());
        return map;
    }

    /**
     * 校验是根据自己的id还是其他人的id进行操作
     */
    private Long checkUserId(Long userId) {
        return userId == null ? UserContext.getUserId() : userId;
    }
}
