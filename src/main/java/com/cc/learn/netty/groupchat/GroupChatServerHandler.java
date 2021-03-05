package com.cc.learn.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description
 *
 * @author wangchen
 * @createDate 2021/03/05
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    //定义一个channel组，管理所有channel
    //GlobalEventExecutor.INSTANCE 是一个单例，全局的事件执行器
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //表示连接建立，一旦客户端连接，第一个执行
    //将当前channel加入到channelGroup
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //通知其他客户，此客户加入聊天了。
        //该方法会遍历所有的channel,发送消息给所有的channel
        channelGroup.writeAndFlush("[客户] " + ctx.channel().remoteAddress() +" "+ sdf.format(new Date()) + " 加入群聊了~\n");
        channelGroup.add(ctx.channel());
    }

    //表示客户断开连接，将xx客户离开信息推送给当前在线客户，该方法会默认执行channelGroup.remove(channel)
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channelGroup.writeAndFlush("[客户] " + ctx.channel().remoteAddress() +" "+sdf.format(new Date()) + " 离开了\n");
        System.out.println("目前有" + channelGroup.size() + "位客户在线~");
    }

    //表示 客户端通道活动状态,提示xx上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 上线了~");
    }

    //表示 连接 处于 不活动状态。提示xx离线
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 离线了~");
    }

    //读取数据，进行转发
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("[客户]" + channel + "发送消息： " + msg);
        //发送消息给所有客户（除了自己）
        channelGroup.forEach(ch -> {
            if (ch != channel) {
                ch.writeAndFlush("[客户]" + channel.remoteAddress() + " " + sdf.format(new Date()) + "说： " + msg + "\n");
            } else {//回显消息
                ch.writeAndFlush("[自己]发送了消息" + msg + "\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭通道
        ctx.close();
    }
}
