package com.kunlun.api.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kunlun.api.mapper.LogisticMapper;
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

    @Autowired
    private LogisticMapper logisticMapper;

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
        /**
         * 根据 订单id、店铺id、订单状态为待发货查询订单
         */
        Order order = orderMapper.findByOrderIdAndSellerId(orderId, orderCondition.getSellerId(),
                CommonEnum.UN_DELIVERY.getCode());
        if (null == order) {
            return new DataRet<>("ERROR", "订单不存在");
        }
        Logistics logistics = this.logistics(orderCondition);
        logisticMapper.add(logistics);
        //TODO  调用日志服务  暂时未写

        orderMapper.updateOrderStatus(orderId,
                CommonEnum.UN_RECEIVE.getCode(),
                logistics.getLogisticNo(), logistics.getId());


        return new DataRet<>("发货成功");
    }

    /**
     * 修改订单
     *
     * @param order
     * @return
     */
    @Override
    public DataRet<String> modify(Order order) {
        Order result = orderMapper.findByOrderIdAndSellerId(order.getId(), order.getSellerId(),
                CommonEnum.UN_DELIVERY.getCode());
        if (null == result) {
            return new DataRet<>("ERROR", "订单不存在");
        }
        orderMapper.modify(order.getId(),
                order.getProvince(),
                order.getCity(),
                order.getArea(),
                order.getAddress(),
                order.getRemark(),
                order.getPhone());
        return new DataRet<>("订单修改成功");
    }

    /**
     * 订单详情
     *
     * @param orderId
     * @param sellerId
     * @return
     */
    @Override
    public DataRet<Order> findById(Long orderId, Long sellerId) {
        Order order = orderMapper.findByOrderIdAndSellerId(orderId, sellerId, null);
        if (null == order) {
            return new DataRet<>("ERROR", "订单不存在");
        }
        return new DataRet<>(order);
    }

    /**
     * 退款审核
     *
     * @param orderId   订单id
     * @param flag      AGREE同意  REFUSE  拒绝
     * @param remark    退款备注
     * @param refundFee 退款金额
     * @param sellerId  商家id
     * @return
     */
    @Override
    public DataRet<String> auditRefund(Long orderId, String flag, String remark, Integer refundFee, Long sellerId) {
        Order order = orderMapper.findByOrderIdAndSellerId(orderId, sellerId, null);
        if (order == null) {
            return new DataRet<>("ERROR", "订单不存在");
        }
        if(CommonEnum.REFUSE.getCode().equals(flag)){
            orderMapper.auditRefund(orderId,CommonEnum.REFUSE.getCode(),remark,0,sellerId);
        }else {
            orderMapper.auditRefund(orderId, flag, remark, refundFee, sellerId);


        }
        //TODO 生成退款日志
        return new DataRet<>("审核成功");
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
