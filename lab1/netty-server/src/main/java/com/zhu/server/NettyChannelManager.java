package com.zhu.server;

import com.zhu.codec.Invocation;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/13 18:07
 */
@Component
public class NettyChannelManager {
    /**
     * {@link io.netty.channel.Channel#attr(AttributeKey)} 属性中，表示 Channel 对应的用户
     */
    private static final AttributeKey<String> CHANNEL_ATTR_KEY_USER = AttributeKey.newInstance("user");

    private Logger logger = LoggerFactory.getLogger(NettyServer.class);

    /**
     * Channel映射
     */
    private ConcurrentHashMap<ChannelId, Channel> channels = new ConcurrentHashMap<>();

    /**
     * 用户与Channel映射
     * <p>
     * 获取用户对应的Channel，可以指定用于发送消息
     */
    private ConcurrentHashMap<String, Channel> userChannels = new ConcurrentHashMap<>();

    /**
     * 添加Channel到 {@link #channels}中
     *
     * @param channel Channel
     */
    public void add(Channel channel) {
        channels.put(channel.id(), channel);
        logger.info("[add]|[一个连接({})加入]", channel.id());
    }

    /**
     * 添加指定用户到 {@link #userChannels} 中
     *
     * @param channel Channel
     * @param user    用户
     */
    public void addUser(Channel channel, String user) {
        Channel ifExistChannel = channels.get(channel.id());
        if (ifExistChannel == null) {
            logger.error("[addUser][连接({}) 不存在]", channel.id());
        }
        //设置属性
        channel.attr(CHANNEL_ATTR_KEY_USER).set(user);
        //添加到userChannels
        userChannels.put(user, channel);
    }

    /**
     * 将Channel从{@link #channels}和{@link #userChannels}中移除
     *
     * @param channel Channel
     */
    public void remove(Channel channel) {
        channels.remove(channel.id());
        if (channel.hasAttr(CHANNEL_ATTR_KEY_USER)) {
            userChannels.remove(channel.attr(CHANNEL_ATTR_KEY_USER).get());
        }
        logger.info("[remove][一个连接({})离开]", channel.id());
    }

    /**
     * 向指定用户发送消息
     *
     * @param user       用户
     * @param invocation 消息体
     */
    public void send(String user, Invocation invocation) {
        //获取用户对应的Channel
        Channel channel = userChannels.get(user);
        if (channel == null) {
            logger.error("[send][连接不存在]");
            return;
        }
        if (!channel.isActive()) {
            logger.error("[send][连接({})未激活]", channel.id());
            return;
        }
        //发送消息
        channel.writeAndFlush(invocation);
    }

    /**
     * 群发消息
     *
     * @param invocation 消息体
     */
    public void sendAll(Invocation invocation) {
        for (Channel channel : channels.values()) {
            if (!channel.isActive()) {
                logger.error("[send][连接({})未激活]", channel.id());
                return;
            }
            //发送消息
            channel.writeAndFlush(invocation);
        }
    }

}