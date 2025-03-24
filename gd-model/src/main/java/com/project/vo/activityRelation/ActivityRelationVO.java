package com.project.vo.activityRelation;

import lombok.Data;

@Data
public class ActivityRelationVO {
    /**
     * 群聊名称
     */
    private String activityName;

    /**
     * 群聊人数
     */
    private Integer personCount;
}
