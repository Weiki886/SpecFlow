package com.specflow.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI specFlowOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SpecFlow 接口文档")
                        .description("AI 驱动的软件开发生命周期治理平台接口文档")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("SpecFlow Team")));
    }
}
