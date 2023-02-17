package com.zhu.messagehandler.heartbeat;

import com.zhu.codec.Invocation;
import com.zhu.dispacher.MessageHandler;
import com.zhu.message.heartbeat.HeartbeatRequest;
import com.zhu.message.heartbeat.HeartbeatResponse;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/17 02:09
 */
@Component
public class HeartbeatRequestHandler implements MessageHandler<HeartbeatRequest> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(Channel channel, HeartbeatRequest message) {
        logger.info("[execute][收到连接({}) 的心跳请求]", channel.id());
        //响应心跳
        HeartbeatResponse response = new HeartbeatResponse();
        channel.writeAndFlush(new Invocation(HeartbeatResponse.TYPE, response.toString()));
    }

    @Override
    public String getType() {
        return HeartbeatRequest.TYPE;
    }
}