package com.specflow.handler;

import com.specflow.common.BizException;
import com.specflow.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public Result<?> handleBiz(BizException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleValidation(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        return Result.fail(40001, msg);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public Result<?> handleNotFound(NoResourceFoundException e) {
        return Result.fail(40401, "资源不存在");
    }

    @ExceptionHandler(org.springframework.jdbc.CannotGetJdbcConnectionException.class)
    public Result<?> handleDbConnection(org.springframework.jdbc.CannotGetJdbcConnectionException e) {
        log.error("数据库连接失败，请检查数据库服务是否启动", e);
        return Result.fail(50001, "数据库连接失败，请联系管理员");
    }

    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        log.error("未处理异常", e);
        return Result.fail(50001, "服务器内部错误");
    }
}
