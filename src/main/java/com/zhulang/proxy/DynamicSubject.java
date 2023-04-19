package com.zhulang.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理角色
 * 使用动态代理必须先实现 InvocationHandler 这个接口
 * @Author zhulang
 * @Date 2023-04-12
 **/
public class DynamicSubject implements InvocationHandler {

    private Object sub;

    public DynamicSubject(Object sub) {
        this.sub = sub;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before calling" + method);
        Object invoke = method.invoke(sub, args);
        System.out.println(args == null);
        System.out.println("after calling" + method);
        return invoke;
    }
}
