package com.pinyougou.pay.service;

import java.util.Map;

/*
生成二维码
 */
public interface WeixinPayService {

    /*
    生成二维码
     */
    public Map createNative(String out_trade_no, String total_fee);

    /*
    查询支付订单状态
     */
    public Map queryPayStatus(String out_trade_no);

    /*
    关闭订单
     */
    public Map closePay(String out_trade_no);
}
