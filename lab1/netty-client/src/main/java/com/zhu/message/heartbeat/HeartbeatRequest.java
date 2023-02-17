package com.zhu.message.heartbeat;

import com.zhu.dispacher.Message;

/**
 * @author by zhuhcong
 * @descr 心跳请求
 * @date 2023/2/15 21:55
 */
public class HeartbeatRequest implements Message {

    public static final String TYPE = "HEARTBEAT_REQUEST";


    @Override
    public String toString() {
        return "{}";
    }
}