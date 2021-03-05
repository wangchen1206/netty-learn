package com.cc.learn.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * 自定义处理器
 * SimpleChannelInboundHandler是 ChannelInboundHandlerAdapter的子类
 * HttpObject是客户端和服务端交互的数据类型
 *
 * @author wangchen
 * @createDate 2021/03/04
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    //channel读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        System.out.println("ctx.channel()+ "+ctx.channel()+"--- pipeline.channel() "+ctx.pipeline().channel()+" --- ctx.pipeline() "+ctx.pipeline());

        System.out.println("pipeline hash: "+ctx.pipeline().hashCode()+" --- handler hash: "+this.hashCode());
        //判断当前msg是不是HttpRequest类型
        if (msg instanceof HttpRequest) {
            System.out.println("msg类型： " + msg.getClass());
            System.out.println("客户端地址： " + ctx.channel().remoteAddress());

            //过滤特定请求
            HttpRequest request = (HttpRequest) msg;
            //回复信息个客户端
            ByteBuf content = null;
            DefaultHttpResponse response = null;

            if ("/favicon.ico".equals(request.uri())) {
                System.out.println("请求了favicon.ico，不做相应");
                //回复信息个客户端
                content = Unpooled.copiedBuffer("请求了favicon.ico,没有资源...", CharsetUtil.UTF_8);
            } else {
                content = Unpooled.copiedBuffer("hello,我是服务器~", CharsetUtil.UTF_8);
                //构造http响应
            }
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain")
                    .set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            //返回相应给客户端
            ctx.writeAndFlush(response);
        }
    }
}
