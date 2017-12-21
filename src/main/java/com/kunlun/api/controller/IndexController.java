package com.kunlun.api.controller;

import com.kunlun.api.service.IndexService;
import com.kunlun.result.DataRet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    /**
     * 测试服务之间调用
     *
     * @param orderId
     * @return
     */
    @GetMapping("/log")
    public DataRet<String> logTest(@RequestParam(value = "order_id") Long orderId) {
        return indexService.logTest(orderId);
    }

}
