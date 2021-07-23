package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.PayMent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HandlerError implements CallPayMent{
    @Override
    public CommonResult<PayMent> add(PayMent payMent) {
        return new CommonResult<>(404,"系统超时",null);
    }

    @Override
    public CommonResult<PayMent> select(Long id) {
        return new CommonResult<>(404,"系统超时",null);
    }

    @Override
    public CommonResult<List<PayMent>> selectAll() {
        return new CommonResult<List<PayMent>>(404,"系统超时",null);
    }

    @Override
    public CommonResult<String> timeout(Long id) {
        return new CommonResult<String>(404,"服务宕机不可用,请稍后重试",null);
    }
}
