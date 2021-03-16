package com.cc.learn.netty.codec2;

import com.cc.learn.netty.codec.StudentPOJO;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * 服务器端处理器
 * <p>
 * 我们自定义的handler需要继承netty规定好的某个HandlerAdapter(规范)
 * 这时我们自定义的handler才能被netty使用
 *
 * @author wangchen
 * @createDate 2021/03/03
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

    //读取客户端发送的消息
    //1.ChannelHandlerContext ctx: 上下文对象，含有管道pipeline，通道channel,地址等信息
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
        if (msg.getDataType() == MyDataInfo.MyMessage.DataType.StudentType){
            System.out.println("客户端发送的消息： 学生名字： "+msg.getStudent().getName()+",id: "+msg.getStudent().getId());
        }else if (msg.getDataType() == MyDataInfo.MyMessage.DataType.WorkerType){
            System.out.println("客户端发送的消息： 工人名字： "+msg.getWorker().getName()+",age: "+msg.getWorker().getAge());
        }else {
            System.out.println("客户端发送的消息数据类型不明确。。。");
        }
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
        ctx.close();
    }
}
