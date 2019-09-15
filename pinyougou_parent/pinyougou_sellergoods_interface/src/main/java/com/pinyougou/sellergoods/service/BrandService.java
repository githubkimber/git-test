package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbBrand;
import entity.PageResult;

import java.util.List;
import java.util.Map;

/*
品牌接口
 */
public interface BrandService {

    public List<TbBrand> findAll();

    /*
    品牌分页
     */
    public PageResult findPage(int pageNum, int pageSize) ;

    /*
    增加品牌
     */
    public void add(TbBrand brand) ;

    /*
    根据ID查询实体
     */
    public TbBrand findOne(Long id);

    /*
    修改品牌内容
     */
    public void update(TbBrand brand);

    /*
    删除品牌内容
     */
    public void delete(Long[] ids) ;

    /*
    品牌分页
     */                        // 品牌信息;        当前页面,    每页记录数;
    public PageResult findPage(TbBrand brand, int pageNum, int pageSize) ;

    /*
    返回下拉列表数据
     */
    public List<Map> selectOptionList() ;
}
