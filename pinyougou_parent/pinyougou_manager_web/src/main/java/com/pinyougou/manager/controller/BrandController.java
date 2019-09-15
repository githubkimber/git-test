package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController     // 相当于@controller和@ResposeBody的结合远程与本地都注入
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService ;

    @RequestMapping("/findAll")
    public List<TbBrand> findAll(){
        return brandService.findAll() ;
    }

    @RequestMapping("/findPage")
    public PageResult findPage(int page, int rows){
        return brandService.findPage(page , rows) ;
    }

    @RequestMapping("/add")
    public Result add(@RequestBody TbBrand brand){    // @RequestBody: 获取键值对形式的请求体
     try {
         brandService.add(brand);
         return new Result(true, "新增成功!");
     }catch(Exception e){
         e.printStackTrace();
         return new Result(false, "新增失败!");
     }
    }

    @RequestMapping("/findOne")
    public TbBrand findOne(Long id){
        return brandService.findOne(id) ;
    }

    @RequestMapping("/update")
    public Result update(@RequestBody TbBrand brand){
        try{
            brandService.update(brand);
            return new Result(true,"修改成功!") ;
        }catch(Exception e){
            e.printStackTrace();
            return new Result(false,"修改失败!") ;
        }
    }

    @RequestMapping("/delete")
    public Result delete(Long[] ids){
        try{
            brandService.delete(ids);
            return new Result(true, "删除成功") ;
        }catch(Exception e){
            e.printStackTrace();
            return new Result(false,"删除失败") ;
        }
    }

     /*
    查询品牌内容
     */
    @RequestMapping("/search")       // brand: 品牌信息; 当前页面; 每页记录数;
    public PageResult search(@RequestBody TbBrand brand, int page, int rows){
        return brandService.findPage(brand, page, rows) ;
    }

    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList() {
        return brandService.selectOptionList();
    }
}
