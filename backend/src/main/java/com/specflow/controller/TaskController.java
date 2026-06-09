package com.specflow.controller;

import com.specflow.common.Result;
import com.specflow.dto.PhaseTaskGroup;
import com.specflow.dto.TaskCreateRequest;
import com.specflow.dto.TaskStatusUpdateRequest;
import com.specflow.entity.Task;
import com.specflow.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}")
@RequiredArgsConstructor
@Tag(name = "任务管理", description = "项目任务的增删改查、状态更新及任务树接口")
@SecurityRequirement(name = "Authorization")
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "任务树", description = "获取该项目下按阶段分组的任务树结构")
    @GetMapping("/tasks")
    public Result<List<PhaseTaskGroup>> taskTree(@PathVariable Long projectId) {
        return Result.ok(taskService.getTaskTree(projectId));
    }

    @Operation(summary = "创建任务", description = "在指定阶段下创建新任务，sortOrder 未指定时自动追加到末尾")
    @PostMapping("/phases/{phaseId}/tasks")
    public Result<Task> create(@PathVariable Long projectId,
                                @PathVariable Long phaseId,
                                @Valid @RequestBody TaskCreateRequest req) {
        return Result.ok(taskService.create(projectId, phaseId, req));
    }

    @Operation(summary = "更新任务", description = "更新任务标题、描述、优先级等信息")
    @PutMapping("/tasks/{id}")
    public Result<Task> update(@PathVariable Long projectId,
                               @PathVariable Long id,
                               @Valid @RequestBody TaskCreateRequest req) {
        return Result.ok(taskService.update(projectId, id, req));
    }

    @Operation(summary = "更新任务状态", description = "修改任务状态为 IN_PROGRESS 时自动填充 startedAt，为 DONE 时填充 completedAt")
    @PatchMapping("/tasks/{id}/status")
    public Result<Task> updateStatus(@PathVariable Long projectId,
                                      @PathVariable Long id,
                                      @Valid @RequestBody TaskStatusUpdateRequest req) {
        return Result.ok(taskService.updateStatus(projectId, id, req));
    }

    @Operation(summary = "删除任务", description = "逻辑删除指定任务")
    @DeleteMapping("/tasks/{id}")
    public Result<?> delete(@PathVariable Long projectId, @PathVariable Long id) {
        taskService.delete(projectId, id);
        return Result.ok();
    }
}
