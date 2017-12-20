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
@RequestMapping("/backend/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public PageResult list(@RequestParam(value = "order_no") String orderNo,
                           @RequestParam(value = "phone") String phone,
                           @RequestParam(value = "status") String status,
                           @RequestParam(value = "type") String type,
                           @RequestParam(value = "search_key") String searchKey,
                           @RequestParam(value = "page_no") Integer pageNo,
                           @RequestParam(value = "page_size") Integer pageSize) {
        return orderService.list(orderNo, phone, status, type, searchKey, pageNo, pageSize);
    }
}
