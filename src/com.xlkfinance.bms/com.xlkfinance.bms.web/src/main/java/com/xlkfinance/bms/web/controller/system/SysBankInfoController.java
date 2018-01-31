/**
 * @Title: SysBankInfoController.java
 * @Package com.xlkfinance.bms.web.controller.system
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: baogang
 * @date: 2016年5月12日 下午2:49:39
 * @version V1.0
 */
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
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.SysBankInfo;
import com.xlkfinance.bms.rpc.system.SysBankInfoDto;
import com.xlkfinance.bms.rpc.system.SysBankService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;
/**
 * 系统银行Controller
  * @ClassName: SysBankInfoController
  * @author: baogang
  * @date: 2016年5月12日 下午2:52:21
 */
@Controller
@RequestMapping("/sysBankInfoController")
public class SysBankInfoController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(SysBankInfoController.class);
	
	/**
	 * 页面跳转方法
	  * @return
	  * @author: baogang
	  * @date: 2016年5月12日 下午2:57:11
	 */
	@RequestMapping(value = "navigation")
	public String navigation() {
		return "system/index_bank";
	}
	
	/**
	 * 分页查询主银行信息
	  * @param sysBankInfo
	  * @param response
	  * @author: baogang
	  * @date: 2016年5月12日 下午3:10:26
	 */
	@RequestMapping(value = "getSysBankInfo", method = RequestMethod.POST)
	@ResponseBody
	public void getSysBankInfo(SysBankInfo sysBankInfo,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		List<SysBankInfo> resultList = new ArrayList<SysBankInfo>();
		int count = 0;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysBankService");
			SysBankService.Client client = (SysBankService.Client) clientFactory.getClient();
			resultList = client.querySysBankInfos(sysBankInfo);
			count = client.getSysBankCount(sysBankInfo);
		} catch (Exception e) {
			logger.error("分页查询主银行信息:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		map.put("rows", resultList);
		map.put("total", count);
		// 输出
		outputJson(map, response);
	}
	
	/**
	 * 根据parentId分页查询银行信息
	  * @param parentId
	  * @param page
	  * @param rows
	  * @param response
	  * @author: baogang
	  * @date: 2016年5月12日 下午3:26:30
	 */
	@RequestMapping(value = "getSysBankByParent")
	@ResponseBody
	public void getSysBankByParent(int parentId,int page,int rows, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		List<SysBankInfo> resultList = new ArrayList<SysBankInfo>();
		int count = 0;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysBankService");
			SysBankService.Client client = (SysBankService.Client) clientFactory.getClient();
			SysBankInfo sysBankInfo = new SysBankInfo();
			sysBankInfo.setPage(page);
			sysBankInfo.setParentId(parentId);
			sysBankInfo.setRows(rows);
			resultList = client.querySysBankInfos(sysBankInfo );
			count = client.getSysBankCount(sysBankInfo);
		} catch (Exception e) {
			logger.error("根据parentId分页查询银行信息:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		map.put("rows", resultList);
		map.put("total", count);
		// 输出
		outputJson(map, response);
	}
	
	/**
	 * 根据parentId不分页查询银行信息
	  * @param parentId
	  * @param page
	  * @param rows
	  * @param response
	  * @author: baogang
	  * @date: 2016年5月12日 下午3:26:30
	 */
	@RequestMapping(value = "getSysBankByParentId")
	@ResponseBody
	public void getSysBankByParentId(int parentId, HttpServletResponse response){
		List<SysBankInfoDto> resultList = new ArrayList<SysBankInfoDto>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysBankService");
			SysBankService.Client client = (SysBankService.Client) clientFactory.getClient();
			SysBankInfo sysBankInfo = new SysBankInfo();
			sysBankInfo.setParentId(parentId);
			resultList = client.queryAllSysBankInfo(sysBankInfo );
		} catch (Exception e) {
			logger.error("根据parentId不分页查询银行信息:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		// 输出
		outputJson(resultList, response);
	}
	
	
	/**
	 * 新增银行信息
	  * @param bankInfo
	  * @param response
	  * @author: baogang
	  * @date: 2016年5月12日 下午4:03:10
	 */
	@RequestMapping(value = "addBankInfo")
	@ResponseBody
	public void addBankInfo(SysBankInfo bankInfo,HttpServletResponse response){
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysBankService");
			SysBankService.Client client = (SysBankService.Client) clientFactory.getClient();
			// 调用新增方法
			int rows = client.addSysBankInfo(bankInfo);
			if (rows == 0) {
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "添加银行信息失败,请重新操作！");
			}else{
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "操作成功！");
			}
		} catch (ThriftServiceException tse) {
			logger.error("新增银行信息:" + ExceptionUtil.getExceptionMessage(tse));
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.error("新增银行信息:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "添加银行信息失败,请重新操作！");
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
	 * 修改银行信息
	  * @param bankInfo
	  * @param response
	  * @author: baogang
	  * @date: 2016年5月12日 下午4:03:24
	 */
	@RequestMapping(value = "updateBankInfo")
	@ResponseBody
	public void updateBankInfo(SysBankInfo bankInfo,HttpServletResponse response){
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysBankService");
			SysBankService.Client client = (SysBankService.Client) clientFactory.getClient();
			// 调用新增方法
			int rows = client.updateSysBankInfo(bankInfo);
			if (rows == 0) {
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "修改银行信息失败,请重新操作！");
			}else{
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "操作成功！");
			}
		} catch (ThriftServiceException tse) {
			logger.error("修改银行信息:" + ExceptionUtil.getExceptionMessage(tse));
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.error("修改银行信息:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "修改银行信息失败,请重新操作！");
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
	  * @param pids
	  * @param response
	  * @author: baogang
	  * @date: 2016年5月12日 下午4:03:33
	 */
	@RequestMapping(value = "deleteBankInfo")
	@ResponseBody
	public void deleteBankInfo(@RequestParam(value = "pids[]") int[] pids,HttpServletResponse response){
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysBankService");
			SysBankService.Client client = (SysBankService.Client) clientFactory.getClient();
			// 调用新增方法
			String bankIds = "";
			for (int i : pids) {
				bankIds+=i+",";
			}
			bankIds = bankIds.substring(0, bankIds.length()-1);
			int rows = client.batchDelete(bankIds);
			if (rows == 0) {
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "删除银行信息失败,请重新操作！");
			}else{
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "操作成功！");
			}
		} catch (Exception e) {
			logger.error("批量删除银行信息:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "删除银行信息失败,请重新操作！");
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
	 * 根据parentId 查询银行数据，用于下拉框
	 * @param parentId
	 * @param response
	 */
	@RequestMapping(value = "getAllBankInfo")
	@ResponseBody
	public void getAllBankInfo(int parentId,HttpServletResponse response){
		List<SysBankInfoDto> resultList = new ArrayList<SysBankInfoDto>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysBankService");
			SysBankService.Client client = (SysBankService.Client) clientFactory.getClient();
			SysBankInfoDto dto = new SysBankInfoDto();
			dto.setPid(-1);
			dto.setBankName("--请选择--");
			resultList.add(dto);
			if(parentId != -1){
				SysBankInfo sysBankInfo = new SysBankInfo();
				sysBankInfo.setParentId(parentId);
				resultList.addAll(client.queryAllSysBankInfo(sysBankInfo));
			}
		} catch (Exception e) {
			logger.error("根据parentId 查询银行数据，用于下拉框:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		// 输出
		outputJson(resultList, response);
	}
}
