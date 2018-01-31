/**
 * @Title: SecondCreditsController.java
 * @Package com.xlkfinance.bms.web.controller.credits
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: lvmingjian
 * @date: 2015年3月17日 上午10:34:30
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.credits;

import java.util.ArrayList;
import java.util.List;

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
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.SysLogTypeConstant;
import com.xlkfinance.bms.rpc.beforeloan.CreditLimitRecord;
import com.xlkfinance.bms.rpc.beforeloan.CreditsDTO;
import com.xlkfinance.bms.rpc.beforeloan.CreditsService;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;

@Controller
@RequestMapping("/creditsCustomerInfoController")
public class CreditsCustomerInfoController extends BaseController {
	private Logger logger = LoggerFactory
			.getLogger(CreditsCustomerInfoController.class);
	private static final String credit = "credits/";

	/**
	 * 
	  * @Description: 额度申请跳转页面
	  * @param model
	  * @param projectId
	  * @throws Exception
	  * @author: Rain.Lv
	  * @date: 2015年3月25日 下午3:47:11
	 */
	@RequestMapping(value = "/toAddCredit")
	public String addClient(ModelMap model,int projectId) throws Exception {
		model.put("proId", projectId);// 项目Id
		return credit + "credits_tab";
	}


	 
	/**
	 * 
	  * @Description: 获取授信项目信息
	  * @param creditsDTO
	  * @param response
	  * @author: Rain.Lv
	  * @date: 2015年3月23日 下午4:29:09
	 */
	@ResponseBody
	@RequestMapping(value = "/getAllCreditsById")
	public void getAllCreditsById(CreditsDTO creditsDTO,
			HttpServletResponse response) {
		List<CreditsDTO> list = new ArrayList<CreditsDTO>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN,
					"CreditsService");
			CreditsService.Client client = (CreditsService.Client) clientFactory
					.getClient();
			list = client.getCreditsInfo(creditsDTO);
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("加载授信项目信息:" + e.getMessage());
			}
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
		outputJson(list, response);
	}

	/**
	 * 
	  * @Description: 保存冻结、解冻信息
	  * @param creditLimitRecord
	  * @param response
	  * @throws Exception
	  * @author: Rain.Lv
	  * @date: 2015年3月25日 下午3:46:56
	 */
	@RequestMapping(value = "saveFreeze")
	public void saveFreeze(CreditLimitRecord creditLimitRecord,
			HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "CreditsService");
		try {
			CreditsService.Client client = (CreditsService.Client) clientFactory.getClient();
			int pid = client.saveFreeze(creditLimitRecord);
			Object[] row = new Object[2];
			row[0]=pid;
			row[1]="success";
			outputJson(row, response);
		} catch (Exception e) {
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
	}
	
	 /**
	  * 
	   * @Description: 解冻或冻结页面
	   * @param pid
	   * @param projectId
	   * @param parma
	   * @param model
	   * @return
	   * @throws Exception
	   * @author: Rain.Lv
	   * @date: 2015年4月1日 下午2:27:37
	  */
	@RequestMapping(value = "/toEditfreeze")
	public String editFreeze(@RequestParam(value = "pid", required = false) Integer pid,
			@RequestParam(value = "projectId", required = false) Integer projectId,
			@RequestParam(value = "parma", required = false) String parma,
			ModelMap model)throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "CreditsService");
		try {
			CreditsService.Client client = (CreditsService.Client) clientFactory
					.getClient();
			
			if(pid!=null){
				CreditLimitRecord creditLimitRecord=client.getCreditLimitRecord(pid);
				if(creditLimitRecord!=null){
					creditLimitRecord.setRequestAmt(Math.abs(creditLimitRecord.getRequestAmt()));
					creditLimitRecord.setAmt(Math.abs(creditLimitRecord.getAmt()));
				}
				model.put("creditLimitRecord", creditLimitRecord);
				model.put("creditId", creditLimitRecord.getCreditId());// 授信Id
				model.put("creditUseType", creditLimitRecord.getCreditUseType());// 状态
				model.put("projectId", projectId);// 项目Id
				model.put("fail", 1);// 显示审批金额开关
	 		}else{
				model.put("fail", 2);// 
				if(null!=parma && !parma.equals("")){
					String [] args = parma.split(",");
					model.put("creditId", args[0]);// 授信Id
					model.put("creditUseType", args[1]);// 状态
					model.put("creditLimit", args[2]);// 可用额度
					model.put("freezeLimit", args[3]);// 冻结额度
				}
				model.put("projectId", projectId);// 项目Id
			}
		} catch (Exception e) {
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
		return credit + "edit_freeze";
	}
	
	/**
	  * @Description: 额度申请生成新的项目
	  * @param project
	  * @param response
	  * @author: Rain.Lv
	  * @date: 2015年4月1日 下午2:23:18
	 */
	@RequestMapping(value = "addNewProject", method = RequestMethod.POST)
	public void addProject(Project project,int tempcusType, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			// 设置当前项目经理为当前登录的用户
			project.setPmUserId(getShiroUser().getPid());
			project.setCusType(tempcusType);
			String str = client.addNewProject(project);
			if (null != str && !"".equals(str)) {
				// 分别获取项目ID和授信ID
				String[] arr = str.split(",");
				j.getHeader().put("success", true);
				j.getHeader().put("msg", arr[0]);
				j.getHeader().put("pid", arr[1]);
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_ADD, "额度提款申请,项目编号:" + arr[0]);
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "新增失败,请重新操作!");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("额度提款申请:" + e.getMessage());
			}
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "新增失败,请重新操作!");
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
		outputJson(j, response);
	}
}
