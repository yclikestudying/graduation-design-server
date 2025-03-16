package com.project.dto.friend;

import com.project.group.ValidationGroup;
import lombok.Data;
import javax.validation.GroupSequence;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@GroupSequence({
        AddRequest.class, // 默认组
        ValidationGroup.Group1.class,
        ValidationGroup.Group2.class,
        ValidationGroup.Group3.class,
        ValidationGroup.Group4.class
})
public class AddRequest implements Serializable {
    /**
     * 关注者id
     */
    @NotNull(message = "id不能为空", groups = ValidationGroup.Group1.class)
    @Min(value = 1, message = "参数错误", groups = ValidationGroup.Group2.class)
    private Long followerId;

    /**
     * 被关注者id
     */
    @NotNull(message = "id不能为空", groups = ValidationGroup.Group3.class)
    @Min(value = 1, message = "参数错误", groups = ValidationGroup.Group4.class)
    private Long followeeId;
}
