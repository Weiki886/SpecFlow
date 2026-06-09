package com.specflow.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskStatusUpdateRequest {
    @NotBlank(message = "状态不能为空")
    private String status;
}
