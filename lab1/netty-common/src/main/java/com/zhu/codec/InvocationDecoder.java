package com.zhu.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/16 17:49
 */
public class InvocationDecoder extends ByteToMessageDecoder {
    ObjectMapper objectMapper = new ObjectMapper();

    private Logger logger = LoggerFactory.getLogger(InvocationDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list)
            throws Exception {
        //标记当前读取位置
        byteBuf.markReaderIndex();
        //判断是否能读取length长度
        //占用4个字节（即32bit）保存整数，所以在判断长度的时候要>4
        if (byteBuf.readableBytes() <= 4) {
            return;
        }
        //读取长度，
        int length = byteBuf.readInt();
        if (length < 0) {
            throw new CorruptedFrameException("长度不正确：" + length);
        }
        //如果message不够读取的，则退回到原读取位置
        if (byteBuf.readableBytes() < length) {
            byteBuf.resetReaderIndex();
            return;
        }
        //读取内容
        byte[] content = new byte[length];
        byteBuf.readBytes(content);
        //解析成Invocation
        Invocation invocation = objectMapper.readValue(content, Invocation.class);
        list.add(invocation);
        logger.info("[invocation decode][连接({}) 解析到一条消息({})]", channelHandlerContext.channel().id(), invocation.toString());

    }
}