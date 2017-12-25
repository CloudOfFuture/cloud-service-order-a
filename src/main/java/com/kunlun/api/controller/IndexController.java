package com.kunlun.api.controller;

import com.kunlun.api.service.IndexService;
import com.kunlun.entity.Order;
import com.kunlun.result.DataRet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by kunlun
 * @version <0.1>
 * @created on 2017/12/20.
 */

@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private IndexService indexService;

    @GetMapping("/test")
    public DataRet<String> test() {
        return new DataRet<>("这是服务A");
    }

    @PostMapping("/test/post")
    public DataRet<String> testPost(@RequestBody Order order) {
        System.out.println("打印传递参数");
        System.out.println(order.toString());
        return new DataRet<>("hahha ");
    }

    /**
     * 测试服务之间调用
     *
     * @return
     */
    @GetMapping("/log")
    public DataRet<String> logTest(@RequestParam("orderNo") String orderNo) {
        return new DataRet<>("我是服务A：orderNo=" + orderNo);
    }

}
