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
public class HeartbeatResponseHandler implements MessageHandler<HeartbeatResponse> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(Channel channel, HeartbeatResponse message) {
        logger.info("[execute][收到连接({}) 的心跳响应]", channel.id());
    }

    @Override
    public String getType() {
        return HeartbeatResponse.TYPE;
    }
}