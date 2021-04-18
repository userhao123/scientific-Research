package com.hao.scientificresearch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hao.scientificresearch.mapper")
public class ScientificResearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScientificResearchApplication.class, args);
    }

}
