/**
 * @Title: AfterLoanController.java
 * @Package com.xlkfinance.bms.web.controller.afterloan
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月11日 上午9:50:40
 * @version V1.0
 * 
 */
package com.xlkfinance.bms.web.controller.afterloan;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
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
import com.alibaba.fastjson.JSONObject;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.afterloan.AfterLoanSupervisionService;
import com.xlkfinance.bms.rpc.afterloan.AssignmentDistribution;
import com.xlkfinance.bms.rpc.afterloan.AssignmentDistributionSearch;
import com.xlkfinance.bms.rpc.afterloan.BadDebtBean;
import com.xlkfinance.bms.rpc.afterloan.BadDebtBeas;
import com.xlkfinance.bms.rpc.afterloan.BadDebtBeasCriteria;
import com.xlkfinance.bms.rpc.afterloan.BizProjectRegHistory;
import com.xlkfinance.bms.rpc.afterloan.BizProjectRegPlan;
import com.xlkfinance.bms.rpc.afterloan.BizProjectViolation;
import com.xlkfinance.bms.rpc.afterloan.BreachDisposeBeas;
import com.xlkfinance.bms.rpc.afterloan.BreachDisposeCriteria;
import com.xlkfinance.bms.rpc.afterloan.CollectionCusComContact;
import com.xlkfinance.bms.rpc.afterloan.CollectionCustomer;
import com.xlkfinance.bms.rpc.afterloan.CollectionRecord;
import com.xlkfinance.bms.rpc.afterloan.CollectionRecordDto;
import com.xlkfinance.bms.rpc.afterloan.MisapprProcess;
import com.xlkfinance.bms.rpc.afterloan.MisapprProcessCriteria;
import com.xlkfinance.bms.rpc.afterloan.MisapprProcessService;
import com.xlkfinance.bms.rpc.afterloan.ProjectInfo;
import com.xlkfinance.bms.rpc.afterloan.ProjectReminderPlanDto;
import com.xlkfinance.bms.rpc.afterloan.ReminderNotice;
import com.xlkfinance.bms.rpc.afterloan.ReminderNoticePart;
import com.xlkfinance.bms.rpc.afterloan.RepaymentCollection;
import com.xlkfinance.bms.rpc.afterloan.RepaymentCollectionSearch;
import com.xlkfinance.bms.rpc.afterloan.RepaymentCollectionService;
import com.xlkfinance.bms.rpc.afterloan.ResultBizProjectRegPlan;
import com.xlkfinance.bms.rpc.afterloan.SupervisionResultSearch;
import com.xlkfinance.bms.rpc.afterloan.SupervisionSearchBean;
import com.xlkfinance.bms.rpc.afterloan.ToDayUpdateDTO;
import com.xlkfinance.bms.rpc.beforeloan.CalcOperDto;
import com.xlkfinance.bms.rpc.beforeloan.Loan;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.beforeloan.RepaymentLoanInfo;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.contract.ContractDynamicTableParameter;
import com.xlkfinance.bms.rpc.contract.TempLateParmDto;
import com.xlkfinance.bms.rpc.file.FileInfo;
import com.xlkfinance.bms.rpc.finance.BadDebtDataBean;
import com.xlkfinance.bms.rpc.finance.CustArrearsView;
import com.xlkfinance.bms.rpc.finance.FinanceReceivablesView;
import com.xlkfinance.bms.rpc.finance.FinanceService;
import com.xlkfinance.bms.rpc.repayment.LoanExtensionService;
import com.xlkfinance.bms.rpc.repayment.RepaymentPlanBaseDTO;
import com.xlkfinance.bms.rpc.repayment.RepaymentService;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.rpc.system.SysLookupService;
import com.xlkfinance.bms.rpc.system.SysLookupVal;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.rpc.system.SysUserService;
import com.xlkfinance.bms.rpc.system.TemplateFile;
import com.xlkfinance.bms.rpc.system.TemplateFileService;
import com.xlkfinance.bms.rpc.task.TaskService;
import com.xlkfinance.bms.rpc.workflow.TaskVo;
import com.xlkfinance.bms.web.constant.Constants;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.controller.contract.ContractGenerator;
import com.xlkfinance.bms.web.controller.finance.FinanceTypeEnum;
import com.xlkfinance.bms.web.page.Json;
import com.xlkfinance.bms.web.shiro.ShiroUser;
import com.xlkfinance.bms.web.util.CommonUtil;
import com.xlkfinance.bms.web.util.ExcelExport;
import com.xlkfinance.bms.web.util.FileDownload;
import com.xlkfinance.bms.web.util.FileUtil;
import com.xlkfinance.bms.web.util.ParseDocAndDocxUtil;

@Controller
@RequestMapping("/afterLoanController")
public class AfterLoanController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(AfterLoanController.class);
	private static final String serviceModuel = "finance";

	@Resource(name = "contractGenerator")
	private ContractGenerator contractGenerator;
	/**
	 * 贷后监管查询
	 * 
	 * @param limitId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "listSuperviseBusiness")
	public String getSuperviseBusiness(SupervisionSearchBean supervisionSearchBean, ModelMap model) throws Exception {
		return "afterloan/superviseBusinessQuery";
	}

	/**
	 * 
	 * 
	 * @Description: 贷后监管查询
	 * @param supervisionSearchBean
	 * @param model
	 * @author: loe.luozhonghua
	 * @date: 2015年3月23日 下午8:13:19
	 */
	@RequestMapping(value = "superviseBusinessList")
	@ResponseBody
	public void superviseBusinessList(SupervisionSearchBean supervisionSearchBean, ModelMap model, HttpServletResponse response) {
		AfterLoanSupervisionService.Client client = null;
		BaseClientFactory clientFactory = null;

		List<GridViewDTO> list = new ArrayList<GridViewDTO>();
		Map<String, Object> map = new HashMap<String, Object>();
		// SupervisionSearchBean s=new SupervisionSearchBean();
		/*
		 * supervisionSearchBean.setPage(page);
		 * supervisionSearchBean.setRows(rows);
		 */
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "AfterLoanSupervisionService");
			client = (AfterLoanSupervisionService.Client) clientFactory.getClient();
			list = client.supervisionList(supervisionSearchBean);
			int total = client.getTotalSupervisionList(supervisionSearchBean);
			map.put("total", total);
			map.put("rows", list);

		} catch (Exception e) {
			logger.error("贷后监管查询失败", e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
				}
			}
		}
		outputJson(map, response);
	}

	/**
	 * 
	 * 
	 * @Description: 监管任务
	 * @param limitId
	 * @param model
	 * @return
	 * @throws Exception
	 * @author: loe.luozhonghua
	 * @date: 2015年3月23日 上午10:11:42
	 */
	@RequestMapping(value = "taskSuperviseBusiness")
	public String taskSuperviseBusiness(@RequestParam(value = "limitId", required = false) Integer limitId, ModelMap model) throws Exception {
		return "afterloan/taskSuperviseBusinessQuery";
	}

	/**
	 * @Description: 新增、编辑监管计划跳转页面
	 * @param pid
	 * @param comId
	 * @param acctId
	 * @param model
	 * @return
	 * @throws Exception
	 * @author: loe.luozhonghua
	 * @date: 2015年3月23日 下午2:33:06
	 */
	@RequestMapping(value = "editAfterloanRegulatoryPlan")
	public String editAfterloanRegulatoryPlan(Integer projectId, ModelMap model) throws Exception {
		model.addAttribute("projectId", projectId);

		AfterLoanSupervisionService.Client client = null;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "AfterLoanSupervisionService");
			client = (AfterLoanSupervisionService.Client) clientFactory.getClient();

			// model.addAttribute("supervisionTask",
			// client.getSupervisionTask(projectId));
			SupervisionSearchBean supervisionSearchBean = new SupervisionSearchBean();
			supervisionSearchBean.setProjectId(projectId);
			supervisionSearchBean.setPage(1);
			supervisionSearchBean.setRows(Integer.MAX_VALUE);
			//int userId = getShiroUser().getPid();
			//supervisionSearchBean.setUserId(userId);
			List<ResultBizProjectRegPlan> list = client.supervisionTaskList(supervisionSearchBean);

			// 由于sql中以项目作为主要表关联查询的，所以会导致有一些计划为空的记录，（避免修改sql复杂，在这里移除要简单一些）要移除
			for (int i = list.size() - 1; i >= 0; i--) {
				ResultBizProjectRegPlan plan = list.get(i);
				if (plan.getPid() == 0 && null == plan.getPlanDt()) {
					list.remove(i);
				}
			}

			model.put("list", list);
		} catch (Exception e) {
			logger.error("新增、编辑监管计划跳转页面失败", e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
				}
			}
		}

		return "afterloan/editAfterSuperviseBusiness";
	}

	/**
	 * 
	 * @Description: 新增、修改监管计划
	 * @param projectId
	 * @param model
	 * @return
	 * @throws Exception
	 * @author: loe.luozhonghua
	 * @date: 2015年3月31日 下午2:14:44
	 */
	@RequestMapping(value = "viewAfterloanRegulatoryPlan")
	public String viewAfterloanRegulatoryPlan(Integer projectId, int pid, ModelMap model) throws Exception {
		model.addAttribute("projectId", projectId);
		ResultBizProjectRegPlan bean = null;
		if (pid > 0) {
			AfterLoanSupervisionService.Client client = null;
			BaseClientFactory clientFactory = null;
			try {
				clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "AfterLoanSupervisionService");
				client = (AfterLoanSupervisionService.Client) clientFactory.getClient();

				// 根据主键来查询
				SupervisionSearchBean supervisionSearchBean = new SupervisionSearchBean();
				supervisionSearchBean.setPid(pid);
				supervisionSearchBean.setPage(1);
				supervisionSearchBean.setRows(1);
				//int userId = getShiroUser().getPid();
				//supervisionSearchBean.setUserId(userId);
				List<ResultBizProjectRegPlan> list = client.supervisionTaskList(supervisionSearchBean);
				if (list != null && list.size() > 0) {
					bean = list.get(0);
				}
			} catch (Exception e) {
				logger.error("viewAfterloanRegulatoryPlan fail", e);
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

		if (bean == null) {
			bean = new ResultBizProjectRegPlan();
		}

		model.put("bean", bean);
		return "afterloan/viewAfterSuperviseBusiness";
	}

	/**
	 * 
	 * @Description: 新增、更新监管计划
	 * @param bizProjectRegPlan
	 * @param model
	 * @return
	 * @author: loe.luozhonghua
	 * @date: 2015年3月31日 上午11:11:37
	 */
	@RequestMapping(value = "saveSupervisePlan")
	public void saveSupervisePlan(BizProjectRegPlan bizProjectRegPlan, ModelMap model, HttpServletResponse response) {

		Json result = null;

		AfterLoanSupervisionService.Client client = null;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "AfterLoanSupervisionService");
			client = (AfterLoanSupervisionService.Client) clientFactory.getClient();
			if (bizProjectRegPlan.getPid() != 0) {
				client.updateBizProRegPlan(bizProjectRegPlan);
			} else {
				bizProjectRegPlan.setRegulatoryStatus(1); // 设置监管状态为等待监管
				bizProjectRegPlan.setPid(client.createBizProRegPlan(bizProjectRegPlan));
				// 发送相关通知
				sendMessage(bizProjectRegPlan);
			}

			result = this.getSuccessObj();
		} catch (Exception e) {
			logger.error("saveSupervisePlan fail", e);
			result = this.getFailedObj("保存失败.");
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
				}
			}
		}

		this.outputJson(result, response);
		// return "redirct:afterloan/superviseBusinessQuery";
	}

	private void sendMessage(BizProjectRegPlan bizProjectRegPlan) {
		int userId = bizProjectRegPlan.getRegulatoryUserId();
		
		
		BaseClientFactory taskClientFactory = getFactory(BusinessModule.MODUEL_TASK, "TaskService");
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		
		// 任务
				try {
					ProjectService.Client projectClient = (ProjectService.Client) clientFactory.getClient();
					Project project = projectClient.getProjectNameOrNumber(bizProjectRegPlan.getProjectId());
					
					// 获取BIZ_TASK表中的当前用户任务列表
					TaskVo	taskVo = new TaskVo();
					taskVo.setTaskUserName(String.valueOf(userId));
					taskVo.setAllocationType("3"); // 任务类型编号 3: 监管
					taskVo.setAllocationDttm(bizProjectRegPlan.getPlanDt());
					taskVo.setAllocationRefId(bizProjectRegPlan.getPid());
					taskVo.setWorkfloPprojectName(project.getProjectName());
					taskVo.setWorkflowName("监管任务"); 
					taskVo.setTaskName(project.getProjectName()); // 借用流程名称未知来存放项目名称
					taskVo.setTaskStatus(1);
					taskVo.setStatus(1);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					taskVo.setAllocationDttm(sdf.format(new Date()));
					
					TaskService.Client client = (TaskService.Client) taskClientFactory.getClient();
					client.insertTaskVo(taskVo);
					
				} catch (Exception e) {
					logger.error("sendMessage fail", e);
				} finally {
					if (null != taskClientFactory) {
						try {
							taskClientFactory.destroy();
						} catch (ThriftException e) {
							e.printStackTrace();
						}
					}
					
					if (null != clientFactory) {
						try {
							clientFactory.destroy();
						} catch (ThriftException e) {
							e.printStackTrace();
						}
					}
				}
				
		
		/*
		 * // 是否发送 if (bizProjectRegPlan.getIsSms()==1) { //
		 * 手机号码。多个手机号码以英文逗号隔开“,” StringBuilder mobile = new StringBuilder(); for
		 * (int i = 0; i < sysUsers.size(); i++) {
		 * mobile.append(sysUsers.get(i).getPhone()); if ((i + 1) <
		 * sysUsers.size()) { mobile.append(","); } } // 短信内容 Map<String,
		 * Object> map = new HashMap<String, Object>(); map.put("taskNodeName",
		 * bizProjectRegPlan.getRemark()); map.put("sysDate",
		 * DateUtil.format(new Date())); // 短信内容 String msg =
		 * templateParsing(map, "sms.ftl"); if (null != msg) { // 发送短信
		 * sendSms(mobile.toString(), msg); } else { // TODO: 记录数据库异常日志 } } //
		 * 是否发送邮件 if (bizProjectRegPlan.isMail==1) { // 主题 String subject =
		 * "【小贷业务审批】" + bizProjectRegPlan.getRemark(); // 邮件内容 // String
		 * htmlText = // "您有一个审批任务。前一审批人的意见如下："+workflowVo.idea+"请您尽快处理。";
		 * Map<String, Object> map = new HashMap<String, Object>();
		 * //map.put("idea", workflowVo.idea); map.put("sysDate",
		 * DateUtil.format(new Date())); map.put("subject",
		 * bizProjectRegPlan.getRemark()); // 邮件内容 String htmlText =
		 * templateParsing(map, "email.ftl"); for (SysUser u : sysUsers) { //
		 * 邮箱号 String emailAddress = u.getMail(); if (null != htmlText) { //
		 * 发送邮件 sendEmail(emailAddress, subject, htmlText); } else { // TODO:
		 * 记录数据库异常日志 } } }
		 */
	}

	/**
	 * 
	 * @Description: 删除监管
	 * @param pid
	 * @param model
	 * @author: loe.luozhonghua
	 * @date: 2015年3月31日 下午8:21:11
	 */
	@RequestMapping(value = "deleteSupervisePlan")
	public void deleteSupervisePlan(String pids, HttpServletResponse response) {
		AfterLoanSupervisionService.Client client = null;
		BaseClientFactory clientFactory = null;

		Json result = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "AfterLoanSupervisionService");
			client = (AfterLoanSupervisionService.Client) clientFactory.getClient();
			client.deleteSupervisePlan(pids);
			result = this.getSuccessObj();
		} catch (Exception e) {
			logger.error("deleteSupervisePlan fail", e);
			result = this.getFailedObj("删除监管计划失败!");
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					// TODO Auto-generated catch block
				}
			}
		}

		outputJson(result, response);
	}

	/**
	 * 
	 * @Description: 删除监管结果记录
	 * @param pid
	 *            监管结果记录Id
	 * @param projectId
	 *            项目ＩＤ
	 * @param model
	 * @param response
	 * @return
	 * @author: loe.luozhonghua
	 * @date: 2015年4月1日 下午3:06:34
	 */
	@RequestMapping(value = "deleteResultSuperviseHistory")
	public String deleteResultSuperviseHistory(Integer pid, Integer projectId, ModelMap model, HttpServletResponse response) {

		AfterLoanSupervisionService.Client client = null;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "AfterLoanSupervisionService");
			client = (AfterLoanSupervisionService.Client) clientFactory.getClient();
			client.deleteResultSuperviseHistory(pid);
		} catch (Exception e) {
			logger.error("deleteResultSuperviseHistory fail", e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return "afterloan/execuSuperyviseBusiness";
	}

	// 监管任务查询
	@RequestMapping(value = "doSearchSuperviseTast")
	public void doSearchSuperviseTast(SupervisionSearchBean supervisionSearchBean, ModelMap model, HttpServletResponse response) {
		AfterLoanSupervisionService.Client client = null;
		BaseClientFactory clientFactory = null;
		int userId = getShiroUser().getPid();
		supervisionSearchBean.setUserId(userId);
		/*
		 * supervisionSearchBean.setPage(page);
		 * supervisionSearchBean.setRows(rows);
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "AfterLoanSupervisionService");
			client = (AfterLoanSupervisionService.Client) clientFactory.getClient();
			map.put("total", client.getTotalSupervisionTask(supervisionSearchBean));
			map.put("rows", client.supervisionTaskList(supervisionSearchBean));
		} catch (Exception e) {
			logger.error("doSearchSuperviseTast fail", e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(map, response);
	}

	/**
	 * 
	 * @Description: 跳转 新增监管结果页面
	 * @param regulatoryPlayId
	 *            监管计划ＩＤ
	 * @param model
	 * @return
	 * @author: loe.luozhonghua
	 * @date: 2015年3月27日 上午9:59:59
	 */
	@RequestMapping(value = "goCreateResultSuperviseHistory")
	public String goCreateResultSuperviseHistory(@Param(value = "regulatoryPlanId") Integer regulatoryPlanId, ModelMap model) {
		model.addAttribute("regulatoryPlanId", regulatoryPlanId);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		model.put("nowDate", sdf.format(new Date()));
		getSysUserId(model);
		return "afterloan/createResultSuperviseHistory";
	}

	void getSysUserId(ModelMap model) {
		SysUserService.Client client = null;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserService");
			client = (SysUserService.Client) clientFactory.getClient();

			SysUser s = client.querySysUserByName(SecurityUtils.getSubject().getSession().getAttribute(Constants.SHIRO_USER).toString());
			model.addAttribute("userId", s.getPid());
		} catch (Exception e) {
			logger.error("getSysUserId fail", e);
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
	 * @Description: 编辑监管结果
	 * @param pid
	 *            监管结果id
	 * @param projectId
	 * @param model
	 * @return
	 * @author: loe.luozhonghua
	 * @date: 2015年4月1日 上午10:58:36
	 */
	@RequestMapping(value = "goUpdateResultSuperviseHistory")
	public String goUpdateResultSuperviseHistory(@Param(value = "pid") Integer pid, @Param(value = "projectId") Integer projectId,int planId, ModelMap model) {

		AfterLoanSupervisionService.Client client = null;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "AfterLoanSupervisionService");
			client = (AfterLoanSupervisionService.Client) clientFactory.getClient();
			BizProjectRegHistory r = client.getBizProjectRegHistory(pid);
			model.addAttribute("projectId", projectId);
			model.addAttribute("planId", planId);
			
			model.addAttribute("r", r);
			getSysUserId(model);
		} catch (Exception e) {
			logger.error("goUpdateResultSuperviseHistory fail", e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return "afterloan/editResultSuperviseHistory";
	}

	/**
	 * 
	 * @Description: 保存监管结果
	 * @param bizProjectRegHistory
	 * @return
	 * @author: loe.luozhonghua
	 * @date: 2015年3月27日 上午11:25:35
	 */
	@RequestMapping(value = "createBizProjectRegHistory")
	public String createBizProjectRegHistory(BizProjectRegHistory bizProjectRegHistory, ModelMap model) {

		AfterLoanSupervisionService.Client client = null;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "AfterLoanSupervisionService");
			client = (AfterLoanSupervisionService.Client) clientFactory.getClient();
			if (bizProjectRegHistory.getPid() != 0) {
				client.updateBizProjectRegHistory(bizProjectRegHistory);
			} else {
				client.createBizProjectRegHistory(bizProjectRegHistory);
			}
		} catch (Exception e) {
			logger.error("createBizProjectRegHistory fail", e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
				}
			}
		}
		return "afterloan/resultSuperyviseBusiness";
	}

	/**
	 * 
	 * @Description:跳轉监管结果查询
	 * @param model
	 * @return
	 * @author: loe.luozhonghua
	 * @date: 2015年3月25日 下午7:07:08
	 */
	@RequestMapping(value = "resultSuperyviseBusiness")
	public String resultSuperyviseBusiness(SupervisionResultSearch supervisionResultSearch, ModelMap model, HttpServletResponse response) {
		return "afterloan/resultSuperyviseBusiness";
	}

	/**
	 * 
	 * @Description: 根据条件获取监管结果
	 * @param supervisionResultSearch
	 * @param model
	 * @param response
	 * @author: loe.luozhonghua
	 * @date: 2015年3月30日 下午2:30:27
	 */
	@RequestMapping(value = "getResultSuperyviseBusiness")
	public void getResultSuperyviseBusiness(SupervisionResultSearch supervisionResultSearch, ModelMap model, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		{
			AfterLoanSupervisionService.Client client = null;
			BaseClientFactory clientFactory = null;

			try {
				clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "AfterLoanSupervisionService");
				client = (AfterLoanSupervisionService.Client) clientFactory.getClient();

				map.put("total", client.getTotalSupervisionResult(supervisionResultSearch));
				map.put("rows", client.getSupervisionResultList(supervisionResultSearch));

			} catch (Exception e) {
				logger.error("getResultSuperyviseBusiness fail", e);
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
		outputJson(map, response);
	}

	/**
	 * 
	 * @Description: 执行监管 页面
	 * @param model
	 * @return
	 * @author: loe.luozhonghua
	 * @date: 2015年3月26日 下午5:16:55
	 */
	@RequestMapping(value = "execuSuperyviseBusiness")
	public String execuSuperyviseBusiness(int planId, ModelMap model, HttpServletRequest request) {

		BaseClientFactory clientFactory = null;
		BaseClientFactory c = null;
		try {
			
			// 打开监管页面，把等待监管状态是修改为监管中
			updateBizProjectRegPlanStart(planId);
			
			clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "AfterLoanSupervisionService");
			AfterLoanSupervisionService.Client client = (AfterLoanSupervisionService.Client) clientFactory.getClient();
			// 1查询监管计划
			ResultBizProjectRegPlan plan = getBizProJectRegPlan(planId, client); // pid
			int projectId = plan.getProjectId();
			// 获取项目信息
			List<ProjectInfo> project = client.getProjectData(projectId);
			model.addAttribute("project", project.get(0));

			model.put("plan", plan);
			model.put("projectId", projectId);
			model.put("planId", planId);
			

			// 当前计划监管记录
			SupervisionResultSearch search = new SupervisionResultSearch();
			search.setPlanId(planId);
			model.addAttribute("historyList", client.getRegulatoryRecordList(search));

			// loadId 贷款id
			c = getFactory(BusinessModule.MODUEL_AFTERLOAN, "MisapprProcessService");
			MisapprProcessService.Client mclient = (MisapprProcessService.Client) c.getClient();
			if (mclient != null && mclient.getLoanId(projectId) != 0) {
				model.addAttribute("loanId", mclient.getLoanId(projectId));
			}

			c = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");
			FinanceService.Client ct = (FinanceService.Client) c.getClient();
			CustArrearsView custArrears = ct.getCustArrearsbyProjectView(projectId);
			// 格式化页面要显示的数据
			this.setCustArrearsView(custArrears);
			model.addAttribute("custArrears", custArrears);
			

		} catch (Exception e) {
			logger.error("execuSuperyviseBusiness fail", e);
		} finally {
			if ( null!= clientFactory ) {
				try {
					clientFactory.destroy();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if ( null!= c ) {
				try {
					c.destroy();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		// 历史遗留问题，上传资料类型共用同一个页面loan_add_datum.jsp,要指明当前是哪个页面跳转过去的(1:监管资料
		// 2：违约处理资料)
		model.put("fileType", 1);
		boolean readOnly = null == request.getParameter("readOnly") ? false : true;
		model.put("readOnly", readOnly);
		return "afterloan/execuSuperyviseBusiness";
	}
	
	
	/**
	 * 打开监管页面，把等待监管状态是修改为监管中
	  * @Description: TODO
	  * @param planId
	  * @author: qiancong.Xian
	  * @date: 2015年5月12日 上午10:45:23
	 */
	private void updateBizProjectRegPlanStart(int planId)
	{
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "AfterLoanSupervisionService");
			AfterLoanSupervisionService.Client client = (AfterLoanSupervisionService.Client) clientFactory.getClient();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String planBeginDt = sdf.format(new Date());
			
			BizProjectRegPlan plan = new BizProjectRegPlan(); // pid
			plan.setPid(planId);
			plan.setAssigneUserId(this.getShiroUser().getPid());
			plan.setPlanBeginDt(planBeginDt);

			client.updateBizProjectRegPlanStart(plan);
		} catch (Exception e) {
			logger.error("updateBizProjectRegPlanStart fail", e);
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

	List<BizProjectRegHistory> getBizProjectRegHistoryList(AfterLoanSupervisionService.Client client, ResultBizProjectRegPlan r) throws TException {
		return client.getBizProjectRegHistoryList(r.getPid());
	}

	private ResultBizProjectRegPlan getBizProJectRegPlan(Integer pid, AfterLoanSupervisionService.Client client) throws ThriftServiceException, TException {
		return client.getSupervisionTask(pid);
	}

	/**
	 * 
	 * @Description: 更新监管计划监管状态
	 * @param pid
	 * @return
	 * @author: loe.luozhonghua
	 * @date: 2015年3月28日 下午4:31:18
	 */
	@RequestMapping(value = "updateBizProjectRegPlanRegulatoryStatus")
	public void updateBizProjectRegPlanRegulatoryStatus(BizProjectRegPlan bizProjectRegPlan, HttpServletResponse response) {
		BaseClientFactory clientFactory = null;
		Json result = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "AfterLoanSupervisionService");
			AfterLoanSupervisionService.Client client = (AfterLoanSupervisionService.Client) clientFactory.getClient();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String planBeginDt = sdf.format(new Date());
			bizProjectRegPlan.setAssigneUserId(this.getShiroUser().getPid());
			bizProjectRegPlan.setPlanBeginDt(planBeginDt);

			client.updateBizProjectRegPlanRegulatoryStatus(bizProjectRegPlan);
			result = this.getSuccessObj();
		} catch (Exception e) {
			logger.error("updateBizProjectRegPlanRegulatoryStatus fail", e);
			result = this.getFailedObj("保存失败!");
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}

		this.outputJson(result, response);
	}

	/**
	 * 
	 * @Description: 项目监管记录查看
	 * @param pid
	 *            项目ID--projectId
	 * @param model
	 * @return
	 * @author: loe.luozhonghua
	 * @date: 2015年3月28日 上午11:48:45
	 */
	@RequestMapping(value = "viewResultSuperviseHitory")
	public String viewResultSuperviseHitory(@Param(value = "projectId") Integer projectId, ModelMap model) {
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "AfterLoanSupervisionService");
			AfterLoanSupervisionService.Client client = (AfterLoanSupervisionService.Client) clientFactory.getClient();

			ResultBizProjectRegPlan r = client.getProject(projectId);
			model.addAttribute("projectName", r.getProjectName());
			model.addAttribute("projectNumber", r.getProjectNumber());

			SupervisionResultSearch search = new SupervisionResultSearch();
			search.setProjectId(projectId);

			model.addAttribute("supervisionTaskList", client.getRegulatoryRecordList(search));
		} catch (Exception e) {
			logger.error("viewResultSuperviseHitory fail", e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return "afterloan/viewResultSuperviseHitory";
	}

	/**
	 * @Description: 获取文件信息列表
	 * @param projectId
	 *            项目ＩＤ
	 * @param page
	 *            　　页数
	 * @param rows
	 *            　　行数
	 * @param response
	 * @author: loe.luozhonghua
	 * @date: 2015年4月8日 上午9:44:02
	 */
	@RequestMapping(value = "getFileInfoList")
	@ResponseBody
	public void getFileInfoList(@RequestParam("refId") int refId, @RequestParam("page") int page, @RequestParam("rows") int rows, int fileType, HttpServletResponse response) {

		BaseClientFactory c = null;
		List<FileInfo> list = new ArrayList<FileInfo>();
		FileInfo dinfo = new FileInfo();
		dinfo.setRefId(refId);
		dinfo.setPage(page);
		dinfo.setRows(rows);
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			int total = 0;

			// 监管
			if (fileType == 1) {
				c = getFactory(BusinessModule.MODUEL_AFTERLOAN, "AfterLoanSupervisionService");
				AfterLoanSupervisionService.Client client = (AfterLoanSupervisionService.Client) c.getClient();
				list = client.getFileDataList(dinfo);
				total = client.getFileDataTotal(dinfo);
			}
			// 违约
			else if (fileType == 2) {
				c = getFactory(BusinessModule.MODUEL_AFTERLOAN, "MisapprProcessService");
				MisapprProcessService.Client client = (MisapprProcessService.Client) c.getClient();

				list = client.getFileDataList(dinfo);
				total = client.getFileDataTotal(dinfo);
			}

			map.put("total", total);
			map.put("rows", list);

			if (null != list && list.size() > 0) {
				// 历史遗留问题，上传资料类型共用同一个页面loan_add_datum.jsp,要指明当前是哪个页面跳转过去的(1:监管资料
				// 2：违约处理资料)
				String filePropertyName = "其他资料";
				if (fileType == 1) {
					filePropertyName = "监管资料";
				} else if (fileType == 2) {
					filePropertyName = "违约处理资料";
				}

				for (FileInfo info : list) {
					info.setFilePropertyName(filePropertyName);
				}
			}

			outputJson(map, response);
		} catch (Exception e) {
				logger.error("查询资料列表出错",e);
			e.printStackTrace();
		} finally {
			if (c != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * @Description: 上传文件选择对话框
	 * @param fileInfo
	 * @param response
	 * @param request
	 * @author: loe.luozhonghua
	 * @date: 2015年4月9日 上午10:12:30
	 */
	@RequestMapping(value = "showUpdateFileDiog")
	public String showUpdateFileDiog(int projectId, int fileType, int refId, int fileId, ModelMap model) {
		model.put("projectId", projectId);
		model.put("fileType", fileType);
		model.put("refId", refId);

		model.put("fileId", fileId);

		return "afterloan/uploadFileSelect";
	}

	/**
	 * 
	 * @Description: 上传文件
	 * @param fileInfo
	 * @param response
	 * @param request
	 * @author: loe.luozhonghua
	 * @date: 2015年4月9日 上午10:12:30
	 */
	@RequestMapping(value = "addOrupdate", method = RequestMethod.POST)
	@ResponseBody
	public void addOrupdate(FileInfo fileInfo, HttpServletResponse response, HttpServletRequest request, int fileType) {
		int fileSize = 0;
		BaseClientFactory c = null;
		BaseClientFactory bizFileClientFactory = null;
		// 文件信息BIZ_FILE
		BizFile bizFile = new BizFile();
		Json result = null;
		try {

			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				Map<String, Object> map = new HashMap<String, Object>();
				// 资料上传得到的信息集合
				map = FileUtil.processFileUpload(request, response, BusinessModule.MODUEL_AFTERLOAN, getUploadFilePath(), getFileSize(), getFileDateDirectory(), getUploadFileType());

				// 设置填充参数
				setFileInfo(response, fileSize, fileInfo, bizFile, map);

				if (bizFile.getFileSize() == 0) {
					result = this.getFailedObj("空文件!");
					outputJson(result, response);
					return;
				}
				if (StringUtils.isEmpty(bizFile.getFileName()) || StringUtils.isEmpty(bizFile.getFileType())) {
					result = this.getFailedObj("空文件!");
					outputJson(result, response);
					return;
				}

				// 监管
				if (fileType == 1) {
					c = getFactory(BusinessModule.MODUEL_AFTERLOAN, "AfterLoanSupervisionService");
					AfterLoanSupervisionService.Client client = (AfterLoanSupervisionService.Client) c.getClient();
					client.saveOrUpdateFileData(fileInfo, bizFile);
				}
				// 违约
				else if (fileType == 2) {
					c = getFactory(BusinessModule.MODUEL_AFTERLOAN, "MisapprProcessService");
					bizFileClientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "BizFileService");
					MisapprProcessService.Client dataService = (MisapprProcessService.Client) c.getClient();
					dataService.saveOrUpdateFileData(fileInfo, bizFile);
				}

				result = this.getSuccessObj();
			}

		} catch (Exception e) {
			logger.error("上传资料列表出错" ,e);
			result = this.getFailedObj("文件上传失败or文件格式不合法");
		} finally {
			if (c != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
			if (bizFileClientFactory != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}

		}
		outputJson(result, response);
	}

	/**
	 * 设置上传文件的对象和信息
	 * 
	 * @param response
	 * @param fileSize
	 * @param fileName
	 * @param fileType
	 * @param dataInfo
	 * @param bizFile
	 * @param map
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @author: qiancong.Xian
	 * @date: 2015年5月7日 下午2:32:12
	 */
	private void setFileInfo(HttpServletResponse response, int fileSize, FileInfo dataInfo, BizFile bizFile, Map<String, Object> map) throws UnsupportedEncodingException, IOException {
		// 拿到当前用户
		ShiroUser shiroUser = getShiroUser();
		int userId = shiroUser.getPid();
		dataInfo.setUserId(userId);
		List items = (List) map.get("items");
		for (int j = 0; j < items.size(); j++) {
			FileItem item = (FileItem) items.get(j);
			// 获取其他信息
			if (item.isFormField()) {
				if (item.getFieldName().equals("dataId") && null != item.getFieldName()) {
					dataInfo.setDataId(getIntValue((ParseDocAndDocxUtil.getStringCode(item) == null || ParseDocAndDocxUtil.getStringCode(item).equals("") ? "0" : ParseDocAndDocxUtil.getStringCode(item)), 0));
				} else if (item.getFieldName().equals("projectId") && null != item.getFieldName()) {
					dataInfo.setProjectId(getIntValue(ParseDocAndDocxUtil.getStringCode(item), 0));
				} else if (item.getFieldName().equals("dataFileProperty") && null != item.getFieldName()) {
					dataInfo.setFileProperty(getIntValue(ParseDocAndDocxUtil.getStringCode(item), 0));
				} else if (item.getFieldName().equals("datafileDesc") && null != item.getFieldName()) {
					dataInfo.setFileDesc(ParseDocAndDocxUtil.getStringCode(item));
				} else if (item.getFieldName().equals("refId") && null != item.getFieldName()) {
					dataInfo.setRefId(getIntValue(ParseDocAndDocxUtil.getStringCode(item), 0));
				} else if (item.getFieldName().equals("remark") && null != item.getFieldName()) {
					bizFile.setRemark(ParseDocAndDocxUtil.getStringCode(item));
				}

				else if (item.getFieldName().equals("fileId") && null != item.getFieldName()) {
					dataInfo.setFileId(getIntValue((ParseDocAndDocxUtil.getStringCode(item) == null || ParseDocAndDocxUtil.getStringCode(item).equals("") ? "0" : ParseDocAndDocxUtil.getStringCode(item)), 0));
				}
			} else {
				if ("file2".equals(item.getFieldName())) {
					// 获取文件上传的信息
					bizFile.setUploadUserId(userId);// 上传用户
					fileSize = (int) item.getSize();
					bizFile.setFileSize(fileSize);
					String fileFullName = item.getName().toLowerCase();
					int dotLocation = fileFullName.lastIndexOf(".");
					String fileName = fileFullName.substring(0, dotLocation);
					String fileType = fileFullName.substring(dotLocation).substring(1);
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
	}

	/**
	 * 
	 * @Description: 删除上传文件(违约资料)
	 * @param pidArray
	 * @param response
	 * @author: loe.luozhonghua
	 * @date: 2015年4月10日 下午1:53:04
	 */
	@RequestMapping(value = "delData")
	@ResponseBody
	public void delData(int dataId, int fileId, int fileType, HttpServletResponse response) {
		Json result = null;
		BaseClientFactory c = null;
		try {

			// 监管
			if (fileType == 1) {
				c = getFactory(BusinessModule.MODUEL_AFTERLOAN, "AfterLoanSupervisionService");
				AfterLoanSupervisionService.Client client = (AfterLoanSupervisionService.Client) c.getClient();
				client.deleteFileData(dataId, fileId);
			}
			// 违约
			else if (fileType == 2) {
				c = getFactory(BusinessModule.MODUEL_AFTERLOAN, "MisapprProcessService");
				MisapprProcessService.Client dataService = (MisapprProcessService.Client) c.getClient();
				dataService.deleteFileData(dataId, fileId);
			}

			result = this.getSuccessObj();
		} catch (Exception e) {
				logger.error("删除资料列表出错" , e);
			result = this.getFailedObj("删除资料失败");
		} finally {
			if (c != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}

		outputJson(result, response);
	}

	/**
	 * 
	 * @Description: 下载文件
	 * @param request
	 * @param response
	 * @param path
	 * @author: loe.luozhonghua
	 * @date: 2015年4月10日 下午1:53:38
	 */
	@RequestMapping(value = "downloadData")
	@ResponseBody
	public void downloadData(HttpServletRequest request, HttpServletResponse response, String path) {
		try {
			FileDownload.downloadLocal(response, request, path);
		} catch (IOException e) {
				logger.error("下载资料文件失败出错" + e.getMessage());
		}
	}

	/**
	 * 坏账呆账处理
	 * 
	 * @param limitId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "listBadDebt")
	public String getBadDebt(@RequestParam(value = "limitId", required = false) Integer limitId, ModelMap model) throws Exception {
		return "afterloan/badDebtQuery";
	}

	/**
	 * 坏账呆账处理
	 * 
	 * @param badDebtBeasCriteria
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "getBadDebtlist")
	public void getBadDebtlist(BadDebtBeasCriteria badDebtBeasCriteria, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "MisapprProcessService");
		try {
			MisapprProcessService.Client client = (MisapprProcessService.Client) clientFactory.getClient();
			List<BadDebtBeas> list = client.getBadDebt(badDebtBeasCriteria);
			outputJson(list, response);
		} catch (Exception e) {
			logger.error("viewResultSuperviseHitory fail", e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
				}
			}
		}
		// 输出
	}

	/**
	 * 
	 * @Description: 违约处理查询
	 * @param projectId
	 * @param model
	 * @return
	 * @throws Exception
	 * @author: loe.luozhonghua
	 * @date: 2015年4月2日 下午7:52:05
	 */
	@RequestMapping(value = "breachDispose")
	public String breachDispose(@RequestParam(value = "projectId", required = false) Integer projectId, ModelMap model) throws Exception {
		return "afterloan/breachDispose";
	}

	/**
	 * @Description: 打开违约处理申请
	 * @param projectId
	 *            项目ID
	 * @param model
	 * @return
	 * @throws Exception
	 * @author: loe.luozhonghua
	 * @date: 2015年4月1日 下午8:15:11
	 */
	@RequestMapping(value = "listBreachDispose")
	public String listBreachDispose(int projectId, ModelMap model, HttpServletRequest request) throws Exception {
		breachAndBadDebtCommon(projectId, model, request);
		return "afterloan/breachDisposeList";
	}

	 /**
	 * 新建坏账处理页面
	  * @param projectId
	  * @param model
	  * @param request
	  * @return
	  * @throws Exception
	  * @author: qiancong.Xian
	  * @date: 2015年6月15日 下午2:11:47
	 */
	@RequestMapping(value = "newBadDebt")
	public String newBadDebt(int projectId, ModelMap model, HttpServletRequest request) throws Exception {
		breachAndBadDebtCommon(projectId, model, request);
		return "afterloan/newBadDebt";
	}
	
	private void breachAndBadDebtCommon(int projectId, ModelMap model, HttpServletRequest request) {
		// 违约ID
		String param = request.getParameter("param");
		int violationId = this.getIntValue(request.getParameter("violationId"), 0);
		// 如果没有从页面传递主键，看看param里面的refId中没有
		if (violationId == 0 && null != param) {
			try {
				param = param.startsWith("{") ? param : "{" + param + "}";
				JSONObject json = JSONObject.parseObject(param);
				violationId = this.getIntValue(json.get("refId").toString(), 0);
			} catch (Exception e) {
				logger.error("breachAndBadDebtCommon fail", e);
			}
		}

		model.put("refId", violationId);
		// 当前是否可以修改
		boolean modifyAble = false;
		// 是否存在
		BizProjectViolation vio = new BizProjectViolation();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "MisapprProcessService");
		BaseClientFactory cF = null;
		try {
			MisapprProcessService.Client client = (MisapprProcessService.Client) clientFactory.getClient();

			cF = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");
			FinanceService.Client ct = (FinanceService.Client) cF.getClient();
			CustArrearsView custArrears = ct.getCustArrearsbyProjectView(projectId);
			// 格式化页面要显示的数据
			this.setCustArrearsView(custArrears);
			model.addAttribute("custArrears", custArrears);
			if (client.getLoanId(projectId) != 0) {
				Integer pid = client.getLoanId(projectId);
				model.addAttribute("loadId", pid);
				// 跳转到收息表页面,顺带处理一下违约处理
				getLoanCaculateList(pid,projectId, custArrears.getReceivablePrincipal(), vio,model);
				handReconciliation(pid, model);
			} else {
				model.addAttribute("error", "0");
				logger.error("贷款金额为零");
			}

			if (violationId > 0) {
				vio = client.getBizProjectViolationById(violationId);
			}
			
			// 当前是否可以修改
			modifyAble = vio.getRequestStatus() < 2; // 如果大于1了，说明已经在走流程了
			
		} catch (Exception e) {
			logger.error("breachAndBadDebtCommon fail", e);
		} finally {
			if (clientFactory != null || cF != null) {
				try {
					clientFactory.destroy();
					cF.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}

		model.put("modifyAble", modifyAble);
		// 历史遗留问题，上传资料类型共用同一个页面loan_add_datum.jsp,要指明当前是哪个页面跳转过去的(1:监管资料
		// 2：违约处理资料)
		model.put("fileType", 2);

		// 显示工作流处理部分
		boolean showFlow = false;
		if (null != param) {
			showFlow = true;
		}
		// 如果是新的流程开始，默认打开
		showFlow = null != request.getParameter("newFlow") ? true : showFlow;

		// 监管结果ID
		model.put("regHistoryId", request.getParameter("regHistoryId"));
		model.addAttribute("projectId", projectId); // 此位置不可移动
		// 等待申请状态
		if (vio.getRequestStatus() == 1) {
			showFlow = true;
		}
		model.put("showFlow", showFlow);
		model.addAttribute("vio", vio);
		
		
		int badDebtId = vio.getBadId();
		setBadDebtInfo(badDebtId, model);
	}
	

	/**
	 *  在违约中设置坏账的相关信息
	  * @param badDebtId
	  * @param model
	  * @author: qiancong.Xian
	  * @date: 2015年5月12日 下午4:35:33
	 */
	private void setBadDebtInfo(int badDebtId,ModelMap model)
	{
		BadDebtBean bean = null;
		BaseClientFactory clientFactory = null;
		//查询账户信息
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "MisapprProcessService");
			MisapprProcessService.Client client = (MisapprProcessService.Client) clientFactory.getClient();
			bean = client.getBadDebtBean(badDebtId);
		} 
		catch(TException e)
		{
			// 如果是TException这种类型的错误，是返回null，不需要管
		}
		catch (Exception e) {
			logger.error("setBadDebtInfo fail", e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		
		if(null == bean)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			bean = new BadDebtBean();
			bean.setAsOfDT(sdf.format(new Date()));
			
		}	
		model.put("badDebtBean", bean);
	}
	
	/**
	 * 
	 * @Description: 更新还款计划表
	 * @param projectId
	 * @param proVioId
	 * @param model
	 * @param response
	 * @author: loe.luozhonghua
	 * @date: 2015年4月16日 下午2:32:05
	 */
	@RequestMapping("/advmakeRepayMent")
	public void advmakeRepayMent(int projectId, int proVioId, ModelMap model, HttpServletResponse response) {
		BaseClientFactory clientFactory = null;

		BaseClientFactory cFt = null;
		BaseClientFactory pvclientFactory = null;
		Loan loan = null;
		RepaymentLoanInfo repayment = null;
		List<RepaymentPlanBaseDTO> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			loan = client.getLoanInfobyProjectId(projectId);

			cFt = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client clt = (ProjectService.Client) cFt.getClient();
			repayment = clt.getRepaymentLoanInfo(loan.getPid());

			pvclientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "MisapprProcessService");
			MisapprProcessService.Client pvclient = (MisapprProcessService.Client) pvclientFactory.getClient();
			BizProjectViolation bizProjectViolation = pvclient.getBizProjectViolationById(proVioId);

			CalcOperDto calcOperDto = new CalcOperDto();
			calcOperDto.setInterestRate(bizProjectViolation.getViolationProportion()); // 违约金比率
			calcOperDto.setPid(proVioId);
			calcOperDto.setRepayType(5);
			calcOperDto.setOperAmt(bizProjectViolation.loanAmt);  // 贷款剩余金额
			String opterDate = bizProjectViolation.violationDt.contains(" ")?bizProjectViolation.violationDt.split(" ")[0]:bizProjectViolation.violationDt;
			calcOperDto.setOperRepayDt(opterDate);
			int userId = getShiroUser().getPid();
			list = client.makeRepayMent(loan, userId, calcOperDto);
		} catch (Exception e) {
			map.put("size", list == null ? 0 : list.size());
			logger.error("advmakeRepayMent",e);
		} finally {
			if (clientFactory != null || pvclientFactory != null || cFt != null) {
				try {
					clientFactory.destroy();
					pvclientFactory.destroy();
					cFt.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}

		map.put("repayment", repayment);
		map.put("loanId", loan.getPid());
		map.put("size", list == null ? 0 : list.size());
		outputJson(map, response);
		// return "beforeloan/loan_calc";
	}

	/**
	 * 跳转到收息表页面,顺带处理一下违约处理
	  * @param loanId   贷款ID
	  * @param projectId  项目ID
	  * @param receivablePrincipal  贷款总金额
	  * @param model
	  * @author: qiancong.Xian
	  * @date: 2015年5月12日 下午2:20:16
	 */
	void getLoanCaculateList(Integer loanId, int projectId, double receivablePrincipal,BizProjectViolation vio, ModelMap model) {
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			RepaymentLoanInfo repayment = client.getRepaymentLoanInfo(loanId);
			// 贷款信息
			Loan loan = client.getLoanInfobyProjectId(projectId);
			// 违约金比例
			double liqDmgProportion =  loan.getLiqDmgProportion();
			// 违约金
			double  liqDmg = receivablePrincipal*liqDmgProportion/100;
			// 违约金逾期费率
			double overdueFineInterest = loan.getOverdueFineInterest();
			
			vio.setLoanAmt(receivablePrincipal); // 贷款总金额
			vio.setViolationProportion(liqDmgProportion); // 违约金比例
			vio.setViolationAmt(liqDmg);// 应收违约金
			vio.setViolationOtInterest(overdueFineInterest);// 违约金逾期利率；
			
			
			if (null != repayment) {
				model.put("otherCostName", repayment.getOtherCostName());
			}
			model.put("loanId", loanId);
			model.put("showNew",true);
		} catch (Exception e) {
				logger.error("查询还款计划表贷款信息:", e);
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
	 * @Description: 项目基本信息
	 * @param loanId
	 * @param model
	 * @throws Exception
	 * @author: loe.luozhonghua
	 * @date: 2015年4月2日 下午2:25:12
	 */
	void handReconciliation(Integer loanId, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(serviceModuel, "FinanceService");
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			if (loanId != 0) {
				FinanceReceivablesView bean = client.getFinanceReceivablesView(loanId);
				model.put("bean", bean);
			}
		} catch (ThriftServiceException e) {
			logger.error("loanId为零 ");
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("handReconciliation",e);
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
	 * @Description: 保存违约
	 * @param model
	 * @param bizProjectViolation
	 * @return
	 * @author: loe.luozhonghua
	 * @date: 2015年4月2日 下午4:10:36
	 */
	@RequestMapping(value = "createBizProjectViolation")
	public void createBizProjectViolation(ModelMap model, BizProjectViolation bizProjectViolation,BadDebtBean badDebtBean,int loanId, HttpServletResponse response) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "MisapprProcessService");
		String message = "success";
		Json result = null;
		try {
			MisapprProcessService.Client client = (MisapprProcessService.Client) clientFactory.getClient();
			int pid = bizProjectViolation.getPid();
			
			calculatBadBebt(bizProjectViolation, badDebtBean,loanId);
			if (pid > 0) {
				client.updateBizProjectViolation(bizProjectViolation,badDebtBean);
				
			} else {
				pid = client.createBizProjectViolation(bizProjectViolation,badDebtBean);
				
			}

			result = this.getSuccessObj(String.valueOf(pid), "保存成功");
		} catch (Exception e) {
			logger.error("createBizProjectViolation fail",e);
			result = this.getFailedObj("保存失败");
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
				}
			}
		}
		outputJson(result, response);
	}

	private void calculatBadBebt(BizProjectViolation bizProjectViolation,BadDebtBean badDebtBean,int loanId)
	{
		badDebtBean.setStatus(0);// 呆账坏账只有流程走完后才能生效，所以这样重新设置一下
		badDebtBean.setPid(bizProjectViolation.getBadId());
		// 非新增状态下，重新设置一下项目终止状态
		if(bizProjectViolation.getPid()>0)
		{
			bizProjectViolation.setIsTermInAtion(bizProjectViolation.getBadId()>0?1:0);
		}	
	    BaseClientFactory clientFactory = null;
		//  输出结果
		//查询账户信息
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
            // 坏账计算后的数据
			List<BadDebtDataBean> list = client.getBadDebtDataBean(loanId);
			   
			for(BadDebtDataBean bean:list)
			{
				// 坏账后应收本金、管理费、利息，其他费用
				if(bean.getOperType() == FinanceTypeEnum.TYPE_3.getType() || bean.getOperType() == FinanceTypeEnum.TYPE_12.getType() || bean.getOperType() == FinanceTypeEnum.TYPE_13.getType() || bean.getOperType() == FinanceTypeEnum.TYPE_14.getType())
				{
					// 坏账后应收总额
					badDebtBean.setBadShouldAmt(badDebtBean.getBadShouldAmt()+bean.getTotalAmt());
				}
				// 坏账后损失金额
				if(bean.getOperType() == FinanceTypeEnum.TYPE_11.getType())
                {
					badDebtBean.setBadLossAmt(bean.getTotalAmt());
				}
			}
			
			
		} catch (Exception e) {
			logger.error("获取呆账坏账结算后数据失败", e);
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
	 * 违约处理列表
	 * @param misapprProcessCriteria
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "getBreachDispose")
	public void getBreachDispose(BreachDisposeCriteria breachDisposeCriteria, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "MisapprProcessService");
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			MisapprProcessService.Client client = (MisapprProcessService.Client) clientFactory.getClient();
			List<BreachDisposeBeas> list = client.getBreachDispose(breachDisposeCriteria);
			int total = client.getBreachDisposeTotal(breachDisposeCriteria);
			map.put("rows",list);
			map.put("total", total);
		} catch (Exception e) {
			logger.error("getBreachDispose fail",e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(map, response);
	}

	/**
	  * @Description: 删除违约处理 根据pid
	  * @param pid
	  * @param response
	  * @throws Exception
	  * @author: JingYu.Dai
	  * @date: 2015年5月14日 下午5:57:20
	 */
	@RequestMapping(value = "deleteBreachDispose")
	public void deleteBreachDispose(int pid,int projectId, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "MisapprProcessService");
		int index = 0;
		try {
			MisapprProcessService.Client client = (MisapprProcessService.Client) clientFactory.getClient();
			index = client.deleteBreachDispose(pid,projectId);
		} catch (Exception e) {
			logger.error("deleteBreachDispose fail",e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(index, response);
	}
	
	/**
	 * 贷后管理催收记录
	 */
	@RequestMapping(value = "listCollectingRecord")
	public String getCollectingRecord(@RequestParam(value = "limitId", required = false) Integer limitId, ModelMap model) throws Exception {
		return "afterloan/collectingRecord";
	}

	/**
	 * 
	 * @param collectingRecord
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "getCollectingRecord")
	public void getCollectingRecord(CollectionRecord collectingRecord, HttpServletResponse response) throws Exception {
		List<CollectionRecord> list = new ArrayList<CollectionRecord>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "RepaymentCollectionService");
		try {
			RepaymentCollectionService.Client client = (RepaymentCollectionService.Client) clientFactory.getClient();
			list = client.getCollectionRecord(collectingRecord);
		} catch (Exception e) {
			logger.error("getCollectingRecord fail",e);
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
	 * 贷后管理催收详情
	 */
	@RequestMapping(value = "listCollectingDetail")
	public String getCollectingDetail(@RequestParam(value = "limitId", required = false) Integer limitId, ModelMap model) throws Exception {
		return "afterloan/collectingDetail";
	}

	/***
	 * 还款催收查询-根据项目id查询对应的客户信息
	 * 
	 * @Description: TODO
	 * @param projectId
	 * @param response
	 * @throws Exception
	 * @author: mayinmei
	 * @date: 2015年3月26日 下午2:41:58
	 */
	@RequestMapping(value = "getCollectionProjectCustomer")
	public String getCollectionProjectCustomer(@RequestParam(value = "projectId") int projectId, HttpServletResponse response, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "RepaymentCollectionService");
		CollectionCustomer collectionCustomer = null;
		try {
			// 查询客户信息
			RepaymentCollectionService.Client client = (RepaymentCollectionService.Client) clientFactory.getClient();
			collectionCustomer = client.getCollectionCustomer(projectId);
		} catch (Exception e) {
			logger.error("getCollectionProjectCustomer fail",e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		model.put("acct", collectionCustomer);
		return "afterloan/collectingDetail";
	}

	/**
	 * 查询客户联系人信息
	 */
	@RequestMapping(value = "getCollectionCusContact")
	public void getCollectionCusContact(@RequestParam(value = "acctId") int acctId, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "RepaymentCollectionService");
		List<CollectionCusComContact> list = null;
		try {
			RepaymentCollectionService.Client client = (RepaymentCollectionService.Client) clientFactory.getClient();
			list = client.getCollectionCusComContact(acctId);
		} catch (Exception e) {
			logger.error("getCollectionCusContact fail",e);
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
	 * @Description: 跳转到贷款申请页面
	 * @author: Cai.Qing
	 * @date: 2015年1月7日 上午11:35:45
	 */
	@RequestMapping(value = "addNavigation")
	public String addNavigation() {
		return "beforeloan/loan_tab";
	}

	/**
	 * 贷后管理催收专员任务分配
	 */
	@RequestMapping(value = "listMissionDistribution")
	public String getMissionDistribution(@RequestParam(value = "limitId", required = false) Integer limitId, ModelMap model) throws Exception {
		return "afterloan/repaymentCollection";
	}

	/**
	 * 还款催收计划列表查询
	 */
	@RequestMapping(value = "getAssignmentDistribution")
	public void getAssignmentDistribution(AssignmentDistributionSearch assignmentDistributionSearch, HttpServletResponse response) throws Exception {
		List<AssignmentDistribution> list = new ArrayList<AssignmentDistribution>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "RepaymentCollectionService");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			RepaymentCollectionService.Client client = (RepaymentCollectionService.Client) clientFactory.getClient();

			if (assignmentDistributionSearch.getRequestStartDt() != null && !"".equals(assignmentDistributionSearch.getRequestStartDt())) {
				assignmentDistributionSearch.setRequestStartDt(assignmentDistributionSearch.getRequestStartDt() + " 00:00:00");
			}
			if (assignmentDistributionSearch.getRequestEndDT() != null && !"".equals(assignmentDistributionSearch.getRequestEndDT())) {
				assignmentDistributionSearch.setRequestEndDT(assignmentDistributionSearch.getRequestEndDT() + " 24:00:00");
			}

			list = client.getAssignmentDistribution(assignmentDistributionSearch);
			int total = client.getAssignmentDistributionTotal(assignmentDistributionSearch);
			map.put("total", total);
			map.put("rows", list);
		} catch (Exception e) {
			logger.error("getAssignmentDistribution fail",e);
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
	 * 贷后管理挪用处理查询列表页面跳转
	 */
	@RequestMapping(value = "listMisapprProcess")
	public String getLoanSettle(@RequestParam(value = "limitId", required = false) Integer limitId, ModelMap model) throws Exception {
		return "afterloan/misapprProcess";
	}

	/**
	 * 贷后管理挪用处理查询列表
	 */
	@RequestMapping(value = "getMisappProcess")
	public void getMisappProcess(MisapprProcessCriteria misapprProcessCriteria, HttpServletResponse response) throws Exception {
		List<MisapprProcess> list = new ArrayList<MisapprProcess>();
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "MisapprProcessService");
			MisapprProcessService.Client client = (MisapprProcessService.Client) clientFactory.getClient();
			ShiroUser user = getShiroUser();
			misapprProcessCriteria.setPmUserId(user.getPid());
			misapprProcessCriteria.setRows(misapprProcessCriteria.rows);
			misapprProcessCriteria.setPage(misapprProcessCriteria.page);
			list = client.getMisappProcess(misapprProcessCriteria);
			count = client.getMisappProcessCount(misapprProcessCriteria);
		} catch (Exception e) {
			logger.error("getMisappProcess fail",e);
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
		map.put("rows", list);
		map.put("total", count);
		outputJson(map, response);
	}

	
	@RequestMapping(value = "makeCollectionNotice")
	public void makeCollectionNotice(int projectId,HttpServletResponse response,HttpServletRequest request){
		String nowDttm = DateUtil.format(new Date());
		String year = nowDttm.split("-")[0];
		BaseClientFactory clientFactory = null;
		BaseClientFactory c = null;
		BaseClientFactory cp = null;
		BaseClientFactory clientF = null;
		ReminderNotice reminderNotice = new ReminderNotice();
		int xuhao = 0;
		TemplateFile template = new TemplateFile();
		List<String> list = new ArrayList<String>();
		Map<String,String> map = new HashMap<String,String>();
		Double outStandPrincipal = 0.0;
		Double outStandInterest = 0.0;
		Double shouldTotal = 0.0;
		Project project = new Project();
		int projectType = 0;
		try{
			c = getFactory(BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
			TemplateFileService.Client cl = (TemplateFileService.Client) c.getClient();
			clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "RepaymentCollectionService");
			RepaymentCollectionService.Client client = (RepaymentCollectionService.Client) clientFactory.getClient();
			cp = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client clientpro = (ProjectService.Client) cp.getClient();
			clientF = getFactory(BusinessModule.MODUEL_REPAYMENT, "LoanExtensionService");
			LoanExtensionService.Client clientloan = (LoanExtensionService.Client) clientF.getClient();
			List<ReminderNoticePart> reList = client.getReminderNoticePart(projectId,nowDttm);
			xuhao = client.getNoticeCurrentSeq(year);
			
			project = clientpro.getProjectById(projectId);
			projectType = project.getProjectType();
			
			if(projectType==4){
				//说明是展期项目所以需要查询老项目的账号密码开户人
				String pids = clientloan.getHisProjectIds(projectId);
				String pid = pids.substring(pids.lastIndexOf(",")+1, pids.length());
				reminderNotice = client.getReminderNotice(Integer.parseInt(pid));
			}else{
				reminderNotice = client.getReminderNotice(projectId);
			}
			reminderNotice.setNumber(year+"年逾催 字第"+xuhao+"号");
			reminderNotice.setSysdateDttm(nowDttm.split(" ")[0]);
			
			if(reminderNotice.getBankCardType()==2){
				reminderNotice.setAccName(reminderNotice.getAccName()+"(公章)");
			}
			template = cl.getTemplateFile("HKCSTZD");
			List<ContractDynamicTableParameter> conList = new ArrayList<ContractDynamicTableParameter>();
			for(int i=0;i<reList.size();i++){
				ReminderNoticePart reminder = reList.get(i);
				ReminderNoticePart re = client.getReminderNoticePartMoney(reminder,nowDttm);
				ContractDynamicTableParameter contractD = new ContractDynamicTableParameter();
				contractD.setGoodsName(reminderNotice.getContractNO());
				contractD.setGoodsNumber(String.valueOf(re.getShouldAmt()));
				contractD.setGoodsCount(reminder.getShouldDttm());
				contractD.setGoodsUnit(String.valueOf(re.getPrincipal()));
				contractD.setGoodsAddress(String.valueOf(re.getInterest()));
				conList.add(contractD);
				shouldTotal = shouldTotal+re.getShouldAmt();
				outStandPrincipal = outStandPrincipal+re.getPrincipal();
				outStandInterest = outStandInterest+re.getInterest();
			}
			ContractDynamicTableParameter contractD = new ContractDynamicTableParameter();
			contractD.setGoodsName("合计");
			contractD.setGoodsNumber(String.valueOf(shouldTotal));
			contractD.setGoodsCount("/");
			contractD.setGoodsUnit(String.valueOf(outStandPrincipal));
			contractD.setGoodsAddress(String.valueOf(outStandInterest));
			conList.add(contractD);
			reminderNotice.setOutStandPrincipal(outStandPrincipal);
			reminderNotice.setOutStandPrincipalUp(contractGenerator.convertVal(String.valueOf(outStandPrincipal),3));
			reminderNotice.setOutStandInterest(outStandInterest);
			reminderNotice.setOutStandInterestUp(contractGenerator.convertVal(String.valueOf(outStandInterest),3));
			String[] s = ParseDocAndDocxUtil.getFiledName(reminderNotice);
			String docText = ParseDocAndDocxUtil.parseDocumet(template.getFileUrl(), request);
			list = ParseDocAndDocxUtil.getDocumentSign(docText);
			List<TempLateParmDto> templateParamList = new ArrayList<TempLateParmDto>();
			for(int i=0;i<list.size();i++){
				if(!list.get(i).equals("")){
					String biaoshi = list.get(i).replace("@", "");
					for(int j=0;j<s.length;j++){
						if(biaoshi.equals(s[j])){
							TempLateParmDto tempLateParmDto = new TempLateParmDto();
							if(s[j].substring(s[j].length()-4,s[j].length()).equals("Dttm")){
								tempLateParmDto.setValConvertFlag(2);
								tempLateParmDto.setMatchFlag(list.get(i));
							}else{
								tempLateParmDto.setValConvertFlag(0);
								tempLateParmDto.setMatchFlag(list.get(i));
							}
							templateParamList.add(tempLateParmDto);
							map.put(list.get(i), ExcelExport.getFieldValueByName(s[j], reminderNotice));
						}
					}
				}
			}
			
			
			ContractGenerator contractGenerator = new ContractGenerator();
			String realFileName = genFileName(template.getFileUrl());
			String localPath = CommonUtil.getRootPath()+ExcelExport.getDateToString();
			File file = ExcelExport.createFile(localPath, realFileName);
			String path = CommonUtil.getRootPath() +template.getFileUrl();
			// TODO  新修改合同动态表格
			boolean b = contractGenerator.generate(this,templateParamList, path, file.getPath(), map, conList,null);
			if(b==true){
				ExcelExport.sentFile(realFileName, localPath, response, request);
			}
			client.updateNoticeCurrentSeq(year);
		}catch(Exception e){
			logger.error("makeCollectionNotice fail",e);
			response.setContentType("text/html;charset=UTF-8");  
			try {
				PrintWriter out = response.getWriter();
				out.println("<script>alert('生成催收通知单失败！')</script>");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}finally{
			if(clientFactory!=null){
				try{
					clientFactory.destroy();
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * 贷后管理还款催收查询
	 */
	@RequestMapping(value = "getRepaymentCollection")
	public String getRepaymentCollectingQuery(@RequestParam(value = "limitId", required = false) Integer limitId, ModelMap model) throws Exception {
		return "afterloan/repaymentCollectingQuery";
	}

	/**
	 * 
	 * 还款催收查询
	 */
	@RequestMapping(value = "getRepaymentCollectionlist")
	public void getRepaymentCollectionlist(RepaymentCollectionSearch repaymentCollection, HttpServletResponse response) throws Exception {
		List<RepaymentCollection> li = new ArrayList<RepaymentCollection>();
		BaseClientFactory clientFactory = null;
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取当前登录人id
		int userId = getShiroUser().getPid();
		repaymentCollection.setUserId(userId);
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "RepaymentCollectionService");
			RepaymentCollectionService.Client client = (RepaymentCollectionService.Client) clientFactory.getClient();
			if (repaymentCollection.getRequestStartDt() != null && !"".equals(repaymentCollection.getRequestStartDt())) {
				repaymentCollection.setRequestStartDt(repaymentCollection.getRequestStartDt() + " 00:00:00");
			}
			if (repaymentCollection.getRequestEndDT() != null && !"".equals(repaymentCollection.getRequestEndDT())) {
				repaymentCollection.setRequestEndDT(repaymentCollection.getRequestEndDT() + " 24:00:00");
			}
			if (repaymentCollection.getFactStartDt() != null && !"".equals(repaymentCollection.getFactStartDt())) {
				repaymentCollection.setFactStartDt(repaymentCollection.getFactStartDt() + " 00:00:00");
			}
			if (repaymentCollection.getFactEndDT() != null && !"".equals(repaymentCollection.getFactEndDT())) {
				repaymentCollection.setFactEndDT(repaymentCollection.getFactEndDT() + " 24:00:00");
			}
			li = client.getRepaymentCollectionList(repaymentCollection);

			map.put("total", 0);
			if (li != null && li.size() > 0) {
				map.put("total", li.get(0).getTotal());
			}
			map.put("rows", li);
		} catch (Exception e) {
				logger.error("还款催收查询失败" ,e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(map, response);
	}

	/**
	 * 保存催收员
	 * 
	 * @Description: TODO
	 * @param pid
	 * @param realName
	 * @param planDt
	 * @param isMail
	 * @param isSms
	 * @param response
	 * @author: mayinmei
	 * @date: 2015年3月27日 下午6:03:26
	 */
	@RequestMapping(value = "conllectionTaskSave", method = RequestMethod.POST)
	public void conllectionTaskSave(@RequestParam("pid[]") int[] pid, @RequestParam("realName[]") String[] realName, @RequestParam("planDt[]") String[] planDt, @RequestParam("isMail") int isMail, @RequestParam("isSms") int isSms,@RequestParam("reminderId[]") int[] reminderId, HttpServletResponse response) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "RepaymentCollectionService");
		BaseClientFactory userClientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserService");
		try {
			RepaymentCollectionService.Client client = (RepaymentCollectionService.Client) clientFactory.getClient();
			SysUserService.Client userClient = (SysUserService.Client) userClientFactory.getClient();
			ProjectReminderPlanDto reminderPlan = null;
			// 发送短信
			SysUser sysUser = null;
			List<SysUser> sysUsers = new ArrayList<SysUser>();
			for (int i = 0; i < pid.length; i++) {
				
				reminderPlan = new ProjectReminderPlanDto();
				reminderPlan.setProjectId(pid[i]);
				reminderPlan.setReminderUserId(Integer.parseInt(realName[i]));
				reminderPlan.setPlanBeginDt(planDt[i]);
				reminderPlan.setIsMail(isMail);
				reminderPlan.setIsSms(isSms);
				if(reminderId[i]>0){
					reminderPlan.setPId(reminderId[i]);
					client.updateReminderPlan(reminderPlan);
				}else{
					
					client.insertReminderPlan(reminderPlan);
				}
				if (isSms == 1 || isMail == 1) {
					sysUser = userClient.getSysUserByPid(reminderPlan.getReminderUserId());
					sysUsers.add(sysUser);
				}
			}

			// 是否发送
			if (isSms == 1) {
				// 手机号码。多个手机号码以英文逗号隔开“,”
				StringBuilder mobile = new StringBuilder();
				for (int i = 0; i < sysUsers.size(); i++) {
					mobile.append(sysUsers.get(i).getPhone());
					if ((i + 1) < sysUsers.size()) {
						mobile.append(",");
					}
				}
				// 短信内容
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("sysDate", DateUtil.format(new Date()));
				// 短信内容
				String msg = templateParsing(map, "colletionTaskSms.ftl");
				if (null != msg) {
					// 发送短信
					sendSms(mobile.toString(), msg);
				} else {
					// TODO: 记录数据库异常日志
				}
			}
			// 是否发送邮件
			if (isMail == 1) {
				// 主题
				String subject = "【还款催收专员分配】";
				// 邮件内容
				// String htmlText =
				// "您有一个审批任务。前一审批人的意见如下："+workflowVo.idea+"请您尽快处理。";
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("sysDate", DateUtil.format(new Date()));
				map.put("subject", "【还款催收专员分配】");
				// 邮件内容
				String htmlText = templateParsing(map, "colletionTaskEmail.ftl");
				for (SysUser u : sysUsers) {
					// 邮箱号
					String emailAddress = u.getMail();
					if (null != htmlText) {
						// 发送邮件
						sendMail(emailAddress, subject, htmlText);
					} else {
						// TODO: 记录数据库异常日志
					}
				}
			}
			outputJson("success", response);
		} catch (Exception e) {
			outputJson("false", response);
			logger.error("afterLoanController.conllectionTaskSave Exception" , e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
			if (userClientFactory != null) {
				try {
					userClientFactory.destroy();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}

	/**
	 * 还款催收记录查询
	 */
	@RequestMapping(value = "getCollectionRecord")
	public String getCollectionRecord() {
		return "afterloan/listCollectionRecord";
	}

	/**
	 * 
	 * 催收记录查询
	 */
	@RequestMapping(value = "getCollectionRecordlist")
	public void getCollectionRecordlist(@RequestParam(value = "projectId") Integer projectId, Integer page, Integer rows, HttpServletResponse response) throws Exception {
		List<CollectionRecordDto> li = new ArrayList<CollectionRecordDto>();
		BaseClientFactory clientFactory = null;
		Map<String, Object> mapData = new HashMap<String, Object>();
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "RepaymentCollectionService");
			RepaymentCollectionService.Client client = (RepaymentCollectionService.Client) clientFactory.getClient();
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("projectId", projectId);
			map.put("page", page);
			map.put("rows", rows);
			li = client.getCollectionRecordList(map);
			int count = 0;
			if (li != null && li.size() > 0) {
				count = li.get(0).getCount();
			}
			mapData.put("rows", li);
			mapData.put("total", count);

		} catch (Exception e) {
				logger.error("还款催收记录查询失败",e.getMessage());
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}

		outputJson(mapData, response);
	}

	@RequestMapping(value = "saveCollectionRecord", method = RequestMethod.POST)
	public void saveCollectionRecord(CollectionRecord collectionRecord, HttpServletResponse response) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "RepaymentCollectionService");
		Json j = super.getSuccessObj();
		try {
			RepaymentCollectionService.Client client = (RepaymentCollectionService.Client) clientFactory.getClient();
			collectionRecord.setReminderUserId(getShiroUser().getPid());// 设置当前用户
			client.insertCollectionRecord(collectionRecord);
			// 发送短信给客户
			// 根据客户id查询联系人信息
			List<CollectionCusComContact> conList = client.getCollectionCusComContact(collectionRecord.getAcctId());
			// 是否发送
			if (collectionRecord.getMethodSms() == 1) {
				// 手机号码。多个手机号码以英文逗号隔开“,”
				StringBuilder mobile = new StringBuilder();
				if (conList.size() > 0) {
					for (int i = 0; i < conList.size(); i++) {
						mobile.append(conList.get(i).getMovePhone());
						if ((i + 1) < conList.size()) {
							mobile.append(",");
						}
					}
				} else {
					mobile.append(collectionRecord.getTelephone());
				}
				// 短信内容
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("sysDate", DateUtil.format(new Date()));
				map.put("content", collectionRecord.getReminderMsg());
				// 短信内容
				String msg = templateParsing(map, "colletionRecordSms.ftl");
				if (null != msg) {
					// 发送短信
					sendSms(mobile.toString(), msg);
				} else {
					// TODO: 记录数据库异常日志
				}
			}
			// 是否发送邮件
			if (collectionRecord.getMethodMall() == 1) {
				// 主题
				String subject = collectionRecord.getReminderSubject();
				// 邮件内容
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("sysDate", DateUtil.format(new Date()));
				map.put("subject", collectionRecord.getCycleNum() + "期    " + collectionRecord.getReminderSubject());
				map.put("content", collectionRecord.getMethodSms());
				// 邮件内容
				String htmlText = templateParsing(map, "colletionRecordEmail.ftl");
				// 邮箱地址
				String emailAddress = collectionRecord.getMailAdds();
				if (null != htmlText) {
					// 发送邮件
					sendMail(emailAddress, subject, htmlText);
				} else {
					// TODO: 记录数据库异常日志
				}
			}

			j.getHeader().put("flag", "success");

		} catch (Exception e) {
			j.getHeader().put("flag", "error");
			logger.error("afterLoanController.conllectionTaskSave Exception" , e);
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
	 * 查询应收款信息
	 */
	@RequestMapping(value = "getReceivablelist")
	public void getReceivablelist(@RequestParam(value = "loadId") Integer loadId, HttpServletResponse response) throws Exception {
		List<RepaymentPlanBaseDTO> li = null;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			RepaymentPlanBaseDTO dto = new RepaymentPlanBaseDTO();
			dto.setLoanInfoId(loadId);
			dto.setFreezeStatus(1);
			li = client.selectRepayReminders(dto);
		} catch (Exception e) {
				logger.error("查询应收款信息失败" ,e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(li, response);
	}

	/**
	 * 
	 * @Description: 还款催收查询导出
	 * @param pid
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: wangheng
	 * @date: 2015年4月23日
	 */
	@RequestMapping(value = "exportRepayReminder")
	@ResponseBody
	public void exportRepayReminder(String pids, HttpServletRequest request, HttpServletResponse response) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "RepaymentCollectionService");
		BaseClientFactory c = getFactory(BusinessModule.MODUEL_SYSTEM, "TemplateFileService");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			TemplateFileService.Client cl = (TemplateFileService.Client) c.getClient();
			RepaymentCollectionService.Client client = (RepaymentCollectionService.Client) clientFactory.getClient();
			List<CollectionRecord> list = client.getCollectionRecordsByProjectIds(pids);
			map.put("bean", list);
			TemplateFile template = cl.getTemplateFile("HKCSJLBB");

			if (!template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(map, path, "还款催收记录导出.xls", response, request);
			}
		} catch (Exception e) {
				logger.error("导出还款催收记录失败" , e);
			response.setContentType("text/html;charset=UTF-8");  
			try {
				PrintWriter out = response.getWriter();
				out.println("<script>alert('导出还款催收记录失败！')</script>");
			} catch (IOException e1) {
				e1.printStackTrace();
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
	 * 跳转到今日更新页面
	 */
	@RequestMapping(value = "toDayUpdatePage")
	public String toDayUpdatePage(ModelMap model ) throws Exception {
		String today = DateUtil.format(DateUtil.addDay(DateUtil.getToDay(), -1), "yyyy-MM-dd");
		model.put("toDay",today );
		model.put("year", today.split("-")[0]);
		model.put("month", today.split("-")[1]);
		model.put("day", today.split("-")[2]);
		
		// 设置数据字典中配制的表格title 
		List<SysLookupVal> list = null;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
			SysLookupService.Client client = (SysLookupService.Client) clientFactory.getClient();
			list = client.getSysLookupValByLookType("TODAY_UPDATE");
			
			if(null!=list && list.size()>0){
				for(SysLookupVal val : list){
					if(null!=val){
						model.put(val.getLookupVal().trim(), val.getLookupDesc().trim());
					}
				}
			}
		} catch (Exception e) {
			logger.error("toDayUpdatePage fail",e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		
		return "afterloan/toDayUpdateList";
	}
	
	/**
	 * 
	  * @Description: TODO
	  * @param date 日期
	  * @param type	1 个人 2 企业
	  * @return
	  * @throws Exception
	  * @author: Zhangyu.Hoo
	  * @date: 2015年5月27日 上午11:28:34
	 */
	@RequestMapping(value = "getToDayUpdateList")
	public void getToDayUpdateList(ToDayUpdateDTO toDayUpdateDTO, HttpServletResponse response) throws Exception {
		AfterLoanSupervisionService.Client client = null;
		BaseClientFactory clientFactory = null;
		List<ToDayUpdateDTO> list = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "AfterLoanSupervisionService");
			client = (AfterLoanSupervisionService.Client) clientFactory.getClient();
			list = client.getToDayUpdateList(toDayUpdateDTO);
		}catch (Exception e) {
			logger.error("getToDayUpdateList fail",e);
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

}
