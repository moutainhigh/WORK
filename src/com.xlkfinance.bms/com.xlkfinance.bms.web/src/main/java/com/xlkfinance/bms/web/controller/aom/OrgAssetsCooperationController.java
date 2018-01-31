/**
 * @Title: OrgAssetsCooperationController.java
 * @Package com.xlkfinance.bms.web.controller.aom
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: baogang
 * @date: 2016年7月5日 下午5:54:35
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.aom;

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
import com.achievo.framework.thrift.exception.ThriftException;
import com.qfang.xk.aom.rpc.org.OrgAssetsCooperationInfo;
import com.qfang.xk.aom.rpc.org.OrgAssetsCooperationService;
import com.qfang.xk.aom.rpc.org.OrgBizAdviserAllocationInf;
import com.qfang.xk.aom.rpc.org.OrgBizAdviserAllocationInfPage;
import com.qfang.xk.aom.rpc.org.OrgBizAdviserAllocationInfService;
import com.qfang.xk.aom.rpc.org.OrgCooperateCityInfo;
import com.qfang.xk.aom.rpc.system.OrgUserInfo;
import com.qfang.xk.aom.rpc.system.OrgUserService;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.OrgSysLogTypeConstant;
import com.xlkfinance.bms.common.constant.SysLogTypeConstant;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.SysAreaInfoService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;

/**
 * 机构管理平台--资产合作机构信息controller
  * @ClassName: OrgAssetsCooperationController
  * @author: baogang
  * @date: 2016年7月5日 下午5:55:05
 */
@Controller
@RequestMapping("/orgAssetsCooperationController")
public class OrgAssetsCooperationController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(OrgAssetsCooperationController.class);
	
	/**
	 * 机构管理已分配列表跳转
	  * @return
	  * @author: baogang
	  * @date: 2016年7月5日 下午5:56:49
	 */
	@RequestMapping(value = "orgIndex")
	public String orgIndex() {
		return "aom/org/org_allocation_index";
	}
	
	/**
	 * 机构管理未分配列表跳转
	  * @return
	  * @author: baogang
	  * @date: 2016年9月12日 上午10:57:42
	 */
	@RequestMapping(value = "orgAllocation")
	public String orgAllocation(){
		return "aom/org/allocation_index";
	}
	
	/**
	 * 查询机构管理已分配列表
	  * @param orgAssets
	  * @param request
	  * @param response
	  * @author: baogang
	  * @date: 2016年7月6日 上午10:36:58
	 */
	@RequestMapping(value = "getAllOrgAssets", method = RequestMethod.POST)
	public void getAllOrgAssets(OrgBizAdviserAllocationInfPage query,HttpServletRequest request, HttpServletResponse response){
		// 需要返回的Map集合
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		try {
			 query.setAllocationType(1);//已分配
			 query.setUserIds(getUserIds(request));//设置数据权限
			 clientFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgBizAdviserAllocationInfService");
	         OrgBizAdviserAllocationInfService.Client client = (OrgBizAdviserAllocationInfService.Client) clientFactory.getClient();
	         List<OrgBizAdviserAllocationInfPage> list = client.queryOrgBizAdviserAllocationInfPage(query);
	         int total = client.getOrgBizAdviserAllocationInfPageTotal(query);
	         map.put("rows", list);
	         map.put("total", total);
		} catch (Exception e) {
				logger.error("查询机构管理已分配列表:" + ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(map, response);
	}
	/**
    * 查询机构管理列表
     * @param orgAssets
     * @param request
     * @param response
     * @author: baogang
     * @date: 2016年7月6日 上午10:36:58
    */
   @RequestMapping(value = "orgList", method = RequestMethod.POST)
   public void orgList(OrgAssetsCooperationInfo orgAssets,HttpServletRequest request, HttpServletResponse response){
      // 需要返回的Map集合
      Map<String, Object> map = new HashMap<String, Object>();
      BaseClientFactory clientFactory = null;
      try {
          clientFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgAssetsCooperationService");
         OrgAssetsCooperationService.Client client = (OrgAssetsCooperationService.Client) clientFactory.getClient();
         // 创建集合
         List<OrgAssetsCooperationInfo> list = client.getOrgAssetsByPage(orgAssets);
         int count = client.getOrgAssetsCooperationCount(orgAssets);
         map.put("rows", list);
         map.put("total", count);
      } catch (Exception e) {
            logger.error("查询机构管理列表:" + ExceptionUtil.getExceptionMessage(e));
      }finally{
         destroyFactory(clientFactory);
      }
      // 输出
      outputJson(map, response);
   }
	/**
	 * 机构分配列表
	 *@author:liangyanjun
	 *@time:2016年9月8日上午10:30:58
	 *@param orgCooperat
	 *@param request
	 *@param response
	 */
	@RequestMapping(value = "orgAllocationList", method = RequestMethod.POST)
   public void orgAllocationList(OrgBizAdviserAllocationInfPage query,HttpServletRequest request, HttpServletResponse response){
      // 需要返回的Map集合
      Map<String, Object> map = new HashMap<String, Object>();
      BaseClientFactory orgFactory = null;
      try {
    	 query.setAllocationType(AomConstant.ORG_IS_ASSIGNED_1);//未分配
         orgFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgBizAdviserAllocationInfService");
         OrgBizAdviserAllocationInfService.Client client = (OrgBizAdviserAllocationInfService.Client) orgFactory.getClient();
         List<OrgBizAdviserAllocationInfPage> list = client.queryOrgBizAdviserAllocationInfPage(query);
         int total = client.getOrgBizAdviserAllocationInfPageTotal(query);
         map.put("rows", list);
         map.put("total", total);
      } catch (Exception e) {
            logger.error("机构分配列表:" + ExceptionUtil.getExceptionMessage(e));
      }finally{
         destroyFactory(orgFactory);
      }
      // 输出
      outputJson(map, response);
   }
	/**
	 * 机构分配
	 *@author:liangyanjun
	 *@time:2016年9月8日下午5:08:53
	 *@param query
	 *@param req
	 *@param resp
	 */
	@RequestMapping(value = "orgAllocation", method = RequestMethod.POST)
	public void orgAllocation(OrgBizAdviserAllocationInf allocationInf,HttpServletRequest req, HttpServletResponse resp){
	   int serviceObjId = allocationInf.getServiceObjId();//服务对象编号(机构系统用户id)
	   int bizAdviserId = allocationInf.getBizAdviserId();//商务顾问编号(ERP后台系统用户id)
	   if (bizAdviserId<=0||serviceObjId<=0) {
	      fillReturnJson(resp, false, "参数不合法,重新操作!");
         return;
      }
	   BaseClientFactory orgFactory = null;
	   Integer userId = getShiroUser().getPid();
	   try {
	      orgFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgBizAdviserAllocationInfService");
	      OrgBizAdviserAllocationInfService.Client client = (OrgBizAdviserAllocationInfService.Client) orgFactory.getClient();
	      OrgBizAdviserAllocationInf query=new OrgBizAdviserAllocationInf();
	      query.setServiceObjId(serviceObjId);
         List<OrgBizAdviserAllocationInf> list = client.getAll(query);
         
	      allocationInf.setUpdateId(userId);
	      //如果已经分配，则进行更新，没有分配则进行插入新数据
	      if (list==null||list.isEmpty()) {
	         allocationInf.setType(AomConstant.USER_TYPE_1);
	         allocationInf.setCreaterId(userId);
	         client.insert(allocationInf);
         }else{
            OrgBizAdviserAllocationInf updateAllocationInf = list.get(0);
            updateAllocationInf.setBizAdviserId(bizAdviserId);
            client.update(updateAllocationInf);
         }
	      recordAomLog(OrgSysLogTypeConstant.LOG_TYPE_UPDATE, "机构分配：参数"+allocationInf, req);
	   } catch (Exception e) {
	      e.printStackTrace();
	      logger.error("机构分配:" + ExceptionUtil.getExceptionMessage(e));
	      fillReturnJson(resp, false, "提交失败,重新操作!");
	   }finally{
	      destroyFactory(orgFactory);
	   }
	   fillReturnJson(resp, true, "提交成功");
	}
	
	/**
	 * 查看或者新增机构,根据editType的值判断是查看、编辑
	  * @param orgId
	  * @param editType
	  * @param model
	  * @return
	  * @author: baogang
	  * @date: 2016年7月6日 上午10:37:14
	 */
	@RequestMapping(value = "editOrgAssets")
	public String editOrgAssets(@RequestParam(value = "orgId", required = false) Integer orgId,@RequestParam(value = "editType", required = false)String editType, ModelMap model){
		OrgAssetsCooperationInfo orgAssetsInfo = new OrgAssetsCooperationInfo();
		BaseClientFactory clientFactory = null;
		try {
		    if(orgId != null && orgId >0){
		    	clientFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgAssetsCooperationService");
				OrgAssetsCooperationService.Client client = (OrgAssetsCooperationService.Client) clientFactory.getClient();
				orgAssetsInfo = client.getById(orgId);
			}
		} catch (Exception e) {
			logger.error("查看或者新增机构,根据editType的值判断是查看、编辑:" + ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(clientFactory);
		}
		model.put("orgId", orgId);
		model.put("orgAssetsInfo", orgAssetsInfo);
		model.put("editType", editType);
		return "aom/org/org_tab";
	}
	
	/**
	    * 查询机构管理列表
	     * @param orgId
	     * @param response
	     * @author: jony
	     * @date: 2016年7月6日 上午10:36:58
	    */
	   @RequestMapping(value = "getOcityInfoList")
		@ResponseBody
		public void getOcityInfoList(@RequestParam(value = "orgId", required = true) Integer orgId, HttpServletResponse response) throws Exception {
			BaseClientFactory clientFactory = null;
			List<OrgCooperateCityInfo> ocityInfoList = new ArrayList<OrgCooperateCityInfo>();
			try {
				clientFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgAssetsCooperationService");
				OrgAssetsCooperationService.Client client = (OrgAssetsCooperationService.Client) clientFactory.getClient();
				ocityInfoList = client.getOrgCityInfoListByOrgId(orgId);
			}catch (Exception e) {
				logger.error("getOcityInfoList fail",e);
			} finally {
				if (clientFactory != null) {
					try {
						clientFactory.destroy();
					} catch (ThriftException e) {
						e.printStackTrace();
					}
				}
			}
			outputJson(ocityInfoList, response);
		}
	
	/**
	 * 新增或者修改机构信息
	  * @param orgAssetsInfo
	  * @param response
	  * @author: baogang
	  * @date: 2016年7月6日 下午4:09:57
	 */
	@RequestMapping(value="saveOrUpdateOrgAssets")
	public void saveOrUpdateOrgAssets(OrgAssetsCooperationInfo orgAssets, HttpServletResponse response,HttpServletRequest req) {
		logger.info("新增或者修改机构信息，参数：" + orgAssets);
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		BaseClientFactory orgFactory = null;
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgAssetsCooperationService");
			OrgAssetsCooperationService.Client client = (OrgAssetsCooperationService.Client) clientFactory.getClient();
			orgFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgBizAdviserAllocationInfService");
		    OrgBizAdviserAllocationInfService.Client orgClient = (OrgBizAdviserAllocationInfService.Client) orgFactory.getClient();
		   
		    int orgId = orgAssets.getPid();
			String currentDateTime = DateUtils.getCurrentDateTime();
			//登录人员ID
			int userId = getSysUser().getPid();
			String orgName = orgAssets.getOrgName().trim();//去除机构名称的空格，防止查询以及触发器查询时重复
			orgAssets.setOrgName(orgName);
			//修改
			if(orgId >0){
				orgAssets.setUpdateDate(currentDateTime);
				orgAssets.setUpdateId(userId);
				client.updateOrgAssetsInfo(orgAssets);
			}else{
				//新增
				orgAssets.setCreatedDate(currentDateTime);
				orgAssets.setCreatorId(userId);//创建人
				orgAssets.setBizAdviserId(userId);//商务顾问
				orgAssets.setAuditStatus(AomConstant.ORG_AUDIT_STATUS_3);//后台新增客户默认已认证，分配给该创建人
				orgId = client.insert(orgAssets);
				//入库机构费分配表
				OrgBizAdviserAllocationInf allocationInf = new OrgBizAdviserAllocationInf();
				allocationInf.setBizAdviserId(userId);
				allocationInf.setServiceObjId(orgId);
				allocationInf.setCreaterId(userId);
				allocationInf.setType(AomConstant.USER_TYPE_1);
				orgClient.insert(allocationInf);
			}
			
			recordAomLog(OrgSysLogTypeConstant.LOG_TYPE_ADD, "ERP后台新增或者修改机构信息：OrgAssetsCooperationController.saveOrUpdateOrgAssets 参数："+orgAssets, req);
			//ERP 后台日志
			recordLog(BusinessModule.MODUEL_ORG, SysLogTypeConstant.LOG_TYPE_UPDATE, "新增或者修改机构信息参数"+orgAssets,orgAssets.getPid());
			j.getHeader().put("orgId", orgId);
			j.getHeader().put("success", true);
			j.getHeader().put("msg", "保存成功");
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.error("新增或者修改机构信息:" + ExceptionUtil.getExceptionMessage(e));
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "保存失败,请重新操作!");
		}finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(j, response);
	}
	
	/**
	 * 检查登录名是否已用过
	  * @param loginName
	  * @param response
	  * @author: baogang
	  * @date: 2016年7月6日 下午7:27:29
	 */
	@RequestMapping(value="checkUserNameIsExist" , method=RequestMethod.POST)
	public void checkUserNameIsExist(String loginName ,Integer pid, HttpServletResponse response){
		boolean flag = true;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG_SYSTEM, "OrgUserService");
			OrgUserService.Client client = (OrgUserService.Client) clientFactory.getClient();
			OrgUserInfo orgUser = new OrgUserInfo();
			orgUser.setUserName(loginName);
			List<OrgUserInfo> result = client.getAll(orgUser);
			if(result != null && result.size() > 0){
				OrgUserInfo userInfo = result.get(0);
				if(userInfo.getPid() >0 && userInfo.getPid() != pid){
					flag = false;
				}
			}
			
		} catch (Exception e) {
			logger.error("OrgAssetsCooperationController.checkUserNameIsExist:" + ExceptionUtil.getExceptionMessage(e));
	        e.printStackTrace();
		}finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(flag, response);
	}
	
	/**
	 * 检查邮箱以及手机号码是否重复
	  * @param loginName
	  * @param response
	  * @author: baogang
	  * @date: 2016年7月6日 下午7:27:47
	 */
	@RequestMapping(value="checkEmailIsExist" , method=RequestMethod.POST)
	public void checkEmailIsExist(OrgUserInfo orgUser , HttpServletResponse response){
		boolean flag = true;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG_SYSTEM, "OrgUserService");
			int pid = orgUser.getPid();
			OrgUserService.Client client = (OrgUserService.Client) clientFactory.getClient();
			OrgUserInfo query = new OrgUserInfo();
			query.setPhone(orgUser.getPhone());
			query.setEmail(orgUser.getEmail());
			List<OrgUserInfo> result = client.getAll(query);
			if(result != null && result.size() > 0){
				OrgUserInfo userInfo = result.get(0);
				if(userInfo.getPid() >0 && userInfo.getPid() != pid){
					flag = false;
				}
			}
		} catch (Exception e) {
			logger.error("OrgAssetsCooperationController.checkEmailIsExist:" + ExceptionUtil.getExceptionMessage(e));
	        e.printStackTrace();
		}finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(flag, response);
	}
	
	/**
	 *	检查组织编码是否重复
	  * @param loginName
	  * @param response
	  * @author: baogang
	  * @date: 2016年7月6日 下午7:28:37
	 */
	@RequestMapping(value="checkOrgCodeIsExist" , method=RequestMethod.POST)
	public void checkOrgCodeIsExist(String code ,Integer pid, HttpServletResponse response){
		boolean flag = true;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgAssetsCooperationService");
			OrgAssetsCooperationService.Client client = (OrgAssetsCooperationService.Client) clientFactory.getClient();
			OrgAssetsCooperationInfo orgAssets = new OrgAssetsCooperationInfo();
			orgAssets.setCode(code);
			List<OrgAssetsCooperationInfo> result = client.getAll(orgAssets);
			if(result != null && result.size() > 0){
				OrgAssetsCooperationInfo orgInfo = result.get(0);
				if(orgInfo.getPid()>0 && orgInfo.getPid() != pid){
					flag = false;
				}
			}
			
		} catch (Exception e) {
			logger.error("OrgAssetsCooperationController.checkOrgCodeIsExist:" + ExceptionUtil.getExceptionMessage(e));
	        e.printStackTrace();
		}finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(flag, response);
	}
	
	/**
	 * 修改认证状态
	  * @param orgAssets
	  * @param response
	  * @author: baogang
	  * @date: 2016年7月8日 上午10:49:12
	 */
	@RequestMapping(value="editAuditStatus")
	public void editAuditStatus(OrgAssetsCooperationInfo orgAssets, HttpServletResponse response,HttpServletRequest req){
		int pid = orgAssets.getPid();
	    if (pid <= 0) {
	         logger.error("请求数据不合法：" + orgAssets);
	         fillReturnJson(response, false, "请输入必填项,重新操作!");
	         return;
	    }
	    BaseClientFactory clientFactory = null;
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgAssetsCooperationService");
	    	OrgAssetsCooperationService.Client client = (OrgAssetsCooperationService.Client) clientFactory.getClient();
	    	client.updateAuditStatus(orgAssets);
	    	
	    	recordAomLog(OrgSysLogTypeConstant.LOG_TYPE_ADD, "修改认证状态：OrgAssetsCooperationController.editAuditStatus 参数："+orgAssets, req);
			//ERP 后台日志
			recordLog(BusinessModule.MODUEL_ORG, SysLogTypeConstant.LOG_TYPE_UPDATE, "修改认证状态"+orgAssets,orgAssets.getPid());
	        fillReturnJson(response, true, "操作成功");
	      } catch (Exception e) {
	         fillReturnJson(response, false, "操作失败,请重新操作!");
	         logger.error("操作失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + orgAssets);
	         e.printStackTrace();
	      }finally {
				destroyFactory(clientFactory);
	      }
	}
	/**
	    * 查询机构管理列表
	     * @param 用户Id
	     * @param response
	     * @author: jony
	     * @date: 2017年7月6日 上午10:36:58
	    */
	   @RequestMapping(value = "getOrgCooperateCityInfo")
		@ResponseBody
		public OrgCooperateCityInfo getOrgCooperateCityInfo(@RequestParam(value = "orgId", required = true) Integer orgId, HttpServletResponse response) throws Exception {
			BaseClientFactory clientFactory = null;
			List<OrgCooperateCityInfo> ocityInfoList = new ArrayList<OrgCooperateCityInfo>();
			try {
				clientFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgAssetsCooperationService");
				OrgAssetsCooperationService.Client client = (OrgAssetsCooperationService.Client) clientFactory.getClient();
				ocityInfoList = client.getOrgCityInfoListByOrgId(orgId);
			}catch (Exception e) {
				logger.error("getOcityInfoList fail",e);
			}
			return null;
		}
	
	
}
