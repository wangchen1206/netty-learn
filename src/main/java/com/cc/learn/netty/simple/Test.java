package com.cc.learn.netty.simple;

import io.netty.util.NettyRuntime;

/**
 * Description
 *
 * @author wangchen
 * @createDate 2021/03/03
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(NettyRuntime.availableProcessors());
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
