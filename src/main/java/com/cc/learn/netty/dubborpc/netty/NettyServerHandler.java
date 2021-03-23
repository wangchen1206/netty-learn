package com.cc.learn.netty.dubborpc.netty;

import com.cc.learn.netty.dubborpc.customer.ClientBootStrap;
import com.cc.learn.netty.dubborpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Description
 *
 * @author wangchen
 * @createDate 2021/03/23
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String msgString = msg.toString();
        System.out.println("msg= "+ msgString);

        //定义一个规则，消息格式必须以 "HelloService#hello#"开头
        if (msgString.startsWith(ClientBootStrap.providerName)){
            String result = msgString.substring(msgString.lastIndexOf("#") + 1);
            String hello = new HelloServiceImpl().hello(result);
            ctx.writeAndFlush(hello);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
