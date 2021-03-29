package com.cc.learn.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
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
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    //读取客户端发送的消息
    //1.ChannelHandlerContext ctx: 上下文对象，含有管道pipeline，通道channel,地址等信息
    //2.Object msg: 客户端发送的消息，默认Object格式，需要转换
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //比如我们在这里有个非常耗时长的任务，此时会阻塞。
        // 解决方案：异步执行-> 提交该耗时任务到该channel对应的NioEventLoop中的taskQueue中
        //异步执行
//        ctx.channel().eventLoop().execute(() -> {
//            try {
//                System.out.println("task1 线程： "+Thread.currentThread().getName());
//                Thread.sleep(5 * 1000);
//                ctx.writeAndFlush(Unpooled.copiedBuffer("1   模拟耗时任务。。。", CharsetUtil.UTF_8));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//
//        ctx.channel().eventLoop().execute(() -> {
//            try {
//                System.out.println("task2 线程： "+Thread.currentThread().getName());
//                Thread.sleep(5 * 1000);
//                ctx.writeAndFlush(Unpooled.copiedBuffer("2   模拟耗时任务。。。", CharsetUtil.UTF_8));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//
//        ctx.channel().eventLoop().schedule(() -> {
//            try {
//                System.out.println("task3 线程： "+Thread.currentThread().getName());
//                Thread.sleep(5 * 1000);
//                ctx.writeAndFlush(Unpooled.copiedBuffer("3   模拟耗时任务。。。", CharsetUtil.UTF_8));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        },5, TimeUnit.SECONDS);
        System.out.println("test...");
        throw new RuntimeException("Test Exception");
//        ctx.writeAndFlush(Unpooled.copiedBuffer("任务。。。", CharsetUtil.UTF_8));
//
//        System.out.println("go on ...");

//        System.out.println("当前线程： "+Thread.currentThread().getName());
//        Channel channel = ctx.channel();
//        ChannelPipeline pipeline = ctx.pipeline();
//        System.out.println("server context = " + ctx);
//        //将msg转换成一个ByteBuf,是netty提供的ByteBuf.
//        ByteBuf byteBuf = (ByteBuf)msg;
//        //获取消息
//        System.out.println("客户端发送的消息： "+byteBuf.toString(CharsetUtil.UTF_8));
//        System.out.println("客户端地址： "+ channel.remoteAddress());
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
        //如果添加了专门的ExceptionHandler,则所有自定义的Handler的exceptionCaught方法必须执行下边的方法，最终交给ExceptionHandler执行
        ctx.fireExceptionCaught(cause);
    }
}
