/**
 * @Title: TemplateFileController.java
 * @Package com.xlkfinance.bms.web.controller.system
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: xuweihao
 * @date: 2015年4月16日 上午10:09:50
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.rpc.system.BizFileService;
import com.xlkfinance.bms.rpc.system.TemplateFile;
import com.xlkfinance.bms.rpc.system.TemplateFileCount;
import com.xlkfinance.bms.rpc.system.TemplateFileService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.shiro.ShiroUser;
import com.xlkfinance.bms.web.util.FileUtil;
import com.xlkfinance.bms.web.util.ParseDocAndDocxUtil;
@Controller
@RequestMapping("/templateFileController")
public class TemplateFileController  extends BaseController{
	private Logger logger = LoggerFactory.getLogger(TemplateFileController.class);

	
	/**
	 * 
	  * @Description: 跳转模板上传页面
	  * @return
	  * @author: xuweihao
	  * @date: 2015年4月16日 下午2:09:35
	 */
	@RequestMapping(value="toTemplateFile")
	public String toTemplateFile(){
		return "system/templateFile";
	}
	
	/**
	 * 
	  * @Description: 模板查询
	  * @param response
	  * @author: xuweihao
	  * @date: 2015年4月16日 下午2:09:55
	 */
	@RequestMapping(value="templateFileList")
	public void templateFileList(TemplateFileCount templateFileCount,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		BaseClientFactory c = null;
		List<TemplateFile> list = new ArrayList<TemplateFile>();
		try {
			c = getFactory(BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
			TemplateFileService.Client templateFileService = (TemplateFileService.Client) c.getClient();
			
			list = templateFileService.listTemplateFile(templateFileCount);
			count = templateFileService.listTemplateFileCount(templateFileCount);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询模板列表出错" + e.getMessage());
			}
			e.printStackTrace();
		} finally {
			if (c != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		// 输出
		map.put("rows", list);
		map.put("total", count);
		outputJson(map, response);
	}
	
	
	/**
	 * 
	  * @Description: 上传模板
	  * @param request
	  * @param response
	  * @author: xuweihao
	  * @date: 2015年4月16日 下午4:30:04
	 */
	@RequestMapping(value = "saveTemplateFile")
	@ResponseBody
	public void saveTemplateFile(HttpServletRequest request, HttpServletResponse response) {
		int count = 0;
		int fileSize = 0;
		String fileName = null;
		String fileType = null;
		BaseClientFactory c = null;
		BaseClientFactory bizFileClientFactory = null;
		// 资料信息dataInfo
		TemplateFile templateFile = new TemplateFile();
		// 文件信息BIZ_FILE
		BizFile bizFile = new BizFile();
		Map<String, Object> tojson = new HashMap<String, Object>();
		try {
			c = getFactory(BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
			bizFileClientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "BizFileService");
			TemplateFileService.Client templateFileService = (TemplateFileService.Client) c.getClient();
			BizFileService.Client bizFileClient = (BizFileService.Client) bizFileClientFactory.getClient();
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				Map<String, Object> map = new HashMap<String, Object>();
				// 资料上传得到的信息集合
				map = FileUtil.processFileUpload(request, response, BusinessModule.MODUEL_SYSTEM, getUploadFilePath(), getFileSize(), getFileDateDirectory(),getUploadFileType());
				if ((Boolean) map.get("flag") == false) {
					tojson.put("uploadStatus", "文件上传失败or上传文件类型错误");
				} else {
					// 拿到当前用户
					ShiroUser shiroUser = getShiroUser();
					int userId = shiroUser.getPid();
					@SuppressWarnings("rawtypes")
					List items = (List) map.get("items");
					for (int j = 0; j < items.size(); j++) {
						FileItem item = (FileItem) items.get(j);
						// 获取其他信息
						if (item.isFormField()) {
							if (item.getFieldName().equals("fileProperty")) {
								templateFile.setFileProperty(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
							}else if (item.getFieldName().equals("fileType")) {
								templateFile.setFileType(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
							}
						} else {
							if ("file2".equals(item.getFieldName())) {
								// 获取文件上传的信息
								bizFile.setUploadUserId(userId);
								fileSize = (int) item.getSize();
								bizFile.setFileSize(fileSize);
								String fileFullName = item.getName().toLowerCase();
								int dotLocation = fileFullName.lastIndexOf(".");
								fileName = fileFullName.substring(0, dotLocation);
								fileType = fileFullName.substring(dotLocation).substring(1);
								bizFile.setFileType(fileType);
								bizFile.setFileName(fileName);
							}
						}
					}
					String uploadDttm = DateUtil.format(new Date());
					bizFile.setUploadDttm(uploadDttm);
					String fileUrl = String.valueOf(map.get("path"));
					fileUrl = fileUrl.replace("\\", "/");
					bizFile.setFileUrl(fileUrl);
					int uploadUserId = getShiroUser().getPid();
					bizFile.setUploadUserId(uploadUserId);
					bizFile.setStatus(1);
					if (fileSize == 0) {
						response.getWriter().write("fileSizeIsZero");
					}
					if (StringUtils.isEmpty(fileName) || StringUtils.isEmpty(fileType)) {
						response.getWriter().write("fileIsEmpty");
					}
					// 保存文件信息
					int fileId = bizFileClient.saveBizFile(bizFile);
					// 获取文件Id
					templateFile.setFileId(fileId);
					
					// 保存资料表信息
					count = templateFileService.saveTemplateFile(templateFile);
					if (count == 0) {
						tojson.put("uploadStatus", "上传模板失败");
					} else {
						tojson.put("uploadStatus", "上传模板成功");
					}
				}
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("上传模板出错" + e.getMessage());
			}
			e.printStackTrace();
		} finally {
			if (c != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
			if (bizFileClientFactory != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(tojson, response);
	}
	
	
	/**
	 * 
	  * @Description: 重新上传模板
	  * @param request
	  * @param response
	  * @author: xuweihao
	  * @date: 2015年4月16日 下午4:30:04
	 */
	@RequestMapping(value = "updateTemplateFile")
	@ResponseBody
	public void updateTemplateFile(HttpServletRequest request, HttpServletResponse response) {
		int count = 0;
		int fileSize = 0;
		String fileName = null;
		String fileType = null;
		BaseClientFactory c = null;
		BaseClientFactory bizFileClientFactory = null;
		// 资料信息dataInfo
		TemplateFile templateFile = new TemplateFile();
		// 文件信息BIZ_FILE
		BizFile bizFile = new BizFile();
		Map<String, Object> tojson = new HashMap<String, Object>();
		try {
			c = getFactory(BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
			bizFileClientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "BizFileService");
			TemplateFileService.Client templateFileService = (TemplateFileService.Client) c.getClient();
			BizFileService.Client bizFileClient = (BizFileService.Client) bizFileClientFactory.getClient();
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				Map<String, Object> map = new HashMap<String, Object>();
				// 资料上传得到的信息集合
				map = FileUtil.processFileUpload(request, response, BusinessModule.MODUEL_SYSTEM, getUploadFilePath(), getFileSize(), getFileDateDirectory(),getUploadFileType());
				if ((Boolean) map.get("flag") == false) {
					tojson.put("uploadStatus", "文件上传失败or上传类型错误");
				} else {
					// 拿到当前用户
					ShiroUser shiroUser = getShiroUser();
					int userId = shiroUser.getPid();
					@SuppressWarnings("rawtypes")
					List items = (List) map.get("items");
					for (int j = 0; j < items.size(); j++) {
						FileItem item = (FileItem) items.get(j);
						// 获取其他信息
						if (item.isFormField()) {
							if (item.getFieldName().equals("fileProperty")) {
								templateFile.setFileProperty(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
							}else if (item.getFieldName().equals("fileType")) {
								templateFile.setFileType(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
							}else if (item.getFieldName().equals("pid")) {
								templateFile.setPid(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
							}
						} else {
							if ("file2".equals(item.getFieldName())) {
								// 获取文件上传的信息
								bizFile.setUploadUserId(userId);
								fileSize = (int) item.getSize();
								bizFile.setFileSize(fileSize);
								String fileFullName = item.getName().toLowerCase();
								int dotLocation = fileFullName.lastIndexOf(".");
								fileName = fileFullName.substring(0, dotLocation);
								fileType = fileFullName.substring(dotLocation).substring(1);
								bizFile.setFileType(fileType);
								bizFile.setFileName(fileName);
							}
						}
					}
					String uploadDttm = DateUtil.format(new Date());
					bizFile.setUploadDttm(uploadDttm);
					String fileUrl = String.valueOf(map.get("path"));
					fileUrl = fileUrl.replace("\\", "/");
					bizFile.setFileUrl(fileUrl);
					int uploadUserId = getShiroUser().getPid();
					bizFile.setUploadUserId(uploadUserId);
					bizFile.setStatus(1);
					if (fileSize == 0) {
						response.getWriter().write("fileSizeIsZero");
					}
					if (StringUtils.isEmpty(fileName) || StringUtils.isEmpty(fileType)) {
						response.getWriter().write("fileIsEmpty");
					}
					// 保存文件信息
					int fileId = bizFileClient.saveBizFile(bizFile);
					// 获取文件Id
					templateFile.setFileId(fileId);
					
					// 保存资料表信息
					count = templateFileService.updateTemplateFile(templateFile);
					if (count == 0) {
						tojson.put("uploadStatus", "重新上传模板失败");
					} else {
						tojson.put("uploadStatus", "重新上传模板成功");
					}
				}
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("重新模板出错" + e.getMessage());
			}
			e.printStackTrace();
		} finally {
			if (c != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
			if (bizFileClientFactory != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(tojson, response);
	}
	
	
	/**
	 * 
	  * @Description: 删除模板
	  * @param pid
	  * @param response
	  * @author: xuweihao
	  * @date: 2015年4月16日 下午5:03:48
	 */
	@RequestMapping(value="delTemplateFile")
	public void delTemplateFile(int pid,HttpServletResponse response){
		BaseClientFactory c = null;
		int count = 0;
		Map<String, Object> tojson = new HashMap<String, Object>();
		try {
			c = getFactory(BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
			TemplateFileService.Client templateFileService = (TemplateFileService.Client) c.getClient();
			count = templateFileService.delTemplateFile(pid);
			if (count == 0) {
				tojson.put("uploadStatus", "删除模板失败");
			} else {
				tojson.put("uploadStatus", "删除模板成功");
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("删除模板列表出错" + e.getMessage());
			}
			e.printStackTrace();
		} finally {
			if (c != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(tojson, response);
	}
	
	
	/**
	 * 
	  * @Description: 检测模板是否存在
	  * @param templateName
	  * @param response
	  * @author: xuweihao
	  * @date: 2015年5月13日 下午3:17:46
	 */
	@RequestMapping(value="checkFileUrl")
	public void checkFileUrl(String templateName,HttpServletResponse response){
		BaseClientFactory c = getFactory(BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		int count=1;
		try{
			TemplateFileService.Client cl = (TemplateFileService.Client) c.getClient();
			TemplateFile template = cl.getTemplateFile(templateName);
			if(template==null || template.getFileUrl()==null){
				count=0;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(c!=null){
				try{
					c.destroy();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		outputJson(count, response);
	}
}
