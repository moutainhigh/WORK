package com.xlkfinance.bms.web.controller.aom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.thrift.TException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.qfang.xk.aom.rpc.finance.OrgAssureFundRecord;
import com.qfang.xk.aom.rpc.finance.OrgAssureFundRecordService;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyInf;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyService;
import com.qfang.xk.aom.rpc.org.OrgCooperationUpdateApply;
import com.qfang.xk.aom.rpc.org.OrgCooperationUpdateApplyService;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.shiro.ShiroUser;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年9月5日上午9:18:11 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 保证金流水记录<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Controller
@RequestMapping("/orgAssureFundRecordController")
public class OrgAssureFundRecordController extends BaseController {
   private static final String PAGE_PATH = "aom/finance/";
   /**
    * 首页跳转
    */
   @RequestMapping(value = "/index")
   public String toIntegratedDeptIndex(ModelMap model) {
      StringBuffer url = new StringBuffer(PAGE_PATH);
      url.append("assure_fund_record_index");
      return url.toString();
   }

   /**
    * 保证金流水记录列表（分页查询）
    *@author:liangyanjun
    *@time:2016年3月12日下午5:00:20
    *@param checkDocumentIndexDTO
    *@param request
    *@param response
    * @throws TException 
    * @throws ThriftException 
    */
   @RequestMapping(value = "/list")
   public void list(OrgAssureFundRecord query, HttpServletRequest request, HttpServletResponse response) throws TException, ThriftException {
      Map<String, Object> map = new HashMap<String, Object>();
      BaseClientFactory factory = getAomFactory(BusinessModule.MODUEL_ORG_FINANCE, "OrgAssureFundRecordService");
      OrgAssureFundRecordService.Client client = (OrgAssureFundRecordService.Client) factory.getClient();
      List<OrgAssureFundRecord> list = client.queryOrgAssureFundRecordPage(query);
      int total = client.getOrgAssureFundRecordTotal(query);
      // 输出
      map.put("rows", list);
      map.put("total", total);
      outputJson(map, response);
      destroyFactory(factory);
   }

   /**
    * 添加保证金流水记录
    * @param assureFundRecord
    * @param request
    * @param response
    * @throws TException
    * @throws ThriftException
    */
   @RequiresPermissions("orgAssureFundRecordController/add")
   @RequestMapping(value = "/add")
   public void add(OrgAssureFundRecord assureFundRecord, HttpServletRequest request, HttpServletResponse response) throws TException, ThriftException {
      double money = assureFundRecord.getMoney();//金额
      String account = assureFundRecord.getAccount();//账号
      String accountName = assureFundRecord.getAccountName();//账户名称
      String recDate = assureFundRecord.getRecDate();//到账日期
      //String bank = assureFundRecord.getBank();//银行
      int type = assureFundRecord.getType();//类型：收保证金=1，退保证金=2
      int orgId = assureFundRecord.getOrgId();//机构ID
      if (StringUtil.isBlank(account, accountName,recDate) ||type <= 0 || orgId <= 0) {
         fillReturnJson(response, false, "数据不合法,请重新操作!");
         return;
      }
      if (money <= 0) {
          fillReturnJson(response, false, "金额要大于零,请重新操作!");
          return;
      }
      ShiroUser shiroUser = getShiroUser();
      Integer userId = shiroUser.getPid();
      
      BaseClientFactory orgCooperationUpdateApplyFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgCooperationUpdateApplyService");
      OrgCooperationUpdateApplyService.Client orgCooperationUpdateApplyService = (OrgCooperationUpdateApplyService.Client) orgCooperationUpdateApplyFactory.getClient();
      //根据机构id查询最后一次申请的机构合作信息修改申请信息
      OrgCooperationUpdateApply cooperationUpdateApply = orgCooperationUpdateApplyService.getLastByOrgId(orgId);
      int applyStatus = cooperationUpdateApply.getApplyStatus();
      //如果机构合作信息修改申请状态为：申请中、驳回、审核中，则不能添加保证金
      if (applyStatus==Constants.APPLY_STATUS_2||applyStatus==Constants.APPLY_STATUS_3||applyStatus==Constants.APPLY_STATUS_4) {
          fillReturnJson(response, false, "机构合作信息修改申请状态为：申请中、驳回、审核中，则不能添加保证金！");
          return;
      }
      BaseClientFactory companyApplyFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgCooperatCompanyApplyService");
      OrgCooperatCompanyApplyService.Client companyApplyFactoryService = (OrgCooperatCompanyApplyService.Client) companyApplyFactory.getClient();
      OrgCooperatCompanyApplyInf companyApplyInf = companyApplyFactoryService.getByOrgId(orgId);
      double assureMoney = companyApplyInf.getAssureMoney();//预计收取保证金
      double realAssureMoney = companyApplyInf.getRealAssureMoney();//已收保证金额
      //判断累计实收保证金金额是否已超过预计收取保证金
      if (type==1&&(realAssureMoney+money)>assureMoney) {
          fillReturnJson(response, false, "累计实收保证金金额已超过预计收取保证金,预计收取保证金为："+assureMoney+"!");
          return;
      }
      //执行添加保证金记录
      BaseClientFactory factory = getAomFactory(BusinessModule.MODUEL_ORG_FINANCE, "OrgAssureFundRecordService");
      OrgAssureFundRecordService.Client client = (OrgAssureFundRecordService.Client) factory.getClient();
      assureFundRecord.setCreaterId(userId);
      assureFundRecord.setUpdateId(userId);
      client.insert(assureFundRecord);
      destroyFactory(factory);
      fillReturnJson(response, true, "提交成功");
   }

}
