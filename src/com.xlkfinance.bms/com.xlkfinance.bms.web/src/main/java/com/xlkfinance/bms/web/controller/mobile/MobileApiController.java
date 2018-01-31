package com.xlkfinance.bms.web.controller.mobile;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.Subject;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.achievo.framework.util.DateUtil;
import com.achievo.framework.util.PasswordUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qfang.data.facade.model.BuildingDto;
import com.qfang.data.facade.model.GardenDto;
import com.qfang.data.facade.model.RoomDto;
import com.qfang.data.facade.model.UnitDto;
import com.qfang.data.facade.model.enums.CityDtoEnum;
import com.qfang.data.facade.model.enums.EvaluationApiCallerIdentifierEnum;
import com.qfang.data.facade.model.request.BuildingSearchRequest;
import com.qfang.data.facade.model.request.EvaluationHousePriceGuidingRequest;
import com.qfang.data.facade.model.request.GardenSearchRequest;
import com.qfang.data.facade.model.request.RoomSearchRequest;
import com.qfang.data.facade.model.response.BuildingSearchResponse;
import com.qfang.data.facade.model.response.EvaluationHousePriceGuidingResponse;
import com.qfang.data.facade.model.response.GardenSearchResponse;
import com.qfang.data.facade.model.response.RoomSearchResponse;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApply;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyService;
import com.xlkfinance.bms.common.constant.BizDynamicConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.constant.OrgSysLogTypeConstant;
import com.xlkfinance.bms.common.constant.SysLogTypeConstant;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.common.util.CheckUtil;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.DataInfo;
import com.xlkfinance.bms.rpc.beforeloan.DataUploadService;
import com.xlkfinance.bms.rpc.beforeloan.ElementLend;
import com.xlkfinance.bms.rpc.beforeloan.ElementLendDetails;
import com.xlkfinance.bms.rpc.beforeloan.ElementLendService;
import com.xlkfinance.bms.rpc.beforeloan.ElementMobileDto;
import com.xlkfinance.bms.rpc.beforeloan.GridViewMobileDto;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectForeInformation;
import com.xlkfinance.bms.rpc.beforeloan.ProjectForeclosure;
import com.xlkfinance.bms.rpc.beforeloan.ProjectGuarantee;
import com.xlkfinance.bms.rpc.beforeloan.ProjectProperty;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.beforeloan.ProjectSurveyReport;
import com.xlkfinance.bms.rpc.beforeloan.ProjectSurveyReportService;
import com.xlkfinance.bms.rpc.checkapi.HouseCheckDoc;
import com.xlkfinance.bms.rpc.checkapi.HouseCheckDocDetails;
import com.xlkfinance.bms.rpc.checkapi.HouseCheckDocService;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.customer.CusAcct;
import com.xlkfinance.bms.rpc.customer.CusAcctBank;
import com.xlkfinance.bms.rpc.customer.CusAcctService;
import com.xlkfinance.bms.rpc.customer.CusComBase;
import com.xlkfinance.bms.rpc.customer.CusComBaseDTO;
import com.xlkfinance.bms.rpc.customer.CusComService;
import com.xlkfinance.bms.rpc.customer.CusComShare;
import com.xlkfinance.bms.rpc.customer.CusDTO;
import com.xlkfinance.bms.rpc.customer.CusPerBase;
import com.xlkfinance.bms.rpc.customer.CusPerBaseDTO;
import com.xlkfinance.bms.rpc.customer.CusPerCredit;
import com.xlkfinance.bms.rpc.customer.CusPerCreditDTO;
import com.xlkfinance.bms.rpc.customer.CusPerFamily;
import com.xlkfinance.bms.rpc.customer.CusPerFamilyDTO;
import com.xlkfinance.bms.rpc.customer.CusPerFamilyFinance;
import com.xlkfinance.bms.rpc.customer.CusPerService;
import com.xlkfinance.bms.rpc.customer.CusPerSocSec;
import com.xlkfinance.bms.rpc.customer.CusPerson;
import com.xlkfinance.bms.rpc.customer.CusRelation;
import com.xlkfinance.bms.rpc.inloan.ApplyHandleIndexDTO;
import com.xlkfinance.bms.rpc.inloan.ApplyHandleInfoDTO;
import com.xlkfinance.bms.rpc.inloan.BizDynamicEvaluateInfo;
import com.xlkfinance.bms.rpc.inloan.BizDynamicEvaluateService;
import com.xlkfinance.bms.rpc.inloan.BizEvaluateMap;
import com.xlkfinance.bms.rpc.inloan.BizHandleService;
import com.xlkfinance.bms.rpc.inloan.BizHandleService.Client;
import com.xlkfinance.bms.rpc.inloan.CollectFileDTO;
import com.xlkfinance.bms.rpc.inloan.HandleDifferWarnDTO;
import com.xlkfinance.bms.rpc.inloan.HandleDynamicDTO;
import com.xlkfinance.bms.rpc.inloan.HandleDynamicFileDTO;
import com.xlkfinance.bms.rpc.inloan.HandleDynamicMap;
import com.xlkfinance.bms.rpc.inloan.HandleFlowDTO;
import com.xlkfinance.bms.rpc.inloan.HandleInfoDTO;
import com.xlkfinance.bms.rpc.inloan.HouseBalanceDTO;
import com.xlkfinance.bms.rpc.inloan.IntegratedDeptService;
import com.xlkfinance.bms.rpc.inloan.IntermediateTransferDTO;
import com.xlkfinance.bms.rpc.inloan.IntermediateTransferIndexDTO;
import com.xlkfinance.bms.rpc.inloan.IntermediateTransferService;
import com.xlkfinance.bms.rpc.inloan.RefundFeeDTO;
import com.xlkfinance.bms.rpc.inloan.RefundFeeIndexDTO;
import com.xlkfinance.bms.rpc.inloan.RefundFeeService;
import com.xlkfinance.bms.rpc.product.Product;
import com.xlkfinance.bms.rpc.product.ProductService;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.rpc.system.BizFileService;
import com.xlkfinance.bms.rpc.system.EidtPassword;
import com.xlkfinance.bms.rpc.system.ProblemFeedback;
import com.xlkfinance.bms.rpc.system.SysAdvPicInfo;
import com.xlkfinance.bms.rpc.system.SysAdvPicService;
import com.xlkfinance.bms.rpc.system.SysAppVersionInfo;
import com.xlkfinance.bms.rpc.system.SysAppVersionInfoService;
import com.xlkfinance.bms.rpc.system.SysBankInfo;
import com.xlkfinance.bms.rpc.system.SysBankService;
import com.xlkfinance.bms.rpc.system.SysLookupService;
import com.xlkfinance.bms.rpc.system.SysLookupVal;
import com.xlkfinance.bms.rpc.system.SysOrgInfo;
import com.xlkfinance.bms.rpc.system.SysOrgInfoService;
import com.xlkfinance.bms.rpc.system.SysProblemFeedbackService;
import com.xlkfinance.bms.rpc.system.SysRole;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.rpc.system.SysUserService;
import com.xlkfinance.bms.rpc.system.SysUserSign;
import com.xlkfinance.bms.rpc.system.SysUserSignService;
import com.xlkfinance.bms.rpc.workflow.TaskVo;
import com.xlkfinance.bms.rpc.workflow.WorkflowService;
import com.xlkfinance.bms.web.api.qfangapi.QfangApiService;
import com.xlkfinance.bms.web.api.qfangapi.util.QfangUtil;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.shiro.CaptchaUsernamePasswordToken;
import com.xlkfinance.bms.web.shiro.IncorrectCaptchaException;
import com.xlkfinance.bms.web.shiro.ShiroUser;
import com.xlkfinance.bms.web.util.FileDownload;
import com.xlkfinance.bms.web.util.FileUtil;
import com.xlkfinance.bms.web.util.HttpRequestParam;
import com.xlkfinance.bms.web.util.HttpUtils;
import com.xlkfinance.bms.web.util.IDCardUtil;
import com.xlkfinance.bms.web.util.PropertiesUtil;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年3月24日上午9:21:42 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 移动端api接口<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Controller
@RequestMapping("/mobileApi")
public class MobileApiController extends BaseController {
   private Logger logger = LoggerFactory.getLogger(MobileApiController.class);
   
	@Autowired
	private QfangApiService qfangApiService;	 
 
	 /**
	    * 全局异常处理
	    *@author:liangyanjun
	    *@time:2015-12-9下午2:58:38
	    *@param request
	    *@param response
	    *@param e
	    *@return
	    */
	/*   @ExceptionHandler
	   public String exception(HttpServletRequest request, HttpServletResponse response, Exception e) {
	      logger.error(ExceptionUtil.getExceptionMessage(e), e);
	      returnJsonOfMobile(response, false, "出现未知异常,请与系统管理员联系!", ExceptionCode.MOBILE_SERVER_ERROR);
	      return null;
	   }*/

	   /**
	    * 登录
	    *@author:liangyanjun
	    *@time:2016年3月24日上午9:23:36
	    *@param params
	    *@param request
	    *@param response
	    */
	   @RequestMapping(value = "/login", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
	   @ResponseBody
	   public void login(@RequestBody Map params, HttpServletRequest request, HttpServletResponse response) {
	      logger.info("method:login;入参：" + params);
	      Map<Object, Object> jsonMap = new HashMap<Object, Object>();
	      String userNameBase = (String) params.get("user_name");// 用户名
	      String passwordBase = (String) params.get("password");// 密码
	      String captcha = (String) params.get("captcha");// 验证码
	      if (StringUtil.isBlank(userNameBase, passwordBase)) {
	         returnJsonOfMobile(response, false, "参数不合法", ExceptionCode.MOBILE_BAD_REQUEST);
	         return;
	      }
	      // shiro检查用户名和密码是否正确
	      SysUserService.Client sysUserService = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");
	      Subject subject = SecurityUtils.getSubject();
	      CaptchaUsernamePasswordToken token = new CaptchaUsernamePasswordToken();
	      token.setCaptcha(captcha);
	      //token.setRememberMe(true);
	      try {
	         // 如果输入的是手机号码，并且存在该用户，则从数据库中获取用户名
	         userNameBase = getFromBASE64(userNameBase);
	         SysUser tempSysUser = sysUserService.getSysUserByPhone(userNameBase);
	         if (tempSysUser.getPid() > 0) {
	            userNameBase = tempSysUser.getUserName();
	         }
	         
	         String passwordEncrypt = PasswordUtil.encrypt(userNameBase, getFromBASE64(passwordBase), PasswordUtil.getStaticSalt());
	         token.setPassword(passwordEncrypt.toCharArray());
	         token.setUsername(userNameBase);
	         subject.login(token);
	         
	         ShiroUser shiroUser = getShiroUser();
	         Integer pid = shiroUser.getPid();
	         //保存登录用户的token
	         Session session = subject.getSession();
	         String sessionId = session.getId().toString();
	         sysUserService.updateToken(pid, sessionId);
	         
	         // 获取用户信息
	         SysUser sysUser = sysUserService.getUserDetailsByPid(pid);
	         Map<Object, Object> userInfoMap = fillUserInfo(sysUser);
	         // 生成预警事项
	         generateHandleDifferWarn(pid);
	         // 返回结果
	         jsonMap.put("user_info", userInfoMap);
	         returnJsonOfMobile(response, jsonMap, true, "登录成功", ExceptionCode.MOBILE_OK);
	      } catch (UnknownSessionException use) {
	         subject = new Subject.Builder().buildSubject();
	         subject.login(token);
	         logger.error("异常会话!");
	         returnJsonOfMobile(response, false, "异常会话!", ExceptionCode.MOBILE_SESSION);
	      } catch (UnknownAccountException ex) {
	         logger.error("账号错误!");
	         returnJsonOfMobile(response, false, "账号错误!", ExceptionCode.MOBILE_LOGIN);
	      } catch (IncorrectCredentialsException ice) {
	         ice.printStackTrace();
	         returnJsonOfMobile(response, false, "密码错误!", ExceptionCode.MOBILE_LOGIN);
	      } catch (LockedAccountException lae) {
	         returnJsonOfMobile(response, false, "账号已被锁定，请与系统管理员联系!", ExceptionCode.MOBILE_USER_LOCK);
	      } catch (IncorrectCaptchaException e) {
	         returnJsonOfMobile(response, false, "验证码错误!", ExceptionCode.MOBILE_CAPTCHA);
	      } catch (AuthenticationException ae) {
	         returnJsonOfMobile(response, false, "出现未知异常,请与系统管理员联系!", ExceptionCode.MOBILE_SERVER_ERROR);
	      } catch (Exception e) {
	         e.printStackTrace();
	         returnJsonOfMobile(response, false, "出现未知异常,请与系统管理员联系!", ExceptionCode.MOBILE_SERVER_ERROR);
	      }
	      logger.info("method:login;返回结果：" + jsonMap);
	   }
	   /**
	    * 登出
	    *@author:liangyanjun
	    *@time:2016年10月26日下午4:39:08
	    *@param request
	    *@param response
	    */
	   @RequestMapping(value = "/logout", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
       @ResponseBody
        public void logout(HttpServletRequest request, HttpServletResponse response) {
            Subject subject = SecurityUtils.getSubject();
            Object principal = subject.getPrincipal();
            if (principal == null) {//表示没有登录
                return;
            }
            logger.info("method:logout;登录退出;userName："+principal);
            if (subject.isAuthenticated()) {
                subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
            }
            returnJsonOfMobile(response,true, "登出成功", ExceptionCode.MOBILE_OK);
        }

	   /**
	    * 修改密码
	    *@author:liangyanjun
	    *@time:2016年3月25日上午10:10:00
	    *@param params
	    *@param request
	    *@param response
	    *@throws TException
	    */
	   @RequestMapping(value = "/updatePassword", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
	   @ResponseBody
	   public void updatePassword(@RequestBody Map params, HttpServletRequest request, HttpServletResponse response) throws TException {
	      logger.info("method:updatePassword;入参：" + params);
	      Integer userId = (Integer) params.get("user_id");// 用户id
	      String userName = (String) params.get("user_name");// 用户名
	      String oldPassword = (String) params.get("old_password");// 旧密码
	      String newPassword = (String) params.get("new_password");// 新密码
	      if (userId < 1 || StringUtil.isBlank(userName, oldPassword, newPassword)) {
	         returnJsonOfMobile(response, false, "参数不合法", null);
	         return;
	      }
	      SysUserService.Client sysUserService = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");

	      String oldPwd = PasswordUtil.encrypt(userName, getFromBASE64(oldPassword), PasswordUtil.getStaticSalt());
	      String newPwd = PasswordUtil.encrypt(userName, getFromBASE64(newPassword), PasswordUtil.getStaticSalt());
	      EidtPassword ep = new EidtPassword();
	      ep.setUid(userId);
	      ep.setNewPwd(newPwd);
	      ep.setOldPwd(oldPwd);
	      // 调用修改方法
	      sysUserService.eidtPassword(ep);
	      returnJsonOfMobile(response, true, "修改成功", null);
	   }

	   /**
	    * 查询个人信息
	    *@author:liangyanjun
	    *@time:2016年3月24日下午4:57:09
	    *@param params
	    *@param request
	    *@param response
	    * @throws TException 
	    */
	   @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
	   @ResponseBody
	   public void getUserInfo(@RequestBody Map params, HttpServletRequest request, HttpServletResponse response) throws TException {
	      logger.info("method:getUserInfo;入参：" + params);
	      Integer userId = (Integer) params.get("user_id");// 用户id
	      if (userId < 1) {
	         returnJsonOfMobile(response, false, "参数不合法", null);
	         return;
	      }
	      Map<Object, Object> jsonMap = new HashMap<Object, Object>();
	      SysUserService.Client sysUserService = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");
	      //
	      SysUser sysUser = sysUserService.getUserDetailsByPid(userId);
	      Map<Object, Object> userInfoMap = fillUserInfo(sysUser);

	      // 返回结果
	      jsonMap.put("user_info", userInfoMap);
	      returnJsonOfMobile(response, jsonMap, true, "查询成功", null);
	      logger.info("method:getUserInfo;返回结果：" + jsonMap);
	   }

	   /**
	    * 用户签到
	    *@author:liangyanjun
	    *@time:2016年3月29日上午10:42:49
	    *@param params
	    *@param request
	    *@param response
	    *@throws TException
	    */
	   @RequestMapping(value = "/userSign", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
	   @ResponseBody
	   public void userSign(@RequestBody Map params, HttpServletRequest request, HttpServletResponse response) throws TException {
	      logger.info("method:userSign;入参：" + params);
	      Integer userId = (Integer) params.get("user_id");// 用户id
	      if (userId < 1) {
	         returnJsonOfMobile(response, false, "参数不合法", null);
	         return;
	      }
	      Map resultMap = new HashMap<String, Object>();
	      SysUserSignService.Client client = (SysUserSignService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserSignService");
	      // 检查用户今天是否已经签到
	      int isSign = client.checkToDayIsSign(userId);
	      if (isSign > 0) {
	         returnJsonOfMobile(response, false, "今天已经签到", null);
	         return;
	      }
	      // 保存用户签到信息
	      SysUserSign sysUserSign = new SysUserSign();
	      sysUserSign.setUserId(userId);
	      client.addSysUserSign(sysUserSign);
	      // 查询签到天数
	      int signDays = client.getSignDaysByUserId(userId);
	      // 返回结果
	      resultMap.put("sign_days", signDays);
	      returnJsonOfMobile(response, resultMap, true, "签到成功", null);
	      logger.info("method:userSign;返回结果：" + resultMap);
	   }

	   /**
	    * 获取签到天数
	    *@author:liangyanjun
	    *@time:2016年3月30日上午9:13:01
	    *@param params
	    *@param request
	    *@param response
	    *@throws TException
	    */
	   @RequestMapping(value = "/getSignDays", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
	   @ResponseBody
	   public void getSignDays(@RequestBody Map params, HttpServletRequest request, HttpServletResponse response) throws TException {
	      logger.info("method:getSignDays;入参：" + params);
	      Integer userId = (Integer) params.get("user_id");// 用户id
	      if (userId < 1) {
	         returnJsonOfMobile(response, false, "参数不合法", null);
	         return;
	      }
	      Map resultMap = new HashMap<String, Object>();
	      SysUserSignService.Client client = (SysUserSignService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserSignService");
	      // 查询签到天数
	      int signDays = client.getSignDaysByUserId(userId);
	      int checkToDayIsSign = client.checkToDayIsSign(userId);
	      // 返回结果
	      resultMap.put("to_day_is_sign", checkToDayIsSign > 0 ? true : false);
	      resultMap.put("sign_days", signDays);
	      returnJsonOfMobile(response, resultMap, true, "查询成功", null);
	      logger.info("method:getSignDays;返回结果：" + resultMap);
	   }

	   /**
	    * 查询首页预警事项
	    *@author:liangyanjun
	    *@time:2016年3月24日下午3:56:30
	    *@param request
	    *@param response
	    * @throws TException 
	    */
	   @RequestMapping(value = "/queryIndexHandleDifferWarn", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
	   @ResponseBody
	   public void queryIndexHandleDifferWarn(@RequestBody Map params, HttpServletRequest request, HttpServletResponse response) throws TException {
	      logger.info("method:queryIndexHandleDifferWarn;入参：" + params);
	      Integer userId = (Integer) params.get("user_id");// 用户id
	      if (userId < 1) {
	         returnJsonOfMobile(response, false, "参数不合法", null);
	         return;
	      }
	      Map resultMap = new HashMap<String, Object>();
	      Client client = (Client) getService(BusinessModule.MODUEL_INLOAN, "BizHandleService");
	      // 查询预警事项
	      List<HandleDifferWarnDTO> listHandleDifferWarnDTOs = client.findIndexHandleDifferWarn(userId);

	      // 封装办理动态预警事项
	      List<Map> list = new ArrayList<Map>();
	      for (HandleDifferWarnDTO dto : listHandleDifferWarnDTOs) {
	         Map<Object, Object> warnMap = new HashMap<Object, Object>();
	         warnMap.put("pid", checkIntValue(dto.getPid()));
	         warnMap.put("project_id", checkIntValue(dto.getProjectId()));
	         warnMap.put("project_name", checkString(dto.getProjectName()));
	         warnMap.put("differ", checkIntValue(dto.getDiffer()));
	         warnMap.put("flow_name", checkString(dto.getFlowName()));
	         warnMap.put("create_date", checkString(dto.getCreateDate()));
	         list.add(warnMap);
	      }

	      // 返回结果
	      resultMap.put("result_list", list);
	      returnJsonOfMobile(response, resultMap, true, "查询成功", null);
	      logger.info("method:queryIndexHandleDifferWarn;返回结果：" + resultMap);
	   }

	   /**
	    * 根据项目id查询办理动态历史备注
	    *@author:liangyanjun
	    *@time:2016年3月28日下午2:21:14
	    *@param params
	    *@param request
	    *@param response
	    *@throws TException
	    */
	   @RequestMapping(value = "/queryHisDifferWarnList", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
	   @ResponseBody
	   public void queryHisDifferWarnList(@RequestBody Map params, HttpServletRequest request, HttpServletResponse response) throws TException {
	      logger.info("method:queryHisDifferWarnList;入参：" + params);
	      Integer projectId = (Integer) params.get("project_id");// 项目id
	      int page = params.get("page") == null ? 1 : (Integer) params.get("page");
	      int rows = params.get("rows") == null ? 10 : (Integer) params.get("rows");
	      List<Map> list = new ArrayList<Map>();
	      Map resultMap = new HashMap<String, Object>();

	      // 查询预警事项列表
	      BizHandleService.Client service = (BizHandleService.Client) getService(BusinessModule.MODUEL_INLOAN, "BizHandleService");
	      HandleDifferWarnDTO handleDifferWarnQuery = new HandleDifferWarnDTO();
	      handleDifferWarnQuery.setProjectId(projectId);
	      handleDifferWarnQuery.setPage(page);
	      handleDifferWarnQuery.setRows(rows);
	      List<HandleDifferWarnDTO> differWarnReportList = service.findAllHisHandleDifferWarn(handleDifferWarnQuery);
	      for (HandleDifferWarnDTO dto : differWarnReportList) {
	         Map<Object, Object> hisWarnMap = new HashMap<Object, Object>();
	         hisWarnMap.put("pid", checkIntValue(dto.getPid()));
	         hisWarnMap.put("remark", checkString(dto.getRemark()));
	         hisWarnMap.put("create_date", checkString(dto.getCreateDate()));
	         list.add(hisWarnMap);
	      }
	      // 根据项目id查询办理总历时天数
	      int handleDays = service.gethandleDaysByProjectId(projectId);

	      resultMap.put("handle_days", handleDays);
	      resultMap.put("resur_list", list);
	      returnJsonOfMobile(response, resultMap, true, "查询成功", null);
	      logger.info("method:queryHisDifferWarnList;返回结果：" + resultMap);
	   }

	   /**
	    * 
	    *@author:liangyanjun
	    *@time:2016年3月24日下午4:15:08
	    *@param userId
	    *@throws TException
	    */
	   private void generateHandleDifferWarn(Integer userId) throws TException {
	      BizHandleService.Client bizHandleService = (BizHandleService.Client) getService(BusinessModule.MODUEL_INLOAN, "BizHandleService");
	      //每次登陆先清空没有办理的预警事项
	      HandleDifferWarnDTO delHandleDifferWarnDTO=new HandleDifferWarnDTO();
	      delHandleDifferWarnDTO.setHandleAuthor(userId);
	      delHandleDifferWarnDTO.setStatus(Constants.NOT_HANDLE_WARN_STATUS);
	      bizHandleService.delHandleDifferWarn(delHandleDifferWarnDTO);
	      //查询是否有需要处理的业务办理预警事项，如果有则把数据增加到差异预警处理（BIZ_LOAN_HANDLE_DIFFER_WARN）表中
	      List<HandleDifferWarnDTO> needHandleWarnList = bizHandleService.getNeedHandleWarn(userId);
	      if (needHandleWarnList != null && needHandleWarnList.size() > 0) {
	         for (HandleDifferWarnDTO warnDTO : needHandleWarnList) {
	            warnDTO.setStatus(com.xlkfinance.bms.common.constant.Constants.NOT_HANDLE_WARN_STATUS);
	            warnDTO.setHandleAuthor(userId);
	            warnDTO.setHandleType(com.xlkfinance.bms.common.constant.Constants.WARN_HANDLE_TYPE_1);
	            bizHandleService.addHandleDifferWarn(warnDTO);
	         }
	      }
	   }

	   /**
	    * 提交预警事项备注
	    *@author:liangyanjun
	    *@time:2016年3月24日下午4:34:25
	    *@param params
	    *@param request
	    *@param response
	    * @throws TException 
	    */
	   @RequestMapping(value = "/subHandleDifferWarn", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
	   @ResponseBody
	   public void subHandleDifferWarn(@RequestBody Map params, HttpServletRequest request, HttpServletResponse response) throws TException {
	      logger.info("method:subHandleDifferWarn;入参：" + params);
	      Integer userId = (Integer) params.get("user_id");// 用户id
	      Integer warnId = (Integer) params.get("warn_pid");// 预警事项id
	      String remark = (String) params.get("remark");// 备注
	      if (userId < 1 || warnId < 1 || StringUtil.isBlank(remark)) {
	         returnJsonOfMobile(response, false, "参数不合法", null);
	         return;
	      }
	      Client client = (Client) getService(BusinessModule.MODUEL_INLOAN, "BizHandleService");

	      // 查询需要修改的差异预警处理信息
	      HandleDifferWarnDTO handleDifferWarnQuery = new HandleDifferWarnDTO();
	      handleDifferWarnQuery.setHandleAuthor(userId);
	      handleDifferWarnQuery.setPid(warnId);
	      List<HandleDifferWarnDTO> handleDifferWarnList = client.findAllHandleDifferWarn(handleDifferWarnQuery);
	      if (handleDifferWarnList == null || handleDifferWarnList.isEmpty()) {
	         returnJsonOfMobile(response, false, "预警不存在", null);
	         return;
	      }

	      HandleDifferWarnDTO updateDifferWarnDTO = handleDifferWarnList.get(0);
	      // 检查是否已经处理
	      if (updateDifferWarnDTO.getStatus() == Constants.IS_HANDLE_WARN_STATUS) {
	         returnJsonOfMobile(response, false, "预警已经备注", null);
	         return;
	      }
	      // 设置修改的值
	      updateDifferWarnDTO.setRemark(remark);// 备注
	      updateDifferWarnDTO.setHandleDate(DateUtils.getCurrentDateTime());// 处理时间
	      updateDifferWarnDTO.setStatus(Constants.IS_HANDLE_WARN_STATUS);// 状态：失效=-1,未处理=1，已处理=2
	      // 更新差异预警处理状态和备注
	      client.updateHandleDifferWarn(updateDifferWarnDTO);
	      // 添加到差异预警历史处理表中
	      client.addHisHandleDifferWarn(updateDifferWarnDTO);

	      returnJsonOfMobile(response, true, "提交成功", null);
	   }

	   /**
	    * 上传头像
	    *@author:liangyanjun
	    *@time:2016年3月25日上午9:16:39
	    *@param request
	    *@param response
	    *@throws TException
	    *@throws ServletException
	    *@throws IOException
	    */
	   @RequestMapping(value = "/uploadPhoto", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
	   public void uploadPhoto(@RequestBody Map params, HttpServletRequest request, HttpServletResponse response) throws TException, ServletException, IOException {
	      logger.info("method:uploadPhoto;入参：" + params);
	      int userId = (Integer) params.get("user_id");
	      int fileId = (Integer) params.get("file_id");
	      Map resultMap = new HashMap<>();
	      // 检查参数是否合法
	      if (fileId <= 0 || userId <= 0) {
	         returnJsonOfMobile(response, false, "参数不合法", null);
	         return;
	      }
	      // 查询用户信息
	      SysUserService.Client sysUserService = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");
	      SysUser sysUser = sysUserService.getSysUserByPid(userId);
	      if (sysUser == null) {
	         returnJsonOfMobile(response, false, "保存失败", null);
	         return;
	      }
	      // 获取文件
	      BizFileService.Client bizFileServiceClient = (BizFileService.Client) getService(BusinessModule.MODUEL_SYSTEM, "BizFileService");
	      BizFile bizFile = bizFileServiceClient.getBizFileById(fileId);
	      if (bizFile == null) {
	         returnJsonOfMobile(response, false, "保存失败", null);
	         return;
	      }
	      // 获取文件url
	      String fileUrl = bizFile.getFileUrl();
	      sysUser.setPhotoUrl(fileUrl);
	      // 执行修改头像url
	      sysUserService.updateSysUser(sysUser);

	      // 返回结果
	      resultMap.put("photo_url", fileUrl);
	      returnJsonOfMobile(response, resultMap, true, "保存成功", null);
	      logger.info("method:uploadPhoto;返回结果：" + resultMap);
	   }

	   /**
	    * 查询业务办理列表
	    *@author:liangyanjun
	    *@time:2016年3月25日上午10:45:28
	    *@param params
	    *@param request
	    *@param response
	    *@throws TException
	    * @throws ThriftException 
	    */
	   @RequestMapping(value = "/queryBizHandleList", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
	   @ResponseBody
	   public void queryBizHandleList(@RequestBody Map params, HttpServletRequest request, HttpServletResponse response) throws TException, ThriftException {
	      logger.info("method:queryBizHandleList;入参：" + params);
	      Map resultMap = new HashMap<String, Object>();

	      // 获取参数
	      int userId = checkIntValue((Integer) params.get("user_id"));// 用户id
	      int bizHandleId = checkIntValue((Integer) params.get("biz_handle_id"));// 业务办理id
	      int projectId = checkIntValue((Integer) params.get("project_id"));// 项目id
	      int productId = checkIntValue((Integer) params.get("product_id"));// 产品id
	      String projectNumber = checkString((String) params.get("project_number"));// 项目编号
	      String projectName = checkString((String) params.get("project_name"));// 项目名称
	      int projectStatus = checkIntValue((Integer) params.get("project_status"));// 项目状态
	      int recStatus = checkIntValue((Integer) params.get("rec_status"));// 收费到帐状态
	      int applyHandleStatus = checkIntValue((Integer) params.get("apply_handle_status"));// 申请办理状态
	      int isMyBiz = params.get("is_my_biz") == null ? -1 : (Integer) params.get("is_my_biz");//
	      String dynamicName = checkString((String) params.get("dynamic_name"));//
	      int page = params.get("page") == null ? 1 : (Integer) params.get("page");
	      int rows = params.get("rows") == null ? 10 : (Integer) params.get("rows");
	      if (userId < 1) {
	         returnJsonOfMobile(response, false, "参数不合法", null);
	         return;
	      }
	      // 查询用户信息
	      SysUserService.Client sysUserService = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");
	      SysUser sysUser = sysUserService.getSysUserByPid(userId);
	      // 查询业务办理列表
	      ApplyHandleIndexDTO query = new ApplyHandleIndexDTO();
	      query.setPid(bizHandleId);
	      query.setProjectId(projectId);
	      query.setProductId(productId);
	      query.setProjectNumber(projectNumber);
	      query.setProjectName(projectName);
	      query.setProjectStatus(projectStatus);
	      query.setRecStatus(recStatus);
	      query.setApplyHandleStatus(applyHandleStatus);
	      query.setTaskName(dynamicName);
	      int workFlowStatus = (isMyBiz == 1) ? 1 : -1;
	      query.setWorkFlowStatus(workFlowStatus);
	      if (workFlowStatus == -1) {// 业务办理
	         query.setTaskUserName(sysUser.getUserName());
	      } else {// 我的业务
	         List<Integer> userIds = new ArrayList<Integer>();
	         userIds.add(sysUser.getPid());
	         query.setUserIds(userIds);
	      }
	      query.setPage(page);
	      query.setRows(rows);
	      Client client = (Client) getService(BusinessModule.MODUEL_INLOAN, "BizHandleService");
	      List<ApplyHandleIndexDTO> list = client.findAllApplyHandleIndex(query);

	      // 封装list
	      List<Map> resultList = new ArrayList<Map>();
	      for (ApplyHandleIndexDTO dto : list) {
	         Map<Object, Object> map = new HashMap<Object, Object>();
	         map.put("biz_handle_id", dto.getPid());
	         map.put("project_id", dto.getProjectId());
	         map.put("project_number", checkString(dto.getProjectNumber()));
	         map.put("project_name", checkString(dto.getProjectName()));
	         map.put("project_status", dto.getProjectStatus());
	         map.put("apply_handle_status", dto.getApplyHandleStatus());
	         map.put("rec_status", dto.getRecFeeStatus());
	         map.put("project_pass_date", checkString(dto.getProjectPassDate()));
	         map.put("dynamic_name", checkString(dto.getTaskName()));
	         map.put("product_name", checkString(dto.getProductName()));
	         map.put("loan_money", dto.getLoanMoney());
	         map.put("is_chechan", dto.getIsChechan());//是否已撤单（1：是，0：不是，默认为0）
	         resultList.add(map);
	      }
	      // 返回结果
	      resultMap.put("result_list", resultList);
	      returnJsonOfMobile(response, resultMap, true, "查询成功", null);
	      logger.info("method:queryBizHandleList;返回结果：" + resultMap);
	   }

	   /**
	    * 获取办理动态对应的条数
	    *@author:liangyanjun
	    *@time:2016年3月25日下午3:26:30
	    *@param params
	    *@param request
	    *@param response
	    *@throws TException
	    * @throws ThriftException 
	    */
	   @RequestMapping(value = "/qeuryHandleDynamicCountMapList", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8",
	         "Accept-Version=api_v1" })
	   @ResponseBody
	   public void qeuryHandleDynamicCountMapList(@RequestBody Map params, HttpServletRequest request, HttpServletResponse response) throws TException, ThriftException {
	      logger.info("method:qeuryHandleDynamicCountMapList;入参：" + params);
	      Map resultMap = new HashMap<String, Object>();

	      // 获取参数
	      int userId = params.get("user_id") == null ? 0 : (Integer) params.get("user_id");// 用户id
	      int bizHandleId = params.get("biz_handle_id") == null ? 0 : (Integer) params.get("biz_handle_id");// 业务办理id
	      int projectId = params.get("project_id") == null ? 0 : (Integer) params.get("project_id");// 项目id
	      String projectNumber = params.get("project_number") == null ? "" : (String) params.get("project_number");// 项目编号
	      String projectName = params.get("project_name") == null ? "" : (String) params.get("project_name");// 项目名称
	      int projectStatus = params.get("project_status") == null ? 0 : (Integer) params.get("project_status");// 项目状态
	      int recStatus = params.get("rec_status") == null ? 0 : (Integer) params.get("rec_status");// 收费到帐状态
	      int applyHandleStatus = params.get("apply_handle_status") == null ? 0 : (Integer) params.get("apply_handle_status");// 申请办理状态
	      if (userId < 1) {
	         returnJsonOfMobile(response, false, "参数不合法", null);
	         return;
	      }
	      // 查询用户信息
	      SysUserService.Client sysUserService = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");
	      SysUser sysUser = sysUserService.getSysUserByPid(userId);
	      // 查询业务办理列表
	      ApplyHandleIndexDTO query = new ApplyHandleIndexDTO();
	      query.setPid(bizHandleId);
	      query.setProjectId(projectId);
	      query.setProjectNumber(projectNumber);
	      query.setProjectName(projectName);
	      query.setProjectStatus(projectStatus);
	      query.setRecStatus(recStatus);
	      query.setApplyHandleStatus(applyHandleStatus);
	      query.setTaskUserName(sysUser.getUserName());
	      // query.setTaskName(sysUser.getUserName());
	      query.setUserIds(getDataUserIds(sysUser));
	      Client client = (Client) getService(BusinessModule.MODUEL_INLOAN, "BizHandleService");
	      //
	      List<HandleDynamicMap> handleDynamicMapList = client.qeuryHandleDynamicCountMapList(query);
	      List<Map> resultList = new ArrayList<Map>();
	      for (HandleDynamicMap dto : handleDynamicMapList) {
	         Map<Object, Object> map = new HashMap<Object, Object>();
	         map.put("dynamic_name", checkString(dto.getDynamicName()));
	         map.put("count", dto.getCount());
	         resultList.add(map);
	      }
	      // 返回结果
	      resultMap.put("result_list", resultList);
	      returnJsonOfMobile(response, resultMap, true, "查询成功", null);
	      logger.info("method:qeuryHandleDynamicCountMapList;返回参数：" + resultMap);
	   }

	   /**
	    * 查询产品
	    *@author:liangyanjun
	    *@time:2016年3月25日下午4:36:19
	    *@param params
	    *@param request
	    *@param response
	    *@throws TException
	 * @throws ThriftException 
	    */
	   @RequestMapping(value = "/queryProduct", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
	   @ResponseBody
	   public void queryProduct(@RequestBody Map params, HttpServletRequest request, HttpServletResponse response) throws TException, ThriftException {
	      logger.info("method:queryProduct;入参：" + params);
	      Map resultMap = new HashMap<String, Object>();
	      int userId = checkIntValue((Integer) params.get("user_id"));// 用户id
	      if (userId < 1) {
	         returnJsonOfMobile(response, false, "参数不合法", null);
	         return;
	      }
	      // 查询用户信息
	      SysUserService.Client sysUserService = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");
	      SysUser sysUser = sysUserService.getSysUserByPid(userId);
	      int orgId = sysUser.getOrgId();
	      // 查询产品
	      ProductService.Client productServiceClient = (ProductService.Client) getService(BusinessModule.MODUEL_PRODUCT, "ProductService");
	      Product productQuery = new Product();
	      productQuery.setCityIds(getCityLists(orgId));// 设置城市的ID
	      productQuery.setStatus(1);// 有效的产品
	      productQuery.setPage(1);
	      productQuery.setRows(1000);
	      List<GridViewDTO> productList = productServiceClient.getAllProduct(productQuery);
	      List<Map> resultList = new ArrayList<Map>();
	      for (GridViewDTO gridViewDTO : productList) {
	         Map<Object, Object> map = new HashMap<Object, Object>();
	         map.put("pid", Integer.parseInt(gridViewDTO.getPid()));
	         map.put("product_num", checkString(gridViewDTO.getValue1()));
	         map.put("product_type", Integer.parseInt(gridViewDTO.getValue8()));
	         map.put("product_type_name", checkString(gridViewDTO.getValue2()));
	         map.put("product_name", checkString(gridViewDTO.getValue3()));
	         map.put("loan_work_process_id", checkString(gridViewDTO.getValue7()));
	         resultList.add(map);
	      }
	      resultMap.put("result_list", resultList);
	      
	      // 查询客户列表信息
	      CusPerBaseDTO query = new CusPerBaseDTO();
	      query.setUserIds(getDataUserIds(sysUser));
	      query.setPage(1);
	      query.setRows(1000);
	      CusPerService.Client client = (CusPerService.Client) getFactory(BusinessModule.MODUEL_CUSTOMER, "CusPerService").getClient();
	      List<GridViewDTO> cusPers = client.getCusPerBases(query);
	      List<Map> customerList = new ArrayList<Map>();
	      for(GridViewDTO dto : cusPers){
	   	   Map<String, Object> customerMap = new HashMap<String, Object>();
	   	   customerMap.put("pid", stringToInt(dto.getPid()));
	   	   customerMap.put("customer_name", dto.getValue2());
	   	   customerMap.put("phone_num", dto.getValue11());
	   	   customerMap.put("card_no", dto.getValue5());
	   	   customerMap.put("comm_address", dto.getValue13());
	   	   
	   	   List<Map> relationList = new ArrayList<Map>();
		   //关系人
		   List<CusDTO> relations = client.getNoSpouseList(stringToInt(dto.getPid()));
		   if(relations != null && relations.size()>0){
			   for(CusDTO relation : relations){
				   if(relation.getRelationVal() >2){
					   Map<Object, Object> relationmap= new LinkedHashMap<Object, Object>();
					   relationmap.put("relation_id",checkIntValue(relation.getPid()));
					   relationmap.put("relation_name",checkString(relation.getChinaName()));
					   relationmap.put("relation_type",checkIntValue(relation.getRelationVal()));
					   relationmap.put("relation_card_no",checkString(relation.getCertNumber()));
					   relationmap.put("relation_phone_num",checkString(relation.getPerTelephone()));
					   //relationmap.put("relation_address",checkString(relation.getCommAddr()));
					   relationmap.put("proportion_property",relation.getProportionProperty());
					   relationList.add(relationmap);
				   }
			   }
		   }
		   customerMap.put("relation_map", relationList);
	   	   customerList.add(customerMap);
	      }
	      resultMap.put("customerList", customerList);
	      
	      returnJsonOfMobile(response, resultMap, true, "查询成功", null);
	      logger.info("method:queryProduct;返回结果：" + resultMap);
	   }

	   /**
	    * 查询办理动态
	    *@author:liangyanjun
	    *@time:2016年3月28日上午9:53:10
	    *@param params
	    *@param request
	    *@param response
	    *@throws TException
	    */
	   @RequestMapping(value = "/queryBizHandleDynamic", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
	   @ResponseBody
	   public void queryBizHandleDynamic(@RequestBody Map params, HttpServletRequest request, HttpServletResponse response) throws TException {
	      logger.info("method:queryBizHandleDynamic;入参：" + params);
	      int handleId = checkIntValue((Integer) params.get("biz_handle_id"));
	      int userId = checkIntValue((Integer) params.get("user_id"));
	      // 判断id是否合法
	      if (handleId <= 0 || userId <= 0) {
	         returnJsonOfMobile(response, false, "参数不合法", null);
	         return;
	      }
	      try {
	         // 查询用户信息
	         SysUserService.Client sysUserService = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");
	         SysUser sysUser = sysUserService.getSysUserByPid(userId);
	         // 填充业务办理编辑用的数据
	         Map resultMap = new HashMap<>();
	         fillHandleDynamicData(resultMap, handleId, sysUser, request);
	         returnJsonOfMobile(response, resultMap, true, "查询成功", null);
	         logger.info("method:queryBizHandleDynamic;返回结果：" + resultMap);
	      } catch (Exception e) {
	         logger.error("查询办理数据失败.handleId=" + handleId + "错误：" + ExceptionUtil.getExceptionMessage(e));
	         returnJsonOfMobile(response, false, "出现未知异常,请与系统管理员联系!", null);
	         return;
	      }
	   }

	   /**
	    * 根据用户id，业务办理id，办理流程id获取办理动态的办理人集合
	    *@author:liangyanjun
	    *@time:2016年5月31日下午3:17:33
	    *@param params
	    *@param request
	    *@param response
	    *@throws TException
	    *@throws ThriftException
	    */
	   @RequestMapping(value = "/getHandleUserSet", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
	   public void getHandleUserSet(@RequestBody JSONObject params, HttpServletRequest request, HttpServletResponse response) throws TException, ThriftException {
	      logger.info("method:getHandleUserSet;入参：" + params);
	      Map resultMap = new HashMap<String, Object>();
	      Integer userId = params.getInteger("user_id");// 业务办理id
	      Integer handleId = params.getInteger("biz_handle_id");// 业务办理id
	      Integer handleFlowId = params.getInteger("handle_flow_id");// 办理流程id
	      
	      // 判断入参是否合法
	      if (CheckUtil.checkInteger(userId,handleId,handleFlowId)) {
	         returnJsonOfMobile(response, false, "参数不合法", null);
	         return;
	      }
	      
	      Client client = (Client) getService(BusinessModule.MODUEL_INLOAN, "BizHandleService");
	      SysUserService.Client sysUserService = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");

	      // 查询用户信息
	      SysUser user = sysUserService.getSysUserByPid(userId);
	      String userName = user.getUserName();

	      // 获取办理人set
	      Set<Map<String, String>> handleUserSet = client.getHandleUser(handleId, handleFlowId, userName);
	      // 转换key值
	      for (Map<String, String> handleUserMap : handleUserSet) {
	         handleUserMap.put("real_name", handleUserMap.get("realName"));
	         handleUserMap.remove("realName");
	         handleUserMap.remove("userName");
	      }

	      resultMap.put("handle_user_set", handleUserSet);
	      returnJsonOfMobile(response, resultMap, true, "查询成功", null);
	      logger.info("method:getHandleUserSet;返回结果：" + resultMap);
	   }
	   /**
	    * 提交办理动态
	    *@author:liangyanjun
	    *@time:2016年3月28日上午10:58:53
	    *@param params
	    *@param request
	    *@param response
	    *@throws TException
	    */
	   @RequestMapping(value = "/subHandleDynamic", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
	   @ResponseBody
	   public void subHandleDynamic(@RequestBody JSONObject params, HttpServletRequest request, HttpServletResponse response) throws TException {
	      logger.info("method:subHandleDynamic;入参：" + params);
	      int userId = params.getInteger("user_id");
	      int handleId =params.getInteger("biz_handle_id");//业务办理id
	      int handleDynamicId = params.getInteger("handle_dynamic_id");//办理动态id
	      int handleUserId = params.getInteger("handle_user_id");//办理人id
	      String finishDate =params.getString("finish_date");//完成日期
	      String remark = params.getString("remark");
	      // 检查参数是否合法
	      if (CheckUtil.checkInteger(userId,handleId,handleDynamicId,handleUserId)|| StringUtil.isBlank(finishDate)) {
	         returnJsonOfMobile(response, false, "参数不合法", null);
	         return;
	      }
	      try {
	         Client client = (Client) getService(BusinessModule.MODUEL_INLOAN, "BizHandleService");
	         // 检查办理动态是否可以更新（申请办理状态为已完成和已归档，不可修改）
	         if (!isUpdate(handleId)) {
	            returnJsonOfMobile(response, false, "提交失败,申请办理状态为已完成和已归档，不可修!", null);
	            return;
	         }
	         // 查询要修改的办理动态
	         HandleDynamicDTO handleDynamicQuery = new HandleDynamicDTO();
	         handleDynamicQuery.setPid(handleDynamicId);
	         // handleDynamicQuery.setUserIds(getUserIds(request));
	         List<HandleDynamicDTO> handleDynamicList = client.findAllHandleDynamic(handleDynamicQuery);
	         if (handleDynamicList == null || handleDynamicList.isEmpty()) {
	            logger.error("修改的办理动态数据不存在：", handleDynamicId);
	            returnJsonOfMobile(response, false, "提交失败,请重新操作!", null);
	            return;
	         }
	         HandleDynamicDTO updateHandleDynamicDTO = handleDynamicList.get(0);
	         // 检查当前用户是否有权限处理该办理动态
	         // 查询用户信息
	         SysUserService.Client sysUserService = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");
	         SysUser user = sysUserService.getSysUserByPid(userId);
	         List<Integer> canHandleFlowIdList = client.getCanHandleFlowByHandleId(handleId, user.getUserName());
	         int handleFlowId = updateHandleDynamicDTO.getHandleFlowId();
	         if (!canHandleFlowIdList.contains(handleFlowId)) {
	            logger.error("修改办理动态信息失败，原因：：修改作者ID:" + user.getPid() + "无权限修改该办理动态.入参：" + params);
	            returnJsonOfMobile(response, false, "提交失败,无权限修改!", null);
	            return;
	         }
	         int projectId = client.getProjectIdByHandleId(handleId);
	         HouseBalanceDTO houseBalanceQuery=new HouseBalanceDTO();
	         houseBalanceQuery.setHandleId(handleId);
	         houseBalanceQuery.setBalanceConfirm(Constants.NO_BALANCE_CONFIRM);
	         int houseBalanceTotal = client.getHouseBalanceTotal(houseBalanceQuery);
	         //检查赎楼余额是否已确认
	         if(handleFlowId==Constants.HANDLE_FLOW_ID_2&&houseBalanceTotal>1){
	            returnJsonOfMobile(response, false, "提交失败：该项目赎楼余额未确认，不能完成赎楼办理节点!", null);
	            return;
	            //如果是办理回款节点，则检查是否已回款和逾期费是否确认
	         }
	         // 设置修改的值
	         updateHandleDynamicDTO.setCurrentHandleUserId(handleUserId);
	         updateHandleDynamicDTO.setFinishDate(finishDate);
	         updateHandleDynamicDTO.setOperator(user.getRealName());
	         updateHandleDynamicDTO.setRemark(remark);
	         // 操作天数：操作天数=创建时间-当前时间
	         String createDate = updateHandleDynamicDTO.getCreateDate();
	         int dayDifference = DateUtils.dayDifference(DateUtils.stringToDate(createDate), new Date());
	         updateHandleDynamicDTO.setHandleDay(dayDifference);
	         List<HandleFlowDTO> handleFlowList = client.findAllHandleFlow(new HandleFlowDTO());
	         // 计算差异：1.差异=规定天数-（当前时间-开始时间）>0=0
	         // 2.差异=规定天数-（当前时间-开始时间）<0=规定天数-（当前时间-开始时间）
	         HandleFlowDTO flowDTO = getHandleFlow(handleFlowList, handleFlowId);
	         int differ = calculateDiffer(dayDifference, flowDTO);
	         updateHandleDynamicDTO.setDiffer(differ);

	         // 执行更新并完成任务
	         client.updateHandleDynamicAndFinishTask(updateHandleDynamicDTO, user.getUserName());
	         //往业务动态插入一条记录
	         SysUser currentHandleUser = sysUserService.getSysUserByPid(handleUserId);
	         String bizDynamicRemark="完成日期:"+finishDate+",操作天数:"+dayDifference+",办理人:"+currentHandleUser.getRealName()+",规定天数:"+flowDTO.getFixDay()+",差异:"+differ+",备注:"+remark;
	         if (handleFlowId==Constants.HANDLE_FLOW_ID_3) {
	            finishBizDynamicByInloan(userId,projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_10, bizDynamicRemark);
	         }else if(handleFlowId==Constants.HANDLE_FLOW_ID_4){
	            finishBizDynamicByInloan(userId,projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_11, bizDynamicRemark);
	         }else if(handleFlowId==Constants.HANDLE_FLOW_ID_5){
	            finishBizDynamicByInloan(userId,projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_12, bizDynamicRemark);
	         }else if(handleFlowId==Constants.HANDLE_FLOW_ID_6){
	            finishBizDynamicByInloan(userId,projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_13, bizDynamicRemark);
	         }else if(handleFlowId==Constants.HANDLE_FLOW_ID_7){
	            finishBizDynamicByInloan(userId,projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_14, bizDynamicRemark);
	         }else if(handleFlowId==Constants.HANDLE_FLOW_ID_10){
	            finishBizDynamicByInloan(userId,projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_15, bizDynamicRemark);
	         }
	         recordLogByMobile(BusinessModule.MODUEL_INLOAN, SysLogTypeConstant.LOG_TYPE_BIZ_HANDLE,user.getPid(),
	                 " 修改" + flowDTO.getName() + "-完成日期    更改为：" + updateHandleDynamicDTO.getFinishDate() + ",-操作天数    更改为：" + updateHandleDynamicDTO.getHandleDay()
	                       + ",-操作人    更改为：" + updateHandleDynamicDTO.getOperator() + ",-差异    更改为：" + updateHandleDynamicDTO.getDiffer() + ",-备注    更改为："
	                       + updateHandleDynamicDTO.getRemark(), projectId);
	         logger.info("修改办理动态信息成功：修改作者ID:" + user.getPid() + "，参数：" + updateHandleDynamicDTO);
	      } catch (Exception e) {
	         e.printStackTrace();
	         returnJsonOfMobile(response, false, "提交失败,请重新操作!", null);
	         return;
	      }
	      returnJsonOfMobile(response, true, "提交成功", null);
	   }
   /**
    * 提交办理动态文件
    *@author:liangyanjun
    *@time:2016年3月28日上午10:58:53
    *@param params
    *@param request
    *@param response
    *@throws TException
    */
   @RequestMapping(value = "/subHandleDynamicFile", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
   @ResponseBody
   public void subHandleDynamicFile(@RequestBody Map params, HttpServletRequest request, HttpServletResponse response) throws TException {
      logger.info("method:subHandleDynamicFile;入参：" + params);
      int userId = (Integer) params.get("user_id");
      int handleDynamicId = (Integer) params.get("handle_dynamic_id");
      String fileIds = (String) params.get("file_ids");
      String remark = (String) params.get("remark");
      Map resultMap = new HashMap<>();
      // 检查参数是否合法
      if (userId <= 0 || handleDynamicId <= 0||StringUtil.isBlank(remark,fileIds)) {
         returnJsonOfMobile(response, false, "参数不合法", null);
         return;
      }
      String[] fileIdArray = fileIds.split(",");
      if (fileIdArray.length==0) {
          returnJsonOfMobile(response, false, "参数不合法", null);
          return;
      }
      Client client = (Client) getService(BusinessModule.MODUEL_INLOAN, "BizHandleService");
      HandleDynamicDTO handleDynamicDTO = client.getHandleDynamicById(handleDynamicId);
      if (handleDynamicDTO == null || handleDynamicDTO.getPid() < 1) {
         returnJsonOfMobile(response, false, "数据不存在", null);
         return;
      }
      BizFileService.Client bizFileServiceClient = (BizFileService.Client) getService(BusinessModule.MODUEL_SYSTEM, "BizFileService");
      for (int i = 0; i < fileIdArray.length; i++) {
        int fileId=Integer.parseInt(fileIdArray[i]);
        BizFile bizFile = bizFileServiceClient.getBizFileById(fileId);
        if (bizFile == null) {
            continue;
        }
        HandleDynamicFileDTO handleDynamicFileDTO = new HandleDynamicFileDTO();
        handleDynamicFileDTO.setFileId(fileId);
        handleDynamicFileDTO.setHandleDynamicId(handleDynamicId);
        handleDynamicFileDTO.setRemark(remark);
        client.addHandleDynamicFileOfComm(handleDynamicFileDTO);
        bizFile.setRemark(remark);
        bizFileServiceClient.updateBizFile(bizFile);
    }
    returnJsonOfMobile(response, resultMap, true, "提交成功", null);
    logger.info("method:subHandleDynamicFile;返回结果：" + resultMap);
   }
   /**
    * 上传文件
    *@author:liangyanjun
    *@time:2016年3月29日下午4:53:54
    *@param request
    *@param response
    *@param handleDynamicFileDTO
   * @throws TException 
   * @throws IOException 
   * @throws ServletException 
    */
   @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
   public void uploadFile(Integer userId, HttpServletRequest request, HttpServletResponse response) throws TException,
         ServletException, IOException {
      logger.info("method:uploadFile;入参：" + userId);
      if (userId==null||userId<=0) {
          returnJsonOfMobile(response, false, "参数不合法", null);
          return;
      }
      Map resultMap = new HashMap<String, Object>();
      // 查询用户信息
      SysUserService.Client sysUserService = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");
      SysUser sysUser = sysUserService.getSysUserByPid(userId);
      if (sysUser == null) {
         returnJsonOfMobile(response, false, "上传失败", null);
         return;
      }
      // 保存文件
      BizFile bizFile = getFileByRequest(request, response, "", userId);
      BizFileService.Client bizFileServiceClient = (BizFileService.Client) getService(BusinessModule.MODUEL_SYSTEM, "BizFileService");
      int fileId = bizFileServiceClient.saveBizFile(bizFile);
      resultMap.put("file_id", fileId);
      resultMap.put("file_url", bizFile.getFileUrl());
      returnJsonOfMobile(response, resultMap, true, "上传成功", null);
      logger.info("method:uploadFile;返回结果：" + resultMap);
   }

	   /**
	    * 提交业务办理申请
	    *@author:liangyanjun
	    *@time:2016年3月28日下午3:15:37
	    *@param params
	    *@param request
	    *@param response
	    *@throws TException
	    */
	   @RequestMapping(value = "/subApplyHandleInfo", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
	   @ResponseBody
	   public void subApplyHandleInfo(@RequestBody Map params, HttpServletRequest request, HttpServletResponse response) throws TException {
	      logger.info("method:subApplyHandleInfo;入参：" + params);
	      // 获取参数
	      int userId = checkIntValue((int)params.get("user_id"));//
	      int handleId = checkIntValue((int)params.get("handle_id"));// 赎楼业务处理ID（关联BIZ_LOAN_HANDLE_INFO主键）
	      String contactPerson = (String) params.get("contact_person");// 联系人
	      String contactPhone = (String) params.get("contact_phone");// 联系电话
	      String handleDate = (String) params.get("handle_date");// 办理时间
	      String specialCase = (String) params.get("special_case");// 特殊情况说明
	      String remark = (String) params.get("remark");// 备注
	      // 检查数据
	      if (userId <= 0 || handleId <= 0 || StringUtil.isBlank(contactPerson, contactPhone, handleDate)) {
	         logger.error("请求数据不合法：" + params);
	         returnJsonOfMobile(response, false, "参数不合法", null);
	         return;
	      }
	      // 查询用户信息
	      SysUserService.Client sysUserService = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");
	      SysUser sysUser = sysUserService.getSysUserByPid(userId);

	      Client client = (Client) getService(BusinessModule.MODUEL_INLOAN, "BizHandleService");
	      ProjectService.Client projectService = (ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
	      // 查询业务处理信息
	      HandleInfoDTO handleInfoQuery = new HandleInfoDTO();
	      handleInfoQuery.setPid(handleId);
	      List<HandleInfoDTO> handleInfoDTOList = client.findAllHandleInfoDTO(handleInfoQuery);
	      if (handleInfoDTOList == null || handleInfoDTOList.isEmpty()) {
	         returnJsonOfMobile(response, false, "提交失败", null);
	         return;
	      }

	      // 检查申请业务处理信息是否可以更新（申请办理状态为已完成和已归档，不可修改）
	      HandleInfoDTO handleInfoDTO = handleInfoDTOList.get(0);
	      if (handleInfoDTO.getApplyHandleStatus() == Constants.APPLY_HANDLE_STATUS_3 || handleInfoDTO.getApplyHandleStatus() == Constants.APPLY_HANDLE_STATUS_4) {
	         returnJsonOfMobile(response, false, "提交失败,申请办理状态为已完成和已归档，不可修改!", null);
	         return;
	      }

	      // 查询项目信息
	      int projectId = handleInfoDTO.getProjectId();
	      Project projectInfo = projectService.getProjectInfoById(projectId);
	      int pmUserId = projectInfo.getPmUserId();
	      // 判断修改的是否为项目的业务经理
	      if (sysUser.getPid() != pmUserId) {
	         returnJsonOfMobile(response, false, "提交失败,非业务经理，不可修改!", null);
	         return;
	      }
         //检查是否已撤单
          if (projectInfo.getIsChechan() == Constants.PROJECT_IS_CHECHAN) {
            fillReturnJson(response, false, "提交失败,该项目已经撤单");
            return;
          }
	      // 查询要修改的申请业务处理信息
	      ApplyHandleInfoDTO query = new ApplyHandleInfoDTO();
	      query.setHandleId(handleId);
	      List<ApplyHandleInfoDTO> applyHandleInfoList = client.findAllApplyHandleInfo(query);
	      if (applyHandleInfoList == null || applyHandleInfoList.size() <= 0) {
	         logger.error("修改的申请业务处理信息数据不存在");
	         returnJsonOfMobile(response, false, "提交失败,请重新操作!", null);
	         return;
	      }
	      // 设置修改的值
	      ApplyHandleInfoDTO updateApplyHandleInfoDTO = applyHandleInfoList.get(0);
	      ApplyHandleInfoDTO oldApplyHandleInfoDTO = new ApplyHandleInfoDTO();
	      BeanUtils.copyProperties(updateApplyHandleInfoDTO, oldApplyHandleInfoDTO);
	      updateApplyHandleInfoDTO.setContactPerson(contactPerson);
	      updateApplyHandleInfoDTO.setContactPhone(contactPhone);
	      updateApplyHandleInfoDTO.setHandleDate(handleDate);
	      updateApplyHandleInfoDTO.setSpecialCase(specialCase);
	      updateApplyHandleInfoDTO.setRemark(remark);
	      // 执行修改
	      client.updateApplyHandleInfo(updateApplyHandleInfoDTO);
	      returnJsonOfMobile(response, true, "提交成功", null);
	      logger.info("修改申请业务处理信息成功：修改作者ID:" + sysUser.getPid() + "，参数：" + updateApplyHandleInfoDTO);
	   }

	    /** 
	     * 下载文件
	     * @author:liangyanjun
	     * @time:2016年10月14日上午11:09:58
	     * @param params
	     * @param request
	     * @param response
	     * @throws TException
	     * @throws IOException
	     * @throws ThriftException */
	    @RequestMapping(value = "/download", method = RequestMethod.POST)
	    public void download(@RequestBody JSONObject params, HttpServletRequest request, HttpServletResponse response) throws TException, IOException, ThriftException {
	        String path = (String) params.get("file_url");//
	        BaseClientFactory factory = getFactory(BusinessModule.MODUEL_SYSTEM, "BizFileService");
	        BizFileService.Client client = (BizFileService.Client) factory.getClient();
	        BizFile bizFile = client.getBizFileByUrl(path);
	        String fileName = bizFile.getFileName();
	        FileDownload.downloadLocalFile(response, request, path, fileName);
	        recordAomLog(OrgSysLogTypeConstant.LOG_TYPE_DOWNLOAN, "下载文件：" + path, request);
	        destroyFactory(factory);
	    }
	   
	   /**
	    * 查询首页退费列表
	    *@author:liangyanjun
	    *@time:2016年3月28日下午3:31:34
	    *@param params
	    *@param request
	    *@param response
	    *@throws TException
	    */
	   @RequestMapping(value = "/queryIndexRefundFee", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
	   @ResponseBody
	   public void queryIndexRefundFee(@RequestBody Map params, HttpServletRequest request, HttpServletResponse response) throws TException {
	      logger.info("method:queryIndexRefundFee;入参：" + params);
	      // 获取参数
	      int userId = checkIntValue((int)params.get("user_id"));//
	      int type = checkIntValue((int)params.get("type"));//
	      String projectName = checkString((String) params.get("project_name"));//
	      int productId = checkIntValue((Integer) params.get("product_id"));//
	      int userSource = checkIntValue((Integer) params.get("user_source"));//
	      String backFeeApplyStatus = params.get("back_fee_apply_status_list") == null ? "" : (String) params.get("back_fee_apply_status_list");//
	      int page = params.get("page") == null ? 1 : (Integer) params.get("page");
	      int rows = params.get("rows") == null ? 10 : (Integer) params.get("rows");

	      Map result = new HashMap<>();
	      RefundFeeIndexDTO refundFeeIndexDTO = new RefundFeeIndexDTO();
	      // 检查数据
	      if (userId <= 0 || type <= 0|| userSource <= 0) {
	         logger.error("请求数据不合法：" + params);
	         returnJsonOfMobile(response, false, "参数不合法", null);
	         return;
	      }
	      try {
	         // 查询用户信息
	         SysUserService.Client sysUserService = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");
	         SysUser sysUser = sysUserService.getSysUserByPid(userId);
	         // 查询退手续费列表
	         refundFeeIndexDTO.setUserIds(getDataUserIds(sysUser));
	         refundFeeIndexDTO.setType(type);
	         refundFeeIndexDTO.setProjectName(projectName);
	         refundFeeIndexDTO.setProductId(productId);
	         refundFeeIndexDTO.setBackFeeApplyHandleStatusList(StringUtil.StringToList(backFeeApplyStatus));
	         refundFeeIndexDTO.setPage(page);
	         refundFeeIndexDTO.setRows(rows);
	         RefundFeeService.Client service = (RefundFeeService.Client) getService(BusinessModule.MODUEL_INLOAN, "RefundFeeService");
	         List<RefundFeeIndexDTO> refundFeeIndexList = service.findAllRefundFeeIndex(refundFeeIndexDTO);
	         WorkflowService.Client workflowServiceClient = (WorkflowService.Client) getService(BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
	         List<Map> resultList = new ArrayList<Map>();
	         for (RefundFeeIndexDTO dto : refundFeeIndexList) {
	            Map<String, Object> refundFeeMap = new HashMap<String, Object>();
	            refundFeeMap.put("pid", dto.getPid());
	            refundFeeMap.put("project_id", dto.getProjectId());
	            refundFeeMap.put("project_name", checkString(dto.getProjectName()));
	            refundFeeMap.put("request_date", checkString(dto.getRequestDate()));
	            refundFeeMap.put("product_type", dto.getProductType());
	            refundFeeMap.put("product_name", checkString(dto.getProductName()));
	            refundFeeMap.put("loan_money", dto.getLoanMoney());
	            refundFeeMap.put("back_fee_apply_status", dto.getBackFeeApplyHandleStatus());
	            refundFeeMap.put("pm_user_id", dto.getPmUserId());
	            refundFeeMap.put("is_chechan", dto.getIsChechan());//是否已撤单（1：是，0：不是，默认为0）
	            resultList.add(refundFeeMap);
	            //移动端关于驳回的特殊处理：驳回根节点则申请状态为"驳回",否则状态为“审核中”
	            if (dto.getBackFeeApplyHandleStatus()==Constants.APPLY_REFUND_FEE_STATUS_3) {
	               String processDefinitionKey = null;
	               if (userSource==Constants.PROJECT_SOURCE_WT) {
	                   processDefinitionKey = Constants.WT_REFUND_FEE_MAP.get(new Long(type));
	               }else{
	                  processDefinitionKey = Constants.REFUND_FEE_MAP.get(new Long(type));
	               }
	               TaskVo taskVo = workflowServiceClient.getTaskVoByWPDefKeyAndRefId(processDefinitionKey, dto.getProjectId());
	               String taskDefKey=taskVo.getWorkflowTaskDefKey();
	               if (!"task_Request".equals(taskDefKey)) {
	                  refundFeeMap.put("back_fee_apply_status", Constants.APPLY_REFUND_FEE_STATUS_4);
	               }
	            }
	         }      
	         result.put("result_list", resultList);
	         returnJsonOfMobile(response, result, true, "查询成功", null);
	         logger.info("退手续费查询列表查询成功：result:" + result);
	      } catch (Exception e) {
	         returnJsonOfMobile(response, false, "出现未知异常,请与系统管理员联系!", null);
	         logger.error("获取退手续费列表失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + params);
	         e.printStackTrace();
	      }
	   }

	   /**
	    * 获取退费信息
	    *@author:liangyanjun
	    *@time:2016年3月28日下午4:52:15
	    *@param params
	    *@param request
	    *@param response
	    *@throws TException
	    */
	   @RequestMapping(value = "/getRefundFeeApplyInfo", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
	   @ResponseBody
	   public void getRefundFeeApplyInfo(@RequestBody Map params, HttpServletRequest request, HttpServletResponse response) throws TException {
	      logger.info("method:getRefundFeeApplyInfo;入参：" + params);
	      // 获取参数
	      int userId = checkIntValue((int)params.get("user_id"));//
	      int pid = checkIntValue((Integer) params.get("pid"));//
	      int projectId =checkIntValue((Integer) params.get("project_id"));//
	      int type = checkIntValue((int)params.get("type"));//
	      // 检查数据
	      if (userId <= 0 || type <= 0 || projectId <= 0) {
	         logger.error("请求数据不合法：" + params);
	         returnJsonOfMobile(response, false, "参数不合法", null);
	         return;
	      }
	      Map resultMap = new HashMap<>();
	      RefundFeeService.Client service = (RefundFeeService.Client) getService(BusinessModule.MODUEL_INLOAN, "RefundFeeService");
	      // 根据id查询退手续费信息
	      RefundFeeDTO refundFeeQuery = new RefundFeeDTO();
	      refundFeeQuery.setPid(pid);
	      refundFeeQuery.setProjectId(projectId);
	      refundFeeQuery.setType(type);
	      // refundFeeQuery.setUserIds(getUserIds(request));
	      List<RefundFeeDTO> refundRefundFeeList = service.findAllRefundFee(refundFeeQuery);
	      if (refundRefundFeeList == null || refundRefundFeeList.isEmpty()) {
	         returnJsonOfMobile(response, false, "数据不存在", null);
	         return;
	      }
	      RefundFeeDTO refundFeeDTO = refundRefundFeeList.get(0);
	      resultMap.put("pid", refundFeeDTO.getPid());
	      resultMap.put("project_id", refundFeeDTO.getProjectId());
	      resultMap.put("return_fee", refundFeeDTO.getReturnFee());
	      resultMap.put("account_name", checkString(refundFeeDTO.getRecAccountName()));
	      resultMap.put("account", checkString(refundFeeDTO.getRecAccount()));
	      resultMap.put("bank_name", checkString(refundFeeDTO.getBankName()));
	      returnJsonOfMobile(response, resultMap, true, "查询成功", null);
	      logger.info("method:getRefundFeeApplyInfo;返回结果：" + resultMap);
	   }

	   /**
	    * 提交退费
	    *@author:liangyanjun
	    *@time:2016年3月28日下午5:10:23
	    *@param params
	    *@param request
	    *@param response
	    *@throws TException
	    */
	   @RequestMapping(value = "/subApplyRefundFee", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
	   public void subApplyRefundFee(@RequestBody JSONObject params, HttpServletRequest request, HttpServletResponse response) throws TException {
	      logger.info("method:subApplyRefundFee;入参：" + params);
	      // 获取参数
	      int userId = checkIntValue((int)params.get("user_id"));//
	      int userSource = checkIntValue((Integer) params.get("user_source"));//
	      int projectId = checkIntValue((Integer) params.get("project_id"));//
	      int type = checkIntValue((int)params.get("type"));//
	      double returnFee =params.getDoubleValue("return_fee");//
	      String accountName = (String) params.get("account_name");//
	      String account = (String) params.get("account");//
	      String bankName = (String) params.get("bank_name");//
	      // 检查数据
	      if (userId <= 0 ||projectId <= 0 || type <= 0|| userSource <= 0) {
	         logger.error("请求数据不合法：" + params);
	         returnJsonOfMobile(response, false, "提交失败,请输入必填项,请重新操作!", null);
	         return;
	      }
	      if (returnFee <= 0) {
	         returnJsonOfMobile(response, false, "提交失败,退费金额要大于0,请重新操作!", null);
	         return;
	      }
	      try {
	         // 查询用户信息
	         SysUserService.Client sysUserService = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");
	         SysUser sysUser = sysUserService.getSysUserByPid(userId);
	         
	         WorkflowService.Client workflowServiceClient = (WorkflowService.Client) getService(BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
	         RefundFeeService.Client service = (RefundFeeService.Client) getService(BusinessModule.MODUEL_INLOAN, "RefundFeeService");
	         RefundFeeDTO refundFeeQuery = new RefundFeeDTO();
	         // refundFeeQuery.setUserIds(getUserIds(request));
	         refundFeeQuery.setType(type);
	         refundFeeQuery.setProjectId(projectId);
	         List<RefundFeeDTO> refundFeeList = service.findAllRefundFee(refundFeeQuery);
	         if (refundFeeList == null || refundFeeList.isEmpty()) {
	            returnJsonOfMobile(response, false, "提交失败,数据不存在!", null);
	            return;
	         }
	         RefundFeeDTO updateRefundFeeDTO = refundFeeList.get(0);
	         // 检查项目是否归档
	         int foreclosureStatus = updateRefundFeeDTO.getForeclosureStatus();
	         if (foreclosureStatus == Constants.FORECLOSURE_STATUS_13) {
	            returnJsonOfMobile(response, false, "提交失败,项目已归档，不可修改!", null);
	            return;
	         }
	         // 状态为待审核以后不可修改
	         if (updateRefundFeeDTO.getApplyStatus() > Constants.APPLY_REFUND_FEE_STATUS_3) {
	            returnJsonOfMobile(response, false, "提交失败,状态为待审核以后不可修改!", null);
	            return;
	         }
	         // 检查修改用户是否为该业务经理
	         int pmUserId = updateRefundFeeDTO.getPmUserId();
	         if (pmUserId != sysUser.getPid()) {
	            returnJsonOfMobile(response, false, "提交失败,非业务经理，不可修改!", null);
	            return;
	         }
	         //
	         if (updateRefundFeeDTO.getApplyStatus() <= Constants.APPLY_REFUND_FEE_STATUS_2) {
	            updateRefundFeeDTO.setApplyStatus(Constants.APPLY_REFUND_FEE_STATUS_4);
	         }
	         updateRefundFeeDTO.setReturnFee(returnFee);
	         updateRefundFeeDTO.setRecAccountName(accountName);
	         updateRefundFeeDTO.setBankName(bankName);
	         updateRefundFeeDTO.setRecAccount(account);
	         updateRefundFeeDTO.setUpdateId(sysUser.getPid());
	         if (updateRefundFeeDTO.getCreaterId() == 0) {
	            updateRefundFeeDTO.setCreaterId(sysUser.getPid());
	         }
	         // 执行更新
	         service.updateRefundFee(updateRefundFeeDTO);
	         
	         String processDefinitionKey = null;
	         if (Constants.PROJECT_SOURCE_WT==userSource) {
	             processDefinitionKey = Constants.WT_REFUND_FEE_MAP.get(new Long(type));
	         }else{
	            processDefinitionKey = Constants.REFUND_FEE_MAP.get(new Long(type));
	         }
	         workflowServiceClient.executeFlow(userId, projectId, projectId, processDefinitionKey, "task_Request", "");
	      } catch (Exception e) {
	         logger.error(ExceptionUtil.getExceptionMessage(e), e);
	         returnJsonOfMobile(response, false, "出现未知异常,请与系统管理员联系!", null);
	         e.printStackTrace();
	         return;
	      }
	      returnJsonOfMobile(response, true, "提交成功", null);
	   }

	   /**
	    * 提交办理动态评分
	    *@author:liangyanjun
	    *@time:2016年3月29日下午2:34:07
	    *@param params
	    *@param request
	    *@param response
	    *@throws TException
	    */
	   @RequestMapping(value = "/subBizDynamicEvaluate", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
	   @ResponseBody
	   public void subBizDynamicEvaluate(@RequestBody Map params, HttpServletRequest request, HttpServletResponse response) throws TException {
	      logger.info("method:subBizDynamicEvaluate;入参：" + params);
	      // 获取参数
	      int userId = checkIntValue((int)params.get("user_id"));//
	      int handleDynamicId = checkIntValue((int)params.get("handle_dynamic_id"));//
	      int projectId = checkIntValue((int)params.get("project_id"));//
	      int category = checkIntValue((int)params.get("category"));//
	      // 检查数据
	      if (userId <= 0 || handleDynamicId <= 0 || projectId <= 0 || category <= 0) {
	         logger.error("请求数据不合法：" + params);
	         returnJsonOfMobile(response, false, "参数不合法", null);
	         return;
	      }

	      BizDynamicEvaluateService.Client bizDynamicEvaluateServiceClient = (BizDynamicEvaluateService.Client) getService(BusinessModule.MODUEL_INLOAN,
	            "BizDynamicEvaluateService");

	      BizDynamicEvaluateInfo bizDynamicEvaluateInfo = new BizDynamicEvaluateInfo();
	      bizDynamicEvaluateInfo.setHandleDynamicId(handleDynamicId);
	      bizDynamicEvaluateInfo.setBizMangerId(userId);
	      bizDynamicEvaluateInfo.setProjectId(projectId);
	      bizDynamicEvaluateInfo.setCategory(category);
	      //
	      int checkIsEvaluate = bizDynamicEvaluateServiceClient.checkIsEvaluate(bizDynamicEvaluateInfo);
	      if (checkIsEvaluate > 0) {
	         returnJsonOfMobile(response, false, "已经评分过，不可多次评分", null);
	         return;
	      }
	      //
	      bizDynamicEvaluateServiceClient.addEvaluate(bizDynamicEvaluateInfo);
	      returnJsonOfMobile(response, true, "提交成功", null);
	   }

	   /**
	    * 检查业务处理状态是否为已完成或已归档
	    * 已完成或已归档返回false;
	    * 不存在或未申请，已申请返回true
	    *@author:liangyanjun
	    *@time:2016年1月18日下午7:40:30
	    *@param handleId
	    *@return
	    *@throws Exception
	    */
	   private boolean isUpdate(int handleId) throws Exception {
	      Client client = (Client) getService(BusinessModule.MODUEL_INLOAN, "BizHandleService");
	      // 查询业务处理信息
	      HandleInfoDTO handleInfoQuery = new HandleInfoDTO();
	      handleInfoQuery.setPid(handleId);
	      List<HandleInfoDTO> handleInfoDTOList = client.findAllHandleInfoDTO(handleInfoQuery);
	      if (handleInfoDTOList == null || handleInfoDTOList.isEmpty()) {
	         return false;
	      }
	      // 申请办理状态为已完成和已归档，不可修改
	      HandleInfoDTO handleInfoDTO = handleInfoDTOList.get(0);
	      if (handleInfoDTO.getApplyHandleStatus() == Constants.APPLY_HANDLE_STATUS_3 || handleInfoDTO.getApplyHandleStatus() == Constants.APPLY_HANDLE_STATUS_4) {
	         return false;
	      }
	      return true;
	   }

	   /**
	    * 填充业务办理编辑用的数据
	    * 包括：申请业务处理信息，赎楼及余额回转信息，办理动态数据，可以处理的办理流程条目ID集合，财务退款明细
	    *@author:liangyanjun
	    *@time:2016年1月29日下午5:40:52
	    *@param map
	    *@param handleId
	    *@param request
	    *@throws Exception
	    */
	   private void fillHandleDynamicData(Map map, Integer handleId, SysUser user, HttpServletRequest request) throws Exception {
	      Client client = (Client) getService(BusinessModule.MODUEL_INLOAN, "BizHandleService");
	      String realName = user.getRealName();

	      // .根据业务处理ID和用户名，获取可以处理的办理流程条目ID集合
	      List<Integer> canHandleFlowIdList = client.getCanHandleFlowByHandleId(handleId, user.getUserName());
	      // .填充办理流程条目数据
	      HandleFlowDTO handleFlowDTO = new HandleFlowDTO();
	      List<HandleFlowDTO> handleFlowList = client.findAllHandleFlow(handleFlowDTO);
	      // .处理当前用户可办理的流程，可以办理设置为true
	      if (canHandleFlowIdList!=null&&!canHandleFlowIdList.isEmpty()) {
	         for (int i = 0; i < handleFlowList.size(); i++) {
	            HandleFlowDTO dto = handleFlowList.get(i);
	            if (canHandleFlowIdList.contains(dto.getPid()))
	               dto.setCanHandle(true);
	         }
	      }
	      List<Map<String, Object>> handleFlowMapList = new ArrayList<Map<String, Object>>();
	      for (int i = 0; i < handleFlowList.size(); i++) {
	         Map<String, Object> handleFlowMap = new HashMap<String, Object>();
	         HandleFlowDTO dto = handleFlowList.get(i);
	         handleFlowMap.put("pid", dto.getPid());
	         handleFlowMap.put("name", checkString(dto.getName()));
	         handleFlowMap.put("fix_day", dto.getFixDay());
	         handleFlowMap.put("advance_notice_day", dto.getAdvanceNoticeDay());
	         handleFlowMap.put("one_level", dto.getOneLevel());
	         handleFlowMap.put("two_level", dto.getTwoLevel());
	         handleFlowMap.put("three_level", dto.getThreeLevel());
	         handleFlowMap.put("can_handle", dto.canHandle);
	         handleFlowMapList.add(handleFlowMap);
	      }
	      map.put("handle_flow_map_list", handleFlowMapList);

	      // .填充办理动态数据
	      int sumHandleDay = 0;// 操作总天数=所有的操作天数总和
	      HandleDynamicDTO handleDynamicDTO = new HandleDynamicDTO();
	      handleDynamicDTO.setHandleId(handleId);
	      List<HandleDynamicDTO> handleDynamicList = client.findAllHandleDynamic(handleDynamicDTO);
	      List<Map<String, Object>> handleDynamicMapList = new ArrayList<Map<String, Object>>();
	      for (int i = 0; i < handleDynamicList.size(); i++) {
	         Map<String, Object> handleDynamicMap = new HashMap<String, Object>();
	         HandleDynamicDTO dto = handleDynamicList.get(i);
	         // 正在办理的办理动态，则计算差异
	         if (canHandleFlowIdList != null && canHandleFlowIdList.contains(dto.getHandleFlowId())) {
	            int handleFlowId = dto.getHandleFlowId();
	            // 操作天数：操作天数=创建时间-当前时间
	            String createDate = dto.getCreateDate();
	            int dayDifference = DateUtils.dayDifference(DateUtils.stringToDate(createDate), new Date());
	            dto.setHandleDay(dayDifference);
	            // 计算差异：1.差异=规定天数-（当前时间-开始时间）>0=0
	            // 2.差异=规定天数-（当前时间-开始时间）<0=规定天数-（当前时间-开始时间）
	            HandleFlowDTO flowDTO = getHandleFlow(handleFlowList, handleFlowId);
	            int differ = calculateDiffer(dayDifference, flowDTO);
	            dto.setDiffer(differ);
	            dto.setOperator(realName);
	            sumHandleDay = dayDifference + sumHandleDay;
	         }
	         sumHandleDay = dto.getHandleDay() + sumHandleDay;
	         handleDynamicMap.put("pid", dto.getPid());
	         handleDynamicMap.put("handle_flow_id", dto.getHandleFlowId());
	         handleDynamicMap.put("finish_date", DateUtils.dateFormatByPattern(dto.getFinishDate(),"yyyy-MM-dd"));
	         handleDynamicMap.put("handle_day", dto.getHandleDay());
	         handleDynamicMap.put("operator", checkString(dto.getOperator()));
	         handleDynamicMap.put("differ", dto.getDiffer());
	         handleDynamicMap.put("remark", checkString(dto.getRemark()));
	         handleDynamicMap.put("handle_user_name", dto.getCurrentHandleUserName());
	         
	         // 查询评分
	         BizDynamicEvaluateService.Client bizDynamicEvaluateServiceClient = (BizDynamicEvaluateService.Client) getService(BusinessModule.MODUEL_INLOAN,
	               "BizDynamicEvaluateService");
	         BizEvaluateMap bizEvaluate = bizDynamicEvaluateServiceClient.queryBizEvaluateMap(dto.getPid());
	         Map<String, Object> bizEvaluateMap = new HashMap<String, Object>();
	         bizEvaluateMap.put("like", bizEvaluate.getLike());
	         bizEvaluateMap.put("dis_like", bizEvaluate.getDisLike());
	         handleDynamicMap.put("biz_evaluate_map", bizEvaluateMap);

	         // 查询办理动态文件
	         HandleDynamicFileDTO handleDynamicFileQuery = new HandleDynamicFileDTO();
	         handleDynamicFileQuery.setHandleDynamicId(dto.getPid());
	         List<HandleDynamicFileDTO> handleDynamicFileList = client.findAllHandleDynamicFile(handleDynamicFileQuery);
	         List<Map<String, Object>> handleDynamicFileMapList = new ArrayList<Map<String, Object>>();
	         for (HandleDynamicFileDTO handleDynamicFileDTO : handleDynamicFileList) {
	            Map<String, Object> handleDynamicFileMap = new HashMap<String, Object>();
	            handleDynamicFileMap.put("id", handleDynamicFileDTO.getPid());
	            handleDynamicFileMap.put("url", checkString(handleDynamicFileDTO.getFileUrl()));
	            handleDynamicFileMap.put("type", checkString(handleDynamicFileDTO.getFileType()));
	            handleDynamicFileMapList.add(handleDynamicFileMap);
	         }
	         handleDynamicMap.put("handle_dynamic_file_map_list", handleDynamicFileMapList);

	         handleDynamicMapList.add(handleDynamicMap);
	      }
	      map.put("sum_handle_days", sumHandleDay);// 操作总天数
	      map.put("handle_dynamic_map_list", handleDynamicMapList);
	   }
	   
	   /**
	    * 问题反馈
	    *@author:liangyanjun
	    *@time:2016年4月23日下午7:10:46
	    *@param params
	    *@param request
	    *@param response
	    * @throws TException 
	    */
	   @RequestMapping(value = "/problemFeedback", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
	   @ResponseBody
	   public void problemFeedback(@RequestBody Map params, HttpServletRequest request, HttpServletResponse response) throws TException {
	      logger.info("method:login;入参：" + params);
	      int userId = checkIntValue((int)params.get("user_id"));//
	      int problemSource = checkIntValue((int) params.get("problem_source"));//问题来源：PC=1,安卓=2,IOS=3
	      String feedbackContent = checkString((String) params.get("feedback_content"));// 反馈内容
	      if (userId <= 0 ||problemSource <= 0 ||StringUtil.isBlank(feedbackContent)) {
	         returnJsonOfMobile(response, false, "参数不合法", null);
	         return;
	      }
	      SysProblemFeedbackService.Client client = (SysProblemFeedbackService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysProblemFeedbackService");
	      ProblemFeedback problemFeedback=new ProblemFeedback();
	      problemFeedback.setProblemSource(problemSource);
	      problemFeedback.setFeedbackContent(feedbackContent);
	      problemFeedback.setCreaterId(userId);
	      client.addProblemFeedback(problemFeedback);
	      returnJsonOfMobile(response, true, "提交成功", null);
	   }
	   /**
	    *计算差异：1.差异=规定天数-（当前时间-开始时间）>0=0
	    *       2.差异=规定天数-（当前时间-开始时间）<0=规定天数-（当前时间-开始时间）
	    *@author:liangyanjun
	    *@time:2016年3月6日上午10:41:00
	    *@param dayDifference
	    *@param flowDTO
	    *@return
	    */
	   private int calculateDiffer(int dayDifference, HandleFlowDTO flowDTO) {
	      int differ = flowDTO.getFixDay() - dayDifference;
	      if (differ > 0) {
	         differ = 0;
	      } else {
	         differ = differ * -1;
	      }
	      return differ;
	   }

	   private HandleFlowDTO getHandleFlow(List<HandleFlowDTO> handleFlowList, int flowId) {
	      for (HandleFlowDTO flowDTO : handleFlowList) {
	         if (flowDTO.pid == flowId) {
	            return flowDTO;
	         }
	      }
	      return null;
	   }

	   /**
	    * 根据机构ID查出其所属城市ID
	    *@author:liangyanjun
	    *@time:2016年3月25日下午5:07:49
	    *@param orgId
	    *@return
	    */
	   private List<Integer> getCityLists(int orgId) {
	      List<Integer> cityList = new ArrayList<Integer>();
	      BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysOrgInfoService");
	      try {
	         // 根据用户ID查出其所属城市ID
	         SysOrgInfoService.Client orgService = (SysOrgInfoService.Client) clientFactory.getClient();
	         SysOrgInfo sysOrgInfo = new SysOrgInfo();
	         sysOrgInfo.setLayer(2);// 城市或者二级部门
	         sysOrgInfo.setId(orgId);// 用户所在部门ID
	         // 通过用户的部门以及需要查询的部门层次，查询部门
	         List<SysOrgInfo> orgList = orgService.listSysParentOrg(sysOrgInfo);
	         if (orgList != null) {
	            for (SysOrgInfo org : orgList) {
	               cityList.add(org.getId());
	            }
	         }
	      } catch (Exception e) {
	         logger.error("获取二级机构列表失败：" + e.getMessage());
	         e.printStackTrace();
	      }
	      return cityList;
	   }

	   /**
	    * 封装用户信息
	    *@author:liangyanjun
	    *@time:2016年3月24日下午3:42:26
	    *@param sysUser
	    *@return
	    */
	   private Map<Object, Object> fillUserInfo(SysUser sysUser) {
	      Map<Object, Object> userInfoMap = new HashMap<Object, Object>();
	      userInfoMap.put("pid", checkIntValue(sysUser.getPid()));// 用户id
	      userInfoMap.put("user_name", checkString(sysUser.getUserName()));// 用户名
	      userInfoMap.put("real_name", checkString(sysUser.getRealName()));// 用户真实姓名
	      userInfoMap.put("phone", checkString(sysUser.getPhone()));// 联系电话
	      userInfoMap.put("org_id", checkIntValue(sysUser.getOrgId()));// 机构id
	      userInfoMap.put("org_name", checkString(sysUser.getOrgName()));// 机构名称
	      userInfoMap.put("photo_url", checkString(sysUser.getPhotoUrl()));// 头像地址
	      userInfoMap.put("maill", checkString(sysUser.getMail()));// 邮箱
	      userInfoMap.put("member_id", checkString(sysUser.getMemberId()));// 工号
	      userInfoMap.put("job_title", checkString(sysUser.getJobTitle()));// 职位
	      userInfoMap.put("token", checkString(sysUser.getToken()));// token

	      // 用户拥有角色
	      List<SysRole> roles = sysUser.getRoles();
	      List<Map> list = new ArrayList<Map>();
	      for (SysRole sysRole : roles) {
	         Map<Object, Object> roleMap = new HashMap<Object, Object>();
	         roleMap.put("pid", checkIntValue(sysRole.getPid()));// 角色id
	         roleMap.put("role_name", checkString(sysRole.getRoleName()));// 角色名称
	         roleMap.put("role_code", checkString(sysRole.getRoleCode()));// 角色编码
	         list.add(roleMap);
	      }
	      userInfoMap.put("roles", list);
	      return userInfoMap;
	   }

	   /**
	    * 从请求中获取文件信息
	    *@author:liangyanjun
	    *@time:2016年3月25日上午9:48:27
	    *@param request
	    *@param response
	    *@param remark
	    *@param userId
	    *@return
	    *@throws ServletException
	    *@throws IOException
	    */
	   private BizFile getFileByRequest(HttpServletRequest request, HttpServletResponse response, String remark, int userId) throws ServletException, IOException {
	      // 文件信息BIZ_FILE
	      BizFile bizFile = new BizFile();
	      Map<String, Object> map = new HashMap<String, Object>();
	      map = FileUtil.processFileUpload(request, response, BusinessModule.MODUEL_SYSTEM, getUploadFilePath(), getFileSize(), getFileDateDirectory(), getUploadFileType());
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
	               fileName=fileName.replaceAll(" ", "");
	               String fileType = fileFullName.substring(dotLocation).substring(1);
	               bizFile.setFileType(fileType);
	               bizFile.setFileName(fileName);
	            }
	         }
	      }

	      if (bizFile.getFileSize() == 0) {
	         return bizFile;
	      }
	      // 文件信息设置值
	      String uploadDttm = DateUtil.format(new Date());
	      bizFile.setUploadDttm(uploadDttm);
	      String fileUrl = String.valueOf(map.get("path"));
	      if (fileUrl == null || "null".equalsIgnoreCase(fileUrl)) {
	         return bizFile;
	      }
	      bizFile.setFileUrl(fileUrl);
	      int uploadUserId = userId;
	      bizFile.setUploadUserId(uploadUserId);
	      bizFile.setStatus(Constants.STATUS_ENABLED);
	      bizFile.setRemark(remark);
	      return bizFile;
	   }
	   
	   /**
	    * 查询客户列表
	     * 
	     * @author: Administrator
	     * @throws ThriftException 
	     * @date: 2016年4月13日 下午7:06:17
	    */
	   @RequestMapping(value = "/queryCustomerList", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
	   @ResponseBody
	   public void queryCustomerList(@RequestBody JSONObject params, HttpServletRequest request, HttpServletResponse response) throws TException, ThriftException {
		   logger.info("method:queryCustomerList;入参：" + params);
		   int userId = (int) params.get("user_id");//用户ID
		   String customerName = checkString((String) params.get("customer_name"));
		   int page = params.get("page") == null ? 1 : (Integer) params.get("page");
		   int rows = params.get("rows") == null ? 10 : (Integer) params.get("rows");
		   
		   if (userId <= 0) {
		         logger.error("请求数据不合法：" + params);
		         returnJsonOfMobile(response, false, "参数不合法", null);
		         return;
		      }
		   Map result = new HashMap<>();
		   // 查询用户信息
		   SysUserService.Client sysUserService = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");
	       SysUser sysUser = sysUserService.getSysUserByPid(userId);
	       CusPerBaseDTO query = new CusPerBaseDTO();
	       query.setUserIds(getDataUserIds(sysUser));
	       query.setCusName(customerName);
	       query.setPage(page);
	       query.setRows(rows);
	       CusPerService.Client client = (CusPerService.Client) getFactory(BusinessModule.MODUEL_CUSTOMER, "CusPerService").getClient();
	       List<GridViewDTO> cusPers = client.getCusPerBases(query);
	       List<Map> resultList = new ArrayList<Map>();
	       for(GridViewDTO dto : cusPers){
	    	   Map<String, Object> customerMap = new HashMap<String, Object>();
	    	   customerMap.put("pid", stringToInt(dto.getPid()));
	    	   customerMap.put("customer_name", dto.getValue2());
	    	   customerMap.put("phone_num", dto.getValue11());
	    	   customerMap.put("card_no", dto.getValue5());
	    	   customerMap.put("picture_url", dto.getValue14());
	    	   customerMap.put("comm_address", dto.getValue13());
	    	   resultList.add(customerMap);
	       }
	       result.put("result_list", resultList);
	       returnJsonOfMobile(response, result, true, "查询成功", null);
	       logger.info("客户列表查询成功：result:" + result);
	   }
	   
	   /**
	    * 查询客户详细信息
	     * @param params
	     * @param request
	     * @param response
	     * @throws TException
	     * @throws ThriftException
	     * @author: Administrator
	     * @date: 2016年4月15日 下午2:28:45
	    */
	   @RequestMapping(value = "/queryCustomerInfoByCid", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
	   @ResponseBody
	   public void queryCustomerInfoByCid(@RequestBody JSONObject params, HttpServletRequest request, HttpServletResponse response) throws TException, ThriftException {
		   logger.info("method:queryCustomerInfoByCid;入参：" + params);
		   int userId = (Integer) params.get("user_id");//用户ID
		   int customerId = checkIntValue((Integer)params.get("customer_id"));//客户ID
		   CusAcctService.Client client = (CusAcctService.Client) getFactory(BusinessModule.MODUEL_CUSTOMER, "CusAcctService").getClient();
		   CusPerService.Client perClient = (CusPerService.Client) getFactory(BusinessModule.MODUEL_CUSTOMER, "CusPerService").getClient();
		   getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService").getClient();
		   if(userId <=0 || customerId <= 0){
			   logger.error("请求数据不合法：" + params);
			   returnJsonOfMobile(response, false, "参数不合法", null);
			   return;
		   }
		   int perId = perClient.selectPerIdByAcctId(customerId);
		   CusPerBaseDTO cusPerBaseDTO = perClient.getCusPerBase(perId);
		   CusAcct acct = cusPerBaseDTO.getCusAcct();
		   CusPerson cusPerson = cusPerBaseDTO.getCusPerson();
		   CusPerBase perBase = cusPerBaseDTO.getCusPerBase();
		   CusPerSocSec socSec = cusPerBaseDTO.getCusPerSocSec();
		   Map<Object, Object> resultMap = new LinkedHashMap<Object, Object>();
		   resultMap.put("customer_id", acct.getPid());
		   //头像地址
		   if(perBase != null && !StringUtil.isBlank(perBase.getPictureUrl())){
			   resultMap.put("file_url", perBase.getPictureUrl());
		   }else{
			   resultMap.put("file_url", "");
		   }
		   //工作信息，基本信息
		   if(cusPerson == null){
			   cusPerson = new CusPerson();
		   }
		   resultMap.put("customer_name", checkString(cusPerson.getChinaName()));
		   if(cusPerson.getCertType() == 13088){
			   resultMap.put("card_no", checkString(cusPerson.getCertNumber()));
		   }else{
			   resultMap.put("card_no", "");
		   }
		   resultMap.put("phone_num", checkString(cusPerson.getTelephone()));
		   resultMap.put("comm_address", checkString(cusPerson.getLiveAddr()));
		   resultMap.put("degree",checkIntValue(cusPerson.getEducation()));
		   resultMap.put("work_unit",checkString(cusPerson.getWorkUnit()));
		   resultMap.put("unit_address",checkString(cusPerson.getUnitAddr()));
		   resultMap.put("unit_phone",checkString(cusPerson.getMobilePhone()));
		   resultMap.put("occ_name",checkIntValue(cusPerson.getOccName()));
		   resultMap.put("month_income",checkDoubleValue(cusPerson.getMonthIncome()));
		   resultMap.put("pay_way",checkIntValue(cusPerson.getPayWay()));
		   resultMap.put("entry_time",checkString(cusPerson.getEntryTime()));
		   resultMap.put("month_pay_day",checkIntValue(cusPerson.getMonthPayDay()));
		   
		   //社保信息
		   if(socSec == null){
			   socSec = new CusPerSocSec();
		   }
		   resultMap.put("safe_unit",checkString(socSec.getSafeUnit()));
		   resultMap.put("safe_time",checkString(socSec.getSafeTime()));
		   resultMap.put("safe_num",formatDecimal(checkDoubleValue(socSec.getSafeNum())));
		   resultMap.put("total_safe_time",checkIntValue(socSec.getTotalSafeTime()));
		   resultMap.put("med_money",formatDecimal(checkDoubleValue(socSec.getMedMoney())));
		   resultMap.put("pen_money",formatDecimal(checkDoubleValue(socSec.getPenMoney())));
		   resultMap.put("suspend",checkIntValue(socSec.getSuspend()));
		   //家庭信息
		   CusPerFamilyDTO cusPerFamilyDTO = perClient.getCusPerFamily(perId, customerId);
		   CusPerFamily cusPerFamily = null ;
		   CusPerFamilyFinance cusPerFamilyFinance = null;
		   CusPerson spousePerson = null;
		   if(cusPerFamilyDTO != null){
			   cusPerFamily = cusPerFamilyDTO.getCusPerFamily();
			   cusPerFamilyFinance = cusPerFamilyDTO.getCusPerFamilyFinance();
			   spousePerson = cusPerFamilyDTO.getCusPerson();
		   }
		 //住宅信息
		   if(cusPerFamily == null){
			   cusPerFamily = new CusPerFamily();
		   }
		   //家庭资产
		   if(cusPerFamilyFinance == null){
			   cusPerFamilyFinance = new CusPerFamilyFinance();
			}
		   //配偶信息
		   if(spousePerson == null){
			   spousePerson = new CusPerson();
			}
		   resultMap.put("house_main",checkIntValue(cusPerFamily.getHouseMain()));
		   resultMap.put("house_area",formatDecimal(checkDoubleValue(cusPerFamily.getHouseArea())));
		   resultMap.put("house_shape",checkIntValue(cusPerFamily.getHouseShape()));
		   resultMap.put("live_status",checkIntValue(cusPerFamily.getLiveStatus()));
		   
		   resultMap.put("total_assets",formatDecimal(checkDoubleValue(cusPerFamilyFinance.getTotalAssets())));
		   resultMap.put("total_liab",formatDecimal(checkDoubleValue(cusPerFamilyFinance.getTotalLiab())));
		   resultMap.put("month_wage",formatDecimal(checkDoubleValue(cusPerFamilyFinance.getMonthWage())));
		   resultMap.put("family_income",formatDecimal(checkDoubleValue(cusPerFamilyFinance.getFamilyIncome())));
		   
		   resultMap.put("spouse_name",checkString(spousePerson.getChinaName()));
		   resultMap.put("spouse_card_no",checkString(spousePerson.getCertNumber()));
		   resultMap.put("spouse_phone",checkString(spousePerson.getTelephone()));	   
		   
		   //银行开户信息
		   CusAcct cusAcct = new CusAcct();
		   cusAcct.setPid(customerId);
		   CusAcctBank bank = new CusAcctBank();
		   bank.setCusAcct(cusAcct);
		   bank.setRows(10);
		   bank.setPage(1);
		   bank.setCusType(1);
		   List<GridViewDTO> list = client.getCusAcctBanks(bank);
		   GridViewDTO acctbank= null;
		   if(list != null && list.size()>0){
			   acctbank = list.get(0);
		   }
		   if(acctbank == null ){
			   acctbank = new GridViewDTO();
		   }
		   resultMap.put("bank_name",checkString(acctbank.getValue1()));
		   resultMap.put("acc_type",stringToInt(acctbank.getValue12()));
		   resultMap.put("acc_use",stringToInt(acctbank.getValue13()));
		   resultMap.put("acc_name",checkString(acctbank.getValue4()));
		   resultMap.put("loan_card_id",checkString(acctbank.getValue7()));
		   
		   //公司信息
	   	   CusPerBase cusPerBase = new CusPerBase();
	   	   CusAcct query = new CusAcct();
	   	   query.setPid(customerId);
		   cusPerBase.setCusAcct(query);
		   cusPerBase.setRows(10);
		   cusPerBase.setPage(1);
		   List<GridViewDTO> cusComs = perClient.getCusUnderCom(cusPerBase);
		   GridViewDTO gridViewDTO= null;
		   if(cusComs != null && cusComs.size()>0){
			   gridViewDTO = cusComs.get(0);
		   }
		   if(gridViewDTO == null){
			   gridViewDTO = new GridViewDTO();
		   }
		   resultMap.put("cpy_name",checkString(gridViewDTO.getValue1()));
		   resultMap.put("org_code",checkString(gridViewDTO.getValue3()));
		   resultMap.put("bus_lic_cert",checkString(gridViewDTO.getValue4()));
		   resultMap.put("reg_money",stringToDouble(gridViewDTO.getValue7()));
		   resultMap.put("com_telephone",checkString(gridViewDTO.getValue8()));
		   resultMap.put("legal_person",checkString(gridViewDTO.getValue6()));
		   
		   
		   List<Map> relationList = new ArrayList<Map>();
		   //关系人
		   List<CusDTO> cusPers = perClient.getNoSpouseList(customerId);
		   if(cusPers != null && cusPers.size()>0){
			   for(CusDTO relation : cusPers){
				   if(relation.getRelationVal() >2){
					   Map<Object, Object> relationmap= new LinkedHashMap<Object, Object>();
					   relationmap.put("relation_id",checkIntValue(relation.getPid()));
					   relationmap.put("relation_name",checkString(relation.getChinaName()));
					   relationmap.put("relation_type",checkIntValue(relation.getRelationVal()));
					   relationmap.put("relation_card_no",checkString(relation.getCertNumber()));
					   relationmap.put("relation_phone_num",checkString(relation.getPerTelephone()));
					   //relationmap.put("relation_address",checkString(relation.getCommAddr()));
					   relationmap.put("proportion_property",relation.getProportionProperty());
					   relationList.add(relationmap);
				   }
			   }
		   }
		   resultMap.put("relation_map", relationList);
		   query.setPid(perId);
		   cusPerBase.setCusAcct(query);
		   //征信记录
		   List<GridViewDTO> credits = perClient.getCusPerCredits(cusPerBase);
		   GridViewDTO creditDTO= null;
		   if(credits != null && credits.size()>0){
			   creditDTO = credits.get(0);
		   }
		   if(creditDTO == null){
			   creditDTO = new GridViewDTO();
		   }
		   resultMap.put("credit_no",checkString(creditDTO.getValue1()));
		   resultMap.put("rep_query_date",checkString(creditDTO.getValue3()));
		   
		   returnJsonOfMobile(response, resultMap, true, "查询成功", null);
		   logger.info("method:queryCustomerInfoByCid;返回结果：" + resultMap);
	   }
	   
	   /**
	    * 保存客户基本信息
	     * @param params
	     * @param request
	     * @param response
	     * @throws TException
	     * @throws ThriftException
	     * @author: Administrator
	     * @date: 2016年4月14日 上午10:43:48
	    */
	   @RequestMapping(value = "/saveCustomerInfo", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
	   @ResponseBody
	   public void saveCustomerInfo(@RequestBody JSONObject params, HttpServletRequest request, HttpServletResponse response) throws TException, ThriftException {
		   logger.info("method:saveCustomerInfo;入参：" + params);
		   int userId = (Integer) params.get("user_id");//用户ID
		   int fileId = checkIntValue((Integer)params.get("file_id"));
		   int customerId = checkIntValue((Integer)params.get("customer_id"));//客户ID
		   String customerName = params.getString("customer_name");
		   String cardNo = params.getString("card_no");
		   String phoneNum = params.getString("phone_num");
		   String commAddr = params.getString("comm_address");
		   
		   if(userId <=0 || StringUtil.isBlank(cardNo)){
			   logger.error("请求数据不合法：" + params);
			   returnJsonOfMobile(response, false, "参数不合法", null);
			   return;
		   }
		   //校验身份证号码是否符合规则
		   if(!IDCardUtil.checkCardId(cardNo)){
			   logger.error("身份证号码不正确：" + params);
			   returnJsonOfMobile(response, false, "身份证号码不正确！", null);
			   return;
		   }
		   
		   CusPerService.Client cusClient = (CusPerService.Client) getFactory(BusinessModule.MODUEL_CUSTOMER, "CusPerService").getClient();
		   
		   CusPerson query = new CusPerson();
		   query.setCertNumber(cardNo);
		   List<CusPerson> result = cusClient.getCusPersonByNumber(query);
		   
		   if(result != null && result.size()>0){
			   //查询cus_acct表pid
			   customerId = result.get(0).getCusAcct().getPid();
		   }
		   
		   //客户基本资料
		   CusPerBaseDTO cusPerBaseDTO = new CusPerBaseDTO();
		   //客户主表
		   CusAcct acct = new CusAcct();
		   //客户人员信息表
		   CusPerson cusPerson = new CusPerson();
		   //个人客户基本信息
		   CusPerBase perBase = new CusPerBase();
		   //社保信息
		   CusPerSocSec socSec = new CusPerSocSec();

		   if(customerId >0){//修改
			   int perId = cusClient.selectPerIdByAcctId(customerId);
			   cusPerBaseDTO = cusClient.getCusPerBase(perId);
			   acct = cusPerBaseDTO.getCusAcct();
			   cusPerson = cusPerBaseDTO.getCusPerson();
			   perBase = cusPerBaseDTO.getCusPerBase();
			   socSec = cusPerBaseDTO.getCusPerSocSec();
		   }else{
			   acct.setStatus(1);
			   acct.setCusStatus(1);
			   acct.setPmUserId(userId);
			   
			   cusPerson.setCertType(13088);
			   cusPerson.setStatus(1);
			   cusPerson.setRelationType(1);//主人员
			   
			   perBase.setStatus(1);
			   socSec.setStatus(1);
		   }
		   
		   
		   acct.setCusType(1);
		   cusPerBaseDTO.setCusAcct(acct);
		   //身份证,客户人员信息
		   cusPerson.setCertNumber(cardNo);
		   cusPerson.setChinaName(customerName);
		   cusPerson.setTelephone(phoneNum);
		   cusPerson.setLiveAddr(commAddr);
		   getBirthByCardNo(cusPerson);
		   cusPerBaseDTO.setCusPerson(cusPerson);
		   cusPerBaseDTO.setCusPerSocSec(socSec);
		   // 获取文件
	      BizFileService.Client bizFileServiceClient = (BizFileService.Client) getService(BusinessModule.MODUEL_SYSTEM, "BizFileService");
	      if(fileId>0){
	    	  
	    	  BizFile bizFile = bizFileServiceClient.getBizFileById(fileId);
	    	  if (bizFile != null) {
	    		  // 获取文件url
	    		  String fileUrl = bizFile.getFileUrl();
	    		  perBase.setPictureUrl(fileUrl);
	    	  }
	      }else if(fileId == -1){
	    	  perBase.setPictureUrl("");
	      }
	      cusPerBaseDTO.setCusPerBase(perBase);
	      
	      //初始化客户关系中间信息
		  CusRelation cusRelation = new CusRelation();
		  SysUserService.Client sysUserService = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");
	      SysUser sysUser = sysUserService.getSysUserByPid(userId);
		  cusRelation.setPmUserId(userId);//登录用户的Id
		  cusRelation.setOrgType(1);
		  cusRelation.setOrgId(sysUser.getOrgId());//登录用户的二级机构
		
		  cusPerBaseDTO.setCusRelation(cusRelation);
	      
	      Map resultMap = new HashMap<>();
	      if(customerId>0){
	    	  customerId = cusClient.updateCusPerBase(cusPerBaseDTO);
	      }else{
	    	  customerId =cusClient.addCusPerBase(cusPerBaseDTO);
	      }
	      resultMap.put("customer_id", customerId);
	      returnJsonOfMobile(response, resultMap, true, "保存成功", null);
	      logger.info("method:saveCustomerInfo;返回结果：" + resultMap);
	   }
	   /**
	    * 修改客户信息（工作社保家庭信息）
	     * @param params
	     * @param request
	     * @param response
	     * @throws TException
	     * @throws ThriftException
	     * @author: Administrator
	     * @date: 2016年4月14日 下午3:28:58
	    */
	   @RequestMapping(value = "/saveCustomerFamilyInfo", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
	   @ResponseBody
	   public void saveCustomerFamilyInfo(@RequestBody JSONObject params, HttpServletRequest request, HttpServletResponse response) throws TException, ThriftException {
		   logger.info("method:saveCustomerFamilyInfo;入参：" + params);
		   int userId = checkIntValue((Integer)params.get("user_id"));//用户ID
		   int customerId = checkIntValue((Integer)params.get("customer_id"));//客户ID
		   if(userId <=0 || customerId <= 0){
			   logger.error("请求数据不合法：" + params);
			   returnJsonOfMobile(response, false, "参数不合法", null);
			   return;
		   }
		   CusPerService.Client cusClient = (CusPerService.Client) getFactory(BusinessModule.MODUEL_CUSTOMER, "CusPerService").getClient();
		   int perId = cusClient.selectPerIdByAcctId(customerId);
		   CusPerBaseDTO cusPerBaseDTO = cusClient.getCusPerBase(perId);
		   CusPerson cusPerson = cusPerBaseDTO.getCusPerson();
		   //工作信息
		   int education = checkIntValue((Integer)params.get("degree"));
		   String workUnit = checkString((String)params.get("work_unit"));
		   String unitAddress = checkString((String)params.get("unit_address"));
		   String unitPhone  = checkString((String)params.get("unit_phone"));
		   int occName = checkIntValue((Integer)params.get("occ_name"));
		   Double monthIncome = checkDoubleValue(params.getDouble("month_income"));
		   int payWay = checkIntValue((Integer)params.get("pay_way"));
		   String entryTime = checkString((String)params.get("entry_time"));
		   int monthPayDay = checkIntValue((Integer)params.get("month_pay_day"));
		  
		   if(!StringUtil.isBlank(workUnit,unitAddress)){
			   cusPerson.setEducation(education);
			   cusPerson.setWorkUnit(workUnit);
			   cusPerson.setUnitAddr(unitAddress);
			   cusPerson.setMobilePhone(unitPhone);
			   cusPerson.setOccName(occName);
			   cusPerson.setMonthIncome(monthIncome);
			   cusPerson.setPayWay(payWay);
			   cusPerson.setEntryTime(entryTime);
			   cusPerson.setMonthPayDay(monthPayDay);
			   cusPerBaseDTO.setCusPerson(cusPerson);
		  }
		   //社保信息
		   String safeUnit = checkString((String)params.get("safe_unit"));
		   String safeTime = checkString((String)params.get("safe_time"));
		   double safeNum = checkDoubleValue(params.getDouble("safe_num"));
		   int totalSafeTime = checkIntValue((Integer)params.get("total_safe_time"));
		   Double medMoney = checkDoubleValue(params.getDouble("med_money"));
		   Double penMoney = checkDoubleValue(params.getDouble("pen_money"));
		   int suspend = checkIntValue((Integer)params.get("suspend"));
		   if(!StringUtil.isBlank(safeUnit,safeTime)){
			   CusPerSocSec cusPerSocSec = cusPerBaseDTO.getCusPerSocSec();
			   cusPerSocSec.setSafeUnit(safeUnit);
			   cusPerSocSec.setSafeTime(safeTime);
			   cusPerSocSec.setSafeNum(safeNum);
			   cusPerSocSec.setTotalSafeTime(totalSafeTime);
			   cusPerSocSec.setMedMoney(medMoney);
			   cusPerSocSec.setPenMoney(penMoney);
			   cusPerSocSec.setSuspend(suspend);
			   cusPerBaseDTO.setCusPerSocSec(cusPerSocSec);
		   }
	   	   //初始化客户关系中间信息
		   CusRelation cusRelation = new CusRelation();
		   SysUserService.Client sysUserService = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");
	       SysUser sysUser = sysUserService.getSysUserByPid(userId);
		   cusRelation.setPmUserId(userId);//登录用户的Id
		   cusRelation.setOrgType(1);
		   cusRelation.setOrgId(sysUser.getOrgId());//登录用户的二级机构
		
		   cusPerBaseDTO.setCusRelation(cusRelation);
		   
		   customerId = cusClient.updateCusPerBase(cusPerBaseDTO);
		   
		   //家庭情况
		   CusPerFamilyDTO cusPerFamilyDTO = cusClient.getCusPerFamily(perId, customerId);
		   CusPerFamily cusPerFamily = null; 
		   CusPerFamilyFinance cusPerFamilyFinance = null;
		   CusPerson spousePerson = null;
		   int houseMain  = checkIntValue((Integer)params.get("house_main"));
		   int liveStatus = checkIntValue((Integer)params.get("live_status"));
		   int houseShape = checkIntValue((Integer)params.get("house_shape"));
		   Double houseArea = checkDoubleValue(params.getDouble("house_area"));
		   Double totalAssets = checkDoubleValue(params.getDouble("total_assets"));
		   Double totalLiab = checkDoubleValue(params.getDouble("total_liab"));
		   Double monthWage = checkDoubleValue(params.getDouble("month_wage"));
		   Double familyIncome = checkDoubleValue(params.getDouble("family_income"));
		   String spouseName = checkString((String)params.get("spouse_name"));
		   String spouseCardNo = checkString((String)params.get("spouse_card_no"));
		   String spousePhone = checkString((String)params.get("spouse_phone"));
		   if(cusPerFamilyDTO == null){
			   //个人家庭信息
			   cusPerFamilyDTO = new CusPerFamilyDTO();
			   
			   //个人家庭经济情况
			   cusPerFamilyFinance = new CusPerFamilyFinance();
			   
			   //配偶信息
			   spousePerson = new CusPerson();
			   
		   }else{
			   cusPerFamily = cusPerFamilyDTO.getCusPerFamily();
			   cusPerFamilyFinance = cusPerFamilyDTO.getCusPerFamilyFinance();
			   spousePerson = cusPerFamilyDTO.getCusPerson();
		   }
		   
		   if(cusPerFamily == null){
			   cusPerFamily = new CusPerFamily();
		   }
		   if(cusPerFamilyFinance == null){
			   cusPerFamilyFinance = new CusPerFamilyFinance();
		   }
		   if(spousePerson == null){
			   spousePerson = new CusPerson();
		   }
		   
		   cusPerFamily.setStatus(1);
		   cusPerFamily.setCusPerBase(cusPerBaseDTO.getCusPerBase());
		   cusPerFamily.setHouseMain(houseMain);
		   cusPerFamily.setHouseArea(houseArea);
		   cusPerFamily.setHouseShape(houseShape);
		   cusPerFamily.setLiveStatus(liveStatus);
		   
		   cusPerFamilyFinance.setStatus(1);
		   CusPerBase cusPerBase = new CusPerBase();
		   
		   cusPerFamilyFinance.setCusPerBase(cusPerBase );
		   cusPerFamilyFinance.setCusPerBase(cusPerBaseDTO.getCusPerBase());
		   cusPerFamilyFinance.setTotalAssets(totalAssets);
		   cusPerFamilyFinance.setTotalLiab(totalLiab);
		   cusPerFamilyFinance.setMonthWage(monthWage);
		   cusPerFamilyFinance.setFamilyIncome(familyIncome);
		   
		   spousePerson.setStatus(1);
		   spousePerson.setRelationType(2);
		   spousePerson.setCusAcct(cusPerBaseDTO.getCusAcct());
		   spousePerson.setChinaName(spouseName);
		   spousePerson.setCertType(13088);
		   spousePerson.setCertNumber(spouseCardNo);
		   spousePerson.setTelephone(spousePhone);
		   
		   cusPerFamilyDTO.setCusPerFamily(cusPerFamily);
		   cusPerFamilyDTO.setCusPerFamilyFinance(cusPerFamilyFinance);
		   cusPerFamilyDTO.setCusPerson(spousePerson);
		   //判断请求是否有效
		   if(houseMain >0 && liveStatus>0 && houseShape>0&&houseArea>0){
			   if (cusPerFamilyDTO.getCusPerFamily().getPid() > 0) {
					cusClient.updateCusPerFamily(cusPerFamilyDTO);
				} else {
					cusClient.addCusPerFamily(cusPerFamilyDTO);
				}
		   }
		   returnJsonOfMobile(response, true, "保存成功", null);
	   }
	   /**
	    * 保存银行
	     * @param params
	     * @param request
	     * @param response
	     * @throws TException
	     * @throws ThriftException
	     * @author: Administrator
	     * @date: 2016年4月14日 下午7:39:04
	    */
	   @RequestMapping(value = "/saveCustomerBankInfo", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
	   @ResponseBody
	   public void saveCustomerBankInfo(@RequestBody JSONObject params, HttpServletRequest request, HttpServletResponse response) throws TException, ThriftException {
		   logger.info("method:saveCustomerBankInfo;入参：" + params);
		   int userId = checkIntValue((Integer)params.get("user_id"));//用户ID
		   int customerId = checkIntValue((Integer)params.get("customer_id"));//客户ID
		   if(userId <=0 || customerId <= 0){
			   logger.error("请求数据不合法：" + params);
			   returnJsonOfMobile(response, false, "参数不合法", null);
			   return;
		   }
		   CusAcctService.Client client = (CusAcctService.Client) getFactory(BusinessModule.MODUEL_CUSTOMER, "CusAcctService").getClient();
		   CusPerService.Client perClient = (CusPerService.Client) getFactory(BusinessModule.MODUEL_CUSTOMER, "CusPerService").getClient();
		   CusComService.Client comClient = (CusComService.Client) getFactory(BusinessModule.MODUEL_CUSTOMER, "CusComService").getClient();
		   int perId = perClient.selectPerIdByAcctId(customerId);
		   //开户信息
		   String bankName  = checkString((String)params.get("bank_name"));
		   int accType = checkIntValue((Integer)params.get("acc_type"));
		   int accUse = checkIntValue((Integer)params.get("acc_use"));
		   String accName = checkString((String)params.get("acc_name"));
		   String loanCardId = checkString((String)params.get("loan_card_id"));
		   //校验传输的数据
		   if(!StringUtil.isBlank(bankName) && accType>0&&accUse>0){
			   //查询银行开户信息
			   CusAcctBank bank = new CusAcctBank();
			   CusAcct cusAcct = new CusAcct();
			   cusAcct.setPid(customerId);
			   bank.setCusAcct(cusAcct);
			   bank.setRows(10);
			   bank.setPage(1);
			   bank.setCusType(1);
			   List<GridViewDTO> list = client.getCusAcctBanks(bank);
			   CusAcctBank cusAcctBank = new CusAcctBank();
			   if(list != null && list.size()>0){
				   String pid = list.get(0).getPid();
				   cusAcctBank = client.getCusAcctBank(Integer.parseInt(pid));
			   }
			   cusAcctBank.setStatus(1);
			   cusAcctBank.setCusType(1);
			   cusAcctBank.setBankName(bankName);
			   cusAcctBank.setAccType(accType);
			   cusAcctBank.setAccUse(accUse);
			   cusAcctBank.setAccName(accName);
			   cusAcctBank.setLoanCardId(loanCardId);
			   cusAcctBank.setCusAcct(cusAcct);
			   //保存银行信息
			   if(list != null && list.size()>0){
				   client.updateCusAcctBank(cusAcctBank);
			   }else{
				   client.addCusAcctBank(cusAcctBank);
			   }
		   }
		   
		   
		   //公司信息
		   String cpyName = checkString((String)params.get("cpy_name"));
		   String orgCode = checkString((String)params.get("org_code"));
		   String busLicCert = checkString((String)params.get("bus_lic_cert"));
		   Double regMoney = checkDoubleValue(params.getDouble("reg_money"));
		   String telephone = checkString((String)params.get("com_telephone"));
		   String legalPerson = checkString((String)params.get("legal_person"));
		   //校验传递的参数
		   if(!StringUtil.isBlank(cpyName,orgCode,legalPerson)){
			  
				CusPerBase cusPerBase = new CusPerBase();
				CusAcct cusAcct = new CusAcct();
				cusAcct.setPid(customerId);
				cusPerBase.setCusAcct(cusAcct);
				cusPerBase.setRows(10);
				cusPerBase.setPage(1);
				int comId = 0;
				int comOwnerId = 0;
				List<GridViewDTO> cusPers = perClient.getCusUnderCom(cusPerBase);
				CusComBaseDTO cusComBaseDTO = new CusComBaseDTO();
				CusPerson cusPerson = new CusPerson();
				CusComBase comBase = new CusComBase();
				
				if(cusPers != null && cusPers.size()>0){
					comId = Integer.parseInt(cusPers.get(0).getPid());
					cusComBaseDTO = comClient.getCusComBase(comId);
					cusPerson = client.getCusPerson(cusComBaseDTO.getCusComBase().getComOwnId());
					comBase = cusComBaseDTO.getCusComBase();
				}
				comBase.setStatus(1);
				comBase.setCusAcct(cusAcct);
				
				cusPerson.setStatus(1);
				cusPerson.setIsComOwn(1);
				
				cusPerson.setCusAcct(cusAcct);
				comBase.setCpyName(cpyName);
				comBase.setOrgCode(orgCode);
				comBase.setBusLicCert(busLicCert);
				comBase.setTelephone(telephone);
				comBase.setRegMoney(regMoney);
				cusComBaseDTO.setAcctId(customerId);
				cusComBaseDTO.setCusComBase(comBase);
				cusComBaseDTO.setCusAcct(cusAcct);
				cusComBaseDTO.setCusComShares(new ArrayList<CusComShare>());
				cusPerson.setChinaName(legalPerson);
				if (cusPerson.getPid() > 0) {
					comOwnerId = client.updateCusPerson(cusPerson);
				} else {
					comOwnerId = client.addCusPerson(cusPerson);
				}
				comBase.setComOwnId(comOwnerId);
				if(comId>0){
					comClient.updateCusComBase(cusComBaseDTO);
				}else{
					comClient.addCusComBase(cusComBaseDTO);
				}
		   }
		   
		   JSONArray relation = params.getJSONArray("relation_list");
		   if(relation != null && relation.size()>0){
			   for(int i=0;i<relation.size();i++){
				   JSONObject relationJson = relation.getJSONObject(i);
				   //关系人信息
				   int relationId = checkIntValue((Integer)relationJson.get("relation_id"));
				   String relationName = checkString((String)relationJson.get("relation_name"));
				   int relationType = checkIntValue((Integer)relationJson.get("relation_type"));
				   String cardNo = checkString((String)relationJson.get("relation_card_no"));
				   String phoneNum = checkString((String)relationJson.get("relation_phone_num"));
				   //String address = checkString((String)relationJson.get("relation_address"));
				   double proportionProperty = checkDoubleValue(relationJson.getDouble("proportion_property"));
				   if(!StringUtil.isBlank(relationName,phoneNum)&&relationType>0){
						CusPerson cusPerson = new CusPerson();
						cusPerson.setStatus(1);
						CusAcct cusAcct = new CusAcct();
						cusAcct.setPid(customerId);
						cusPerson.setCusAcct(cusAcct);
						cusPerson.setChinaName(relationName);
						cusPerson.setPid(relationId);
						cusPerson.setRelationType(relationType);
						cusPerson.setCertNumber(cardNo);
						cusPerson.setCertType(13088);
						cusPerson.setTelephone(phoneNum);
						cusPerson.setProportionProperty(proportionProperty);
						//cusPerson.setCommAddr(address);
						if (cusPerson.getPid() > 0) {
							client.updateCusPerson(cusPerson);
						} else {
							client.addCusPerson(cusPerson);
						}
				   }
				   
			   }
		   }
		   //征信信息
		   String creditNo = checkString((String)params.get("credit_no"));
		   String repQueryDate = checkString((String)params.get("rep_query_date"));
		   if(!StringUtil.isBlank(creditNo,repQueryDate)){
			   CusAcct cusAcct = new CusAcct();
			   CusPerBase cusPerBase = new CusPerBase();
			   cusAcct.setPid(perId);
				cusPerBase.setCusAcct(cusAcct);
				cusPerBase.setPage(1);
				cusPerBase.setRows(10);
				CusPerCreditDTO cusPerCreditDTO = new CusPerCreditDTO();
				CusPerCredit cusPerCredit = new CusPerCredit();
				List<GridViewDTO> cusPers = perClient.getCusPerCredits(cusPerBase);
				int pid = 0;
				if(cusPers != null && cusPers.size()>0){
					pid = Integer.parseInt(cusPers.get(0).getPid());
					cusPerCreditDTO = perClient.getCusPerCredit(pid);
					cusPerCredit = cusPerCreditDTO.getCusPerCredit();
				}
				cusPerCredit.setStatus(1);
				cusPerBase.setPid(perId);
				cusPerCredit.setPid(pid);
				cusPerCredit.setCreNo(creditNo);
				cusPerCredit.setCusPerBase(cusPerBase);
				cusPerCredit.setRepQueryDate(repQueryDate);
				cusPerCreditDTO.setCusPerCredit(cusPerCredit);
				if (cusPerCreditDTO.getCusPerCredit().getPid() > 0) {
					perClient.updateCusPerCredit(cusPerCreditDTO);
				} else {
					perClient.addCusPerCredit(cusPerCreditDTO);
				}
		   }
		   
		   returnJsonOfMobile(response, true, "保存成功", null);
	   }
	   
	   /**
	    * 保存项目附加信息，除第一页信息外
	     * 
	     * @author: Administrator
	     * @date: 2016年4月19日 上午9:48:37
	    */
	   @RequestMapping(value = "/saveProjectInfo", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
	   @ResponseBody
		public void saveProjectInfo(@RequestBody JSONObject params,
				HttpServletRequest request, HttpServletResponse response)
				throws TException, ThriftException {
			logger.info("method:saveProjectInfo;入参：" + params);
			int userId = checkIntValue((Integer) params.get("user_id"));// 用户ID
			int projectId = checkIntValue((Integer) params.get("project_id"));//项目ID
			ProjectService.Client projectService = (ProjectService.Client) getService(
					BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			
			if (userId <= 0 || projectId <=0) {
				logger.error("请求数据不合法：" + params);
				returnJsonOfMobile(response, false, "参数不合法", null);
				return;
			}

			Project project = projectService.getLoanProjectByProjectId(projectId);
			if(project == null || project.getPid() <=0){
				logger.error("请求数据不合法：" + params);
				returnJsonOfMobile(response, false, "参数不合法", null);
				return;
			}
			//判断项目状态
			if(project.getForeclosureStatus() >1){
				logger.error("请求数据不合法：" + params);
				returnJsonOfMobile(response, false, "项目审批中无法修改", null);
				return;
			}
			
			// 解析APP传递的参数，组装成project对象，修改项目信息
			project = parseProjectJson(params, project);
			
			//保存项目信息
			projectId = projectService.saveProjectInfoByMobile(project, params.toJSONString());
			
			Map<Object, Object> resultMap = new LinkedHashMap<Object, Object>();
			resultMap.put("project_id", projectId);
			returnJsonOfMobile(response, resultMap, true, "保存成功", null);
			logger.info("method:saveProjectInfo;返回结果：" + resultMap);
		}

		/**
		 * 提交审核项目信息
		 * 
		 * @author: Administrator
		 * @date: 2016年4月19日 上午9:48:52
		 */
		@RequestMapping(value = "/applyProjectInfo", method = RequestMethod.POST, headers = {"Content-Type=application/json;charset=utf-8","Accept-Version=api_v1" })
		@ResponseBody
		public void applyProjectInfo(@RequestBody JSONObject params,HttpServletRequest request, HttpServletResponse response)throws TException, ThriftException {
			logger.info("method:applyProjectInfo;入参：" + params);
			int userId = checkIntValue((Integer) params.get("user_id"));// 用户ID
			int projectId = checkIntValue((Integer) params.get("project_id"));//业务申请IDq
			
			ProjectService.Client projectService = (ProjectService.Client) getService(
					BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			CusPerService.Client cusClient = (CusPerService.Client) getFactory(
					BusinessModule.MODUEL_CUSTOMER, "CusPerService").getClient();
			ProductService.Client productService = (ProductService.Client) getFactory(
					BusinessModule.MODUEL_PRODUCT, "ProductService").getClient();
			
			if (userId <= 0 || projectId <= 0) {
				logger.error("请求数据不合法：" + params);
				returnJsonOfMobile(response, false, "参数不合法", null);
				return;
			}
			
			Project project = projectService.getLoanProjectByProjectId(projectId);
			if(project == null || project.getPid() <=0){
				logger.error("请求数据不合法：" + params);
				returnJsonOfMobile(response, false, "参数不合法", null);
				return;
			}
			
			// 根据产品信息查询流程ID
			Product product = productService.getProductById(project.getProductId());
			if (product == null) {
				logger.error("请求数据不合法：" + params);
				returnJsonOfMobile(response, false, "产品信息不存在", null);
				return;
			}
			
			// 解析APP传递的参数，组装成project对象，修改项目信息
			project = parseProjectJson(params, project);
			//判断项目状态
			if(project.getForeclosureStatus() >1){
				logger.error("请求数据不合法：" + params);
				returnJsonOfMobile(response, false, "项目审批中无法修改", null);
				return;
			}
			//保存项目信息
			projectId = projectService.saveProjectInfoByMobile(project, params.toJSONString());
			//保存成功后才可以提交审核
			if(projectId >0){
				// 提交审批
				WorkflowService.Client workClient = (WorkflowService.Client) getFactory(
						BusinessModule.MODUEL_WORKFLOW, "WorkflowService").getClient();
				workClient.executeFlow(userId, projectId, projectId,product.getLoanWorkProcessId(),"task_LoanRequest","");
			}

			Map<Object, Object> resultMap = new LinkedHashMap<Object, Object>();
			resultMap.put("project_id", projectId);
			returnJsonOfMobile(response, resultMap, true, "提交成功", null);
			logger.info("method:applyProjectInfo;返回结果：" + resultMap);
		}

		/**
		 * 查询项目详细信息
		 * 
		 * @param params
		 * @param request
		 * @param response
		 * @throws TException
		 * @throws ThriftException
		 * @author: Administrator
		 * @date: 2016年4月19日 下午5:50:47
		 */
		@RequestMapping(value = "/queryProjectByPid", method = RequestMethod.POST, headers = {"Content-Type=application/json;charset=utf-8","Accept-Version=api_v1" })
		@ResponseBody
		public void queryProjectByPid(@RequestBody JSONObject params,HttpServletRequest request, HttpServletResponse response)throws TException, ThriftException {
			logger.info("method:queryProjectByPid;入参：" + params);
			int userId = checkIntValue((Integer) params.get("user_id"));// 用户ID
			int projectId = checkIntValue((Integer) params.get("project_id"));// 项目ID
			if (userId <= 0 || projectId <= 0) {
				logger.error("请求数据不合法：" + params);
				returnJsonOfMobile(response, false, "参数不合法", null);
				return;
			}
			ProjectService.Client projectService = (ProjectService.Client) getService(
					BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			Project project = projectService.getLoanProjectByProjectId(projectId);
			if (project == null) {
				logger.error("请求的项目数据不存在：" + params);
				returnJsonOfMobile(response, false, "项目不存在", null);
				return;
			}
			
			ProjectSurveyReportService.Client projectSurveyReportService = (ProjectSurveyReportService.Client) getService(
					BusinessModule.MODUEL_BEFORELOAN, "ProjectSurveyReportService");;
			ProjectSurveyReport surveyReport= projectSurveyReportService.getSurveyReportByProjectId(projectId);
			
			ProjectForeclosure foreclosure = project.getProjectForeclosure();
			ProjectGuarantee guarantee = project.getProjectGuarantee();
			ProjectProperty property = project.getProjectProperty();
			// 查询客户信息
			CusPerService.Client perClient = (CusPerService.Client) getFactory(
					BusinessModule.MODUEL_CUSTOMER, "CusPerService").getClient();
			CusDTO cusBase = perClient.getPersonalListKeyPid(project.getPid());

			if (foreclosure == null) {
				foreclosure = new ProjectForeclosure();
			}
			if (guarantee == null) {
				guarantee = new ProjectGuarantee();
			}
			if (property == null) {
				property = new ProjectProperty();
			}
			Map<Object, Object> resultMap = new LinkedHashMap<Object, Object>();
			resultMap.put("project_id", checkIntValue(project.getPid()));
			resultMap.put("product_id", project.getProductId());
			
			resultMap.put("business_source",
					checkIntValue(project.getBusinessSource()));
			resultMap.put("fore_status", project.getForeclosureStatus());
			resultMap.put("business_source_no", 
					checkIntValue(project.getBusinessSourceNo()));
			resultMap.put("business_source_str", project.getSourceStr());
			
			resultMap.put("other_source", checkString(project.getMyMainText()));
			resultMap.put("address", checkString(project.getAddress()));
			
			resultMap.put("declaration", checkString(project.getDeclaration()));//新增的报单员
			String specialDesc = "";
			if(surveyReport != null){
				specialDesc = surveyReport.getSpecialDesc();
			}
			resultMap.put("special_desc", checkString(specialDesc));//新增的报单员
			
			// 业务信息
			resultMap.put("loan_money",
					formatDecimal(checkDoubleValue(guarantee.getLoanMoney())));
			resultMap.put("loan_days", checkIntValue(foreclosure.getLoanDays()));
			resultMap.put("rece_date", checkString(foreclosure.getReceDate()));
			resultMap.put("payment_account",
					checkString(foreclosure.getPaymentAccount()));
			resultMap
					.put("payment_name", checkString(foreclosure.getPaymentName()));
			resultMap.put("business_contacts",
					checkString(project.getBusinessContacts()));
			resultMap
					.put("contacts_phone", checkString(project.getContactsPhone()));
			resultMap.put("inner_or_out", checkIntValue(project.getInnerOrOut()));
			resultMap.put("business_category",
					checkIntValue(project.getBusinessCategory()));
			// 申请信息
			resultMap.put("customer_id", checkIntValue(cusBase.getAcctId()));
			resultMap.put("customer_name", checkString(cusBase.getChinaName()));
			resultMap.put("cert_num", checkString(cusBase.getCertNumber()));
			resultMap.put("customer_phone", checkString(cusBase.getPerTelephone()));
			resultMap.put("customer_address", checkString(cusBase.getLiveAddr()));
			//共同借款人
			List<CusDTO> list = perClient.getNoSpouseLists(projectId);
			List<Map> relationList = new ArrayList<Map>();
			if(list != null && list.size()>0){
				   for(CusDTO relation : list){
					   Map<Object, Object> relationmap= new LinkedHashMap<Object, Object>();
					   relationmap.put("relation_id",checkIntValue(relation.getPid()));
					   relationmap.put("relation_name",checkString(relation.getChinaName()));
					   relationmap.put("relation_type",checkIntValue(relation.getRelationVal()));
					   relationmap.put("relation_card_no",checkString(relation.getCertNumber()));
					   relationmap.put("relation_phone_num",checkString(relation.getPerTelephone()));
					   //relationmap.put("relation_address",checkString(relation.getCommAddr()));
					   relationmap.put("proportion_property",relation.getProportionProperty());
					   relationList.add(relationmap);
				   }
			   }
			resultMap.put("relation_list", relationList);
			
			// 赎楼信息,物业信息
			resultMap.put("property_id", checkIntValue(property.getPid()));
			resultMap.put("house_name", checkString(property.getHouseName()));
			resultMap.put("purpose", checkString(property.getPurpose()));//新增的用途
			resultMap.put("house_area",
					formatDecimal(checkDoubleValue(property.getArea())));
			resultMap.put("cost_money",
					formatDecimal(checkDoubleValue(property.getCostMoney())));
			resultMap.put("house_property_card",
					checkString(property.getHousePropertyCard()));
			resultMap
					.put("tranasction_money",
							formatDecimal(checkDoubleValue(property 
									.getTranasctionMoney())));
			resultMap.put("evaluation_price",
					formatDecimal(checkDoubleValue(property.getEvaluationPrice())));
			resultMap.put("fore_rate",
					formatDecimal(checkDoubleValue(property.getForeRate())));
			// 买卖双方信息
			resultMap.put("seller_name", checkString(property.getSellerName()));
			resultMap
					.put("seller_card_no", checkString(property.getSellerCardNo()));
			resultMap.put("seller_phone", checkString(property.getSellerPhone()));
			resultMap.put("seller_address",
					checkString(property.getSellerAddress()));
			resultMap.put("buyer_name", checkString(property.getBuyerName()));
			resultMap.put("buyer_card_no", checkString(property.getBuyerCardNo()));
			resultMap.put("buyer_phone", checkString(property.getBuyerPhone()));
			resultMap.put("buyer_address", checkString(property.getBuyerAddress()));
			// 银行信息
			resultMap.put("foreclosure_id", checkIntValue(foreclosure.getPid()));
			resultMap.put("old_loan_bank",
					stringToInt(foreclosure.getOldLoanBank()));
			resultMap.put("old_loan_bank_branch",
					stringToInt(foreclosure.getOldLoanBankBranch()));//新增的分行
			//中文
			resultMap.put("old_loan_bank_str",
					foreclosure.getOldBankStr());
			resultMap.put("old_loan_bank_branch_str",
					foreclosure.getOldBankBranchStr());//新增的分行
			
			resultMap.put("old_loan_money",
					formatDecimal(checkDoubleValue(foreclosure.getOldLoanMoney())));
			resultMap
					.put("old_owed_amount",
							formatDecimal(checkDoubleValue(foreclosure
									.getOldOwedAmount())));
			resultMap.put("old_loan_time",
					checkString(foreclosure.getOldLoanTime()));
			resultMap.put("old_loan_person",
					checkString(foreclosure.getOldLoanPerson()));
			resultMap.put("old_loan_phone",
					checkString(foreclosure.getOldLoanPhone()));
			resultMap.put("third_borrower",
					checkString(foreclosure.getThirdBorrower()));
			resultMap.put("third_borrower_card",
					checkString(foreclosure.getThirdBorrowerCard()));
			resultMap.put("third_borrower_phone",
					checkString(foreclosure.getThirdBorrowerPhone()));
			resultMap.put("third_borrower_address",
					checkString(foreclosure.getThirdBorrowerAddress()));
			resultMap.put("new_loan_bank",
					stringToInt(foreclosure.getNewLoanBank()));
			resultMap.put("new_loan_bank_branch",
					stringToInt(foreclosure.getNewLoanBankBranch()));//新增的分行
			//中文
			resultMap.put("new_loan_bank_str",
					foreclosure.getNewBankStr());
			resultMap.put("new_loan_bank_branch_str",
					foreclosure.getNewBankBranchStr());//新增的分行
			
			resultMap.put("new_loan_money",
					formatDecimal(checkDoubleValue(foreclosure.getNewLoanMoney())));
			resultMap.put("new_loan_person",
					checkString(foreclosure.getNewLoanPerson()));
			resultMap.put("new_loan_phone",
					checkString(foreclosure.getNewLoanPhone()));

			resultMap.put("new_rece_bank", foreclosure.getNewReceBank());
			
			resultMap.put("payment_type",
					checkIntValue(foreclosure.getPaymentType()));
			resultMap
					.put("fore_account", checkString(foreclosure.getForeAccount()));
			resultMap.put("accumulation_fund_bank",
					stringToInt(foreclosure.getAccumulationFundBank()));
			
			resultMap.put("accumulation_fund_bank_str",
					checkString(foreclosure.getAccumulationFundBankStr()));
			
			resultMap.put("accumulation_fund_money",
					formatDecimal(checkDoubleValue(foreclosure
							.getAccumulationFundMoney())));
			resultMap.put("supervise_department",
					stringToInt(foreclosure.getSuperviseDepartment()));
			resultMap.put("supervise_department_branch",
					stringToInt(foreclosure.getSuperviseDepartmentBranch()));//新增的分行
			//中文
			resultMap.put("supervise_department_str",
					foreclosure.getSuperviseDepartmentStr());
			resultMap.put("supervise_department_branch_str",
					foreclosure.getSuperviseDepartmentBranchStr());//新增的分行
			resultMap.put("supersion_rece_bank", foreclosure.getSupersionReceBank());
			resultMap.put("supersion_rece_name", foreclosure.getSupersionReceName());
			
			resultMap.put("funds_money",
					formatDecimal(checkDoubleValue(foreclosure.getFundsMoney())));
			resultMap.put("supervise_account",
					checkString(foreclosure.getSuperviseAccount()));
			resultMap.put("notarization_date",
					checkString(foreclosure.getNotarizationDate()));
			resultMap.put("sign_date", checkString(foreclosure.getSignDate()));

			// 费用信息
			resultMap.put("guarantee_id", checkIntValue(guarantee.getPid()));
			resultMap.put("guarantee_fee",
					formatDecimal(checkDoubleValue(guarantee.getGuaranteeFee())));
			resultMap.put("poundage",
					formatDecimal(checkDoubleValue(guarantee.getPoundage())));
			resultMap
					.put("charges_subsidized",
							formatDecimal(checkDoubleValue(guarantee
									.getChargesSubsidized())));
			resultMap.put("rece_money",
					formatDecimal(checkDoubleValue(guarantee.getReceMoney())));
			resultMap
					.put("charges_type", checkIntValue(guarantee.getChargesType()));
			resultMap.put("dept_money",
					formatDecimal(checkDoubleValue(guarantee.getDeptMoney())));
			resultMap.put("fee_rate", formatDecimal(checkDoubleValue(guarantee.getFeeRate())));
			
			List<LinkedHashMap> foreInfos = new ArrayList<LinkedHashMap>();
			// 赎楼清单列表
			List<ProjectForeInformation> foreInformations = projectService
					.queryForeInformations(projectId);
			for (ProjectForeInformation foreInfo : foreInformations) {
				LinkedHashMap foreMap = new LinkedHashMap<>();
				foreMap.put("pid", checkIntValue(foreInfo.getPid()));
				foreMap.put("fore_id", checkIntValue(foreInfo.getForeId()));
				foreMap.put("fore_info_name",
						checkString(foreInfo.getForeInformationName()));
				foreMap.put("project_id", checkIntValue(foreInfo.getProjectId()));
				foreMap.put("original_number",
						checkIntValue(foreInfo.getOriginalNumber()));
				foreMap.put("copy_number", checkIntValue(foreInfo.getCopyNumber()));
				foreMap.put("remark", checkString(foreInfo.getRemark()));
				foreInfos.add(foreMap);
			}

			resultMap.put("foreInfos", foreInfos);

			// 申请办理信息
			BizHandleService.Client client = (BizHandleService.Client) getFactory(
					BusinessModule.MODUEL_INLOAN, "BizHandleService").getClient();
			ApplyHandleInfoDTO query = new ApplyHandleInfoDTO();
			query.setProjectId(projectId);
			query.setPage(1);
			query.setRows(100);
			List<ApplyHandleInfoDTO> resultList = client
					.findAllApplyHandleInfo(query);
			ApplyHandleInfoDTO applyHandleInfoDTO = null;
			if (resultList != null && resultList.size() > 0) {
				applyHandleInfoDTO = resultList.get(0);
			}
			if (applyHandleInfoDTO == null) {
				applyHandleInfoDTO = new ApplyHandleInfoDTO();
			}
			resultMap.put("handleDate",
					checkString(applyHandleInfoDTO.getHandleDate()));
			resultMap.put("contactPerson",
					checkString(applyHandleInfoDTO.getContactPerson()));
			resultMap.put("contactPhone",
					checkString(applyHandleInfoDTO.getContactPhone()));
			resultMap.put("specialCase",
					checkString(applyHandleInfoDTO.getSpecialCase()));
			resultMap.put("remark", checkString(applyHandleInfoDTO.getRemark()));
			resultMap.put("create_id",
					checkIntValue(applyHandleInfoDTO.getCreaterId()));

			returnJsonOfMobile(response, resultMap, true, "查询成功", null);
			logger.info("method:queryCustomerInfoByCid;返回结果：" + resultMap);
		}

		/**
		 * 查询数据字典值
		 * 
		 * @param params
		 * @param request
		 * @param response
		 * @throws TException
		 * @throws ThriftException
		 * @author: Administrator
		 * @date: 2016年4月20日 上午9:44:25
		 */
		@RequestMapping(value = "/queryLookUpValue", method = RequestMethod.POST, headers = {
				"Content-Type=application/json;charset=utf-8",
				"Accept-Version=api_v1" })
		@ResponseBody
		public void queryLookUpValue(@RequestBody JSONObject params,
				HttpServletRequest request, HttpServletResponse response)
				throws TException, ThriftException {
			logger.info("method:queryLookUpValue;入参：" + params);
			int userId = checkIntValue((Integer) params.get("user_id"));// 用户ID
			String lookType = params.getString("type_name");
			if (userId <= 0 || StringUtil.isBlank(lookType)) {
				logger.error("请求数据不合法：" + params);
				returnJsonOfMobile(response, false, "参数不合法", null);
				return;
			}
			Map<Object, Object> resultMap = new LinkedHashMap<Object, Object>();
			List<LinkedHashMap> lookUpValues = new ArrayList<LinkedHashMap>();
			List<SysLookupVal> list = new ArrayList<SysLookupVal>();
			SysLookupService.Client client = (SysLookupService.Client) getFactory(
					BusinessModule.MODUEL_SYSTEM, "SysLookupService").getClient();
			list.addAll(client.getSysLookupValByLookType(lookType));
			for (SysLookupVal lookup : list) {
				LinkedHashMap lookMap = new LinkedHashMap<>();
				lookMap.put("pid", lookup.getPid());
				lookMap.put("look_desc", lookup.getLookupDesc());
				lookUpValues.add(lookMap);
			}

			resultMap.put("result_list", lookUpValues);
			returnJsonOfMobile(response, resultMap, true, "查询成功", null);
			logger.info("客户列表查询成功：result:" + resultMap);

		}
		
		/**
		 * <li>未申请列表</li>
		 * <li>已申请列表</li>
		 * <li>查询申请</li>
		  * @param params
		  * @param request
		  * @param response
		  * @throws TException
		  * @throws ThriftException
		  * @author: xiayt
		  * @date: 2016年5月31日 下午5:12:59
		 */
		@RequestMapping(value = "/queryElementList", method = RequestMethod.POST, headers = {
				"Content-Type=application/json;charset=utf-8",
				"Accept-Version=api_v1" })
		@ResponseBody
		public void queryElementList(@RequestBody JSONObject params,
				HttpServletRequest request, HttpServletResponse response)
				throws TException, ThriftException {
		   logger.info("method:queryElementList;入参：" + params);
		   int productId = checkIntValue((Integer) params.get("product_id"));
		   int applyStatus = checkIntValue((Integer) params.get("apply_status"));
		   int userId = getShiroUser().getPid();// 用户ID
		   if (userId <= 0) {
				logger.error("请求数据不合法：" + params);
				returnJsonOfMobile(response, false, "参数不合法", null);
				return;
			}
		   
		   String projectName = checkString((String) params.get("project_name"));
		   int page = params.get("page") == null ? 1 : (Integer) params.get("page");
		   int rows = params.get("rows") == null ? 10 : (Integer) params.get("rows");
		   ElementMobileDto elementMobileDto = new ElementMobileDto();
		   elementMobileDto.setUserIds(getDataUserIds(getSysUser()));
		   elementMobileDto.setProductId(productId);
		   elementMobileDto.setApplyStatus(applyStatus);
		   elementMobileDto.setProjectName(projectName);
		   elementMobileDto.setRows(rows);
		   elementMobileDto.setPage(page);
		   ElementLendService.Client service = (ElementLendService.Client) getService(BusinessModule.MODUEL_BEFORELOAN, "ElementLendService");
		   List<GridViewMobileDto> list = service.queryElementList(elementMobileDto);
	       int total = service.getTotalElement(elementMobileDto);
	       List<LinkedHashMap> gridViewList = new ArrayList<LinkedHashMap>();
	       for(GridViewMobileDto gridViewMobileDto:list){
	    	   LinkedHashMap map = new LinkedHashMap<>();
	    	   map.put("pid", gridViewMobileDto.getPid());
	    	   map.put("project_name", gridViewMobileDto.getProjectName());
	    	   map.put("product_name", gridViewMobileDto.getProductName());
	    	   map.put("loan_money", gridViewMobileDto.getLoanMoney());
	    	   if(gridViewMobileDto.getApplyStatus() ==1){
	    		   if(StringUtil.isBlank(gridViewMobileDto.getCreaterDate())){
	        		   map.put("creater_date", null);
	        	   }else{
	        		   map.put("creater_date", DateUtils.dateFormatByPattern(gridViewMobileDto.getCreaterDate(),"yyyy-MM-dd"));
	        	   }
	    	   }else if(gridViewMobileDto.getApplyStatus() ==2){
	    		   if(StringUtil.isBlank(gridViewMobileDto.getUpdateTime())){
	        		   map.put("creater_date", null);
	        	   }else{
	        		   map.put("creater_date", DateUtils.dateFormatByPattern(gridViewMobileDto.getUpdateTime(),"yyyy-MM-dd"));
	        	   }
	    	   }
	    	   map.put("apply_status", gridViewMobileDto.getApplyStatus());
	    	   map.put("lend_id", gridViewMobileDto.getLendId());
	    	   map.put("lend_state", gridViewMobileDto.getLendState());
	    	   map.put("org_id", gridViewMobileDto.getOrgId());
	    	   map.put("lend_files_count", gridViewMobileDto.getLendFilesCount());
	    	   map.put("page", gridViewMobileDto.getPage());
	    	   map.put("rows", gridViewMobileDto.getRows());
	    	   gridViewList.add(map);
	       }
	       
	       Map result = new HashMap<>();
	       result.put("result_list", gridViewList);
	       result.put("total", total);
	       returnJsonOfMobile(response, result, true, "查询成功", null);
	       logger.info("要件项目列表查询成功：result:" + result);
		}
		
		/**
		 * 
		  * @Description: 编辑要件申请/要件归还/查看要件详情
		  * @param params
		  * @param request
		  * @param response
		  * @throws TException
		  * @throws ThriftException
		  * @author: xiayt
		  * @date: 2016年5月31日 下午5:15:48
		 */
		@RequestMapping(value = "/addElementLend", method = RequestMethod.POST, headers = {
				"Content-Type=application/json;charset=utf-8",
				"Accept-Version=api_v1" })
		@ResponseBody
		public void addElementLend(@RequestBody JSONObject params,
				HttpServletRequest request, HttpServletResponse response)
				throws TException, ThriftException {
			
			Map<Object, Object> resultMap = new LinkedHashMap<Object, Object>();
			int projectId = checkIntValue((Integer) params.get("project_id"));
			int pid = checkIntValue((Integer) params.get("pid"));
			ElementLend elementLend = new ElementLend();
			Map<Object, Object> elementMap = new LinkedHashMap();
			if (pid > 0) {
				BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ElementLendService");
				ElementLendService.Client client = (ElementLendService.Client) clientFactory.getClient();
				elementLend = client.getElementLendById(pid);
				elementMap.put("pid", elementLend.getPid());
				elementMap.put("projectId", elementLend.getProjectId());
				elementMap.put("lendUserId", elementLend.getLendUserId());
				elementMap.put("orgId", elementLend.getOrgId());
				elementMap.put("lendFilesId", elementLend.getLendFilesId());
				elementMap.put("returnFilesId", elementLend.getReturnFilesId());
				if(StringUtil.isBlank(elementLend.getLendTime())){
					elementMap.put("lendTime", null);
		    	   }else{
		    		   elementMap.put("lendTime", DateUtils.dateFormatByPattern(elementLend.getLendTime(),"yyyy-MM-dd"));
		    	   }
//				elementMap.put("lendTime", elementLend.getLendTime());
				if(StringUtil.isBlank(elementLend.getOriginalReturnTime())){
					elementMap.put("originalReturnTime", null);
		    	   }else{
		    		   elementMap.put("originalReturnTime", DateUtils.dateFormatByPattern(elementLend.getOriginalReturnTime(),"yyyy-MM-dd"));
		    	   }
//				elementMap.put("originalReturnTime", elementLend.getOriginalReturnTime());
				if(StringUtil.isBlank(elementLend.getActualReturnTime())){
					elementMap.put("actualReturnTime", null);
		    	   }else{
		    		   elementMap.put("actualReturnTime", DateUtils.dateFormatByPattern(elementLend.getActualReturnTime(),"yyyy-MM-dd"));
		    	   }
//				elementMap.put("actualReturnTime", elementLend.getActualReturnTime());
				elementMap.put("lendState", elementLend.getLendState());
				elementMap.put("projectName", elementLend.getProjectName());
				elementMap.put("orgName", elementLend.getOrgName());
				elementMap.put("realName", elementLend.getRealName());
				elementMap.put("porpuse", elementLend.getPorpuse());
				elementMap.put("remark", elementLend.getRemark());
				
				List<ElementLendDetails> lendFileList = getLendFileList(pid);
				List<ElementLendDetails> returnFileList = getReturnFileList(pid);
				List<Map<Object, Object>> lendFileMap = new ArrayList<Map<Object, Object>>();
				List<Map<Object, Object>> returnFileMap = new ArrayList<Map<Object, Object>>();
				for(ElementLendDetails elementLendDetails:lendFileList){
					Map<Object, Object> temp = new LinkedHashMap<Object, Object>();
					temp.put("pid", elementLendDetails.getPid());
					temp.put("lendId", elementLendDetails.getLendId());
					temp.put("elementFileId", elementLendDetails.getElementFileId());
					temp.put("elementFileName", elementLendDetails.getElementFileName());
					if(StringUtil.isBlank(elementLendDetails.getLendTime())){
						temp.put("lendTime", null);
			    	   }else{
			    		   temp.put("lendTime", DateUtils.dateFormatByPattern(elementLendDetails.getLendTime(),"yyyy-MM-dd"));
			    	   }
//					temp.put("lendTime", elementLendDetails.getLendTime());
					temp.put("status", elementLendDetails.getStatus());
					temp.put("buyerSellerType", elementLendDetails.getBuyerSellerType());
					temp.put("buyerSellerName", elementLendDetails.getBuyerSellerName());
					temp.put("remark", elementLendDetails.getRemark());
					temp.put("code", elementLendDetails.getCode());
					lendFileMap.add(temp);
					
				}
				
				for(ElementLendDetails elementLendDetails:returnFileList){
					Map<Object, Object> temp = new LinkedHashMap<Object, Object>();
					temp.put("pid", elementLendDetails.getPid());
					temp.put("lendId", elementLendDetails.getLendId());
					temp.put("elementFileId", elementLendDetails.getElementFileId());
					temp.put("elementFileName", elementLendDetails.getElementFileName());
					if(StringUtil.isBlank(elementLendDetails.getLendTime())){
						temp.put("lendTime", null);
			    	   }else{
			    		   temp.put("lendTime", DateUtils.dateFormatByPattern(elementLendDetails.getLendTime(),"yyyy-MM-dd"));
			    	   }
//					temp.put("lendTime", elementLendDetails.getLendTime());
					temp.put("status", elementLendDetails.getStatus());
					temp.put("buyerSellerType", elementLendDetails.getBuyerSellerType());
					temp.put("buyerSellerName", elementLendDetails.getBuyerSellerName());
					temp.put("remark", elementLendDetails.getRemark());
					temp.put("code", elementLendDetails.getCode());
					returnFileMap.add(temp);
				}
				resultMap.put("lend_file_list", lendFileMap);//已借出的要件
				resultMap.put("return_file_list", returnFileMap);//已归还的要件
			}
			
			BaseClientFactory clientFactoryOrg = null;
			BaseClientFactory clientFactory = null;
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			Project project = client.getLoanProjectByProjectId(projectId);
			ProjectProperty projectProperty = project.getProjectProperty();
			String sellerName = projectProperty.getSellerName();// 卖方姓名
			String sellerCardNo = projectProperty.getSellerCardNo();// 卖方身份证号
			String buyerName = projectProperty.getBuyerName();// 买家名称
			String buyerCardNo = projectProperty.getBuyerCardNo();// 买家身份证号

			List<Map<String, String>> sellerList = fillBuyerSellerMap(sellerName, sellerCardNo);
			List<Map<String, String>> buyerList = fillBuyerSellerMap(buyerName, buyerCardNo);
			
			resultMap.put("seller_list", sellerList);
			resultMap.put("buyer_list", buyerList);
			resultMap.put("project_id", projectId);
			resultMap.put("element_lend", elementMap);
			returnJsonOfMobile(response, resultMap, true, "查询成功", null);
			logger.info("method:addElementLend;返回结果：" + resultMap);
		}
		
		/**
		  * @Description: 提交要件申请/归还接口
		  * @param params
		  * @param request
		  * @param response
		  * @throws TException
		  * @throws ThriftException
		  * @author: xiayt
		  * @date: 2016年5月31日 下午5:16:21
		 */
		@RequestMapping(value = "/saveElementLend", method = RequestMethod.POST, headers = {
				"Content-Type=application/json;charset=utf-8",
				"Accept-Version=api_v1" })
		@ResponseBody
		public void saveElementLend(@RequestBody JSONObject params,
				HttpServletRequest request, HttpServletResponse response)
				throws TException, ThriftException {
			Boolean isSuccess = true;
			String resultTips = "保存成功";
			Map<Object, Object> resultMap = new LinkedHashMap<Object, Object>();
			int pid  = checkIntValue((Integer) params.get("pid"));
			int lendUserId = checkIntValue((Integer) params.get("lend_user_id"));
			int productId = checkIntValue((Integer) params.get("product_id"));
			int projectId = checkIntValue((Integer) params.get("project_id"));
			int orgId = checkIntValue((Integer) params.get("org_id"));
			int op = checkIntValue((Integer) params.get("op"));
			
			if (orgId <= 0 || projectId <=0) {
				logger.error("请求数据不合法：" + params);
				returnJsonOfMobile(response, false, "参数不合法", null);
				return;
			}
			
			int lendState = checkIntValue((Integer) params.get("lend_state"));
			StringBuffer returnFilesId = new StringBuffer();
			String lendFilesId = checkString((String) params.get("lend_files_id"));
			String lendTime = checkString((String) params.get("lend_time"));
			String actualReturnTime = checkString((String) params.get("actual_return_time"));//实际归还时间
			String originalReturnTime = checkString((String) params.get("original_return_time"));
			String porpuse = checkString((String) params.get("porpuse"));
			String remark = checkString((String) params.get("remark"));
			BaseClientFactory clientFactory = null;
			ElementLend elementLend = new ElementLend();
			elementLend.setPid(pid);
			elementLend.setLendUserId(lendUserId);
			elementLend.setProductId(productId);
			elementLend.setProjectId(projectId);
			elementLend.setOrgId(orgId);
			elementLend.setLendFilesId(lendFilesId);
			elementLend.setLendTime(lendTime);
			elementLend.setOriginalReturnTime(originalReturnTime);
			elementLend.setPorpuse(porpuse);
			elementLend.setActualReturnTime(actualReturnTime);
			elementLend.setLendState(lendState);
//			elementLend.setReturnFilesId(returnFilesId);
			elementLend.setRemark(remark);
			elementLend.setUpdateTime(DateUtil.format(new Date()));
		
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ElementLendService");
			ElementLendService.Client client = (ElementLendService.Client) clientFactory.getClient();
			pid = elementLend.getPid();
			WorkflowService.Client workflowServiceClient = (WorkflowService.Client) getService(BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
			if (op ==3) {// 归还要件
				List<ElementLendDetails> elementLendDetailsList = new ArrayList<ElementLendDetails>();
				
				List<Map> returnFileList = (List<Map>)params.get("return_list");
				if(!StringUtil.isBlank(elementLend.getReturnFilesId())){
					returnFilesId.append(elementLend.getReturnFilesId() + ",");
				}
				
				for(Map obj: returnFileList){
					int pidTmp = checkIntValue((Integer) obj.get("pid"));
					int stateTmp = checkIntValue((Integer) obj.get("state"));
					String returnTimeTmp = checkString((String) obj.get("return_time"));
					ElementLendDetails elementLendDetails = new ElementLendDetails();
					elementLendDetails.setPid(pidTmp);
					elementLendDetails.setElementFileId(pidTmp);
					elementLendDetails.setStatus(stateTmp);
					elementLendDetails.setReturnTime(returnTimeTmp);
					elementLendDetailsList.add(elementLendDetails);
					returnFilesId.append(pidTmp + ",");
				}
				returnFilesId.deleteCharAt(returnFilesId.length() - 1);
				elementLend.setReturnFilesId(returnFilesId.toString());
//				elementLend.setLendFilesId(returnFilesId.toString());
				
				int rows = client.batchUpdateElementLendDetails(elementLend, elementLendDetailsList);
//				if (rows != 0) {
//					// 成功的话,就做业务日志记录
////					recordLog(BusinessModule.MODUEL_ELEMENT, SysLogTypeConstant.LOG_TYPE_UPDATE, "保存要件借出信息,编号:" + elementLend.getPid(),elementLend.getProjectId());
//				} else {
//					// 失败的话,做提示处理
//					isSuccess=false;
//					resultTips="保存失败";
//				}
			} else if(op == 1) {// 新增要件信息，新增的逻辑有问题
				elementLend.setLendState(2);// 审核中
				// 获取系统时间,并设置
				Timestamp time = new Timestamp(new Date().getTime());
				elementLend.setUpdateTime(time.toString());
				List<Map> returnFileList = (List<Map>)params.get("return_list");
				for(Map obj: returnFileList){
					int pidTmp = checkIntValue((Integer) obj.get("pid"));
					int stateTmp = checkIntValue((Integer) obj.get("state"));
					String returnTimeTmp = checkString((String) obj.get("return_time"));
					ElementLendDetails elementLendDetails = new ElementLendDetails();
//					elementLendDetails.setPid(pidTmp);
					elementLendDetails.setElementFileId(pidTmp);
					elementLendDetails.setStatus(stateTmp);
					elementLendDetails.setReturnTime(returnTimeTmp);
					elementLendDetails.setLendTime(lendTime);
					returnFilesId.append(pidTmp + ",");
				}
				returnFilesId.deleteCharAt(returnFilesId.length() - 1);
				elementLend.setLendFilesId(returnFilesId.toString());

				pid = client.addElementLend(elementLend);
				
				workflowServiceClient.executeFlow(lendUserId, pid, projectId, "elementLendRequestProcess", "task_ElementLendProcess", "");
			}else if(op == 2) {//修改要件
				List<ElementLendDetails> elementLendDetailsList = new ArrayList<ElementLendDetails>();
				elementLend.setUpdateTime(DateUtil.format(new Date()));
				List<Map> returnFileList = (List<Map>)params.get("return_list");

				for(Map obj: returnFileList){
					int pidTmp = checkIntValue((Integer) obj.get("pid"));
					int stateTmp = checkIntValue((Integer) obj.get("state"));
					String returnTimeTmp = checkString((String) obj.get("return_time"));
					ElementLendDetails elementLendDetails = new ElementLendDetails();
					elementLendDetails.setPid(pidTmp);
					elementLendDetails.setLendId(pid);
					elementLendDetails.setElementFileId(pidTmp);
					elementLendDetails.setStatus(stateTmp);
					elementLendDetails.setLendTime(lendTime);
					elementLendDetailsList.add(elementLendDetails);
					returnFilesId.append(pidTmp + ",");
				}
				returnFilesId.deleteCharAt(returnFilesId.length() - 1);
				elementLend.setLendFilesId(returnFilesId.toString());
				client.updateElementLendDetails(elementLend,elementLendDetailsList);
				workflowServiceClient.executeFlow(lendUserId, pid, projectId, "elementLendRequestProcess", "task_ElementLendProcess", "");
			}
			
			resultMap.put("project_id", projectId);
			resultMap.put("lend_id", pid);
			returnJsonOfMobile(response, resultMap, isSuccess, resultTips, null);
			logger.info("method:saveElementLend;返回结果：" + resultMap);
		}
		
		/**
		 * 
		  * @Description: 申请要件列表
		  * @param params
		  * @param request
		  * @param response
		  * @throws TException
		  * @throws ThriftException
		  * @author: xiayt
		  * @date: 2016年6月2日 下午4:16:21
		 */
		@RequestMapping(value = "/getApplyFileInfo", method = RequestMethod.POST, headers = {
				"Content-Type=application/json;charset=utf-8",
				"Accept-Version=api_v1" })
		@ResponseBody
		public void getApplyFileInfo(@RequestBody JSONObject params,
				HttpServletRequest request, HttpServletResponse response)
				throws TException, ThriftException {
			IntegratedDeptService.Client service = (IntegratedDeptService.Client) getService(BusinessModule.MODUEL_INLOAN, "IntegratedDeptService");
			int projectId = checkIntValue((Integer) params.get("project_id"));
			int buyerSellerType = checkIntValue((Integer) params.get("buyer_seller_type"));
			String buyerSellerName = checkString((String) params.get("buyer_seller_name"));
	        CollectFileDTO collectfileQuery=new CollectFileDTO();
	        collectfileQuery.setProjectId(projectId);
	        collectfileQuery.setPage(-1);//不分页查询
	        collectfileQuery.setStatus(Constants.STATUS_ENABLED);
	        collectfileQuery.setBuyerSellerType(buyerSellerType);
	        collectfileQuery.setBuyerSellerName(buyerSellerName);
	        //查询已收取的要件
	        List<CollectFileDTO> collectFileList = service.queryCollectFile(collectfileQuery);
	        List<Map<Object, Object>> colectFileMap = new ArrayList<Map<Object, Object>>();
	        for(CollectFileDTO collectFileDTO: collectFileList){
	        	Map<Object, Object> map = new LinkedHashMap<Object, Object>();
	        	map.put("pid", collectFileDTO.getPid());
	        	map.put("projectId", collectFileDTO.getProjectId());
	        	map.put("status", collectFileDTO.getStatus());
	        	map.put("remark", collectFileDTO.getStatus());
	        	map.put("code", collectFileDTO.getCode());
	        	map.put("name", collectFileDTO.getName());
	        	map.put("buyerSellerType", collectFileDTO.getBuyerSellerType());
	        	map.put("buyerSellerName", collectFileDTO.getBuyerSellerName());
	        	colectFileMap.add(map);
	        }
	        Map<Object, Object> resultMap = new LinkedHashMap<Object, Object>();
	        resultMap.put("collectFileList", colectFileMap);
	        returnJsonOfMobile(response, resultMap, true, "查询成功", null);
			logger.info("method:getApplyFileInfo;返回结果：" + resultMap);
			
		}
		
		/**
		 * 
		  * @Description: 归还要件列表
		  * @param params
		  * @param request
		  * @param response
		  * @throws TException
		  * @throws ThriftException
		  * @author: xiayt
		  * @date: 2016年6月2日 下午4:16:21
		 */
		@RequestMapping(value = "/getRefundFileInfo", method = RequestMethod.POST, headers = {
				"Content-Type=application/json;charset=utf-8",
				"Accept-Version=api_v1" })
		@ResponseBody
		public void getRefundFileInfo(@RequestBody JSONObject params,
				HttpServletRequest request, HttpServletResponse response)
				throws TException, ThriftException {
			int lendId = checkIntValue((Integer) params.get("lend_id"));
			int buyerSellerType = checkIntValue((Integer) params.get("buyer_seller_type"));
			String buyerSellerName = checkString((String) params.get("buyer_seller_name"));
			List<ElementLendDetails> lendFileList = new ArrayList<ElementLendDetails>();
			BaseClientFactory clientFactory = null;
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ElementLendService");
			ElementLendService.Client client = (ElementLendService.Client) clientFactory.getClient();
			ElementLendDetails details = new ElementLendDetails();
			details.setLendId(lendId);
			details.setBuyerSellerName(buyerSellerName);
			details.setBuyerSellerType(buyerSellerType);
			lendFileList = client.queryElementLendDetails(details );
			List<Map<Object, Object>> lendFileMap = new ArrayList<Map<Object, Object>>();
			for(ElementLendDetails elementLendDetails:lendFileList){
				Map<Object, Object> map = new LinkedHashMap<Object, Object>();
				map.put("pid", elementLendDetails.getPid());
				map.put("lendId", elementLendDetails.getLendId());
				map.put("elementFileId", elementLendDetails.getElementFileId());
				map.put("elementFileName", elementLendDetails.getElementFileName());
				if(StringUtil.isBlank(elementLendDetails.getLendTime())){
					map.put("lendTime", null);
		    	   }else{
		    		   map.put("lendTime", DateUtils.dateFormatByPattern(elementLendDetails.getLendTime(),"yyyy-MM-dd"));
		    	   }
				if(StringUtil.isBlank(elementLendDetails.getReturnTime())){
					map.put("returnTime", null);
		    	   }else{
		    		   map.put("returnTime", DateUtils.dateFormatByPattern(elementLendDetails.getReturnTime(),"yyyy-MM-dd"));
		    	   }
//				lendFileMap.put("lendTime", elementLendDetails.getLendTime());
				map.put("status", elementLendDetails.getStatus());
				map.put("buyerSellerType", elementLendDetails.getBuyerSellerType());
				map.put("buyerSellerName", elementLendDetails.getBuyerSellerName());
				map.put("remark", elementLendDetails.getRemark());
				map.put("code", elementLendDetails.getCode());
				lendFileMap.add(map);
				
			}
		
			Map<Object, Object> resultMap = new LinkedHashMap<Object, Object>();
	        resultMap.put("lendFileList", lendFileMap);
	        returnJsonOfMobile(response, resultMap, true, "查询成功", null);
			logger.info("method:getRefundFileInfo;返回结果：" + resultMap);
		}
		
		/**
		 * 	查询已借出的要件列表
		  * @Description: TODO
		  * @param pid
		  * @param response
		  * @author: andrew
		  * @date: 2016年2月28日 上午9:45:32
		 */
		public List<ElementLendDetails> getLendFileList(Integer pid){
			List<ElementLendDetails> lendFileList = new ArrayList<ElementLendDetails>();
			BaseClientFactory clientFactory = null;
			try {
				clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ElementLendService");
				ElementLendService.Client client = (ElementLendService.Client) clientFactory.getClient();
				ElementLendDetails details = new ElementLendDetails();
				details.setLendId(pid);
				details.setStatus(1);
				lendFileList = client.queryElementLendDetails(details );
				}catch (Exception e) {
					logger.error("查询已借出的要件：" + e.getMessage());
					e.printStackTrace();
				}
			
			
			return lendFileList;
		}
		
		/**
		 * 	查询已归还的要件列表
		  * @Description: TODO
		  * @param pid
		  * @param response
		  * @author: andrew
		  * @date: 2016年2月28日 上午9:45:32
		 */
		public  List<ElementLendDetails> getReturnFileList(Integer pid){
			List<ElementLendDetails> returnFileList = new ArrayList<ElementLendDetails>();
			BaseClientFactory clientFactory = null;
			try {
				clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ElementLendService");
				ElementLendService.Client client = (ElementLendService.Client) clientFactory.getClient();
				ElementLendDetails details = new ElementLendDetails();
				details.setLendId(pid);
				details.setStatus(2);
				returnFileList = client.queryElementLendDetails(details );
			}catch (Exception e) {
				logger.error("查询已归还的要件：" + e.getMessage());
				e.printStackTrace();
			}
			return returnFileList;
		}

		/**
		 * 解析贷前项目传递的参数，项目基本信息、赎楼信息、物业双方买卖信息，费用信息
		 * 
		 * @param params
		 * @author: Administrator
		 * @date: 2016年4月19日 上午11:24:24
		 */
		private Project parseProjectJson(JSONObject params, Project project) {
			ProjectForeclosure foreclosure = project.getProjectForeclosure();
			ProjectProperty property = project.getProjectProperty();
			ProjectGuarantee guarantee = project.getProjectGuarantee();

			String address = checkString((String) params.get("address"));
			
			String specialDesc =  checkString((String) params.get("special_desc"));//特殊情况
			
			//String declaration = checkString((String) params.get("declaration"));//新增的报单员
			// 业务信息
			String receDate = checkString((String) params.get("rece_date"));
			String paymentAccount = checkString((String) params
					.get("payment_account"));
			String paymentName = checkString((String) params.get("payment_name"));
			String businessContacts = checkString((String) params
					.get("business_contacts"));
			String contactsPhone = checkString((String) params
					.get("contacts_phone"));
			int innerOrOut = checkIntValue((Integer) params.get("inner_or_out"));
			int businessCategory = checkIntValue((Integer) params
					.get("business_category"));
			// 申请信息
			//int customerId = checkIntValue((Integer) params.get("customer_id"));
			int userId = checkIntValue((Integer) params.get("user_id"));// 用户ID

			// 赎楼信息,物业信息
			
			Double houseArea = params.getDouble("house_area");
			Double costMoney = params.getDouble("cost_money");
			String housePropertyCard = checkString((String) params
					.get("house_property_card"));
			Double tranasctionMoney = params
					.getDouble("tranasction_money");
			Double evaluationPrice = params.getDouble("evaluation_price");
			int checkUserOrg = checkUserOrg(userId);//判断系统使用人为万通或者小科
			if(checkUserOrg == 1){
				tranasctionMoney = evaluationPrice;
			}
			
			Double foreRate = params.getDouble("fore_rate");
			String purpose = checkString((String) params.get("purpose"));//新增的用途
			// 买卖双方信息
			String sellerName = checkString((String) params.get("seller_name"));
			String sellerCardNo = checkString((String) params.get("seller_card_no"));
			String sellerPhone = checkString((String) params.get("seller_phone"));
			String sellerAddress = checkString((String) params
					.get("seller_address"));
			String buyerName = checkString((String) params.get("buyer_name"));
			String buyerCardNo = checkString((String) params.get("buyer_card_no"));
			String buyerPhone = checkString((String) params.get("buyer_phone"));
			String buyerAddress = checkString((String) params.get("buyer_address"));
			// 银行信息
			Integer oldLoanBank = (Integer)params.get("old_loan_bank");
			//Integer oldLoanBankBranch = (Integer)params.get("old_loan_bank_branch");//新增的分行
			Double oldLoanMoney = params.getDouble("old_loan_money");
			Double oldOwedAmount = params.getDouble("old_owed_amount");
			String oldLoanTime = checkString((String) params.get("old_loan_time"));
			String oldLoanPerson = checkString((String) params
					.get("old_loan_person"));
			String oldLoanPhone = checkString((String) params.get("old_loan_phone"));
			String thirdBorrower = checkString((String) params
					.get("third_borrower"));
			String thirdBorrowerCard = checkString((String) params
					.get("third_borrower_card"));
			String thirdBorrowerPhone = checkString((String) params
					.get("third_borrower_phone"));
			String thirdBorrowerAddress = checkString((String) params
					.get("third_borrower_address"));
			Integer newLoanBank = (Integer) params.get("new_loan_bank");
			//Integer newLoanBankBranch = (Integer)params.get("new_loan_bank_branch");//新增的分行
			Double newLoanMoney = params.getDouble("new_loan_money");
			String newLoanPerson = checkString((String) params
					.get("new_loan_person"));
			String newLoanPhone = checkString((String) params.get("new_loan_phone"));

			String newReceBank = checkString((String) params.get("new_rece_bank"));
			
			int paymentType = checkIntValue((Integer) params.get("payment_type"));
			String foreAccount = checkString((String) params.get("fore_account"));
			Integer accumulationFundBank = (Integer) params.get("accumulation_fund_bank");
			Double accumulationFundMoney = params
					.getDouble("accumulation_fund_money");
			Integer superviseDepartment = (Integer) params.get("supervise_department");
			//Integer superviseDepartmentBranch = (Integer)params.get("supervise_department_branch");//新增的监管分行
			String supersionReceBank = checkString((String) params.get("supersion_rece_bank"));
			String supersionReceName = checkString((String) params.get("supersion_rece_name"));
			
			Double fundsMoney = params.getDouble("funds_money");
			String superviseAccount = checkString((String) params
					.get("supervise_account"));
			String notarizationDate = checkString((String) params
					.get("notarization_date"));
			String signDate = checkString((String) params.get("sign_date"));

			// 费用信息
			Double guaranteeFee = params.getDouble("guarantee_fee");
			Double poundage = params.getDouble("poundage");
			Double chargesSubsidized = params
					.getDouble("charges_subsidized");
			Double receMoney = checkDoubleValue(params.getDouble("rece_money"));
			int chargesType = checkIntValue((Integer) params.get("charges_type"));
			Double deptMoney = checkDoubleValue(params.getDouble("dept_money"));
			Double feeRate = params.getDouble("fee_rate");//新增的费率
			// 项目信息
			
			project.setAddress(address);
			project.setBusinessCategory(businessCategory);
			project.setBusinessContacts(businessContacts);
			project.setContactsPhone(contactsPhone);
			project.setInnerOrOut(innerOrOut);
			//project.setDeclaration(declaration);
			project.setSpecialDesc(specialDesc);
			
			// 赎楼信息
			foreclosure.setProjectId(project.getPid());
			if(oldLoanBank != null){
				foreclosure.setOldLoanBank(oldLoanBank.toString());
			}
/*			if(oldLoanBankBranch != null){
				foreclosure.setOldLoanBankBranch(oldLoanBankBranch.toString());
			}*/
			
			foreclosure.setOldLoanMoney(oldLoanMoney);
			foreclosure.setOldLoanPerson(oldLoanPerson);
			foreclosure.setOldLoanPhone(oldLoanPhone);
			foreclosure.setOldLoanTime(oldLoanTime);
			foreclosure.setOldOwedAmount(oldOwedAmount);
			foreclosure.setThirdBorrower(thirdBorrower);
			foreclosure.setThirdBorrowerAddress(thirdBorrowerAddress);
			foreclosure.setThirdBorrowerCard(thirdBorrowerCard);
			foreclosure.setThirdBorrowerPhone(thirdBorrowerPhone);
			if(newLoanBank != null){
				foreclosure.setNewLoanBank(newLoanBank.toString());
			}
/*			if(newLoanBankBranch != null){
				foreclosure.setNewLoanBankBranch(newLoanBankBranch.toString());
			}*/
			
			foreclosure.setNewLoanMoney(newLoanMoney);
			foreclosure.setNewLoanPerson(newLoanPerson);
			foreclosure.setNewLoanPhone(newLoanPhone);
			foreclosure.setNewReceBank(newReceBank);
			foreclosure.setPaymentType(paymentType);
			foreclosure.setForeAccount(foreAccount);
			if(accumulationFundBank != null){
				foreclosure.setAccumulationFundBank(accumulationFundBank.toString());
			}
			foreclosure.setAccumulationFundMoney(accumulationFundMoney);
			foreclosure.setFundsMoney(fundsMoney);
			if(superviseDepartment != null){
				foreclosure.setSuperviseDepartment(superviseDepartment.toString());
			}
/*			if(superviseDepartmentBranch != null){
				foreclosure.setSuperviseDepartmentBranch(superviseDepartmentBranch.toString());
			}*/
			
			foreclosure.setSuperviseAccount(superviseAccount);
			foreclosure.setSupersionReceBank(supersionReceBank);
			foreclosure.setSupersionReceName(supersionReceName);
			
			
			foreclosure.setNotarizationDate(notarizationDate);
			foreclosure.setSignDate(signDate);
			
			foreclosure.setReceDate(receDate);
			foreclosure.setPaymentName(paymentName);
			foreclosure.setPaymentAccount(paymentAccount);
			project.setProjectForeclosure(foreclosure);
			// 物业及买卖双方信息
			property.setArea(houseArea);
			property.setCostMoney(costMoney);
			property.setHousePropertyCard(housePropertyCard);
			property.setTranasctionMoney(tranasctionMoney);
			property.setEvaluationPrice(evaluationPrice);
			property.setForeRate(foreRate);
			property.setSellerName(sellerName);
			property.setSellerAddress(sellerAddress);
			property.setSellerCardNo(sellerCardNo);
			property.setSellerPhone(sellerPhone);
			property.setBuyerName(buyerName);
			property.setBuyerCardNo(buyerCardNo);
			property.setBuyerPhone(buyerPhone);
			property.setBuyerAddress(buyerAddress);
			property.setProjectId(project.getPid());
			property.setPurpose(purpose);
			project.setProjectProperty(property);
			// 费用信息
			
			guarantee.setGuaranteeFee(guaranteeFee);
			guarantee.setPoundage(poundage);
			guarantee.setChargesSubsidized(chargesSubsidized);
			guarantee.setChargesType(chargesType);
			guarantee.setDeptMoney(deptMoney);
			guarantee.setReceMoney(receMoney);
			guarantee.setProjectId(project.getPid());
			guarantee.setFeeRate(feeRate);
			project.setProjectGuarantee(guarantee);
			return project;
		}
		
		/**
		 * 获取广告列表
		  * @param params
		  * @param request
		  * @param response
		  * @throws TException
		  * @throws ThriftException
		  * @author: baogang
		  * @date: 2016年10月17日 下午2:24:38
		 */
		@RequestMapping(value = "/getAdvPicList", method = RequestMethod.POST, headers = {"Content-Type=application/json;charset=utf-8","Accept-Version=api_v1" })
		@ResponseBody
		public void getAdvPicList(@RequestBody JSONObject params,HttpServletRequest request, HttpServletResponse response)throws TException, ThriftException {
			logger.info("method:applyProjectInfo;入参：" + params);
			int userId = checkIntValue((Integer) params.get("user_id"));// 用户ID
			if (userId <= 0) {
				logger.error("请求数据不合法：" + params);
				returnJsonOfMobile(response, false, "参数不合法", null);
				return;
			}
			BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysAdvPicService");
			SysAdvPicService.Client client = (SysAdvPicService.Client) clientFactory.getClient();
			SysAdvPicInfo sysAdvPicInfo = new SysAdvPicInfo();
			sysAdvPicInfo.setStatus(1);
			List<SysAdvPicInfo> list = client.querySysAdvPics(sysAdvPicInfo);
			Map result = new LinkedHashMap<>();
			List<Map> resultList = new ArrayList<Map>();
		       for(SysAdvPicInfo advPic : list){
		    	   Map<String, Object> advMap = new LinkedHashMap<String, Object>();
		    	   advMap.put("title", checkString(advPic.getTitle()));
		    	   advMap.put("adv_url", checkString(advPic.getUrl()));
		    	   advMap.put("picture_url", checkString(advPic.getPictureUrl()));
		    	   advMap.put("order_index", advPic.getOrderIndex());
		    	   resultList.add(advMap);
		       }
		       result.put("result_list", resultList);
		       returnJsonOfMobile(response, result, true, "查询成功", null);
		       logger.info("广告列表查询成功：result:" + result);
		}
		
		/**
		 * 检查用户是否属于万通
		  * 
		  * @author: Administrator
		  * @date: 2016年4月6日 上午10:22:28
		 */
		private int checkUserOrg(int userId){
			int isWantong = 2;//默认不是万通用户
			BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysOrgInfoService");
			try {
				//根据用户ID查出其所属城市ID
				SysUserService.Client sysUserService = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");
			    SysUser sysUser = sysUserService.getSysUserByPid(userId);
				SysOrgInfoService.Client orgService = (SysOrgInfoService.Client) clientFactory.getClient();
				SysOrgInfo sysOrgInfo = new SysOrgInfo();
				sysOrgInfo.setLayer(2);//城市或者二级部门
				sysOrgInfo.setId(sysUser.getOrgId());//用户所在部门ID
				//通过用户的部门以及需要查询的部门层次，查询部门
				List<SysOrgInfo> orgList = orgService.listSysParentOrg(sysOrgInfo);
				if(orgList != null){
					for(SysOrgInfo org : orgList){
						if(org.getName() != null && org.getName().indexOf("万通")!=-1){
							isWantong = 1;//万通用户
							break;
						}
					}
				}
			} catch (Exception e) {
				logger.error("获取二级机构列表失败：" + ExceptionUtil.getExceptionMessage(e));
		        e.printStackTrace();
			}
			return isWantong;
		}
		
		/**
		 * 新增借款人与共同借款人
		  * @param params
		  * @param request
		  * @param response
		  * @throws TException
		  * @throws ThriftException
		  * @author: baogang
		  * @date: 2016年6月1日 上午11:51:49
		 */
		@RequestMapping(value = "/saveCustomer", method = RequestMethod.POST, headers = {"Content-Type=application/json;charset=utf-8","Accept-Version=api_v1" })
		@ResponseBody
		public void saveCustomer(@RequestBody JSONObject params,HttpServletResponse response)throws TException, ThriftException {
			logger.info("method:saveCustomer;入参：" + params);

			int customerId = checkIntValue((Integer)params.get("customer_id"));//客户ID
			int userId = checkIntValue((Integer)params.get("user_id"));//用户ID
			if(userId <=0 || customerId <= 0){
			   logger.error("请求数据不合法：" + params);
			   returnJsonOfMobile(response, false, "参数不合法", null);
			   return;
		   }
			CusAcctService.Client actClient = (CusAcctService.Client) getFactory(BusinessModule.MODUEL_CUSTOMER, "CusAcctService").getClient();
			CusPerService.Client perClient = (CusPerService.Client) getFactory(BusinessModule.MODUEL_CUSTOMER, "CusPerService").getClient();
			String relationIds = "";
	        //关系人
			JSONArray relation = params.getJSONArray("relation_list");
			if (relation != null && relation.size() > 0) {
				for (int i = 0; i < relation.size(); i++) {
					
					JSONObject relationJson = relation.getJSONObject(i);
					// 关系人信息
					int relationId = checkIntValue((Integer) relationJson
							.get("relation_id"));
					String relationName = checkString((String) relationJson
							.get("relation_name"));
					int relationType = checkIntValue((Integer) relationJson
							.get("relation_type"));
					String realtionCardNo = checkString((String) relationJson
							.get("relation_card_no"));
					String relationPhoneNum = checkString((String) relationJson
							.get("relation_phone_num"));
					/*String address = checkString((String) relationJson
							.get("relation_address"));*/
					
					double proportionProperty = checkDoubleValue(relationJson.getDouble("proportion_property"));
					if (!StringUtil.isBlank(relationName, realtionCardNo)
							&& relationType > 0) {
						CusPerson relationPerson = new CusPerson();
						relationPerson.setStatus(1);
						CusAcct cusAcct = new CusAcct();
						cusAcct.setPid(customerId);
						relationPerson.setCusAcct(cusAcct);
						relationPerson.setChinaName(relationName);
						relationPerson.setPid(relationId);
						relationPerson.setRelationType(relationType);
						relationPerson.setCertNumber(realtionCardNo);
						relationPerson.setCertType(13088);
						relationPerson.setTelephone(relationPhoneNum);
						//relationPerson.setCommAddr(address);
						relationPerson.setProportionProperty(proportionProperty);
						if (relationPerson.getPid() > 0) {
							relationIds += actClient.updateCusPerson(relationPerson)+",";
						} else {
							relationIds +=actClient.addCusPerson(relationPerson)+",";
						}
					}

				}
			}
			Map resultMap = new HashMap<>();
			List<Map> relationList = new ArrayList<Map>();
		    //关系人
		    List<CusDTO> cusPers = perClient.getNoSpouseList(customerId);
		    if(cusPers != null && cusPers.size()>0){
			    for(CusDTO cus : cusPers){
				    if(cus.getRelationVal() >2){
					    Map<Object, Object> relationmap= new LinkedHashMap<Object, Object>();
					    relationmap.put("relation_id",checkIntValue(cus.getPid()));
					    relationmap.put("relation_name",checkString(cus.getChinaName()));
					    relationmap.put("relation_type",checkIntValue(cus.getRelationVal()));
					    relationmap.put("relation_card_no",checkString(cus.getCertNumber()));
					    relationmap.put("relation_phone_num",checkString(cus.getPerTelephone()));
					   //relationmap.put("relation_address",checkString(relation.getCommAddr()));
					    relationmap.put("proportion_property",cus.getProportionProperty());
					    relationList.add(relationmap);
				    }
			    }
		    }
		    resultMap.put("relation_map", relationList);
			resultMap.put("customer_id", customerId);
	        returnJsonOfMobile(response, resultMap, true, "保存成功", null);
	        logger.info("method:saveCustomer;返回结果：" + resultMap);
			
		}
		
		@RequestMapping(value = "/getBankInfo", method = RequestMethod.POST, headers = {"Content-Type=application/json;charset=utf-8","Accept-Version=api_v1" })
		@ResponseBody
		public void getBankInfo(@RequestBody JSONObject params,HttpServletRequest request, HttpServletResponse response)
				throws TException, ThriftException {
			logger.info("method:getBankInfo;入参：" + params);
			int userId = checkIntValue((Integer) params.get("user_id"));// 用户ID
			if (userId <= 0) {
				logger.error("请求数据不合法：" + params);
				returnJsonOfMobile(response, false, "参数不合法", null);
				return;
			}
			SysBankService.Client client = (SysBankService.Client) getFactory(BusinessModule.MODUEL_SYSTEM, "SysBankService").getClient();
			
			SysBankInfo sysBankInfo = new SysBankInfo();
			sysBankInfo.setPage(1);
			sysBankInfo.setRows(10000);
			sysBankInfo.setParentId(-1);
			List<SysBankInfo> resultList = client.querySysBankInfos(sysBankInfo);
			JSONArray resultJson = new JSONArray();
			JSONArray parentJson = new JSONArray();
			if(resultList != null && resultList.size()>0){
				for(SysBankInfo bankInfo : resultList){
					if(bankInfo.getParentId() == 0){
						JSONObject bank = new JSONObject();
						bank.put("pid", bankInfo.getPid());
						bank.put("bank_name", bankInfo.getBankName());
						parentJson.add(bank);
					}
				}
			}
			for(int i=0;i<parentJson.size();i++){
				JSONObject parentBank = parentJson.getJSONObject(i);
				JSONArray childJson = new JSONArray();
				for(SysBankInfo bankInfo : resultList){
					if(parentBank.getInteger("pid") == bankInfo.getParentId()){
						JSONObject bank = new JSONObject();
						bank.put("pid", bankInfo.getPid());
						bank.put("bank_name", bankInfo.getBankName());
						childJson.add(bank);
					}
				}
				parentBank.put("child_bank", childJson);
				resultJson.add(parentBank);
			}
			Map resultMap = new HashMap<>();
			resultMap.put("bank_list", resultJson);
	        returnJsonOfMobile(response, resultMap, true, "查询成功", null);
	        logger.info("method:getBankInfo;返回结果：" + resultMap);
		}
		
		/**
		 * 贷前列表
		  * @param params
		  * @param request
		  * @param response
		  * @throws TException
		  * @throws ThriftException
		  * @author: baogang
		  * @date: 2016年6月2日 下午2:14:00
		 */
		@RequestMapping(value = "/getForeProjectList", method = RequestMethod.POST, headers = {"Content-Type=application/json;charset=utf-8","Accept-Version=api_v1" })
		@ResponseBody
		public void getForeProjectList(@RequestBody JSONObject params,HttpServletRequest request, HttpServletResponse response)
				throws TException, ThriftException {
			logger.info("method:getForeProjectList;入参：" + params);
			int userId = checkIntValue((Integer) params.get("user_id"));// 用户ID
			int page = params.get("page") == null ? 1 : (Integer) params.get("page");
			int rows = params.get("rows") == null ? 10 : (Integer) params.get("rows");
			if (userId <= 0) {
				logger.error("请求数据不合法：" + params);
				returnJsonOfMobile(response, false, "参数不合法", null);
				return;
			}
			
			int productId = checkIntValue((Integer) params.get("product_id"));// 产品ID
			String projectName = checkString(params.getString("project_name"));//项目名称
			// 查询用户信息
		    SysUserService.Client sysUserService = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");
	        SysUser sysUser = sysUserService.getSysUserByPid(userId);
	        ProjectService.Client projectService = (ProjectService.Client) getService(
					BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			Project query = new Project();
			query.setUserIds(getDataUserIds(sysUser));//数据权限
			query.setIsChechan(0);//未撤单
			query.setProductType(2);//赎楼类产品
			query.setPage(page);
			query.setRows(rows);
			query.setProductId(productId);
			query.setProjectName(projectName);
			// 创建集合
			List<GridViewDTO> list = projectService.getAllForeProjects(query);
			Map result = new HashMap<>();
			List<Map> resultList = new ArrayList<Map>();
		    for(GridViewDTO dto : list){
		    	Map<String, Object> projectMap = new HashMap<String, Object>();
		    	projectMap.put("pid", stringToInt(dto.getPid()));
		    	projectMap.put("project_name", dto.getValue23());
		    	projectMap.put("product_name", dto.getValue3());
		    	projectMap.put("fore_status", stringToInt(dto.getValue1()));
		    	projectMap.put("loan_money", stringToDouble(dto.getValue7()));
		    	projectMap.put("request_time", dto.getValue26());
		    	projectMap.put("pm_user_id", stringToInt(dto.getValue27()));
		    	projectMap.put("fore_status_str", Constants.FORECLOSURE_STATUS_MAP.get(dto.getValue1()+""));
		    	resultList.add(projectMap);
		    }
			
			result.put("result_list", resultList);
	        returnJsonOfMobile(response, result, true, "查询成功", null);
	        logger.info("贷前列表列表查询成功：result:" + result);
		}
		
	   /**
	    * 转换json接受的数值型数据
	     * @param value
	     * @return
	     * @author: Administrator
	     * @date: 2016年4月14日 下午4:14:39
	    */
	   private int checkIntValue(Integer value){
		   if(value == null){
			   return 0;
		   }
		   return value.intValue();
	   }
	   
	   /**
	    * 转换json接受的数值型数据
	     * @param value
	     * @return
	     * @author: Administrator
	     * @date: 2016年4月14日 下午4:14:39
	    */
	   private double checkDoubleValue(Double value){
		   if(value == null){
			   return 0;
		   }
		   return value.doubleValue();
	   }
	   
	   /**
	    * 根据身份证号码计算年龄、性别、生日
	     * @param cusPerson
	     * @author: Administrator
	     * @date: 2016年4月14日 下午2:35:04
	    */
	   private void getBirthByCardNo(CusPerson cusPerson){
		   String cardNo = cusPerson.getCertNumber();
		   cusPerson.setAge(IDCardUtil.parseAge(cardNo));
		   cusPerson.setSex(IDCardUtil.parseGender(cardNo));
		   cusPerson.setBirthDate(IDCardUtil.parseBirthday(cardNo));
	   }
	   
	   /**
	    * 判断字符串是否为空，为空则返回""
	     * @param str
	     * @return
	     * @author: Administrator
	     * @date: 2016年4月14日 下午3:18:42
	    */
	   private String checkString(String str){
		   if(StringUtil.isBlank(str)){
			   return "";
		   }
		   return str;
	   }
	   
	   private int stringToInt(String value){
		   if(StringUtil.isBlank(value)){
			   return 0;
		   }
		   int num=0;
		   try {
			   num = Integer.parseInt(value);
		   } catch (Exception e) {
				num= 0;
		   }
		   return num;
	   }
	   
	   private double stringToDouble(String value){
		   if(StringUtil.isBlank(value)){
			   return 0; 
		   }
		   return Double.parseDouble(value);
	   }
	   
	   /**
	    * 格式化小数(一般用于显示金额)
	    * @param obj
	    * @return
	    */
	   public static double formatDecimal (Object obj) {//obj应该为double,int,long,float等数字类型
	       DecimalFormat myformat = new DecimalFormat("###.00");
	       String money = myformat.format(obj);
	       if (money.indexOf(".") == 0) {
	    	   money = "0" + money;
	       }
	       return Double.parseDouble(money);
	   }
	   /**
	    * 查询中途划转列表
	    *@author:liangyanjun
	    *@time:2016年6月1日上午10:48:01
	    *@param params
	    *@param request
	    *@param response
	    *@throws TException
	    * @throws ThriftException 
	    */
	   @RequestMapping(value = "/queryIntermediateTransferList", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
	   public void queryIntermediateTransferList(@RequestBody JSONObject params, HttpServletRequest request, HttpServletResponse response) throws TException, ThriftException {
	      logger.info("method:queryIntermediateTransferList;入参：" + params);
	      Integer userId = params.getInteger("user_id");// 登录用户id
	      Integer queryType = params.getInteger("query_type");// 查询类型：中途划转查询=1，中途划转列表=2
	      String projectName = params.getString("project_name");// 项目名称
	      int productId = params.getIntValue("product_id");// 产品id
	      int page = params.getInteger("page") == null ? 1 : params.getInteger("page");
	      int rows = params.getInteger("rows") == null ? 10 : params.getInteger("rows");
	      
	      // 判断入参是否合法
	      if (CheckUtil.checkInteger(userId, queryType)) {
	         returnJsonOfMobile(response, false, "参数不合法", null);
	         return;
	      }
	      Map resultMap = new HashMap<String, Object>();
	      
	      // 查询用户信息
	      SysUserService.Client sysUserService = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");
	      SysUser sysUser = sysUserService.getSysUserByPid(userId);
	      
	      // 查询中途划转列表
	      int total=0;
	      List<IntermediateTransferIndexDTO> intermediateTransferList = null;
	      IntermediateTransferService.Client service = (IntermediateTransferService.Client) getService(BusinessModule.MODUEL_INLOAN, "IntermediateTransferService");
	      IntermediateTransferIndexDTO query = new IntermediateTransferIndexDTO();
	      query.setProjectName(projectName);
	      query.setProductId(productId);
	      query.setType(queryType);
	      query.setUserIds(getDataUserIds(sysUser));
	      query.setPage(page);
	      query.setRows(rows);
	      if (queryType == 2) {
	         // 中途划转列表（已申请列表）
	         intermediateTransferList = service.queryIntermediateTransferRequestIndex(query);
	         total = service.getIntermediateTransferRequestIndexTotal(query);
	      } else {
	         // 中途划转查询（未申请列表）
	         intermediateTransferList = service.queryIntermediateTransferIndex(query);
	         total = service.getIntermediateTransferIndexTotal(query);
	      }
	      
	      List resultList=new ArrayList<>(); 
	      for (IntermediateTransferIndexDTO dto : intermediateTransferList) {
	         HashMap map = new HashMap<>();
	         map.put("project_id", dto.getProjectId());
	         map.put("project_name", dto.getProjectName());
	         map.put("product_name", dto.getProductName());
	         map.put("loan_money", dto.getLoanMoney());
	         map.put("project_pass_date", dto.getProjectPassDate());
	         map.put("pm_user_id", dto.getPmUserId());
	         map.put("is_chechan", dto.getIsChechan());//是否已撤单（1：是，0：不是，默认为0）
	         if (queryType == 2) {//已申请列表返回申请状态
	            map.put("pid", dto.getPid());
	            map.put("apply_handle_status", dto.getApplyHandleStatus());
	         }
	         resultList.add(map);
	      }
	      resultMap.put("result_list", resultList);
	      returnJsonOfMobile(response, resultMap, true, "查询成功", null);
	      logger.info("method:queryIntermediateTransferList;返回结果：" + resultMap);
	   }
	   /**
	    * 根据id查询中途划转信息
	    *@author:liangyanjun
	    *@time:2016年6月1日下午3:00:22
	    *@param params
	    *@param request
	    *@param response
	    *@throws TException
	    */
	   @RequestMapping(value = "/getIntermediateTransferById", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8","Accept-Version=api_v1" })
	   public void getIntermediateTransferById(@RequestBody JSONObject params, HttpServletRequest request, HttpServletResponse response) throws TException {
	      logger.info("method:getIntermediateTransferById;入参：" + params);
	      Map resultMap = new HashMap<String, Object>();
	      int userId = params.getIntValue("user_id");
	      int pid = params.getIntValue("pid");
	      // 判断入参是否合法
	      if (CheckUtil.checkInteger(userId, pid)) {
	         returnJsonOfMobile(response, false, "参数不合法", null);
	         return;
	      }
	      //根据id查询中途划转信息
	      IntermediateTransferService.Client service = (IntermediateTransferService.Client) getService(BusinessModule.MODUEL_INLOAN, "IntermediateTransferService");
	      IntermediateTransferDTO dto = service.getIntermediateTransferById(pid);
	      if (dto != null && dto.getPid() > 0) {
	         Map intermediateTransferMap = new HashMap<String, Object>();
	         intermediateTransferMap.put("pid", dto.getPid());
	         intermediateTransferMap.put("project_id", dto.getProjectId());
	         intermediateTransferMap.put("rec_money", dto.getRecMoney());
	         intermediateTransferMap.put("rec_account", dto.getRecAccount());
	         intermediateTransferMap.put("rec_date", DateUtils.dateFormatByPattern(dto.getRecDate(),"yyyy-MM-dd"));
	         intermediateTransferMap.put("rec_account_name", dto.getRecAccountName());
	         intermediateTransferMap.put("transfer_money", dto.getTransferMoney());
	         intermediateTransferMap.put("transfer_account", dto.getTransferAccount());
	         intermediateTransferMap.put("transfer_date", DateUtils.dateFormatByPattern(dto.getTransferDate(),"yyyy-MM-dd"));
	         intermediateTransferMap.put("transfer_account_name", dto.getTransferAccountName());
	         intermediateTransferMap.put("special_type", dto.getSpecialType());
	         intermediateTransferMap.put("remark", dto.getRemark());
	         
	         resultMap.put("intermediate_transfer_info", intermediateTransferMap);
	      }
	      returnJsonOfMobile(response, resultMap, true, "查询成功", null);
	      logger.info("method:getIntermediateTransferById;返回结果：" + resultMap);
	   }
	   /**
	    * 提交中途划转申请
	    *@author:liangyanjun
	    *@time:2016年6月1日下午2:22:07
	    *@param params
	    *@param request
	    *@param response
	    *@throws TException
	    */
	   @RequestMapping(value = "/subApplyIntermediateTransfer", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8","Accept-Version=api_v1" })
	   public void subApplyIntermediateTransfer(@RequestBody JSONObject params, HttpServletRequest request, HttpServletResponse response) throws TException {
	      logger.info("method:subApplyIntermediateTransfer;入参：" + params);
	      int userId = params.getIntValue("user_id");
	      int pid = params.getIntValue("pid");//已申请，pid一定要传值
	      int projectId = params.getIntValue("project_id");//
	      double recMoney = params.getDoubleValue("rec_money");
	      String recAccount = params.getString("rec_account");
	      String recDate = params.getString("rec_date");
	      String recAccountName = params.getString("rec_account_name");

	      double transferMoney = params.getDoubleValue("transfer_money");
	      String transferAccount = params.getString("transfer_account");
	      String transferDate = params.getString("transfer_date");
	      String transferAccountName = params.getString("transfer_account_name");
	      int specialType = params.getIntValue("special_type");
	      String remark = params.getString("remark");

	      // 判断入参是否合法
	      if (CheckUtil.checkInteger(projectId, specialType) || StringUtil.isBlank(recAccount, recDate, recAccountName, transferAccount, transferDate, transferAccountName)) {
	         returnJsonOfMobile(response, false, "参数不合法", null);
	         return;
	      }
	      if (recMoney < 0 || transferMoney < 0) {
	         returnJsonOfMobile(response, false, "金额要大于0", null);
	         return;
	      }
	      IntermediateTransferDTO it = new IntermediateTransferDTO();
	      it.setPid(pid);
	      it.setProjectId(projectId);
	      it.setRecMoney(recMoney);
	      it.setRecDate(recDate);
	      it.setRecAccount(recAccount);
	      it.setRecAccountName(recAccountName);
	      it.setTransferMoney(transferMoney);
	      it.setTransferAccount(transferAccount);
	      it.setTransferDate(transferDate);
	      it.setTransferAccountName(transferAccountName);
	      it.setSpecialType(specialType);
	      it.setRemark(remark);

	      // 查询用户信息
	      SysUserService.Client sysUserService = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");
	      SysUser sysUser = sysUserService.getSysUserByPid(userId);
	      
	      WorkflowService.Client workflowServiceClient = (WorkflowService.Client) getService(BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
	      IntermediateTransferService.Client service = (IntermediateTransferService.Client) getService(BusinessModule.MODUEL_INLOAN, "IntermediateTransferService");
	      IntermediateTransferDTO query = new IntermediateTransferDTO();
	      query.setPid(pid);
	      List<IntermediateTransferDTO> transferDTOs = service.queryIntermediateTransfer(query);
	      // 不存在则新增
	      if (pid <= 0 || transferDTOs == null || transferDTOs.isEmpty()) {
	         it.setCreaterId(sysUser.getPid());
	         it.setUpdateId(sysUser.getPid());
	         it.setApplyStatus(Constants.APPLY_STATUS_4);
	         pid=service.addIntermediateTransfer(it);
	         workflowServiceClient.executeFlow(userId, pid, projectId,"intermediateTransferProcess","task_Request","");
	         returnJsonOfMobile(response, true, "提交成功", null);
	         return;
	      }

	      IntermediateTransferDTO updateIntermediateTransferDTO = transferDTOs.get(0);
	      if (updateIntermediateTransferDTO.getApplyStatus() > Constants.APPLY_STATUS_3) {
	         returnJsonOfMobile(response, false, "状态为待审核以后不可修改", null);
	         return;
	      }
	      if (updateIntermediateTransferDTO.getApplyStatus() == Constants.APPLY_STATUS_3 && updateIntermediateTransferDTO.getCreaterId() != sysUser.getPid()) {
	         returnJsonOfMobile(response, false, "非申请者不可修改", null);
	         return;
	      }

	      // 存在则更新
	      updateIntermediateTransferDTO.setRecMoney(recMoney);
	      updateIntermediateTransferDTO.setRecAccount(recAccount);
	      updateIntermediateTransferDTO.setRecAccountName(recAccountName);
	      updateIntermediateTransferDTO.setRecDate(recDate);
	      updateIntermediateTransferDTO.setTransferMoney(transferMoney);
	      updateIntermediateTransferDTO.setTransferAccount(transferAccount);
	      updateIntermediateTransferDTO.setTransferAccountName(transferAccountName);
	      updateIntermediateTransferDTO.setTransferDate(transferDate);
	      updateIntermediateTransferDTO.setSpecialType(specialType);
	      updateIntermediateTransferDTO.setRemark(remark);
	      updateIntermediateTransferDTO.setUpdateId(sysUser.getPid());
	      service.updateIntermediateTransfer(updateIntermediateTransferDTO);
	      pid=updateIntermediateTransferDTO.getPid();
	      workflowServiceClient.executeFlow(userId, pid, projectId,"intermediateTransferProcess","task_Request","");
	      returnJsonOfMobile(response, true, "提交成功", null);
	   }
	   
	   private List<Map<String, String>> fillBuyerSellerMap(String nameStr, String cardNoStr) {
	      List<Map<String, String>> result = new ArrayList<Map<String, String>>();
	      if (!StringUtil.isBlank(nameStr)) {
	         String[] names = nameStr.split(",");
	         String[] cardNos = cardNoStr.split(",");
	         for (int i = 0; i < cardNos.length; i++) {
	        	 Map<String, String> map = new HashMap<String, String>();
	        	 map.put("name", names[i]);
	        	 map.put("cardNo", cardNos[i]);
	        	 result.add(map);
	         }
	      }
	      return result;
	   }

	   
	   
	   	/**
	   	 * 业务申请第一页的提交保存方法
	   	  * @param params
	   	  * @param request
	   	  * @param response
	   	  * @throws TException
	   	  * @throws ThriftException
	   	  * @author: baogang
	   	  * @date: 2016年10月12日 下午3:24:59
	   	 */
	    @RequestMapping(value = "/saveBizProjectInfo", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
	    @ResponseBody
		public void saveBizProjectInfo(@RequestBody JSONObject params,
				HttpServletRequest request, HttpServletResponse response)
				throws TException, ThriftException {
			logger.info("method:saveBizProjectInfo;入参：" + params);
			int userId = checkIntValue((Integer) params.get("user_id"));// 用户ID
			int productId = checkIntValue((Integer) params.get("product_id"));// 产品ID
			int customerId = checkIntValue((Integer) params.get("customer_id"));// 客户ID
			
			ProjectService.Client projectService = (ProjectService.Client) getService(
					BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			CusPerService.Client cusClient = (CusPerService.Client) getFactory(
					BusinessModule.MODUEL_CUSTOMER, "CusPerService").getClient();
			ProductService.Client productService = (ProductService.Client) getFactory(
					BusinessModule.MODUEL_PRODUCT, "ProductService").getClient();
			// 根据产品信息查询流程ID
			Product product = productService.getProductById(productId);
			if (product == null) {
				logger.error("请求数据不合法：" + params);
				returnJsonOfMobile(response, false, "产品信息不存在", null);
				return;
			}
			String relationIds = "";
			//关系人
			JSONArray relation = params.getJSONArray("relation_list");
			if (relation != null && relation.size() > 0) {
				for (int i = 0; i < relation.size(); i++) {
					JSONObject relationJson = relation.getJSONObject(i);
					// 关系人信息
					int relationId = checkIntValue((Integer) relationJson
							.get("relation_id"));
					relationIds +=relationId+",";
				}
			}
			if(!StringUtil.isBlank(relationIds)){
				relationIds = relationIds.substring(0,relationIds.length()-1);
			}
			
			if (userId <= 0 || productId <= 0 || customerId <= 0) {
				logger.error("请求数据不合法：" + params);
				returnJsonOfMobile(response, false, "参数不合法", null);
				return;
			}
			
			SysUserService.Client sysUserService = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");
		    SysUser sysUser = sysUserService.getSysUserByPid(userId);
			
			// 客户信息不存在
			CusDTO cusBase = cusClient.getPersonalListByAcctId(customerId);
			if (cusBase == null) {
				logger.error("请求数据不合法：" + params);
				returnJsonOfMobile(response, false, "客户不存在", null);
				return;
			}
			
			Project project = null;//业务信息
			ProjectForeclosure foreclosure = null;//赎楼信息
			ProjectProperty property = null;//物业以及买卖双方信息
			ProjectGuarantee guarantee = null;//费用信息
			
			int projectId = checkIntValue((Integer) params.get("project_id"));
			if (projectId > 0) {
				project = projectService.getLoanProjectByProjectId(projectId);
				foreclosure = project.getProjectForeclosure();
				property = project.getProjectProperty();
				guarantee = project.getProjectGuarantee();
			}else{
				project = new Project();
				foreclosure = new ProjectForeclosure();
				property = new ProjectProperty();
				guarantee = new ProjectGuarantee();
			}

			//判断项目状态
			if(project.getForeclosureStatus() >1){
				logger.error("请求数据不合法：" + params);
				returnJsonOfMobile(response, false, "项目审批中无法修改", null);
				return;
			}
			
			int businessSource = checkIntValue((Integer) params
					.get("business_source"));
			int businessSourceNo = checkIntValue((Integer) params
					.get("business_source_no"));
			String myMainText = checkString((String) params.get("other_source"));// 业务来源为其它时传递的值
			
			Double loanMoney = params.getDouble("loan_money");
			int loanDays = checkIntValue((Integer) params.get("loan_days"));
			String houseName = checkString((String) params.get("house_name"));
			int checkUserOrg = checkUserOrg(userId);//判断系统使用人为万通或者小科
			
			project.setPmUserId(userId);//客户经理ID，创建人
			project.setAcctType(1);//客户类型，个人客户
			project.setProductId(productId);//产品ID
			project.setProjectType(2);//项目类型，赎楼
			project.setBusinessSource(businessSource);//业务来源
			project.setBusinessSourceNo(businessSourceNo);//业务来源具体值
			project.setMyMainText(myMainText);//业务来源为其他时的输入项
			project.setProjectSource(checkUserOrg);// 项目来源，小科、万通

			project.setAbbreviation(cusBase.getChinaName());//客户昵称，用于生成项目名称
			project.setProductType(product.getProductType());//产品类型
			project.setUserPids(relationIds);//共同借款人IDS
			project.setAcctId(customerId);//客户ID
			project.setManagers(sysUser.getRealName());//经办人
			project.setManagersPhone(sysUser.getPhone());//经办人电话
			
			foreclosure.setLoanDays(loanDays);//借款天数
			guarantee.setLoanMoney(loanMoney);//借款金额
			property.setHouseName(houseName);//物业名称
			
			project.setProjectForeclosure(foreclosure);
			project.setProjectGuarantee(guarantee);
			project.setProjectProperty(property);
			//保存项目信息
			String result = projectService.saveProjectInfo(project);
			if (null != result && !"".equals(result)) {
				// 获取项目ID
				String[] arr = result.split(",");
				projectId = Integer.parseInt(arr[0]);
			}else{
				logger.error("请求数据不合法：saveBizProjectInfo" + params);
				returnJsonOfMobile(response, false, "项目信息保存失败", null);
				return;
			}
			
			Map<Object, Object> resultMap = new LinkedHashMap<Object, Object>();
			resultMap.put("project_id", projectId);
			returnJsonOfMobile(response, resultMap, true, "保存成功", null);
			logger.info("method:saveBizProjectInfo;返回结果：" + resultMap);
		}
	    
	    /**
	     * 根据项目ID查询贷前项目资料上传文件类型
	      * @param params
	      * @param request
	      * @param response
	      * @author: baogang
	      * @date: 2016年10月13日 上午11:06:44
	     */
	    @RequestMapping(value = "/getProjectFileType", method = RequestMethod.POST, headers = {"Content-Type=application/json;charset=utf-8","Accept-Version=api_v1" })
		@ResponseBody
	    public void getProjectFileType(@RequestBody JSONObject params,HttpServletRequest request, HttpServletResponse response){
			logger.info("method:getProjectFileType;入参：" + params);
			int userId = checkIntValue((Integer) params.get("user_id"));// 用户ID
			int projectId = checkIntValue((Integer) params.get("project_id"));// 项目ID
			if (userId <= 0 || projectId <= 0) {
				logger.error("请求数据不合法：" + params);
				returnJsonOfMobile(response, false, "参数不合法", null);
				return;
			}
			BaseClientFactory c = null;
			List<Map> resultList = new ArrayList<Map>();
			try {
				c = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
				SysLookupService.Client sysLookupService = (SysLookupService.Client) c.getClient();
				List<SysLookupVal> list = sysLookupService.getDataTypeSysLookup(projectId);
				if(list != null && list.size()>0){
					for(SysLookupVal lookupVal:list){
						Map<String, Object> resultMap = new HashMap<String, Object>();
						resultMap.put("pid", lookupVal.getPid());
						resultMap.put("look_desc", lookupVal.getLookupDesc());
						resultList.add(resultMap);
					}
				}
			} catch (Exception e) {
				logger.error("请求数据不合法：" + params);
				returnJsonOfMobile(response, false, "参数不合法", null);
				return;
			}finally{
				destroyFactory(c);
			}
			Map resultMap = new HashMap<>();
			resultMap.put("result_list", resultList);
	        returnJsonOfMobile(response, resultMap, true, "查询成功", null);
	        logger.info("method:getProjectFileType;返回结果：" + resultMap);
	    }
	    
	    /**
	     * 查询项目上传资料列表
	      * @param params
	      * @param request
	      * @param response
	      * @author: baogang
	      * @date: 2016年10月13日 下午3:14:32
	     */
	    @RequestMapping(value = "/getProjectFileList", method = RequestMethod.POST, headers = {"Content-Type=application/json;charset=utf-8","Accept-Version=api_v1" })
		@ResponseBody
	    public void getProjectFileList(@RequestBody JSONObject params,HttpServletRequest request, HttpServletResponse response){
	    	logger.info("method:getProjectFileList;入参：" + params);
			int userId = checkIntValue((Integer) params.get("user_id"));// 用户ID
			int projectId = checkIntValue((Integer) params.get("project_id"));// 项目ID
			int page = params.get("page") == null ? 1 : (Integer) params.get("page");
			int rows = params.get("rows") == null ? 10 : (Integer) params.get("rows");
			if (userId <= 0 || projectId <= 0) {
				logger.error("请求数据不合法：" + params);
				returnJsonOfMobile(response, false, "参数不合法", null);
				return;
			}
			BaseClientFactory c = null;
			List<Map> resultList = new ArrayList<Map>();
			try {
				c = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ElementLendService");
				ElementLendService.Client elementLendService = (ElementLendService.Client) c.getClient();
				
				List<DataInfo> list = elementLendService.findProjectFiles(projectId);
				if(list != null && list.size()>0){
					for(DataInfo dataInfo : list){
						Map<String, Object> resultMap = new HashMap<String, Object>();
						resultMap.put("file_property", dataInfo.getFileProperty());
						resultMap.put("property_name", dataInfo.getFilePropertyName());
						resultMap.put("file_name", dataInfo.getFileName());
						resultMap.put("file_type", dataInfo.getFileType());
						resultMap.put("file_url", dataInfo.getFileUrl());
						resultMap.put("data_id", dataInfo.getDataId());
						resultList.add(resultMap);
					}
				}
				
			} catch (Exception e) {
				logger.error("请求数据不合法：" + params);
				returnJsonOfMobile(response, false, "参数不合法", null);
				return;
			}finally{
				destroyFactory(c);
			}
			Map resultMap = new HashMap<>();
			resultMap.put("result_list", resultList);
	        returnJsonOfMobile(response, resultMap, true, "查询成功", null);
	        logger.info("method:getProjectFileList;返回结果：" + resultMap);
	    }
	    
	    /**
	     * 保存上传资料文件
	      * @param params
	      * @param request
	      * @param response
	      * @author: baogang
	      * @date: 2016年10月13日 下午4:22:57
	     */
	    @RequestMapping(value = "/saveProjectFile", method = RequestMethod.POST, headers = {"Content-Type=application/json;charset=utf-8","Accept-Version=api_v1" })
		@ResponseBody
	    public void saveProjectFile(@RequestBody JSONObject params,HttpServletRequest request, HttpServletResponse response){
	    	logger.info("method:getProjectFileList;入参：" + params);
			int userId = checkIntValue((Integer) params.get("user_id"));// 用户ID
			int projectId = checkIntValue((Integer) params.get("project_id"));// 项目ID
			if (userId <= 0 || projectId <= 0) {
				logger.error("请求数据不合法：" + params);
				returnJsonOfMobile(response, false, "参数不合法", null);
				return;
			}
			BaseClientFactory c = null;
			try {
				JSONArray projectFileArray = params.getJSONArray("project_files");
				List<DataInfo> list = new ArrayList<DataInfo>();
				for(int i=0;i<projectFileArray.size();i++){
					JSONObject jsonObject = projectFileArray.getJSONObject(i);
					int fileProperty = checkIntValue((Integer)jsonObject.get("file_property"));
					int dataId = checkIntValue((Integer)jsonObject.get("data_id"));
					int fileId = checkIntValue((Integer)jsonObject.get("file_id"));
					DataInfo dataInfo = new DataInfo();
					dataInfo.setProjectId(projectId);
					dataInfo.setFileId(fileId);
					dataInfo.setFileProperty(fileProperty);
					dataInfo.setUserId(userId);
					dataInfo.setDataId(dataId);
					list.add(dataInfo);
				}
				c = getFactory(BusinessModule.MODUEL_BEFORELOAN, "DataUploadService");
				DataUploadService.Client dataService = (DataUploadService.Client) c.getClient();
				dataService.saveDataList(list);
			} catch (Exception e) {
				logger.error("请求数据不合法：" + params);
				returnJsonOfMobile(response, false, "参数不合法", null);
				return;
			}finally{
				destroyFactory(c);
			}
			returnJsonOfMobile(response, true, "提交成功", null);
	    }
   
   
   
   
	/**
	 * Q房估价-城市列表
	 * 
	 * @param params
	 * @param request
	 * @param response
	 * @throws TException
	 * @throws ThriftException
	 */
	@RequestMapping(value = "/getQfangCity.action", method = RequestMethod.POST, 
			headers = {"Content-Type=application/json;charset=utf-8","Accept-Version=api_v1" })
	public void getQfangCity(HttpServletRequest request, HttpServletResponse response)throws Exception {

		Map resultMap = new HashMap<String, Object>();

		List<CityDtoEnum> cityList = qfangApiService.getQfangCity();

		List resultList = new ArrayList<>();
		if (cityList != null && cityList.size() > 0) {
			Map<String, String> tempMap = null;
			for (CityDtoEnum indexObj : cityList) {
				tempMap = new HashMap<String, String>();
				tempMap.put("city_name", indexObj.getAlias());
				tempMap.put("city_code", indexObj.getValue());
				resultList.add(tempMap);
			}
		}
		resultMap.put("result_list", resultList);
		returnJsonOfMobile(response, resultMap, true, "查询成功", null);
		logger.info("method:getQfangCity;返回结果：" + resultMap);
	}
	
	
	/**
	 * Q房估价-小区查询
	 * 
	 * @param params
	 * @param request
	 * @param response
	 * @throws TException
	 * @throws ThriftException
	 */
	@RequestMapping(value = "/getQfangGarden.action", method = RequestMethod.POST, 
			headers = {"Content-Type=application/json;charset=utf-8","Accept-Version=api_v1" })
	public void getQfangGarden(@RequestBody JSONObject params,HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("method:getQfangGarden;入参：" + params);
		Map resultMap = new HashMap<String, Object>();

		//楼盘/小区名
		String cityCode = params.getString("city_code");
		//城市
		String gardenName = params.getString("garden_name");
		
		if (StringUtil.isBlank(cityCode, gardenName)) {
			returnJsonOfMobile(response, false, "参数不合法",ExceptionCode.MOBILE_BAD_REQUEST);
			return;
		}
		GardenSearchRequest qfangRequest = new GardenSearchRequest();
		
		qfangRequest.setCity(QfangUtil.getQfangCityDtoEnum(cityCode));
		qfangRequest.setSearchName(gardenName);
		qfangRequest.setPageSize(20);
		GardenSearchResponse  qfangResponse =qfangApiService.getQfangGarden(qfangRequest);
		
		List resultList = new ArrayList<>();
		if(qfangResponse != null ){
			System.out.println("qfangResponse:"+JSONObject.toJSONString(qfangResponse));
			if(qfangResponse.isSuccess()){
				List<GardenDto>  tempList = qfangResponse.getItems() ; 
				if(tempList != null  && tempList.size()> 0){
					
					Map<String,String> tempMap = null;
					
					for (GardenDto indexObj : tempList) {
						tempMap = new HashMap<String,String>();
						tempMap.put("garden_name", indexObj.getName());
						tempMap.put("garden_id", indexObj.getId());
						resultList.add(tempMap);
					}
				}
			}
		}
		
		resultMap.put("result_list", resultList);
		returnJsonOfMobile(response, resultMap, true, "查询成功", null);
		logger.info("method:getQfangGarden;返回结果：" + resultMap);
	}
	
	
	
	/**
	 * Q房估价-楼栋/单元查询
	 * 
	 * @param params
	 * @param request
	 * @param response
	 * @throws TException
	 * @throws ThriftException
	 */
	@RequestMapping(value = "/getQfangBuilding.action", method = RequestMethod.POST, 
			headers = {"Content-Type=application/json;charset=utf-8","Accept-Version=api_v1" })
	public void getQfangBuilding(@RequestBody JSONObject params,HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("method:getQfangBuilding;入参：" + params);
		Map resultMap = new HashMap<String, Object>();

		//楼盘/小区名
		String cityCode = params.getString("city_code");
		//城市
		String gardenId = params.getString("garden_id");
		
		if (StringUtil.isBlank(cityCode, gardenId)) {
			returnJsonOfMobile(response, false, "参数不合法",ExceptionCode.MOBILE_BAD_REQUEST);
			return;
		}
		BuildingSearchRequest qfangRequest = new BuildingSearchRequest();
		
		qfangRequest.setCity(QfangUtil.getQfangCityDtoEnum(cityCode));
		qfangRequest.setGardenId(gardenId);
		BuildingSearchResponse  qfangResponse =qfangApiService.getQfangBuilding(qfangRequest);
		
		List resultList = new ArrayList<>();
		if(qfangResponse != null ){
			System.out.println("qfangResponse:"+JSONObject.toJSONString(qfangResponse));
			if(qfangResponse.isSuccess()){
				List<BuildingDto>  buildList = qfangResponse.getItems() ; 
				if(buildList != null  && buildList.size()> 0){
					
					Map<String,String> tempMap = null;
					List<UnitDto> unitList = null; 
					for (BuildingDto build : buildList) {
						unitList =  build.getUnits();
						if(unitList != null && unitList.size() > 0 ){
							for (UnitDto unit : unitList) {
								tempMap = new HashMap<String,String>();
								tempMap.put("building_id", unit.getId());
								tempMap.put("building_name", build.getName()+unit.getUnitName());
								resultList.add(tempMap);
							}
						}else{
							tempMap = new HashMap<String,String>();
							tempMap.put("building_id", build.getId());
							tempMap.put("building_name", build.getName());
							resultList.add(tempMap);
						}
					}
				}
			}
		}
		
		resultMap.put("result_list", resultList);
		returnJsonOfMobile(response, resultMap, true, "查询成功", null);
		logger.info("method:getQfangBuilding;返回结果：" + resultMap);
	}
	
	

	/**
	 * Q房估价-房号查询
	 * 
	 * @param params
	 * @param request
	 * @param response
	 * @throws TException
	 * @throws ThriftException
	 */
	@RequestMapping(value = "/getQfangRoom.action", method = RequestMethod.POST, 
			headers = {"Content-Type=application/json;charset=utf-8","Accept-Version=api_v1" })
	public void getQfangRoom(@RequestBody JSONObject params,HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("method:getQfangRoom;入参：" + params);
		Map resultMap = new HashMap<String, Object>();

		//楼盘/小区名
		String buildingId = params.getString("building_id");
		//城市
		String cityCode = params.getString("city_code");
 
		if (StringUtil.isBlank(cityCode, buildingId)) {
			returnJsonOfMobile(response, false, "参数不合法",ExceptionCode.MOBILE_BAD_REQUEST);
			return;
		}
		RoomSearchRequest qfangRequest = new RoomSearchRequest();
		qfangRequest.setCity(QfangUtil.getQfangCityDtoEnum(cityCode));
		qfangRequest.setBuildingId(buildingId);
		RoomSearchResponse  qfangResponse =qfangApiService.getQfangRoom(qfangRequest);
		
		List resultList = new ArrayList<>();
		if(qfangResponse != null ){
			System.out.println("qfangResponse:"+JSONObject.toJSONString(qfangResponse));
			if(qfangResponse.isSuccess()){
				List<RoomDto>  roomList = qfangResponse.getItems() ; 
				if(roomList != null  && roomList.size()> 0){
					
					Map<String, Object> tempMap = null;
					for (RoomDto room : roomList) {
						tempMap = new HashMap<String,Object>();
						tempMap.put("room_id", room.getId());
						tempMap.put("room_number", room.getRoomNumber());
						tempMap.put("build_area", room.getBuildArea());
						tempMap.put("direction", room.getDirection() == null ? "" : room.getDirection().getValue());
						tempMap.put("floor_num", room.getFloor() == null ? "" : room.getFloor().getFloorNum());
						tempMap.put("max_floor", room.getMaxFloor());
						tempMap.put("bed_room", room.getBedRoom());
						tempMap.put("living_room", room.getLivingRoom());
						resultList.add(tempMap);
					}
				}
			}
		}
		resultMap.put("result_list", resultList);
		returnJsonOfMobile(response, resultMap, true, "查询成功", null);
		logger.info("method:getQfangRoom;返回结果：" + resultMap);
	}
	
	
	/**
	 * Q房估价-立即估价
	 * 
	 * @param params
	 * @param request
	 * @param response
	 * @throws TException
	 * @throws ThriftException
	 */
	@RequestMapping(value = "/getQfangHousePrice.action", method = RequestMethod.POST, 
			headers = {"Content-Type=application/json;charset=utf-8","Accept-Version=api_v1" })
	public void getQfangHousePrice(@RequestBody JSONObject params,HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("method:getQfangHousePrice;入参：" + params);
		Map resultMap = new HashMap<String, Object>();

		//查询方式 1：模糊查询，2：精确查询
		String searchType = params.getString("search_type");
		//城市
		String cityCode = params.getString("city_code");
		//楼盘/小区id
		String gardenId = params.getString("garden_id");
		//楼盘/单元id
		String buildingId = params.getString("building_id");
		//房号
		String roomNumber = params.getString("room_number");
		//面积
		String buildArea = params.getString("build_area");
		//朝向
		String directionEnum = params.getString("direction");
		//楼层
		String floor = params.getString("floor_num");
		//最高层
		String maxFloor = params.getString("max_floor");
		//室
		String bedRoom = params.getString("bed_room");
		//厅
		String livingRoom = params.getString("living_room");
		
		if (StringUtil.isBlank(searchType,cityCode,gardenId)) {
			returnJsonOfMobile(response, false, "参数不合法",ExceptionCode.MOBILE_BAD_REQUEST);
			return;
		}
		
		if(searchType.equals("1")){
			if (StringUtil.isBlank(buildArea)) {
				returnJsonOfMobile(response, false, "参数不合法",ExceptionCode.MOBILE_BAD_REQUEST);
				return;
			}
		}else{
			if (StringUtil.isBlank(buildingId,roomNumber)) {
				returnJsonOfMobile(response, false, "参数不合法",ExceptionCode.MOBILE_BAD_REQUEST);
				return;
			}
		}
		
		EvaluationHousePriceGuidingRequest qfangRequest = new EvaluationHousePriceGuidingRequest(); 
		
		//调用方标识 默认CLOUD("数据平台","3")
		qfangRequest.setCallerIdentifierEnum(EvaluationApiCallerIdentifierEnum.CLOUD);
		qfangRequest.setCity(QfangUtil.getQfangCityDtoEnum(cityCode));
		qfangRequest.setGardenId(gardenId);
		if(!StringUtil.isBlank(buildingId)){
			qfangRequest.setBuilding(buildingId);
		}
		if(!StringUtil.isBlank(roomNumber)){
			qfangRequest.setRoomNumber(roomNumber);
		}
		if(!StringUtil.isBlank(buildArea)){
			qfangRequest.setBuildArea(Integer.parseInt(buildArea));
		}
		
		//朝向
		if(!StringUtil.isBlank(directionEnum)){
			qfangRequest.setDirectionEnum(QfangUtil.getQfangDirectionEnum(directionEnum));
		}
		
		if(!StringUtil.isBlank(floor)){
			qfangRequest.setFloor(Integer.parseInt(floor));
		}
		if(!StringUtil.isBlank(maxFloor)){
			qfangRequest.setMaxFloor(Integer.parseInt(maxFloor));
		}
		if(!StringUtil.isBlank(bedRoom)){
			qfangRequest.setBedRoom(Integer.parseInt(bedRoom));
		}
		if(!StringUtil.isBlank(livingRoom)){
			qfangRequest.setLivingRoom(Integer.parseInt(livingRoom));
		}
		
		EvaluationHousePriceGuidingResponse qfangResponse = qfangApiService.getQfangHousePrice(qfangRequest);
 
		Map<String,Object> result = new HashMap<String,Object>();
		
		if(qfangResponse != null ){
			System.out.println("qfangResponse:"+JSONObject.toJSONString(qfangResponse));
			if(qfangResponse.isSuccess()){
				result.put("qfang_price", qfangResponse.getEvaluationHousePriceGuiding().getQfangPrice());
				result.put("total_qfang_price", qfangResponse.getEvaluationHousePriceGuiding().getTotalQfangPrice());
			}
		}
		resultMap.put("result", result);
		returnJsonOfMobile(response, resultMap, true, "查询成功", null);
		logger.info("method:getQfangHousePrice;返回结果：" + resultMap);
	}
	
	
	
	/**
	 * 房产自动查档
	 * @param params
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getHouseCheckDoc.action", method = RequestMethod.POST, 
			headers = {"Content-Type=application/json;charset=utf-8","Accept-Version=api_v1" })
	public void getHouseCheckDoc(@RequestBody JSONObject params,HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("method:getHouseCheckDoc;入参：" + params);
		
		Map resultMap = new HashMap<String, Object>();
		
		BaseClientFactory clientFactoryCheckDoc = null;
		try{
			clientFactoryCheckDoc = getFactory(BusinessModule.MODUEL_CHECKAPI, "HouseCheckDocService");
			HouseCheckDocService.Client clientCheckDoc =(HouseCheckDocService.Client) clientFactoryCheckDoc.getClient();

			//查档类型:1-分户,2-分栋
			String query_type = params.getString("query_type");
			//产权证书类型:1-房地产权证 书,2-不动产权证书
			String cert_type = params.getString("cert_type");
			//房产证号
			String cert_no = params.getString("cert_no");
			//（必填）身份证号/权利人姓名
			String person_info = params.getString("person_info");
			//(产权证书类型为2必填)不动产权证书时的年份
			String year = params.getString("year");
			
			if (StringUtil.isBlank(query_type,cert_type,cert_no,person_info)) {
				returnJsonOfMobile(response, false, "参数不合法",ExceptionCode.MOBILE_BAD_REQUEST);
				return;
			}
			
			if("2".equals(cert_type) && StringUtil.isBlank(year)){
				returnJsonOfMobile(response, false, "参数不合法",ExceptionCode.MOBILE_BAD_REQUEST);
				return;
			}
			
			Integer userId =  this.getShiroUser().getPid();
//			Integer userId =  2;
			JSONObject resultJson = new JSONObject();
			
			Map<String,String> queryMap = new HashMap<String,String>();
			
			queryMap.put("queryType", query_type);
			queryMap.put("certType", cert_type);
			queryMap.put("certNo", cert_no);
			queryMap.put("personInfo", person_info);
			queryMap.put("year", StringUtil.isBlank(year) ? "" : year);
			queryMap.put("userId", userId.toString());
			
			Date nowDate = new Date();
			//查档id
			int pid = 0;
			int tempPid= 0 ;
			
			//查询最后一条记录，判断时间查询
			HouseCheckDocDetails tempDoc = clientCheckDoc.getLastOneCheckDocDetails(queryMap);
			
			String configLimitTime = PropertiesUtil.getValue("check.doc.time.limit");
			if(StringUtil.isBlank(configLimitTime)){
				configLimitTime = "2";
			}
			
			if(tempDoc != null && tempDoc.getPid() > 0 ){
				pid = tempDoc.getHouseCheckDocId();
				tempPid = tempDoc.getHouseCheckDocId();
				//判断时间是否超过限制
				Date tempCreateTime = DateUtils.stringToDate(tempDoc.getCheckDocTime(), "yyyy-MM-dd HH:mm");
				long timeDiffs = DateUtils.hoursDiffs(nowDate, tempCreateTime);
				//判断是否查询时限
			 
				if(timeDiffs <= Long.parseLong(configLimitTime)){
					
					resultJson.put("pid", pid);
					resultMap.put("result", resultJson);
					returnJsonOfMobile(response,resultMap, false, "查询间隔时间"+configLimitTime+"小时", ExceptionCode.MOBILE_COMM_LIMIT);
			        return;
				}
			}
			
			//查询自动查档配置，并调用自动查档系统
			String apiCheckDocUrl = PropertiesUtil.getValue("api.check.doc.url");
			if(StringUtil.isBlank(apiCheckDocUrl)){
				returnJsonOfMobile(response, false, "系统未配置自动查档",ExceptionCode.MOBILE_SERVER_ERROR);
				return;
			}
			
			List<HttpRequestParam> paramsList = new ArrayList<HttpRequestParam>();
			HttpRequestParam param1 = new HttpRequestParam("queryType",query_type);
			HttpRequestParam param2 = new HttpRequestParam("certType",cert_type);
			HttpRequestParam param3 = new HttpRequestParam("certNo",cert_no);
			HttpRequestParam param4 = new HttpRequestParam("personInfo",person_info);
			HttpRequestParam param5 = new HttpRequestParam("year",StringUtil.isBlank(year) ? "" : year.replace("粤",""));
			
			paramsList.add(param1);
			paramsList.add(param2);
			paramsList.add(param3);
			paramsList.add(param4);
			paramsList.add(param5);
			
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.openConnection(60000,60000);
			//
			String apiResultStr = httpUtils.executeHttpPost(apiCheckDocUrl, paramsList,"UTF-8");
			httpUtils.closeConnection();
			
			logger.info(">>>>>>getHouseCheckDoc apiResultStr:"+apiResultStr);
			
			if(StringUtil.isBlank(apiResultStr)){
				returnJsonOfMobile(response, false, "查询失败",ExceptionCode.MOBILE_SERVER_ERROR);
				return;
			}
			
			JSONObject apiResultJson = JSONObject.parseObject(apiResultStr);
			Map<String,Object> result = new HashMap<String,Object>();
			String msg = apiResultJson.getString("msg");
			String code = apiResultJson.getString("code");
			JSONObject dataJson = apiResultJson.getJSONObject("data");
			
			Timestamp nowTime = new Timestamp(new Date().getTime());
			
			//添加查档和查档详情
			HouseCheckDoc insertCheckDoc = new HouseCheckDoc();
			insertCheckDoc.setUserId(userId);
			insertCheckDoc.setQueryType(Integer.parseInt(query_type));
			insertCheckDoc.setCertType(Integer.parseInt(cert_type));
			insertCheckDoc.setCertNo(cert_no);
			insertCheckDoc.setYear(year);
			insertCheckDoc.setPersonInfo(person_info);				
			insertCheckDoc.setIsDel(Constants.COMM_NO);
			insertCheckDoc.setCreateTime(nowTime.toString());
			insertCheckDoc.setUpdateTime(nowTime.toString());
			if(pid > 0 ){
				insertCheckDoc.setPid(pid);
			}
			
			String checkDocTime = DateUtils.dateToString(nowDate, "yyyy-MM-dd HH:mm");
			
			HouseCheckDocDetails insertDetails = new HouseCheckDocDetails();
			insertDetails.setCheckDocTime(checkDocTime);
			insertDetails.setHouseCheckDocId(pid);
			insertDetails.setCheckStatus(Constants.COMM_NO);
			String checkDocContent = "";
			
			if("ok".equals(code)){
				insertDetails.setCheckStatus(Constants.COMM_YES);
				if(dataJson != null && dataJson.get("result")!= null){
					checkDocContent = dataJson.getString("result");
					insertDetails.setCheckDocContent(checkDocContent);
				}
			}
			
			insertCheckDoc.setDetailsList(Arrays.asList(insertDetails));
			//添加到数据库,如果存在则不增加
			pid = clientCheckDoc.addHouseCheckDoc(insertCheckDoc);
			
			if(!"ok".equals(code)){
				if(tempPid > 0){
					resultJson.put("pid", pid);
					resultMap.put("result", resultJson);
					returnJsonOfMobile(response,resultMap, false, "查询失败", ExceptionCode.MOBILE_COMM_LIMIT);
					return;
				}
				returnJsonOfMobile(response, false, "查询失败",ExceptionCode.MOBILE_SERVER_ERROR);
				return;
			}
			
			resultJson.put("check_doc_time", checkDocTime);
			resultJson.put("check_doc_content", checkDocContent);
			resultJson.put("pid", pid);
			resultJson.put("time_limit", configLimitTime);
			
			resultMap.put("result", resultJson);
			
			logger.info("method:getHouseCheckDoc;返回结果：" + resultMap);
			returnJsonOfMobile(response, resultMap, true, "查询成功", null);
			return ;
			
		}catch(Exception e){
			logger.error(">>>>>>getHouseCheckDoc error",e);
			throw e;
		}finally{
			if (clientFactoryCheckDoc != null) {
				try {
					clientFactoryCheckDoc.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>getHouseCheckDoc clientCheckDocument destroy thrift error：",e);
				}
			}
		}
	}
	
	
	/**
	 * 房产查档记录列表
	 * @param params
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getHouseCheckDocList.action", method = RequestMethod.POST, 
			headers = {"Content-Type=application/json;charset=utf-8","Accept-Version=api_v1" })
	public void getHouseCheckDocList(@RequestBody JSONObject params,HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("method:getHouseCheckDocList;入参：" + params);
		
		Map resultMap = new HashMap<String, Object>();
		
		BaseClientFactory clientFactoryCheckDoc = null;
		try{
			clientFactoryCheckDoc = getFactory(BusinessModule.MODUEL_CHECKAPI, "HouseCheckDocService");
			HouseCheckDocService.Client clientCheckDoc =(HouseCheckDocService.Client) clientFactoryCheckDoc.getClient();

			//页数
			String page = params.getString("page");
			//行数
			String rows = params.getString("rows");
			
			if (StringUtil.isBlank(page,rows)) {
				returnJsonOfMobile(response, false, "参数不合法",ExceptionCode.MOBILE_BAD_REQUEST);
				return;
			}
			
			Date nowDate = new Date(); 
			String nowDateStr = DateUtils.dateToString(nowDate,"yyyy-MM-dd HH:mm");
			
			
			Integer userId =  this.getShiroUser().getPid();
//			Integer userId =  2;

			//验证数据库查询记录
			HouseCheckDoc queryCheckDoc = new HouseCheckDoc();
			queryCheckDoc.setUserId(userId);
			queryCheckDoc.setPage(Integer.parseInt(page));
			queryCheckDoc.setRows(Integer.parseInt(rows));
			queryCheckDoc.setIsDel(Constants.COMM_NO);
			
			List<HouseCheckDoc> tempList = clientCheckDoc.getHouseCheckDocs(queryCheckDoc);
			int totalCount = clientCheckDoc.getHouseCheckDocCount(queryCheckDoc);
			
			JSONArray jsonArray = new JSONArray();
			if(tempList != null && tempList.size() > 0 ){
				JSONObject tempJson = null;
				HouseCheckDoc tempLog = null;
				String checkDocContent = null;
				for (int i = 0 ; i < tempList.size(); i++) {
					tempJson = new JSONObject();
					tempLog = tempList.get(i);
					
					if(tempLog.getCheckStatus() ==Constants.COMM_NO &&  StringUtil.isBlank(checkDocContent)){
						checkDocContent = "查询失败";
					}
					tempJson.put("pid", tempLog.getPid());
					tempJson.put("query_type", tempLog.getQueryType());
					tempJson.put("cert_type", tempLog.getCertType());
					tempJson.put("cert_no", tempLog.getCertNo());
					tempJson.put("year", StringUtil.isBlank(tempLog.getYear())?"":tempLog.getYear());
					tempJson.put("person_info", tempLog.getPersonInfo());
					tempJson.put("check_doc_time", tempLog.getCheckDocTime());
					tempJson.put("check_status", tempLog.getCheckStatus());
					jsonArray.add(tempJson);
				}
			}
			
			resultMap.put("result_list", jsonArray);
			resultMap.put("page", page);
			resultMap.put("rows", rows);
			resultMap.put("totalPage", (int)Math.ceil(((double)(totalCount))/Integer.parseInt(rows)));
			
			logger.info("method:getHouseCheckDocList;返回结果：" + resultMap);
			returnJsonOfMobile(response, resultMap, true, "查询成功", null);
			
		}catch(Exception e){
			logger.error(">>>>>>getHouseCheckDocList error",e);
			throw e;
		}finally{
			if (clientFactoryCheckDoc != null) {
				try {
					clientFactoryCheckDoc.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>getHouseCheckDocList clientCheckDocument destroy thrift error：",e);
				}
			}
		}
	}
	
	
	
	/**
	 * 房产查档记录详情
	 * @param params
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getHouseCheckDocDetails.action", method = RequestMethod.POST, 
			headers = {"Content-Type=application/json;charset=utf-8","Accept-Version=api_v1" })
	public void getHouseCheckDocDetails(@RequestBody JSONObject params,HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("method:getHouseCheckDocDetails;入参：" + params);
		
		Map resultMap = new HashMap<String, Object>();
		
		BaseClientFactory clientFactoryCheckDoc = null;
		try{
			clientFactoryCheckDoc = getFactory(BusinessModule.MODUEL_CHECKAPI, "HouseCheckDocService");
			HouseCheckDocService.Client clientCheckDoc =(HouseCheckDocService.Client) clientFactoryCheckDoc.getClient();

			//id
			String pid = params.getString("pid");
			
			if (StringUtil.isBlank(pid)) {
				returnJsonOfMobile(response, false, "参数不合法",ExceptionCode.MOBILE_BAD_REQUEST);
				return;
			}
			
			Integer userId =  this.getShiroUser().getPid();
//			Integer userId =  2;
			
			Date nowDate = new Date(); 
			String nowDateStr = DateUtils.dateToString(nowDate,"yyyy-MM-dd HH:mm");
			
			HouseCheckDoc tempHouseCheckDoc = clientCheckDoc.getHouseCheckDocById(Integer.parseInt(pid));
			//查询详情列是有
			HouseCheckDocDetails queryDetails = new HouseCheckDocDetails();
			queryDetails.setHouseCheckDocId(Integer.parseInt(pid));
			List<HouseCheckDocDetails> tempList = clientCheckDoc.getCheckDocDetailsList(queryDetails);
			
			if(tempHouseCheckDoc == null || tempHouseCheckDoc.getPid() == 0 ){
				returnJsonOfMobile(response, false, "数据不存在",ExceptionCode.MOBILE_COMM_NO_EXIST);
				return;
			}

			String configLimitTime = PropertiesUtil.getValue("check.doc.time.limit");
			if(StringUtil.isBlank(configLimitTime)){
				configLimitTime = "2";
			}
			
			JSONObject resultJson = new JSONObject();
			
			resultJson.put("pid", tempHouseCheckDoc.getPid());
			resultJson.put("query_type", tempHouseCheckDoc.getQueryType());
			resultJson.put("cert_type", tempHouseCheckDoc.getCertType());
			resultJson.put("cert_no", tempHouseCheckDoc.getCertNo());
			resultJson.put("year", StringUtil.isBlank(tempHouseCheckDoc.getYear())?"":tempHouseCheckDoc.getYear());
			resultJson.put("person_info", tempHouseCheckDoc.getPersonInfo());
			resultJson.put("is_check", Constants.COMM_NO);
			resultJson.put("time_limit", configLimitTime);
			
			JSONArray deatilsArray = new JSONArray();
			if(tempList != null ){
				HouseCheckDocDetails tempDetails = null;
				JSONObject tempJson = null;
				for(int i= 0 ; i < tempList.size() ; i ++){
					tempDetails = tempList.get(i);
					//判断是否能再次查询
					if( i  == 0 ){
						//判断时间是否超过限制
						Date tempCreateTime = DateUtils.stringToDate(tempDetails.getCheckDocTime(), "yyyy-MM-dd HH:mm");
						long timeDiffs = DateUtils.hoursDiffs(nowDate, tempCreateTime);
						//判断是否查询时限
						if(timeDiffs > Long.parseLong(configLimitTime)){
							resultJson.put("is_check", Constants.COMM_YES);
						}
					}
					tempJson = new JSONObject();
					tempJson.put("check_doc_time", tempDetails.getCheckDocTime());
					tempJson.put("check_doc_content", StringUtil.isBlank(tempDetails.getCheckDocContent()) ? "查询失败" :tempDetails.getCheckDocContent());
					deatilsArray.add(tempJson);
				}
			}
			
			resultJson.put("details", deatilsArray);
			resultMap.put("result", resultJson);
			
			logger.info("method:getHouseCheckDocDetails;返回结果：" + resultMap);
			
			returnJsonOfMobile(response, resultMap, true, "查询成功", null);
			
		}catch(Exception e){
			logger.error(">>>>>>getHouseCheckDocDetails error",e);
			throw e;
		}finally{
			if (clientFactoryCheckDoc != null) {
				try {
					clientFactoryCheckDoc.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>getHouseCheckDocDetails clientCheckDocument destroy thrift error：",e);
				}
			}
		}
	}
	/**
	 * 检查app版本接口，如果有新版本返回版本信息，没有则不返回
	 *@author:liangyanjun
	 *@time:2016年11月1日下午4:37:20
	 *@param params
	 *@param request
	 *@param response
	 *@throws Exception
	 */
	@RequestMapping(value="/checkAppVersion",method=RequestMethod.POST,headers={"Content-Type=application/json;charset=utf-8","Accept-Version=api_v1"})
    @ResponseBody
    public void checkAppVersion(@RequestBody JSONObject params, HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("method:checkAppVersion;入参:"+params);
        String appVersion = params.getString("app_version");
        Integer systemPlatform = (Integer)params.get("system_platform");
        Integer appCategory = (Integer)params.get("category");
        if (StringUtil.isBlank(appVersion)||systemPlatform<=0||appCategory<=0) {
            returnJsonOfMobile(response, false, "参数不合法", ExceptionCode.MOBILE_BAD_REQUEST);
            return;
        }
        Map<Object,Object> resultMap = new HashMap<Object,Object>();
        BaseClientFactory appFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysAppVersionInfoService");
        SysAppVersionInfoService.Client appClient = (SysAppVersionInfoService.Client) appFactory.getClient();
        SysAppVersionInfo query=new SysAppVersionInfo();
        query.setAppVersion(appVersion);
        query.setSystemPlatform(systemPlatform);
        query.setAppCategory(appCategory);
        //检查版本
        SysAppVersionInfo latestAppVersionInfo = appClient.checkVersion(query);
        if (latestAppVersionInfo.getPid()>0) {
            Map<String,Object> appMap = new HashMap<String,Object>();
            BizFile file = latestAppVersionInfo.getFile();
            appMap.put("name", latestAppVersionInfo.getAppName());//版本名称
            appMap.put("url", file.getFileUrl());//载链接
            appMap.put("version", latestAppVersionInfo.getAppVersion());//版本号
            appMap.put("create_date", latestAppVersionInfo.getCreaterDate());//创建日期
            appMap.put("size", file.getFileSize());//app大小
            appMap.put("update_status", latestAppVersionInfo.getCoercivenessUpgradesStatus());//强制升级状态：1=不强制升级，2强制升级
            appMap.put("description", latestAppVersionInfo.getAppDescription());//版本描述
            resultMap.put("result", appMap);
        }
        returnJsonOfMobile(response, resultMap, true, "查询成功", null);
    }
	
	/**
	 * 获取合作机构数据
	 * @param params
	 * @param request
	 * @param response
//	 * @throws TException
	 * @throws ThriftException
	 */
	@RequestMapping(value="/getOrgList",method=RequestMethod.POST,headers={"Content-Type=application/json;charset=utf-8","Accept-Version=api_v1"})
    @ResponseBody
	public void getOrgList(@RequestBody JSONObject params,
			HttpServletRequest request, HttpServletResponse response)
			throws TException, ThriftException {
		logger.info("method:getOrgList;入参：" + params);
		int userId = checkIntValue((Integer) params.get("user_id"));// 用户ID
		if (userId <= 0) {
			logger.error("请求数据不合法：" + params);
			returnJsonOfMobile(response, false, "参数不合法", null);
			return;
		}
		List<OrgCooperatCompanyApply> list = new ArrayList<OrgCooperatCompanyApply>();
		BaseClientFactory orgFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgCooperatCompanyApplyService");
		OrgCooperatCompanyApplyService.Client client = (OrgCooperatCompanyApplyService.Client) orgFactory.getClient();
		list = client.getOrgAssetsList();
		List<Map> resultList = new ArrayList<Map>();
		Map result = new HashMap<>();
		if(list != null && list.size()>0){
			for(OrgCooperatCompanyApply applyInfo :list){
				Map<String, Object> orgMap = new HashMap<String, Object>();
				orgMap.put("pid", applyInfo.getOrgId());
				orgMap.put("look_desc", applyInfo.getOrgName());
				orgMap.put("fee_rate", applyInfo.getRate());
		    	resultList.add(orgMap);
	       }
	       result.put("result_list", resultList);
	       returnJsonOfMobile(response, result, true, "查询成功", null);
	       logger.info("合作机构列表查询成功：result:" + result);
		}
	}
	
	/**
	 * 查询可申请要件借出的列表
	 * @param params
	 * @param request
	 * @param response
	 * @throws TException
	 * @throws ThriftException
	 */
	@RequestMapping(value="/getProjectListByElement",method=RequestMethod.POST,headers={"Content-Type=application/json;charset=utf-8","Accept-Version=api_v1"})
    @ResponseBody
	public void getProjectListByElement(@RequestBody JSONObject params,
			HttpServletRequest request, HttpServletResponse response)
			throws TException, ThriftException {
		logger.info("method:getProjectListByElement;入参：" + params);
		int userId = getShiroUser().getPid();// 用户ID
		int productId = checkIntValue((Integer) params.get("product_id"));
		int applyStatus = checkIntValue((Integer) params.get("apply_status"));
			   
		String projectName = checkString((String) params.get("project_name"));
		int page = params.get("page") == null ? 1 : (Integer) params.get("page");
		int rows = params.get("rows") == null ? 10 : (Integer) params.get("rows");
		
		if (userId <= 0) {
			logger.error("请求数据不合法：" + params);
			returnJsonOfMobile(response, false, "参数不合法", null);
			return;
		}
		
		// 查询用户信息
	    SysUserService.Client sysUserService = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");
        SysUser sysUser = sysUserService.getSysUserByPid(userId);
        ProjectService.Client projectService = (ProjectService.Client) getService(
				BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		Project query = new Project();
		query.setUserIds(getDataUserIds(sysUser));//数据权限
		query.setIsChechan(0);//未撤单
		query.setProductType(2);//赎楼类产品
		query.setPage(page);
		query.setRows(rows);
		query.setProductId(productId);
		query.setProjectName(projectName);
		// 创建集合
		List<GridViewDTO> list = projectService.getAllForeProjects(query);
		Map result = new HashMap<>();
		List<Map> resultList = new ArrayList<Map>();
	    for(GridViewDTO dto : list){
	    	Map<String, Object> projectMap = new HashMap<String, Object>();
	    	projectMap.put("pid", stringToInt(dto.getPid()));
	    	projectMap.put("project_name", dto.getValue23());
	    	projectMap.put("product_name", dto.getValue3());
	    	projectMap.put("loan_money", stringToDouble(dto.getValue7()));
	    	projectMap.put("creater_date", dto.getValue26());
	    	projectMap.put("apply_status", 1);//可申请
	    	resultList.add(projectMap);
	    }
		
		result.put("result_list", resultList);
        returnJsonOfMobile(response, result, true, "查询成功", null);
        logger.info("贷前列表列表查询成功：result:" + result);
		
	}
   /**
    * 查询产品列表
    * @param params
    * @param request
    * @param response
    * @throws TException
    * @throws ThriftException
    */
   @RequestMapping(value = "/queryProductList", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
   @ResponseBody
   public void queryProductList(@RequestBody Map params, HttpServletRequest request, HttpServletResponse response) throws TException, ThriftException {
      logger.info("method:queryProductList;入参：" + params);
      Map resultMap = new HashMap<String, Object>();
      int userId = checkIntValue((Integer) params.get("user_id"));// 用户id
      if (userId < 1) {
         returnJsonOfMobile(response, false, "参数不合法", null);
         return;
      }
      // 查询用户信息
      SysUserService.Client sysUserService = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");
      SysUser sysUser = sysUserService.getSysUserByPid(userId);
      int orgId = sysUser.getOrgId();
      // 查询产品
      ProductService.Client productServiceClient = (ProductService.Client) getService(BusinessModule.MODUEL_PRODUCT, "ProductService");
      Product productQuery = new Product();
      productQuery.setCityIds(getCityLists(orgId));// 设置城市的ID
      productQuery.setStatus(1);// 有效的产品
      productQuery.setPage(1);
      productQuery.setRows(1000);
      List<GridViewDTO> productList = productServiceClient.getAllProduct(productQuery);
      List<Map> resultList = new ArrayList<Map>();
      for (GridViewDTO gridViewDTO : productList) {
         Map<Object, Object> map = new HashMap<Object, Object>();
         map.put("pid", Integer.parseInt(gridViewDTO.getPid()));
         map.put("product_num", checkString(gridViewDTO.getValue1()));
         map.put("product_type", Integer.parseInt(gridViewDTO.getValue8()));
         map.put("product_type_name", checkString(gridViewDTO.getValue2()));
         map.put("product_name", checkString(gridViewDTO.getValue3()));
         map.put("loan_work_process_id", checkString(gridViewDTO.getValue7()));
         resultList.add(map);
      }
      resultMap.put("result_list", resultList);
      
      returnJsonOfMobile(response, resultMap, true, "查询成功", null);
      logger.info("method:queryProductList;返回结果：" + resultMap);
   }
	
   /**
    * 不分页查询客户列表
    * @param params
    * @param request
    * @param response
    * @throws TException
    * @throws ThriftException
    */
   @RequestMapping(value = "/queryCustomerInfoList", method = RequestMethod.POST, headers = { "Content-Type=application/json;charset=utf-8", "Accept-Version=api_v1" })
   @ResponseBody
   public void queryCustomerInfoList(@RequestBody Map params, HttpServletRequest request, HttpServletResponse response) throws TException, ThriftException {
      logger.info("method:queryCustomerList;入参：" + params);
      Map resultMap = new HashMap<String, Object>();
      int userId = checkIntValue((Integer) params.get("user_id"));// 用户id
      if (userId < 1) {
         returnJsonOfMobile(response, false, "参数不合法", null);
         return;
      }
      // 查询用户信息
      SysUserService.Client sysUserService = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");
      SysUser sysUser = sysUserService.getSysUserByPid(userId);
   // 查询客户列表信息
      CusPerBaseDTO query = new CusPerBaseDTO();
      query.setUserIds(getDataUserIds(sysUser));
      query.setPage(1);
      query.setRows(1000);
      CusPerService.Client client = (CusPerService.Client) getFactory(BusinessModule.MODUEL_CUSTOMER, "CusPerService").getClient();
      List<GridViewDTO> cusPers = client.getCusPerBases(query);
      List<Map> customerList = new ArrayList<Map>();
      for(GridViewDTO dto : cusPers){
        Map<String, Object> customerMap = new HashMap<String, Object>();
        customerMap.put("pid", stringToInt(dto.getPid()));
        customerMap.put("customer_name", dto.getValue2());
        customerMap.put("phone_num", dto.getValue11());
        customerMap.put("card_no", dto.getValue5());
        customerMap.put("comm_address", dto.getValue13());
       
        List<Map> relationList = new ArrayList<Map>();
         //关系人
         List<CusDTO> relations = client.getNoSpouseList(stringToInt(dto.getPid()));
         if(relations != null && relations.size()>0){
              for(CusDTO relation : relations){
                   if(relation.getRelationVal() >2){
                         Map<Object, Object> relationmap= new LinkedHashMap<Object, Object>();
                         relationmap.put("relation_id",checkIntValue(relation.getPid()));
                         relationmap.put("relation_name",checkString(relation.getChinaName()));
                         relationmap.put("relation_type",checkIntValue(relation.getRelationVal()));
                         relationmap.put("relation_card_no",checkString(relation.getCertNumber()));
                         relationmap.put("relation_phone_num",checkString(relation.getPerTelephone()));
                         //relationmap.put("relation_address",checkString(relation.getCommAddr()));
                         relationmap.put("proportion_property",relation.getProportionProperty());
                         relationList.add(relationmap);
                   }
              }
         }
         customerMap.put("relation_map", relationList);
        customerList.add(customerMap);
      }
      resultMap.put("customerList", customerList);

      
      returnJsonOfMobile(response, resultMap, true, "查询成功", null);
      logger.info("method:queryCustomerList;返回结果：" + resultMap);
   }
}

