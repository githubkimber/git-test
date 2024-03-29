package com.pinyougou.sellergoods.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.pinyougou.mapper.*;
import com.pinyougou.pojo.*;
import com.pinyougou.pojogroup.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.pojo.TbGoodsExample.Criteria;
import com.pinyougou.sellergoods.service.GoodsService;

import entity.PageResult;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private TbGoodsMapper goodsMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbGoods> findAll() {
		return goodsMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbGoods> page=   (Page<TbGoods>) goodsMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Autowired
	private TbGoodsDescMapper goodsDescMapper ;

	@Autowired
	private TbItemMapper itemMapper ;

	@Autowired
	private TbItemCatMapper itemCatMapper ;

	@Autowired
	private TbBrandMapper brandMapper ;

	@Autowired
	private TbSellerMapper sellerMapper ;

	/**
	 * 增加
	 */
	@Override
	public void add(Goods goods) {
		goods.getGoods().setAuditStatus("0");	//设置初始状态:未审核;
		goodsMapper.insert(goods.getGoods());	//引入商品基本信息;
		goods.getGoodsDesc().setGoodsId(goods.getGoods().getId());	//将商品基本表的ID给商品扩展表;
		goodsDescMapper.insert(goods.getGoodsDesc()) ;	//引入商品扩展表数据;
		saveItemList(goods) ;	// // 插入SKU列表数据
	}

	private void setItemValues(TbItem item, Goods goods){

        // 商品分类
        item.setCategoryid(goods.getGoods().getCategory3Id());	//三级分类Id
        // 创建日期
        item.setCreateTime(new Date());
        // 更新日期
        item.setUpdateTime(new Date());
        // 商品Id
        item.setGoodsId(goods.getGoods().getId());
        // 商家Id
        item.setSellerId(goods.getGoods().getSellerId());
        // 分类名称
        TbItemCat itemCat = itemCatMapper.selectByPrimaryKey(goods.getGoods().getCategory3Id());
        item.setCategory(itemCat.getName());
        // 品牌名称
        TbBrand brand = brandMapper.selectByPrimaryKey(goods.getGoods().getBrandId()) ;
        item.setBrand(brand.getName());
        // 商家名称(店铺名称)
        TbSeller seller = sellerMapper.selectByPrimaryKey(goods.getGoods().getSellerId());
        item.setSeller(seller.getNickName());
        // 图片
        List<Map> imageList = JSON.parseArray(goods.getGoodsDesc().getItemImages(),Map.class);
        if (imageList.size()>0){
            item.setImage((String)imageList.get(0).get("url"));
        }

    }

    // 插入SKU列表数据
    private void saveItemList(Goods goods){

		if ("1".equals(goods.getGoods().getIsEnableSpec())){
			for (TbItem item: goods.getItemList()){
				// 构建标题 SPU名称+规格选项值
				String title = goods.getGoods().getGoodsName();	// SPU名称
				Map<String,Object> map = JSON.parseObject(item.getSpec());
				for (String key : map.keySet()){
					title+=" "+map.get(key);
				}
				item.setTitle(title);

				setItemValues(item, goods);

				// 保存
				itemMapper.insert(item);
			}
		}else{
			// 没有启用规格
			TbItem item = new TbItem() ;
			item.setTitle(goods.getGoods().getGoodsName()); // 标题
			item.setPrice(goods.getGoods().getPrice()); // 价格
			item.setNum(99999); // 库存数量
			item.setStatus("1"); // 状态
			item.setIsDefault("1"); // 默认
			item.setSpec("{}");	//规格
			setItemValues(item, goods);

			itemMapper.insert(item);
		}

	}

	/**
	 * 修改
	 */
	@Override
	public void update(Goods goods){
		// 更新基本表数据
		goodsMapper.updateByPrimaryKey(goods.getGoods()) ;
		// 更新扩展表数据
		goodsDescMapper.updateByPrimaryKey(goods.getGoodsDesc()) ;

		// 删除原有的SKU列表数据
		TbItemExample example = new TbItemExample() ;
		com.pinyougou.pojo.TbItemExample.Criteria criteria = example.createCriteria() ;
		criteria.andGoodsIdEqualTo(goods.getGoods().getId()) ;
		itemMapper.deleteByExample(example) ;

		// 插入新的SKU列表数据
		saveItemList(goods) ;
		//goodsMapper.updateByPrimaryKey(goods);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Goods findOne(Long id){
		Goods goods = new Goods() ;
		// 查询商品基本表
		TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
		goods.setGoods(tbGoods) ;
		// 商品扩展表
		TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(id) ;
		goods.setGoodsDesc(goodsDesc) ;
		// 读取SKU列表
		TbItemExample example = new TbItemExample() ;
		com.pinyougou.pojo.TbItemExample.Criteria criteria = example.createCriteria() ;
		criteria.andGoodsIdEqualTo(id) ;
		List<TbItem> itemList = itemMapper.selectByExample(example) ;
		goods.setItemList(itemList) ;

		return goods ; 	// goodsMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			TbGoods goods = goodsMapper.selectByPrimaryKey(id) ;
			goods.setIsDelete("1") ;	//"1"为逻辑删除
			goodsMapper.updateByPrimaryKey(goods) ; // 返回值
//			goodsMapper.deleteByPrimaryKey(id);	物理删除;
		}		
	}

		@Override
	public PageResult findPage(TbGoods goods, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbGoodsExample example=new TbGoodsExample();
		Criteria criteria = example.createCriteria();

		criteria.andIsDeleteIsNull();	// 指定条件为未逻辑删除状态
		
		if(goods!=null){			
			if(goods.getSellerId()!=null && goods.getSellerId().length()>0){
//				criteria.andSellerIdLike("%"+goods.getSellerId()+"%");	// 模糊查询
				criteria.andSellerIdEqualTo(goods.getSellerId());	// 精确查询参才能准确捕捉想要的
			}
			if(goods.getGoodsName()!=null && goods.getGoodsName().length()>0){
				criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
			}
			if(goods.getAuditStatus()!=null && goods.getAuditStatus().length()>0){
				criteria.andAuditStatusLike("%"+goods.getAuditStatus()+"%");
			}
			if(goods.getIsMarketable()!=null && goods.getIsMarketable().length()>0){
				criteria.andIsMarketableLike("%"+goods.getIsMarketable()+"%");
			}
			if(goods.getCaption()!=null && goods.getCaption().length()>0){
				criteria.andCaptionLike("%"+goods.getCaption()+"%");
			}
			if(goods.getSmallPic()!=null && goods.getSmallPic().length()>0){
				criteria.andSmallPicLike("%"+goods.getSmallPic()+"%");
			}
			if(goods.getIsEnableSpec()!=null && goods.getIsEnableSpec().length()>0){
				criteria.andIsEnableSpecLike("%"+goods.getIsEnableSpec()+"%");
			}
			if(goods.getIsDelete()!=null && goods.getIsDelete().length()>0){
				criteria.andIsDeleteLike("%"+goods.getIsDelete()+"%");
			}
	
		}
		
		Page<TbGoods> page= (Page<TbGoods>)goodsMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public void updateStatus(Long[] ids, String status) {
		for (long id:ids){
			TbGoods goods = goodsMapper.selectByPrimaryKey(id) ;
			goods.setAuditStatus(status) ;
			goodsMapper.updateByPrimaryKey(goods) ;
		}
	}

	/*
	根据SPU的集合查询SKU的列表
	 */
	public List<TbItem> findItemListByGoodsIdListAndStatus(Long[] goodsIds, String status){

		TbItemExample example = new TbItemExample() ;
		com.pinyougou.pojo.TbItemExample.Criteria criteria = example.createCriteria() ;
		criteria.andStatusEqualTo(status) ;	// 已审核
		// 将字符串转换成SPU的Id集合, 指定条件
		criteria.andGoodsIdIn(Arrays.asList(goodsIds)) ;
		return itemMapper.selectByExample(example) ;
	}
}
