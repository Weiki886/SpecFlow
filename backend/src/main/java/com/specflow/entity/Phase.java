package com.specflow.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sf_phase")
public class Phase {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long projectId;
    private String name;
    private String description;
    private Integer sortOrder;
    private String status;
    private Integer isGated;
    private LocalDateTime gatedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
