package com.kunlun.api.client;

import com.kunlun.api.hystrix.DeliveryClientHystrix;
import com.kunlun.result.DataRet;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author by fk
 * @version <0.1>
 * @created on 2018-01-05.
 */
@FeignClient(value = "cloud-service-user-center", fallback = DeliveryClientHystrix.class)
public interface DeliveryClient {

    /**
     * 查询订单收货地址
     *
     * @param orderId
     * @return
     */
    @GetMapping("/delivery/findById")
    DataRet findDetailById(@RequestParam(value = "orderId") Long orderId);
}
