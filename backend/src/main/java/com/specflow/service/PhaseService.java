package com.specflow.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.specflow.common.BizException;
import com.specflow.dto.PhaseCreateRequest;
import com.specflow.entity.Phase;
import com.specflow.entity.Project;
import com.specflow.mapper.PhaseMapper;
import com.specflow.mapper.ProjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhaseService {

    private final PhaseMapper phaseMapper;
    private final ProjectMapper projectMapper;

    public List<Phase> listByProject(Long projectId) {
        verifyProjectOwnership(projectId);
        return phaseMapper.selectList(
                new LambdaQueryWrapper<Phase>()
                        .eq(Phase::getProjectId, projectId)
                        .orderByAsc(Phase::getSortOrder)
        );
    }

    public Phase create(Long projectId, PhaseCreateRequest req) {
        verifyProjectOwnership(projectId);
        Phase phase = new Phase();
        phase.setProjectId(projectId);
        phase.setName(req.getName());
        phase.setDescription(req.getDescription());
        if (req.getSortOrder() != null) {
            phase.setSortOrder(req.getSortOrder());
        } else {
            phase.setSortOrder(calculateNextSortOrder(projectId));
        }
        phase.setStatus(StringUtils.hasText(req.getStatus()) ? req.getStatus() : "pending");
        phase.setIsGated(0);
        phase.setCreatedAt(LocalDateTime.now());
        phase.setUpdatedAt(LocalDateTime.now());
        phaseMapper.insert(phase);
        return phase;
    }

    public Phase update(Long projectId, Long id, PhaseCreateRequest req) {
        verifyProjectOwnership(projectId);
        Phase phase = phaseMapper.selectOne(
                new LambdaQueryWrapper<Phase>()
                        .eq(Phase::getId, id)
                        .eq(Phase::getProjectId, projectId)
        );
        if (phase == null) {
            throw new BizException(40401, "阶段不存在");
        }
        if (StringUtils.hasText(req.getName())) phase.setName(req.getName());
        if (req.getDescription() != null) phase.setDescription(req.getDescription());
        if (req.getSortOrder() != null) phase.setSortOrder(req.getSortOrder());
        if (StringUtils.hasText(req.getStatus())) phase.setStatus(req.getStatus());
        phase.setUpdatedAt(LocalDateTime.now());
        phaseMapper.updateById(phase);
        return phase;
    }

    public void delete(Long projectId, Long id) {
        verifyProjectOwnership(projectId);
        int rows = phaseMapper.delete(
                new LambdaQueryWrapper<Phase>()
                        .eq(Phase::getId, id)
                        .eq(Phase::getProjectId, projectId)
        );
        if (rows == 0) {
            throw new BizException(40401, "阶段不存在");
        }
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

    private Integer calculateNextSortOrder(Long projectId) {
        Phase last = phaseMapper.selectOne(
                new LambdaQueryWrapper<Phase>()
                        .eq(Phase::getProjectId, projectId)
                        .orderByDesc(Phase::getSortOrder)
                        .last("LIMIT 1")
        );
        return last == null ? 1 : last.getSortOrder() + 1;
    }

    private Long getCurrentUserId() {
        return Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
