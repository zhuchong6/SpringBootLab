package com.zhu.messagehandler.chat;

import com.zhu.codec.Invocation;
import com.zhu.dispacher.MessageHandler;
import com.zhu.message.chat.ChatRedirectToUserRequest;
import com.zhu.message.chat.ChatSendResponse;
import com.zhu.message.chat.ChatSendToAllRequest;
import com.zhu.server.NettyChannelManager;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/19 00:24
 */
@Component
public class ChatSendAllHandler implements MessageHandler<ChatSendToAllRequest> {

    @Autowired
    private NettyChannelManager nettyChannelManager;

    @Override
    public void execute(Channel channel, ChatSendToAllRequest message) {
        //处理消息。。。

        //处理完成返回给客户端
        ChatSendResponse chatSendResponse = new ChatSendResponse()
                .setCode(0)
                .setMessage(message.getContent());
        channel.writeAndFlush(new Invocation(ChatSendResponse.TYPE, chatSendResponse));

        //创建转发的消息，并广播发送
        ChatRedirectToUserRequest chatRedirectToUserRequest = new ChatRedirectToUserRequest()
                .setMsgId(message.getMsgId())
                .setContent(message.getContent())
                .setToUser(message.getGroupId());
        nettyChannelManager.sendAll(new Invocation(ChatRedirectToUserRequest.TYPE, chatRedirectToUserRequest));
    }

    @Override
    public String getType() {
        return ChatSendToAllRequest.TYPE;
    }
}