package com.zhuyunf.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class UnpooledTest {
    public static void main(String[] args) {
        /**
         * 通过readindex 和writeindex和capacity 将buffer分为三个区域
         * 通过一个readindex 已经读取的区域通过
         * 通过readindex--writerindex ，可读的区域
         * writerindex-- capacity可写的区域
         *
         */

        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i <10 ; i++) {
            buffer.writeByte(i);
        }
        System.out.println(buffer.capacity());
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.getByte(i));
        }
    }
}
