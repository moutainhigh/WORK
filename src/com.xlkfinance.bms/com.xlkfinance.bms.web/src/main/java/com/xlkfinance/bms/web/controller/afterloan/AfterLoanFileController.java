/**
 * @Title: AfterLoanController.java
 * @Package com.xlkfinance.bms.web.controller.afterloan
 * @Description: 贷后管理>>项目归档管理
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: HeZhiYing
 * @date: 2015年3月11日 上午9:50:40
 * @version V1.0
 * 
 */
package com.xlkfinance.bms.web.controller.afterloan;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.beforeloan.AfterLoanArchive;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;

@Controller
@RequestMapping("/afterLoanFileController")
public class AfterLoanFileController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(AfterLoanFileController.class);

	/**
	 * 贷后管理--归档记录
	 */
	@RequestMapping(value = "getAfterLoadFileRecord")
	public String getAfterLoadFileRecord() {
		return "afterloan/index";
	}

	/**
	 * 
	 * @Description: 查询所有贷款申请列表
	 * @param loanProject
	 *            贷款申请对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: HeZhiYing
	 * @date: 2015年3月11日 上午9:50:40
	 */
	@RequestMapping(value = "getAfterLoadFileList")
	public void getAfterLoadFileList(Project loanProject, HttpServletResponse response) {
		// 创建集合
		List<Project> list = new ArrayList<Project>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			list = client.getAfterLoadFileList(loanProject);
			int count = client.getAfterLoadFileCount(loanProject);
			map.put("rows", list);
			map.put("total", count);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询贷后归档项目列表:" + e.getMessage());
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
		outputJson(map, response);
	}

	/**
	 * 
	 * @Description: 查询一条贷后项目信息
	 * @author: HeZhiYing
	 * @date: 2015年3月11日 上午11:35:45
	 */
	@RequestMapping(value = "afterLoanFileDateil")
	public String afterLoanFileDateil(@RequestParam(value = "mflag", required = false) Integer mflag, @RequestParam(value = "proStatus", required = false) Integer proStatus, @RequestParam(value = "projectId", required = false) Integer projectId, @RequestParam(value = "projectNumber", required = false) String projectNumber, Project loan, ModelMap model, HttpServletResponse response) {
		// 创建集合
		Project loanProject = new Project();
		loanProject.setProjectNumber(projectNumber);
		List<Project> list = new ArrayList<Project>();
		int loanId = 0;

		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		try {
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();

			loanId = client.getLoanIdByProjectId(projectId);
			list = client.getAfterLoadFileList(loanProject);
			if (list != null && list.size() != 0) {
				loanProject = list.get(0);
			}
			model.put("project", loanProject);

			/*
			 * // 收息表 clientFactory = getFactory(BusinessModule.MODUEL_FINANCE,
			 * "FinanceService"); FinanceService.Client ct =
			 * (FinanceService.Client) clientFactory.getClient();
			 * custArrears=ct.getCustArrearsbyProjectView(projectId);
			 */

		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询贷后归档项目信息:" + e.getMessage());
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

		model.put("mflag", mflag);
		model.put("projectId", projectId);
		model.addAttribute("loanId", loanId);
		model.put("projectNumber", projectNumber);
		model.put("proStatus", proStatus);
		return "afterloan/afterLoanFileDateil";
	}

	/**
	 * 
	 * @Description: 查询贷后归档信息
	 * @param projectId
	 * @param response
	 * @author: xuweihao
	 * @date: 2015年4月26日 下午6:56:11
	 */
	@RequestMapping(value = "getAfterLoanArchive")
	public void getAfterLoanArchive(@RequestParam(value = "projectId", required = false) Integer projectId, HttpServletResponse response) {
		// 创建集合
		AfterLoanArchive afterLoanArchive = new AfterLoanArchive();

		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		try {
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			afterLoanArchive = client.getAfterLoadFileOne(projectId);
			if (afterLoanArchive.getProjectId() == 0) {
				afterLoanArchive.setProjectId(projectId);
				afterLoanArchive.setAfterloanStatus(0);
				afterLoanArchive.setAfterloanDttm("");
				afterLoanArchive.setAfterloanComments("");
				client.addAfterLoanArchive(afterLoanArchive);
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询贷后归档项目信息:" + e.getMessage());
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
		outputJson(afterLoanArchive, response);
	}

	/**
	 * 
	 * @Description: 贷后项目归档提交
	 * @param pids
	 *            贷款申请对象id
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: hezhiying
	 * @date: 2015年3月12日 上午10:54:59
	 */
	@RequestMapping(value = "updateProjectStatus")
	public void updateProjectStatus(AfterLoanArchive afterLoanArchive, HttpServletResponse response) throws Exception {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		try {
			if (afterLoanArchive.getAfterloanStatus() == 1) {
				afterLoanArchive.setAfterloanDttm(DateUtil.format(new Date()));
			}
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			client.updateAfterLoanArchive(afterLoanArchive);
		} catch (Exception e) {
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "提交失败！");
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
	 * @Description: 新增项目归档信息
	 * @param afterLoanArchive
	 * @param response
	 * @throws Exception
	 * @author: xuweihao
	 * @date: 2015年4月26日 下午7:18:32
	 */
	@RequestMapping(value = "addProjectStatus")
	public void addProjectStatus(AfterLoanArchive afterLoanArchive, HttpServletResponse response) throws Exception {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		try {
			if (afterLoanArchive.getAfterloanStatus() == 1) {
				afterLoanArchive.setAfterloanDttm(DateUtil.format(new Date()));
			}
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			client.addAfterLoanArchive(afterLoanArchive);
		} catch (Exception e) {
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "提交失败！");
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
	 * @Description: 贷后项目归档资料状态
	 * @param pids
	 *            贷款申请对象id
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: hezhiying
	 * @date: 2015年3月12日 上午10:54:59
	 */
	@RequestMapping(value = "getProjectFileStatus")
	public void getProjectFileStatus(@RequestParam(value = "projectId", required = false) Integer projectId, HttpServletResponse response) throws Exception {
		int mcount = 0;
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		try {
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			mcount = client.getProjectFileStatus(projectId);
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
		outputJson(mcount, response);
	}

}
