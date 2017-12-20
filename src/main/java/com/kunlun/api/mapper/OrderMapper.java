package com.kunlun.api.mapper;

import com.github.pagehelper.Page;
import com.kunlun.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author by kunlun
 * @version <0.1>
 * @created on 2017/12/20.
 */
@Mapper
public interface OrderMapper {

    Page<Order> list();
}
