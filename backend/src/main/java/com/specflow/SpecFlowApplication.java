package com.specflow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.specflow.mapper")
public class SpecFlowApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpecFlowApplication.class, args);
    }
}
