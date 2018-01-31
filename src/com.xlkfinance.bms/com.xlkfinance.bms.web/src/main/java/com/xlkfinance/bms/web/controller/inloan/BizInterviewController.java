package com.xlkfinance.bms.web.controller.inloan;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.inloan.BizInterviewInfo;
import com.xlkfinance.bms.rpc.inloan.BizInterviewInfoIndexDTO;
import com.xlkfinance.bms.rpc.inloan.BizInterviewInfoService;
import com.xlkfinance.bms.rpc.project.CusCardInfo;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.rpc.system.BizFileService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;
import com.xlkfinance.bms.web.util.FileUtil;

/**
 * @desc 面签管理
 * @author qinqiwei
 *
 */
@Controller
@RequestMapping("/bizInterviewController")
public class BizInterviewController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(BizInterviewController.class);
	
	@RequestMapping(value="index")
	public String index() {
		return "inloan/bizInterview/bizInterview_index";
	}
	
	/**
	 * 面签管理列表
	 */
	@RequestMapping(value="getAllProject")
	public void getAllProject(BizInterviewInfoIndexDTO infoDto,HttpServletRequest request, HttpServletResponse response) {
		BaseClientFactory clientFactory = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_INLOAN, "BizInterviewInfoService");
			BizInterviewInfoService.Client bizInterviewInfoService = (BizInterviewInfoService.Client) clientFactory.getClient();
			infoDto.setUserIds(getUserIds(request));
			infoDto.setRecordClerkId(getShiroUser().getPid());//查询录单员为登录人的业务
			infoDto.setIsChechan(0);//未撤单
			infoDto.setProductType(8);//抵押贷类产品
			infoDto.setHisWarrantStartTime(DateUtils.getDateStartStr(infoDto.getHisWarrantStartTime()));
			infoDto.setHisWarrantEndTime(DateUtils.getDateStartStr(infoDto.getHisWarrantEndTime()));
			List<BizInterviewInfoIndexDTO> list = bizInterviewInfoService.getProjectByPage(infoDto);
			int count = bizInterviewInfoService.getProjectCount(infoDto);
			map.put("rows", list);
			map.put("total", count);
		} catch (Exception e) {
			logger.error("查询面签管理列表:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
		outputJson(map, response);
	}
	
	/**
	 * 面签指派
	 */
	@SuppressWarnings("null")
	@RequestMapping(value="asign")
	public void asign(BizInterviewInfo infoDto,HttpServletRequest request, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		
		if(infoDto == null || infoDto.getPid() <=0) {
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "操作失败!");
		}else {
			try {
				clientFactory = getFactory(BusinessModule.MODUEL_INLOAN, "BizInterviewInfoService");
				BizInterviewInfoService.Client bizInterviewInfoService = (BizInterviewInfoService.Client) clientFactory.getClient();
				BizInterviewInfo info = new BizInterviewInfo();
				//面签ID
				info.setPid(infoDto.getPid());
				//面签人员
				info.setInterviewId(infoDto.getInterviewId());
				//面签公证人员
				info.setNotarizationId(infoDto.getNotarizationId());
				//面签抵押人员
				info.setMortgageUser(infoDto.getMortgageUser());
				//公证 / 抵押  / 面签状态  是否已经 处理
				if(bizInterviewInfoService.asign(info) > 0) {
					j.getHeader().put("success", true);
					j.getHeader().put("msg", "操作成功!");
				}else {
					j.getHeader().put("success", false);
					j.getHeader().put("msg", "操作失败!");
				}
			} catch (Exception e) {
				logger.error("面签指派:" + ExceptionUtil.getExceptionMessage(e));
			} finally {
				destroyFactory(clientFactory);
			}
		}
		
		outputJson(j, response);
	}
	
	/**
	 * 获取银行卡信息 
	 */
	@RequestMapping(value="getCusCardInfoByProjectId")
	public void getCusCardInfoByProjectId(@RequestParam(value = "projectId", required = true) Integer projectId,HttpServletRequest request, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		if(projectId == null || projectId <=0) {
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "获取银行卡信息错误");
		}
		else {
			try {
				clientFactory = getFactory(BusinessModule.MODUEL_INLOAN, "BizInterviewInfoService");
				BizInterviewInfoService.Client bizInterviewInfoService = (BizInterviewInfoService.Client) clientFactory.getClient();
				CusCardInfo info = bizInterviewInfoService.getCardInfoByProjectId(projectId);
				j.getBody().put("data", info);
			} catch (Exception e) {
				logger.error("获取银行卡信息失败：" + ExceptionUtil.getExceptionMessage(e));
		        j.getHeader().put("success", false);
				j.getHeader().put("msg", "获取银行卡信息失败,请重新操作!");
			} finally {
				destroyFactory(clientFactory);
			}
			
		}
		// 输出
		outputJson(j, response);
	}
	
	/**
	 * 面签
	 */
	@RequestMapping(value="interview")
	public void interview(BizInterviewInfoIndexDTO infoDto,HttpServletRequest request, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			if(infoDto == null || infoDto.getPid() <=0) {
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "操作失败!");
			}else {
				clientFactory = getFactory(BusinessModule.MODUEL_INLOAN, "BizInterviewInfoService");
				BizInterviewInfoService.Client bizInterviewInfoService = (BizInterviewInfoService.Client) clientFactory.getClient();
				infoDto.setUpdateId(getShiroUser().getPid());
				if(bizInterviewInfoService.interivew(infoDto) > 0) {
					j.getHeader().put("success", true);
					j.getHeader().put("msg", "操作成功!");
				}else {
					j.getHeader().put("success", false);
					j.getHeader().put("msg", "面签失败,请重新操作!");
				}
			}
		} catch (Exception e) {
			logger.error("面签签约失败：" + ExceptionUtil.getExceptionMessage(e));
	        j.getHeader().put("success", false);
			j.getHeader().put("msg", "获取银行卡信息失败,请重新操作!");
		} finally {
			destroyFactory(clientFactory);
		}
		
		// 输出
		outputJson(j, response);
	}
	
	/**
	 * 公证notarization
	 */
	@RequestMapping(value="notarization")
	public void notarization(BizInterviewInfo infoDto,HttpServletRequest request, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		
		if(infoDto == null || infoDto.getPid() <=0) {
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "操作失败!");
		}else {
			try {
				clientFactory = getFactory(BusinessModule.MODUEL_INLOAN, "BizInterviewInfoService");
				BizInterviewInfoService.Client bizInterviewInfoService = (BizInterviewInfoService.Client) clientFactory.getClient();
				BizInterviewInfo info = new BizInterviewInfo();
				//面签ID
				info.setPid(infoDto.getPid());
				info.setProjectId(infoDto.getProjectId());
				//共证类型
				info.setNotarizationType(infoDto.getNotarizationType());
				//领证时间
				info.setReceiveTime(DateUtils.getDateStartStr(infoDto.getReceiveTime()));
				//办理时间
				info.setHandingTime(DateUtils.getDateStartStr(infoDto.getHandingTime()));
				//公证状态
				info.setNotarizationStatus(1);
				//公证办理人员
				//info.setNotarizationId(infoDto.getNotarizationId());
				if(bizInterviewInfoService.notarization(info) > 0) {
					j.getHeader().put("success", true);
					j.getHeader().put("msg", "操作成功!");
				}else {
					j.getHeader().put("success", false);
					j.getHeader().put("msg", "操作失败!");
				}
			} catch (Exception e) {
				logger.error("面签公证:" + ExceptionUtil.getExceptionMessage(e));
			} finally {
				destroyFactory(clientFactory);
			}
		}
		j.getHeader().put("success", true);
		j.getHeader().put("msg", "操作成功!");
		
		outputJson(j, response);
	}
	
	/**
	 * 面签抵押
	 * @param infoDto
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="mortgage")
	public void mortgage(BizInterviewInfo infoDto,HttpServletRequest request, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		
		if(infoDto == null || infoDto.getPid() <=0) {
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "操作失败!");
		}else {
			try {
				clientFactory = getFactory(BusinessModule.MODUEL_INLOAN, "BizInterviewInfoService");
				BizInterviewInfoService.Client bizInterviewInfoService = (BizInterviewInfoService.Client) clientFactory.getClient();
				BizInterviewInfo info = new BizInterviewInfo();
				//面签ID
				info.setPid(infoDto.getPid());
				info.setProjectId(infoDto.getProjectId());
				//抵押权人
				info.setMortgageName(infoDto.getMortgageName());
				//抵押回执编号
				info.setMortgageCode(infoDto.getMortgageCode());
				//抵押领证时间
				info.setMortgageTime(DateUtils.getDateStartStr(infoDto.getMortgageTime()));
				//抵押办理人员
				info.setMortgageUser(infoDto.getMortgageUser());
				//抵押办理日期
				info.setMortgageHandTime(DateUtils.getDateStartStr(infoDto.getMortgageHandTime()));
				//抵押状态
				info.setMortgageStatus(1);
				if(bizInterviewInfoService.mortgage(info) > 0) {
					j.getHeader().put("success", true);
					j.getHeader().put("msg", "操作成功!");
				}else {
					j.getHeader().put("success", false);
					j.getHeader().put("msg", "操作失败!");
				}
			} catch (Exception e) {
				logger.error("面签抵押:" + ExceptionUtil.getExceptionMessage(e));
			} finally {
				destroyFactory(clientFactory);
			}
		}
		j.getHeader().put("success", true);
		j.getHeader().put("msg", "操作成功!");
		
		outputJson(j, response);
	}
	
	
	/**
	 * 附件上传
	 */
	@RequestMapping(value = "/uploadFile")
	public String uploadFile(@RequestParam(value = "imgurl", required = false) String imgurl, ModelMap model) throws Exception {
		model.put("imgurl", imgurl);
		return "inloan/bizInterview/uploadFile";
	}
	
	/**
	 * 附件上传
	 */
	@RequestMapping(value = "/saveFile")
	public void saveFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Json j = super.getSuccessObj();
		Map<String, Object> map = FileUtil.processFileUpload(request, response, "interview", getUploadFilePath(), getFileSize(), getFileDateDirectory(), getUploadFileType());
		if ((Boolean) map.get("flag") == false) {
			logger.error("上传出错");
			j.getHeader().put("success", false);
			j.getHeader().put("uploadStatus", "文件上传失败or文件格式不合法");
		}else {
			// 拿到当前用户
			BaseClientFactory bizFileClientFactory = null;
			bizFileClientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "BizFileService");
			BizFileService.Client bizFileClient = (BizFileService.Client) bizFileClientFactory.getClient();
			BizFile bizFile = new BizFile();
			
			@SuppressWarnings("unchecked")
			List<FileItem> items = (List<FileItem>) map.get("items");
			for (int i = 0; i < items.size(); i++) {
				FileItem item = (FileItem) items.get(i);
				// 获取文件上传的信息
				int fileSize = (int) item.getSize();
				String fileFullName = item.getName().toLowerCase();
				bizFile.setFileSize(fileSize);
				bizFile.setFileName(item.getName());
				int dotLocation = fileFullName.lastIndexOf(".");
				String fileName = fileFullName.substring(0, dotLocation);
				fileName = fileName.replaceAll(" ", "");
				String fileType = fileFullName.substring(dotLocation).substring(1);
				bizFile.setFileType(fileType);
				bizFile.setFileName(fileName);
			}
			
			bizFile.setFileUrl(map.get("path").toString());
			String uploadDttm = DateUtil.format(new Date());
			bizFile.setUploadDttm(uploadDttm);
			bizFile.setStatus(1);
			int uploadUserId = getShiroUser().getPid();
			bizFile.setUploadUserId(uploadUserId);
			
			int pid = bizFileClient.saveBizFile(bizFile);
			j.getBody().put("pid", pid);
		} 
		j.getHeader().put("success", map.get("flag"));
		j.getHeader().put("path", map.get("path"));
		outputJson(j, response);

	}
	
	/**
	 * 领他证
	 */
	@RequestMapping(value="his")
	public void his(BizInterviewInfo infoDto,HttpServletRequest request, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		
		if(infoDto == null || infoDto.getPid() <=0) {
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "操作失败!");
		}else {
			try {
				clientFactory = getFactory(BusinessModule.MODUEL_INLOAN, "BizInterviewInfoService");
				BizInterviewInfoService.Client bizInterviewInfoService = (BizInterviewInfoService.Client) clientFactory.getClient();
				BizInterviewInfo info = new BizInterviewInfo();
				//面签ID 
				info.setPid(infoDto.getPid());
				info.setProjectId(infoDto.getProjectId());
				//他项权证
				info.setHisWarrant(infoDto.getHisWarrant());
				//他项权证领取时间
				info.setHisWarrantTime(DateUtils.getDateStartStr(infoDto.getHisWarrantTime()));
				//他项权证经办人
				info.setHisWarrantUser(infoDto.getHisWarrantUser());
				//他项权证经办人
				info.setMortgageUser(infoDto.getMortgageUser());
				//关联他证文件
				info.setInterviewFile(infoDto.getInterviewFile());
				
				if(bizInterviewInfoService.his(info) > 0) {
					j.getHeader().put("success", true);
					j.getHeader().put("msg", "操作成功!");
				}else {
					j.getHeader().put("success", false);
					j.getHeader().put("msg", "面签/公证/抵押未处理!");
				}
			} catch (Exception e) {
				logger.error("面签指派:" + ExceptionUtil.getExceptionMessage(e));
			} finally {
				destroyFactory(clientFactory);
			}
		}
		j.getHeader().put("success", true);
		j.getHeader().put("msg", "操作成功!");
		
		outputJson(j, response);
		
	}
	
	
}
