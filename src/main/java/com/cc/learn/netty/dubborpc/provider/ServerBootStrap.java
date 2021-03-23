package com.cc.learn.netty.dubborpc.provider;

import com.cc.learn.netty.dubborpc.netty.NettyServer;

/**
 * Description
 *
 * @author wangchen
 * @createDate 2021/03/23
 */
public class ServerBootStrap {
    public static void main(String[] args) {
        //启动server
        NettyServer.startServer("127.0.0.1",7000);
    }
}
