package com.cc.learn.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Netty服务端
 *
 * @author wangchen
 * @createDate 2021/01/18
 */
public class NettyServer {

    public static void main(String[] args) {
        //用于监听客户端请求
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        //处理每条连接的数据读写
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //最小化的参数配置：配置线程组，IO模型，逻辑处理，绑定端口
        serverBootstrap.group(bossGroup,workerGroup)//配置两个线程组的角色
                .channel(NioServerSocketChannel.class)//配置服务端的IO模型
                .childHandler(new ChannelInitializer<NioSocketChannel>() {//配置每条连接的数据读写和业务处理
                    protected void initChannel(NioSocketChannel ch) throws Exception {

                    }
                })
                .bind(8000);//绑定监听端口
    }
}
