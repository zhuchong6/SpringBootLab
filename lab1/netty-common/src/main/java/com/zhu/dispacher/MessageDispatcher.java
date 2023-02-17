package com.zhu.dispacher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhu.codec.Invocation;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/17 01:40
 */
@ChannelHandler.Sharable
public class MessageDispatcher extends SimpleChannelInboundHandler<Invocation> {

    @Autowired
    private MessageHandlerContainer messageHandlerContainer;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 为什么要创建线程池？
     * 因为在Netty线程模型中,如果是启用NIO模式（目前就是），EVENTLOOP会绑定多个Channel
     * 如果我们不此时，一个Channel中处理的事情耗时比较长，那么会影响到其他的Channel中数据的处理
     * 因此，开一个线程池处理一些耗时长的业务，不阻塞其他Channel的操作，提高了系统吞吐量
     */
    private final ExecutorService executor = Executors.newFixedThreadPool(200);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Invocation msg) throws Exception {
        //获得 type 对应的 MessageHandler 处理器
        MessageHandler messageHandler = messageHandlerContainer.getMessageHandler(msg.getType());
        // 获得  MessageHandler 处理器的消息类
        Class<? extends Message> messageClazz = MessageHandlerContainer.getMessageClazz(messageHandler);
        //解析消息,这里message是一个实现类
        Message message = objectMapper.readValue(msg.getMessage(), messageClazz);
        //子线程处理数据
        executor.submit(() -> {
            messageHandler.execute(ctx.channel(), message);
        });
    }
}