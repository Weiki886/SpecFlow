package com.specflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.specflow.entity.GithubRepo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GithubRepoMapper extends BaseMapper<GithubRepo> {
}
