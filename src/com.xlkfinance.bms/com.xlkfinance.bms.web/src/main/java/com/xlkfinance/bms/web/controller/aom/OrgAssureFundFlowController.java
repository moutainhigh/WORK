/**
 * @Title: OrgAssureFundFlowController.java
 * @Package com.xlkfinance.bms.web.controller.aom
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: baogang
 * @date: 2016年7月13日 下午3:59:04
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
import com.qfang.xk.aom.rpc.org.OrgAssetsCooperationInfo;
import com.qfang.xk.aom.rpc.org.OrgAssureFundFlowDTO;
import com.qfang.xk.aom.rpc.org.OrgAssureFundFlowInfo;
import com.qfang.xk.aom.rpc.org.OrgAssureFundFlowInfoService;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyInf;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyService;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.OrgSysLogTypeConstant;
import com.xlkfinance.bms.common.constant.SysLogTypeConstant;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;
/**
 * 保证金变更流水Cntroller
  * @ClassName: OrgAssureFundFlowController
  * @author: baogang
  * @date: 2016年7月13日 下午3:59:20
 */
@Controller
@RequestMapping("/orgAssureFundFlowController")
public class OrgAssureFundFlowController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(OrgAssureFundFlowController.class);
	
	/**
	 * 跳转保证金变更流水列表
	  * @return
	  * @author: baogang
	  * @date: 2016年7月13日 下午4:02:29
	 */
	@RequestMapping(value="index")
	public String index(){
		return "/aom/org/fundFlow_index";
	}
	
	/**
	 * 分页查询保证金变更信息
	  * @param fundFlowDTO
	  * @param request
	  * @param response
	  * @author: baogang
	  * @date: 2016年7月13日 下午4:08:12
	 */
	@RequestMapping(value = "getAllFundFlow", method = RequestMethod.POST)
	public void getAllFundFlow(OrgAssureFundFlowDTO fundFlowDTO,HttpServletRequest request, HttpServletResponse response){
		// 需要返回的Map集合
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgAssureFundFlowInfoService");
			OrgAssureFundFlowInfoService.Client client = (OrgAssureFundFlowInfoService.Client) clientFactory.getClient();
			// 创建集合
			fundFlowDTO.setUserIds(getUserIds(request));
			List<OrgAssureFundFlowDTO> list = client.getOrgAssureFundByPage(fundFlowDTO);
			int count = client.getOrgAssureFundCount(fundFlowDTO);
			map.put("rows", list);
			map.put("total", count);
		} catch (Exception e) {
				logger.error("分页查询保证金变更信息:" + ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(map, response);
	}
	
	/**
	 * 跳转编辑页面
	  * @param cooperationId
	  * @param pid
	  * @param editType
	  * @param model
	  * @return
	  * @author: baogang
	  * @date: 2016年7月13日 下午4:46:58
	 */
	@RequestMapping(value = "editFundFlowInfo")
	public String editFundFlowInfo(@RequestParam(value = "cooperationId", required = false) Integer cooperationId,@RequestParam(value = "pid", required = false) Integer pid,@RequestParam(value = "editType", required = false)String editType, ModelMap model){
		OrgCooperatCompanyApplyInf orgCooperateInfo = new OrgCooperatCompanyApplyInf();
		OrgAssetsCooperationInfo orgAssetsInfo = new OrgAssetsCooperationInfo();
		OrgAssureFundFlowInfo orgAssureFundFlowInfo = new OrgAssureFundFlowInfo();
		BaseClientFactory clientFactory = null;
		BaseClientFactory orgFactory = null;
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgCooperatCompanyApplyService");
			orgFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgAssureFundFlowInfoService");
			OrgCooperatCompanyApplyService.Client client = (OrgCooperatCompanyApplyService.Client) clientFactory.getClient();
			OrgAssureFundFlowInfoService.Client fundClient = (OrgAssureFundFlowInfoService.Client) orgFactory.getClient();
			if(cooperationId != null && cooperationId>0){
				//申请信息
				orgCooperateInfo = client.getByPid(cooperationId);
				orgAssetsInfo = orgCooperateInfo.getOrgAssetsInfo();
			}
			if(pid != null && pid>0){
				//保证金变更信息
				orgAssureFundFlowInfo = fundClient.getById(pid);
			}
		} catch (Exception e) {
			logger.error("根据ID查询机构合作申请信息:" + ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(clientFactory,orgFactory);
		}
		model.put("orgCooperateInfo", orgCooperateInfo);
		model.put("orgAssetsInfo", orgAssetsInfo);
		model.put("orgAssureFundFlowInfo", orgAssureFundFlowInfo);
		model.put("editType", editType);
		
		return "/aom/org/edit_fundFlow";
	}
	
	/**
	 * 保存保证金变更信息
	  * @param fundFlowInfo
	  * @param response
	  * @author: baogang
	  * @date: 2016年7月13日 下午5:38:51
	 */
	@RequestMapping(value="saveOrUpdateFundFlow")
	public void saveOrUpdateFundFlow(OrgAssureFundFlowInfo fundFlowInfo, HttpServletResponse response,HttpServletRequest req) {
		logger.info("保存保证金变更信息，参数：" + fundFlowInfo);
		Json j = super.getSuccessObj();
		
		BaseClientFactory orgFactory = null;
		try {
			orgFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgAssureFundFlowInfoService");
			OrgAssureFundFlowInfoService.Client fundClient = (OrgAssureFundFlowInfoService.Client) orgFactory.getClient();
			int pid = fundFlowInfo.getPid();
			int applyId = fundFlowInfo.getApplyId();
			OrgAssureFundFlowInfo query = new OrgAssureFundFlowInfo();
			query.setApplyId(applyId);
			query.setStatus(AomConstant.FUND_FLOW_STATUS_1);
			List<OrgAssureFundFlowInfo> result = fundClient.getAll(query);
			if(result != null && result .size()>0){
				OrgAssureFundFlowInfo flowInfo = result.get(0);
				if(flowInfo.getPid()>0 && flowInfo.getPid() != pid){
					fillReturnJson(response, false, "申请保证金变更已经在审核中，不能重复申请");
			        return;
				}
			}
			
			int status = fundFlowInfo.getStatus();
			String currentDateTime = DateUtils.getCurrentDateTime();
			//登录人员ID
			int userId = getSysUser().getPid();
			if(pid>0 || status>AomConstant.FUND_FLOW_STATUS_1){
				fundFlowInfo.setAuditId(userId);
				fundFlowInfo.setAuditDate(currentDateTime);
			}
			if(pid == 0){
				fundFlowInfo.setCreatedDateTime(currentDateTime);
				fundFlowInfo.setOperator(userId);
			}
			pid = fundClient.updateOrgFundFlow(fundFlowInfo);
			
			recordAomLog(OrgSysLogTypeConstant.LOG_TYPE_ADD, "保存保证金变更信息：OrgAssureFundFlowController.saveOrUpdateFundFlow 参数："+fundFlowInfo, req);
			//ERP 后台日志
			recordLog(BusinessModule.MODUEL_ORG, SysLogTypeConstant.LOG_TYPE_UPDATE, "保存保证金变更信息"+fundFlowInfo,fundFlowInfo.getPid());
			j.getHeader().put("pid", pid);
			j.getHeader().put("success", true);
			j.getHeader().put("msg", "保存成功");
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.error("保存保证金变更信息:" + ExceptionUtil.getExceptionMessage(e));
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "保存失败,请重新操作!");
		}finally{
			destroyFactory(orgFactory);
		}
		// 输出
		outputJson(j, response);
	}
}
