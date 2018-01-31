/**
 * @Title: BaseController.java
 * @Package com.xlkfinance.bms.web.controller
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月11日 上午9:45:40
 * @version V1.0
 */
package com.qfang.xk.aom.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.achievo.framework.util.DateUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.qfang.xk.aom.rpc.org.OrgAssetsCooperationInfo;
import com.qfang.xk.aom.rpc.org.OrgAssetsCooperationService;
import com.qfang.xk.aom.rpc.partner.OrgPartnerInfo;
import com.qfang.xk.aom.rpc.partner.OrgPartnerInfoService;
import com.qfang.xk.aom.rpc.system.OrgSysLogInfo;
import com.qfang.xk.aom.rpc.system.OrgSysLogInfoService;
import com.qfang.xk.aom.rpc.system.OrgUserInfo;
import com.qfang.xk.aom.web.constants.Constants;
import com.qfang.xk.aom.web.page.Json;
import com.qfang.xk.aom.web.util.FileDownload;
import com.qfang.xk.aom.web.util.FileUtil;
import com.qfang.xk.aom.web.util.IpAddressUtil;
import com.qfang.xk.aom.web.util.ParseDocAndDocxUtil;
import com.qfang.xk.aom.web.util.WebSysConfig;
import com.xinlikang.framework.email.service.XlkEmailService;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.OrgSysLogTypeConstant;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.rpc.system.BizFileService;
import com.xlkfinance.bms.rpc.system.SysBankInfo;
import com.xlkfinance.bms.rpc.system.SysBankInfoDto;
import com.xlkfinance.bms.rpc.system.SysBankService;

public  class BaseController {
	private Logger logger = LoggerFactory.getLogger(BaseController.class);

	@Resource(name = "webSysConfig")
	private com.qfang.xk.aom.web.util.WebSysConfig webSysConfig;

	@Resource(name = "xlkEmailService")
	private XlkEmailService xlkEmailService;
	

	/**
	 * 下载文件
	 *@author:liangyanjun
	 *@time:2016年7月18日上午11:28:27
	 *@param response
	 *@param request
	 *@param path
	 *@param model
	 * @throws IOException 
	 */
	@RequestMapping(value = "download")
	protected void download(HttpServletResponse response, HttpServletRequest request, @RequestParam("path") String path, ModelMap model) throws IOException {
		try {
		   BaseClientFactory factory = getFactory(AomConstant.BMS_SYSTEM, BusinessModule.MODUEL_SYSTEM, "BizFileService");
	      BizFileService.Client client = (BizFileService.Client) factory.getClient();
	      BizFile bizFile = client.getBizFileByUrl(path);
		   String fileName=bizFile.getFileName();
         FileDownload.downloadLocalFile(response, request, path, fileName);
		   recordLog(OrgSysLogTypeConstant.LOG_TYPE_DOWNLOAN, "下载文件："+path, request);
		   destroyFactory(factory);
		} catch (Exception e) {
		   response.setContentType("text/html;charset=UTF-8");
         response.getWriter().write("<script type='text/javascript'>alert('下载文件失败出错，请联系管理员！')</script>");
         logger.error("下载文件失败出错" + ExceptionUtil.getExceptionMessage(e), e);
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
      return "commom/look_file";
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
	 * 判断登录用户数据权限，数据权限为集体时，sql语句中不需要加入pmuserId 条件，数据权限为私有时，加入pmUserID的条件
	  * @return
	  * @author: baogang
	  * @date: 2016年7月19日 下午3:02:55
	 */
	protected int getDataScope(){
		int pmUserId = 0;
		OrgUserInfo loginUser = getLoginUser();
        int dataScope = loginUser.getDateScope();
        if(dataScope == AomConstant.DATE_SCOPE_1){
    	    pmUserId = loginUser.getPid();
        }
        return pmUserId;
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
	 * 获取登录用户
	 *@author:liangyanjun
	 *@time:2016年7月6日下午2:52:30
	 *@return
	 */
	public OrgUserInfo getLoginUser() {
		Subject subject = SecurityUtils.getSubject();
		return (OrgUserInfo) subject.getSession().getAttribute(Constants.LOGIN_USER);
	}


	protected String getFileDateDirectory() {
		return DateUtil.format(new Date(), "yyyy/MM/dd");
	}


	public BaseClientFactory getFactory(int system, String serviceModuel, String serviceName) {
      String rpcPackage = null;
      // 系统：信贷系统=1，机构管理系统=2
      if (AomConstant.BMS_SYSTEM == system) {
         rpcPackage = webSysConfig.getRpcPackage();
      } else if (AomConstant.AOM_SYSTEM == system) {
         rpcPackage = webSysConfig.getAomRpcPackage();
      }
      String service = new StringBuffer().append(rpcPackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
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
	protected void outputJson(Object jsonObj, HttpServletResponse response) {
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
	protected void outputJsonOfMobile(Object jsonObj, HttpServletResponse response) {
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
   protected void returnJson(HttpServletResponse response,Boolean success,String msg){
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
   protected void returnJsonOfMobile(HttpServletResponse response,Boolean success,String msg, String errorCode){
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

//	public GlobalConfig getGlobalConfig() {
//		return globalConfig;
//	}
//
//	public void setGlobalConfig(GlobalConfig globalConfig) {
//		this.globalConfig = globalConfig;
//	}
	
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
	
	
	protected String formatAmtToString(double amt)
	{
		DecimalFormat df = new DecimalFormat("#,##0.00");
		
	    return  df.format(amt);
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
   private static Map<String, Object> serviceMap = new HashMap<String, Object>();

   /**
    * 获取Service
    *@author:liangyanjun
    *@time:2016年1月13日下午5:37:36
    *@return
    */
   protected Object getService(int system,String serviceModuel,String serviceName) {
      // 如果已存在则直接获取
//      if (serviceMap.containsKey(serviceName)) {
//         return serviceMap.get(serviceName);
//      }
      try {
         BaseClientFactory clientFactory = getFactory(system, serviceModuel, serviceName);
         Object service = clientFactory.getClient();
         serviceMap.put(serviceName, service);
      } catch (ThriftException e) {
         logger.error(serviceName+"初始化失败：" + e.getMessage());
         e.printStackTrace();
      }
      return serviceMap.get(serviceName);
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
    * 获取service客户端
    * @param serviceModuel
    * @param serviceName
    * @return
    * @throws Exception
    */
   protected Object getClient(String serviceModuel,String serviceName)throws Exception {
	   BaseClientFactory  clientFactory = getFactory(1, serviceModuel, serviceName);
	   return clientFactory.getClient();
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
   protected Json getSuccessObj() {
      Map<String, Object> header = Maps.newHashMap();
      header.put("success", true);
      header.put("code", "200");
      header.put("msg", "操作成功");
      Json json = new Json();
      json.setHeader(header);
      return json;
   }

   protected void fillReturnJson(HttpServletResponse response,boolean isSucc,String msg) {
      Json j = getSuccessObj();
      j.getHeader().put("success", isSucc);
      j.getHeader().put("msg", msg);
      outputJson(j, response);
   }
   /**
    * 分页输出
    *@author:liangyanjun
    *@time:2016年7月11日下午3:39:02
    *@param query
    *@param response
    *@param list
    *@param total
    */
   protected void outputPage(int rows, HttpServletResponse response, List list, int total) {
      Map<String, Object> resultMap = new HashMap<String, Object>();
      resultMap.put("rows", list);
      int i = total / rows;
      double j = Double.parseDouble(total + "") / rows;
      if (j > i) {
         i++;
      }
      resultMap.put("total", i);
      resultMap.put("records", total);
      outputJson(resultMap, response);
   }
   
   protected Map<String, Object> getFileMap(HttpServletRequest request, HttpServletResponse response, String remark,String businessModule) throws ServletException, IOException {
      // 文件信息BIZ_FILE
      Map<String, Object> resultMap = new HashMap<String, Object>();
      BizFile bizFile = new BizFile();
      resultMap.put("bizFile", bizFile);
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
         }else if ("offlineMeetingFile".equals(fieldName)) {
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
      int uploadUserId = getLoginUser().getPid();
      bizFile.setUploadUserId(uploadUserId);
      bizFile.setStatus(Constants.STATUS_ENABLED);
      bizFile.setRemark(remark);
      return resultMap;
   }
   
   protected String getString(Object obj){
      if (obj==null) {
         return "";
      }
      return obj.toString();
   }
   
   protected int getInt(Object obj){
      if (obj==null) {
         return 0;
      }
      return Integer.parseInt(obj.toString());
   }
   protected double getDouble(Object obj){
      if (obj==null) {
         return 0;
      }
      return Double.parseDouble(obj.toString());
   }
   /**
    * 判断是否已认证
    *@author:liangyanjun
    *@time:2016年7月29日上午9:25:28
    *@param loginUser
    *@throws ThriftServiceException
    *@throws TException
    */
   protected boolean checkAudit(OrgUserInfo loginUser) throws ThriftServiceException, TException {
      boolean isAudut=false;
      int userType = loginUser.getUserType();
      if (userType==AomConstant.USER_TYPE_2) {//判断合伙人是否认证
         OrgPartnerInfoService.Client partnerClient = (OrgPartnerInfoService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_PARTNER,
               "OrgPartnerInfoService");
         int userId = loginUser.getPid();
         OrgPartnerInfo orgPartnerInfo = partnerClient.getByUserId(userId);
         if (orgPartnerInfo.getStatus()==AomConstant.ORG_AUDIT_STATUS_3) {//认证状态1:未认证,2表示认证中3、已认证
            isAudut=true;
         }
      }else{//判断机构是否认证
         OrgAssetsCooperationService.Client orgClient = (OrgAssetsCooperationService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG, "OrgAssetsCooperationService");
         int orgId = loginUser.getOrgId();
         OrgAssetsCooperationInfo org = orgClient.getById(orgId);
         if (org.getAuditStatus()==AomConstant.ORG_AUDIT_STATUS_3) {
            isAudut=true;
         }
      }
      return isAudut;
   }
   /**
    * 检查是否已合作状态
    *@author:liangyanjun
    *@time:2016年8月2日下午3:10:44
    *@param loginUser
    *@return
    *@throws ThriftServiceException
    *@throws TException
    */
   protected boolean checkCooperation(OrgUserInfo loginUser) throws ThriftServiceException, TException {
      boolean isCooperation=false;
      int userType = loginUser.getUserType();
      if (userType==AomConstant.USER_TYPE_2) {//判断合伙人是否认证
         OrgPartnerInfoService.Client partnerClient = (OrgPartnerInfoService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_PARTNER,
               "OrgPartnerInfoService");
         int userId = loginUser.getPid();
         OrgPartnerInfo orgPartnerInfo = partnerClient.getByUserId(userId);
         if (orgPartnerInfo.getCooperationStatus()==AomConstant.ORG_COOPERATE_STATUS_2) {//合作状态,1:未合作,2表示已合作,3表示已过期,4合作待确认
            isCooperation=true;
         }
      }else{//判断机构是否合伙
         int orgId = loginUser.getOrgId();
         OrgAssetsCooperationService.Client orgClient = (OrgAssetsCooperationService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG, "OrgAssetsCooperationService");
         OrgAssetsCooperationInfo org = orgClient.getById(orgId);
         if (org.getCooperateStatus()==AomConstant.ORG_COOPERATE_STATUS_2) {//合作状态,1:未合作,2表示已合作,3表示已过期,4合作待确认
            isCooperation=true;
         }
      }
      return isCooperation;
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
   protected void recordLog(int logType, String content,HttpServletRequest request){
      recordLog(0, logType, content, request);
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
   protected void recordLog(int orderId,int logType, String content,HttpServletRequest request){
      OrgUserInfo loginUser = getLoginUser();
      int userId=0;
      if (loginUser!=null) {
         userId = loginUser.getPid();
      }
      OrgSysLogInfoService.Client client = (OrgSysLogInfoService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_SYSTEM,
            "OrgSysLogInfoService");
      OrgSysLogInfo orgSysLogInfo=new OrgSysLogInfo();
      orgSysLogInfo.setOrderId(orderId);
      orgSysLogInfo.setContent(content);
      orgSysLogInfo.setType(logType);
      orgSysLogInfo.setOperator(userId);
      if (request!=null) {
          orgSysLogInfo.setIpAddress(IpAddressUtil.getIpAddress(request));
          orgSysLogInfo.setBrowser(request.getHeader("User-Agent"));
     }
      try {
         client.insert(orgSysLogInfo);
      } catch (TException e) {
         e.printStackTrace();
      }
   }
   
   /**
    * 初始化一级银行列表
     * @return
     * @author: baogang
     * @date: 2016年8月11日 下午4:06:28
    */
	protected List<SysBankInfoDto> getBankList() {
		List<SysBankInfoDto> resultList = new ArrayList<SysBankInfoDto>();
		try {
			// 初始化银行列表
			SysBankService.Client bankClient = (SysBankService.Client) getFactory(
					AomConstant.BMS_SYSTEM, BusinessModule.MODUEL_SYSTEM,
					"SysBankService").getClient();
			SysBankInfo sysBankInfo = new SysBankInfo();
			sysBankInfo.setParentId(0);
			resultList.addAll(bankClient.queryAllSysBankInfo(sysBankInfo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
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
}

