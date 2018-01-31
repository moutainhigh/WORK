/**
 * @Title: BadDebtController.java
 * @Package com.xlkfinance.bms.web.controller.afterloan
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: qiancong.Xian
 * @date: 2015年4月8日 下午3:07:13
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.afterloan;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.achievo.framework.util.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.afterloan.BadDebtBean;
import com.xlkfinance.bms.rpc.afterloan.BadDebtBeas;
import com.xlkfinance.bms.rpc.afterloan.BadDebtBeasCriteria;
import com.xlkfinance.bms.rpc.afterloan.LoanProRecCondition;
import com.xlkfinance.bms.rpc.afterloan.LoanProReconciliationDtlView;
import com.xlkfinance.bms.rpc.afterloan.MisapprProcessService;
import com.xlkfinance.bms.rpc.beforeloan.CalcOperDto;
import com.xlkfinance.bms.rpc.beforeloan.Loan;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.beforeloan.RepaymentLoanInfo;
import com.xlkfinance.bms.rpc.finance.BadDebtDataBean;
import com.xlkfinance.bms.rpc.finance.FinanceReceivablesView;
import com.xlkfinance.bms.rpc.finance.FinanceService;
import com.xlkfinance.bms.rpc.finance.LoanBaseDataBean;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssBase;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssBaseService;
import com.xlkfinance.bms.rpc.repayment.RegAdvapplyFileview;
import com.xlkfinance.bms.rpc.repayment.RepaymentPlanBaseDTO;
import com.xlkfinance.bms.rpc.repayment.UploadFileService;
import com.xlkfinance.bms.rpc.repayment.UploadinstAdvapplyBaseDTO;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.controller.finance.FinanceTypeEnum;
import com.xlkfinance.bms.web.page.Json;
import com.xlkfinance.bms.web.shiro.ShiroUser;
import com.xlkfinance.bms.web.util.FileUtil;
import com.xlkfinance.bms.web.util.ParseDocAndDocxUtil;

import fr.opensagres.xdocreport.utils.StringUtils;

/**
 *   坏账呆账处理
  * @ClassName: BadDebtController
  * @Description: TODO
  * @author: qiancong.Xian
  * @date: 2015年4月8日 下午3:07:27
 */
@Controller
@RequestMapping("/badDebtController")
public class BadDebtController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(BadDebtController.class);
	
	/**
	 * 坏账呆账处理
	 * 
	 * @param limitId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "listBadDebt")
	public String getBadDebt(
			@RequestParam(value = "limitId", required = false) Integer limitId,
			ModelMap model) throws Exception {
		return "afterloan/badDebtQuery";
	}

	/**
	 * 坏账呆账处理
	 * 
	 * @param badDebtBeasCriteria
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "getBadDebtlist")
	public void getBadDebtlist(BadDebtBeasCriteria badDebtBeasCriteria,
			HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(
				BusinessModule.MODUEL_AFTERLOAN, "MisapprProcessService");
		try {
			MisapprProcessService.Client client = (MisapprProcessService.Client) clientFactory
					.getClient();
			List<BadDebtBeas> list = client.getBadDebt(badDebtBeasCriteria);
			outputJson(list, response);
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
	}
	
	/**
	 * 查看或者编辑坏账处理
	 * 
	 * @param pid
	 * @param model
	 * @return
	 * @throws Exception
	 * @author: qiancong.Xian
	 * @date: 2015年4月9日 上午10:02:50
	 */
	@RequestMapping(value = "viewBadDebt")
	public String viewBadDebt(@RequestParam(value = "pid", required = true) Integer pid, 
			@RequestParam(value = "loanId", required = true) Integer loanId,
			@RequestParam(value = "projectId", required = true) Integer projectId, ModelMap model) throws Exception {

		BaseClientFactory clientFactory = null;
		//查询账户信息
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "MisapprProcessService");
			MisapprProcessService.Client client = (MisapprProcessService.Client) clientFactory.getClient();
			
			BadDebtBean bean = client.getBadDebtBean(pid);
			String uploadDttm = DateUtil.format(new Date());
			model.put("currDate",uploadDttm);
			model.put("badDebtBean", bean);
			model.put("loanId", loanId);
			model.put("projectId", bean.getProjectId());
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
		
		setLoanBaseDataBean(projectId,loanId, model);
		return "afterloan/viewBadDebt";
	}
	/**
	 * 
	  * @Description: 上传文件
	  * @param request
	  * @param response
	  * @author: gW
	  * @date: 2015年4月9日 下午3:29:26
	 */
	@RequestMapping(value = "uploadBaddealFile")
	@ResponseBody
	public void uploadBaddealFile(HttpServletRequest request,HttpServletResponse response){
		UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO = new UploadinstAdvapplyBaseDTO();
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		ShiroUser shiroUser = getShiroUser();
		int userId = shiroUser.getPid();
		String path = null;
		int st = 0;
		Json json = super.getSuccessObj();
		try {
			request.setCharacterEncoding("UTF-8");
			map = FileUtil.processFileUpload(request, response, BusinessModule.MODUEL_REPAYMENT, getUploadFilePath(), getFileSize(), getFileDateDirectory(),getUploadFileType());
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "UploadFileService");
			UploadFileService.Client client = (UploadFileService.Client) clientFactory.getClient();
			if ((Boolean) map.get("flag") == false) {
				logger.error("查询出错");
				json.getHeader().put("uploadStatus", "文件上传失败or文件格式不合法");
			} else {
				@SuppressWarnings("rawtypes")
				List items = (List) map.get("items");
				for (int j = 0; j < items.size(); j++) {
					FileItem item = (FileItem) items.get(j);
					if (item.isFormField()) {
						if (item.getFieldName().equals("fileKinds")) {
							uploadinstAdvapplyBaseDTO.setFileKinds(ParseDocAndDocxUtil.getStringCode(item));
						} else if (item.getFieldName().equals("fileDesc")) {
							uploadinstAdvapplyBaseDTO.setFileDesc(ParseDocAndDocxUtil.getStringCode(item));
						} else if (item.getFieldName().equals("projectId")) {
							uploadinstAdvapplyBaseDTO.setProjectId(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
						}
						 else if (item.getFieldName().equals("badId")) {
								uploadinstAdvapplyBaseDTO.setBadId(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
							}
					} else {
						if (item.getFieldName().equals("file1")){
							uploadinstAdvapplyBaseDTO.setFileSize((int) item.getSize());
							String fileFullName = item.getName().toLowerCase();
							int dotLocation = fileFullName.lastIndexOf(".");
							String fileName = fileFullName.substring(0, dotLocation);
							String fileType = fileFullName.substring(dotLocation).substring(1);
							uploadinstAdvapplyBaseDTO.setFileType(fileType);
							uploadinstAdvapplyBaseDTO.setFileName(fileName);
						}
							
					}
				}
				String uploadDttm = DateUtil.format(new Date());
			uploadinstAdvapplyBaseDTO.setFileDttm(uploadDttm);
			path = String.valueOf(map.get("path"));
			// .substring( 0,path.length()-pathname.length()-1)
			uploadinstAdvapplyBaseDTO.setFilePath(path);
			uploadinstAdvapplyBaseDTO.setUploadUserid(userId + "");
			st = client.uploadBaddealFile(uploadinstAdvapplyBaseDTO);
			//response.getWriter().write("saveSucc");
			json.getHeader().put("saveSucc", "saveSucc");
			}
		} catch (Exception e) {
			logger.error("呆账坏账上传失败",e);
		}finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(json, response);
	}	
	/**
	 * 
	  * @Description: 查询上传文件
	  * @param request
	  * @param response
	  * @author: gW
	  * @date: 2015年4月9日 下午3:29:26
	 */
	@RequestMapping("/queryBaddealFile")
	@ResponseBody
	public void queryBaddealFile(int badId, HttpServletResponse response) {
		if(badId==-1){
			outputJson("", response);
		}
		BaseClientFactory clientFactory = null;
		List<RegAdvapplyFileview>  list = new ArrayList<RegAdvapplyFileview>();
		try {
			
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "UploadFileService");
			UploadFileService.Client client = (UploadFileService.Client) clientFactory.getClient();
			list = client.queryBaddealFile(badId);;
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
		outputJson(list, response);
	}
	/**
	  * 设置项目收息表和项目的数据信息
	  * @param projectId
	  * @param model
	  * @author: qiancong.Xian
	  * @date: 2015年4月3日 上午11:55:07
	 */
	private void setLoanBaseDataBean(int projectId,int loanId,ModelMap model)
	{
		BaseClientFactory clientFactory = null;
		LoanBaseDataBean loanBaseDataBean = null;
		//查询账户信息
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
           //查找客户名称，客户可用余额
			loanBaseDataBean= client.getLoanBaseDataBean(projectId);
			
			FinanceReceivablesView bean = client.getFinanceReceivablesView(loanId);
			model.put("bean", bean);
			
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
		
		if(null == loanBaseDataBean)
		{
			 loanBaseDataBean = new LoanBaseDataBean();
		}
		
		model.put("loanBaseDataBean", loanBaseDataBean);
		
		//查询账户信息
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();								
			RepaymentLoanInfo repayment = client.getRepaymentLoanInfo(loanId);
			if(null !=repayment){
				model.put("otherCostName", repayment.getOtherCostName());
			}
			model.put("loanId", loanId);
			
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
	//add by yql
	/**
	 * 项目对账信息页面
	 * 
	 * @param limitId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getProjectReconciliation")
	public String getProjectReconciliation(int projectId,int loanId ,String recCycleNums,String realtimeIds,ModelMap model) throws Exception {
		// 查询项目信息
		BaseClientFactory clientFactory = null;
		
		//查询账户信息
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			
			Project project = client.getLoanProjectByProjectId(projectId);
			model.put("project", project);
			model.put("loanId", loanId);
			model.put("recCycleNums", recCycleNums);
			model.put("realtimeIds", realtimeIds);
			
			
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
		//传递查询条件
		return "afterloan/project_rec_list";
	}

	/**
	 * 项目对账信息列表
	 * 
	 * @param badDebtBeasCriteria
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "getProjectReconciliationList")
	public void getProjectReconciliationList(int loanId,String recCycleNums,String realtimeIds,
			HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory =null;
		try {
			LoanProRecCondition loanProRecCondition= new LoanProRecCondition();
			 List<String> recCycleNumList=new ArrayList<String>(); //期数列表
			 List<String> realtimeIdList=new ArrayList<String>(); // 即时发生表主键id
			 if(loanId!=0){
				 loanProRecCondition.setLoanId(loanId);
			 }
			 if(StringUtils.isNotEmpty(realtimeIds)){
				 String[] strs= realtimeIds.split(",");
				 for(String s:strs){
					 realtimeIdList.add(s);
				 }
				 loanProRecCondition.setRealtimeIds(realtimeIdList);
			 }
			 if(StringUtils.isNotEmpty(recCycleNums)){
				 String[] strs= recCycleNums.split(",");
				 for(String s:strs){
					 recCycleNumList.add(s);
				 }
				 loanProRecCondition.setRecCycleNums(recCycleNumList);
			 }
			 
			clientFactory= getFactory(BusinessModule.MODUEL_AFTERLOAN, "MisapprProcessService");
			MisapprProcessService.Client client = (MisapprProcessService.Client) clientFactory.getClient();
			
			List<LoanProReconciliationDtlView> list = client.getProjectReconciliationList(loanProRecCondition);
			
			outputJson(list, response);
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
	
	// add by yql end
	

	/**
	 * 查看或者编辑坏账处理
	 * 
	 * @param pid
	 * @param model
	 * @return
	 * @throws Exception
	 * @author: qiancong.Xian
	 * @date: 2015年4月9日 上午10:02:50
	 */
	@RequestMapping(value = "updateBadDebt")
	public void updateBadDebt(BadDebtBean badDebtBean,int loanId,HttpServletResponse response) throws Exception {
		Json result = null;
		BaseClientFactory clientFactory = null;
		//查询账户信息
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "MisapprProcessService");
			MisapprProcessService.Client client = (MisapprProcessService.Client) clientFactory.getClient();
			int updateRow = client.updateBadDebtBean(badDebtBean,loanId);
			if(updateRow == 0)
			{
				result = this.getFailedObj("未找到相关记录，请刷新后重新提交或者联系管理员。");
			}
			else
			{
				result = this.getSuccessObj();
			}
		} catch (Exception e) {
			logger.error("更新呆账坏账记录失败", e);
			result = this.getFailedObj("保存失败");
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
				}
			}
		}
		
		outputJson(result, response);
	}
	
	
	
	@RequestMapping(value = "getProjectAssBase")
	public void getProjectAssBase(@RequestParam(value = "projectId", required = true) int projectId,
	HttpServletResponse response) throws Exception {
		
		BaseClientFactory clientFactory = null;
		List<ProjectAssBase> projectAssBaseList = null;
		//查询账户信息
		try {
			ProjectAssBase projectAssBase = new ProjectAssBase();
			projectAssBase.setProjectId(projectId);
			projectAssBase.setStatus(1);
			projectAssBase.setMortgageType(-1);
			projectAssBase.setMortgageGuaranteeType(-1);// 查询所有
			
			clientFactory = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseService");
			ProjectAssBaseService.Client client = (ProjectAssBaseService.Client) clientFactory.getClient();
			projectAssBaseList = client.getAllProjectAssBase(projectAssBase);
			
			// 处理地址
			if(null != projectAssBaseList)
			{
				
				for(ProjectAssBase assBase:projectAssBaseList)
				{
					assBase.setAddressDetail(assBase.getAddressProvince()+assBase.getAddressCity()+assBase.getAddressArea()+assBase.getAddressDetail());
				}
			}
			
			
			
		} catch (Exception e) {
			projectAssBaseList = new ArrayList<ProjectAssBase>();
			logger.error("查询呆账坏账的抵押物列表失败", e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
				}
			}
		}
		
		outputJson(projectAssBaseList, response);
	}
	
	/**
	  * @Description: 展现坏账重算的结果
	  * @param loanId
	  * @param response
	  * @throws Exception
	  * @author: qiancong.Xian
	  * @date: 2015年4月10日 下午3:39:12
	 */
	@RequestMapping(value = "loanBadDetCalculation")
	public void loanBadDetCalculation(@RequestParam(value = "loanId", required = true) int loanId,
	HttpServletResponse response) throws Exception {
		
		BaseClientFactory clientFactory = null;
		
		//  输出结果
		JSONArray array = new JSONArray();
		//查询账户信息
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
            // 坏账计算后的数据
			List<BadDebtDataBean> list = client.getBadDebtDataBean(loanId);
			createRowArray(list, array);
		} catch (Exception e) {
			logger.error("获取呆账坏账结算后数据失败", e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		
		this.outputJson(array, response);
	}
	
	private void createRowArray(List<BadDebtDataBean> list,JSONArray array)
	{
		if(null == list || list.size()==0)
		{
			return;
		}
		
        List<double[]> dataList = new ArrayList<double[]>();
        dataList.add(new double[6]); // 先初始化第一行数据
        
		for(BadDebtDataBean bean:list)
		{
			// 坏账后应收本金
			if(bean.getOperType() == FinanceTypeEnum.TYPE_3.getType())
			{
				dataList.get(0)[1] = bean.getTotalAmt();
			}
			// 坏账后应收管理费
			else if(bean.getOperType() == FinanceTypeEnum.TYPE_12.getType())
			{
				dataList.get(0)[2] = bean.getTotalAmt();
			}
			// 坏账后应收其他费用
			else if(bean.getOperType() == FinanceTypeEnum.TYPE_13.getType())
			{
				dataList.get(0)[3] = bean.getTotalAmt();
			}
			// 坏账后应收利息
			else if(bean.getOperType() == FinanceTypeEnum.TYPE_14.getType())
			{
				dataList.get(0)[4] = bean.getTotalAmt();
			}
			else
			{
				double[] row = new double[6];
				row[0] = bean.getOperType();
				row[1] = 	bean.getTotalAmt();	
				dataList.add(row);
			}
		}
		
		double alltotal = 0;// 最后一行的合计
		for(int i=0;i<dataList.size();i++)
		{
			double[] row  = dataList.get(i);
			row[5] = row[1]+row[2]+row[3]+row[4];
			alltotal += row[5];
			// 如果合计为0，不添加这一行
			if(row[5] == 0)
			{
				continue;
			}
			
			JSONObject json = new JSONObject();
			json.put("typeName", i==0?"坏账后应收":FinanceTypeEnum.getName((int)row[0]));
			json.put("col1", row[1]);
			json.put("col2", row[2]);
			json.put("col3", row[3]);
			json.put("col4", row[4]);
			json.put("totalAmt", row[5]);
			
			array.add(json);
		}	
		
		// 最后一行合计
		JSONObject json = new JSONObject();
		json.put("typeName", "合计");
		json.put("col1", "");
		json.put("col2", "");
		json.put("col3", "");
		json.put("col4", "");
		json.put("totalAmt",alltotal);
		
		array.add(json);
	}
	
	
	/**
	  * @Description: 重算呆账坏账
	  * @param pid 坏账记录主键
	  * @param  asOfDT 坏账记息截止日
	  * @param  shouldDt 应收日期
	  * @param response
	  * @throws Exception
	  * @author: qiancong.Xian
	  * @date: 2015年4月10日 下午5:23:29
	 */
	@RequestMapping(value = "reCalculationBadDebt")
	public void reCalculationBadDebt(@RequestParam(value = "pid", required = true) int pid,
	@RequestParam(value = "projectId", required = true) int projectId,
	@RequestParam(value = "asOfDT", required = true) String asOfDT,
	@RequestParam(value = "shouldDt", required = true) String shouldDt,
	HttpServletResponse response) throws Exception {
		
		Json result = null;
		BaseClientFactory clientFactory =  null;
		try {
			 clientFactory = getFactory("beforeloan", "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			
			Loan loan= client.getLoanInfobyProjectId(projectId);
			CalcOperDto dto=new CalcOperDto(pid, 0,0,0, 0, shouldDt, 6,0,asOfDT,0);
			List<RepaymentPlanBaseDTO> list= client.makeRepayMent(loan, this.getShiroUser().getPid(),dto);
			result = this.getSuccessObj();
		} catch (Exception e) {
			logger.error("重算坏账呆账数据失败", e);
			result = this.getFailedObj("重算坏账呆账数据失败");
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		
		outputJson(result, response);
	}
	
	/**
	 * 查看或者编辑抵押物
	 * 
	 * @param pid
	 * @param model
	 * @return
	 * @throws Exception
	 * @author: qiancong.Xian
	 * @date: 2015年4月9日 上午10:02:50
	 */
	@RequestMapping(value = "viewCollateralProcessing",method=RequestMethod.POST)
	public String viewCollateralProcessing(@RequestParam(value = "pid", required = true) Integer pid,  ModelMap model) throws Exception {
		
		BaseClientFactory clientFactory = null;
			
		//查询账户信息
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseService");
			ProjectAssBaseService.Client client = (ProjectAssBaseService.Client) clientFactory.getClient();
			ProjectAssBase assBase = client.getProjectAssBaseByPid(pid);
			
			// 处理地址
			if(null != assBase)
			{
					assBase.setAddressDetail(assBase.getAddressProvince()+assBase.getAddressCity()+assBase.getAddressArea()+assBase.getAddressDetail());
			}
			
			if(assBase.getProcessDt() == null)
			{
				String uploadDttm = DateUtil.format(new Date());
				assBase.setProcessDt(uploadDttm);
			}	
			model.put("bean", assBase);
		} catch (Exception e) {
			logger.error("查看或者编辑抵押物", e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		
		return "afterloan/badDebt_collateralProcessing";
	}
	
	/**
	 * 查看或者编辑坏账处理
	 * 
	 * @param pid
	 * @param model
	 * @return
	 * @throws Exception
	 * @author: qiancong.Xian
	 * @date: 2015年4月9日 上午10:02:50
	 */
	@RequestMapping(value = "updateProjectAssBaseProcessing")
	public void updateProjectAssBaseProcessing(ProjectAssBase projectAssBase, HttpServletResponse response) throws Exception {
		Json result = null;
		BaseClientFactory clientFactory = null;
		// 查询账户信息
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_MORTGAGE, "ProjectAssBaseService");
			ProjectAssBaseService.Client client = (ProjectAssBaseService.Client) clientFactory.getClient();
			int row = client.updateProjectAssBaseProcessing(projectAssBase);
			if (row > 0) {
				result = this.getSuccessObj();
			} else {
				result = this.getFailedObj("找到不到对于的记录，请联系管理员");
			}

		} catch (Exception e) {
			logger.error("更新抵押物处理信息失败", e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
				}
			}
		}

		this.outputJson(result, response);
	}
}
