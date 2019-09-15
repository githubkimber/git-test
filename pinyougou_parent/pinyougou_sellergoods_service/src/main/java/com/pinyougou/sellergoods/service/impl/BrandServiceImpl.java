package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbBrandExample;
import com.pinyougou.pojo.TbBrandExample.Criteria;
import com.pinyougou.sellergoods.service.BrandService;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {

    @Autowired
    private TbBrandMapper brandMapper;

    @Override
    public List<TbBrand> findAll() {

        return brandMapper.selectByExample(null);   // 传回全部列表
    }

    /*
    品牌分页
     */
    // 当前页面,    每页记录数;
    public PageResult findPage(int pageNum, int pageSize) {
        // 自动分页
        PageHelper.startPage(pageNum, pageSize);
        // 传回全部数据
        Page<TbBrand> page = (Page<TbBrand>) brandMapper.selectByExample(null);

        // 返回数据
        return new PageResult(page.getTotal(), page.getResult());
    }

    /*
    增加品牌
     */
    @Override
    public void add(TbBrand brand) {

        brandMapper.insert(brand);
    }

    /*
    根据id查询
     */
    @Override
    public TbBrand findOne(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    /*
    修改品牌内容
     */
    @Override
    public void update(TbBrand brand) {
        brandMapper.updateByPrimaryKey(brand);
    }

    /*
    删除品牌内容
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            brandMapper.deleteByPrimaryKey(id);
        }
    }

    /*
    查询品牌内容
     */
    @Override               // brand: 品牌信息      当前页面,    每页记录数;
    public PageResult findPage(TbBrand brand, int pageNum, int pageSize) {
        // 自动分页
        PageHelper.startPage(pageNum, pageSize) ;
        // 创造条件
        TbBrandExample example = new TbBrandExample() ;
        // 构建条件
        Criteria criteria = example.createCriteria() ;
        if (brand!=null){
            if (brand.getName()!=null && brand.getName().length()>0){
                // 根据名称模糊查询
                criteria.andNameLike("%"+brand.getName()+"%") ;
            }
            if (brand.getFirstChar()!=null && brand.getFirstChar().length()>0){
                // 根据首字母模糊查询
                criteria.andFirstCharLike("%"+brand.getFirstChar()+"%") ;
            }
        }
        // 传回全部数据
        Page<TbBrand> page = (Page<TbBrand>) brandMapper.selectByExample(example) ;
        // 返回数据
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<Map> selectOptionList() {
        return brandMapper.selectOptionList();
    }
}