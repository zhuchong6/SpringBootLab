package com.zhu.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author by zhuhcong
 * @descr 带有熔断机制的feign
 * @date 2023/2/22 01:04
 */
@Component
@FeignClient(name = "nacos-discovery01", fallbackFactory = FallbackFactory.class)
public interface NacosCircuitbreakerServiceFeign {
    @GetMapping("/discovery/{name}")
    String hello(@PathVariable("name") String name);


    @GetMapping("/discovery2/{name}")
    String getException();
}
