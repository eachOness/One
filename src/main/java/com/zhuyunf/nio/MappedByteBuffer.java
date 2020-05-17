package com.zhuyunf.nio;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * MappedByteBuffer 可以让文件直接内存（堆外内存）修改 操作系统不需要拷贝一次
 */
public class MappedByteBuffer {
    public static void main(String[] args) throws  Exception{
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        java.nio.MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, channel.size());
        map.put(0,(byte) 'H');
        map.put(3,(byte) 9);
        randomAccessFile.close();

    }
}
