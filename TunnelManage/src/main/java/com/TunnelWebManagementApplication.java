package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = ("com.mapper"))
public class TunnelWebManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(TunnelWebManagementApplication.class, args);
    }

}
