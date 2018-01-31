/**
 * @Title: BaseController.java
 * @Package com.xlkfinance.bms.web.controller
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月11日 上午9:45:40
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.thrift.TException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.achievo.framework.util.DateUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.qfang.xk.aom.rpc.system.OrgSysLogInfo;
import com.qfang.xk.aom.rpc.system.OrgSysLogInfoService;
import com.xlkfinance.bms.common.config.GlobalConfig;
import com.xlkfinance.bms.common.constant.BizDynamicConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.MortgageDynamicConstant;
import com.xlkfinance.bms.common.constant.OrgSysLogTypeConstant;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.DataInfo;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.finance.CustArrearsView;
import com.xlkfinance.bms.rpc.inloan.BizDynamic;
import com.xlkfinance.bms.rpc.inloan.BizDynamicService;
import com.xlkfinance.bms.rpc.repayment.RepaymentPlanBaseDTO;
import com.xlkfinance.bms.rpc.repayment.RepaymentService;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.rpc.system.BizFileService;
import com.xlkfinance.bms.rpc.system.SysConfig;
import com.xlkfinance.bms.rpc.system.SysConfigService;
import com.xlkfinance.bms.rpc.system.SysLog;
import com.xlkfinance.bms.rpc.system.SysLogService;
import com.xlkfinance.bms.rpc.system.SysLookupService;
import com.xlkfinance.bms.rpc.system.SysLookupVal;
import com.xlkfinance.bms.rpc.system.SysMailInfo;
import com.xlkfinance.bms.rpc.system.SysMailInfoService;
import com.xlkfinance.bms.rpc.system.SysOrgInfo;
import com.xlkfinance.bms.rpc.system.SysOrgInfoService;
import com.xlkfinance.bms.rpc.system.SysSmsInfo;
import com.xlkfinance.bms.rpc.system.SysSmsInfoService;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.rpc.system.SysUserOrgInfo;
import com.xlkfinance.bms.rpc.system.SysUserOrgInfoService;
import com.xlkfinance.bms.rpc.system.SysUserService;
import com.xlkfinance.bms.web.constant.Constants;
import com.xlkfinance.bms.web.page.Json;
import com.xlkfinance.bms.web.shiro.ShiroUser;
import com.xlkfinance.bms.web.type.DoubleEditor;
import com.xlkfinance.bms.web.type.IntEditor;
import com.xlkfinance.bms.web.util.CommonUtil;
import com.xlkfinance.bms.web.util.FileDownload;
import com.xlkfinance.bms.web.util.FileUtil;
import com.xlkfinance.bms.web.util.IpAddressUtil;
import com.xlkfinance.bms.web.util.ParseDocAndDocxUtil;
import com.xlkfinance.bms.web.util.WebSysConfig;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public abstract class BaseController {
	private Logger logger = LoggerFactory.getLogger(BaseController.class);
	public static ExecutorService pool = Executors.newFixedThreadPool(2);

	@Resource(name = "webSysConfig")
	private WebSysConfig webSysConfig;

	@Resource(name = "globalConfig")
	private GlobalConfig globalConfig;

    @Autowired
    private  HttpServletRequest request;

	/**
	 * @Description: 解析模版
	 * @param map
	 *            需要替换的值
	 * @param tempPath
	 *            模版名称加后缀 例如：eamil.ftl
	 * @return String
	 * @author: Dai.jingyu
	 * @date: 2015年3月4日 下午2:50:42
	 */
	protected String templateParsing(Map<String, Object> map, String tempPath) {
		/* 创建一configuration */
		Configuration cfg = new Configuration();
		try {
			// 这里我设置模版的根目录
			cfg.setDirectoryForTemplateLoading(new File(getClass().getResource("/ftl").getPath()));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			/* 而以下代码你通常会在一个应用生命周期中执行多次 */
			/* 获取或创建一个模版 */
			Template temp = cfg.getTemplate(tempPath,"utf-8");
			/* 合并数据模型和模版 */
			Writer out = new StringWriter(2048);
			temp.process(map, out);
			return out.toString();
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		}
		return null;
	}

	/**
	 * 发短信
	 *@author:liangyanjun
	 *@time:2017年1月18日上午11:00:11
	 *@param mobile
	 *@param msg
	 */
	protected void sendSms(String mobile, String msg) {
	    BaseClientFactory factory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysSmsInfoService");
		try {
		    Integer userId = getShiroUser().getPid();
		    SysSmsInfoService.Client client = (SysSmsInfoService.Client) factory.getClient();
		    
		    SysSmsInfo sysSmsInfo=new SysSmsInfo();
		    sysSmsInfo.setTelphone(mobile);
		    sysSmsInfo.setContent(msg);
		    sysSmsInfo.setCreatorId(userId);
            client.sendSms(sysSmsInfo);
		} catch (Exception e) {
		    logger.error("sendSms Exception" + e.getMessage());
		}finally{
            destroyFactory(factory);
        }
	}

	/**
	 * 发邮件
	 *@author:liangyanjun
	 *@time:2017年1月18日上午10:51:50
	 *@param emailAddress
	 *@param subject
	 *@param htmlText
	 */
	protected void sendMail(String emailAddress, String subject, String htmlText) {
	    BaseClientFactory factory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysMailInfoService");
		try {
		    Integer userId = getShiroUser().getPid();
            SysMailInfoService.Client client = (SysMailInfoService.Client) factory.getClient();
            SysMailInfo mail=new SysMailInfo();
			mail.setSubject(subject);
			mail.setRecMail(emailAddress);
			mail.setContent(htmlText);
			mail.setCreatorId(userId);
			client.sendMail(mail);
		} catch (Exception e) {
			logger.error("sendMail Exception" + e.getMessage());
		}finally{
			destroyFactory(factory);
		}
	}

	/**
	 * 
	 * @Description: 下载文件
	 * @param response
	 * @param request
	 * @param path
	 * @author: Chong.Zeng
	 * @date: 2015年1月8日 下午4:04:21
	 */
	@RequestMapping(value = "download")
	protected void download(HttpServletResponse response, HttpServletRequest request, @RequestParam("path") String path, ModelMap model) {
		try {
			path = new String(path.getBytes("iso-8859-1"), "utf-8");
			BaseClientFactory factory = getFactory(BusinessModule.MODUEL_SYSTEM, "BizFileService");
	        BizFileService.Client client = (BizFileService.Client) factory.getClient();
	        BizFile bizFile = client.getBizFileByUrl(path);
	        String fileName=bizFile.getFileName();
	        FileDownload.downloadLocalFile(response, request, path, fileName);
	        recordAomLog(OrgSysLogTypeConstant.LOG_TYPE_DOWNLOAN, "下载文件："+path, request);
	        destroyFactory(factory);
		} catch (Exception e) {
			logger.error("下载文件异常" + e.getMessage());
			e.printStackTrace();
		}
	}
   /**
    * 
    *@author:liangyanjun
    *@time:2016年9月12日下午4:13:45
    *@param model
    *@param path
    *@return
    * @throws UnsupportedEncodingException 
    */
   @RequestMapping("/toLookFile")
   public String toLookFile(ModelMap model,String path,String fileName) {
      model.put("path", path);
      if (!StringUtil.isBlank(fileName)) {
         try {
            model.put("fileName", URLDecoder.decode(fileName, "UTF-8"));
         } catch (Exception e) {
         }
      }
      return "look_file";
   }
	/**
	 * @Description: 生成文件名，文件名后缀取自模板的后缀。
	 * @param tempalteFileName
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2015年3月7日 上午11:43:43
	 */
	protected String genFileName(String tempalteFileName) {
		return UUID.randomUUID() + tempalteFileName.substring(tempalteFileName.lastIndexOf("."), tempalteFileName.length());
	}

	/**
	 * 
	 * @Description: 根据系统配置名查找系统配置对象
	 * @param configName
	 *            系统配置名称
	 * @return 系统配置对象
	 * @author: Cai.Qing
	 * @date: 2015年2月2日 下午3:16:30
	 */
	protected SysConfig getByConfigName(String configName) {
		SysConfig sysConfig = null;
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysConfigService");
		try {
			SysConfigService.Client client = (SysConfigService.Client) clientFactory.getClient();
			// 调用修改方法
			sysConfig = client.getByConfigName(configName);
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
		return sysConfig;
	}

	/**
	 * 
	 * @Description: 根据list查询
	 * @param listSysConfig
	 *            系统配置list
	 * @return 系统配置集合
	 * @author: Cai.Qing
	 * @date: 2015年2月2日 下午3:17:28
	 */
	protected List<SysConfig> getListByListSysConfig(List<SysConfig> listSysConfig) {
		List<SysConfig> list = null;
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysConfigService");
		try {
			SysConfigService.Client client = (SysConfigService.Client) clientFactory.getClient();
			// 调用修改方法
			list = client.getListByListSysConfig(listSysConfig);
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
		return list;
	}

	/**
	 * 获取文件上传的根目录
	 * 
	 * @author Chong.Zeng
	 * @return
	 */
	protected String getUploadFilePath() {
		return webSysConfig.getUploadFileRoot();
	}
	/**
	 * 获取合作机构文件上传目录
	  * @return
	  * @author: baogang
	  * @date: 2016年7月14日 下午4:11:33
	 */
	protected String getAomUploadFilePath() {
		return webSysConfig.getAomFilePath();
	}
	
	
	/**
	 * 获取文件上传应许的类型
	 * 
	 * @author gW
	 * @return
	 */
	protected String[] getUploadFileType() {
		return webSysConfig.getFileType();
	}

	/**
	 * 
	 * @Description: 获取文件上传大小限制
	 * @return
	 * @author: Chong.Zeng
	 * @date: 2015年1月19日 上午11:04:57
	 */
	protected HashMap<String, Integer> getFileSize() {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("maxFileSize", webSysConfig.getMaxFileSize());
		map.put("maxReqSize", webSysConfig.getMaxRequestSize());
		return map;
	}

	/**
	 * @Description: 记录日志
	 * @param sysLog
	 * @author: Simon.Hoo
	 * @date: 2014年12月24日 下午2:11:07
	 */
	protected void recordLog(String moduel, String logType, String message) {
	    recordLog(moduel, logType, message, 0);
	}
	protected void recordLog(String moduel, String logType, String message,int projectId) {
	    ShiroUser shiroUser = getShiroUser();
	    int userId=shiroUser.getPid();
        recordLogByMobile(moduel, logType, userId, message, projectId);
	}
	protected void recordLogByMobile(String moduel, String logType, int userId,String message,int projectId) {
	   BaseClientFactory clientFactory = null;
	   try {
	      if (globalConfig.isRecord(moduel)) {
	         clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLogService");
	         SysLogService.Client client = (SysLogService.Client) clientFactory.getClient();
	         // 调用新增方法
	         
	         SysLog sysLog = new SysLog();
	         sysLog.setModules(moduel);
	         sysLog.setLogType(logType);
	         sysLog.setUserId(userId);
	         sysLog.setLogMsg(message);
	         sysLog.setProjectId(projectId);
	         if (request!=null) {
	             sysLog.setIpAddress(IpAddressUtil.getIpAddress(request));
	             sysLog.setBrowser(request.getHeader("User-Agent"));
	        }
	         client.addSysLog(sysLog);
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
	}

	/**
	 * @Description: 从Shiro中获取用户信息
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2014年12月24日 下午4:26:53
	 */
	public ShiroUser getShiroUser() {
		Subject subject = SecurityUtils.getSubject();
		return (ShiroUser) subject.getSession().getAttribute(Constants.SHIRO_USER);
	}

	@InitBinder
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(int.class, new IntEditor());
		binder.registerCustomEditor(double.class, new DoubleEditor());

	}

	protected String getFileDateDirectory() {
		return DateUtil.format(new Date(), "yyyy/MM/dd");
	}

	protected static Json getSuccessObj() {
		Map<String, Object> header = Maps.newHashMap();
		header.put("success", true);
		header.put("code", "200");
		header.put("msg", "操作成功");
		Json json = new Json();
		json.setHeader(header);
		return json;
	}

	protected Json getSuccessObj(String msg) {
		Map<String, Object> header = Maps.newHashMap();
		header.put("success", true);
		header.put("code", "200");
		header.put("msg", msg);
		Json json = new Json();
		json.setHeader(header);
		return json;
	}

	protected Json getSuccessObj(String code, String msg) {
		Map<String, Object> header = Maps.newHashMap();
		header.put("success", true);
		header.put("code", code);
		header.put("msg", msg);
		Json json = new Json();
		json.setHeader(header);
		return json;
	}

	protected Json getFailedObj() {
		Map<String, Object> header = Maps.newHashMap();
		header.put("success", false);
		header.put("code", "400");
		header.put("msg", "操作失败");
		Json json = new Json();
		json.setHeader(header);
		return json;
	}

	protected Json getFailedObj(String msg) {
		Map<String, Object> header = Maps.newHashMap();
		header.put("success", false);
		header.put("code", "400");
		header.put("msg", msg);
		Json json = new Json();
		json.setHeader(header);
		return json;
	}

	protected Json getFailedObj(String code, String msg) {
		Map<String, Object> header = Maps.newHashMap();
		header.put("success", false);
		header.put("code", code);
		header.put("msg", msg);
		Json json = new Json();
		json.setHeader(header);
		return json;
	}

	public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
		String service = new StringBuffer().append(webSysConfig.getRpcPackage()).append(".").append(serviceModuel).append(".").append(serviceName).toString();
		BaseClientFactory clientFactory = new ThriftBinaryClientFactory(webSysConfig.getThriftServerIp(), webSysConfig.getThriftServerPort(), webSysConfig.getThriftTimeout(), service);
		return clientFactory;
	}
	
	public BaseClientFactory getAomFactory(String serviceModuel, String serviceName) {
		String service = new StringBuffer().append(webSysConfig.getAomPackage()).append(".").append(serviceModuel).append(".").append(serviceName).toString();
		BaseClientFactory clientFactory = new ThriftBinaryClientFactory(webSysConfig.getThriftServerIp(), webSysConfig.getThriftServerPort(), webSysConfig.getThriftTimeout(), service);
		return clientFactory;
	}

	protected String redirect(String path) {
		return new StringBuffer().append("redirect:").append((!path.startsWith("/") ? "/" + path : path)).toString();
	}

	protected String getJson(Object jsonObj) {
		try {
			return new ObjectMapper().writeValueAsString(jsonObj);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 输出JSON信息
	 * 
	 * @param jsonObj
	 */
	protected static void outputJson(Object jsonObj, HttpServletResponse response) {
		// 兼容IE浏览器
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		try {
			PrintWriter pw = response.getWriter();
			// 将Java对象转换为JSON字符串
			String gsonStr = new ObjectMapper().writeValueAsString(jsonObj);
			// 输出JSON字符串
			pw.print(gsonStr);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			System.out.println("输出GSON出错：" + e);
		}
	}
	protected static void outputJsonOfMobile(Object jsonObj, HttpServletResponse response) {
	   response.setContentType("text/json;charset=utf-8");
	   response.setHeader("Cache-Control", "no-cache");
	   try {
	      PrintWriter pw = response.getWriter();
	      // 将Java对象转换为JSON字符串
	      String gsonStr = new ObjectMapper().writeValueAsString(jsonObj);
	      // 输出JSON字符串
	      pw.print(gsonStr);
	      pw.flush();
	      pw.close();
	   } catch (IOException e) {
	      System.out.println("输出GSON出错：" + e);
	   }
	}

	public void outputJson(Map<String, Object> returnMap, HttpServletResponse response) {
		try {
			String retrunJson = JSON.toJSONString(returnMap);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(retrunJson);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
   protected void returnJson(HttpServletResponse response,Map<Object,Object> jsonMap,Boolean success,String msg){
      jsonMap.put("success", success);
      jsonMap.put("msg", msg);
      outputJson(jsonMap, response);
   }
   protected static void returnJson(HttpServletResponse response,Boolean success,String msg){
      Map<Object, Object> jsonMap=new HashMap<Object, Object>();
      jsonMap.put("success", success);
      jsonMap.put("msg", msg);
      outputJson(jsonMap, response);
   }
   protected void returnJsonOfMobile(HttpServletResponse response,Map<Object,Object> jsonMap,Boolean success,String msg, String errorCode){
      jsonMap.put("success", success);
      jsonMap.put("msg", msg);
      if(!StringUtil.isBlank(errorCode))jsonMap.put("error_code", errorCode);
      outputJsonOfMobile(jsonMap, response);
   }
   public static void returnJsonOfMobile(HttpServletResponse response,Boolean success,String msg, String errorCode){
      Map<Object, Object> jsonMap=new HashMap<Object, Object>();
      jsonMap.put("success", success);
      jsonMap.put("msg", msg);
      if(!StringUtil.isBlank(errorCode))jsonMap.put("error_code", errorCode);
      outputJsonOfMobile(jsonMap, response);
   }
	public WebSysConfig getWebSysConfig() {
		return webSysConfig;
	}

	public void setWebSysConfig(WebSysConfig webSysConfig) {
		this.webSysConfig = webSysConfig;
	}

	public GlobalConfig getGlobalConfig() {
		return globalConfig;
	}

	public void setGlobalConfig(GlobalConfig globalConfig) {
		this.globalConfig = globalConfig;
	}
	
	protected int getIntValue(String str,int defulat)
	{
		try
		{
			return Integer.parseInt(str);
		}
		catch(Exception e)
		{
			
		}
		
		return defulat;
	}
	
	/**
	 * 获取系统参数	
	  * @param controller
	  * @param name
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年7月7日 下午4:52:58
	 */
	protected String getSysConfigValue(String name,String defualt)
	{
		BaseClientFactory clientFactory = this.getFactory(BusinessModule.MODUEL_SYSTEM, "SysConfigService");
		try {
			SysConfigService.Client client = (SysConfigService.Client) clientFactory.getClient();
			return client.getSysConfigValueByName(name);
		} catch (Exception e) {
			logger.warn("获取系统参数名称["+name+"]的值为空，获取系统默认的数据代替");
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		
		return defualt;
	}
	
	
	protected String formatAmtToString(double amt)
	{
		DecimalFormat df = new DecimalFormat("#,##0.00");
		
	    return  df.format(amt);
	}
	
	protected void setCustArrearsView(CustArrearsView view)
	{
		if(null == view)
		{
			return;
		} 
		
		view.setReceivablePrincipalStr(this.formatAmtToString(view.getReceivablePrincipal()));
		view.setPrincipalSurplusStr(this.formatAmtToString(view.getPrincipalSurplus()));
		view.setNoReceiveTotalAmtStr(this.formatAmtToString(view.getNoReceiveTotalAmt()));
		// 逾期未收费用
		view.setUnReceivedOverdueInterestStr(this.formatAmtToString(view.getDueUnReceivedTotal()));
		view.setUnReceivedOverduePunitiveStr(this.formatAmtToString(view.getUnReceivedOverdueInterest()+view.getUnReceivedOverduePunitive()));

		view.setTotalFeedStr(this.formatAmtToString(view.receivablePrincipal+view.receivableInterest+view.receivableMangCost+view.receivableOtherCost+  view.unReceivedOverdueInterest+view.unReceivedOverduePunitive));
		List<RepaymentPlanBaseDTO> list = Lists.newArrayList();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
		try {
			RepaymentPlanBaseDTO dto = new RepaymentPlanBaseDTO();
			dto.setLoanInfoId(view.getLoanId());
			dto.setFreezeStatus(0);
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			list = client.selectRepaymentPlanBaseDTO(dto);
			
			// 取最后一项合计
			RepaymentPlanBaseDTO lastDto = list.get(list.size()-1);
		    // 从收息表中获取应收合计
			view.setTotalFeedStr(this.formatAmtToString(lastDto.getAccountsTotal()));
			
		} catch (Exception e) {
				logger.error("查询还款计划表列表:" + e.getMessage());
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
	 * 获取数据权限用户ids
	 * <p>
	 * 1.当用户所属机构为集团且数据范围为集体数据时,可以查看所有数据，返回的list为空
	 * 2.当用户数据范围为私有数据时,返回登录用户id,即只能查看自己创建的数据
	 * 3.当用户管理多个机构且数据范围为集体数据时,返回用户管理机构下的所有用户id
	 * </p>
	 * @param loginUser
	 * @return
	 * @throws TException 
	 * @throws ThriftServiceException 
	 * @throws ThriftException 
	 */
	protected List<Integer> getDataUserIds(SysUser loginUser) throws ThriftServiceException, TException, ThriftException{
		
		BaseClientFactory clientFactoryUserOrg = null;
		BaseClientFactory clientFactoryOrg = null;
		BaseClientFactory clientFactory = null;
		SysUserOrgInfo dataScopeUserOrgInfo = null;
		
		List<Integer> dataUserIds = new ArrayList<Integer>();
		
		if(clientFactoryUserOrg == null){
			clientFactoryUserOrg = this.getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserOrgInfoService");
		}
		
		SysUserOrgInfoService.Client clientUserOrg = (SysUserOrgInfoService.Client) clientFactoryUserOrg.getClient();
		
		if(clientFactoryOrg == null){
			clientFactoryOrg = this.getFactory(BusinessModule.MODUEL_SYSTEM, "SysOrgInfoService");
		}
		SysOrgInfoService.Client clientOrg = (SysOrgInfoService.Client) clientFactoryOrg.getClient();
		
		if(clientFactory == null){
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserService");
		}
		
		SysUserService.Client clientUser = (SysUserService.Client) clientFactory.getClient();
		
		//根据登录用户id获取该用户的数据权限列表
		List<SysUserOrgInfo> dataUserList = clientUserOrg.listUserOrgInfo(loginUser.getPid());
		
		if(dataUserList != null && !dataUserList.isEmpty()){
			
			dataScopeUserOrgInfo = dataUserList.get(0);
			//私有数据,返回登录用户Id
			if(dataScopeUserOrgInfo.getDataScope() == Constants.DATA_SCOPE_PRIVATE){
				dataUserIds.add(loginUser.getPid());
			//集体数据,返回登录用户所管理的机构下所有员工编号	
			}else if(dataScopeUserOrgInfo.getDataScope() == Constants.DATA_SCOPE_PROTECED){
				//获取登录用所属机构
				SysOrgInfo loginUserOrg = clientOrg.getSysOrgInfo(loginUser.getOrgId());
				//判断登录用户所属机构是否为集团,如为集团领导,则可以查看所有数据，dataUserIds=null
				if(Constants.QFANG_GROUP_LAYER==loginUserOrg.layer){
					return null;
				}
				//获取登录用户所管理的机构编号
				List<Integer> orgIds = new ArrayList<Integer>();
				for(SysUserOrgInfo org :  dataUserList){
					
					Integer orgId = org.getOrgId();
					orgIds.add(orgId);
					//获取该机构下的子机构
					List<SysOrgInfo> subOrgList = clientOrg.listSysOrgInfo(orgId);
					
					for(SysOrgInfo sysOrg : subOrgList ){
						
						orgIds.add(sysOrg.getId());
					}
				}
				//获取机构下的所有用户id
				dataUserIds = clientUser.getUsersByOrgId(orgIds);
			}
		}
		//无设计权限则默认自己
		if (dataUserIds.isEmpty()) {
		   dataUserIds.add(loginUser.getPid());
      }
		return dataUserIds;
	}
	/**  
	  * base64解码  
	  * @param str  
	  * @return string  
	  */
	@SuppressWarnings("restriction")
	public String getFromBASE64(String s) { 
		   if (s == null) return null; 
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder(); 
		   try { 
		      byte[] b = decoder.decodeBuffer(s); 
		      return new String(b); 
		   } catch (Exception e) { 
		      return null; 
		   } 
	}
	/**
	 * 获取数据权限--用户编号list
	 *@author:liangyanjun
	 *@time:2016年1月18日下午5:42:46
	 *@param request
	 *@return
	 */
   public List<Integer> getUserIds(HttpServletRequest request) {
      return (List<Integer>) request.getSession().getAttribute(Constants.DATA_USERIDS);
   }

   /**
    * 获取Service
    *@author:liangyanjun
    *@time:2016年1月13日下午5:37:36
    *@return
    */
   protected Object getService(String serviceModuel,String serviceName) {
        try {
            BaseClientFactory clientFactory = getFactory(serviceModuel, serviceName);
            Object service = clientFactory.getClient();
            return service;
        } catch (ThriftException e) {
            logger.error(serviceName + "初始化失败：" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("初始化失败");
        }
   }
   
   /**
    * 获取
     * @param serviceModuel
     * @param serviceName
     * @return
     * @author: baogang
     * @date: 2016年7月5日 下午6:09:50
    */
  /* protected Object getAomService(String serviceModuel,String serviceName) {

      try {
         BaseClientFactory clientFactory = getAomFactory(serviceModuel, serviceName);
         Object service = clientFactory.getClient();
         serviceMap.put(serviceName, service);
      } catch (ThriftException e) {
         logger.error(serviceName+"初始化失败：" + e.getMessage());
         e.printStackTrace();
      }
      return serviceMap.get(serviceName);
   }*/
   
   
   /**
    * 获取登录用户信息
     * @return
     * @author: Administrator
     * @date: 2016年1月28日 下午2:27:33
    */
	public SysUser getSysUser() {
		BaseClientFactory clientFactory = getFactory(
				BusinessModule.MODUEL_SYSTEM, "SysUserService");
		SysUserService.Client client;
		SysUser loginUser = null;
		try {
			client = (SysUserService.Client) clientFactory.getClient();
			ShiroUser shiroUser = getShiroUser();
			loginUser = client.getSysUserByPid(shiroUser.getPid());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginUser;
	}
   protected static void fillReturnJson(HttpServletResponse response,boolean isSucc,String msg) {
      Json j = getSuccessObj();
      j.getHeader().put("success", isSucc);
      j.getHeader().put("msg", msg);
      outputJson(j, response);
   }
   protected void fillReturnJson(HttpServletResponse response,boolean isSucc,String msg,Map<String,Object> body) {
	   Json j = getSuccessObj();
	   j.getHeader().put("success", isSucc);
	   j.getHeader().put("msg", msg);
	   j.setBody(body);
	   outputJson(j, response);
   }
   /**
    * 获取服务端的地址
    * 例如：http://localhost:8080
    *@author:liangyanjun
    *@time:2016年4月7日上午11:36:39
    *@param request
    *@return
    */
   protected String getServerUrl(HttpServletRequest request) {
      StringBuffer requestURL = request.getRequestURL();
      String contextPathFullUrl = requestURL.substring(0, requestURL.indexOf(request.getServletPath()));
      String serverUrl = contextPathFullUrl.substring(0, contextPathFullUrl.indexOf(request.getContextPath()));
      return serverUrl;
   }
   
   
	/**
	 * 获取服务端的地址 例如：http://localhost:8080
	 * @param request
	 * @return
	 */
	public String getServerBaseUrl(HttpServletRequest request) {
 
		//String path = request.getContextPath();
		return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
		 
	}
   
   
   /**
    * 获取service客户端
    * @param serviceModuel
    * @param serviceName
    * @return
    * @throws Exception
    */
   protected Object getClient(String serviceModuel,String serviceName)throws Exception {
	   BaseClientFactory  clientFactory = getFactory(serviceModuel, serviceName);
	   return clientFactory.getClient();
   }
   /**
    * 更新业务动态数据，合适贷中大部分操作
    *@author:liangyanjun
    *@time:2016年5月12日下午3:28:21
    *@param projectId
    *@param dynamicNumber
    *@param remark
    *@throws Exception
    */
   protected void finishBizDynamicByInloan(int projectId,String dynamicNumber,String remark)throws Exception {
      ShiroUser shiroUser = getShiroUser();
      finishBizDynamicByInloan(shiroUser.getPid(),projectId, dynamicNumber, remark);
   }

   /**
    * 更新业务动态数据，合适贷中大部分操作
    *@author:liangyanjun
    *@time:2016年6月12日上午10:00:37
    *@param userId
    *@param projectId
    *@param dynamicNumber
    *@param remark
    *@throws Exception
    */
   protected void finishBizDynamicByInloan(int userId,int projectId,String dynamicNumber,String remark)throws Exception {
	  
	  BaseClientFactory factory = getFactory(BusinessModule.MODUEL_INLOAN, "BizDynamicService");
	  try {
		  BizDynamicService.Client bizDynamicService = (BizDynamicService.Client) factory.getClient();
	      BizDynamic bizDynamic=new BizDynamic();
	      bizDynamic.setProjectId(projectId);
	      bizDynamic.setModuelNumber(BizDynamicConstant.MODUEL_NUMBER_INLOAN);
	      bizDynamic.setDynamicNumber(dynamicNumber);
	      bizDynamic.setDynamicName(BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_MAP.get(dynamicNumber));
	      bizDynamic.setHandleAuthorId(userId);
	      bizDynamic.setFinishDate(DateUtils.getCurrentDateTime());
	      bizDynamic.setStatus(BizDynamicConstant.STATUS_3);
	      bizDynamic.setRemark(remark);
	      bizDynamicService.addOrUpdateBizDynamic(bizDynamic);
		} catch (Exception e) {
			logger.error("finishBizDynamicByInloan Exception" + e.getMessage());
		}finally{
			destroyFactory(factory);
		}

   }
   /**
    * 完成办理动态（房抵贷）
    *@author:liangyanjun
    *@time:2017年12月29日上午9:33:23
    *@param userId
    *@param projectId
    *@param moduelNumber
    *@param dynamicNumber
    *@param remark
    *@throws Exception
    */
   protected void finishBizDynamicByMortgage(int userId,int projectId,String moduelNumber,String dynamicNumber,String remark)throws Exception {
      
      BaseClientFactory factory = getFactory(BusinessModule.MODUEL_INLOAN, "BizDynamicService");
      try {
         BizDynamicService.Client bizDynamicService = (BizDynamicService.Client) factory.getClient();
         BizDynamic bizDynamic=new BizDynamic();
         bizDynamic.setProjectId(projectId);
         bizDynamic.setModuelNumber(moduelNumber);
         bizDynamic.setDynamicNumber(dynamicNumber);
         bizDynamic.setDynamicName(MortgageDynamicConstant.MODUEL_NUMBER_MAP.get(moduelNumber).get(dynamicNumber));
         bizDynamic.setHandleAuthorId(userId);
         bizDynamic.setFinishDate(DateUtils.getCurrentDateTime());
         bizDynamic.setStatus(BizDynamicConstant.STATUS_3);
         bizDynamic.setRemark(remark);
         bizDynamicService.addOrUpdateBizDynamic(bizDynamic);
      } catch (Exception e) {
         logger.error("finishBizDynamicByMortgage Exception" + e.getMessage());
      }finally{
         destroyFactory(factory);
      }
      
   }
   /**
    * 
    *@author:liangyanjun
    *@time:2016年5月26日下午7:59:00
    *@param projectSource
    *@param lookType
    *@return
    *@throws TException
    */
   protected Map<String, String> getSysLookupValMap(int projectSource,String lookType) throws TException {
		Map<String, String> map = new HashMap<>();
		BaseClientFactory factory = getFactory(BusinessModule.MODUEL_SYSTEM,
				"SysLookupService");
		try {
			SysLookupService.Client sysLookupService = (SysLookupService.Client) factory
					.getClient();
			List<SysLookupVal> lookupVals = new ArrayList<SysLookupVal>();
			if (projectSource == com.xlkfinance.bms.common.constant.Constants.PROJECT_SOURCE_WT) {// 项目来源：万通=1，小科=2
				lookupVals = sysLookupService.getSysLookupValByLookType("WT_"
						+ lookType);
			} else {
				lookupVals = sysLookupService
						.getSysLookupValByLookType(lookType);
			}
			for (SysLookupVal sl : lookupVals) {
				map.put(sl.getLookupVal(), sl.getLookupDesc());
			}
		} catch (Exception e) {
			logger.error("getSysLookupValMap Exception" + e.getMessage());
		}

      return map;
   }
   
   protected Map<String, Object> getFileMap(HttpServletRequest request, HttpServletResponse response, String remark,String businessModule) throws ServletException, IOException {
	      // 文件信息BIZ_FILE
	      Map<String, Object> resultMap = new HashMap<String, Object>();
	      BizFile bizFile = new BizFile();
	      Map<String, Object> map = new HashMap<String, Object>();
	      map = FileUtil.processFileUpload(request, response, businessModule, getUploadFilePath(), getFileSize(), getFileDateDirectory(), getUploadFileType());
	      resultMap.put("flag", map.get("flag"));
	      resultMap.put("errorMsg", map.get("errorMsg"));
	      @SuppressWarnings("rawtypes")
	      List items = (List) map.get("items");
	      for (int i = 0; i < items.size(); i++) {
	         FileItem item = (FileItem) items.get(i);
	         String fieldName = item.getFieldName();
	         if (item.isFormField()) {
	            resultMap.put(item.getFieldName(), ParseDocAndDocxUtil.getStringCode(item));
	         }else if ("fileName".equals(fieldName)) {
	               if (item.getSize() != 0) {
	                  bizFile.setFileSize((int) item.getSize());
	                  // 获得文件名
	                  String fileFullName = item.getName().toLowerCase();
	                  int dotLocation = fileFullName.lastIndexOf(".");
	                  String fileName = fileFullName.substring(0, dotLocation);
	                  String fileType = fileFullName.substring(dotLocation).substring(1);
	                  bizFile.setFileType(fileType);
	                  bizFile.setFileName(fileName);
	               }
	         }
	      }
	     
	      if (bizFile.getFileSize() == 0) {
	         return resultMap;
	      }
	      // 文件信息设置值
	      String uploadDttm = DateUtil.format(new Date());
	      bizFile.setUploadDttm(uploadDttm);
	      String fileUrl = String.valueOf(map.get("path"));
	      if (fileUrl == null || "null".equalsIgnoreCase(fileUrl)) {
	         return resultMap;
	      }
	      bizFile.setFileUrl(fileUrl);
	      int uploadUserId = getShiroUser().getPid();
	      bizFile.setUploadUserId(uploadUserId);
	      bizFile.setStatus(Constants.STATUS_ENABLED);
	      bizFile.setRemark(remark);
	      resultMap.put("bizFile", bizFile);
	      return resultMap;
	   }
   
   	/**
   	 * 获取二级机构Id
   	  * @return
   	  * @author: baogang
   	  * @date: 2016年8月8日 下午4:01:02
   	 */
	protected int getSecondOrgId(int orgId){
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysOrgInfoService");
		int result = 0;
		try {
			//根据用户ID查出其所属城市ID
			SysOrgInfoService.Client orgService = (SysOrgInfoService.Client) clientFactory.getClient();
			SysOrgInfo sysOrgInfo = new SysOrgInfo();
			sysOrgInfo.setLayer(2);//城市或者二级部门
			sysOrgInfo.setId(orgId);//用户所在部门ID
			//通过用户的部门以及需要查询的部门层次，查询部门
			List<SysOrgInfo> orgList = orgService.listSysParentOrg(sysOrgInfo);
			//只有用户属于一级机构时，此结果集为空
			if(orgList != null && orgList.size()>0){
				SysOrgInfo org = orgList.get(0);
				result = org.getId();
			}else{
				result = 2;//默认是深圳小科的用户
			}
		} catch (Exception e) {
			logger.error("获取二级机构失败：" + ExceptionUtil.getExceptionMessage(e));
	        e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 关闭工厂类
	  * @param clientFactorys
	  * @author: baogang
	  * @date: 2016年8月19日 上午9:17:58
	 */
	protected void destroyFactory(BaseClientFactory... clientFactorys){
		for(BaseClientFactory clientFactory : clientFactorys){
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
	    * 记录系统日记
	    *@author:liangyanjun
	    *@time:2016年7月29日下午3:52:10
	    *@param logType
	    *@param content
	    *@param request
	    *@throws TException
	    */
	   protected void recordAomLog(int logType, String content,HttpServletRequest request){
	      recordAomLog(0, logType, content, request);
	   }
	   /**
	    * 记录系统日记
	    *@author:liangyanjun
	    *@time:2016年7月29日下午3:51:53
	    *@param orderId
	    *@param logType
	    *@param content
	    *@param request
	    *@throws TException
	    */
	   protected void recordAomLog(int orderId,int logType, String content,HttpServletRequest request){
	      int userId=0;
		   ShiroUser shiroUser = null;
         try {
            shiroUser = getShiroUser();
         } catch (Exception e) {
         }
	      if (shiroUser!=null) {
	         userId = shiroUser.getPid();
	      }
	      BaseClientFactory factory = getAomFactory(BusinessModule.MODUEL_ORG_SYSTEM,"OrgSysLogInfoService");
	      try {
	    	  OrgSysLogInfoService.Client client = (OrgSysLogInfoService.Client) factory.getClient();
		      OrgSysLogInfo orgSysLogInfo=new OrgSysLogInfo();
		      orgSysLogInfo.setOrderId(orderId);
		      orgSysLogInfo.setContent(content);
		      orgSysLogInfo.setType(logType);
		      orgSysLogInfo.setOperator(userId);
		      if (request!=null) {
               orgSysLogInfo.setIpAddress(IpAddressUtil.getIpAddress(request));
            }
            client.insert(orgSysLogInfo);
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }
	   
	   /**
	    * 打包文件，返回文件全路径
	     * @param fileUrls
	     * @param response
	     * @param request
	     * @return
	     * @throws IOException
	     * @author: baogang
	     * @date: 2016年10月18日 上午11:29:32
	    */
	   public String zipFiles(List<DataInfo> fileUrls,HttpServletResponse response, HttpServletRequest request)throws IOException {
		   	//时间格式
		   String nowTimeStr = DateUtils.dateToFormatString(new Date(), "yyyyMMddHHmmss");
		   String zipFileUrl = getUploadFilePath()+File.separator+nowTimeStr+File.separator;//相对路径
		   String zipLocalUrl= CommonUtil.getRootPath() + zipFileUrl;;	//服务绝对路径
		   String zipFileName = UUID.randomUUID()+".zip";
		   //创建目录
		   File zipFilePath = new File(zipLocalUrl);
		   if (!zipFilePath.exists()) {
			   zipFilePath.mkdirs();
		   }
		   //创建文件
		   File zipFile = new File(zipLocalUrl,zipFileName);
		   if (!zipFile.exists()) {
			   zipFile.createNewFile();
		   }
		    
		 //zip文件的全路径
		  zipLocalUrl = zipLocalUrl+"/"+zipFileName;
		  ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipLocalUrl));
		  Set<String> fileSet = new HashSet<String>();
		  for (int i = 0; i < fileUrls.size(); i++) {
				DataInfo dataInfo = fileUrls.get(i);
				String fileUrl = dataInfo.getFileUrl();
				File temp_file = new File(CommonUtil.getRootPath()+fileUrl);
				if(temp_file.exists() && fileSet.add(String.format("%s.%s", dataInfo.getFileName(), dataInfo.getFileType()))) {
					FileInputStream fis = new FileInputStream(temp_file);  
					out.putNextEntry(new ZipEntry(FileUtil.takeOutFileName(fileUrl,dataInfo.getFileName())));   
					byte[] buffer = new byte[1024];
					int len;
					// 读入需要下载的文件的内容，打包到zip文件
					while ((len = fis.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}
					out.closeEntry();
					fis.close();
				}
			}
			out.close();
			
			return zipFileUrl+"/"+zipFileName;
	   }

	   /**
	    * 根据数据字典类型,和字典值     查询当前字典对象
	    *@author:liangyanjun
	    *@time:2016年10月18日上午11:31:18
	    *@param lookupType
	    *@param lookupVal
	    *@return
	    */
	   public SysLookupVal getSysLookupValByChildType(String lookupType, String lookupVal) {
	       BaseClientFactory clientFactory = null;
	       SysLookupVal value=null;
	       try {
	           clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
	           SysLookupService.Client client = (SysLookupService.Client) clientFactory.getClient();
	           value = client.getSysLookupValByChildType(lookupType, lookupVal);
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
	       return value;
	   }
	   /**
	    * 根据数据字典类型查询当前数据类型的值
	    *@author:liangyanjun
	    *@time:2017年1月17日上午9:13:25
	    *@param lookupType
	    *@return
	    */
	   public List<SysLookupVal> getSysLookupValByLookType(String lookupType) {
	       BaseClientFactory clientFactory = null;
	       List<SysLookupVal> lookupVals=null;
	       try {
	           clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
	           SysLookupService.Client client = (SysLookupService.Client) clientFactory.getClient();
	           lookupVals = client.getSysLookupValByLookType(lookupType);
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
	       return lookupVals;
	   }
	   
	    
	/**
	 * 检查用户是否属于万通 万通=1，小科=2
	 * 
	 * @author: Administrator
	 * @date: 2016年4月6日 上午10:22:28
	 */
	protected int checkUserOrg() {
		int isWantong = 2;// 默认不是万通用户
		BaseClientFactory clientFactory = getFactory(
				BusinessModule.MODUEL_SYSTEM, "SysOrgInfoService");
		try {
			// 根据用户ID查出其所属城市ID
			SysOrgInfoService.Client orgService = (SysOrgInfoService.Client) clientFactory
					.getClient();
			SysOrgInfo sysOrgInfo = new SysOrgInfo();
			sysOrgInfo.setLayer(2);// 城市或者二级部门
			sysOrgInfo.setId(getSysUser().getOrgId());// 用户所在部门ID
			// 通过用户的部门以及需要查询的部门层次，查询部门
			List<SysOrgInfo> orgList = orgService.listSysParentOrg(sysOrgInfo);
			if (orgList != null) {
				for (SysOrgInfo org : orgList) {
					if (org.getName() != null
							&& org.getName().indexOf("万通") != -1) {
						isWantong = 1;// 万通用户
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
	 * 获取项目的classpath路径 绝对
	 * @return
	 */
	public String getClassPath(){
		
		//当前项目路径
		String webClassPath =Thread.currentThread().getContextClassLoader().getResource("").getFile();
		//File.separator    文件分隔符   
		if   ("//".equals(File.separator)){
			//win
			webClassPath = webClassPath.substring(1);
		     
		}else if("/".equals(File.separator)){
		  //linux
		}
		return webClassPath;
	}
	    
}

