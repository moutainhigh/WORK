package com.qfang.xk.aom.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.Subject;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qfang.xk.aom.rpc.finance.OrgFinanceService;
import com.qfang.xk.aom.rpc.finance.OrgOrderSummary;
import com.qfang.xk.aom.rpc.finance.PartnerOrderSummary;
import com.qfang.xk.aom.rpc.org.OrgAssetsCooperationInfo;
import com.qfang.xk.aom.rpc.org.OrgAssetsCooperationService;
import com.qfang.xk.aom.rpc.partner.OrgPartnerInfo;
import com.qfang.xk.aom.rpc.partner.OrgPartnerInfoService;
import com.qfang.xk.aom.rpc.system.OrgSysMenuInfo;
import com.qfang.xk.aom.rpc.system.OrgSysMenuInfoService;
import com.qfang.xk.aom.rpc.system.OrgUserInfo;
import com.qfang.xk.aom.web.constants.Constants;
import com.qfang.xk.aom.web.shiro.CaptchaUsernamePasswordToken;
import com.qfang.xk.aom.web.shiro.IncorrectCaptchaException;
import com.qfang.xk.aom.web.util.MD5Util;
import com.qfang.xk.aom.web.util.PropertiesUtil;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.OrgSysLogTypeConstant;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年7月6日上午10:30:57 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 对菜单加载，用户登录登出以及首页的管理<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Controller
public class IndexController extends BaseController {
   private Logger logger = LoggerFactory.getLogger(IndexController.class);

   @ExceptionHandler
   public String exception(HttpServletRequest request, HttpServletResponse response, Exception e) {
      logger.error(ExceptionUtil.getExceptionMessage(e), e);
      fillReturnJson(response, false, "出现未知异常,请与系统管理员联系!");
      return null;
   }
   /**
    * 
    *@author:liangyanjun
    *@time:2016年7月6日下午2:22:27
    *@param model
    *@return
    * @throws TException 
    */
   @RequestMapping(value = "/index")
   public String toIndex(ModelMap model) throws TException {
      // 加载页面菜单（只加载二级以内的）
      OrgSysMenuInfoService.Client menuClient = (OrgSysMenuInfoService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_SYSTEM,
            "OrgSysMenuInfoService");
      OrgUserInfo loginUser = getLoginUser();
      int userId = loginUser.getPid();
      int userType = loginUser.getUserType();
      if (userType==AomConstant.USER_TYPE_1||userType==AomConstant.USER_TYPE_3) {
         loginUser.setMenuType(1);//机构和员工的菜单
         int orgId = loginUser.getOrgId();
         OrgAssetsCooperationService.Client orgClient = (OrgAssetsCooperationService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG, "OrgAssetsCooperationService");
         //查询机构信息
         OrgAssetsCooperationInfo orgInfo = orgClient.getById(orgId);
         model.put("orgName", orgInfo.getOrgName());//机构名称
         model.put("auditStatus", orgInfo.getAuditStatus());//认证状态
         model.put("cooperateStatus", orgInfo.getCooperateStatus());//合作状态
      }else{
         loginUser.setMenuType(2);//合伙人的菜单
         OrgPartnerInfoService.Client partnerClient = (OrgPartnerInfoService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_PARTNER, "OrgPartnerInfoService");
         //查询合伙人信息
         OrgPartnerInfo partnerInfo = partnerClient.getByUserId(userId);
         model.put("auditStatus", partnerInfo.getStatus());//认证状态
         model.put("cooperateStatus", partnerInfo.getCooperationStatus());//合作状态
      }
      model.put("auditStatusMap", AomConstant.ORG_AUDIT_STATUS_MAP);//认证状态1:未认证,2表示认证中3、已认证
      model.put("cooperateStatusMap", AomConstant.ORG_COOPERATE_STATUS_MAP);//合作状态,1:未合作,2表示已合作,3表示已过期,4合作待确认
      List<OrgSysMenuInfo> list = menuClient.getTree(loginUser);
      // 检查合伙人或机构是否已认证，未认证之前对菜单显示有控制
      boolean checkAudit = checkAudit(loginUser);
      if (!checkAudit) {
         List<OrgSysMenuInfo> tempList = new ArrayList<OrgSysMenuInfo>();
         List<String> idList = Arrays.asList(PropertiesUtil.getValue("audit.menuIds").split(","));
         filterMenu(list, tempList, idList);
         list = null;
         list = tempList;
      }
      // 检查合伙人或机构是否已合作状态，未合作之前对菜单显示有控制
      boolean checkCooperation = checkCooperation(loginUser);
      if (!checkCooperation) {
         List<OrgSysMenuInfo> tempList = new ArrayList<OrgSysMenuInfo>();
         List<String> idList = Arrays.asList(PropertiesUtil.getValue("cooperation.menuIds").split(","));
         filterMenu(list, tempList, idList);
         list = null;
         list = tempList;
      }
      model.put("userTypeMap", AomConstant.USER_TYPE_MAP);
      model.put("sysMenus", list);
      return "/index";
   }
   /**
    * 菜单过滤
    *@author:liangyanjun
    *@time:2016年7月29日上午10:18:38
    *@param list
    *@param tempList
    *@param idList
    */
   private void filterMenu(List<OrgSysMenuInfo> list, List<OrgSysMenuInfo> tempList, List<String> idList) {
      for (OrgSysMenuInfo menu : list) {
         if (idList.contains(menu.getPid() + "") || tempList.contains(menu)) {
            continue;
         }
         List<OrgSysMenuInfo> childrenList = menu.getChildrenList();
         if (childrenList!=null) {
            List<OrgSysMenuInfo> tempChildrenList = new ArrayList<OrgSysMenuInfo>();
            filterMenu(childrenList, tempChildrenList, idList);
            childrenList=null;
            menu.setChildrenList(tempChildrenList);
         }
         tempList.add(menu);
      }
   }

   @RequestMapping(value = "/orgLogin")
   public String toOrgLogin(ModelMap model) {
      return "/org_login";
   }

   /**
    * 机构首页
    *@author:liangyanjun
    *@time:2016年7月27日下午2:46:42
    *@param model
    *@return
    *@throws ThriftServiceException
    *@throws TException
    */
   @RequestMapping("/toOrgIndexContent.action")
   public String toOrgIndexContent(ModelMap model) throws ThriftServiceException, TException {
      OrgUserInfo loginUser = getLoginUser();
      int pid = loginUser.getOrgId();
      int userType = loginUser.getUserType();//用户类型,1表示机构,2表示合伙人,3表示员工(机构下的员工)
      OrgAssetsCooperationService.Client orgClient = (OrgAssetsCooperationService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG, "OrgAssetsCooperationService");
      OrgFinanceService.Client financeClient = (OrgFinanceService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_FINANCE, "OrgFinanceService");
      //查询机构信息
      OrgAssetsCooperationInfo orgInfo = orgClient.getById(pid);
      //根据机构id查询机构订单数据汇总（用于机构首页的数据展现）
      OrgOrderSummary orgOrderSummary = financeClient.getOrgOrderSummaryByOrgId(pid);
      model.put("orgInfo", orgInfo);
      model.put("orgOrderSummary", orgOrderSummary);
      model.put("orgAuditStatusMap", AomConstant.ORG_AUDIT_STATUS_MAP);//认证状态1:未认证,2表示认证中3、已认证
      model.put("orgCooperateStatusMap", AomConstant.ORG_COOPERATE_STATUS_MAP);//合作状态,1:未合作,2表示已合作,3表示已过期,3合作待确认
      String url = "/org/org_index_content";
      if (AomConstant.USER_TYPE_3==userType) {//当登录用户是员工时，显示欢迎登录页面
          url = "/welcome";
      }
      return url;
   }
   /**
    * 合伙人首页
    *@author:liangyanjun
    *@time:2016年7月27日下午2:46:57
    *@param model
    *@return
    *@throws ThriftServiceException
    *@throws TException
    */
   @RequestMapping("/toPartnerIndexContent.action")
   public String toPartnerIndexContent(ModelMap model) throws ThriftServiceException, TException {
      int userId = getLoginUser().getPid();
      OrgPartnerInfoService.Client partnerClient = (OrgPartnerInfoService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_PARTNER, "OrgPartnerInfoService");
      OrgFinanceService.Client financeClient = (OrgFinanceService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_FINANCE, "OrgFinanceService");
      //查询合伙人信息
      OrgPartnerInfo partnerInfo = partnerClient.getByUserId(userId);
      //合伙人订单数据汇总（用于合伙人首页的数据展现）
      PartnerOrderSummary partnerOrderSummary = financeClient.getPartnerOrderSummary(userId);
      model.put("partnerInfo", partnerInfo);
      model.put("partnerOrderSummary", partnerOrderSummary);
      model.put("orgAuditStatusMap", AomConstant.ORG_AUDIT_STATUS_MAP);//认证状态1:未认证,2表示认证中3、已认证
      model.put("orgCooperateStatusMap", AomConstant.ORG_COOPERATE_STATUS_MAP);//合作状态
      return "/partner/partner_index_content";
   }

   @RequestMapping("/toProfile")
   public String toProfile(ModelMap model) {
      return "/profile";
   }
   @RequestMapping("/ignore/toForgetPassword")
   public String toForgetPassword(ModelMap model) {
      return "/forget_password";
   }

   /**
    * 用户登录系统
    *@author:liangyanjun
    *@time:2016年7月6日下午2:22:22
    *@param model
    *@param userName
    *@param password
    *@param captcha
    *@param request
    *@return
    */
   @RequestMapping(value = "/ignore/checkUserLogin", method = RequestMethod.POST)
   public void checkUserLogin(ModelMap model, String userName, String password, String captcha, HttpServletRequest request,HttpServletResponse response) {
      HashMap<String,Object> result=new HashMap<String,Object>();
      Subject subject = SecurityUtils.getSubject();
      CaptchaUsernamePasswordToken token = new CaptchaUsernamePasswordToken();
      token.setCaptcha(captcha);
      token.setRememberMe(true);
      try {
         token.setPassword(MD5Util.tltMd5(password).toCharArray());
         token.setUsername(userName);
         subject.login(token);
         recordLog(OrgSysLogTypeConstant.LOG_TYPE_LOGIN, "用户登录", request);
      } catch (UnknownSessionException use) {
         subject = new Subject.Builder().buildSubject();
         subject.login(token);
         result.put("msg", Constants.UNKNOWN_SESSION_EXCEPTION);
         logger.error(ExceptionUtil.getExceptionMessage(use));
      } catch (UnknownAccountException | IncorrectCredentialsException ex) {
          result.put("msg", Constants.UNKNOWN_PWD_ACCOUNT_EXCEPTION);
      } catch (LockedAccountException lae) {
          result.put("msg", Constants.LOCKED_ACCOUNT_EXCEPTION);
      } catch (DisabledAccountException lae) {
          result.put("msg", Constants.DISABLED_ACCOUNT_EXCEPTION);
      }  catch (IncorrectCaptchaException e) {
          result.put("msg", Constants.INCORRECT_CAPTCHA_EXCEPTION);
      } catch (Exception e) {
          result.put("msg", Constants.UNKNOWN_EXCEPTION);
          logger.error(ExceptionUtil.getExceptionMessage(e));
      }
      if (result.get("msg")!=null) {
          result.put("success", false);
          recordLog(OrgSysLogTypeConstant.LOG_TYPE_LOGIN, userName+"用户登录失败："+model.get("msg"), request);
      }else{
          result.put("success", true);
      }
      outputJson(result, response);
   }

   /**
    * 登出系统
    *@author:liangyanjun
    *@time:2016年7月6日下午3:56:52
    *@param req
    *@return
    */
   @RequestMapping("/logout")
   public String logout(HttpServletRequest req) {
      Subject subject = SecurityUtils.getSubject();
      if (subject.isAuthenticated()) {
         recordLog(OrgSysLogTypeConstant.LOG_TYPE_LOGOUT, "登录退出", req);
         subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
      }
      return "/org_login";
   }
}
