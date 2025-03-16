package com.project.dto.user;

import com.project.group.ValidationGroup;
import lombok.Data;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@GroupSequence({
        UserLoginRequest.class, // 默认组
        ValidationGroup.Group1.class,
        ValidationGroup.Group2.class,
        ValidationGroup.Group3.class,
        ValidationGroup.Group4.class
})
public class UserLoginRequest implements Serializable {
    /**
     * 账号
     */
    @NotBlank(message = "手机号不能为空", groups = ValidationGroup.Group1.class)
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号不符合规范", groups = ValidationGroup.Group2.class)
    private String userPhone;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空", groups = ValidationGroup.Group3.class)
    @Size(min = 6, max = 18, message = "密码长度应在6~18位", groups = ValidationGroup.Group4.class)
    private String userPassword;
}
