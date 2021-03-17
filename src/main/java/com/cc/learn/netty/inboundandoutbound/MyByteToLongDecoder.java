package com.cc.learn.netty.inboundandoutbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Description
 *
 * @author wangchen
 * @createDate 2021/03/17
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder 被调用");
        //判断是否大于8个字节
        //如果ByteBuf字节数远远超过8个字节，则会被调用很多次，相应的MyServerHandler也会被调用很多次。
        if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        }
    }
}
