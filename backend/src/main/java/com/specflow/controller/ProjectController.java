package com.specflow.controller;

import com.specflow.common.PageResponse;
import com.specflow.common.Result;
import com.specflow.dto.ProjectCreateRequest;
import com.specflow.dto.ProjectDetailResponse;
import com.specflow.dto.ProjectUpdateRequest;
import com.specflow.entity.Project;
import com.specflow.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "项目管理", description = "项目增删改查及详情接口")
@SecurityRequirement(name = "Authorization")
public class ProjectController {

    private final ProjectService projectService;

    @Operation(summary = "项目列表", description = "获取当前用户的所有项目，支持分页")
    @GetMapping
    public Result<PageResponse<Project>> list(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") int pageSize) {
        List<Project> rows = projectService.listMyProjects(page, pageSize);
        long total = projectService.countMyProjects();
        return Result.ok(PageResponse.of(rows, total, page, pageSize));
    }

    @Operation(summary = "创建项目", description = "创建一个新项目，所有者为当前用户")
    @PostMapping
    public Result<Project> create(@Valid @RequestBody ProjectCreateRequest req) {
        return Result.ok(projectService.create(req));
    }

    @Operation(summary = "项目详情", description = "获取指定项目的详细信息，包含阶段数、任务数、已完成任务数")
    @GetMapping("/{id}")
    public Result<ProjectDetailResponse> detail(@PathVariable Long id) {
        return Result.ok(projectService.getDetail(id));
    }

    @Operation(summary = "更新项目", description = "更新项目名称、描述、技术栈、状态等信息")
    @PutMapping("/{id}")
    public Result<Project> update(@PathVariable Long id,
                                  @Valid @RequestBody ProjectUpdateRequest req) {
        return Result.ok(projectService.update(id, req));
    }

    @Operation(summary = "删除项目", description = "逻辑删除指定项目")
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        projectService.delete(id);
        return Result.ok();
    }
}
