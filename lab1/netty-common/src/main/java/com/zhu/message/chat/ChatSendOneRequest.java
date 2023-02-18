package com.zhu.message.chat;

import com.zhu.constant.MessageType;
import com.zhu.dispacher.Message;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/18 22:21
 */
public class ChatSendOneRequest implements Message {

    public static final String TYPE = MessageType.CHAT_SEND_TO_ONE_REQUEST.name();

    /**
     * 当前要发信息的用户
     */
    private String fromUser;

    /**
     * 发送给用户
     */
    private String toUser;

    /**
     * 消息编号
     */
    private String msgId;

    /**
     * 内容
     */
    private String content;

    public String getToUser() {
        return toUser;
    }

    public ChatSendOneRequest setToUser(String toUser) {
        this.toUser = toUser;
        return this;
    }

    public String getMsgId() {
        return msgId;
    }

    public ChatSendOneRequest setMsgId(String msgId) {
        this.msgId = msgId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ChatSendOneRequest setContent(String content) {
        this.content = content;
        return this;
    }

    public String getFromUser() {
        return fromUser;
    }

    public ChatSendOneRequest setFromUser(String fromUser) {
        this.fromUser = fromUser;
        return this;
    }
}