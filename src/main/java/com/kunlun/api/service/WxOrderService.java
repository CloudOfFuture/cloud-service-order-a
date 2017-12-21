package com.kunlun.api.service;

import com.kunlun.entity.Order;
import com.kunlun.entity.OrderExt;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import com.kunlun.wxentity.OrderCondition;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author by kunlun
 * @version <0.1>
 * @created on 2017/12/20.
 */
public interface WxOrderService {

    /**
     * 订单列表
     *
     * @param pageNo
     * @param pageSize
     * @param wxCode
     * @param orderStatus
     * @param payType
     * @return
     */
    PageResult findByOpenid(Integer pageNo, Integer pageSize, String wxCode, String orderStatus, String payType);

    /**
     * 申请退款
     *
     * @param orderId
     * @param wxCode
     * @param refundFee
     * @return
     */
    DataRet<String> refund(Long orderId, String wxCode, Integer refundFee);

    /**
     * 查询订单详情
     *
     * @param orderId
     * @return
     */
    DataRet<OrderExt> findById(Long orderId);
}
