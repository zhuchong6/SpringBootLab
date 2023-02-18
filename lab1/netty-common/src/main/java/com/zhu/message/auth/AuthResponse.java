package com.zhu.message.auth;

import com.zhu.constant.MessageType;
import com.zhu.dispacher.Message;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/17 15:31
 */
public class AuthResponse implements Message {
    public static final String TYPE = MessageType.AUTH_RESPONSE.name();

    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应提示
     */
    private String message;

    public Integer getCode() {
        return code;
    }

    public AuthResponse setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public AuthResponse setMessage(String message) {
        this.message = message;
        return this;
    }
}