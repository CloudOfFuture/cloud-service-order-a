package com.kunlun.api.controller;

import com.kunlun.api.service.OrderService;
import com.kunlun.result.PageResult;
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
@RequestMapping("/base/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list/{pageNo}/{pageSize}")
    public PageResult list(@RequestParam(value = "pageNo") Integer pageNo, @RequestParam(value = "pageSize") Integer pageSize) {
        return orderService.list(pageNo, pageSize);
    }
}
