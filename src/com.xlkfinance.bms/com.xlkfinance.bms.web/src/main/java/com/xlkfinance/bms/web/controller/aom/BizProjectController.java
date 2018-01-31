/**
 * @Title: BizProjectController.java
 * @Package com.xlkfinance.bms.web.controller.aom
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: baogang
 * @date: 2016年7月22日 下午4:36:30
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.aom;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
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
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyInf;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyService;
import com.qfang.xk.aom.rpc.org.OrgCooperateCityInfo;
import com.qfang.xk.aom.rpc.project.BizProject;
import com.qfang.xk.aom.rpc.project.BizProjectDTO;
import com.qfang.xk.aom.rpc.project.BizProjectService;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.constant.OrgSysLogTypeConstant;
import com.xlkfinance.bms.common.constant.SysLogTypeConstant;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.NumberUtils;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.DataInfo;
import com.xlkfinance.bms.rpc.beforeloan.DataUploadService;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectForeInformation;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.beforeloan.RepaymentLoanInfo;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.inloan.ApplyHandleInfoDTO;
import com.xlkfinance.bms.rpc.inloan.BizDynamic;
import com.xlkfinance.bms.rpc.inloan.BizDynamicService;
import com.xlkfinance.bms.rpc.inloan.BizHandleService;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleDTO;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleService;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.rpc.system.SysUserService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;

/**
 * 合作机构业务申请Controller
 * 
 * @ClassName: BizProjectController
 * @author: baogang
 * @date: 2016年7月22日 下午4:36:50
 */
@Controller
@RequestMapping("/bizProjectController")
public class BizProjectController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(BizProjectController.class);
	private static final String PATH = "aom/project/";

	/**
	 * 跳转业务申请列表
	 * 
	 * @return
	 * @author: baogang
	 * @date: 2016年7月22日 下午4:39:07
	 */
	@RequestMapping(value = "index")
	public String index() {
		return PATH + "index";
	}

	/**
	 * 跳转业务申请未分配列表
	 * 
	 * @return
	 * @author: baogang
	 * @date: 2016年7月22日 下午4:39:07
	 */
	@RequestMapping(value = "assignedIndex")
	public String assignedIndex() {
		return PATH + "assigned_index";
	}

	/**
	 * 查询已分配的业务申请列表
	 * 
	 * @param query
	 * @param request
	 * @param response
	 * @author: baogang
	 * @date: 2016年7月25日 下午2:59:41
	 */
	@RequestMapping(value = "getAssignedProjectList", method = RequestMethod.POST)
	public void getAssignedProjectList(BizProjectDTO query,
			HttpServletRequest request, HttpServletResponse response) {
		// 需要返回的Map集合
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG_PROJECT,
					"BizProjectService");
			// 创建集合
			BizProjectService.Client projectService = (BizProjectService.Client) clientFactory
					.getClient();
			// 只显示已提交的业务申请
			List<Integer> requestStatuList = new ArrayList<Integer>();
			requestStatuList.add(AomConstant.BUSINESS_REQUEST_STATUS_2);// 2、待审核3、审核中4、审核通过5、审核失败
			requestStatuList.add(AomConstant.BUSINESS_REQUEST_STATUS_3);
			requestStatuList.add(AomConstant.BUSINESS_REQUEST_STATUS_4);
			requestStatuList.add(AomConstant.BUSINESS_REQUEST_STATUS_5);
			query.setRequestStatuList(requestStatuList);
			query.setIsClosed(AomConstant.IS_CLOSED_2);// 未关闭的订单
			query.setIsReject(AomConstant.IS_REJECT_2);// 未驳回的订单
			query.setIsAssigned(AomConstant.IS_ASSIGNED_2);// 已分配的订单
			query.setUserIds(getUserIds(request));// 数据权限
			List<BizProjectDTO> list = projectService
					.getBizProjectByPage(query);
			int count = projectService.getBizProjectCount(query);
			map.put("rows", list);
			map.put("total", count);
		} catch (Exception e) {
			logger.error(" 查询业务申请列表:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(map, response);
	}

	/**
	 * 查询未分配的业务申请列表
	 * 
	 * @param query
	 * @param request
	 * @param response
	 * @author: baogang
	 * @date: 2016年7月25日 下午2:59:41
	 */
	@RequestMapping(value = "getProjectList", method = RequestMethod.POST)
	public void getProjectList(BizProjectDTO query, HttpServletRequest request,
			HttpServletResponse response) {
		// 需要返回的Map集合
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG_PROJECT,
					"BizProjectService");
			// 创建集合
			BizProjectService.Client projectService = (BizProjectService.Client) clientFactory
					.getClient();
			// 只显示已提交的业务申请
			List<Integer> requestStatuList = new ArrayList<Integer>();
			requestStatuList.add(AomConstant.BUSINESS_REQUEST_STATUS_2);// 2、待审核3、审核中4、审核通过5、审核失败
			requestStatuList.add(AomConstant.BUSINESS_REQUEST_STATUS_3);
			requestStatuList.add(AomConstant.BUSINESS_REQUEST_STATUS_4);
			requestStatuList.add(AomConstant.BUSINESS_REQUEST_STATUS_5);
			query.setRequestStatuList(requestStatuList);
			query.setIsClosed(AomConstant.IS_CLOSED_2);// 未关闭的订单
			query.setIsReject(AomConstant.IS_REJECT_2);// 未驳回的订单
			query.setIsAssigned(AomConstant.IS_ASSIGNED_1);// 未分配的订单
			List<BizProjectDTO> list = projectService
					.getBizProjectByPage(query);
			int count = projectService.getBizProjectCount(query);
			map.put("rows", list);
			map.put("total", count);
		} catch (Exception e) {
			logger.error(" 查询业务申请列表:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(map, response);
	}

	/**
	 * 跳转业务编辑页面
	 * 
	 * @param model
	 * @param pid
	 * @return
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: baogang
	 * @date: 2016年7月19日 下午2:31:33
	 */
	@RequestMapping("/toEditProject")
	public String toEditProject(
			ModelMap model,
			Integer projectId,
			@RequestParam(value = "editType", required = false) Integer editType,
			@RequestParam(value = "isEdit", required = false) String isEdit,
			HttpServletResponse response) throws ThriftServiceException,
			TException {
		BaseClientFactory clientFactory = null;
		BaseClientFactory orgFactory = null;
		BizProject project = new BizProject();
		List<OrgCooperateCityInfo> cityInfoList = new ArrayList<OrgCooperateCityInfo>();
		try {
			// 根据业务申请ID查询详情以及资料集合
			if (projectId != null && projectId > 0) {
				clientFactory = getAomFactory(
						BusinessModule.MODUEL_ORG_PROJECT, "BizProjectService");
				orgFactory = getAomFactory(BusinessModule.MODUEL_ORG,
						"OrgCooperatCompanyApplyService");
				BizProjectService.Client projectService = (BizProjectService.Client) clientFactory
						.getClient();
				project = projectService.getProjectByPid(projectId);
				// 查询合作城市集合
				OrgCooperateCityInfo query = new OrgCooperateCityInfo();
				query.setOrgId(project.getOrgId());
				OrgCooperatCompanyApplyService.Client client = (OrgCooperatCompanyApplyService.Client) orgFactory
						.getClient();
				cityInfoList = client.getOrgCooperateCityInfo(query);
			}
		} catch (Exception e) {
			logger.error(" 跳转业务编辑页面:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory, orgFactory);
		}
		SysUser sysUser = getSysUser();
		model.put("login", sysUser);
		model.put("project", project);
		model.put("cityInfoList", cityInfoList);
		model.put("editType", editType);
		model.put("isEdit", isEdit);
		return PATH + "/edit_project";
	}

	/**
	 * 获取资料列表
	 * 
	 * @param projectId
	 * @param request
	 * @param response
	 * @author: baogang
	 * @date: 2016年7月25日 下午5:26:55
	 */
	@RequestMapping("/getDataInfoList")
	public void getDataInfoList(Integer projectId, HttpServletRequest request,
			HttpServletResponse response) {
		List<DataInfo> dataInfoList = new ArrayList<DataInfo>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG_PROJECT,
					"BizProjectService");
			// 创建集合
			BizProjectService.Client projectService = (BizProjectService.Client) clientFactory
					.getClient();
			dataInfoList = projectService.getProjectDataInfos(projectId);
		} catch (Exception e) {
			logger.error("获取资料列表:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(dataInfoList, response);
	}

	/**
	 * 保存业务申请信息
	 * 
	 * @param bizProject
	 * @param resp
	 * @throws TException
	 * @author: baogang
	 * @date: 2016年7月25日 上午10:07:45
	 */
	@RequestMapping("/saveProjectInfo")
	public void saveProjectInfo(BizProject bizProject,
			HttpServletResponse resp, HttpServletRequest req) throws TException {
		String customerName = bizProject.getOrgCustomerName();
		String customerCard = bizProject.getOrgCustomerCard();
		String customerPhone = bizProject.getOrgCustomerPhone();
		double loanMoney = bizProject.getProjectGuarantee().getLoanMoney();
		int projectId = bizProject.getPid();
		recordAomLog(
				OrgSysLogTypeConstant.LOG_TYPE_ADD,
				"ERP后台保存业务申请信息：BizProjectController.saveProjectInfo 参数："
						+ bizProject.getOrgCustomerName() + ",金额"
						+ loanMoney, req);
		// ERP 后台日志
		recordLog(BusinessModule.MODUEL_BEFORELOAN,
				SysLogTypeConstant.LOG_TYPE_UPDATE,
				"修改机构业务申请参数" + bizProject.getOrgCustomerName() + ",金额"
						+ loanMoney, bizProject.getPid());
		// 判断业务申请主键、客户姓名、手机号码、身份证号以及贷款金额
		if (projectId <= 0
				|| StringUtil
						.isBlank(customerName, customerCard, customerPhone)
				|| loanMoney <= 0) {
			fillReturnJson(resp, false, "参数不合法，请输入必填项");
			return;
		}
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG_PROJECT,
					"BizProjectService");
			// 创建集合
			BizProjectService.Client projectService = (BizProjectService.Client) clientFactory
					.getClient();
			String result = projectService.updateBizProject(bizProject);
			if (StringUtil.isBlank(result)) {
				fillReturnJson(resp, false, "保存出错，请联系后台管理员！");
				return;
			}
			j.getHeader().put("success", true);
			j.getHeader().put("msg", "保存成功");
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.error("保存业务申请信息:" + ExceptionUtil.getExceptionMessage(e));
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "保存失败,请重新操作!");
		} finally {
			destroyFactory(clientFactory);
		}
		outputJson(j, resp);
	}

	/**
	 * 查询借款金额
	 * 
	 * @param projectId
	 * @param response
	 * @throws Exception
	 * @author: baogang
	 * @date: 2016年7月26日 下午2:08:57
	 */
	@RequestMapping(value = "getLoanMoney")
	public void getLoanMoney(Integer projectId, HttpServletResponse response)
			throws Exception {
		String loanMoney = "0.0";
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG_PROJECT,
					"BizProjectService");
			// 创建集合
			BizProjectService.Client projectService = (BizProjectService.Client) clientFactory
					.getClient();
			BizProject bizProject = projectService.getProjectByPid(projectId);
			loanMoney = NumberUtils.formatDecimal(String.valueOf(bizProject.getProjectGuarantee().getLoanMoney()));
		} catch (Exception e) {
			logger.error("查询项目贷款金额:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
		} finally {
			destroyFactory(clientFactory);
		}
		BigDecimal decimal = NumberUtils.parseDecimal(loanMoney);
		outputJson(decimal.doubleValue(), response);
	}

	/**
	 * 机构业务申请总揽
	 * 
	 * @param projectId
	 * @param model
	 * @return
	 * @author: baogang
	 * @date: 2016年8月11日 下午7:05:04
	 */
	@RequestMapping(value = "getProjectOverview")
	public String getProjectOverview(Integer projectId, ModelMap model) {
		// 创建对象
		BizProject project = new BizProject();
		List<BizDynamic> dynamicList = new ArrayList<BizDynamic>();
		OrgCooperatCompanyApplyInf orgApplyInfo = new OrgCooperatCompanyApplyInf();
		BaseClientFactory clientFactory = null;
		BaseClientFactory bizDynamicFactory = null;
		BaseClientFactory orgFactory = null;
		try {
			if (projectId != null && projectId > 0) {
				clientFactory = getAomFactory(
						BusinessModule.MODUEL_ORG_PROJECT, "BizProjectService");
				bizDynamicFactory = getFactory(BusinessModule.MODUEL_INLOAN,
						"BizDynamicService");
				orgFactory = getAomFactory(BusinessModule.MODUEL_ORG,
						"OrgCooperatCompanyApplyService");

				BizProjectService.Client projectService = (BizProjectService.Client) clientFactory
						.getClient();
				BizDynamicService.Client bizDynamicService = (BizDynamicService.Client) bizDynamicFactory
						.getClient();
				project = projectService.getProjectByPid(projectId);
				// 查询机构信息以及合作信息
				int orgId = project.getOrgId();
				OrgCooperatCompanyApplyService.Client client = (OrgCooperatCompanyApplyService.Client) orgFactory
						.getClient();
				orgApplyInfo = client.getByOrgId(orgId);
				// 日志信息
				BizDynamic bizDynamic = new BizDynamic();
				bizDynamic.setProjectId(projectId);
				bizDynamic.setPage(-1);// 不分页
				dynamicList = bizDynamicService.queryBizDynamic(bizDynamic);
			}
		} catch (Exception e) {
			logger.error("机构业务申请总揽:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory, orgFactory, bizDynamicFactory);
		}
		model.put("login", getSysUser());
		model.put("project", project);
		model.put("orgApplyInfo", orgApplyInfo);
		model.put("dynamicList", dynamicList);
		return PATH + "project_over_view";
	}

	/**
	 * 驳回业务申请
	 * 
	 * @param bizProject
	 * @param req
	 * @param resp
	 * @author: baogang
	 * @date: 2016年9月2日 上午9:33:33
	 */
	@RequestMapping(value = "rejectProject")
	public void rejectProject(BizProject bizProject, HttpServletRequest req,
			HttpServletResponse resp) {
		int pid = bizProject.getPid();
		if (pid <= 0) {
			logger.error("请求数据不合法：" + bizProject);
			fillReturnJson(resp, false, "请求数据不合法!");
			return;
		}
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG_PROJECT,
					"BizProjectService");
			BizProjectService.Client projectService = (BizProjectService.Client) clientFactory
					.getClient();
			bizProject.setExamineUser(getShiroUser().getPid());

			projectService.updateProjectReject(bizProject);
			recordAomLog(OrgSysLogTypeConstant.LOG_TYPE_UPDATE,
					"驳回业务申请：BizProjectController.rejectProject 参数："
							+ bizProject.getPid(), req);
			// ERP 后台日志
			recordLog(BusinessModule.MODUEL_ORG,
					SysLogTypeConstant.LOG_TYPE_UPDATE,
					"驳回业务申请:" + bizProject.getExamineOpinion(),
					bizProject.getPid());
			fillReturnJson(resp, true, "保存成功");
		} catch (Exception e) {
			fillReturnJson(resp, false, "驳回失败,请重新操作!");
			logger.error("驳回业务申请:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
	}

	/**
	 * 
	 * @Description: 页面跳转
	 * @return 尽职调查
	 * @author: Cai.Qing
	 * @date: 2015年2月3日 下午3:30:44
	 */
	@RequestMapping(value = "navigationInvestigation")
	public String navigationInvestigation(Integer projectId, ModelMap model) {
		model.put("projectId", projectId);
		return PATH + "loan_add_investigation";
	}

	/**
	 * 
	 * @Description: 页面跳转
	 * @return 合同页面
	 * @author: Cai.Qing
	 * @date: 2015年2月3日 下午3:36:44
	 */
	@RequestMapping(value = "navigationContract")
	public String navigationContract(String contractCatelog, Integer projectId,
			ModelMap model) {
		model.put("contractCatelog", contractCatelog);
		model.put("projectId", projectId);
		return PATH + "loan_add_contract";
	}

	/**
	 * 
	 * @Description: 借据及还款计划表
	 * @param contractCatelog
	 * @param model
	 * @return 还款计划表页面
	 * @author: zhanghg
	 * @date: 2015年2月10日 上午9:36:44
	 */
	@RequestMapping(value = "receiptRepaymentList")
	public String receiptRepaymentList(String contractCatelog, Integer loanId,
			Integer projectId, ModelMap model) {
		BaseClientFactory clientFactory = getFactory(
				BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		try {
			ProjectService.Client client = (ProjectService.Client) clientFactory
					.getClient();
			if (loanId != null && loanId > 0) {
				RepaymentLoanInfo repayment = client
						.getRepaymentLoanInfo(loanId);
				model.put("repayment", repayment);
			}
			model.put("loanId", loanId);
			model.put("projectId", projectId);
		} catch (Exception e) {
			logger.error("查询还款计划表贷款信息:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		model.put("contractCatelog", contractCatelog);
		return PATH + "receipt_repayment_list";
	}

	/**
	 * 
	 * @Description: 页面跳转
	 * @return 资料上传
	 * @author: Cai.Qing
	 * @date: 2015年2月3日 下午3:46:44
	 */
	@RequestMapping(value = "navigationDatum")
	public String navigationDatum() {
		return PATH + "loan_add_datum";
	}

	/**
	 * 
	 * @Description: 资料查询
	 * @param projectId
	 * @param page
	 * @param rows
	 * @param response
	 * @author: xuweihao
	 * @date: 2015年3月10日 上午8:47:17
	 */
	@RequestMapping(value = "fileList")
	@ResponseBody
	public void fileList(@RequestParam("projectId") int projectId,
			@RequestParam("page") int page, @RequestParam("rows") int rows,
			HttpServletResponse response) {
		BaseClientFactory c = null;
		BaseClientFactory clientFactory = getFactory(
				BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		List<DataInfo> list = new ArrayList<DataInfo>();
		DataInfo dinfo = new DataInfo();
		dinfo.setProjectId(projectId);
		dinfo.setPage(page);
		dinfo.setRows(rows);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			c = getFactory(BusinessModule.MODUEL_BEFORELOAN,
					"DataUploadService");
			DataUploadService.Client dataService = (DataUploadService.Client) c
					.getClient();
			
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			Project project = client.getLoanProjectByProjectId(projectId);
			
			List<String> projectTypes = new ArrayList<String>();
			projectTypes.add("1");
			//判断项目交易类型，13755为交易类型，13756为非交易类型
			if(project.getBusinessCategory() == 13755){
				projectTypes.add("4");//1与4表示数据字典中文件类型的数值，数值1和4的文件为交易类型的文件
			}else if(project.getBusinessCategory() == 13756){
				projectTypes.add("3");//1与3表示数据字典中文件类型的数值，数值1和3的文件为非交易类型的文件
			}
			// 获取项目类型
			dinfo.setProjectTypes(projectTypes);
			list = dataService.dataListByType(dinfo);
			int total = dataService.getDataTotalByType(dinfo);
			map.put("total", total);
			map.put("rows", list);
			outputJson(map, response);
		} catch (Exception e) {
			logger.error("查询资料列表出错", e);
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
	 * 跳转到赎楼清单页面
	 * 
	 * @param projectId
	 * @param model
	 * @return
	 * @author: Administrator
	 * @date: 2016年3月31日 下午5:41:59
	 */
	@RequestMapping(value = "navigationForeInformation")
	public String navigationForeInformation(Integer projectId, ModelMap model) {
		BaseClientFactory clientFactory = getFactory(
				BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		List<ProjectForeInformation> foreInformations = new ArrayList<ProjectForeInformation>();
		try {
			ProjectService.Client client = (ProjectService.Client) clientFactory
					.getClient();
			foreInformations = client.queryForeInformations(projectId);
		} catch (Exception e) {
			logger.error("根据项目ID查询项目赎楼清单:"
					+ ExceptionUtil.getExceptionMessage(e));
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
		model.put("foreInformations", foreInformations);
		model.put("projectId", projectId);
		return PATH + "project_fore_information";
	}

	/**
	 * 跳转到客户经理申请办理页面
	 * 
	 * @param projectId
	 * @param model
	 * @return
	 * @author: Administrator
	 * @date: 2016年4月1日 上午11:30:55
	 */
	@RequestMapping(value = "/navigationApplyInfo")
	public String navigationApplyInfo(Integer projectId, ModelMap model) {
		BaseClientFactory clientFactory = getFactory(
				BusinessModule.MODUEL_INLOAN, "BizHandleService");
		ApplyHandleInfoDTO applyHandleInfoDTO = new ApplyHandleInfoDTO();
		try {
			BizHandleService.Client client = (BizHandleService.Client) clientFactory
					.getClient();
			ApplyHandleInfoDTO query = new ApplyHandleInfoDTO();
			query.setProjectId(projectId);
			query.setPage(1);
			query.setRows(100);
			List<ApplyHandleInfoDTO> resultList = client
					.findAllApplyHandleInfo(query);
			if (resultList != null && resultList.size() > 0) {
				applyHandleInfoDTO = resultList.get(0);
			}
		} catch (Exception e) {
			logger.error("根据项目ID查询项目赎楼清单:"
					+ ExceptionUtil.getExceptionMessage(e));
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		model.put("applyHandleInfo", applyHandleInfoDTO);
		model.put("projectId", projectId);

		return PATH + "project_apply_info";
	}

	/**
	 * 分配订单
	 * 
	 * @param projectId
	 * @param pmUserId
	 * @param response
	 * @author: baogang
	 * @date: 2016年9月8日 下午5:38:58
	 */
	@RequestMapping(value = "assignedProject")
	public void assignedProject(BizProject bizProject,
			HttpServletResponse resp, HttpServletRequest req) {
		String pids = bizProject.getPids();
		int pmUserId = bizProject.getPmUserId();
		if (StringUtil.isBlank(pids) || pmUserId <= 0) {
			logger.error("请求数据不合法：" + bizProject);
			fillReturnJson(resp, false, "参数不合法,重新操作!");
			return;
		}
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG_PROJECT,
					"BizProjectService");
			BizProjectService.Client projectService = (BizProjectService.Client) clientFactory
					.getClient();
			bizProject.setIsAssigned(AomConstant.IS_ASSIGNED_2);// 已分配
			String[] pidArray = pids.split(",");
			List<String> pidList = Arrays.asList(pidArray);
			bizProject.setProjectIds(pidList);// 主键集合
			projectService.updateProjectAssigned(bizProject);
			// ERP 后台日志
			recordLog(BusinessModule.MODUEL_ORG,
					SysLogTypeConstant.LOG_TYPE_UPDATE,
					"分配订单:" + bizProject.getPmUserId(), bizProject.getPid());
			fillReturnJson(resp, true, "提交成功");
		} catch (Exception e) {
			fillReturnJson(resp, false, "提交失败,重新操作!");
			logger.error("分配订单:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
	}

	/**
	 * 业务分配时人员选择
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @author: baogang
	 * @date: 2016年9月9日 上午11:24:04
	 */
	@RequestMapping(value = "orderAllocationUserList", method = RequestMethod.POST)
	public void orderAllocationUserList(SysUser user,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SysUser> listUser = new ArrayList<SysUser>();
		BaseClientFactory clientFactory = getFactory(
				BusinessModule.MODUEL_SYSTEM, "SysUserService");
		user.setUserIds(getUserIds(request));
		user.setRoleCode("OPERATION_DEPARTMENT_USER");
		try {
			SysUserService.Client client = (SysUserService.Client) clientFactory
					.getClient();
			listUser = client.getAllSysUser(user);
			int count = client.getAllSysUserCount(user);
			map.put("rows", listUser);
			map.put("total", count);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(map, response);
	}
	
	/**
	 * 拒单
	 * @param bizProject
	 * @param req
	 * @param resp
	 */
	@RequestMapping(value = "refuseProject")
	public void refuseProject(BizProject bizProject, HttpServletRequest req,
			HttpServletResponse resp) {
		int pid = bizProject.getPid();
		if (pid <= 0) {
			logger.error("请求数据不合法：" + bizProject);
			fillReturnJson(resp, false, "请求数据不合法!");
			return;
		}
		BaseClientFactory clientFactory = null;
		BaseClientFactory financeFactory = null;
		try {
			//查询项目放款信息，如果项目已存在放款申请，则不能进行拒单操作
			financeFactory =getFactory(BusinessModule.MODUEL_INLOAN,
					"FinanceHandleService");
			FinanceHandleService.Client service = (FinanceHandleService.Client) financeFactory.getClient();
			FinanceHandleDTO financeHandleDTO = service.getFinanceHandleByProjectId(pid);
	        if(financeHandleDTO!= null){
	        	 int status = financeHandleDTO.getStatus();
	        	 if(status == Constants.REC_STATUS_ALREADY_LOAN || status == Constants.REC_STATUS_LOAN_NO_FINISH || status == Constants.REC_STATUS_LOAN_APPLY){
	        		 fillReturnJson(resp, false, "已存在放款申请，不允许拒单!");
	                 return;
	        	 }
	         }
	        
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG_PROJECT,
					"BizProjectService");
			BizProjectService.Client projectService = (BizProjectService.Client) clientFactory
					.getClient();
			bizProject.setExamineUser(getShiroUser().getPid());
			projectService.updateProjectReject(bizProject);

			recordAomLog(OrgSysLogTypeConstant.LOG_TYPE_UPDATE,
					"拒单：BizProjectController.rejectProject 参数："
							+ bizProject.getPid(), req);
			// ERP 后台日志
			recordLog(BusinessModule.MODUEL_ORG,
					SysLogTypeConstant.LOG_TYPE_UPDATE,
					"拒单:" + bizProject.getExamineOpinion(),
					bizProject.getPid());
			fillReturnJson(resp, true, "保存成功");
		} catch (Exception e) {
			fillReturnJson(resp, false, "拒单失败,请重新操作!");
			logger.error("拒单失败:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
	}
	
	/**
	 * 项目经理查看自已维护机构订单功能
	 * @return
	 */
	@RequestMapping(value = "orgProjectIndex")
	public String orgProjectIndex(){
		return PATH+"project_index";
	}
	
	/**
	 * 根据条件查询所有机构业务列表
	 * 
	 * @param query
	 * @param request
	 * @param response
	 * @author: baogang
	 * @date: 2016年7月25日 下午2:59:41
	 */
	@RequestMapping(value = "getOrgProjectList", method = RequestMethod.POST)
	public void getOrgProjectList(BizProjectDTO query,
			HttpServletRequest request, HttpServletResponse response) {
		// 需要返回的Map集合
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG_PROJECT,
					"BizProjectService");
			// 创建集合
			BizProjectService.Client projectService = (BizProjectService.Client) clientFactory
					.getClient();
			query.setUserIds(getUserIds(request));// 数据权限
			List<BizProjectDTO> list = projectService.getProjectListByPage(query);
			int count = projectService.getProjectListCount(query);
			map.put("rows", list);
			map.put("total", count);
		} catch (Exception e) {
			logger.error(" 根据条件查询所有机构业务列表:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(map, response);
	}
}
