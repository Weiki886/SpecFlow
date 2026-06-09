package com.specflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.specflow.entity.ConstraintViolation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ConstraintViolationMapper extends BaseMapper<ConstraintViolation> {
}
