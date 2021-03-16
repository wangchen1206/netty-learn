package com.cc.learn.netty.codec;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * 服务器端处理器
 * <p>
 * 我们自定义的handler需要继承netty规定好的某个HandlerAdapter(规范)
 * 这时我们自定义的handler才能被netty使用
 *
 * @author wangchen
 * @createDate 2021/03/03
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<StudentPOJO.Student> {

    //读取客户端发送的消息
    //1.ChannelHandlerContext ctx: 上下文对象，含有管道pipeline，通道channel,地址等信息
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, StudentPOJO.Student msg) throws Exception {
        System.out.println("服务端收到消息： id="+msg.getId()+",name="+msg.getName());
    }

    //数据读取完毕后进行的操作
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //写入并刷新，将消息写入缓存，并发送到通道
        //我们要对发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端~", CharsetUtil.UTF_8));
    }

    //处理异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
