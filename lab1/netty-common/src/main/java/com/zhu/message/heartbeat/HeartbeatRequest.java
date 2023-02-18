package com.zhu.message.heartbeat;

import com.zhu.constant.MessageType;
import com.zhu.dispacher.Message;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/17 02:11
 */
public class HeartbeatRequest implements Message {
    /**
     * 类型 - 心跳请求
     */
    public static final String TYPE = MessageType.HEARTBEAT_REQUEST.name();

    @Override
    public String toString() {
        return "{}";
    }
}