package com.zhu.feign;

import org.springframework.stereotype.Component;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/22 01:54
 */

public class FallbackFactory implements org.springframework.cloud.openfeign.FallbackFactory<FallbackClass> {
    @Override
    public FallbackClass create(Throwable cause) {
        return new FallbackClass();
    }
}