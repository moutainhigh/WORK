
package com.xlkfinance.bms.web.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.SysAreaInfo;
import com.xlkfinance.bms.rpc.system.SysAreaInfoService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;
/**
 * 系统地区Controller
 * @author chenzhuzhen
 * @date 2016年6月30日 下午6:57:19
 */
@Controller
@RequestMapping("/sysAreaInfoController")
public class SysAreaInfoController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(SysAreaInfoController.class);
	
	
	/**
	 * @Description: 进入省市区信息管理界面
	 * @return 需要跳转的页面
	 * @author: dulin@qfangfin.com
	 * @date: 2017年12月4日 上午10:21:38
	 */
	@RequestMapping(value = "index")
	public String navigation() {
		return "system/index_area";
	}
	
	/**
	 * 分页查询地区信息（根据省、市、区级别和上级编码）
	  * @param sysBankInfo
	  * @param response
	  * @author: dulin
	  * @date: 2017年12月04日 下午3:10:26
	 */
	@RequestMapping(value = "pagedAreaInfo", method = RequestMethod.POST)
	@ResponseBody
	public void pagedAreaInfo(SysAreaInfo areaInfo, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		List<SysAreaInfo> resultList = new ArrayList<SysAreaInfo>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysAreaInfoService");
			SysAreaInfoService.Client client = (SysAreaInfoService.Client) clientFactory.getClient();
			if(StringUtil.isBlank(areaInfo.getLevelNo())){
				areaInfo.setLevelNo("1"); //默认查询省份
			}
			count = client.countAreaInfo(areaInfo);
			if (count > 0) {
				resultList = client.queryPagedAreaInfo(areaInfo);
			}
		} catch (Exception e) {
			logger.error("分页查询地区数据出错："+ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
		map.put("rows", resultList);
		map.put("total", count);
		// 输出
		outputJson(map, response);
		// 输出
		outputJson(resultList, response);
	}
	
	/**
	 * 保存地区信息
	  * @param bankInfo
	  * @param response
	  * @author: baogang
	  * @date: 2016年5月12日 下午4:03:10
	 */
	@RequestMapping(value = "saveAreaInfo")
	@ResponseBody
	public void saveAreaInfo(SysAreaInfo areaInfo, HttpServletResponse response){
		Json j = super.getSuccessObj();
		if (StringUtil.isBlank(areaInfo.getAreaCode()) || StringUtil.isBlank(areaInfo.getAreaName())) {
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "编码信息或名称信息不能为空,请重新操作！");
			outputJson(j, response);
			return;
		}
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysAreaInfoService");
			SysAreaInfoService.Client client = (SysAreaInfoService.Client) clientFactory.getClient();
			// 编码已存在校验
			SysAreaInfo query  = new SysAreaInfo();
			query.setAreaCode(areaInfo.areaCode);
			List<SysAreaInfo> existAreas = client.getSysAreaInfo(query);
			if (existAreas != null && existAreas.size() > 0) {
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "保存地区信息失败,编码已存在！");
			} else {
				// 保存地区信息
				int rows = client.saveAreaInfo(areaInfo);
				if (rows == 0) {
					j.getHeader().put("success", false);
					j.getHeader().put("msg", "保存地区信息失败,请重新操作！");
				} else {
					j.getHeader().put("success", true);
					j.getHeader().put("msg", "操作成功！");
				}
			}
		} catch (ThriftServiceException tse) {
			logger.error("保存地区信息:" + ExceptionUtil.getExceptionMessage(tse));
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.error("保存地区信息:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "保存地区信息失败,请重新操作！");
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(j, response);
	}
	
	/**
	 * 批量删除银行信息
	  * @param areaCodes
	  * @param response
	  * @author: dulin
	  * @date: 2017年12月05日 下午4:03:33
	 */
	@RequestMapping(value = "deleteAreaInfo")
	@ResponseBody
	public void deleteBankInfo(@RequestParam(value = "areaCodes") String areaCodes,HttpServletResponse response){
 		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysAreaInfoService");
			SysAreaInfoService.Client client = (SysAreaInfoService.Client) clientFactory.getClient();
			// 调用删除方法
			int rows = client.batchDelete(areaCodes);
			if (rows == 0) {
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "删除地区信息失败,请重新操作！");
			}else{
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "操作成功！");
			}
		} catch (Exception e) {
			logger.error("批量删除地区信息:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "删除地区信息失败,请重新操作！");
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(j, response);
	}
	
	/**
	 * 根据级别和地区编号查询数据
	 */
	@RequestMapping(value = "getSysAreaInfo")
	@ResponseBody
	public void getSysAreaInfo(String levelNo,String parentCode, HttpServletResponse response){
		List<SysAreaInfo> resultList = new ArrayList<SysAreaInfo>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysAreaInfoService");
			SysAreaInfoService.Client client = (SysAreaInfoService.Client) clientFactory.getClient();
			
			SysAreaInfo query  = new SysAreaInfo();
			
			if(StringUtil.isBlank(levelNo)){
				levelNo = "1";	//默认查询省份
			}
			if(!StringUtil.isBlank(parentCode)){
				query.setParentCode(parentCode);
			}
			query.setLevelNo(levelNo);
			resultList = client.getSysAreaInfo(query);
 
		} catch (Exception e) {
			logger.error("根据级别和地区编号查询数据getSysAreaInfo出错："+ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(resultList, response);
	}
	
	/**
	 *根据用户Id查询城市信息
	 */
	@RequestMapping(value = "getSysAreaInfoByUserId")
	@ResponseBody
	public void getSysAreaInfoByUserId(int userId, HttpServletResponse response){
		BaseClientFactory clientFactory = null;
		SysAreaInfo sysAreaInfo  = new SysAreaInfo();
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysAreaInfoService");
			SysAreaInfoService.Client client = (SysAreaInfoService.Client) clientFactory.getClient();
			if(userId >0 ){
				sysAreaInfo = client.getSysAreaInfoByUserId(userId);
			}
			//如果该用户ID匹配的城市为空时，默认为深圳地区
			if(sysAreaInfo == null || StringUtil.isBlank(sysAreaInfo.getAreaCode())){
				sysAreaInfo.setAreaCode("440300");
				sysAreaInfo.setAreaName("深圳市");
			}
		} catch (Exception e) {
			logger.error("根据用户Id查询城市信息getSysAreaInfoByUserId出错："+ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(sysAreaInfo, response);
	}
	 
}
