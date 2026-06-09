package com.specflow.config;

import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LlmConfig {

    @Value("${llm.deepseek.api-key}")
    private String deepseekApiKey;

    @Value("${llm.deepseek.model}")
    private String deepseekModel;

    @Value("${llm.deepseek.base-url}")
    private String deepseekBaseUrl;

    @Value("${llm.qwen.api-key}")
    private String qwenApiKey;

    @Value("${llm.qwen.model}")
    private String qwenModel;

    @Bean
    public OpenAiChatModel deepseekChatModel() {
        return OpenAiChatModel.builder()
                .apiKey(deepseekApiKey)
                .modelName(deepseekModel)
                .baseUrl(deepseekBaseUrl)
                .build();
    }

    @Bean
    public OpenAiChatModel qwenChatModel() {
        return OpenAiChatModel.builder()
                .apiKey(qwenApiKey)
                .modelName(qwenModel)
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .build();
    }
}
