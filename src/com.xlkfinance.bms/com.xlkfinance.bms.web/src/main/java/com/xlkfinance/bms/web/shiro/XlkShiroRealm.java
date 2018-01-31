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
package com.xlkfinance.bms.web.shiro;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
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
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.system.SysPermission;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.rpc.system.SysUserService;
import com.xlkfinance.bms.web.constant.Constants;
import com.xlkfinance.bms.web.util.WebSysConfig;

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
		String service = new StringBuffer().append(webSysConfig.getRpcPackage()).append(".").append(serviceModuel).append(".").append(serviceName).toString();
		BaseClientFactory clientFactory = new ThriftBinaryClientFactory(webSysConfig.getThriftServerIp(), webSysConfig.getThriftServerPort(), webSysConfig.getThriftTimeout(), service);
		return clientFactory;
	}

	@Override
   protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		BaseClientFactory clientFactory = null;
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserService");
			ShiroUser shiroUser = (ShiroUser) principals.fromRealm(getName()).iterator().next();
			String userName = shiroUser.getUserName();
			SysUserService.Client client = (SysUserService.Client) clientFactory.getClient();
			if (userName != null) {
				// 查询用户授权信息
				List<SysPermission> permissionList = client.getSysUserPermissionByUserName(userName);

				if (permissionList != null && permissionList.size() > 0) {
					for (SysPermission sysPermission : permissionList) {
						String permisCode = sysPermission.getPermisCode();
                        info.addRole(permisCode);
						info.addStringPermission(permisCode);
					}
				}
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
		if (userName != null && !"".equals(userName)&& doCaptchaValidate(token) ) {
			BaseClientFactory clientFactory = null;
			try {
				clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserService");
				SysUserService.Client client = (SysUserService.Client) clientFactory.getClient();
				SysUser sysuser = client.querySysUserByName(userName);

				// 从数据库中取出user
				if (sysuser != null && sysuser.getPid() > 0) {
					Subject subject = SecurityUtils.getSubject();
					ShiroUser shiroUser = new ShiroUser(sysuser.getPid(), sysuser.getUserName(), sysuser.getRealName(), sysuser.getToken());
					subject.getSession().setAttribute(Constants.SHIRO_USER, shiroUser);
					return new SimpleAuthenticationInfo(shiroUser, sysuser.getPwd(), getName());
				}
			} catch (ThriftException e) {
				throw new AuthenticationException("连接服务器失败!");
			} catch (TException e) {
				throw new AuthenticationException("服务器请求失败!");
			} catch (Exception e) {
				throw new AuthenticationException(e);
			} finally {
				if (clientFactory != null) {
					try {
						clientFactory.destroy();
					} catch (ThriftException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
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
