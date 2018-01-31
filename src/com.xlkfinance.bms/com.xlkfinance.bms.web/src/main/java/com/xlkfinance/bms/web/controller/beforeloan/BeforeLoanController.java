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
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.achievo.framework.util.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApply;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyService;
import com.qfang.xk.aom.rpc.project.BizProject;
import com.qfang.xk.aom.rpc.project.BizProjectService;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.constant.SysLogTypeConstant;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.NumberUtils;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.BeforloadOutputSerice;
import com.xlkfinance.bms.rpc.beforeloan.BizLoanApprovalInvoice;
import com.xlkfinance.bms.rpc.beforeloan.BizMeetingMinutesFile;
import com.xlkfinance.bms.rpc.beforeloan.BizOriginalLoan;
import com.xlkfinance.bms.rpc.beforeloan.BizOriginalLoanService;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectArchive;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectArchiveFile;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstate;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstateService;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectMeetingDTO;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectRetreat;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectRetreatService;
import com.xlkfinance.bms.rpc.beforeloan.CalcOperDto;
import com.xlkfinance.bms.rpc.beforeloan.DataInfo;
import com.xlkfinance.bms.rpc.beforeloan.DataUploadService;
import com.xlkfinance.bms.rpc.beforeloan.ElementLendService;
import com.xlkfinance.bms.rpc.beforeloan.Loan;
import com.xlkfinance.bms.rpc.beforeloan.LoanApprovalInvoiceInfo;
import com.xlkfinance.bms.rpc.beforeloan.LoanApprovalPaperService;
import com.xlkfinance.bms.rpc.beforeloan.LoanOutputInfo;
import com.xlkfinance.bms.rpc.beforeloan.LoanOutputInfoImpl;
import com.xlkfinance.bms.rpc.beforeloan.LoanOutputInfoImplDTO;
import com.xlkfinance.bms.rpc.beforeloan.OfflineMeetingService;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectArchiveFileDTO;
import com.xlkfinance.bms.rpc.beforeloan.ProjectArchiveService;
import com.xlkfinance.bms.rpc.beforeloan.ProjectDto;
import com.xlkfinance.bms.rpc.beforeloan.ProjectForeInformation;
import com.xlkfinance.bms.rpc.beforeloan.ProjectForeclosure;
import com.xlkfinance.bms.rpc.beforeloan.ProjectGuarantee;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.beforeloan.RepaymentLoanInfo;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.contract.Contract;
import com.xlkfinance.bms.rpc.contract.ContractService;
import com.xlkfinance.bms.rpc.contract.ContractTempLate;
import com.xlkfinance.bms.rpc.contract.ContractTempLateService;
import com.xlkfinance.bms.rpc.inloan.ApplyFinanceHandleDTO;
import com.xlkfinance.bms.rpc.inloan.ApplyHandleInfoDTO;
import com.xlkfinance.bms.rpc.inloan.BizDynamic;
import com.xlkfinance.bms.rpc.inloan.BizDynamicService;
import com.xlkfinance.bms.rpc.inloan.BizHandleService;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentDTO;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentHisService;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleDTO;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleService;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleService.Client;
import com.xlkfinance.bms.rpc.inloan.HouseBalanceDTO;
import com.xlkfinance.bms.rpc.inloan.IntegratedDeptService;
import com.xlkfinance.bms.rpc.product.Product;
import com.xlkfinance.bms.rpc.product.ProductService;
import com.xlkfinance.bms.rpc.repayment.LoanExtensionService;
import com.xlkfinance.bms.rpc.repayment.ProjectExtensionView;
import com.xlkfinance.bms.rpc.repayment.RegAdvapplyFileview;
import com.xlkfinance.bms.rpc.repayment.RepaymentPlanBaseDTO;
import com.xlkfinance.bms.rpc.repayment.RepaymentService;
import com.xlkfinance.bms.rpc.repayment.UploadFileService;
import com.xlkfinance.bms.rpc.repayment.UploadinstAdvapplyBaseDTO;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.rpc.system.BizFileService;
import com.xlkfinance.bms.rpc.system.SysAreaInfo;
import com.xlkfinance.bms.rpc.system.SysAreaInfoService;
import com.xlkfinance.bms.rpc.system.SysLookupService;
import com.xlkfinance.bms.rpc.system.SysLookupVal;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.rpc.system.SysUserService;
import com.xlkfinance.bms.rpc.system.TemplateFile;
import com.xlkfinance.bms.rpc.system.TemplateFileService;
import com.xlkfinance.bms.rpc.workflow.TaskHistoryDto;
import com.xlkfinance.bms.rpc.workflow.WorkflowProjectVo;
import com.xlkfinance.bms.rpc.workflow.WorkflowService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;
import com.xlkfinance.bms.web.shiro.ShiroUser;
import com.xlkfinance.bms.web.util.CommonUtil;
import com.xlkfinance.bms.web.util.ExcelExport;
import com.xlkfinance.bms.web.util.FileDownload;
import com.xlkfinance.bms.web.util.FileUtil;
import com.xlkfinance.bms.web.util.ParseDocAndDocxUtil;

/**
 * 
 * @ClassName: BeforeLoanController
 * @Description: 贷前管理Controller
 * @author: Cai.Qing
 * @date: 2015年1月6日 下午3:18:23
 */
@Controller
@RequestMapping("/beforeLoanController")
public class BeforeLoanController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(BeforeLoanController.class);
	private ProductService.Client client;
	
	private ProductService.Client getService() {
	      BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_PRODUCT, "ProductService");
	      try {
	         client = (ProductService.Client) clientFactory.getClient();
	      } catch (ThriftException e) {
	         logger.error("ProductService获取失败：" + ExceptionUtil.getExceptionMessage(e));
	         e.printStackTrace();
	      }
	      return client;
	   }

	/**
	 * 
	 * @Description: 页面跳转方法  
	 * @return 贷前申请列表（信用）
	 * @author: Cai.Qing
	 * @date: 2015年1月6日 下午3:21:23
	 */
	@RequestMapping(value = "navigation")
	public String navigation() {
		return "beforeloan/index";
	}

	/**
	 * 赎楼项目页面跳转
	  * @return
	  * @author: Administrator
	  * @date: 2016年2月15日 下午2:55:43
	 */
	@RequestMapping(value = "navigationFore")
	public String navigationFore() {
		return "beforeloan/navigation_index";
	}
	
	/**
	 * 查询赎楼项目列表
	  * @param project
	  * @param request
	  * @param response
	  * @author: Administrator
	  * @date: 2016年2月15日 下午2:57:11
	 */
	@RequestMapping(value = "getAllForeProject", method = RequestMethod.POST)
	public void getAllForeProject(Project project,HttpServletRequest request, HttpServletResponse response) {
		// 需要返回的Map集合
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			/*int projectSource = checkUserOrg();//查询用户是万通or小科
			project.setProjectSource(projectSource);*/
			project.setUserIds(getUserIds(request));//数据权限
			project.setRecordClerkId(getShiroUser().getPid());//查询录单员为登录人的业务
			project.setIsChechan(0);//未撤单
			project.setProductType(2);//赎楼类产品
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			// 创建集合
 			List<GridViewDTO> list = client.getAllForeProjects(project);
			int count = client.getAllForeProjectCount(project);
			map.put("rows", list);
			map.put("total", count);
		} catch (Exception e) {
				logger.error("查询贷前列表:" + ExceptionUtil.getExceptionMessage(e));
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
	 * @Description: 新增/编辑页面跳转方法
	 * @return 新增/编辑页面
	 * @author: Cai.Qing
	 * @date: 2015年1月7日 上午11:35:45
	 */
	@RequestMapping(value = "addNavigation")
	public String addNavigation(@RequestParam(value = "projectId", required = false) Integer projectId,@RequestParam(value = "type", required = false)String editType, ModelMap model) {
		int type = checkUserOrg();//检查用户是否属于万通 万通=1，小科=2
		int retreatFlag = 0;
		// 创建对象
		Project project = new Project();
		SysUser sysUser = getSysUser();
		if(projectId != null && projectId >0){
			Project oldProject = new Project();
			BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			BaseClientFactory factory = null;
			BaseClientFactory SysAreaInfoFactory = null;
			SysAreaInfoFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysAreaInfoService");
			
			SysAreaInfo sysAreaInfo = new SysAreaInfo();
			String cityName = "";
			
			List<Product> resultList = new ArrayList<Product>();
			Product product = new Product();
			String productNumber = "";
			try {
				SysAreaInfoService.Client sysAreaInfoClient = (SysAreaInfoService.Client) SysAreaInfoFactory.getClient();
				ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
				project = client.getLoanProjectByProjectId(projectId);
				//根据城市编码查询城市名称
				if(!StringUtils.isEmpty(project.getAreaCode()) && project.getProjectType() == AomConstant.PROJECT_TYPE_6){
					sysAreaInfo = sysAreaInfoClient.getSysAreaInfoByCode(project.getAreaCode());
					cityName = sysAreaInfo.getAreaName();
					project.setAreaCode(cityName);
				}
				factory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizProjectRetreatService");
				BizProjectRetreatService.Client bizClient = (BizProjectRetreatService.Client) factory.getClient();
				//根据项目查询该业务是否为回撤业务，是增加被回撤项目链接
				BizProjectRetreat query = new BizProjectRetreat();
				query.setNewProjectId(projectId);
				List<BizProjectRetreat> result = bizClient.getAll(query);
				if(result !=null && result.size()>0){
					retreatFlag = 1;
					int oldProjectId = result.get(0).getOldProjectId();
					oldProject = client.getLoanProjectByProjectId(oldProjectId);
				}
				type = project.getProjectSource();
				ProductService.Client service = getService();
				product.setProductType(Constants.PRODUCT_TYPE_REDEMPTION);
				product.setStatus(1);//有效的产品
				product.setPid(project.getProductId());
				resultList.addAll(service.getAllProductList(product));
				if(resultList !=null && resultList.size() > 0){
					productNumber = resultList.get(0).getProductNumber();
					model.put("productNumber", productNumber);
				}
				
			} catch (Exception e) {
				logger.error("根据项目ID查询项目详细信息:" + ExceptionUtil.getExceptionMessage(e));
			} finally {
				destroyFactory(clientFactory);
			}
			
			model.put("oldProject", oldProject);
		}else{
			project.setProjectType(2);
			if(type != 1){
				project.setProjectSource(2);
				project.setRecordClerkId(sysUser.getPid());
				project.setDeclaration(sysUser.getRealName());
			}
			
		}
		
		model.put("project", project);
		model.put("login", sysUser);
		model.put("editType", editType);
		model.put("retreatFlag", retreatFlag);
		String url = "beforeloan/loan_tab";
		//来源等于1表示万通的业务，来源等于2表示小科ERP内部录入的业务，来源等于3表示机构端提交的业务
		if(type == 1){
			url = "beforeloan/loan_tab_wantong";
		}
		return url;
	}
	
	/**
	 * 
	 * @Description: 页面跳转
	 * @return 客户信息
	 * @author: Cai.Qing
	 * @date: 2015年2月3日 下午5:06:59
	 */
	@RequestMapping(value = "navigationCustomer")
	public String navigationCustomer() {
		return "beforeloan/loan_add_before";
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
		return "beforeloan/loan_add_investigation";
	}

	/**
	 * 
	 * @Description: 页面跳转
	 * @return 合同页面
	 * @author: Cai.Qing
	 * @date: 2015年2月3日 下午3:36:44
	 */
	@RequestMapping(value = "navigationContract")
	public String navigationContract(String contractCatelog, Integer projectId, ModelMap model) {
		model.put("contractCatelog", contractCatelog);
		model.put("projectId", projectId);
		return "beforeloan/loan_add_contract";
	}

	@RequestMapping(value = "getContractListUrl")
	public void getContractListUrl(Contract contract, HttpServletResponse response) {
		// 创建集合
		List<Contract> list = new ArrayList<Contract>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CONTRACT, "ContractService");
		try {
			ContractService.Client client = (ContractService.Client) clientFactory.getClient();
			list = client.getLoanContracts(contract);
		} catch (Exception e) {
			logger.error("查询贷款合同信息失败:" + ExceptionUtil.getExceptionMessage(e));
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
	 * @Description: 借据及还款计划表
	 * @param contractCatelog
	 * @param model
	 * @return 还款计划表页面
	 * @author: zhanghg
	 * @date: 2015年2月10日 上午9:36:44
	 */
	@RequestMapping(value = "receiptRepaymentList")
	public String receiptRepaymentList(String contractCatelog, Integer loanId, Integer projectId, ModelMap model) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		try {
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			if (loanId != null && loanId > 0) {
				RepaymentLoanInfo repayment = client.getRepaymentLoanInfo(loanId);
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
		return "beforeloan/receipt_repayment_list";
	}

	/**
	 * 
	 * @Description: 页面跳转
	 * @return 资料上传
	 * @author: Cai.Qing
	 * @date: 2015年2月3日 下午3:46:44
	 */
	@RequestMapping(value = "navigationDatum")
	public String navigationDatum(int foreStatus, ModelMap model) {
		model.put("foreStatus", foreStatus);
		return "beforeloan/loan_add_datum";
	}

	/**
	 * 
	 * @Description: 获取所有合同模版
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年2月4日 下午2:54:02
	 */
	@RequestMapping(value = "getTempLateList")
	public void getTempLateList(@RequestParam(value = "templateCatelogText", required = false) String templateCatelogText, @RequestParam(value = "customerType", required = false) Integer customerType, 
			@RequestParam(value = "projectId", required = false) Integer projectId,HttpServletResponse response) {
		// 创建集合
		List<ContractTempLate> list = new ArrayList<ContractTempLate>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CONTRACT, "ContractTempLateService");
		BaseClientFactory proFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		try {
			ContractTempLate temp=new ContractTempLate();
			 temp.setPid(0);
			 temp.setComboboxTemplateText("--请选择--");
			if (!StringUtils.isEmpty(templateCatelogText)) {
				ContractTempLateService.Client client = (ContractTempLateService.Client) clientFactory.getClient();
				ProjectService.Client proClient = (ProjectService.Client) proFactory.getClient();
				
				ContractTempLate contractTempLate = new ContractTempLate();
				contractTempLate.setRows(1000);
				contractTempLate.setPage(1);
				contractTempLate.setTemplateUseMode(customerType == null ? 0 : customerType);
				contractTempLate.setTemplateCatelogText(templateCatelogText);
				if(projectId!=null && projectId>0){
					Project project=proClient.getLoanProjectByProjectId(projectId);
					int applyType=project.getProjectType();
					if(applyType==3 || applyType==4){
						applyType=2;
					}
					contractTempLate.setTemplateOwner(project.getAcctType());
					contractTempLate.setApplyType(applyType);
					contractTempLate.setCycleType(project.getCirculateType());
				}
				list.add(temp);
				list.addAll(client.pageTempLateList(contractTempLate));
			}
		} catch (Exception e) {
			logger.error("查询所有合同模版:" + ExceptionUtil.getExceptionMessage(e));
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
	 * @Description: 根据项目ID查询项目详细信息
	 * @param projectId
	 *            项目ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年2月12日 下午1:38:14
	 */
	@RequestMapping(value = "getLoanProjectByProjectId", method = RequestMethod.POST)
	public void getLoanProjectByProjectId(Integer projectId, HttpServletResponse response) {
		// 创建对象
		Project project = new Project();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		try {
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			project = client.getLoanProjectByProjectId(projectId);
		} catch (Exception e) {
			logger.error("根据项目ID查询项目详细信息:" + ExceptionUtil.getExceptionMessage(e));
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
		outputJson(project, response);
	}

	/**
	 * 
	 * @Description: 根据项目ID查询项目名称和项目编号
	 * @param projectId
	 *            项目ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年2月12日 下午3:12:45
	 */
	@RequestMapping(value = "getProjectNameOrNumber", method = RequestMethod.POST)
	public void getProjectNameOrNumber(Integer projectId, HttpServletResponse response) {
		// 创建对象
		Project project = new Project();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			project = client.getProjectNameOrNumber(projectId);
		} catch (Exception e) {
			logger.error("根据项目ID查询项目名称和项目编号:" + ExceptionUtil.getExceptionMessage(e));
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
		outputJson(project, response);
	}

	/**
	 * 
	 * @Description: 新增授信和额度详细信息
	 * @param project
	 *            项目对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年2月9日 下午4:12:55
	 */
	@RequestMapping(value = "addInformation", method = RequestMethod.POST)
	public void addInformation(Project project, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		try {
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			int rows = client.addInformation(project);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_ADD, "新增授信和额度详细信息,授信ID:" + rows);
				// 失败的话,做提示处理
				j.getHeader().put("success", true);
				j.getHeader().put("msg", rows);
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "新增失败,请重新操作!");
			}
		} catch (Exception e) {
			logger.error("新增授信和额度详细信息:" + ExceptionUtil.getExceptionMessage(e));
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
	 * @Description: 查询所有（信用类产品）贷款申请列表
	 * @param project
	 *            贷款申请对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年1月9日 上午10:54:59
	 */
	@RequestMapping(value = "getAllProject", method = RequestMethod.POST)
	public void getAllProject(Project project, HttpServletRequest request, HttpServletResponse response) {
		// 需要返回的Map集合
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			
			project.setUserIds(getUserIds(request));//数据权限
			project.setIsChechan(0);//未撤单
			project.setProductType(1);//信用类产品
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			// 创建集合
			List<Project> list = client.getAllProject(project);
			int count = client.getAllProjectCount(project);
			map.put("rows", list);
			map.put("total", count);
		} catch (Exception e) {
			logger.error("查询贷前列表:" + ExceptionUtil.getExceptionMessage(e));
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
	 * @Description: 删除贷款申请列表
	 * @param pids
	 *            贷款申请对象id
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: hezhiying
	 * @date: 2015年1月9日 上午10:54:59
	 */
	@RequestMapping(value = "deleteProject")
	public void deleteProject(String pids, HttpServletResponse response) throws Exception {
		
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		try {
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			client.batchDelete(pids);
			recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_UPDATE, "删除贷款申请列表,ids:"+pids);
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "删除失败,请重新操作！");
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
	 * @author: gaoWen
	 * @param loanProject
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "loadOutputinfo")
	public void getLoadOutput(Project loanProject, HttpServletResponse response, ModelMap model) {
		LoanOutputInfoImpl loan = new LoanOutputInfoImpl();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BeforloadOutputSerice");
			BeforloadOutputSerice.Client client = (BeforloadOutputSerice.Client) clientFactory.getClient();
			loan = client.getLoadOutputListImpl(loanProject);
		} catch (Exception e) {
			logger.error("查询贷前信息:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(loan, response);

	}

	/**
	 * 类描述： 插入放款信息 创建人：gaoWen 创建时间：2015年2月9日 上午10:13:47
	 *
	 * 修改时间：2015年2月9日 上午10:13:47 修改备注：
	 * 
	 * @version
	 * @param
	 * @return
	 */
	@RequestMapping(value = "insertLoadOutputinfo")
	@ResponseBody
	public void insertLoadOutputinfo(LoanOutputInfoImplDTO loanOutputInfoImplDTO, HttpServletResponse response) {
		BaseClientFactory clientFactory = null;
		int sta = 0;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BeforloadOutputSerice");
			BeforloadOutputSerice.Client client = (BeforloadOutputSerice.Client) clientFactory.getClient();
			ShiroUser s = getShiroUser();
			loanOutputInfoImplDTO.setFtUserId(s.getPid());
			loanOutputInfoImplDTO.setCreateDate(DateUtil.format(DateUtil.getToDay()));
			loanOutputInfoImplDTO.setCreateUser(s.getPid()+"");
			sta = client.insertLoadOutputinfo(loanOutputInfoImplDTO);
		} catch (Exception e) {
			logger.error("查询贷前列表:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		// 1代表成功0代表失败
		outputJson(sta, response);
	}

	/**
	 * @author: gaoWen
	 * @param loanProject
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "loadOutputinfoList")
	@ResponseBody
	public void getLoadOutputList(int projectId, HttpServletResponse response) {
		List<LoanOutputInfo> list = new ArrayList<LoanOutputInfo>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BeforloadOutputSerice");
			BeforloadOutputSerice.Client client = (BeforloadOutputSerice.Client) clientFactory.getClient();
			list = client.getLoadOutputList(projectId);
		} catch (Exception e) {
			logger.error("查询贷前列表:" + ExceptionUtil.getExceptionMessage(e));
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
	 * @Description: 贷款试算页面跳转
	 * @return
	 * @author: zhanghg
	 * @date: 2015年2月6日 上午10:43:13
	 */
	@RequestMapping(value = "loanCalc")
	public String loanCalc(ModelMap model, @RequestParam(value = "loanId", required = false) Integer loanId) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		try {
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			RepaymentLoanInfo repayment = client.getRepaymentLoanInfo(loanId);
			model.put("repayment", repayment);
			model.put("loanId", loanId);
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
		return "beforeloan/loan_calc";
	}

	/**
	 * 
	 * @Description: 贷款试算 列表数据查询
	 * @param loanId
	 * @param response
	 * @author: zhanghg
	 * @date: 2015年2月6日 上午10:43:13
	 */
	@RequestMapping(value = "repaymentListUrl")
	public void repaymentListUrl(Integer loanId, HttpServletResponse response) {
		// 创建集合
		List<RepaymentPlanBaseDTO> list = Lists.newArrayList();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
		try {
			if(loanId != null && loanId>0){
				RepaymentPlanBaseDTO dto = new RepaymentPlanBaseDTO();
				dto.setLoanInfoId(loanId);
				dto.setFreezeStatus(0);
				RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
				list = client.selectRepaymentPlanBaseDTO(dto);
			}
		} catch (Exception e) {
			logger.error("查询还款计划表列表:" + ExceptionUtil.getExceptionMessage(e));
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
	 * @Description: 修改还款计划表
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author: zhanghg
	 * @date: 2015年2月6日 下午3:39:36
	 */
	@ResponseBody
	@RequestMapping(value = "/saveRepaymentPlan")
	public void saveRepaymentPlan(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String saveSucc = "";

		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");

		String content = request.getParameter("content");
		content = URLDecoder.decode(content, "utf-8");
		String shouldOtherCostName = request.getParameter("shouldOtherCostName");
		shouldOtherCostName = URLDecoder.decode(shouldOtherCostName, "utf-8");
		String repayFun = request.getParameter("repayFun");
		String loanInfoId = request.getParameter("loanInfoId");

		try {
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			if ("9".equals(repayFun)) {
				List<RepaymentPlanBaseDTO> repaymentList = JSON.parseObject(content, new TypeToken<List<RepaymentPlanBaseDTO>>() {
					private static final long serialVersionUID = -6822918771393132681L;
				}.getType());

				// 移除最后一条合计
				//repaymentList.remove(repaymentList.size() - 1);

				List<RepaymentPlanBaseDTO> list = Lists.newArrayList();

				for (RepaymentPlanBaseDTO rp : repaymentList) {

					// 如果是还款计划表数据，做修改操作
					if (rp.getDataType() == 2 || rp.getDataType() == 4) {
						rp.setShouldOtherCostName(shouldOtherCostName);
						list.add(rp);
					}
				}
				client.editRepayMent(list);
			} else {
				RepaymentPlanBaseDTO rp = new RepaymentPlanBaseDTO();
				rp.setShouldOtherCostName(shouldOtherCostName);
				rp.setLoanInfoId(Integer.parseInt(loanInfoId));
				client.updateRepaymentOtherCostName(rp);
			}

			saveSucc = "saveSucc";
		} catch (Exception e) {
			saveSucc = "saveFail";
			e.printStackTrace();
			// logger.info(e.toString());
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(saveSucc, response);
	}

	/**
	 * 
	 * @Description: 文件上传
	 * @param
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年2月5日
	 */
	@RequestMapping(value = "saveFileInfo")
	public void saveFileInfo(HttpServletRequest request, HttpServletResponse response,BizProjectMeetingDTO bizProjectMeetingDTO) {
		BaseClientFactory offlineMeetingServiceClientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "OfflineMeetingService");
		BaseClientFactory bizFileServiceClientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "BizFileService");
		// 文件信息BIZ_FILE
		BizFile bizFile = new BizFile();
		// 会议纪要附件BIZ_MEETING_MINUTES_FILE
		BizMeetingMinutesFile bizMeetingMinutesFile = new BizMeetingMinutesFile();
		try {
			OfflineMeetingService.Client offlineMeetingServiceClient = (OfflineMeetingService.Client) offlineMeetingServiceClientFactory.getClient();
			BizFileService.Client bizFileServiceClient = (BizFileService.Client) bizFileServiceClientFactory.getClient();
			Map<String, Object> map = new HashMap<String, Object>();
			map = FileUtil.processFileUpload(request, response, BusinessModule.MODUEL_BEFORELOAN, getUploadFilePath(), getFileSize(), getFileDateDirectory(), getUploadFileType());
			@SuppressWarnings("rawtypes")
			List items = (List) map.get("items");
			for (int i = 0; i < items.size(); i++) {
				FileItem item = (FileItem) items.get(i);
				String fieldName = item.getFieldName();
				if ("offlineMeetingFile".equals(fieldName)) {
						if (item.getSize() != 0) {
							bizFile.setFileSize((int) item.getSize());
							// 获得文件名
							String fileFullName = item.getName().toLowerCase();
							int dotLocation = fileFullName.lastIndexOf(".");
							String fileName = fileFullName.substring(0, dotLocation);
							String fileType = fileFullName.substring(dotLocation).substring(1);
							bizFile.setFileType(fileType);
							bizFile.setFileName(fileName);
						}
				}
			}
		 
			if (bizFile.getFileSize() != 0) {
				String uploadDttm = DateUtil.format(new Date());
				bizFile.setUploadDttm(uploadDttm);

				String fileUrl = String.valueOf(map.get("path"));
				bizFile.setFileUrl(fileUrl);

				int uploadUserId = getShiroUser().getPid();
				bizFile.setUploadUserId(uploadUserId);

				bizFile.setStatus(1);

				int fileId = bizFileServiceClient.saveBizFile(bizFile);// 保存文件信息

				bizMeetingMinutesFile.setMeetingId(bizProjectMeetingDTO.getMeetingId());
				bizMeetingMinutesFile.setFileId(fileId);
				bizMeetingMinutesFile.setStatus(1);
				offlineMeetingServiceClient.saveBizMeetingMinutesFile(bizMeetingMinutesFile);// 会议纪要附件
			}
			response.getWriter().write("saveSucc");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (ThriftException e) {
			e.printStackTrace();
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			if (offlineMeetingServiceClientFactory != null) {
				try {
					offlineMeetingServiceClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}

			if (bizFileServiceClientFactory != null) {
				try {
					bizFileServiceClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 
	 * @Description: 线下决议会议纪要
	 * @param
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年2月5日
	 *//*
	@RequestMapping(value = "obtainOfflineMeetingFileInfo")
	@ResponseBody
	public void obtainOfflineMeetingFileInfo(HttpServletRequest request, HttpServletResponse response) {

		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "OfflineMeetingService");
		int projectId = Integer.parseInt(request.getParameter("projectId"));
		// projectId=0;
		try {

			OfflineMeetingService.Client client = (OfflineMeetingService.Client) clientFactory.getClient();
			List<BizFile> bizFileList = client.obtainBizFileForOfflineMeeting(projectId);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", bizFileList);
			map.put("count", "10");// 总共有多少条记录
			outputJson(map, response);
		} catch (ThriftException e) {
			e.printStackTrace();
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (TException e) {
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

	*//**
	 * 
	 * @Description: 单个删除线下决议会议纪要附件
	 * @param
	 * @return
	 * @author: pengchuntao
	 * @throws IOException
	 * @date: 2014年2月5日
	 *//*
	@RequestMapping(value = "deleteSingleMeetingFile")
	@ResponseBody
	public void deleteSingleMeetingFile(HttpServletRequest request, HttpServletResponse response, int pid) {
		deleteSingleFile(request, response, pid);
	}

	*//**
	 * 
	 * @Description: 删除多选的线下决议会议纪要附件
	 * @param
	 * @return
	 * @author: pengchuntao
	 * @throws IOException
	 * @date: 2014年2月5日
	 *//*
	@RequestMapping(value = "deleteAllSelectedMeetingFile")
	@ResponseBody
	public void deleteAllSelectedMeetingFile(HttpServletRequest request, HttpServletResponse response, @RequestBody List<String> bizFileJSON) {
		deleteAllSelectedFile(request, response, bizFileJSON);
	}
*/
	/**
	 * 
	 * @Description: 生成放款审批单
	 * @param projectId
	 * @param response
	 * @author: xuweihao
	 * @date: 2015年3月21日 上午11:27:50
	 */
	@RequestMapping(value = "makeLoanApprovalPaper")
	@ResponseBody
	public void makeLoanApprovalPaper(int projectId, HttpServletResponse response, HttpServletRequest request) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "LoanApprovalPaperService");
		BaseClientFactory c = getFactory(BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		LoanApprovalInvoiceInfo loanApprovalInvoiceInfo = new LoanApprovalInvoiceInfo();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			TemplateFileService.Client cl = (TemplateFileService.Client) c.getClient();
			LoanApprovalPaperService.Client client = (LoanApprovalPaperService.Client) clientFactory.getClient();
			loanApprovalInvoiceInfo = client.listApprovalInvoiceInfo(projectId);
			if ("1".equals(loanApprovalInvoiceInfo.getCusType())) {
				loanApprovalInvoiceInfo.setCusType("个人■供应链□非供应链□\n企业□供应链□非供应链□");
			} else {
				loanApprovalInvoiceInfo.setCusType("个人□供应链□非供应链□\n企业■供应链□非供应链□");
			}
			
			//开始时间取当前时间，结束时间和每月收息日期相应改变
			
			 Calendar aCalendar = Calendar.getInstance();
		       aCalendar.setTime(new Date());
		       int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);

		       aCalendar.setTime(DateUtil.fmtStrToDate(loanApprovalInvoiceInfo.getPlanOutLoanDT()));
		       int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
		       int i= day1 - day2;
		   
		       if(i>0){
		    	   String currDate= DateUtil.format(new Date(), "yyyy-MM-dd");
			       loanApprovalInvoiceInfo.setPlanOutLoanDT(currDate);
			       loanApprovalInvoiceInfo.setPlanRepayLoanDT(DateUtil.format(DateUtil.addDay(DateUtil.fmtStrToDate(loanApprovalInvoiceInfo.getPlanRepayLoanDT()), i), "yyyy-MM-dd"));
			      
			       String[] dates= currDate.split("-");
			       loanApprovalInvoiceInfo.setRepayDate(Integer.parseInt(dates[2])-1);
		       }
		       
		      
			
			
			loanApprovalInvoiceInfo.setMonthLoanInterest(loanApprovalInvoiceInfo.getMonthLoanInterest()*1.0/100);
			loanApprovalInvoiceInfo.setMonthLoanMgr(loanApprovalInvoiceInfo.getMonthLoanMgr()*1.0/100);
			map.put("bean", loanApprovalInvoiceInfo);
			TemplateFile template = cl.getTemplateFile("FKTZ");
			
			if (!template.getFileUrl().equals("")){
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(map, path, "放款审批单"+"."+template.getFileUrl().substring(template.getFileUrl().lastIndexOf(".")+1), response, request);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setContentType("text/html;charset=UTF-8");  
			try {
				PrintWriter out = response.getWriter();
				out.println("<script>alert('生成放款审批单失败！')</script>");
			} catch (IOException e1) {
				e1.printStackTrace();
			}  
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}finally{
					if (c != null) {
						try {
							c.destroy();
						} catch (ThriftException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
		}

	}

	/**
	 * 
	 * @Description: 上传放款审批单文件
	 * @param
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年2月5日
	 */
	@RequestMapping(value = "uploadLoanApprovalPaperFile")
	public void uploadLoanApprovalPaperFile(HttpServletRequest request, HttpServletResponse response) {
		// String recordUserIdStr = request.getParameter("recordUserId");
		// String meetingDttm = request.getParameter("meetingDttm");
		// int recordUserId = offlineMeetingDTO.getRecordUserId();
		BaseClientFactory loanApprovalPaperServiceClientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "LoanApprovalPaperService");
		BaseClientFactory bizFileServiceClientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "BizFileService");

		int projectId = 0;

		// 文件信息BIZ_FILE
		BizFile bizFile = new BizFile();
		// 放款审批单信息
		BizLoanApprovalInvoice bizLoanApprovalInvoice = new BizLoanApprovalInvoice();

		try {

			LoanApprovalPaperService.Client loanApprovalPaperServiceClient = (LoanApprovalPaperService.Client) loanApprovalPaperServiceClientFactory.getClient();
			BizFileService.Client bizFileServiceClient = (BizFileService.Client) bizFileServiceClientFactory.getClient();
			Map<String, Object> map = new HashMap<String, Object>();
			map = FileUtil.processFileUpload(request, response, BusinessModule.MODUEL_BEFORELOAN, getUploadFilePath(), getFileSize(), getFileDateDirectory(), getUploadFileType());
			@SuppressWarnings("rawtypes")
			List items = (List) map.get("items");
			for (int i = 0; i < items.size(); i++) {
				FileItem item = (FileItem) items.get(i);
				String fieldName = item.getFieldName();
				if (item.isFormField()) {
					if ("loanAP_projectId".equals(fieldName)) {
						String loanAP_projectId = ParseDocAndDocxUtil.getStringCode(item);
						projectId = Integer.parseInt(loanAP_projectId.trim());
					}
				} else {
					if ("loanApprovalPaperFile".equals(fieldName)) {
						bizFile.setFileSize((int) item.getSize());
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

			int fileId = bizFileServiceClient.saveBizFile(bizFile);// 保存文件信息

			// 保存放款审批单，BIZ_LOAN_APPROVAL_INVOICE
			bizLoanApprovalInvoice.setFileId(fileId);
			// bizLoanApprovalInvoice.setLoanId(loanId);//在service层设置
			bizLoanApprovalInvoice.setStatus(1);
			loanApprovalPaperServiceClient.saveBizLoanApprovalInvoiceByProjectId(projectId, bizLoanApprovalInvoice);

			response.getWriter().write("saveSucc");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (ThriftException e) {
			e.printStackTrace();
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			destroyFactory(bizFileServiceClientFactory,loanApprovalPaperServiceClientFactory);
		}

	}

	/**
	 * 
	 * @Description: 获得所有放款审批单的文件信息
	 * @param
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年2月5日
	 */
	@RequestMapping(value = "obtainLoanApprovalPaperFileInfo")
	@ResponseBody
	public void obtainLoanApprovalPaperFileInfo(HttpServletRequest request, HttpServletResponse response) {

		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "LoanApprovalPaperService");
		int projectId = Integer.parseInt(request.getParameter("projectId"));
		// projectId=0;
		try {

			LoanApprovalPaperService.Client client = (LoanApprovalPaperService.Client) clientFactory.getClient();
			List<BizFile> bizFileList = client.obtainLoanApprovalPaperFile(projectId);
			int count = client.obtainLoanApprovalPaperFileTotal(projectId);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", bizFileList);
			map.put("count", count);// 总共有多少条记录
			outputJson(map, response);
		} catch (ThriftException e) {
			e.printStackTrace();
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			destroyFactory(clientFactory);
		}
	}

	/**
	 * 
	 * @Description: 单个删除放款审批单附件
	 * @param
	 * @return
	 * @author: pengchuntao
	 * @throws IOException
	 * @date: 2014年2月5日
	 */
	@RequestMapping(value = "deleteSingleLoanApprovalPaperFile")
	@ResponseBody
	private void deleteSingleLoanApprovalPaperFile(HttpServletRequest request, HttpServletResponse response, int pid) {
		deleteSingleFile(request, response, pid);
	}

	/**
	 * 
	 * @Description: 删除多选的放款审批单附件
	 * @param
	 * @return
	 * @author: pengchuntao
	 * @throws IOException
	 * @date: 2014年2月5日
	 */
	@RequestMapping(value = "deleteAllSelectedLoanApprovalPaperFile")
	@ResponseBody
	public void deleteAllSelectedLoanApprovalPaperFile(HttpServletRequest request, HttpServletResponse response, @RequestBody List<String> bizFileJSON) {
		deleteAllSelectedFile(request, response, bizFileJSON);
	}

	/**
	 * 
	 * @Description: 单个删除附件通用方法
	 * @param
	 * @return
	 * @author: pengchuntao
	 * @throws IOException
	 * @date: 2014年2月5日
	 */
	private void deleteSingleFile(HttpServletRequest request, HttpServletResponse response, int pid) {

		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "BizFileService");

		try {

			BizFileService.Client client = (BizFileService.Client) clientFactory.getClient();

			// 删除表中文件配置信息
			client.deleteBizFileByPid(pid);

			// 删除磁盘上的真实文件
			String basePath = CommonUtil.getRootPath();
			// String basePath = request.getParameter("basePath");
			// basePath = basePath.replaceFirst("http", "ftp").replaceAll("/",
			// "\\\\\\\\");
			String fileUrl = request.getParameter("fileUrl");
			File deleteFile = new File(basePath + fileUrl);
			try {
				deleteFile.delete();
			} catch (Exception e) {
			}
			/*
			 * boolean isDeleteDiscFile = deleteFile.delete();
			 * if(!isDeleteDiscFile){ response.getWriter().write("delFail"); }
			 */

			response.getWriter().write("delSucc");
		} catch (ThriftException e) {
			e.printStackTrace();
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			destroyFactory(clientFactory);
		}

	}

	/**
	 * 
	 * @Description: 通用的删除多选的文件附件
	 * @param
	 * @return
	 * @author: pengchuntao
	 * @throws IOException
	 * @date: 2014年2月5日
	 */
	private void deleteAllSelectedFile(HttpServletRequest request, HttpServletResponse response, @RequestBody List<String> bizFileJSON) {

		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "BizFileService");

		try {

			BizFileService.Client client = (BizFileService.Client) clientFactory.getClient();
			// String fileUrl1 =
			// request.getSession().getServletContext().getRealPath("/") +
			// bizFile.getFileUrl();
			// String fileUrl2 = request.getContextPath() +
			// bizFile.getFileUrl();
			// String userDir = System.getProperty("user.dir");
			String basePath = CommonUtil.getRootPath();
			JSONArray jsonArray = (JSONArray) JSONArray.toJSON(bizFileJSON);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject h = (JSONObject) JSONObject.toJSON(jsonArray.get(i));
				BizFile bizFile = JSONArray.toJavaObject(h, BizFile.class);

				// 删除表中文件配置信息
				client.deleteBizFileByPid(bizFile.getPid());
				// 删除磁盘上的文件
				String fileUrl = bizFile.getFileUrl();
				// FileUtil.deleteUploadedFile(request,fileUrl);
				try {
					File deleteFile = new File(basePath + fileUrl);
					deleteFile.delete();
					/*
					 * boolean isDeleteDiscFile = deleteFile.delete();
					 * if(!isDeleteDiscFile){
					 * response.getWriter().write("delFail"); }
					 */
				} catch (Exception e) {
					continue;
				}

			}

			response.getWriter().write("delSucc");
		} catch (ThriftException e) {
			e.printStackTrace();
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			destroyFactory(clientFactory);
		}
	}

	/**
	 * 
	 * @Description: 单个删除单一项目归档资料附件
	 * @param
	 * @return
	 * @author: pengchuntao
	 * @throws IOException
	 * @date: 2014年2月5日
	 */
	@RequestMapping(value = "deleteSingleProjectArchiveFile")
	@ResponseBody
	public void deleteSingleProjectArchiveFile(HttpServletRequest request, HttpServletResponse response, int pid) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "BizFileService");
		BaseClientFactory projectArchiveClientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectArchiveService");

		try {
			ProjectArchiveService.Client projectArchiveClient = (ProjectArchiveService.Client) projectArchiveClientFactory.getClient();
			BizFileService.Client bizFileclient = (BizFileService.Client) clientFactory.getClient();

			// 删除BIZ_FILE表中文件配置信息
			bizFileclient.deleteBizFileByPid(pid);

			// 删除BIZ_PROJECT_ARCHIVE_FILE表中间信息
			projectArchiveClient.deleteBizProjectArchiveFileByFileId(pid);

			response.getWriter().write("delSucc");
		} catch (ThriftException e) {
			e.printStackTrace();
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			destroyFactory(clientFactory,projectArchiveClientFactory);
		}
	}

	/**
	 * 
	 * @Description: 新增单一项目资料归档信息，不包括文件附件内容
	 * @param
	 * @return
	 * @author: pengchuntao
	 * @throws IOException
	 * @date: 2014年2月5日
	 */
	// private int archiveId;
	@RequestMapping(value = "saveBizProjectArchive")
	@ResponseBody
	public void saveBizProjectArchive(HttpServletRequest request, HttpServletResponse response, BizProjectArchive bizProjectArchive) {
		BaseClientFactory projectArchiveClientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectArchiveService");
		try {
			ProjectArchiveService.Client projectArchiveClient = (ProjectArchiveService.Client) projectArchiveClientFactory.getClient();

			String uploadDttm = DateUtil.format(new Date());
			bizProjectArchive.setCreateDttm(uploadDttm);
			bizProjectArchive.setProjectId(bizProjectArchive.getArchiveProjectId());
			bizProjectArchive.setStatus(1);

			int archiveId = projectArchiveClient.saveBizProjectArchive(bizProjectArchive);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("archiveId", archiveId);
			map.put("saveSucc", "saveSucc");

			outputJson(map, response);

			// response.getWriter().write("saveSucc");
		} catch (ThriftException e) {
			e.printStackTrace();
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			destroyFactory(projectArchiveClientFactory);

		}
	}

	/**
	 * 
	 * @Description: 新增单一项目资料归档信息的文件附件内容
	 * @param
	 * @return
	 * @author: pengchuntao
	 * @throws IOException
	 * @date: 2014年2月5日
	 */
	@RequestMapping(value = "saveBizProjectArchiveFile")
	@ResponseBody
	public void saveBizProjectArchiveFile(HttpServletRequest request, HttpServletResponse response) {
		BaseClientFactory projectArchiveClientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectArchiveService");
		BaseClientFactory bizFileClientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "BizFileService");

		try {
			ProjectArchiveService.Client projectArchiveClient = (ProjectArchiveService.Client) projectArchiveClientFactory.getClient();
			BizFileService.Client bizFileClient = (BizFileService.Client) bizFileClientFactory.getClient();

			BizProjectArchiveFile bizProjectArchiveFile = new BizProjectArchiveFile();
			BizFile bizFile = new BizFile();

			int fileSize = 0;
			String fileName = "";
			String fileType = "";
			int archiveId = 0;

			Map<String, Object> map = new HashMap<String, Object>();
			map = FileUtil.processFileUpload(request, response, BusinessModule.MODUEL_BEFORELOAN, getUploadFilePath(), getFileSize(), getFileDateDirectory(), getUploadFileType());
			@SuppressWarnings("rawtypes")
			List items = (List) map.get("items");
			for (int i = 0; i < items.size(); i++) {
				FileItem item = (FileItem) items.get(i);
				String fieldName = item.getFieldName();
				if (item.isFormField()) {
					if ("fileBusType".equals(fieldName)) {
						String fileBusType = ParseDocAndDocxUtil.getStringCode(item);
						bizProjectArchiveFile.setFileBusType(Integer.parseInt(fileBusType));
					} else if ("fileRemark".equals(fieldName)) {
						String fileRemark = ParseDocAndDocxUtil.getStringCode(item);
						bizProjectArchiveFile.setFileRemark(fileRemark);
					} else if ("projectArchiveFileName".equals(fieldName)) {
						fileName = ParseDocAndDocxUtil.getStringCode(item);
						bizFile.setFileName(fileName);
					} else if ("fileArchiveId".equals(fieldName)) {
						archiveId = Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item));
					}
				} else {
					if ("projectArchiveFile".equals(fieldName)) {
						fileSize = (int) item.getSize();

						if (fileSize > 0) {
							bizFile.setFileSize((int) item.getSize());

							String fileFullName = item.getName().toLowerCase();
							int dotLocation = fileFullName.lastIndexOf(".");
							fileType = fileFullName.substring(dotLocation).substring(1);
							bizFile.setFileType(fileType);

							// 文件名从界面输入
							if (StringUtils.isEmpty(fileName)) {
								String fileNameSys = fileFullName.substring(0, dotLocation);
								bizFile.setFileName(fileNameSys);
							}
						}
					}
				}
			}

			// 表示上传了文件
			if (fileSize > 0) {
				String uploadDttm = DateUtil.format(new Date());
				bizFile.setUploadDttm(uploadDttm);

				String fileUrl = String.valueOf(map.get("path"));
				bizFile.setFileUrl(fileUrl);

				int uploadUserId = getShiroUser().getPid();
				bizFile.setUploadUserId(uploadUserId);

				bizFile.setStatus(1);

				int fileId = bizFileClient.saveBizFile(bizFile);// 保存文件信息

				// 保存信息到BIZ_PROJECT_ARCHIVE_FILE中间表
				bizProjectArchiveFile.setFileId(fileId);
				if (archiveId == 0) {
					response.getWriter().write("archiveId_is_error");
				}
				bizProjectArchiveFile.setArchiveId(archiveId);
				// bizLoanApprovalInvoice.setLoanId(loanId);//在service层设置
				bizProjectArchiveFile.setStatus(1);

				projectArchiveClient.saveBizProjectArchiveFile(bizProjectArchiveFile);

				response.getWriter().write("saveSucc");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ThriftException e) {
			e.printStackTrace();
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} finally {
			destroyFactory(bizFileClientFactory,projectArchiveClientFactory);
		}
	}

	/**
	 * 
	 * @Description: 获得单一项目资料归档信息的文件内容
	 * @param
	 * @return
	 * @author: pengchuntao
	 * @throws IOException
	 * @date: 2014年2月5日
	 */
	@RequestMapping(value = "obtainBizProjectArchiveFile")
	@ResponseBody
	public void obtainBizProjectArchiveFile(HttpServletRequest request, HttpServletResponse response) {
		BaseClientFactory projectArchiveClientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectArchiveService");
		try {
			ProjectArchiveService.Client projectArchiveClient = (ProjectArchiveService.Client) projectArchiveClientFactory.getClient();

			String archiveId = request.getParameter("archiveId_textbox");
			List<ProjectArchiveFileDTO> list = projectArchiveClient.obtainBizProjectArchiveFileByArchiveId(Integer.parseInt(archiveId));

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", list);
			map.put("count", "10");// 总共有多少条记录
			outputJson(map, response);

		} catch (ThriftException e) {
			e.printStackTrace();
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			destroyFactory(projectArchiveClientFactory);
		}
	}

	/**
	 * 
	 * @Description: 根据projectId获得该项目所有资料归档信息
	 * @param
	 * @return
	 * @author: pengchuntao
	 * @throws IOException
	 * @date: 2014年2月5日
	 */
	@RequestMapping(value = "obtainBizProjectArchiveByProjectId")
	@ResponseBody
	public void obtainBizProjectArchiveByProjectId(HttpServletRequest request, HttpServletResponse response, int projectId) {
		BaseClientFactory projectArchiveClientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectArchiveService");
		try {
			ProjectArchiveService.Client projectArchiveClient = (ProjectArchiveService.Client) projectArchiveClientFactory.getClient();
			List<BizProjectArchive> list = projectArchiveClient.obtainBizProjectArchiveByProjectId(projectId);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", list);
			map.put("count", "10");// 总共有多少条记录
			outputJson(map, response);

		} catch (ThriftException e) {
			e.printStackTrace();
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			destroyFactory(projectArchiveClientFactory);
		}
	}

	/**
	 * 
	 * @Description: 删除多选的项目资料归档
	 * @param
	 * @return
	 * @author: pengchuntao
	 * @throws IOException
	 * @date: 2014年2月5日
	 */
	@RequestMapping(value = "deleteAllSelectedProjectArchive")
	@ResponseBody
	public void deleteAllSelectedProjectArchive(HttpServletRequest request, HttpServletResponse response, @RequestBody List<String> bizProjectFileArchiveAndTimeDTOJSON) {

		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "BizFileService");
		BaseClientFactory beforeloanClientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectArchiveService");

		try {

			BizFileService.Client client = (BizFileService.Client) clientFactory.getClient();
			ProjectArchiveService.Client beforeloanClient = (ProjectArchiveService.Client) beforeloanClientFactory.getClient();
			JSONArray jsonArray = (JSONArray) JSONArray.toJSON(bizProjectFileArchiveAndTimeDTOJSON);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject h = (JSONObject) JSONObject.toJSON(jsonArray.get(i));
				BizProjectArchive bizProjectArchive = JSONArray.toJavaObject(h, BizProjectArchive.class);
				int pid = bizProjectArchive.getPid();

				// 删除BIZ_PROJECT_ARCHIVE表里面的唯一一条记录
				beforeloanClient.deleteBizProjectArchiveByPid(pid);

				// 根据唯一ARCHIVE_ID获得 所有FILE_ID ,然后删除BizFile里面文件信息
				List<ProjectArchiveFileDTO> pfDTOList = beforeloanClient.obtainBizProjectArchiveFileByArchiveId(pid);
				for (ProjectArchiveFileDTO projectArchiveFileDTO : pfDTOList) {

					/**
					 * // 删除磁盘上的文件,代码成功。但只做逻辑删除 String fileUrl =
					 * projectArchiveFileDTO.getFileUrl(); //
					 * FileUtil.deleteUploadedFile(request,fileUrl); File
					 * deleteFile = new File(basePath + fileUrl); try {
					 * deleteFile.delete(); } catch (Exception e) { continue; }
					 **/

					int fileId = projectArchiveFileDTO.getBizFilePid();
					client.deleteBizFileByPid(fileId);
				}

				// 删除BIZ_PROJECT_ARCHIVE_FILE表里面多条记录
				beforeloanClient.deleteBizProjectArchiveFileByArchiveId(pid);

			}

			response.getWriter().write("delSucc");
		} catch (ThriftException e) {
			e.printStackTrace();
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			destroyFactory(clientFactory,beforeloanClientFactory);
		}
	}

	/**
	 * 
	 * @Description: 编辑单一项目,初始化资料归档信息，不包括文件附件内容
	 * @param
	 * @return
	 * @author: pengchuntao
	 * @throws IOException
	 * @date: 2014年2月5日
	 */
	@RequestMapping(value = "initBizProjectArchive")
	@ResponseBody
	public void initBizProjectArchive(HttpServletRequest request, HttpServletResponse response, @JSONField int pid) {

		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectArchiveService");
		try {

			ProjectArchiveService.Client client = (ProjectArchiveService.Client) clientFactory.getClient();
			BizProjectArchive bizProjectArchive = client.obtainBizProjectArchiveByPID(pid);

			Map<String, Object> map = new HashMap<String, Object>();
			// map.put("isEdit", "isEdit");
			map.put("editInitSucc", "editInitSucc");
			map.put("bizProjectArchive", bizProjectArchive);

			outputJson(map, response);

			// outputJson(bizProjectArchive,response);

		} catch (ThriftException e) {
			e.printStackTrace();
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			destroyFactory(clientFactory);
		}
	}

	/**
	 * 
	 * @Description: 编辑单一项目资料归档信息，不包括文件附件内容
	 * @param
	 * @return
	 * @author: pengchuntao
	 * @throws IOException
	 * @date: 2014年2月5日
	 */
	// private int archiveId;
	@RequestMapping(value = "updateBizProjectArchive")
	@ResponseBody
	public void updateBizProjectArchive(HttpServletRequest request, HttpServletResponse response, BizProjectArchive bizProjectArchive) {
		BaseClientFactory projectArchiveClientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectArchiveService");
		try {
			ProjectArchiveService.Client projectArchiveClient = (ProjectArchiveService.Client) projectArchiveClientFactory.getClient();

			String uploadDttm = DateUtil.format(new Date());
			bizProjectArchive.setCreateDttm(uploadDttm);
			bizProjectArchive.setPid(bizProjectArchive.getProjectArchiveId());
			bizProjectArchive.setProjectId(bizProjectArchive.getArchiveProjectId());
			bizProjectArchive.setStatus(1);

			projectArchiveClient.updateBizProjectArchive(bizProjectArchive);

			response.getWriter().write("editSucc");
		} catch (ThriftException e) {
			e.printStackTrace();
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			destroyFactory(projectArchiveClientFactory);
		}
	}

	/**
	 * 
	 * @Description: 删除多选的单一项目资料归档的文件附件
	 * @param
	 * @return
	 * @author: pengchuntao
	 * @throws IOException
	 * @date: 2014年2月5日
	 */
	@RequestMapping(value = "deleteALLProjectArchiveFile")
	@ResponseBody
	public void deleteALLProjectArchiveFile(HttpServletRequest request, HttpServletResponse response, @RequestBody List<String> projectArchiveFileDTOJSON) {

		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "BizFileService");
		BaseClientFactory projectArchiveClientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectArchiveService");

		try {
			BizFileService.Client bizFileclient = (BizFileService.Client) clientFactory.getClient();
			ProjectArchiveService.Client projectArchiveClient = (ProjectArchiveService.Client) projectArchiveClientFactory.getClient();
			JSONArray jsonArray = (JSONArray) JSONArray.toJSON(projectArchiveFileDTOJSON);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject h = (JSONObject) JSONObject.toJSON(jsonArray.get(i));
				ProjectArchiveFileDTO bfDTO = JSONArray.toJavaObject(h, ProjectArchiveFileDTO.class);

				/**
				 * // 删除磁盘上的文件,只删除逻辑数据 String fileUrl = bfDTO.getFileUrl(); //
				 * FileUtil.deleteUploadedFile(request,fileUrl); File deleteFile
				 * = new File(basePath + fileUrl); boolean isDeleteDiscFile =
				 * deleteFile.delete(); if (!isDeleteDiscFile) {
				 * response.getWriter().write("delFail"); }
				 **/

				// 删除BIZ_FILE表中文件配置信息
				int fileId = bfDTO.getBizFilePid();
				bizFileclient.deleteBizFileByPid(fileId);

				// 删除BIZ_PROJECT_ARCHIVE_FILE表中间信息
				projectArchiveClient.deleteBizProjectArchiveFileByFileId(fileId);
			}

			response.getWriter().write("delSucc");
		} catch (ThriftException e) {
			e.printStackTrace();
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			destroyFactory(clientFactory,projectArchiveClientFactory);
		}
	}

	/**
	 * 
	 * @Description: 编辑单一项目的单一文件信息,初始化文件附件内容
	 * @param
	 * @return
	 * @author: pengchuntao
	 * @throws IOException
	 * @date: 2014年2月5日
	 */
	@RequestMapping(value = "initBizProjectArchiveFile")
	@ResponseBody
	public void initBizProjectArchiveFile(HttpServletRequest request, HttpServletResponse response, @JSONField int bizFilePid) {

		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectArchiveService");
		try {

			ProjectArchiveService.Client client = (ProjectArchiveService.Client) clientFactory.getClient();
			ProjectArchiveFileDTO projectArchiveFileDTO = client.obtainProjectArchiveFileDTOByFileId(bizFilePid);

			Map<String, Object> map = new HashMap<String, Object>();
			// map.put("isEdit", "isEdit");
			map.put("editInitSucc", "editInitSucc");
			map.put("projectArchiveFileDTO", projectArchiveFileDTO);

			outputJson(map, response);

			// outputJson(bizProjectArchive,response);

		} catch (ThriftException e) {
			e.printStackTrace();
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			destroyFactory(clientFactory);
		}
	}

	/**
	 * 
	 * @Description: 编辑单一项目资料归档信息的文件附件内容
	 * @param
	 * @return
	 * @author: pengchuntao
	 * @throws IOException
	 * @date: 2014年2月5日
	 */
	@RequestMapping(value = "updateBizProjectArchiveFile")
	public void updateBizProjectArchiveFile(HttpServletRequest request, HttpServletResponse response) {
		BaseClientFactory projectArchiveClientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectArchiveService");
		BaseClientFactory bizFileClientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "BizFileService");

		try {
			ProjectArchiveService.Client projectArchiveClient = (ProjectArchiveService.Client) projectArchiveClientFactory.getClient();
			BizFileService.Client bizFileClient = (BizFileService.Client) bizFileClientFactory.getClient();

			BizProjectArchiveFile bizProjectArchiveFile = new BizProjectArchiveFile();
			BizFile bizFile = new BizFile();

			int fileSize = 0;
			String fileName = "";
			String fileType = "";
			Map<String, Object> map = new HashMap<String, Object>();
			map = FileUtil.processFileUpload(request, response, BusinessModule.MODUEL_BEFORELOAN, getUploadFilePath(), getFileSize(), getFileDateDirectory(), getUploadFileType());
			@SuppressWarnings("rawtypes")
			List items = (List) map.get("items");
			for (int i = 0; i < items.size(); i++) {
				FileItem item = (FileItem) items.get(i);
				String fieldName = item.getFieldName();
				if (item.isFormField()) {
					if ("fileBusType".equals(fieldName)) {
						String fileBusType = ParseDocAndDocxUtil.getStringCode(item);
						bizProjectArchiveFile.setFileBusType(Integer.parseInt(fileBusType));
					} else if ("fileRemark".equals(fieldName)) {
						String fileRemark = ParseDocAndDocxUtil.getStringCode(item);
						bizProjectArchiveFile.setFileRemark(fileRemark);
					} else if ("projectArchiveFileName".equals(fieldName)) {
						fileName = ParseDocAndDocxUtil.getStringCode(item);
						bizFile.setFileName(fileName);
					} else if ("archiveFileId".equals(fieldName)) {
						int fileId = Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item));
						bizFile.setPid(fileId);
						bizProjectArchiveFile.setFileId(fileId);
					}
				} else {
					if ("projectArchiveFile".equals(fieldName)) {
						fileSize = (int) item.getSize();

						if (fileSize > 0) {
							bizFile.setFileSize((int) item.getSize());

							String fileFullName = item.getName().toLowerCase();
							int dotLocation = fileFullName.lastIndexOf(".");
							fileType = fileFullName.substring(dotLocation).substring(1);
							bizFile.setFileType(fileType);

							// 文件名从界面输入
							if (StringUtils.isEmpty(fileName)) {
								String fileNameSys = fileFullName.substring(0, dotLocation);
								bizFile.setFileName(fileNameSys);
							}
						}
					}
				}
			}

			// 表示上传了文件
			if (fileSize > 0) {
				String fileUrl = String.valueOf(map.get("path"));
				bizFile.setFileUrl(fileUrl);
			}

			String uploadDttm = DateUtil.format(new Date());
			bizFile.setUploadDttm(uploadDttm);

			int uploadUserId = getShiroUser().getPid();
			bizFile.setUploadUserId(uploadUserId);

			bizFile.setStatus(1);

			bizFileClient.updateBizFile(bizFile);// 保存文件信息

			// 保存信息到BIZ_PROJECT_ARCHIVE_FILE中间表
			bizProjectArchiveFile.setStatus(1);

			projectArchiveClient.updateProjectArchiveFile(bizProjectArchiveFile);

			response.getWriter().write("editSucc");

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ThriftException e) {
			e.printStackTrace();
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} finally {
			destroyFactory(bizFileClientFactory,projectArchiveClientFactory);
		}
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
	public void fileList(@RequestParam("projectId") int projectId, @RequestParam("page") int page, @RequestParam("rows") int rows, HttpServletResponse response) {
		BaseClientFactory c = null;
		List<DataInfo> list = new ArrayList<DataInfo>();
		DataInfo dinfo = new DataInfo();
		dinfo.setProjectId(projectId);
		dinfo.setPage(page);
		dinfo.setRows(rows);
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = getFactory(
				BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		try {
			c = getFactory(BusinessModule.MODUEL_BEFORELOAN, "DataUploadService");
			DataUploadService.Client dataService = (DataUploadService.Client) c.getClient();
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			Project project = client.getLoanProjectByProjectId(projectId);
			
			List<String> projectTypes = new ArrayList<String>();
			// 如果是房抵贷项目，则取8
			if (project.getProjectType() == Constants.MORTGAGE_LOAN_PROJECT_TYPE) {
				projectTypes.add("8");
			//如果是消费贷项目，则取10
			}else if (project.getProjectType() == Constants.CONSUMER_LOAN_PROJECT_TYPE) {
				projectTypes.add("10");
			} else {
				projectTypes.add("1");
				//判断项目交易类型，13755为交易类型，13756为非交易类型
				if(project.getBusinessCategory() == 13755){
					projectTypes.add("4");//1与4表示数据字典中文件类型的数值，数值1和4的文件为交易类型的文件
				}else if(project.getBusinessCategory() == 13756){
					projectTypes.add("3");//1与3表示数据字典中文件类型的数值，数值1和3的文件为非交易类型的文件
				}
			}
			// 获取项目类型
			dinfo.setProjectTypes(projectTypes);
			list = dataService.dataListByType(dinfo);
			int total = dataService.getDataTotalByType(dinfo);
			map.put("total", total);
			map.put("rows", list);
			outputJson(map, response);
		} catch (Exception e) {
			logger.error("查询资料列表出错",e);
		} finally {
			destroyFactory(c);
		}
	}

	/**
	 * 
	 * @Description: 资料删除
	 * @param pidArray
	 * @param response
	 * @author: xuweihao
	 * @date: 2015年3月18日 下午2:14:46
	 */
	@RequestMapping(value = "delData")
	@ResponseBody
	public void delData(@RequestParam("pidArray") String pidArray, HttpServletResponse response) {
		boolean del = true;
		BaseClientFactory c = null;
		Map<String, Object> tojson = new HashMap<String, Object>();
		try {
			c = getFactory(BusinessModule.MODUEL_BEFORELOAN, "DataUploadService");
			DataUploadService.Client dataService = (DataUploadService.Client) c.getClient();
			del = dataService.deleteData(pidArray);
			if (del) {
				tojson.put("delStatus", "删除资料成功");
			} else {
				tojson.put("delStatus", "删除资料失败");
			}
		} catch (Exception e) {
			logger.error("删除资料列表出错",e);
		} finally {
			destroyFactory(c);
		}
		outputJson(tojson, response);
	}

	/**
	 * 
	 * @Description: 重新上传资料
	 * @param request
	 * @param response
	 * @author: xuweihao
	 * @date: 2015年3月18日 下午2:15:04
	 */
	@RequestMapping(value = "editData")
	@ResponseBody
	public void editData(HttpServletRequest request, HttpServletResponse response) {
		int count = 0;
		int fileSize = 0;
		String fileName = null;
		String fileType = null;
		BaseClientFactory c = null;
		BaseClientFactory bizFileClientFactory = null;
		// 资料信息dataInfo
		DataInfo dataInfo = new DataInfo();
		// 文件信息BIZ_FILE
		BizFile bizFile = new BizFile();
		Map<String, Object> tojson = new HashMap<String, Object>();
		try {
			c = getFactory(BusinessModule.MODUEL_BEFORELOAN, "DataUploadService");
			bizFileClientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "BizFileService");
			DataUploadService.Client dataService = (DataUploadService.Client) c.getClient();
			BizFileService.Client bizFileClient = (BizFileService.Client) bizFileClientFactory.getClient();
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				Map<String, Object> map = new HashMap<String, Object>();
				// 资料上传得到的信息集合
				map = FileUtil.processFileUpload(request, response, BusinessModule.MODUEL_BEFORELOAN, getUploadFilePath(), getFileSize(), getFileDateDirectory(), getUploadFileType());
				if ((Boolean) map.get("flag") == false) {
					logger.error("上传出错");
					tojson.put("uploadStatus", "文件上传失败or文件格式不合法");
				} else {
					// 拿到当前用户
					ShiroUser shiroUser = getShiroUser();
					int userId = shiroUser.getPid();
					dataInfo.setUserId(userId);
					@SuppressWarnings("rawtypes")
					List items = (List) map.get("items");
					for (int j = 0; j < items.size(); j++) {
						FileItem item = (FileItem) items.get(j);
						// 获取其他信息
						if (item.isFormField()) {
							if (item.getFieldName().equals("dataPid")) {
								dataInfo.setDataId(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
							} else if (item.getFieldName().equals("datafileDesc")) {
								dataInfo.setFileDesc(ParseDocAndDocxUtil.getStringCode(item));
							}else if (item.getFieldName().equals("createCode")) {
								dataInfo.setCreateNode(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
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
					// 保存文件信息
					int fileId = bizFileClient.saveBizFile(bizFile);
					// 获取文件Id
					dataInfo.setFileId(fileId);
					// 保存资料表信息
					count = dataService.editData(dataInfo);
					if (count == 0) {
						tojson.put("uploadStatus", "保存资料数据失败");
					} else {
						tojson.put("uploadStatus", "保存资料数据成功");
					}
				}
			}
		} catch (Exception e) {
			logger.error("重新上传资料列表出错",e);
		} finally {
			destroyFactory(bizFileClientFactory,c);
		}
		outputJson(tojson, response);
	}

	/**
	 * 
	 * @Description: 上传资料
	 * @param request
	 * @param response
	 * @author: xuweihao
	 * @date: 2015年3月18日 下午2:15:26
	 */
	@RequestMapping(value = "saveData")
	@ResponseBody
	public void saveData(HttpServletRequest request, HttpServletResponse response) {
		int count = 0;
		int fileSize = 0;
		String fileName = null;
		String fileType = null;
		BaseClientFactory c = null;
		BaseClientFactory bizFileClientFactory = null;
		// 资料信息dataInfo
		DataInfo dataInfo = new DataInfo();
		// 文件信息BIZ_FILE
		BizFile bizFile = new BizFile();
		Map<String, Object> tojson = new HashMap<String, Object>();
		try {
			c = getFactory(BusinessModule.MODUEL_BEFORELOAN, "DataUploadService");
			bizFileClientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "BizFileService");
			DataUploadService.Client dataService = (DataUploadService.Client) c.getClient();
			BizFileService.Client bizFileClient = (BizFileService.Client) bizFileClientFactory.getClient();
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				Map<String, Object> map = new HashMap<String, Object>();
				// 资料上传得到的信息集合
				map = FileUtil.processFileUpload(request, response, BusinessModule.MODUEL_BEFORELOAN, getUploadFilePath(), getFileSize(), getFileDateDirectory(), getUploadFileType());
				if ((Boolean) map.get("flag") == false) {
					logger.error("上传出错");
					tojson.put("uploadStatus", "文件上传失败or文件格式不合法");
				} else {
					// 拿到当前用户
					ShiroUser shiroUser = getShiroUser();
					int userId = shiroUser.getPid();
					dataInfo.setUserId(userId);
					@SuppressWarnings("rawtypes")
					List items = (List) map.get("items");
					for (int j = 0; j < items.size(); j++) {
						FileItem item = (FileItem) items.get(j);
						// 获取其他信息
						if (item.isFormField()) {
							if (item.getFieldName().equals("dataProjectId")) {
								dataInfo.setProjectId(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
							} else if (item.getFieldName().equals("dataFileProperty")) {
								dataInfo.setFileProperty(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
							} else if (item.getFieldName().equals("datafileDesc")) {
								dataInfo.setFileDesc(ParseDocAndDocxUtil.getStringCode(item));
							} else if (item.getFieldName().equals("createNode")) {
								dataInfo.setCreateNode(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
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
								fileName = fileName.replaceAll(" ", "");
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
					// 保存文件信息
					int fileId = bizFileClient.saveBizFile(bizFile);
					// 获取文件Id
					dataInfo.setFileId(fileId);
					// 保存资料表信息
					count = dataService.saveData(dataInfo);
					if (count == 0) {
						tojson.put("uploadStatus", "保存资料数据失败");
					} else {
						tojson.put("uploadStatus", "保存资料数据成功");
					}
				}
			}
		} catch (Exception e) {
			logger.error("上传资料列表出错" ,e);
		} finally {
			destroyFactory(bizFileClientFactory,c);
		}
		outputJson(tojson, response);
	}

	/**
	 * 
	 * @Description: 下载资料
	 * @param request
	 * @param response
	 * @param path
	 * @author: xuweihao
	 * @date: 2015年3月18日 下午2:15:44
	 */
	@RequestMapping(value = "downloadData")
	@ResponseBody
	public void downloadData(HttpServletRequest request, HttpServletResponse response, String path ,@RequestParam(value="fileName",required=false)String fileName) {
		try {
			//fileName=new String(fileName.getBytes("ISO-8859-1"),"utf-8").trim();
			FileDownload.downloadLocalFile(response, request, path,fileName);
		} catch (IOException e) {
			logger.error("下载资料文件失败出错" ,e);
		}
	}

	/**
	 * 
	 * @Description: 根据projectId查询资料类型
	 * @param projectId
	 * @param response
	 * @author: xuweihao
	 * @date: 2015年3月30日 上午10:35:02
	 */
	@RequestMapping(value = "searchDataType")
	@ResponseBody
	public void searchDataType(int projectId, HttpServletResponse response,HttpServletRequest request) {
		BaseClientFactory c = null;
		BaseClientFactory factory = null;
		List<SysLookupVal> list = new ArrayList<SysLookupVal>();
		try {
			 String fileType = request.getParameter("fileType");
			 if(null != fileType){
				 //历史遗留问题，上传资料类型共用同一个页面loan_add_datum.jsp,要指明当前是哪个页面跳转过去的(1:监管资料 2：违约处理资料)
				 SysLookupVal val = new SysLookupVal();
				 if("1".equals(fileType)){
					 val.setLookupDesc("监管资料");
				 }else if("2".equals(fileType)){
					 val.setLookupDesc("违约处理资料");
				 }else{
					 val.setLookupDesc("其他资料");
				 }
				 
				 list.add(val);
			 }else{
				factory = getAomFactory(BusinessModule.MODUEL_ORG_PROJECT, "BizProjectService");
				BizProjectService.Client projectService = (BizProjectService.Client) factory
						.getClient();
				BizProject project = projectService.getById(projectId);
			 	String projectTypes = "";
			 	// 如果是房抵贷项目，则取8
			 	if (project.getProjectType() == Constants.MORTGAGE_LOAN_PROJECT_TYPE) {
			 		projectTypes = "8";
			 		//如果是消费贷项目，则取10
				}else if (project.getProjectType() == Constants.CONSUMER_LOAN_PROJECT_TYPE) {
					projectTypes = "10";
				} else {
					if(project.getBusinessCategory() == 13756){//非交易
						projectTypes = "1,3";//1与3表示数据字典中文件类型的数值，数值1和3的文件为非交易类型的文件
					}else if(project.getBusinessCategory() == 13755){
						projectTypes = "1,4";//1与4表示数据字典中文件类型的数值，数值1和4的文件为交易类型的文件
					}
				}
				 c = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
				 SysLookupService.Client sysLookupService = (SysLookupService.Client) c.getClient();
				 list = sysLookupService.getDataTypeByType(projectTypes);
			 }
		} catch (Exception e) {
			logger.error("根据projectId查询资料类型出错",e);
		} finally {
			destroyFactory(c);
		}
		outputJson(list, response);
	}

	/**
	 * 
	 * @Description: 上传抵制物压文件
	 * @param request
	 * @param response
	 * @author: gW
	 * @date: 2015年4月9日 下午3:29:26
	 */
	@RequestMapping(value = "uploadDivertapply")
	@ResponseBody
	public void uploadDivertapply(HttpServletRequest request, HttpServletResponse response) {
		UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO = new UploadinstAdvapplyBaseDTO();
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		ShiroUser shiroUser = getShiroUser();
		int userId = shiroUser.getPid();
		String path = null;
		Map<String, Object> tojson = new HashMap<String, Object>();
		try {
			request.setCharacterEncoding("UTF-8");
			map = FileUtil.processFileUpload(request, response, BusinessModule.MODUEL_REPAYMENT, getUploadFilePath(), getFileSize(), getFileDateDirectory(), getUploadFileType());
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "UploadFileService");
			if ((Boolean) map.get("flag") == false) {
				logger.error("上传出错");
				tojson.put("uploadStatus", "文件上传失败or文件格式不合法");
			} else {
				@SuppressWarnings("rawtypes")
				List items = (List) map.get("items");
				for (int j = 0; j < items.size(); j++) {
					FileItem item = (FileItem) items.get(j);
					if (item.isFormField()) {
						if (item.getFieldName().equals("fileKinds")) {
							uploadinstAdvapplyBaseDTO.setFileKinds(ParseDocAndDocxUtil.getStringCode(item));
						} else if (item.getFieldName().equals("fileDesc")) {
							uploadinstAdvapplyBaseDTO.setFileDesc(ParseDocAndDocxUtil.getStringCode(item));
						} else if (item.getFieldName().equals("projectId")) {
							uploadinstAdvapplyBaseDTO.setProjectId(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
						} else if (item.getFieldName().equals("baseId")) {
							uploadinstAdvapplyBaseDTO.setBaseId(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
						}
					} else {
						if (item.getFieldName().equals("file1"))
							uploadinstAdvapplyBaseDTO.setFileSize((int) item.getSize());
					}
				}
				String uploadDttm = DateUtil.format(new Date());
				uploadinstAdvapplyBaseDTO.setFileDttm(uploadDttm);
				path = String.valueOf(map.get("path"));
				String pathname = path.split("/")[path.split("/").length - 1];
				uploadinstAdvapplyBaseDTO.setFileName(pathname);
				String fileType = pathname.substring(pathname.lastIndexOf(".")).substring(1);
				uploadinstAdvapplyBaseDTO.setFileType(fileType);
				// .substring( 0,path.length()-pathname.length()-1)
				uploadinstAdvapplyBaseDTO.setFilePath(path);
				uploadinstAdvapplyBaseDTO.setUploadUserid(userId + "");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			destroyFactory(clientFactory);
		}
		outputJson(tojson, response);
	}

	@RequestMapping("/queryAssBaseFile")
	@ResponseBody
	public void queryAssBaseFile(int baseId, HttpServletResponse response) {
		if (baseId == -1) {
			outputJson("", response);
		}
		BaseClientFactory clientFactory = null;
		List<RegAdvapplyFileview> list = new ArrayList<RegAdvapplyFileview>();
		try {

			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "UploadFileService");
			UploadFileService.Client client = (UploadFileService.Client) clientFactory.getClient();
			list = client.queryAssBaseFile(baseId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			destroyFactory(clientFactory);
		}
		outputJson(list, response);
	}

	@RequestMapping("/querytest")
	public String queryquerytest() {
		return "beforeloan/test";

	}

	@RequestMapping("/updateRepaymentPlan")
	public String updateRepaymentPlan(LoanOutputInfoImplDTO loanOutputInfoImplDTO, ModelMap model) {
		// loanMapper.updateByPrimaryKey(loan);
		BaseClientFactory clientFactory = null;
		BaseClientFactory cFactory = null;
		BaseClientFactory cFt = null;
		Loan loan = new Loan();
		Loan loanup = null;
		RepaymentLoanInfo repayment = new RepaymentLoanInfo();
		RepaymentLoanInfo verrepayment = null;
		BaseClientFactory cfy = null;
		Calendar c2=Calendar.getInstance();
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BeforloadOutputSerice");
			BeforloadOutputSerice.Client client = (BeforloadOutputSerice.Client) clientFactory.getClient();
			cFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			cFt = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client clt = (ProjectService.Client) cFt.getClient();
			ProjectService.Client ct = (ProjectService.Client) cFactory.getClient();
		    cfy = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BeforloadOutputSerice");
		    BeforloadOutputSerice.Client clty = (BeforloadOutputSerice.Client) cfy.getClient();
		   
		    //获取改变钱loan信息
			loanup=ct.getLoanInfobyProjectId(loanOutputInfoImplDTO.getPId());
			verrepayment = clt.getRepaymentLoanInfo(loanup.getPid());
			String cycleNum = clty.queryloanRepayFun(verrepayment.getRepayFun());
			// ============== 改变还款日期开始============
			//此时代表周期不是30天的（按月还款）
			if (!cycleNum.equals("-1")) {
			String outLoanDt= 	loanOutputInfoImplDTO.getPlanOutLoanDt();
			c2.setTime(DateUtil.format(outLoanDt,"yyyy-MM-dd"));
			int d=c2.get(Calendar.DAY_OF_MONTH);
			//System.out.println(d);
			int days= d==1?31:(d-1);
			loanOutputInfoImplDTO.setRepayCycleDate(days+"");
		//	System.out.println(time);
			}else{
				//2代表对日还款
				if (loanup.getEachissueOption()==2) {
					String outLoanDt= loanOutputInfoImplDTO.getPlanOutLoanDt();
					c2.setTime(DateUtil.format(outLoanDt,"yyyy-MM-dd"));
					int d1=c2.get(Calendar.DAY_OF_MONTH);
					int days1= d1==1?31:(d1-1);
					loanOutputInfoImplDTO.setRepayCycleDate(days1+"");
				}
				else {
					//1代表对日还款
					loanOutputInfoImplDTO.setRepayCycleDate(loanup.getRepayDate()+"");//固定应放repayDate字段//lv
				}
			}
			// ============== 改变还款日期结束============
			//获取用户id
			ShiroUser s = getShiroUser();
			loanOutputInfoImplDTO.setFtUserId(s.getPid());
			client.updateRepaymentPlan(loanOutputInfoImplDTO);
			  //获取改变后loan信息
			loan=ct.getLoanInfobyProjectId(loanOutputInfoImplDTO.getPId());
			
			if (null == loan) {
				logger.error("查询贷前列表出错");
			} else {
				int userId = getShiroUser().getPid();
				// 生成还款计划表
				ct.makeRepayMent(loan, userId, new CalcOperDto(0, 0, 0, 0, 0, "", 1, 0, "", 0));
				//获取计划表所需信息
				repayment = clt.getRepaymentLoanInfo(loan.getPid());
				model.put("loanId", loan.getPid());
			}
		} catch (Exception e) {
			logger.error("查询贷前列表:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory,cFt,cFactory,cfy);
		}
		model.put("repayment", repayment);
		return "beforeloan/loan_calc";
	}
	/**
	 * 
	 * @Description: 算贷款结束日期
	 * @author: GW
	 * @date: 2015年3月18日 下午2:14:46
	 */
	@RequestMapping(value = "queryloanEndDtInfo")
	@ResponseBody
	public void queryloanEndDtInfo(int loanId,String loanOutDt,String realoanOutDt, HttpServletResponse response) {
		BaseClientFactory c = null;
		BaseClientFactory cf = null;
		BaseClientFactory cfy = null;
		  Calendar c2=Calendar.getInstance();
		  Calendar c3=Calendar.getInstance();
		  c2.setTime(DateUtil.format(realoanOutDt,"yyyy-MM-dd"));
		  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String st="";
		String enDate=null;
		RepaymentLoanInfo repayment =null;
		try {
			//判断不是整月一期的情况
			cf = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client clt = (ProjectService.Client) cf.getClient();
			repayment = clt.getRepaymentLoanInfo(loanId);
			
		   cfy = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BeforloadOutputSerice");
			BeforloadOutputSerice.Client clty = (BeforloadOutputSerice.Client) cfy.getClient();
		   String cycleNum = clty.queryloanRepayFun(repayment.getRepayFun());
		   c = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BeforloadOutputSerice");
			
			//代表一期30天
			if(cycleNum.equals("-1")){
		   BeforloadOutputSerice.Client dataService = (BeforloadOutputSerice.Client) c.getClient();
			st=dataService.queryloanEndDtInfo(loanId,loanOutDt);
			if (st.split(",")[1].equals("0")) {
				c2.add(Calendar.MONTH,Integer.parseInt( st.split(",")[0]));
				c2.add(Calendar.DAY_OF_MONTH, -1);
				enDate = format.format(c2.getTime());
			}else {
				c2.add(Calendar.MONTH,Integer.parseInt( st.split(",")[0])-2);
				c2.add(Calendar.DAY_OF_MONTH, Integer.parseInt(st.split(",")[1])-1);
				enDate = format.format(c2.getTime());
			}
			}
			else{
			String loanRepayDt=	repayment.getLoanRepayDt();
	        int days=	DateUtil.getDaysInterval(DateUtil.format(loanRepayDt,"yyyy-MM-dd"), DateUtil.format(loanOutDt,"yyyy-MM-dd"));
	        c3.setTime(DateUtil.format(realoanOutDt,"yyyy-MM-dd"));
	        c3.add(Calendar.DAY_OF_MONTH, days);
	        enDate = format.format(c3.getTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			destroyFactory(c,cf,cfy);
		}   
		outputJson(enDate, response);
	}
	
	
	/**
	 * 
	  * @Description: 获取项目类型
	  * @param projectId
	  * @param response
	  * @author: xuweihao
	  * @date: 2015年5月22日 下午5:06:12
	 */
	@RequestMapping(value="getProjectType")
	public void getProjectType(int projectId,HttpServletResponse response){
		BaseClientFactory c = null;
		Project project= new Project();
		try {
			c = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) c.getClient();
			project = client.getProjectInfoById(projectId);
		} catch (Exception e) {
			logger.error("获取项目类型出错" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
		} finally {
			destroyFactory(c);
		}
		outputJson(project, response);
	}

	 /**
	  * 
	   * @Description: 获取授信开始结束时间
	   * @param projectId
	   * @param response
	   * @author: Rain.Lv
	   * @date: 2015年5月28日 下午5:52:50
	  */
	@RequestMapping(value = "getProjectCredtiEndDt", method = RequestMethod.POST)
	public void getProjectCredtiEndDt(Integer projectId, HttpServletResponse response) {
		// 创建对象
		Project project = new Project();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		try {
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			project = client.getProjectCredtiEndDt(projectId);
		} catch (Exception e) {
			logger.error("获取授信开始结束时间:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(project, response);
	}
	 /**
	  * 
	   * @Description: 判断同一个客户的项目是否重复
	   * @param projectId
	   * @param response
	   * @author: Rain.Lv
	   * @date: 2015年5月28日 下午5:52:50
	  */
	@RequestMapping(value = "getCheckAcctProject", method = RequestMethod.POST)
	public void getCheckAcctProject(Project project, HttpServletResponse response) {
		// 创建对象
		Project resultProject =new Project();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		try {
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			resultProject = client.getCheckAcctProject(project);
		} catch (Exception e) {
			logger.error("判断同一个客户的项目是否重复出错:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		
		outputJson(resultProject, response);
	}
	
	/**
	 * 
	 * @Description: 新增授信和额度详细信息（信用）
	 * @param project
	 *            项目对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年2月9日 下午4:12:55
	 */
	@RequestMapping(value = "addGuarantee", method = RequestMethod.POST)
	public void addGuarantee(Project project, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		try {
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			int rows = client.addGuarantee(project);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_ADD, "保存担保信息,项目ID:" + rows,project.getPid());
				// 失败的话,做提示处理
				j.getHeader().put("success", true);
				j.getHeader().put("msg", rows);
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "保存担保信息失败,请重新操作!");
			}
		} catch (Exception e) {
			logger.error("保存担保信息:" + ExceptionUtil.getExceptionMessage(e));
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "保存担保信息失败,请重新操作!");
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(j, response);
	}
	
	/**
	 * 客户经理项目撤单
	 *@author:liangyanjun
	 *@time:2016年5月28日上午9:51:11
	 *@param project
	 *@param response
	 *@throws Exception
	 */
	@RequestMapping(value = "setProjectChechan", method = RequestMethod.POST)
   public void setProjectChechan(Project project, HttpServletResponse response) throws Exception {
      int projectId = project.getPid();
      if (projectId <= 0) {
         logger.error("请求数据不合法：" + project);
         fillReturnJson(response, false, "请输入必填项,重新操作!");
         return;
      }
      ShiroUser shiroUser = getShiroUser();
      BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
      Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, "FinanceHandleService");
      try {
         ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
         Project projectInfo = client.getProjectInfoById(projectId);
         if (shiroUser.getPid() != projectInfo.getPmUserId()) {
            fillReturnJson(response, false, "撤单只有客户经理才能进行撤单!");
            return;
         }
         if (projectInfo.getForeclosureStatus() != Constants.FORECLOSURE_STATUS_1) {
            fillReturnJson(response, false, "撤单只有待客户经理提交才能进行撤单!");
            return;
         }
         FinanceHandleDTO financeHandleDTO = service.getFinanceHandleByProjectId(projectId);
         if(financeHandleDTO!= null){
        	 int status = financeHandleDTO.getStatus();
        	 if(status == Constants.REC_STATUS_ALREADY_LOAN || status == Constants.REC_STATUS_LOAN_NO_FINISH || status == Constants.REC_STATUS_LOAN_APPLY){
        		 fillReturnJson(response, false, "已存在放款申请，不允许撤单!");
                 return;
        	 }
         }
         
         project.setChechanUserId(shiroUser.getPid());
         client.updateProjectChechan(project);
         fillReturnJson(response, true, "撤单成功");
         recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_UPDATE, "设置赎楼项目为撤单,项目ID" + projectId + "操作人:" + getShiroUser().getRealName(), projectId);
      } catch (Exception e) {
         fillReturnJson(response, false, "撤单失败,请重新操作!");
         logger.error("项目撤单失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + project);
         e.printStackTrace();
      }finally{
    	  destroyFactory(clientFactory);
      }
   }
	
	/**
	 * 运营经理项目撤单
	 *@author:liangyanjun
	 *@time:2016年5月28日上午9:51:11
	 *@param project
	 *@param response
	 *@throws Exception
	 */
	@RequestMapping(value = "setChechanByBusiness", method = RequestMethod.POST)
   public void setChechanByBusiness(Project project, HttpServletResponse response) throws Exception {
      int projectId = project.getPid();
      if (projectId <= 0) {
         logger.error("请求数据不合法：" + project);
         fillReturnJson(response, false, "请输入必填项,重新操作!");
         return;
      }
      ShiroUser shiroUser = getShiroUser();
      BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
      Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, "FinanceHandleService");
      try {
         ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
         Project projectInfo = client.getProjectInfoById(projectId);
         if (projectInfo.getForeclosureStatus() != Constants.FORECLOSURE_STATUS_10) {
            fillReturnJson(response, false, "只有已审批状态才能进行撤单!");
            return;
         }
         FinanceHandleDTO financeHandleDTO = service.getFinanceHandleByProjectId(projectId);
         if(financeHandleDTO!= null){
        	 int status = financeHandleDTO.getStatus();
        	 if(status == Constants.REC_STATUS_ALREADY_LOAN || status == Constants.REC_STATUS_LOAN_NO_FINISH || status == Constants.REC_STATUS_LOAN_APPLY){
        		 fillReturnJson(response, false, "已存在放款申请，不允许撤单!");
                 return;
        	 }
         }
         
         project.setChechanUserId(shiroUser.getPid());
         client.updateProjectChechan(project);
         fillReturnJson(response, true, "撤单成功");
         recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_UPDATE, "设置赎楼项目为撤单,项目ID" + projectId + "操作人:" + getShiroUser().getRealName(), projectId);
        
      } catch (Exception e) {
         fillReturnJson(response, false, "撤单失败,请重新操作!");
         logger.error("项目撤单失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + project);
         e.printStackTrace();
      }finally{
    	  destroyFactory(clientFactory);
      }
   }
	
	/**
	 * 查询项目贷款金额
	  * @param projectId
	  * @param response
	  * @throws Exception
	  * @author: Administrator
	  * @date: 2016年2月19日 下午5:32:33
	 */
	@RequestMapping(value = "findProjectLoanMoney")
	public void findProjectLoanMoney(Integer projectId, HttpServletResponse response) throws Exception {
		String loanMoney = "0.0";
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			loanMoney = client.findProjectLoanMoney(projectId);
			
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
	 * 查詢項目经理列表
	  * @Description: 
	  * @param request
	  * @param response
	  * @author: andrew
	  * @date: 2016年2月28日 下午7:11:08
	 */
	@RequestMapping(value = "findAcctManagerList")
	public void findAcctManagerList(HttpServletRequest request, HttpServletResponse response) {
		BaseClientFactory clientFactory = null;
		List<SysUser> userList = new ArrayList<SysUser>();
		SysUser user = new SysUser();
		user.setPid(-1);
		user.setRealName("--请选择--");
		userList.add(user);
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserService");
			SysUserService.Client client = (SysUserService.Client) clientFactory.getClient();
			
			userList.addAll(client.getAcctManagerByLogin(getUserIds(request)));

		} catch (Exception e) {
			logger.error("查詢項目经理列表:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
		outputJson(userList, response);
	}
	
	/**
	 * 出账单打印信息查询
	  * @param model
	  * @param refundFeeQuery
	  * @param request
	  * @param processDefinitionKey
	  * @return
	  * @author: andrew
	  * @date: 2016年3月6日 下午7:59:55
	 */
	@RequestMapping(value="print")
	public String print(ModelMap model, Integer projectId, HttpServletRequest request){
		ProjectDto printProjectInfo = new ProjectDto();
		BaseClientFactory clientFactory = null;
		BaseClientFactory integratedFactory = getFactory(BusinessModule.MODUEL_INLOAN, "IntegratedDeptService");
		BaseClientFactory financeFactory = getFactory(BusinessModule.MODUEL_INLOAN, "FinanceHandleService");
		BaseClientFactory bizHandleFactory = getFactory(BusinessModule.MODUEL_INLOAN, "BizHandleService");
		BaseClientFactory workFactory = getFactory(BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			printProjectInfo = client.printProjectInfo(projectId);
			model.put("project", printProjectInfo);
			
         
			int projectSource = printProjectInfo.getProjectSource();
			// 获取收费银行（收费）
			Map<String, String> collectFeeBankMap = getSysLookupValMap(
					projectSource, "COLLECT_FEE_BANK");
			// 获取放款银行（放款，赎楼）
			Map<String, String> makeLoansBankMap = getSysLookupValMap(
					projectSource, "MAKE_LOANS_BANK");
			// 查档信息
			IntegratedDeptService.Client integratedDeptService = (IntegratedDeptService.Client) integratedFactory.getClient();
			CheckDocumentDTO documentDTO = integratedDeptService.getCheckDocumentByProjectId(projectId);
			model.put("documentDTO", documentDTO);
			 //产权查询
			 BaseClientFactory CheckDocumentHisFactory = getFactory(BusinessModule.MODUEL_INLOAN, "CheckDocumentHisService");
			 CheckDocumentHisService.Client checkDocumentHisService = (CheckDocumentHisService.Client) CheckDocumentHisFactory.getClient();
	         CheckDocumentDTO documentQuery=new CheckDocumentDTO();
	         documentQuery.setProjectId(projectId);
	         documentQuery.setCheckWay(Constants.CHECK_WAY_ARTIFICIAL);
			List<CheckDocumentDTO> cdtoList = checkDocumentHisService.queryCheckDocumentHisIndex(documentQuery);
			model.put("cdtoList", cdtoList);
			// 查询咨询费和放款信息
			FinanceHandleService.Client financeHandleService = (FinanceHandleService.Client) financeFactory.getClient();
			ApplyFinanceHandleDTO applyFinanceHandleQuery = new ApplyFinanceHandleDTO();
			applyFinanceHandleQuery.setProjectId(projectId);
			applyFinanceHandleQuery.setRecProList(Arrays.asList(
					Constants.REC_PRO_1, Constants.REC_PRO_3,
					Constants.REC_PRO_5,Constants.REC_PRO_9));
			List<ApplyFinanceHandleDTO> applyFinanceHandles = financeHandleService
					.findAllApplyFinanceHandle(applyFinanceHandleQuery);
			if (applyFinanceHandles != null && !applyFinanceHandles.isEmpty()) {
				for (ApplyFinanceHandleDTO applyFinanceHandleDTO : applyFinanceHandles) {
					int recPro = applyFinanceHandleDTO.getRecPro();
					double recMoney = applyFinanceHandleDTO.getRecMoney();
					if (recMoney <= 0) {
						continue;
					}
					String recAccount = applyFinanceHandleDTO.getRecAccount();
					if (recPro == Constants.REC_PRO_1) {
						applyFinanceHandleDTO.setRecAccount(collectFeeBankMap
								.get(recAccount));
						model.put("consultFee", applyFinanceHandleDTO);
					} else if (recPro == Constants.REC_PRO_3) {
						applyFinanceHandleDTO.setRecAccount(makeLoansBankMap
								.get(recAccount));
						model.put("firstRealLoan", applyFinanceHandleDTO);
					} else if (recPro == Constants.REC_PRO_5) {
						applyFinanceHandleDTO.setRecAccount(makeLoansBankMap
								.get(recAccount));
						model.put("secondRealLoan", applyFinanceHandleDTO);
					}else if (recPro == Constants.REC_PRO_9) {
						applyFinanceHandleDTO.setRecAccount(makeLoansBankMap
								.get(recAccount));
						model.put("secondLoan", applyFinanceHandleDTO);
					}
				}
			}
			// 获取赎楼信息
			BizHandleService.Client bizHandleService = (BizHandleService.Client) bizHandleFactory.getClient();
			HouseBalanceDTO houseBalanceQuery = new HouseBalanceDTO();
			houseBalanceQuery.setProjectId(projectId);
			List<HouseBalanceDTO> houseBalances = bizHandleService
					.findAllHouseBalance(houseBalanceQuery);
			if (houseBalances != null && !houseBalances.isEmpty()) {
				for (HouseBalanceDTO hb : houseBalances) {
					if (hb.getPrincipal() <= 0) {
						continue;
					}
					String backAccount = hb.getBackAccount();
					hb.setBackAccount(makeLoansBankMap.get(backAccount));
					model.put("houseBalances" + hb.getCount(), hb);
				}
			}
   
         
			String processDefinitionKey = printProjectInfo.getLoanRequestProcess();
			
			//查询审批历史记录
	         WorkflowService.Client workClient = (WorkflowService.Client) workFactory.getClient();
	         WorkflowProjectVo vo=new WorkflowProjectVo();
	         vo.setProjectId(projectId);
	         vo.setProcessDefinitionKey(processDefinitionKey);
	         WorkflowProjectVo w = workClient.findWorkflowProject(vo);
	         if(null != w && w.getPid()>0  ){
	            String workflowTaskDefKey = workClient.getRunLastTaskKeyByWIId(w.getWorkflowInstanceId());
	            w.setWorkflowTaskDefKey(workflowTaskDefKey);
	         }
	         if (w!=null&&!StringUtil.isBlank(w.getWorkflowInstanceId())) {
	            String workflowInstanceId = w.getWorkflowInstanceId();
	            List<TaskHistoryDto> workFlowHistoryList = workClient.queryWorkFlowHistory(workflowInstanceId, 1, 100);
	            List<TaskHistoryDto> examinerCheckDtos = new ArrayList<TaskHistoryDto>();
	            List<TaskHistoryDto> riskCheckDtos = new ArrayList<TaskHistoryDto>();
	            List<TaskHistoryDto> orgManagerCheckDtos = new ArrayList<TaskHistoryDto>();
	            if(workFlowHistoryList != null){
	            	Collections.reverse(workFlowHistoryList);
	            	for(TaskHistoryDto dto:workFlowHistoryList ){
		            	String taskName = dto.getTaskName();
		            	//审查员审查
		            	if(taskName!= null && taskName.equals("审查员审查")){
		            		examinerCheckDtos.add(dto);
		            	}
		            	if(taskName!= null && taskName.equals("风控初审")){
		            		examinerCheckDtos.add(dto);
		            	}
		            	//风控总监
	            		if(taskName!= null && (taskName.equals("风控复审") || taskName.equals("风控总监审批"))){
	            			riskCheckDtos.add(dto);
	            		}
	            		//总经理
		            	if(taskName!= null && taskName.equals("总经理审批")){
		            		orgManagerCheckDtos.add(dto);
		            	}
		            }
	            }
	            
	            TaskHistoryDto examinerCheckDto = null;
	            TaskHistoryDto riskCheckDto = null;
	            TaskHistoryDto orgManagerCheckDto = null;
	            
	            if(examinerCheckDtos.size() >0){
	            	examinerCheckDto = examinerCheckDtos.get(examinerCheckDtos.size()-1);
	            }
	            if(riskCheckDtos.size() >0){
	            	riskCheckDto = riskCheckDtos.get(riskCheckDtos.size()-1);
	            }
	            if(orgManagerCheckDtos.size() >0){
	            	orgManagerCheckDto = orgManagerCheckDtos.get(orgManagerCheckDtos.size()-1);
	            }
	            
	            model.put("examinerCheckDto", examinerCheckDto);
	            model.put("riskCheckDto", riskCheckDto);
	            model.put("orgManagerCheckDto", orgManagerCheckDto);
	         }
			
		} catch (Exception e) {
			logger.error("打印出账单信息:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
		}finally{
			destroyFactory(clientFactory,workFactory,bizHandleFactory,financeFactory,integratedFactory);
		}
		
		return "beforeloan/print/fore_loan_print";
	}

	/**
	 * 出账单打印信息查询
	  * @param model
	  * @param refundFeeQuery
	  * @param request
	  * @param processDefinitionKey
	  * @return
	  * @author: andrew
	  * @date: 2016年3月6日 下午7:59:55
	 */
	@RequestMapping(value="printLoanHis")
	public String printLoanHis(ModelMap model, Integer projectId, Integer chooseType, HttpServletRequest request){
		ProjectDto printProjectInfo = new ProjectDto();
		BaseClientFactory clientFactory = null;
		BaseClientFactory integratedFactory = getFactory(BusinessModule.MODUEL_INLOAN, "IntegratedDeptService");
		BaseClientFactory financeFactory = getFactory(BusinessModule.MODUEL_INLOAN, "FinanceHandleService");
		BaseClientFactory bizHandleFactory = getFactory(BusinessModule.MODUEL_INLOAN, "BizHandleService");
		BaseClientFactory workFactory = getFactory(BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			printProjectInfo = client.printProjectInfo(projectId);
			model.put("project", printProjectInfo);
			
         
			int projectSource = printProjectInfo.getProjectSource();
			// 获取收费银行（收费）
			Map<String, String> collectFeeBankMap = getSysLookupValMap(
					projectSource, "COLLECT_FEE_BANK");
			// 获取放款银行（放款，赎楼）
			Map<String, String> makeLoansBankMap = getSysLookupValMap(
					projectSource, "MAKE_LOANS_BANK");
			// 查档信息
			IntegratedDeptService.Client integratedDeptService = (IntegratedDeptService.Client) integratedFactory.getClient();
			CheckDocumentDTO documentDTO = integratedDeptService.getCheckDocumentByProjectId(projectId);
			model.put("documentDTO", documentDTO);
			 //产权查询
			 BaseClientFactory CheckDocumentHisFactory = getFactory(BusinessModule.MODUEL_INLOAN, "CheckDocumentHisService");
			 CheckDocumentHisService.Client checkDocumentHisService = (CheckDocumentHisService.Client) CheckDocumentHisFactory.getClient();
	         CheckDocumentDTO documentQuery=new CheckDocumentDTO();
	         documentQuery.setProjectId(projectId);
	         documentQuery.setCheckWay(Constants.CHECK_WAY_ARTIFICIAL);
			List<CheckDocumentDTO> cdtoList = checkDocumentHisService.queryCheckDocumentHisIndex(documentQuery);
			model.put("cdtoList", cdtoList);
			// 查询咨询费和放款信息
			FinanceHandleService.Client financeHandleService = (FinanceHandleService.Client) financeFactory.getClient();
			ApplyFinanceHandleDTO applyFinanceHandleQuery = new ApplyFinanceHandleDTO();
			applyFinanceHandleQuery.setProjectId(projectId);
			applyFinanceHandleQuery.setRecProList(Arrays.asList(
					Constants.REC_PRO_1, Constants.REC_PRO_3,
					Constants.REC_PRO_5));
			List<ApplyFinanceHandleDTO> applyFinanceHandles = financeHandleService
					.findAllApplyFinanceHandle(applyFinanceHandleQuery);
			if (applyFinanceHandles != null && !applyFinanceHandles.isEmpty()) {
				for (ApplyFinanceHandleDTO applyFinanceHandleDTO : applyFinanceHandles) {
					int recPro = applyFinanceHandleDTO.getRecPro();
					double recMoney = applyFinanceHandleDTO.getRecMoney();
					if (recMoney <= 0) {
						continue;
					}
					String recAccount = applyFinanceHandleDTO.getRecAccount();
					if (recPro == Constants.REC_PRO_1) {
						applyFinanceHandleDTO.setRecAccount(collectFeeBankMap
								.get(recAccount));
						model.put("consultFee", applyFinanceHandleDTO);
					} else if (recPro == Constants.REC_PRO_3) {
						applyFinanceHandleDTO.setRecAccount(makeLoansBankMap
								.get(recAccount));
						model.put("firstRealLoan", applyFinanceHandleDTO);
					} else if (recPro == Constants.REC_PRO_5) {
						applyFinanceHandleDTO.setRecAccount(makeLoansBankMap
								.get(recAccount));
						model.put("secondRealLoan", applyFinanceHandleDTO);
					}
				}
			}
			// 获取赎楼信息
			BizHandleService.Client bizHandleService = (BizHandleService.Client) bizHandleFactory.getClient();
			HouseBalanceDTO houseBalanceQuery = new HouseBalanceDTO();
			houseBalanceQuery.setProjectId(projectId);
			List<HouseBalanceDTO> houseBalances = bizHandleService
					.findAllHouseBalance(houseBalanceQuery);
			if (houseBalances != null && !houseBalances.isEmpty()) {
				for (HouseBalanceDTO hb : houseBalances) {
					if (hb.getPrincipal() <= 0) {
						continue;
					}
					String backAccount = hb.getBackAccount();
					hb.setBackAccount(makeLoansBankMap.get(backAccount));
					model.put("houseBalances" + hb.getCount(), hb);
				}
			}
   
         
			String processDefinitionKey = printProjectInfo.getLoanRequestProcess();
			
			//查询审批历史记录
	         WorkflowService.Client workClient = (WorkflowService.Client) workFactory.getClient();
	         WorkflowProjectVo vo=new WorkflowProjectVo();
	         vo.setProjectId(projectId);
	         vo.setProcessDefinitionKey(processDefinitionKey);
	         WorkflowProjectVo w = workClient.findWorkflowProject(vo);
	         if(null != w && w.getPid()>0  ){
	            String workflowTaskDefKey = workClient.getRunLastTaskKeyByWIId(w.getWorkflowInstanceId());
	            w.setWorkflowTaskDefKey(workflowTaskDefKey);
	         }
	         if (w!=null&&!StringUtil.isBlank(w.getWorkflowInstanceId())) {
	            String workflowInstanceId = w.getWorkflowInstanceId();
	            List<TaskHistoryDto> workFlowHistoryList = workClient.queryWorkFlowHistory(workflowInstanceId, 1, 100);
	            List<TaskHistoryDto> examinerCheckDtos = new ArrayList<TaskHistoryDto>();
	            List<TaskHistoryDto> riskCheckDtos = new ArrayList<TaskHistoryDto>();
	            List<TaskHistoryDto> orgManagerCheckDtos = new ArrayList<TaskHistoryDto>();
	            if(workFlowHistoryList != null){
	            	Collections.reverse(workFlowHistoryList);
	            	for(TaskHistoryDto dto:workFlowHistoryList ){
		            	String taskName = dto.getTaskName();
		            	//审查员审查
		            	if(taskName!= null && taskName.equals("审查员审查")){
		            		examinerCheckDtos.add(dto);
		            	}
		            	if(taskName!= null && taskName.equals("风控初审")){
		            		examinerCheckDtos.add(dto);
		            	}
		            	//风控总监
	            		if(taskName!= null && (taskName.equals("风控复审") || taskName.equals("风控总监审批"))){
	            			riskCheckDtos.add(dto);
	            		}
	            		//总经理
		            	if(taskName!= null && taskName.equals("总经理审批")){
		            		orgManagerCheckDtos.add(dto);
		            	}
		            }
	            }
	            
	            TaskHistoryDto examinerCheckDto = null;
	            TaskHistoryDto riskCheckDto = null;
	            TaskHistoryDto orgManagerCheckDto = null;
	            
	            if(examinerCheckDtos.size() >0){
	            	examinerCheckDto = examinerCheckDtos.get(examinerCheckDtos.size()-1);
	            }
	            if(riskCheckDtos.size() >0){
	            	riskCheckDto = riskCheckDtos.get(riskCheckDtos.size()-1);
	            }
	            if(orgManagerCheckDtos.size() >0){
	            	orgManagerCheckDto = orgManagerCheckDtos.get(orgManagerCheckDtos.size()-1);
	            }
	            
	            model.put("examinerCheckDto", examinerCheckDto);
	            model.put("riskCheckDto", riskCheckDto);
	            model.put("orgManagerCheckDto", orgManagerCheckDto);
	         }
			
		} catch (Exception e) {
			logger.error("打印出账单信息:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
		}finally{
			destroyFactory(clientFactory,workFactory,bizHandleFactory,financeFactory,integratedFactory);
		}
		
		return "beforeloan/print/fore_loan_print";
	}
	
	
	/**
	 * 跳转到赎楼清单页面
	  * @param projectId
	  * @param model
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月31日 下午5:41:59
	 */
	@RequestMapping(value="navigationForeInformation")
	public String navigationForeInformation(Integer projectId, ModelMap model){
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		List<ProjectForeInformation> foreInformations = new ArrayList<ProjectForeInformation>();
		try {
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			foreInformations = client.queryForeInformations(projectId);
		} catch (Exception e) {
			logger.error("根据项目ID查询项目赎楼清单:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
		} finally {
			destroyFactory(clientFactory);
		}
		model.put("foreInformations", foreInformations);
		model.put("projectId", projectId);
		return "beforeloan/project_fore_information";
	}
	
	/**
	 * 保存赎楼清单
	  * @param foreList
	  * @param response
	  * @author: Administrator
	  * @date: 2016年3月31日 下午6:44:30
	 */
	@RequestMapping(value="saveForeInformation", method = RequestMethod.POST)
	public void saveForeInformation(Project project, HttpServletResponse response){
		logger.info("保存赎楼清单，参数"+project);
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			List<ProjectForeInformation> foreList = project.getForeList();
			int rows = client.updateProjectForeInformation(foreList);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_UPDATE, "保存赎楼清单",project.getPid());
				// 失败的话,做提示处理
				j.getHeader().put("success", true);
				j.getHeader().put("msg", rows);
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "保存失败,请重新操作!");
			}
		} catch (Exception e) {
			logger.error("保存赎楼清单:" + ExceptionUtil.getExceptionMessage(e));
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "保存失败,请重新操作!");
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(j, response);
	}
	/**
	 * 跳转到客户经理申请办理页面
	  * @param projectId
	  * @param model
	  * @return
	  * @author: Administrator
	  * @date: 2016年4月1日 上午11:30:55
	 */
	@RequestMapping(value="/navigationApplyInfo")
	public String navigationApplyInfo(Integer projectId, ModelMap model){
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_INLOAN, "BizHandleService");
		ApplyHandleInfoDTO applyHandleInfoDTO = new ApplyHandleInfoDTO();
		try {
			BizHandleService.Client client = (BizHandleService.Client) clientFactory.getClient();
			ApplyHandleInfoDTO query = new ApplyHandleInfoDTO();
			query.setProjectId(projectId);
			query.setPage(1);
			query.setRows(100);
			List<ApplyHandleInfoDTO> resultList = client.findAllApplyHandleInfo(query);
			if(resultList != null && resultList.size()>0){
				applyHandleInfoDTO = resultList.get(0);
			}
		} catch (Exception e) {
			logger.error("跳转到客户经理申请办理页面:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
		model.put("applyHandleInfo", applyHandleInfoDTO);
		model.put("projectId", projectId);
		
		return "beforeloan/project_apply_info";
	}
	
	/**
	 * 保存客户经理申请办理信息
	  * @param applyHandleInfoDTO
	  * @param response
	  * @author: Administrator
	  * @date: 2016年4月1日 上午11:30:49
	 */
	@RequestMapping(value="saveApplyInfo", method = RequestMethod.POST)
	public void saveApplyInfo(ApplyHandleInfoDTO applyHandleInfoDTO,HttpServletResponse response){
		logger.info("保存客户经理申请办理信息，参数"+applyHandleInfoDTO);
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_INLOAN, "BizHandleService");
			BizHandleService.Client client = (BizHandleService.Client) clientFactory.getClient();
			ApplyHandleInfoDTO query = new ApplyHandleInfoDTO();
			query.setProjectId(applyHandleInfoDTO.getProjectId());
			query.setPage(1);
			query.setRows(100);
			List<ApplyHandleInfoDTO> resultList = client.findAllApplyHandleInfo(query);
			boolean result = false;
			//业务申请办理数据是否存在
			applyHandleInfoDTO.setCreaterId(getShiroUser().getPid());
			
			if(resultList != null && resultList.size()>0){
				applyHandleInfoDTO.setPid(resultList.get(0).getPid());
				result = client.updateApplyHandleInfo(applyHandleInfoDTO);
			}else{
				result = client.addApplyHandleInfo(applyHandleInfoDTO);
			}
			
			if (result) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_INLOAN, SysLogTypeConstant.LOG_TYPE_UPDATE, "保存客户经理申请办理信息",applyHandleInfoDTO.getProjectId());
				// 失败的话,做提示处理
				j.getHeader().put("success", true);
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "保存失败,请重新操作!");
			}
		} catch (Exception e) {
			logger.error("保存客户经理申请办理信息:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "保存失败,请重新操作!");
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(j, response);
	}
	
	/**
	 * 检查用户是否属于万通
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月28日 下午2:37:58
	 */
	@RequestMapping(value="checkUserOrgInfo")
	public void checkUserOrgInfo(HttpServletResponse response){
		int isWantong = 2;//默认不是万通用户
		try {
			isWantong = checkUserOrg();
		} catch (Exception e) {
			logger.error("检查用户是否属于万通出错：" + ExceptionUtil.getExceptionMessage(e));
	        e.printStackTrace();
		}
		// 输出
		outputJson(isWantong, response);
	}

	/**
	 * 贷前一键保存贷款申请
	  * @param project
	  * @param response
	  * @author: baogang
	  * @date: 2016年5月5日 上午11:29:14
	 */
	@RequestMapping(value="saveProjectInfo")
	public void saveProjectInfo(Project project, HttpServletRequest request,HttpServletResponse response) {
		logger.info("贷前一键保存贷款申请，参数：" + project);
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		String productNumber = request.getParameter("productNumber");
		//有赎楼提放贷业务，借款金额等于赎楼金额加上周转金额
		if(Constants.JY_YSL_TFD.equals(productNumber) || Constants.FJY_YSL_TFD.equals(productNumber)){
			double loanMoney = project.getProjectGuarantee().getForeclosureMoney() + project.getProjectGuarantee().getTurnoverMoney();
			project.getProjectGuarantee().setLoanMoney(loanMoney);
		}
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			Project oldProject = new Project();
			if(project.getPid()<=0 && project.getPmUserId() <=0){
				// 设置当前项目经理为当前登录的用户
				project.setPmUserId(getShiroUser().getPid());
			}
			
			if(project.getPid()>0){
				oldProject = client.getLoanProjectByProjectId(project.getPid());
			}
			String str = client.saveProjectInfo(project);
			if (null != str && !"".equals(str)) {
				// 分别获取项目ID和授信ID
				String[] arr = str.split(",");
				j.getHeader().put("success", true);
				j.getHeader().put("msg", arr[0]);
				j.getHeader().put("projectName", arr[2]);
				j.getHeader().put("projectNumber", arr[3]);
				j.getHeader().put("foreclosureId", arr[4]);
				j.getHeader().put("propertyId", arr[5]);
				j.getHeader().put("guaranteeId", arr[6]);
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_ADD, "新增贷前申请,参数creditId、projectName、foreclosureId、propertyId、guaranteeId:" + str,project.getPid());
				//业务日志。主要记录修改了那些数据
				recordLogOfProjectInfo(oldProject, project);
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "保存贷款申请失败,请重新操作!");
			}
		}catch(Exception e) {
			logger.error("保存贷款申请失败：" + ExceptionUtil.getExceptionMessage(e));
	        e.printStackTrace();
	        j.getHeader().put("success", false);
			j.getHeader().put("msg", "保存贷款申请失败,请重新操作!");
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(j, response);
	}
	
	/**
	 * 项目总揽
	  * @param projectId
	  * @param model
	  * @return
	  * @author: baogang
	  * @date: 2016年5月17日 上午10:24:38
	 */
	@RequestMapping(value="getProjectOverview")
	public String getProjectOverview(Integer projectId, ModelMap model){
		// 创建对象
		Project project = new Project();
		Project oldProject = new Project();
		 String productNumber = "";
		int retreatFlag = 0;
		List<BizDynamic> dynamicList = new ArrayList<BizDynamic>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		BaseClientFactory bizFactory = getFactory(BusinessModule.MODUEL_INLOAN, "BizDynamicService");
		BaseClientFactory factory = null;
		String cityName ="";
		SysAreaInfo sysAreaInfo = new SysAreaInfo();
		BaseClientFactory SysAreaInfoFactory = null;
		SysAreaInfoFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysAreaInfoService");
		try {
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			BizDynamicService.Client bizDynamicService = (BizDynamicService.Client) bizFactory.getClient();
			SysAreaInfoService.Client sysAreaInfoClient = (SysAreaInfoService.Client) SysAreaInfoFactory.getClient();
			if(projectId != null && projectId >0){
				project = client.getLoanProjectByProjectId(projectId);
				if(!StringUtils.isEmpty(project.getAreaCode())){
					sysAreaInfo = sysAreaInfoClient.getSysAreaInfoByCode(project.getAreaCode());
					cityName = sysAreaInfo.getAreaName();
					project.setAreaCode(cityName);
				}
				
				BizDynamic bizDynamic = new BizDynamic();
				bizDynamic.setProjectId(projectId);
				bizDynamic.setPage(-1);
				dynamicList = bizDynamicService.queryBizDynamic(bizDynamic);
				
				
				factory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizProjectRetreatService");
				BizProjectRetreatService.Client bizClient = (BizProjectRetreatService.Client) factory.getClient();
				//查询是否是回撤的业务
				BizProjectRetreat query = new BizProjectRetreat();
				query.setNewProjectId(projectId);
				List<BizProjectRetreat> result = bizClient.getAll(query);
				if(result !=null && result.size()>0){
					retreatFlag = 1;
					int oldProjectId = result.get(0).getOldProjectId();
					oldProject = client.getLoanProjectByProjectId(oldProjectId);
				}
				  Product product = new Product();
			      BaseClientFactory clientFactory1 = null;
			      clientFactory1 = getFactory(BusinessModule.MODUEL_PRODUCT, "ProductService");
				  ProductService.Client client1 = (ProductService.Client) clientFactory1.getClient();
			      product = client1.findProductByProjectId(projectId);
			      //提房贷判断，交易/非交易有赎楼提房贷的有二次放款
			      productNumber = product.getProductNumber();
			}
		} catch (Exception e) {
				logger.error("根据项目ID查询项目详细信息:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory,bizFactory);
		}
		model.put("project", project);
		model.put("oldProject", oldProject);
		model.put("retreatFlag", retreatFlag);
		model.put("dynamicList", dynamicList);
	    model.put("productNumber", productNumber);
		return "beforeloan/project_over_view";
	}
	
	/**
	 * 展期项目总揽
	  * @param projectId
	  * @param model
	  * @return
	  * @author: baogang
	  * @date: 2016年5月17日 上午10:24:38
	 */
	@RequestMapping(value="toExtensionProjectOverview")
	public String toExtensionProjectOverview(Integer projectId, ModelMap model){
		// 创建对象
		Project project = new Project();
		ProjectExtensionView extension = new ProjectExtensionView();
		List<BizDynamic> dynamicList = new ArrayList<BizDynamic>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		BaseClientFactory bizFactory = getFactory(BusinessModule.MODUEL_INLOAN, "BizDynamicService");
		BaseClientFactory loanFactory = getFactory( BusinessModule.MODUEL_REPAYMENT, "LoanExtensionService");
		try {
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			BizDynamicService.Client bizDynamicService = (BizDynamicService.Client) bizFactory.getClient();
			if(projectId != null && projectId >0){
				project = client.getLoanProjectByProjectId(projectId);
				BizDynamic bizDynamic = new BizDynamic();
				bizDynamic.setProjectId(projectId);
				bizDynamic.setPage(-1);
				dynamicList = bizDynamicService.queryBizDynamic(bizDynamic);
				// 查询被展期项目的信息
				LoanExtensionService.Client loanClient = (LoanExtensionService.Client) loanFactory.getClient();
				extension = loanClient.getByProjectId(projectId);
			}
		} catch (Exception e) {
				logger.error("展期项目总揽:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory,bizFactory,loanFactory);
		}
		model.put("project", project);
		model.put("extension", extension);
		model.put("dynamicList", dynamicList);
		return "beforeloan/extension_over_view";
	}
	
	
	/**
	 * 记录修改记录，主要针对贷款金额费用等关键数据
	  * @param oldProject
	  * @param newProject
	  * @author: baogang
	  * @date: 2016年5月18日 下午4:41:57
	 */
	private void recordLogOfProjectInfo(Project oldProject,Project newProject){
		  HashMap<ProjectGuarantee._Fields, String> hashMap = new HashMap<ProjectGuarantee._Fields,String>();
	      hashMap.put(ProjectGuarantee._Fields.LOAN_MONEY, "贷款金额");
	      hashMap.put(ProjectGuarantee._Fields.GUARANTEE_FEE, "咨询费");
	      hashMap.put(ProjectGuarantee._Fields.CHARGES_SUBSIDIZED, "手续费补贴");
	      hashMap.put(ProjectGuarantee._Fields.POUNDAGE, "手续费");
	      hashMap.put(ProjectGuarantee._Fields.RECE_MONEY, "应付佣金");
	      hashMap.put(ProjectGuarantee._Fields.FEE_RATE, "费率");
	      Set<ProjectGuarantee._Fields> keySet = hashMap.keySet();
	      StringBuffer logsb=new StringBuffer();
	      
	      ProjectGuarantee oldProjectGuarantee = oldProject.getProjectGuarantee();
	      ProjectGuarantee newProjectGuarantee = newProject.getProjectGuarantee();
	      
	      ProjectForeclosure oldProjectForeclosure = oldProject.getProjectForeclosure();
	      ProjectForeclosure newProjectForeclosure = newProject.getProjectForeclosure();
	      
	      if(oldProjectGuarantee == null){
	    	  oldProjectGuarantee = new ProjectGuarantee();
	      }
	      if(oldProjectForeclosure == null){
	    	  oldProjectForeclosure = new ProjectForeclosure();
	      }
	      if(newProjectForeclosure!= null&& oldProjectForeclosure.getLoanDays() != newProjectForeclosure.getLoanDays()){
	    	  logsb.append("-贷款天数"+":("+oldProjectForeclosure.getLoanDays()+")更改为("+newProjectForeclosure.getLoanDays()+") ");
	      }
	      //检查值是否更新，更新了则写入日志
	      for (ProjectGuarantee._Fields key : keySet) {
			Object updateFieldValue = newProjectGuarantee.getFieldValue(key);//修改后的值
	         Object oldFieldValue = oldProjectGuarantee.getFieldValue(key);//原来的值
	         if ((updateFieldValue==null||StringUtil.isBlank(updateFieldValue.toString()))&&(oldFieldValue==null||StringUtil.isBlank(oldFieldValue.toString()))) {
	            continue;
	         }
	         if (updateFieldValue==null||!updateFieldValue.equals(oldFieldValue)) {
	            String fieldName = hashMap.get(key);
	            logsb.append("-"+fieldName+":("+oldFieldValue+")更改为("+updateFieldValue+") ");
	         }
	      }
	      //没有更新内容，则不写入日志
	      if (logsb.length()==0) {
	         return;
	      }
	      logsb.insert(0, " 修改 项目收费信息("+newProject.getProjectName()+"):");
	      recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_UPDATE, logsb.toString(),oldProject.getPid());
	}
	
	
	/**
	 * 审查员意见打印
	  * @param model
	  * @param refundFeeQuery
	  * @param request
	  * @param processDefinitionKey
	  * @return
	  * @author: andrew
	  * @date: 2016年3月6日 下午7:59:55
	 */
	@RequestMapping(value="printAutitorOpinion")
	public String printAutitorOpinion(ModelMap model, Integer projectId, HttpServletRequest request){
		Project projectInfo = new Project();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			projectInfo = client.getLoanProjectByProjectId(projectId);
		} catch (Exception e) {
			logger.error("审查员意见打印:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
		}
		model.put("project", projectInfo);
		return "beforeloan/print/auditor_opinion_print";
	}
	
	/**
	 * 赎楼清单打印
	  * @param model
	  * @param refundFeeQuery
	  * @param request
	  * @param processDefinitionKey
	  * @return
	  * @author: andrew
	  * @date: 2016年3月6日 下午7:59:55
	 */
	@RequestMapping(value="printForeInformation")
	public String printForeInformation(ModelMap model, Integer projectId, HttpServletRequest request){
		ProjectDto projectInfo = new ProjectDto();
		List<ProjectForeInformation> foreInformations = new ArrayList<ProjectForeInformation>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			projectInfo = client.printForeInfo(projectId);
			foreInformations = client.queryForeInformations(projectId);
		} catch (Exception e) {
			logger.error("赎楼清单打印:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
		}
		model.put("project", projectInfo);
		model.put("foreInformations", foreInformations);
		return "beforeloan/print/fore_information_print";
	}
	
	/**
	 * 查询合作机构列表信息
	  * @param response
	  * @author: baogang
	  * @date: 2016年10月17日 上午9:50:30
	 */
	@RequestMapping(value="getOrgAssetsList")
	public void getOrgAssetsList(HttpServletResponse response){
		BaseClientFactory orgFactory = null;
		List<OrgCooperatCompanyApply> list = new ArrayList<OrgCooperatCompanyApply>();
		try {
			orgFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgCooperatCompanyApplyService");
			OrgCooperatCompanyApplyService.Client client = (OrgCooperatCompanyApplyService.Client) orgFactory.getClient();
			list = client.getOrgAssetsList();
		} catch (Exception e) {
			logger.error("查询合作机构列表:" + ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(orgFactory);
		}
		outputJson(list, response);
	}
	
	/**
	 * 一键下载文件
	  * @param projectId
	  * @param projectName
	  * @param response
	  * @param request
	  * @author: baogang
	  * @date: 2016年10月18日 下午2:14:13
	 */
	@RequestMapping(value="downLoadZipFile")
	public void downLoadZipFile(int projectId,HttpServletResponse response,HttpServletRequest request){
		BaseClientFactory factory = null;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			Project project = client.getProjectNameOrNumber(projectId);
			
			factory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ElementLendService");
			ElementLendService.Client elementLendService = (ElementLendService.Client) factory.getClient();
			
			List<DataInfo> list = elementLendService.findProjectFiles(projectId);
			List<String> fileUrlList = new ArrayList<String>();
			if(list != null && list.size()>0){
				for(DataInfo dataInfo : list){
					fileUrlList.add(dataInfo.getFileUrl());
				}
			}
			String zipFilePath = zipFiles(list,response, request);
			
			FileDownload.downloadLocalFile(response, request, zipFilePath,project.getProjectName());
		} catch (Exception e) {
			logger.error("下载资料文件失败出错" ,e);
		}finally{
			destroyFactory(factory,clientFactory);
		}
	}
	
	/**
	 * 回撤业务申请
	 * @param projectId
	 * @param response
	 * @param request
	 */
	@RequestMapping(value="retreatsProject")
	public void retreatProject(int projectId,HttpServletResponse response,HttpServletRequest request){
		BaseClientFactory factory = null;
		BaseClientFactory clientFactory = null;
		Json j = super.getSuccessObj();
		try {
			factory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizProjectRetreatService");
			ProjectService.Client client = (ProjectService.Client) factory.getClient();
			BizProjectRetreatService.Client bizClient = (BizProjectRetreatService.Client) clientFactory.getClient();
			//根据项目查询该业务是否已回撤
			BizProjectRetreat query = new BizProjectRetreat();
			query.setOldProjectId(projectId);
			List<BizProjectRetreat> list = bizClient.getAll(query);
			if(list != null && list.size()>0){
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "操作失败,该业务申请已回撤，不可重复操作!");
			}else{
				int retreatUserId = getShiroUser().getPid();
				int rows =client.retreatProject(projectId, retreatUserId);
				if(rows>0){
					j.getHeader().put("success", true);
				}else{
					j.getHeader().put("success", false);
					j.getHeader().put("msg", "操作失败,该重新操作!");
				}
			}
		} catch (Exception e) {
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "操作失败,该重新操作!");
			logger.error("回撤出错" ,ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(factory,clientFactory);
		}
		outputJson(j, response);
	}
	
	/**
	 * 判断业务申请是否可回撤
	 * @param projectId
	 * @param response
	 * @param request
	 */
	@RequestMapping(value="checkRetreatProject")
	public void checkRetreatProject(int projectId,HttpServletResponse response,HttpServletRequest request){
		int flag = 0;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizProjectRetreatService");
			BizProjectRetreatService.Client bizClient = (BizProjectRetreatService.Client) clientFactory.getClient();
			//根据项目查询该业务是否已回撤
			BizProjectRetreat query = new BizProjectRetreat();
			query.setOldProjectId(projectId);
			List<BizProjectRetreat> result = bizClient.getAll(query);
			if(result != null && result.size()>0){
				flag = 1;
			}else{
				flag = 0;
			}
		} catch (Exception e) {
			logger.error("判断业务申请是否可回撤:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
		outputJson(flag, response);
	}
	
	/**
	 * 万通业务推送小科
	 * @param projectId
	 * @param response
	 * @param request
	 */
	@RequestMapping(value="updateNeedFinancial")
	public void updateNeedFinancial(int projectId,HttpServletResponse response,HttpServletRequest request){
		BaseClientFactory factory = null;
		Json j = super.getSuccessObj();
		try {
			factory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) factory.getClient();
			
			Project project = client.getProjectInfoById(projectId);
			if(project.getProjectSource() != 1){
				fillReturnJson(response, false, "此业务不属于万通，请勿进行推送!");
	            return;
			}
			//查询放款状态（1：未申请 2：申请中3：放款中 4：放款成功 5：放款失败），只有未申请状态才可以推送
			Client service = (Client) getService(BusinessModule.MODUEL_INLOAN,"FinanceHandleService");
	        FinanceHandleDTO financeHandleDTO = service.getFinanceHandleByProjectId(projectId);
	        int status = financeHandleDTO.getStatus();
			if(status == Constants.REC_STATUS_ALREADY_LOAN || status == Constants.REC_STATUS_LOAN_NO_FINISH || status == Constants.REC_STATUS_LOAN_APPLY){//status等于0或者1 表示放款数据不存在或者未进行放款申请
				fillReturnJson(response, false, "此业务正在放款申请中，不允许推送!");
	            return;
			}
			
			if(project.getIsNeedFinancial() == 2){
				fillReturnJson(response, false, "已推送小科,该勿重复操作!");
	            return;
			}else{
				project.setIsNeedFinancial(2);//需要小科资金
				int rows =client.updateProjectNeed(project );
				if(rows>0){
					j.getHeader().put("success", true);
				}else{
					j.getHeader().put("success", false);
					j.getHeader().put("msg", "操作失败,该重新操作!");
				}
			}
		} catch (Exception e) {
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "操作失败,该重新操作!");
			logger.error("推送出错" ,ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(factory);
		}
		outputJson(j, response);
	}
	
	/**
	 * 抵押贷
	 * 保存物业信息 
	 */
	@RequestMapping(value="savePledge",method = RequestMethod.POST)
	public void savePledgeInfo(BizProjectEstate projectEstate,HttpServletResponse response) {
		logger.info("保存物业信息，参数：" + projectEstate);
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizProjectEstateService");
			BizProjectEstateService.Client client = (BizProjectEstateService.Client) clientFactory.getClient();
			int userId = getShiroUser().getPid();//获取登录用户的ID
			
			//判断物业ID,小于等于0时做新增操作，大于0时做更新操作
			int houseId = projectEstate.getHouseId();
			if(houseId<=0){
				projectEstate.setCreaterId(userId);
				projectEstate.setStatus(1);//设置数据有效
				houseId = client.originInsert(projectEstate);
			}else{
				projectEstate.setUpdateId(userId);
				client.originUpdate(projectEstate);
			}
			//根据返回的物业ID判断操作是否成功
			if(houseId >0){
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "保存成功");
				j.getHeader().put("houseId", houseId);
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "保存物业信息失败,请重新操作!");
			}
		}catch(Exception e) {
			logger.error("保存物业信息失败：" + ExceptionUtil.getExceptionMessage(e));
	        e.printStackTrace();
	        j.getHeader().put("success", false);
			j.getHeader().put("msg", "保存物业信息失败,请重新操作!");
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(j, response);
	}
	
	/**
	 * 保存物业信息
	 * @param projectEstate
	 * @param response
	 */
	@RequestMapping(value="saveHouseInfo", method = RequestMethod.POST)
	public void saveHouseInfo(BizProjectEstate projectEstate,HttpServletResponse response){
		logger.info("保存物业信息，参数：" + projectEstate);
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizProjectEstateService");
			BizProjectEstateService.Client client = (BizProjectEstateService.Client) clientFactory.getClient();
			int userId = getShiroUser().getPid();//获取登录用户的ID
			
			//判断物业ID,小于等于0时做新增操作，大于0时做更新操作
			int houseId = projectEstate.getHouseId();
			if(houseId<=0){
				projectEstate.setCreaterId(userId);
				projectEstate.setStatus(1);//设置数据有效
				houseId = client.insert(projectEstate);
			}else{
				projectEstate.setUpdateId(userId);
				client.update(projectEstate);
			}
			//根据返回的物业ID判断操作是否成功
			if(houseId >0){
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "保存成功");
				j.getHeader().put("houseId", houseId);
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "保存物业信息失败,请重新操作!");
			}
		}catch(Exception e) {
			logger.error("保存物业信息失败：" + ExceptionUtil.getExceptionMessage(e));
	        e.printStackTrace();
	        j.getHeader().put("success", false);
			j.getHeader().put("msg", "保存物业信息失败,请重新操作!");
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(j, response);
	}
	
	
	/**
	 * 根据物业ID集合查询物业信息
	 * @param response
	 */
	@RequestMapping(value="getHouseList")
	public void getHouseList(String houseIds,HttpServletResponse response){
		BaseClientFactory clientFactory = null;
		List<BizProjectEstate> list = new ArrayList<BizProjectEstate>();
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizProjectEstateService");
			BizProjectEstateService.Client client = (BizProjectEstateService.Client) clientFactory.getClient();
			BizProjectEstate query = new BizProjectEstate();
			List<Integer> houseIdList = StringUtil.StringToList(houseIds);
			if(houseIdList != null && houseIdList.size()>0){
				query.setHouseIds(houseIdList);
				query.setStatus(1);//有效的
				list = client.getAll(query);
			}
		} catch (Exception e) {
			logger.error("根据物业ID集合查询物业信息:" + ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(clientFactory);
		}
		outputJson(list, response);
	}
	
	/**
	 * 根据项目ID查询物业信息
	 * @param projectId
	 * @param response
	 */
	@RequestMapping(value="getHouseByProjectId")
	public void getHouseByProjectId(Integer projectId,HttpServletResponse response){
		BaseClientFactory clientFactory = null;
		List<BizProjectEstate> list = new ArrayList<BizProjectEstate>();
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizProjectEstateService");
			BizProjectEstateService.Client client = (BizProjectEstateService.Client) clientFactory.getClient();
			list = client.getAllByProjectId(projectId);
		} catch (Exception e) {
			logger.error("根据项目ID查询物业信息:" + ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(clientFactory);
		}
		outputJson(list, response);
	}
	
	/**
	 * 抵押贷
	 * 删除物业信息
	 * @param houseIds
	 * @param response
	 */
	@RequestMapping(value="delPledge")
	public void delPledge(String houseIds,HttpServletResponse response){
		logger.info("删除物业信息，参数：" + houseIds);
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		int result = 0;
		try {
			
			List<Integer> houseIdList = StringUtil.StringToList(houseIds);
			if(houseIdList != null && houseIdList.size()>0){
				clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizProjectEstateService");
				BizProjectEstateService.Client client = (BizProjectEstateService.Client) clientFactory.getClient();
				result = client.originDelProjectEstate(houseIdList);
			}
			//根据返回的物业ID判断操作是否成功
			if(result >0){
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "操作成功");
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "操作失败,请重新操作!");
			}
		}catch(Exception e) {
			logger.error("批量删除物业信息失败：" + ExceptionUtil.getExceptionMessage(e));
	        e.printStackTrace();
	        j.getHeader().put("success", false);
			j.getHeader().put("msg", "操作失败,请重新操作!");
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(j, response);
	}
	
	/**
	 * 删除物业信息
	 * @param houseIds
	 * @param response
	 */
	@RequestMapping(value="delHouse")
	public void delHouse(String houseIds,HttpServletResponse response){
		logger.info("删除物业信息，参数：" + houseIds);
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		BaseClientFactory loanFactory = null;
		try {
			
			List<Integer> houseIdList = StringUtil.StringToList(houseIds);
			List<BizOriginalLoan> list = new ArrayList<BizOriginalLoan>();
			
			loanFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizOriginalLoanService");
			BizOriginalLoanService.Client loanClient = (BizOriginalLoanService.Client) loanFactory.getClient();
			BizOriginalLoan query = new BizOriginalLoan();
			if(houseIdList!= null && houseIdList.size() == 1){
				query.setEstateId(houseIdList.get(0));
				query.setStatus(1);//有效的
				list = loanClient.getAllByCondition(query);
			}
			if(list != null && list.size()>0){
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "操作失败,此物业已关联原贷款信息!");
			}else{
				clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizProjectEstateService");
				BizProjectEstateService.Client client = (BizProjectEstateService.Client) clientFactory.getClient();
				//页面传的ID字符串分解为list
				
				int result = 0;
				if(houseIdList != null && houseIdList.size()>0){
					result = client.delProjectEstate(houseIdList);
				}
				//根据返回的物业ID判断操作是否成功
				if(result >0){
					j.getHeader().put("success", true);
					j.getHeader().put("msg", "操作成功");
				} else {
					// 失败的话,做提示处理
					j.getHeader().put("success", false);
					j.getHeader().put("msg", "操作失败,请重新操作!");
				}
			}
		}catch(Exception e) {
			logger.error("批量删除物业信息失败：" + ExceptionUtil.getExceptionMessage(e));
	        e.printStackTrace();
	        j.getHeader().put("success", false);
			j.getHeader().put("msg", "操作失败,请重新操作!");
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(j, response);
	}
	
	/**
	 * 查询小科深圳下面所有的客户经理
	  * @Description: 
	  * @param request
	  * @param response
	  * @author: andrew
	  * @date: 2017年1月4日 下午7:11:08
	 */
	@RequestMapping(value = "getAcctManagerByOrg")
	public void getAcctManagerByOrg(HttpServletRequest request, HttpServletResponse response) {
		BaseClientFactory clientFactory = null;
		List<SysUser> userList = new ArrayList<SysUser>();
		SysUser user = new SysUser();
		user.setPid(0);
		user.setRealName("--请选择--");
		userList.add(user);
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserService");
			SysUserService.Client client = (SysUserService.Client) clientFactory.getClient();
			int orgId = 2;//查询小科深圳下面所有的客户经理
			String roleCode = "JUNIOR_ACCOUNT_MANAGER";//客户经理的角色编码
			userList.addAll(client.getUserByOrg(orgId, roleCode));
		} catch (Exception e) {
			logger.error("查询小科深圳下面所有的客户经理:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
		outputJson(userList, response);
	}
	
	/**
	 * 保存原贷款信息
	 * @param originalLoan
	 * @param response
	 */
	@RequestMapping(value="saveOriginalLoanInfo", method = RequestMethod.POST)
	public void saveOriginalLoanInfo(BizOriginalLoan originalLoan,HttpServletResponse response){
		logger.info("保存原贷款信息，参数：" + originalLoan);
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizOriginalLoanService");
			BizOriginalLoanService.Client client = (BizOriginalLoanService.Client) clientFactory.getClient();
			int userId = getShiroUser().getPid();//获取登录用户的ID
			
			//判断原贷款信息ID,小于等于0时做新增操作，大于0时做更新操作
			int originalLoanId = originalLoan.getOriginalLoanId();
			if(originalLoanId<=0){
				originalLoan.setCreaterId(userId);
				originalLoan.setStatus(1);//设置数据有效
				originalLoanId = client.insert(originalLoan);
			}else{
				originalLoan.setUpdateId(userId);
				client.update(originalLoan);
			}
			//根据返回的原贷款ID判断操作是否成功
			if(originalLoanId >0){
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "保存成功");
				j.getHeader().put("originalLoanId", originalLoanId);
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "保存原贷款信息失败,请重新操作!");
			}
		}catch(Exception e) {
			logger.error("保存原贷款信息失败：" + ExceptionUtil.getExceptionMessage(e));
	        e.printStackTrace();
	        j.getHeader().put("success", false);
			j.getHeader().put("msg", "保存原贷款信息失败,请重新操作!");
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(j, response);
	}
	
	
	/**
	 * 根据原贷款信息ID集合查询原贷款信息
	 * @param response
	 */
	@RequestMapping(value="getOriginalLoanList")
	public void getOriginalLoanList(String originalLoanIds,HttpServletResponse response){
		BaseClientFactory clientFactory = null;
		List<BizOriginalLoan> list = new ArrayList<BizOriginalLoan>();
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizOriginalLoanService");
			BizOriginalLoanService.Client client = (BizOriginalLoanService.Client) clientFactory.getClient();
			BizOriginalLoan query = new BizOriginalLoan();
			List<Integer> originalLoanIdList = StringUtil.StringToList(originalLoanIds);
			if(originalLoanIdList != null && originalLoanIdList.size()>0){
				query.setOriginalLoanIds(originalLoanIdList);
				query.setStatus(1);//有效的
				list = client.getAllByCondition(query);
			}
		} catch (Exception e) {
			logger.error("根据原贷款信息ID集合查询原贷款信息:" + ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(clientFactory);
		}
		outputJson(list, response);
	}
	
	/**
	 * 根据项目ID查询原贷款信息
	 * @param projectId
	 * @param response
	 */
	@RequestMapping(value="getOriginalLoanByProjectId")
	public void getOriginalLoanByProjectId(Integer projectId,HttpServletResponse response){
		BaseClientFactory clientFactory = null;
		List<BizOriginalLoan> list = new ArrayList<BizOriginalLoan>();
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizOriginalLoanService");
			BizOriginalLoanService.Client client = (BizOriginalLoanService.Client) clientFactory.getClient();
			BizOriginalLoan query = new BizOriginalLoan();
			query.setProjectId(projectId);
			query.setStatus(1);//有效的
			list = client.getAllByCondition(query);
		} catch (Exception e) {
			logger.error("根据项目ID查询原贷款信息:" + ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(clientFactory);
		}
		outputJson(list, response);
	}
	
	/**
	 * 删除原贷款信息
	 * @param originalLoanIds
	 * @param response
	 */
	@RequestMapping(value="delOriginalLoan")
	public void delOriginalLoan(String originalLoanIds,HttpServletResponse response){
		logger.info("删除原贷款信息，参数：" + originalLoanIds);
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizOriginalLoanService");
			BizOriginalLoanService.Client client = (BizOriginalLoanService.Client) clientFactory.getClient();
			//页面传的ID字符串分解为list
			List<Integer> originalLoanIdList = StringUtil.StringToList(originalLoanIds);
			int result = 0;
			if(originalLoanIdList != null && originalLoanIdList.size()>0){
				result = client.delOriginalLoan(originalLoanIdList);
			}
			//根据返回的结果判断操作是否成功
			if(result >0){
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "操作成功");
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "操作失败,请重新操作!");
			}
		}catch(Exception e) {
			logger.error("批量删除原贷款信息失败：" + ExceptionUtil.getExceptionMessage(e));
	        e.printStackTrace();
	        j.getHeader().put("success", false);
			j.getHeader().put("msg", "操作失败,请重新操作!");
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(j, response);
	}
	
	/**
	 * 根据物业ID集合查询物业信息,用于物业下拉框
	 * @param response
	 */
	@RequestMapping(value="getEstateList")
	@ResponseBody
	public void getEstateList(@RequestParam(value = "houseIds", required = true)String houseIds,HttpServletResponse response){
		BaseClientFactory clientFactory = null;
		List<BizProjectEstate> list = new ArrayList<BizProjectEstate>();
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizProjectEstateService");
			BizProjectEstateService.Client client = (BizProjectEstateService.Client) clientFactory.getClient();
			BizProjectEstate query = new BizProjectEstate();
			BizProjectEstate result = new BizProjectEstate();
			result.setHouseId(-1);
			result.setHouseName("--请选择--");
			list.add(result);
			List<Integer> houseIdList = StringUtil.StringToList(houseIds);
			if(houseIdList != null && houseIdList.size()>0){
				query.setHouseIds(houseIdList);
				query.setStatus(1);//有效的
				list.addAll(client.getAll(query));
			}
		} catch (Exception e) {
			logger.error("根据物业ID集合查询物业信息:" + ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(clientFactory);
		}
		outputJson(list, response);
	}
	
	/**
	 * 万通业务推送小科撤回
	 * @param projectId
	 * @param response
	 * @param request
	 */
	@RequestMapping(value="cancleNeedFinancial")
	public void cancleNeedFinancial(int projectId,HttpServletResponse response,HttpServletRequest request){
		BaseClientFactory factory = null;
		Json j = super.getSuccessObj();
		try {
			factory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) factory.getClient();
			
			Project project = client.getProjectInfoById(projectId);
			if(project.getProjectSource() != 1){
				fillReturnJson(response, false, "此业务不属于万通，请勿进行撤销操作!");
	            return;
			}
			int count = client.getPartnerLoanCount(projectId);
			if(count >0){
				fillReturnJson(response, false, "此业务已经申请资金方放款，请勿进行撤销操作!");
	            return;
			}
			
			//查询放款状态（1：未申请 2：申请中3：放款中 4：放款成功 5：放款失败），只有未申请状态才可以推送
			Client service = (Client) getService(BusinessModule.MODUEL_INLOAN,"FinanceHandleService");
	        FinanceHandleDTO financeHandleDTO = service.getFinanceHandleByProjectId(projectId);
	        int status = financeHandleDTO.getStatus();
			if(status == Constants.REC_STATUS_ALREADY_LOAN || status == Constants.REC_STATUS_LOAN_NO_FINISH || status == Constants.REC_STATUS_LOAN_APPLY){//status等于0或者1 表示放款数据不存在或者未进行放款申请
				fillReturnJson(response, false, "此业务已存在放款数据，不允许撤销!");
	            return;
			}
			
			if(project.getIsNeedFinancial() == 1){
				fillReturnJson(response, false, "此业务未推送小科，请重新选择!");
	            return;
			}else{
				project.setIsNeedFinancial(1);//不需要小科资金
				int rows =client.updateProjectNeed(project );
				if(rows>0){
					j.getHeader().put("success", true);
				}else{
					j.getHeader().put("success", false);
					j.getHeader().put("msg", "操作失败,请重新操作!");
				}
			}
		} catch (Exception e) {
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "操作失败,请重新操作!");
			logger.error("撤销推送小科出错" ,ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(factory);
		}
		outputJson(j, response);
	}
	
	/**
	 * 保后监管一键下载文件
	  * @param projectId
	  * @param projectName
	  * @param response
	  * @param request
	  * @author: Jony
	  * @date: 2016年10月18日 下午2:14:13
	 */
	@RequestMapping(value="downLoadZipFileByfileIds")
	public void downLoadZipFileByfileIds(int projectId,HttpServletResponse response,HttpServletRequest request,String fileIdStrs){
		BaseClientFactory factory = null;
		BaseClientFactory clientFactory = null;
		if (StringUtil.isBlank(fileIdStrs)) {
	          fillReturnJson(response, false, "下载失败,参数不合法!");
	          return;
	      }
		List<Integer> fileIds = new ArrayList<Integer>();
		try {
			 String[] fileIdArr = fileIdStrs.split(",");
	         for (int i = 0; i < fileIdArr.length; i++) {
	            int fileId=Integer.parseInt(fileIdArr[i]);
	            fileIds.add(fileId);
	         }
	         
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			Project project = client.getProjectNameOrNumber(projectId);
			
			factory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ElementLendService");
			ElementLendService.Client elementLendService = (ElementLendService.Client) factory.getClient();
			List<DataInfo> list = elementLendService.findProjectFilesByfileIds(projectId,fileIds);
			//判断下载是不是有重复，有的话只下载一个
			if(list != null && list.size()>0){
				for (int i = 0; i < list.size(); i++)  //外循环是循环的次数
	            {
	                for (int j = list.size() - 1 ; j > i; j--)  //内循环是 外循环一次比较的次数
	                {
	                    if (list.get(i).getFileName().equals(list.get(j).getFileName()))
	                    {
	                    	list.remove(j);
	                    }
	                }
	            }
			}
			
			String zipFilePath = zipFiles(list,response, request);
			
			FileDownload.downloadLocalFile(response, request, zipFilePath,project.getProjectName());
		} catch (Exception e) {
			logger.error("下载资料文件失败出错" ,e);
		}finally{
			destroyFactory(factory,clientFactory);
		}
	}
}
