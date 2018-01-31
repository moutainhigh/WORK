/**
 * @Title: XlkShiroRealm.java
 * @Package com.xlkfinance.bms.web.shiro
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月10日 下午6:10:52
 * @version V1.0
 */
package com.qfang.xk.aom.web.shiro;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.thrift.TException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.qfang.xk.aom.rpc.system.OrgUserInfo;
import com.qfang.xk.aom.rpc.system.OrgUserService;
import com.qfang.xk.aom.web.constants.Constants;
import com.qfang.xk.aom.web.util.WebSysConfig;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.util.StringUtil;

/**
 * @ClassName: XlkShiroRealm
 * @Description: ShiroRealm 实现
 * @author: Simon.Hoo
 * @date: 2014年12月15日 下午8:22:12
 */
public class XlkShiroRealm extends AuthorizingRealm {
   @Resource(name = "webSysConfig")
   private WebSysConfig webSysConfig;

   public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
      String service = new StringBuffer().append("com.qfang.xk.aom.rpc").append(".").append(serviceModuel).append(".").append(serviceName).toString();
      BaseClientFactory clientFactory = new ThriftBinaryClientFactory(webSysConfig.getThriftServerIp(), webSysConfig.getThriftServerPort(),
            webSysConfig.getThriftTimeout(), service);
      return clientFactory;
   }

   @Override
   protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
      BaseClientFactory clientFactory = null;
      SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
      try {
         clientFactory = getFactory(BusinessModule.MODUEL_ORG_SYSTEM, "OrgUserService");
         ShiroUser shiroUser = (ShiroUser) principals.fromRealm(getName()).iterator().next();
         String userName = shiroUser.getUserName();
         OrgUserService.Client client = (OrgUserService.Client) clientFactory.getClient();
         if (userName != null) {
            // 查询用户授权信息
            // List<SysPermission> permissionList = client.getSysUserPermissionByUserName(userName);
            //
            // if (permissionList != null && permissionList.size() > 0) {
            // for (SysPermission sysPermission : permissionList) {
            // info.addRole(sysPermission.getPermisCode());
            // }
            // }
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         if (clientFactory != null) {
            try {
               clientFactory.destroy();
            } catch (ThriftException e) {
               e.printStackTrace();
            }
         }
      }
      return info;
   }

   // 获取认证信息
   @Override
   protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {

      CaptchaUsernamePasswordToken token = (CaptchaUsernamePasswordToken) authcToken;

      // 通过表单接收的用户名
      String userName = token.getUsername();
      if (StringUtil.isBlank(userName)) {
         return null;
      }
      doCaptchaValidate(token);
      try {
         BaseClientFactory clientFactory = getFactory("system", "OrgUserService");
         OrgUserService.Client client = (OrgUserService.Client) clientFactory.getClient();
         // 从数据库中取出user
         OrgUserInfo sysuser = client.queryOrgUserByName(userName);
         if (sysuser == null || sysuser.getPid() == 0) {
            return null;
         }
         //检查用户状态是否有效
         if (sysuser.getStatus() == AomConstant.STATUS_DISABLED) {
            throw new DisabledAccountException("账户已禁用!");
         }
         //如果是机构或员工登录，机构状态禁用了将无法登录
         int userType = sysuser.getUserType();
         if (userType==AomConstant.USER_TYPE_1||userType==AomConstant.USER_TYPE_3) {
             int orgId = sysuser.getOrgId();
             OrgUserInfo org = client.getById(orgId);
             if (org.getStatus() == AomConstant.STATUS_DISABLED) {
               throw new DisabledAccountException("对不起你所属的机构已禁，账户暂时不能登录!");
             }
         }
         Subject subject = SecurityUtils.getSubject();
         ShiroUser shiroUser = new ShiroUser(sysuser.getPid(), sysuser.getUserName(), sysuser.getRealName(), null);
         subject.getSession().setAttribute(Constants.SHIRO_USER, shiroUser);
         subject.getSession().setAttribute(Constants.LOGIN_USER, sysuser);
         return new SimpleAuthenticationInfo(shiroUser, sysuser.getPassword(), getName());
      } catch (ThriftException e) {
         throw new AuthenticationException("连接服务器失败!");
      } catch (TException e) {
         throw new AuthenticationException("服务器请求失败!");
      }
   }

   /**
    * 更新用户授权信息缓存.
    */
   public void clearCachedAuthorizationInfo(String principal) {
      SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
      clearCachedAuthorizationInfo(principals);
   }

   /**
    * 清除所有用户授权信息缓存.
    */
   public void clearAllCachedAuthorizationInfo() {
      Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
      if (cache != null) {
         for (Object key : cache.keys()) {
            cache.remove(key);
         }
      }
   }

   // 验证码校验
   protected boolean doCaptchaValidate(CaptchaUsernamePasswordToken token) {
       HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
       String captcha = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
       if (captcha != null && !captcha.equalsIgnoreCase(token.getCaptcha())) {
       throw new IncorrectCaptchaException("验证码错误！");
       }
      return true;
   }

}
