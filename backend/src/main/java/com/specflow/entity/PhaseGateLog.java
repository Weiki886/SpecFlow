package com.specflow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sf_phase_gate_log")
public class PhaseGateLog {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long projectId;
    private Long phaseId;
    private String action;
    private String checkResult;
    private Integer errorCount;
    private Integer warnCount;
    private Integer apiTestPass;
    private Integer allTasksDone;
    private Integer passed;
    private Long operatedBy;
    private LocalDateTime createdAt;
}
