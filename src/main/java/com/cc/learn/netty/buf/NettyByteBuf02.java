package com.cc.learn.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * Description
 *
 * @author wangchen
 * @createDate 2021/03/05
 */
public class NettyByteBuf02 {
    public static void main(String[] args) {
        //创建ByteBuf
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world!", Charset.forName("utf-8"));

        //使用相关方法
        if (byteBuf.hasArray()){
            byte[] content = byteBuf.array();
            System.out.println(new String(content,Charset.forName("utf-8")));

            System.out.println("byteBuf="+byteBuf);
            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity());

            System.out.println(byteBuf.getByte(0));
//            System.out.println(byteBuf.readByte());

            int len = byteBuf.readableBytes();
            System.out.println("len="+len);

            //取出各个字节
            for (int i = 0; i < len; i++) {
                System.out.println((char) byteBuf.getByte(i));
            }

            //分段取出
            System.out.println(byteBuf.getCharSequence(0,4,Charset.forName("utf-8")));
            System.out.println(byteBuf.getCharSequence(4,6,Charset.forName("utf-8")));
        }
    }
}
