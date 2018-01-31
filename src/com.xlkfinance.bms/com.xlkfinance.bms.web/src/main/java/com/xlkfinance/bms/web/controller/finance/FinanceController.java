/**
 * @Title: FinanceController.java
 * @Package com.xlkfinance.bms.web.controller.finance
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月11日 上午9:53:29
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.finance;

import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.beforeloan.RepaymentLoanInfo;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.finance.CustArrearsView;
import com.xlkfinance.bms.rpc.finance.FinanceAcctTotalCondition;
import com.xlkfinance.bms.rpc.finance.FinanceAcctTotalDetailView;
import com.xlkfinance.bms.rpc.finance.FinanceAcctTotalView;
import com.xlkfinance.bms.rpc.finance.FinanceBank;
import com.xlkfinance.bms.rpc.finance.FinanceBusinessCondition;
import com.xlkfinance.bms.rpc.finance.FinanceBusinessView;
import com.xlkfinance.bms.rpc.finance.FinanceReceivablesDTO;
import com.xlkfinance.bms.rpc.finance.FinanceReceivablesView;
import com.xlkfinance.bms.rpc.finance.FinanceService;
import com.xlkfinance.bms.rpc.finance.FinanceTransactionService;
import com.xlkfinance.bms.rpc.finance.LoanReconciliationDtlView;
import com.xlkfinance.bms.rpc.finance.MonthlyReportRecord;
import com.xlkfinance.bms.rpc.finance.MonthlyReportRecordCondition;
import com.xlkfinance.bms.rpc.finance.ProjectTotalDetailView;
import com.xlkfinance.bms.rpc.finance.UserCommissionCondition;
import com.xlkfinance.bms.rpc.finance.UserCommissionView;
import com.xlkfinance.bms.rpc.system.TemplateFile;
import com.xlkfinance.bms.rpc.system.TemplateFileService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;
import com.xlkfinance.bms.web.util.CommonUtil;
import com.xlkfinance.bms.web.util.ExcelExport;

@Controller
@RequestMapping("/financeController")
public class FinanceController extends BaseController {
	private static final String serviceModuel = "finance";
	private static final String finance = "finance/";
	private Logger logger = LoggerFactory.getLogger(FinanceController.class);

	/**
	 * 客户业务查询跳转
	 */
	@RequestMapping(value = "/listCusBusiness")
	public String listCusBusiness(ModelMap model) throws Exception {
		return finance + "list_cus_business";
	}

	/**
	 * 客户业务查询数据
	 * 
	 * @param cusPerBaseDTO
	 * @param response
	 */
	@RequestMapping(value = "/listCusBusinessUrl")
	@ResponseBody
	public void listCusBusinessUrl(
			FinanceBusinessCondition financeBusinessCondition,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<FinanceBusinessView> cusBusiness = null;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(serviceModuel,
					"FinanceService");
			
			FinanceService.Client client = (FinanceService.Client) clientFactory
					.getClient();
			cusBusiness = client.getCusBusiness(financeBusinessCondition);
			int total = client.countCusBusiness(financeBusinessCondition);
			map.put("rows", cusBusiness);
			map.put("total", total);
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(" 客户业务查询数据失败",e);
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
	 * @Description: 财务账户列表
	 * @return 财务账户查询列表
	 * @author: wangheng
	 * @date: 2014年1月26日
	 */
	@RequestMapping(value = "listFinanceAcctManage")
	public String listFinanceAcctManage(HttpServletResponse response)
			throws Exception {

		return finance + "listFinanceAcctManagement";
	}

	/**
	 * 
	 * @Description: 跳到财务账户列表页面
	 * @return 跳到财务账户查询列表
	 * @author: wangheng
	 * @date: 2014年1月26日
	 */
	@RequestMapping(value = "saveFinanceAcctManager")
	public String saveFinanceAcctManager(HttpServletResponse response,
			ModelMap model) throws Exception {
		return finance + "editFinanceAcctManager";
	}

	/**
	 * 
	 * @Description: 修改财务账户
	 * @return 修改财务账户查询列表
	 * @author: wangheng
	 * @date: 2014年1月26日
	 */
	@RequestMapping(value = "editFinanceAcctManager")
	public String editFinanceAcctManager( int pid,HttpServletResponse response,ModelMap model) throws Exception {

		model.put("pid", pid);
		return finance +"editFinanceAcctManager";
    }
	/**
	 * 
	 * @Description: 修改财务账户
	 * @return 修改财务账户查询列表
	 * @author: wangheng
	 * @date: 2014年1月26日
	 */
	@RequestMapping(value = "editFinanceAcctManageUrl")
	@ResponseBody
	public void editFinanceAcctManageUrl(int pid, HttpServletResponse response,
			ModelMap model) throws Exception {
		FinanceBank financeBank = null;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(
					BusinessModule.MODUEL_FINANCE, "FinanceService");
			FinanceService.Client client = (FinanceService.Client) clientFactory
					.getClient();
			if (pid > 0) {
				financeBank = client.getFinanceActtManagerById(pid);
			}
		} catch (Exception e) {
			logger.error("修改财务账户失败",e);
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
		outputJson(financeBank, response);

	}

	/**
	 * 
	 * @Description: 添加财务账户列表
	 * @return 添加财务账户查询列表
	 * @author: wangheng
	 * @date: 2014年1月26日
	 */
	@RequestMapping(value = "addFinanceAcctManager")
	public String addFinanceAcctManager(FinanceBank financeBank,
			HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(
					BusinessModule.MODUEL_FINANCE, "FinanceService");
			FinanceService.Client client = (FinanceService.Client) clientFactory
					.getClient();
			int pid = financeBank.getPid();
			if (pid > 0) {
				financeBank.setStatus(1);
				client.updateFinanceAcctManager(financeBank);
			} else {
				financeBank.setStatus(1);
				client.addFinanceAcctManager(financeBank);
			}
			outputJson("success", response);
		} catch (Exception e) {
			logger.error("添加财务账户列表失败",e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}

		return finance + "editFinanceAcctManager";
	}

	/**
	 * 
	 * @Description: 查询财务账户列表
	 * @return 查询财务账户查询列表
	 * @author: wangheng
	 * @date: 2014年1月26日
	 */
	@RequestMapping(value = "searcherFinanceAcctManage")
	@ResponseBody
	public void searcherFinanceAcctManage(FinanceBank financeBank,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<FinanceBank> list = null;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(
					BusinessModule.MODUEL_FINANCE, "FinanceService");
			FinanceService.Client client = (FinanceService.Client) clientFactory
					.getClient();
			list = client.getFinanceActtManager(financeBank);
			int total = client.countFinanceActtManager(financeBank);
			map.put("rows", list);
			map.put("total", total);
		} catch (Exception e) {
			logger.error("查询财务账户列表失败",e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(map, response);
	}

	/**
	 * 
	 * @Description: 查询财务账户 账号列表
	 * @return 查询财务账户查询列表
	 * @author: hezhiying
	 * @date: 2014年3月19日
	 */
	@RequestMapping(value = "searcherFinanceAcctManageRow")
	@ResponseBody
	public void searcherFinanceAcctManageRow(FinanceBank financeBank,
			HttpServletResponse response) throws Exception {
		List<FinanceBank> list = null;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE,"FinanceService");
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			list = client.getFinanceActtManager(financeBank);
		} catch (Exception e) {
			logger.error("查询财务账户 账号列表失败",e);
		} finally {
			if (clientFactory != null) {
				try {
					FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
					list = client.getFinanceActtManager(financeBank);
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
		}
		outputJson(list, response);
	}

	/**
	 * 删除财务账户列表
	 */
	@RequestMapping(value = "deleteFinaceAcctManager")
	public String deleteFinaceAcctManager(String pid,
			HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory =null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			
			
			FinanceAcctTotalCondition financeAcctTotalCondition= new FinanceAcctTotalCondition();
			String[] tempId=pid.split(",");
			for(String id:tempId){
				financeAcctTotalCondition.setPid(Integer.parseInt(id));
				List<FinanceAcctTotalDetailView> list=client.getFinanceAcctTotalDetail(financeAcctTotalCondition);
				if(list!=null && list.size()>0){
					outputJson("nodelete", response);
					return finance + "listFinanceAcctManagement";
				}
			}
//			financeAcctTotalCondition.setPid(Integer.parseInt(pid));
//			List<FinanceAcctTotalDetailView> list=client.getFinanceAcctTotalDetail(financeAcctTotalCondition);
//			if(list!=null && list.size()>0){
//				outputJson("nodelete", response);
//			}else{
			client.deleteFinanceAcctManager(pid);
			outputJson("success", response);
//			}
			
		} catch (Exception e) {
			logger.error("删除财务账户列表失败",e);
		}
		return finance + "listFinanceAcctManagement";
	}

	//add by yql
	/**
	 * 客户欠款明细列表
	 */
	@RequestMapping(value = "custArrearsList")
	public String custArrearsList(HttpServletResponse response) throws Exception {
		return finance +"custArrearsList";
	}
	
	/**
	 * 客户欠款明细列表处理页面
	 */
	@RequestMapping(value = "custArrearsListUrl")
	@ResponseBody
	public void custArrearsListUrl(FinanceBusinessCondition financeBusinessCondition, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory =null;		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");		
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			List<CustArrearsView> list=client.getCustArrearsView(financeBusinessCondition);
			
//			double receivablePrincipal=0.0;//应收本金金额 
//			double receivableInterest=0.0;//应收利息
//			double receivableMangCost=0.0;//应收管理费
//			double receivableOtherCost=0.0;//应收其他费
//			double receivedPrincipal=0.0;//已收本金
//			double receivedInterest=0.0;//已收利息
//			double receivedMangCost=0.0;//已收管理费
//			double receivedOtherCost=0.0;//已收其他费
//			double receivedExpireInterest=0.0;//已收逾期利息
//			double receivedOverduePenalty=0.0;//已收逾期罚息		
			double unReceivedPrincipal=0.0;//未收本金
			double unReceivedInterest=0.0;//未收利息
			double unReceivedMangCost=0.0;//未收管理费
			double unReceivedOtherCost=0.0;//未收其他费
			double unReceivedOverdueInterest=0.0;//未收逾期利息
			double unReceivedOverduePunitive=0.0;//未收逾期罚息		
			double receiveTotalAmt=0.0;//已收小计
			double dueUnReceivedTotal=0.0;//到期未收小计
			double noReceiveTotalAmt=0.0;//未收合计
			double outstandingTotal=0.0;//未到期本息
			
			List<CustArrearsView> resultList= new ArrayList<CustArrearsView>();
			CustArrearsView temp=null;
			for (int i = 0; i <= list.size(); i++) {
//				// 最后的合计行
				if(i==list.size()){
					CustArrearsView custArrearsView= new CustArrearsView();
					custArrearsView.setContractNo("总计(元)");
//					custArrearsView.setReceivablePrincipal(receivablePrincipal);//应收本金金额 
//					custArrearsView.setReceivableInterest(receivableInterest);//应收利息
//					custArrearsView.setReceivableMangCost(receivableMangCost);//应收管理费
//					custArrearsView.setReceivableOtherCost(receivableOtherCost);//应收其他费
//					custArrearsView.setReceivedPrincipal(receivedPrincipal);//已收本金
//					custArrearsView.setReceivedInterest(receivedInterest);//已收利息
//					custArrearsView.setReceivedMangCost(receivedMangCost);//已收管理费
//					custArrearsView.setReceivedOtherCost(receivedOtherCost);//已收其他费
//					custArrearsView.setReceivedExpireInterest(receivedExpireInterest);//已收逾期利息
//					custArrearsView.setReceivedOverduePenalty(receivedOverduePenalty);//已收逾期罚息		
					custArrearsView.setUnReceivedPrincipal(unReceivedPrincipal);//未收本金
					custArrearsView.setUnReceivedInterest(unReceivedInterest);//未收利息
					custArrearsView.setUnReceivedMangCost(unReceivedMangCost);//未收管理费
					custArrearsView.setUnReceivedOtherCost(unReceivedOtherCost);//未收其他费
					custArrearsView.setUnReceivedOverdueInterest(unReceivedOverdueInterest);//未收逾期利息
					custArrearsView.setUnReceivedOverduePunitive(unReceivedOverduePunitive);//未收逾期罚息		
					custArrearsView.setReceiveTotalAmt(receiveTotalAmt);//已收小计
					custArrearsView.setDueUnReceivedTotal(dueUnReceivedTotal);//到期未收小计
					custArrearsView.setNoReceiveTotalAmt(noReceiveTotalAmt);//未收合计
					custArrearsView.setOutstandingTotal(outstandingTotal);//未到期本息
					resultList.add(custArrearsView);
				}else{
					temp=list.get(i);
//					receivablePrincipal=receivablePrincipal+temp.getReceivablePrincipal();//应收本金金额 
//					receivableInterest=receivableInterest+temp.getReceivableInterest();//应收利息
//					receivableMangCost=receivableMangCost+temp.getReceivableMangCost();//应收管理费
//					receivableOtherCost=receivableMangCost+temp.getReceivableOtherCost();//应收其他费
//					receivedPrincipal=receivedPrincipal+temp.getReceivedPrincipal();//已收本金
//					receivedInterest=receivedInterest+temp.getReceivedInterest();//已收利息
//					receivedMangCost=receivedMangCost+temp.getReceivedMangCost();//已收管理费
//					receivedOtherCost=receivedOtherCost+temp.getReceivedOtherCost();//已收其他费
//					receivedExpireInterest=receivedExpireInterest+temp.getReceivedExpireInterest();//已收逾期利息
//					receivedOverduePenalty=receivedOverduePenalty+temp.getReceivedOverduePenalty();//已收逾期罚息	
					// 未收：应收-已收（数据库查询出来的是到期未收，需要需要的是所有未收的）
					temp.setUnReceivedPrincipal(temp.getReceivablePrincipal()-temp.getReceivedPrincipal());
					temp.setUnReceivedInterest(temp.getReceivableInterest()-temp.getReceivedInterest());
					temp.setUnReceivedMangCost(temp.getReceivableMangCost()-temp.getReceivedMangCost());
					temp.setUnReceivedOtherCost(temp.getReceivableOtherCost()-temp.getReceivedOtherCost());
					//未收合计  = 未收本金+未收利息+未收管理费+未收其他费+ 剩余其他未收（即时发生项目的剩余未收）
				//	temp.setNoReceiveTotalAmt(temp.getUnReceivedPrincipal()+temp.getUnReceivedInterest()+temp.getUnReceivedMangCost()+temp.getUnReceivedOtherCost()+ temp.getUnReceivedOverdueInterest()+temp.getUnReceivedOverduePunitive());
					//temp.setNoReceiveTotalAmt(temp.getNoReceiveTotalAmt() + temp.getNoReceiveTotalAmt_im());
					
					unReceivedPrincipal=unReceivedPrincipal+temp.getUnReceivedPrincipal();//未收本金
					unReceivedInterest=unReceivedInterest+temp.getUnReceivedInterest();//未收利息
					unReceivedMangCost=unReceivedMangCost+temp.getUnReceivedMangCost();//未收管理费
					unReceivedOtherCost=unReceivedOtherCost+temp.getUnReceivedOtherCost();//未收其他费
					unReceivedOverdueInterest=unReceivedOverdueInterest+temp.getUnReceivedOverdueInterest();//未收逾期利息
					unReceivedOverduePunitive=unReceivedOverduePunitive+temp.getUnReceivedOverduePunitive();//未收逾期罚息		
					receiveTotalAmt=receiveTotalAmt+temp.getReceiveTotalAmt();//已收小计
					dueUnReceivedTotal=dueUnReceivedTotal+temp.getDueUnReceivedTotal();//到期未收小计
					
					
					// 未收合计
					noReceiveTotalAmt=noReceiveTotalAmt+temp.getNoReceiveTotalAmt();
					outstandingTotal=outstandingTotal+temp.getOutstandingTotal();//未到期本息
					resultList.add(temp);
				}
			
			}
			
			int total=client.countCustArrearsView(financeBusinessCondition);
			map.put("rows", resultList);
			map.put("total", total);
		} catch (Exception e) {
			logger.error("客户欠款明细列表处理页面失败",e);
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
	 * 客户总账列表列表
	 */
	@RequestMapping(value = "listFinanceAcctTotal")
	public String listFinanceAcctTotal(HttpServletResponse response) throws Exception {
		return finance +"financeTotleAcctSearcher";
	}
	
	/**
	 * 客户总账列表处理页面
	 */
	@RequestMapping(value = "listFinanceAcctTotalUrl")
	@ResponseBody
	public void listFinanceAcctTotalUrl(FinanceAcctTotalCondition financeAcctTotalCondition, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory =null;
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");		
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			List<FinanceAcctTotalView> list=client.getFinanceAcctTotalView(financeAcctTotalCondition);	
			List<FinanceAcctTotalView> resultList=new ArrayList<FinanceAcctTotalView>();
			
			double initialAmtTotal=0.0;//初始金额小计
			double incomeAccountTotal=0.0;//账户收入小计
			double accountOutTotal=0.0;//账户支出小计
			double periodBalanceTotal=0.0;//期间金额小计
			
			if(list!=null){
				for(int i=0;i<=list.size();i++){
					
					if (i == list.size()) {
						FinanceAcctTotalView temp= new FinanceAcctTotalView();
						temp.setBankNum("合计");
						temp.setInitialAmt(initialAmtTotal);
						temp.setIncomeAccount(incomeAccountTotal);
						temp.setAccountOut(accountOutTotal);
						temp.setPeriodBalance(periodBalanceTotal);
						resultList.add(temp);
					}else{
						FinanceAcctTotalView financeAcctTotalView=list.get(i);
						//期间金额计算
//						if("1".equals(financeAcctTotalView.getFtType())){
//							financeAcctTotalView.setPeriodBalance(financeAcctTotalView.getInitialAmt()-financeAcctTotalView.getAccountOut());
//						}else if("2".equals(financeAcctTotalView.getFtType())){
//							financeAcctTotalView.setPeriodBalance(financeAcctTotalView.getInitialAmt()+financeAcctTotalView.getIncomeAccount());
//						}
						initialAmtTotal=initialAmtTotal+financeAcctTotalView.getInitialAmt();
						incomeAccountTotal=incomeAccountTotal+financeAcctTotalView.getIncomeAccount();
						accountOutTotal=accountOutTotal+financeAcctTotalView.getAccountOut();
						periodBalanceTotal=periodBalanceTotal+financeAcctTotalView.getPeriodBalance();
						//最后一样的计算
						resultList.add(financeAcctTotalView);
					}
				}
			}
			
			int total=client.countFinanceAcctTotal(financeAcctTotalCondition);
			map.put("rows", resultList);
			map.put("total", total);
		} catch (Exception e) {
			logger.error("客户总账列表处理页面失败",e);
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
	  * @Description: TODO 财务明细查看
	  * @param pid
	  * @param response
	  * @param model
	  * @return
	  * @throws Exception
	  * @author: yql
	  * @date: 2015年3月23日 上午11:57:43
	 */
	@RequestMapping(value = "getFinanceAcctDetailList")
	public String getFinanceAcctDetailList( int pId,String searcherPeriodStart,String searcherPeriodEnd,HttpServletResponse response,ModelMap model,double defaultAmt) throws Exception {
		BaseClientFactory clientFactory = null;
		
		//查询账户信息
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			FinanceBank financeBank=client.getFinanceActtManagerById(pId);
			model.put("financeBank", financeBank);
			model.put("defaultAmt", defaultAmt);
			model.put("searcherPeriodStart", searcherPeriodStart);
			model.put("searcherPeriodEnd", searcherPeriodEnd);
		} catch (Exception e) {
			logger.error("财务明细查看失败",e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		model.put("pId", pId);
		String searcherPeriodText=null;
		if(searcherPeriodStart!=null && !"".equals(searcherPeriodStart) && searcherPeriodEnd!=null && !"".equals(searcherPeriodEnd)){
			StringBuffer sbf= new StringBuffer();
			sbf.append(searcherPeriodStart);
			sbf.append("~");
			sbf.append(searcherPeriodEnd);
			searcherPeriodText=sbf.toString();
			model.put("searcherPeriodText",searcherPeriodText);
		}else{
			model.put("searcherPeriodText","");
		}
		
		return finance +"detailFinanceAcctList";
    }
	/**
	 * 
	  * @Description: TODO财务明细查看
	  * @param pid
	  * @param response
	  * @param model
	  * @throws Exception
	  * @author: yql
	  * @date: 2015年3月23日 上午11:58:08
	 */
	@RequestMapping(value = "getFinanceAcctDetailListUrl")
	@ResponseBody
	public void getFinanceAcctDetailListUrl( int pid,String searcherPeriodStart,String searcherPeriodEnd,HttpServletResponse response,ModelMap model,double defaultAmt) throws Exception {
		
		BaseClientFactory clientFactory = null;
		Map<String,Object> map = null;
		try {
			map = new HashMap<String,Object>();
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			
			FinanceAcctTotalCondition financeAcctTotalCondition= new FinanceAcctTotalCondition();
			financeAcctTotalCondition.setPid(pid);
			financeAcctTotalCondition.setSearcherPeriodStart(searcherPeriodStart);
			financeAcctTotalCondition.setSearcherPeriodEnd(searcherPeriodEnd);
			List<FinanceAcctTotalDetailView> list=client.getFinanceAcctTotalDetail(financeAcctTotalCondition);
			//明细列表显示处理
			FinanceAcctTotalDetailView financeAcctTotalDetailView=null;
			List<FinanceAcctTotalDetailView> resultList=new ArrayList<FinanceAcctTotalDetailView>();
			//查询账户信息
//			FinanceBank financeBank=client.getFinanceActtManagerById(pid);
			
			//添加第一条数据
			FinanceAcctTotalDetailView financeAcctTotalDetailViewOne=new FinanceAcctTotalDetailView();
			financeAcctTotalDetailViewOne.setInitialAmt(defaultAmt);
			financeAcctTotalDetailViewOne.setTerminalBalance(defaultAmt);
			resultList.add(financeAcctTotalDetailViewOne);
			double terminalBalance=financeAcctTotalDetailViewOne.getTerminalBalance();
			
			double initialAmtTotal=financeAcctTotalDetailViewOne.getInitialAmt();
			double incomeAccountTotal=0.0;
			double accountOutTotal=0.0;
			double gapAmtTotal=0.0;
			double terminalBalanceTotal=0.0;
			for(int i=0;i<=list.size();i++){	
				
				if(i==list.size()){
					FinanceAcctTotalDetailView temp= new FinanceAcctTotalDetailView();
					temp.setFtDate("合计");
					temp.setInitialAmt(initialAmtTotal);
					temp.setIncomeAccount(incomeAccountTotal);
					temp.setAccountOut(accountOutTotal);
					temp.setGapAmt(gapAmtTotal);
					temp.setTerminalBalance(terminalBalance);
					resultList.add(temp);
				}else{
					financeAcctTotalDetailView=list.get(i);
					if("1".equals(financeAcctTotalDetailView.getFtType())){//支出
						financeAcctTotalDetailView.setAccountOut(financeAcctTotalDetailView.getFtAmt());
					}else if("2".equals(financeAcctTotalDetailView.getFtType())){//收入
						financeAcctTotalDetailView.setIncomeAccount(financeAcctTotalDetailView.getFtAmt());
					}
					
					financeAcctTotalDetailView.setGapAmt(financeAcctTotalDetailView.getIncomeAccount()-financeAcctTotalDetailView.getAccountOut());

					terminalBalance=terminalBalance+financeAcctTotalDetailView.getGapAmt();
					financeAcctTotalDetailView.setTerminalBalance(terminalBalance);
					
					//合计金额
					initialAmtTotal=initialAmtTotal+financeAcctTotalDetailView.getInitialAmt();
					incomeAccountTotal=incomeAccountTotal+financeAcctTotalDetailView.getIncomeAccount();
					accountOutTotal=accountOutTotal+financeAcctTotalDetailView.getAccountOut();
					gapAmtTotal=gapAmtTotal+financeAcctTotalDetailView.getGapAmt();
					terminalBalanceTotal=terminalBalanceTotal+financeAcctTotalDetailView.getTerminalBalance();
					
					resultList.add(financeAcctTotalDetailView);
				}
				
			}
			map.put("rows", resultList);
			
		} catch (Exception e) {
			logger.error("财务明细查看失败",e);
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
	  * @Description: TODO项目总流水查询页面跳转
	  * @param pid
	  * @param response
	  * @param model
	  * @return
	  * @throws Exception
	  * @author: yql
	  * @date: 2015年3月23日 上午11:57:43
	 */
	@RequestMapping(value = "getProjectTotalDetailList")
	public String getProjectTotalDetailList( int loanId,Integer projectId,HttpServletResponse response,ModelMap model) throws Exception {
		BaseClientFactory clientFactory = null;
		
		//查询账户信息
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			
			Project project = client.getLoanProjectByProjectId(projectId);
			model.put("project", project);
		} catch (Exception e) {
			logger.error("项目总流水查询页面跳转失败",e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		model.put("loanId", loanId);
		return finance +"projectTotalDetailList";
    }
	
	/**
	 * 
	  * @Description: TODO项目总流水查询列表
	  * @param pid
	  * @param response
	  * @param model
	  * @throws Exception
	  * @author: yql
	  * @date: 2015年3月23日 上午11:58:08
	 */
	@RequestMapping(value = "getProjectTotalDetailListUrl")
	@ResponseBody
	public void getProjectTotalDetailListUrl( int loanId,HttpServletResponse response,ModelMap model) throws Exception {
		
		BaseClientFactory clientFactory = null;
		Map<String,Object> map = null;
		try {
			map = new HashMap<String,Object>();
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");
			
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			
			FinanceBusinessCondition financeBusinessCondition= new FinanceBusinessCondition();
			financeBusinessCondition.setLoanId(loanId);
			List<ProjectTotalDetailView> list= client.getProjectTotalDetailList(financeBusinessCondition);
			List<ProjectTotalDetailView> resultList=new ArrayList<ProjectTotalDetailView>();
			double actualAmt=0.0;
			double availableBalance=0.0;
			double reconciliationAmt=0.0;
			if(list!=null){
				for(int i=0;i<=list.size();i++){
					if(i==list.size()){//最后的合计计算
						ProjectTotalDetailView temp=new ProjectTotalDetailView();
						temp.setReceiveDt("合计");
						temp.setActualAmt(actualAmt);
						temp.setAvailableBalance(availableBalance);
						temp.setReconciliationAmt(reconciliationAmt);
						resultList.add(temp);
					}else{
						ProjectTotalDetailView projectTotalDetailView=list.get(i);	
						actualAmt=actualAmt+projectTotalDetailView.getActualAmt();
						availableBalance=availableBalance+projectTotalDetailView.getAvailableBalance();
						reconciliationAmt=reconciliationAmt+projectTotalDetailView.getReconciliationAmt();
						
						// 未对账，可删除
						if(projectTotalDetailView.getAvailableBalance() == projectTotalDetailView.getActualAmt() && projectTotalDetailView.getDataVersion()==0)
						{
							projectTotalDetailView.setDeleteAble(true);
						}
						
						resultList.add(projectTotalDetailView);
					}
				}
			}
			
			map.put("rows", resultList);
		} catch (Exception e) {
			logger.error("项目总流水查询列表失败",e);
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
	
	@ResponseBody
	@RequestMapping(value = "deleteLoanInputDate")
	public void deleteLoanInputDate( int pid,HttpServletResponse response) throws Exception {
	  Json result = this.getSuccessObj();
		BaseClientFactory clientFactory = null;
	  try {
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			// 删除不成功
			if(!client.deleteLoanInputDate(pid))
			{
				result = this.getFailedObj("删除财务收款失败，数据已经对账或者已经删除。");
			}	
		} catch (Exception e) {
			logger.error("删除财务收款记录失败", e);
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
		outputJson(result, response);
	}
	
	/**
	 * 
	  * @Description: TODO查看对账信息页面跳转
	  * @param pid
	  * @param response
	  * @param model
	  * @return
	  * @throws Exception
	  * @author: yql
	  * @date: 2015年3月23日 上午11:57:43
	 */
	@RequestMapping(value = "getLoanReconciliationDtl")
	public String getLoanReconciliationDtl(@RequestParam(value = "financeReceivablesId", required = true) int financeReceivablesId,HttpServletResponse response,ModelMap model) throws Exception {
		BaseClientFactory clientFactory = null;
		
		//查询账户信息
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			FinanceReceivablesDTO financeReceivablesDTO = client.getFinanceReceivables(financeReceivablesId);
			if(null == financeReceivablesDTO)
			{
				financeReceivablesDTO = new FinanceReceivablesDTO();
			}else{
				double reconciliationAmt=(financeReceivablesDTO.getPaymentAmount()+financeReceivablesDTO.getUseBalance())-financeReceivablesDTO.getAvailableBalance();
				financeReceivablesDTO.setReconciliationAmt(reconciliationAmt);
			}
			
			model.put("bean", financeReceivablesDTO);
		} catch (Exception e) {
			logger.error("查看对账信息页面跳转失败",e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		
		return finance +"loanReconciliationDtlList";
    }
	
	/**
	 * 
	  * @Description: TODO项目总流水查询列表
	  * @param pid
	  * @param response
	  * @param model
	  * @throws Exception
	  * @author: yql 
	  * @date: 2015年3月23日 上午11:58:08
	 */
	@RequestMapping(value = "getLoanReconciliationDtlUrl")
	@ResponseBody
	public void getLoanReconciliationDtlUrl( int receId,HttpServletResponse response,ModelMap model) throws Exception {
		
		BaseClientFactory clientFactory = null;
		Map<String,Object> map = null;
		try {
			map = new HashMap<String,Object>();
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");			
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			
			List<LoanReconciliationDtlView> list= client.getLoanReconciliationDtl(receId);
			List<LoanReconciliationDtlView> resultList=new ArrayList<LoanReconciliationDtlView>();			

			double reconciliationAmt=0.0;
			if(list!=null){
				for(int i=0;i<=list.size();i++){
					if(i==list.size()){//最后的合计计算
						LoanReconciliationDtlView temp=new LoanReconciliationDtlView();
						temp.setDelTypeName("合计");
						temp.setReconciliationAmt(reconciliationAmt);
						resultList.add(temp);
					}else{
						LoanReconciliationDtlView loanReconciliationDtlView=list.get(i);
						// 退管理费、退利息、退其他费用(只有小于0的，真正需要退的)
						if(loanReconciliationDtlView.getReconciliationAmt()<0 && (loanReconciliationDtlView.getType() == FinanceTypeEnum.TYPE_4.getType() || loanReconciliationDtlView.getType() == FinanceTypeEnum.TYPE_5.getType() || loanReconciliationDtlView.getType() == FinanceTypeEnum.TYPE_6.getType()) )
						{
							loanReconciliationDtlView.setDescription(loanReconciliationDtlView.getDelTypeName()+":"+loanReconciliationDtlView.getReconciliationAmt());
							loanReconciliationDtlView.setReconciliationAmt(0);
						}	
						
						
						reconciliationAmt=reconciliationAmt+loanReconciliationDtlView.getReconciliationAmt();
						resultList.add(loanReconciliationDtlView);
					}
				}
			}			
			map.put("rows", resultList);
		} catch (Exception e) {
			logger.error("目总流水查询列表失败",e);
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
	  * @Description: TODO 收息表查看
	  * @param pid
	  * @param response
	  * @param model
	  * @return
	  * @throws Exception
	  * @author: yql
	  * @date: 2015年3月23日 上午11:57:43
	 */
	@RequestMapping(value = "getFinanceListLoanCalculate")
	public String getFinanceListLoanCalculate(int loanId,int projectId,HttpServletResponse response,ModelMap model) throws Exception {
		BaseClientFactory clientFactory = null;
		BaseClientFactory projectF = null;
			//查询账户信息
			try {
				clientFactory = getFactory(serviceModuel, "FinanceService");
				FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
				FinanceReceivablesView bean = client.getFinanceReceivablesView(loanId);
				projectF = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
				ProjectService.Client clientProject = (ProjectService.Client) projectF.getClient();
				RepaymentLoanInfo repayment = clientProject.getRepaymentLoanInfo(loanId);
				if(null !=repayment){
					model.put("otherCostName", repayment.getOtherCostName());
				}

				model.put("bean", bean);
				model.put("loanId", loanId);
				model.put("projectId", projectId);
				
			} catch (Exception e) {
				logger.error("收息表查看失败",e);
			} finally {
				if (clientFactory != null) {
					try {
						clientFactory.destroy();
					} catch (ThriftException e) {
						e.printStackTrace();
					}
				}
				if ( projectF!=null) {
					try {
						projectF.destroy();
					} catch (ThriftException e) {
						e.printStackTrace();
					}
				}
			}			
			return finance +"finance_list_loanCalculate";
    }
	//add by yql  end 

	@RequestMapping(value = "addFinanceMonthlyReport")
	@ResponseBody
	public void addFinanceMonthlyReport(String month, HttpServletResponse response) throws Exception {
		String result = "success";
		try
		{
			String da[] = getRunDate(month);
			testAddFinanceMonthlyReport(da[0], da[1]);
		}
		catch(Exception e)
		{
			logger.error("运算应收月["+month+"]报表数据失败", e);
			result = "faile";
		}
		// 输出
		outputJson(result, response);
    }
	
	private String [] getRunDate(String monthStr)
	{
		int year = this.getIntValue(monthStr.substring(0, 4), 0);
		String smonth = monthStr.substring(4, monthStr.length());
		int month = this.getIntValue(smonth, 0) ;
		// 如果是以0开头
		if(smonth.startsWith("0"))
		{
			month = this.getIntValue(smonth.substring(1, smonth.length()), 0) ;
		}
		
		Calendar calendar = Calendar.getInstance();   
		calendar.set(year, month, 0);
		
		int maxDay=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);   
		java.text.Format formatter1=new java.text.SimpleDateFormat("yyyy-MM-01");   
		java.text.Format formatter2=new java.text.SimpleDateFormat("yyyy-MM-"+maxDay);   
		
		String [] da = new String[2];
		da[0] = formatter1.format(calendar.getTime());	
		da[1]=	formatter2.format(calendar.getTime());	
		
		return da;
	}

	private void testAddFinanceMonthlyReport(String startDate, String endDate) {
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");			
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			client.addFinanceMonthlyReport(startDate, endDate);
			
		} catch (Exception e) {
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
	 * 项目应收月报表查询
	 */
	@RequestMapping(value = "doListMonthlyReportRecords")
	public String doListMonthlyReportRecords(HttpServletResponse response) throws Exception {
		return finance +"listMonthlyReportRecords";
	}
	
	/**
	 * 客户总账列表处理页面
	 */
	@RequestMapping(value = "listMonthlyReportRecords")
	@ResponseBody
	public void listMonthlyReportRecords(MonthlyReportRecordCondition condition, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory =null;
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");		
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			List<MonthlyReportRecord> resultList=client.listMonthlyReportRecords(condition);
			// 添加合计
			if(null!= resultList && resultList.size() > 0){
				double interestCount = 0;
				double mangCostCount = 0;
				double otherCostCount = 0;
				double theRestCostCount = 0;
				double totalCostCount = 0;
				for(MonthlyReportRecord m: resultList){
					if(m.getInterest()< 0.01){
						m.setInterest(0);
					}
					if(m.getMangCost()< 0.01){
						m.setMangCost(0);
					}
					if(m.getOtherCost()< 0.01){
						m.setOtherCost(0);
					}
					if(m.getTheRestCost()< 0.01){
						m.setTheRestCost(0);
					}
					if(m.getTotalCost()< 0.01){
						m.setTotalCost(0);
					}
					interestCount+=m.getInterest();
					mangCostCount+=m.getMangCost();
					otherCostCount+=m.getOtherCost();
					theRestCostCount+=m.getTheRestCost();
					totalCostCount+=m.getTotalCost();
				}
				MonthlyReportRecord record = new MonthlyReportRecord();
				record.setPid(-1);
				record.setProjectName("合计");
				record.setInterest(interestCount);
				record.setMangCost(mangCostCount);
				record.setOtherCost(otherCostCount);
				record.setTheRestCost(theRestCostCount);
				record.setTotalCost(totalCostCount);
				resultList.add(record);
			}
			int total = client.listMonthlyReportRecordsTotal(condition);
			map.put("rows", resultList);
			map.put("total", total);
		} catch (Exception e) {
			logger.error("展现应收月报表页面失败",e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
				}
			}
		}
		// 输出
		outputJson(map, response);
	}
	
	/**
	 * 
	  * @Description: 导出
	  * @param condition
	  * @param request
	  * @param response
	  * @throws Exception
	  * @author: Zhangyu.Hoo
	  * @date: 2015年7月11日 上午10:39:16
	 */
	@RequestMapping(value = "exportExcel")
	@ResponseBody
	public void exportExcel(MonthlyReportRecordCondition condition, HttpServletRequest request,HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory =null;
		BaseClientFactory temFactory = null;
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			response.setCharacterEncoding("UTF-8");
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");		
			temFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			condition.setPage(1);
			condition.setRows(Integer.MAX_VALUE);
			// 请求中文乱码问题解决
			String tempProjectName = new String(condition.getProjectName().getBytes("ISO-8859-1"),"UTF-8");
			condition.setProjectName(tempProjectName);
			List<MonthlyReportRecord> resultList = client.listMonthlyReportRecords(condition);
			map.put("bean", resultList);
			
			
			TemplateFileService.Client temClient = (TemplateFileService.Client) temFactory.getClient();
			TemplateFile template = temClient.getTemplateFile("FINACE_TOTAL");
			if (!template.getFileUrl().equals("")) {
				String filePath = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(map, filePath, "财务总账.xlsx", response, request);
			}
		} catch (Exception e) {
			logger.error("导出应收月报表页面失败",e);
		} finally {
			if ( null != clientFactory ) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
				}
			}
			if ( null != temFactory ) {
				try {
					temFactory.destroy();
				} catch (ThriftException e) {
				}
			}
		}
	}
	
	/**
	 * 客户总账列表状态锁定
	 */
	@RequestMapping(value = "updateStatus")
	@ResponseBody
	public void updateStatus(MonthlyReportRecordCondition condition, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory =null;
		String result =  "";
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");		
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			int count = client.updateStatus(condition);
			if(count > 0 ){
				result = "操作成功!";
			}else{
				result = "操作失败";
			}
		} catch (Exception e) {
			result = "操作失败";
			logger.error("应收月报表锁定状态操作失败",e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
				}
			}
		}
		// 输出
		outputJson(result, response);
	}
	
	/**
	 * 客户总账列表数据删除
	 */
	@RequestMapping(value = "deleteMonthlyReportRecordsById")
	@ResponseBody
	public void deleteMonthlyReportRecordsById(String pids, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory =null;
		String result =  "";
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");		
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			int count = client.deleteMonthlyReportRecordsById(pids);
			if(count > 0 ){
				result = "操作成功!";
			}else{
				result = "操作失败";
			}
		} catch (Exception e) {
			result = "操作失败";
			logger.error("应收月报表数据删除操作失败",e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
				}
			}
		}
		// 输出
		outputJson(result, response);
	}
	
	/**
	 * 打开修正数据的页面
	 */
	@RequestMapping(value = "updateMonthlyReportRecord")
	public String updateMonthlyReportRecord(HttpServletResponse response) throws Exception {
		return finance +"updateMonthlyReportRecord";
	}
	
	/**
	 * 保存修改的记录
	 */
	@RequestMapping(value = "saveUpdateMonthlyReportRecord")
	public void saveUpdateMonthlyReportRecord(MonthlyReportRecord monthlyReportRecord,HttpServletResponse response) throws Exception {
	
		Json result = this.getSuccessObj();
		try
		{
			String []  data = getRunDate(monthlyReportRecord.getMonth());
			monthlyReportRecord.setStartDate(data[0]);
			monthlyReportRecord.setEndDate(data[1]);
			monthlyReportRecord.setMark("["+monthlyReportRecord.getMonth()+"手动修改数据]");
		}
		catch(Exception e)
		{
			result = this.getFailedObj("修正月份不符合格式，请检查!");
			outputJson(result, response);
			return;
		}
		
		BaseClientFactory clientFactory =null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");		
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			int loanId = client.checkLoanIdByProjectNo(monthlyReportRecord.getProjectNo());
			monthlyReportRecord.setLoanId(loanId);
			if(-1 == loanId)
			{
				result = this.getFailedObj("根据项目编号找不到对于记录，请检查!");
			}
			else
			{
				client.saveMonthlyReportRecord(monthlyReportRecord);
			}
		} catch (Exception e) {
			result = this.getFailedObj("保存失败!");
			logger.error("保存自定义报表数据失败",e);
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
	/**
	 * 打开提成列表页面
	 */
	@RequestMapping(value = "findListUserCommission")
	public String findListUserCommission(HttpServletResponse response) throws Exception {
		return finance +"list_user_commission";
	}
	
	/**
	 * 查看提成记录
	 */
	@RequestMapping(value = "findListUserCommissionUrl")
	public void findListUserCommissionUrl(UserCommissionCondition userCommissionCondition,HttpServletResponse response) throws Exception {
	
		Map<String, Object> map = new HashMap<String, Object>();
		List<UserCommissionView> cusBusiness = null;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(serviceModuel,
					"FinanceTransactionService");
			
			FinanceTransactionService.Client client = (FinanceTransactionService.Client) clientFactory
					.getClient();
			cusBusiness = client.getListUserCommission(userCommissionCondition);
			int total = client.countListUserCommission(userCommissionCondition);
			map.put("rows", cusBusiness);
			map.put("total", total);
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("查看提成记录查询数据失败",e);
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
	  * @Description: TODO查看提成记录详细信息
	  * @param pid
	  * @param response
	  * @param model
	  * @return
	  * @throws Exception
	  * @author: yql
	  * @date: 2015年3月23日 上午11:57:43
	 */
	@RequestMapping(value = "getListUserCommissionDetail")
	public String getListUserCommissionDetail( int userId,String date,HttpServletResponse response,ModelMap model) throws Exception {		
		
		model.put("userId", userId);
		model.put("date", date);
		return finance +"commissionDetailList";
    }
	/**
	 * 查看提成记录详细信息
	 */
	@RequestMapping(value = "getListUserCommissionDetailUrl")
	public void getListUserCommissionDetailUrl(UserCommissionCondition userCommissionCondition,HttpServletResponse response) throws Exception {
	
		Map<String, Object> map = new HashMap<String, Object>();
		List<UserCommissionView> cusBusiness = null;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(serviceModuel,
					"FinanceTransactionService");
			
			FinanceTransactionService.Client client = (FinanceTransactionService.Client) clientFactory
					.getClient();
			cusBusiness = client.getListUserCommissionDetail(userCommissionCondition);
			map.put("rows", cusBusiness);
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(" 查看提成记录详细信息查询数据失败",e);
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
}
