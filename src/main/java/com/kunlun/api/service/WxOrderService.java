package com.kunlun.api.service;

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
     * 退款
     *
     * @param orderId
     * @param wxCode
     * @return
     */
    DataRet<String> refund(Long orderId, String wxCode);
}
