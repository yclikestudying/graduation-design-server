package com.project.mapper;

import com.project.domain.User;
import com.project.vo.user.UserVO;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper{
    UserVO queryUser(@Param("userPhone") String userPhone);
}
