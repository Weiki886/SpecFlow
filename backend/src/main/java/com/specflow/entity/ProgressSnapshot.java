package com.specflow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sf_progress_snapshot")
public class ProgressSnapshot {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long projectId;
    private Integer totalTasks;
    private Integer completedTasks;
    private BigDecimal completionRate;
    private LocalDate snapshotDate;
    private LocalDateTime createdAt;
}
