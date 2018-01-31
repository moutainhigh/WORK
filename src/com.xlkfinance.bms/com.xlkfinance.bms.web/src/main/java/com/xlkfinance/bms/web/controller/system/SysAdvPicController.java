/**
 * @Title: SysAdvPicController.java
 * @Package com.xlkfinance.bms.web.controller.system
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: baogang
 * @date: 2016年4月25日 下午2:19:21
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.SysLogTypeConstant;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.SysAdvPicInfo;
import com.xlkfinance.bms.rpc.system.SysAdvPicService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;
import com.xlkfinance.bms.web.util.FileUtil;
/**
 * 广告Controller
  * @ClassName: SysAdvPicController
  * @author: baogang
  * @date: 2016年4月25日 下午2:19:31
 */
@Controller
@RequestMapping("/sysAdvPicController")
public class SysAdvPicController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(SysAdvPicController.class);
	
	@RequestMapping(value="navigation")
	public String navigation(){
		return "system/index_adv";
	}
	
	/**
	 * 
	 * @Description: 条件查询业务日志
	 * @param sysLog  业务日志对象
	 * @param response  HttpServletResponse 对象
	 * @author: Mr.Cai
	 * @date: 2014年12月23日 下午6:22:17
	 */
	@RequestMapping(value = "getAllAdvPic" , method=RequestMethod.POST)
	@ResponseBody
	public void getAllAdvPic(SysAdvPicInfo advPic, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String,Object>();
		List<SysAdvPicInfo> list = new ArrayList<SysAdvPicInfo>();
		int total = 0;
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysAdvPicService");
		try {
			SysAdvPicService.Client client = (SysAdvPicService.Client) clientFactory.getClient();
			list = client.querySysAdvPics(advPic);
			total = client.getSysAdvCount(advPic);
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
		map.put("rows", list);
		map.put("total", total);
		// 输出
		outputJson(map, response);
	}

	/**
	 * 跳转到编辑广告页面
	  * @param pid
	  * @param response
	  * @param model
	  * @return
	  * @author: baogang
	 * @param request 
	  * @date: 2016年4月25日 下午2:30:04
	 */
	@RequestMapping(value="/editAdvPic")
	public String editAdvPic(@RequestParam(value = "pid", required = false) Integer pid, HttpServletResponse response, ModelMap model, HttpServletRequest request){
		SysAdvPicInfo advPicInfo = new SysAdvPicInfo();
		try {
			if(pid != null && pid >0){
				BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysAdvPicService");
				SysAdvPicService.Client client = (SysAdvPicService.Client) clientFactory.getClient();
				advPicInfo = client.querySysAdvPic(pid);
			}
		} catch (Exception e) {
			logger.error("获取广告详情失败：" + ExceptionUtil.getExceptionMessage(e));
	        e.printStackTrace();
		}
		model.put("serverUrl", getServerUrl(request));
		model.put("advPic", advPicInfo);
		return "system/edit_adv";
	}
	
	/**
	 * 保存广告信息
	  * @param advPicInfo
	  * @param response
	  * @author: baogang
	  * @date: 2016年4月25日 下午2:48:27
	 */
	@RequestMapping(value="saveAdvPic")
	public void saveAdvPic(SysAdvPicInfo advPicInfo,HttpServletResponse response){
		Json j = super.getSuccessObj();
		int pid = 0;
		try {
			BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysAdvPicService");
			SysAdvPicService.Client client = (SysAdvPicService.Client) clientFactory.getClient();
			pid = advPicInfo.getPid();
			if(pid>0){//修改广告
				
				int rows =client.updateSysAdvPicInfo(advPicInfo);
				if(rows != 0){
					j.getHeader().put("success", true);
					// 成功的话,就做业务日志记录
					recordLog(BusinessModule.MODUEL_SYSTEM, SysLogTypeConstant.LOG_TYPE_UPDATE, "修改广告信息，标题：" + advPicInfo.getTitle());
				}else {
					// 失败的话,做提示处理
					j.getHeader().put("success", false);
					j.getHeader().put("msg", "修改失败,请重新操作!");
				}
			}else{//新增产品信息
				//设置当前登录者为广告的创建人
				advPicInfo.setCreateId(getShiroUser().getPid());
				advPicInfo.setStatus(1);//广告有效
				
				pid =client.addSysAdvPicInfo(advPicInfo);
				if(pid >0){
					j.getHeader().put("success", true);
					// 成功的话,就做业务日志记录
					recordLog(BusinessModule.MODUEL_SYSTEM, SysLogTypeConstant.LOG_TYPE_ADD, "新增广告信息，标题：" + advPicInfo.getTitle());
				}else {
					// 失败的话,做提示处理
					j.getHeader().put("success", false);
					j.getHeader().put("msg", "新增失败,请重新操作!");
				}
			}
			
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.debug("新增广告信息:" + ExceptionUtil.getExceptionMessage(e));
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "操作失败,请重新操作!");
		}
		j.getHeader().put("pid", pid);
		outputJson(j, response);
	}
	
	/**
	 * 附件上传
	 */
	@RequestMapping(value = "/uploadFile")
	public String uploadFile(@RequestParam(value = "imgurl", required = false) String imgurl, ModelMap model) throws Exception {
		model.put("imgurl", imgurl);
		return "system/uploadFile";
	}

	/**
	 * 附件上传
	 */
	@RequestMapping(value = "/saveFile")
	public void saveFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Json j = super.getSuccessObj();
		Map<String, Object> map = FileUtil.processFileUpload(request, response, "system", getUploadFilePath(), getFileSize(), getFileDateDirectory(), getUploadFileType());
		j.getHeader().put("success", map.get("flag"));
		j.getHeader().put("path", map.get("path"));
		outputJson(j, response);
	}
	
	/**
	 * 	修改产品状态
	  * @Description: TODO
	  * @param productId
	  * @param status
	  * @param response
	  * @author: Administrator
	  * @date: 2016年1月15日 上午10:09:59
	 */
	@RequestMapping(value = "/updateAdvStatus")
	public void updateAdvStatus(Integer pid,Integer status, HttpServletResponse response){
		Json j = super.getSuccessObj();
		try {
			BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysAdvPicService");
			SysAdvPicService.Client client = (SysAdvPicService.Client) clientFactory.getClient();
			SysAdvPicInfo advPicInfo = client.querySysAdvPic(pid);
			advPicInfo.setStatus(status);
			int rows =client.updateSysAdvPicInfo(advPicInfo);
			if(rows != 0){
				j.getHeader().put("success", true);
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_SYSTEM, SysLogTypeConstant.LOG_TYPE_UPDATE, "修改广告状态，标题：" + advPicInfo.getTitle());
			}else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "修改失败,请重新操作!");
			}
			
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.error("修改广告信息:" + ExceptionUtil.getExceptionMessage(e));
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "修改失败,请重新操作!");
		}
		
		outputJson(j, response);
	}
}
