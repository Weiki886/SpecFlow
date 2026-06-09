package com.specflow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sf_task_file_relation")
public class TaskFileRelation {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long taskId;
    private String filePath;
    private String relationType;
}
