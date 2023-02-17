package com.zhu.controller;

import com.zhu.client.NettyClient;
import com.zhu.codec.Invocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/17 15:55
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private NettyClient nettyClient;


    @PostMapping("/mock")
    public String mock(String type, String message) {
        Invocation invocation = new Invocation(type, message);
        nettyClient.send(invocation);
        return "success";
    }
}