package com.atguigu.springcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    /**
     *   1.服务提供方宕机
     *   2.服务提供者超时
     *   3.服务提供者异常
     *   以上3种都会触发gateway回退方法
     */

    @GetMapping("/fallback")
    public String fallback() {
        return "服务暂时不可用";
    }
}
