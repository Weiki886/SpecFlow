package com.specflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.specflow.entity.Phase;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PhaseMapper extends BaseMapper<Phase> {
}
