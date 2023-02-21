package com.zhu.config;

import feign.Feign;
import feign.Logger;
import feign.Request;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author by zhuhcong
 * @descr feign全局配置
 * @date 2023/2/21 22:29
 */
@Configuration
public class FeignConfiguration {

    /**
     * 自定义日志级别
     *
     * @return
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * 自定义拦截器
     *
     * @return
     */
    @Bean
    public FeignAuthRequestInterceptor feignAuthRequestInterceptor() {
        return new FeignAuthRequestInterceptor();
    }

    /**
     * 超时事件配置
     * 5000-代表连接超时5s
     * 10000-代表请求处理超时事件是10s
     *
     * @return
     */
    @Bean
    public Request.Options options() {
        return new Request.Options(5000, 1000);
    }


}