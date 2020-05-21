package com.zhuyunf.netty.inboundhandleroutboundhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("myClientHandler发送数据");
        ctx.writeAndFlush(123456L);

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long aLong) throws Exception {


    }
}
