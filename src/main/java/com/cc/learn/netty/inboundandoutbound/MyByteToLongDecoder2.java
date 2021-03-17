package com.cc.learn.netty.inboundandoutbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Description
 *
 * @author wangchen
 * @createDate 2021/03/17
 */
public class MyByteToLongDecoder2 extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder2 被调用");
        //不判断是否大于8个字节，ReplayingDecoder内部会进行处理
        out.add(in.readLong());
    }
}
