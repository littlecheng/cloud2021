package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.PayMent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(value = "PaymentService",fallback = FeignError.class)
public interface CallPayMent {

     @PostMapping(value = "/payment/save",produces = "application/json")
     CommonResult<PayMent> add(@RequestBody PayMent payMent);

    @GetMapping(value = "/payment/select/{id}")
     CommonResult<PayMent> select(@PathVariable("id") Long id);

    @GetMapping(value = "/payment/timeout2/{id}")
    String timeout(@PathVariable("id") Long id);

}
