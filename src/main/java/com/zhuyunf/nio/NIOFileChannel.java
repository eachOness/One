package com.zhuyunf.nio;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel {
    public static void main(String[] args) throws Exception {
        String str = "The weather is terrible today";
        FileOutputStream fileOutputStream = new FileOutputStream(new File("C:\\NIOFileChannel.txt"));
        FileChannel channel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();
        channel.write(byteBuffer);
        channel.close();

    }
}
