package com.qfang.xk.aom.web.controller.finance;

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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qfang.xk.aom.rpc.fee.OrgFeeSettleDetailPage;
import com.qfang.xk.aom.rpc.fee.OrgFeeSettleDetailService;
import com.qfang.xk.aom.rpc.fee.OrgFeeSettlePage;
import com.qfang.xk.aom.rpc.fee.OrgFeeSettleService;
import com.qfang.xk.aom.rpc.fee.PartnerFeeSettleDetailPage;
import com.qfang.xk.aom.rpc.fee.PartnerFeeSettlePage;
import com.qfang.xk.aom.rpc.finance.OrgFinanceService;
import com.qfang.xk.aom.rpc.finance.OrgMakeLoansIndex;
import com.qfang.xk.aom.rpc.finance.OrgRepaymentIndex;
import com.qfang.xk.aom.rpc.partner.OrgPartnerInfo;
import com.qfang.xk.aom.rpc.partner.OrgPartnerInfoService;
import com.qfang.xk.aom.web.controller.BaseController;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.inloan.RepaymentRecordDTO;
import com.xlkfinance.bms.rpc.inloan.RepaymentService;

/**
 * 
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年7月19日上午11:05:27 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 财务相关：放款，回款，息费等<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Controller
@RequestMapping("/financeController")
public class FinanceController extends BaseController {
   private Logger logger = LoggerFactory.getLogger(FinanceController.class);
   private String PATH = "/org";

   @ExceptionHandler
   public String exception(HttpServletRequest request, HttpServletResponse response, Exception e) {
      logger.error(ExceptionUtil.getExceptionMessage(e), e);
      fillReturnJson(response, false, "出现未知异常,请与系统管理员联系!");
      return null;
   }

   /**
    * 跳转到机构放款页面
    *@author:liangyanjun
    *@time:2016年7月19日下午3:21:20
    *@param model
    *@return
    *@throws ThriftServiceException
    *@throws TException
    */
   @RequestMapping("/toOrgMakeLoansIndex")
   public String toOrgMakeLoansIndex(ModelMap model) throws ThriftServiceException, TException {
      return PATH + "/finance/org_make_loans_index";
   }
   
   /**
    * 跳转到机构回款页面
    *@author:liangyanjun
    *@time:2016年7月20日上午11:33:03
    *@param model
    *@return
    *@throws ThriftServiceException
    *@throws TException
    */
   @RequestMapping("/toOrgRepaymentIndex")
   public String toOrgRepaymentIndex(ModelMap model) throws ThriftServiceException, TException {
      return PATH + "/finance/org_repayment_index";
   }
   /**
    * 跳转到机构费用结算页面
    *@author:liangyanjun
    *@time:2016年7月25日上午10:50:11
    *@param model
    *@return
    *@throws ThriftServiceException
    *@throws TException
    */
   @RequestMapping("/toOrgFeeSettleIndex")
   public String toOrgFeeSettleIndex(ModelMap model) throws ThriftServiceException, TException {
      return PATH + "/finance/org_fee_settle_index";
   }
   /**
    * 跳转到合伙人费用结算页面
    *@author:liangyanjun
    *@time:2016年8月1日上午10:37:11
    *@param model
    *@return
    *@throws ThriftServiceException
    *@throws TException
    */
   @RequestMapping("/toPartnerFeeSettleIndex")
   public String toPartnerFeeSettleIndex(ModelMap model) throws ThriftServiceException, TException {
      return "/partner/finance/partner_fee_settle_index";
   }
   /**
    * 跳转到机构费用详情结算页面
    *@author:liangyanjun
    *@time:2016年9月5日下午4:52:06
    *@param model
    *@return
    *@throws ThriftServiceException
    *@throws TException
    */
   @RequestMapping("/toOrgFeeSettleDetailIndex")
   public String toOrgFeeSettleDetailIndex(int settleId,ModelMap model) throws ThriftServiceException, TException {
       model.put("settleId", settleId);
      return "/org/finance/org_fee_settle_detail_index";
   }
   /**
    * 跳转到合伙人费用结算明细页面
    *@author:liangyanjun
    *@time:2016年8月1日上午11:47:19
    *@param model
    *@return
    *@throws ThriftServiceException
    *@throws TException
    */
   @RequestMapping("/toPartnerFeeSettleDetailIndex")
   public String toPartnerFeeSettleDetailIndex(ModelMap model,int settleId) throws ThriftServiceException, TException {
      model.put("settleId", settleId);
      return "/partner/finance/partner_fee_settle_detail_index";
   }
   /**
    * 根据条件查询机构的放款列表
    *@author:liangyanjun
    *@time:2016年7月20日上午11:32:12
    *@param query
    *@param response
    *@throws TException
    */
   @RequestMapping("/orgMakeLoansList")
   public void orgMakeLoansList(OrgMakeLoansIndex query, HttpServletResponse response) throws TException {
      query.setApplyUserId(getDataScope());
      query.setOrgId(getLoginUser().getOrgId());
      OrgFinanceService.Client financeClient = (OrgFinanceService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_FINANCE, "OrgFinanceService");
      List<OrgMakeLoansIndex> list = financeClient.queryOrgMakeLoansIndex(query);
      int total = financeClient.getOrgMakeLoansIndexTotal(query);
      outputPage(query.getRows(), response, list, total);
   }

  
   /**
    * 根据条件查询机构的回款列表
    *@author:liangyanjun
    *@time:2016年7月20日上午11:32:01
    *@param query
    *@param response
    *@throws TException
    */
   @RequestMapping("/orgRepaymentList")
   public void orgRepaymentList(OrgRepaymentIndex query, HttpServletResponse response) throws TException {
      query.setApplyUserId(getDataScope());
      query.setOrgId(getLoginUser().getOrgId());
      OrgFinanceService.Client financeClient = (OrgFinanceService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_FINANCE, "OrgFinanceService");
      List<OrgRepaymentIndex> list = financeClient.queryOrgRepaymentIndex(query);
      int total = financeClient.getOrgRepaymentIndexTotal(query);
      outputPage(query.getRows(), response, list, total);
   }
   /**
    * 查看回款明细
    *@author:liangyanjun
    *@time:2016年8月11日上午10:54:24
    *@param projectId
    *@param request
    *@param response
    * @throws TException 
    */
   @RequestMapping(value = "/getRepaymentInfoHis", method = RequestMethod.POST)
   public void getRepaymentInfoHis(int projectId, HttpServletRequest request, HttpServletResponse response) throws TException {
      RepaymentService.Client client = (RepaymentService.Client) getService(AomConstant.BMS_SYSTEM, BusinessModule.MODUEL_INLOAN, "RepaymentService");
      int orgId = getLoginUser().getOrgId();
      Map<String, Object> returnMap;
      returnMap = new HashMap<String, Object>();
      if (projectId>0&&orgId>0) {
         RepaymentRecordDTO repaymentRecordQuery = new RepaymentRecordDTO();
         repaymentRecordQuery.setProjectId(projectId);
         repaymentRecordQuery.setOrgId(orgId);
         repaymentRecordQuery.setPage(-1);
         // 查询回款记录
         List<RepaymentRecordDTO> repaymentRecordList = client.queryRepaymentRecord(repaymentRecordQuery);
         returnMap.put("repaymentRecordList", repaymentRecordList);
      }
      outputJson(returnMap, response);
   }
   
   /**
    * 根据条件查询机构的费用结算列表（分页查询）
    *@author:liangyanjun
    *@time:2016年7月25日上午10:49:26
    *@param query
    *@param response
    *@throws TException
    */
   @RequestMapping("/orgFeeSettleList")
   public void orgFeeSettleList(OrgFeeSettlePage query, HttpServletResponse response) throws TException {
      OrgFeeSettleService.Client client = (OrgFeeSettleService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_FEE, "OrgFeeSettleService");
      query.setOrgId(getLoginUser().getOrgId());
      List<OrgFeeSettlePage> list = client.queryOrgFeeSettlePage(query);
      int total = client.getOrgFeeSettlePageTotal(query);
      outputPage(query.getRows(), response, list, total);
   }
   /**
    * 根据条件查询合伙人的费用结算列表（分页查询）
    *@author:liangyanjun
    *@time:2016年8月1日上午10:38:42
    *@param query
    *@param response
    *@throws TException
    */
   @RequestMapping("/partnerFeeSettleList")
   public void partnerFeeSettleList(PartnerFeeSettlePage query, HttpServletResponse response) throws TException {
      OrgFeeSettleService.Client client = (OrgFeeSettleService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_FEE, "OrgFeeSettleService");
      OrgPartnerInfoService.Client partnerClient = (OrgPartnerInfoService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_PARTNER, "OrgPartnerInfoService");
      
      OrgPartnerInfo orgPartnerInfo = partnerClient.getByUserId(getLoginUser().getPid());
      query.setPartnerId(orgPartnerInfo.getPid());
      List<PartnerFeeSettlePage> list = client.queryPartnerFeeSettlePage(query);
      int total = client.getPartnerFeeSettlePageTotal(query);
      outputPage(query.getRows(), response, list, total);
   }
   /**
    * 根据条件查询合伙人的费用结算明细列表（分页查询）
    *@author:liangyanjun
    *@time:2016年8月1日上午11:46:34
    *@param query
    *@param response
    *@throws TException
    */
   @RequestMapping("/partnerFeeSettleDetailList")
   public void partnerFeeSettleDetailList(PartnerFeeSettleDetailPage query, HttpServletResponse response) throws TException {
      OrgFeeSettleDetailService.Client client = (OrgFeeSettleDetailService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_FEE, "OrgFeeSettleDetailService");
      OrgPartnerInfoService.Client partnerClient = (OrgPartnerInfoService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_PARTNER, "OrgPartnerInfoService");
      OrgPartnerInfo orgPartnerInfo = partnerClient.getByUserId(getLoginUser().getPid());
      query.setPartnerId(orgPartnerInfo.getPid());
      
      List<PartnerFeeSettleDetailPage> list = client.queryPartnerFeeSettleDetailPage(query);
      int total = client.getPartnerFeeSettleDetailPageTotal(query);
      outputPage(query.getRows(), response, list, total);
   }
   /**
    * 机构端费用结算明细列表(分页查询)
    *@author:liangyanjun
    *@time:2016年9月5日下午4:41:16
    *@param query
    *@param response
    *@throws TException
    */
   @RequestMapping("/orgFeeSettleDetailList")
   public void orgFeeSettleDetailList(OrgFeeSettleDetailPage query, HttpServletResponse response) throws TException {
      int orgId = getLoginUser().getOrgId();
      OrgFeeSettleDetailService.Client client = (OrgFeeSettleDetailService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_FEE, "OrgFeeSettleDetailService");
      query.setOrgId(orgId);
      List<OrgFeeSettleDetailPage> list = client.queryOrgFeeSettleDetailPage(query);
      int total = client.getOrgFeeSettleDetailPageTotal(query);
      outputPage(query.getRows(), response, list, total);
   }
}
