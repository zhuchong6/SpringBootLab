package com.zhu.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/21 22:07
 */
@Component
@FeignClient(value = "nacos-discovery", url = "localhost:9090", path = "/discovery")
public interface FeignDiscoveryClient {

    @GetMapping("/{name}")
    String hello(@PathVariable("name") String name);
}
