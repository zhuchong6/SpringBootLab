package com.zhu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/20 23:38
 */
@RestController
@RequestMapping("/discovery")
public class HelloController {

    @GetMapping("/{string}")
    public String hello(@PathVariable String string) {
        return "hello nacos discovery" + string;
    }
}