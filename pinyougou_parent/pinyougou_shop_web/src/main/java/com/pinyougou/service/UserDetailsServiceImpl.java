package com.pinyougou.service;

import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/*
认证类
 */
public class UserDetailsServiceImpl implements UserDetailsService{

    private SellerService sellerService ;

    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("经过了UserDetailsServiceImpl.......!!!");
        // 构建一个角色列表; GrantedAuthority: 角色;
        List<GrantedAuthority> grantAuths=new ArrayList();
        // 添加一个角色;
        grantAuths.add(new SimpleGrantedAuthority("ROLE_SELLER"));
        // 根据输入的用户名查寻得到商家对象
        TbSeller seller = sellerService.findOne(username) ;
        // 判断该输入的商家对象是否存在
        if (seller!=null){
            // 判断其是否通过审核
            if (seller.getStatus().equals("1")){
                return new User(username,seller.getPassword(),grantAuths) ;
            }else{
             return null ;
            }
        }else{
            return null ;
        }

    }
}