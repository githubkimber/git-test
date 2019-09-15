package cn.itcast.b2c.gciantispider.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.JedisCluster;
import cn.itcast.b2c.gciantispider.model.Datacollect;
import cn.itcast.b2c.gciantispider.model.DatacollectView;
import cn.itcast.b2c.gciantispider.pageUtil.LinkJsonVO;
import cn.itcast.b2c.gciantispider.service.IDataCollectService;
import cn.itcast.b2c.gciantispider.util.Constants;
import cn.itcast.b2c.gciantispider.util.JedisConnectionUtil;
import cn.itcast.b2c.gciantispider.util.JsonResolveUtil;

import com.alibaba.fastjson.JSON;
/**
 * @author itheima
 * 数据采集控制器
 *
 */
@Controller
@RequestMapping("/dataCollect")
public class DataCollectController {
	
	private final static Logger logger = Logger.getLogger(DataCollectController.class.getName());
	
	@Autowired
	private IDataCollectService dataCollectService;
	/**
	 * 
	 * 获取所有服务器信息
	 * @return
	 */
	@RequestMapping(value="/getDataCollect",method = RequestMethod.GET)
    @ResponseBody
    public List<DatacollectView> getDataCollect(HttpServletRequest request){
		
		List<DatacollectView> dataCollectViewList = new ArrayList<DatacollectView>();
		try{
				//实例化redis集群
			JedisCluster jedisCluster = JedisConnectionUtil.getJedisCluster();
	        	//从数据库表dataCollect中拿出所有数据
			List<Datacollect> dataCollectList = dataCollectService.getDataCollect();
				//遍历mysql中所有的servername
			for (Datacollect datacollect : dataCollectList) {
				
					//在redis服务器   获取正在运行的服务器  （CSANTI_MONITOR_LP*） 
					//last 类似 CSANTI_MONITOR_LP1553088805099
				String last = JedisConnectionUtil.keys(jedisCluster, Constants.CSANTI_MONITOR_LP +"*").last();
					//获取到key 对应的value  {"serversCountMap":{"192.168.2.141":30},"activeNumMap":{"192.168.2.141":"5"}}
				String value = jedisCluster.get(last);
					//将 {"serversCountMap":{"192.168.2.141":30},"activeNumMap":{"192.168.2.141":"5"}} 转换成LinkJsonVO
				LinkJsonVO resolveLinkJson = JsonResolveUtil.resolveLinkJson(value);
					//获取到bean中的"activeNumMap"  也就是    {"192.168.2.141":"5"}
					//<String,Integer> 就是   "192.168.100.160": "57"
				Map<String,Integer> map2 = resolveLinkJson.getActiveNumMap();
					//可能会有多个keySet，keySet是IP(192.168.2.141)  
				Set<String> keySet = map2.keySet();
					//遍历每一个IP ,key 当前正在服务的服务器ip,在redis数据库获取的
				for (String key : keySet) {
					//获取mysql中，当前正在服务的servername   当前服务的IP 在mysql存的数据
					if (key.equals(datacollect.getServerName())) {
						//实例化前端展现 的bean
						DatacollectView datacollectView = new DatacollectView();
							//将当前最新的活跃连接数封装到bean<<< 数据来源于redis >>>
						datacollectView.setActiveNum(map2.get(key));
							//将其他信息封装进bean
						datacollectView.setBeforeYesterdayNum(datacollect.getBeforeYesterdayNum());
						datacollectView.setId(datacollect.getId());
						datacollectView.setLastThreeDaysNum(datacollect.getLastThreeDaysNum());
							//封装server name（server IP）
						datacollectView.setServerName(datacollect.getServerName());
						datacollectView.setYesterdayNum(datacollect.getYesterdayNum());
							//将当前的bean 添加到bean的list 内
						dataCollectViewList.add(datacollectView);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		//返回bean的list
		return dataCollectViewList;
	}
	/**
	 * 
	 * 删除服务器
	 * @return
	 */
	@RequestMapping(value="/deleteServer",method = RequestMethod.GET)
    @ResponseBody
	public Map<String,Object> deleteServer(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		String id = request.getParameter("id");
		try{
			dataCollectService.deleteServer(id);
			map.put("result", "删除成功");
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return map;
	}
	/**
	 * 
	 * 修改脚本
	 * @return
	 */
	@RequestMapping(value="/modifyData",method = RequestMethod.GET)
    @ResponseBody
	public Map<String,Object> modifyData(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		String id = request.getParameter("id");
		String newScript = request.getParameter("newScript");
		try{
			dataCollectService.modifyData(id, newScript);
			map.put("result", "修改成功");
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return map;
	}
	
	/**
	 * 
	 * 获取脚本(用于修改时前端展示)
	 * @return
	 */
	@RequestMapping(value="/getScript",method = RequestMethod.GET)
    @ResponseBody
    public Datacollect getScript(HttpServletRequest request){
		Datacollect datacollect = null;
		String id = request.getParameter("id");
		try{
			datacollect = dataCollectService.get(id);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return datacollect;
	}
}
