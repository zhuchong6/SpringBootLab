package com.zhu.server;

import com.zhu.server.handler.NettyServerHandlerInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/12 01:56
 */
@Component
public class NettyServer {
    private Logger logger = LoggerFactory.getLogger(NettyServer.class);

    /**
     * netty 监听的端点
     */
    @Value("${netty.port}")
    private Integer port;

    @Autowired
    private NettyServerHandlerInitializer nettyServerHandlerInitializer;

    /**
     * boss线程组，用于接收客户端的连接
     */
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    /**
     * worker线程组，用于服务端接收客户端的读写
     */
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    /**
     * netty server channel
     */
    private Channel channel;

    @PostConstruct
    public void start() throws InterruptedException {

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //设置serverBootstrap的启动参数
        serverBootstrap.group(bossGroup, workerGroup)
                //设置Channel类型为NioServerSocketChannel
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                //服务端accept队列的大小
                .option(ChannelOption.SO_BACKLOG, 1024)
                //TCP Keepalive 机制，实现 TCP 层级的心跳保活功能
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                //允许较小的数据包的发送，降低延迟
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(nettyServerHandlerInitializer);

        ChannelFuture future = serverBootstrap.bind().sync();
        if (future.isSuccess()) {
            channel = future.channel();
            logger.info("netty server start in port[{}]", port);
        }

    }

    @PreDestroy
    public void shutdown() {
        if (channel != null) {
            channel.close();
        }
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}