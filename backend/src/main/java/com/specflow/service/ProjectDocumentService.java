package com.specflow.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.specflow.common.BizException;
import com.specflow.dto.ProjectDocumentRequest;
import com.specflow.entity.Project;
import com.specflow.entity.ProjectDocument;
import com.specflow.mapper.ProjectMapper;
import com.specflow.mapper.ProjectDocumentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectDocumentService {

    private final ProjectDocumentMapper documentMapper;
    private final ProjectMapper projectMapper;

    public List<ProjectDocument> listByProject(Long projectId) {
        verifyProjectOwnership(projectId);
        return documentMapper.selectList(
                new LambdaQueryWrapper<ProjectDocument>()
                        .eq(ProjectDocument::getProjectId, projectId)
                        .orderByAsc(ProjectDocument::getCreatedAt)
        );
    }

    public ProjectDocument getByDocType(Long projectId, String docType) {
        verifyProjectOwnership(projectId);
        ProjectDocument doc = documentMapper.selectOne(
                new LambdaQueryWrapper<ProjectDocument>()
                        .eq(ProjectDocument::getProjectId, projectId)
                        .eq(ProjectDocument::getDocType, docType)
        );
        if (doc == null) {
            throw new BizException(40401, "文档不存在");
        }
        return doc;
    }

    public ProjectDocument saveOrUpdate(Long projectId, ProjectDocumentRequest req) {
        verifyProjectOwnership(projectId);
        ProjectDocument existing = documentMapper.selectOne(
                new LambdaQueryWrapper<ProjectDocument>()
                        .eq(ProjectDocument::getProjectId, projectId)
                        .eq(ProjectDocument::getDocType, req.getDocType())
        );
        if (existing != null) {
            existing.setTitle(req.getTitle());
            existing.setContent(req.getContent());
            existing.setVersion(existing.getVersion() + 1);
            existing.setUpdatedAt(LocalDateTime.now());
            documentMapper.updateById(existing);
            return existing;
        } else {
            ProjectDocument doc = new ProjectDocument();
            doc.setProjectId(projectId);
            doc.setDocType(req.getDocType());
            doc.setTitle(req.getTitle());
            doc.setContent(req.getContent());
            doc.setVersion(1);
            doc.setCreatedAt(LocalDateTime.now());
            doc.setUpdatedAt(LocalDateTime.now());
            documentMapper.insert(doc);
            return doc;
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

    private Long getCurrentUserId() {
        return Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
