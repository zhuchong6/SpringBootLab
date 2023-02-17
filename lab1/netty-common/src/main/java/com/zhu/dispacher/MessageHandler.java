package com.zhu.dispacher;

import io.netty.channel.Channel;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/17 01:09
 */
public interface MessageHandler<T extends Message> {

    /**
     * 执行处理消息
     *
     * @param channel 通道
     * @param message 消息内容
     */
    void execute(Channel channel, T message);

    /**
     * @return 消息类型，即Message实现类上的TYPE静态字段
     */
    String getType();
}