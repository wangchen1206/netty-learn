package com.cc.learn.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Netty Server
 *
 * @author wangchen
 * @createDate 2021/03/03
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        //创建bossGroup 和 workerGroup
        //说明：
        //1.创建两个线程组bossGroup和workerGroup
        //2.bossGroup只是处理连接请求，workerGroup真正处理业务
        //3.两个线程组都是无限循环
        //4.创建的两个线程组含有的子线程（NioEventLoop）的个数，默认是操作系统的cpu核数*2
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(2);

        try {
            //创建服务器端的启动对象并配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            //使用链式编程来配置
            bootstrap.group(bossGroup,workerGroup)//设置两个线程组
                    .channel(NioServerSocketChannel.class)//使用NioServerSocketChannel来作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG,128)//设置线程队列得到连接的个数（长度）
                    .childOption(ChannelOption.SO_KEEPALIVE,true)//设置保持活动连接状态
                    //.handler(null)//设置bossGroup的处理器,childHandler对应workerGroup
                    .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道初始化对象
                        //给pipeline设置处理器

                        @Override
                        protected void initChannel(SocketChannel ch) {
                            //这里可以将SocketChannel用一个集合管理起来，在主动向用户推送异步消息时，
                            // 可以将此类业务加入到各个channel对应的NioEventLoop的taskQueue和scheduleQueue中执行。
                            System.out.println("客户端socketChannel hashcode: "+ch.hashCode());
                            ch.pipeline().addLast(new NettyServerHandler());//将处理器添加到管道中。
                        }
                    });//给我们的workerGroup 的EventLoop设置处理器

            System.out.println("服务器 is ready...");
            //启动服务器，并绑定端口
            ChannelFuture channelFuture = bootstrap.bind(6668).sync();//sync表示异步运行

            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()){
                        System.out.println("监听bootStarp启动线程： "+Thread.currentThread().getName());
                        System.out.println("监听端口成功");
                    }else{
                        System.out.println("监听端口失败");
                    }
                }
            });

            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        }finally {
            //优雅地关闭两个线程组
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
