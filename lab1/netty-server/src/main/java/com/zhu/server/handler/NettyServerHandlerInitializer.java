package com.zhu.server.handler;

import com.zhu.codec.InvocationDecoder;
import com.zhu.codec.InvocationEncoder;
import com.zhu.dispacher.MessageDispatcher;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/12 22:59
 */
@Component
public class NettyServerHandlerInitializer extends ChannelInitializer {

    /**
     * 心跳超时时间
     */
    private static final Integer READ_TIMEOUT_SECONDS = 3 * 60;

    @Autowired
    private NettyServerHandler nettyServerHandler;

    @Autowired
    private MessageDispatcher messageDispatcher;

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new ReadTimeoutHandler(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS))
                //编码器
                .addLast(new InvocationEncoder())
                //解码器
                .addLast(new InvocationDecoder())
                //消息分发器
                .addLast(messageDispatcher)
                //服务端处理器
                .addLast(nettyServerHandler);
    }
}