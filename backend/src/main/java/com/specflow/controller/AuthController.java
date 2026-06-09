package com.specflow.controller;

import com.specflow.common.Result;
import com.specflow.dto.LoginRequest;
import com.specflow.dto.LoginResponse;
import com.specflow.dto.RegisterRequest;
import com.specflow.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "认证管理", description = "用户注册、登录接口")
public class AuthController {

    private final UserService userService;

    @Operation(summary = "用户注册", description = "输入用户名、邮箱、密码进行注册")
    @PostMapping("/register")
    public Result<?> register(@Valid @RequestBody RegisterRequest req) {
        userService.register(req);
        return Result.ok();
    }

    @Operation(summary = "用户登录", description = "输入用户名、密码登录，返回 JWT Token")
    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest req) {
        return Result.ok(userService.login(req));
    }
}
