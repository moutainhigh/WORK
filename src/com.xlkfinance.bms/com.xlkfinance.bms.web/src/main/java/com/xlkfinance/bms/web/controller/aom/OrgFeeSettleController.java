/**
 * @Title: OrgFeeSettleController.java
 * @Package com.xlkfinance.bms.web.controller.aom
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: baogang
 * @date: 2016年8月1日 下午4:48:10
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.aom;

import java.util.ArrayList;
import java.util.Date;
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
import com.qfang.xk.aom.rpc.fee.OrgFeeSettle;
import com.qfang.xk.aom.rpc.fee.OrgFeeSettleDTO;
import com.qfang.xk.aom.rpc.fee.OrgFeeSettleDetail;
import com.qfang.xk.aom.rpc.fee.OrgFeeSettleDetailService;
import com.qfang.xk.aom.rpc.fee.OrgFeeSettleService;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyInf;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyService;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.system.TemplateFile;
import com.xlkfinance.bms.rpc.system.TemplateFileService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;
import com.xlkfinance.bms.web.util.CommonUtil;
import com.xlkfinance.bms.web.util.ExcelExport;
/**
 * 费用结算Controller
  * @ClassName: OrgFeeSettleController
  * @author: baogang
  * @date: 2016年8月1日 下午4:48:37
 */
@Controller
@RequestMapping("/orgFeeSettleController")
public class OrgFeeSettleController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(OrgFeeSettleController.class);
	private static final String PATH = "aom/fee/";
	
	/**
	 * 跳转费用列表页面
	  * @return
	  * @author: baogang
	  * @date: 2016年8月17日 上午11:35:21
	 */
	@RequestMapping(value="index")
	public String index(){
		return PATH+"index";
	}
	
	/**
	 * 分页查询费用信息
	  * @param query
	  * @param request
	  * @param response
	  * @author: baogang
	  * @date: 2016年8月17日 上午11:35:45
	 */
	@RequestMapping(value = "getOrgFeeList", method = RequestMethod.POST)
	public void getOrgFeeList(OrgFeeSettleDTO query,HttpServletRequest request, HttpServletResponse response){
		// 需要返回的Map集合
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG_FEE, "OrgFeeSettleService");
			OrgFeeSettleService.Client client = (OrgFeeSettleService.Client) clientFactory.getClient();
			// 创建集合
			List<OrgFeeSettleDTO> list = client.queryOrgFeeSettleByPage(query);
			int count = client.getOrgFeeSettleCount(query);
			map.put("rows", list);
			map.put("total", count);
			
		} catch (Exception e) {
			logger.error("分页查询费用信息:" + ExceptionUtil.getExceptionMessage(e));
		}
		// 输出
		outputJson(map, response);
	}
	
	/**
	 * 息费查看页面
	  * @param pid
	  * @param orgId
	  * @param model
	  * @return
	  * @author: baogang
	  * @date: 2016年8月18日 上午11:23:27
	 */
	@RequestMapping("/toEditOrgFee")
	public String toEditOrgFee(@RequestParam(value = "pid", required = false) Integer pid,@RequestParam(value = "orgId", required = false)Integer orgId, ModelMap model){
		BaseClientFactory clientFactory = null;
		OrgCooperatCompanyApplyInf orgInfo = new OrgCooperatCompanyApplyInf();
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgCooperatCompanyApplyService");
			OrgCooperatCompanyApplyService.Client client = (OrgCooperatCompanyApplyService.Client) clientFactory.getClient();
			//查询机构基本信息以及合作信息
			orgInfo = client.getByOrgId(orgId);

		} catch (Exception e) {
			logger.error("根据ID查询机构合作申请信息:" + ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(clientFactory);
		}
		model.put("orgCooperateInfo", orgInfo);
		model.put("pid", pid);
		model.put("orgId", orgId);
		return PATH+"edit_fee";
	}
	
	/**
	 * 查询解保数据
	  * @param pid
	  * @param orgId
	  * @param request
	  * @param response
	  * @author: baogang
	  * @date: 2016年8月18日 上午11:11:26
	 */
	@RequestMapping("/getCancelProjectList")
	public void getCancelProjectList(Integer pid,Integer orgId,HttpServletRequest request, HttpServletResponse response){
		BaseClientFactory feeFactory = null;
		BaseClientFactory detailFactory = null;
		List<OrgFeeSettleDetail> cancelProjectList = new ArrayList<OrgFeeSettleDetail>();
		try {
			feeFactory = getAomFactory(BusinessModule.MODUEL_ORG_FEE, "OrgFeeSettleService");
			OrgFeeSettleService.Client feeClient = (OrgFeeSettleService.Client) feeFactory.getClient();
			detailFactory = getAomFactory(BusinessModule.MODUEL_ORG_FEE, "OrgFeeSettleDetailService");
			OrgFeeSettleDetailService.Client detailClient = (OrgFeeSettleDetailService.Client) detailFactory.getClient();
			
			OrgFeeSettle orgFee = feeClient.getById(pid);
			String settleDate = orgFee.getSettleDate();
			Date date = DateUtils.stringToDate(settleDate, "yyyy-MM");
			String settleDateStart = DateUtils.dateToString(DateUtils.getFirstDayOfMonth(date));
			String settleDateEnd = DateUtils.dateToString(DateUtils.getLastDayOfMonth(DateUtils.getDateEnd(date)));
			
			OrgFeeSettleDTO query = new OrgFeeSettleDTO();
			query.setSettleDate(settleDateStart);
			query.setSettleDateEnd(settleDateEnd);
			query.setOrgId(orgId);
			//解保列表
			cancelProjectList = detailClient.getCancelProjectList(query);
		} catch (Exception e) {
			logger.error("根据ID查询机构合作申请信息:" + ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(feeFactory,detailFactory);
		}
		// 输出
		outputJson(cancelProjectList, response);
	}
	
	/**
	 * 查询放款数据
	  * @param pid
	  * @param orgId
	  * @param request
	  * @param response
	  * @author: baogang
	  * @date: 2016年8月18日 上午11:11:26
	 */
	@RequestMapping("/getLoanProjectList")
	public void getLoanProjectList(Integer pid,HttpServletRequest request, HttpServletResponse response){
		BaseClientFactory detailFactory = null;
		List<OrgFeeSettleDetail> loanProjectList = new ArrayList<OrgFeeSettleDetail>();
		try {
			detailFactory = getAomFactory(BusinessModule.MODUEL_ORG_FEE, "OrgFeeSettleDetailService");
			OrgFeeSettleDetailService.Client detailClient = (OrgFeeSettleDetailService.Client) detailFactory.getClient();
			//放款列表
			loanProjectList = detailClient.getLoanProjectList(pid);
		} catch (Exception e) {
			logger.error("根据ID查询机构合作申请信息:" + ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(detailFactory);
		}
		// 输出
		outputJson(loanProjectList, response);
	}
	
	/**
	 * 手动进行结算
	  * @param number
	  * @param request
	  * @param response
	  * @author: baogang
	  * @date: 2016年8月24日 下午5:17:49
	 */
	@RequestMapping("/createFeeSettle")
	public void createFeeSettle(Integer number,HttpServletRequest request, HttpServletResponse response){
		Json j = super.getSuccessObj();
		BaseClientFactory feeFactory = null;
		try {
			feeFactory = getAomFactory(BusinessModule.MODUEL_ORG_FEE, "OrgFeeSettleService");
			OrgFeeSettleService.Client feeClient = (OrgFeeSettleService.Client) feeFactory.getClient();
			if(number != null && number >=0){
				int result = feeClient.createLastMonthFeeSettle(number);
				if(result >0){
					j.getHeader().put("success", true);
					j.getHeader().put("msg", "结算成功");
				}else{
					j.getHeader().put("success", false);
					j.getHeader().put("msg", "结算出错，请联系系统管理员！");
				}
			}else{
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "参数错误");
			}
		} catch (Exception e) {
			logger.error("手动进行结算:" + ExceptionUtil.getExceptionMessage(e));
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "结算失败");
		}finally{
			destroyFactory(feeFactory);
		}
		// 输出
		outputJson(j, response);
	}
	
	/**
	 * 放款列表导出
	  * @param projectIds
	  * @param request
	  * @param response
	  * @author: baogang
	  * @date: 2016年9月7日 上午11:04:08
	 */
	@RequestMapping(value = "/loanProjectExcelExportList")
	@ResponseBody
	public void loanProjectExcelExportList(Integer pid,Integer orgId, HttpServletRequest request, HttpServletResponse response) {
	      	Map<String, Object> map = new HashMap<String, Object>();
	      	BaseClientFactory detailFactory = null;
	      	BaseClientFactory factory = null;
			List<OrgFeeSettleDetail> loanProjectList = new ArrayList<OrgFeeSettleDetail>();
			try {
				detailFactory = getAomFactory(BusinessModule.MODUEL_ORG_FEE, "OrgFeeSettleDetailService");
				OrgFeeSettleDetailService.Client detailClient = (OrgFeeSettleDetailService.Client) detailFactory.getClient();
				//放款列表
				loanProjectList = detailClient.getLoanProjectList(pid);
				if(loanProjectList != null && loanProjectList.size()>0){
					for(OrgFeeSettleDetail detail : loanProjectList){
						detail.setLoanDate(DateUtils.dateFormatByPattern(detail.getLoanDate(), "yyyy-MM-dd"));
					}
				}
				map.put("bean", loanProjectList);
	      		factory = getFactory(BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
	      		TemplateFileService.Client templateFileService = (TemplateFileService.Client) factory.getClient();
	      		TemplateFile template = templateFileService.getTemplateFile("LOANPROJECTLIST");

	      		if (template!=null&&!template.getFileUrl().equals("")) {
	      			String path = CommonUtil.getRootPath() + template.getFileUrl();
	      			ExcelExport.outToExcel(map, path, "放款列表导出" + "." + template.getFileUrl().substring(template.getFileUrl().lastIndexOf(".") + 1), response, request);
	      		}
	      	} catch (Exception e) {
	      		e.printStackTrace();
	      	}finally{
				destroyFactory(detailFactory,factory);
			}
	  }
	
	/**
	 * 解保列表导出
	  * @param projectIds
	  * @param request
	  * @param response
	  * @author: baogang
	  * @date: 2016年9月7日 上午11:04:08
	 */
	@RequestMapping(value = "/cancleProjectExcelExportList")
	@ResponseBody
	public void cancleProjectExcelExportList(Integer pid,Integer orgId, HttpServletRequest request, HttpServletResponse response) {
	      	Map<String, Object> map = new HashMap<String, Object>();
	      	BaseClientFactory detailFactory = null;
	      	BaseClientFactory factory = null;
			BaseClientFactory feeFactory = null;
			List<OrgFeeSettleDetail> cancelProjectList = new ArrayList<OrgFeeSettleDetail>();
			try {
				feeFactory = getAomFactory(BusinessModule.MODUEL_ORG_FEE, "OrgFeeSettleService");
				OrgFeeSettleService.Client feeClient = (OrgFeeSettleService.Client) feeFactory.getClient();
				detailFactory = getAomFactory(BusinessModule.MODUEL_ORG_FEE, "OrgFeeSettleDetailService");
				OrgFeeSettleDetailService.Client detailClient = (OrgFeeSettleDetailService.Client) detailFactory.getClient();
				
				OrgFeeSettle orgFee = feeClient.getById(pid);
				String settleDate = orgFee.getSettleDate();
				Date date = DateUtils.stringToDate(settleDate, "yyyy-MM");
				String settleDateStart = DateUtils.dateToString(DateUtils.getFirstDayOfMonth(date));
				String settleDateEnd = DateUtils.dateToString(DateUtils.getLastDayOfMonth(date));
				
				OrgFeeSettleDTO query = new OrgFeeSettleDTO();
				query.setSettleDate(settleDateStart);
				query.setSettleDateEnd(settleDateEnd);
				query.setOrgId(orgId);
				//解保列表
				cancelProjectList = detailClient.getCancelProjectList(query);
				if(cancelProjectList != null && cancelProjectList.size()>0){
					for(OrgFeeSettleDetail detail : cancelProjectList){
						detail.setLoanDate(DateUtils.dateFormatByPattern(detail.getLoanDate(), "yyyy-MM-dd"));
						detail.setPaymentDate(DateUtils.dateFormatByPattern(detail.getPaymentDate(), "yyyy-MM-dd"));
					}
				}
	      		map.put("bean", cancelProjectList);
	      		factory = getFactory(BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
	      		TemplateFileService.Client templateFileService = (TemplateFileService.Client) factory.getClient();
	      		TemplateFile template = templateFileService.getTemplateFile("CANCLEPROJECTLIST");

	      		if (template!=null&&!template.getFileUrl().equals("")) {
	      			String path = CommonUtil.getRootPath() + template.getFileUrl();
	      			ExcelExport.outToExcel(map, path, "解保列表导出" + "." + template.getFileUrl().substring(template.getFileUrl().lastIndexOf(".") + 1), response, request);
	      		}
	      	} catch (Exception e) {
	      		e.printStackTrace();
	      	}finally{
				destroyFactory(detailFactory,factory,feeFactory);
			}
	  }
	
	
	/**
	 * 生成历史数据
	 * @param number
	 * @param request
	 * @param response
	 */
	@RequestMapping("/createHistoryFeeSettle")
	public void createHistoryFeeSettle(HttpServletRequest request, HttpServletResponse response){
		Json j = super.getSuccessObj();
		BaseClientFactory feeFactory = null;
		try {
			feeFactory = getAomFactory(BusinessModule.MODUEL_ORG_FEE, "OrgFeeSettleService");
			OrgFeeSettleService.Client feeClient = (OrgFeeSettleService.Client) feeFactory.getClient();
			int result = 0;
			int[] nums = {4,3,2,1,0};
			for(int num : nums){
				result = feeClient.createLastMonthFeeSettle(num);
			}
			
			if(result >0){
					j.getHeader().put("success", true);
					j.getHeader().put("msg", "结算成功");
				}else{
					j.getHeader().put("success", false);
					j.getHeader().put("msg", "结算出错，请联系系统管理员！");
				}
		} catch (Exception e) {
			logger.error("手动进行结算:" + ExceptionUtil.getExceptionMessage(e));
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "结算失败");
		}finally{
			destroyFactory(feeFactory);
		}
		// 输出
		outputJson(j, response);
	}
}
