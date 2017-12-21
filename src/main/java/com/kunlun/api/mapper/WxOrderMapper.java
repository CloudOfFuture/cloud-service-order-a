package com.kunlun.api.mapper;

import com.github.pagehelper.Page;
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

    Page<OrderExt> findByCondition(@Param("userId") String userId, @Param("status") String status);
}
