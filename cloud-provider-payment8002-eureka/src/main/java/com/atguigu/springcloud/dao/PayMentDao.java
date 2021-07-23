package com.atguigu.springcloud.dao;

import com.atguigu.springcloud.entities.PayMent;

import java.util.List;

public interface PayMentDao {
    int create(PayMent payMent);

    PayMent getPayMentByID(Long id);

    List<PayMent> selectAll();
}
