package com.zhu.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/22 00:35
 */
@Component
@FeignClient(name = "nacos-discovery01")
public interface NacosServiceFeign {

    @GetMapping("/discovery/{name}")
    String hello(@PathVariable("name") String name);
}
