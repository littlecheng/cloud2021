package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.PayMent;
import org.springframework.stereotype.Component;

@Component
public class FeignError implements CallPayMent {

    @Override
    public CommonResult<PayMent> add(PayMent payMent) {
        return new CommonResult<>(404,"维护中...");
    }


    @Override
    public CommonResult<PayMent> select(Long id) {
        return new CommonResult<>(404,"维护中...");
    }

    @Override
    public String timeout(Long id) {
        return "系统维护中";
    }
}
