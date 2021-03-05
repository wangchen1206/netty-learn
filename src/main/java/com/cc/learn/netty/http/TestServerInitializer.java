package com.cc.learn.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * work Group 添加处理器
 *
 * @author wangchen
 * @createDate 2021/03/04
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    //给管道添加处理器
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //获取管道
        ChannelPipeline pipeline = ch.pipeline();
        //给管道添加一个HttpServerCodec codec->[coder,decoder]
        //HttpServerCodec是一个netty提供的http server 编码解码器
        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());
        //添加自定义处理器
        pipeline.addLast("MyTestHttpServerHandler",new TestHttpServerHandler());

        System.out.println("initChannel ok...");
    }
}
