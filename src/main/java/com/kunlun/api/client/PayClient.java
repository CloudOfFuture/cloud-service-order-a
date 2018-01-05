package com.kunlun.api.client;

import com.kunlun.api.hystrix.PayClientHystrix;
import com.kunlun.result.DataRet;
import com.kunlun.wxentity.UnifiedOrderNotifyRequestData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2018-01-05 14:20
 */
@FeignClient(value = "cloud-service-pay", fallback = PayClientHystrix.class)
public interface PayClient {
    /**
     * 微信支付回调
     *
     * @param requestData UnifiedOrderNotifyRequestData
     * @return DataRet
     */
    @PostMapping("/pay/callback")
    DataRet callback(@RequestBody UnifiedOrderNotifyRequestData requestData);
}
