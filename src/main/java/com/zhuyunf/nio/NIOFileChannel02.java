package com.zhuyunf.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel02 {
    public static void main(String[] args) throws  Exception{
        File file = new File("C:\\NIOFileChannel.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel channel = fileInputStream.getChannel();
        //创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
        //将数据通道读到buffer
        channel.read(buffer);
        System.out.println(new String(buffer.array()));
        fileInputStream.close();
    }
}
