package com.kunlun.api.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kunlun.api.mapper.OrderMapper;
import com.kunlun.entity.Order;
import com.kunlun.result.PageResult;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author by kunlun
 * @version <0.1>
 * @created on 2017/12/20.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;


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
    @Override
    public PageResult list(String orderNo, String phone, String status, String type, String searchKey, Integer pageNo, Integer pageSize) {
        if (StringUtils.isNullOrEmpty(String.valueOf(pageNo)) ||
                StringUtils.isNullOrEmpty(String.valueOf(pageSize))) {
            return new PageResult("ERROR", "参数错误");
        }
        PageHelper.startPage(pageNo, pageSize);
        Page<Order> page = orderMapper.list(orderNo, phone, status, type, searchKey);
        return null;
    }
}
