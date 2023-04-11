package com.zhulang.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 * 动态代理
 * 客户端调用服务
 */
public class RpcClient<T> implements InvocationHandler {

    private Class<T> serviceInterface;
    private InetSocketAddress addr;

    /**
     * serviceInterface:远程服务接口
     * ip:远程IP地址
     * port:远程端口
     */
    public RpcClient(Class<T> serviceInterface, String ip, String port) {
        this.serviceInterface = serviceInterface;
        this.addr = new InetSocketAddress(ip, Integer.parseInt(port));
    }

    public T getClientInstance() {
        /*传入被代理的类*/
        return (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class<?>[]{serviceInterface}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Socket socket = null;
        ObjectOutputStream output = null;
        ObjectInputStream input = null;

        try {
            /*创建Socket客户端，根据指定地址连接远程服务提供者*/
            socket = new Socket();
            /*连接服务端*/
            socket.connect(addr);
            /*将远程服务调用所需的接口类、方法名、参数列表等编码后发送给服务提供者*/
            output = new ObjectOutputStream(socket.getOutputStream());
            output.writeUTF(serviceInterface.getName());
            output.writeUTF(method.getName());
            output.writeObject(method.getParameterTypes());
            output.writeObject(args);

            /*同步阻塞等待服务器返回应答，获取应答后返回*/
            input = new ObjectInputStream(socket.getInputStream());
            /*返回结果*/
            return input.readObject();
        } finally {
            if (socket != null) {
                socket.close();
            }
            if (output != null) {
                output.close();
            }
            if (input != null) {
                input.close();
            }
        }
    }
}