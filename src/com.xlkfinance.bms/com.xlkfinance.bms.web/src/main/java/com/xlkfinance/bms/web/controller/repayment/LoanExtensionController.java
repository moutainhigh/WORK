/**
 * @Title: LoanExtensionController.java
 * @Package com.xlkfinance.bms.web.controller.repayment
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Chong.Zeng
 * @date: 2014年12月11日 上午9:54:39
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.repayment;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.SysLogTypeConstant;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.beforeloan.RepaymentLoanInfo;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.contract.ContractService;
import com.xlkfinance.bms.rpc.repayment.LoanExtensionBaseDTO;
import com.xlkfinance.bms.rpc.repayment.LoanExtensionBaseView;
import com.xlkfinance.bms.rpc.repayment.LoanExtensionDTO;
import com.xlkfinance.bms.rpc.repayment.LoanExtensionFileDTO;
import com.xlkfinance.bms.rpc.repayment.LoanExtensionFileView;
import com.xlkfinance.bms.rpc.repayment.LoanExtensionService;
import com.xlkfinance.bms.rpc.repayment.LoanExtensionView;
import com.xlkfinance.bms.rpc.repayment.ProjectExtensionView;
import com.xlkfinance.bms.rpc.repayment.RepayNoReconciliationDTO;
import com.xlkfinance.bms.rpc.repayment.RepayNoReconciliationView;
import com.xlkfinance.bms.rpc.repayment.RepaymentPlanBaseDTO;
import com.xlkfinance.bms.rpc.repayment.RepaymentService;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.rpc.system.BizFileService;
import com.xlkfinance.bms.rpc.system.TemplateFile;
import com.xlkfinance.bms.rpc.system.TemplateFileService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;
import com.xlkfinance.bms.web.shiro.ShiroUser;
import com.xlkfinance.bms.web.util.CommonUtil;
import com.xlkfinance.bms.web.util.ExcelExport;
import com.xlkfinance.bms.web.util.FileUtil;
import com.xlkfinance.bms.web.util.ParseDocAndDocxUtil;

@Controller
@RequestMapping("/loanExtensionController")
public class LoanExtensionController extends BaseController {
	private Logger logger = LoggerFactory
			.getLogger(LoanExtensionController.class);

	// 展期页面跳转
	@RequestMapping("/loanExtension")
	public String loanExtension() {
		return "repayment/list_loanExtension";
	}
	
	// 展期列表跳转
	@RequestMapping("loanExtentionApplyList")
	public String loanExtentionApplyList() {
		return "repayment/list_loanExtensionList";
	}
	
	/**
	 * 
	  * @Description: 合同页面跳转
	  * @param contractCatelog
	  * @param projectId
	  * @param model
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月27日 下午5:41:14
	 */
	@RequestMapping(value = "navigationContract")
	public String navigationContract(String contractCatelog, Integer projectId, ModelMap model) {
		model.put("contractCatelog", contractCatelog);
		model.put("projectId", projectId);
		return "repayment/extension_add_contract";
	}
	
	/**
	 * 
	 * @Description: 借据及还款计划表
	 * @param contractCatelog
	 * @param model
	 * @return 还款计划表页面
	 * @author: zhangyu
	 * @date: 2015年2月10日 上午9:36:44
	 */
	@RequestMapping(value = "receiptRepaymentList")
	public String receiptRepaymentList(String contractCatelog, Integer loanId, Integer projectId, ModelMap model) {
		try {
			setRepaymentListModel(model, loanId, projectId);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询还款计划表贷款信息:" + e.getMessage());
			}
		} 
		model.put("contractCatelog", contractCatelog);
		return "repayment/loanExtension/receipt_repayment_list";
	}
	
	/**
	 * 
	  * @Description: 导出还款计划表
	  * @param loanId
	  * @param projectId
	  * @param exprotType
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年5月21日 上午11:42:39
	 */
	@RequestMapping(value = "exportRepayment")
	public void exportRepayment(Integer loanId,Integer projectId,String templateName,String planRepayDts ,HttpServletRequest request, HttpServletResponse response) {
		BaseClientFactory clientFactory = null;
		BaseClientFactory temFactory = null;
		BaseClientFactory contractFactory = null;
		BaseClientFactory loanFactory = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (loanId != null && loanId > 0) {
				
				clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
				temFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
				contractFactory = getFactory(BusinessModule.MODUEL_CONTRACT, "ContractService");
				loanFactory = getFactory( BusinessModule.MODUEL_REPAYMENT, "LoanExtensionService");
				
				ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
				TemplateFileService.Client temClient = (TemplateFileService.Client) temFactory.getClient();
				ContractService.Client contractClient = (ContractService.Client) contractFactory.getClient();
				LoanExtensionService.Client loanClient = (LoanExtensionService.Client) loanFactory.getClient();
				
				// step.1 查询还款信息
				RepaymentLoanInfo repayment = client.getRepaymentLoanInfo(loanId);
				if(null!=repayment){
					// 利率除以100 
					repayment.setLoanInterestStr(repayment.getLoanInterest()+"%");
					repayment.setLoanMgrStr(repayment.getLoanMgr()+"%");
					repayment.setLoanOtherFeeStr(repayment.getLoanOtherFee()+"%");
					repayment.setFeesProportionStr(repayment.getFeesProportion()+"%");
					
					// 判断项目是否是展期,展期项目需要单独查询借据合同编号
					Project project = client.getProjectById(projectId);
					if(null!=project){
						if(project.getProjectType() == 4){
							String contractNum = contractClient.getExtensionContractNum(projectId);
							repayment.setContractNo(contractNum);
							
							// 设置展期项目的原借据项目编号
							ProjectExtensionView view = loanClient.getByProjectId(projectId);
							if(null!= view && view.getExtensionProjectId() > 0){
								//查询老项目的放款日期
								int oldLoanId = client.getLoanIdByProjectId(view.getExtensionProjectId());
								RepaymentLoanInfo oldRepayment = client.getRepaymentLoanInfo(oldLoanId);
								if(null != oldRepayment){
									// 查询被展期项目是否是展期项目,是展期项目需要单独查询借据项目编号
									Project oldProject = client.getProjectById(view.getExtensionProjectId());
									if( null != oldProject ){
										if(oldProject.getProjectType() ==4){
											String oldContractNum = contractClient.getExtensionContractNum(view.getExtensionProjectId());
											oldRepayment.setContractNo(oldContractNum);
										}
									}
									map.put("oldRepayment", oldRepayment);
								}
							}
						}
					}
				}
				map.put("repay", repayment);
				
				// step.2 设置还款计划表数据到导出map中
				setRepayExportInfo(loanId, planRepayDts, map);
				
				// step.3 判断是否是展期申请,并且设置展期导出需要的信息
				//setExtensionExportInfo(projectId, templateName, map, client);
				
				// step.4 获取导出模版,导出excel 
				TemplateFile template = temClient.getTemplateFile(templateName);
				if (!template.getFileUrl().equals("")) {
					String filePath = CommonUtil.getRootPath() + template.getFileUrl();
					ExcelExport.outToExcel(map, filePath, "还款计划表.xlsx", response, request);
				}
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("导出还款计划表信息:" + e.getMessage());
			}
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
			if (temFactory != null) {
				try {
					temFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
			if (contractFactory != null) {
				try {
					contractFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
			if (loanFactory != null) {
				try {
					loanFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	  * @Description: 设置还款计划表数据到导出map中
	  * @param loanId
	  * @param planRepayDts
	  * @param map
	  * @param repayClient
	  * @throws ThriftServiceException
	  * @throws TException
	  * @author: Zhangyu.Hoo
	 * @throws ThriftException 
	  * @date: 2015年5月21日 下午3:51:23
	 */
	private void setRepayExportInfo(Integer loanId, String planRepayDts, Map<String, Object> map) throws ThriftServiceException, TException, ThriftException {
		BaseClientFactory repayFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
		RepaymentService.Client repayClient = (RepaymentService.Client) repayFactory.getClient();
		// 查询还款计划表信息
		RepaymentPlanBaseDTO dto = new RepaymentPlanBaseDTO();
		dto.setLoanInfoId(loanId);
		dto.setFreezeStatus(0);
		List<RepaymentPlanBaseDTO> list = repayClient.selectRepaymentPlanBaseDTO(dto);
		RepaymentPlanBaseDTO lastDto = null;
		if(null!=list && list.size() > 0){
			lastDto = list.get(list.size()-1);
			// 删除最后一行合计数据 
			list.remove(list.size()-1);
			
			// 将页面传过来的还款日期替换到查询出来的还款计划表中
			if(null!=planRepayDts && !"".equals(planRepayDts)){
				String [] planRepayDt = planRepayDts.split(",");
				for(int i = 0 ;i<list.size() ; i++){
					list.get(i).setPlanRepayDt(planRepayDt[i]);
				}
			}
		}else{
			list = new ArrayList<RepaymentPlanBaseDTO>();
		}
		
		map.put("bean", list);
		map.put("total", lastDto);
	}

	/**
	 * 
	  * @Description: 判断是否是展期申请,并且设置展期导出需要的信息
	  * @param projectId
	  * @param templateName
	  * @param map
	  * @param client
	  * @throws ThriftException
	  * @throws ThriftServiceException
	  * @throws TException
	  * @author: Zhangyu.Hoo
	  * @date: 2015年5月21日 下午3:48:53
	 */
	private void setExtensionExportInfo(Integer projectId, String templateName,
			Map<String, Object> map, ProjectService.Client client)
			throws ThriftException, ThriftServiceException, TException {
		BaseClientFactory contractFactory = null;
		BaseClientFactory loanFactory = null;
		try{
			if("REPAYMENT_EXTENSION".equals(templateName)){
				loanFactory = getFactory( BusinessModule.MODUEL_REPAYMENT, "LoanExtensionService");
				contractFactory = getFactory(BusinessModule.MODUEL_CONTRACT, "ContractService");
				LoanExtensionService.Client loanClient = (LoanExtensionService.Client) loanFactory.getClient();
				ProjectExtensionView view = loanClient.getByProjectId(projectId);
				ContractService.Client contractClient = (ContractService.Client) contractFactory.getClient();
				if(null!= view && view.getExtensionProjectId() > 0){
					//查询老项目的放款日期
					int oldLoanId = client.getLoanIdByProjectId(view.getExtensionProjectId());
					RepaymentLoanInfo repayment = client.getRepaymentLoanInfo(oldLoanId);
					if(null != repayment){
						// 查询被展期项目是否是展期项目,是展期项目需要单独查询借据项目编号
						Project project = client.getProjectById(view.getExtensionProjectId());
						if(null!=project){
							if(project.getProjectType() ==4){
								String contractNum = contractClient.getExtensionContractNum(view.getExtensionProjectId());
								repayment.setContractNo(contractNum);
							}
						}
						map.put("oldRepayment", repayment);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if (loanFactory != null) {
				try {
					loanFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
			if (contractFactory != null) {
				try {
					contractFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 
	  * @Description: 贷款展期申请
	  * @param proId 被展期的项目ID
	  * @param response
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月2日 下午7:20:04
	 */
	@RequestMapping("toLoanExtensionApply")
	public String toLoanExtensionApply(@RequestParam("projectId") Integer proId,@RequestParam("projectType") Integer projectType,ModelMap model){
		BaseClientFactory clientFactory = null;
		Project project = null;
		try {
			// 1 设置项目名称与编号
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			project = client.getLoanProjectByProjectId(proId);
			if(null!=project){
				model.put("projectName", project.getProjectName());
				model.put("projectNum", project.getProjectNumber());
				model.put("loanId", project.getLoanId());
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("贷款展期申请:" + e.getMessage());
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
		model.put("projectId", proId);
		model.put("projectType", projectType);
		// 新增 展期项目ID默认为 -1
		model.put("newProjectId", -1);
		return "repayment/extension_tab";
	}
	
	/**
	 * 
	  * @Description: 编辑展期申请
	  * @param projectId	被展期的项目ID
	  * @param newProjectId	展期项目ID
	  * @param model
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月10日 上午11:23:47
	 */
	@RequestMapping("toEditLoanExtensionApply")
	public String toEditLoanExtensionApply(@RequestParam("projectId") Integer projectId,@RequestParam("newProjectId") Integer newProjectId,ModelMap model){
		BaseClientFactory clientFactory = null;
		Project project = null;
		try {
			// 1 设置项目名称与编号
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			project = client.getLoanProjectByProjectId(projectId);
			if(null!=project){
				model.put("projectName", project.getProjectName());
				model.put("projectNum", project.getProjectNumber());
				model.put("loanId", project.getLoanId());
				RepaymentLoanInfo repayment = client.getRepaymentLoanInfo(project.getLoanId());
				if(null !=repayment){
					model.put("otherCostName", repayment.getOtherCostName());
				}
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("贷款展期申请:" + e.getMessage());
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
		model.put("projectId", projectId);
		model.put("newProjectId", newProjectId);
		return "repayment/extension_tab";
	}
	
	
	
	
	/**
	 * 
	  * @Description: 尽职调查页面
	  * @param proId
	  * @param model
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月7日 上午10:15:36
	 */
	@RequestMapping("toLoanExtensionInvestigation")
	public String toLoanExtensionInvestigation(){
		return "repayment/extension_add_investigation";
	}
	
	/**
	 * 
	  * @Description: 展期资料上传
	  * @return
	  * @author: xuweihao
	  * @date: 2015年4月7日 上午10:16:49
	 */
	@RequestMapping(value = "navigationDatum")
	public String navigationDatum() {
		return "repayment/extension_add_datum";
	}
	
	
	/**
	 * 
	  * @Description: 查询展期申请列表
	  * @param request
	  * @param response
	  * @param page
	  * @param rows
	  * @author: Chong.Zeng
	  * @date: 2015年1月26日 下午5:58:30
	 */
	@RequestMapping("/extentionList")
	public void extentionList(LoanExtensionDTO loan,HttpServletResponse response){
		List<LoanExtensionView> list = null;
		BaseClientFactory clientFactory = null;
		Map<String, Object> map = null;
		try {
			clientFactory = getFactory( BusinessModule.MODUEL_REPAYMENT, "LoanExtensionService");
			LoanExtensionService.Client client = (LoanExtensionService.Client) clientFactory.getClient();
			map = new HashMap<String, Object>();
			int userId = getShiroUser().getPid();
			loan.setPmUserId(userId);
			list = client.selectLoanExtensionList(loan);
			int total = client.selectLoanExtensionListTotal(loan);
			map.put("total", total);
			map.put("rows", list);
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
		// 输出
		outputJson(map, response);
	}
	
	/**
	 * 
	  * @Description: 查询展期申请列表
	  * @param request
	  * @param response
	  * @param page
	  * @param rows
	  * @author: Chong.Zeng
	  * @date: 2015年1月26日 下午5:58:30
	 */
	@RequestMapping("/extentionRequestList")
	public void extentionRequestList(LoanExtensionBaseDTO loan,HttpServletResponse response){
		List<LoanExtensionBaseView> list = null;
		Map<String, Object> map = null;
		BaseClientFactory clientFactory = null;
		try {
			map = new HashMap<String, Object>();
			int userId = getShiroUser().getPid();
			loan.setPmUserId(userId);
			clientFactory = getFactory( BusinessModule.MODUEL_REPAYMENT, "LoanExtensionService");
			LoanExtensionService.Client client = (LoanExtensionService.Client) clientFactory.getClient();
			list = client.selectLoanExtensionBaseList(loan);
			int total = client.selectLoanExtensionBaseListTotal(loan);
			map.put("total", total);
			map.put("rows", list);
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
		// 输出
		outputJson(map, response);
	}
	
	/**
	 * 
	  * @Description: 获取展期文件列表
	  * @param loan
	  * @param response
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月9日 下午1:40:20
	 */
	@RequestMapping("/getExtensionFileList")
	public void getExtensionFileList(int projectId,LoanExtensionFileDTO dto,HttpServletResponse response){
		BaseClientFactory clientFactory = null;
		Map<String, Object> map = null;
		try {
			map = new HashMap<String, Object>();
			clientFactory = getFactory( BusinessModule.MODUEL_REPAYMENT, "LoanExtensionService");
			LoanExtensionService.Client client = (LoanExtensionService.Client) clientFactory.getClient();
			ProjectExtensionView view = client.getByProjectId(projectId);
			dto.setExtensionId(view.getPid());
			List<LoanExtensionFileView> list = client.getExtensionFileList(dto);
			int total = client.getExtensionFileListTotal(dto);
			map.put("total", total);
			map.put("rows", list);
			outputJson(map, response);
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
	  * @Description: 展期申请生成新的项目
	  * @param project
	  * @param response
	  * @author: Rain.Lv
	  * @date: 2015年4月1日 下午2:23:18
	 */
	@RequestMapping(value = "addExtensionProject", method = RequestMethod.POST)
	public void addProject(Project project,int tempcusType, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			project.setCusType(tempcusType);
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			// 设置当前项目经理为当前登录的用户
			project.setPmUserId(getShiroUser().getPid());
			String str = client.addExtensionProject(project);
			if (null != str && !"".equals(str)) {
				// 分别获取项目ID和授信ID
				String[] arr = str.split(","); 
				j.getHeader().put("success", true);
				j.getHeader().put("msg", arr[0]);
				j.getHeader().put("pid", arr[1]);
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_REPAYMENT, SysLogTypeConstant.LOG_TYPE_ADD, "展期申请,项目编号:" + arr[0]);
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
				logger.debug("展期申请:" + e.getMessage());
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
	
	/**
	 * 
	  * @Description: 删除展期项目
	  * @param project
	  * @param response
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月10日 上午11:26:11
	 */
	@RequestMapping(value = "removeExtensionProject", method = RequestMethod.POST)
	public void removeExtensionProject(String pid,String projectId, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory extensionclientFactory = null;
		int count = 0 ;
		try {
			extensionclientFactory = getFactory( BusinessModule.MODUEL_REPAYMENT, "LoanExtensionService");
			LoanExtensionService.Client extensionClient = (LoanExtensionService.Client) extensionclientFactory.getClient();
			count = extensionClient.batchDelete(pid,projectId);
			if (count > 0 ) {
				// 分别获取项目ID和授信ID
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "删除成功!");
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_REPAYMENT, SysLogTypeConstant.LOG_TYPE_DELETE, "展期申请,项目ID:" +projectId);
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "删除失败,请重新操作!");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("展期申请:" + e.getMessage());
			}
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "删除失败,请重新操作!");
		} finally {
			if (extensionclientFactory != null) {
				try {
					extensionclientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		// 输出
		outputJson(j, response);
	}
	
	/**
	 * 
	  * @Description: 新增展期贷款详细信息
	  * @param project
	  * @param response
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月7日 下午7:17:46
	 */
	@RequestMapping(value = "addInformation", method = RequestMethod.POST)
	public void addInformation(@RequestParam("projectPid") Integer projectPid ,Project project, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		try {
			project.setPid(projectPid);
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			int rows = client.addExtensionInformation(project);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_REPAYMENT, SysLogTypeConstant.LOG_TYPE_ADD, "新增展期贷款详细信息,授信ID:" + rows);
				// 失败的话,做提示处理
				j.getHeader().put("success", true);
				j.getHeader().put("msg", rows);
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "新增失败,请重新操作!");
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("新增展期贷款详细信息:" + e.getMessage());
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
	
	/**
	 * 
	  * @Description:获取展期期数
	  * @param projectId
	  * @param response
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月8日 下午4:27:26
	 */
	@RequestMapping(value = "getExtensionNum", method = RequestMethod.POST)
	public void  getExtensionNum(RepayNoReconciliationDTO dto,HttpServletResponse response){
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory( BusinessModule.MODUEL_REPAYMENT, "LoanExtensionService");
			LoanExtensionService.Client client = (LoanExtensionService.Client) clientFactory.getClient();
			List<RepayNoReconciliationView> view = client.getRepayNoReconciliationList(dto);
			outputJson(view, response);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("获取展期期数:" + e.getMessage());
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
	}
	
	/**
	 * 
	  * @Description: 获取展期金额
	  * @param dto
	  * @param response
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月8日 下午5:24:42
	 */
	@RequestMapping(value = "getProjectExtension", method = RequestMethod.POST)
	public void  getProjectExtension(int projectId, HttpServletResponse response){
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory( BusinessModule.MODUEL_REPAYMENT, "LoanExtensionService");
			LoanExtensionService.Client client = (LoanExtensionService.Client) clientFactory.getClient();
			ProjectExtensionView view = client.getByProjectId(projectId);
			outputJson(view, response);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("获取展期信息:" + e.getMessage());
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
	}
	
	/**
	 * 
	  * @Description: 根据被展期项目ID判断是否存在流程中的数据
	  * @param projectId
	  * @param response
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月27日 上午10:30:22
	 */
	@RequestMapping(value = "isRequestingByExtensionProjectId", method = RequestMethod.POST)
	public void  isRequestingByExtensionProjectId(int extensionProjectId, HttpServletResponse response){
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory( BusinessModule.MODUEL_REPAYMENT, "LoanExtensionService");
			LoanExtensionService.Client client = (LoanExtensionService.Client) clientFactory.getClient();
			List<ProjectExtensionView> list = client.getByExtensionProjectId(extensionProjectId);
			if(null!=list && list.size()>0 && null!= list.get(0)){
				outputJson(true, response);
			}else{
				outputJson(false, response);
			}
			
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("获取展期信息:" + e.getMessage());
			}
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					if (logger.isDebugEnabled()) {
						logger.debug("获取展期信息:" + e.getMessage());
					}
				}
			}
		}
	}
	
	/**
	 * 
	  * @Description: 保存上传文件
	  * @param request
	  * @param response
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月9日 下午4:02:27
	 */
	@RequestMapping(value = "saveFile")
	@ResponseBody
	public void saveFile(HttpServletRequest request, HttpServletResponse response) {
		int count = 0;
		int fileSize = 0;
		String fileName = null;
		String fileType = null;
		BaseClientFactory loan = null;
		BaseClientFactory bizFileClientFactory = null;
		// 展期表dto
		LoanExtensionFileDTO loanFileDTO = new LoanExtensionFileDTO();
		// 文件信息BIZ_FILE
		BizFile bizFile = new BizFile();
		Map<String, Object> tojson = new HashMap<String, Object>();
		try {
			loan = getFactory(BusinessModule.MODUEL_REPAYMENT, "LoanExtensionService");
			bizFileClientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "BizFileService");
			
			LoanExtensionService.Client loanClient = (LoanExtensionService.Client) loan.getClient();
			BizFileService.Client bizFileClient = (BizFileService.Client) bizFileClientFactory.getClient();
			
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				Map<String, Object> map = new HashMap<String, Object>();
				// 资料上传得到的信息集合
				map = FileUtil.processFileUpload(request, response, BusinessModule.MODUEL_REPAYMENT, getUploadFilePath(), getFileSize(), getFileDateDirectory(),getUploadFileType());
				if ((Boolean) map.get("flag") == false) {
					logger.error("上传出错");
					tojson.put("uploadStatus", "文件上传失败or不支持该文件格式上传!");
				} else {
					// 拿到当前用户
					ShiroUser shiroUser = getShiroUser();
					int userId = shiroUser.getPid();
					@SuppressWarnings("rawtypes")
					List items = (List) map.get("items");
					for (int j = 0; j < items.size(); j++) {
						FileItem item = (FileItem) items.get(j);
						// 获取其他信息
						if (item.isFormField()) {
							if (item.getFieldName().equals("projectId")) {
								int projectId = Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item));
								// 根据项目ID 查询展期表信息
								ProjectExtensionView view = loanClient.getByProjectId(projectId);
								loanFileDTO.setExtensionId(view.getPid());
							} else if (item.getFieldName().equals("fileDesc")) {
								loanFileDTO.setFileDesc(ParseDocAndDocxUtil.getStringCode(item));
							}else if (item.getFieldName().equals("fileCategory")) {
								loanFileDTO.setFileCategory(ParseDocAndDocxUtil.getStringCode(item));
							}
						} else {
							if ("file2".equals(item.getFieldName())) {
								// 获取文件上传的信息
								bizFile.setUploadUserId(userId);
								fileSize = (int) item.getSize();
								bizFile.setFileSize(fileSize);
								String fileFullName = item.getName().toLowerCase();
								int dotLocation = fileFullName.lastIndexOf(".");
								fileName = fileFullName.substring(0, dotLocation);
								fileType = fileFullName.substring(dotLocation).substring(1);
								bizFile.setFileType(fileType);
								bizFile.setFileName(fileName);
							}
						}
					}
					String uploadDttm = DateUtil.format(new Date());
					bizFile.setUploadDttm(uploadDttm);
					String fileUrl = String.valueOf(map.get("path"));
					bizFile.setFileUrl(fileUrl);
					int uploadUserId = getShiroUser().getPid();
					bizFile.setUploadUserId(uploadUserId);
					bizFile.setStatus(1);
					if (fileSize == 0) {
						response.getWriter().write("fileSizeIsZero");
					}
					if (StringUtils.isEmpty(fileName) || StringUtils.isEmpty(fileType)) {
						response.getWriter().write("fileIsEmpty");
					}
					// 保存资料表信息
					count = loanClient.saveExtensionFile(loanFileDTO,bizFile);
					if (count == 0) {
						tojson.put("uploadStatus", "保存资料数据失败");
					} else {
						tojson.put("uploadStatus", "保存资料数据成功");
					}
				}
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("上传资料列表出错" + e.getMessage());
			}
			e.printStackTrace();
		} finally {
			if (loan != null) {
				try {
					loan.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
			if (bizFileClientFactory != null) {
				try {
					bizFileClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}

		}
		outputJson(tojson, response);
	}
	
	/**
	 * 
	  * @Description: 修改展期资料
	  * @param request
	  * @param response
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月9日 下午6:43:54
	 */
	@RequestMapping(value = "updateFile")
	@ResponseBody
	public void updateFile(LoanExtensionFileDTO dto, HttpServletRequest request, HttpServletResponse response) {
		BaseClientFactory loan = null;
		Map<String, Object> tojson = new HashMap<String, Object>();
		int count = 0;
		try {
			loan = getFactory(BusinessModule.MODUEL_REPAYMENT, "LoanExtensionService");
			
			LoanExtensionService.Client loanClient = (LoanExtensionService.Client) loan.getClient();
			
			// 更新资料表信息
			count = loanClient.updateExtensionFile(dto);
			
			if (count == 0) {
				tojson.put("uploadStatus", "编辑资料数据失败");
			} else {
				tojson.put("uploadStatus", "编辑资料数据成功");
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("编辑资料列表出错" + e.getMessage());
			}
			e.printStackTrace();
		} finally {
			if (loan != null) {
				try {
					loan.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(tojson, response);
	}
	
	
	/**
	 * 
	  * @Description: 重新上传
	  * @param request
	  * @param response
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月9日 下午6:53:00
	 */
	@RequestMapping(value = "modifyFile")
	@ResponseBody
	public void modfiyFile(HttpServletRequest request, HttpServletResponse response) {
		int count = 0;
		int fileSize = 0;
		String fileName = null;
		String fileType = null;
		BaseClientFactory bizFileClientFactory = null;
		// 文件信息BIZ_FILE
		BizFile bizFile = new BizFile();
		Map<String, Object> tojson = new HashMap<String, Object>();
		try {
			bizFileClientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "BizFileService");
			
			BizFileService.Client bizFileClient = (BizFileService.Client) bizFileClientFactory.getClient();
			
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				Map<String, Object> map = new HashMap<String, Object>();
				// 资料上传得到的信息集合
				map = FileUtil.processFileUpload(request, response, BusinessModule.MODUEL_REPAYMENT, getUploadFilePath(), getFileSize(), getFileDateDirectory(),getUploadFileType());
				if ((Boolean) map.get("flag") == false) {
					logger.error("上传出错");
					tojson.put("uploadStatus", "文件上传失败or文件格式不合法");
				} else {
					// 拿到当前用户
					ShiroUser shiroUser = getShiroUser();
					int userId = shiroUser.getPid();
					@SuppressWarnings("rawtypes")
					List items = (List) map.get("items");
					for (int j = 0; j < items.size(); j++) {
						FileItem item = (FileItem) items.get(j);
						if (item.isFormField()) {
							if (item.getFieldName().equals("fileId")) {
								bizFile.setPid(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
							}
						} 
						if ("file2".equals(item.getFieldName())) {
							// 获取文件上传的信息
							bizFile.setUploadUserId(userId);
							fileSize = (int) item.getSize();
							bizFile.setFileSize(fileSize);
							String fileFullName = item.getName().toLowerCase();
							int dotLocation = fileFullName.lastIndexOf(".");
							fileName = fileFullName.substring(0, dotLocation);
							fileType = fileFullName.substring(dotLocation).substring(1);
							bizFile.setFileType(fileType);
							bizFile.setFileName(fileName);
						}
					}
					String uploadDttm = DateUtil.format(new Date());
					bizFile.setUploadDttm(uploadDttm);
					String fileUrl = String.valueOf(map.get("path"));
					bizFile.setFileUrl(fileUrl);
					int uploadUserId = getShiroUser().getPid();
					bizFile.setUploadUserId(uploadUserId);
					bizFile.setStatus(1);
					if (fileSize == 0) {
						response.getWriter().write("fileSizeIsZero");
					}
					if (StringUtils.isEmpty(fileName) || StringUtils.isEmpty(fileType)) {
						response.getWriter().write("fileIsEmpty");
					}
					// 保存文件信息
					count = bizFileClient.updateBizFile(bizFile);
					if (count == 0) {
						tojson.put("uploadStatus", "保存资料数据失败");
					} else {
						tojson.put("uploadStatus", "保存资料数据成功");
					}
				}
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("上传资料列表出错" + e.getMessage());
			}
			e.printStackTrace();
		} finally {
			if (bizFileClientFactory != null) {
				try {
					bizFileClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}

		}
		outputJson(tojson, response);
	}
	
	
	/**
	 * 
	  * @Description: 删除展期资料
	  * @param request
	  * @param response
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月9日 下午6:43:54
	 */
	@RequestMapping(value = "deleteFile")
	@ResponseBody
	public void deleteFile(String fileId,HttpServletResponse response) {
		BaseClientFactory loan = null;
		BaseClientFactory bizFileClientFactory = null;
		BizFileService.Client bizFileClient = null;
		Map<String, Object> tojson = new HashMap<String, Object>();
		int count = 0;
		try {
			loan = getFactory(BusinessModule.MODUEL_REPAYMENT, "LoanExtensionService");
			bizFileClientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "BizFileService");
			
			bizFileClient = (BizFileService.Client) bizFileClientFactory.getClient();
			LoanExtensionService.Client loanClient = (LoanExtensionService.Client) loan.getClient();
			
			// 更新资料表信息
			if(null!=fileId ){
				count = loanClient.batchDeleteFile(fileId);
			}
			
			if (count == 0) {
				tojson.put("uploadStatus", "删除资料数据失败");
			} else {
				tojson.put("uploadStatus", "删除资料数据成功");
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("编辑资料列表出错" + e.getMessage());
			}
			e.printStackTrace();
		} finally {
			if (loan != null) {
				try {
					loan.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
			if (bizFileClientFactory != null) {
				try {
					loan.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
			if (bizFileClient != null) {
				try {
					loan.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(tojson, response);
	}
	
	/**
	 * 
	  * @Description: 获取展期目标历史项目ID
	  * @param projectId
	  * @param response
	  * @author: Zhangyu.Hoo
	  * @date: 2015年5月14日 下午3:34:02
	 */
	@RequestMapping(value = "getHisProjectIds", method = RequestMethod.POST)
	public void  getHisProjectIds(int projectId, HttpServletResponse response){
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory( BusinessModule.MODUEL_REPAYMENT, "LoanExtensionService");
			LoanExtensionService.Client client = (LoanExtensionService.Client) clientFactory.getClient();
			String hisProjectIds = client.getHisProjectIds(projectId);
			outputJson(hisProjectIds, response);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("获取展期目标历史项目ID信息错误:" + e.getMessage());
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
	}
	
	/**
	 * 
	  * @Description: 展期贷款试算页面跳转
	  * @param model
	  * @param loanId
	  * @param projectId
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年6月1日 下午5:52:24
	 */
	@RequestMapping(value = "loanCalc")
	public String loanCalc(ModelMap model, @RequestParam(value = "loanId", required = false) Integer loanId,Integer projectId) {
		try {
			setRepaymentListModel(model, loanId, projectId);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询还款计划表贷款信息:" + e.getMessage());
			}
		} 
		return "repayment/loanExtension/loan_calc";
	}

	/**
	 * 
	  * @Description: 设置展期还款计划表信息
	  * @param model
	  * @param loanId
	  * @param projectId
	  * @throws ThriftException
	  * @throws ThriftServiceException
	  * @throws TException
	  * @author: Zhangyu.Hoo
	  * @date: 2015年6月1日 下午5:54:40
	 */
	private void setRepaymentListModel(ModelMap model, Integer loanId, Integer projectId) throws ThriftException, ThriftServiceException,TException {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		BaseClientFactory loanFactory = getFactory( BusinessModule.MODUEL_REPAYMENT, "LoanExtensionService");
		BaseClientFactory contractFactory = getFactory(BusinessModule.MODUEL_CONTRACT, "ContractService");
		try {
			// 查询还款计划表数据
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			RepaymentLoanInfo repayment = client.getRepaymentLoanInfo(loanId);
			
			// 查询展期借款合同编号
			ContractService.Client contractClient = (ContractService.Client) contractFactory.getClient();
			String contractNum = contractClient.getExtensionContractNum(projectId);
			repayment.setContractNo(contractNum);
			model.put("repayment", repayment);
			model.put("loanId", loanId);
			
			// 查询被展期项目的信息
			LoanExtensionService.Client loanClient = (LoanExtensionService.Client) loanFactory.getClient();
			ProjectExtensionView view = loanClient.getByProjectId(projectId);
			
			if(null!= view){
				int oldLoanId = client.getLoanIdByProjectId(view.getExtensionProjectId());
				repayment = client.getRepaymentLoanInfo(oldLoanId);
/*				// 查询被展期项目是否是展期项目,是展期项目需要单独查询借据项目编号
				Project project = client.getProjectById(view.getExtensionProjectId());
				if(null!=project){
					if(project.getProjectType() ==4){
						contractNum = contractClient.getExtensionContractNum(view.getExtensionProjectId());
						repayment.setContractNo(contractNum);
					}
				}
*/				model.put("olRepayment", repayment);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (logger.isDebugEnabled()) {
				logger.debug("查询还款计划表贷款信息:" + e.getMessage());
			}
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
			if (loanFactory != null) {
				try {
					loanFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
			if (contractFactory != null) {
				try {
					contractFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	/**
	 * 根据客户ID查询当前客户是否存在正在进行的展期申请 
	  * @param projectId
	  * @param response
	  * @author: Administrator
	  * @date: 2016年4月7日 上午10:18:38
	 */
	@RequestMapping(value="isWorkflowByStatus")
	public void isWorkflowByStatus(int projectId, HttpServletResponse response){
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		BaseClientFactory loanFactory = getFactory( BusinessModule.MODUEL_REPAYMENT, "LoanExtensionService");
		int result = 0;
		try {
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			Project project = client.getProjectInfoById(projectId);
			int acctId = project.getAcctId();
			
			LoanExtensionService.Client loanClient = (LoanExtensionService.Client) loanFactory.getClient();
			
			result = loanClient.getCountForeExtensionByAcctId(acctId);
			outputJson(result, response);
		} catch (Exception e) {
			e.printStackTrace();
			if (logger.isDebugEnabled()) {
				logger.debug("根据客户ID查询当前客户是否存在正在进行的展期申请 :" + e.getMessage());
			}
			outputJson(1, response);
		}
	}
	
}
