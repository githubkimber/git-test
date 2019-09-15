package cn.itcast.demo.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class UserDetailServiceImpl implements UserDetailsService{

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        System.out.println("经过认证类:"+username);
        // 创建角色列表
        List<GrantedAuthority> authorities = new ArrayList() ;
        // 添加角色
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        // 返回用户名,密码,角色列表;
        return new User(username,"",authorities);
    }
}

