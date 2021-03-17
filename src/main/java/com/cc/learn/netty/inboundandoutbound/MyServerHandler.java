package com.cc.learn.netty.inboundandoutbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Description
 *
 * @author wangchen
 * @createDate 2021/03/17
 */
public class MyServerHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("MyServerHandler被调用");
        System.out.println("客户端发送的Long: "+msg);
        ctx.channel().writeAndFlush(11111L);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
