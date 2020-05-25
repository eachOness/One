package com.zhuyunf.dubboprc.provider;

import com.zhuyunf.dubboprc.publicinterface.HelloService;

public class HelloServiceImpl implements HelloService {
    private static int count = 0;
    @Override
    public String hello(String mes) {

        System.out.println("客户端接受的消息：" + mes);
        if (mes != null) {
            return "你好客户端 ，我收到了你的消息["+mes+"]第"+(++count )+"次";
        }
        return "你好客户端 ，我收到了你的消息 ";
    }
}
