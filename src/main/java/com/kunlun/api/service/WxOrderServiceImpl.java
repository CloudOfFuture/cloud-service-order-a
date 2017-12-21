package com.kunlun.api.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kunlun.api.mapper.WxOrderMapper;
import com.kunlun.entity.OrderExt;
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
     * 查询订单列表
     *
     * @param orderCondition
     * @return
     */
//    @Override
//    public PageResult findByCondition(OrderCondition orderCondition) {
//        if (null == orderCondition || StringUtils.isNullOrEmpty(orderCondition.getWxCode())) {
//            return new PageResult("ERROR", "参数错误");
//        }
//        String openId = WxUtil.getOpenId(orderCondition.getWxCode());
//        PageHelper.startPage(orderCondition.getPageNo(), orderCondition.getPageSize());
//        Page<OrderExt> page = wxOrderMapper.findByCondition(openId, orderCondition.getStatus());
//        return new PageResult(page);
//    }

    /**
     * 订单列表
     *
     * @param pageNo   当前页
     * @param pageSize 每页条数
     * @param wxCode   微信code
     * @param status   订单状态
     * @return
     */
    @Override
    public PageResult findByOpenid(Integer pageNo, Integer pageSize, String wxCode, String status) {


        return null;
    }

    @Override
    public DataRet<String> refund(Long orderId, String wxCode) {
        return null;
    }
}
