package com.zhulang.proxy;

/**
 * @Author zhulang
 * @Date 2023-04-12
 **/
public class RealSubject implements Subject{
    @Override
    public void request() {
        System.out.println("From the RealSubject.");
    }
}
