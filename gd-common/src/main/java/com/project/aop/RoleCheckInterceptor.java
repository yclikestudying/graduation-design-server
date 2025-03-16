package com.project.aop;

import com.project.common.CodeEnum;
import com.project.exception.BusinessExceptionHandler;
import com.project.utils.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import com.project.annotation.RoleCheck;

@Aspect
@Component
@Slf4j
public class RoleCheckInterceptor {
    @Around("@annotation(roleCheck)")
    public Object roleCheck(ProceedingJoinPoint joinPoint, RoleCheck roleCheck) throws Throwable {
        String role = roleCheck.role();
        if (!role.equals(UserContext.getUser().getUserRole())) {
            log.error("权限校验----->权限不足");
            throw new BusinessExceptionHandler(CodeEnum.PERMISSION_DENIED);
        }
        return joinPoint.proceed();
    }
}
