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
@TableName("sf_github_repo")
public class GithubRepo {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long projectId;
    private Long connectionId;
    private String repoFullName;
    private String repoUrl;
    private String webhookSecret;
    private String webhookId;
    private LocalDateTime createdAt;
}
