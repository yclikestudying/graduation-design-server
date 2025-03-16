package com.project.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import com.project.common.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    // 自定义异常捕获
    @ExceptionHandler(BusinessExceptionHandler.class)
    public BaseResponse<String> businessException(BusinessExceptionHandler e) {
        return BaseResponse.fail(e.getCode(), e.getMessage());
    }

    // 参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<String> ConstraintViolationException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .findFirst()
                .orElse("参数校验失败");
        return BaseResponse.fail(400, errorMessage);
    }

    // 系统异常
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<String> runtimeException(RuntimeException e) {
        log.error("系统异常:", e);
        return BaseResponse.fail(500, "系统异常，请稍后重试");
    }
}
