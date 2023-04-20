package com.zhulang.rmi;

import com.zhulang.dto.User;
import com.zhulang.iface.IUserService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @Author zhulang
 * @Date 2023-04-11
 **/
public class RMIClient {
    public static void main(String[] args) throws RemoteException {
        // 1 获取 registry 实例
        Registry registry = LocateRegistry.getRegistry("172.17.170.30",29999);
        // 2 通过 registry 实例查找对应的远程对象
//        for (String name : registry.list()) {
//            System.out.println(name);
//        }
        try {
            IUserService userService = (IUserService) registry.lookup("userService");
            // 这里会调用到服务端里面的方法
            User user = userService.findById(1);
            System.out.println(user.getId()+"-----"+ user.getName());
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}