package com.project.vo.problem;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class QueryProblemVO {
    /**
     * 问题反馈表id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 反馈用户id
     */
    private Long userId;

    /**
     * 问题内容
     */
    private String problemContent;

    /**
     * 问题图片
     */
    private String problemPhoto;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 反馈时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 0-未处理，1-已处理
     */
    private Integer status;
}
