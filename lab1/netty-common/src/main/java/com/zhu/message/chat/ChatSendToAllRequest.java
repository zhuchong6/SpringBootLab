package com.zhu.message.chat;

import com.zhu.constant.MessageType;
import com.zhu.dispacher.Message;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/19 00:21
 */
public class ChatSendToAllRequest implements Message {

    public static final String TYPE = MessageType.CHAT_SEND_TO_ALL_REQUEST.name();

    /**
     * 群id
     */
    private String groupId;

    /**
     * 消息编号
     */
    private String msgId;
    /**
     * 内容
     */
    private String content;

    public String getGroupId() {
        return groupId;
    }

    public ChatSendToAllRequest setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getMsgId() {
        return msgId;
    }

    public ChatSendToAllRequest setMsgId(String msgId) {
        this.msgId = msgId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ChatSendToAllRequest setContent(String content) {
        this.content = content;
        return this;
    }
}