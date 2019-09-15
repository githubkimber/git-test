package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;

import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;


import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.*;
import org.springframework.data.solr.core.query.result.HighlightEntry.Highlight;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(timeout = 5000)
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private SolrTemplate solrTemplate;

    /*
    关键字查询
     */
    @Override
    public Map search(Map searchMap) {

        Map map = new HashMap();

        // 空格处理
        String keywords = (String)searchMap.get("keywords");
        // 关键字去空格
        searchMap.put("keywords",keywords.replace(" ","")) ;

        // 1.查询列表
        map.putAll(searchList(searchMap));
        // 2.分组查询商品分类列表
        List<String> categoryList = searchCategoryList(searchMap);
        map.put("categoryList", categoryList);
        // 3.查询品牌和规格列表
        String category = (String) searchMap.get("category");
        if (!category.equals("")) {
            map.putAll(searchBrandAndSpecList(category));
        } else {
            if (categoryList.size() > 0) {
                map.putAll(searchBrandAndSpecList(categoryList.get(0)));
            }
        }
        // 返回结果
        return map;


    }

    @Override
    public void importList(List list) {
        solrTemplate.saveBeans(list) ;
        solrTemplate.commit();
    }

    @Override
    public void deleteByGoodsIds(List goodsIds) {
        // 创建query(查询对象)
        Query query = new SimpleQuery("*:*") ;
        // 创建criteria(查询方式) (查某字段的某关键字)item_goodsid
        Criteria criteria = new Criteria("item_goodsid").in(goodsIds);
        // 将criteria(查询方式)添加到query(查询对象)中
        query.addCriteria(criteria);
        // 删除query(查询对象)
        solrTemplate.delete(query) ;
        solrTemplate.commit();
    }

    // 查询列表
    private Map searchList(Map searchMap) {

        Map map = new HashMap();
        // 高亮显示
        HighlightQuery query = new SimpleHighlightQuery();
        // 创建一个高亮显示对象确定一个域(字段)
        HighlightOptions highlightOptions = new HighlightOptions().addField("item_title");
        // 设置显示对象的前缀
        highlightOptions.setSimplePrefix("<em style='color:red'>");
        // 设置显示对象的后缀
        highlightOptions.setSimplePostfix("</em>");
        // 把这个高亮显示对象给查询对象
        query.setHighlightOptions(highlightOptions);


        // 1.1某域的某关键字查询
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);

        // 1.2按商品分类过滤
        // 判断用户是否选择了商品分类
        if (!"".equals(searchMap.get("category"))) {
            // 构建过滤查询对象
            FilterQuery filterQuery = new SimpleFilterQuery();
            Criteria filterCriteria = new Criteria("item_category").is(searchMap.get("category"));
            // 添加过滤查询小对象
            filterQuery.addCriteria(filterCriteria);
            // 添加过滤查询大对象 (filterCriteria是filterQuery的子集)
            query.addFilterQuery(filterQuery);
        }

        // 1.3按品牌分类过滤
        // 判断用户是否选择了品牌分类
        if (!"".equals(searchMap.get("brand"))) {
            // 构建过滤查询对象
            FilterQuery filterQuery = new SimpleFilterQuery();
            Criteria filterCriteria = new Criteria("item_brand").is(searchMap.get("brand"));
            // 添加过滤查询小对象
            filterQuery.addCriteria(filterCriteria);
            // 添加过滤查询大对象 (filterCriteria是filterQuery的子集)
            query.addFilterQuery(filterQuery);
        }

        // 1.4按规格过滤
        if (searchMap.get("spec") != null) {
            // 转成Map对象
            Map<String, String> specMap = (Map<String, String>) searchMap.get("spec");
            for (String key : specMap.keySet()) {
                // 构建过滤查询对象
                FilterQuery filterQuery = new SimpleFilterQuery();
                // "item_brand_"+key:为动态域 可以是动态的;
                Criteria filterCriteria = new Criteria("item_spec_" + key).is(specMap.get(key));
                // 添加过滤查询小对象
                filterQuery.addCriteria(filterCriteria);
                // 添加过滤查询大对象 (filterCriteria是filterQuery的子集)
                query.addFilterQuery(filterQuery);
            }
        }

        // 1.5按价格过滤
        // 价格不为空字符串
        if (!"".equals(searchMap.get("price"))) {
            String priceStr = (String) searchMap.get("price");    // 500-1000
            String[] price = priceStr.split("-");       // split() :字符串分隔符方法;
            // 下限判断: 如果最低价格不等于0
            if (!price[0].equals("0")) {
                // 构建过滤查询对象
                FilterQuery filterQuery = new SimpleFilterQuery();  //          greaterThanEqual 大于等于
                Criteria filterCriteria = new Criteria("item_price").greaterThanEqual(price[0]);
                // 添加过滤查询小对象
                filterQuery.addCriteria(filterCriteria);
                // 添加过滤查询大对象 (filterCriteria是filterQuery的子集)
                query.addFilterQuery(filterQuery);
            }
            // 上限判断: 如果最高价格不等于0
            if (!price[1].equals("*")) {
                // 构建过滤查询对象
                FilterQuery filterQuery = new SimpleFilterQuery();  //          lessThanEqual 小于等于
                Criteria filterCriteria = new Criteria("item_price").lessThanEqual(price[1]);
                // 添加过滤查询小对象
                filterQuery.addCriteria(filterCriteria);
                // 添加过滤查询大对象 (filterCriteria是filterQuery的子集)
                query.addFilterQuery(filterQuery);
            }
        }

        // 1.6分页
        // 获取页码
        Integer pageNo = (Integer) searchMap.get("pageNo");
        // 判断页码是否为空
        if (pageNo==null){
            pageNo=1 ;
        }
        // 获取页大小(每页记录数)
        Integer pageSize = (Integer) searchMap.get("pageSize");
        if (pageSize==null){
            pageSize=20 ;
        }
        // 起始索引
        query.setOffset((pageNo-1)*pageSize);
        //  每页记录数
        query.setRows(pageSize) ;

        // 1.7按字段(如价格)排序
        // 获取升序ASC, 降序DESC;
        String sortValue = (String) searchMap.get("sort") ;
        //获取排序字段
        String sortField = (String) searchMap.get("sortField") ;
        if (sortValue!=null && !sortValue.equals("")){
            // 如果为升序
            if (sortValue.equals("ASC")){
                // 创建排序对象,对排序进行封装, Sort.Direction.ASC:升序, item_price:(价格字段)按价格排序
                Sort sort = new Sort(Sort.Direction.ASC, "item_"+sortField) ;
                query.addSort(sort);
            }
            // 如果为降序
            if (sortValue.equals("DESC")){
                // 创建排序对象,对排序进行封装, Sort.Direction.ASC:升序, item_price:(价格字段)按价格排序
                Sort sort = new Sort(Sort.Direction.DESC, "item_"+sortField) ;
                query.addSort(sort);
            }
        }




            //*********获取高亮结果集********
            //返回高亮页对象
            HighlightPage<TbItem> page = solrTemplate.queryForHighlightPage(query, TbItem.class);
            // 通过高亮入口集合获得高亮结果; (每条记录的高亮的入口)
            List<HighlightEntry<TbItem>> entryList = page.getHighlighted();
            for (HighlightEntry<TbItem> entry : entryList) {
                // 获取高亮列表(域(字段)列表)
                List<Highlight> highlightList = entry.getHighlights();
//            for (Highlight h : highlightList) {
//                // 每个域(字段)存储的是多个值(或字符串)
//                List<String> sns = h.getSnipplets();
//                System.out.println(sns);

                // 判断域列表和域中值列表中是否有元素
                if (highlightList.size() > 0 && highlightList.get(0).getSnipplets().size() > 0) {
                    // 获取实体
                    TbItem item = entry.getEntity();
                    // 获得集合的第一个元素加入实体
                    item.setTitle(highlightList.get(0).getSnipplets().get(0));
                }
            }
            map.put("rows", page.getContent());     //rous为自定义的键
            map.put("totalPages",page.getTotalPages()); //总页数
            map.put("total",page.getTotalElements());    // 总记录数
            return map;
        }

    /*
    分组查询(查询商品分类列表)
     */
        private List<String> searchCategoryList (Map searchMap){
            List<String> list = new ArrayList();

            Query query = new SimpleQuery("*:*");
            // 根据关键字查询 (相当于sql语句中的where条件查询)
            Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
            query.addCriteria(criteria);

            // 根据域(字段)名称来分组 (相当于sql语句中的group by分组条件查询)
            GroupOptions groupOptions = new GroupOptions().addGroupByField("item_category");
            query.setGroupOptions(groupOptions);
            // 获取分组页对象
            GroupPage<TbItem> page = solrTemplate.queryForGroupPage(query, TbItem.class);
            // 获取分组结果对象 (一个分组页可以有多个分组结果)
            GroupResult<TbItem> groupResult = page.getGroupResult("item_category");
            // 获取分组入口页
            Page<GroupEntry<TbItem>> groupEntries = groupResult.getGroupEntries();
            // 获取分组页入口集合
            List<GroupEntry<TbItem>> entryList = groupEntries.getContent();
            for (GroupEntry<TbItem> entry : entryList) {
                // 将分组的结果添加到返回值中
                list.add(entry.getGroupValue());
            }
            return list;
        }

        @Autowired
        private RedisTemplate redisTemplate;

    /*
    根据商品分类名称查询品牌和规格列表
    category: 商品分类列表
     */
        private Map searchBrandAndSpecList (String category){
            Map map = new HashMap();
            // 1.根据商品分类名称得到模板Id
            Long templateId = (Long) redisTemplate.boundHashOps("itemCat").get(category);
            if (templateId != null) {
                // 2.根据模板Id获取品牌列表
                List brandList = (List) redisTemplate.boundHashOps("brandList").get(templateId);
                map.put("brandList", brandList);
                System.out.println("品牌列表条数:" + brandList.size());

                List specList = (List) redisTemplate.boundHashOps("specList").get(templateId);
                // 3.根据模板Id获取规格列表
                map.put("specList", specList);
                System.out.println("规格列表条数:" + specList.size());
            }

            return map;
        }

    }



