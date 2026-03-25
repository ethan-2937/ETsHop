package com.etshop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.etshop")
@MapperScan("com.etshop.mappers")
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
public class ETsHopAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(ETsHopAdminApplication.class, args);
    }
}
