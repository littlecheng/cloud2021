package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.PayMent;

import java.util.List;

public interface PayMentService {


     int create(PayMent payMent);

     PayMent getPayMentByID(Long id);

     List<PayMent> selectAll();

     String timeout(Long id);

}
