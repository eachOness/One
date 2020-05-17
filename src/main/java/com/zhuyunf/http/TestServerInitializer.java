package com.zhuyunf.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer  extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //向管道加入处理器
        //得到管道
        ChannelPipeline pipeline = socketChannel.pipeline();
         //加入一个netty提供的HttpServerCodec 的解码器

        pipeline.addLast("myHttpServerCodec", new HttpServerCodec());

        pipeline.addLast("myTestHttpServerHandel", new TestHttpServerHandel());
    }
}
