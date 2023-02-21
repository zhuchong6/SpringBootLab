package com.zhu.controller;

import com.zhu.feign.FeignDiscoveryClient;
import com.zhu.feign.NacosCircuitbreakerServiceFeign;
import com.zhu.feign.NacosServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/21 22:24
 */
@RestController
@RequestMapping("/feign")
public class FeignClientController {

    @Autowired
    private FeignDiscoveryClient nacosDiscoveryClient;

    @Autowired
    private NacosServiceFeign nacosServiceFeign;

    @Autowired
    private NacosCircuitbreakerServiceFeign nacosCircuitbreakerServiceFeign;

    @GetMapping("/{name}")
    public String hello(@PathVariable("name") String name) {
        String hello = nacosDiscoveryClient.hello(name);
        return hello;
    }

    @GetMapping("/nacos/{name}")
    public String hello2(@PathVariable("name") String name) {
        String hello = nacosServiceFeign.hello(name);
        return hello;
    }

    @GetMapping("/nacos/circuitbreaker/{name}")
    public String hello3(@PathVariable("name") String name) {
        String hello = nacosCircuitbreakerServiceFeign.hello(name);
        return hello;
    }

}