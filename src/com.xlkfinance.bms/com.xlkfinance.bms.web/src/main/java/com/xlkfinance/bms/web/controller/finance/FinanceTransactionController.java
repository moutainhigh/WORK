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
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.finance.FinanceBank;
import com.xlkfinance.bms.rpc.finance.FinanceService;
import com.xlkfinance.bms.rpc.finance.FinanceTDTO;
import com.xlkfinance.bms.rpc.finance.FinanceTransactionCondition;
import com.xlkfinance.bms.rpc.finance.FinanceTransactionService;
import com.xlkfinance.bms.rpc.finance.FinanceTransactionView;
import com.xlkfinance.bms.rpc.finance.TransactionView;
import com.xlkfinance.bms.rpc.system.TemplateFile;
import com.xlkfinance.bms.rpc.system.TemplateFileService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.shiro.ShiroUser;
import com.xlkfinance.bms.web.util.CommonUtil;
import com.xlkfinance.bms.web.util.ExcelExport;

/**
 * 财务资金头寸
  * @ClassName: FinanceTransactionController
  * @Description: TODO
  * @author: yql
  * @date: 2015年4月16日 下午5:00:06
 */
@Controller
@RequestMapping("/financeTransactionController")
public class FinanceTransactionController extends BaseController {
	private static final String serviceModuel = "finance";
	private static final String finance = "finance/";
	private Logger logger = LoggerFactory.getLogger(FinanceTransactionController.class);

	//add by yql
	/**
	 * 财务支出列表查询跳转
	 */
	@RequestMapping(value = "/listAcctountOutput")
	public String listAcctountOutput(ModelMap model,int bankAcctId,int regCategory) throws Exception {
		BaseClientFactory fClientFactory  = null;
		String page=null;
		try {
			fClientFactory = getFactory(serviceModuel,"FinanceService");	
			FinanceService.Client fclient = (FinanceService.Client) fClientFactory.getClient();
			FinanceBank fbank=fclient.getFinanceActtManagerById(bankAcctId);
			model.put("financeBank", fbank);
			if(regCategory==4){
				page=finance + "list_acctount_output";
			}else if(regCategory==3){
				page=finance + "list_acctount_input";
			}
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fClientFactory != null) {
				try {
					fClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		return page;
		
	}

	/**
	 * 财务支出列表
	 * 
	 * @param cusPerBaseDTO
	 * @param response
	 */
	@RequestMapping(value = "/listFinanceTransactionUrl")
	@ResponseBody
	public void listFinanceTransactionUrl(
			FinanceTransactionCondition ftcondition,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<FinanceTransactionView> list = null;
		BaseClientFactory clientFactory = null;
		
		try {
			List<FinanceTransactionView> resultList = new ArrayList<FinanceTransactionView>();
			clientFactory = getFactory(serviceModuel,"FinanceTransactionService");
			
			FinanceTransactionService.Client client = (FinanceTransactionService.Client) clientFactory.getClient();			
			
			list = client.getFinanceTransactionList(ftcondition);
			double ftAmtTotal=0.0;
			if(list!=null){
				for(int i=0;i<=list.size();i++){
					
					if (i == list.size()) {
						FinanceTransactionView temp= new FinanceTransactionView();
						temp.setFtDt("合计");
						temp.setFtAmt(ftAmtTotal);
						resultList.add(temp);					
						
					}else{
						FinanceTransactionView financeTransactionView=list.get(i);
						ftAmtTotal=ftAmtTotal+financeTransactionView.getFtAmt();
						resultList.add(financeTransactionView);
					}
				}
			}
			int total = client.countFinanceTransactionList(ftcondition);
			map.put("rows", resultList);
			map.put("total", total);
		} catch (ThriftServiceException e) {
			e.printStackTrace();
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
	 * 跳转到财务支出新增编辑页面
	 */
	@RequestMapping(value = "/editAcctountOutput")
	public String editAcctountOutput(ModelMap model,FinanceTransactionCondition ftcondition) throws Exception {
		BaseClientFactory clientFactory  = null;
		BaseClientFactory fClientFactory  = null;
		String page=null;
		try {
			clientFactory = getFactory(serviceModuel,"FinanceService");	
			fClientFactory = getFactory(serviceModuel,"FinanceTransactionService");
			FinanceService.Client fclient = (FinanceService.Client) clientFactory.getClient();
			FinanceTransactionService.Client ftClient = (FinanceTransactionService.Client) fClientFactory.getClient();	
			
			FinanceBank fbank=fclient.getFinanceActtManagerById(ftcondition.getBankAcctId());
			FinanceTransactionView financeTransactionView=ftClient.getFinanceTransactionById(ftcondition.getPid());			
			model.put("financeBank", fbank);
			model.put("financeTransactionView", financeTransactionView);
			if(ftcondition.getRegCategory()==4){
				page=finance + "editAcctountOutput";
			}else if(ftcondition.getRegCategory()==3){
				page=finance + "editAcctountInput";
			}
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fClientFactory != null && clientFactory!=null) {
				try {
					fClientFactory.destroy();
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		
		return page;
		
	}
	/**
	 *添加财务支出记录
	 */
	@RequestMapping(value = "/editOuptut")
	@ResponseBody
	public void insertOuptut(HttpServletResponse response,ModelMap model,FinanceTDTO financeTDTO) throws Exception {
		BaseClientFactory clientFactory  = null;
		int result=0;
		ShiroUser shiroUser = super.getShiroUser();
		try {
			clientFactory = getFactory(serviceModuel,"FinanceTransactionService");
			
			FinanceTransactionService.Client client = (FinanceTransactionService.Client) clientFactory.getClient();		
			financeTDTO.setCreUserId(shiroUser.getPid());			
			if(financeTDTO.getPid()>0){//有主键id的执行编辑方法
				financeTDTO.setStatus(1);
				result=client.updateFinanceTransaction(financeTDTO);
			}else{
				if(financeTDTO.getRegCategory()==4){
					financeTDTO.setFtType(2);//财务类型支出
				}else if(financeTDTO.getRegCategory()==3){
					financeTDTO.setFtType(1);//财务类型收入
					financeTDTO.setIncomeType(1);// 理财收入类型1
				}
				result=client.insertFt(financeTDTO);
				//
			}
			
		} catch (ThriftServiceException e) {
			e.printStackTrace();
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
		outputJson(result, response);
	}
	/**
	 *删除财务支出记录
	 */
	@RequestMapping(value = "/deleteFinanceTransaction")
	@ResponseBody
	public void deleteFinanceTransaction(HttpServletResponse response,ModelMap model,FinanceTDTO financeTDTO) throws Exception {
		BaseClientFactory clientFactory  = null;
		int result=0;
		ShiroUser shiroUser = super.getShiroUser();
		try {
			clientFactory = getFactory(serviceModuel,"FinanceTransactionService");
			
			FinanceTransactionService.Client client = (FinanceTransactionService.Client) clientFactory.getClient();		
			financeTDTO.setCreUserId(shiroUser.getPid());	
			financeTDTO.setStatus(0);//删除数据
			result=client.updateFinanceTransaction(financeTDTO);//删除数据
			
			
		} catch (ThriftServiceException e) {
			e.printStackTrace();
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
		outputJson(result, response);
	}
	
	/**
	 * 融资借款列表查询跳转
	 */
	@RequestMapping(value = "/listFinanceFinancing")
	public String listFinanceFinancing(ModelMap model,int bankAcctId) throws Exception {
		BaseClientFactory fClientFactory  = null;

		try {
			fClientFactory = getFactory(serviceModuel,"FinanceService");	
			FinanceService.Client fclient = (FinanceService.Client) fClientFactory.getClient();
			FinanceBank fbank=fclient.getFinanceActtManagerById(bankAcctId);
			model.put("financeBank", fbank);
			
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fClientFactory != null) {
				try {
					fClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		return finance + "list_acctount_financing";
		
	}

	/**
	 * 融资借款列表
	 * 
	 * @param cusPerBaseDTO
	 * @param response
	 */
	@RequestMapping(value = "/listFinanceFinancingUrl")
	@ResponseBody
	public void listFinanceFinancingUrl(
			FinanceTransactionCondition ftcondition,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<FinanceTransactionView> list = null;
		BaseClientFactory clientFactory = null;
		
		
		try {
			List<FinanceTransactionView> resultList = new ArrayList<FinanceTransactionView>();
			clientFactory = getFactory(serviceModuel,"FinanceTransactionService");
			
			FinanceTransactionService.Client client = (FinanceTransactionService.Client) clientFactory.getClient();			
			
			list = client.getFinanceFinancing(ftcondition);
			double ftAmtLoan=0.0;//借款金额
		    double ftAmtInput=0.0;//已还金额
			double loanBalance=0.0;//借款金额
			double ftAmtInterest=0.0;//支出利息
			if(list!=null){
				for(int i=0;i<=list.size();i++){
					
					if (i == list.size()) {
						FinanceTransactionView temp= new FinanceTransactionView();
						temp.setRepayDt("合计");
						temp.setFtAmtLoan(ftAmtLoan);
						temp.setFtAmtInput(ftAmtInput);
						temp.setLoanBalance(loanBalance);
						temp.setFtAmtInterest(ftAmtInterest);
						resultList.add(temp);					
						
					}else{
						FinanceTransactionView financeTransactionView=list.get(i);
						ftAmtLoan=ftAmtLoan+financeTransactionView.getFtAmtLoan();
						ftAmtInput=ftAmtInput+financeTransactionView.getFtAmtInput();
						loanBalance=loanBalance+financeTransactionView.getLoanBalance();
						ftAmtInterest=ftAmtInterest+financeTransactionView.getFtAmtInterest();
						resultList.add(financeTransactionView);
					}
				}
			}
			int total = client.countFinanceFinancing(ftcondition);
			map.put("rows", resultList);
			map.put("total", total);
		} catch (ThriftServiceException e) {
			e.printStackTrace();
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
	 * 跳转到财务融资借贷新增编辑页面
	 */
	@RequestMapping(value = "/editAcctountFinancing")
	public String editAcctountFinancing( HttpServletRequest request,ModelMap model,FinanceTransactionCondition ftcondition) throws Exception {
		BaseClientFactory clientFactory  = null;
		BaseClientFactory fClientFactory  = null;
		String page=null;
		try {
			
			clientFactory = getFactory(serviceModuel,"FinanceService");	
			fClientFactory = getFactory(serviceModuel,"FinanceTransactionService");
			FinanceService.Client fclient = (FinanceService.Client) clientFactory.getClient();
			FinanceTransactionService.Client ftClient = (FinanceTransactionService.Client) fClientFactory.getClient();	
			
			FinanceBank fbank=fclient.getFinanceActtManagerById(ftcondition.getBankAcctId());
			FinanceTransactionView financeTransactionView=null;	
			
			
			if(ftcondition.getRegCategory()==1){//支息
				financeTransactionView=ftClient.getFinanceTransactionById(ftcondition.getPid());	
				if(financeTransactionView.getPid()==0){
					financeTransactionView=new FinanceTransactionView();
					String currtDttm = DateUtil.format(new Date());
					financeTransactionView.setFtDt(currtDttm);
					financeTransactionView.setRepayDt(currtDttm);
				}
				
				page=finance + "editAcctountFinancing";
				
			}else if(ftcondition.getRegCategory()==2){//还款
				financeTransactionView= new FinanceTransactionView();
				String currtDttm = DateUtil.format(new Date());
				financeTransactionView.setFtDt(currtDttm);
				financeTransactionView.setPid(ftcondition.getPid());
				financeTransactionView.setRepayType(ftcondition.getRepayType());
//				financeTransactionView.setFtAmt(ftcondition.get)
				financeTransactionView.setRegCategory(2);
				
				//坐借款金额判断
				String ftAmtLoan=request.getParameter("ftAmtLoan");
				double ftAmtLoanDouble=ftAmtLoan!=null?Double.parseDouble(ftAmtLoan):0;
				String ftAmtInput=request.getParameter("ftAmtInput");
				double ftAmtInputDouble=ftAmtInput!=null?Double.parseDouble(ftAmtInput):0;
				
				model.put("unFtAmtLoan", ftAmtLoanDouble-ftAmtInputDouble);	
				model.put("repayType", ftcondition.getRepayType());
				page=finance + "editFinancingRepayment";
				
				
			}
			model.put("financeBank", fbank);			
			model.put("financeTransactionView", financeTransactionView);
			
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fClientFactory != null && clientFactory!=null) {
				try {
					fClientFactory.destroy();
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		
		return page;
		
	}
	/**
	 *添加修改融资记录数据
	 */
	@RequestMapping(value = "/saveAcctountFinancing")
	@ResponseBody
	public void saveAcctountFinancing(HttpServletResponse response,ModelMap model,FinanceTDTO financeTDTO) throws Exception {
		BaseClientFactory clientFactory  = null;
		int result=0;
		ShiroUser shiroUser = super.getShiroUser();
		try {
			clientFactory = getFactory(serviceModuel,"FinanceTransactionService");
			
			FinanceTransactionService.Client client = (FinanceTransactionService.Client) clientFactory.getClient();		
			financeTDTO.setCreUserId(shiroUser.getPid());			
			if(financeTDTO.getPid()>0 && financeTDTO.getRegCategory()!=2){//有主键id的执行编辑方法
				financeTDTO.setStatus(1);
				result=client.updateFinanceTransaction(financeTDTO);
			}else{
				if(financeTDTO.getRegCategory()==2){//融资还款
					financeTDTO.setFtType(2);//财务类型支出
					financeTDTO.setParentId(financeTDTO.getPid());
					financeTDTO.setPid(0);
				}else if(financeTDTO.getRegCategory()==1){//融资借款
					financeTDTO.setFtType(1);//财务类型收入
					
				}
				result=client.insertFt(financeTDTO);
			}
			
		} catch (ThriftServiceException e) {
			e.printStackTrace();
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
		outputJson(result, response);
	}
	/**
	 *删除财务支出记录
	 */
	@RequestMapping(value = "/deleteFinanceFinancing")
	@ResponseBody
	public void deleteFinanceFinancing(HttpServletResponse response,ModelMap model,FinanceTDTO financeTDTO) throws Exception {
		BaseClientFactory clientFactory  = null;
		int result=0;
		ShiroUser shiroUser = super.getShiroUser();
		try {
			clientFactory = getFactory(serviceModuel,"FinanceTransactionService");
			
			FinanceTransactionService.Client client = (FinanceTransactionService.Client) clientFactory.getClient();		
			financeTDTO.setCreUserId(shiroUser.getPid());	
			financeTDTO.setStatus(0);//删除数据
			result=client.deleteFinanceFinancing(financeTDTO);//删除数据
			
			
		} catch (ThriftServiceException e) {
			e.printStackTrace();
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
		outputJson(result, response);
	}

	/**
	 * 资金头寸查询跳转
	 */
	@RequestMapping(value = "/getTransactionList")
	public String getTransactionList(ModelMap model) throws Exception {
		
		return finance + "list_finance_transaction";
		
	}

	/**
	 * 资金头寸数据查询
	 */
	@RequestMapping(value = "/getTransactionListUrl")
	@ResponseBody
	public void getTransactionListUrl(ModelMap model,FinanceBank financeBank,HttpServletResponse response) throws Exception {
		BaseClientFactory fClientFactory  = null;
		List<TransactionView> list = null;
		
		Map<String, Object> map = new HashMap<String, Object>();
		 double defaultAmt=0.0;
		 double borrowLoanBalance=0.0;//借款余额
		 double loanTotal=0.0;//借款总额
	 	 double loanHasAlsoTotal=0.0;//借款已还总额
	 	 double loanBalance=0.0;//贷款
		 double loanPrincipalTotal=0.0;//放款本金总额
		 double takeBackPrincipalTotal=0.0;//收回本金总额
		 double rateTakeBacTotal=0.0;//费利收回总额
		 double financialDepositInterest=0.0;//理财存款利息
		 double expensesCostTotal=0.0;//支出费用总额
		 double availableFundBalance=0.0;//支出费用总额	
		 double inputUnrecAmt=0.0;//收款未对账金额
		try {
			List<TransactionView> resultList = new ArrayList<TransactionView>();
			fClientFactory = getFactory(serviceModuel,"FinanceTransactionService");			
			FinanceTransactionService.Client client = (FinanceTransactionService.Client) fClientFactory.getClient();		
			list = client.getTransactionList(financeBank);
			if(list!=null){
				for(int i=0;i<=list.size();i++){
					
					if (i == list.size()) {
						TransactionView temp= new TransactionView();
						temp.setBankCardTypeText("合计");
						temp.setDefaultAmt(defaultAmt);
						temp.setBorrowLoanBalance(borrowLoanBalance);
						temp.setLoanTotal(loanTotal);
						temp.setLoanHasAlsoTotal(loanHasAlsoTotal);
						temp.setLoanBalance(loanBalance);
						temp.setLoanPrincipalTotal(loanPrincipalTotal);
						temp.setTakeBackPrincipalTotal(takeBackPrincipalTotal);
						temp.setRateTakeBacTotal(rateTakeBacTotal);
						temp.setFinancialDepositInterest(financialDepositInterest);
						temp.setExpensesCostTotal(expensesCostTotal);
						temp.setAvailableFundBalance(availableFundBalance+inputUnrecAmt);
						temp.setInputUnrecAmt(inputUnrecAmt);
					
						resultList.add(temp);					
						
					}else{
						TransactionView transactionView= list.get(i);						
						
						defaultAmt=defaultAmt+transactionView.getDefaultAmt();
						borrowLoanBalance=borrowLoanBalance+transactionView.getBorrowLoanBalance();
						loanTotal=loanTotal+transactionView.getLoanTotal();//借款总额
				 	    loanHasAlsoTotal=loanHasAlsoTotal+transactionView.getLoanHasAlsoTotal();//借款已还总额
				 	    loanBalance=loanBalance+transactionView.getLoanBalance();//贷款
					    loanPrincipalTotal=loanPrincipalTotal+transactionView.getLoanPrincipalTotal();//放款本金总额
					    takeBackPrincipalTotal=takeBackPrincipalTotal+transactionView.getTakeBackPrincipalTotal();//收回本金总额
					    rateTakeBacTotal=rateTakeBacTotal+transactionView.getRateTakeBacTotal();//费利收回总额
					    financialDepositInterest=financialDepositInterest+transactionView.getFinancialDepositInterest();//理财存款利息
					    expensesCostTotal=expensesCostTotal+transactionView.getExpensesCostTotal();//支出费用总额
					    availableFundBalance=availableFundBalance+transactionView.getAvailableFundBalance();//支出费用总额	
					    inputUnrecAmt=transactionView.getInputUnrecAmt();
					    transactionView.setInputUnrecAmt(0.0);
					    resultList.add(transactionView);
					}
				}
			}
			int total = client.countTransactionList(financeBank);
			map.put("rows", resultList);
			map.put("total", total);
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fClientFactory != null) {
				try {
					fClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(map, response);
		
	}
	/**
	 * 
	 * @Description: 导出资金头寸报表
	 * @param financeBank
	 * @param response
	 * @author: yql
	 * @date: 2015年3月21日 上午11:27:50
	 */
	@RequestMapping(value = "exportTransactionTable")
	@ResponseBody
	public void exportTransactionTable( HttpServletResponse response,FinanceBank financeBank, HttpServletRequest request) {
		BaseClientFactory fClientFactory  = null;
		BaseClientFactory c =null;
		Map<String,Object> map = new HashMap<String,Object>();
		List<TransactionView>  list=null;
		try {
			c = getFactory(BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
			TemplateFileService.Client cl = (TemplateFileService.Client) c.getClient();
			fClientFactory = getFactory(serviceModuel,"FinanceTransactionService");	
			
			FinanceTransactionService.Client client = (FinanceTransactionService.Client) fClientFactory.getClient();		
			list = client.getTransactionList(financeBank);
			
			map.put("bean", list);
			TemplateFile template = cl.getTemplateFile("ZJTC");
			
			if(!template.getFileUrl().equals("")){
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(map, path, "资金头寸报表.xlsx", response, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fClientFactory != null) {
				try {
					fClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
			if (c != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}

	}
	/**
	 * 首页资金信息
	 */
	@RequestMapping(value = "/getTransactionIndexUrl")
	@ResponseBody
	public void getTransactionIndexUrl(ModelMap model,FinanceBank financeBank,HttpServletResponse response) throws Exception {
		BaseClientFactory fClientFactory  = null;
		BaseClientFactory clientFactory  = null;
		List<TransactionView> list = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
	 	 double loanBalance=0.0;//贷款
		 double loanPrincipalTotal=0.0;//放款本金总额		 
		 int custSum=0;//客户总数
		 double availableFundBalance=0.0;//支出费用总额	
		 double weekAmt=0.0;//未来一周应收金额
		 double aprilAmt=0.0;//未来一月应收金额
		 double inputUnrecAmt=0.0;//收款未对账金额
		try {
			
			fClientFactory = getFactory(serviceModuel,"FinanceTransactionService");			
			FinanceTransactionService.Client client = (FinanceTransactionService.Client) fClientFactory.getClient();		
			list = client.getTransactionList(financeBank);
			if(list!=null){
				for(int i=0;i<list.size();i++){
						TransactionView transactionView= list.get(i);						
				 	    loanBalance=loanBalance+transactionView.getLoanBalance();//贷款
					    loanPrincipalTotal=loanPrincipalTotal+transactionView.getLoanPrincipalTotal();//放款本金总额
					    availableFundBalance=availableFundBalance+transactionView.getAvailableFundBalance();//支出费用总额	
					    aprilAmt=transactionView.getAprilAmt();
					    weekAmt=transactionView.getWeekAmt();
					    inputUnrecAmt=transactionView.getInputUnrecAmt();
				}
				availableFundBalance=availableFundBalance+inputUnrecAmt;
			}
			 resultMap.put("loanBalance", loanBalance/10000);
			 resultMap.put("weekAmt", weekAmt/10000);//未来一周应收金额
			 resultMap.put("aprilAmt", aprilAmt/10000);//未来一月应收金额
			 resultMap.put("availableFundBalance", availableFundBalance/10000);
//			 clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusAcctService");
//			 CusAcctService.Client clientCus = (CusAcctService.Client) clientFactory.getClient();
			 // 获取有效的客户总数
			 custSum = client.countCustArrearsTotal();
			 resultMap.put("custSum", custSum);
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fClientFactory != null) {
				try {
					fClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(resultMap, response);
		
	}
	//add by yql end
	

}
