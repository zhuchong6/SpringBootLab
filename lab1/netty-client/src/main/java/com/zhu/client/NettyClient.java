package com.zhu.client;

import com.zhu.client.handler.NettyClientHandlerInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/15 21:27
 */
@Component
public class NettyClient {
    private Logger logger = LoggerFactory.getLogger(NettyClient.class);
    /**
     * 重连间隔20s
     */
    private static final Integer RECONNECT_DURATION = 20;


    @Value("${netty.server.host}")
    private String nettyServerHost;
    @Value("${netty.server.port}")
    private Integer nettyServerPort;

    /**
     * 线程组，用于客户端对服务端的连接、数据读写
     */
    private EventLoopGroup eventGroup = new NioEventLoopGroup();
    /**
     * Netty Client Channel
     */
    private Channel channel;

    @Autowired
    private NettyClientHandlerInitializer nettyClientHandlerInitializer;


    @PostConstruct
    public void start() throws Exception {
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(eventGroup)
                //执行Channel类型是NioSocketChannel
                .channel(NioSocketChannel.class)
                //设置服务器端的地址和端口
                .remoteAddress(nettyServerHost, nettyServerPort)
                //TCP Keepalive 机制，实现 TCP 层级的心跳保活功能
                .option(ChannelOption.SO_KEEPALIVE, true)
                //允许较小的数据包的发送，降低延迟
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(nettyClientHandlerInitializer);

        //连接服务器，并异步等待客户端启动成功，并设置重试功能
        bootstrap.connect().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                //连接失败
                if (!channelFuture.isSuccess()) {
                    logger.error("[start][Netty Client 连接服务器({}:{}) 失败]", nettyServerHost, nettyServerPort);
                    reconnect();
                    return;
                }
                channel = channelFuture.channel();
                logger.info("[start][Netty Client 连接服务器({}:{}) 成功]", nettyServerHost, nettyServerPort);
            }
        });

    }

    public void reconnect() {
        eventGroup.schedule(() -> {
            logger.info("[reconnect][开始重连]");
            try {
                start();
            } catch (Exception e) {
                logger.error("[reconnect][重连失败]", e);
            }
        }, RECONNECT_DURATION, TimeUnit.SECONDS);
        logger.info("[reconnect][{} 秒后将发起重连]", RECONNECT_DURATION);
    }


    @PreDestroy
    public void shutdown() {
        if (channel != null) {
            channel.close();
        }
        eventGroup.shutdownGracefully();
    }

}