package com.xlkfinance.bms.web.api.qfangapi;

import java.util.List;

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

/**
 * Q房集团接口
 * @author chenzhuzhen
 * @date 2016年10月13日 上午9:43:04
 */
public interface QfangApiService {
	
	/**
	 * 获取Q房城市列表
	 * @return
	 * @throws Exception
	 */
	public List<CityDtoEnum> getQfangCity() throws Exception;
	
	/**
	 * 获取Q房小区
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public GardenSearchResponse getQfangGarden(GardenSearchRequest request)throws Exception;

	/**
	 * 获取Q房楼栋/单元
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public BuildingSearchResponse getQfangBuilding(BuildingSearchRequest request)throws Exception;
	
	/**
	 * 获取Q房楼层
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public FloorSearchResponse getQfangFloor(FloorSearchRequest request)throws Exception;

	/**
	 * 获取Q房房号
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public RoomSearchResponse getQfangRoom(RoomSearchRequest request)throws Exception;
	
	/**
	 * 获取Q房估价
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public EvaluationHousePriceGuidingResponse getQfangHousePrice(EvaluationHousePriceGuidingRequest request)throws Exception;
	
	/**
	 * 获取Q房区域房价趋势
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public AreaQfangPriceResponse getQfangAreaPrice(AreaQfangPriceRequest request)throws Exception;
	
	/**
	 * 获取Q房区域价格和同比环比
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public AreasQfangPriceInOneMonthResponse getQfangAreasPriceInOneMonth(AreasQfangPriceInOneMonthRequest request)throws Exception;
	
	/**
	 * 获取Q房近1月涨跌幅top楼盘
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public GardenMonthRateRankResponse getQfangGardenMonthRateRankList(GardenMonthRateRankRequest request)throws Exception;
	
	
	
	
	

	
	
	
}
