package com.zhu.config;

import com.zhu.dispacher.MessageDispatcher;
import com.zhu.dispacher.MessageHandlerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/17 02:03
 */
@Configuration
public class NettyClientConfig {
    @Bean
    public MessageDispatcher messageDispatcher() {
        return new MessageDispatcher();
    }

    @Bean
    public MessageHandlerContainer messageHandlerContainer() {
        return new MessageHandlerContainer();
    }

}