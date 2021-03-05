package com.cc.learn.netty.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Description
 *
 * @author wangchen
 * @createDate 2021/03/05
 */
public class GroupChatServer {
    private final int port;

    public GroupChatServer(int port){
        this.port = port;
    }

    public void run() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //添加StringDecoder
                            pipeline.addLast("decoder",new StringDecoder());
                            //添加StringEncoder
                            pipeline.addLast("encoder",new StringEncoder());
                            //添加自定义处理器
                            pipeline.addLast("myHandler",new GroupChatServerHandler());
                        }
                    });
            System.out.println("Netty Group Chat Server is ok...");
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            //监听通道关闭
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new GroupChatServer(7000).run();
    }
}
