package com.zhuyunf.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettySimple {
    public static void main(String[] args) throws  Exception {
        //创建 bossGroup 和workerGroup
        // bossGroup只是处理连接请求 ，真正与客户端的是 workerGroup

            NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
            NioEventLoopGroup workerGroup=new NioEventLoopGroup(8);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)  //设置两个线程组
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128) //设置线程队列得到的链接数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//设置保持活动的连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() { //创建一个测试通道 （匿名对象）
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                            socketChannel.pipeline().addLast(new NettyServerHandel());
                        }
                    });
            System.out.println("服务器以准备好");
            //绑定一个端口并且同步 生成一个ChannelFuture
            ChannelFuture cf = bootstrap.bind(8848).sync();
            //对关闭通道进行监听  只有这个事件才会关闭通道
            cf.channel().closeFuture().sync();

            cf.addListener(ChannelFutureListener->{
                if (cf.isSuccess()) {
                    System.out.println("绑定端口号8848成功");
                }else {
                    System.out.println("绑定端口号8848失败 ");
                }
            });


        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
