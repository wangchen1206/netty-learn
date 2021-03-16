package com.cc.learn.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Description
 *
 * @author wangchen
 * @createDate 2021/03/03
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    //通道就绪，就会触发该动作
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client: "+ctx);
        //发送消息
        StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(1).setName("及时雨-宋江").build();
        ctx.writeAndFlush(student);
    }

    //当通道有读事件，就会触发
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //转换
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务端发送的消息： "+byteBuf.toString(CharsetUtil.UTF_8));
    }

    //异常发生时
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
