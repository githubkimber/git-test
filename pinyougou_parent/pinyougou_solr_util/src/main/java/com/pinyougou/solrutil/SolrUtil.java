package com.pinyougou.solrutil;

import com.alibaba.fastjson.JSON;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import com.pinyougou.pojo.TbItemExample.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.List;


@Component  // 若不写值,默认将类名首字母小写后为bean的Id;
public class SolrUtil {

    @Autowired
    private TbItemMapper itemMapper ;

    @Autowired
    private SolrTemplate solrTemplate ;

    public void importItemData(){
        TbItemExample example = new TbItemExample();
        Criteria criteria = example.createCriteria() ;
        criteria.andStatusEqualTo("1") ;    // 审核通过才导入
        // 根据条件查询
        List<TbItem> itemList = itemMapper.selectByExample(example);

        System.out.println("---商品列表---");
        for (TbItem item:itemList){
            System.out.println(item.getId()+"   "+item.getTitle()+" "+item.getPrice());
            // 从数据库中提取规格json字符串转换为map
            Map specMap = JSON.parseObject(item.getSpec(), Map.class) ;
            item.setSpecMap(specMap) ;
        }

        solrTemplate.saveBeans(itemList);
        solrTemplate.commit();

        System.out.println("---结束---");
    }

    public static void main(String[] args) {                    // 前一个*:搜索jar包下工程文件; 第二个*:是省略applicationContext*后缀名;
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml") ;
        SolrUtil solrUtil = (SolrUtil)context.getBean("solrUtil");
        solrUtil.importItemData();
    }
}
