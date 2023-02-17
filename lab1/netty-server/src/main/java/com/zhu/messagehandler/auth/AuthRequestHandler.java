package com.zhu.messagehandler.auth;

import com.zhu.codec.Invocation;
import com.zhu.dispacher.MessageHandler;
import com.zhu.message.auth.AuthRequest;
import com.zhu.message.auth.AuthResponse;
import com.zhu.server.NettyChannelManager;
import com.zhu.utils.JSONUtils;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/17 15:37
 */
@Component
public class AuthRequestHandler implements MessageHandler<AuthRequest> {

    @Autowired
    private NettyChannelManager nettyChannelManager;

    @Override
    public void execute(Channel channel, AuthRequest authRequest) {
        //如果没有携带token
        if (!StringUtils.hasText(authRequest.getAccessToken())) {
            AuthResponse authResponse = new AuthResponse().setCode(1).setMessage("认证token未传入");
            String requestString = JSONUtils.toJsonString(authResponse);
            channel.writeAndFlush(new Invocation(AuthResponse.TYPE, requestString));
            return;
        }

        //这里是验证token有效性，这里暂时省略，假装已经验证通过

        //用户和Channel绑定,这里简单使用accessToken作为User
        nettyChannelManager.addUser(channel, authRequest.getAccessToken());

        //响应认证
        AuthResponse authResponse = new AuthResponse().setCode(0);
        String responseString = JSONUtils.toJsonString(authResponse);
        channel.writeAndFlush(new Invocation(AuthResponse.TYPE, responseString));
    }

    @Override
    public String getType() {
        return AuthRequest.TYPE;
    }
}