package com.kunlun.api.mapper;

import com.github.pagehelper.Page;
import com.kunlun.entity.Order;
import com.kunlun.entity.OrderExt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author by kunlun
 * @version <0.1>
 * @created on 2017/12/20.
 */
@Mapper
public interface WxOrderMapper {

    /**
     * 订单列表
     *
     * @param userId
     * @param orderStatus
     * @param payType
     * @return
     */
    Page<Order> findByOpenid(@Param("userId") String userId,
                             @Param("orderStatus") String orderStatus,
                             @Param("payType") String payType);

    /**
     * 查询订单详情
     *
     * @param orderId
     * @return
     */
    OrderExt findById(@Param("orderId") Long orderId);

    /**
     * 申请退款
     *
     * @param orderId
     * @return
     */
    int refund(@Param("orderId") Long orderId, @Param("refundFee") Integer refundFee);

    /**
     * 更新订单状态
     *
     * @param orderId
     * @param orderStatus
     * @return
     */
    int updateOrderStatus(@Param("orderId") Long orderId, @Param("orderStatus") String orderStatus);

}
