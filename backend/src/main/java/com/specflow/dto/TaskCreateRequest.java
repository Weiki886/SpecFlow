package com.specflow.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskCreateRequest {
    @NotBlank(message = "任务标题不能为空")
    private String title;

    private String description;

    private String priority;

    private Integer sortOrder;

    private String acceptanceCriteria;

    private String estimatedFiles;

    private String status;
}
