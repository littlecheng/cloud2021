package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.PayMent;
import com.atguigu.springcloud.service.CallPayMent;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/consumer/fegin")
@DefaultProperties(defaultFallback = "GlobalMethod")   //全局配置
public class OpenFeginController {

    /**
     * 触发fallback方式
     * 1.   服务提供者不可访问，并且开启断路器，执行FeignError timeout()
     * 2.  超时,异常 执行@HystrixCommand(fallbackMethod="fallback")  fallbackMethod属性值定义的方法
     * 3.
     */


    @Autowired
    private CallPayMent callPayMent;

    @PostMapping(value = "/save",produces = "application/json")
    public CommonResult<PayMent> add(@RequestBody PayMent payMent){
        return callPayMent.add(payMent);
    }

    @GetMapping(value = "/select/{id}")
    @HystrixCommand(commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000")})
    public CommonResult<PayMent> select(@PathVariable("id") Long id)  {
        return callPayMent.select(id);
    }

    /**
     * 宕机情况下（提供者关掉服务）由HandlerError处理
     * 超时情况下由fallback处理
     * @param id
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/selectAll/{id}")
    @HystrixCommand(fallbackMethod="fallback",commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "6500")})
    public CommonResult<List<PayMent>> selectAll(@PathVariable("id") Long id) throws InterruptedException {
        log.info("继续执行?");
        TimeUnit.SECONDS.sleep(id);
      //  int i = 1/0;
        log.info("继续执行?????");
        return callPayMent.selectAll();
    }

    /**
     * 宕机情况下（提供者关掉服务）由HandlerError处理
     * 超时情况下由全局方法处理
     * 其他情况下正常访问
     * @return
     */
    @GetMapping("/timeout/{id}")
    @HystrixCommand(fallbackMethod = "timeoutfallback",commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2500")})
    public CommonResult<String> timeout(@PathVariable("id") Long id)  {
        try {
            log.info(id+" start date="+new Date());
            TimeUnit.SECONDS.sleep(id);
            log.info("end date="+new Date());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("执行吗");
        return callPayMent.timeout(id);
    }


    /**
     * 服务熔断  HystrixCommand
     * 宕机情况下（提供者关掉服务）由HandlerError处理
     * 在熔断时间内，请求阈值条件下，失败率达到设定值,触发circuitBreaker方法。
     * 测试方法:先访问各自正常逻辑,都是正常访问的;
     *        访问http://localhost/consumer/fegin/timeoutWithCircuitBreaker/-1  不断F5刷新该请求地址,满足失败率,
     *        然后请求正常地址http://localhost/consumer/fegin/timeoutWithCircuitBreaker/1 会发现刚开始还会返回熔断后的timeoutfallback方法，等过一会访问就返回正常的返回值了
     * @return
     */
    @GetMapping("/timeoutWithCircuitBreaker/{id}")
    @HystrixCommand(fallbackMethod = "timeoutfallback",commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2500"),
            @HystrixProperty(name="circuitBreaker.enabled",value = "true"),
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "10"),//请求阈值
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//熔断时间
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value = "50")//失败率达到多少百分比后熔断
    })
    public CommonResult<String> timeoutWithCircuitBreaker(@PathVariable("id") Long id)  {
        if(id <0){
            throw new RuntimeException("id 不能为负数");
        }
        return callPayMent.timeout(id);
    }

    /**
     * 返回类型和参数个数、类型保持一致，不然报错
     * @param id
     * @return
     */
    public  CommonResult<List<PayMent>> fallback(Long id){
        return new CommonResult<>(400, "请稍后重试", null);
    }

    /**
     * 返回类型和参数个数、类型保持一致，不然报错
     * @param id
     * @return
     */
    public  CommonResult<String> timeoutfallback(Long id){
        return new CommonResult<>(404, "请稍后重试", "");
    }


    /**
     * 返回类型和参数个数、类型保持一致，不然报错
     * @return
     */
    public CommonResult<PayMent> GlobalMethod() {
        return new CommonResult<PayMent>(400, "系统超时,请稍后重试", null);
    }


}
