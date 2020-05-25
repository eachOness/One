package com.zhuyunf.dubboprc.customer;

import com.zhuyunf.dubboprc.netty.NettyClient;
import com.zhuyunf.dubboprc.publicinterface.HelloService;

public class ClientBootstrap {

    public static final String provideName = "HelloService#hello#";

    public static void main(String[] args) throws  Exception {
        NettyClient nettyClient=new NettyClient();
        HelloService service =(HelloService)
                nettyClient.getBean(HelloService.class, provideName);
        for (; ; ) {
            Thread.sleep(5*1000);
            String hello = service.hello(" 你好 dubbo");
            System.out.println("调用的结果：" + hello);
        }

    }
}
