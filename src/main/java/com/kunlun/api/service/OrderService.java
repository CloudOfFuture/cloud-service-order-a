package com.kunlun.api.service;

import com.kunlun.result.PageResult;

/**
 * @author by kunlun
 * @version <0.1>
 * @created on 2017/12/20.
 */
public interface OrderService {
    /**
     * @param orderNo   订单号
     * @param phone     收件人手机
     * @param status    订单状态
     * @param type      订单类型
     * @param searchKey 搜索关键字
     * @param pageNo    当前页
     * @param pageSize  每页条数
     * @return
     */
    PageResult list(String orderNo,
                    String phone,
                    String status,
                    String type,
                    String searchKey,
                    Integer pageNo,
                    Integer pageSize);
}
