package com.zhu.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/16 17:49
 */
public class InvocationEncoder extends MessageToByteEncoder<Invocation> {
    ObjectMapper objectMapper = new ObjectMapper();

    private Logger logger = LoggerFactory.getLogger(InvocationEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Invocation invocation, ByteBuf byteBuf) throws Exception {
        byte[] bytes = objectMapper.writeValueAsString(invocation).getBytes();
        //占用4个字节（即32bit）保存整数，所以在判断长度的时候要>4
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
        logger.info("[invocation encode][连接({}) 编码了一条消息({})]", channelHandlerContext.channel().id(), invocation.toString());
    }
}