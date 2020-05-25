package com.zhuyunf.dubboprc.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyServer {

    public static void startServer(String hostName, int port) {
        startServer0(hostName, port);
    }
    public static void startServer0(String hostName, int port) {
        EventLoopGroup boos = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup(8);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(boos, worker)
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast(new StringDecoder());
                    pipeline.addLast(new StringEncoder());
                    //TODO
                    pipeline.addLast(new NettyServerHandler());
                }
            }
         );
            ChannelFuture sync = bootstrap.bind(hostName, port).sync();
            System.out.println("服务提供方开始提供服务");
            sync.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            boos.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }
}
