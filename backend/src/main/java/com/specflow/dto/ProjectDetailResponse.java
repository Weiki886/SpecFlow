package com.specflow.dto;

import com.specflow.entity.Project;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectDetailResponse extends Project {
    private long phaseCount;
    private long taskCount;
    private long completedTaskCount;
}
