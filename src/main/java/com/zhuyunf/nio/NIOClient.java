package com.zhuyunf.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {
    public static void main(String[] args) throws  Exception{
        //得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        InetSocketAddress inetSocketAddress=new InetSocketAddress("127.0.0.1",8848);
        if (!socketChannel.connect(inetSocketAddress)) {
            while (socketChannel.finishConnect()) {
                System.out.println( "Client connection takes time，The client does not block ,do other things");

            }
        }
        String str = "The weather is good today";
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        socketChannel.write(byteBuffer);
    }
}
