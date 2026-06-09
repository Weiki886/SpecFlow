package com.specflow.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.specflow.common.BizException;
import com.specflow.dto.PhaseTaskGroup;
import com.specflow.dto.TaskCreateRequest;
import com.specflow.dto.TaskStatusUpdateRequest;
import com.specflow.entity.Phase;
import com.specflow.entity.Project;
import com.specflow.entity.Task;
import com.specflow.mapper.PhaseMapper;
import com.specflow.mapper.ProjectMapper;
import com.specflow.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskMapper taskMapper;
    private final PhaseMapper phaseMapper;
    private final ProjectMapper projectMapper;

    public List<PhaseTaskGroup> getTaskTree(Long projectId) {
        verifyProjectOwnership(projectId);
        List<Phase> phases = phaseMapper.selectList(
                new LambdaQueryWrapper<Phase>()
                        .eq(Phase::getProjectId, projectId)
                        .orderByAsc(Phase::getSortOrder)
        );
        List<PhaseTaskGroup> result = new ArrayList<>();
        for (Phase phase : phases) {
            List<Task> tasks = taskMapper.selectList(
                    new LambdaQueryWrapper<Task>()
                            .eq(Task::getPhaseId, phase.getId())
                            .orderByAsc(Task::getSortOrder)
            );
            PhaseTaskGroup group = new PhaseTaskGroup();
            group.setPhaseId(phase.getId());
            group.setPhaseName(phase.getName());
            group.setPhaseStatus(phase.getStatus());
            group.setSortOrder(phase.getSortOrder());
            group.setTasks(tasks);
            result.add(group);
        }
        return result;
    }

    public Task create(Long projectId, Long phaseId, TaskCreateRequest req) {
        verifyProjectOwnership(projectId);
        Phase phase = phaseMapper.selectOne(
                new LambdaQueryWrapper<Phase>()
                        .eq(Phase::getId, phaseId)
                        .eq(Phase::getProjectId, projectId)
        );
        if (phase == null) {
            throw new BizException(40401, "阶段不存在");
        }
        Task task = new Task();
        task.setPhaseId(phaseId);
        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());
        task.setPriority(StringUtils.hasText(req.getPriority()) ? req.getPriority() : "medium");
        task.setStatus(StringUtils.hasText(req.getStatus()) ? req.getStatus() : "todo");
        if (req.getSortOrder() != null) {
            task.setSortOrder(req.getSortOrder());
        } else {
            task.setSortOrder(calculateNextSortOrder(phaseId));
        }
        task.setAcceptanceCriteria(req.getAcceptanceCriteria());
        task.setEstimatedFiles(req.getEstimatedFiles());
        task.setCreatedBy(getCurrentUserId());
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        taskMapper.insert(task);
        return task;
    }

    public Task update(Long projectId, Long id, TaskCreateRequest req) {
        verifyProjectOwnership(projectId);
        Task task = taskMapper.selectOne(
                new LambdaQueryWrapper<Task>()
                        .eq(Task::getId, id)
                        .ne(Task::getDeleted, 1)
        );
        if (task == null) {
            throw new BizException(40401, "任务不存在");
        }
        if (StringUtils.hasText(req.getTitle())) task.setTitle(req.getTitle());
        if (req.getDescription() != null) task.setDescription(req.getDescription());
        if (StringUtils.hasText(req.getPriority())) task.setPriority(req.getPriority());
        if (req.getSortOrder() != null) task.setSortOrder(req.getSortOrder());
        if (StringUtils.hasText(req.getAcceptanceCriteria())) task.setAcceptanceCriteria(req.getAcceptanceCriteria());
        if (StringUtils.hasText(req.getEstimatedFiles())) task.setEstimatedFiles(req.getEstimatedFiles());
        task.setUpdatedAt(LocalDateTime.now());
        taskMapper.updateById(task);
        return task;
    }

    public Task updateStatus(Long projectId, Long id, TaskStatusUpdateRequest req) {
        verifyProjectOwnership(projectId);
        Task task = taskMapper.selectOne(
                new LambdaQueryWrapper<Task>()
                        .eq(Task::getId, id)
                        .ne(Task::getDeleted, 1)
        );
        if (task == null) {
            throw new BizException(40401, "任务不存在");
        }
        task.setStatus(req.getStatus());
        if ("IN_PROGRESS".equals(req.getStatus()) && task.getStartedAt() == null) {
            task.setStartedAt(LocalDateTime.now());
        }
        if ("DONE".equals(req.getStatus())) {
            task.setCompletedAt(LocalDateTime.now());
        }
        task.setUpdatedAt(LocalDateTime.now());
        taskMapper.updateById(task);
        return task;
    }

    public void delete(Long projectId, Long id) {
        verifyProjectOwnership(projectId);
        Task task = taskMapper.selectOne(
                new LambdaQueryWrapper<Task>()
                        .eq(Task::getId, id)
                        .ne(Task::getDeleted, 1)
        );
        if (task == null) {
            throw new BizException(40401, "任务不存在");
        }
        taskMapper.deleteById(id);
    }

    private void verifyProjectOwnership(Long projectId) {
        Long userId = getCurrentUserId();
        Project project = projectMapper.selectOne(
                new LambdaQueryWrapper<Project>()
                        .eq(Project::getId, projectId)
                        .eq(Project::getOwnerId, userId)
        );
        if (project == null) {
            throw new BizException(40401, "项目不存在");
        }
    }

    private Integer calculateNextSortOrder(Long phaseId) {
        Task last = taskMapper.selectOne(
                new LambdaQueryWrapper<Task>()
                        .eq(Task::getPhaseId, phaseId)
                        .ne(Task::getDeleted, 1)
                        .orderByDesc(Task::getSortOrder)
                        .last("LIMIT 1")
        );
        return last == null ? 1 : last.getSortOrder() + 1;
    }

    private Long getCurrentUserId() {
        return Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
