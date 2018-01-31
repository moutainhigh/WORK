package com.xlkfinance.bms.web.controller.beforeloan;

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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.constant.SysLogTypeConstant;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstate;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstateService;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectGuarantee;
import com.xlkfinance.bms.rpc.product.Product;
import com.xlkfinance.bms.rpc.product.ProductService;
import com.xlkfinance.bms.rpc.project.BizCalLoanMoney;
import com.xlkfinance.bms.rpc.project.BizProjectContacts;
import com.xlkfinance.bms.rpc.project.BizProjectContactsService;
import com.xlkfinance.bms.rpc.project.ProjectDTO;
import com.xlkfinance.bms.rpc.project.ProjectInfoService;
import com.xlkfinance.bms.rpc.project.ProjectInfoService.Client;
import com.xlkfinance.bms.rpc.repayment.RepaymentPlanBaseDTO;
import com.xlkfinance.bms.rpc.system.SysConfigService;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;
/**
 * 抵押贷贷前申请controller
 * @author: Leif
 * @date: 2018年1月3日 上午10:35:24
 */
@Controller
@RequestMapping("/consumeProjectInfoController")
public class ConsumeProjectInfoController  extends BaseController {
	private Logger logger = LoggerFactory.getLogger(ConsumeProjectInfoController.class);
	
	/**
	 * 消费贷列表页面跳转
	 * @return
	 */
	@RequestMapping(value = "index")
	public String index() {
		return "consumeprojectinfo/index";
	}
	
	/**
	 * 查询消费贷项目列表
	 * @return void
	 * @author: Leif
	 * @date: 2018年1月3日 上午10:35:11
	 */
	  
	@RequestMapping(value = "getConsumeProjectByPage", method = RequestMethod.POST)
	public void getConsumeProjectByPage(ProjectDTO query,HttpServletRequest request, HttpServletResponse response) {
		// 需要返回的Map集合
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_PROJECT, "ProjectInfoService");
			query.setUserIds(getUserIds(request));//数据权限
			query.setRecordClerkId(getShiroUser().getPid());//查询录单员为登录人的业务
			query.setIsChechan(0);//未撤单
			query.setProjectType(10);//消费贷类产品
			ProjectInfoService.Client client = (ProjectInfoService.Client) clientFactory.getClient();
			// 创建集合
 			List<ProjectDTO> list = client.getProjectByPage(query);
			int count = client.getProjectCount(query);
			map.put("rows", list);
			map.put("total", count);
		} catch (Exception e) {
				logger.error("查询贷前列表:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(map, response);
	}
	
	/**
	 * 跳转到项目编辑页面
	 * @return String
	 * @author: Leif
	 * @date: 2018年1月3日 上午10:47:48
	 */
	@RequestMapping(value = "toEditConsumeProject")
	public String toEditConsumeProject(@RequestParam(value = "projectId", required = false) Integer projectId,@RequestParam(value = "type", required = false)String editType,@RequestParam(value = "showType", required = false)String showType, ModelMap model) {
		// 创建对象
		Project project = new Project();
		
		SysUser sysUser = getSysUser();
		project.setProjectType(10);//默认设置消费贷项目类型
		project.setProjectSource(2);//项目来源为小科录入
		project.setRecordClerkId(sysUser.getPid());//录单员为系统登录人员
		project.setDeclaration(sysUser.getRealName());//录单员为系统登录人员
		BaseClientFactory clientFactory = null;
		
		try {
			// 项目id存在时，获取项目信息
			if (projectId != null && projectId > 0) {
				clientFactory = getFactory(BusinessModule.MODUEL_PROJECT, "ProjectInfoService");
				ProjectInfoService.Client projectInfoService = (Client) clientFactory.getClient();
				project = projectInfoService.getLoanProjectByProjectId(projectId);
			}
			clientFactory = getFactory(BusinessModule.MODUEL_PRODUCT, "ProductService");
			ProductService.Client client = (ProductService.Client) clientFactory.getClient();
			Product product = new Product();
			product.setProductNumber("XFD001");
			List<Product> productList = client.getAllProductList(product);
			if (!CollectionUtils.isEmpty(productList)) {
				model.put("mortgageLoanProduct", productList.get(0));
			}	

		} catch (Exception e) {
			logger.error("跳转抵押贷申请页面，查询抵押贷产品出错！" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
		
		model.put("project", project);
		model.put("login", sysUser);
		model.put("editType", editType);
		//区分查看跳转来的页面
		model.put("showType", showType);
		
		return "consumeprojectinfo/project_tab";
	}
	
	
	
	/**
	 * 保存消费贷申请信息
	 * @return void
	 * @author: Leif
	 * @date: 2018年1月3日 上午11:03:17
	 */
	@RequestMapping(value="saveConsumeProjectInfo")
	public void saveConsumeProjectInfo(Project project, HttpServletRequest request,HttpServletResponse response) {
		logger.info("保存消费贷申请信息！{}", project);
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_PROJECT, "ProjectInfoService");
			ProjectInfoService.Client client = (ProjectInfoService.Client) clientFactory.getClient();
			if(project.getPid() <= 0 && project.getPmUserId() <= 0){
				// 设置当前项目经理为当前登录的用户
				project.setPmUserId(getShiroUser().getPid());
				// 设置当前项目的下一节点处理人为登录用户
				project.setNextUserId(getShiroUser().getRealName());
			}
			//借款金额
			ProjectGuarantee projectGuarantee = project.getProjectGuarantee();
			int foreclosureStatus = project.getForeclosureStatus();
			if(projectGuarantee != null && foreclosureStatus == Constants.CONSUME_LOAN_TO_SUBMIT){
				double guaranteeMoney = projectGuarantee.getGuaranteeMoney();
				projectGuarantee.setLoanMoney(guaranteeMoney);
			}
			 Map<String, String> projectInfo = client.saveConsumeProjectInfo(project);
			if (null != projectInfo && !projectInfo.isEmpty()) {
				// 分别获取项目ID和授信ID
				j.getHeader().put("success", true);
				j.getHeader().putAll(projectInfo);
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_ADD, String.format("保存房抵贷贷前申请成功返回结果：%s", projectInfo));
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "保存贷款申请失败,请重新操作!");
			}
		}catch(Exception e) {
			logger.error("保存贷款申请失败：" + ExceptionUtil.getExceptionMessage(e));
	        j.getHeader().put("success", false);
			j.getHeader().put("msg", "保存贷款申请失败,请重新操作!");
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(j, response);
	}
	
	/**
	 * 计算可贷金额
	 * @param query
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "makeBizCalLoanMoney", method = RequestMethod.POST)
	public void makeBizCalLoanMoney(BizCalLoanMoney query,HttpServletRequest request, HttpServletResponse response) {
		// 需要返回的Map集合
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_PROJECT, "ProjectInfoService");
			ProjectInfoService.Client client = (ProjectInfoService.Client) clientFactory.getClient();
			double rentMoney = query.getRentMoney();
			double feeRate = query.getFeeRate();
			int loanTerm = query.getLoanTerm();
			
			List<BizCalLoanMoney> list = new ArrayList<BizCalLoanMoney>();
			if(rentMoney >0 && feeRate>0 && loanTerm>0){
				// 创建集合
	 			list = client.makeBizCalLoanMoney(query);
			}
			int count = list.size();
			map.put("rows", list);
			map.put("total", count);
		} catch (Exception e) {
			logger.error("计算可贷金额，输出可贷金额列表:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(map, response);
	}
	
	
	/**
	 * 跳转到尽职调查
	 */
	@RequestMapping(value="toSpotInfoPage")
	public String toSpotInfoPage(Integer projectId,int foreclosureStatus, ModelMap model) {
		//物业列表
		BaseClientFactory clientFactory = null;
		List<BizProjectEstate> list = new ArrayList<BizProjectEstate>();
		String vacantRate=" ";
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizProjectEstateService");
			BizProjectEstateService.Client client = (BizProjectEstateService.Client) clientFactory.getClient();
			list = client.getAllCascadeSpotInfoByProjectId(projectId);
			BaseClientFactory baseClientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysConfigService");
            //查询空置率
			SysConfigService.Client baseClient = (SysConfigService.Client) baseClientFactory.getClient();
			 vacantRate = baseClient.getSysConfigValueByName("VACANT_RATE");
		} catch (Exception e) {
			logger.error("根据项目ID查询物业信息:" + ExceptionUtil.getExceptionMessage(e));
		}  finally {
			destroyFactory(clientFactory);
		}
		model.put("projectSpotInfoList", list);
		model.put("projectId", projectId);
		model.put("foreclosureStatus", foreclosureStatus);
		//空置率
		model.put("vacantRate", vacantRate);
		return "consumeprojectinfo/projectSpotInfo";
	}
	
	
	/**
	 * 根据项目id获取项目联系人信息
	 * @return void
	 * @author: dongibao
	 * @date: 2018年1月3日 上午11:03:17
	 */
	@RequestMapping(value="getContactsByProjectId",method = RequestMethod.POST)
	public void getContactsByProjectId(Integer projectId,HttpServletResponse response){
		BaseClientFactory clientFactory = null;
		List<BizProjectContacts> list = new ArrayList<BizProjectContacts>();
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_PROJECT, "BizProjectContactsService");
			BizProjectContactsService.Client client = (BizProjectContactsService.Client) clientFactory.getClient();
			list = client.getContactsByProjectId(projectId);
		} catch (Exception e) {
			logger.error("根据项目ID查询项目联系人信息:" + ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(clientFactory);
		}
		outputJson(list, response);
	}
	
	
	/**
	 * 根据联系人ID集合查询项目联系人信息
	 * @return void
	 * @author: dongibao
	 * @date: 2018年1月3日 上午11:03:17
	 */
	@RequestMapping(value="getContactList")
	public void getHouseList(String contactIds,HttpServletResponse response){
		BaseClientFactory clientFactory = null;
		List<BizProjectContacts> list = new ArrayList<BizProjectContacts>();
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_PROJECT, "BizProjectContactsService");
			BizProjectContactsService.Client client = (BizProjectContactsService.Client) clientFactory.getClient();
			BizProjectContacts query = new BizProjectContacts();
			List<Integer> contactIdList = StringUtil.StringToList(contactIds);
			if(contactIdList != null && contactIdList.size()>0){
				query.setContactIdList(contactIdList);
				list = client.getAll(query);
			}
		} catch (Exception e) {
			logger.error("根据联系人ID集合查询项目联系人信息:" + ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(clientFactory);
		}
		outputJson(list, response);
	}
	
	
	/**
	 * 项目联系人操作
	 * @return void
	 * @author: dongibao
	 * @date: 2018年1月3日 上午11:03:17
	 */
	@RequestMapping(value="modContacts",method = RequestMethod.POST)
	public void modContacts(BizProjectContacts bizProjectContacts,HttpServletResponse response){
		logger.info("保存项目联系人信息，参数：" + bizProjectContacts);
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_PROJECT, "BizProjectContactsService");
			BizProjectContactsService.Client client = (BizProjectContactsService.Client) clientFactory.getClient();
			int userId = getShiroUser().getPid();//获取登录用户的ID
			
			 String currDate= DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
			//判断联系人ID,小于等于0时做新增操作，大于0时做更新操作
			int contactId = bizProjectContacts.getPid();
			if(contactId<=0){
				bizProjectContacts.setCreaterId(userId);
				bizProjectContacts.setCreateDate(currDate);
				contactId = client.insert(bizProjectContacts);
			}else{
				bizProjectContacts.setUpdateId(userId);
				bizProjectContacts.setUpdateDate(currDate);
				client.update(bizProjectContacts);
			}
			//根据返回的物业ID判断操作是否成功
			if(contactId >0){
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "保存成功");
				j.getHeader().put("contactId", contactId);
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "保存项目联系人信息失败,请重新操作!");
			}
		}catch(Exception e) {
			logger.error("保存项目联系人失败：" + ExceptionUtil.getExceptionMessage(e));
	        e.printStackTrace();
	        j.getHeader().put("success", false);
			j.getHeader().put("msg", "保存项目联系人信息失败,请重新操作!");
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(j, response);
	}
	
	/**
	 * 删除项目联系人操作
	 * @return void
	 * @author: dongibao
	 * @date: 2018年1月3日 上午11:03:17
	 */
	@RequestMapping(value="delContacts")
	public void delPledge(String contactIds,HttpServletResponse response){
		logger.info("项目联系人，参数：" + contactIds);
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		int result = 0;
		try {
			
			List<Integer>contactIdList = StringUtil.StringToList(contactIds);
			if(contactIdList != null && contactIdList.size()>0){
				clientFactory = getFactory(BusinessModule.MODUEL_PROJECT, "BizProjectContactsService");
				BizProjectContactsService.Client client = (BizProjectContactsService.Client) clientFactory.getClient();
				result = client.deleteProjectContact(contactIdList);
			}
			//根据返回的联系人ID判断操作是否成功
			if(result >0){
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "操作成功");
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "操作失败,请重新操作!");
			}
		}catch(Exception e) {
			logger.error("批量删除联系人信息失败：" + ExceptionUtil.getExceptionMessage(e));
	        e.printStackTrace();
	        j.getHeader().put("success", false);
			j.getHeader().put("msg", "操作失败,请重新操作!");
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(j, response);
	}
	
	@RequestMapping(value="toFinanceDetail")
	public String toFinanceDetail(){
		return "consumeprojectinfo/finance_detail";
	}
	
	/**
	 * 获取还款计划表
	 */
	@RequestMapping(value="getRepaymentPlan")
	public void getRepaymentPlan(Integer projectId,@RequestParam(value = "receDate", required = false)String receDate,HttpServletResponse response){
		if (projectId == null || projectId <= 0) {
	         logger.error("请求数据不合法：" + projectId);
	         fillReturnJson(response, false, "获取还款计划表数据失败,请输入必填项!");
	         return;
	      }
		// 需要返回的Map集合
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_PROJECT, "ProjectInfoService");
			ProjectInfoService.Client client = (ProjectInfoService.Client) clientFactory.getClient();
			
			//查询回款记录
			List<RepaymentPlanBaseDTO> list =  client.queryRepaymentPlan(projectId, getShiroUser().getPid(), receDate);
			int count = list.size();
			map.put("rows", list);
			map.put("total", count);
		} catch (Exception e) {
			logger.error("获取还款计划表的数据:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(map, response);
	}
	
	/**
	 * (资料上传列表页面跳转).<br/>
	 * @author dongbiao
	 * @param foreStatus
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "navigationDatum")
	public String navigationDatum(int foreStatus, ModelMap model) {
		model.put("foreStatus", foreStatus);
		return "consumeprojectinfo/loan_add_datum";
	}
}
