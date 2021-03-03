package com.cc.learn.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Netty Client
 *
 * @author wangchen
 * @createDate 2021/03/03
 */
public class NettyClient {

    public static void main(String[] args) throws InterruptedException {
        //创建一个事件循环组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //创建客户端启动类,和服务端的不一样。服务端是ServerBootStrap
            Bootstrap bootstrap = new Bootstrap();
            //设置参数
            bootstrap.group(group)//设置事件循环组
                    .channel(NioSocketChannel.class)//设置客户端通道的实现类（反射）
                    .handler(new ChannelInitializer<SocketChannel>() {//设置处理器

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyClientHandler());//给管道添加处理器handler
                        }
                    });

            System.out.println("客户端 ok...");
            //启动客户端，连接服务器
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();
            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            //异常，关闭事件循环组
            group.shutdownGracefully();
        }


    }
}
