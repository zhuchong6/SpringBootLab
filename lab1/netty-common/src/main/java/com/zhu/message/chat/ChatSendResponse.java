package com.zhu.message.chat;

import com.zhu.constant.MessageType;
import com.zhu.dispacher.Message;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/18 22:34
 */
public class ChatSendResponse implements Message {
    public static final String TYPE = MessageType.CHAT_SEND_RESPONSE.name();

    /**
     * 消息编号
     */
    private String msgId;
    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应提示
     */
    private String message;

    public String getMsgId() {
        return msgId;
    }

    public ChatSendResponse setMsgId(String msgId) {
        this.msgId = msgId;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public ChatSendResponse setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ChatSendResponse setMessage(String message) {
        this.message = message;
        return this;
    }
}