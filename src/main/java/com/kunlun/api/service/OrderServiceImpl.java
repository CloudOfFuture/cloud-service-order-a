package com.kunlun.api.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kunlun.api.mapper.OrderMapper;
import com.kunlun.entity.Logistics;
import com.kunlun.entity.Order;
import com.kunlun.enums.CommonEnum;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import com.kunlun.wxentity.OrderCondition;
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

    /**
     * 发货
     *
     * @param orderCondition
     * @return
     */
    @Override
    public DataRet<String> sendGood(OrderCondition orderCondition) {
        Long orderId = orderCondition.getOrderId();
        if (null == orderId) {
            return new DataRet<>("ERROR", "参数错误");
        }
        if (StringUtils.isNullOrEmpty(orderCondition.getLogisticNo())) {
            return new DataRet<>("ERROR", "运单号不能为空");
        }
        if (StringUtils.isNullOrEmpty(orderCondition.getLogisticName())) {
            return new DataRet<>("ERROR", "快递公司不能为空");
        }
        //TODO   发件人ID  应该为店铺ID
        /**
         * 根据 订单id、店铺id、订单状态为待发货查询订单
         */
        Order order = orderMapper.findByOrderIdAndSellerId(orderId, orderCondition.getSellerId(),
                CommonEnum.UN_DELIVERY.getCode());
        if (null == order) {
            return new DataRet<>("ERROR", "订单不存在");
        }


        return null;
    }


    private Logistics logistics(OrderCondition orderCondition) {
        Logistics logistics = new Logistics();
        logistics.setCompanyCode(orderCondition.getCompanyCode());
        logistics.setOrderId(orderCondition.getOrderId());
        logistics.setLogisticName(orderCondition.getLogisticName());
        logistics.setSenderId(orderCondition.getSellerId());
        return logistics;
    }
}
