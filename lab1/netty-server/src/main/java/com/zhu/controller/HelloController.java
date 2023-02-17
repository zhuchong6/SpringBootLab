package com.zhu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/12 01:51
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello netty server";
    }
}