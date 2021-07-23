package com.atguigu.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsulController {


    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/")
    public String hello(){
        return restTemplate.getForObject("http://consul-provider/hello",String.class);
    }
}
