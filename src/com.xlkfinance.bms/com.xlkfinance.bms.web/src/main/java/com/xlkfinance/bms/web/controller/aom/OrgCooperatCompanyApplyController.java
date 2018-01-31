/**
 * @Title: OrgCooperatCompanyApplyController.java
 * @Package com.xlkfinance.bms.web.controller.aom
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: baogang
 * @date: 2016年7月8日 下午4:19:32
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.aom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
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
import com.achievo.framework.util.DateUtil;
import com.qfang.xk.aom.rpc.org.OrgAssetsCooperationInfo;
import com.qfang.xk.aom.rpc.org.OrgAssetsCooperationService;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApply;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyInf;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyService;
import com.qfang.xk.aom.rpc.org.OrgCooperateCityInfo;
import com.qfang.xk.aom.rpc.org.OrgCooperationContract;
import com.qfang.xk.aom.rpc.org.OrgCooperationContractService;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.constant.OrgSysLogTypeConstant;
import com.xlkfinance.bms.common.constant.SysLogTypeConstant;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.rpc.system.BizFileService;
import com.xlkfinance.bms.rpc.system.SysAreaInfo;
import com.xlkfinance.bms.rpc.system.SysAreaInfoService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;
import com.xlkfinance.bms.web.util.FileUtil;
import com.xlkfinance.bms.web.util.ParseDocAndDocxUtil;
/**
 * 机构合作申请信息
  * @ClassName: OrgCooperatCompanyApplyController
  * @author: baogang
  * @date: 2016年7月8日 下午4:23:56
 */
@Controller
@RequestMapping("/orgCooperatCompanyApplyController")
public class OrgCooperatCompanyApplyController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(OrgCooperatCompanyApplyController.class);
	
	/**
	 * 跳转到结构合作申请列表页面
	  * @return
	  * @author: baogang
	  * @date: 2016年7月8日 下午4:33:24
	 */
	@RequestMapping(value="cooperateIndex")
	public String cooperateIndex(){
		return "aom/org/cooperate_index";
	}
	
	/**
	 * 跳转到审批成功列表
	  * @return
	  * @author: baogang
	  * @date: 2016年9月1日 上午9:29:26
	 */
	@RequestMapping(value="applyIndex")
	public String applyIndex(){
		return "aom/org/apply_success_index";
	}
	
	/**
	 * 分页查询机构合作申请信息
	  * @param orgCooperat
	  * @param request
	  * @param response
	  * @author: baogang
	  * @date: 2016年7月8日 下午4:59:22
	 */
	@RequestMapping(value = "getAllOrgCooperate", method = RequestMethod.POST)
	public void getAllOrgCooperate(OrgCooperatCompanyApply orgCooperat,HttpServletRequest request, HttpServletResponse response){
		// 需要返回的Map集合
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory orgFactory = null;
		try {
			orgFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgCooperatCompanyApplyService");
			OrgCooperatCompanyApplyService.Client client = (OrgCooperatCompanyApplyService.Client) orgFactory.getClient();
			// 创建集合
			orgCooperat.setUserIds(getUserIds(request));
			List<OrgCooperatCompanyApply> list = client.getOrgCooperateByPage(orgCooperat);
			int count = client.getOrgCooperateCount(orgCooperat);
			map.put("rows", list);
			map.put("total", count);
		} catch (Exception e) {
				logger.error("分页查询机构合作申请信息:" + ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(orgFactory);
		}
		// 输出
		outputJson(map, response);
	}
	
	/**
	 * 查询审核通过的合作申请
	  * @param orgCooperat
	  * @param request
	  * @param response
	  * @author: baogang
	  * @date: 2016年9月1日 上午9:30:44
	 */
	@RequestMapping(value = "getAllApplySuccess", method = RequestMethod.POST)
	public void getAllApplySuccess(OrgCooperatCompanyApply orgCooperat,HttpServletRequest request, HttpServletResponse response){
		// 需要返回的Map集合
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory orgFactory = null;
		try {
			orgFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgCooperatCompanyApplyService");
			OrgCooperatCompanyApplyService.Client client = (OrgCooperatCompanyApplyService.Client) orgFactory.getClient();
			orgCooperat.setApplyStatus(AomConstant.BUSINESS_REQUEST_STATUS_4);
			// 创建集合
			List<OrgCooperatCompanyApply> list = client.getOrgCooperateByPage(orgCooperat);
			int count = client.getOrgCooperateCount(orgCooperat);
			map.put("rows", list);
			map.put("total", count);
		} catch (Exception e) {
				logger.error("分页查询机构合作申请信息:" + ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(orgFactory);
		}
		// 输出
		outputJson(map, response);
	}
	
	/**
	 * 跳转机构合作编辑页面
	  * @param pid
	  * @param editType
	  * @param model
	  * @return
	  * @author: baogang
	  * @date: 2016年7月8日 下午4:49:42
	 */
	@RequestMapping(value="editCooperate")
	public String editCooperate(@RequestParam(value = "isEdit", required = false) String isEdit,@RequestParam(value = "pid", required = false) Integer pid,@RequestParam(value = "orgId", required = false) Integer orgId,@RequestParam(value = "editType", required = false)String editType, ModelMap model){
		OrgCooperatCompanyApplyInf orgCooperateInfo = new OrgCooperatCompanyApplyInf();
		OrgAssetsCooperationInfo orgAssetsInfo = new OrgAssetsCooperationInfo();
		BaseClientFactory orgFactory = null;
		BaseClientFactory clientFactory = null;
		try {
			orgFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgCooperatCompanyApplyService");
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgAssetsCooperationService");
			OrgCooperatCompanyApplyService.Client client = (OrgCooperatCompanyApplyService.Client) orgFactory.getClient();
			OrgAssetsCooperationService.Client assetsClient = (OrgAssetsCooperationService.Client) clientFactory.getClient();
			if(pid != null && pid>0){
				//申请信息
				orgCooperateInfo = client.getByPid(pid);
				orgAssetsInfo = orgCooperateInfo.getOrgAssetsInfo();
			}
			if(orgId != null && orgId>0){
				//机构信息
				orgAssetsInfo = assetsClient.getById(orgId);
			}
		} catch (Exception e) {
			logger.error("根据ID查询机构合作申请信息:" + ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(clientFactory,orgFactory);
		}
		model.put("orgCooperateInfo", orgCooperateInfo);
		model.put("orgAssetsInfo", orgAssetsInfo);
		model.put("editType", editType);
		model.put("isEdit", isEdit);
		return "aom/org/edit_cooperate";
	}
	
	/**
	 * 查看机构合作详情
	  * @param pid
	  * @param model
	  * @return
	  * @author: baogang
	  * @date: 2016年9月1日 上午10:02:27
	 */
	@RequestMapping(value="lookupCooperate")
	public String lookupCooperate(@RequestParam(value = "pid", required = false) Integer pid, ModelMap model){
		OrgCooperatCompanyApplyInf orgCooperateInfo = new OrgCooperatCompanyApplyInf();
		OrgAssetsCooperationInfo orgAssetsInfo = new OrgAssetsCooperationInfo();
		BaseClientFactory orgFactory = null;
		BaseClientFactory clientFactory = null;
		try {
			orgFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgCooperatCompanyApplyService");
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgAssetsCooperationService");
			OrgCooperatCompanyApplyService.Client client = (OrgCooperatCompanyApplyService.Client) orgFactory.getClient();
			if(pid != null && pid>0){
				//申请信息
				orgCooperateInfo = client.getByPid(pid);
				orgAssetsInfo = orgCooperateInfo.getOrgAssetsInfo();
			}
		} catch (Exception e) {
			logger.error("根据ID查询机构合作申请信息:" + ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(clientFactory,orgFactory);
		}
		model.put("orgCooperateInfo", orgCooperateInfo);
		model.put("orgAssetsInfo", orgAssetsInfo);
		return "aom/org/look_apply_info";
	}
	
	/**
	 * 保存合作申请信息
	  * @param cooperationInfo
	  * @param response
	  * @author: baogang
	  * @date: 2016年7月12日 上午9:30:52
	 */
	@RequestMapping(value="saveOrgCompanyApplyInfo")
	public void saveOrgCompanyApplyInfo(OrgCooperatCompanyApplyInf orgCooperateInfo,HttpServletResponse response,HttpServletRequest req){
		logger.info("保存合作申请信息，参数：" + orgCooperateInfo);
		Json j = super.getSuccessObj();
		recordAomLog(OrgSysLogTypeConstant.LOG_TYPE_ADD, "保存合作申请信息：OrgCooperatCompanyApplyController.saveOrgCompanyApplyInfo 参数："+orgCooperateInfo, req);
		//ERP 后台日志
		recordLog(BusinessModule.MODUEL_ORG, SysLogTypeConstant.LOG_TYPE_UPDATE, "保存合作申请信息"+orgCooperateInfo,orgCooperateInfo.getPid());
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgCooperatCompanyApplyService");
			OrgCooperatCompanyApplyService.Client client = (OrgCooperatCompanyApplyService.Client) clientFactory.getClient();
			int pid = orgCooperateInfo.getPid();
			//登录人员ID
			int userId = getSysUser().getPid();
			if(pid>0){
				orgCooperateInfo.setUpdateId(userId);
			}else{
				orgCooperateInfo.setCreatorId(userId);
				orgCooperateInfo.setStatus(AomConstant.STATUS_ENABLED);//保证金额调整状态,1表示正常（不做调整）,2表示待审核,3表示已调整
				orgCooperateInfo.setApplyStatus(AomConstant.ORG_COOPERATE_APPLY_STATUS_1);
			}
			
			pid =client.saveOrgCooperateApplyInfo(orgCooperateInfo);
			//合作信息保存成功后，保存合作城市信息
//			if(pid>0){
//				int orgId = orgCooperateInfo.getOrgId();
//				String citys = orgCooperateInfo.getCooperationCitys();
//				client.saveOrgCooperateCitys(citys, orgId);
//			}
			j.getHeader().put("pid", pid);
			j.getHeader().put("applyStatus", orgCooperateInfo.getApplyStatus());
			j.getHeader().put("success", true);
			j.getHeader().put("msg", "保存成功");
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.error("保存合作申请信息:" + ExceptionUtil.getExceptionMessage(e));
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "保存失败,请重新操作!");
		}finally{
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(j, response);
	}
	
	/**
	 * 判断机构是否已提交合作申请,已存在返回1，不存在返回0
	  * @param orgId
	  * @param response
	  * @author: baogang
	  * @date: 2016年7月11日 下午3:34:15
	 */
	@RequestMapping(value="checkOrgCooperate" , method=RequestMethod.POST)
	public void checkOrgCooperate(@RequestParam(value = "orgId", required = false) Integer orgId, HttpServletResponse response){
		int flag = 0;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgCooperatCompanyApplyService");
			OrgCooperatCompanyApplyService.Client client = (OrgCooperatCompanyApplyService.Client) clientFactory.getClient();
			OrgCooperatCompanyApplyInf orgCooperate = new OrgCooperatCompanyApplyInf();
			orgCooperate.setOrgId(orgId);
			List<OrgCooperatCompanyApplyInf> result = client.getAll(orgCooperate );
			if(result != null && result.size() > 0){
				flag = 1;
			}
		} catch (Exception e) {
			logger.error("OrgAssetsCooperationController.checkEmailIsExist:" + ExceptionUtil.getExceptionMessage(e));
	        e.printStackTrace();
		}finally{
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(flag, response);
	}
	
	/**
	 * 上传合同文件
	  * @param request
	  * @param response
	  * @author: baogang
	  * @date: 2016年8月19日 上午10:38:42
	 */
	@RequestMapping(value = "/uploadFile")
	@ResponseBody
	public void uploadFile(HttpServletRequest request, HttpServletResponse response){
		OrgCooperationContract contractInfo = new OrgCooperationContract();
		Json j = super.getSuccessObj();
		BaseClientFactory bizFactory = null;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgCooperationContractService");
			bizFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "BizFileService");
			BizFileService.Client bizFileServiceClient = (BizFileService.Client) bizFactory.getClient();
			OrgCooperationContractService.Client client = (OrgCooperationContractService.Client) clientFactory.getClient();
			BizFile bizFile = saveFile(request, response, contractInfo);
			
			int pid = contractInfo.getPid();
			//保存bizFile数据
			int fileId = bizFileServiceClient.saveBizFile(bizFile);// 保存文件信息
			if(fileId >0){
				contractInfo.setFileId(fileId);
			}
			if(pid >0){
				client.update(contractInfo);
			}else{
				client.insert(contractInfo);
			}
			j.getHeader().put("success", true);
			j.getHeader().put("msg", "保存成功");
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.error("保存合作申请信息:" + ExceptionUtil.getExceptionMessage(e));
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "保存失败,请重新操作!");
		}finally{
			destroyFactory(clientFactory,bizFactory);
		}
		// 输出
		outputJson(j, response);
	}
	
	
	/**
	 * 上传文件
	  * @param request
	  * @param response
	  * @return
	  * @throws ServletException
	  * @throws IOException
	  * @author: baogang
	  * @date: 2016年7月14日 下午5:11:02
	 */
	private BizFile saveFile(HttpServletRequest request, HttpServletResponse response,OrgCooperationContract contractInfo) throws ServletException, IOException {
      // 文件信息BIZ_FILE
      BizFile bizFile = new BizFile();
      Map<String, Object> map = new HashMap<String, Object>();
      map = FileUtil.processFileUpload(request, response, BusinessModule.MODUEL_INLOAN, getAomUploadFilePath(), getFileSize(), getFileDateDirectory(), getUploadFileType());
      @SuppressWarnings("rawtypes")
      List items = (List) map.get("items");
      String remark = "";
      for (int i = 0; i < items.size(); i++) {
         FileItem item = (FileItem) items.get(i);
         String fieldName = item.getFieldName();
         
         if (item.isFormField()) {
				if (item.getFieldName().equals("cooperationId")) {
					contractInfo.setCooperationId(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
				} else if (item.getFieldName().equals("contractNo")) {
					contractInfo.setContractNo(ParseDocAndDocxUtil.getStringCode(item));
				} else if (item.getFieldName().equals("remark")) {
					remark = ParseDocAndDocxUtil.getStringCode(item);
				}else if (item.getFieldName().equals("pid")) {
					String stringCode = ParseDocAndDocxUtil.getStringCode(item);
					if(!StringUtil.isBlank(stringCode)){
						contractInfo.setPid(Integer.parseInt(stringCode));
					}
				}
			}else if ("fileName".equals(fieldName)) {
               if (item.getSize() != 0) {
                  bizFile.setFileSize((int) item.getSize());
                  // 获得文件名
                  String fileFullName = item.getName().toLowerCase();
                  int dotLocation = fileFullName.lastIndexOf(".");
                  String fileName = fileFullName.substring(0, dotLocation);
                  fileName = fileName.replaceAll(" ", "");
                  String fileType = fileFullName.substring(dotLocation).substring(1);
                  bizFile.setFileType(fileType);
                  bizFile.setFileName(fileName);
               }
         }
      }
      contractInfo.setRemark(remark);
      if (bizFile.getFileSize() == 0) {
         return bizFile;
      }
      // 文件信息设置值
      String uploadDttm = DateUtil.format(new Date());
      bizFile.setUploadDttm(uploadDttm);
      String fileUrl = String.valueOf(map.get("path"));
      if (fileUrl == null || "null".equalsIgnoreCase(fileUrl)) {
         return bizFile;
      }
      bizFile.setFileUrl(fileUrl);
      int uploadUserId = getShiroUser().getPid();
      bizFile.setUploadUserId(uploadUserId);
      bizFile.setStatus(Constants.STATUS_ENABLED);
      bizFile.setRemark(remark);
      return bizFile;
   }
	
	/**
	 * 获取合同列表
	  * @param cooperationId
	  * @param request
	  * @param response
	  * @author: baogang
	  * @date: 2016年8月19日 上午10:39:13
	 */
	@RequestMapping(value = "getOrgContractList", method = RequestMethod.POST)
	public void getOrgContractList(@RequestParam(value = "cooperationId", required = false)Integer cooperationId,HttpServletRequest request, HttpServletResponse response){
		// 需要返回的Map集合
		Map<String, Object> map = new HashMap<String, Object>();
		List<OrgCooperationContract> list = new ArrayList<OrgCooperationContract>();
		BaseClientFactory clientFactory = null;
		if(cooperationId >0){
			try {
				clientFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgCooperationContractService");
				OrgCooperationContractService.Client client = (OrgCooperationContractService.Client) clientFactory.getClient();
				
				OrgCooperationContract orgCooperationContract = new OrgCooperationContract();
				orgCooperationContract.setCooperationId(cooperationId);
				// 创建集合
				list = client.getAll(orgCooperationContract);
				map.put("rows", list);
			} catch (Exception e) {
					logger.error("获取合同列表:" + ExceptionUtil.getExceptionMessage(e));
			}finally{
				destroyFactory(clientFactory);
			}
		}
		
		// 输出
		outputJson(list, response);
	}
	
	/**
	 * 删除合作合同附件
	  * @param pid
	  * @param request
	  * @param resp
	  * @author: baogang
	  * @date: 2016年9月2日 上午11:20:10
	 */
	@RequestMapping(value="delContractFile")
	public void delContractFile(Integer pid,HttpServletRequest request, HttpServletResponse resp){
		if(pid<=0){
			logger.error("请求数据不合法：" + pid);
		    fillReturnJson(resp, false, "请输入必填项,重新操作!");
		    return;
		}
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgCooperationContractService");
			OrgCooperationContractService.Client client = (OrgCooperationContractService.Client) clientFactory.getClient();
			client.deleteById(pid);
			recordAomLog(OrgSysLogTypeConstant.LOG_TYPE_DELETE, "删除合作合同附件：OrgCooperatCompanyApplyController.delContractFile 参数："+pid, request);
			//ERP 后台日志
			recordLog(BusinessModule.MODUEL_ORG, SysLogTypeConstant.LOG_TYPE_DELETE, "删除合作合同附件:"+pid,pid);
			fillReturnJson(resp, true, "操作成功");
		} catch (Exception e) {
			fillReturnJson(resp, false, "操作失败,请重新操作!");
			logger.error("删除合作合同附件:" + ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(clientFactory);
		}
	}
	
	/**
	 * 传签单打印页面
	  * @param model
	  * @param pid
	  * @param request
	  * @return
	  * @author: baogang
	  * @date: 2016年9月7日 上午10:50:38
	 */
	@RequestMapping(value="printCompanyApplyInfo")
	public String printCompanyApplyInfo(ModelMap model, Integer pid, HttpServletRequest request){
		return "aom/org/print_apply_info";
	}
	
	
	/**
	 * 新增or修改合作城市信息表
	  * @param cooperationInfo
	  * @param response
	  * @author: jony
	  * @date: 2016年7月12日 上午9:30:52
	 */
	@RequestMapping(value="saveCityInfo")
	public void saveCityInfo(OrgCooperateCityInfo orgCooperateCityInfo ,HttpServletResponse response,HttpServletRequest req){
		logger.info("新增or修改合作城市信息表，参数：" + orgCooperateCityInfo);
		String cityCode = orgCooperateCityInfo.getAreaCode();
		
		//通过orgId 查询新增城市表是不是存在cityCode
		Json j = super.getSuccessObj();
		recordAomLog(OrgSysLogTypeConstant.LOG_TYPE_ADD, "新增or修改合作城市信息表：OrgCooperatCompanyApplyController.saveCityInfo 参数："+orgCooperateCityInfo, req);
		BaseClientFactory clientFactory = null;
		BaseClientFactory SysAreaInfoFactory = null;
		String cityName = "";
		String provinceName = "";
		SysAreaInfo sysAreaInfo1 = new SysAreaInfo();
		SysAreaInfo sysAreaInfo2 = new SysAreaInfo();
		List<OrgCooperateCityInfo> orgCooperateCityInfoList = new ArrayList<OrgCooperateCityInfo>();
		List<String> listCityCode = new ArrayList<String>();
		//status 0：修改，1:新增
		int status = orgCooperateCityInfo.getStatus();
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgAssetsCooperationService");
//			获取城市名字修改
			OrgAssetsCooperationService.Client client = (OrgAssetsCooperationService.Client) clientFactory.getClient();
			orgCooperateCityInfoList = client.getOrgCityInfoListByOrgId(orgCooperateCityInfo.getOrgId());
		    if(orgCooperateCityInfoList !=null && orgCooperateCityInfoList.size() >0){
			for(int i=0;i<orgCooperateCityInfoList.size();i++){
				listCityCode.add(orgCooperateCityInfoList.get(i).getAreaCode());
			}
		}
		//更新或者查询是不是有重复的城市
		if(listCityCode.contains(cityCode)){
			//status 0：修改，1:新增
			if(status==1){
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "保存失败,已存在该城市，请重新选择!");
				// 输出
				outputJson(j, response);
				destroyFactory(clientFactory);
				return;
			}
		}
			SysAreaInfoFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysAreaInfoService");
			SysAreaInfoService.Client sysAreaInfoClient = (SysAreaInfoService.Client) SysAreaInfoFactory.getClient();
			if(!StringUtil.isBlank(orgCooperateCityInfo.getAreaCode())){
				//获得市名 cityName
				sysAreaInfo1 = sysAreaInfoClient.getSysAreaInfoByCode(orgCooperateCityInfo.getAreaCode());
				cityName = sysAreaInfo1.getAreaName();
				orgCooperateCityInfo.setCityName(cityName);
			}
			if(!StringUtil.isBlank(orgCooperateCityInfo.getProvinceCode())){
				//获得省份 ProvinceName
				sysAreaInfo2 = sysAreaInfoClient.getSysAreaInfoByCode(orgCooperateCityInfo.getProvinceCode());
				provinceName= sysAreaInfo2.getAreaName();
				orgCooperateCityInfo.setProvinceName(provinceName);
			}
			if(status == 0){
				client.updateOrgCooperateCityInfo(orgCooperateCityInfo);
			}else{
				client.insertOrgCooperateCityInfo(orgCooperateCityInfo);
			}
		} catch (Exception e) {
			logger.error("保存合作申请信息:" + ExceptionUtil.getExceptionMessage(e));
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "保存失败,请重新操作!");
		}finally{
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(j, response);
	}
	
	/**
	 * 删除合作城市信息表
	  * @param cooperationInfo
	  * @param response
	  * @author: baogang
	  * @date: 2016年7月12日 上午9:30:52
	 */
	@RequestMapping(value="deleteCityInfo")
	public void deleteCityInfo(OrgCooperateCityInfo orgCooperateCityInfo ,HttpServletResponse response,HttpServletRequest req){
		logger.info("删除合作城市信息表，参数：" + orgCooperateCityInfo);
		Json j = super.getSuccessObj();
		recordAomLog(OrgSysLogTypeConstant.LOG_TYPE_ADD, "删除合作城市信息表：OrgCooperatCompanyApplyController.saveCityInfo 参数："+orgCooperateCityInfo, req);
		BaseClientFactory clientFactory = null;
		 BaseClientFactory projectFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		 
		int pid = orgCooperateCityInfo.getPid();
		int orgId = orgCooperateCityInfo.getOrgId();
		String areaCode = orgCooperateCityInfo.getAreaCode();
		Project project = new Project();
		List<Project> projectList = new ArrayList<Project>();
		try {
			project.setBusinessSourceNo(orgId);
			project.setAreaCode(areaCode);
			
			ProjectService.Client projectClient = (ProjectService.Client) projectFactory.getClient();
			//判断删除的机构是不是在项目表中有数据
			projectList = projectClient.checkProjectByNoAndCode(project);
			if(projectList.size()>0){
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "已存在此机构所属城市的订单，不允许删除!");
				destroyFactory(clientFactory);
				outputJson(j, response);
				return;
			}
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgAssetsCooperationService");
			OrgAssetsCooperationService.Client client = (OrgAssetsCooperationService.Client) clientFactory.getClient();
			client.deleteById(pid);
		} catch (Exception e) {
			logger.error("删除合作城市信息表:" + ExceptionUtil.getExceptionMessage(e));
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "删除失败,请重新操作!");
		}finally{
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(j, response);
	}
	
	
	/**
	 * 根据OrgId获取该所属机构的城市名列表
	 */
	@RequestMapping(value = "getCityByOrgId")
	@ResponseBody
	public void getCityByOrgId(Integer orgId, HttpServletResponse response){
		List<OrgCooperateCityInfo> resultList = new ArrayList<OrgCooperateCityInfo>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgCooperatCompanyApplyService");
			OrgCooperatCompanyApplyService.Client client = (OrgCooperatCompanyApplyService.Client) clientFactory.getClient();
			OrgCooperateCityInfo query  = new OrgCooperateCityInfo();
			query.setOrgId(orgId);
			//获取所属机构的城市名列表
			resultList = client.getOrgCooperateCityInfo(query);
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
		outputJson(resultList, response);
	}
	
	
}
