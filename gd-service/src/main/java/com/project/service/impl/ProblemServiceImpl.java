package com.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.Problem;
import com.project.exception.BusinessExceptionHandler;
import com.project.service.ProblemService;
import com.project.utils.Upload;
import com.project.utils.UserContext;
import com.project.vo.problem.QueryProblemVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.project.mapper.ProblemMapper;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem>
    implements ProblemService {
    @Resource
    private ProblemMapper problemMapper;

    /**
     * 提交问题和意见
     */
    @Override
    public boolean uplaod(MultipartFile file, String content, String phone) {
        // 校验参数
        if (StringUtils.isBlank(content) || file == null) {
            log.error("提交问题和意见 -----> 文本和图片不能为空");
            throw new BusinessExceptionHandler(400, "文本和图片不能为空");
        }

        // 上传图片到阿里云，获取访问地址
        String avatar;
        try {
            avatar = Upload.uploadAvatar(file, "problem");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 存储到数据库
        Problem problem = new Problem();
        problem.setUserId(UserContext.getUserId());
        problem.setProblemContent(content);
        problem.setProblemPhoto(avatar);
        problem.setContactPhone(phone);
        return problemMapper.insert(problem) > 0;
    }

    /**
     * 查询问题和意见
     */
    @Override
    public List<QueryProblemVO> queryProblem() {
        // 获取当前用户的id
        Long userId = UserContext.getUserId();

        // 查询数据库
        List<Problem> problems = problemMapper.selectList(new QueryWrapper<Problem>()
                .select("id", "user_id", "problem_content", "problem_photo", "create_time", "status")
                .eq("user_id", userId)
                .orderByDesc("create_time"));

        // 转换类型
        return problems.stream().map(problem -> {
            QueryProblemVO queryProblemVO = new QueryProblemVO();
            BeanUtils.copyProperties(problem, queryProblemVO);
            return queryProblemVO;
        }).collect(Collectors.toList());
    }

    /**
     * 清空全部反馈记录
     */
    @Override
    public boolean deleteAll() {
        // 获取当前用户的id
        Long userId = UserContext.getUserId();

        // 删除全部反馈记录
        return problemMapper.delete(new QueryWrapper<Problem>().eq("user_id", userId)) > 0;
    }
}




