package com.kunlun.api.mapper;

import com.kunlun.entity.Logistics;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author by kunlun
 * @version <0.1>
 * @created on 2017/12/20.
 */
@Mapper
public interface LogisticMapper {

    /**
     * 创建物流信息
     *
     * @param logistics
     * @return
     */
    int add(Logistics logistics);

    
}
