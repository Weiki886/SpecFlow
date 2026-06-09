package com.specflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.specflow.entity.AiChatSession;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AiChatSessionMapper extends BaseMapper<AiChatSession> {
}
