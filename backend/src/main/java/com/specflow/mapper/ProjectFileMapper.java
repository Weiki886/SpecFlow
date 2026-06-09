package com.specflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.specflow.entity.ProjectFile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProjectFileMapper extends BaseMapper<ProjectFile> {
}
