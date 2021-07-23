package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.PayMent;
import com.atguigu.springcloud.service.PayMentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("/payment")
public class MayMentController {

    @Value("${server.port}")
    private String port;

    @Autowired
    private PayMentService payMentService;

    @PostMapping(value = "/save",produces = "application/json")
    public CommonResult<PayMent> create(@RequestBody PayMent payMent){
        int result =  payMentService.create(payMent);
        if(result>0){
            return new CommonResult(200,"插入成功,端口:"+port,result);
        }else{
            return new CommonResult(500,"插入失败,端口:"+port,null);
        }
    }

    @GetMapping(value = "/select/{id}")
    @HystrixCommand(fallbackMethod = "errorHandler",commandProperties ={@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value ="3000" )})
    public CommonResult<PayMent> select(@PathVariable("id") Long id){
            try {
                TimeUnit.SECONDS.sleep(id);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            PayMent payMent =  payMentService.getPayMentByID(id);
            if(payMent !=null){
                return new CommonResult(200, "查询成功,端口:"+port, payMent);
            }else{
                return new CommonResult(500,"没有"+id+"对应的记录,端口:"+port,null);
            }
    }

    @GetMapping(value = "/selectAll")
    @HystrixCommand(fallbackMethod = "errorHandlerFor",commandProperties ={@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value ="6000" )})
    public CommonResult<List<PayMent>> selectAll(){
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new CommonResult(200, "查询成功", payMentService.selectAll());

    }

    /**
     * 目标方法和回退方法  参数和返回类型要一致
     * @return
     */
    public  CommonResult<List<PayMent>> errorHandlerFor(){
          return new CommonResult<List<PayMent>>(404, "服务器繁忙，稍后重试",null);
    }

    /**
     * 目标方法和回退方法  参数和返回类型要一致
     * @return
     */
    public  CommonResult<PayMent> errorHandler(Long  id){
        return new CommonResult<PayMent>(400, "服务器繁忙，稍后重试",null);
    }

    @GetMapping(value = "/timeout/{id}")
    public CommonResult<String> timeout(@PathVariable("id") Long id){
        String result = payMentService.timeout(id);
        return new CommonResult(200, "查询成功", result);

    }
}
