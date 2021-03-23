package com.cc.learn.netty.dubborpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * Description
 *
 * @author wangchen
 * @createDate 2021/03/23
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable<String> {

    private ChannelHandlerContext context;
    private String result;
    private String param;



    //1
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(" channelActive 被调用  ");
        context = ctx;//其他方法要使用
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    //4
    //方法上要加上同步关键字
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(Thread.currentThread().getName()+" channelRead 被调用  ");
        result = msg.toString();//接收服务端返回得对象
        notify();//唤醒call在等待得线程
    }

    //3->4->3
    //方法上要加上同步关键字
    @Override
    public synchronized String call() throws Exception {
        //给服务端发送消息进行调用服务程序api
        System.out.println(Thread.currentThread().getName()+" call1 被调用  ");
        context.writeAndFlush(param);
        //等待被唤醒
        wait();
        System.out.println(Thread.currentThread().getName()+" call2 被调用  ");
        //唤醒后返回channelRead0接收的结果
        return result;
    }

    //2
    public void setParam(String param){
        this.param = param;
    }
}
