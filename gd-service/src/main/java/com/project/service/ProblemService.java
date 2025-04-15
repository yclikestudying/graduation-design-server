package com.project.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.Problem;
import com.project.vo.problem.QueryProblemVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProblemService extends IService<Problem> {
    /**
     * 提交问题和意见
     */
    boolean uplaod(MultipartFile file, String content, String phone);

    /**
     * 查询问题和意见
     */
    List<QueryProblemVO> queryProblem();

    /**
     * 清空全部反馈记录
     */
    boolean deleteAll();
}
