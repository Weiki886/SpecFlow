package com.specflow.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjectDocumentRequest {
    private String docType;

    @NotBlank(message = "文档标题不能为空")
    private String title;

    @NotBlank(message = "文档内容不能为空")
    private String content;
}
