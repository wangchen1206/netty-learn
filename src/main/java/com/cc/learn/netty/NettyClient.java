package com.cc.learn.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Netty Client
 *
 * @author wangchen
 * @createDate 2021/01/18
 */
public class NettyClient {
    private static int MAX_RETRY = 5;
    private static String HOST = "127.0.0.1";
    private static int PORT = 8000;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(workerGroup)//配置 处理每条连接数据读写 group
                .channel(NioSocketChannel.class)//配置IO模型
                .handler(new ChannelInitializer<NioSocketChannel>() {//IO处理逻辑
                    protected void initChannel(NioSocketChannel ch) throws Exception {

                    }
                });
        //建立连接，可充重试5次
        int MAX_RETRY = 5;
        connect(bootstrap,HOST,PORT,MAX_RETRY);
    }


    /**
    * 重连 ，并且时间间隔是2的n次方秒。
    * <br>
    * @param bootstrap
     * @param host 域名
     * @param port 端口
     * @param retry 重连次数
    * @date 2021/1/18
    * @author wangchen
    * @exception
    * @return: void
    **/
    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        //建立连接
        bootstrap.connect(host, port)
                .addListener(future -> {
                    if (future.isSuccess()) {
                        System.out.println("连接成功");
                    } else if (retry == 0) {
                        System.out.println("连接次数已用完，放弃连接");
                    } else {
                        //第几次重连
                        int order = MAX_RETRY - retry + 1;
                        //本次重连的间隔，1<<order，相当于1乘以2的order次方
                        int delay = 1 << order;
                        System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                        bootstrap.config().group().schedule(()->connect(bootstrap,host,port,retry - 1),delay, TimeUnit.SECONDS);
                    }
                });
    }
}
