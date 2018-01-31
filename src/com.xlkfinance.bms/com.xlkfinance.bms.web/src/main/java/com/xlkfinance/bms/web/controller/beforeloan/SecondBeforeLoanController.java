/**
 * @Title: BeforeLoanController.java
 * @Package com.xlkfinance.bms.web.controller.beforeloan
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月11日 上午9:51:23
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.beforeloan;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.common.constant.BizDynamicConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.constant.SysLogTypeConstant;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.CreditsService;
import com.xlkfinance.bms.rpc.beforeloan.ProceduresService;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectApprovalInfo;
import com.xlkfinance.bms.rpc.beforeloan.ProjectAssure;
import com.xlkfinance.bms.rpc.beforeloan.ProjectForeclosure;
import com.xlkfinance.bms.rpc.beforeloan.ProjectGuaranteeType;
import com.xlkfinance.bms.rpc.beforeloan.ProjectProperty;
import com.xlkfinance.bms.rpc.beforeloan.ProjectPublicMan;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.beforeloan.ProjectSurveyReport;
import com.xlkfinance.bms.rpc.beforeloan.ProjectSurveyReportService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.contract.TempLateParmDto;
import com.xlkfinance.bms.rpc.customer.CusComService;
import com.xlkfinance.bms.rpc.customer.CusComShare;
import com.xlkfinance.bms.rpc.customer.ExcelCusComBalanceSheet;
import com.xlkfinance.bms.rpc.customer.ExcelCusComIncomeReport;
import com.xlkfinance.bms.rpc.customer.ExportCusComBase;
import com.xlkfinance.bms.rpc.finance.FinanceBank;
import com.xlkfinance.bms.rpc.finance.FinanceService;
import com.xlkfinance.bms.rpc.inloan.ApplyHandleInfoDTO;
import com.xlkfinance.bms.rpc.inloan.BizDynamic;
import com.xlkfinance.bms.rpc.inloan.BizDynamicService;
import com.xlkfinance.bms.rpc.inloan.BizHandleService;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleService;
import com.xlkfinance.bms.rpc.system.TemplateFile;
import com.xlkfinance.bms.rpc.system.TemplateFileService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.controller.contract.ContractGenerator;
import com.xlkfinance.bms.web.page.Json;
import com.xlkfinance.bms.web.util.CommonUtil;
import com.xlkfinance.bms.web.util.ExcelExport;
import com.xlkfinance.bms.web.util.ParseDocAndDocxUtil;

/**
 * 
 * @ClassName: SecondBeforeLoanController
 * @Description: 贷前controller
 * @author: Cai.Qing
 * @date: 2015年3月5日 上午11:45:29
 */
@Controller
@RequestMapping("/secondBeforeLoanController")
public class SecondBeforeLoanController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(SecondBeforeLoanController.class);

	/**
	 * 
	 * @Description: 新增尽职调查报告
	 * @param projectSurveyReport
	 *            尽职调查报告对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年3月5日 下午2:48:28
	 */
	@RequestMapping(value = "addSurveyReport", method = RequestMethod.POST)
	public void addSurveyReport(ProjectSurveyReport projectSurveyReport, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectSurveyReportService");
			ProjectSurveyReportService.Client client = (ProjectSurveyReportService.Client) clientFactory.getClient();
			int rows = client.addSurveyReport(projectSurveyReport);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_ADD, "新增尽职调查报告",projectSurveyReport.getProjectId());
				j.getHeader().put("success", true);
				j.getHeader().put("msg", rows);
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
				logger.debug("新增尽职调查报告:" + ExceptionUtil.getExceptionMessage(e));
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
	 * @Description: 修改尽职调查报告
	 * @param projectSurveyReport
	 *            尽职调查报告对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年3月9日 下午3:06:46
	 */
	@RequestMapping(value = "updateSurveyReport")
	public void updateSurveyReport(ProjectSurveyReport projectSurveyReport, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectSurveyReportService");
			ProjectSurveyReportService.Client client = (ProjectSurveyReportService.Client) clientFactory.getClient();
			int rows = client.updateSurveyReport(projectSurveyReport);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_UPDATE, "修改尽职调查报告",projectSurveyReport.getProjectId());
				j.getHeader().put("success", true);
				j.getHeader().put("msg", rows);
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "修改失败,请重新操作!");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("修改尽职调查报告:" + ExceptionUtil.getExceptionMessage(e));
			}
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "修改失败,请重新操作!");
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
	 * @Description: 查询尽职调查报告
	 * @param projectId
	 *            项目ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年3月9日 下午5:56:28
	 */
	@RequestMapping(value = "getSurveyReportByProjectId")
	public void getSurveyReportByProjectId(int projectId, HttpServletResponse response) {
		ProjectSurveyReport projectSurveyRepor = new ProjectSurveyReport();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectSurveyReportService");
			ProjectSurveyReportService.Client client = (ProjectSurveyReportService.Client) clientFactory.getClient();
			projectSurveyRepor = client.getSurveyReportByProjectId(projectId);
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询尽职调查报告:" + ExceptionUtil.getExceptionMessage(e));
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
		outputJson(projectSurveyRepor, response);
	}

	/**
	 * 
	 * @Description: 根据项目ID查找贷款ID
	 * @param projectId
	 *            项目ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年3月10日 上午11:56:50
	 */
	@RequestMapping(value = "getLoanIdByProjectId")
	public void getLoanIdByProjectId(int projectId, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			// 调用方法获取贷款ID
			int loanId = client.getLoanIdByProjectId(projectId);
			if (loanId != 0) {
				j.getHeader().put("success", true);
				j.getHeader().put("msg", loanId);
			} else {
				j.getHeader().put("success", true);
				j.getHeader().put("msg", -1);
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("根据项目ID查询贷款ID:" + ExceptionUtil.getExceptionMessage(e));
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
		outputJson(j, response);
	}

	/**
	 * 
	 * @Description: 根据项目ID查询授信ID
	 * @param projectId
	 *            项目ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年3月26日 上午9:56:46
	 */
	@RequestMapping(value = "getCreditIdByProjectId")
	public void getCreditIdByProjectId(int projectId, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			// 调用方法获取授信ID
			int creditId = client.getCreditIdByProjectId(projectId);
			if (creditId != 0) {
				j.getHeader().put("success", true);
				j.getHeader().put("msg", creditId);
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("根据项目ID查询授信ID:" + ExceptionUtil.getExceptionMessage(e));
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
		outputJson(j, response);
	}

	/**
	 * 
	 * @Description: 根据项目ID查询落实条件
	 * @param projectId
	 *            项目ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年3月10日 下午5:19:11
	 */
	@RequestMapping(value = "getProjectApprovalLs")
	public void getProjectApprovalLs(int projectId, HttpServletResponse response) {
		List<ProjectApprovalInfo> list = new ArrayList<ProjectApprovalInfo>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProceduresService");
			ProceduresService.Client client = (ProceduresService.Client) clientFactory.getClient();
			list = client.getProjectApprovalLs(projectId);
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询落实审批信息:" + ExceptionUtil.getExceptionMessage(e));
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
	 * @Description: 根据项目ID查询管理要求
	 * @param projectId
	 *            项目ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年3月10日 下午5:19:45
	 */
	@RequestMapping(value = "getProjectApprovalGl")
	public void getProjectApprovalGl(int projectId, HttpServletResponse response) {
		List<ProjectApprovalInfo> list = new ArrayList<ProjectApprovalInfo>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProceduresService");
			ProceduresService.Client client = (ProceduresService.Client) clientFactory.getClient();
			list = client.getProjectApprovalGl(projectId);
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询管理要求审批信息:" + ExceptionUtil.getExceptionMessage(e));
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
	 * @Description: 新增贷审会信息
	 * @param projectApprovalInfo
	 *            贷审会对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年3月9日 下午3:09:08
	 *//*
	@RequestMapping(value = "addProjectApprovalInfo", method = RequestMethod.POST)
	public void addProjectApprovalInfo(ProjectApprovalInfo projectApprovalInfo, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProceduresService");
			ProceduresService.Client client = (ProceduresService.Client) clientFactory.getClient();
			// 设置审批人员ID，就是当前登录人员的ID
			projectApprovalInfo.setApprovalUserId(getShiroUser().getPid());
			int rows = client.addProjectApprovalInfo(projectApprovalInfo);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_ADD, "新增贷审会审批信息");
				j.getHeader().put("success", true);
				j.getHeader().put("msg", rows);
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
				logger.debug("新增贷审会审批信息:" + ExceptionUtil.getExceptionMessage(e));
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

	*//**
	 * 
	 * @Description: 修改贷审会审批条件
	 * @param projectApprovalInfo
	 *            贷审会对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年4月1日 上午11:08:18
	 *//*
	@RequestMapping(value = "updateProjectApprovalInfo", method = RequestMethod.POST)
	public void updateProjectApprovalInfo(ProjectApprovalInfo projectApprovalInfo, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProceduresService");
			ProceduresService.Client client = (ProceduresService.Client) clientFactory.getClient();
			int rows = client.updProjectApprovalInfo(projectApprovalInfo);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_UPDATE, "修改贷审会审批信息");
				j.getHeader().put("success", true);
				j.getHeader().put("msg", rows);
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "修改失败,请重新操作!");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("修改贷审会审批信息:" + ExceptionUtil.getExceptionMessage(e));
			}
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "修改失败,请重新操作!");
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

	*//**
	 * 
	 * @Description: 修改贷审会信息是否确认
	 * @param projectApprovalInfo
	 *            审批信息对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年4月21日 上午11:13:45
	 *//*
	@RequestMapping(value = "updateIsConfirmPrimaryKey", method = RequestMethod.POST)
	public void updateIsConfirmPrimaryKey(ProjectApprovalInfo projectApprovalInfo, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProceduresService");
			ProceduresService.Client client = (ProceduresService.Client) clientFactory.getClient();
			// 设置确认人员为当前登录用户
			projectApprovalInfo.setConfirmUserId(getShiroUser().getPid());
			int rows = client.updateIsConfirmPrimaryKey(projectApprovalInfo);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_UPDATE, "修改贷审会是否确认");
				j.getHeader().put("success", true);
				j.getHeader().put("msg", rows);
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "修改失败,请重新操作!");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("修改贷审会是否确认:" + ExceptionUtil.getExceptionMessage(e));
			}
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "修改失败,请重新操作!");
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
	}*/

	/**
	 * 
	 * @Description: 删除审批信息
	 * @param pids
	 *            审批信息PID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年3月10日 下午5:15:00
	 *//*
	@RequestMapping(value = "deleteProjectApprovalInfo")
	public void deleteProjectApprovalInfo(String pids, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProceduresService");
			ProceduresService.Client client = (ProceduresService.Client) clientFactory.getClient();
			int rows = client.deleteProjectApprovalInfo(pids);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_DELETE, "删除贷审会审批信息");
				j.getHeader().put("success", true);
				j.getHeader().put("msg", rows);
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
				logger.debug("删除贷审会审批信息:" + ExceptionUtil.getExceptionMessage(e));
			}
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "删除失败,请重新操作!");
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

	*//**
	 * 
	 * @Description: 新增贷审会意见信息
	 * @param project
	 *            贷审会信息对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年3月12日 下午3:11:46
	 *//*
	@RequestMapping(value = "addProcedures", method = RequestMethod.POST)
	public void addProcedures(Project project, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			int rows = client.addProcedures(project);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_ADD, "新增贷审会信息");
				j.getHeader().put("success", true);
				j.getHeader().put("msg", rows);
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
				logger.debug("新增贷审会信息:" + ExceptionUtil.getExceptionMessage(e));
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
*/
	/**
	 * 
	 * @Description: 修改项目状态
	 * @param projectId
	 *            项目ID
	 * @param status
	 *            状态ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年3月12日 下午3:26:01
	 */
	@RequestMapping(value = "updateProjectStatus")
	public void updateProjectStatus(int projectId, int requestStatus, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			int rows = client.updateProjectStatusByPrimaryKey(projectId, requestStatus);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_UPDATE, "修改项目状态",projectId);
				j.getHeader().put("success", true);
				j.getHeader().put("msg", rows);
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "修改失败,请重新操作!");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("修改项目状态:" + ExceptionUtil.getExceptionMessage(e));
			}
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "修改失败,请重新操作!");
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
	 * @Description: 获取所有的贷款单位
	 * @param financeBank
	 *            银行账户信息对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年3月25日 上午11:57:37
	 */
	@RequestMapping(value = "getFinanceAcctManage")
	public void getFinanceAcctManage(FinanceBank financeBank, HttpServletResponse response) {
		List<FinanceBank> list = new ArrayList<FinanceBank>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			// 创建请选择对象
			//FinanceBank s = new FinanceBank();
			//s.setPid(-1);
			//s.setChargeName("--请选择--");
			//list.add(s);
			for (FinanceBank fb : client.getFinanceActtManager(financeBank)) {
				if (fb.getIsOpenText().equals("是")) {
					list.add(fb);
				}
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询贷款单位:" + ExceptionUtil.getExceptionMessage(e));
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
		outputJson(list, response);
	}

	/**
	 * 
	 * @Description: 根据项目ID查询当前项目的担保方式
	 * @param projectId
	 *            项目ID
	 * @param response
	 *            担保方式集合
	 * @author: Cai.Qing
	 * @date: 2015年3月30日 下午3:31:10
	 */
	@RequestMapping(value = "getGuaranteeTypeByProjectId")
	public void getGuaranteeTypeByProjectId(int projectId, HttpServletResponse response) {
		List<ProjectGuaranteeType> list = new ArrayList<ProjectGuaranteeType>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			list = client.getGuaranteeTypeByProjectId(projectId);
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("根据项目ID查询当前项目的担保方式:" + ExceptionUtil.getExceptionMessage(e));
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
	 * @Description: 根据项目ID查询保证人信息
	 * @param projectId
	 *            项目ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年4月7日 下午2:31:10
	 */
	@RequestMapping(value = "getAssureByProjectId")
	public void getAssureByProjectId(int projectId, HttpServletResponse response) {
		List<ProjectAssure> list = new ArrayList<ProjectAssure>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "CreditsService");
			CreditsService.Client client = (CreditsService.Client) clientFactory.getClient();
			list = client.getAssureByProjectId(projectId);
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("根据项目ID查询保证人信息:" + ExceptionUtil.getExceptionMessage(e));
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
	 * @Description: 根据项目ID查询保证个人信息
	 * @param projectId
	 *            新项目ID
	 * @param oldProject
	 *            旧项目ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年4月14日 上午9:51:39
	 */
	@RequestMapping(value = "getProjectAssureByPersonal")
	public void getProjectAssureByPersonal(int projectId, String oldProject, HttpServletResponse response) {
		List<ProjectAssure> list = new ArrayList<ProjectAssure>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "CreditsService");
			CreditsService.Client client = (CreditsService.Client) clientFactory.getClient();
			// 拼接项目ID --
			// 1.如果没有传旧项目ID过来,就表示当前只需要查询当前项目的个人保证信息;
			// 2.如果传了旧项目ID过来,就表示需要查询两个项目的个人保证信息.需要拼接项目ID 如：(1,2)
			String projectIds = "";
			if (null == oldProject || "-1".equals(oldProject)) {
				// 等于 -1 表示没有旧项目ID,不需要拼接
				projectIds = projectId + "";
			} else {
				// 反之,表示有旧项目ID,需要拼接
				projectIds = projectId + "," + oldProject;
			}
			list = client.getProjectAssureByPersonal(projectIds);
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("根据项目ID查询个人保证人信息:" + ExceptionUtil.getExceptionMessage(e));
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
	 * @Description: 根据项目ID查询企业法人保证信息
	 * @param projectId
	 *            新项目ID
	 * @param oldProject
	 *            旧项目ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年4月14日 上午9:52:04
	 */
	@RequestMapping(value = "getProjectAssureByEnterprise")
	public void getProjectAssureByEnterprise(int projectId, String oldProject, HttpServletResponse response) {
		List<ProjectAssure> list = new ArrayList<ProjectAssure>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "CreditsService");
			CreditsService.Client client = (CreditsService.Client) clientFactory.getClient();
			// 拼接项目ID --
			// 1.如果没有传旧项目ID过来,就表示当前只需要查询当前项目的企业法人保证信息;
			// 2.如果传了旧项目ID过来,就表示需要查询两个项目的企业法人保证信息.需要拼接项目ID 如：(1,2)
			String projectIds = "";
			if (null == oldProject || "-1".equals(oldProject)) {
				// 等于 -1 表示没有旧项目ID,不需要拼接
				projectIds = projectId + "";
			} else {
				// 反之,表示有旧项目ID,需要拼接
				projectIds = projectId + "," + oldProject;
			}
			list = client.getProjectAssureByEnterprise(projectIds);
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("根据项目ID查询企业法人保证人信息:" + ExceptionUtil.getExceptionMessage(e));
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
	 * @Description: 新增担保信息
	 * @param projectAssure
	 *            担保信息对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年4月14日 上午9:54:49
	 */
	@RequestMapping(value = "addProjectAssure", method = RequestMethod.POST)
	public void addProjectAssure(ProjectAssure projectAssure, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "CreditsService");
			CreditsService.Client client = (CreditsService.Client) clientFactory.getClient();
			int rows = client.addProjectAssure(projectAssure);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_ADD, "新增保证信息",projectAssure.getProjectId());
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "新增担保信息成功!");
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
				logger.debug("新增保证信息:" + ExceptionUtil.getExceptionMessage(e));
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
	 * @Description: 批量删除保证信息
	 * @param refIds
	 *            保证信息对象PID(1,1,1)
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年4月14日 上午9:56:18
	 */
	@RequestMapping(value = "deleteProjectAssure", method = RequestMethod.POST)
	public void deleteProjectAssure(String refIds, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "CreditsService");
			CreditsService.Client client = (CreditsService.Client) clientFactory.getClient();
			int rows = client.deleteProjectAssure(refIds);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_UPDATE, "删除保证信息");
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "删除此批担保信息成功!");
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
				logger.debug("删除保证信息:" + ExceptionUtil.getExceptionMessage(e));
			}
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "删除失败,请重新操作!");
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

	/*// 组织召开贷审会人员查询
	@RequestMapping(value = "getOrganizationCommission")
	public void getOrganizationCommission(int projectId, HttpServletResponse response) {
		BaseClientFactory offlineMeetingServiceClientFactory = null;
		BaseClientFactory sysUserServiceClientFactory = null;
		StringBuffer member = new StringBuffer();
		StringBuffer memberId = new StringBuffer();
		OrganizationCommission organizationCommission = new OrganizationCommission();
		try {
			sysUserServiceClientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserService");
			offlineMeetingServiceClientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "OfflineMeetingService");
			OfflineMeetingService.Client client = (OfflineMeetingService.Client) offlineMeetingServiceClientFactory.getClient();
			SysUserService.Client sysUserServiceClient = (SysUserService.Client) sysUserServiceClientFactory.getClient();

			// 通过projectId查询meetingId
			BizProjectMeeting bizProjectMeeting = client.obtainBizProjectMeetingByProjectId(projectId);
			int meetingId = bizProjectMeeting.getPid();
			// 通过meetingId查询menber
			List<BizMeetingMinutesMember> list = client.obtainBizMeetingMinutesMemberByMeetingId(meetingId);
			List<String> pidList = new ArrayList<String>();
			StringBuffer allMeetingMembersPID = new StringBuffer();
			for (BizMeetingMinutesMember bizMeetingMinutesMember : list) {
				int userId = bizMeetingMinutesMember.getMeetingMemberUserId();
				allMeetingMembersPID.append(userId + ";");
				pidList.add(String.valueOf(userId));
			}

			List<SysUser> sysUserList = sysUserServiceClient.getSysUserByPids(pidList);
			for (int i = 0; i < sysUserList.size(); i++) {
				if (i != sysUserList.size()) {
					String userName = sysUserList.get(i).getRealName();
					int userId = sysUserList.get(i).getPid();
					member.append(userName + ",");
					memberId.append(userId + ",");
				} else {
					String userName = sysUserList.get(i).getRealName();
					int userId = sysUserList.get(i).getPid();
					member.append(userName);
					memberId.append(userId);
				}
			}
			organizationCommission.setMeetingMenberAllUserId(memberId.toString());
			organizationCommission.setMeetingMenberUser(member.toString());
			organizationCommission.setPid(meetingId);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("组织召开待审会:" + ExceptionUtil.getExceptionMessage(e));
			}
		} finally {
			if (offlineMeetingServiceClientFactory != null) {
				try {
					offlineMeetingServiceClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(organizationCommission, response);
	}

	// 组织召开贷审会
	@RequestMapping(value = "insertOrganizationCommission")
	public void insertoOrganizationCommission(OrganizationCommission organizationCommission, HttpServletResponse response) {
		BaseClientFactory offlineMeetingServiceClientFactory = null;
		int meetingId = organizationCommission.getPid();
		try {
			offlineMeetingServiceClientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "OfflineMeetingService");
			OfflineMeetingService.Client client = (OfflineMeetingService.Client) offlineMeetingServiceClientFactory.getClient();
			if (organizationCommission.getPid() == 0) {
				meetingId = client.saveOrganizationCommission(organizationCommission);
			}
			client.deleteBizMeetingMinutesMemberByMeetingId(meetingId);
			String[] allMembersPIDArrays = organizationCommission.getMeetingMenberUser().split(",");
			for (String meetingMemberUserId : allMembersPIDArrays) {
				// 参与人员BIZ_MEETING_MINUTES_MEMBER
				BizMeetingMinutesMember bizMeetingMinutesMember = new BizMeetingMinutesMember();
				bizMeetingMinutesMember.setMeetingId(meetingId);
				bizMeetingMinutesMember.setMeetingMemberUserId(Integer.parseInt(meetingMemberUserId));
				bizMeetingMinutesMember.setStatus(2);
				client.saveBizMeetingMinutesMember(bizMeetingMinutesMember);// 参与人员BIZ_MEETING_MINUTES_MEMBER
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("组织召开待审会:" + ExceptionUtil.getExceptionMessage(e));
			}
		} finally {
			if (offlineMeetingServiceClientFactory != null) {
				try {
					offlineMeetingServiceClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(meetingId, response);
	}*/

	/**
	 * 
	 * @Description: 批量设置授信项目为无效
	 * @param pids
	 *            项目ID(1,2,3)
	 * @param response
	 *            HttpServletResponse 对象
	 * @throws Exception
	 * @author: Cai.Qing
	 * @date: 2015年5月3日 上午9:03:27
	 */
	@RequestMapping(value = "setProjectInvalid", method = RequestMethod.POST)
	public void setProjectInvalid(String pids, HttpServletResponse response) throws Exception {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			int rows = client.updateProjectInvalid(pids);
			// 判断是否成功,如果大于0,表示成功
			if (rows > 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_UPDATE, "批量设置授信项目为无效");
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "批量设置授信项目为无效,操作成功!");
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "批量设置授信项目为无效,操作失败,请重新操作!");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "批量设置授信项目为无效,操作失败,请重新操作!");
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
	 * 
	 * @Description: 设置授信项目为有效
	 * @param pid
	 *            需要修改为有效的项目ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @throws Exception
	 * @author: Cai.Qing
	 * @date: 2015年5月3日 上午9:04:50
	 */
	@RequestMapping(value = "setProjectEffective", method = RequestMethod.POST)
	public void setProjectEffective(Integer projectId, HttpServletResponse response) throws Exception {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			int rows = client.setProjectEffective(projectId);
			if (rows > 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_UPDATE, "设置授信项目为有效");
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "设置授信项目为有效,操作成功!");
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "设置授信项目为有效,操作失败,请重新操作!");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "设置授信项目为有效,操作失败,请重新操作！");
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
	 * 
	 * @Description: 批量删除共同借款人
	 * @param userPids
	 *            共同借款人ID(1,2,3)
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年6月16日 下午4:28:03
	 */
	@RequestMapping(value = "batchDeleteProjectPublicMan", method = RequestMethod.POST)
	public void batchDeleteProjectPublicMan(String userPids, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			// 调用批量删除的方法
			int rows = client.batchDeleteProjectPublicMan(userPids);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_DELETE, "删除共同借款人");
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "删除共同借款人成功！");
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
				logger.debug("删除共同借款人:" + ExceptionUtil.getExceptionMessage(e));
			}
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "删除失败,请重新操作!");
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
	 * @Description: 新增共同借款人
	 * @param projectPublicMan
	 *            共同借款人对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年6月16日 下午4:29:56
	 */
	@RequestMapping(value = "addProjectPublicMan", method = RequestMethod.POST)
	public void addProjectPublicMan(ProjectPublicMan projectPublicMan, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			// 调用批量删除的方法
			int rows = client.addProjectPublicMan(projectPublicMan);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_ADD, "选择共同借款人");
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "选择共同借款人成功！");
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "选择失败,请重新操作!");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.debug("选择共同借款人:" + ExceptionUtil.getExceptionMessage(e));
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "选择失败,请重新操作!");
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(j, response);
	}

	/**
	 * 
	 * @Description: 导出尽职调查报告
	 * @param projectId
	 * @param comId
	 * @param response
	 * @param request
	 * @author: xuweihao
	 * @date: 2015年5月20日 下午7:18:16
	 */
	@RequestMapping(value = "exportSurveyReport")
	public void exportSurveyReport(int projectId, int comId, HttpServletResponse response, HttpServletRequest request) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		BaseClientFactory c = getFactory(BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		BaseClientFactory cf = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectSurveyReportService");
		BaseClientFactory cfcom = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		Project project = new Project();
		ProjectSurveyReport projectSurveyRepor = new ProjectSurveyReport();
		Map<String, String> map = new HashMap<String, String>();
		List<String> list = new ArrayList<String>();
		ContractGenerator contractGenerator = new ContractGenerator();
		ExportCusComBase cusComBase = new ExportCusComBase();
		List<ExcelCusComIncomeReport> elist = new ArrayList<ExcelCusComIncomeReport>();
		List<ExcelCusComBalanceSheet> blist = new ArrayList<ExcelCusComBalanceSheet>();
		List<CusComShare> clist = new ArrayList<CusComShare>();
		int count = 0;
		try {
			ProjectSurveyReportService.Client cli = (ProjectSurveyReportService.Client) cf.getClient();
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			CusComService.Client clcom = (CusComService.Client) cfcom.getClient();
			TemplateFileService.Client cl = (TemplateFileService.Client) c.getClient();
			cusComBase = clcom.selectCompanyByAcctId(comId);
			count = clcom.getPersonTotal(cusComBase.getPid());
			cusComBase.setPersonTotal(count);
			elist = clcom.excelCusComIncomeReportByComId(cusComBase.getPid());
			blist = clcom.excelCusComBalanceSheetByComId(cusComBase.getPid());
			clist = clcom.getShareList(cusComBase.getPid());
			project = client.getProjectById(projectId);
			projectSurveyRepor = cli.getSurveyReportByProjectId(projectId);
			TemplateFile template = cl.getTemplateFile("INVESTI");

			String[] projects = ParseDocAndDocxUtil.getFiledName(project);
			String[] repors = ParseDocAndDocxUtil.getFiledName(projectSurveyRepor);
			String[] company = ParseDocAndDocxUtil.getFiledName(cusComBase);
			String docText = ParseDocAndDocxUtil.parseDocumet(template.getFileUrl(), request);
			list = ParseDocAndDocxUtil.getDocumentSign(docText);
			List<TempLateParmDto> templateParamList = new ArrayList<TempLateParmDto>();
			for (int i = 0; i < list.size(); i++) {
				if (!list.get(i).equals("")) {
					String biaoshi = list.get(i).replace("@", "");
					for (int j = 0; j < projects.length; j++) {
						if (biaoshi.equals(projects[j])) {
							TempLateParmDto tempLateParmDto = new TempLateParmDto();
							if (projects[j].substring(projects[j].length() - 4, projects[j].length()).equals("Dttm")) {
								tempLateParmDto.setValConvertFlag(2);
								tempLateParmDto.setMatchFlag(list.get(i));
							} else {
								tempLateParmDto.setValConvertFlag(0);
								tempLateParmDto.setMatchFlag(list.get(i));
							}
							templateParamList.add(tempLateParmDto);
							if (ExcelExport.getFieldValueByName(projects[j], project) == null) {
								map.put(list.get(i), "");
							} else {
								map.put(list.get(i), ExcelExport.getFieldValueByName(projects[j], project));
							}
						}
					}
					for (int j = 0; j < repors.length; j++) {
						if (biaoshi.equals(repors[j])) {
							TempLateParmDto tempLateParmDto = new TempLateParmDto();
							if (repors[j].substring(repors[j].length() - 4, repors[j].length()).equals("Dttm")) {
								tempLateParmDto.setValConvertFlag(2);
								tempLateParmDto.setMatchFlag(list.get(i));
							} else {
								tempLateParmDto.setValConvertFlag(0);
								tempLateParmDto.setMatchFlag(list.get(i));
							}
							templateParamList.add(tempLateParmDto);
							if (ExcelExport.getFieldValueByName(repors[j], projectSurveyRepor) == null) {
								map.put(list.get(i), "");
							} else {
								map.put(list.get(i), ExcelExport.getFieldValueByName(repors[j], projectSurveyRepor));
							}

						}
					}
					for (int j = 0; j < company.length; j++) {
						if (biaoshi.equals(company[j])) {
							TempLateParmDto tempLateParmDto = new TempLateParmDto();
							if (company[j].substring(company[j].length() - 4, company[j].length()).equals("Dttm")) {
								tempLateParmDto.setValConvertFlag(2);
								tempLateParmDto.setMatchFlag(list.get(i));
							} else {
								tempLateParmDto.setValConvertFlag(0);
								tempLateParmDto.setMatchFlag(list.get(i));
							}
							templateParamList.add(tempLateParmDto);
							if (ExcelExport.getFieldValueByName(company[j], cusComBase) == null) {
								map.put(list.get(i), "");
							} else {
								map.put(list.get(i), ExcelExport.getFieldValueByName(company[j], cusComBase));
							}
						}
					}
				}
			}
			if (elist.size() != 0) {
				for (int j = 0; j < elist.size(); j++) {
					map.put("@thisMonthVal" + j + "@", elist.get(j).getThisMonthVal() == null ? "" : elist.get(j).getThisMonthVal());
					map.put("@thisYearVal" + j + "@", elist.get(j).getThisYearVal() == null ? "" : elist.get(j).getThisYearVal());
				}
				Double MonthgrossProfit = 0.0;
				Double YeargrossProfit = 0.0;
				Double grossMargin = 0.0;
				Double netProfitMargin = 0.0;
				if (elist.get(0).getThisMonthVal() != null && elist.get(1).getThisMonthVal() != null) {
					MonthgrossProfit = Double.parseDouble(elist.get(0).getThisMonthVal()) - Double.parseDouble(elist.get(1).getThisMonthVal());
					map.put("@MonthgrossProfit@", String.valueOf(MonthgrossProfit));
				} else {
					map.put("@MonthgrossProfit@", "");
				}
				if (elist.get(0).getThisYearVal() != null && elist.get(1).getThisYearVal() != null) {
					YeargrossProfit = Double.parseDouble(elist.get(0).getThisYearVal()) - Double.parseDouble(elist.get(1).getThisYearVal());
					grossMargin = Math.round(YeargrossProfit / Double.parseDouble(elist.get(0).getThisYearVal()) * 100) / 100.0;
					map.put("@YeargrossProfit@", String.valueOf(YeargrossProfit));
					map.put("@grossMargin@", String.valueOf(grossMargin * 100) + "%");
				} else {
					map.put("@YeargrossProfit@", "");
					map.put("@grossMargin@", "");
				}
				if (elist.get(14).getThisYearVal() != null && elist.get(0).getThisYearVal() != null) {
					netProfitMargin = Math.round(Double.parseDouble(elist.get(14).getThisYearVal()) / Double.parseDouble(elist.get(0).getThisYearVal()) * 100) / 100.0;
					map.put("@netProfitMargin@", String.valueOf(netProfitMargin * 100) + "%");
				} else {
					map.put("@netProfitMargin@", "");
				}
			} else {
				for (int j = 0; j < 18; j++) {
					map.put("@thisMonthVal" + j + "@", "");
					map.put("@thisYearVal" + j + "@", "");
				}
				map.put("@MonthgrossProfit@", "");
				map.put("@YeargrossProfit@", "");
				map.put("@grossMargin@", "");
				map.put("@netProfitMargin@", "");
			}
			double total = 0;
			double shareRatio = 0;
			for (int j = 0; j < 4; j++) {
				if (clist.size() - 1 < j) {
					map.put("@shareName" + j + "@", "");
					map.put("@invMoney" + j + "@", "");
					map.put("@invWayName" + j + "@", "");
					map.put("@shareRatio" + j + "@", "");
				} else {
					map.put("@shareName" + j + "@", clist.get(j).getShareName() == null ? "" : clist.get(j).getShareName());
					map.put("@invMoney" + j + "@", String.valueOf(clist.get(j).getInvMoney()) + "万");
					map.put("@invWayName" + j + "@", clist.get(j).getInvWayName() == null ? "" : clist.get(j).getInvWayName());
					map.put("@shareRatio" + j + "@", String.valueOf(clist.get(j).getShareRatio()));
					total = total + clist.get(j).getInvMoney();
					shareRatio = shareRatio + clist.get(j).getShareRatio();
				}
			}
			map.put("@total@", String.valueOf(total) + "万");
			map.put("@shareRatio@", String.valueOf(shareRatio));
			if (blist.size() != 0) {
				for (int j = 0; j < blist.size(); j++) {
					map.put("@endVal" + j + "@", blist.get(j).getEndVal() == null ? "" : blist.get(j).getEndVal());
				}
				map.put("@year@", blist.get(0).getAccountsCode() == null ? "" : blist.get(0).getAccountsCode() + "年");
				map.put("@month@", blist.get(0).getAccountsName() == null ? "" : blist.get(0).getAccountsName() + "月");
			} else {
				for (int j = 0; j < 74; j++) {
					map.put("@endVal" + j + "@", "");
				}
				map.put("@year@", " 年");
				map.put("@month@", " 月");
			}
			//
			String postfix= template.getFileUrl().substring(template.getFileUrl().lastIndexOf("."), template.getFileUrl().length());
//			String realFileName = genFileName(template.getFileUrl());
			String realFileName="尽职报告"+postfix;
//			String realFileName = genFileName(template.getFileUrl());
			String localPath = CommonUtil.getRootPath() + ExcelExport.getDateToString();
			File file = ExcelExport.createFile(localPath, realFileName);
			String path = CommonUtil.getRootPath() + template.getFileUrl();
			// TODO 新修改合同动态表格
			boolean b = contractGenerator.generate(this, templateParamList, path, file.getPath(), map, null, null);
			if (b == true) {
				ExcelExport.sentFile(realFileName, localPath, response, request);
			}
		} catch (Exception e) {
			logger.error("导出尽职调查报告失败", e);
			response.setContentType("text/html;charset=UTF-8");
			try {
				PrintWriter out = response.getWriter();
				out.println("<script>alert('导出尽职调查报告失败！')</script>");
			} catch (IOException e1) {
				logger.error("流输出错误", e1);
			}
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}

	}

	@RequestMapping(value = "getAcctIdByComId")
	public void getAcctIdByComId(int comId, HttpServletResponse response) {
		BaseClientFactory cfcom = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService");
		int acctId = 0;
		try {
			CusComService.Client clcom = (CusComService.Client) cfcom.getClient();
			acctId = clcom.getAcctIdByComId(comId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cfcom != null) {
				try {
					cfcom.destroy();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		outputJson(acctId, response);
	}
	
	
	/**
	 * 
	 * @Description: 根据项目ID查询落实条件
	 * @param projectId
	 *            项目ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年3月10日 下午5:19:11
	 */
	@RequestMapping(value = "getProjectApprovalLsInfo")
	public void getProjectApprovalLsInfo(int interestChgId, HttpServletResponse response) {
		List<ProjectApprovalInfo> list = new ArrayList<ProjectApprovalInfo>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProceduresService");
			ProceduresService.Client client = (ProceduresService.Client) clientFactory.getClient();
			list = client.getProjectApprovalLsInfo(interestChgId);
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询落实审批信息:" + ExceptionUtil.getExceptionMessage(e));
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
	 * @Description: 根据项目ID查询管理要求
	 * @param projectId
	 *            项目ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年3月10日 下午5:19:45
	 */
	@RequestMapping(value = "getProjectApprovalGlInfo")
	public void getProjectApprovalGlInfo(int interestChgId, HttpServletResponse response) {
		List<ProjectApprovalInfo> list = new ArrayList<ProjectApprovalInfo>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProceduresService");
			ProceduresService.Client client = (ProceduresService.Client) clientFactory.getClient();
			list = client.getProjectApprovalGlInfo(interestChgId);
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询管理要求审批信息:" + ExceptionUtil.getExceptionMessage(e));
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
	 * @Description: 修改贷审会信息是否确认
	 * @param projectApprovalInfo
	 *            审批信息对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年4月21日 上午11:13:45
	 *//*
	@RequestMapping(value = "updateIsConfirmPrimaryKeyInfo", method = RequestMethod.POST)
	public void updateIsConfirmPrimaryKeyInfo(ProjectApprovalInfo projectApprovalInfo, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProceduresService");
			ProceduresService.Client client = (ProceduresService.Client) clientFactory.getClient();
			// 设置确认人员为当前登录用户
			projectApprovalInfo.setConfirmUserId(getShiroUser().getPid());
			int rows = client.updateIsConfirmPrimaryKeyInfo(projectApprovalInfo);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_UPDATE, "修改贷审会是否确认");
				j.getHeader().put("success", true);
				j.getHeader().put("msg", rows);
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "修改失败,请重新操作!");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("修改贷审会是否确认:" + ExceptionUtil.getExceptionMessage(e));
			}
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "修改失败,请重新操作!");
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
	}*/

	/**
	 * 
	 * @Description: 删除审批信息
	 * @param pids
	 *            审批信息PID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年3月10日 下午5:15:00
	 *//*
	@RequestMapping(value = "deleteProjectApprovalResInfo")
	public void deleteProjectApprovalResInfo(String pids, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProceduresService");
			ProceduresService.Client client = (ProceduresService.Client) clientFactory.getClient();
			int rows = client.deleteProjectApprovalResInfo(pids);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_REPAYMENT, SysLogTypeConstant.LOG_TYPE_DELETE, "删除贷审会审批信息");
				j.getHeader().put("success", true);
				j.getHeader().put("msg", rows);
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
				logger.debug("删除贷审会审批信息:" + ExceptionUtil.getExceptionMessage(e));
			}
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "删除失败,请重新操作!");
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
	}*/
	
	/**
	 * 查询项目状态
	  * @param projectId
	  * @param response
	  * @author: Administrator
	  * @date: 2016年3月7日 下午2:26:07
	 */
	@RequestMapping(value="getProjectForeStates")
	public void getProjectForeStates(Integer projectId,HttpServletResponse response){
		int foreState = -1;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			Project project = client.getLoanProjectByProjectId(projectId);
			if(project != null){
				foreState = project.getForeclosureStatus();
			}
		} catch (Exception e) {
			logger.error("查询项目状态出错:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(foreState, response);
	}
	/**
	 * 查询赎楼信息是否填写
	  * @param projectId
	  * @param response
	  * @author: Administrator
	  * @date: 2016年4月1日 下午2:56:03
	 */
	@RequestMapping(value="checkForeInfo")
	public void checkForeInfo(Integer projectId,HttpServletResponse response){
		int foreState = 0;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			Project project = client.getLoanProjectByProjectId(projectId);
			ProjectProperty property = project.getProjectProperty();
			ProjectForeclosure foreInfo = project.getProjectForeclosure();
			
			//物业信息、赎楼信息存在并且卖方姓名不为空，原贷款银行不为空
			if(property != null && foreInfo != null && !StringUtil.isBlank(property.getSellerName())){
				foreState = 1;
			}
			
		} catch (Exception e) {
			logger.error("查询赎楼信息是否填写:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(foreState, response);
	}
	
	/**
	 * 校验客户经理是否填写申请办理信息
	  * @param projectId
	  * @param response
	  * @author: Administrator
	  * @date: 2016年4月1日 下午3:00:22
	 */
	@RequestMapping(value="checkApplyHandleInfo")
	public void checkApplyHandleInfo(Integer projectId,HttpServletResponse response){
		int applyHandleState = 0;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_INLOAN, "BizHandleService");
			BizHandleService.Client client = (BizHandleService.Client) clientFactory.getClient();
			ApplyHandleInfoDTO applyHandleInfoDTO = new ApplyHandleInfoDTO();
			applyHandleInfoDTO.setProjectId(projectId);
			List<ApplyHandleInfoDTO> result = client.findAllApplyHandleInfo(applyHandleInfoDTO );
			if(result != null && result.size()>0){
				applyHandleState = 1;
			}else{
				applyHandleState = 0;
			}
		} catch (Exception e) {
			logger.error("查询是否填写申请办理信息:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(applyHandleState, response);
	}
	
	/**
	 * 校验是否填写特殊情况说明
	  * @param projectId
	  * @param response
	  * @author: Administrator
	  * @date: 2016年4月2日 下午3:47:10
	 */
	@RequestMapping(value = "checkSpecialDesc")
	public void checkSpecialDesc(int projectId, HttpServletResponse response) {
		
		BaseClientFactory clientFactory = null;
		int specialState = 0;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectSurveyReportService");
			ProjectSurveyReportService.Client client = (ProjectSurveyReportService.Client) clientFactory.getClient();
			ProjectSurveyReport projectSurveyRepor = client.getSurveyReportByProjectId(projectId);
			if(projectSurveyRepor != null){
				String specialDesc = projectSurveyRepor.getSpecialDesc();
				//无特殊情况
				if(StringUtil.isBlank(specialDesc)){
					specialState = 0;
				}else{
					specialState = 1;
				}
			}else{
				specialState = 0;
			}
			
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
		} catch (Exception e) {
				logger.error("校验是否填写特殊情况说明出错:" + ExceptionUtil.getExceptionMessage(e));
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
		outputJson(specialState, response);
	}
	
	/**
	 * 查询审查员意见
	  * @param projectId
	  * @param response
	  * @author: baogang
	  * @date: 2016年4月26日 上午11:47:31
	 */
	@RequestMapping(value="getAuditorOpinion")
	public void getAuditorOpinion(int projectId, HttpServletResponse response){
		String auditorOpinion = "";
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			Project project = client.getProjectInfoById(projectId);
			auditorOpinion = project.getAuditorOpinion();
		} catch (Exception e) {
			logger.error("查询审查员意见:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(auditorOpinion, response);
	}
	
	/**
	 * 保存审查员意见
	 * @param project
	 * @param response
	 */
	@RequestMapping(value="updateAuditorOpinion")
	public void updateAuditorOpinion(Project project,HttpServletResponse response){
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			if(project.getPid() <= 0){
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "保存失败,请重新操作!");
			}else{
				int result = client.updateAuditorOpinion(project);
				if(result >0){
					j.getHeader().put("success", true);
					// 成功的话,就做业务日志记录
					recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_UPDATE, "保存审查员审批信息",project.getPid());
				}else{
					j.getHeader().put("success", false);
					j.getHeader().put("msg", "保存失败,请重新操作!");
				}
			}
		} catch (Exception e) {
			logger.error("保存审查员意见出错:" + ExceptionUtil.getExceptionMessage(e));
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "保存失败,请重新操作!");
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
	 * 查询展期费率是否大于贷前费率（大于返回1，小于返回0）
	  * @param projectId
	  * @param response
	  * @author: Administrator
	  * @date: 2016年4月1日 下午2:56:03
	 */
	@RequestMapping(value="checkFeeRate")
	public void checkFeeRate(Integer projectId,HttpServletResponse response){
		int exFeeRate = 0;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			Project project = client.getLoanProjectByProjectId(projectId);
			double feeRate = project.getProjectGuarantee().getFeeRate();//贷前费率
			double extensionRate = project.getProjectForeclosure().getExtensionRate();//展期费率
			//四舍五入，保留两位小数
			feeRate = (double)Math.round(feeRate*100)/100;
			extensionRate = (double)Math.round(extensionRate*100)/100;
			
			if(extensionRate >= feeRate ){
				exFeeRate = 1;
			}
			
		} catch (Exception e) {
			logger.error("查询展期费率是否大于贷前费率:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(exFeeRate, response);
	}
	
	/**
	 * 加载项目实时流程图页面
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value="toProjectFlow")
	public String toProjectFlow(Integer projectId, ModelMap model){
		if(projectId != null && projectId >0){
			// 创建对象
			Project project = new Project();
			double realLoan=0;
			List<BizDynamic> dynamicList = new ArrayList<BizDynamic>();
			//贷前流程节点集合
			Map<String,Object> beforeLoanMap = new HashMap<String,Object>();
			//贷中流程节点集合
			Map<String,Object> inloanMap = new HashMap<String,Object>();
			BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			BaseClientFactory bizFactory = getFactory(BusinessModule.MODUEL_INLOAN, "BizDynamicService");
			BaseClientFactory financeFactory = getFactory(BusinessModule.MODUEL_INLOAN, "FinanceHandleService");
			try {
				ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
				project = client.getLoanProjectByProjectId(projectId);
				//获取放款金额
				FinanceHandleService.Client financeHandleService = (FinanceHandleService.Client) financeFactory.getClient();
				List<Integer> recPros=Arrays.asList(Constants.REC_PRO_3);
                realLoan = financeHandleService.getRecMoney(projectId, recPros);
				//查询动态办理记录列表
				BizDynamicService.Client bizDynamicService = (BizDynamicService.Client) bizFactory.getClient();
				BizDynamic query = new BizDynamic();
				query.setProjectId(projectId);
				query.setPage(-1);
				dynamicList = bizDynamicService.queryBizDynamic(query);
				
				if(dynamicList != null && dynamicList.size()>0){
					for(BizDynamic bizDynamic:dynamicList){
						String moduelNumber = bizDynamic.getModuelNumber();
                        if(moduelNumber.equals(BizDynamicConstant.MODUEL_NUMBER_BEFORELOAN)){
							beforeLoanMap.put(bizDynamic.getDynamicNumber(), bizDynamic);
						}else if(BizDynamicConstant.MODUEL_NUMBER_INLOAN.equals(moduelNumber)){
						    inloanMap.put(bizDynamic.getDynamicNumber(), bizDynamic);
						}
					}
				}
			} catch (Exception e) {
				logger.error("加载项目实时流程图页面:" + ExceptionUtil.getExceptionMessage(e));
			} finally {
				destroyFactory(clientFactory,bizFactory,financeFactory);
			}
			model.put("project", project);
			model.put("dynamicList", dynamicList);
			model.put("beforeLoanMap", beforeLoanMap);
			model.put("inloanMap", inloanMap);
			model.put("realLoan", realLoan);
		}
		
		return "beforeloan/project_flow";
	}
	
	/**
	 * 修改项目名称
	 * @param project
	 * @param response
	 */
	@RequestMapping("/updateProjectName")
	public void updateProjectName(Project project, HttpServletResponse response){
		logger.info("修改项目名称，参数：" + project);
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			int rows = client.updateProjectName(project);
			if (rows>0) {
				j.getHeader().put("success", true);
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_UPDATE, "项目编号为："+project.getPid()+"的项目修改项目名称为:" +project.getProjectName() ,project.getPid());
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "修改项目名称失败,请重新操作!");
			}
		}catch(Exception e) {
			logger.error("项目修改项目名称失败：" + ExceptionUtil.getExceptionMessage(e));
	        e.printStackTrace();
	        j.getHeader().put("success", false);
			j.getHeader().put("msg", "修改项目名称失败,请重新操作!");
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(j, response);
	}
}
