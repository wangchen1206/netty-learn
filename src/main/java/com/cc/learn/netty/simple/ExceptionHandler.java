package com.cc.learn.netty.simple;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * 定义通用异常
 * 如果有跟handler相关的异常，则可以在handler的exceptionCaught方法里处理异常。
 *
 * @author wangchen
 * @createDate 2021/03/26
 */
public class ExceptionHandler extends ChannelDuplexHandler {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof RuntimeException){
            System.out.println("Handler Business Exception Success");
        }
    }
}
