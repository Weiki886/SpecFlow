package com.specflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.specflow.entity.Project;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProjectMapper extends BaseMapper<Project> {
}
