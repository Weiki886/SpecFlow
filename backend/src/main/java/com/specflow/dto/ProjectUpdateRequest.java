package com.specflow.dto;

import lombok.Data;

@Data
public class ProjectUpdateRequest {
    private String name;
    private String description;
    private String techStack;
    private String status;
}
