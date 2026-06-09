package com.specflow.dto;

import com.specflow.entity.Task;
import lombok.Data;

import java.util.List;

@Data
public class PhaseTaskGroup {
    private Long phaseId;
    private String phaseName;
    private String phaseStatus;
    private Integer sortOrder;
    private List<Task> tasks;
}
