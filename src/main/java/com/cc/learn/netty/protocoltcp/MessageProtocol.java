package com.cc.learn.netty.protocoltcp;

/**
 * Description
 *
 * @author wangchen
 * @createDate 2021/03/18
 */
public class MessageProtocol {
    private int length;
    private byte[] content;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
