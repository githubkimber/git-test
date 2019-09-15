package com.pinyougou.cart.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.pinyougou.cart.service.CartService;
import com.pinyougou.pojogroup.Cart;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Reference(timeout = 6000)
    private CartService cartService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @RequestMapping("/findCartList")
    public List<Cart> findCartList() {

        // 当前登录人账号
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("当前登录人:" + username);

        String cartListString = util.CookieUtil.getCookieValue(request, "cartList", "utf-8");
        if (cartListString == null || ("null").equals(cartListString)|| "".equals(cartListString)) {
            cartListString = "[]";// 转换成JSON字符串;
        }
        List<Cart> cartList_cookie = JSON.parseArray(cartListString, Cart.class);

        System.out.println("cartList_cookie:"+cartList_cookie);
        System.out.println("2号cartListString:"+cartListString);
        System.out.println("Cart.class:"+Cart.class);
        // 如果未登录
        if (username.equals("anonymousUser")) {
                // 从cookie中提取购物车 (cookie只能存取字符串不能存取对象,可以用JSON转换)
                System.out.println("从cookie中提取购物车");

                return cartList_cookie;
        } else {    // 如果已登录
                // 获取redies购物车
                List<Cart> cartList_redis = cartService.findCartListFromRedis(username);
                // 判断本地购物车当中是否还存在数据
                if (cartList_cookie.size()>0){
                    // 合并购物车
                    List<Cart> cartList = cartService.mergeCartList(cartList_cookie, cartList_redis);
                    // 将合并后的购物车存入redis
                    cartService.saveCartListToRedis(username, cartList);
                    // 清除本地购物车
                    util.CookieUtil.deleteCookie(request,response,"cartList");
                    System.out.println("执行了合并购物车的逻辑!");
                    return cartList ;
                }
                return cartList_redis;
        }
    }


    @RequestMapping("/addGoodsToCartList")
    @CrossOrigin(origins = "http://localhost:9105",allowCredentials = "true")
    public Result addGoodsToCartList(Long itemId, Integer num){

        // 可以访问的域 "http://localhost:9105"
        //response.setHeader("Access-Control-Allow-Origin","http://localhost:9105");
        // 如果操作cookie,必须配置Access-Control-Allow-Creadentials为true,且必须明确配置要跨域访问的地址,不能只有*替代,
        //response.setHeader("Access-Control-Allow-Creadentials","true");

        // 当前登录人账号
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("当前登录人:" + name);

     try {
         // 从cookie中提取购物车
         List<Cart> cartList = findCartList();
         // 调用服务方法操作方法 覆盖旧的cartList
         cartList = cartService.addGoodsToCartList(cartList, itemId, num);

         // 如果未登录
         if (name.equals("anonymousUser")) {
             // 将新的购物车存入cookieist
             String cartListString = JSON.toJSONString(cartList);
             System.out.println("1号cartListString:"+cartListString);
             //                                                                          cookie的生命周期
             util.CookieUtil.setCookie(request,response, "cartList", cartListString, 3600 * 24, "utf-8");
             System.out.println("向cookie中存储购物车");
         }else{     // 如果登录
             cartService.saveCartListToRedis(name, cartList);
         }
         return new Result(true, "存入购物车成功!");
     }catch(Exception e){
         e.printStackTrace();
         return new Result(false, "存入购物车失败!");
     }
   }
}
