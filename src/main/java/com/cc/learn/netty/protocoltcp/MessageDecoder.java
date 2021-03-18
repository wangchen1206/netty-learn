package com.cc.learn.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Description
 *
 * @author wangchen
 * @createDate 2021/03/18
 */
public class MessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MessageDecoder被调用");
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        //封装成MessageProtocol对象，放入out中，传递给下一个handler处理
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLength(length);
        messageProtocol.setContent(bytes);
        out.add(messageProtocol);
    }
}
