package com.zhu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/21 00:00
 */
@RestController
@RequestMapping("/remote")
public class HelloController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.application.name}")
    private String appName;


    @GetMapping("/app-name")
    public String echoAppName() {
        //使用 LoadBalanceClient 和 RestTemolate 结合的方式来访问
        ServiceInstance serviceInstance = loadBalancerClient.choose("nacos-discovery01");
        String url = String.format("http://%s:%s/discovery/%s", serviceInstance.getHost(), serviceInstance.getPort(), appName);
        System.out.println("request url:" + url);
        return restTemplate.getForObject(url, String.class);
    }


}