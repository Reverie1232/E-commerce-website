package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@MapperScan("com.example.mapper")
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class FurinaApplication {

    public static void main(String[] args) {
        SpringApplication.run(FurinaApplication.class, args);
    }

}
