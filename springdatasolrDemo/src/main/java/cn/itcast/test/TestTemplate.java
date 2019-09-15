package cn.itcast.test;

import cn.itcast.pojo.TbItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SolrDataQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-solr.xml")
public class TestTemplate {

    @Autowired
    private SolrTemplate solrTemplate;

    @Test   // 添加搜索内容
    public void testAdd(){

        TbItem item = new TbItem() ;
        item.setId(1L);
        item.setTitle("华为METE10");
        item.setCategory("手机");
        item.setBrand("华为");
        item.setSeller("华为旗舰店");
        item.setGoodsId(100L);
        item.setPrice(new BigDecimal(3000.01));

        solrTemplate.saveBean(item) ;
        solrTemplate.commit();
    }

    @Test
    public void findById(){
        // 根据主键值查询
        TbItem item = solrTemplate.getById(1L,TbItem.class) ;
        System.out.println(item.getTitle());
    }

    @Test   // 根据主键值删除
    public void deleteById(){
        solrTemplate.deleteById("1") ;
        solrTemplate.commit();
    }

    @Test
    public void testAddList(){
        List list = new ArrayList() ;
        for(int i=0 ; i<100 ; i++){
            TbItem item = new TbItem() ;
            item.setId(1L+i);
            item.setTitle("华为METE");
            item.setCategory("手机");
            item.setBrand("华为"+i);
            item.setSeller("华为旗舰店"+i);
            item.setGoodsId(100L);
            item.setPrice(new BigDecimal(3000.01+i));
            list.add(item) ;
        }
        solrTemplate.saveBeans(list) ;
        solrTemplate.commit();
    }

    @Test
    public void estPageQuery(){
        // 构造查询对象 (前面的*:查询全部的字段,  后面的*:查询全部的值)
        Query query = new SimpleQuery("*:*") ;
        Criteria criteria = new Criteria("item_category").contains("手机") ;  //查询字段(手机)
        criteria=criteria.and("item_brand").contains("2");
        query.addCriteria(criteria) ;

        // 开始索引位置
      //  query.setOffset(0) ;
        //每页记录数
       // query.setRows(30) ;
        //分页查询方法,第一个参数:查询对象; 第二个参数:
        ScoredPage<TbItem> page = solrTemplate.queryForPage(query,TbItem.class);
        for (TbItem item:page.getContent()){
            System.out.println(item.getTitle()+"    "+item.getPrice()+" "+item.getBrand());
        }
        System.out.println("总记录数"+page.getTotalElements());
        System.out.println("总页数"+page.getTotalPages());
    }

    @Test
    public void deleteAll(){
        Query query = new SimpleQuery("*:*");
        solrTemplate.delete(query) ;
        solrTemplate.commit() ;
    }
}
