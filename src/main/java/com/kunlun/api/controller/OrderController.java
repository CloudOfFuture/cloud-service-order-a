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

    @GetMapping("/findByCondition")
    public PageResult list(@RequestParam(value = "order_no") String orderNo,
                           @RequestParam(value = "phone") String phone,
                           @RequestParam(value = "status") String status,
                           @RequestParam(value = "type") String type,
                           @RequestParam(value = "search_key") String searchKey,
                           @RequestParam(value = "page_no") Integer pageNo,
                           @RequestParam(value = "page_size") Integer pageSize) {
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
    public DataRet<Order> findById(@RequestParam(value = "order_id") Long orderId,
                                   @RequestHeader Long sellerId) {
        return orderService.findById(orderId, sellerId);
    }

    /**
     * 退款审核
     *
     * @param orderId   订单id
     * @param flag      AGREE同意 REFUSE 拒绝
     * @param remark    退款备注
     * @param refundFee 退款金额
     * @param sellerId  商家id
     * @return
     */
    @GetMapping("/audit/refund")
    public DataRet<String> auditRefund(@RequestParam(value = "order_id") Long orderId,
                                       @RequestParam(value = "flag") String flag,
                                       @RequestParam(value = "remark") String remark,
                                       @RequestParam(value = "refund_fee") Integer refundFee,
                                       @RequestHeader(value = "seller_id") Long sellerId) {
        return orderService.auditRefund(orderId, flag, remark, refundFee, sellerId);
    }

}
