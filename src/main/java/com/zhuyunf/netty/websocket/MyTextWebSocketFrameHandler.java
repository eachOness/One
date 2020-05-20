package com.zhuyunf.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

public class MyTextWebSocketFrameHandler  extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded"+ctx.channel().id().asLongText());

    }

    //当 web 连接后出发该方法
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        System.out.println("handlerAdded"+ctx.channel().id().asLongText());
        System.out.println("handlerAdded"+ctx.channel().id().asShortText());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("服务器接受到消息"+msg.text());
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间"+
                LocalDateTime.now()+""+ msg.text()
        ));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
        ctx.close();
    }
}
