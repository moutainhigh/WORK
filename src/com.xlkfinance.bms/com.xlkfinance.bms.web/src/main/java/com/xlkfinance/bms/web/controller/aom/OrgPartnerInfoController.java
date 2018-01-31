/**
 * @Title: OrgPartnerInfoController.java
 * @Package com.xlkfinance.bms.web.controller.aom
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: baogang
 * @date: 2016年7月15日 上午11:16:39
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.aom;

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

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.qfang.xk.aom.rpc.partner.OrgPartnerDTO;
import com.qfang.xk.aom.rpc.partner.OrgPartnerInfo;
import com.qfang.xk.aom.rpc.partner.OrgPartnerInfoService;
import com.qfang.xk.aom.rpc.system.OrgUserFile;
import com.qfang.xk.aom.rpc.system.OrgUserFileService;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.OrgSysLogTypeConstant;
import com.xlkfinance.bms.common.constant.SysLogTypeConstant;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;
import com.xlkfinance.bms.web.util.FileUtil;

/**
 * 合伙人信息Controller
  * @ClassName: OrgPartnerInfoController
  * @author: baogang
  * @date: 2016年7月15日 上午11:16:43
 */
@Controller
@RequestMapping("/orgPartnerInfoController")
public class OrgPartnerInfoController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(OrgPartnerInfoController.class);
	
	/**
	 * 跳转合伙人列表页面
	  * @return
	  * @author: baogang
	  * @date: 2016年7月15日 上午11:18:45
	 */
	@RequestMapping(value="index")
	public String index(){
		return "/aom/partner/index";
	}
	
	/**
	 * 跳转合伙人列表页面
	  * @return
	  * @author: baogang
	  * @date: 2016年7月15日 上午11:18:45
	 */
	@RequestMapping(value="cooperateIndex")
	public String cooperateIndex(){
		return "/aom/partner/cooperate_index";
	}
	
	/**
	 * 分页查询合伙人信息
	  * @param orgPartner
	  * @param request
	  * @param response
	  * @author: baogang
	  * @date: 2016年7月15日 上午11:47:58
	 */
	@RequestMapping(value = "getOrgPartnerByPage", method = RequestMethod.POST)
	public void getOrgPartnerByPage(OrgPartnerDTO orgPartner,HttpServletRequest request, HttpServletResponse response){
		// 需要返回的Map集合
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG_PARTNER, "OrgPartnerInfoService");
			OrgPartnerInfoService.Client client = (OrgPartnerInfoService.Client) clientFactory.getClient();
			// 创建集合
			List<OrgPartnerDTO> list = client.getOrgPartnerByPage(orgPartner);
			int count = client.getOrgPartnerInfoCount(orgPartner);
			map.put("rows", list);
			map.put("total", count);
		} catch (Exception e) {
				logger.error("分页查询合伙人信息:" + ExceptionUtil.getExceptionMessage(e));
		}
		// 输出
		outputJson(map, response);
	}
	
	
	/**
	 * 分页查询合伙人合作信息
	  * @param orgPartner
	  * @param request
	  * @param response
	  * @author: baogang
	  * @date: 2016年7月15日 上午11:47:58
	 */
	@RequestMapping(value = "getPartnerCooperationInfo", method = RequestMethod.POST)
	public void getPartnerCooperationInfo(OrgPartnerDTO orgPartner,HttpServletRequest request, HttpServletResponse response){
		// 需要返回的Map集合
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG_PARTNER, "OrgPartnerInfoService");
			OrgPartnerInfoService.Client client = (OrgPartnerInfoService.Client) clientFactory.getClient();
			orgPartner.setAuditStatus(AomConstant.ORG_AUDIT_STATUS_3);//只有已认证的才显示
			// 创建集合
			List<OrgPartnerDTO> list = client.getOrgPartnerByPage(orgPartner);
			int count = client.getOrgPartnerInfoCount(orgPartner);
			map.put("rows", list);
			map.put("total", count);
		} catch (Exception e) {
				logger.error("分页查询合伙人信息:" + ExceptionUtil.getExceptionMessage(e));
		}
		// 输出
		outputJson(map, response);
	}
	
	/**
	 * 跳转编辑页面
	  * @param pid
	  * @param editType
	  * @param model
	  * @return
	  * @author: baogang
	  * @date: 2016年7月15日 上午11:53:18
	 */
	@RequestMapping(value = "editOrgPartnerInfo")
	public String editOrgPartnerInfo(@RequestParam(value = "pid", required = false) Integer pid,@RequestParam(value = "editType", required = false)String editType, ModelMap model){
		OrgPartnerInfo partnerInfo = new OrgPartnerInfo();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG_PARTNER, "OrgPartnerInfoService");
			if(pid != null && pid>0){
				OrgPartnerInfoService.Client client = (OrgPartnerInfoService.Client) clientFactory.getClient();
				partnerInfo = client.getById(pid);
			}
		} catch (Exception e) {
			logger.error("跳转编辑页面:" + ExceptionUtil.getExceptionMessage(e));
		}
		model.put("partnerInfo", partnerInfo);
		model.put("editType", editType);
		return "/aom/partner/edit_partner";
	}
	
	/**
	 * 跳转合作信息编辑页面
	  * @param pid
	  * @param editType
	  * @param model
	  * @return
	  * @author: baogang
	  * @date: 2016年7月15日 上午11:53:18
	 */
	@RequestMapping(value = "editPartnerCooperate")
	public String editPartnerCooperate(@RequestParam(value = "pid", required = false) Integer pid,@RequestParam(value = "editType", required = false)String editType, ModelMap model){
		OrgPartnerInfo partnerInfo = new OrgPartnerInfo();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG_PARTNER, "OrgPartnerInfoService");
			if(pid != null && pid>0){
				OrgPartnerInfoService.Client client = (OrgPartnerInfoService.Client) clientFactory.getClient();
				partnerInfo = client.getById(pid);
			}
		} catch (Exception e) {
			logger.error("跳转合作信息编辑页面:" + ExceptionUtil.getExceptionMessage(e));
		}
		model.put("partnerInfo", partnerInfo);
		model.put("editType", editType);
		return "/aom/partner/edit_cooperate";
	}
	
	/**
	 * 保存合伙人信息
	  * @param partnerInfo
	  * @param response
	  * @author: baogang
	  * @date: 2016年7月15日 上午11:56:32
	 */
	@RequestMapping(value="saveOrUpdatePartner")
	public void saveOrUpdatePartner(OrgPartnerInfo partnerInfo, HttpServletResponse response,HttpServletRequest req) {
		logger.info("保存合伙人信息，参数：" + partnerInfo);
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		
		recordAomLog(OrgSysLogTypeConstant.LOG_TYPE_ADD, "保存合伙人信息：OrgPartnerInfoController.saveOrUpdatePartner 参数："+partnerInfo, req);
		//ERP 后台日志
		recordLog(BusinessModule.MODUEL_ORG_PARTNER, SysLogTypeConstant.LOG_TYPE_UPDATE, "保存合伙人信息"+partnerInfo,partnerInfo.getPid());
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG_PARTNER, "OrgPartnerInfoService");
			OrgPartnerInfoService.Client client = (OrgPartnerInfoService.Client) clientFactory.getClient();
			int pid = partnerInfo.getPid();
			int userId = partnerInfo.getUserId();//用户Id
			int loginUserId = getShiroUser().getPid();//操作人ID
			if(pid>0){
				partnerInfo.setUpdateId(loginUserId);
			}else{
				partnerInfo.setCooperationStatus(AomConstant.ORG_COOPERATE_STATUS_1);
				partnerInfo.setReviewStatus(AomConstant.ORG_PARTNER_COOPERATE_APPLY_STATUS_1);//申请时
				partnerInfo.setCreatorId(loginUserId);
			}
			//通过返回值获取合伙人ID以及用户ID
			String result = client.saveOrUpdateOrgPartner(partnerInfo);
			if(!StringUtil.isBlank(result)){
				String[] ids =  result.split(",");
				pid = Integer.parseInt(ids[0]);
				userId = Integer.parseInt(ids[1]);
			}
			j.getHeader().put("pid", pid);
			j.getHeader().put("userId", userId);
			j.getHeader().put("success", true);
			j.getHeader().put("msg", "保存成功");
		}catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.error("保存合伙人信息:" + ExceptionUtil.getExceptionMessage(e));
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "保存失败,请重新操作!");
		}
		// 输出
		outputJson(j, response);
	}
	
	/**
	 * 修改认证状态
	  * @param orgAssets
	  * @param response
	  * @author: baogang
	  * @date: 2016年7月8日 上午10:49:12
	 */
	@RequestMapping(value="editAuditStatus")
	public void editAuditStatus(OrgPartnerInfo partnerInfo, HttpServletResponse response,HttpServletRequest req){
		int pid = partnerInfo.getPid();
	      if (pid <= 0) {
	         logger.error("请求数据不合法：" + partnerInfo);
	         fillReturnJson(response, false, "请求数据不合法,重新操作!");
	         return;
	      }
		  recordAomLog(OrgSysLogTypeConstant.LOG_TYPE_ADD, "修改认证状态：OrgPartnerInfoController.editAuditStatus 参数："+partnerInfo, req);
		  //ERP 后台日志
		  recordLog(BusinessModule.MODUEL_ORG_PARTNER, SysLogTypeConstant.LOG_TYPE_UPDATE, "保存合伙人信息"+partnerInfo,partnerInfo.getPid());
			
	      BaseClientFactory clientFactory = null;
	      try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG_PARTNER, "OrgPartnerInfoService");
	    	OrgPartnerInfoService.Client client = (OrgPartnerInfoService.Client) clientFactory.getClient();
	    	client.updateAuditStatus(partnerInfo);
	          
	        fillReturnJson(response, true, "操作成功");
	      } catch (Exception e) {
	         fillReturnJson(response, false, "操作失败,请重新操作!");
	         logger.error("操作失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + partnerInfo);
	         e.printStackTrace();
	      }
	}
	
	/**
	 * 保存合伙人合作信息
	  * @param partnerInfo
	  * @param response
	  * @author: baogang
	  * @date: 2016年7月15日 上午11:56:32
	 */
	@RequestMapping(value="savePartnerCooperateInfo")
	public void savePartnerCooperateInfo(OrgPartnerInfo partnerInfo, HttpServletResponse response,HttpServletRequest req) {
		logger.info("保存合伙人合作信息，参数：" + partnerInfo);
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		recordAomLog(OrgSysLogTypeConstant.LOG_TYPE_ADD, "保存合伙人合作信息：OrgPartnerInfoController.savePartnerCooperateInfo 参数："+partnerInfo, req);
		//ERP 后台日志
		recordLog(BusinessModule.MODUEL_ORG_PARTNER, SysLogTypeConstant.LOG_TYPE_UPDATE, "保存合伙人合作信息"+partnerInfo,partnerInfo.getPid());
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG_PARTNER, "OrgPartnerInfoService");
			OrgPartnerInfoService.Client client = (OrgPartnerInfoService.Client) clientFactory.getClient();
			int reviewStatus = partnerInfo.getReviewStatus();
			int loginUserId = getShiroUser().getPid();//操作人ID
			if(reviewStatus >AomConstant.ORG_PARTNER_COOPERATE_APPLY_STATUS_1){
				partnerInfo.setReviewId(loginUserId);
				partnerInfo.setReviewTime(DateUtils.getCurrentDateTime());
			}
			//修改合作信息
			int pid = client.updateReviewStatus(partnerInfo);
			j.getHeader().put("pid", pid);
		}catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.error("保存合伙人信息:" + ExceptionUtil.getExceptionMessage(e));
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "保存失败,请重新操作!");
		}
		// 输出
		outputJson(j, response);
	}
	
	 /**
    * 上传机构用户资料附件
    *@author:liangyanjun
    *@time:2016年9月1日上午11:00:25
    *@param req
    *@param resp
    *@throws Exception
    */
   @RequestMapping("/uploadUserFile")
   public void uploadUserFile(int orgPartnerId,HttpServletRequest req, HttpServletResponse resp) throws Exception {
      Map<String, Object> resultMap = FileUtil.uploadFile2(req, resp, "system", getAomUploadFilePath(), getFileSize(), getFileDateDirectory(), getUploadFileType());
      List<BizFile> bizFileList = (List<BizFile>) resultMap.get("bizFileList");
      if (bizFileList == null || bizFileList.isEmpty()) {
         return;
      }
      BaseClientFactory factory = getAomFactory(BusinessModule.MODUEL_ORG_SYSTEM, "OrgUserFileService");
      OrgUserFileService.Client client = (OrgUserFileService.Client) factory.getClient();
      client.saveOrgUserFile(orgPartnerId, bizFileList);
      recordAomLog(OrgSysLogTypeConstant.LOG_TYPE_UPLOAN, "上传机构用户资料附件文件：参数"+bizFileList, req);
      destroyFactory(factory);
   }
   /**
    * 上传机构用户资料附件分页查询
    *@author:liangyanjun
    *@time:2016年9月2日上午10:14:55
    *@param req
    *@param resp
    *@param query
    *@throws Exception
    */
   @RequestMapping("/userFileList")
   public void userFileList(HttpServletRequest req, HttpServletResponse resp, OrgUserFile query) throws Exception {
      Map<String, Object> map = new HashMap<String, Object>();
      BaseClientFactory factory = getAomFactory(BusinessModule.MODUEL_ORG_SYSTEM, "OrgUserFileService");
      OrgUserFileService.Client client = (OrgUserFileService.Client) factory.getClient();
      List<BizFile> list = client.queryOrgUserFilePage(query);
      int total = client.getOrgUserFileTotal(query);
      map.put("rows", list);
      map.put("total", total);
      outputJson(map, resp);
      destroyFactory(factory);
   }
}
