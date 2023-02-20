package com.zhu.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/20 01:17
 */
@RestController
@RequestMapping("/nacos")
@RefreshScope
public class NacosConfigTestController {

    @Value("${user.name}")
    private String userName;

    @Value("${user.age}")
    private String userAge;

    @Value("${user.type}")
    private String userType;

    @Value("${user.share}")
    private String userShare;

    @GetMapping("/hello")
    public String hello() {
        return "1231";
    }

    @RequestMapping("/get")
    public String get() {
        return userName + " : " + userAge + " : " + userType + " : " + userShare;
    }
}