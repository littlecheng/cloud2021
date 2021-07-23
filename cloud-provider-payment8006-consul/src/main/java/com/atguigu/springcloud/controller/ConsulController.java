package com.atguigu.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsulController {

    @Value("${server.port}")
    private String port;

    @RequestMapping("/hello")
    public String home() {
        return "Hello world ,port:"+port;
    }

}
