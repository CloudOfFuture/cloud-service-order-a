package com.kunlun.api.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kunlun.api.mapper.WxOrderMapper;
import com.kunlun.entity.Order;
import com.kunlun.entity.OrderExt;
import com.kunlun.enums.CommonEnum;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import com.kunlun.wxentity.OrderCondition;
import com.kunlun.wxentity.wxUtils.WxUtil;
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

    @Override
    public DataRet<String> refund(Long orderId, String wxCode) {
        return null;
    }

    /**
     * 查询订单详情
     *
     * @param orderId
     * @return
     */
    @Override
    public DataRet<OrderExt> findById(Long orderId) {
        if (StringUtils.isNullOrEmpty(orderId.toString())) {
            return new DataRet<>("ERROR", "传入的参数有误");
        }
        OrderExt orderExt = wxOrderMapper.findById(orderId);
        //TODO 订单发货信息 订单收货地址
        if (orderExt == null) {
            return new DataRet<>("ERROR", "订单不存在");
        }
        return new DataRet<>(orderExt);
    }
}
