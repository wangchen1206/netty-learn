package com.cc.learn.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Description
 *
 * @author wangchen
 * @createDate 2021/03/05
 */
public class NettyByteBuf {
    public static void main(String[] args) {
        //ByteBuf和Nio.ByteBuffer不一样
        //说明：
        //1.ByteBuf底层是一个byte[]
        //2.不需要filp就可以进行读写转化，因为 readerIndex和writerIndex
        //  readerIndex针对 buffer.readByte()方法，会进行readerIndex+1
        //  0-readerIndex，是已经读取的，
        //  readerIndex-writerIndex，是还未读取的，
        //  writerIndex-capacity，是还有的空间，可以写入。

        ByteBuf buffer = Unpooled.buffer(10);

        for (int i = 0; i < 5; i++) {
            buffer.writeByte(i);
        }

        System.out.println("buf容量： "+buffer.capacity());
        //此方法获取可读的字节 writerIndex - readerIndex
        int readableBytes = buffer.readableBytes();

        for (int i = 0; i < buffer.readableBytes(); i++) {
            System.out.println(buffer.readByte());
        }


    }
}
