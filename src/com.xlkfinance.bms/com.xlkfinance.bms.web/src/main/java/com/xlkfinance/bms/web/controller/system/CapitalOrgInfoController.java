/**
 * @Title: CapitalOrgInfoController.java
 * @Package com.xlkfinance.bms.web.controller.system
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: baogang
 * @date: 2016年9月18日 下午2:04:47
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.SysLogTypeConstant;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.CapitalOrgInfo;
import com.xlkfinance.bms.rpc.system.CapitalOrgInfoService;
import com.xlkfinance.bms.rpc.system.OrgCapitalCost;
import com.xlkfinance.bms.rpc.system.OrgCapitalCostService;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;

/**
 * 资金机构管理
  * @ClassName: CapitalOrgInfoController
  * @author: baogang
  * @date: 2016年9月18日 下午2:04:52
 */
@Controller
@RequestMapping("/capitalOrgInfoController")
public class CapitalOrgInfoController extends BaseController{

	/**
	 * 日志
	 */
	private Logger logger = LoggerFactory.getLogger(CapitalOrgInfoController.class);

	/**
	 * 跳转列表页面
	  * @return
	  * @author: baogang
	  * @date: 2016年9月18日 下午2:14:02
	 */
	@RequestMapping(value="index")
	public String index(){
		return "/system/capital_index";
	}
	
	/**
	 * 获取分页资金机构信息
	  * @param query
	  * @param resp
	  * @author: baogang
	  * @date: 2016年9月18日 下午2:20:33
	 */
	@RequestMapping(value = "getCapitalList" , method=RequestMethod.POST)
	@ResponseBody
	public void getCapitalList(CapitalOrgInfo query,HttpServletResponse resp){
		Map<String, Object> map = new HashMap<String,Object>();
		List<CapitalOrgInfo> result = new ArrayList<CapitalOrgInfo>();
		int total = 0;
		BaseClientFactory factory = null;
		try {
			factory = getFactory(BusinessModule.MODUEL_SYSTEM, "CapitalOrgInfoService");
			CapitalOrgInfoService.Client client = (CapitalOrgInfoService.Client) factory.getClient();
			result = client.getCapitalByPage(query);
			total = client.getCapitalCount(query);
		} catch (Exception e) {
			logger .error("获取分页资金机构信息：" + ExceptionUtil.getExceptionMessage(e));
	        e.printStackTrace();
		}finally{
			destroyFactory(factory);
		}
		map.put("rows", result);
		map.put("total", total);
		// 输出
		outputJson(map, resp);
	}
	
	/**
	 * 获取资金机构信息
	  * @param query
	  * @param response
	  * @author: JonyHe
	  * @date: 2016年9月18日 下午2:20:33
	 */
	@RequestMapping(value = "getCapitalLists" , method=RequestMethod.POST)
	@ResponseBody
	public void getCapitalLists(HttpServletResponse response){
		List<CapitalOrgInfo> resultList = new ArrayList<CapitalOrgInfo>();
		CapitalOrgInfo capitalOrgInfo = new CapitalOrgInfo();
		capitalOrgInfo.setOrgCode("-1");
		capitalOrgInfo.setOrgName("--请选择--");
		resultList.add(capitalOrgInfo);
		BaseClientFactory factory = null;
		try {
			factory = getFactory(BusinessModule.MODUEL_SYSTEM, "CapitalOrgInfoService");
			CapitalOrgInfoService.Client client = (CapitalOrgInfoService.Client) factory.getClient();
			List<CapitalOrgInfo> list = new ArrayList<CapitalOrgInfo>();
			list = client.getAllByStatus();
			resultList.addAll(list);
		} catch (Exception e) {
			logger.error("获取合作机构列表失败：" + ExceptionUtil.getExceptionMessage(e));
	         e.printStackTrace();
		}
		outputJson(resultList, response);
	}
	
	/**
	 * 跳转新增或编辑页面
	  * @param pid
	  * @param response
	  * @param model
	  * @param request
	  * @return
	  * @author: baogang
	  * @date: 2016年9月18日 下午2:29:01
	 */
	@RequestMapping(value="/toEditCapital")
	public String toEditCapital(@RequestParam(value = "pid", required = false) Integer pid,@RequestParam(value = "editType", required = false)String editType, HttpServletResponse response, ModelMap model, HttpServletRequest request){
		BaseClientFactory factory = null;
		CapitalOrgInfo capitalOrgInfo = new CapitalOrgInfo();
		try {
			if(pid != null && pid >0){
				factory = getFactory(BusinessModule.MODUEL_SYSTEM, "CapitalOrgInfoService");
				CapitalOrgInfoService.Client client = (CapitalOrgInfoService.Client) factory.getClient();
				capitalOrgInfo = client.getById(pid);
			}
		} catch (Exception e) {
			logger .error("跳转新增或编辑页面：" + ExceptionUtil.getExceptionMessage(e));
	        e.printStackTrace();
		}finally{
			destroyFactory(factory);
		}
		model.put("capitalOrgInfo", capitalOrgInfo);
		model.put("editType", editType);
		return "/system/edit_capital";
	}
	
	/**
	 * 保存资金机构信息
	  * @param capitalOrgInfo
	  * @param response
	  * @author: baogang
	  * @date: 2016年9月18日 下午2:55:31
	 */
	@RequestMapping(value="saveOrUpdate")
	public void saveOrUpdate(CapitalOrgInfo capitalOrgInfo,HttpServletResponse response){
		Json j = super.getSuccessObj();
		int pid = capitalOrgInfo.getPid();
		BaseClientFactory factory = null;
		try {
			factory = getFactory(BusinessModule.MODUEL_SYSTEM, "CapitalOrgInfoService");
			CapitalOrgInfoService.Client client = (CapitalOrgInfoService.Client) factory.getClient();
			SysUser sysUser = getSysUser();//登录用户
			if(pid>0){
				capitalOrgInfo.setUpdateId(sysUser.getPid());
				client.update(capitalOrgInfo);
			}else{
				capitalOrgInfo.setStatus(1);//新增的机构有效
				capitalOrgInfo.setCreateId(sysUser.getPid());
				pid = client.insert(capitalOrgInfo);
			}
			if(pid>0){
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "操作成功！");
			}else{
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "操作失败，请重新操作！");
			}
		}catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.debug("保存资金机构信息:" + ExceptionUtil.getExceptionMessage(e));
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "操作失败,请重新操作!");
		}finally{
			destroyFactory(factory);
		}
		j.getHeader().put("pid", pid);
		outputJson(j, response);
	}
	
	/**
	 * 查询资金机构资金成本信息
	  * @param orgId
	  * @param response
	  * @author: baogang
	  * @date: 2016年9月18日 下午3:11:23
	 */
	@RequestMapping(value = "getCapitalCostList")
	@ResponseBody
	public void getCapitalCostList(int orgId,HttpServletResponse response){
		List<OrgCapitalCost> resultList = new ArrayList<OrgCapitalCost>();
		BaseClientFactory factory = null;
		try {
			factory = getFactory(BusinessModule.MODUEL_SYSTEM, "OrgCapitalCostService");
			OrgCapitalCostService.Client client = (OrgCapitalCostService.Client) factory.getClient();
			OrgCapitalCost query = new OrgCapitalCost();
			if(orgId >0){
				query.setStepAmount(-1);//查询是不带阶梯金额条件
				query.setOrgId(orgId);
				resultList = client.getAll(query);
			}
		} catch (Exception e) {
			logger.debug("保存资金机构信息:" + ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(factory);
		}
		// 输出
		outputJson(resultList, response);
	}
	
	/**
	 * 保存资金成本信息
	  * @param capitalOrgInfo
	  * @param response
	  * @author: baogang
	  * @date: 2016年9月18日 下午2:55:31
	 */
	@RequestMapping(value="saveCapitalCost")
	public void saveCapitalCost(OrgCapitalCost orgCapitalCost,HttpServletResponse response){
		Json j = super.getSuccessObj();
		int pid = orgCapitalCost.getPid();
		BaseClientFactory factory = null;
		try {
			factory = getFactory(BusinessModule.MODUEL_SYSTEM, "OrgCapitalCostService");
			OrgCapitalCostService.Client client = (OrgCapitalCostService.Client) factory.getClient();
			//判断次阶梯金额是否存在
			OrgCapitalCost query = new OrgCapitalCost();
			query.setOrgId(orgCapitalCost.getOrgId());
			query.setStepAmount(orgCapitalCost.getStepAmount());
			List<OrgCapitalCost> list = client.getAll(query);
			boolean flag = false;
			if(list != null && list.size() > 0){
				OrgCapitalCost result = list.get(0);
				if(result.getPid() >0 && result.getPid() != pid){
					flag = true;
				}
			}
			//如果阶梯金额已存在
			if(flag){
				logger.error("已存在阶梯金额：" + pid);
			    fillReturnJson(response, false, "此机构阶梯金额已存在,请重新操作!");
			    return;
			}
			
			SysUser sysUser = getSysUser();//登录用户
			if(pid>0){
				orgCapitalCost.setUpdateId(sysUser.getPid());
				client.update(orgCapitalCost);
			}else{
				orgCapitalCost.setCreateId(sysUser.getPid());
				pid = client.insert(orgCapitalCost);
			}
			if(pid>0){
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "操作成功！");
			}else{
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "操作失败，请重新操作！");
			}
		}catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.debug("保存资金成本信息:" + ExceptionUtil.getExceptionMessage(e));
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "操作失败,请重新操作!");
		}finally{
			destroyFactory(factory);
		}
		j.getHeader().put("pid", pid);
		outputJson(j, response);
	}
	
	/**
	 * 删除资金成本信息
	  * @param pid
	  * @param response
	  * @author: baogang
	  * @date: 2016年9月18日 下午3:30:12
	 */
	@RequestMapping(value = "deleteCapitalCost")
	@ResponseBody
	public void deleteCapitalCost(int pid, HttpServletResponse response) {
		if(pid <=0){
			logger.error("请求数据不合法：" + pid);
		    fillReturnJson(response, false, "请求数据不合法,请重新操作!");
		    return;
		}
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "OrgCapitalCostService");
			OrgCapitalCostService.Client client = (OrgCapitalCostService.Client) clientFactory.getClient();
			int rows = client.delCapitionCost(pid);
			// 判断是否成功
			if (rows == 0) {
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "删除资金成本信息失败,请重新操作！");
			}else{
				j.getHeader().put("success", true);
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "删除资金成本信息失败,请重新操作！");
		} finally {
			destroyFactory(clientFactory);
		}
		outputJson(j, response);
	}
	
	/**
	 * 修改资金机构状态
	  * @param pid
	  * @param status
	  * @param response
	  * @author: baogang
	  * @date: 2016年9月21日 下午2:34:31
	 */
	@RequestMapping(value = "/updateStatus")
	public void updateStatus(Integer pid,Integer status, HttpServletResponse response){
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "CapitalOrgInfoService");
			CapitalOrgInfoService.Client client = (CapitalOrgInfoService.Client) clientFactory.getClient();
			CapitalOrgInfo capitalOrgInfo = client.getById(pid);
			capitalOrgInfo.setStatus(status);
			int rows =client.update(capitalOrgInfo);
			if(rows != 0){
				j.getHeader().put("success", true);
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_SYSTEM, SysLogTypeConstant.LOG_TYPE_UPDATE, "修改资金机构状态，标题：" + capitalOrgInfo.getOrgName());
			}else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "修改失败,请重新操作!");
			}
			
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.error("修改资金机构状态:" + ExceptionUtil.getExceptionMessage(e));
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "修改失败,请重新操作!");
		} finally {
			destroyFactory(clientFactory);
		}
		
		outputJson(j, response);
	}
	
	/**
	 * 检查机构名是否存在
	  * @param orgName
	  * @param pid
	  * @param response
	  * @author: baogang
	  * @date: 2016年9月21日 下午3:26:35
	 */
	@RequestMapping(value="checkOrgNameIsExist" , method=RequestMethod.POST)
	public void checkOrgNameIsExist(String orgName ,Integer pid, HttpServletResponse response){
		boolean flag = true;
		BaseClientFactory factory = null;
		try {
			factory = getFactory(BusinessModule.MODUEL_SYSTEM, "CapitalOrgInfoService");
			CapitalOrgInfoService.Client client = (CapitalOrgInfoService.Client) factory.getClient();
			CapitalOrgInfo query = new CapitalOrgInfo();
			query.setOrgName(orgName);
			List<CapitalOrgInfo> result = client.getAll(query);
			if(result != null && result.size() > 0){
				CapitalOrgInfo capitalOrgInfo = result.get(0);
				if(capitalOrgInfo.getPid() >0 && capitalOrgInfo.getPid() != pid){
					flag = false;
				}
			}
			
		} catch (Exception e) {
			logger.error("CapitalOrgInfoController.checkOrgNameIsExist:" + ExceptionUtil.getExceptionMessage(e));
	        e.printStackTrace();
		}finally {
			destroyFactory(factory);
		}
		// 输出
		outputJson(flag, response);
	}
	
	/**
	 * 检查机构代码是否存在
	  * @param orgCode
	  * @param pid
	  * @param response
	  * @author: baogang
	  * @date: 2016年9月21日 下午3:31:04
	 */
	@RequestMapping(value="checkOrgCodeIsExist" , method=RequestMethod.POST)
	public void checkOrgCodeIsExist(String orgCode ,Integer pid, HttpServletResponse response){
		boolean flag = true;
		BaseClientFactory factory = null;
		try {
			factory = getFactory(BusinessModule.MODUEL_SYSTEM, "CapitalOrgInfoService");
			CapitalOrgInfoService.Client client = (CapitalOrgInfoService.Client) factory.getClient();
			CapitalOrgInfo query = new CapitalOrgInfo();
			query.setOrgCode(orgCode);
			List<CapitalOrgInfo> result = client.getAll(query);
			if(result != null && result.size() > 0){
				CapitalOrgInfo capitalOrgInfo = result.get(0);
				if(capitalOrgInfo.getPid() >0 && capitalOrgInfo.getPid() != pid){
					flag = false;
				}
			}
			
		} catch (Exception e) {
			logger.error("CapitalOrgInfoController.checkOrgCodeIsExist:" + ExceptionUtil.getExceptionMessage(e));
	        e.printStackTrace();
		}finally {
			destroyFactory(factory);
		}
		// 输出
		outputJson(flag, response);
	}
	
	/**
	 * 判断阶梯金额是否重复
	  * @param stepAmount
	  * @param pid
	  * @param orgId
	  * @param response
	  * @author: baogang
	  * @date: 2016年9月21日 下午3:47:49
	 */
	@RequestMapping(value="checkStepAmount" , method=RequestMethod.POST)
	public void checkStepAmount(double stepAmount ,Integer pid,Integer orgId, HttpServletResponse response){
		if(orgId == null || orgId <=0){
			logger.error("请求数据不合法：" + pid);
		    fillReturnJson(response, false, "请求数据不合法,请重新操作!");
		    return;
		}
		boolean flag = true;
		BaseClientFactory factory = null;
		try {
			factory = getFactory(BusinessModule.MODUEL_SYSTEM, "OrgCapitalCostService");
			OrgCapitalCostService.Client client = (OrgCapitalCostService.Client) factory.getClient();
			OrgCapitalCost query = new OrgCapitalCost();
			query.setOrgId(orgId);
			query.setStepAmount(stepAmount);
			List<OrgCapitalCost> result = client.getAll(query);
			if(result != null && result.size() > 0){
				OrgCapitalCost orgCapitalCost = result.get(0);
				if(orgCapitalCost.getPid() >0 && orgCapitalCost.getPid() != pid){
					flag = false;
				}
			}
			
		} catch (Exception e) {
			logger.error("CapitalOrgInfoController.checkStepAmount:" + ExceptionUtil.getExceptionMessage(e));
	        e.printStackTrace();
		}finally {
			destroyFactory(factory);
		}
		// 输出
		outputJson(flag, response);
	}
	
	/**
	 * 查询资金机构信息
	  * @param response
	  * @author: baogang
	  * @date: 2016年9月22日 下午2:54:47
	 */
	@RequestMapping(value = "getAllCapitalOrg")
	public void getAllCapitalOrg(HttpServletResponse response){
		List<CapitalOrgInfo> resultList = new ArrayList<CapitalOrgInfo>();
		BaseClientFactory factory = null;
		try {
			factory = getFactory(BusinessModule.MODUEL_SYSTEM, "CapitalOrgInfoService");
			CapitalOrgInfoService.Client client = (CapitalOrgInfoService.Client) factory.getClient();
			CapitalOrgInfo capitalOrgInfo = new CapitalOrgInfo();
			capitalOrgInfo.setPid(-1);
			capitalOrgInfo.setOrgName("--请选择--");
			resultList.add(capitalOrgInfo);
			CapitalOrgInfo query = new CapitalOrgInfo();
			query.setStatus(1);
			resultList = client.getAll(query);
		} catch (Exception e) {
			logger.debug("查询资金机构信息:" + ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(factory);
		}
		// 输出
		outputJson(resultList, response);
	}
}
