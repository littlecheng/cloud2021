package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.PayMent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class PayMentController {


    @Autowired
    private RestTemplate restTemplate;//单例模式

    //微服务集群调用方式
    private static final  String REST_URL_PREFIX = "http://PaymentService";


    @PostMapping(value = "/consumer/payment/create",produces = "application/json")
    public CommonResult<PayMent> create(@RequestBody PayMent payMent){
        return restTemplate.postForObject(REST_URL_PREFIX+"/payment/save",payMent,CommonResult.class);
    }

    @GetMapping("/consumer/payment/select/{id}")
    public CommonResult<PayMent> selectByID(@PathVariable(name="id") Long id){
        log.info("restTemplate="+restTemplate);
        return restTemplate.getForObject(REST_URL_PREFIX+"/payment/select/{id}",CommonResult.class,id);
    }


}
