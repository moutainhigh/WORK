package com.xlkfinance.bms.web.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.qfang.data.facade.model.enums.AreaQfangPriceTypeEnum;
import com.qfang.data.facade.model.enums.CityDtoEnum;
import com.qfang.data.facade.model.enums.EvaluationApiCallerIdentifierEnum;
import com.qfang.data.facade.model.enums.GardenMonthRateRankOrderEnum;
import com.qfang.data.facade.model.enums.OrderModeEnum;
import com.qfang.data.facade.model.request.AreaQfangPriceRequest;
import com.qfang.data.facade.model.request.AreasQfangPriceInOneMonthRequest;
import com.qfang.data.facade.model.request.BuildingSearchRequest;
import com.qfang.data.facade.model.request.EvaluationHousePriceGuidingRequest;
import com.qfang.data.facade.model.request.FloorSearchRequest;
import com.qfang.data.facade.model.request.GardenMonthRateRankRequest;
import com.qfang.data.facade.model.request.GardenSearchRequest;
import com.qfang.data.facade.model.request.RoomSearchRequest;
import com.qfang.data.facade.model.response.AreaQfangPriceResponse;
import com.qfang.data.facade.model.response.AreasQfangPriceInOneMonthResponse;
import com.qfang.data.facade.model.response.BuildingSearchResponse;
import com.qfang.data.facade.model.response.EvaluationHousePriceGuidingResponse;
import com.qfang.data.facade.model.response.FloorSearchResponse;
import com.qfang.data.facade.model.response.GardenMonthRateRankResponse;
import com.qfang.data.facade.model.response.GardenSearchResponse;
import com.qfang.data.facade.model.response.RoomSearchResponse;
import com.xlkfinance.bms.web.api.qfangapi.QfangApiService;
import com.xlkfinance.bms.web.api.qfangapi.util.QfangUtil;

/**
 * Q房集团API接口
 * @author chenzhuzhen
 * @date 2016年9月13日 上午10:03:24
 */
@Controller
@RequestMapping("/qfangApi")
public class QfangApiController {
	
	private Logger logger = LoggerFactory.getLogger(QfangApiController.class);
	
	@Autowired
	private QfangApiService qfangApiService;
	
	
	
	/**
	 * 查询城市列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCityList")
	@ResponseBody
	public Object getCityList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		List resultList = new ArrayList<>();
		try{
			List<CityDtoEnum> cityList = qfangApiService.getQfangCity();
			if (cityList != null && cityList.size() > 0) {
				Map<String, String> tempMap = null;
				for (CityDtoEnum indexObj : cityList) {
					tempMap = new HashMap<String, String>();
					tempMap.put("cityName", indexObj.getAlias());
					tempMap.put("cityCode", indexObj.getValue());
					resultList.add(tempMap);
				}
			}
		}catch(Exception e){
			logger.error(">>>>>>getCityList error", e);
		}
		return resultList;
	}
	
	
	/**
	 * 查询楼盘（小区）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchGarden")
	@ResponseBody
	public Object searchGarden(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			//楼盘/小区名
			String gardenName = request.getParameter("q");
			//城市
			String qfangCity = request.getParameter("qfangCity");
			
			GardenSearchRequest qfangRequest = new GardenSearchRequest();
			
			//设置城市
			if(StringUtils.isNotBlank(qfangCity)){
				qfangRequest.setCity(QfangUtil.getQfangCityDtoEnum(qfangCity));
			}
			qfangRequest.setPageSize(10);
			
			if(StringUtils.isNotBlank(gardenName)){
				qfangRequest.setSearchName(gardenName);
			}
			
			GardenSearchResponse  qfangResponse =qfangApiService.getQfangGarden(qfangRequest);
			
			if(qfangResponse != null ){
				logger.info("searchGarden qfangResponse:"+JSONObject.toJSONString(qfangResponse));
			}
			
			return qfangResponse;
		}catch(Exception e){
			logger.error(">>>>>>searchGarden error", e);
		}
		return null;
	}
	
	/**
	 * 查询楼栋/单元
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchBuilding")
	@ResponseBody
	public Object searchBuilding(HttpServletRequest request,HttpServletResponse response) throws Exception {
		//楼盘/小区id
		String gardenId = request.getParameter("gardenId");
		//城市
		String qfangCity = request.getParameter("qfangCity");
		
		BuildingSearchRequest qfangRequest = new BuildingSearchRequest();
		
		//设置城市
		if(StringUtils.isNotBlank(qfangCity)){
			qfangRequest.setCity(QfangUtil.getQfangCityDtoEnum(qfangCity));
		}
		if(StringUtils.isNotBlank(gardenId)){
			qfangRequest.setGardenId(gardenId);
		}
		BuildingSearchResponse qfangResponse = qfangApiService.getQfangBuilding(qfangRequest);
		
		if(qfangResponse != null ){
			logger.info("searchBuilding qfangResponse:"+JSONObject.toJSONString(qfangResponse));
		}
		return qfangResponse;
	}
	
	
	/**
	 * 查询楼层
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchFloor")
	@ResponseBody
	public Object searchFloor(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		//楼栋/单元id
		String buildingId = request.getParameter("buildingId");
		//城市
		String qfangCity = request.getParameter("qfangCity");
		
		FloorSearchRequest qfangRequest = new FloorSearchRequest();
		//设置城市
		if(StringUtils.isNotBlank(qfangCity)){
			qfangRequest.setCity(QfangUtil.getQfangCityDtoEnum(qfangCity));
		}
		
		if(StringUtils.isNotBlank(buildingId)){
			qfangRequest.setBuildingId(buildingId);
		}
		FloorSearchResponse qfangResponse =qfangApiService.getQfangFloor(qfangRequest);
		
		if(qfangResponse != null ){
			logger.info("searchFloor qfangResponse:"+JSONObject.toJSONString(qfangResponse));
		}
		return qfangResponse;
	}
	
	/**
	 * 查询房号
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchRoom")
	@ResponseBody
	public Object searchRoom(HttpServletRequest request,HttpServletResponse response) throws Exception {
		//楼栋/单元id
		String buildingId = request.getParameter("buildingId");
		//楼层id
		String floorId = request.getParameter("floorId");
		//房号id
		String roomId = request.getParameter("roomId");
		//城市
		String qfangCity = request.getParameter("qfangCity");
		
		RoomSearchRequest qfangRequest = new RoomSearchRequest();
		//设置城市
		if(StringUtils.isNotBlank(qfangCity)){
			qfangRequest.setCity(QfangUtil.getQfangCityDtoEnum(qfangCity));
		}
		
		if(StringUtils.isNotBlank(buildingId)){
			qfangRequest.setBuildingId(buildingId);
		}
		if(StringUtils.isNotBlank(floorId)){
			qfangRequest.setFloorId(floorId);
		}
		if(StringUtils.isNotBlank(roomId)){
			qfangRequest.setRoomId(roomId);
		}
		
		RoomSearchResponse qfangResponse = qfangApiService.getQfangRoom(qfangRequest);
		
		if(qfangResponse != null ){
			logger.info("searchRoom qfangResponse:"+JSONObject.toJSONString(qfangResponse));
		}
		return qfangResponse;
	}
	
	
	/**
	 * 查询房间估价
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchGuidingPrice")
	@ResponseBody
	public Object searchGuidingPrice(HttpServletRequest request,HttpServletResponse response) throws Exception {
		//查询方式
		String searchHouseType = request.getParameter("searchHouseType");
		//楼盘/小区id
		String gardenId = request.getParameter("gardenId");
		//楼栋id
		String buildingId = request.getParameter("buildingId");
 
		//单元id
		String unit = request.getParameter("unit");
		//房号id
		String roomNumber = request.getParameter("roomNumber");
		//面积
		String buildArea = request.getParameter("buildArea");
		
		//朝向
		String directionEnum = request.getParameter("directionEnum");
		//楼层
		String floor = request.getParameter("floor");
		//最高层
		String maxFloor = request.getParameter("maxFloor");
		//室
		String bedRoom = request.getParameter("bedRoom");
		//厅
		String livingRoom = request.getParameter("livingRoom");
		//城市
		String qfangCity = request.getParameter("qfangCity");
		
		EvaluationHousePriceGuidingRequest qfangRequest = new EvaluationHousePriceGuidingRequest(); 

		//设置城市
		if(StringUtils.isNotBlank(qfangCity)){
			qfangRequest.setCity(QfangUtil.getQfangCityDtoEnum(qfangCity));
		}
		//FINANCE("金融","7");
		qfangRequest.setCallerIdentifierEnum(EvaluationApiCallerIdentifierEnum.FINANCE);
		//调用方标识 默认CLOUD("数据平台","3")
		//qfangRequest.setCallerIdentifierEnum(EvaluationApiCallerIdentifierEnum.CLOUD);
		
		if(StringUtils.isNotBlank(gardenId)){
			qfangRequest.setGardenId(gardenId);
		}
		//精确查询
		if("2".equals(searchHouseType)){
			if(StringUtils.isNotBlank(unit)){
				qfangRequest.setUnit(unit);
			}
			if(StringUtils.isNotBlank(buildingId)){
				qfangRequest.setBuilding(buildingId);
			}
			if(StringUtils.isNotBlank(roomNumber)){
				qfangRequest.setRoomNumber(roomNumber);
			}
		}
		
		if(StringUtils.isNotBlank(buildArea)){
			qfangRequest.setBuildArea(Double.parseDouble(buildArea));
		}
		//朝向
		if(StringUtils.isNotBlank(directionEnum)){
			qfangRequest.setDirectionEnum(QfangUtil.getQfangDirectionEnum(directionEnum));
		}
		
		if(StringUtils.isNotBlank(floor)){
			qfangRequest.setFloor(Integer.parseInt(floor));
		}
		if(StringUtils.isNotBlank(maxFloor)){
			qfangRequest.setMaxFloor(Integer.parseInt(maxFloor));
		}
		if(StringUtils.isNotBlank(bedRoom)){
			qfangRequest.setBedRoom(Integer.parseInt(bedRoom));
		}
		if(StringUtils.isNotBlank(livingRoom)){
			qfangRequest.setLivingRoom(Integer.parseInt(livingRoom));
		}
		
		logger.info("qfangRequest:"+JSONObject.toJSONString(qfangRequest));
		
		EvaluationHousePriceGuidingResponse qfangResponse = qfangApiService.getQfangHousePrice(qfangRequest);
 
		if(qfangResponse != null ){
			logger.info("searchGuidingPrice qfangResponse:"+JSONObject.toJSONString(qfangResponse));
		}
		return qfangResponse;
	}
	
	
	/**
	 * 查询区域房价趋势
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchAreaPrice")
	@ResponseBody
	public Object searchAreaPrice(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		//楼盘/小区id
		String gardenId = request.getParameter("gardenId");
		//开始月份
		String startMonth = request.getParameter("startMonth");
		//结束月份
		String endMonth = request.getParameter("endMonth");
		//城市
		String qfangCity = request.getParameter("qfangCity");
		
		AreaQfangPriceRequest qfangRequest = new AreaQfangPriceRequest(); 
		qfangRequest.setCity(CityDtoEnum.SHENZHEN);
		//设置城市
		if(StringUtils.isNotBlank(qfangCity)){
			qfangRequest.setCity(QfangUtil.getQfangCityDtoEnum(qfangCity));
		}
		
		//按楼盘小区查询
		if(StringUtils.isNotBlank(gardenId)){
			qfangRequest.setId(gardenId);
			qfangRequest.setType(AreaQfangPriceTypeEnum.GARDEN);
		}
		
		if(StringUtils.isBlank(startMonth)){
			startMonth="201607";
		}
		if(StringUtils.isBlank(endMonth)){
			endMonth="201608";
		}
		
		qfangRequest.setStartMonth(startMonth);
		qfangRequest.setEndMonth(endMonth);
		
		AreaQfangPriceResponse qfangResponse =  qfangApiService.getQfangAreaPrice(qfangRequest);
		
		logger.info("searchAreaPrice qfangResponse:"+JSONObject.toJSONString(qfangResponse));
		 
		return qfangResponse;
	}
	
	
	/**
	 * 查询区域价格和同比环比
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchAreasPriceInOneMonth")
	@ResponseBody
	public Object searchAreasPriceInOneMonth(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		//楼盘/小区id
		String gardenId = request.getParameter("gardenId");
		//月份(必填)201605
		String month = request.getParameter("month");
		//城市
		String qfangCity = request.getParameter("qfangCity");

		AreasQfangPriceInOneMonthRequest qfangRequest = new AreasQfangPriceInOneMonthRequest(); 
		//设置城市
		if(StringUtils.isNotBlank(qfangCity)){
			qfangRequest.setCity(QfangUtil.getQfangCityDtoEnum(qfangCity));
		}
		
		//是否需要同比和环比数据，默认为false
		qfangRequest.setIsNeedRate(false);	
		
		if(StringUtils.isNotBlank(gardenId)){
			qfangRequest.setIds(gardenId);
			qfangRequest.setType(AreaQfangPriceTypeEnum.GARDEN);
		}
		if(StringUtils.isNotBlank(month)){
			qfangRequest.setMonth(month);
		}
		
		AreasQfangPriceInOneMonthResponse qfangResponse = qfangApiService.getQfangAreasPriceInOneMonth(qfangRequest);
		
		logger.info("searchAreasPriceInOneMonth qfangResponse:"+JSONObject.toJSONString(qfangResponse));
		 
		return qfangResponse;
	}
	
	
	/**
	 * 查询近1月涨跌幅top楼盘
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchGardenMonthRateRankList")
	@ResponseBody
	public Object searchGardenMonthRateRankList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		//楼盘/小区id
		String gardenId = request.getParameter("gardenId");
		//月份(必填)201605
		String month = request.getParameter("month");
		//排序字段
		String orderField = request.getParameter("orderField");
		//排序方式，涨幅或跌幅。
		String orderMode = request.getParameter("orderMode");
		
		//城市
		String qfangCity = request.getParameter("qfangCity");
		
		GardenMonthRateRankRequest qfangRequest = new GardenMonthRateRankRequest(); 
		
		//设置城市
		if(StringUtils.isNotBlank(qfangCity)){
			qfangRequest.setCity(QfangUtil.getQfangCityDtoEnum(qfangCity));
			qfangRequest.setId(QfangUtil.getQfangCityDtoEnum(qfangCity).getNumber());
		}
		qfangRequest.setType(AreaQfangPriceTypeEnum.CITY);
		
		if(StringUtils.isNotBlank(gardenId)){
			qfangRequest.setGardenId(gardenId);
		}
		if(StringUtils.isNotBlank(month)){
			qfangRequest.setMonth(month);
		}
		
		if(GardenMonthRateRankOrderEnum.PRICE.equals(orderMode)){
			qfangRequest.setOrderField(GardenMonthRateRankOrderEnum.PRICE);
		}else if(GardenMonthRateRankOrderEnum.MONTHRATE.equals(orderField)){
			qfangRequest.setOrderField(GardenMonthRateRankOrderEnum.MONTHRATE);
		}
		
		if(OrderModeEnum.UPPER.equals(orderMode)){
			qfangRequest.setOrderMode(OrderModeEnum.UPPER);
		}else if(OrderModeEnum.LOWER.equals(orderField)){
			qfangRequest.setOrderMode(OrderModeEnum.LOWER);
		}else{
			qfangRequest.setOrderMode(OrderModeEnum.UPPER);
		}
		
		GardenMonthRateRankResponse qfangResponse = qfangApiService.getQfangGardenMonthRateRankList(qfangRequest);
		
		logger.info("searchGardenMonthRateRankList qfangResponse:"+JSONObject.toJSONString(qfangResponse));
		 
		return qfangResponse;
	}
	
 

}
