package com.zhu.constant;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/18 23:31
 */
public enum MessageType {

    HEARTBEAT_REQUEST("心跳消息请求"),
    HEARTBEAT_RESPONSE("心跳消息返回"),

    AUTH_REQUEST("认证请求类型"),
    AUTH_RESPONSE("认证返回类型"),

    CHAT_REDIRECT_TO_USER_REQUEST("消息转发请求"),
    CHAT_SEND_TO_ONE_REQUEST("发送单人消息请求"),

    CHAT_SEND_RESPONSE("消息返回"),


    ;


    private String value;

    MessageType(String value) {
        this.value = value;
    }
}
