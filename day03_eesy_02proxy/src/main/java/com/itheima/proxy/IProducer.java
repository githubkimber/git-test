package com.itheima.proxy;

/*
对生产厂家要求的接口
 */
public interface IProducer {

    /*
   销售
    */
    public void saleProduct(float money);



    /*
    售后
     */
    public void afterService(float money);
}
