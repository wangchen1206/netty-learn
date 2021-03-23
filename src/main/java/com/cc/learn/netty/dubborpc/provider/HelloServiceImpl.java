package com.cc.learn.netty.dubborpc.provider;

import com.cc.learn.netty.dubborpc.publicinterface.HelloService;

import java.util.Objects;

/**
 * Description
 *
 * @author wangchen
 * @createDate 2021/03/23
 */
public class HelloServiceImpl implements HelloService {
    private static int count = 0;

    @Override
    public String hello(String msg) {
        System.out.println("收到服务消费者发来的消息： " + msg);
        if (Objects.isNull(msg)) {
            return "你好客户端，我已经收到你的消息了";
        } else {
            return "你好客户端，我已经收到你的消息了 [" + msg + "] 第" + (++count) + "次";
        }
    }
}
