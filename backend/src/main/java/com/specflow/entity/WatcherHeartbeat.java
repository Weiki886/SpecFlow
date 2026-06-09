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
@TableName("sf_watcher_heartbeat")
public class WatcherHeartbeat {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long projectId;
    private Integer watchingFiles;
    private Integer todayChecks;
    private Integer todayViolations;
    private LocalDateTime lastBeatAt;
    private LocalDateTime createdAt;
}
