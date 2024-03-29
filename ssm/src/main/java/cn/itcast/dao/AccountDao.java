package cn.itcast.dao;

import cn.itcast.domain.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
账户dao接口
 */
@Repository
public interface AccountDao {

    // 查询所有账户
    @Select("select * from account")
    public List<Account> findall() ;

    // 保存账户信息
    @Insert("insert into account (name,money) value (#{name},#{money})")
    public void saveAccount(Account account) ;
}
