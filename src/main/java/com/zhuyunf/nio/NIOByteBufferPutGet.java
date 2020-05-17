package com.zhuyunf.nio;

import java.nio.ByteBuffer;

public class NIOByteBufferPutGet {
    public static void main(String[] args) {
        ByteBuffer allocate = ByteBuffer.allocate(64);
        allocate.putInt(100);
        allocate.putChar('s');
        allocate.putLong(100L);
        allocate.putShort((short) 1);
        allocate.flip();
        System.out.println("\n " + allocate.getInt());
        System.out.println(allocate.getChar());
        System.out.println(allocate.getLong()+"\n"+allocate.getShort());
    }
}
