package com.zhu.codec;

import com.zhu.utils.JSONUtils;

/**
 * @author by zhuhcong
 * @descr 消息体
 * @date 2023/2/13 19:07
 */

public class Invocation {

    /**
     * 类型
     */
    private String type;

    /**
     * 消息，简单JSON格式，后续可以自定义拓展
     */
    private String message;

    public Invocation() {
    }

    public Invocation(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public Invocation(String type, Object message) {
        this.type = type;
        this.message = JSONUtils.toJsonString(message);
    }

    public String getType() {
        return type;
    }

    public Invocation setType(String type) {
        this.type = type;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Invocation setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "Invocation{" +
                "type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}