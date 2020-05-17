package com.zhuyunf.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class MyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     *
     * @param ctx  上下文
     * @param evt  事件
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent stateEvent = (IdleStateEvent) evt;
            String state = "";
            switch (stateEvent.state()) {
                case ALL_IDLE:
                    state = "读，写 空闲";
                    break;
                case WRITER_IDLE:
                    state = "写空闲";
                    break;
                case READER_IDLE:
                    state = "读空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress()+"超时事件"+state);
        }
    }
}
