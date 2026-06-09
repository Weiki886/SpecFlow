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
@TableName("sf_github_commit")
public class GithubCommit {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long projectId;
    private Long repoId;
    private String sha;
    private String message;
    private String authorName;
    private Long matchedTaskId;
    private String matchMethod;
    private LocalDateTime pushedAt;
    private LocalDateTime createdAt;
}
