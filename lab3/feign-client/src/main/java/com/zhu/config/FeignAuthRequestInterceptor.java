package com.zhu.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.UUID;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/22 00:14
 */
public class FeignAuthRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 业务逻辑
        String access_token = UUID.randomUUID().toString();
        requestTemplate.header("Authorization", access_token);

        String tranceId = "1";
        requestTemplate.header("tranceId", tranceId);
    }
}