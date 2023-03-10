package com.zhu.message.heartbeat;

import com.zhu.constant.MessageType;
import com.zhu.dispacher.Message;

/**
 * @author by zhuhcong
 * @descr 消息，心跳响应信息
 * @date 2023/2/17 02:12
 */
public class HeartbeatResponse implements Message {
    /**
     * 类型 - 心跳响应
     */
    public static final String TYPE = MessageType.HEARTBEAT_RESPONSE.name();

    @Override
    public String toString() {
        return "{}";
    }
}