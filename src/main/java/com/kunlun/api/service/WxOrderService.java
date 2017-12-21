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
     * @param pageNo   当前页
     * @param pageSize 每页条数
     * @param wxCode   微信code
     * @param status   订单状态
     * @return
     */
    PageResult findByOpenid(Integer pageNo,
                            Integer pageSize,
                            String wxCode,
                            String status);

    /**
     * 退款
     *
     * @param orderId
     * @param wxCode
     * @return
     */
    DataRet<String> refund(Long orderId, String wxCode);
}
