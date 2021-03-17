package com.cc.learn.netty.inboundandoutbound;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Description
 *
 * @author wangchen
 * @createDate 2021/03/17
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("服务端地址： "+ctx.channel().remoteAddress());
        System.out.println("接收服务端消息： "+msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler被调用");
        ctx.channel().writeAndFlush(123456L);
        //如果发送的数据类型不是编码器指定的类型，则不会经过编码器处理
//        ctx.channel().writeAndFlush(Unpooled.copiedBuffer("abcdabcdabcdabcd", CharsetUtil.UTF_8));
    }
}
