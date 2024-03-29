package com.pinyougou.search.service;

import java.util.List;
import java.util.Map;

public interface ItemSearchService {

    /*
    搜索方法
     */
    public Map search(Map searchMap);

    /*
    导入列表
     */
    public void importList(List list);

    /*
    删除商品列表
    goodsIds (SPU)
     */
    public void deleteByGoodsIds(List goodsIds);
}
