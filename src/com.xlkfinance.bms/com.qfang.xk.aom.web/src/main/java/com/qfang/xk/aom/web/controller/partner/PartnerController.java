package com.qfang.xk.aom.web.controller.partner;

import java.util.List;

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

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.qfang.xk.aom.rpc.org.OrgAssetsCooperationInfo;
import com.qfang.xk.aom.rpc.org.OrgAssetsCooperationService;
import com.qfang.xk.aom.rpc.org.PartnerOrgIndex;
import com.qfang.xk.aom.rpc.partner.OrgPartnerInfo;
import com.qfang.xk.aom.rpc.partner.OrgPartnerInfoService;
import com.qfang.xk.aom.rpc.system.OrgUserFile;
import com.qfang.xk.aom.rpc.system.OrgUserFileService;
import com.qfang.xk.aom.rpc.system.OrgUserInfo;
import com.qfang.xk.aom.rpc.system.OrgUserService;
import com.qfang.xk.aom.rpc.system.SmsValidateCodeInfoService;
import com.qfang.xk.aom.web.controller.BaseController;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.OrgSysLogTypeConstant;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;

/**
 * 
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年7月27日下午2:18:18 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 合伙人管理<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Controller
@RequestMapping("/partnerController")
public class PartnerController extends BaseController {
   private Logger logger = LoggerFactory.getLogger(PartnerController.class);
   private String PATH = "/partner";

   @ExceptionHandler
   public String exception(HttpServletRequest request, HttpServletResponse response, Exception e) {
      logger.error(ExceptionUtil.getExceptionMessage(e), e);
      fillReturnJson(response, false, "出现未知异常,请与系统管理员联系!");
      return null;
   }

   /**
    * 跳转到合伙人注册页面
    *@author:liangyanjun
    *@time:2016年7月27日下午2:18:01
    *@param model
    *@return
    */
   @RequestMapping("/ignore/toPartnerRegister")
   public String toPartnerRegister(ModelMap model) {
      return PATH + "/partner_register";
   }
   
   /**
    * 跳转到客户列表页面
    *@author:liangyanjun
    *@time:2016年7月28日上午11:02:21
    *@param model
    *@return
    */
   @RequestMapping("/toCustomerIndex")
   public String toCustomerIndex(ModelMap model) {
      return PATH + "/customer/customer_index";
   }
   /**
    * 跳转到客户新增或修改页面
    *@author:liangyanjun
    *@time:2016年7月28日下午2:40:39
    *@param model
    *@return
    * @throws TException 
    * @throws ThriftServiceException 
    */
   @RequestMapping("/toCustomerAddOrUpdate")
   public String toCustomerAddOrUpdate(int orgId,ModelMap model) throws ThriftServiceException, TException {
      OrgAssetsCooperationService.Client orgService = (OrgAssetsCooperationService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG,"OrgAssetsCooperationService");
      OrgAssetsCooperationInfo cooperationInfo = orgService.getById(orgId);
      model.put("cooperationInfo", cooperationInfo);
      model.put("orgAuditStatusMap", AomConstant.ORG_AUDIT_STATUS_MAP);//认证状态1:未认证,2表示认证中3、已认证
      model.put("orgCooperateStatusMap", AomConstant.ORG_COOPERATE_STATUS_MAP);//合作状态,1:未合作,2表示已合作,3表示已过期,3合作待确认
      return PATH + "/customer/customer_add_or_update";
   }
   /**
    * 合伙人的客户（机构）列表
    *@author:liangyanjun
    *@time:2016年7月28日下午2:02:55
    *@param query
    *@param response
    *@throws TException
    */
   @RequestMapping("/customerIndexList")
   public void customerIndexList(PartnerOrgIndex query, HttpServletResponse response) throws TException {
      int pid = getLoginUser().getPid();
      // 设置数据权限
      OrgPartnerInfoService.Client partnerClient = (OrgPartnerInfoService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_PARTNER, "OrgPartnerInfoService");
      OrgPartnerInfo orgPartnerInfo = partnerClient.getByUserId(pid);
      query.setPartnerId(orgPartnerInfo.getPid());
      OrgAssetsCooperationService.Client orgService = (OrgAssetsCooperationService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG,"OrgAssetsCooperationService");
      List<PartnerOrgIndex> list = orgService.findPartnerOrgIndex(query);
      int total = orgService.getPartnerOrgIndexTotal(query);
      // 输出
      outputPage(query.getRows(), response, list, total);
   }
   /**
    * 合伙人注册
    *@author:liangyanjun
    *@time:2016年7月27日下午2:17:46
    *@param partnerInfo
    *@param resp
    *@throws TException
    */
   @RequestMapping(value = "/ignore/partnerRegister", method = RequestMethod.POST)
   public void partnerRegister(OrgPartnerInfo partnerInfo, HttpServletResponse resp,HttpServletRequest req) throws TException {
      OrgUserInfo orgUserInfo = partnerInfo.getOrgUserInfo();
      String userName = orgUserInfo.getUserName();
      String realName = orgUserInfo.getRealName();
      String phone = orgUserInfo.getPhone();
      String msgCode = partnerInfo.getMsgCode();
      String email = orgUserInfo.getEmail();
      String password = orgUserInfo.getPassword();
      if (StringUtil.isBlank(userName, realName, phone, msgCode, email, password)) {
         fillReturnJson(resp, false, "参数不合法，请输入必填项");
         return;
      }
      SmsValidateCodeInfoService.Client smsClient = (SmsValidateCodeInfoService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_SYSTEM,
            "SmsValidateCodeInfoService");
      // 检查短信验证码是否正确
      boolean isSucc = smsClient.validCode(phone, msgCode, AomConstant.VALIDATE_CODE_CATEGORY_1);
      if (!isSucc) {
         fillReturnJson(resp, false, "提交失败，请输入正确的验证码");
         return;
      }
      OrgUserService.Client userClient = (OrgUserService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_SYSTEM, "OrgUserService");
      // 检查数据是否存在
      if (userClient.checkUserNameIsExist(userName)) {
         fillReturnJson(resp, false, "该登录名已存在！");
         return;
      }
      if (userClient.checkPhoneIsExist(phone)) {
         fillReturnJson(resp, false, "该手机号码已注册！");
         return;
      }
      if (userClient.checkEmailIsExist(email)) {
         fillReturnJson(resp, false, "该邮箱已注册！");
         return;
      }
      partnerInfo.setStatus(AomConstant.ORG_AUDIT_STATUS_2);
      partnerInfo.setCooperationStatus(AomConstant.ORG_COOPERATE_STATUS_1);
      partnerInfo.setReviewStatus(AomConstant.ORG_PARTNER_COOPERATE_APPLY_STATUS_1);// 申请时
      OrgPartnerInfoService.Client partnerClient = (OrgPartnerInfoService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_PARTNER,
            "OrgPartnerInfoService");
      // 执行添加
      orgUserInfo.setStatus(AomConstant.STATUS_ENABLED);
      partnerInfo.setStatus(AomConstant.STATUS_ENABLED);
      partnerClient.saveOrUpdateOrgPartner(partnerInfo);
      // 验证码设置无效
      smsClient.disabledMsg(phone, msgCode, AomConstant.VALIDATE_CODE_CATEGORY_1);
      recordLog(OrgSysLogTypeConstant.LOG_TYPE_ADD, "合伙人注册：参数："+partnerInfo, req);
      fillReturnJson(resp, true, "提交成功");
   }
   /**
    * 跳转到申请加入页面
    *@author:liangyanjun
    *@time:2016年7月27日下午3:54:26
    *@param model
    *@return
    *@throws ThriftServiceException
    *@throws TException
    * @throws ThriftException 
    */
   @RequestMapping("/toCooperatApply")
   public String toCooperatApply(ModelMap model) throws ThriftServiceException, TException, ThriftException {
      OrgUserInfo loginUser = getLoginUser();
      int userId = loginUser.getPid();
      OrgPartnerInfoService.Client partnerClient = (OrgPartnerInfoService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_PARTNER,
            "OrgPartnerInfoService");
      //查询合伙人信息
      OrgPartnerInfo partnerInfo = partnerClient.getByUserId(userId);
      model.put("partnerInfo", partnerInfo);
      //查询用户附件
      BaseClientFactory OrgUserFileFactory = getFactory(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_SYSTEM, "OrgUserFileService");
      OrgUserFileService.Client OrgUserFileClient = (OrgUserFileService.Client) OrgUserFileFactory.getClient();
      List<OrgUserFile> userFileList = OrgUserFileClient.getByUserId(userId);
      model.put("userFileList", userFileList);
      destroyFactory(OrgUserFileFactory);
      //合作状态
      model.put("orgCooperateStatusMap", AomConstant.ORG_COOPERATE_STATUS_MAP);
      return PATH+"/cooperat/cooperat_apply";
   }
   /**
    * 跳转到合作信息
    *@author:liangyanjun
    *@time:2016年7月14日上午10:11:18
    *@param model
    *@return
    *@throws ThriftServiceException
    *@throws TException
    */
   @RequestMapping("/toCooperatInfo")
   public String toCooperatInfo(ModelMap model) throws ThriftServiceException, TException {
      OrgUserInfo loginUser = getLoginUser();
      int userId = loginUser.getPid();
      OrgPartnerInfoService.Client partnerClient = (OrgPartnerInfoService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_PARTNER,
            "OrgPartnerInfoService");
      OrgPartnerInfo partnerInfo = partnerClient.getByUserId(userId);
      model.put("partnerInfo", partnerInfo);
      //合作状态
      model.put("orgCooperateStatusMap", AomConstant.ORG_COOPERATE_STATUS_MAP);
      return PATH+"/cooperat/cooperat_info";
   }
   /**
    * 提交合作申请
    * 
    *@author:liangyanjun
    *@time:2016年7月27日下午4:32:42
    *@param partnerInfo
    *@param resp
    *@throws TException
    */
   @RequestMapping(value="/subApplyCooperat" , method=RequestMethod.POST)
   public void subApplyCooperat(OrgPartnerInfo partnerInfo,HttpServletResponse resp,HttpServletRequest req) throws TException {
      String cardNo = partnerInfo.getCardNo();
      String payeeAccount = partnerInfo.getPayeeAccount();
      String remark = partnerInfo.getRemark();
      if (StringUtil.isBlank(cardNo,payeeAccount)) {
         fillReturnJson(resp, false, "参数不合法，请输入必填项");
         return;
      }
      OrgUserInfo loginUser = getLoginUser();
      int userId = loginUser.getPid();
      OrgPartnerInfoService.Client partnerClient = (OrgPartnerInfoService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_PARTNER,
            "OrgPartnerInfoService");
      OrgPartnerInfo updatePartnerInfo = partnerClient.getByUserId(userId);
      //检查是否已认证
      if (updatePartnerInfo.getStatus()!=AomConstant.ORG_AUDIT_STATUS_3) {
         fillReturnJson(resp, false, "账户未认证，不能提交申请!");
         return;
      }
      //检查合作申请状态，1、未申请,2、已提交,10、审核中20、审核通过30、审核不通过
      int reviewStatus = updatePartnerInfo.getReviewStatus();
      if (reviewStatus!=AomConstant.ORG_PARTNER_COOPERATE_APPLY_STATUS_1&&reviewStatus!=AomConstant.ORG_PARTNER_COOPERATE_APPLY_STATUS_30) {
         fillReturnJson(resp, false, "你已经申请合作,请勿重复提交");
         return;
      }
      if (reviewStatus==AomConstant.ORG_PARTNER_COOPERATE_APPLY_STATUS_30) {
         fillReturnJson(resp, false, "对不起，你提交的申请合作后台审核结果为不通过,请勿重复提交");
         return;
      }
      //检查合作状态，待确认或者已合作不能申请
      int cooperationStatus = updatePartnerInfo.getCooperationStatus();
      if (cooperationStatus==AomConstant.ORG_COOPERATE_STATUS_2||cooperationStatus==AomConstant.ORG_COOPERATE_STATUS_4) {
         fillReturnJson(resp, false, "你已经申请合作,请勿重复提交");
         return;
      }
      //执行更新
      updatePartnerInfo.setPayeeAccount(payeeAccount);//收款账号
      updatePartnerInfo.setCardNo(cardNo);//身份证
      updatePartnerInfo.setRemark(remark);//合作说明
      updatePartnerInfo.setApplyTime(DateUtils.getCurrentDateTime());//申请时间
      updatePartnerInfo.setReviewStatus(AomConstant.ORG_PARTNER_COOPERATE_APPLY_STATUS_10);//合伙人合作申请状态 1、未申请,10、审核中20、审核通过30、审核不通过
      partnerClient.update(updatePartnerInfo);
      recordLog(OrgSysLogTypeConstant.LOG_TYPE_UPDATE, "提交合作申请：参数："+updatePartnerInfo, req);
      fillReturnJson(resp, true, "提交成功");
   }
   /**
    * 合作人确认展开合作
    *@author:liangyanjun
    *@time:2016年7月28日上午9:23:46
    *@param resp
    *@throws TException
    */
   @RequestMapping(value="/confirmCooperat" , method=RequestMethod.POST)
   public void confirmCooperat(HttpServletRequest req,HttpServletResponse resp) throws TException {
      OrgUserInfo loginUser = getLoginUser();
      int userId = loginUser.getPid();
      OrgPartnerInfoService.Client partnerClient = (OrgPartnerInfoService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_PARTNER,
            "OrgPartnerInfoService");
      OrgPartnerInfo updatePartnerInfo = partnerClient.getByUserId(userId);
      //检查是否已认证
      if (updatePartnerInfo.getStatus()!=AomConstant.ORG_AUDIT_STATUS_3) {
         fillReturnJson(resp, false, "信息未认证，不能点击确认!");
         return;
      }
      //检查合作状态，只有待确认状态才能点击
      int cooperationStatus = updatePartnerInfo.getCooperationStatus();
      if (cooperationStatus==AomConstant.ORG_COOPERATE_STATUS_2) {
         fillReturnJson(resp, false, "已经在合作中了");
         return;
      }
      if (cooperationStatus!=AomConstant.ORG_COOPERATE_STATUS_4) {
         fillReturnJson(resp, false, "后台审核未通过，不能点击合作确认");
         return;
      }
      //执行更新
      updatePartnerInfo.setCooperationStatus(AomConstant.ORG_COOPERATE_STATUS_2);
      partnerClient.updateCooperateStatus(updatePartnerInfo);
      recordLog(OrgSysLogTypeConstant.LOG_TYPE_UPDATE, "合作人确认展开合作：参数："+updatePartnerInfo, req);
      fillReturnJson(resp, true, "提交成功，即将自动退出重新登录");
   }
}
