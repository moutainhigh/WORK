package com.xlkfinance.bms.web.api.qfangapi.impl;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfang.data.facade.BuildingFacade;
import com.qfang.data.facade.EvaluationHousePriceGuidingFacade;
import com.qfang.data.facade.FloorFacade;
import com.qfang.data.facade.GardenFacade;
import com.qfang.data.facade.RoomFacade;
import com.qfang.data.facade.model.enums.CityDtoEnum;
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

/**
 * Q房集团接口实现类
 * @author chenzhuzhen
 * @date 2016年10月13日 上午9:55:47
 */
@Service("qfangApiService")
public class QfangApiServiceImpl implements QfangApiService {
	
	private Logger logger = LoggerFactory.getLogger(QfangApiServiceImpl.class);
	
	@Autowired(required=false) 
	private GardenFacade gardenFacade;	//楼盘查询
	@Autowired(required=false) 
	private BuildingFacade buildingFacade;	//楼栋/单元查询
	@Autowired(required=false) 
	private FloorFacade floorFacade;	//楼层查询
	@Autowired(required=false) 
	private RoomFacade roomFacade;	//房号查询
	@Autowired(required=false) 
	private EvaluationHousePriceGuidingFacade evaluationHousePriceGuidingFacade;	//房产估价查询

	@Override
	public List<CityDtoEnum> getQfangCity() throws Exception {
		List<CityDtoEnum> list = null;
		try{
			list =  Arrays.asList(CityDtoEnum.values());
		}catch(Exception e){
			logger.error(">>>>>>getQfangCity error", e);
			throw e;
		}
		return list;
	}

	@Override
	public GardenSearchResponse getQfangGarden(GardenSearchRequest request)throws Exception {
		GardenSearchResponse response = null;
		try{
			response =  gardenFacade.search(request);
		}catch(Exception e){
			logger.error(">>>>>>getQfangGarden error", e);
			throw e;
		}
		return response;
	}

	@Override
	public BuildingSearchResponse getQfangBuilding(BuildingSearchRequest request)throws Exception {
		BuildingSearchResponse response = null;
		try{
			response =  buildingFacade.search(request);
		}catch(Exception e){
			logger.error(">>>>>>getQfangBuilding error", e);
			throw e;
		}
		return response;
	}
	
	@Override
	public FloorSearchResponse getQfangFloor(FloorSearchRequest request)throws Exception {
		FloorSearchResponse response = null;
		try{
			response =  floorFacade.search(request);
		}catch(Exception e){
			logger.error(">>>>>>getQfangFloor error", e);
			throw e;
		}
		return response;
	}

	@Override
	public RoomSearchResponse getQfangRoom(RoomSearchRequest request)throws Exception {
		RoomSearchResponse response = null;
		try{
			response =  roomFacade.search(request);
		}catch(Exception e){
			logger.error(">>>>>>getQfangRoom error", e);
			throw e;
		}
		return response;
	}

	@Override
	public EvaluationHousePriceGuidingResponse getQfangHousePrice(
			EvaluationHousePriceGuidingRequest request) throws Exception {
		EvaluationHousePriceGuidingResponse response = null;
		try{
			response =  evaluationHousePriceGuidingFacade.searchGuidingPrice(request);
		}catch(Exception e){
			logger.error(">>>>>>getQfangHousePrice error", e);
			throw e;
		}
		return response;
	}



	@Override
	public AreaQfangPriceResponse getQfangAreaPrice(AreaQfangPriceRequest request) throws Exception {
		AreaQfangPriceResponse response = null;
		try{
			response =  evaluationHousePriceGuidingFacade.searchAreaPrice(request);
		}catch(Exception e){
			logger.error(">>>>>>getQfangAreaPrice error", e);
			throw e;
		}
		return response;
	}

	@Override
	public AreasQfangPriceInOneMonthResponse getQfangAreasPriceInOneMonth(AreasQfangPriceInOneMonthRequest request) throws Exception {
		AreasQfangPriceInOneMonthResponse response = null;
		try{
			response =  evaluationHousePriceGuidingFacade.searchAreasPriceInOneMonth(request);
		}catch(Exception e){
			logger.error(">>>>>>getQfangAreasPriceInOneMonth error", e);
			throw e;
		}
		return response;
	}

	@Override
	public GardenMonthRateRankResponse getQfangGardenMonthRateRankList(GardenMonthRateRankRequest request) throws Exception {
		GardenMonthRateRankResponse response = null;
		try{
			response =  evaluationHousePriceGuidingFacade.searchGardenMonthRateRankList(request);
		}catch(Exception e){
			logger.error(">>>>>>getQfangGardenMonthRateRankList error", e);
			throw e;
		}
		return response;
	}

}
