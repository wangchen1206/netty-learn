package com.cc.learn.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * Description
 *
 * @author wangchen
 * @createDate 2021/03/18
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        System.out.println("服务端收到消息长度： "+msg.getLength());
        System.out.println("服务端收到消息内容： "+new String(msg.getContent(),CharsetUtil.UTF_8));
        System.out.println("服务端收到消息次数： "+(++this.count));

        //给客户端回复消息
        String uuid = UUID.randomUUID().toString();
        byte[] content = uuid.getBytes(CharsetUtil.UTF_8);
        int length = content.length;
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLength(length);
        messageProtocol.setContent(content);
        ctx.writeAndFlush(messageProtocol);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
