package com.atguigu.springcloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
@MapperScan(basePackages = "com.atguigu.springcloud.dao")
public class EurekaProvider8001 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaProvider8001.class,args);
    }
}
