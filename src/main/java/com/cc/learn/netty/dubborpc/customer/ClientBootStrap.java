package com.cc.learn.netty.dubborpc.customer;

import com.cc.learn.netty.dubborpc.netty.NettyClient;
import com.cc.learn.netty.dubborpc.publicinterface.HelloService;

/**
 * Description
 *
 * @author wangchen
 * @createDate 2021/03/23
 */
public class ClientBootStrap {

    public static String providerName = "HelloService#hello#";

    public static void main(String[] args) throws Exception{
        NettyClient nettyClient = new NettyClient();
        HelloService helloService = (HelloService) nettyClient.getBean(HelloService.class, providerName);
        for(;;){
            Thread.sleep(2000);
            String result = helloService.hello("你好，dubbo~");
            System.out.println(result);
        }
    }
}
