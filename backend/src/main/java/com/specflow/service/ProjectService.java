package com.specflow.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.specflow.common.BizException;
import com.specflow.dto.ProjectCreateRequest;
import com.specflow.dto.ProjectDetailResponse;
import com.specflow.dto.ProjectUpdateRequest;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectMapper projectMapper;
    private final PhaseMapper phaseMapper;
    private final TaskMapper taskMapper;

    public List<Project> listMyProjects(int page, int pageSize) {
        Long userId = getCurrentUserId();
        Page<Project> p = new Page<>(page, pageSize);
        return projectMapper.selectPage(p,
                new LambdaQueryWrapper<Project>()
                        .eq(Project::getOwnerId, userId)
                        .orderByDesc(Project::getCreatedAt)
        ).getRecords();
    }

    public long countMyProjects() {
        Long userId = getCurrentUserId();
        return projectMapper.selectCount(
                new LambdaQueryWrapper<Project>().eq(Project::getOwnerId, userId)
        );
    }

    public ProjectDetailResponse getDetail(Long id) {
        Long userId = getCurrentUserId();
        Project project = projectMapper.selectOne(
                new LambdaQueryWrapper<Project>()
                        .eq(Project::getId, id)
                        .eq(Project::getOwnerId, userId)
        );
        if (project == null) {
            throw new BizException(40401, "项目不存在");
        }
        ProjectDetailResponse resp = new ProjectDetailResponse();
        org.springframework.beans.BeanUtils.copyProperties(project, resp);
        resp.setPhaseCount(phaseMapper.selectCount(
                new LambdaQueryWrapper<Phase>().eq(Phase::getProjectId, id)
        ));
        List<Long> phaseIds = phaseMapper.selectList(
                new LambdaQueryWrapper<Phase>().eq(Phase::getProjectId, id)
        ).stream().map(Phase::getId).toList();

        resp.setTaskCount(phaseIds.isEmpty() ? 0 : taskMapper.selectCount(
                new LambdaQueryWrapper<Task>().in(Task::getPhaseId, phaseIds)
        ));
        resp.setCompletedTaskCount(phaseIds.isEmpty() ? 0 : taskMapper.selectCount(
                new LambdaQueryWrapper<Task>()
                        .in(Task::getPhaseId, phaseIds)
                        .eq(Task::getStatus, "done")
        ));
        return resp;
    }

    public Project create(ProjectCreateRequest req) {
        Long userId = getCurrentUserId();
        Project project = new Project();
        project.setOwnerId(userId);
        project.setName(req.getName());
        project.setDescription(req.getDescription());
        project.setTechStack(req.getTechStack());
        project.setStatus("active");
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());
        projectMapper.insert(project);
        return project;
    }

    public Project update(Long id, ProjectUpdateRequest req) {
        Long userId = getCurrentUserId();
        Project project = projectMapper.selectOne(
                new LambdaQueryWrapper<Project>()
                        .eq(Project::getId, id)
                        .eq(Project::getOwnerId, userId)
        );
        if (project == null) {
            throw new BizException(40401, "项目不存在");
        }
        if (StringUtils.hasText(req.getName())) project.setName(req.getName());
        if (req.getDescription() != null) project.setDescription(req.getDescription());
        if (StringUtils.hasText(req.getTechStack())) project.setTechStack(req.getTechStack());
        if (StringUtils.hasText(req.getStatus())) project.setStatus(req.getStatus());
        project.setUpdatedAt(LocalDateTime.now());
        projectMapper.updateById(project);
        return project;
    }

    public void delete(Long id) {
        Long userId = getCurrentUserId();
        Project project = projectMapper.selectOne(
                new LambdaQueryWrapper<Project>()
                        .eq(Project::getId, id)
                        .eq(Project::getOwnerId, userId)
        );
        if (project == null) {
            throw new BizException(40401, "项目不存在");
        }
        projectMapper.deleteById(id);
    }

    private Long getCurrentUserId() {
        return Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
