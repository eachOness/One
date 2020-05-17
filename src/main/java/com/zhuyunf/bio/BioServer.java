package com.zhuyunf.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioServer {
    public static void main(String[] args)  throws  Exception{
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket serverSocket=new ServerSocket(8848);
        Socket socket = serverSocket.accept();
        System.out.println("连接到一个客户");
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                handler(socket);
            }
        });

    }

    public static void handler(Socket socket) {
        System.out.println("线程的ID:"+Thread.currentThread().getId()+
                "线程的名字："+Thread.currentThread().getName());
        byte[] bytes = new byte[1024];
        try {
            InputStream inputStream = socket.getInputStream();
            while (true) {
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String (bytes,0,read));
                }
                else {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
