package com.specflow.tool;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.model.chat.ChatModel;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/**
 * 手动测试 LLM 连通性工具。
 * 直接运行 main 方法即可测试各大模型是否可用。
 * 支持从环境变量和 application-local.yml 读取配置。
 */
@Slf4j
public class LLMTester {

    private static final String CONFIG_PATH = "src/main/resources/application-local.yml";
    private static final String CONFIG_PATH_FALLBACK = "src/main/resources/application.yml";

    public static void main(String[] args) {
        log.info("========== LLM 连通性测试 ==========");
        log.info("配置来源优先级：环境变量 > application-local.yml");
        log.info("====================================\n");

        testQwen();
        testDeepseek();
    }

    private static void testQwen() {
        String apiKey = getApiKey("QWEN_API_KEY", "llm.qwen.api-key");
        if (apiKey == null || apiKey.isBlank() || apiKey.contains("your-qwen")) {
            log.warn("[通义千问/Qwen] 未配置有效 API_KEY，跳过测试");
            return;
        }
        try {
            String modelName = getModelName("QWEN_MODEL", "llm.qwen.model", "qwen-plus");
            log.info("[通义千问/Qwen] 开始测试，模型: {}", modelName);
            ChatModel model = QwenChatModel.builder()
                    .apiKey(apiKey)
                    .modelName(modelName)
                    .temperature(0.7f)
                    .build();
            String response = model.chat("用一句话介绍自己");
            log.info("[通义千问/Qwen] 连通正常，回复: {}", response);
        } catch (Exception e) {
            log.error("[通义千问/Qwen] 连通失败: {}", e.getMessage(), e);
        }
    }

    private static void testDeepseek() {
        String apiKey = getApiKey("DEEPSEEK_API_KEY", "llm.deepseek.api-key");
        if (apiKey == null || apiKey.isBlank() || apiKey.contains("your-deepseek")) {
            log.warn("[DeepSeek] 未配置有效 API_KEY，跳过测试");
            return;
        }
        try {
            String modelName = getModelName("DEEPSEEK_MODEL", "llm.deepseek.model", "deepseek-chat");
            String baseUrl = getFromYaml("llm.deepseek.base-url");
            if (baseUrl == null || baseUrl.isBlank()) {
                baseUrl = "https://api.deepseek.com";
            }
            log.info("[DeepSeek] 开始测试，模型: {}，BaseURL: {}", modelName, baseUrl);
            ChatModel model = dev.langchain4j.model.openai.OpenAiChatModel.builder()
                    .baseUrl(baseUrl)
                    .apiKey(apiKey)
                    .modelName(modelName)
                    .temperature(0.7d)
                    .build();
            String response = model.chat("用一句话介绍自己");
            log.info("[DeepSeek] 连通正常，回复: {}", response);
        } catch (Exception e) {
            log.error("[DeepSeek] 连通失败: {}", e.getMessage(), e);
        }
    }

    private static String getApiKey(String envKey, String ymlKey) {
        String value = System.getenv(envKey);
        if (value != null && !value.isBlank()) {
            return value;
        }
        return getFromYaml(ymlKey);
    }

    private static String getModelName(String envKey, String ymlKey, String defaultVal) {
        String fromEnv = System.getenv(envKey);
        if (fromEnv != null && !fromEnv.isBlank()) {
            return fromEnv;
        }
        String fromYml = getFromYaml(ymlKey);
        return fromYml != null && !fromYml.isBlank() ? fromYml : defaultVal;
    }

    private static String getFromYaml(String key) {
        Path ymlPath = Path.of(CONFIG_PATH);
        if (!Files.exists(ymlPath)) {
            ymlPath = Path.of(CONFIG_PATH_FALLBACK);
        }
        if (!Files.exists(ymlPath)) {
            return null;
        }
        try (InputStream is = new FileInputStream(ymlPath.toFile())) {
            Yaml yaml = new Yaml();
            Map<String, Object> config = yaml.load(is);
            String[] parts = key.split("\\.");
            Object current = config;
            for (String part : parts) {
                if (current instanceof Map) {
                    current = ((Map<?, ?>) current).get(part);
                } else {
                    return null;
                }
            }
            return current != null ? current.toString() : null;
        } catch (Exception e) {
            log.debug("读取 yml 失败: {}", e.getMessage());
            return null;
        }
    }
}
