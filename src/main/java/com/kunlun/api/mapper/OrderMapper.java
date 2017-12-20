package com.kunlun.api.mapper;

import com.github.pagehelper.Page;
import com.kunlun.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author by kunlun
 * @version <0.1>
 * @created on 2017/12/20.
 */
@Mapper
public interface OrderMapper {

    /**
     * 订单列表
     *
     * @param orderNo
     * @param phone
     * @param status
     * @param type
     * @param searchKey
     * @return
     */
    Page<Order> list(@Param("orderNo") String orderNo,
                     @Param("phone") String phone,
                     @Param("status") String status,
                     @Param("type") String type,
                     @Param("searchKey") String searchKey);

    /**
     * 根据id查询订单
     *
     * @param orderId
     * @param sellerId
     * @param status
     * @return
     */
    Order findByOrderIdAndSellerId(@Param("orderId") Long orderId,
                                   @Param("sellerId") Long sellerId,
                                   @Param("status") String status);
}
