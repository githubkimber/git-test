package com.pinyougou.page.service.impl;

import com.pinyougou.mapper.TbGoodsDescMapper;
import com.pinyougou.mapper.TbGoodsMapper;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.page.service.ItemPageService;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import com.pinyougou.pojo.TbItemExample.Criteria;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ItemPageServiceImpl implements ItemPageService {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${pagedir}")
    private String pagedir;

    @Autowired
    private TbGoodsMapper goodsMapper;

    @Autowired
    private TbGoodsDescMapper goodsDescMapper ;

    @Autowired
    private TbItemCatMapper itemCatMapper ;

    @Autowired
    private TbItemMapper itemMapper ;

    @Override
    public boolean genItemHtml(Long goodsId) {

        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        try {
            Template template = configuration.getTemplate("item.ftl");
            // 创建数据模型
            Map dataModel = new HashMap<>() ;
            // 1.1根据主键查询商品主表数据
            TbGoods goods = goodsMapper.selectByPrimaryKey(goodsId) ;
            // 1.1装入数据模型
            dataModel.put("goods",goods);

            // 2.1根据主键查询商品扩展表数据
            TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId) ;
            dataModel.put("goodsDesc",goodsDesc);

            // 3.读取商品分类
            String itemCat1 = itemCatMapper.selectByPrimaryKey(goods.getCategory1Id()).getName();
            String itemCat2 = itemCatMapper.selectByPrimaryKey(goods.getCategory2Id()).getName();
            String itemCat3 = itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName();
            dataModel.put("itemCat1",itemCat1);
            dataModel.put("itemCat2",itemCat2);
            dataModel.put("itemCat3",itemCat3);

            // 4.读取SKU列表数据
            TbItemExample example = new TbItemExample();
            Criteria criteria = example.createCriteria();
            criteria.andGoodsIdEqualTo(goodsId);    // 根据SPU的Id(个goodsId)来查询
            criteria.andStatusEqualTo("1"); // 设置状态有效
            example.setOrderByClause("is_default desc");  // 按是否默认字段进行降序排序,目的是返回的结果第一条为默认SKU
            List<TbItem> itemList = itemMapper.selectByExample(example);
            dataModel.put("itemList",itemList);

        // 生成文件目录
            Writer out = new PrintWriter(pagedir+goodsId+".html","utf-8");

            template.process(dataModel,out);
            out.close();
            return true ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteItemHtml(Long[] goodsIds) {
        System.out.println("你好啊!!!123");

     try {
         for (Long goodsId : goodsIds) {
        System.out.println( pagedir+"  "+"OK"+goodsId+"   "+"你好啊!!!123");
             new File(pagedir + goodsId + ".html").delete();
         }
         return true;
     }catch(Exception e){
         e.printStackTrace();
         return false;
     }
    }
}
