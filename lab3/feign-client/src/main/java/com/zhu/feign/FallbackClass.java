package com.zhu.feign;

import org.springframework.cloud.client.circuitbreaker.NoFallbackAvailableException;
import org.springframework.stereotype.Component;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/22 01:22
 */
@Component
public class FallbackClass implements NacosCircuitbreakerServiceFeign {
    @Override
    public String hello(String name) {
        throw new NoFallbackAvailableException("服务调用失败", new RuntimeException());
    }

    @Override
    public String getException() {
        return "失败";
    }
}