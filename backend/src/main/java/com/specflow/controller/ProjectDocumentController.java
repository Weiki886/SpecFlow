package com.specflow.controller;

import com.specflow.common.Result;
import com.specflow.dto.ProjectDocumentRequest;
import com.specflow.entity.ProjectDocument;
import com.specflow.service.ProjectDocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/documents")
@RequiredArgsConstructor
@Tag(name = "项目文档管理", description = "项目开发文档的查询与编辑接口")
public class ProjectDocumentController {

    private final ProjectDocumentService documentService;

    @Operation(summary = "文档列表", description = "获取指定项目的所有文档")
    @GetMapping
    public Result<List<ProjectDocument>> list(@PathVariable Long projectId) {
        return Result.ok(documentService.listByProject(projectId));
    }

    @Operation(summary = "获取文档", description = "根据文档类型获取单个文档内容")
    @GetMapping("/{docType}")
    public Result<ProjectDocument> get(@PathVariable Long projectId,
                                         @PathVariable String docType) {
        return Result.ok(documentService.getByDocType(projectId, docType));
    }

    @Operation(summary = "保存/更新文档", description = "文档不存在则新建，存在则更新内容并使版本号自增")
    @PutMapping("/{docType}")
    public Result<ProjectDocument> saveOrUpdate(@PathVariable Long projectId,
                                                  @PathVariable String docType,
                                                  @Valid @RequestBody ProjectDocumentRequest req) {
        req.setDocType(docType);
        return Result.ok(documentService.saveOrUpdate(projectId, req));
    }
}
