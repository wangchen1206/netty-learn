package com.cc.learn.netty.inboundandoutbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Description
 *
 * @author wangchen
 * @createDate 2021/03/17
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //添加入站的解码器 MyByteToLongDecoder
//        pipeline.addLast(new MyByteToLongDecoder());
        pipeline.addLast(new MyByteToLongDecoder2());
        //添加出站的编码器
        pipeline.addLast(new MyLongToByteEncoder());
        //添加自定义业务处理器
        pipeline.addLast(new MyClientHandler());
    }
}
