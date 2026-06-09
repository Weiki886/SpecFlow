package com.specflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.specflow.entity.ProgressSnapshot;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProgressSnapshotMapper extends BaseMapper<ProgressSnapshot> {
}
