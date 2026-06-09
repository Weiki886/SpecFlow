package com.specflow.controller;

import com.specflow.common.Result;
import com.specflow.dto.PhaseCreateRequest;
import com.specflow.entity.Phase;
import com.specflow.service.PhaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/phases")
@RequiredArgsConstructor
@Tag(name = "阶段管理", description = "项目阶段（Phase）的增删改查接口")
@SecurityRequirement(name = "Authorization")
public class PhaseController {

    private final PhaseService phaseService;

    @Operation(summary = "阶段列表", description = "获取指定项目的所有阶段，按排序字段升序排列")
    @GetMapping
    public Result<List<Phase>> list(@PathVariable Long projectId) {
        return Result.ok(phaseService.listByProject(projectId));
    }

    @Operation(summary = "创建阶段", description = "在指定项目下创建新阶段，sortOrder 未指定时自动追加到末尾")
    @PostMapping
    public Result<Phase> create(@PathVariable Long projectId,
                                 @Valid @RequestBody PhaseCreateRequest req) {
        return Result.ok(phaseService.create(projectId, req));
    }

    @Operation(summary = "更新阶段", description = "更新阶段名称、描述、排序、状态等信息")
    @PutMapping("/{id}")
    public Result<Phase> update(@PathVariable Long projectId,
                                 @PathVariable Long id,
                                 @Valid @RequestBody PhaseCreateRequest req) {
        return Result.ok(phaseService.update(projectId, id, req));
    }

    @Operation(summary = "删除阶段", description = "删除指定阶段（物理删除）")
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long projectId, @PathVariable Long id) {
        phaseService.delete(projectId, id);
        return Result.ok();
    }
}
