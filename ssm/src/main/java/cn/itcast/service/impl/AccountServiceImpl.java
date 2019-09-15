package cn.itcast.service.impl;

import cn.itcast.dao.AccountDao;
import cn.itcast.domain.Account;
import cn.itcast.service.AccountService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("accountService")
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountDao accountDao ;

    @Override
    public List<Account> findall() {
        System.out.println("业务层: 查询所有账户...!");
        return accountDao.findall();
    }

    @Override
    public void saveAccount(Account account) {
        System.out.println("业务层: 保存账户...!");
        accountDao.saveAccount(account);
    }
}
