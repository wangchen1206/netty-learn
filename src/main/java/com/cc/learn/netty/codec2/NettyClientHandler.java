package com.cc.learn.netty.codec2;

import com.cc.learn.netty.codec.StudentPOJO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Random;

/**
 * Description
 *
 * @author wangchen
 * @createDate 2021/03/03
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    //通道就绪，就会触发该动作
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client: " + ctx);
        //发送消息
        int random = new Random().nextInt(3);
        System.out.println("random: " + random);
        MyDataInfo.MyMessage myMessage;
        if (0 == random) {
            myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.StudentType)
                    .setStudent(MyDataInfo.Student.newBuilder().setId(1).setName("CC").build()).build();
        } else {
            myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.WorkerType)
                    .setWorker(MyDataInfo.Worker.newBuilder().setAge(19).setName("QQ").build()).build();
        }
        ctx.writeAndFlush(myMessage);
    }

    //当通道有读事件，就会触发
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //转换
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务端发送的消息： " + byteBuf.toString(CharsetUtil.UTF_8));
    }

    //异常发生时
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
