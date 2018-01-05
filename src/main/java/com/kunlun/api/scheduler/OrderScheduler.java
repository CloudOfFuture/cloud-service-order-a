package com.kunlun.api.scheduler;

import com.kunlun.api.client.LogClient;
import com.kunlun.api.client.PayClient;
import com.kunlun.api.mapper.WxOrderMapper;
import com.kunlun.entity.Order;
import com.kunlun.entity.OrderLog;
import com.kunlun.enums.CommonEnum;
import com.kunlun.utils.*;
import com.kunlun.wxentity.OrderQueryResponseData;
import com.kunlun.wxentity.UnifiedOrderNotifyRequestData;
import com.kunlun.wxentity.WxRefundNotifyResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ycj
 * @version V1.0 <定时查询订单定时器>
 * @date 2018-01-05 11:00
 */
@Component
public class OrderScheduler {
    @Autowired
    private WxOrderMapper wxOrderMapper;
    @Autowired
    private PayClient payClient;
    @Autowired
    private LogClient logClient;

    /**
     * 12小时执行一次 查询申请退款时间大于两天（48小时） 的订单
     * 12 * 60 * 60 * 1000
     */
    @Scheduled(fixedRate = 2 * 60 * 1000)
    public void refundingOrder() {
        List<Order> orderList = wxOrderMapper.findRefundingOrder();
        orderList.forEach(this::refund);
    }

    private void refund(Order order) {
        String nonceStr = WxUtil.createRandom(false, 10);
        String refundOrderNo = order.getOrderNo() + "T";
        //定义接收退款返回字符串
        String refundXML = WxSignUtil.refundSign(order.getOrderNo(), order.getPaymentFee(), order.getRefundFee(),
                refundOrderNo, nonceStr);
        //接收退款返回
        String response = null;
        try {
            response = HttpUtil.refundHttp(WxPayConstant.WECHAT_REFUND, refundXML, WxPayConstant.MCHID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        OrderLog orderLog;
        if (response != null) {
            WxRefundNotifyResponseData responseData = XmlUtil.castXMLStringToWxRefundNotifyResponseData(response);
            Boolean result = "success".equalsIgnoreCase(responseData.getReturn_code());
            if (result) {
                //退款成功，更新状态
                wxOrderMapper.updateOrderStatusById(order.getId(), CommonEnum.REFUND_SUCCESS.getCode());
            }
            //记录退款日志
            orderLog = CommonUtil.constructOrderLog(order.getOrderNo(), responseData.getReturn_msg(),
                    null, order.getId());

        } else {
            //记录退款失败日志
            orderLog = CommonUtil.constructOrderLog(order.getOrderNo(), "微信退款失败",
                    null, order.getId());
        }
        logClient.addOrderLog(orderLog);
    }

    /**
     * 查询未付款订单，校验微信支付结果
     */
    @Scheduled(fixedRate = 4 * 60 * 60 * 1000)
    public void findUnPayOrder() {
        List<Order> page = wxOrderMapper.findUnPayOrder();
        if (page == null || page.size() == 0) {
            return;
        }
        page.forEach(order -> {
            if (order.getWxOrderNo() != null) {
                queryOrder(order.getId(), order.getWxOrderNo(), null);
            } else if (order.getOrderNo() != null) {
                queryOrder(order.getId(), null, order.getOrderNo());
            }
        });
    }

    /**
     * 商户订单号
     *
     * @param orderId   订单id
     * @param orderNo   订单号
     * @param wxOrderNo 微信订单号
     */
    private void queryOrder(Long orderId, String wxOrderNo, String orderNo) {
        Map<String, Object> data = new HashMap<>(16);
        data.put("appid", WxPayConstant.APP_ID);
        data.put("mch_id", WxPayConstant.MCHID);
        if (wxOrderNo != null) {
            data.put("transaction_id", wxOrderNo);
        } else {
            data.put("out_trade_no", orderNo);
        }
        data.put("nonce_str", WxUtil.createRandom(false, 32));
        String sign = WxSignUtil.sort(data);
        sign = WxUtil.md5(sign).toUpperCase();
        data.put("sign", sign);
        String param = XmlUtil.mapConvertToXML(data);
        String result = WxUtil.httpsRequest(WxPayConstant.WECHAT_ORDER_QUERY_URL, "POST", param);
        OrderQueryResponseData response = XmlUtil.castXMLStringToOrderQueryResponseData(result);
        updateLocalStatus(orderId, response);
    }

    /**
     * @param orderId  订单id
     * @param response 微信返回
     */
    private void updateLocalStatus(Long orderId, OrderQueryResponseData response) {
        if (orderId == null || response.getTrade_state() == null) {
            return;
        }
        if (CommonEnum.SUCCESS.getCode().equals(response.getTrade_state())) {
            //SUCCESS—支付成功
            UnifiedOrderNotifyRequestData data = new UnifiedOrderNotifyRequestData();
            data.setTransaction_id(response.getTransaction_id());
            data.setOut_trade_no(response.getOut_trade_no());
            data.setTime_end(response.getTime_end());
            payClient.callback(data);
        }
    }
}
