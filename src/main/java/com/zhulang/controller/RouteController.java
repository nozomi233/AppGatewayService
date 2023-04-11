package com.zhulang.controller;

import com.zhulang.iface.ILoginService;
import com.zhulang.iface.IService;
import com.zhulang.rpc.RpcClient;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
public class RouteController {
    @RequestMapping("/test")
    public String Test(){
        RpcClient client = new RpcClient<>(IService.class, "localhost", "7777");
        IService hello = (IService) client.getClientInstance();
        /*调用成功 打印返回值*/
        System.out.println(hello.getTest("RPC"));

        RpcClient loginPrcClient = new RpcClient<>(ILoginService.class, "localhost", "7777");
        ILoginService iLoginService = (ILoginService) loginPrcClient.getClientInstance();
        /*调用成功 打印返回值*/
        System.out.println(iLoginService.login("登录成功了"));
        return "祝浪hello";
    }

    /**
     * url中包含param的方式
     * @param tmp
     * @return
     */
    @RequestMapping(value = "/posttest",produces = {"applicaiton/json;charset=utf-8"},method = RequestMethod.POST)
    public String PostTest(String tmp){
        System.out.println("tmp =" + tmp);
        return "success";
    }

    /**
     * form-data中param的方式
     * @param name
     * @param age
     * @return
     */
    @PostMapping("/posttestparam")
    public String PostTest(@RequestParam(name="name",required = true) String name, @RequestParam(name = "age",required = false) Integer age){
        return "name : " + name + " and age= " + age;
    }
}
