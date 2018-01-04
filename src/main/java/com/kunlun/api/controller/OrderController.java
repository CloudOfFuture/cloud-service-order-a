package com.kunlun.api.controller;

import com.kunlun.api.service.OrderService;
import com.kunlun.entity.Order;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import com.kunlun.wxentity.OrderCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

    /**
     * 订单列表
     *
     * @param orderNo
     * @param phone
     * @param status
     * @param type
     * @param searchKey
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/findByCondition")
    public PageResult list(@RequestParam(value = "orderNo",required = false) String orderNo,
                           @RequestParam(value = "phone",required = false) String phone,
                           @RequestParam(value = "status",required = false) String status,
                           @RequestParam(value = "type",required = false) String type,
                           @RequestParam(value = "searchKey",required = false) String searchKey,
                           @RequestParam(value = "pageNo") Integer pageNo,
                           @RequestParam(value = "pageSize") Integer pageSize) {
        return orderService.list(orderNo, phone, status, type, searchKey, pageNo, pageSize);
    }

    /**
     * 发货
     *
     * @param orderCondition
     * @return
     */
    @PostMapping("/sendGood")
    public DataRet<String> sendGood(@RequestBody OrderCondition orderCondition) {
        return orderService.sendGood(orderCondition);
    }

    /**
     * 修改订单
     *
     * @param order
     * @return
     */
    @PostMapping("/modify")
    public DataRet<String> modify(@RequestBody Order order) {
        return orderService.modify(order);
    }

    /**
     * 订单详情
     *
     * @param orderId
     * @param sellerId
     * @return
     */
    @GetMapping("/findById")
    public DataRet<Order> findById(@RequestParam(value = "orderId") Long orderId,
                                   @RequestHeader Long sellerId) {
        return orderService.findById(orderId, sellerId);
    }

    /**
     * 根据订单编号查找订单
     * @param orderNo
     * @return
     */
    @GetMapping("/findByOrderNo")
    public DataRet<Order> findByOrderNo(@RequestParam(value = "orderNo") String orderNo){
        return orderService.findByOrderNo(orderNo);
    }

    /**
     * 退款
     *
     * @param order
     * @return
     */
    @PostMapping("/refund")
    public DataRet<String> refund(@RequestBody Order order) {
        return orderService.refund(order);
    }

    /**
     * 修改订单状态
     * @param id
     * @param status
     * @return
     */
    @PostMapping("/modifyStatus")
    public DataRet<String> modifyOrderById(@RequestParam(value = "id")Long id,
                                           @RequestParam(value = "status") String status){
        return orderService.modifyOrderStatus(id,status);
    }

    @PostMapping("/modifyStatusAndPayOrderNo")
    public DataRet<String> modifyStatusAndPayOrderNo(@RequestParam(value = "id") Long id,
                                                     @RequestParam(value = "status")String status,
                                                     @RequestParam(value = "wxOrderNo") String wxOrderNo){
        return  orderService.modifyStatusAndWxOrderNo(id,status,wxOrderNo);
    }
}
