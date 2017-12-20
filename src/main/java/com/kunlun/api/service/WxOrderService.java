package com.kunlun.api.service;

import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import com.kunlun.wxentity.OrderCondition;

/**
 * @author by kunlun
 * @version <0.1>
 * @created on 2017/12/20.
 */
public interface WxOrderService {

    /**
     * 订单列表
     * @param orderCondition
     * @return
     */
    PageResult findByCondition(OrderCondition orderCondition);

    /**
     * 退款
     * @param orderId
     * @param wxCode
     * @return
     */
    DataRet<String> refund (Long orderId,String wxCode);
}
