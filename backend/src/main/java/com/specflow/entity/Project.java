package com.specflow.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sf_project")
public class Project {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long ownerId;
    private String name;
    private String description;
    private String requirementRaw;
    private String techStack;
    private String apiToken;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @TableLogic
    private Integer deleted;
}
