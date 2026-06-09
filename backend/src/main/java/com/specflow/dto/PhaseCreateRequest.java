package com.specflow.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PhaseCreateRequest {
    @NotBlank(message = "阶段名称不能为空")
    private String name;

    private String description;

    private Integer sortOrder;

    private String status;
}
