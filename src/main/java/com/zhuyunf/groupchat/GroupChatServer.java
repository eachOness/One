package com.zhuyunf.groupchat;



import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class GroupChatServer {
    private Selector selector;
    private ServerSocketChannel listen;
    private static final int PORT=6667;

    public GroupChatServer() {
        try {
            selector = Selector.open();
            listen = ServerSocketChannel.open();
            listen.socket().bind(new InetSocketAddress(PORT));
            //设置非阻塞模式
            listen.configureBlocking(false);
            listen.register(selector, SelectionKey.OP_ACCEPT);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        while (true) {
            try {
                int count = selector.select(2000);
                //有事件要处理
                if (count > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()) {
                            SocketChannel sc = listen.accept();
                            sc.register(selector, SelectionKey.OP_READ);
                            System.out.println(sc.getRemoteAddress()+"以上线");
                        }
                        //发生read事件
                        if (key.isReadable()) {
                            //处理读
                            readData(key);
                        }
                    }
                    iterator.remove();

                }else
                {
                    System.out.println("等待连接");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void readData(SelectionKey selectionKey) {
        SocketChannel socketChannel = null;
        try {
            socketChannel=(SocketChannel)  selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int read = socketChannel.read(byteBuffer);
            if (read > 0) {
                String msg = new String(byteBuffer.array());
                System.out.println("from 客户端：" + msg);
                //向其他客户端转发消息
                sendInfoTOtherClient(msg,socketChannel);
            }
        } catch (IOException e) {
            try {
                System.out.println(socketChannel.getRemoteAddress() +"以离线");
                //取消注册
                selectionKey.cancel();
                socketChannel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void sendInfoTOtherClient(String msg,SocketChannel self)   {
        System.out.println("服务器消息转发中");
        selector.keys().forEach(key->{
             Channel targetChannel = key.channel();
            if (targetChannel instanceof SocketChannel && targetChannel != self) {
                SocketChannel socketChannel = (SocketChannel) targetChannel;
                ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
                try {
                    socketChannel.write(byteBuffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {

    }
}
