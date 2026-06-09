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
@TableName("sf_github_connection")
public class GithubConnection {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long githubUserId;
    private String githubUsername;
    private String accessToken;
    private LocalDateTime createdAt;
}
