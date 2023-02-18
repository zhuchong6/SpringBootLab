package com.zhu.messagehandler.chat;

import com.zhu.dispacher.MessageHandler;
import com.zhu.message.chat.ChatSendResponse;
import com.zhu.utils.JSONUtils;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/18 22:56
 */
@Component
public class ChatSendResponseHandler implements MessageHandler<ChatSendResponse> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(Channel channel, ChatSendResponse message) {
        //后续会有其他处理，一般push给消息队列处理
        logger.info("[execute][发送结果：{}]", JSONUtils.toJsonString(message));
    }

    @Override
    public String getType() {
        return ChatSendResponse.TYPE;
    }
}