package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.PayMent;
import com.atguigu.springcloud.service.CallPayMent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hystrix")
@Slf4j
public class OpenFeginController {

    @Autowired
    private CallPayMent callPayMent;

    @PostMapping(value = "/save",produces = "application/json")
    public CommonResult<PayMent> add(@RequestBody PayMent payMent){
        return callPayMent.add(payMent);
    }

    @GetMapping(value = "/select/{id}")
    public CommonResult<PayMent> select(@PathVariable("id") Long id){
        return callPayMent.select(id);
    }

    /**
     * 1.提供方不可访问，开启断路器,执行fallback
     * @param id
     * @return
     */
    @GetMapping("/timeout/{id}")
    public String timeout(@PathVariable("id") Long id){
        return callPayMent.timeout(id);
    }



}
