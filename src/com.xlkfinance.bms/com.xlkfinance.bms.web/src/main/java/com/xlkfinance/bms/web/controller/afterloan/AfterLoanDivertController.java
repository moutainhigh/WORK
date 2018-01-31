/**
 * @Title: AfterLoanDivertController.java
 * @Package com.xlkfinance.bms.web.controller.afterloan
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: gW
 * @date: 2015年3月31日 下午5:16:46
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.afterloan;

import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.afterloan.LoanDivertService;
import com.xlkfinance.bms.rpc.afterloan.LoanDivertinfo;
import com.xlkfinance.bms.rpc.beforeloan.CalcOperDto;
import com.xlkfinance.bms.rpc.beforeloan.Loan;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.beforeloan.RepaymentLoanInfo;
import com.xlkfinance.bms.rpc.finance.CustArrearsView;
import com.xlkfinance.bms.rpc.finance.FinanceService;
import com.xlkfinance.bms.rpc.repayment.RegAdvapplyFileview;
import com.xlkfinance.bms.rpc.repayment.RepayFeewdtlDatView;
import com.xlkfinance.bms.rpc.repayment.RepayFeewdtlService;
import com.xlkfinance.bms.rpc.repayment.UploadinstAdvapplyBaseDTO;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;
import com.xlkfinance.bms.web.shiro.ShiroUser;
import com.xlkfinance.bms.web.util.FileUtil;
import com.xlkfinance.bms.web.util.ParseDocAndDocxUtil;

@Controller
@RequestMapping("/afterLoanDivertController")
public class AfterLoanDivertController  extends BaseController {
	private Logger logger = LoggerFactory.getLogger(AfterLoanFileController.class);
	

	// 还款管理/利率变更任务更新计划还款表
	@RequestMapping("/advMakeRepayCgInfo")
	public void advMakeRepayCgInfo( int projectId,int divertId,HttpServletResponse response) {
		BaseClientFactory clientFactory = null;
		BaseClientFactory cf = null;
		BaseClientFactory cFt=null;
		Loan loan=null;
		RepaymentLoanInfo repayment =null;
		LoanDivertinfo divert=null;
		Json result = this.getSuccessObj();
		try {//更具项目id查询贷款信息
			clientFactory=	getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			loan=client.getLoanInfobyProjectId(projectId);
			
			//更具利挪用id查询挪用信息
			cf= getFactory(BusinessModule.MODUEL_AFTERLOAN, "LoanDivertService");
			LoanDivertService.Client ct = (LoanDivertService.Client) cf.getClient();
			divert= ct.queryLoanDivertServicebyDivertId(divertId);
			if(null==loan||loan.getPid()<1){
				logger.error("==========贷款信息为空=============");
			}
			//更具贷款id查询更新后的贷款信息跳转到贷款试算页面
			cFt = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client clt = (ProjectService.Client) cFt.getClient();
			repayment = clt.getRepaymentLoanInfo(loan.getPid());
			
			CalcOperDto calcOperDto=new CalcOperDto();
			calcOperDto.setMonthLoanMgr(loan.getMonthLoanMgr());
			calcOperDto.setMonthLoanOtherFee(loan.getMonthLoanOtherFee());
			calcOperDto.setMonthLoanInterest(loan.getMisFineInterest());
			calcOperDto.setInterestRate(divert.getDivertFine());
			calcOperDto.setOperAmt(divert.getDivertAmt());
			calcOperDto.setPid(divertId);
		    //挪用处理=4
			calcOperDto.setRepayType(4);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String divertFinePayDt = formatter.format(formatter.parse(divert.getDivertFinePayDt()));
			String divertFineEndDt = formatter.format(formatter.parse(divert.getDivertFineEndDt()));
			calcOperDto.setOperRepayDt(divertFinePayDt);
			calcOperDto.setEndDate(divertFineEndDt);
			int userId = getShiroUser().getPid();
			//	makeRepayMent(loan, 1,new CalcOperDto(0, 0, 0, 0, 0, "", 1,0,""));
			client.makeRepayMent(loan,userId, calcOperDto);
		} catch (Exception e) {
			logger.error("==========程序出错=============");
			
			result = this.getFailedObj("更新还款计划表失败");
		} finally {
			if (clientFactory != null||cf != null|| cFt != null) {
				try {
					clientFactory.destroy();
					cf.destroy();
					cFt.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		
       this.outputJson(result, response);
	}
	
	
	/**
	 *挪用工作流
	 */
	@RequestMapping(value = "updatefterLoanDivertinfo")
	public void  updatefterLoanDivertinfo( LoanDivertinfo loanDivertinfo,HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = null;
		int st=0;
		try {
			clientFactory= getFactory(BusinessModule.MODUEL_AFTERLOAN, "LoanDivertService");
			LoanDivertService.Client client = (LoanDivertService.Client) clientFactory.getClient();
			st= client.updateLoanDivertinfo(loanDivertinfo);
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
	outputJson(st, response);
	}
	/**
	 *挪用工作流跳转
	 */
	@RequestMapping(value = "afterLoanDivertbyprocess")
	public String AfterLoanDivertbyprocess(@RequestParam(value = "divertId")Integer divertId,ModelMap model) throws Exception {
		BaseClientFactory clientFactory = null;
		BaseClientFactory cF = null;
		BaseClientFactory ldsClientFactory = null;
		
		LoanDivertinfo divert=null;
		CustArrearsView custArrears=null; 
		LoanDivertinfo divprojectinfo=null;
		LoanDivertinfo st= null;
		try {
			clientFactory= getFactory(BusinessModule.MODUEL_AFTERLOAN, "LoanDivertService");
			LoanDivertService.Client client = (LoanDivertService.Client) clientFactory.getClient();
			divert= client.queryLoanDivertServicebyDivertId(divertId);
			divprojectinfo=	client.queryProjectDivertbyDivertId(divertId);
			cF = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");		
			FinanceService.Client ct = (FinanceService.Client) cF.getClient();
			custArrears=ct.getCustArrearsbyProjectView(divprojectinfo.getProjectId());
			
			ldsClientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "LoanDivertService");
			LoanDivertService.Client ldClient = (LoanDivertService.Client) clientFactory.getClient();
			st= ldClient.queryDivertinfobyprojectId(divert.getProjectId());
			 if(st!=null){
				 divert.setPlanOutLoanDt(st.getPlanOutLoanDt());
			 }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null|| cF!= null || ldsClientFactory!=null) {
				try {
					clientFactory.destroy();
					cF.destroy();
					ldsClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		// 显示最新的收息表
		model.put("showNew",true);
		// 格式化页面要显示的数据
		this.setCustArrearsView(custArrears);
		model.addAttribute("custArrears", custArrears);
		
		model.addAttribute("divert", divert);
		model.addAttribute("divertId", divertId);
		model.addAttribute("projectId", divprojectinfo.getProjectId());
		model.addAttribute("loanId", divprojectinfo.getLoanId());
		model.addAttribute("projectName", divprojectinfo.getProjectName());
		model.addAttribute("projectNum", divprojectinfo.getProjectNumber());
		return "afterloan/after_loan_divert_process";
	}
	/**
	 *挪用(监管结果跳转过来的挪用)
	 */
	@RequestMapping(value = "afterLoanDivert")
	public String AfterLoanDivert( int projectId,ModelMap model) throws Exception {
		BaseClientFactory clientFactory = null;
		CustArrearsView custArrears=null;
		BaseClientFactory cf = null;
		RepayFeewdtlDatView re=null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");		
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			custArrears=client.getCustArrearsbyProjectView(projectId);
			cf = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepayFeewdtlService");
			RepayFeewdtlService.Client ct = (RepayFeewdtlService.Client) cf.getClient();
			re= ct.queryRegFeewprojectinfobyproId(projectId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null||cf!= null) {
				try {
					cf.destroy();
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		// 显示最新的收息表
		model.put("showNew",true);
		this.setCustArrearsView(custArrears);
		model.addAttribute("custArrears",custArrears);
		
		model.addAttribute("divertId", -1);
		model.addAttribute("projectId", projectId);
		model.addAttribute("loanId", re.getLoanId());
		model.addAttribute("projectName", re.getProjectName());
		model.addAttribute("projectNum", re.getProjectNum());
		
		return "afterloan/after_loan_divert";
	}

	/**
	 *插入挪用信息
	 */
	@RequestMapping(value = "insertAfterLoanDivert")
	public void insertAfterLoanDivert( LoanDivertinfo loanDivertinfo,HttpServletResponse response ) throws Exception {
		BaseClientFactory clientFactory = null;
		int st=0;
		try {
			clientFactory= getFactory(BusinessModule.MODUEL_AFTERLOAN, "LoanDivertService");
			LoanDivertService.Client client = (LoanDivertService.Client) clientFactory.getClient();
		 st = client.insertLoanDivertService(loanDivertinfo);
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
		outputJson(st, response);
	}

	/**
	 *查询挪用信息
	 */
	@RequestMapping(value = "queryAfterLoanDivert")
	public void queryAfterLoanDivert( int divertId,HttpServletResponse response ) throws Exception {
		BaseClientFactory clientFactory = null;
		LoanDivertinfo loanDivertinfo=null;
		try {
			clientFactory= getFactory(BusinessModule.MODUEL_AFTERLOAN, "LoanDivertService");
			LoanDivertService.Client client = (LoanDivertService.Client) clientFactory.getClient();
			loanDivertinfo = client.queryLoanDivertServicebyDivertId(divertId);
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
		outputJson(loanDivertinfo, response);
	}
	

	@RequestMapping(value = "uploadDivertapply")
	@ResponseBody
	public void uploadDivertapply(HttpServletRequest request,HttpServletResponse response){
		UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO = new UploadinstAdvapplyBaseDTO();
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		ShiroUser shiroUser = getShiroUser();
		int userId = shiroUser.getPid();
		String path = null;
		int st = 0;
		String jsonString=null;
		try {
			request.setCharacterEncoding("UTF-8");
			map = FileUtil.processFileUpload(request, response, BusinessModule.MODUEL_REPAYMENT, getUploadFilePath(), getFileSize(), getFileDateDirectory(),getUploadFileType());
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepayFeewdtlService");
			RepayFeewdtlService.Client client = (RepayFeewdtlService.Client) clientFactory.getClient();
			if ((Boolean) map.get("flag") == false) {
				logger.error("上传出错");
				jsonString= "文件上传失败or文件格式不合法";
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
						 else if (item.getFieldName().equals("divertId")) {
								uploadinstAdvapplyBaseDTO.setDivertId(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
							}
					} else {
						if (item.getFieldName().equals("file1")) 
							uploadinstAdvapplyBaseDTO.setFileName(item.getName());
							uploadinstAdvapplyBaseDTO.setFileSize((int) item.getSize());
					}
				}
				String uploadDttm = DateUtil.format(new Date());
			uploadinstAdvapplyBaseDTO.setFileDttm(uploadDttm);
			path = String.valueOf(map.get("path"));
			String pathname = path.split("/")[path.split("/").length - 1];
			//uploadinstAdvapplyBaseDTO.setFileName(pathname);
			String fileType = pathname.substring(pathname.lastIndexOf(".")).substring(1);
			uploadinstAdvapplyBaseDTO.setFileType(fileType);
			// .substring( 0,path.length()-pathname.length()-1)
			uploadinstAdvapplyBaseDTO.setFilePath(path);
			uploadinstAdvapplyBaseDTO.setUploadUserid(userId + "");
			st = client.uploadinstiDvertapply(uploadinstAdvapplyBaseDTO);
			jsonString= "文件上传成功";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	
		outputJson(jsonString, response);
	}
	
	/**
	 * 
	  * @Description: 修改挪用资料文件信息
	  * @param uploadinstAdvapplyBaseDTO
	  * @param request
	  * @throws Exception
	  * @author: Zhangyu.Hoo
	  * @date: 2015年7月7日 下午4:34:18
	 */
	@RequestMapping(value = "updateEmbezzleFile")
	public void updateEmbezzleFile(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO,HttpServletRequest request,HttpServletResponse response)throws Exception {
		BaseClientFactory clientFactory = null;
		int count = 0;
		String result = "";
		try{
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepayFeewdtlService");
			RepayFeewdtlService.Client client = (RepayFeewdtlService.Client) clientFactory.getClient();
			count = client.updateEmbezzleFile(uploadinstAdvapplyBaseDTO);
			if(count > 0 ){
				result = "挪用文件修改成功!";
			}else{
				result = "挪用文件修改失败!";
			}
		}catch(Exception e){
			result = "挪用文件修改失败!";
			logger.info("修改挪用资料文件信息异常",e.getMessage());
		}
		outputJson(result, response);
	}
	
	/**
	 *查询挪用信息
	 */
	@RequestMapping(value = "queryRepayDivertapplyFile")
	public void queryRepayDivertapplyFile( int divertId,HttpServletResponse response ) throws Exception {
		if(divertId==-1){
			outputJson("", response);
		}
		BaseClientFactory clientFactory = null;
		List<RegAdvapplyFileview>  list = new ArrayList<RegAdvapplyFileview>();
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepayFeewdtlService");
			RepayFeewdtlService.Client client = (RepayFeewdtlService.Client) clientFactory.getClient();
			list = client.queryRegDivertapplyFile(divertId);
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
	}/**
	 *删除挪用信息
	 */
	@RequestMapping(value = "delectDivertbyId")
	public void delectDivertbyId( int divertId,int projectId,HttpServletResponse response ) throws Exception {
		BaseClientFactory clientFactory = null;
		int st=0;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "LoanDivertService");
			LoanDivertService.Client client = (LoanDivertService.Client) clientFactory.getClient();
			 st= client.delectDivertbyId(divertId,projectId);
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
		outputJson(st, response);
	}
	

	@RequestMapping(value = "queryDivertinfobyprojectId")
	public void queryDivertinfobyprojectId( int projectId,HttpServletResponse response ) throws Exception {
		BaseClientFactory clientFactory = null;
		LoanDivertinfo st=null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_AFTERLOAN, "LoanDivertService");
			LoanDivertService.Client client = (LoanDivertService.Client) clientFactory.getClient();
			 st= client.queryDivertinfobyprojectId(projectId);
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
		outputJson(st, response);
	}
	
	
}
