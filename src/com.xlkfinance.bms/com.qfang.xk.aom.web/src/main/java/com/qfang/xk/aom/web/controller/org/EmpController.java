package com.qfang.xk.aom.web.controller.org;

import java.util.ArrayList;
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

import com.qfang.xk.aom.rpc.system.OrgEmpIndex;
import com.qfang.xk.aom.rpc.system.OrgFnPermissionIndex;
import com.qfang.xk.aom.rpc.system.OrgSysMenuInfo;
import com.qfang.xk.aom.rpc.system.OrgSysMenuInfoService;
import com.qfang.xk.aom.rpc.system.OrgUserInfo;
import com.qfang.xk.aom.rpc.system.OrgUserMenuInfo;
import com.qfang.xk.aom.rpc.system.OrgUserMenuInfoService;
import com.qfang.xk.aom.rpc.system.OrgUserService;
import com.qfang.xk.aom.web.controller.BaseController;
import com.qfang.xk.aom.web.util.MD5Util;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.OrgSysLogTypeConstant;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;

/**
 * 
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年7月11日下午2:06:08 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 员工管理<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Controller
@RequestMapping("/empController")
public class EmpController extends BaseController {
   private Logger logger = LoggerFactory.getLogger(EmpController.class);
   private String PATH = "/org/emp";

   @ExceptionHandler
   public String exception(HttpServletRequest request, HttpServletResponse response, Exception e) {
      logger.error(ExceptionUtil.getExceptionMessage(e), e);
      fillReturnJson(response, false, "出现未知异常,请与系统管理员联系!");
      return null;
   }

   /**
    * 跳转到员工列表
    *@author:liangyanjun
    *@time:2016年7月11日下午2:10:03
    *@param model
    *@return
    */
   @RequestMapping("/toEmpIndex")
   public String toEmpIndex(ModelMap model) {
      return PATH + "/emp_index";
   }
   /**
    * 跳转到权限管理列表页面
    *@author:liangyanjun
    *@time:2016年7月26日上午10:03:59
    *@param model
    *@return
    */
   @RequestMapping("/toFnPermissionIndex")
   public String toFnPermissionIndex(ModelMap model) {
      return PATH + "/fn_permission_index";
   }

   /**
    * 跳转到员工修改页面
    *@author:liangyanjun
    *@time:2016年7月11日下午5:55:22
    *@param model
    *@return
    * @throws TException 
    * @throws ThriftServiceException 
    */
   @RequestMapping("/toEmpAddOrUpdate")
   public String toEmpAddOrUpdate(ModelMap model, Integer pid) throws ThriftServiceException, TException {
      if (pid != null && pid > 0) {
         OrgUserService.Client userService = (OrgUserService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_SYSTEM, "OrgUserService");
         OrgUserInfo userInfo = userService.getById(pid);
         model.put("userInfo", userInfo);
      }
      return PATH + "/emp_add_or_update";
   }
    /**
     * 根据条件查询员工列表（分页查询）
     *@author:liangyanjun
     *@time:2016年7月11日下午5:55:22
     *@param query
     *@param response
     *@throws TException
     */
   @RequestMapping("/emp_list")
   public void empList(OrgEmpIndex query, HttpServletResponse response) throws TException {
      OrgUserInfo loginUser = getLoginUser();
      query.setOrgId(loginUser.getOrgId());
      OrgUserService.Client userService = (OrgUserService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_SYSTEM, "OrgUserService");
      List<OrgEmpIndex> list = userService.findOrgEmpIndexPage(query);
      int total = userService.getOrgEmpIndexTotal(query);
      // 输出
      outputPage(query.getRows(), response, list, total);
   }
   /**
    * 根据条件查询权限列表（分页查询）
    *@author:liangyanjun
    *@time:2016年7月11日下午5:55:22
    *@param query
    *@param response
    *@throws TException
    */
   @RequestMapping("/fnPermissionList")
   public void fnPermissionList(OrgFnPermissionIndex query, HttpServletResponse response) throws TException {
      OrgUserInfo loginUser = getLoginUser();
      query.setOrgId(loginUser.getOrgId());
      query.setUserType(1);//只查询属于机构的菜单
      OrgUserService.Client userService = (OrgUserService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_SYSTEM, "OrgUserService");
      List<OrgFnPermissionIndex> list = userService.findOrgFnPermissionIndexPage(query);
      int total = userService.getOrgFnPermissionIndexTotal(query);
      // 输出
      outputPage(query.getRows(), response, list, total);
   }

   /**
    * 添加或更新员工信息
    *@author:liangyanjun
    *@time:2016年7月12日上午10:28:49
    *@param userInfo
    *@param resp
    *@throws TException
    */
   @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
   public void orgRegister(OrgUserInfo userInfo,HttpServletRequest req, HttpServletResponse resp) throws TException {
      int pid = userInfo.getPid();
      String userName = userInfo.getUserName();// 用户名
      String phone = userInfo.getPhone();// 手机号码
      String realName = userInfo.getRealName();// 真实姓名
      String jobNo = userInfo.getJobNo();// 工号
      int status = userInfo.getStatus();// 状态,1表示启用,2表示禁用
      String deptName = userInfo.getDeptName();// 部门
      String email = userInfo.getEmail();// 邮箱
      String password = userInfo.getPassword();// 密码
      String remark = userInfo.getRemark();// 备注
      if (StringUtil.isBlank(userName, phone, realName, jobNo, deptName, email)) {
         fillReturnJson(resp, false, "参数不合法，请输入必填项");
         return;
      }

      OrgUserService.Client userClient = (OrgUserService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_SYSTEM, "OrgUserService");
      if (pid == 0) {
         if (StringUtil.isBlank(password)) {
            fillReturnJson(resp, false, "参数不合法，请输入必填项");
            return;
         }
         // 检查数据是否存在
         if (userClient.checkUserNameIsExist(userName)) {
            fillReturnJson(resp, false, "该登录名已存在！");
            return;
         }
         if (userClient.checkPhoneIsExist(phone)) {
            fillReturnJson(resp, false, "该手机号码已存在！");
            return;
         }
         if (userClient.checkPhoneIsExist(phone)) {
            fillReturnJson(resp, false, "该手机号码已存在！");
            return;
         }
         if (userClient.checkEmailIsExist(email)) {
             fillReturnJson(resp, false, "该邮箱已存在！");
             return;
         }
      }
      OrgUserInfo loginUser = getLoginUser();
      // 有id为更新操作，否则为新增操作
      if (pid > 0) {
         // 更新员工信息
         OrgUserInfo updateUserInfo = userClient.getById(pid);
         updateUserInfo.setRealName(realName);
         updateUserInfo.setJobNo(jobNo);
         updateUserInfo.setStatus(status);
         updateUserInfo.setDeptName(deptName);
         updateUserInfo.setRemark(remark);
         //updateUserInfo.setEmail(email);
         updateUserInfo.setUpdateId(loginUser.getPid());
         userClient.update(updateUserInfo);
         recordLog(OrgSysLogTypeConstant.LOG_TYPE_UPDATE, "修改机构员工信息：参数："+userInfo, req);
      } else {
         // 添加员工
         userInfo.setPassword(MD5Util.tltMd5(password));
         userInfo.setUserType(AomConstant.USER_TYPE_3);
         userInfo.setDateScope(AomConstant.DATE_SCOPE_1);
         userInfo.setOrgId(loginUser.getOrgId());
         userInfo.setUpdateId(loginUser.getPid());
         userInfo.setCreateId(loginUser.getPid());
         userClient.addOrgEmp(userInfo);
         recordLog(OrgSysLogTypeConstant.LOG_TYPE_UPDATE, "增加机构员工：参数："+userInfo, req);
      }
      fillReturnJson(resp, true, "提交成功");
   }
   /**
    * 修改员工的数据权限
    *@author:liangyanjun
    *@time:2016年7月12日下午4:17:54
    *@param pids
    *@param resp
    *@throws TException
    */
   @RequestMapping(value = "/updateDateScopeByIds", method = RequestMethod.POST)
   public void updateDateScopeByIds(String pids,int dateScope, HttpServletRequest req,HttpServletResponse resp) throws TException {
      if (StringUtil.isBlank(pids)) {
         fillReturnJson(resp, false, "参数不合法，请输入必填项");
         return;
      }
      List<Integer> idList = new ArrayList<Integer>();
      String[] tempIds = pids.split(",");
      for (String id : tempIds) {
         idList.add(Integer.parseInt(id));
      }
      OrgUserInfo loginUser = getLoginUser();
      int orgId = loginUser.getOrgId();
      
      OrgUserService.Client userClient = (OrgUserService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_SYSTEM, "OrgUserService");
      userClient.updateDateScopeByIds(idList, dateScope, orgId, AomConstant.USER_TYPE_3);
      recordLog(OrgSysLogTypeConstant.LOG_TYPE_UPDATE, "修改员工的数据权限：参数：idList="+idList+";dateScope="+dateScope, req);
      fillReturnJson(resp, true, "提交成功");
   }
   /**
    * 修改员工功能权限
    *@author:liangyanjun
    *@time:2016年8月2日下午2:26:21
    *@param fnPids
    *@param status
    *@param userId
    *@param resp
    *@throws TException
    */
   @RequestMapping(value = "/updateFnPermisseionByIds", method = RequestMethod.POST)
   public void updateFnPermisseionByIds(String fnPids,int status,int userId,HttpServletRequest req, HttpServletResponse resp) throws TException {
      if (StringUtil.isBlank(fnPids)||status<=0||userId<=0) {
         fillReturnJson(resp, false, "参数不合法，请联系管理员");
         return;
      }
      OrgUserInfo loginUser = getLoginUser();
      int orgId = loginUser.getOrgId();
      OrgSysMenuInfoService.Client orgSysMenuInfoServiceClient = (OrgSysMenuInfoService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_SYSTEM, "OrgSysMenuInfoService");
      OrgUserMenuInfoService.Client client = (OrgUserMenuInfoService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_SYSTEM, "OrgUserMenuInfoService");
      String[] tempIds = fnPids.split(",");
      if (status==1) {//1：取消授权。2：赋予权限
         for (String id : tempIds) {
            int fnId = Integer.parseInt(id);
            OrgUserMenuInfo orgUserMenuInfo=new OrgUserMenuInfo();
            orgUserMenuInfo.setMenuId(fnId);
            orgUserMenuInfo.setUserId(userId);
            orgUserMenuInfo.setOrgId(orgId);
            List<OrgUserMenuInfo> list = client.getAll(orgUserMenuInfo);
            if (list==null||list.isEmpty()) {
               continue;
            }
            client.deleteById(list.get(0).getPid());
            //如果有子菜单，则把子菜单也取消
            OrgSysMenuInfo childrenMenuQuery=new OrgSysMenuInfo();
            childrenMenuQuery.setParentId(fnId);
            List<OrgSysMenuInfo> childrenMenuList = orgSysMenuInfoServiceClient.getAll(childrenMenuQuery);
            for (OrgSysMenuInfo menu : childrenMenuList) {
                orgUserMenuInfo.setMenuId(menu.getPid());
                list = client.getAll(orgUserMenuInfo);
                if (list==null||list.isEmpty()) {
                   continue;
                }
                client.deleteById(list.get(0).getPid());
            }
         }
      }else{
         for (String id : tempIds) {
            int fnId = Integer.parseInt(id);
            OrgUserMenuInfo orgUserMenuInfo=new OrgUserMenuInfo();
            orgUserMenuInfo.setMenuId(fnId);
            orgUserMenuInfo.setUserId(userId);
            orgUserMenuInfo.setOrgId(orgId);
            List<OrgUserMenuInfo> list = client.getAll(orgUserMenuInfo);
            if (list!=null&&list.size()>0) {
               continue;
            }
            client.insert(orgUserMenuInfo);
            //查询菜单的父级id，如果有父级菜单，并且该用户没有父级菜单的权限，则增加父级菜单的权限
            OrgSysMenuInfo menuInfo = orgSysMenuInfoServiceClient.getById(fnId);
            int parentId = menuInfo.getParentId();
            if (parentId<=0) {
               continue;
            }
            orgUserMenuInfo.setMenuId(parentId);
            List<OrgUserMenuInfo> parentList = client.getAll(orgUserMenuInfo);
            if (parentList!=null&&parentList.size()>0) {
               continue;
            }
            client.insert(orgUserMenuInfo);
         }
      }
      recordLog(OrgSysLogTypeConstant.LOG_TYPE_UPDATE, "修改员工功能权限:参数：fnPids="+fnPids+";status="+status+";userId="+userId, req);
      fillReturnJson(resp, true, "提交成功");
      
   }
   /**
    * 重置员工密码为123456
    * 备注：只可以重置自己机构下的员工，其他机构的员工无权限重置密码
    *@author:liangyanjun
    *@time:2016年12月1日上午9:35:03
    *@param userId
    *@param req
    *@param resp
    *@throws TException
    */
   @RequestMapping(value = "/resetEmpPwd", method = RequestMethod.POST)
   public void resetEmpPwd(int userId,HttpServletRequest req, HttpServletResponse resp) throws TException {
      if (userId<=0) {
         fillReturnJson(resp, false, "参数不合法，请联系管理员");
         return;
      }
      OrgUserInfo loginUser = getLoginUser();
      OrgUserService.Client userClient = (OrgUserService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_SYSTEM, "OrgUserService");
      OrgUserInfo updateUserInfo = userClient.getById(userId);
      //判断员工是否属于自己的机构，不是则无权重置密码
      if (updateUserInfo.getOrgId()!=loginUser.getOrgId()) {
         fillReturnJson(resp, false, "无权限重置该员工密码！");
         return;
      }
      //判断用户类型是否为员工
      if (updateUserInfo.getUserType()!=AomConstant.USER_TYPE_3) {
         fillReturnJson(resp, false, "无权限重置该用户密码！");
         return;
      }
      //执行密码重置
      updateUserInfo.setPassword(MD5Util.tltMd5("123456"));
      updateUserInfo.setUpdateId(loginUser.getPid());
      userClient.update(updateUserInfo);
      fillReturnJson(resp, true, "密码重置成功，用户名："+updateUserInfo.getUserName());
      recordLog(OrgSysLogTypeConstant.LOG_TYPE_UPDATE, "密码重置成功，用户名："+updateUserInfo.getUserName(), req);
   }
}
