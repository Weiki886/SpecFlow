package com.specflow.controller;

import com.specflow.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "健康检查", description = "系统健康状态接口")
public class HealthController {

    @Operation(summary = "健康检查", description = "返回系统健康状态")
    @GetMapping("/api/health")
    public Result<String> health() {
        return Result.ok("OK");
    }
}
