package com.atguigu.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class zkController {

    @Value("${server.port}")
    private String port;


    @GetMapping(value = "/payment/zk")
    public String select(){
        return "springcloud with zookeeper:"+port;
    }


}
