package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * springcloud与zookeeper整合,zk提供服务注册和发现
 *
 */
@SpringBootApplication
@EnableDiscoveryClient //该注解用于想使用consul或者zookeeper作为注册中心时注册服务，如果关闭该服务,zk会删除服务临时节点。
public class ZkProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZkProviderApplication.class, args);
    }

}
