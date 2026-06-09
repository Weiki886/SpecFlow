package com.specflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.specflow.entity.GithubConnection;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GithubConnectionMapper extends BaseMapper<GithubConnection> {
}
