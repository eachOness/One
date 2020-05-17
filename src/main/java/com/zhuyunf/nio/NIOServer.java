package com.zhuyunf.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    public static void main(String[] args) throws Exception {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        Selector selector = Selector.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(8848));
        serverSocketChannel.configureBlocking(false);
        //把serverSocketChannel注册到selector 关心时间为 op_accept
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //循环等待客户连接
        while (true) {
            if (selector.select(1000) == 0) {  //没有时间发生
                System.out.println("服务器等待了一秒 没有人来连接");
                continue;
            }
            //获取有时间发生的selectKeys
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {

                SelectionKey next = iterator.next();
                if (next.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功 生成一个socketChannel："+socketChannel.hashCode());
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if (next.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) next.channel();
                    socketChannel.configureBlocking(false);
                    ByteBuffer byteBuffer = (ByteBuffer) next.attachment();
                    socketChannel.read(byteBuffer);
                    System.out.println("客户端发来的："+new String(byteBuffer.array()));
                }
                iterator.remove(); //防止重复操作
            }
        }
    }
}
