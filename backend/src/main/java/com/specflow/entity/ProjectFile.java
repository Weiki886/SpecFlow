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
@TableName("sf_project_file")
public class ProjectFile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long projectId;
    private String filePath;
    private String fileType;
    private String contentMd5;
    private LocalDateTime lastScanned;
}
