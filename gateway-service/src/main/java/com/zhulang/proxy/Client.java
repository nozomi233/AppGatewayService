package com.zhulang.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @Author zhulang
 * @Date 2023-04-12
 **/
public class Client {
    public static void main(String[] args) {
        RealSubject realSubject = new RealSubject();
        InvocationHandler handler = new DynamicSubject(realSubject);
        Class<?> classType = handler.getClass();

        System.out.println(classType.getClassLoader());
        System.out.println(Arrays.toString(realSubject.getClass().getInterfaces()));
        System.out.println(handler);
        // 扩展：String类加载器为启动类加载器(BootstrapClassLoader)，对Java不可见，故打印null;
        System.out.println(String.class.getClassLoader());
        Subject subject = (Subject) Proxy.newProxyInstance(classType.getClassLoader(),
                realSubject.getClass().getInterfaces(),
                handler);
        subject.request();
        System.out.println(subject.getClass());
    }
}
