package com.specflow.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sf_project_document")
public class ProjectDocument {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long projectId;
    private String docType;
    private String title;
    private String content;
    private Integer version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
