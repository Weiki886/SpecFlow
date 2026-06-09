package com.specflow.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sf_task")
public class Task {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long phaseId;
    private String title;
    private String description;
    private String status;
    private String priority;
    private Integer sortOrder;
    private String acceptanceCriteria;
    private String estimatedFiles;
    private Long createdBy;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @TableLogic
    private Integer deleted;
}
