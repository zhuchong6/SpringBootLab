package com.zhu.messagehandler.auth;

import com.zhu.dispacher.MessageHandler;
import com.zhu.message.auth.AuthResponse;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/17 15:54
 */
@Component
public class AuthResponseHandler implements MessageHandler<AuthResponse> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(Channel channel, AuthResponse message) {
        logger.info("[execute][认证结果：{}]", message);
    }

    @Override
    public String getType() {
        return AuthResponse.TYPE;
    }
}