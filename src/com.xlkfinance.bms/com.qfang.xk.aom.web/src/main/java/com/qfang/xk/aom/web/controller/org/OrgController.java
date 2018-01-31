package com.qfang.xk.aom.web.controller.org;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
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

import com.qfang.xk.aom.rpc.org.OrgAssetsCooperationInfo;
import com.qfang.xk.aom.rpc.org.OrgAssetsCooperationService;
import com.qfang.xk.aom.rpc.org.OrgAssureFundFlowInfo;
import com.qfang.xk.aom.rpc.org.OrgAssureFundFlowInfoService;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyInf;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyService;
import com.qfang.xk.aom.rpc.org.OrgCooperationContract;
import com.qfang.xk.aom.rpc.org.OrgCooperationContractService;
import com.qfang.xk.aom.rpc.partner.OrgPartnerInfo;
import com.qfang.xk.aom.rpc.partner.OrgPartnerInfoService;
import com.qfang.xk.aom.rpc.system.OrgUserInfo;
import com.qfang.xk.aom.rpc.system.OrgUserService;
import com.qfang.xk.aom.rpc.system.SmsValidateCodeInfoService;
import com.qfang.xk.aom.web.controller.BaseController;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.OrgSysLogTypeConstant;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.rpc.system.BizFileService;
/**
 * 
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年7月11日下午2:06:20 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 机构管理<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Controller
@RequestMapping("/orgController")
public class OrgController extends BaseController {
   private Logger logger = LoggerFactory.getLogger(OrgController.class);
   private String PATH = "/org";

   @ExceptionHandler
   public String exception(HttpServletRequest request, HttpServletResponse response, Exception e) {
      logger.error(ExceptionUtil.getExceptionMessage(e), e);
      fillReturnJson(response, false, "出现未知异常,请与系统管理员联系!");
      return null;
   }

   /**
    * 跳转到机构注册页面
    *@author:liangyanjun
    *@time:2016年7月8日上午11:02:40
    *@param model
    *@return
    */
   @RequestMapping("/ignore/toOrgRegister")
   public String toOrgRegister(ModelMap model) {
      return "/org/org_register";
   }
   /**
    * 跳转到申请加入页面
    *@author:liangyanjun
    *@time:2016年7月13日上午10:58:01
    *@param model
    *@return
    * @throws TException 
    * @throws ThriftServiceException 
    */
   @RequestMapping("/toCooperatApply")
   public String toCooperatApply(ModelMap model) throws ThriftServiceException, TException {
      OrgUserInfo loginUser = getLoginUser();
      int orgId = loginUser.getOrgId();
      OrgAssetsCooperationService.Client orgClient = (OrgAssetsCooperationService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG, "OrgAssetsCooperationService");
      OrgAssetsCooperationInfo cooperationInfo = orgClient.getById(orgId);
      model.put("cooperationInfo", cooperationInfo);
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
      int orgId = loginUser.getOrgId();
      //查询机构信息
      OrgAssetsCooperationService.Client orgClient = (OrgAssetsCooperationService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG, "OrgAssetsCooperationService");
      OrgAssetsCooperationInfo cooperationInfo = orgClient.getById(orgId);
      model.put("cooperationInfo", cooperationInfo);
      //查询机构合作申请信息
      OrgCooperatCompanyApplyService.Client orgCooperatClient = (OrgCooperatCompanyApplyService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG,"OrgCooperatCompanyApplyService");
      OrgCooperatCompanyApplyInf applyInf = orgCooperatClient.getByUserId(orgId);
      model.put("applyInf", applyInf);
      if (applyInf.getPid()>0) {
         //查询机构合作合同
         OrgCooperationContractService.Client orgContractClient = (OrgCooperationContractService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG,
               "OrgCooperationContractService");
         OrgCooperationContract orgContractQuery = new OrgCooperationContract();
         orgContractQuery.setCooperationId(applyInf.getPid());
         List<OrgCooperationContract> orgContractList = orgContractClient.getAll(orgContractQuery);
         model.put("orgContractList", orgContractList);
      }
      model.put("fundFlowApplyStatusMap", AomConstant.FUND_FLOW_APPLY_STATUS_MAP);
      model.put("orgCooperateStatusMap", AomConstant.ORG_COOPERATE_STATUS_MAP);//合作状态
      return PATH+"/cooperat/cooperat_info";
   }

   /**
    * 机构注册
    *@author:liangyanjun
    *@time:2016年8月2日下午2:29:34
    *@param org
    *@param req
    *@param resp
    *@throws TException
    */
   @RequestMapping(value = "/ignore/orgRegister", method = RequestMethod.POST)
   public void orgRegister(OrgAssetsCooperationInfo org, HttpServletRequest req,HttpServletResponse resp) throws TException {
      String orgName = org.getOrgName();//机构名称
      String code = org.getCode();//机构代码
      String email = org.getEmail();//公司邮箱
      OrgUserInfo orgUserInfo = org.getOrgUserInfo();
      String userName = orgUserInfo.getUserName();//用户名
      String realName = orgUserInfo.getRealName();//姓名
      String phone = orgUserInfo.getPhone();//手机号码
      String msgCode = org.getMsgCode();//短信验证码
      String password = orgUserInfo.getPassword();//登录密码
      if (StringUtil.isBlank(userName, realName, phone, msgCode, orgName, code, email, password)) {
         fillReturnJson(resp, false, "参数不合法，请输入必填项");
         return;
      }
      SmsValidateCodeInfoService.Client smsClient = (SmsValidateCodeInfoService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_SYSTEM,
            "SmsValidateCodeInfoService");
      //检查短信验证码是否正确
      boolean isSucc = smsClient.validCode(phone, msgCode, AomConstant.VALIDATE_CODE_CATEGORY_1);
      if (!isSucc) {
         fillReturnJson(resp, false, "提交失败，请输入正确的验证码");
         return;
      }
      OrgUserService.Client userClient = (OrgUserService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_SYSTEM, "OrgUserService");
      OrgAssetsCooperationService.Client orgClient = (OrgAssetsCooperationService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG, "OrgAssetsCooperationService");
      //检查数据是否存在
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
      if (orgClient.checkOrgCodeIsExist(code)) {
         fillReturnJson(resp, false, "该机构代码已注册！");
         return;
      }
      orgUserInfo.setStatus(AomConstant.STATUS_ENABLED);
      orgUserInfo.setUserType(AomConstant.USER_TYPE_1);
      orgUserInfo.setRole(AomConstant.ORG_ROLE_1);
      orgUserInfo.setDateScope(AomConstant.DATE_SCOPE_2);
      org.setAuditStatus(AomConstant.ORG_AUDIT_STATUS_1);
      org.setCooperateStatus(AomConstant.ORG_COOPERATE_APPLY_STATUS_1);
      org.setPhone(phone);
      org.setEmail(email);
      org.setContact(realName);
      org.setPhone(phone);
      //执行添加
      orgClient.insert(org);
      // 验证码设置无效
      smsClient.disabledMsg(phone, code, AomConstant.VALIDATE_CODE_CATEGORY_1);
      recordLog(OrgSysLogTypeConstant.LOG_TYPE_ADD, "机构注册:参数："+org, req);
      fillReturnJson(resp, true, "提交成功");
   }
   /**
    * 添加机构
    * 如果是合伙人添加的机构，则设置机构的合伙人id
    *@author:liangyanjun
    *@time:2016年7月28日下午3:45:12
    *@param org
    *@param resp
    *@throws TException
    */
   @RequestMapping(value = "/addOrg", method = RequestMethod.POST)
   public void addOrg(OrgAssetsCooperationInfo org, HttpServletRequest req,HttpServletResponse resp) throws TException {
      String orgName = org.getOrgName();//机构名称
      String code = org.getCode();//机构代码
      String email = org.getEmail();//公司邮箱
      String contactPhone = org.getPhone();//联系电话
      String contact = org.getContact();//联系人
      OrgUserInfo orgUserInfo = org.getOrgUserInfo();
      String userName = orgUserInfo.getUserName();//用户名
      String phone = orgUserInfo.getPhone();//手机号码
      String password = orgUserInfo.getPassword();//登录密码
      if (StringUtil.isBlank(userName, phone, orgName, code, email, password)) {
         fillReturnJson(resp, false, "参数不合法，请输入必填项");
         return;
      }
      OrgUserInfo loginUser = getLoginUser();
      int userId = loginUser.getPid();
      OrgUserService.Client userClient = (OrgUserService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_SYSTEM, "OrgUserService");
      OrgAssetsCooperationService.Client orgClient = (OrgAssetsCooperationService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG, "OrgAssetsCooperationService");
      //检查数据是否存在
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
      if (orgClient.checkOrgCodeIsExist(code)) {
         fillReturnJson(resp, false, "该机构代码已注册！");
         return;
      }
      orgUserInfo.setCreateId(userId);
      orgUserInfo.setUpdateId(userId);
      orgUserInfo.setStatus(AomConstant.STATUS_ENABLED);
      orgUserInfo.setUserType(AomConstant.USER_TYPE_1);
      orgUserInfo.setRole(AomConstant.ORG_ROLE_1);
      orgUserInfo.setDateScope(AomConstant.DATE_SCOPE_2);
      org.setAuditStatus(AomConstant.ORG_AUDIT_STATUS_1);
      org.setCooperateStatus(AomConstant.ORG_COOPERATE_APPLY_STATUS_1);
      org.setPhone(contactPhone);
      org.setEmail(email);
      org.setContact(contact);
      org.setCreatorId(userId);
      org.setUpdateId(userId);
      //如果是合伙人添加的机构，则设置机构的合伙人id
      if (loginUser.getUserType()==AomConstant.USER_TYPE_2) {
         OrgPartnerInfoService.Client partnerClient = (OrgPartnerInfoService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_PARTNER, "OrgPartnerInfoService");
         OrgPartnerInfo orgPartnerInfo = partnerClient.getByUserId(userId);
         org.setPartnerId(orgPartnerInfo.getPid());
      }
      //执行添加
      orgClient.insert(org);
      recordLog(OrgSysLogTypeConstant.LOG_TYPE_ADD, "添加机构:参数："+org, req);
      fillReturnJson(resp, true, "提交成功");
   }
   /**
    * 更新机构信息
    *@author:liangyanjun
    *@time:2016年7月28日下午4:13:17
    *@param org
    *@param resp
    *@throws TException
    */
   @RequestMapping(value = "/updateOrg", method = RequestMethod.POST)
   public void updateOrg(OrgAssetsCooperationInfo org, HttpServletRequest req,HttpServletResponse resp) throws TException {
      int pid = org.getPid();//机构id
      String contactPhone = org.getPhone();//联系电话
      String contact = org.getContact();//联系人
      int userId = getLoginUser().getPid();
      OrgAssetsCooperationService.Client orgClient = (OrgAssetsCooperationService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG, "OrgAssetsCooperationService");
      OrgAssetsCooperationInfo updateOrg = orgClient.getById(pid);
      if (updateOrg.getAuditStatus()==AomConstant.ORG_AUDIT_STATUS_3) {
         fillReturnJson(resp, false, "机构已认证，不可做修改");
         return;
      }
      if (updateOrg.getCreatorId()!=userId) {
         fillReturnJson(resp, false, "对不起！你无权限修改该数据");
         return;
      }
      updateOrg.setUpdateId(userId);
      updateOrg.setPhone(contactPhone);
      updateOrg.setContact(contact);
      //执行更新
      orgClient.update(updateOrg);
      recordLog(OrgSysLogTypeConstant.LOG_TYPE_ADD, "更新机构信息:参数："+updateOrg, req);
      fillReturnJson(resp, true, "提交成功");
   }
   /**
    * 检查机构代码是否已存在
    * 存在返回true，不存在返回false
    *@author:liangyanjun
    *@time:2016年7月8日下午3:30:03
    *@param orgCode
    *@param resp
    * @param pid 
    * @throws TException 
    */
   @RequestMapping(value="/ignore/checkOrgCodeIsExist" , method=RequestMethod.POST)
   public void checkOrgCodeIsExist(String orgCode, int pid, HttpServletResponse resp) throws TException{
      if (StringUtil.isBlank(orgCode)) {
         fillReturnJson(resp, false, "参数不合法，请输入必填项");
         return;
      }
      boolean flag = false;
      OrgAssetsCooperationService.Client orgService = (OrgAssetsCooperationService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG, "OrgAssetsCooperationService");
      OrgAssetsCooperationInfo org = orgService.getOrgByCode(orgCode);
      if (org.getPid() > 0) {
         flag = true;
         if (pid > 0 && org.getPid() == pid) {
            flag=false;
         }
      }
      // 输出
      fillReturnJson(resp, flag, "查询成功");
   }
   /**
    * 提交合作申请
    *@author:liangyanjun
    *@time:2016年7月13日下午4:09:48
    *@param resp
    *@throws TException
    */
   @RequestMapping(value="/subApplyCooperat" , method=RequestMethod.POST)
   public void subApplyCooperat(HttpServletRequest req,HttpServletResponse resp) throws TException {
      OrgUserInfo loginUser = getLoginUser();
      int pid = loginUser.getPid();
      int orgId = loginUser.getOrgId();
      OrgAssetsCooperationService.Client orgService = (OrgAssetsCooperationService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG,"OrgAssetsCooperationService");
      OrgAssetsCooperationInfo org = orgService.getById(orgId);
      int auditStatus = org.getAuditStatus();// 认证状态1:未认证,2表示认证中3、已认证
      if (AomConstant.ORG_AUDIT_STATUS_3 != auditStatus) {
         fillReturnJson(resp, false, "机构信息未认证，不能提交申请!");
         return;
      }
      OrgCooperatCompanyApplyService.Client orgCooperatClient = (OrgCooperatCompanyApplyService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG,"OrgCooperatCompanyApplyService");
      OrgCooperatCompanyApplyInf applyInf = orgCooperatClient.getByUserId(orgId);
      if (applyInf.getPid() == 0) {
         // 插入数据
         OrgCooperatCompanyApplyInf newApplyInf = new OrgCooperatCompanyApplyInf();
         newApplyInf.setUserId(pid);
         newApplyInf.setOrgId(orgId);
         newApplyInf.setStatus(AomConstant.FUND_FLOW_APPLY_STATUS_1);
         newApplyInf.setApplyStatus(AomConstant.ORG_COOPERATE_APPLY_STATUS_2);
         newApplyInf.setCreatorId(pid);
         newApplyInf.setUpdateId(pid);
         orgCooperatClient.saveOrgCooperateApplyInfo(newApplyInf);
      } else {
         int applyStatus = applyInf.getApplyStatus();
         if (applyStatus!=AomConstant.ORG_COOPERATE_APPLY_STATUS_1) {
            fillReturnJson(resp, false, "申请审核中,请勿重复提交");
            return;
         }
         //检查合作状态，1:未合作,2表示已合作,3表示已过期,4合作待确认
         int cooperateStatus = org.getCooperateStatus();
         if (cooperateStatus==AomConstant.ORG_COOPERATE_STATUS_2||cooperateStatus==AomConstant.ORG_COOPERATE_STATUS_4) {
            fillReturnJson(resp, false, "你已经申请合作,请勿重复提交");
            return;
         }
         // 更新数据
         applyInf.setApplyStatus(AomConstant.ORG_COOPERATE_APPLY_STATUS_2);
         applyInf.setUpdateId(pid);
         orgCooperatClient.update(applyInf);
      }
      recordLog(OrgSysLogTypeConstant.LOG_TYPE_ADD, "提交机构合作申请", req);
      fillReturnJson(resp, true, "提交成功");
   }
   /**
    * 上传机构合作合同
    *@author:liangyanjun
    *@time:2016年7月15日下午3:56:38
    *@param contractType
    *@param req
    *@param resp
    *@throws ServletException
    *@throws IOException
    * @throws TException 
    * @throws ThriftServiceException 
    */
   @RequestMapping(value = "/uploadOrgCooperationContract")
   public void uploadOrgCooperationContract(int contractType, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,ThriftServiceException, TException {
      if(true){
         fillReturnJson(resp, false, "上传失败：该功能已屏蔽");
         return;
      }
      if (contractType<=0) {
         fillReturnJson(resp, false, "参数不合法，请输入必填项");
         return;
      }
      OrgUserInfo loginUser = getLoginUser();
      //获取上传文件并保存本地
      Map<String, Object> fileMap = getFileMap(req, resp, "", BusinessModule.MODUEL_ORG);
      boolean uploadIsSucc = (boolean) fileMap.get("flag");
      if (!uploadIsSucc) {
         fillReturnJson(resp, false, "上传失败："+fileMap.get("errorMsg"));
         return;
      }
      BizFile contractFile = (BizFile) fileMap.get("bizFile");
      //把文件数据保存数据库
      BizFileService.Client fileService = (BizFileService.Client) getService(AomConstant.BMS_SYSTEM, BusinessModule.MODUEL_SYSTEM, "BizFileService");
      int bizFileId = fileService.saveBizFile(contractFile);
      //关联到机构合作合同
      OrgCooperationContractService.Client orgContractClient = (OrgCooperationContractService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG,"OrgCooperationContractService");
      OrgCooperationContract upCooperationContract = orgContractClient.getByOrgIdAndType(loginUser.getOrgId(), contractType);
      if (upCooperationContract.getPid()==0) {
         fillReturnJson(resp, false, "机构合作合同数据不存在，请联系管理员");
         return;
      }
      upCooperationContract.setFileId(bizFileId);
      orgContractClient.update(upCooperationContract);
      recordLog(OrgSysLogTypeConstant.LOG_TYPE_ADD, "上传机构合作合同,参数："+upCooperationContract, req);
      fillReturnJson(resp, true, "提交成功");
   }
   
   /**
    * 确认展开合作
    *@author:liangyanjun
    *@time:2016年7月18日上午11:36:24
    *@param resp
    *@throws TException
    */
   @RequestMapping(value="/confirmCooperat" , method=RequestMethod.POST)
   public void confirmCooperat(HttpServletRequest req,HttpServletResponse resp) throws TException {
      OrgUserInfo loginUser = getLoginUser();
      OrgAssetsCooperationService.Client orgService = (OrgAssetsCooperationService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG,
            "OrgAssetsCooperationService");
      //查询资产合作机构信息
      OrgAssetsCooperationInfo orgAssets = orgService.getById(loginUser.getOrgId());
      if (orgAssets.getCooperateStatus() == AomConstant.ORG_COOPERATE_STATUS_3) {
          fillReturnJson(resp, false, "合作已过期，不能点击确认");
          return;
      }
      if (orgAssets.getCooperateStatus() != AomConstant.ORG_COOPERATE_STATUS_4) {
         fillReturnJson(resp, false, "后台未审核通过，不能点击确认");
         return;
      }
      if (orgAssets.getCooperateStatus() == AomConstant.ORG_COOPERATE_STATUS_2) {
         fillReturnJson(resp, false, "已经在合作中了");
         return;
      }
      // 执行修改状态
      orgAssets.setCooperateStatus(AomConstant.ORG_COOPERATE_STATUS_2);
      orgService.updateCooperateStatus(orgAssets);
      recordLog(OrgSysLogTypeConstant.LOG_TYPE_UPDATE, "机构确认展开合作,参数："+orgAssets, req);
      fillReturnJson(resp, true, "提交成功，即将自动退出重新登录");
   }
   /**
    * 申请保证金变更
    *@author:liangyanjun
    *@time:2016年7月18日下午3:53:57
    *@param resp
    *@throws TException
    */
   @RequestMapping(value="/applyOrgAssureFund" , method=RequestMethod.POST)
   public void applyOrgAssureFund(double updateAssureMoney,HttpServletRequest req,HttpServletResponse resp) throws TException {
      if (updateAssureMoney<=0) {
         fillReturnJson(resp, false, "参数不合法");
         return;
      }
      //查询资产合作机构信息
      OrgUserInfo loginUser = getLoginUser();
      int orgId = loginUser.getOrgId();
      OrgAssetsCooperationService.Client orgService = (OrgAssetsCooperationService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG,
            "OrgAssetsCooperationService");
      OrgAssetsCooperationInfo orgAssets = orgService.getById(orgId);
      //检查是否已合作状态
      if (orgAssets.getCooperateStatus() != AomConstant.ORG_COOPERATE_STATUS_2) {
         fillReturnJson(resp, false, "未合作不能变更保证金");
         return;
      }
      OrgAssureFundFlowInfoService.Client orgAssureFundFlowClient = (OrgAssureFundFlowInfoService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG,
            "OrgAssureFundFlowInfoService");
      OrgAssureFundFlowInfo applyOrgFundFlow = orgAssureFundFlowClient.getApplyOrgFundFlow(orgId);
      //检查是否已经申请保证金变更
      if (applyOrgFundFlow.getPid()>0&&applyOrgFundFlow.getStatus()==AomConstant.FUND_FLOW_STATUS_1) {
         fillReturnJson(resp, false, "申请保证金变更已经在审核中，不能重复申请");
         return;
      }
      //提交保证金变更操作
      OrgCooperatCompanyApplyService.Client orgCooperatClient = (OrgCooperatCompanyApplyService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG,"OrgCooperatCompanyApplyService");
      OrgCooperatCompanyApplyInf orgCooperat = orgCooperatClient.getByUserId(orgId);
      OrgAssureFundFlowInfo orgAssureFundFlow=new OrgAssureFundFlowInfo();
      orgAssureFundFlow.setApplyId(orgCooperat.getPid());
      orgAssureFundFlow.setCurAssureMoney(updateAssureMoney);//申请调整的保证金
      orgAssureFundFlow.setOldAssureMoney(orgCooperat.getAssureMoney());//原保证金
      orgAssureFundFlow.setOldCreditLimit(orgCooperat.getCreditLimit());//原授信额度
      orgAssureFundFlow.setOperator(orgId);//操作人
      orgAssureFundFlow.setStatus(AomConstant.FUND_FLOW_STATUS_1);//保证金变更流水状态,10表示同意变更,20表示不同意变更,1待审核
      orgAssureFundFlowClient.insert(orgAssureFundFlow);
      //修改保证金调整状态为审核中
      OrgCooperatCompanyApplyService.Client orgCooperatCompanyApplyClient = (OrgCooperatCompanyApplyService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG,"OrgCooperatCompanyApplyService");
      OrgCooperatCompanyApplyInf updateApplyInf = orgCooperatCompanyApplyClient.getByOrgId(orgId);
      updateApplyInf.setStatus(AomConstant.FUND_FLOW_APPLY_STATUS_2);
      orgCooperatCompanyApplyClient.update(updateApplyInf);
      recordLog(OrgSysLogTypeConstant.LOG_TYPE_UPDATE, "申请保证金变更,参数："+orgAssureFundFlow+","+updateApplyInf, req);
      fillReturnJson(resp, true, "提交成功");
   }
   /**
    * 级联删除机构,未认证之前可删除，保存用户信息，机构信息，资金账户信息
    *@author:liangyanjun
    *@time:2016年7月28日下午4:54:50
    *@param orgId
    *@param req
    *@param resp
    *@throws ServletException
    *@throws IOException
    *@throws ThriftServiceException
    *@throws TException
    */
   @RequestMapping(value = "/deleteByOrgId")
   public void deleteByOrgId(int orgId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,ThriftServiceException, TException {
      if (orgId<=0) {
         fillReturnJson(resp, false, "参数不合法，请重新操作");
         return;
      }
      int loginUserId = getLoginUser().getPid();
      OrgAssetsCooperationService.Client orgService = (OrgAssetsCooperationService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG,
            "OrgAssetsCooperationService");
      /**
       * 级联删除机构,未认证之前可删除，保存用户信息，机构信息，资金账户信息
       * 删除成功返回1，删除失败返回-1
       */
      int isSucc = orgService.cascadeDeleteOrg(orgId, loginUserId);
      if (isSucc==1) {
         fillReturnJson(resp, true, "删除成功");
         recordLog(OrgSysLogTypeConstant.LOG_TYPE_DELETE, "删除机构,参数："+orgId, req);
      }else{
         fillReturnJson(resp, true, "删除失败，请重新操作");
      }
   }
}
