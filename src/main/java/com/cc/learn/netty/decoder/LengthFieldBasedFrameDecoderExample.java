package com.cc.learn.netty.decoder;

import com.cc.learn.netty.protocoltcp.MyServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteOrder;

/**
 * Description
 *
 * @author wangchen
 * @createDate 2021/03/29
 */
public class LengthFieldBasedFrameDecoderExample {
//    //以下是LengthFieldBasedFrameDecoder解码器的参数 解释
//    // 长度字段的偏移量，也就是存放长度数据的起始位置
//    private final int lengthFieldOffset; 
//// 长度字段所占用的字节数
//    private final int lengthFieldLength; 
///*
// * 消息长度的修正值
// *
// * 在很多较为复杂一些的协议设计中，长度域不仅仅包含消息的长度，而且包含其他的数据，如版本号、数据类型、数据状态等，那么这时候我们需要使用 lengthAdjustment 进行修正
// * 
// * lengthAdjustment = 包体的长度值 - 长度域的值
// *
// */
//    private final int lengthAdjustment; 
//// 解码后需要跳过的初始字节数，也就是消息内容字段的起始位置
//    private final int initialBytesToStrip;
//// 长度字段结束的偏移量，lengthFieldEndOffset = lengthFieldOffset + lengthFieldLength
//    private final int lengthFieldEndOffset;
//    private final int maxFrameLength; // 报文最大限制长度
//    private final boolean failFast; // 是否立即抛出 TooLongFrameException，与 maxFrameLength 搭配使用
//    private boolean discardingTooLongFrame; // 是否处于丢弃模式
//    private long tooLongFrameLength; // 需要丢弃的字节数
//    private long bytesToDiscard; // 累计丢弃的字节数


    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            /* 按照以下顺序进行解码
                             * +---------------------------------------------------------------+
                            | 魔数 2byte | 协议版本号 1byte | 序列化算法 1byte | 报文类型 1byte  |
                            +---------------------------------------------------------------+
                            | 状态 1byte |        保留字段 4byte     |      数据长度 4byte     | 
                            +---------------------------------------------------------------+
                            |                   数据内容 （长度不定）                          |
                            +---------------------------------------------------------------+
                             **/
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(1000,10,4));
                        }
                    });//自定义服务端初始化器
            ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
