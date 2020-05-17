package com.zhuyunf.nio;

import java.nio.ByteBuffer;

public class ReadOnlyBuffer {
    public static void main(String[] args) {
        ByteBuffer allocate = ByteBuffer.allocate(64);
        for (int i = 0; i < 64; i++) {
            allocate.put( (byte) i);
        }
        allocate.flip();
        //只读ByteBuffer
        ByteBuffer byteBuffer = allocate.asReadOnlyBuffer();
        System.out.println(byteBuffer.getClass());
        while (byteBuffer.hasRemaining()) {

            System.out.println(byteBuffer.get());

        }
        // 抛出异常 ：ReadOnlyBufferException
        byteBuffer.put((byte) 1);
    }
}
