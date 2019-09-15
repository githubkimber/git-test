package cn.itcast.controller;

import cn.itcast.domain.Account;
import cn.itcast.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/*
账户web
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService ;

    /*
    查询所有用户信息
     */
    @RequestMapping("/findall")
    public String findall(Model model){
        System.out.println("表现层: 查询所有账户...!");
        // 调用Service的方法
        List<Account> list = accountService.findall();
        model.addAttribute("list",list) ;
        return "list" ;
    }


    /*
    保存信息1
     */
    @RequestMapping("/save")
    public String save(Account account){
     accountService.saveAccount(account) ;
        return "list" ;
    }

//    /*
//   保存信息2
//    */
//    @RequestMapping("/save")
//    public void save(Account account, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        accountService.saveAccount(account) ;
//        response.sendRedirect(request.getContextPath()+"/account/findall");
//        return ;
//    }
}
