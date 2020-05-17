package com.zhuyunf.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class GroupChatClient {
    private final String HOST = "127.0.0.1";
    private final int PORT = 6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    public GroupChatClient() {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open().bind(new InetSocketAddress("127.0.0.1", PORT));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
           username= socketChannel.getLocalAddress().toString().substring(1);
            System.out.println(username+"已经准备好了");


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void sendInfo(String info) {
        info = username + ":说" + info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readInfo() {
        try {
            int select = selector.select();
            if (select > 0) { //有可用的通道
                Iterator<SelectionKey> iterator = selector.keys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        //得到相关的通道
                        SocketChannel sc = (SocketChannel) key.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        sc.read(byteBuffer);
                        String s = new String(byteBuffer.array());
                        System.out.println(s.trim());

                    }
                }
                iterator.remove();
            }else {
                System.out.println("没有可以用的通道    ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
