/**
 * @Title: FileUploadListener.java
 * @Package com.xlkfinance.bms.web.controller.system
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Chong.Zeng
 * @date: 2015年1月5日 下午8:03:51
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xlkfinance.bms.web.controller.BaseController;

@Controller
@RequestMapping("/filelistener")
public class FileUploadListenerController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(FileUploadListenerController.class);
	/**
	  * @Description: 监听文件状态
	  * @param response
	  * @param request
	  * @author: Chong.Zeng
	  * @date: 2015年1月5日 下午8:06:06
	 */
	@RequestMapping(value = "filestatus")
	public void  uploadFile(HttpServletResponse response,HttpServletRequest request){
		try {
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (!isMultipart){
	            request.setCharacterEncoding("utf-8");

	            if (request.getParameter("uploadStatus") != null) {
	            	com.xlkfinance.bms.web.util.FileUtil.responseStatusQuery(request, response);
	            }
	            if (request.getParameter("cancelUpload") != null) {
	            	com.xlkfinance.bms.web.util.FileUtil.processCancelFileUpload(request, response);
	            }

	        }
		} catch (Exception e) {
			logger.error("监听文件上传状态出错 方法： FileUploadListenerController.uploadFile");
			e.printStackTrace();
		}
	}
}
