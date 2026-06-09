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
@TableName("sf_api_test_result")
public class ApiTestResult {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long projectId;
    private String checkRunId;
    private String endpoint;
    private Integer expectedStatus;
    private Integer actualStatus;
    private String responseBody;
    private Integer passed;
    private String errorMessage;
    private LocalDateTime checkedAt;
}
