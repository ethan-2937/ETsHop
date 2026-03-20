package com.etshop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication(scanBasePackages = "com.etshop")
@MapperScan
public class ETsHopAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(ETsHopAdminApplication.class, args);
    }
}
