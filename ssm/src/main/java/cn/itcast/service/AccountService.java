package cn.itcast.service;

import cn.itcast.domain.Account;

import java.util.List;

/*
账户dao接口
 */
public interface AccountService {

    // 查询所有账户
    public List<Account> findall() ;

    // 保存账户信息
    public void saveAccount(Account account) ;
}
