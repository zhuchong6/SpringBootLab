package com.zhu.client.handler;

import com.zhu.codec.InvocationDecoder;
import com.zhu.codec.InvocationEncoder;
import com.zhu.dispacher.MessageDispatcher;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/15 21:43
 */
@Component
public class NettyClientHandlerInitializer extends ChannelInitializer<Channel> {

    @Autowired
    private NettyClientHandler nettyClientHandler;

    @Autowired
    private MessageDispatcher messageDispatcher;

    /**
     * 心跳超时时间60s
     */
    private static final Integer READ_TIMEOUT_SECONDS = 60;

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                .addLast(new IdleStateHandler(READ_TIMEOUT_SECONDS, 0, 0))
                .addLast(new ReadTimeoutHandler(READ_TIMEOUT_SECONDS * 3, TimeUnit.SECONDS))
                .addLast(new InvocationEncoder())
                //解码器
                .addLast(new InvocationDecoder())
                //消息分发器
                .addLast(messageDispatcher)
                //客户端处理消息
                .addLast(nettyClientHandler);
    }
}