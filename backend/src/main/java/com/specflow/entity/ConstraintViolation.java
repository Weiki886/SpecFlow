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
@TableName("sf_constraint_violation")
public class ConstraintViolation {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long projectId;
    private Long ruleId;
    private Long taskId;
    private String filePath;
    private Integer lineNumber;
    private String message;
    private String fixSuggestion;
    private String severity;
    private String status;
    private LocalDateTime createdAt;
}
