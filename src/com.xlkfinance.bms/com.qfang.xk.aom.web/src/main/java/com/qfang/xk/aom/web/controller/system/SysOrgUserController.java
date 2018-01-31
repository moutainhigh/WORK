
package com.qfang.xk.aom.web.controller.system;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.qfang.xk.aom.rpc.system.OrgUserFileService;
import com.qfang.xk.aom.rpc.system.OrgUserInfo;
import com.qfang.xk.aom.rpc.system.OrgUserService;
import com.qfang.xk.aom.rpc.system.SmsValidateCodeInfoService;
import com.qfang.xk.aom.web.controller.BaseController;
import com.qfang.xk.aom.web.util.FileUtil;
import com.qfang.xk.aom.web.util.MD5Util;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.OrgSysLogTypeConstant;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.system.BizFile;
/**
 * 
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年7月11日下午2:06:43 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation：系统用户管理 <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Controller
@RequestMapping("/sysOrgUserController")
public class SysOrgUserController extends BaseController {
   private Logger logger = LoggerFactory.getLogger(SysOrgUserController.class);

   @ExceptionHandler
   public String exception(HttpServletRequest request, HttpServletResponse response, Exception e) {
      logger.error(ExceptionUtil.getExceptionMessage(e), e);
      fillReturnJson(response, false, "出现未知异常,请与系统管理员联系!");
      return null;
   }

   /**
    * 通过手机验证码设置新的密码（找回密码）
    *@author:liangyanjun
    *@time:2016年7月7日下午5:22:17
    *@param phone
    *@param msgCode
    *@param password
    *@param resp
    *@throws TException
    */
   @RequestMapping(value = "/ignore/forgetPassword", method = RequestMethod.POST)
   public void forgetPassword(String phone, String msgCode, String password, HttpServletResponse resp) throws TException {
      if (StringUtil.isBlank(phone, msgCode, password)) {
         fillReturnJson(resp, false, "参数不合法，请输入必填项");
         return;
      }
      SmsValidateCodeInfoService.Client smsService = (SmsValidateCodeInfoService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_SYSTEM,
            "SmsValidateCodeInfoService");
      OrgUserService.Client userService = (OrgUserService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_SYSTEM, "OrgUserService");

      boolean isSucc = smsService.validCode(phone, msgCode, AomConstant.VALIDATE_CODE_CATEGORY_2);
      if (!isSucc) {
         fillReturnJson(resp, false, "提交失败，请输入正确的验证码");
         return;
      }
      OrgUserInfo userInfo = userService.getUserByPhone(phone);
      if (userInfo==null||userInfo.getPid()<=0) {
          fillReturnJson(resp, false, "提交失败，手机号码不存在!");
          return;
      }
      userInfo.setPassword(MD5Util.tltMd5(password));
      userService.update(userInfo);//更新密码
      //验证码设置无效
      smsService.disabledMsg(phone, msgCode, AomConstant.VALIDATE_CODE_CATEGORY_2);
      fillReturnJson(resp, true, "提交成功");
   }
   
   /**
    * 检查用户名是否已存在
    * 存在返回true，不存在返回false
    *@author:liangyanjun
    *@time:2016年7月8日下午3:26:40
    *@param userName
    *@param pid
    *@param response
    * @throws TException 
    */
   @RequestMapping(value="/ignore/checkUserNameIsExist" , method=RequestMethod.POST)
   public void checkUserNameIsExist(String userName, int pid, HttpServletResponse resp) throws TException {
      if (StringUtil.isBlank(userName)) {
         fillReturnJson(resp, false, "参数不合法");
         return;
      }
      boolean flag = false;
      OrgUserService.Client client = (OrgUserService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_SYSTEM, "OrgUserService");
      OrgUserInfo userInfo = client.queryOrgUserByName(userName);
      if (userInfo.getPid() > 0) {
         flag = true;
         if (pid > 0 && userInfo.getPid() == pid) {
            flag=false;
         }
      }
      // 输出
      fillReturnJson(resp, flag, "查询成功");
   }
   
   /**
    * 检查手机号码是否已存在
    * 存在返回true，不存在返回false
    *@author:liangyanjun
    *@time:2016年7月8日下午3:30:03
    *@param phone
    *@param resp
    * @param pid 
    * @throws TException 
    */
   @RequestMapping(value="/ignore/checkPhoneIsExist" , method=RequestMethod.POST)
   public void checkPhoneIsExist(String phone , int pid, HttpServletResponse resp) throws TException{
      if (StringUtil.isBlank(phone)) {
         fillReturnJson(resp, false, "参数不合法");
         return;
      }
      boolean flag = false;
      OrgUserService.Client client = (OrgUserService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_SYSTEM, "OrgUserService");
      OrgUserInfo userInfo = client.getUserByPhone(phone);
      if (userInfo.getPid() > 0) {
         flag = true;
         if (pid > 0 && userInfo.getPid() == pid) {
            flag=false;
         }
      }
      // 输出
      fillReturnJson(resp, flag, "查询成功");
   }
   /**
    * 检查email是否已存在
    * 存在返回true，不存在返回false
    *@author:liangyanjun
    *@time:2016年7月8日下午3:30:03
    *@param email
    *@param resp
    * @throws TException 
    */
   @RequestMapping(value="/ignore/checkEmailIsExist" , method=RequestMethod.POST)
   public void checkEmailIsExist(String email , int pid, HttpServletResponse resp) throws TException{
      if (StringUtil.isBlank(email)) {
         fillReturnJson(resp, false, "参数不合法");
         return;
      }
      boolean flag = false;
      OrgUserService.Client client = (OrgUserService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_SYSTEM, "OrgUserService");
      OrgUserInfo userInfo = client.getUserByEmail(email);
      if (userInfo.getPid() > 0) {
         flag = true;
         if (pid > 0 && userInfo.getPid() == pid) {
            flag=false;
         }
      }
      // 输出
      fillReturnJson(resp, flag, "查询成功");
   }
   /**
    * 修改密码
    *@author:liangyanjun
    *@time:2016年7月13日上午9:27:45
    *@param oldPwd
    *@param newPwd
    *@param resp
    *@throws TException
    */
   @RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
   public void updatePwd(String oldPwd,String newPwd,HttpServletRequest req, HttpServletResponse resp) throws TException {
      if (StringUtil.isBlank(oldPwd,newPwd)) {
         fillReturnJson(resp, false, "参数不合法，请输入必填项");
         return;
      }
      //md5加密
      oldPwd=MD5Util.tltMd5(oldPwd);
      newPwd=MD5Util.tltMd5(newPwd);
      
      OrgUserInfo loginUser = getLoginUser();
      int pid = loginUser.getPid();
      OrgUserService.Client userClient = (OrgUserService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_SYSTEM, "OrgUserService");
      //
      OrgUserInfo updateUserInfo = userClient.getById(pid);
      if (!oldPwd.equals(updateUserInfo.getPassword())) {
         fillReturnJson(resp, false, "旧密码错误，请重新输入");
         return;
      }
      //设置新密码
      updateUserInfo.setPassword(newPwd);
      userClient.update(updateUserInfo);
      recordLog(OrgSysLogTypeConstant.LOG_TYPE_UPDATE, "修改密码", req);
      fillReturnJson(resp, true, "提交成功");
   }
   /**
    * 上传机构用户资料附件
    *@author:liangyanjun
    *@time:2016年9月1日上午11:00:25
    *@param req
    *@param resp
    *@throws Exception
    */
   @RequestMapping("/uploadUserFile")
   public void uploadUserFile(HttpServletRequest req, HttpServletResponse resp) throws Exception {
      OrgUserInfo loginUser = getLoginUser();
      int userId = loginUser.getPid();
      boolean checkAudit = checkAudit(loginUser);
      if (!checkAudit) {//检查是否已认证，未认证不能上传
         return;
      }
      Map<String, Object> resultMap = FileUtil.uploadFile(req, resp, "system", getUploadFilePath(), getFileSize(), getFileDateDirectory(), getUploadFileType());
      List<BizFile> bizFileList = (List<BizFile>) resultMap.get("bizFileList");
      if (bizFileList == null || bizFileList.isEmpty()) {
         return;
      }
      BaseClientFactory factory = getFactory(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_SYSTEM, "OrgUserFileService");
      OrgUserFileService.Client client = (OrgUserFileService.Client) factory.getClient();
      client.saveOrgUserFile(userId, bizFileList);
      recordLog(OrgSysLogTypeConstant.LOG_TYPE_UPLOAN, "上传机构用户资料附件文件：参数"+bizFileList, req);
      destroyFactory(factory);
   }
}
