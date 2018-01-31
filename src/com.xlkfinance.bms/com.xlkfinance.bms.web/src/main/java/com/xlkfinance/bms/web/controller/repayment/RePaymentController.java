/**
 * @Title: RePaymentController.java
 * @Package com.xlkfinance.bms.web.controller.repayment
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: gaoWen
 * @date: 2014年12月11日 上午9:54:39
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.repayment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.thrift.TException;
import org.apache.xmlbeans.XmlException;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.beforeloan.CalcOperDto;
import com.xlkfinance.bms.rpc.beforeloan.Loan;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.beforeloan.RepaymentLoanInfo;
import com.xlkfinance.bms.rpc.contract.TempLateParmDto;
import com.xlkfinance.bms.rpc.finance.CustArrearsView;
import com.xlkfinance.bms.rpc.finance.FinanceService;
import com.xlkfinance.bms.rpc.finance.LoanBaseDataBean;
import com.xlkfinance.bms.rpc.repayment.AdvRepaymentBaseDTO;
import com.xlkfinance.bms.rpc.repayment.AdvRepaymentView;
import com.xlkfinance.bms.rpc.repayment.AdvapplyRepaymentBaseDTO;
import com.xlkfinance.bms.rpc.repayment.AdvapplyRepaymentView;
import com.xlkfinance.bms.rpc.repayment.ApplyfileuploadBaseDTO;
import com.xlkfinance.bms.rpc.repayment.AvailabilityChangeDTO;
import com.xlkfinance.bms.rpc.repayment.AvailabilityChangeTabulationDTO;
import com.xlkfinance.bms.rpc.repayment.AvailabilityChangeTabulationView;
import com.xlkfinance.bms.rpc.repayment.AvailabilityChangeView;
import com.xlkfinance.bms.rpc.repayment.CostDerateDTO;
import com.xlkfinance.bms.rpc.repayment.CostDerateTabulationDTO;
import com.xlkfinance.bms.rpc.repayment.CostDerateTabulationView;
import com.xlkfinance.bms.rpc.repayment.CostDerateView;
import com.xlkfinance.bms.rpc.repayment.CusBusiness;
import com.xlkfinance.bms.rpc.repayment.ExecutiveOperBaseDTO;
import com.xlkfinance.bms.rpc.repayment.LoanExtensionService;
import com.xlkfinance.bms.rpc.repayment.PreApplyRepayBaseDTO;
import com.xlkfinance.bms.rpc.repayment.ProjectComplete;
import com.xlkfinance.bms.rpc.repayment.ProjectCompleteFile;
import com.xlkfinance.bms.rpc.repayment.RegAdvapplyFileview;
import com.xlkfinance.bms.rpc.repayment.RegAdvapplyRepayDTO;
import com.xlkfinance.bms.rpc.repayment.RegAdvapplyRepayView;
import com.xlkfinance.bms.rpc.repayment.RepayEconciliationDTO;
import com.xlkfinance.bms.rpc.repayment.RepayOverdueDTO;
import com.xlkfinance.bms.rpc.repayment.RepayOverdueView;
import com.xlkfinance.bms.rpc.repayment.RepaymentBaseDTO;
import com.xlkfinance.bms.rpc.repayment.RepaymentPlanBaseDTO;
import com.xlkfinance.bms.rpc.repayment.RepaymentService;
import com.xlkfinance.bms.rpc.repayment.RepaymentView;
import com.xlkfinance.bms.rpc.repayment.SaveAdvRepaymentBaseDTO;
import com.xlkfinance.bms.rpc.repayment.SettleFile;
import com.xlkfinance.bms.rpc.repayment.UploadFileService;
import com.xlkfinance.bms.rpc.repayment.UploadinstAdvapplyBaseDTO;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.rpc.system.BizFileService;
import com.xlkfinance.bms.rpc.system.TemplateFile;
import com.xlkfinance.bms.rpc.system.TemplateFileService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.controller.contract.ContractGenerator;
import com.xlkfinance.bms.web.controller.contract.MoneyUtil;
import com.xlkfinance.bms.web.shiro.ShiroUser;
import com.xlkfinance.bms.web.util.CommonUtil;
import com.xlkfinance.bms.web.util.ExcelExport;
import com.xlkfinance.bms.web.util.FileDownload;
import com.xlkfinance.bms.web.util.FileUtil;
import com.xlkfinance.bms.web.util.ParseDocAndDocxUtil;

@Controller
@RequestMapping("/rePaymentController")
public class RePaymentController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(RePaymentController.class);
	
		@RequestMapping("/repayfinancialDeal")
		public String repayfinancialDeal() {
			return "repayment/financial_deal";
		}
		
		
		// 还款管理/重新生成计划还款表
		@RequestMapping("/advmakeRepayMent")
		public String   advmakeRepayMent(int projectId,int preRepayId,ModelMap model ) {
			BaseClientFactory clientFactory = null;
			BaseClientFactory cf = null;
			BaseClientFactory cFt=null;
			Loan loan=null;
			RepaymentLoanInfo repayment =null;
			try {
				clientFactory=	getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
				ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
				loan=client.getLoanInfobyProjectId(projectId);
				
				cf =getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
				RepaymentService.Client ct = (RepaymentService.Client) cf.getClient();
				RegAdvapplyRepayView cView=	ct.getrepayadvdatilbyProcess(preRepayId);
				
				// 设置新的还款罚金比例
				loan.setPrepayLiqDmgProportion(cView.getFineRates());
				
				cFt = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
				ProjectService.Client clt = (ProjectService.Client) cFt.getClient();
				repayment = clt.getRepaymentLoanInfo(loan.getPid());
				CalcOperDto calcOperDto=new CalcOperDto();
				calcOperDto.setMonthLoanMgr(loan.getMonthLoanMgr());
				calcOperDto.setMonthLoanOtherFee(loan.getMonthLoanOtherFee());
				calcOperDto.setMonthLoanInterest(loan.getMisFineInterest());
				calcOperDto.setPid(preRepayId);
				calcOperDto.setRepayType(2);
				calcOperDto.setOperAmt(cView.preRepayAmt);
				
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date date = formatter.parse(cView.getRepayDate());
				String datestring = formatter.format(date);
				calcOperDto.setOperRepayDt(datestring);
				int userId = getShiroUser().getPid();
				client.makeRepayMent(loan,userId, calcOperDto);
			} catch (Exception e) {
				logger.info("************* 提前还款,重新生成还款计划表异常 *****************"+e.getMessage());
			} finally {
				if ( null != clientFactory) {
					try {
						clientFactory.destroy();
					} catch (ThriftException e) {
						e.printStackTrace();
					}
				}
				if ( null != cf ) {
					try {
						cf.destroy();
					} catch (ThriftException e) {
						e.printStackTrace();
					}
				}
				if (null != cFt) {
					try {
						cFt.destroy();
						
					} catch (ThriftException e) {
						e.printStackTrace();
					}
				}
			}
			model.put("repayment", repayment);
			model.put("loanId", loan.getPid());
			return "beforeloan/loan_calc";
		}
		
	// 还款管理/工作流的提前还款跳转
	@RequestMapping("/repaydatilbyProcess")
	public String getrepaydatilbyProcess(int preRepayId,ModelMap model,HttpServletRequest request ) {
		RegAdvapplyRepayView reginfo =null;
		RegAdvapplyRepayView advinfo =null;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory=	getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			reginfo = client.getrepaydatilbyProcess(preRepayId);
			advinfo = client.getrepayadvdatilbyProcess(preRepayId);
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
		model.addAttribute("isShow", request.getParameter("isShow"));
		model.addAttribute("advinfo", advinfo);
		model.addAttribute("reginfo", reginfo);
		model.addAttribute("loanId", reginfo.getLoanId());
		model.addAttribute("preRepayId", preRepayId);
		model.addAttribute("projectId", reginfo.getProjectId());
		model.addAttribute("projectName", reginfo.getProjectName());
		model.addAttribute("projectNum", reginfo.getProjectNum());
		return "repayment/list_adv_applypro_repayment";
	}


	/**
	 * 
	  * @Description: 提前还款信息更新
	  * @param preApplyRepayBaseDTO
	  * @param response
	  * @author: Zhangyu.Hoo
	  * @date: 2015年6月26日 上午11:42:21
	 */
	@RequestMapping("/updateadvApplyrepayment")
	public void updateadvApplyrepayment(PreApplyRepayBaseDTO preApplyRepayBaseDTO,HttpServletResponse response) {
		BaseClientFactory clientFactory = null;
		int  st=0;
		try {
			clientFactory=	getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			st = client.updateadvApplyrepayment(preApplyRepayBaseDTO);
		} catch (Exception e) {
			logger.info("***********提前还款保存异常************"+e.getMessage());
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
	
	// 还款管理/费用减免列表
	@RequestMapping("/getCostDerateTabulationlist")
	public String getCostDerateTabulationlist() {
		return "repayment/list_costDerateTabulation";
	}

	// 还款管理/费用减免列表
	@RequestMapping("/costDerateTabulation")
	@ResponseBody
	public void costDerateTabulation(CostDerateTabulationDTO costDerateTabulationDTO, HttpServletResponse response) {
		List<CostDerateTabulationView> list = new ArrayList<CostDerateTabulationView>();
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			ShiroUser user=getShiroUser();
			costDerateTabulationDTO.setPmUserId(user.getPid());
			list = client.getCostDerateTabulation(costDerateTabulationDTO);
			count = client.getCostDerateTabulationCount(costDerateTabulationDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
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
		map.put("rows", list);
		map.put("total", count);
		outputJson(map, response);
	}

	// 还款管理/费用减免信息查询
	@RequestMapping("/getcostDeratelist")
	public String getcostDeratelist() {
		return "repayment/list_costDerate";
	}

	// 还款管理/利率变更/
	@RequestMapping("/listcostDerate")
	@ResponseBody
	public void listcostDerate(CostDerateDTO costDerateDTO, HttpServletResponse response) {
		List<CostDerateView> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		BaseClientFactory clientFactory = null;

		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			costDerateDTO.setRows(costDerateDTO.rows);
			costDerateDTO.setPage(costDerateDTO.page);
			ShiroUser user =getShiroUser();
			costDerateDTO.setPmUserId(user.getPid());
			list = client.getCostDerate(costDerateDTO);
			count = client.getCostDerateCount(costDerateDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
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
		map.put("rows", list);
		map.put("total", count);
		outputJson(map, response);
	}

	// 还款管理/提前还款列表
	@RequestMapping("/getavailabilityChangetabulationlist")
	public String getavailabilityChangetabulationlist() {
		return "repayment/list_availabilityChangetabulation";
	}

	// 还款管理/利息变更
	@RequestMapping("/availabilityChangeTabulation")
	@ResponseBody
	public void getavailabilityChangeTabulation(AvailabilityChangeTabulationDTO availabilityChangeTabulationDTO, HttpServletResponse response) {
		List<AvailabilityChangeTabulationView> list = new ArrayList<AvailabilityChangeTabulationView>();
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
	      	RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
	        ShiroUser user =getShiroUser();
	      	availabilityChangeTabulationDTO.setPmUserId(user.getPid());
			list = client.getAvailabilityTabulation(availabilityChangeTabulationDTO);
			count = client.getAvailabilityTabulationCount(availabilityChangeTabulationDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
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
		map.put("rows", list);
		map.put("total", count);
		outputJson(map, response);
	}

	// 还款管理/利率变更/查询及展示
	@RequestMapping("/getavailabilityChangelist")
	public String getavailabilityChangelist() {
		return "repayment/list_availabilityChange";
	}

	// 还款管理/利率变更/查询及展示
	@RequestMapping("/listavailabilityChange")
	@ResponseBody
	public void getavailabilityChange(AvailabilityChangeDTO availabilityChangeDTO, HttpServletResponse response) {
		List<AvailabilityChangeView> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			ShiroUser user =getShiroUser();
			availabilityChangeDTO.setPmUserId(user.getPid());
			list = client.getChangeList(availabilityChangeDTO);
			count = client.getChangeTotaleCount(availabilityChangeDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
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
		map.put("rows", list);
		map.put("total", count);
		outputJson(map, response);
	}

	// 还款管理/查询及展示
	@RequestMapping("/getrepaymentlist")
	public String getrepaymentlist() {
		return "repayment/list_repayment";
	}

	// 还款管理/查询及展示
	@RequestMapping("/listrepaymenturl")
	@ResponseBody
	public void getlistrepaymenturl(RepaymentBaseDTO repaymentBaseDTO, HttpServletResponse response) {
		List<RepaymentView> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			ShiroUser user=getShiroUser();
			repaymentBaseDTO.setPmUserId(user.getPid());
			list = client.getRepaymentList(repaymentBaseDTO);
			count = client.getTempTotaleCount(repaymentBaseDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
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
		map.put("rows", list);
		map.put("total", count);
		outputJson(map, response);
	}

	// 还款管理/提前还款列表
	@RequestMapping("/getadvrepaymentlist")
	public String getadvrepaymentlist( ) {
		return "repayment/list_adv_repayment";
	}

	// 还款管理/提前还款列表
	@RequestMapping("/advrepaymenturl")
	@ResponseBody
	public void getadvrepaymenturl(AdvRepaymentBaseDTO advRepaymentBaseDTO, HttpServletResponse response) {
		List<AdvRepaymentView> list = new ArrayList<AdvRepaymentView>();
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			advRepaymentBaseDTO.setRows(advRepaymentBaseDTO.rows);
			advRepaymentBaseDTO.setPage(advRepaymentBaseDTO.page);
			ShiroUser user=getShiroUser();
			advRepaymentBaseDTO.setPmUserId(user.getPid());
			list = client.getAdvrepaymenturl(advRepaymentBaseDTO);
			count = client.getAdvRepayTotaleCount(advRepaymentBaseDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
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
		map.put("rows", list);
		map.put("total", count);
		outputJson(map, response);
	}

	/*
	 * 还款管理/提前还款申请
	 */
	@RequestMapping(value = "/getadvapplyrepaymentlist", method = RequestMethod.GET)
	public String getadvapplyrepaymentlist(RegAdvapplyRepayDTO regAdvapplyRepayDTO, HttpServletResponse response, ModelMap model) throws UnsupportedEncodingException {
		RegAdvapplyRepayView rv = null;
		BaseClientFactory clientFactory = null;
		BaseClientFactory contractFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			if (null != regAdvapplyRepayDTO.getPId() && !(regAdvapplyRepayDTO.getPId().equals(""))) {
				rv = client.queryRegAdvapplyRepay(regAdvapplyRepayDTO);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
			
			if (contractFactory != null) {
				try {
					contractFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		model.addAttribute("Rarv", rv);
		model.addAttribute("loanId", rv.getLoanId());
		model.addAttribute("projectId", regAdvapplyRepayDTO.getPId());
		model.addAttribute("projectNum", rv.getProjectNum());
		model.addAttribute("projectName",rv.getProjectName());
		return "repayment/list_adv_apply_repayment";
	}
        //查询上传文件
	@RequestMapping("/queryRegAdvapplyFile")
	@ResponseBody
	public void queryRegAdvapplyFile(int preRepayId,UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO, HttpServletResponse response) {
		List<RegAdvapplyFileview> list = null;
		BaseClientFactory clientFactory = null;
		Map<String, Object> map = new HashMap<String, Object>();
		uploadinstAdvapplyBaseDTO.setPreRepayId(preRepayId);
		int count = 0;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			ShiroUser user=getShiroUser();
			uploadinstAdvapplyBaseDTO.setPmUserId(user.getPid());
			list = client.queryRegAdvapplyFile(uploadinstAdvapplyBaseDTO);
			count = client.queryRegAdvapplyFileCount(uploadinstAdvapplyBaseDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
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
		map.put("rows", list);
		map.put("total", count);
		outputJson(map, response);
	}

	/*
	 * 还款管理/提前还款管理
	 *  
	 */
	@RequestMapping("/advapplyrepaymenturl")
	@ResponseBody
	public void getadvapplyrepaymenturl(AdvapplyRepaymentBaseDTO advapplyRepaymentBaseDTO, HttpServletResponse response) {
		List<AdvapplyRepaymentView> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			advapplyRepaymentBaseDTO.setRows(advapplyRepaymentBaseDTO.rows);
			advapplyRepaymentBaseDTO.setPage(advapplyRepaymentBaseDTO.page);
			list = client.getAdvapplyrepaymenturl(advapplyRepaymentBaseDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					logger.error(e.getMessage());
				}
			}
		}
		// 输出
		map.put("rows", list);
		map.put("total", list.size());
		outputJson(map, response);
	}

	@RequestMapping("/saveAdvRepayment")
	@ResponseBody
	public void saveAdvRepayment(SaveAdvRepaymentBaseDTO saveAdvRepaymentBaseDTO, HttpServletResponse response) {
		int st = 0;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			st = client.saveAdvRepayment(saveAdvRepaymentBaseDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					logger.error(e.getMessage());
				}
			}
		}
		outputJson(st, response);
	}
  //上传文件
	@RequestMapping(value = "/uploadadvapply")
	@ResponseBody
	public void uploadadvapply(HttpServletResponse response, HttpServletRequest request) throws TException, XmlException, OpenXML4JException {
		UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO = new UploadinstAdvapplyBaseDTO();
		BaseClientFactory clientFactory = null;
		ShiroUser shiroUser = getShiroUser();
		int userId = shiroUser.getPid();
		String path = null;
		int st = 0;
		String statusString="";
		try {
			request.setCharacterEncoding("UTF-8");
			Map<String, Object> map = new HashMap<String, Object>();
			map = FileUtil.processFileUpload(request, response, BusinessModule.MODUEL_REPAYMENT, getUploadFilePath(), getFileSize(), getFileDateDirectory(),getUploadFileType());
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			if ((Boolean) map.get("flag") == false) {
				logger.error("上传出错");
				statusString="上传失败or上传类型错误!"	;
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
						 else if (item.getFieldName().equals("preRepayId")) {
								uploadinstAdvapplyBaseDTO.setPreRepayId(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
						 }
					} else {
						if (item.getFieldName().equals("file1"))
							uploadinstAdvapplyBaseDTO.setFileSize((int) item.getSize());
					}
				}
				String uploadDttm = DateUtil.format(new Date());
			uploadinstAdvapplyBaseDTO.setFileDttm(uploadDttm);
			path = String.valueOf(map.get("path"));
			String aa =  items.get(3).toString();
			//获取文件文件名字
			String pathname = aa.split(",")[aa.split(",").length - 5];
			String pathNames = pathname.substring(5, pathname.length());
			//String pathname = path.split("/")[path.split("/").length - 1];
			uploadinstAdvapplyBaseDTO.setFileName(pathNames);
			String fileType = pathname.substring(pathname.lastIndexOf(".")).substring(1);
			uploadinstAdvapplyBaseDTO.setFileType(fileType);
			// .substring( 0,path.length()-pathname.length()-1)
			uploadinstAdvapplyBaseDTO.setFilePath(path);
			uploadinstAdvapplyBaseDTO.setUploadUserid(userId + "");
			st = client.uploadinstAdvapply(uploadinstAdvapplyBaseDTO);
			statusString="上传成功!"	;

			}
		} catch (Exception e) {
			logger.error(e.getMessage());
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
		
		outputJson(statusString, response);
	}
	//下载文件
	@RequestMapping(value = "downloadadvapply")
	@ResponseBody
	public void downloadData(HttpServletRequest request,HttpServletResponse response,String path){
		try {
			FileDownload.downloadLocal(response, request, path);
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/seeDownloadadvapply")
	public void aeedownloadadvapply(HttpServletResponse response, HttpServletRequest request, @RequestParam("path") String path, ModelMap model) throws IOException {
		try{
			response.setHeader("Content-type", "image/jpeg");
			response.setContentType("application/octet-stream");
			path = CommonUtil.getRootPath() + path;
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			String pathname = path.split("/")[path.split("/").length - 1];
			long fileLength = new File(path).length();
			// response.setHeader("Content-type", "image/jpeg");
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment; filename=" + pathname);
			response.setHeader("Content-Length", String.valueOf(fileLength));
	
			bis = new BufferedInputStream(new FileInputStream(path));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			bis.close();
			bos.close();	
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}
     //删除
	@RequestMapping(value = "/delectUpdateAdvapply")
	@ResponseBody
	public void delectUpdateAdvapply(@RequestParam("pId") String pId,HttpServletResponse response){
		int  st = 0;
		BaseClientFactory clientFactory = null; 
		Map<String,Object> tojson = new HashMap<String,Object>();
		try{
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			st = client.delectFilebyId(pId);
			if(st>0){
				tojson.put("delStatus", "删除资料成功");
			}else{
				tojson.put("delStatus","删除资料失败" );
			}
		}catch(Exception e){
			tojson.put("delStatus","删除资料失败" );
			logger.error("删除资料文件失败出错" + e.getMessage());
			e.printStackTrace();
		}finally{
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(tojson, response);
	}
	

	// 执行操作
	@RequestMapping("/executiveOperation")
	@ResponseBody
	public void executiveOperation(ExecutiveOperBaseDTO executiveOperBaseDTO, HttpServletResponse response) {
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			client.executiveOperation(executiveOperBaseDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
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
		outputJson("成功", response);
	}

	@RequestMapping("/delectapplyrepay")
	@ResponseBody
	public void delectApplyRepay(ApplyfileuploadBaseDTO applyfileuploadBaseDTO, HttpServletResponse response) {
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			client.delectApplyRepay(applyfileuploadBaseDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
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
		outputJson("成功", response);
	}

	/**
	 * 
	  * @Description: 提前还款申请保存
	  * @param preApplyRepayBaseDTO
	  * @param response
	  * @author: Zhangyu.Hoo
	  * @date: 2015年6月26日 上午11:40:28
	 */
	@RequestMapping("/insertadvApplyrepayment")
	@ResponseBody
	public void insertadvApplyrepayment(PreApplyRepayBaseDTO preApplyRepayBaseDTO, HttpServletResponse response) {
		int st = 0;
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
		try {
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			st = client.insertPreApplyRepay(preApplyRepayBaseDTO);
		} catch (Exception e) {
			logger.info("***************提前还款申请保存异常*****************"+e.getMessage());
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
	 * 客户业务查询列表
	 */
	@RequestMapping(value = "listCusBusiness")
	public String listCusBusiness(@RequestParam(value = "limitId", required = false) Integer limitId, ModelMap model) throws Exception {
		return "repayment/listCusBusiness";
	}

	/**
	 * 客户业务查询列表查询
	 */
	@RequestMapping(value = "getCusBusiness")
	public void getCusBusiness(CusBusiness cusBusiness, HttpServletResponse response) throws Exception {
		List<CusBusiness> list = new ArrayList<CusBusiness>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
		Map map = new HashMap();
		try {
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			list = client.getCusBusiness(cusBusiness);
			int total = client.getCusBusinessTotal(cusBusiness);
			map.put("total", total);
			map.put("rows", list);
		} catch (Exception e) {
			logger.error(e.getMessage());
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
		outputJson(map, response);
	}

	/**
	 * 
	  * @Description: 跳转到结清办理
	  * @return
	  * @author: xuweihao
	  * @date: 2015年3月26日 下午5:40:39
	 */
	@RequestMapping(value="toSettlement")
	public String toSettlement(Integer projectId,int loanId,ModelMap model){
		int pid = 0 ;
		BaseClientFactory clientFactory = null;
		ProjectComplete projectComplete = new ProjectComplete();
		try{
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT,"RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			projectComplete = client.getLoanSettleOne(projectId);
			if(projectComplete.getPId()==0){
				projectComplete.setProjectId(projectId);
				pid = client.saveLoanSettle(projectComplete);
			}else{
				pid = projectComplete.getPId();
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			e.printStackTrace();
		}finally{
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		model.put("pid", pid);
		model.put("projectId", projectId);
		model.put("loanId", loanId);
		
		//设置项目收息表和项目的数据信息
		setLoanBaseDataBean(projectId,model);
		
		return "repayment/projectSettlementManagement";
	}
	
	/**
	  * 设置项目收息表和项目的数据信息
	  * @param projectId
	  * @param model
	  * @author: qiancong.Xian
	  * @date: 2015年4月3日 上午11:55:07
	 */
	private void setLoanBaseDataBean(int projectId,ModelMap model)
	{
		BaseClientFactory clientFactory = null;
		LoanBaseDataBean loanBaseDataBean = null;
		//查询账户信息
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
            //查找客户名称，客户可用余额
			loanBaseDataBean= client.getLoanBaseDataBean(projectId);
		} catch (Exception e) {
			logger.error(e.getMessage());
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
		int loanId = loanBaseDataBean.getLoanId();
		
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
			logger.error(e.getMessage());
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
	 * 
	  * @Description: 贷款结清
	  * @param projectComplete
	  * @param response
	  * @author: xuweihao
	  * @date: 2015年3月27日 下午2:18:07
	 */
	@RequestMapping(value="updateLoanSettle")
	@ResponseBody
	public void updateLoanSettle(ProjectComplete projectComplete,HttpServletResponse response){
		int str = 0 ;
		BaseClientFactory clientFactory = null;
		try{
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT,"RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			str = client.updateLoanSettle(projectComplete);
		}catch(Exception e){
			logger.error(e.getMessage());
			e.printStackTrace();
		}finally{
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(str, response);
	}
	
	/**
	 * 
	  * @Description: 查询单个
	  * @param pId
	  * @param response
	  * @author: xuweihao
	  * @date: 2015年4月20日 下午4:30:28
	 */
	@RequestMapping(value="getLoanSettleOne")
	@ResponseBody
	public void getLoanSettleOne(int projectId,HttpServletResponse response){
		ProjectComplete projectComplete = new ProjectComplete();
		BaseClientFactory clientFactory = null;
		try{
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT,"RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			projectComplete = client.getLoanSettleOne(projectId);
		}catch(Exception e){
			logger.error(e.getMessage());
			e.printStackTrace();
		}finally{
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(projectComplete, response);
	}
	
	
	/**
	 * 
	  * @Description: 生成结清文件
	  * @param projectId
	  * @param request
	  * @param response
	  * @author: xuweihao
	  * @date: 2015年4月21日 下午5:23:31
	 */
	@RequestMapping(value="makeProjectCompleteFile")
	public void makeProjectCompleteFile(int projectId ,HttpServletRequest request,HttpServletResponse response){
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
		BaseClientFactory clientF = getFactory(BusinessModule.MODUEL_REPAYMENT, "LoanExtensionService");
		BaseClientFactory c = getFactory(BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		BaseClientFactory cp = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		int projectType=0;
		Project project= new Project();
		SettleFile sf = new SettleFile();
		Map<String,String> map = new HashMap<String,String>();
		List<String> list = new ArrayList<String>();
		TemplateFile template = new TemplateFile();
		String pids = null;
		try{
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			ProjectService.Client clientpro = (ProjectService.Client) cp.getClient();
			LoanExtensionService.Client clientloan = (LoanExtensionService.Client) clientF.getClient();
			project = clientpro.getProjectById(projectId);
			projectType = project.getProjectType();
			
			if(projectType==4){
				//说明是展期项目所以需要查询老项目的账号密码开户人
				pids = clientloan.getHisProjectIds(projectId);
				String pid = pids.substring(pids.lastIndexOf(",")+1, pids.length());
				//老项目
				SettleFile sff = client.getSettleFile(Integer.parseInt(pid));
				sf = client.getSettleFileRepayment(projectId);
				sf.setAccName(sff.getAccName());
				sf.setBankCardId(sff.getBankCardId());
				sf.setBankName(sff.getBankName());
				sf.setOutputDttm(sff.getOutputDttm());
				sf.setBankCardType(sff.getBankCardType());
			}else{
				sf = client.getSettleFile(projectId);
			}
			
			// 将金额小写转换为大写
			sf.setCreditAmt(MoneyUtil.toChinese(sf.getCreditAmt()));
			
			if(sf.getBankCardType()==1){
				TemplateFileService.Client cl = (TemplateFileService.Client) c.getClient();
				template = cl.getTemplateFile("JQWJPER");
			}else{
				TemplateFileService.Client cl = (TemplateFileService.Client) c.getClient();
				template = cl.getTemplateFile("JQWJCOM");
			}
			
			
			String[] s = ParseDocAndDocxUtil.getFiledName(sf);
			String docText = ParseDocAndDocxUtil.parseDocumet(template.getFileUrl(), request);
			list = ParseDocAndDocxUtil.getDocumentSign(docText);
			List<TempLateParmDto> templateParamList = new ArrayList<TempLateParmDto>();
			for(int i=0;i<list.size();i++){
				if(!list.get(i).equals("")){
					String biaoshi = list.get(i).replace("@", "");
					for(int j=0;j<s.length;j++){
						if(biaoshi.equals(s[j])){
							TempLateParmDto tempLateParmDto = new TempLateParmDto();
							if(s[j].substring(s[j].length()-3,s[j].length()).equals("Amt")){
								tempLateParmDto.setValConvertFlag(3);
								tempLateParmDto.setMatchFlag(list.get(i));
							}else if(s[j].substring(s[j].length()-4,s[j].length()).equals("Dttm")){
								tempLateParmDto.setValConvertFlag(2);
								tempLateParmDto.setMatchFlag(list.get(i));
							}else{
								tempLateParmDto.setValConvertFlag(0);
								tempLateParmDto.setMatchFlag(list.get(i));
							}
							templateParamList.add(tempLateParmDto);
							map.put(list.get(i), ExcelExport.getFieldValueByName(s[j], sf));
						}
					}
				}
			}
			ContractGenerator contractGenerator = new ContractGenerator();
			String postfix= template.getFileUrl().substring(template.getFileUrl().lastIndexOf("."), template.getFileUrl().length());
//			String realFileName = genFileName(template.getFileUrl());
			String realFileName=" 结清文件"+postfix;
			String localPath = CommonUtil.getRootPath()+ExcelExport.getDateToString();
			File file = ExcelExport.createFile(localPath, realFileName);
			String path = CommonUtil.getRootPath() +template.getFileUrl();
			// TODO 新修改合同动态表格
			boolean b = contractGenerator.generate(this,templateParamList, path, file.getPath(), map, null,null);
			if(b==true){
				ExcelExport.sentFile(realFileName, localPath, response, request);
			}
		}catch(Exception e){
			e.printStackTrace();
			response.setContentType("text/html;charset=UTF-8");  
			try {
				PrintWriter out = response.getWriter();
				out.println("<script>alert('生成结清文件失败！')</script>");
			} catch (IOException e1) {
				logger.error(e.getMessage());
				e1.printStackTrace();
			}  
		}finally{
			if (c != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				}finally{
					if (clientFactory != null) {
						try {
							clientFactory.destroy();
						} catch (ThriftException e) {
							logger.error(e.getMessage());
							e.printStackTrace();
						}
					}
				}
			}
			
		}
	}
	
	
	
	/**
	 * 
	  * @Description: 结清办理上传文件
	  * @param request
	  * @param response
	  * @author: xuweihao
	  * @date: 2015年3月28日 下午2:54:09
	 */
	@RequestMapping(value = "saveProjectCompleteFile")
	@ResponseBody
	public void saveDataFile(HttpServletRequest request, HttpServletResponse response) {
		int count = 0;
		String fileName = null;
		String fileType = null;
		BaseClientFactory c = null;
		BaseClientFactory bizFileClientFactory = null;
		// 结清资料信息proCompleFile
		ProjectCompleteFile proCompleFile = new ProjectCompleteFile();
		// 文件信息BIZ_FILE
		BizFile bizFile = new BizFile();
		Map<String, Object> tojson = new HashMap<String, Object>();
		try {
			c = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
			bizFileClientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "BizFileService");
			RepaymentService.Client repayService = (RepaymentService.Client) c.getClient();
			BizFileService.Client bizFileClient = (BizFileService.Client) bizFileClientFactory.getClient();
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				Map<String, Object> map = new HashMap<String, Object>();
				// 资料上传得到的信息集合
				map = FileUtil.processFileUpload(request, response, BusinessModule.MODUEL_BEFORELOAN, getUploadFilePath(), getFileSize(), getFileDateDirectory(),getUploadFileType());
				if ((Boolean) map.get("flag") == false) {
					tojson.put("uploadStatus", "文件上传失败");
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
							if (item.getFieldName().equals("completePid")) {
								proCompleFile.setCompleteId(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
							}else if(item.getFieldName().equals("fileDesc")){
								proCompleFile.setFileDesc(ParseDocAndDocxUtil.getStringCode(item));
							}else if(item.getFieldName().equals("Legalconfirmation")){
								proCompleFile.setLegalconfirmation(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
							}
						} else {
							if ("file2".equals(item.getFieldName())) {
								// 获取文件上传的信息
								bizFile.setUploadUserId(userId);
								bizFile.setFileSize((int) item.getSize());
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
					bizFile.setFileUrl(fileUrl);
					int uploadUserId = getShiroUser().getPid();
					bizFile.setUploadUserId(uploadUserId);
					bizFile.setStatus(1);
					if (StringUtils.isEmpty(fileName) || StringUtils.isEmpty(fileType)) {
						response.getWriter().write("fileIsEmpty");
					}
					// 保存文件信息
					int fileId = bizFileClient.saveBizFile(bizFile);
					// 获取文件Id
					proCompleFile.setFileId(fileId);
					// 保存资料表信息
					count = repayService.saveProjectCompleteFile(proCompleFile);
					if (count == 0) {
						tojson.put("uploadStatus", 1);
					} else {
						tojson.put("uploadStatus", 2);
					}
				}
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("上传资料列表出错" + e.getMessage());
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
	  * @Description: 删除结清文件
	  * @param pId
	  * @param response
	  * @throws Exception
	  * @author: xuweihao
	  * @date: 2015年3月30日 下午4:35:01
	 */
	@RequestMapping(value = "deleteProjectCompleteFile")
	@ResponseBody
	public void deleteDataFile(String pId, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = null;
		boolean st = false;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			st = client.deleteProjectCompleteFile(pId);
		} catch (Exception e) {
			logger.error(e.getMessage());
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
	 * 
	  * @Description: 修改结清文件
	  * @param projectCompleteFile
	  * @param response
	  * @throws Exception
	  * @author: xuweihao
	  * @date: 2015年3月30日 下午4:52:16
	 */
	@RequestMapping(value = "editProjectCompleteFile")
	@ResponseBody
	public void editProjectCompleteFile(ProjectCompleteFile projectCompleteFile, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = null;
		boolean st = false;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			st = client.editProjectCompleteFile(projectCompleteFile);
		} catch (Exception e) {
			logger.error(e.getMessage());
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
	 * 	
	  * @Description: 重新上传结清文件
	  * @param projectCompleteFile
	  * @param response
	  * @throws Exception
	  * @author: xuweihao
	  * @date: 2015年3月30日 下午5:11:43
	 */
	@RequestMapping(value = "reUploadProjectCompleteFile")
	@ResponseBody
	public void reUploadProjectCompleteFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean count = false;
		String fileName = null;
		String fileType = null;
		BaseClientFactory c = null;
		BaseClientFactory bizFileClientFactory = null;
		// 结清资料信息proCompleFile
		ProjectCompleteFile proCompleFile = new ProjectCompleteFile();
		// 文件信息BIZ_FILE
		BizFile bizFile = new BizFile();
		Map<String, Object> tojson = new HashMap<String, Object>();
		try {
			c = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
			bizFileClientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "BizFileService");
			RepaymentService.Client repayService = (RepaymentService.Client) c.getClient();
			BizFileService.Client bizFileClient = (BizFileService.Client) bizFileClientFactory.getClient();
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				Map<String, Object> map = new HashMap<String, Object>();
				// 资料上传得到的信息集合
				map = FileUtil.processFileUpload(request, response, BusinessModule.MODUEL_BEFORELOAN, getUploadFilePath(), getFileSize(), getFileDateDirectory(),getUploadFileType());
				if ((Boolean) map.get("flag") == false) {
					tojson.put("uploadStatus", "文件上传失败");
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
							if (item.getFieldName().equals("pId")) {
								proCompleFile.setPId(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
							}
						} else {
							if ("file2".equals(item.getFieldName())) {
								// 获取文件上传的信息
								bizFile.setUploadUserId(userId);
								bizFile.setFileSize((int) item.getSize());
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
					bizFile.setFileUrl(fileUrl);
					int uploadUserId = getShiroUser().getPid();
					bizFile.setUploadUserId(uploadUserId);
					bizFile.setStatus(1);
					if (StringUtils.isEmpty(fileName) || StringUtils.isEmpty(fileType)) {
						response.getWriter().write("fileIsEmpty");
					}
					// 保存文件信息
					int fileId = bizFileClient.saveBizFile(bizFile);
					// 获取文件Id
					proCompleFile.setFileId(fileId);
					// 保存资料表信息
					count = repayService.editProjectCompleteFile(proCompleFile);
					if (count == false) {
						tojson.put("uploadStatus", 1);
					} else {
						tojson.put("uploadStatus", 2);
					}
				}
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("上传资料列表出错" + e.getMessage());
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
	  * @Description:下载结清文件
	  * @param request
	  * @param response
	  * @param path
	  * @author: xuweihao
	  * @date: 2015年3月30日 下午6:48:35
	 */
	@RequestMapping(value = "downloadFile")
	@ResponseBody
	public void downloadFile(HttpServletRequest request, HttpServletResponse response, String path) {
		try {
			FileDownload.downloadLocal(response, request, path);
		} catch (IOException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("下载资料文件失败出错" + e.getMessage());
			}
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	  * @Description: 查询结清文件列表
	  * @param pid
	  * @param response
	  * @author: xuweihao
	  * @date: 2015年3月30日 下午2:27:29
	 */
	@RequestMapping(value = "getProjectCompleteFile")
	@ResponseBody
	public void getDataFile(Integer pid,HttpServletResponse response){
		BaseClientFactory clientFactory = null;
		List<ProjectCompleteFile> list = new ArrayList<ProjectCompleteFile>();
		try{
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT,"RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			list = client.getProjectCompleteFile(pid);
		}catch(Exception e){
			Log.debug(e.getMessage());
			e.printStackTrace();
		}finally{
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
	 * 贷款结清查询列表
	 */
	@RequestMapping(value = "listLoanSettle")
	public String getLoanSettle(@RequestParam(value = "limitId", required = false) Integer limitId, ModelMap model) throws Exception {
		return "repayment/listLoanSettlement";
	}

	/**
	 * 贷款结清查询
	 */
	@RequestMapping(value = "getLoanSettle")
	public void getLoanSettle(CusBusiness cusBusiness, HttpServletResponse response) throws Exception {
		List<CusBusiness> list = new ArrayList<CusBusiness>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			list = client.getLoanSettle(cusBusiness);
			int total = client.getLoanSettleTotal(cusBusiness);
			map.put("total", total);
			map.put("rows", list);
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
		outputJson(map, response);
	}

	/**
	 * 
	 * 逾期页面跳转
	 */
	@RequestMapping(value = "getrepaymentoverdue")
	public String getRepaymentOverdue() throws Exception {
		return "repayment/list_overdue";
	}

	/**
	 * 
	 * 逾期查询
	 */
	@RequestMapping(value = "getrepaymentoverduelist")
	public void getRepaymentOverdueList(RepayOverdueView repayOverdueView, HttpServletResponse response) throws Exception {
		List<RepayOverdueDTO> li = new ArrayList<RepayOverdueDTO>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			li = client.getRepaymentOverdueList(repayOverdueView);
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
		outputJson(li, response);
	}

	/**
	 * 删除项目
	 */
	@RequestMapping(value = "deleteProjectbyId")
	public void deleteProjectbyId(String pId, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = null;
		int st = 0;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			st = client.deleteProjectbyId(pId);
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
	 * 
	 * @Description:删除贷款结清列表
	 * @return 贷款结清列表信息页面
	 * @author: wangheng
	 * @date: 2014年12月25日
	 */
	@RequestMapping(value = "deleteLoanSettle")
	public String deleteLoanSettle(String pid, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			client.deleteLoanSettle(pid);
			outputJson("success", response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "repayment/listLoanSettlement";
	}
	
	
	/**
	 * 
	  * @Description: 跳转到收息表页面
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年3月28日 上午11:05:24
	 */
	@RequestMapping("/getLoanCaculateList")
	public String getLoanCaculateList(ModelMap model, @RequestParam(value = "loanId", required = false) Integer loanId) {
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			RepaymentLoanInfo repayment = client.getRepaymentLoanInfo(loanId);
			if(null !=repayment){
				model.put("otherCostName", repayment.getOtherCostName());
			}
			model.put("loanId", loanId);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询还款计划表贷款信息:" + e.getMessage());
			}
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		return "repayment/list_loanCalculate";
	}
	
	
	
	/**
	 * 
	  * @Description: 查询收息表数据
	  * @param loanId
	  * @param response
	  * @throws Exception
	  * @author: Zhangyu.Hoo
	  * @date: 2015年3月27日 下午5:13:33
	 */
	@RequestMapping(value = "queryLoanCaculate")
	public void queryLoanCaculate(int  loanId, HttpServletResponse response,HttpServletRequest request){
		BaseClientFactory clientFactory = null;
		List<RepaymentPlanBaseDTO> list = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			RepaymentPlanBaseDTO dto = new RepaymentPlanBaseDTO();
			dto.setLoanInfoId(loanId);
			dto.setFreezeStatus(1);
			
			// 如果是要展现最新的数据
			if("true".equals(request.getParameter("showNew")))
			{
				dto.setFreezeStatus(0);
			}
			list = client.selectRepaymentPlanBaseDTO(dto);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询收息表数据信息:" + e.getMessage());
			}
		}finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		outputJson(list, response);
	}	
	/**
	 * 
	  * @Description: 删除
	  * @param preRepayId
	  * @param response
	  * @throws Exception
	  * @author: GW
	  * @date: 2015年3月27日 下午5:13:33
	 */
	@RequestMapping(value = "deleteProjectbyPreRepayId")
	public void deleteProjectbyPreRepayId(String  preRepayId,String projectId, HttpServletResponse response){
		BaseClientFactory clientFactory = null;
		int st=0;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			st = client.deleteProjectbyPreRepayId(preRepayId,projectId);
		} catch (Exception e) {
			logger.info("提前还款申请删除失败  preRepayId :　"+preRepayId+"  errorinfo :" + e.getMessage());
		}finally {
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
	 * 
	  * @Description: 验证是否提前还款流程没走完
	  * @param projectId
	  * @param response
	  * @throws Exception
	  * @author: GW
	  * @date: 2015年3月27日 下午5:13:33
	 */
	@RequestMapping(value = "checkpreRepayByProjectId")
	public void checkpreRepayByProjectId(int  projectId, HttpServletResponse response){
		BaseClientFactory clientFactory = null;
		int st=0;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			st = client.checkpreRepayByProjectId(projectId);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("核对失败:" + e.getMessage());
			}
		}finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}//大于0就不可以继续提前还款申请
		outputJson(st, response);
	}
	
	/**
	 * 
	  * @Description: 编辑上传信息
	  * @param projectId
	  * @param response
	  * @throws Exception
	  * @author: GW
	  * @date: 2015年3月27日 下午5:13:33
	 */
	@RequestMapping(value = "updateloadfileinfo")
	public void updateLoadFileInfo(String  fileDesc, int fileKinds,int preRepayId,int fileId,HttpServletResponse response){
		BaseClientFactory clientFactory = null;
		UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO=new UploadinstAdvapplyBaseDTO();
		uploadinstAdvapplyBaseDTO.setFileDesc(fileDesc);
		uploadinstAdvapplyBaseDTO.setFileKinds(fileKinds+"");
		uploadinstAdvapplyBaseDTO.setPreRepayId(preRepayId);
		uploadinstAdvapplyBaseDTO.setFileId(fileId);
		int st=0;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "UploadFileService");
			UploadFileService.Client client = (UploadFileService.Client) clientFactory.getClient();
			st=	client.updateAdvLoadFileInfo(uploadinstAdvapplyBaseDTO);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		}finally {
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
	 * 
	  * @Description: 查询展期jine
	  * @param projectId
	  * @param response
	  * @throws Exception
	  * @author: GW
	  * @date: 2015年3月27日 下午5:13:33
	 */
	@RequestMapping(value = "getExtensionamtbyprojectId")
	public void getExtensionamtbyprojectId(int projectId,HttpServletResponse response){
		BaseClientFactory clientFactory = null;
		double st=0;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			st=	client.getExtensionamtbyprojectId(projectId);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		}finally {
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
	//获取剩余本金
	/**
	 * 
	  * @Description: 查询展期jine
	  * @param projectId
	  * @param response
	  * @throws Exception
	  * @author: GW
	  * @date: 2015年3月27日 下午5:13:33
	 */
	@RequestMapping(value = "getSurplusbyprojectId")
	public void getSurplusbyprojectId(int projectId,HttpServletResponse response){
		CustArrearsView custArrears=null;
		BaseClientFactory clientFactory = null;
		double st=0;
		try {
			
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");		
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			custArrears=client.getCustArrearsbyProjectView(projectId);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		}finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(custArrears.getUnReceivedPrincipal(), response);
	}
	
	/**
	 * 
	  * @Description: 获取贷款剩余本金
	  * @param loanId
	  * @param response
	  * @author: Zhangyu.Hoo
	  * @date: 2015年5月18日 下午5:17:58
	 */
	@RequestMapping(value = "getOverplusAmt")
	public void getOverplusAmt(int loanId,HttpServletResponse response){
		BaseClientFactory clientFactory = null;
		double overplusAmt=0;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			overplusAmt = client.getOverplusAmt(loanId);
		} catch (Exception e) {
			logger.info("**********获取贷款剩余本金异常*************"+e.getMessage());
		}finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(overplusAmt, response);
	}
	
	/**
	 * 
	  * @Description: 还款催收,获取已经对账的金额
	  * @param loanId
	  * @param response
	  * @author: Zhangyu.Hoo
	  * @date: 2015年5月18日 下午5:17:58
	 */
	@RequestMapping(value = "getReconciliationDtl")
	public void getReconciliationDtl(RepayEconciliationDTO dto,HttpServletResponse response){
		BaseClientFactory clientFactory = null;
		RepaymentPlanBaseDTO repaymentPlanBaseDTO= null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepaymentService");
			RepaymentService.Client client = (RepaymentService.Client) clientFactory.getClient();
			repaymentPlanBaseDTO = client.getReconciliationDtl(dto);
		} catch (Exception e) {
			logger.info("**********还款催收,获取已经对账的金额异常*************"+e.getMessage());
		}finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(repaymentPlanBaseDTO, response);
	}
}
