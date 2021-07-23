package com.atguigu.springcloud.service;

import com.atguigu.springcloud.dao.PayMentDao;
import com.atguigu.springcloud.entities.PayMent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PayMentServiceImpl implements PayMentService {

    @Autowired
    private PayMentDao payMentDao;

    @Override
    public int create(PayMent payMent){
        return  payMentDao.create(payMent);
    }

    @Override
    public PayMent getPayMentByID(Long id){
        return  payMentDao.getPayMentByID(id);
    }

    @Override
    public List<PayMent> selectAll() {
        return payMentDao.selectAll();
    }

    /**
     * 服务端超时
     * @return
     */
    @Override
    public String timeout(Long id) {
        return "ThreadName="+Thread.currentThread().getName()+" ,date="+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }




}
