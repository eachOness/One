package com.zhuyunf.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws  Exception {
        ServerSocketChannel open = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        open.socket().bind(inetSocketAddress);
        SocketChannel read = open.accept();
        ByteBuffer[] buffers = new ByteBuffer[2];
        buffers[0] = ByteBuffer.allocate(3);
        buffers[1] = ByteBuffer.allocate(5);
        int messageLength = 8;
        while (true) {
            int byteRead = 0;
            while (byteRead < messageLength) {
                long l = read.read(buffers);
                byteRead += l; //默认读取的字节数
                System.out.println("ByteRead:" + byteRead);
                Arrays.asList(buffers).stream().map(buffer->
                  "position:"+ buffer.position()+ "limit:"+buffer.limit()
                 ).forEach(System.out::println);

            }
            Arrays.asList(buffers).forEach(
                    byteBuffer -> byteBuffer.flip()
            );
            long writeByte = 0;
            while (writeByte<messageLength) {
                long write = read.write(buffers);
                writeByte += write;
            }
            Arrays.asList(buffers).forEach(byteBuffer -> byteBuffer.flip());
            System.out.println("byteRead:="+byteRead+"byteWrite:="+writeByte+"messageLength"+messageLength);
        }
    }
}
