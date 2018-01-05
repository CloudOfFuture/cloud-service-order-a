package com.kunlun.api.hystrix;

import com.kunlun.api.client.DeliveryClient;
import com.kunlun.entity.Delivery;
import com.kunlun.result.DataRet;
import org.springframework.stereotype.Component;

/**
 * @author by fk
 * @version <0.1>
 * @created on 2018-01-05.
 */
@Component
public class DeliveryClientHystrix implements DeliveryClient {

    /**
     * 查询订单收货地址
     *
     * @param deliveryId
     * @return
     */
    @Override
    public DataRet findDetailById(Long deliveryId) {
        return new DataRet("ERROR", "查询订单收货地址失败");
    }
}
