package com.zhulang.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @Author zhulang
 * @Date 2023-04-12
 **/
public class Client {
    public static void main(String[] args) {
        RealSubject realSubject = new RealSubject();
        InvocationHandler handler = new DynamicSubject(realSubject);
        Class<?> classType = handler.getClass();

        Subject subject = (Subject) Proxy.newProxyInstance(classType.getClassLoader(),
                realSubject.getClass().getInterfaces(),
                handler);
        subject.request();
        System.out.println(subject.getClass());
    }
}
