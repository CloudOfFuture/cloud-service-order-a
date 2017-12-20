package com.kunlun.api.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kunlun.api.mapper.OrderMapper;
import com.kunlun.entity.Order;
import com.kunlun.result.PageResult;
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


    @Override
    public PageResult list(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        Page<Order> page = orderMapper.list();
        return new PageResult(page);
    }

}
