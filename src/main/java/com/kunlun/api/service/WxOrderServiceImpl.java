package com.kunlun.api.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kunlun.api.mapper.WxOrderMapper;
import com.kunlun.entity.Order;
import com.kunlun.entity.OrderExt;
import com.kunlun.enums.CommonEnum;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import com.kunlun.utils.WxUtil;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author by kunlun
 * @version <0.1>
 * @created on 2017/12/20.
 */
@Service
public class WxOrderServiceImpl implements WxOrderService {

    @Autowired
    private WxOrderMapper wxOrderMapper;

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
    @Override
    public PageResult findByOpenid(Integer pageNo, Integer pageSize, String wxCode, String orderStatus, String payType) {
        String userId = WxUtil.getOpenId(wxCode);
        PageHelper.startPage(pageNo, pageSize);
        if (StringUtils.isNullOrEmpty(userId)) {
            return new PageResult("ERROR", "微信code为空");
        }
        if (CommonEnum.ALL.getCode().equals(orderStatus)) {
            orderStatus = null;
        }
        Page<Order> page = wxOrderMapper.findByOpenid(userId, orderStatus, payType);
        return new PageResult(page);
    }

    /**
     * 申请退款
     *
     * @param orderId
     * @param wxCode
     * @param refundFee
     * @return
     */
    @Override
    public DataRet<String> refund(Long orderId, String wxCode, Integer refundFee) {
        if (StringUtils.isNullOrEmpty(orderId.toString()) && StringUtils.isNullOrEmpty(wxCode)) {
            return new DataRet<>("ERROR", "传入参数有误");
        }
        Order order = wxOrderMapper.findById(orderId);
        if (CommonEnum.DONE.getCode().equals(order.getOrderStatus())) {
            return new DataRet<>("ERROR", "订单已完成，不能退款");
        }
        if (CommonEnum.UN_PAY.getCode().equals(order.getOrderStatus())) {
            return new DataRet<>("ERROR", "订单还未付款，请先付款");
        }
        int result = wxOrderMapper.refund(orderId, refundFee);
        if (result < 0) {
            return new DataRet<>("ERROR", "申请退款失败,请重试");
        }
        return new DataRet<>("申请退款成功");
    }

    /**
     * 查询订单详情
     *
     * @param orderId
     * @return
     */
    @Override
    public DataRet<OrderExt> findById(Long orderId) {
        if (orderId == null) {
            return new DataRet<>("ERROR", "传入的参数有误");
        }
        OrderExt orderExt = wxOrderMapper.findById(orderId);
        //TODO 订单发货信息 订单收货地址
        if (orderExt == null) {
            return new DataRet<>("ERROR", "订单不存在");
        }
        return new DataRet<>(orderExt);
    }

    /**
     * 确认收货
     *
     * @param orderId
     * @return
     */
    @Override
    public DataRet<String> confirmByGood(Long orderId, String ipAddress) {
        if (orderId == null) {
            return new DataRet<>("ERROR", "传入的参数有误");
        }
        //查询订单详情
        Order order = wxOrderMapper.findById(orderId);
        if (order == null) {
            return new DataRet<>("ERROR", "订单不存在");
        }
        //判断/更改订单状态(已完成）
        if (!CommonEnum.UN_RECEIVE.getCode().equals(order.getOrderStatus())) {
            return new DataRet<>("ERROR", "订单状态不对，不能确认收货");
        }
        int result = wxOrderMapper.updateOrderStatus(orderId, CommonEnum.DONE.getCode());
        if (result > 0) {
            //TODO 保存订单日志
            return new DataRet<>("确认收货成功");
        }
        return new DataRet<>("ERROR", "确认收货失败");
    }

    /**
     * 取消订单
     *
     * @param orderId
     * @param ipAddress
     * @return
     */
    @Override
    public DataRet<String> cancelByOrder(Long orderId, String ipAddress) {
        if (orderId == null) {
            return new DataRet<>("ERROR", "传入的参数有误");
        }
        //查询订单详情
        Order order = wxOrderMapper.findById(orderId);
        if (order == null) {
            return new DataRet<>("ERROR", "订单不存在");
        }
        //关闭订单
        if (!CommonEnum.UN_PAY.getCode().equals(order.getOrderStatus())) {
            return new DataRet<>("ERROR", "订单状态异常，不能取消订单");
        }
        int result = wxOrderMapper.updateOrderStatus(orderId, CommonEnum.CLOSING.getCode());
        if (result > 0) {
            //TODO 保存日志
            return new DataRet<>("取消订单成功");
        }
        return new DataRet<>("ERROR", "取消订单失败");
    }
}
