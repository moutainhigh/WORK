package com.xlkfinance.bms.web.controller.inloan;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.qfang.xk.aom.rpc.org.OrgAssetsCooperationInfo;
import com.qfang.xk.aom.rpc.org.OrgAssetsCooperationService;
import com.xlkfinance.bms.common.constant.BizDynamicConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.constant.MortgageDynamicConstant;
import com.xlkfinance.bms.common.constant.PartnerConstant;
import com.xlkfinance.bms.common.constant.SysLogTypeConstant;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.MoneyFormatUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectForeclosure;
import com.xlkfinance.bms.rpc.beforeloan.ProjectGuarantee;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.inloan.ApplyFinanceHandleDTO;
import com.xlkfinance.bms.rpc.inloan.BizDynamic;
import com.xlkfinance.bms.rpc.inloan.BizDynamicService;
import com.xlkfinance.bms.rpc.inloan.BizHandleService;
import com.xlkfinance.bms.rpc.inloan.BizInterviewInfo;
import com.xlkfinance.bms.rpc.inloan.BizInterviewInfoService;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentDTO;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentHisService;
import com.xlkfinance.bms.rpc.inloan.CheckLitigationDTO;
import com.xlkfinance.bms.rpc.inloan.CheckLitigationHisService;
import com.xlkfinance.bms.rpc.inloan.CollectFeeHis;
import com.xlkfinance.bms.rpc.inloan.CollectFeeHisService;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleDTO;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleService.Client;
import com.xlkfinance.bms.rpc.inloan.FinanceIndexDTO;
import com.xlkfinance.bms.rpc.inloan.HandleDynamicFileDTO;
import com.xlkfinance.bms.rpc.inloan.IntegratedDeptService;
import com.xlkfinance.bms.rpc.inloan.LoanSuspendRecord;
import com.xlkfinance.bms.rpc.inloan.LoanSuspendRecordService;
import com.xlkfinance.bms.rpc.inloan.RepaymentDTO;
import com.xlkfinance.bms.rpc.inloan.RepaymentService;
import com.xlkfinance.bms.rpc.partner.ProjectPartner;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerService;
import com.xlkfinance.bms.rpc.product.Product;
import com.xlkfinance.bms.rpc.product.ProductService;
import com.xlkfinance.bms.rpc.repayment.LoanExtensionService;
import com.xlkfinance.bms.rpc.workday.WorkDay;
import com.xlkfinance.bms.rpc.workday.WorkDayService;
import com.xlkfinance.bms.rpc.workflow.WorkflowProjectVo;
import com.xlkfinance.bms.rpc.workflow.WorkflowService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;
import com.xlkfinance.bms.web.shiro.ShiroUser;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年1月11日下午9:01:19 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 财务受理<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Controller
@RequestMapping("/financeHandleController")
public class FinanceHandleController extends BaseController {
   private static final String PAGE_PATH = "inloan/financeHandle/";
   private Logger logger = LoggerFactory.getLogger(FinanceHandleController.class);
   private final String serviceName = "FinanceHandleService";

   /**
    * 收费页面跳转
    */
   @RequestMapping(value = "/collect_fee_index")
   public String toCollectFeeIndex(ModelMap model) {
      return PAGE_PATH + "collect_fee_index";
   }

   /**
    * 放款列表页面跳转
    *@author:liangyanjun
    *@time:2016年3月10日上午9:09:37
    *@param model
    *@return
    */
   @RequestMapping(value = "/make_loans_index")
   public String toMakeLoansIndex(ModelMap model) {
       model.put("userSource", checkUserOrg());//当前查询用户来源：万通用户=1，小科用户=2
      return PAGE_PATH + "make_loans_index";
   }
   /**
    * 放款页面跳转
    *@author:liangyanjun
    *@time:2016年8月5日上午9:34:46
    *@param model
    *@return
    */
   @RequestMapping(value = "/toMakeLoans")
   public String toMakeLoans(ModelMap model,int projectId,int chooseType) {
	   String toPage=PAGE_PATH + "make_loans";
      if (projectId<=0) {
         return toPage;
      }
      model.put("userSource", checkUserOrg());//当前查询用户来源：万通用户=1，小科用户=2
      FinanceIndexDTO financeIndexQuery=new FinanceIndexDTO();
      financeIndexQuery.setProjectId(projectId);
      Product product = new Product();
      BaseClientFactory clientFactory = null;
      try {
         // 查询财务受理列表信息
         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN,serviceName);
         List<FinanceIndexDTO> list = service.queryFinanceIndex(financeIndexQuery);
         if (list==null||list.isEmpty()) {
            return toPage;
         }
         FinanceIndexDTO financeIndexDTO = list.get(0);
         model.put("financeIndexDTO", financeIndexDTO);
         //查询担保信息
         BaseClientFactory ProjectFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
         ProjectService.Client projectService = (ProjectService.Client) ProjectFactory.getClient();
         ProjectGuarantee projectGuarantee = projectService.getGuaranteeByProjectId(projectId);
         model.put("projectGuarantee",projectGuarantee);
      	      
         //查询赎楼信息
         ProjectForeclosure projectForeclosure = projectService.getForeclosureByProjectId(projectId);
         model.put("projectForeclosure",projectForeclosure);
         //查询贷前办理动态
         BaseClientFactory bizDynamicFactory = getFactory(BusinessModule.MODUEL_INLOAN, "BizDynamicService");
         BizDynamicService.Client bizDynamicService = (BizDynamicService.Client) bizDynamicFactory.getClient();
         BizDynamic bizDynamic = new BizDynamic();
         bizDynamic.setProjectId(projectId);
         bizDynamic.setModuelNumber(BizDynamicConstant.MODUEL_NUMBER_BEFORELOAN);
         bizDynamic.setPage(-1);
         List<BizDynamic> dynamicList = bizDynamicService.queryBizDynamic(bizDynamic);
         model.put("dynamicList",dynamicList);
         
         //根据项目Id查询产品 目的是找产品编码
         clientFactory = getFactory(BusinessModule.MODUEL_PRODUCT, "ProductService");
		  ProductService.Client client = (ProductService.Client) clientFactory.getClient();
	      product = client.findProductByProjectId(projectId);
	      String productNumber = "";
	      //提房贷判断，交易/非交易有赎楼提房贷的有二次放款
	      productNumber = product.getProductNumber();
	      ApplyFinanceHandleDTO makeLoans = service.getByProjectIdAndRecPro(projectId, Constants.REC_PRO_3);
	      int applyStatus = makeLoans.getApplyStatus();//第一次放款审批状态
	      //有赎楼提房贷,并且第一次 放款已完成
	      if ((Constants.JY_YSL_TFD.equals(productNumber)  || Constants.FJY_YSL_TFD.equals(productNumber))&&Constants.APPLY_STATUS_5==applyStatus) {
	    	  makeLoans = service.getByProjectIdAndRecPro(projectId, Constants.REC_PRO_9);
	    	  toPage=PAGE_PATH + "turnover_loans";
		  }
	      if(0!=chooseType){
	    	  //3=第一次放款记录
	    	  if(3==chooseType){
	    		  toPage=PAGE_PATH + "make_loans";
	    	  //9=第一次放款记录
	    	  }else if(9==chooseType){
	    		  toPage=PAGE_PATH + "turnover_loans";
	    	  }
	      }
	      model.put("makeLoans", makeLoans);
          destroyFactory(ProjectFactory,bizDynamicFactory);
      } catch (Exception e) {
         logger.error("获取财务收费列表失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + financeIndexQuery);
         e.printStackTrace();
      }
		  return toPage;
   }
   @RequestMapping(value = "/toFddMakeLoans")
   public String toFddMakeLoans(ModelMap model,int projectId) {
      // 查询财务受理列表信息
      FinanceIndexDTO financeIndexQuery=new FinanceIndexDTO();
      financeIndexQuery.setProjectId(projectId);
      Client service = (Client) getService(BusinessModule.MODUEL_INLOAN,serviceName);
      try {
         BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_INLOAN, "BizInterviewInfoService");
         BizInterviewInfoService.Client bizInterviewInfoService = (BizInterviewInfoService.Client) clientFactory.getClient();
         BizInterviewInfo interviewInfo = bizInterviewInfoService.getByProjectId(projectId);
         if (interviewInfo.getMortgageStatus()==Constants.COMM_NO) {
            throw new RuntimeException("请先办理抵押再放款");
         }
         List<FinanceIndexDTO> list = service.queryFinanceIndex(financeIndexQuery);
         if (list==null||list.isEmpty()) {
            return PAGE_PATH+"fdd_make_loans";
         }
         //放款主表信息
         FinanceIndexDTO financeIndexDTO = list.get(0);
         int collectFeeStatus = financeIndexDTO.getCollectFeeStatus();//收费状态：未收费=1，已收费=2
         int collectFeeType = financeIndexDTO.getCollectFeeType();//(赎楼贷)收费方式:贷前收费=1，贷后收费=2。（房抵贷）收费节点：下户前收费=1，放款前收费=2，任意节点收费=3
         if (collectFeeType==2&&collectFeeStatus==1) {
            throw new RuntimeException("请先收费再放款");
         }
         model.put("financeIndexDTO", financeIndexDTO);
         //放款信息
         ApplyFinanceHandleDTO makeLoans = service.getByProjectIdAndRecPro(projectId, Constants.REC_PRO_3);
         model.put("makeLoans", makeLoans);
         //项目信息
         ProjectService.Client projectService=(ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN,"ProjectService");
         Project project = projectService.getLoanProjectByProjectId(projectId);
         model.put("project", project);
         //查询贷前办理动态
         BaseClientFactory bizDynamicFactory = getFactory(BusinessModule.MODUEL_INLOAN, "BizDynamicService");
         BizDynamicService.Client bizDynamicService = (BizDynamicService.Client) bizDynamicFactory.getClient();
         BizDynamic bizDynamic = new BizDynamic();
         bizDynamic.setProjectId(projectId);
         bizDynamic.setModuelNumber(MortgageDynamicConstant.MODUEL_NUMBER_BEFORELOAN);
         bizDynamic.setPage(-1);
         List<BizDynamic> dynamicList = bizDynamicService.queryBizDynamic(bizDynamic);
         model.put("dynamicList",dynamicList);
      } catch (Exception e) {
         e.printStackTrace();
      }
      return PAGE_PATH+"fdd_make_loans";
      
   }

   /**
    * 放款来源和金额自动匹配
    * 关于放款来源和金额自动匹配规则
    * 1.只有放款状态等于“未申请”才会执行自动匹配
    * 2.只有平台放款状态等于“放款成功”才会执行自动匹配
    * 3.只有平台放款状态不等于“放款成功”或者没有平台数据时，款项来源金额1等于借款金额
    * 4.当平台金额等于借款金额时，款项来源2和款项来源金额2为平台数据，款项来源1和款项来源金额1保持默认值
    * 5.当平台金额小于借款金额时，款项来源2和款项来源金额2为平台数据，款项来源1保持默认值，项来源金额1等于（借款金额-平台金额）
    * 6.当平台金额大于借款金额时，款项来源2和款项来源金额2为平台数据，款项来源1和款项来源金额1保持默认值
    */
    private void matchOrgData(int projectId, ApplyFinanceHandleDTO financeHandleDTO) throws ThriftException, ThriftServiceException, TException {
        int applyStatus = financeHandleDTO.getApplyStatus();
        if (applyStatus>=Constants.APPLY_STATUS_2) {
            return;
        }
        //查询担保信息
        BaseClientFactory ProjectFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
        ProjectService.Client projectService = (ProjectService.Client) ProjectFactory.getClient();
        ProjectGuarantee projectGuarantee = projectService.getGuaranteeByProjectId(projectId);
        double loanMoney = projectGuarantee.getLoanMoney();//申请借款金额
        //假如是交易/非交易有赎楼提放贷 款项来源设置值
        double realLoan = getRealLoan(projectId, projectGuarantee);
        if (realLoan > 0) {
        	loanMoney=realLoan;
		}
        
        //author:chenzhuzhen
        //查询平台数据
        BaseClientFactory projectPartnerFactory = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
        ProjectPartnerService.Client projectPartnerService = (ProjectPartnerService.Client) projectPartnerFactory.getClient();
        ProjectPartner query=new ProjectPartner();
        query.setProjectId(projectId);
        query.setIsClosed(PartnerConstant.IsClosed.NO_CLOSED.getCode());
        List<ProjectPartner> tempPartnerList = projectPartnerService.findProjectPartnerInfoList(query);
        ProjectPartner projectPartner = null;
        if(!CollectionUtils.isEmpty(tempPartnerList)){
        	for (ProjectPartner indexObj : tempPartnerList) {
                if (indexObj.getLoanStatus() == Constants.LOAN_STATUS_4) {
                	projectPartner =indexObj;
                    break;
                }
			}
        }
        
        //author:chenzhuzhen
        if (projectPartner==null||projectPartner.getPid()<=0) {//平台没有放款数据，则默认使用借款金额
            financeHandleDTO.setResourceMoney(loanMoney);
            return;
        }
        
        int loanStatus = projectPartner.getLoanStatus();//平台放款状态(1：未申请 2：申请中3：放款中 4：放款成功 5：放款失败)
        if (loanStatus!=Constants.LOAN_STATUS_4) {
        
          financeHandleDTO.setResourceMoney(loanMoney);
            return;
        }
        //查询款项来源数据
        /*BaseClientFactory capitalOrgFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "CapitalOrgInfoService");
        CapitalOrgInfoService.Client capitalOrgService = (CapitalOrgInfoService.Client) capitalOrgFactory.getClient();*/
        
        String partnerNo = projectPartner.getPartnerNo();//平台名称
        double loanAmount = projectPartner.getLoanAmount();//平台放款金额
        /*CapitalOrgInfo capitalOrgInfo = capitalOrgService.getByOrgName(partnerNo);
        int pid = capitalOrgInfo.getPid();*/
        financeHandleDTO.setResource2(partnerNo);
        if (loanMoney>loanAmount) {
            double temp=loanMoney-loanAmount;
            financeHandleDTO.setResourceMoney(temp);
        }else{
            financeHandleDTO.setRecMoney(loanAmount);
        }
        financeHandleDTO.setResourceMoney2(loanAmount);
        destroyFactory(projectPartnerFactory);
    }

	private double getRealLoan(int projectId, ProjectGuarantee projectGuarantee) throws ThriftException {
		double foreclosureMoney = projectGuarantee.getForeclosureMoney();// 赎楼金额
		double turnoverMoney = projectGuarantee.getTurnoverMoney();// 周转金额
		// 根据projectId查询该产品编码
		Product product = new Product();
		BaseClientFactory clientFactory = null;
		
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_PRODUCT,
					"ProductService");
			ProductService.Client client = (ProductService.Client) clientFactory.getClient();
			product = client.findProductByProjectId(projectId);
		} catch (TException e) {
			e.printStackTrace();
		}
		String productNumber = "";
		// 提放贷判断，交易/非交易有赎楼提房贷的有二次放款
		productNumber = product.getProductNumber();
		if (Constants.JY_YSL_TFD.equals(productNumber)
				|| Constants.FJY_YSL_TFD.equals(productNumber)) {
			// 判断第一次放款保存
			int fApplayStatus = getApplayStatusByProjectId(projectId, 3);
			if (fApplayStatus==Constants.APPLY_STATUS_1) {
				return foreclosureMoney;
			}
			// 判断第二次放款保存
			int sApplayStatus = getApplayStatusByProjectId(projectId, 9);
			if (sApplayStatus==Constants.APPLY_STATUS_1) {
				return turnoverMoney;
			}
		}
		return 0;
	}
   /**
    * 财务收费查询列表
    *@author:liangyanjun
    *@time:2016年1月12日下午11:04:22
    *@param financeIndexDTO
    *@param response
    */
   @RequestMapping(value = "/collectFeeIndexList")
   @ResponseBody
   public void collectFeeIndexList(FinanceIndexDTO financeIndexDTO, HttpServletRequest request, HttpServletResponse response) {
      if (financeIndexDTO==null)financeIndexDTO=new FinanceIndexDTO();
      Map<String, Object> map = new HashMap<String, Object>();
      List<FinanceIndexDTO> list = null;
      int total = 0;
      // 设置数据权限--用户编号list
      financeIndexDTO.setUserIds(getUserIds(request));
      try {
         // 查询财务受理列表
         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN,serviceName);
         list = service.queryFinanceCollectFeeIndex(financeIndexDTO);
         total = service.getFinanceCollectFeeIndexTotal(financeIndexDTO);
         logger.info("财务收费查询列表查询成功：total：" + total + ",list:" + list);
      } catch (Exception e) {
         logger.error("获取财务收费列表失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + financeIndexDTO);
         e.printStackTrace();
      }
      // 输出
      map.put("rows", list);
      map.put("total", total);
      outputJson(map, response);
   }
   
   /**
    * 财务放款查询列表
    *@author:liangyanjun
    *@time:2016年3月10日上午9:31:00
    *@param financeHandleDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/makeLoansIndexList")
   @ResponseBody
   public void makeLoansIndexList(FinanceIndexDTO financeIndexDTO, HttpServletRequest request, HttpServletResponse response) {
      if (financeIndexDTO==null)financeIndexDTO=new FinanceIndexDTO();
      Map<String, Object> map = new HashMap<String, Object>();
      List<FinanceIndexDTO> list = null;
      int total = 0;
      // 设置数据权限--用户编号list
      financeIndexDTO.setUserIds(getUserIds(request));
      // 过滤收费和放款的状态
//      List<Integer> statusList=new ArrayList<Integer>();
//      statusList.add(Constants.REC_STATUS_ALREADY_CHARGE);
//      statusList.add(Constants.REC_STATUS_LOAN_NO_FINISH);
//      statusList.add(Constants.REC_STATUS_ALREADY_LOAN);
//      statusList.add(Constants.REC_STATUS_LOAN_APPLY);
//      financeIndexDTO.setStatusList(statusList);
      financeIndexDTO.setQueryUserSource(checkUserOrg());
      try {
         // 查询财务受理列表
         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN,serviceName);
         
         list = service.queryFinanceIndex(financeIndexDTO);
         total = service.getFinanceIndexTotal(financeIndexDTO);
         logger.info("财务放款查询列表查询成功：total：" + total + ",list:" + list);
      } catch (Exception e) {
         logger.error("获取财务放款列表失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + financeIndexDTO);
         e.printStackTrace();
      }
      // 输出
      map.put("rows", list);
      map.put("total", total);
      outputJson(map, response);
   }

   /**
    * 根据id获取财务办理的状态
    *@author:liangyanjun
    *@time:2016年2月22日上午11:59:18
    *@param model
    *@param financeHandleId
    *@param request
    *@return
    */
   @RequestMapping(value = "/{financeHandleId}/getFinanceHandleStatus")
   @ResponseBody
   public String getFinanceHandleStatus(ModelMap model, @PathVariable java.lang.Integer financeHandleId, HttpServletRequest request) {
      Client service = (Client) getService(BusinessModule.MODUEL_INLOAN,serviceName);
      try {
        int status = getFinanceHandleStatus(financeHandleId, service);
         if (status==0) {
            return "";
         }
         return status+"";
      } catch (TException e) {
         logger.error("根据id获取财务办理的状态失败.financeHandleId=" + financeHandleId + "错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
         return "";
      }
   }

   private int getFinanceHandleStatus(java.lang.Integer financeHandleId, Client service) throws TException {
      FinanceHandleDTO financeHandleQuery = new FinanceHandleDTO();
      financeHandleQuery.setPid(financeHandleId);
      List<FinanceHandleDTO> financeHandleList = service.findAllFinanceHandle(financeHandleQuery);
      if (financeHandleList==null||financeHandleList.isEmpty()) {
         return 0;
      }
      return financeHandleList.get(0).getStatus();
   }
   /**
    * 根据财务办理的ID查询所有收款项目的财务办理信息，把查处的数据填充到model中
    *@author:liangyanjun
    *@time:2016年1月14日上午9:32:38
    *@param model
    *@param financeHandleId
    */
   private void fillApplyFinanceHandleMap(ModelMap model,int type, java.lang.Integer financeHandleId, HttpServletRequest request) {
      Client service = (Client) getService(BusinessModule.MODUEL_INLOAN,serviceName);
      ShiroUser loginUser = getShiroUser();
      String projectId = request.getParameter("projectId");
      String projectNumber = request.getParameter("projectNumber");
      String projectName = request.getParameter("projectName");
      model.put("projectId", projectId);
      model.put("projectNumber", projectNumber);
      model.put("projectName", projectName);
      
      // 根据财务办理的ID查询所有收款项目的财务办理信息
      Map<String, ApplyFinanceHandleDTO> applyFinanceHandleMap = new HashMap<String, ApplyFinanceHandleDTO>();
      ApplyFinanceHandleDTO applyFinanceHandleDTO = new ApplyFinanceHandleDTO();
      applyFinanceHandleDTO.setFinanceHandleId(financeHandleId);
//      applyFinanceHandleDTO.setUserIds(getUserIds(request));
      try {
         List<ApplyFinanceHandleDTO> applyFinanceHandleList = service.findAllApplyFinanceHandle(applyFinanceHandleDTO);
         // 把查询的结果封装成map
         for (ApplyFinanceHandleDTO f : applyFinanceHandleList) {
            if (StringUtil.isBlank(f.getOperator())&&type==1) {
               f.setOperator(loginUser.getRealName());
            }
            applyFinanceHandleMap.put(f.getRecPro() + "", f);
         }
         // 填充结果
         model.addAttribute("applyFinanceHandleMap", applyFinanceHandleMap);
      } catch (Exception e) {
         logger.error("根据财务办理的ID查询所有收款项目的财务办理信息失败.financeHandleId=" + financeHandleId + "错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
      }
   }
   /**
    * 财务收费
    *@author:liangyanjun
    *@time:2016年3月10日上午11:48:52
    *@param applyFinanceHandleDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/collectFee")
   public void collectFee(ApplyFinanceHandleDTO applyFinanceHandleDTO, HttpServletRequest request, HttpServletResponse response) {
      double poundage = applyFinanceHandleDTO.getPoundage();//手续费
      double interest = applyFinanceHandleDTO.getInterest();//咨询费
      double brokerage = applyFinanceHandleDTO.getBrokerage();//佣金
      String recAccount = applyFinanceHandleDTO.getRecAccount();//收款账号
      String recDate = applyFinanceHandleDTO.getRecDate();//收款日期
      String resource = applyFinanceHandleDTO.getResource();//收费来源
      String remark = applyFinanceHandleDTO.getRemark();//备注
      int financeHandleId = applyFinanceHandleDTO.getFinanceHandleId();
      // 检查数据是否合法
      if (financeHandleId <= 0 || poundage < 0 || interest < 0 || brokerage < 0 || StringUtil.isBlank(recAccount, recDate)) {
         fillReturnJson(response, false, "提交失败,数据不合法!");
         logger.error("数据不合法：" + applyFinanceHandleDTO);
         return;
      }
      if (StringUtil.isBlank(resource)) {
          fillReturnJson(response, false, "提交失败,请选择合法的收费来源");
          return;
      }
      try {
         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
         ShiroUser shiroUser = getShiroUser();
         //查询查财务受理主表信息
         FinanceHandleDTO financeHandleQuery = new FinanceHandleDTO();
         financeHandleQuery.setPid(financeHandleId);
         List<FinanceHandleDTO> financeHandleList = service.findAllFinanceHandle(financeHandleQuery);
         if (financeHandleList==null||financeHandleList.isEmpty()) {
            fillReturnJson(response, false, "提交失败,数据不存在!");
            return;
         }
         FinanceHandleDTO financeHandleDTO = financeHandleList.get(0);
         int projectId = financeHandleDTO.getProjectId();
         ProjectService.Client projectService=(ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN,"ProjectService");
         Project project = projectService.getProjectInfoById(projectId);
         //检查是否已撤单
         if (project.getIsChechan()==Constants.PROJECT_IS_CHECHAN) {
            fillReturnJson(response, false, "提交失败,该项目已经撤单或已经拒单");
            return;
         }
         //判断是否已回款，已回款不能挂起
         BaseClientFactory repaymentFactory = getFactory(BusinessModule.MODUEL_INLOAN, "RepaymentService");
         RepaymentService.Client repaymentClient = (RepaymentService.Client) repaymentFactory.getClient();
         RepaymentDTO repaymentInfo = repaymentClient.getRepaymentByProjectId(projectId);
         if (repaymentInfo.getStatus()==Constants.REPAYMENT_STATUS_2) {
             fillReturnJson(response, false, "提交失败，该业务已经回款");
             return;
         }
         // 查询要修改的申请财务受理信息
         ApplyFinanceHandleDTO query = new ApplyFinanceHandleDTO();
         // query.setUserIds(getUserIds(request));
         query.setFinanceHandleId(financeHandleId);
         query.setRows(1000);
         List<ApplyFinanceHandleDTO> applyFinanceHandleList = service.findAllApplyFinanceHandle(query);
         if (applyFinanceHandleList == null || applyFinanceHandleList.isEmpty()) {
            fillReturnJson(response, false, "提交失败,财务收费数据不存在!");
            logger.error("财务收费数据不存在："+ financeHandleId);
            return;
         }
         BaseClientFactory collectFeeHisFactory = getFactory(BusinessModule.MODUEL_INLOAN, "CollectFeeHisService");
         CollectFeeHisService.Client collectFeeHisService = (CollectFeeHisService.Client) collectFeeHisFactory.getClient();
         CollectFeeHis hisquery=new CollectFeeHis();
         hisquery.setFinanceHandleId(financeHandleId);
         //查询收费历史金额
         CollectFeeHis collectFeeHisMoney = collectFeeHisService.getCollectFeeHisMoney(hisquery);
         List<ApplyFinanceHandleDTO> updateApplyFinanceHandleList=new ArrayList<>();
         for (ApplyFinanceHandleDTO updateApplyFinanceHandleDTO : applyFinanceHandleList) {
            int recPro = updateApplyFinanceHandleDTO.getRecPro();
            //如果不是手续费，利息，佣金，则跳过
            if (recPro != Constants.REC_PRO_1 && recPro != Constants.REC_PRO_2 && recPro != Constants.REC_PRO_7) {
               continue;
            }
            if (recPro == Constants.REC_PRO_1) {
               interest+=collectFeeHisMoney.getConsultingFee();
               updateApplyFinanceHandleDTO.setRecMoney(interest);
            } else if (recPro == Constants.REC_PRO_2) {
               poundage+=collectFeeHisMoney.getPoundage();
               updateApplyFinanceHandleDTO.setRecMoney(poundage);
            } else if (recPro == Constants.REC_PRO_7) {
               brokerage+=collectFeeHisMoney.getBrokerage();
               updateApplyFinanceHandleDTO.setRecMoney(brokerage);
            }
            updateApplyFinanceHandleDTO.setRecAccount(recAccount);
            updateApplyFinanceHandleDTO.setRecDate(recDate);
            updateApplyFinanceHandleDTO.setOperator(shiroUser.getRealName());
            updateApplyFinanceHandleDTO.setResource(resource);
            updateApplyFinanceHandleDTO.setRemark(remark);
            updateApplyFinanceHandleList.add(updateApplyFinanceHandleDTO);
         }
         //执行收费
         service.collectFee(updateApplyFinanceHandleList);
         //保存收费记录
         CollectFeeHis collectFeeHis=new CollectFeeHis();
         collectFeeHis.setFinanceHandleId(financeHandleId);
         collectFeeHis.setProjectId(projectId);
         collectFeeHis.setConsultingFee(applyFinanceHandleDTO.getInterest());
         collectFeeHis.setBrokerage(applyFinanceHandleDTO.getBrokerage());
         collectFeeHis.setPoundage(applyFinanceHandleDTO.getPoundage());
         collectFeeHis.setRecDate(recDate);
         collectFeeHis.setProResource(resource);
         collectFeeHis.setRecAccount(recAccount);
         collectFeeHis.setCreaterId(shiroUser.getPid());
         collectFeeHisService.insert(collectFeeHis);
         //往业务动态插入一条记录
        String bizDynamicRemark="咨询费:"+MoneyFormatUtil.format(interest)+",手续费:"+MoneyFormatUtil.format(poundage)+",佣金:"+MoneyFormatUtil.format(brokerage);
         finishBizDynamicByInloan(projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_1, bizDynamicRemark);
         destroyFactory(collectFeeHisFactory,repaymentFactory);
      } catch (Exception e) {
         logger.error("修改财务收费失败：入参：applyFinanceHandleDTO" + applyFinanceHandleDTO + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         fillReturnJson(response, false, "提交失败,请联系管理员!");
         e.printStackTrace();
         return;
      }
      fillReturnJson(response, true, "提交成功");
   }
   /**
    * 财务放款
    *@author:liangyanjun
    *@time:2016年3月10日下午2:29:49
    *@param applyFinanceHandleDTO
    *@param isLoanFinish:放款是否已完结，未完结=0，已完结=1
    *@param request
    *@param response
    */
   @RequestMapping(value = "/makeLoans")
   public void makeLoans(ApplyFinanceHandleDTO applyFinanceHandleDTO,int isLoanFinish,int houseClerkId, HttpServletRequest request, HttpServletResponse response) {
      double recMoney = applyFinanceHandleDTO.getRecMoney();
      String recAccount = applyFinanceHandleDTO.getRecAccount();// 收款账号
      String recDate = applyFinanceHandleDTO.getRecDate();// 收款日期
      String resource = applyFinanceHandleDTO.getResource();// 款项来源1
      double resourceMoney = applyFinanceHandleDTO.getResourceMoney();
      String resource2 = applyFinanceHandleDTO.getResource2();// 款项来源2
      double resourceMoney2 = applyFinanceHandleDTO.getResourceMoney2();
      String remark = applyFinanceHandleDTO.getRemark();// 备注
      int recPro = applyFinanceHandleDTO.getRecPro();
      int financeHandleId = applyFinanceHandleDTO.getFinanceHandleId();
      BaseClientFactory clientFactory = null;
      // 检查数据是否合法
      if (financeHandleId <= 0 || StringUtil.isBlank(recAccount, recDate)) {
         fillReturnJson(response, false, "提交失败,数据不合法!");
         logger.error("数据不合法：" + applyFinanceHandleDTO);
         return;
      }
      
      if (recMoney <= 0) {
         fillReturnJson(response, false, "提交失败,金额要大于0!");
         return;
      }
      
      try {
         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
         ShiroUser shiroUser = getShiroUser();
         
         // 查询查财务受理主表信息
         FinanceHandleDTO financeHandleQuery = new FinanceHandleDTO();
         financeHandleQuery.setPid(financeHandleId);
         List<FinanceHandleDTO> financeHandleList = service.findAllFinanceHandle(financeHandleQuery);
         
         if (financeHandleList == null || financeHandleList.isEmpty()) {
            fillReturnJson(response, false, "提交失败,数据不存在!");
            return;
         }
      
         FinanceHandleDTO financeHandleDTO = financeHandleList.get(0);
         int projectId = financeHandleDTO.getProjectId();
   	  
         //根据projectId查询该产品编码
         Product product = new Product();
         clientFactory = getFactory(BusinessModule.MODUEL_PRODUCT, "ProductService");
         ProductService.Client client = (ProductService.Client) clientFactory.getClient();
	     product = client.findProductByProjectId(projectId);
	     String productNumber = "";
	     //提房贷判断，交易/非交易有赎楼提房贷的有二次放款
	     productNumber = product.getProductNumber();
         // 查询第一次放款申请财务受理信息
         ApplyFinanceHandleDTO makeLoanQuery = new ApplyFinanceHandleDTO();
         makeLoanQuery.setFinanceHandleId(financeHandleId);
         makeLoanQuery.setRecPro(Constants.REC_PRO_3);
         List<ApplyFinanceHandleDTO> makeLoanList = service.findAllApplyFinanceHandle(makeLoanQuery);
         
         if (makeLoanList == null || makeLoanList.isEmpty()) {
            fillReturnJson(response, false, "提交失败,查询第一次放款申请财务受理信息数据不存在!");
            logger.error("查询第一次放款申请财务受理信息数据不存在："+ financeHandleId);
            return;
         }
         
         ApplyFinanceHandleDTO makeLoans = makeLoanList.get(0);
         int applyStatus = makeLoans.getApplyStatus();
         
         //检查项目状态是否贷前流程走完
         ProjectService.Client projectService=(ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN,"ProjectService");
         Project project = projectService.getProjectInfoById(projectId);
         if (project.getForeclosureStatus()<Constants.FORECLOSURE_STATUS_10) {
            fillReturnJson(response, false, "提交失败,贷前审批未完成,不可放款!");
            return;
         }
         
         //检查是否已撤单
         if (project.getIsChechan()==Constants.PROJECT_IS_CHECHAN) {
            fillReturnJson(response, false, "提交失败,该项目已经撤单或已经拒单");
            return;
         }
         
         int financeHandleStatus = financeHandleDTO.getStatus();
         // 检查是否已经收费了
         if (financeHandleStatus == Constants.REC_STATUS_NO_CHARGE) {
            fillReturnJson(response, false, "提交失败,财务未收费，不可放款!");
            return;
         }
         
         //检测是不是有赎楼提放贷
         if(!Constants.JY_YSL_TFD.equals(productNumber) && !Constants.FJY_YSL_TFD.equals(productNumber)){
        	 // 检查是否已放款完结
             if (financeHandleStatus == Constants.REC_STATUS_ALREADY_LOAN) {
                fillReturnJson(response, false, "提交失败,财务已放款完结!");
                return;
             }
         }
         
         //检查放款申请工作流是否审核中
         if (applyStatus==Constants.APPLY_STATUS_4) {
            fillReturnJson(response, false, "提交失败,财务放款申请审核中!");
            return;
         }

	     if (project.getIsNeedHandle()==Constants.IS_NEED_HANDLE_1) {
		      //检查是否查档通过
		      IntegratedDeptService.Client integratedDeptServiceClient = (IntegratedDeptService.Client) getService(BusinessModule.MODUEL_INLOAN, "IntegratedDeptService");
		      CheckDocumentDTO checkDocumentQuery = new CheckDocumentDTO();
		      checkDocumentQuery.setProjectId(projectId);
		      List<CheckDocumentDTO> queryCheckDocument = integratedDeptServiceClient.queryCheckDocument(checkDocumentQuery);
		        if (queryCheckDocument == null || queryCheckDocument.isEmpty() || queryCheckDocument.get(0).getReCheckStatus() != Constants.CHECK_DOCUMENT_RE_CHECK_STATUS_3) {
		            fillReturnJson(response, false, "提交失败,查档复核未同意,不可放款!");
		             return;
		          }
		    }
         
         // 查询要修改的申请财务受理信息
         ApplyFinanceHandleDTO query = new ApplyFinanceHandleDTO();
         query.setFinanceHandleId(financeHandleId);
         query.setRecPro(recPro);
         
         ApplyFinanceHandleDTO updateApplyFinanceHandleDTO = null;
         
         List<ApplyFinanceHandleDTO> applyFinanceHandleList = service.findAllApplyFinanceHandle(query);
         if (applyFinanceHandleList == null || applyFinanceHandleList.isEmpty()) {
            fillReturnJson(response, false, "提交失败,修改的申请财务受理信息数据不存在!");
            logger.error("修改的申请财务受理信息数据不存在："+ financeHandleId);
            return;
         }
         updateApplyFinanceHandleDTO = applyFinanceHandleList.get(0);
         ApplyFinanceHandleDTO oldApplyFinanceHandleDTO = new ApplyFinanceHandleDTO();
         BeanUtils.copyProperties(updateApplyFinanceHandleDTO, oldApplyFinanceHandleDTO);
         updateApplyFinanceHandleDTO.setRecMoney(recMoney);
         updateApplyFinanceHandleDTO.setRecDate(recDate);
         updateApplyFinanceHandleDTO.setRecAccount(recAccount);
         updateApplyFinanceHandleDTO.setOperator(shiroUser.getRealName());
         updateApplyFinanceHandleDTO.setResource(resource);
         updateApplyFinanceHandleDTO.setResourceMoney(resourceMoney);
         updateApplyFinanceHandleDTO.setResource2(resource2);
         updateApplyFinanceHandleDTO.setResourceMoney2(resourceMoney2);
         updateApplyFinanceHandleDTO.setRemark(remark);
        if (updateApplyFinanceHandleDTO.getApplyStatus()==Constants.APPLY_STATUS_1) {
            updateApplyFinanceHandleDTO.setApplyStatus(Constants.APPLY_STATUS_2);
             }
         
        // applyStatus 第一次放款审批状态
        String bizDynamicRemark = "";
        if((Constants.JY_YSL_TFD.equals(productNumber) || Constants.FJY_YSL_TFD.equals(productNumber)) && Constants.APPLY_STATUS_5==applyStatus){
        	  // 执行更新 出账单打印及详情页审批流显示
            service.makeLoans(updateApplyFinanceHandleDTO, shiroUser.getPid(),isLoanFinish,houseClerkId);
            List<ApplyFinanceHandleDTO> list = service.getLoanHisByProjectId(projectId,productNumber);
            double firstLoanMoney= list.get(0).getRecMoney();
            String firstLoanDate= list.get(0).getUpdateDate();
            //往业务动态插入一条记录
            double totalMoney = service.getRecMoney(projectId, Arrays.asList(Constants.REC_PRO_9));
            bizDynamicRemark="第一次放款金额:"+MoneyFormatUtil.format(firstLoanMoney)+",第一次放款日期:"+firstLoanDate+"第二次放款金额:"+MoneyFormatUtil.format(totalMoney)+",第二次放款日期:"+recDate ;
            service.makeLoans(updateApplyFinanceHandleDTO, shiroUser.getPid(),isLoanFinish,houseClerkId);
        }else{
       	     // 执行更新
            service.makeLoans(updateApplyFinanceHandleDTO, shiroUser.getPid(),isLoanFinish,houseClerkId);
            //往业务动态插入一条记录
            double totalMoney = service.getRecMoney(projectId, Arrays.asList(Constants.REC_PRO_3,Constants.REC_PRO_4,Constants.REC_PRO_5,Constants.REC_PRO_6));
            bizDynamicRemark="放款金额:"+MoneyFormatUtil.format(totalMoney)+",放款日期:"+recDate;
        }
        finishBizDynamicByInloan(projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_7, bizDynamicRemark);
        // 记录日志
        recordLogOfApplyFinance(service, updateApplyFinanceHandleDTO, oldApplyFinanceHandleDTO, projectId);
      } catch (Exception e) {
         logger.error("修改财务收费失败：入参：applyFinanceHandleDTO" + applyFinanceHandleDTO + ",isLoanFinish="+isLoanFinish+",houseClerkId="+houseClerkId+"。错误：" + ExceptionUtil.getExceptionMessage(e));
         fillReturnJson(response, false, "提交失败,请联系管理员!");
         e.printStackTrace();
         return;
      }
      fillReturnJson(response, true, "提交成功");
   }
   /**
    * 财务收展期费
    *@author:liangyanjun
    *@time:2016年4月13日上午11:53:06
    *@param applyFinanceHandleDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/collectExtensionFee")
   public void collectExtensionFee(ApplyFinanceHandleDTO applyFinanceHandleDTO, HttpServletRequest request, HttpServletResponse response) {
      double extensionFee = applyFinanceHandleDTO.getExtensionFee();//展期费
      String recAccount = applyFinanceHandleDTO.getRecAccount();//收款账号
      String recDate = applyFinanceHandleDTO.getRecDate();//收款日期
      String resource = applyFinanceHandleDTO.getResource();//款项来源
      String remark = applyFinanceHandleDTO.getRemark();//备注
      int financeHandleId = applyFinanceHandleDTO.getFinanceHandleId();
      // 检查数据是否合法
      if (financeHandleId <= 0 ||extensionFee < 0 ||  StringUtil.isBlank(recAccount, recDate)) {
         fillReturnJson(response, false, "提交失败,数据不合法!");
         logger.error("数据不合法：" + applyFinanceHandleDTO);
         return;
      }
      if (StringUtil.isBlank(resource)) {
          fillReturnJson(response, false, "提交失败,请选择合法的收费来源");
          return;
      }
      try {
         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
         ShiroUser shiroUser = getShiroUser();
         //查询查财务受理主表信息
         FinanceHandleDTO financeHandleQuery = new FinanceHandleDTO();
         financeHandleQuery.setPid(financeHandleId);
         List<FinanceHandleDTO> financeHandleList = service.findAllFinanceHandle(financeHandleQuery);
         if (financeHandleList==null||financeHandleList.isEmpty()) {
            fillReturnJson(response, false, "提交失败,数据不存在!");
            return;
         }
         FinanceHandleDTO financeHandleDTO = financeHandleList.get(0);
         int projectId = financeHandleDTO.getProjectId();
         int financeHandleStatus = financeHandleDTO.getStatus();
         //检查是否已经收费了
         if (financeHandleStatus!=Constants.REC_STATUS_NO_CHARGE) {
            fillReturnJson(response, false, "提交失败,展期费已收!");
            return;
         }
         // 查询要修改的申请财务受理信息
         ApplyFinanceHandleDTO query = new ApplyFinanceHandleDTO();
         // query.setUserIds(getUserIds(request));
         query.setFinanceHandleId(financeHandleId);
         List<ApplyFinanceHandleDTO> applyFinanceHandleList = service.findAllApplyFinanceHandle(query);
         if (applyFinanceHandleList == null || applyFinanceHandleList.isEmpty()) {
            fillReturnJson(response, false, "提交失败,修改的申请财务受理信息数据不存在!");
            logger.error("修改的申请财务受理信息数据不存在："+ financeHandleId);
            return;
         }
         for (ApplyFinanceHandleDTO updateApplyFinanceHandleDTO : applyFinanceHandleList) {
            int recPro = updateApplyFinanceHandleDTO.getRecPro();
            //如果不是展期费，则跳过
            if (recPro != Constants.REC_PRO_8) {
               continue;
            }
            ApplyFinanceHandleDTO oldApplyFinanceHandleDTO = new ApplyFinanceHandleDTO();
            BeanUtils.copyProperties(updateApplyFinanceHandleDTO, oldApplyFinanceHandleDTO);
            updateApplyFinanceHandleDTO.setRecMoney(extensionFee);
            updateApplyFinanceHandleDTO.setRecAccount(recAccount);
            updateApplyFinanceHandleDTO.setRecDate(recDate);
            updateApplyFinanceHandleDTO.setOperator(shiroUser.getRealName());
            updateApplyFinanceHandleDTO.setResource(resource);
            updateApplyFinanceHandleDTO.setRemark(remark);
            //执行更新
            financeHandleDTO.setStatus(Constants.REC_STATUS_ALREADY_CHARGE);
            service.collectExtensionFee(financeHandleDTO, updateApplyFinanceHandleDTO);
            //记录日志
            recordLogOfApplyFinance(service, updateApplyFinanceHandleDTO, oldApplyFinanceHandleDTO, projectId);
            
            //往业务动态插入一条记录
//            String bizDynamicRemark="展期费:"+MoneyFormatUtil.format(extensionFee)+",收款日期:"+recDate+",收款账号:"+recAccount;
//            finishBizDynamicByInloan(projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_1, bizDynamicRemark);
         }
      } catch (Exception e) {
         logger.error("财务收展期费失败：入参：applyFinanceHandleDTO" + applyFinanceHandleDTO + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         fillReturnJson(response, false, "提交失败,请联系管理员!");
         e.printStackTrace();
         return;
      }
      fillReturnJson(response, true, "提交成功");
   }
   
   /**
    * 财务收费历史列表(分页查询)
    *@author:liangyanjun
    *@time:2017年3月24日上午10:39:10
    *@param query
    *@param request
    *@param response
    */
   @RequestMapping(value = "/collectFeeHisList")
    public void collectFeeHisList(CollectFeeHis query, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<CollectFeeHis> list = null;
        int total = 0;
        try {
            // 财务收费历史列表(分页查询)
            BaseClientFactory collectFeeHisFactory = getFactory(BusinessModule.MODUEL_INLOAN, "CollectFeeHisService");
            CollectFeeHisService.Client collectFeeHisService = (CollectFeeHisService.Client) collectFeeHisFactory.getClient();
            query.setProResourcelookupVal(checkUserOrg()==1?"WT_LOAN_SOURCES_OF_FUNDS":"LOAN_SOURCES_OF_FUNDS");//款项来源字典（区分万通和小科）
            list = collectFeeHisService.queryCollectFeeHis(query);
            total = collectFeeHisService.getCollectFeeHisTotal(query);
            destroyFactory(collectFeeHisFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 输出
        map.put("rows", list);
        map.put("total", total);
        outputJson(map, response);
    }
   /**
    * 
    *@author:liangyanjun
    *@time:2016年3月10日下午3:36:42
    *@param financeHandleId
    *@param recPros
    *@param request
    *@param response
    */
   @RequestMapping(value = "/getApplyFinanceHandle")
   public void getApplyFinanceHandle(int projectId,int financeHandleId,String recPros, HttpServletRequest request, HttpServletResponse response){
		if (StringUtil.isBlank(recPros) || financeHandleId <= 0
				|| projectId <= 0) {
			return;
		}
		Map<String, Object> result = new HashMap<>();
		//
		String[] strings = recPros.split(",");
		ArrayList<Integer> recProList = new ArrayList<Integer>();
		for (String recPro : strings) {
			recProList.add(Integer.parseInt(recPro));
		}
		Client service = (Client) getService(BusinessModule.MODUEL_INLOAN,
				serviceName);
		try {
			// 查询
			ApplyFinanceHandleDTO applyFinanceHandleQuery = new ApplyFinanceHandleDTO();
			applyFinanceHandleQuery.setFinanceHandleId(financeHandleId);
			applyFinanceHandleQuery.setRecProList(recProList);
			// applyFinanceHandleQuery.setUserIds(getUserIds(request));
			List<ApplyFinanceHandleDTO> list = service
					.findAllApplyFinanceHandle(applyFinanceHandleQuery);
			if (list == null || list.isEmpty()) {
				return;
			}
			for (ApplyFinanceHandleDTO applyFinanceHandle : list) {
				if (applyFinanceHandle.getRecPro() == Constants.REC_PRO_3
						|| applyFinanceHandle.getRecPro() == Constants.REC_PRO_9) {
					matchOrgData(projectId, applyFinanceHandle);
				}
			}
			result.put("list", list);

			// 当贷前选择的业务来源合作机构时，收费页面的收费来源默认选中合作机构
			ProjectService.Client projectService = (ProjectService.Client) getService(
					BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			Project project = projectService.getProjectInfoById(projectId);// 根据projectId查询该产品编码
			Product product = new Product();
			BaseClientFactory productClientFactory = getFactory(
					BusinessModule.MODUEL_PRODUCT, "ProductService");
			ProductService.Client productClient = (ProductService.Client) productClientFactory
					.getClient();
			product = productClient.findProductByProjectId(projectId);
			// 提放贷判断，交易/非交易有赎楼提房贷的有二次放款
			String productNumber = product.getProductNumber();
			result.put("productNumber", productNumber);
			int businessSource = project.getBusinessSource();
			int orgId = project.getBusinessSourceNo();
			if (businessSource == 13782 && orgId > 0) {// 业务来源为合作机构
				BaseClientFactory clientFactory = getAomFactory(
						BusinessModule.MODUEL_ORG,
						"OrgAssetsCooperationService");
				OrgAssetsCooperationService.Client client = (OrgAssetsCooperationService.Client) clientFactory
						.getClient();
				OrgAssetsCooperationInfo cooperationInfo = client
						.getById(orgId);
				String orgName = cooperationInfo.getOrgName();
				result.put("orgName", orgName);
			}
			// 返回结果集
			outputJson(result, response);
		} catch (Exception e) {
			logger.error("获取放款数据失败：入参：projectId" + projectId + "。错误："
					+ ExceptionUtil.getExceptionMessage(e));
			fillReturnJson(response, false, "获取放款数据失败,请联系管理员!");
			e.printStackTrace();
			return;
		}
	}
   private void recordLogOfApplyFinance(Client client, ApplyFinanceHandleDTO updateApplyFinanceHandleDTO, ApplyFinanceHandleDTO oldApplyFinanceHandleDTO,int projectId) throws TException {
      Long recPro = Long.parseLong(oldApplyFinanceHandleDTO.getRecPro()+"");//收款项目
      HashMap<ApplyFinanceHandleDTO._Fields, String> hashMap = new HashMap<ApplyFinanceHandleDTO._Fields,String>();
      hashMap.put(ApplyFinanceHandleDTO._Fields.REC_MONEY, "收款金额");
      hashMap.put(ApplyFinanceHandleDTO._Fields.REC_ACCOUNT, "收款账号");
      hashMap.put(ApplyFinanceHandleDTO._Fields.REC_DATE, "收款日期");
      hashMap.put(ApplyFinanceHandleDTO._Fields.RESOURCE, "款项来源");
      hashMap.put(ApplyFinanceHandleDTO._Fields.REMARK, "备注");
      Set<ApplyFinanceHandleDTO._Fields> keySet = hashMap.keySet();
      StringBuffer logsb=new StringBuffer();
      //检查值是否更新，更新了则写入日志
      for (ApplyFinanceHandleDTO._Fields key : keySet) {
         Object updateFieldValue = updateApplyFinanceHandleDTO.getFieldValue(key);//修改后的值
         Object oldFieldValue = oldApplyFinanceHandleDTO.getFieldValue(key);//原来的值
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
      logsb.insert(0, " 修改 财务受理信息("+Constants.REC_PRO_MAP.get(recPro)+"):");
      recordLog(BusinessModule.MODUEL_INLOAN, SysLogTypeConstant.LOG_TYPE_FINANCE_HANDLE, logsb.toString(),projectId);
   }
   /**
    * 根据项目id和收款项目查询申请状态，不存在返回-1，存在返回对于的状态
    * 申请状态：未申请=1，申请中=2，驳回=3，审核中=4，审核通过=5，审核不通过=6，已归档=7
    * 收款项目:咨询费=1,手续费=2,第一次放款=3,一次赎楼余额转二次放款=4,第二次放款=5,监管（客户）资金转入=6,佣金=7,展期费=8,第二次放款=9
    *@author:liangyanjun
    *@time:2016年8月8日上午11:34:07
    *@param applyFinanceHandleQuery
    *@param request
    *@param response
    *@return
    */
   @RequestMapping(value = "/getApplyStatus")
   @ResponseBody
   public int getApplyStatus(int projectId,int recPro) {
      try {
         //
         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN,serviceName);
         ApplyFinanceHandleDTO applyFinanceHandleDTO = service.getByProjectIdAndRecPro(projectId, recPro);
         if (applyFinanceHandleDTO == null || applyFinanceHandleDTO.getPid()<=0) {
            return -1;
         }
         int applyStatus = applyFinanceHandleDTO.getApplyStatus();
         return applyStatus;
      } catch (TException e) {
         e.printStackTrace();
         return -1;
      }
   }

   /**
    * 根据项目id和收款项目查询财务受理的金额
    *@author:liangyanjun
    *@time:2016年8月8日下午4:05:40
    *@param projectId
    *@param recPro 收款项目:咨询费=1,手续费=2,第一次放款=3,一次赎楼余额转二次放款=4,第二次放款=5,监管（客户）资金转入=6,佣金=7,展期费=8
    *@return
    */
   @RequestMapping(value = "/getMakeLoansMoney")
   @ResponseBody
   public double getMakeLoansMoney(int projectId, int recPro) {
      try {
         //
         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
         ApplyFinanceHandleDTO applyFinanceHandleDTO = service.getByProjectIdAndRecPro(projectId, recPro);
         if (applyFinanceHandleDTO == null || applyFinanceHandleDTO.getPid() <= 0) {
            return 0;
         }
         double recMoney = applyFinanceHandleDTO.getRecMoney();
         return recMoney;
      } catch (TException e) {
         e.printStackTrace();
         return 0;
      }
   }
   /**
    * 根据项目id查询放款状态
    *@author:liangyanjun
    *@time:2016年8月8日下午2:14:36
    *@param projectId
    *@param request
    *@param response
    *@return
    */
   @RequestMapping(value = "/getFinanceStatus")
   @ResponseBody
   public int getFinanceStatus(int projectId) {
      try {
         //
         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN,serviceName);
         FinanceHandleDTO financeHandleDTO = service.getFinanceHandleByProjectId(projectId);
         int status = financeHandleDTO.getStatus();
         return status;
      } catch (TException e) {
         e.printStackTrace();
         return -1;
      }
   }
   /**
    * 获取放款挂起数据
    *@author:liangyanjun
    *@time:2017年3月28日下午2:29:21
    *@param projectId
    *@param request
    *@param response
    */
   @RequestMapping(value = "/getLoanSuspendInfo")
   public void getLoanSuspendInfo(int projectId, HttpServletRequest request, HttpServletResponse response) {
       Map<String, Object> map = new HashMap<String, Object>();
       try {
           // 财务收费历史列表(分页查询)
           BaseClientFactory factory = getFactory(BusinessModule.MODUEL_INLOAN, "LoanSuspendRecordService");
           LoanSuspendRecordService.Client client = (LoanSuspendRecordService.Client) factory.getClient();
           LoanSuspendRecord query=new LoanSuspendRecord();
           query.setProjectId(projectId);
           List<LoanSuspendRecord> list = client.getAll(query);
           if (list!=null&&!list.isEmpty()) {
               map.put("loanSuspend", list.get(0));
           }
           destroyFactory(factory);
       } catch (Exception e) {
           e.printStackTrace();
       }
       // 输出
       outputJson(map, response);
   }
   /**
    * 放款挂起
    *@author:liangyanjun
    *@time:2017年3月28日下午2:24:07
    *@param projectId
    *@param request
    *@param response
    */
   @RequestMapping(value = "/loanSuspendInfo")
   public void loanSuspendInfo(LoanSuspendRecord loanSuspend, HttpServletRequest request, HttpServletResponse response) {
       int projectId = loanSuspend.getProjectId();//项目id
       String startDate = loanSuspend.getStartDate();//开始时间
       String endDate = loanSuspend.getEndDate();//结束时间
       int suspendDay = loanSuspend.getSuspendDay();//挂起天数
       if (projectId<=0||suspendDay<=0||StringUtil.isBlank(startDate,endDate)) {
           fillReturnJson(response, false, "提交失败,数据不合法!");
           logger.error("数据不合法：" + loanSuspend);
           return;
       }
       try {
           ShiroUser shiroUser = getShiroUser();
           //判断是否已回款，已回款不能挂起
           BaseClientFactory repaymentFactory = getFactory(BusinessModule.MODUEL_INLOAN, "RepaymentService");
           RepaymentService.Client repaymentClient = (RepaymentService.Client) repaymentFactory.getClient();
           RepaymentDTO repaymentInfo = repaymentClient.getRepaymentByProjectId(projectId);
           if (repaymentInfo.getStatus()==Constants.REPAYMENT_STATUS_2) {
               fillReturnJson(response, false, "提交失败，该业务已经回款");
               return;
           }
           //检查是否有展期申请中，展期申请中，不能挂起
           BaseClientFactory loanExtensionFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "LoanExtensionService");
           LoanExtensionService.Client loanExtensionClient = (LoanExtensionService.Client) loanExtensionFactory.getClient();
           int foreExtensionByPid = loanExtensionClient.getForeExtensionByPid(projectId);
           if (foreExtensionByPid>0) {
               fillReturnJson(response, false, "提交失败，该业务正在展期申请中");
               return;
           }
           // 财务收费历史列表(分页查询)
           BaseClientFactory factory = getFactory(BusinessModule.MODUEL_INLOAN, "LoanSuspendRecordService");
           LoanSuspendRecordService.Client client = (LoanSuspendRecordService.Client) factory.getClient();
           LoanSuspendRecord query=new LoanSuspendRecord();
           query.setProjectId(projectId);
           List<LoanSuspendRecord> list = client.getAll(query);
           //判断是否已经提交过，目前业务只能申请一次挂起
           if (list!=null&&list.size()>0) {
               fillReturnJson(response, false, "提交失败，该业务已经提交过，不能重复提交");
               return;
           }
           //执行放款挂起
           loanSuspend.setCreaterId(shiroUser.getPid());
           client.loanSuspendInfo(loanSuspend);
           //记录日志
           String logMsg="放款挂起:入参：loanSuspend" + loanSuspend;
           recordLog(BusinessModule.MODUEL_ORG,SysLogTypeConstant.LOG_TYPE_UPDATE, logMsg, projectId);
           destroyFactory(factory,repaymentFactory,loanExtensionFactory);
       } catch (Exception e) {
           logger.error("放款挂起失败：入参：loanSuspend" + loanSuspend + "。错误：" + ExceptionUtil.getExceptionMessage(e));
           fillReturnJson(response, false, "提交异常,请联系管理员!");
           e.printStackTrace();
           return;
       }
       fillReturnJson(response, true, "提交成功");
   }
   
   /**
    * 根据项目id和收款项目查询财务受理的金额
    *@author:Jony
    *@time:2017年7月8日下午4:05:40
    *@param projectId
    *@param recPro 收款项目:咨询费=1,手续费=2,第一次放款=3,一次赎楼余额转二次放款=4,第二次放款=5,监管（客户）资金转入=6,佣金=7,展期费=8
    *@return
    */
   @RequestMapping(value = "/getApplayStatusByProjectId")
   @ResponseBody
   public int getApplayStatusByProjectId(int projectId, int recPro) {
      try {
    	  
         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
         ApplyFinanceHandleDTO applyFinanceHandleDTO = service.getByProjectIdAndRecPro(projectId, recPro);
         if (applyFinanceHandleDTO == null || applyFinanceHandleDTO.getPid() <= 0) {
            return 0;
         }
         int applyStatus = applyFinanceHandleDTO.getApplyStatus();
         return applyStatus;
      } catch (TException e) {
         e.printStackTrace();
         return 0;
      }
   }
   
   /**
    * 根据项目id查询放款记录
    *@author:Jony
    *@time:2017年7月19日下午4:05:40
    *@param projectId
    *@param recPro 
    *@return
    */
   @RequestMapping(value = "/getLoanHisByProjectId", method = RequestMethod.POST)
   @ResponseBody
   public void getLoanHisByProjectId(int projectId,HttpServletRequest request, HttpServletResponse response) {// 需要返回的Map集合
		Map<String, Object> map = new HashMap<String, Object>();
		 List<ApplyFinanceHandleDTO> list = new ArrayList<ApplyFinanceHandleDTO>();
		try {
			//根据projectId查询该产品编码
	         Product product = new Product();
				BaseClientFactory productClientFactory = getFactory(
						BusinessModule.MODUEL_PRODUCT, "ProductService");
				ProductService.Client productClient = (ProductService.Client) productClientFactory
						.getClient();
		     product = productClient.findProductByProjectId(projectId);
		     String productNum = "";
		     //提房贷判断，交易/非交易有赎楼提房贷的有二次放款
		     productNum = product.getProductNumber();
			  Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
			  list = service.getLoanHisByProjectId(projectId,productNum);
			  int total = list.size();
			  map.put("rows", list);
			  map.put("total", total);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询个人信息列表-贷前:" + e.getMessage());
			}
		} 
		// 输出
		outputJson(map, response);
   }
   
   /**
	 * @Description: 校验放款(对于深圳的单查档/查诉讼时间不能超过24小时，对于非深圳的单不能超过48小时)
	 * @author: jony
	 * @param projectId
     * @param financeHandleId 
     * @param chooseType 
	 * @date: 2017年7月15日
	 */
	@RequestMapping(value = "/validateApprove")
	@ResponseBody
	public void validateApprove(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "projectId") int projectId, @RequestParam(value = "financeHandleId") int financeHandleId,@RequestParam(value = "chooseType") int chooseType) throws Exception {
		logger.info("校验放款参数：projectId：" + projectId + ",financeHandleId:" + financeHandleId+",chooseType:"+chooseType);
		Json j = super.getSuccessObj();
		try {
			// 查询第一次放款申请财务受理信息
			ApplyFinanceHandleDTO makeLoanQuery = new ApplyFinanceHandleDTO();
			makeLoanQuery.setFinanceHandleId(financeHandleId);
			makeLoanQuery.setRecPro(Constants.REC_PRO_3);
			Client service = (Client) getService(BusinessModule.MODUEL_INLOAN,
					serviceName);
			List<ApplyFinanceHandleDTO> makeLoanList = service
					.findAllApplyFinanceHandle(makeLoanQuery);
			if (makeLoanList == null || makeLoanList.isEmpty()) {
				fillReturnJson(response, false, "查询第一次放款申请财务受理信息数据不存在!");
				logger.error("查询第一次放款申请财务受理信息数据不存在：" + financeHandleId);
				return;
			}
			ApplyFinanceHandleDTO makeLoans = makeLoanList.get(0);
			// 查询第一次是不是通过
			int applyStatus = makeLoans.getApplyStatus();

			// 获取最近查档时间
			List<CheckDocumentDTO> checkDocumentHisList = null;
			CheckDocumentDTO checkDocument = new CheckDocumentDTO();
			//1=人工查档
			checkDocument.setCheckWay(1);
			checkDocument.setProjectId(projectId);

			CheckDocumentHisService.Client checkDocumentHisService = (CheckDocumentHisService.Client) getService(
					BusinessModule.MODUEL_INLOAN, "CheckDocumentHisService");
			checkDocumentHisList = checkDocumentHisService
					.queryCheckDocumentHisIndex1(checkDocument);
			// 最新的查档时间
			String lastCheckDocumentHisTime = checkDocumentHisList.get(0)
					.getCheckDate();
			logger.info("获取最近查档时间成功" + lastCheckDocumentHisTime);

			// 获取最新查诉讼时间
			List<CheckLitigationDTO> checkLitigationHisList = null;
			CheckLitigationDTO query = new CheckLitigationDTO();
			query.setProjectId(projectId);
			query.setApprovalStatus(3);
			query.setCheckWay(1);
			CheckLitigationHisService.Client litigationHisService = (CheckLitigationHisService.Client) getService(
					BusinessModule.MODUEL_INLOAN, "CheckLitigationHisService");
			checkLitigationHisList = litigationHisService
					.queryCheckLitigationHisIndex1(query);
			// 最新的查诉讼时间
			String lastCheckLitigationHisTime = checkLitigationHisList.get(0)
					.getCheckDate();
			logger.info("获取最新的查诉讼时间成功：" + lastCheckLitigationHisTime);

			// 获取当前时间
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
			String dateNowStr = sdf.format(d);

			Project project = new Project();
			BaseClientFactory ProjectFactory = getFactory(
					BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client projectService = (ProjectService.Client) ProjectFactory
					.getClient();
			project = projectService.getProjectInfoById(projectId);

			// 查询出所有的法定假日
			WorkDay workDay = new WorkDay();
			workDay.setIsHolidays(1);
			List<WorkDay> workDayList = null;
			BaseClientFactory workDayClientFactory = null;
			workDayClientFactory = getFactory(BusinessModule.MODUEL_WORK_DAY,
					"WorkDayService");
			WorkDayService.Client workDayclient = (WorkDayService.Client) workDayClientFactory
					.getClient();
			// 所有的法定节假日
			workDayList = workDayclient.getWorkDay(workDay);
			// 所有的法定节假日放在一个集合中
			List<String> workDayNotWork = new ArrayList<String>();
			if (workDayList != null && workDayList.size() > 0) {
				for (int i = 0; i < workDayList.size(); i++) {
					workDayNotWork.add(workDayList.get(i).getCorrectDate());
				}
			}
			logger.info("获取所有的法定节假日成功：" + workDayNotWork);

			// 查询最新查档/查诉讼到放款这段时间的法定假日
			WorkDay btLastToNowNotWork = new WorkDay();
			btLastToNowNotWork.setIsHolidays(1);
			btLastToNowNotWork.setFromCorrectDate(lastCheckDocumentHisTime
					.substring(0, 10));
			btLastToNowNotWork.setToCorrectDate(DateUtils.getCurrentDate());
			List<WorkDay> workDayNotWorkDays = null;

			// 查询出最新查档/查诉讼到放款这段时间的所有法定假日
			workDayNotWorkDays = workDayclient.getWorkDay(btLastToNowNotWork);
			// 查询最新查档/查诉讼到放款这段时间的法定假日 放在一个集合中
			List<String> btworkDayNotWork = new ArrayList<String>();
			if (workDayNotWorkDays != null && workDayNotWorkDays.size() > 0) {
				for (int i = 0; i < workDayNotWorkDays.size(); i++) {
					btworkDayNotWork.add(workDayNotWorkDays.get(i)
							.getCorrectDate());
				}
			}
			logger.info("获取最新查档/查诉讼到放款这段时间的所有法定假日成功：" + btworkDayNotWork);
			
			// 最新查档/查诉讼到放款这段时间的周末
			List<String> btLastToNowWeekDays = getWeekDays(
					lastCheckDocumentHisTime.substring(0, 10),
					DateUtils.getCurrentDate());

			// 最新查档/查诉讼到放款这段时间的周末上班的日期放在一个集合中
			List<String> btLastToNowWeekDayWork = new ArrayList<String>();
			if (btLastToNowWeekDays != null && btLastToNowWeekDays.size() > 0) {
				for (int i = 0; i < btLastToNowWeekDays.size(); i++) {
					WorkDay repeatWeekday = new WorkDay();
					repeatWeekday.setCorrectDate(btLastToNowWeekDays.get(i));
					repeatWeekday.setIsHolidays(-1);
					List<WorkDay> repeatWeekdayList = workDayclient
							.getWorkDay(repeatWeekday);

					if (repeatWeekdayList != null
							&& repeatWeekdayList.size() > 0) {
						if (repeatWeekdayList.get(0).getIsHolidays() == 2) {
							btLastToNowWeekDayWork.add(repeatWeekdayList.get(0)
									.getCorrectDate());
						}
					}
				}
			}
			logger.info("获取最新查档/查诉讼到放款这段时间的周末成功：" + btLastToNowWeekDayWork);

			// 根据projectId查询该产品编码
			Product product = new Product();
			BaseClientFactory clientFactory = getFactory(
					BusinessModule.MODUEL_PRODUCT, "ProductService");
			ProductService.Client client = (ProductService.Client) clientFactory
					.getClient();
			product = client.findProductByProjectId(projectId);
			// 提放贷判断，交易/非交易有赎楼提房贷的有二次放款
			String productNumber = product.getProductNumber();
			
			/*
			 * a)对于“交易有赎楼提放贷”、“非交易有赎楼提放贷”，允许二次放款，即便有过一次放款，还需要出现在列表中。通过产品编码判断。
			 * b)在放款申请之时，需要做最后一次查档、查诉讼时间的判断；任何产品均需做此判断（目前包括提放贷和赎楼贷）。
			 * 对于深圳单，当最后一次查档或查诉讼 (系统查档不算)时间超过工作日24小时，不允许提交放款申请。需提示“深圳单，查档或者查诉讼
			 * (实际情况而定)超过24小时失效，请重新查档或查诉讼！”
			 * 对于非深圳单，当最后一次查档或查诉讼(系统查档不算)时间超过工作日48小时
			 * ，不允许提交放款申请。需提示“非深圳单，查档或者查诉讼(实际情况而定)超过48小时失效，请重新查档或查诉讼！”
			 * 注：深圳与非深圳单的判断标准，根据订单对应的录单员（依据组织架构）归属来判断。 c)对于“交易有赎楼提放贷”、“非交易有赎楼提放贷
			 * ”，第二次放款时，除了查档、查诉讼有效时间判断外，还需要判断是否有提交赎楼凭证； 判断逻辑：在保后监管中有个赎楼凭证上传的入口
			 * ，如果有上传文件，则认为是有上传，如下图所示，至于传的文件是否是赎楼凭证，目前系统无法判断
			 * ，文件的真实性和准确性纯靠线下贷后人员主动和细心来规避 ，在做需求功能培训之时，必须强调此内容。当赎楼凭证资料没上传时，提示“
			 * 赎楼扣款凭证未上传，不能提交二次放款申请！”，返回列表。
			 */
			//查诉讼返回值
			boolean isApproveLitigationHis = isApprove(
					lastCheckLitigationHisTime, dateNowStr, project,
					workDayNotWork, btworkDayNotWork, btLastToNowWeekDayWork);
			
			//查档返回值
			boolean isApproveDocumentHis = isApprove(lastCheckDocumentHisTime,
					dateNowStr, project, workDayNotWork, btworkDayNotWork,
					btLastToNowWeekDayWork);
			//查诉讼时间是不是超时
			if (!isApproveLitigationHis) {
				j.getHeader().put("success", false);
				j.getHeader().put("code", 400);
				j.getHeader().put("msg", "查诉讼已经超过有效时间，请重新查诉讼!");
			}
			//查档时间是不是超时
			if (!isApproveDocumentHis) {
				j.getHeader().put("success", false);
				j.getHeader().put("code", 400);
				j.getHeader().put("msg", "查档已经超过有效时间，请重新查档!");
			}
			//假如是交易/非交易有赎楼提放贷则去校验是不是上传赎楼凭证（这里只去校验有没有上传不考虑上传了什么）
			if ((Constants.JY_YSL_TFD.equals(productNumber) || Constants.FJY_YSL_TFD
					.equals(productNumber))
					&& applyStatus == Constants.APPLY_STATUS_5) {
				List<HandleDynamicFileDTO> list = new ArrayList<>();

				BaseClientFactory bizHandleFactory = getFactory(
						BusinessModule.MODUEL_INLOAN, "BizHandleService");
				BizHandleService.Client bizHandleClient = (BizHandleService.Client) bizHandleFactory
						.getClient();
				list = bizHandleClient
						.findHandleDynamicFileByProjectId(projectId);
				if (list == null || list.size() == 0) {
					j.getHeader().put("success", false);
					j.getHeader().put("code", 400);
					j.getHeader().put("msg", "请上传赎楼凭证!");
				}
			}
		} catch (Exception e) {
			logger.error("validateApprove校验失败：" + ExceptionUtil.getExceptionMessage(e));
		}
		outputJson(j, response);
	}
   
   
	// workDayNotWork=所有的具体的法定节假日   btWorkDayNotWork = 时间差内的具体的法定节假日   btLastToNowWeekDayWork=某个时间段的周末上班的日期放在一个集合中
   private static boolean isApprove(String beginTime,String endTime,Project project,
	 List<String> workDayNotWork,List<String> btWorkDayNotWork,List<String> btLastToNowWeekDayWork) throws ParseException{
	   
	   //查询某个时间段的周末
       List<String> repeatWeekdays  = getWeekDays(beginTime.substring(0, 10),endTime.substring(0, 10));
       int repeatWeekDaySize = repeatWeekdays.size();
       
       //时间差与节假日重复的天数
       int repeatSize = getIntersection(workDayNotWork,btWorkDayNotWork);
       
       //时间差内周末上班的天数
       int betweenWeekSize = btLastToNowWeekDayWork.size();
       
	   DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
	   //真正的时间差
	   long hours = DateUtils.hoursDiffs(sdf.parse(beginTime) , sdf.parse(endTime));
	   //不上班的时间 = 【（时间差与节假日重复的天数 + 时间差内的周末天数 - 时间差内周末上班的天数】 * 24
	   //真正的时间差 - 不上班的时间
       int last = (int) (hours - (repeatSize + repeatWeekDaySize -betweenWeekSize)*24);
       
       //判断此单为何地的单
	   String areaCode = project.getAreaCode();
	   //假如是空则为深圳的单
//	   if(StringUtil.isBlank(areaCode)){
//		   areaCode = "440300";
//	   }
	   //当小于等于24小时的时候直接过
	   if(last <= 24){
		   return true;
		//当大于等于24小于等于48小时的时候判断是不是深圳的单，是则不过，不是则过
	   }else if(24 < last && last <= 48){
		   //非深圳可以放
		   if(!"440300".equals(areaCode)){
			   return true;
			   //深圳不可以放
		   }else{
			   return false;
		   }
	   }else{
		   return false;
	   }
   }
   
   /*
    * 判断最新查档查诉讼到放款之间的周末天数
    */
   private static List<String> getWeekDays(String fromDate,String endDate) {
		 Calendar c_begin = new GregorianCalendar();
	     Calendar c_end = new GregorianCalendar();
	     //2017-07-12
	     List<String> weekDays =  new ArrayList<String>();
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	     int beginYear = Integer.parseInt(fromDate.substring(0, 4));
	     int beginMonth = Integer.parseInt(fromDate.substring(6,7)) - 1;
	     int beginDate = Integer.parseInt(fromDate.substring(8));
	     
	     int endYear = Integer.parseInt(endDate.substring(0,4));
	     int endMonth = Integer.parseInt(endDate.substring(6,7)) - 1;
	     int endDate1 = Integer.parseInt(endDate.substring(8));
	     
	     c_begin.set(beginYear, beginMonth, beginDate); //
	     c_end.set(endYear, endMonth, endDate1); 
	     List<String> weekDays1 =  new ArrayList<String>();
	     
	     while(c_begin.before(c_end)){
	    	   weekDays.add(sdf.format(c_begin.getTime()));
	      c_begin.add(Calendar.DAY_OF_YEAR, 1);
	     }
	     for(int i=0;i<weekDays.size();i++){
	    	 //转换成date类型  例如：2000-02-12 不带时分秒
	    	 Date weekDate = DateUtils.stringToDate1(weekDays.get(i));
	    	 //判断当前日期是否为工作日
	    	 boolean flag = DateUtils.isWork(weekDate);
	    	 if(!flag){
	    		 weekDays1.add(weekDays.get(i)); 
	    	 }
	     }
		return weekDays1;
   }
   
   /*
    * 判断最新查档查诉讼到放款之间的日期与节假日重复的天数
    */
   private static int getIntersection(List<String> list1,
           List<String> list2) {
       List<String> result = new ArrayList<String>();
       for (String integer : list2) {//遍历list1
           if (list1.contains(integer)) {//如果存在这个数
               result.add(integer);//放进一个list里面，这个list就是交集
           }
       }
       return result.size();
   }
   /**
    * 房抵贷收费
    *@author:liangyanjun
    *@time:2017年12月11日上午11:02:48
    *@param applyFinanceHandleDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/collectfddFee")
   public void collectfddFee(ApplyFinanceHandleDTO applyFinanceHandleDTO, HttpServletRequest request, HttpServletResponse response) {
      double interest = applyFinanceHandleDTO.getInterest();//金额
      String recAccount = applyFinanceHandleDTO.getRecAccount();//收款账号
      String recDate = applyFinanceHandleDTO.getRecDate();//收款日期
      int recPro = applyFinanceHandleDTO.getRecPro();//收费项目
      int financeHandleId = applyFinanceHandleDTO.getFinanceHandleId();
      // 检查数据是否合法
      if (financeHandleId <= 0 || interest < 0 || recPro < 0 ||StringUtil.isBlank(recAccount, recDate)) {
         fillReturnJson(response, false, "提交失败,数据不合法!");
         logger.error("数据不合法：" + applyFinanceHandleDTO);
         return;
      }
      try {
         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
         ShiroUser shiroUser = getShiroUser();
         Integer userId = shiroUser.getPid();
         //查询查财务受理主表信息
         FinanceHandleDTO financeHandleQuery = new FinanceHandleDTO();
         financeHandleQuery.setPid(financeHandleId);
         List<FinanceHandleDTO> financeHandleList = service.findAllFinanceHandle(financeHandleQuery);
         if (financeHandleList==null||financeHandleList.isEmpty()) {
            fillReturnJson(response, false, "提交失败,数据不存在!");
            return;
         }
         FinanceHandleDTO financeHandleDTO = financeHandleList.get(0);
         int projectId = financeHandleDTO.getProjectId();
         //判断是否已回款，已回款不能挂起
         BaseClientFactory repaymentFactory = getFactory(BusinessModule.MODUEL_INLOAN, "RepaymentService");
         RepaymentService.Client repaymentClient = (RepaymentService.Client) repaymentFactory.getClient();
         RepaymentDTO repaymentInfo = repaymentClient.getRepaymentByProjectId(projectId);
         if (repaymentInfo.getStatus()==Constants.REPAYMENT_STATUS_2) {
             fillReturnJson(response, false, "提交失败，该业务已经回款");
             return;
         }
         // 查询要修改的申请财务受理信息
         ApplyFinanceHandleDTO query = new ApplyFinanceHandleDTO();
         query.setFinanceHandleId(financeHandleId);
         query.setRecPro(recPro);
         List<ApplyFinanceHandleDTO> applyFinanceHandleList = service.findAllApplyFinanceHandle(query);
         if (applyFinanceHandleList == null || applyFinanceHandleList.isEmpty()) {
            fillReturnJson(response, false, "提交失败,财务收费数据不存在!");
            logger.error("财务收费数据不存在："+ financeHandleId);
            return;
         }
         ApplyFinanceHandleDTO updateApplyFinanceHandle = applyFinanceHandleList.get(0);
         double recMoney = updateApplyFinanceHandle.getRecMoney();
         updateApplyFinanceHandle.setInterest(interest);
         updateApplyFinanceHandle.setRecMoney(interest+recMoney);//金额累加
         updateApplyFinanceHandle.setRecAccount(recAccount);
         updateApplyFinanceHandle.setRecDate(recDate);
         updateApplyFinanceHandle.setOperator(shiroUser.getRealName());
         updateApplyFinanceHandle.setCreaterId(shiroUser.getPid());
         service.collectFddFee(updateApplyFinanceHandle);
         finishBizDynamicByMortgage(userId, projectId, MortgageDynamicConstant.MODUEL_NUMBER_FINANCE,MortgageDynamicConstant.DYNAMIC_NUMBER_FINANCE_1, "");
      } catch (Exception e) {
         logger.error("财务收费失败：入参：applyFinanceHandleDTO" + applyFinanceHandleDTO + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         fillReturnJson(response, false, "提交失败,请联系管理员!");
         e.printStackTrace();
         return;
      }
      fillReturnJson(response, true, "提交成功");
   }
   
   /**
    * 保存房抵贷放款信息
    *@author:liangyanjun
    *@time:2017年12月13日上午10:35:53
    *@param applyFinanceHandleDTO
    *@param isLoanFinish
    *@param houseClerkId
    *@param request
    *@param response
    */
   @RequestMapping(value = "/saveFddMakeLoansInfo")
   public void saveFddMakeLoansInfo(ApplyFinanceHandleDTO applyFinanceHandleDTO, HttpServletRequest request, HttpServletResponse response) {
      double recMoney = applyFinanceHandleDTO.getRecMoney();
      String recAccount = applyFinanceHandleDTO.getRecAccount();// 收款账号
      String recDate = applyFinanceHandleDTO.getRecDate();// 收款日期
      String remark = applyFinanceHandleDTO.getRemark();// 备注
      int financeHandleId = applyFinanceHandleDTO.getFinanceHandleId();
      // 检查数据是否合法
      if (financeHandleId <= 0 || StringUtil.isBlank(recAccount, recDate)) {
         fillReturnJson(response, false, "提交失败,数据不合法!");
         logger.error("数据不合法：" + applyFinanceHandleDTO);
         return;
      }
      if (recMoney <= 0) {
         fillReturnJson(response, false, "提交失败,金额要大于0!");
         return;
      }
      ShiroUser shiroUser = getShiroUser();
      Integer userId = shiroUser.getPid();
      Client service = (Client) getService(BusinessModule.MODUEL_INLOAN,serviceName);
      int isLoanFinish=1;//放款是否已完结，未完结=0，已完结=1
      try {
         // 查询查财务受理主表信息
         FinanceHandleDTO financeHandleQuery = new FinanceHandleDTO();
         financeHandleQuery.setPid(financeHandleId);
         List<FinanceHandleDTO> financeHandleList = service.findAllFinanceHandle(financeHandleQuery);
         
         if (financeHandleList == null || financeHandleList.isEmpty()) {
            fillReturnJson(response, false, "提交失败,数据不存在!");
            return;
         }
      
         FinanceHandleDTO financeHandleDTO = financeHandleList.get(0);
         int collectFeeStatus = financeHandleDTO.getCollectFeeStatus();
         int projectId = financeHandleDTO.getProjectId();
         BaseClientFactory ProjectFactory = getFactory(
               BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
         ProjectService.Client projectService = (ProjectService.Client) ProjectFactory.getClient();
         Project project = projectService.getLoanProjectByProjectId(projectId);
         ProjectGuarantee projectGuarantee = project.getProjectGuarantee();
         int chargesType = projectGuarantee.getChargesType();//(赎楼贷)收费方式:贷前收费=1，贷后收费=2。（房抵贷）收费节点：下户前收费=1，放款前收费=2，任意节点收费=3
         // 检查是否已经收费了
         if (chargesType==2&&collectFeeStatus == Constants.REC_STATUS_NO_CHARGE) {
            fillReturnJson(response, false, "提交失败,财务未收费，不可放款!");
            return;
         }
         //查询当前任务是否可以更改放款数据
         WorkflowService.Client workflowService=(WorkflowService.Client) getService(BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
         WorkflowProjectVo workflowProjectQuery=new WorkflowProjectVo();
         workflowProjectQuery.setProjectId(projectId);
         workflowProjectQuery.setProcessDefinitionKey("fddMakeLoansProcess");
         List<WorkflowProjectVo> workflowProjectVos = workflowService.getRunWorkflowProject(workflowProjectQuery);
         if (workflowProjectVos!=null&&workflowProjectVos.size()>0) {
            WorkflowProjectVo workflowProjectVo = workflowProjectVos.get(0);
            String workflowInstanceId = workflowProjectVo.getWorkflowInstanceId();
            Map<String, String> taskMap = workflowService.getTaskMapByProcessInstanceId(workflowInstanceId);
            String taskDefinitionKey = taskMap.get("taskDefinitionKey");//任务key
            if (!"task_FundManagerCheck".equals(taskDefinitionKey)&&!"task_inputData".equals(taskDefinitionKey)) {
               fillReturnJson(response, false, "提交失败,该节点不能修改放款数据!");
               return;
            }
         }
         // 查询要修改的申请财务受理信息
         ApplyFinanceHandleDTO query = new ApplyFinanceHandleDTO();
         query.setFinanceHandleId(financeHandleId);
         query.setRecPro(Constants.REC_PRO_3);
         List<ApplyFinanceHandleDTO> applyFinanceHandleList = service.findAllApplyFinanceHandle(query);
         if (applyFinanceHandleList == null || applyFinanceHandleList.isEmpty()) {
            fillReturnJson(response, false, "提交失败,修改的申请财务受理信息数据不存在!");
            return;
         }
         ApplyFinanceHandleDTO updateApplyFinanceHandleDTO = applyFinanceHandleList.get(0);
         updateApplyFinanceHandleDTO.setRecMoney(recMoney);
         updateApplyFinanceHandleDTO.setRecDate(recDate);
         updateApplyFinanceHandleDTO.setRecAccount(recAccount);
         updateApplyFinanceHandleDTO.setRemark(remark);
         service.makeLoans(updateApplyFinanceHandleDTO, userId, isLoanFinish, 0);
      } catch (Exception e) {
         logger.error("保存房抵贷放款信息失败：入参：applyFinanceHandleDTO" + applyFinanceHandleDTO + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         fillReturnJson(response, false, "提交失败,请联系管理员!");
         e.printStackTrace();
         return;
      }
      fillReturnJson(response, true, "提交成功");
   }
   /**
    * 保存放款资金
    *@author:liangyanjun
    *@time:2017年12月13日下午4:31:37
    *@param applyFinanceHandleDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/saveCapitalNameInfo")
   public void saveCapitalNameInfo( Project project, HttpServletRequest request, HttpServletResponse response) {
      int pid = project.getPid();//项目id
      String capitalName = project.getCapitalName();//资方名称
      if (pid<=0||StringUtil.isBlank(capitalName)) {
         fillReturnJson(response, false, "提交失败,数据不合法!");
         return;
      }
      ShiroUser shiroUser = getShiroUser();
      Integer userId = shiroUser.getPid();
      try {
         ProjectService.Client projectService=(ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN,"ProjectService");
         projectService.updateCapitalNameInfo(project,userId);
      } catch (TException e) {
         logger.error("保存放款资金失败：入参：project" + project + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         fillReturnJson(response, false, "提交失败,请联系管理员!");
         e.printStackTrace();
         return;
      }
      fillReturnJson(response, true, "提交成功");
   }
}
