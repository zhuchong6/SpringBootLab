package com.zhu.client.handler;

import com.zhu.client.NettyClient;
import com.zhu.codec.Invocation;
import com.zhu.message.heartbeat.HeartbeatRequest;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/15 21:33
 */
@Component
@ChannelHandler.Sharable
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(NettyClient.class);

    @Autowired
    @Lazy
    private NettyClient nettyClient;

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        nettyClient.reconnect();
        //继续触发事件
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("[exceptionCaught][连接({}) 发生异常]", ctx.channel().id(), cause);
        // 断开连接
        ctx.channel().close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //空闲时，向服务器发送一次心跳检测
        if (evt instanceof IdleStateEvent) {
            logger.info("[userEventTriggered][发起一次心跳]");
            HeartbeatRequest heartBeatRequest = new HeartbeatRequest();
            ctx.writeAndFlush(new Invocation(HeartbeatRequest.TYPE, heartBeatRequest.toString()))
                    .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}