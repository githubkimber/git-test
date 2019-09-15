package com.pinyougou.seckill.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pay.service.WeixinPayService;
import com.pinyougou.pojo.TbSeckillOrder;
import com.pinyougou.seckill.service.SeckillOrderService;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pay")
public class PayController {

    @Reference
    private WeixinPayService weixinPayService;

    @Reference
    private SeckillOrderService seckillOrderService;

    @RequestMapping("/createNative")
    public Map crateNative(){

        // 1.获取当前登录用户
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // 2.从缓存中提取支付日志
        TbSeckillOrder seckillOrder = seckillOrderService.searchOrderFromRedisByUserId(username);
        if (seckillOrder!=null){
            return weixinPayService.createNative(seckillOrder.getId()+"", (long)(seckillOrder.getMoney()*100)+"");
        }else{
            return new HashMap<>();
        }
    }

    @RequestMapping("/queryPayStatus")
    public Result queryPayStatus(String out_trade_no){

        // 1.获取当前登录用户
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Result result = null ;
        int x=0;
        while(true){
            // 调用查询
            Map<String, String> map = weixinPayService.queryPayStatus(out_trade_no);
            System.out.println("map:"+map);

            if (map==null){
                result = new Result(false, "支付发生错误");
                break;
            }
            if (map.get("trade_state").equals("SUCCESS")){
                result = new Result(true, "支付成功");

               // 保存订单
                seckillOrderService.saveOrderFromRedisTodb(username, Long.valueOf(out_trade_no), map.get("transaction_id"));
                break;
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            x++;
            if (x>=4){
                result=new Result(false, "二维码超时");
                // 关闭支付
                Map payResult = weixinPayService.closePay(out_trade_no);
                if (payResult!=null && "Fall".equals(payResult.get("return_code"))){
                    if ("ORDERPAID".equals(payResult.get("err_code"))){
                        result = new Result(true, "支付成功");
                        // 保存订单
                        seckillOrderService.saveOrderFromRedisTodb(username, Long.valueOf(out_trade_no), map.get("transaction_id") );
                    }
                }
                if (result.isSuccess()==false){
                    // 删除订单
                    seckillOrderService.deleteOrderFromRedis(username, Long.valueOf(out_trade_no));
                }
                break;
            }
        }
        return result ;
    }
}
