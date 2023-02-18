package com.zhu.messagehandler.chat;

import com.zhu.codec.Invocation;
import com.zhu.dispacher.MessageHandler;
import com.zhu.message.chat.ChatRedirectToUserRequest;
import com.zhu.message.chat.ChatSendOneRequest;
import com.zhu.message.chat.ChatSendResponse;
import com.zhu.server.NettyChannelManager;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/18 22:42
 */
@Component
public class ChatSendOneHandler implements MessageHandler<ChatSendOneRequest> {

    @Autowired
    private NettyChannelManager nettyChannelManager;

    @Override
    public void execute(Channel channel, ChatSendOneRequest message) {
        //立即返回成功结果给客户端
        ChatSendResponse chatSendResponse = new ChatSendResponse().setCode(0).setMsgId(message.getMsgId());
        channel.writeAndFlush(new Invocation(ChatSendResponse.TYPE, chatSendResponse));

        //将消息转发给指定用户
        ChatRedirectToUserRequest chatRedirectToUserRequest = new ChatRedirectToUserRequest()
                .setMsgId(message.getMsgId())
                .setFromUser(message.getFromUser())
                .setToUser(message.getToUser())
                .setContent(message.getContent());
        nettyChannelManager.send(message.getToUser(),
                new Invocation(ChatRedirectToUserRequest.TYPE, chatRedirectToUserRequest));
    }

    @Override
    public String getType() {
        return ChatSendOneRequest.TYPE;
    }
}