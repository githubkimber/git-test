package com.itheima.cglib;




import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import java.lang.reflect.Method;


/*
模拟一个消费者
 */
public class Client {

    public static void main(String[] args) {
        final Producer producer = new Producer();

        //    producer.saleProduct(10000f);

        /*
        动态代理:
        特点: 字节码随用随创建,随用随加载
        作用:不修改源码的基础上对方法增强
        分类:
             基于接口的动态代理
             基于子类的动态代理
        基于接口的动态代理:
            涉及的类: Proxy
            提供者: JDK官方
        如何创建代理对象:
            使用Proxy类中的newProxyInstance方法
        创建代理对象的要求:
            被代理类最少实现一个接口,如果没有则不能使用
        newProxyInstance方法的参数:
            Classloader: 类加载器
                它是用于加载代理对象字节码的,和被代理对象使用相同的类加载器;
                写法固定: xxx.getClass().getClassloader(); (注:xxx表示代理对象名)
            Class[]: 字节码数组
                它是用于让代理对象和被代理对象有相同方法.
                写法固定: xxx.getClass().getInterfaces(); (注:xxx表示代理对象名)
            InvocationHandler: 用于提供增强的代码
                它是让我们写如何代理,我们一般都是些一个该接口的实现类,通常情况下都是匿名内部类,
                但不是必须的,此接口的实现类都是谁用谁写.

                基于子类的动态代理:
            涉及的类: Enhancer
            提供者: 第三方cglib库
        如何创建代理对象:
            使用Enhancer类中的create方法
        创建代理对象的要求:
            被代理类不能是最终类
        create方法的参数:
            Class: 字节码
                它是用于指定被代理对象的字节码.

            Callback: 用于提供增强的代码
                它是让我们写如何代理,我们一般都是些一个该接口的实现类,通常情况下都是匿名内部类,
                但不是必须的,此接口的实现类都是谁用谁写.\
            我们一般写的都是该接口的子接口实现类: MethodInterceptor (方法拦截)
         */

         Producer cglibProducer = (Producer)Enhancer.create(producer.getClass() , new MethodInterceptor(){
            /*
            执行被代理对象的任何方法都会经过该方法
            proxy
            method
            args
            以上三个参数和基于接口的动态代理中invoke方法的参数是一样的
            methodProxy: 当前执行方法的代理对象
            return
            throwable
             */
            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            // 1.提供增强的代码
            Object returnValue = null;
            // 2.获取方法执行参数
            Float money = (Float) args[0];
            // 3.判断当前方法是不是销售
            if ("saleProduct".equals(method.getName())) {
                returnValue = method.invoke(producer, money * 0.8f);
            }
            return returnValue;

        }
    });
    cglibProducer.saleProduct(12000f);
}
}