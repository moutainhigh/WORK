/**
 * @Title: SysUserController.java
 * @Package com.xlkfinance.bms.web.controller.system
 * @Description: 深圳市信利康供应链管理有限公司
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月13日 上午10:50:14
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.achievo.framework.util.PasswordUtil;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.SysLogTypeConstant;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.RamdomUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.inloan.BizHandleService;
import com.xlkfinance.bms.rpc.inloan.HandleDifferWarnDTO;
import com.xlkfinance.bms.rpc.system.EidtPassword;
import com.xlkfinance.bms.rpc.system.SysOrgInfo;
import com.xlkfinance.bms.rpc.system.SysOrgInfoService;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.rpc.system.SysUserOrgInfo;
import com.xlkfinance.bms.rpc.system.SysUserOrgInfoService;
import com.xlkfinance.bms.rpc.system.SysUserService;
import com.xlkfinance.bms.web.constant.Constants;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;
import com.xlkfinance.bms.web.shiro.CaptchaUsernamePasswordToken;
import com.xlkfinance.bms.web.shiro.IncorrectCaptchaException;
import com.xlkfinance.bms.web.shiro.ShiroUser;

/**
 * @ClassName: SysUserController
 * @Description: 用户登录、注册、添加、修改、密码、角色、权限管理
 * @author: Simon.Hoo
 * @date: 2014年12月13日 上午10:58:58
 */
@Controller
@RequestMapping("/sysUserController")
public class SysUserController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(SysUserController.class);

	/**
	 * @Description: 用户登录
	 * @return index.jsp
	 * @author: Simon.Hoo
	 * @date: 2014年12月13日 上午10:51:03
	 */
	@RequestMapping(value = "login")
	public String login() {
		
		return "main";
	}

	@RequestMapping(value = "navigation")
	public String navigation() {
		return "system/index";
	}
	/**
	 * 跳转到无权限操作提示页面
	 *@author:liangyanjun
	 *@time:2016年10月28日上午9:20:12
	 *@return
	 */
	@RequestMapping(value = "toUnauthor")
	public String toUnauthor() {
	    return "common/unauthor";
	}
	/**
     * 跳转到错误提示页面
     *@author:liangyanjun
     *@time:2016年10月28日上午9:20:12
     *@return
     */
	@RequestMapping(value = "toError")
	public String toError() {
	    return "common/error";
	}

	/**
	 * 数据授权页面
	 * @return
	 */
	@RequestMapping(value="/dataIndex")
	public String dataIndex(){
		
		return "system/data_authority";
	}
	/**
	 * @Description: 用户登出系统
	 * @return login.jsp
	 * @author: Simon.Hoo
	 * @date: 2014年12月13日 上午10:52:09
	 */
	@RequestMapping(value = "logout")
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
	      if (subject.isAuthenticated()) {
	         recordLog(BusinessModule.MODUEL_SYSTEM, SysLogTypeConstant.LOG_TYPE_LOGOUT, "登出系统");
	         subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
	      }
		return redirect("/");
	}

   /**
    * 检查工号是否存在,不包括自己
    * 存在：false
    * 不存在：true
    *@author:liangyanjun
    *@time:2016年6月20日下午5:22:04
    *@param sysUser
    *@param response
    */
	@RequestMapping(value="checkMemberIdIsExist" , method=RequestMethod.POST)
   public void checkMemberIdIsExist(SysUser sysUser, HttpServletResponse response) {
      boolean falg = true;
      try {
         int pid = sysUser.getPid();
         String memberId = sysUser.getMemberId();
         SysUserService.Client client = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");
         SysUser queryUser = client.getSysUserByMemberId(memberId);
         if (queryUser.getPid() > 0 && queryUser.getPid() != pid) {
            falg = false;
         }
      } catch (Exception e) {
          logger.error("sysUserController.checkMemberIdIsExist:" + ExceptionUtil.getExceptionMessage(e));
          e.printStackTrace();
      }
      // 输出
      outputJson(falg, response);
   }
	/**
	  * @Description: 检查用户名是否存在
	  * @param userName 用户名
	  * @param response
	  * @author: JingYu.Dai
	  * @date: 2015年5月13日 下午8:31:44
	 */
	@RequestMapping(value="checkUserNameIsExist" , method=RequestMethod.POST)
	public void checkUserNameIsExist(String userName , HttpServletResponse response){
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserService");
		boolean falg = true;
		try {
			SysUserService.Client client = (SysUserService.Client) clientFactory.getClient();
			falg = client.checkUserNameIsExist(userName);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("SysUserController.checkUserNameIsExist"+e.getMessage());
			}
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		// 输出
		outputJson(falg, response);
	}
	/**
	 * 检查手机号码是否存在,不包括自己
	 * 存在：false
	 * 不存在：true
	 *@author:liangyanjun
	 *@time:2016年6月20日下午4:38:55
	 *@param phone
	 *@param response
	 */
   @RequestMapping(value = "checkPhoneIsExist", method = RequestMethod.POST)
   public void checkPhoneIsExist(SysUser sysUser, HttpServletResponse response) {
      boolean falg = true;
      try {
         int pid = sysUser.getPid();
         String phone = sysUser.getPhone();
         SysUserService.Client client = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");
         SysUser queryUser = client.getSysUserByPhone(phone);
         if (queryUser.getPid() > 0 && queryUser.getPid()!=pid) {
            falg = false;
         }
      } catch (Exception e) {
         logger.error("sysUserController.checkPhoneIsExist:" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
      }
      // 输出
      outputJson(falg, response);
   }
	/**
	 * @Description: 利用Shiro进行登录验证
	 * @param userName 用户名
	 * @param password 密码
	 * @param captcha 验证码
	 * @return 验证结果VO， JSON格式
	 * @author: Simon.Hoo
	 * @date: 2014年12月13日 上午10:53:00
	 */
	@RequestMapping(value = "checkUserLogin", method=RequestMethod.POST)
	@ResponseBody
	public Json checkUserLogin(String userNameBase, String passwordBase, String captcha,HttpServletRequest request) {
		Json j = super.getSuccessObj("登录提示");
		HttpSession session = request.getSession(false);
		SysUserService.Client client = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");
		Subject subject = SecurityUtils.getSubject();
		CaptchaUsernamePasswordToken token = new CaptchaUsernamePasswordToken();
		token.setCaptcha(captcha);
		token.setRememberMe(true);
		try {
         // 如果输入的是手机号码，并且存在该用户，则从数据库中获取用户名
		   userNameBase = getFromBASE64(userNameBase);
         SysUser tempSysUser = client.getSysUserByPhone(userNameBase);
         if (tempSysUser.getPid() > 0) {
            userNameBase = tempSysUser.getUserName();
         }
		   
         String passwordEncrypt = PasswordUtil.encrypt(userNameBase, getFromBASE64(passwordBase), PasswordUtil.getStaticSalt());
         token.setPassword(passwordEncrypt.toCharArray());
		 token.setUsername(userNameBase);
		 subject.login(token);
			
		 ShiroUser shiroUser = this.getShiroUser();
		 SysUser loginUser = client.getSysUserByPid(shiroUser.getPid());
		 //登录用户状态为失效或者离职时，不允许登录
		 if(loginUser.getStatus() == Constants.STATU_DISABLED || loginUser.getStatus() == Constants.STATU_DISABLED_2){
			 logger.error(Constants.UNKNOWN_PWD_ACCOUNT_EXCEPTION);
			 j.getHeader().put("success", false);
			 j.getHeader().put("msg", Constants.UNKNOWN_PWD_ACCOUNT_EXCEPTION);
			 return j;
		 }
		 List<Integer> userIds = this.getDataUserIds(loginUser);
			
		 //将用户数据权限数组存放到session中
		 session.setAttribute(Constants.DATA_USERIDS, userIds);
		 recordLog(BusinessModule.MODUEL_SYSTEM, SysLogTypeConstant.LOG_TYPE_LOGIN, "登录系统");
			
         BizHandleService.Client bizHandleService = (BizHandleService.Client) getService(BusinessModule.MODUEL_INLOAN, "BizHandleService");
         //每次登陆先清空没有办理的预警事项
         HandleDifferWarnDTO delHandleDifferWarnDTO=new HandleDifferWarnDTO();
         delHandleDifferWarnDTO.setHandleAuthor(loginUser.getPid());
         delHandleDifferWarnDTO.setStatus(com.xlkfinance.bms.common.constant.Constants.NOT_HANDLE_WARN_STATUS);
         
         //清空处理类型  1 和 2 
         delHandleDifferWarnDTO.setHandleTypeList(Arrays.asList(com.xlkfinance.bms.common.constant.Constants.WARN_HANDLE_TYPE_1,
        		 com.xlkfinance.bms.common.constant.Constants.WARN_HANDLE_TYPE_2));
         
         bizHandleService.delHandleDifferWarn(delHandleDifferWarnDTO);
         //查询是否有需要处理的业务办理预警事项，如果有则把数据增加到差异预警处理（BIZ_LOAN_HANDLE_DIFFER_WARN）表中
         List<HandleDifferWarnDTO> needHandleWarnList = bizHandleService.getNeedHandleWarn(loginUser.getPid());
         if(CollectionUtils.isEmpty(needHandleWarnList)){
        	 needHandleWarnList = new ArrayList<HandleDifferWarnDTO>();
         }
         
         
         //查询查档预警列表----------
         HandleDifferWarnDTO queryHand = new HandleDifferWarnDTO();
         queryHand.setStatus(com.xlkfinance.bms.common.constant.Constants.NOT_HANDLE_WARN_STATUS);
         queryHand.setHandleType(com.xlkfinance.bms.common.constant.Constants.WARN_HANDLE_TYPE_3);
         queryHand.setHandleAuthor(loginUser.getPid());
         List<HandleDifferWarnDTO> needHandleWarnList2 = bizHandleService.getHandleDifferWarnList(queryHand);
         if(!CollectionUtils.isEmpty(needHandleWarnList2)){
        	 for (HandleDifferWarnDTO handleDifferWarnDTO : needHandleWarnList2) {
        		 handleDifferWarnDTO.setFlowName("系统查档");
			 }
        	 needHandleWarnList.addAll(needHandleWarnList2);
         }
         
         if (needHandleWarnList != null && needHandleWarnList.size() > 0) {
            for (HandleDifferWarnDTO warnDTO : needHandleWarnList) {
               warnDTO.setStatus(com.xlkfinance.bms.common.constant.Constants.NOT_HANDLE_WARN_STATUS);
               warnDTO.setHandleAuthor(loginUser.getPid());
               
               //动态办理为空，则为系统查档
               if(warnDTO.getHandleDynamicId() == 0 ){
            	   continue;
               }else{
            	   warnDTO.setHandleType(com.xlkfinance.bms.common.constant.Constants.WARN_HANDLE_TYPE_1);
               }
               bizHandleService.addHandleDifferWarn(warnDTO);
            }
         }
         //查询未处理预警事项的数量,并设置到session中（备注：该值大于0，则不允许点击其他功能）
         HandleDifferWarnDTO handleDifferWarnQuery=new HandleDifferWarnDTO();
         handleDifferWarnQuery.setHandleAuthor(loginUser.getPid());
         handleDifferWarnQuery.setStatus(com.xlkfinance.bms.common.constant.Constants.NOT_HANDLE_WARN_STATUS);
         int indexHandleDifferWarnTotal = bizHandleService.getIndexHandleDifferWarnTotal(handleDifferWarnQuery);
         session.setAttribute(com.xlkfinance.bms.common.constant.Constants.NOT_HANDLE_WARN_COUNT, indexHandleDifferWarnTotal);
         //用户来源
         int userSource = checkUserOrg();
         session.setAttribute(com.xlkfinance.bms.common.constant.Constants.USER_SOURCE, userSource);
         session.setAttribute("v", RamdomUtil.getRandom(6));
			this.login();
		} catch (UnknownSessionException use) {
			subject = new Subject.Builder().buildSubject();
			subject.login(token);
			logger.error(Constants.UNKNOWN_SESSION_EXCEPTION);
			j.getHeader().put("success", false);
			j.getHeader().put("msg", Constants.UNKNOWN_SESSION_EXCEPTION);
		} catch (UnknownAccountException | IncorrectCredentialsException ex) {
			logger.error(Constants.UNKNOWN_PWD_ACCOUNT_EXCEPTION);
			j.getHeader().put("success", false);
			j.getHeader().put("msg", Constants.UNKNOWN_PWD_ACCOUNT_EXCEPTION);
		}catch (LockedAccountException lae) {
			j.getHeader().put("success", false);
			j.getHeader().put("msg", Constants.LOCKED_ACCOUNT_EXCEPTION);
		} catch (IncorrectCaptchaException e) {
			j.getHeader().put("success", false);
			j.getHeader().put("msg", Constants.INCORRECT_CAPTCHA_EXCEPTION);
		} catch (AuthenticationException ae) {
			j.getHeader().put("success", false);
			j.getHeader().put("msg", ae.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", Constants.UNKNOWN_EXCEPTION);
		}
		return j;
	}
	
	
	/**
	 * 
	 * @Description: 获取所有系统用户
	 * @param user
	 *            系统用户对象
	 * @return 系统用户首页
	 * @author: Mr.Cai
	 * @date: 2014年12月16日
	 */
	@RequestMapping(value = "getAllSysUser", method = RequestMethod.POST)
	@ResponseBody
	public void getAllSysUser(SysUser user, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<SysUser> listUser = new ArrayList<SysUser>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserService");
		try {
			SysUserService.Client client = (SysUserService.Client) clientFactory.getClient();
			listUser = client.getAllSysUser(user);
			if(user.getRows() == 0){
				outputJson(listUser, response);
				return;
			}
			int count = client.getAllSysUserCount(user);
			map.put("rows", listUser);
			map.put("total", count);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		// 输出
		outputJson(map, response);
	}
	/**
	 * 机构分配列表
	 *@author:liangyanjun
	 *@time:2016年9月8日下午4:26:46
	 *@param user
	 *@param response
	 */
	@RequestMapping(value = "orgAllocationUserList", method = RequestMethod.POST)
	public void orgAllocationUserList(SysUser user,HttpServletRequest request, HttpServletResponse response) {
	   Map<String,Object> map = new HashMap<String,Object>();
	   List<SysUser> listUser = new ArrayList<SysUser>();
	   BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserService");
	   user.setUserIds(getUserIds(request));
	   user.setRoleCode("ORG_BIZ_CENTER");
	   try {
	      SysUserService.Client client = (SysUserService.Client) clientFactory.getClient();
	      listUser = client.getAllSysUser(user);
	      int count = client.getAllSysUserCount(user);
	      map.put("rows", listUser);
	      map.put("total", count);
	   } catch (Exception e) {
	      if (logger.isDebugEnabled()) {
	         logger.debug(e.getMessage());
	      }
	   } finally {
	     destroyFactory(clientFactory);
	   }
	   // 输出
	   outputJson(map, response);
	}
	
	/**
	  * @Description: 查询所有用户 for combobox
	  * @param response
	  * @author: xuweihao
	  * @date: 2015年3月16日 下午3:26:27
	  */
	@RequestMapping(value = "getAllSysUserForcombox", method = RequestMethod.POST)
	@ResponseBody
	public void getAllSysUserForcombox(HttpServletResponse response){
		List<SysUser> listUser = new ArrayList<SysUser>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserService");
		try {
			SysUserService.Client client = (SysUserService.Client) clientFactory.getClient();
			listUser = client.getSysUserList();
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		// 输出
		outputJson(listUser, response);
	}
	
	

	/**
	 * 
	 * @Description: 删除系统用户
	 * @param userName
	 *            用户名
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Mr.Cai
	 * @date: 2014年12月20日 下午2:25:55
	 */
	@RequestMapping(value = "delete")
	public void delete(String userNames, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserService");
		try {
			SysUserService.Client client = (SysUserService.Client) clientFactory.getClient();
			// 调用删除方法(逻辑删除)
			client.batchUpdate(userNames);
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "删除失败,请重新操作！");
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(j, response);
	}
	
	/**
	 * 
	  * @Description: 重置密码
	  * @param userIds
	  * @param response
	  * @author: zhanghg
	  * @date: 2015年4月22日 下午8:06:59
	 */
	@RequestMapping(value = "resetPwd")
	public void resetPwd(String userIds, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserService");
		try {
			SysUserService.Client client = (SysUserService.Client) clientFactory.getClient();
			String arr[]=userIds.split(",");
			List<String> userList = new ArrayList<String>();
			Collections.addAll(userList, arr);
			
			List<SysUser> users=client.getSysUserByPids(userList);
			for(SysUser sysUser:users){
            String pwd = "123456";// generateMixed(8);重置密码默认123456
				sysUser.setPwd(PasswordUtil.encrypt(sysUser.getUserName(),pwd, PasswordUtil.getStaticSalt()));
				client.updateSysUserPwd(sysUser);
//				sendSms(sysUser.getPhone(), "您好：您的用户密码已重置,新密码为("+pwd+"). [Q房金融业务系统]");
//				sendMail(sysUser.getMail(), "重置用户密码", getMailTemplate("重置用户密码","您好：您的用户密码已重置,新密码为(<strong style='color:#FF0000'>"+pwd+"</strong>). [Q房金融业务系统]"));
			}
			j.getHeader().put("success", true);
			j.getHeader().put("msg","重置密码成功！");
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "重置密码失败,请重新操作！");
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(j, response);
	}
	
	/*private String getMailTemplate(String subject,String content){
		String mailHtml="<!DOCTYPE html>"+
		"<html>"+
		"<head>"+
		"<meta charset=\"UTF-8\">"+
		"<title>Insert title here</title>"+
		"</head>"+
		"<body>"+
			"<h1>主题：</h1>"+
			"<samp>【Q房金融业务系统】"+subject+"</samp>"+
			"<h3>内容：</h3>"+
			"<p><samp>"+content+"</samp></p>"+
		"</body>"+
		"</html>";
		return mailHtml;
	}*/
	
	
/*
	// 产生动态密码
	private String generateMixed(int n) {
		String[] chars = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
	     StringBuffer res =new StringBuffer();
	     for(int i = 0; i < n ; i ++) {
	         int id = (int)Math.ceil(Math.random()*59);
	         res.append(chars[id]);
	     }
	     return res.toString();
	}*/

	/**
	 * 
	 * @Description: 新增系统用户
	 * @param sysUser
	 *            系统用户对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Mr.Cai
	 * @date: 2014年12月20日 下午2:27:35
	 */
	@RequestMapping(value = "insert")
	public void insert(SysUser user, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserService");
			SysUserService.Client client = (SysUserService.Client) clientFactory.getClient();
			// 调用新增方法
			client.addSysUser(user);
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "添加失败,请重新操作！");
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(j, response);
	}
	
	/**
	 * 
	 * @Description: 选择直属上级
	 * @param sysUser
	 *            系统用户对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: wangheng
	 * @date: 2015年03月11日
	 */
	@RequestMapping(value = "xzSuperior")
	public String xzSuperior(SysUser user, HttpServletResponse response) {
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserService");
			SysUserService.Client client = (SysUserService.Client) clientFactory.getClient();
			// 调用查询所有用户的方法
			client.getSysUserList();
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
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
		return "system/xz_superior";
		//return "est/xz_com_cus";
		
	}

	/**
	 * 
	 * @Description: 修改系统用户信息
	 * @param sysUser
	 *            系统用户
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Mr.Cai
	 * @date: 2014年12月20日 下午2:35:16
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public void update(SysUser user, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserService");
		try {
			SysUserService.Client client = (SysUserService.Client) clientFactory.getClient();
			// 调用修改方法
			client.updateSysUser(user);
			
			int status = user.getStatus();
			int userId = user.getPid();

			//假如状态为无效则删除该用户
			if(status==2 && userId >0){
				client.deleteByUserId(userId);
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "修改失败,请重新操作！");
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(j, response);
	}
	
	/**
	 * 用户下拉框
	 * @param response
	 */
	@RequestMapping(value = "getAllUsers")
	@ResponseBody
	public void getAllUsers(HttpServletResponse response) {
		List<SysUser> list = new ArrayList<SysUser>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserService");
		try {
			SysUserService.Client client = (SysUserService.Client) clientFactory.getClient();
			SysUser sysUser=new SysUser();
			sysUser.setPid(0);
			sysUser.setRealName("--请选择--");
			list.add(sysUser);
			list.addAll(client.getSysUserList());
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
		// 输出
		outputJson(list, response);
	}

	/**
	  * @Description: 根据系统当前登录用户ID查询用户数据
	  * @param response
	  * @author: Dai.jingyu
	  * @date: 2015年3月17日 上午11:34:17
	  */
	@RequestMapping(value = "userInfo" , method = RequestMethod.POST)
	@ResponseBody
	public void userInfo(HttpServletResponse response){
		ShiroUser user = getShiroUser();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserService");
		try {
			SysUserService.Client client = (SysUserService.Client) clientFactory.getClient();
			SysUser sysUser = client.getSysUserByPid(user.getPid());
			List<Integer> userIds = this.getDataUserIds(sysUser);
			
			if(userIds != null && !userIds.isEmpty()){
				
				for(Integer id : userIds){
					
					System.out.println("=======data userid:"+id);
				}
				
			}else{
				
				System.out.println("---------is null-----------");
			}
			outputJson(sysUser, response);
		} catch (Exception e) {
			e.printStackTrace();
			if (logger.isDebugEnabled()) {
				logger.debug("SysUserController.userInfo Exception" + e.getMessage());
			}
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
	/**
	  * @Description: 修改密码
	  * @param response
	  * @param ep
	  * @author: Dai.jingyu
	  * @date: 2015年3月17日 下午5:57:43
	  */
	@RequestMapping(value = "editPassword" , method = RequestMethod.POST)
	public void editPassword(HttpServletResponse response,EidtPassword ep){
		ShiroUser user = getShiroUser();
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserService");
		try {
			SysUserService.Client client = (SysUserService.Client) clientFactory.getClient();
			String oldPwd = PasswordUtil.encrypt(user.getUserName(), ep.oldPwd, PasswordUtil.getStaticSalt());
			String newPwd = PasswordUtil.encrypt(user.getUserName(), ep.newPwd, PasswordUtil.getStaticSalt());
			ep.setUid(user.getPid());
			ep.setNewPwd(newPwd);
			ep.setOldPwd(oldPwd);
			// 调用修改方法
			client.eidtPassword(ep);
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "修改密码错误！");
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(j, response);
	}
	
	/**
	 * 根据角色编号得到对应下的所有用户 
	 * @param response
	 */
	@RequestMapping(value = "getUsersByRoleCode")
	@ResponseBody
	public void getUsersByRoleCode(HttpServletResponse response,String roleCode) {
		List<SysUser> list = new ArrayList<SysUser>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserService");
		try {
			SysUserService.Client client = (SysUserService.Client) clientFactory.getClient();
			SysUser sysUser=new SysUser();
			sysUser.setPid(0);
			sysUser.setRealName("--请选择--");
			list.add(sysUser);
			list.addAll(client.getUsersByRoleCode(roleCode));
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
		// 输出
		outputJson(list, response);
	}
	/**
	  * @Description: 根据用户ID查询用户直属上级
	  * @param pid
	  * @param response
	  * @author: JingYu.Dai
	  * @date: 2015年5月3日 上午11:34:05
	 */
	@RequestMapping(value = "getuperiorSysUserByPid")
	public void getuperiorSysUserByPid(int pid , HttpServletResponse response){
		SysUser sysUser = new SysUser();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserService");
		try {
			SysUserService.Client client = (SysUserService.Client) clientFactory.getClient();
			sysUser = client.getuperiorSysUserByPid(pid);
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
		// 输出
		outputJson(sysUser, response);
	}
	
	/**
	 * 保存数据授权信息
	 * @param userIds
	 * @param sysOrgIds
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "saveDataAuthrotity")
	@ResponseBody
	public void saveDataAuthrotity(String userIds,String sysOrgIds,int dataScope,HttpServletResponse response,HttpServletRequest request){
		
		String[] ids = null;
		String[] orgIds = null;
		SysUserOrgInfo dataAuthor = null;
		SysUser sysUser = null;
		SysOrgInfo sysOrg = null;
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactoryDataAuthor = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserOrgInfoService");
		BaseClientFactory clientFactoryUser = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserService");
		BaseClientFactory clientFactoryOrg = getFactory(BusinessModule.MODUEL_SYSTEM, "SysOrgInfoService");
		try {
			
			if(StringUtils.isEmpty(userIds) || StringUtils.isEmpty(sysOrgIds)){
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "数据授权失败,请选择用户与机构!");
			}else{
				
				ids = userIds.split(",");
				orgIds = sysOrgIds.split(",");
				
				
				SysOrgInfoService.Client clientOrg = (SysOrgInfoService.Client) clientFactoryOrg.getClient();
				SysUserOrgInfoService.Client clientDataAuthor = (SysUserOrgInfoService.Client) clientFactoryDataAuthor.getClient();
				SysUserService.Client client = (SysUserService.Client) clientFactoryUser.getClient();
				//组装数据权限实体
				if(ids.length > orgIds.length){
					//按照机构id构造数据权限对象
					for(String id :orgIds){
						//用户id
						for(String uid : ids){
							
							if(!StringUtils.isEmpty(uid) && !StringUtils.isEmpty(id)){
								
								dataAuthor = new SysUserOrgInfo();
								sysUser = client.getSysUserByPid(Integer.parseInt(uid));
								sysOrg = clientOrg.getSysOrgInfo(sysUser.getOrgId());
								//机构类型
								dataAuthor.setCategory(sysOrg.getCategory());
								dataAuthor.setDataScope(dataScope);
								dataAuthor.setUserId(Integer.parseInt(uid));
								dataAuthor.setOrgId(Integer.parseInt(id));
								SysUserOrgInfo userOrg = clientDataAuthor.getUserOrgInfo(dataAuthor);
								
								if(userOrg == null||userOrg.getId()<=0){
									clientDataAuthor.saveUserOrgInfo(dataAuthor);
								}else{
									dataAuthor.setId(userOrg.getId());
									clientDataAuthor.updateUserOrgInfo(dataAuthor);
								}
							}
						}
					}
				}else{
					
					//按照用户id构造数据权限对象
					for(String uid :ids){
						//机构id
						for(String id : orgIds){
							
							if(!StringUtils.isEmpty(uid) && !StringUtils.isEmpty(id)){
								
								dataAuthor = new SysUserOrgInfo();
								sysUser = client.getSysUserByPid(Integer.parseInt(uid));
								sysOrg = clientOrg.getSysOrgInfo(sysUser.getOrgId());
								//机构类型
								dataAuthor.setCategory(sysOrg.getCategory());
								dataAuthor.setDataScope(dataScope);
								dataAuthor.setUserId(Integer.parseInt(uid));
								dataAuthor.setOrgId(Integer.parseInt(id));
								boolean isExists = clientDataAuthor.isExistUserOrgInfo(dataAuthor);
								//如果不存在,则新增
								if(!isExists){
									clientDataAuthor.saveUserOrgInfo(dataAuthor);
								}
							}
						}
					}
					
				}
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "数据授权成功!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "数据授权失败!");
		} finally{
			
			try {
				if (clientFactoryDataAuthor != null) {
					clientFactoryDataAuthor.destroy();
				}
				if (clientFactoryOrg != null) {
					clientFactoryOrg.destroy();
				}
				if (clientFactoryUser != null) {
					clientFactoryUser.destroy();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		
		// 输出
		outputJson(j, response);
	}
	@RequestMapping(value = "toHandleDifferWarnError")
   public String toHandleDifferWarnError() {
      return "inloan/handleDifferWarnError";
	}
	
	
	
	/**
	  * @Description: 获取用户来源 （万通=1，小科=2）
	 */
	@RequestMapping(value = "getUserOrg")
	public void getUserOrg(HttpServletResponse response){
		int userOrg = this.checkUserOrg();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("userOrg", userOrg);
		fillReturnJson(response, true, "操作成功!", dataMap);
		return;
	}
	
	

	/**
	 * 
	 * @Description: 获取所有系统用户
	 * @param user
	 *            系统用户对象
	 * @return 系统用户首页
	 * @author: Mr.Cai
	 * @date: 2014年12月16日
	 */
	@RequestMapping(value = "getAllSysUserNormal", method = RequestMethod.POST)
	@ResponseBody
	public void getAllSysUserNormal(SysUser user, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<SysUser> listUser = new ArrayList<SysUser>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserService");
		try {
			SysUserService.Client client = (SysUserService.Client) clientFactory.getClient();
			listUser = client.getAllSysUserNormal(user);
			if(user.getRows() == 0){
				outputJson(listUser, response);
				return;
			}
			int count = client.getAllSysUserNormalCount(user);
			map.put("rows", listUser);
			map.put("total", count);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		// 输出
		outputJson(map, response);
	}
	
}
