package com.zhu.message.chat;

import com.zhu.constant.MessageType;
import com.zhu.dispacher.Message;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/18 22:41
 */
public class ChatRedirectToUserRequest implements Message {
    public static final String TYPE = MessageType.CHAT_REDIRECT_TO_USER_REQUEST.name();

    /**
     * 来自谁的消息
     */
    private String fromUser;

    /**
     * 消息编号
     */
    private String msgId;
    /**
     * 内容
     */
    private String content;


    public String getFromUser() {
        return fromUser;
    }

    public ChatRedirectToUserRequest setFromUser(String fromUser) {
        this.fromUser = fromUser;
        return this;
    }

    public String getMsgId() {
        return msgId;
    }

    public ChatRedirectToUserRequest setMsgId(String msgId) {
        this.msgId = msgId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ChatRedirectToUserRequest setContent(String content) {
        this.content = content;
        return this;
    }
}