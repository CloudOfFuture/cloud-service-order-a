package com.kunlun.api.hystrix;

import com.kunlun.api.client.PayClient;
import com.kunlun.result.DataRet;
import com.kunlun.wxentity.UnifiedOrderNotifyRequestData;
import org.springframework.stereotype.Component;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2018-01-05 14:21
 */
@Component
public class PayClientHystrix implements PayClient {
    @Override
    public DataRet callback(UnifiedOrderNotifyRequestData requestData) {
        return new DataRet("ERROR", "回调失败");
    }
}
