package com.itheima;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.itheima.dao")
public class UserHealthApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserHealthApplication.class, args);
    }

}
