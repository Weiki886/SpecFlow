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
@TableName("sf_constraint_rule")
public class ConstraintRule {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long projectId;
    private String name;
    private String category;
    private String severity;
    private String ruleDefinition;
    private String fixSuggestion;
    private Integer isEnabled;
    private Integer isBuiltin;
    private LocalDateTime createdAt;
}
