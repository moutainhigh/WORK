/**
 * @Title: FinanceReconciliationController.java
 * @Package com.xlkfinance.bms.web.controller.finance
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: qiancong.Xian
 * @date: 2015年3月12日 下午4:37:30
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.finance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
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
import com.achievo.framework.util.StringUtil;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.beforeloan.BeforloadOutputSerice;
import com.xlkfinance.bms.rpc.beforeloan.LoanOutputInfoImpl;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.customer.CusAcct;
import com.xlkfinance.bms.rpc.customer.CusAcctBank;
import com.xlkfinance.bms.rpc.customer.CusAcctBankView;
import com.xlkfinance.bms.rpc.customer.CusAcctService;
import com.xlkfinance.bms.rpc.finance.AcctProjectBalanceDTO;
import com.xlkfinance.bms.rpc.finance.AcctProjectBalanceView;
import com.xlkfinance.bms.rpc.finance.BatchRepaymentBean;
import com.xlkfinance.bms.rpc.finance.BatchRepaymentItem;
import com.xlkfinance.bms.rpc.finance.BatchRepaymentView;
import com.xlkfinance.bms.rpc.finance.FinanceBank;
import com.xlkfinance.bms.rpc.finance.FinanceBusinessCondition;
import com.xlkfinance.bms.rpc.finance.FinanceReceivablesDTO;
import com.xlkfinance.bms.rpc.finance.FinanceReceivablesView;
import com.xlkfinance.bms.rpc.finance.FinanceService;
import com.xlkfinance.bms.rpc.finance.LoanCycleNumView;
import com.xlkfinance.bms.rpc.finance.LoanRecIntoDTO;
import com.xlkfinance.bms.rpc.finance.LoanRecIntoService;
import com.xlkfinance.bms.rpc.finance.LoanRefundDTO;
import com.xlkfinance.bms.rpc.finance.LoanRefundView;
import com.xlkfinance.bms.rpc.finance.ReconciliationItem;
import com.xlkfinance.bms.rpc.finance.ReconciliationOptionsView;
import com.xlkfinance.bms.rpc.finance.RepaymentReconciliationBean;
import com.xlkfinance.bms.rpc.finance.RepaymentReconciliationDTO;
import com.xlkfinance.bms.rpc.finance.RepaymentReconciliationDetailDTO;
import com.xlkfinance.bms.rpc.finance.UnReconciliationCondition;
import com.xlkfinance.bms.rpc.finance.UnReconciliationView;
import com.xlkfinance.bms.rpc.repayment.RepayFeewdtlDatDTO;
import com.xlkfinance.bms.rpc.repayment.RepayFeewdtlService;
import com.xlkfinance.bms.rpc.system.SysConfigService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;
import com.xlkfinance.bms.web.shiro.ShiroUser;

@Controller
@RequestMapping("/financeReconciliationController")
public class FinanceReconciliationController extends BaseController {

	private static final String serviceModuel = "finance";
	private static final String finance = "finance/";
	private Logger logger = LoggerFactory.getLogger(FinanceReconciliationController.class);

	/**
	 * @Description: 调整手动对账页面
	 * @return
	 * @author: wangheng
	 * @date: 2014年1月26日
	 */
	@RequestMapping(value = "handReconciliation")
	public String handReconciliation(@RequestParam(value = "loanId", required = false) Integer loanId, ModelMap model, HttpServletResponse response, HttpServletRequest request) throws Exception {
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(serviceModuel, "FinanceService");
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			FinanceReceivablesView bean = client.getFinanceReceivablesView(loanId);
			String uploadDttm = DateUtil.format(new Date());

			model.put("currDate", uploadDttm);
			// 是否以期限日作为逾期计息日
			model.put("useRepayDt", this.getSysConfigValue("FinanceReconciliationUseRepayDt", "true"));
			
			model.put("paymentDttm", uploadDttm);
			// 查询 贷款利息收取单位账号、贷款管理费收取单位账号、其它费用收取单位账号
			FinanceBank financeBankInterest = client.getFinanceActtManagerById(bean != null ? bean.getLoanInterestRecord() : 0);
			bean.setLoanInterestRecordNo(financeBankInterest != null ? financeBankInterest.getBankNum() : "");

			FinanceBank financeBankMgr = client.getFinanceActtManagerById(bean != null ? bean.getLoanMgrRecord() : 0);
			bean.setLoanMgrRecordNo(financeBankMgr != null ? financeBankMgr.getBankNum() : "");

			FinanceBank financeBankOther = client.getFinanceActtManagerById(bean != null ? bean.getLoanOtherFee() : 0);
			bean.setLoanOtherFeeNo(financeBankOther != null ? financeBankOther.getBankNum() : "");
			// 查询未对账金额,从对账流水页面跳转过来会使用
			String financeReceivablesIdStr = request.getParameter("financeReceivablesId");
			int financeReceivablesId = financeReceivablesIdStr != null && !"".equals(financeReceivablesIdStr) ? Integer.parseInt(financeReceivablesIdStr) : 0;

			if (financeReceivablesId > 0) {
				FinanceReceivablesDTO financeReceivablesDTO = client.getFinanceReceivables(financeReceivablesId);
				if (null != financeReceivablesDTO) {
					bean.setWaitReconciliationAmount(financeReceivablesDTO.getAvailableBalance());
					bean.setHedgingAmount(financeReceivablesDTO.getPaymentAmount() - financeReceivablesDTO.getAvailableBalance());
					bean.setAvailableReconciliationAmount(financeReceivablesDTO.getAvailableBalance());
					// 收款日期
					model.put("paymentDttm", financeReceivablesDTO.getPaymentDttm());

					if (bean.getFinanceReceivablesDTO() != null) {
						bean.getFinanceReceivablesDTO().setPaymentAmount(financeReceivablesDTO.getPaymentAmount());
					} else {
						FinanceReceivablesDTO finreDto = new FinanceReceivablesDTO();
						finreDto.setPaymentAmount(financeReceivablesDTO.getPaymentAmount());
						bean.setFinanceReceivablesDTO(financeReceivablesDTO);
					}

					// 页面展现的使用余额初始化未0
					financeReceivablesDTO.setUseBalance(0);
				}

				// 旧的财务收款
				model.put("oldfinanceReceivables", true);
			}

			model.put("bean", bean);
		}  catch (Exception e) {
			logger.error("打开财务对账页面失败", e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}

		String earlyRepayment = request.getParameter("earlyRepayment");

		// 是否是提前还款
		model.put("earlyRepayment", null == earlyRepayment ? false : true);
		return finance + "hand_reconciliation";
	}

	/**
	 * 费用减免对话框
	 * 
	 * @param loanId
	 * @param model
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 * @author: qiancong.Xian
	 * @date: 2015年4月22日 下午2:36:18
	 */
	@RequestMapping(value = "reductionDialogn")
	public String reductionDialogn(int loanId, String currDate, int planId, ModelMap model, HttpServletResponse response, HttpServletRequest request) throws Exception {

		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		int reductionNum = 7; // 减免天数（用于构造表头）
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd");
		long currtTime = dateFm.parse(currDate).getTime(); // 当前日期的时间
		long dateTime = 24 * 60 * 60 * 1000; // 一天的毫秒数
		currtTime = currtTime - reductionNum * dateTime;
		for (int i = 0; i < reductionNum; i++) {
			// 计算的日期
			Map<String, String> josn = new HashMap<String, String>();
			// 计算的当天时间
			currtTime = currtTime + dateTime;
			Date date = new Date(currtTime);
			String dateStr = dateFm.format(date); // 计算的日期
			josn.put("dataStr", dateStr);

			cal.setTime(date);
			int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
			if (w < 0)
				w = 0;
			String wweekDay = weekDays[w]; // 星期
			josn.put("title", dateStr + "<br>(" + wweekDay + ")");
			result.add(josn);
		}

		// 逾期利息
		List<Double> result_l = new ArrayList<Double>();
		// 逾期罚息
		List<Double> result_f = new ArrayList<Double>();
		
	    // 保存结果
			BaseClientFactory clientFactory = getFactory(serviceModuel, "FinanceService");
			try {
				FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
				
				// 遍历表头
				for (Map<String,String> titls:result) {
					// 计划还款
					if(planId>0)
					{
						result_l.add(Double.valueOf(client.getOverdueByDate(loanId, planId, titls.get("dataStr"))));
						result_f.add(Double.valueOf(0));
					}
					// 即使发生
					else
					{
						result_f.add(Double.valueOf(client.getOverdueByDate(loanId, planId, titls.get("dataStr"))));
						result_l.add(Double.valueOf(0));
					}
				}

			} catch (Exception e) {
				logger.error("打开费用减免对话框页面失败", e);
			} finally {
				if (clientFactory != null) {
					try {
						clientFactory.destroy();
					} catch (ThriftException e) {
					}
				}
			}
			
			// 最大可减免
			double maxAmt = 0;
			for(Double amt:result_l)
			{
				maxAmt += amt;
			}	
			model.put("titls", result);
			model.put("overdueL", result_l);
			model.put("overdueF", result_f);
			model.put("maxAmt", maxAmt);
	
		return finance + "reductionDialogn";
	}

	/**
	 * 基于旧的收款对账时候，继续使用客户余额
	 * 
	 * @param projectId
	 * @param acctId
	 * @param ReceivablesDTO
	 * @param useBalance
	 * @param response
	 * @author: qiancong.Xian
	 * @date: 2015年4月21日 下午4:49:29
	 */
	@RequestMapping(value = "saveUseBalance")
	public void saveUseBalance(FinanceReceivablesDTO dto, int projectId, int acctId, HttpServletResponse response) {
		
		logger.info("基于旧的收款对账时候，继续使用客户余额  FinanceReconciliationController.saveUseBalance ：projectId["+projectId+"] acctId ["+acctId+"] loanId["+dto.getLoanId()+"] paymentAmount["+dto.getPaymentAmount()+"] paymentDttm["+dto.getPaymentDttm()+"] useBalance["+dto.getUseBalance()+"] reconciliation["+dto.getReconciliation()+"] status["+dto.getStatus()+"] version["+dto.getVersion()+"] availableBalance["+dto.getAvailableBalance()+"] reconciliationAmt["+dto.getReconciliation()+"]" );

		// 保存结果
		Json result = null;
		BaseClientFactory clientFactory = getFactory(serviceModuel, "FinanceService");
		try {
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			FinanceReceivablesDTO dto2 = client.saveUseBalance(dto, projectId, acctId);
			result = getSuccessObj(String.valueOf(dto2.getVersion()), "保存成功");
		}  catch (Exception e) {
			logger.error("使用客户余额失败", e);
			result = getFailedObj("保存收款失败");
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
	 * 保存财务收款
	 * 
	 * @Description: TODO
	 * @param dto
	 * @param response
	 * @author: qiancong.Xian
	 * @date: 2015年3月12日 下午4:41:23
	 */
	@RequestMapping(value = "saveFinanceReceivables")
	public void saveFinanceReceivables(FinanceReceivablesDTO dto, int projectId, int acctId, HttpServletResponse response) {
		// 保存结果
		Json result = null;
		BaseClientFactory clientFactory = getFactory(serviceModuel, "FinanceService");
		
		logger.info("财务收款FinanceReconciliationController.saveFinanceReceivables ：projectId["+projectId+"] acctId ["+acctId+"] loanId["+dto.getLoanId()+"] paymentAmount["+dto.getPaymentAmount()+"] paymentDttm["+dto.getPaymentDttm()+"] useBalance["+dto.getUseBalance()+"] reconciliation["+dto.getReconciliation()+"] status["+dto.getStatus()+"] version["+dto.getVersion()+"] availableBalance["+dto.getAvailableBalance()+"] reconciliationAmt["+dto.getReconciliation()+"]" );
		try {
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			// 可用余额=实收+使用用户账户的余额
			dto.setAvailableBalance(dto.getPaymentAmount() + dto.getUseBalance());
			dto.setCreateUser(getShiroUser().getPid()+"");
			dto.setCreateDate(DateUtil.format(DateUtil.getToDay()));
			FinanceReceivablesDTO dto2 = client.saveFinanceReceivables(dto, projectId, acctId);
			result = getSuccessObj(String.valueOf(dto2.getPid()), "保存成功");
		} catch (Exception e) {
			logger.error("财务收款失败",  e);
			result = getFailedObj("保存收款失败");
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
	 * 获取可对账的期数选项
	 * 
	 * @param dto
	 * @param response
	 * @author: qiancong.Xian
	 * @date: 2015年3月12日 下午4:41:23
	 */
	@RequestMapping(value = "getReconciliationOptionsList")
	public void getReconciliationOptionsList(@RequestParam(value = "loanId", required = false) Integer loanId, @RequestParam(value = "currentDt", required = true) String currentDt,double waitReconciliationAmount, HttpServletResponse response) {
		// 保存结果
		List<ReconciliationOptionsView> list = new ArrayList<ReconciliationOptionsView>();
	BaseClientFactory clientFactory = getFactory(serviceModuel, "FinanceService");
		try {
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			list = client.getReconciliationOptionsList(loanId, currentDt);
		} catch (Exception e) {
			logger.error("获取可对账的期数选项失败", e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
			
		// 退款和其他分开对账
		List<ReconciliationOptionsView> resultList = new ArrayList<ReconciliationOptionsView>();
		if(null != list)
		{
			for(ReconciliationOptionsView view :list)
			{
				// 如果还没有到时间，不要显示出来给对账
//				if(isNotDueTime(view.getRepayDt(), currentDt))
//				{
//					continue;
//				}	
				
				// 如果是负数，
				if (waitReconciliationAmount < 0) {
					// 是退利息、退管理费、退其他费用的，展现
					if (view.getType()==2 && (view.getRefNum() == FinanceTypeEnum.TYPE_4.getType() || view.getRefNum() == FinanceTypeEnum.TYPE_5.getType() || view.getRefNum() == FinanceTypeEnum.TYPE_6.getType())) {
						resultList.add(view);
					}
				} else {
					if (!(view.getType()==2 && (view.getRefNum() == FinanceTypeEnum.TYPE_4.getType() || view.getRefNum() == FinanceTypeEnum.TYPE_5.getType() || view.getRefNum() == FinanceTypeEnum.TYPE_6.getType()))) {
						resultList.add(view);
					}
				}
			}
		}	
		
		// 输出
		outputJson(resultList, response);
	}

	/**
	 * 是否是未到时间的选项
	  * @param repayDt
	  * @param nowDate
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年8月3日 下午3:05:10
	 */
	private boolean isNotDueTime(String repayDt,String nowDate)
	{
		long repayDtlong = Long.valueOf(repayDt.split(" ")[0].replaceAll("[-\\s:]",""));
		long nowDatelong = Long.valueOf(nowDate.split(" ")[0].replaceAll("[-\\s:]","")); 
		
		if(repayDtlong>nowDatelong)
		{
			return true;
		}	
		
		return false;
	}
	/**
	 * 获取对账表详情（如2期对账单，或者其他里面的提前还款罚金项）
	 * 
	 * @Description: TODO
	 * @param pid
	 *            还款计划表中的主键
	 * @param type
	 *            1:还款计划表中的数据 2：即时发生计划表的数据
	 * @param currentDt
	 *            当前时间
	 * @param response
	 * @author: qiancong.Xian
	 * @date: 2015年3月13日 下午4:32:31
	 */
	@RequestMapping(value = "getReconciliationItem")
	public void getReconciliationItem(@RequestParam(value = "otherPid") String otherPid, @RequestParam(value = "pid", required = true) Integer pid, @RequestParam(value = "type", required = true) Integer type, @RequestParam(value = "currentDt", required = true) String currentDt, @RequestParam(value = "automaticReconciliationAmt", required = true) double automaticReconciliationAmt, HttpServletResponse response) {

		// 正常计划的对账单
		if (type == 1) {
			createPlanReconciliation(pid, type, currentDt, automaticReconciliationAmt, response);
		}
		// 即时发生的对账单
		else if (type == 2) {
			createInstantReconciliation(otherPid, type, currentDt, automaticReconciliationAmt, response);
		}
	}

	/**
	 * 构建正常还款计划对账单（一期）
	 * 
	 * @param pid
	 * @param type
	 * @param currentDt
	 * @param automaticReconciliationAmt
	 * @param response
	 * @author: qiancong.Xian
	 * @date: 2015年3月18日 下午2:36:56
	 */
	private void createInstantReconciliation(String otherPid, Integer type, String currentDt, double automaticReconciliationAmt, HttpServletResponse response) {
		// 保存结果,优先把负数的记录排在前面
		List<ReconciliationItem> itemList = new ArrayList<ReconciliationItem>();
		List<ReconciliationItem> itemList2 = new ArrayList<ReconciliationItem>();
		BaseClientFactory clientFactory = getFactory(serviceModuel, "FinanceService");
		try {
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			String pids[] = otherPid.split(",");// 跟前台约定好，使用，分割
			for (String pid : pids) {
				if (pid.length() > 0) {
					ReconciliationItem item = client.getReconciliationItem(type, Integer.parseInt(pid), currentDt);
					if(item.getRealtimePlan()<0)
					{
						itemList.add(item);
					}	
					else
					{
						itemList2.add(item);
					}
				}
			}
			
			itemList.addAll(itemList2);

		} catch (Exception e) {
			logger.error("获取对账表详情（如2期对账单，或者其他里面的提前还款罚金项）", e);
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
		outputJson(getInstantPlanJSONArray(itemList, automaticReconciliationAmt, type), response);
	}

	private JSONArray getInstantPlanJSONArray(List<ReconciliationItem> itemList, double automaticReconciliationAmt, int type) {
		JSONArray array = new JSONArray();
		if (null == itemList || itemList.size() == 0) {
			return array;
		}

		for (ReconciliationItem item : itemList) {
			automaticReconciliationAmt = addRow(array, item.getRealtimePlanName(), item.getRealtimePlan(), automaticReconciliationAmt, type, item.getRefPid(), item.getOperType(), item.getCycleNum());
		}

		for (ReconciliationItem item : itemList) {
			automaticReconciliationAmt = addRow(array, item.getRealtimePlanName() + "逾期利息", item.getRealtimePlan_yl(), automaticReconciliationAmt, type, item.getRefPid(), FinanceTypeEnum.getOverdueinterestType(item.getOperType()), item.getCycleNum());
			automaticReconciliationAmt = addRow(array, item.getRealtimePlanName() + "逾期罚息", item.getRealtimePlan_yf(), automaticReconciliationAmt, type, item.getRefPid(), FinanceTypeEnum.getOverduePenaltyType(item.getOperType()), item.getCycleNum());
		}

		// 构建合计列
		JSONObject obj = new JSONObject();
		obj.put("typeName", "合计"); // 名称
		obj.put("receivable", ""); // 应收
		obj.put("reconciliation", ""); // 实收
		obj.put("uncollected", ""); // 未收
		obj.put("remark", ""); // 备注
		obj.put("type", type); // 类型(即时或计划中)
		obj.put("refId", ""); // 对应的计划表或者即时表中的主键
		obj.put("detailType", ""); // 细节的类型 参考 FinanceTypeEnum.java 中定义的
		array.add(obj);

		return array;
	}

	/**
	 * 构建正常还款计划对账单（一期）
	 * 
	 * @param pid
	 * @param type
	 * @param currentDt
	 * @param automaticReconciliationAmt
	 * @param response
	 * @author: qiancong.Xian
	 * @date: 2015年3月18日 下午2:36:56
	 */
	private void createPlanReconciliation(Integer pid, Integer type, String currentDt, double automaticReconciliationAmt, HttpServletResponse response) {
		// 保存结果
		ReconciliationItem planItem = null;
		BaseClientFactory clientFactory = getFactory(serviceModuel, "FinanceService");
		try {
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			planItem = client.getReconciliationItem(type, pid, currentDt);
		} catch (Exception e) {
			logger.error("构建正常还款计划对账单（一期）失败", e);
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
		outputJson(getPlanJSONArray(planItem, automaticReconciliationAmt, type, pid), response);
	}

	private JSONArray getPlanJSONArray(ReconciliationItem planItem, double automaticReconciliationAmt, int type, int refId) {
		JSONArray array = new JSONArray();
		if (null == planItem) {
			return array;
		}

		// 后面的所有类型数字来源：FinanceTypeEnum.java
		automaticReconciliationAmt = addRows(array, "本金", planItem.getPrincipal(), automaticReconciliationAmt, type, refId, FinanceTypeEnum.TYPE_30.getType(), planItem.getCycleNum());
		automaticReconciliationAmt = addRows(array, "管理费", planItem.getMangCost(), automaticReconciliationAmt, type, refId, FinanceTypeEnum.TYPE_40.getType(), planItem.getCycleNum());
		automaticReconciliationAmt = addRows(array, "利息", planItem.getInterest(), automaticReconciliationAmt, type, refId, FinanceTypeEnum.TYPE_50.getType(), planItem.getCycleNum());
		automaticReconciliationAmt = addRows(array, "其他费用", planItem.getOtherCost(), automaticReconciliationAmt, type, refId, FinanceTypeEnum.TYPE_60.getType(), planItem.getCycleNum());
		automaticReconciliationAmt = addRows(array, "本金逾期利息", planItem.getPrincipal_yl(), automaticReconciliationAmt, type, refId, FinanceTypeEnum.getOverdueinterestType(FinanceTypeEnum.TYPE_30.getType()), planItem.getCycleNum());
		automaticReconciliationAmt = addRows(array, "本金逾期罚息", planItem.getPrincipal_yf(), automaticReconciliationAmt, type, refId, FinanceTypeEnum.getOverduePenaltyType(FinanceTypeEnum.TYPE_30.getType()), planItem.getCycleNum());
		automaticReconciliationAmt = addRows(array, "管理费逾期利息", planItem.getMangCost_yl(), automaticReconciliationAmt, type, refId, FinanceTypeEnum.getOverdueinterestType(FinanceTypeEnum.TYPE_40.getType()), planItem.getCycleNum());
		automaticReconciliationAmt = addRows(array, "管理费逾期罚息", planItem.getMangCost_yf(), automaticReconciliationAmt, type, refId, FinanceTypeEnum.getOverduePenaltyType(FinanceTypeEnum.TYPE_40.getType()), planItem.getCycleNum());
		automaticReconciliationAmt = addRows(array, "利息逾期利息", planItem.getInterest_yl(), automaticReconciliationAmt, type, refId, FinanceTypeEnum.getOverdueinterestType(FinanceTypeEnum.TYPE_50.getType()), planItem.getCycleNum());
		automaticReconciliationAmt = addRows(array, "利息逾期罚息", planItem.getInterest_yf(), automaticReconciliationAmt, type, refId, FinanceTypeEnum.getOverduePenaltyType(FinanceTypeEnum.TYPE_50.getType()), planItem.getCycleNum());
		automaticReconciliationAmt = addRows(array, "其他费用逾期利息", planItem.getOtherCost_yl(), automaticReconciliationAmt, type, refId, FinanceTypeEnum.getOverdueinterestType(FinanceTypeEnum.TYPE_60.getType()), planItem.getCycleNum());
		automaticReconciliationAmt = addRows(array, "其他费用逾期罚息", planItem.getOtherCost_yf(), automaticReconciliationAmt, type, refId, FinanceTypeEnum.getOverduePenaltyType(FinanceTypeEnum.TYPE_60.getType()), planItem.getCycleNum());

		// 构建合计列
		JSONObject obj = new JSONObject();
		obj.put("typeName", "合计"); // 名称
		obj.put("receivable", ""); // 应收
		obj.put("reconciliation", ""); // 实收
		obj.put("uncollected", ""); // 未收
		obj.put("remark", ""); // 备注
		
		obj.put("type", type); // 类型(即时或计划中)
		obj.put("refId", refId); // 对应的计划表或者即时表中的主键
		obj.put("cycleNum", planItem.getCycleNum()); // 细节的类型 参考 FinanceTypeEnum.java 中定义的
		
		array.add(obj);
		

		return array;
	}

	/**
	 * 构建对账单的行
	 * 
	 * @param type
	 * @param pid
	 * @param array
	 * @param name
	 * @param value
	 * @param automaticReconciliationAmt
	 *            可以用于自动对账的金额
	 * @return
	 * @author: qiancong.Xian
	 * @date: 2015年3月18日 上午10:02:45
	 */
	private double addRows(JSONArray array, String name, double value, double automaticReconciliationAmt, int type, int refId, int detailType, int cycleNum) {
		return addRow(array, name, value, automaticReconciliationAmt, type, refId, detailType, cycleNum);
	}

	private double addRow(JSONArray array, String name, double value, double automaticReconciliationAmt, int type, int refId, int detailType, int cycleNum) {
		// 没有金额，隐藏当前行
		if (value == 0 && !"合计".equals(name)) {
			return automaticReconciliationAmt;
		}
		// 实收金额
		double reconciliation = (automaticReconciliationAmt >= value) ? value : automaticReconciliationAmt;
		// 未收金额
		double uncollected = (value - reconciliation);
		
		// 如果是退款对账的话
		if(automaticReconciliationAmt<0)
		{
			reconciliation = (automaticReconciliationAmt <= value) ? value : automaticReconciliationAmt;
			uncollected = (value - reconciliation);
		}	
		

		JSONObject obj = new JSONObject();
		obj.put("typeName", name); // 名称
		obj.put("receivable", value); // 应收
		obj.put("reconciliation", reconciliation); // 实收
		obj.put("uncollected", uncollected); // 未收
		obj.put("remark", ""); // 备注
		obj.put("type", type); // 类型(即时或计划中)
		obj.put("refId", refId); // 对应的计划表或者即时表中的主键
		obj.put("detailType", detailType); // 细节的类型 参考 FinanceTypeEnum.java 中定义的
		obj.put("cycleNum", cycleNum); // 细节的类型 参考 FinanceTypeEnum.java 中定义的

		array.add(obj);

	/*	// 如果是退管理费，退利息，退其他费用, 不计算金额
		if(detailType ==4 || detailType==5 || detailType==6)
		{
			return automaticReconciliationAmt;
		}	*/
		
		return (automaticReconciliationAmt - reconciliation);
	}

	/**
	 * 保存对账
	 * 
	 * @Description: TODO
	 * @param dto
	 * @param response
	 * @author: qiancong.Xian
	 * @date: 2015年3月12日 下午4:41:23
	 */
	@RequestMapping(value = "saveReconciliation")
	public void saveReconciliation(double waitReconciliationAmount, int loanId, double availableReconciliationAmount, int receivablesVersion, int receivablesId, String currentDt, String typeName, String type, String refId, String detailType, String reconciliationAmt, String remark, String cycleNum, String uncollected, int loanInterestRecord, int loanMgrRecord, int loanOtherFee, HttpServletResponse response) {
		
		logger.info("保存财务对账:loanId["+loanId+"], receivablesId["+receivablesId+"], currentDt["+currentDt+"], typeName["+typeName+"], type["+type+"], refId["+refId+"], detailType["+detailType+"], reconciliationAmt["+reconciliationAmt+"], remark["+remark+"], cycleNum["+cycleNum+"], uncollected["+uncollected+"]");
		Json result = null;
		List<RepaymentReconciliationBean> list = null;
		try {
			list = createRepaymentReconciliationBeanList(loanId, receivablesId, currentDt, typeName, type, refId, detailType, reconciliationAmt, remark, cycleNum, uncollected);
		} catch (Exception e) {
			logger.error("保存财务对账失败", e);
			result = getFailedObj("-1", "存在不合法数据,请检查输入参数");
			outputJson(result, response);
			return;
		}

		// 未对账金额（财务收款对账完毕后剩余可用金额） , 退费之类的不从财务收款扣去，直接从财务流水记录添加记录
		//availableReconciliationAmount = availableReconciliationAmount + getRefundTotal(list);
		// 退费这里不管
		//正常情况下剩余金额小于0，或者退款情况下，剩余金额大于0， 对账金额超出了财务收款可以金额
		if (waitReconciliationAmount > 0 && availableReconciliationAmount < 0 || waitReconciliationAmount < 0 && availableReconciliationAmount > 0) {
			result = getFailedObj("-1", "对账金额超出了财务收款剩余可用金额，请检查!");
			outputJson(result, response);
			return;
		}	
		
		BaseClientFactory clientFactory = getFactory(serviceModuel, "FinanceService");
		try {

			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			int theResult = client.saveRepaymentReconciliation(list, receivablesVersion, availableReconciliationAmount, receivablesId, loanInterestRecord, loanMgrRecord, loanOtherFee);
			if (theResult == 0) {
				result = getSuccessObj("0", "对账成功");
			} else if (theResult == -1) {
				result = getFailedObj("-1", "保存失败，请联系管理员");
			} else if (theResult == 1) {
				result = getFailedObj("1", "财务收款数据已经发生变化，请重新加载");
			}

		} catch (Exception e) {
			logger.error("保存财务对账失败", e);
			result = getFailedObj("-1", "保存失败，请联系管理员");
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
	  * 获取退费的总金额（退管理费、退利息、退其他费用，以及对于的逾期罚息） 
	  * @param list
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年5月28日 下午8:10:41
	 */
	private double getRefundTotal(List<RepaymentReconciliationBean> list )
	{
		double returnAmt = 0;
		if(null != list)
		{
			for(RepaymentReconciliationBean bean:list)
			{
				// 当期对账下的所有细节明细
				List<RepaymentReconciliationDetailDTO> detailDTOList = bean.getDetaiList();

				if (null != detailDTOList) {
					for (RepaymentReconciliationDetailDTO detailDTO : detailDTOList) {

						// 细节对应的类型Type
						int detailType = detailDTO.getDetailType();
						// 如果是退管理费、退利息、退其他费用
						if (detailType == FinanceTypeEnum.TYPE_4.getType() || detailType == FinanceTypeEnum.TYPE_5.getType() || detailType == FinanceTypeEnum.TYPE_6.getType()) {
							// 累计对账金额
							returnAmt += detailDTO.getReconciliationAmt();
						}
						// 对应的逾期罚息
						// 如果是退管理费、退利息、退其他费用对应的逾期罚息
						if (detailType == FinanceTypeEnum.getOverduePenaltyType(FinanceTypeEnum.TYPE_4.getType()) || detailType == FinanceTypeEnum.getOverduePenaltyType(FinanceTypeEnum.TYPE_5.getType()) || detailType == FinanceTypeEnum.getOverduePenaltyType(FinanceTypeEnum.TYPE_6.getType())) {
							// 累计对账金额
							returnAmt += detailDTO.getReconciliationAmt();
						}
					}
				}
			}
		}
		
		return returnAmt;
	}
	
	/**
	 * @Description: 构建保存财务对账，service需要的数据结构
	 * @param typeName
	 *            对账细节中午名称
	 * @param type
	 * @param refId
	 * @param detailType
	 * @param reconciliationAmt
	 * @param remark
	 * @return
	 * @author: qiancong.Xian
	 * @date: 2015年3月19日 上午11:40:29
	 */
	private List<RepaymentReconciliationBean> createRepaymentReconciliationBeanList(int loanId, int receivablesId, String currentDtStr, String typeNameStr, String typeStr, String refIdStr, String detailTypeStr, String reconciliationAmtStr, String remarkStr, String cycleNumStr, String uncollectedStr) {

		// 调用service的数据结构
		List<RepaymentReconciliationBean> repaymentReconciliationBeanList = new ArrayList<RepaymentReconciliationBean>();
		String[] typeNames = typeNameStr.split(",");
		String[] types = typeStr.split(",");
		String[] refIds = refIdStr.split(",");
		String[] detailTypes = detailTypeStr.split(",");
		String[] reconciliationAmts = reconciliationAmtStr.split(",");
		String[] remarks = remarkStr.split(",");
		String[] cycleNums = cycleNumStr.split(",");
		String[] uncollecteds = uncollectedStr.split(",");
		String[] currentDt = currentDtStr.split(",");

		int crefId = 0; // 当前遍历的
		int userId = getShiroUser().getPid();
		RepaymentReconciliationBean repaymentReconciliationBean = null;
		 Map<String,Integer> reconciliationTypeMap =  getReconciliationType(refIds, uncollecteds); // 对账完成情况
		for (int i = 0; i < typeNames.length; i++) { // 没有数据，最后一个是空的
			if (refIds[i].trim().length() == 0) {
				continue;
			}

			int type = Integer.parseInt(types[i]); // 还款细节对应的类型：计划或即时
			String typeName = typeNames[i]; // 款细节对应的 中文名称
			int refId = Integer.parseInt(refIds[i]); // 款细节对应的 还款引用主键
			int detailType = Integer.parseInt(detailTypes[i]); // 款细节对应的 类型
			String remark = remarks[i]; // 款细节对应的 备注
			int cycleNum = Integer.parseInt(cycleNums[i]); // 还款计划表对应的期数
			double reconciliationAmt = Double.parseDouble(reconciliationAmts[i]);// 款细节对应的已收
			double uncollected = Double.parseDouble(uncollecteds[i]);// 款细节对应的
																		// 未对账金额
			// 如果当前细节没有对账，跳过,并且不是费用减免的项
			if (uncollected !=0 && reconciliationAmt == 0 && detailType != FinanceTypeEnum.TYPE_100.getType()) {
				
				continue;
			}

			// 如果还款计划表不一样了，新生产一个对象
			if (crefId != refId) {
				repaymentReconciliationBean = getNewRepaymentReconciliationBean(loanId, receivablesId, currentDt[i], refId, cycleNum, type, userId);
				// 设置对账完成情况
				repaymentReconciliationBean.setReconciliationType(reconciliationTypeMap.get(String.valueOf(refId)));
				
				repaymentReconciliationBeanList.add(repaymentReconciliationBean);
				crefId = refId;
			}

			addDeailToRepaymentReconciliationBean(repaymentReconciliationBean, reconciliationAmt, uncollected, detailType, remark, typeName,userId);
		}

		return repaymentReconciliationBeanList;
	}
	
	/**
	 * 计算还款当前的状态：  1=已对账(全部完成)，2=未对账(未开始)，3=部分对账(部分完成);
	  * @Description: TODO
	  * @param refIds
	  * @param uncollecteds
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年5月15日 下午5:14:50
	 */
	private Map<String,Integer> getReconciliationType(String[] refIds,String[] uncollecteds)
	{
		Map<String,Integer> map = new HashMap<String,Integer>();

		String cRefId = null;
		int type = 1; // 默认对账完成
		 for(int i=0;i<refIds.length;i++)
		 {
			 // 新的
			 if(!refIds[i].equals(cRefId))
			 {
				 map.put(refIds[i], type); 
				 cRefId = refIds[i];
			 }		 
			 
			 try
			 {
				 // 如果有没有对账的，修改为部分对账
				 if(Double.parseDouble(uncollecteds[i])>0)
				 {
					 map.put(refIds[i], 3); 
				 }	
			 }
			 catch(Exception e)
			 {
				 
			 }
		 }
		
		return map;
				
	}

	private void addDeailToRepaymentReconciliationBean(RepaymentReconciliationBean bean, double reconciliationAmt, double uncollected, int detailType, String description, String detailTypeName, int userId) {
		// 对应的对账单记录
		RepaymentReconciliationDTO dto = bean.getRepaymentReconciliation();
		dto.setReconciliationAmt(dto.getReconciliationAmt() + reconciliationAmt);

		RepaymentReconciliationDetailDTO detail = new RepaymentReconciliationDetailDTO();
		detail.setDetailType(detailType); // 对账项类型，参考FinanceTypeEnum.java 中定义的
		detail.setReconciliationAmt(reconciliationAmt); // 对账金额
		detail.setTotalAmt(reconciliationAmt+uncollected); // 应收金额 = 已对账金额+未对账金额
		detail.setDescription(description); // 对账描述
		detail.setDetailTypeName(detailTypeName); // 对应的类型名称
		detail.setCreateDate(DateUtil.format(DateUtil.getToday()));
		detail.setCreateUser(String.valueOf(userId));
		bean.getDetaiList().add(detail);
	}

	/**
	 * 构建一个新的对账单对账记录
	 * 
	 * @param loanId
	 * @param receivablesId
	 * @param currentDt
	 * @param refId
	 * @param cycleNum
	 * @param type
	 * @return
	 * @author: qiancong.Xian
	 * @date: 2015年3月19日 下午1:51:28
	 */
	private RepaymentReconciliationBean getNewRepaymentReconciliationBean(int loanId, int receivablesId, String currentDt, int refId, int cycleNum, int type, int userId) {
		RepaymentReconciliationBean bean = new RepaymentReconciliationBean();
		bean.setType(type);
		bean.setRefId(refId);

		RepaymentReconciliationDTO dto = new RepaymentReconciliationDTO();
		dto.setLoanId(loanId);
		dto.setType(type);
		dto.setRealtimeId(1 == type ? -1 : refId);
		dto.setCycleNum(cycleNum);
		dto.setReconciliationDt(currentDt);
		dto.setReconciliationAmt(0);
		dto.setReceivablesId(receivablesId);
		bean.setRepaymentReconciliation(dto);
		dto.setUserId(userId);
		dto.setCreateUser(String.valueOf(userId));
		dto.setCreateDate(DateUtil.format(DateUtil.getToday()));

		List<RepaymentReconciliationDetailDTO> detailDTOList = new ArrayList<RepaymentReconciliationDetailDTO>();
		bean.setDetaiList(detailDTOList);

		return bean;
	}

	/**
	 * 客户批量还款处理页面
	 */
	@RequestMapping(value = "acctBatchRepayment")
	public String acctBatchRepayment(ModelMap model, HttpServletResponse response) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		model.put("currDate", sdf.format(new Date()));
		return finance + "acctBatchRepayment";
	}

	/**
	 * 客户批量还款处理页面
	 */
	@RequestMapping(value = "listAcctBatchRepayment")
	@ResponseBody
	public void listAcctBatchRepayment(FinanceBusinessCondition financeBusinessCondition, HttpServletResponse response) throws Exception {

		BaseClientFactory clientFactory = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = sdf.format(new Date());
		// 初始化查询日期
		if (financeBusinessCondition.getExpireStartDt() == null && financeBusinessCondition.getExpireEndDt() == null) {
			financeBusinessCondition.setExpireEndDt(currentDate);
			financeBusinessCondition.setExpireStartDt(currentDate);
		}

		Map<String, Object> map = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");
			map = new HashMap<String, Object>();

			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			List<BatchRepaymentView> list = client.getAcctBatchRepayment(financeBusinessCondition);

			for (BatchRepaymentView view : list) {
				view.setActualReceDt(currentDate);
			}

			int total = client.countAcctBatchRepayment(financeBusinessCondition);
			map.put("rows", list);
			map.put("total", total);
		} catch (Exception e) {
			logger.error("客户批量还款处理页面失败",e);
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
	 * 预览批量还款结果
	 * 
	 * @param pid
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 * @author: qiancong.Xian
	 * @date: 2015年3月30日 上午10:34:46@RequestParam(value = "para", required = true) String para, 
	 */
	@RequestMapping(value = "previewBatchRepaymentResult")
	public String previewBatchRepaymentResult(HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception {
//		String para = java.net.URLDecoder.decode(request.getParameter("para"), "UTF-8").trim();  
//		JSONArray array = JSONArray.parseArray(para);
//		model.put("resultList", array);
		return finance + "previewBatchRepaymentResult";
	}

	/**
	 * 获取批量对账的一项
	 * 
	 * @param loanId
	 *            贷款ID
	 * @param currentDt
	 *            收款时间
	 * @param amount
	 *            实收金额
	 * @param response
	 * @author: qiancong.Xian
	 * @date: 2015年3月28日 上午10:33:18
	 */
	@RequestMapping(value = "getBatchRepaymentItem")
	public void getBatchRepaymentItem(@RequestParam(value = "loanId", required = true) Integer loanId, @RequestParam(value = "currentDt", required = true) String currentDt, @RequestParam(value = "amount", required = true) double amount, HttpServletResponse response) {
		// 保存结果
		List<BatchRepaymentItem> list = new ArrayList<BatchRepaymentItem>();
		BaseClientFactory clientFactory = getFactory(serviceModuel, "FinanceService");
		try {
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			list = client.getBatchRepaymentLoanItemList(loanId, currentDt, amount);
		} catch (Exception e) {
			logger.error("获取批量对账的一项失败",e);
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
		outputJson(list, response);
	}

	/**
	 * 取小数点后2位
	  * @Description: TODO
	  * @param value
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年5月23日 下午5:45:46
	 */
	private double getFomatValue(double value)
	{
		return ((int)(value*100))/100.0;
	}
	
	/**
	 * 保存批量对账
	  * @param paramStr
	  * @param response
	  * @author: qiancong.Xian
	  * @date: 2015年7月7日 下午2:19:25
	 */
	@RequestMapping(value = "saveBatchRepayment")
	public void saveBatchRepayment(@RequestParam(value = "paramStr", required = true) String paramStr, HttpServletResponse response) {
		Json result = this.getSuccessObj();
		List<BatchRepaymentBean> batchRepaymentItemList = new ArrayList<BatchRepaymentBean>();
		JSONArray array = JSONArray.parseArray(paramStr);
		for (int i = 0; i < array.size(); i++) {
			// 对象
			JSONObject json = array.getJSONObject(i);
			BatchRepaymentBean bean = new BatchRepaymentBean();
			batchRepaymentItemList.add(bean);
			bean.setLoanId(json.getIntValue("loanId"));
			bean.setReceiptDate(json.getString("currentDt"));// 收款时间
			bean.setAmount(getFomatValue(json.getDoubleValue("amount")));// 收款总金额
			bean.setRemainingAmt(getFomatValue(json.getDoubleValue("remainingAmt")));
			// 细节
			List<BatchRepaymentItem> details = new ArrayList<BatchRepaymentItem>();
			bean.setDetails(details);
			JSONArray detailsArray = json.getJSONArray("details"); // 细节Array
			for (int j = 0; j < detailsArray.size(); j++) {
				JSONObject detailJson = detailsArray.getJSONObject(j);
				BatchRepaymentItem item = new BatchRepaymentItem();
				item.setRefPid(detailJson.getIntValue("refPid"));
				item.setSType(detailJson.getIntValue("sType"));
				item.setLoanId(detailJson.getIntValue("loanId"));
				item.setFinanceType(detailJson.getIntValue("financeType"));
				item.setBalanceAmt(getFomatValue(detailJson.getDoubleValue("balanceAmt")));
				item.setTotalAmt(getFomatValue(detailJson.getDoubleValue("totalAmt")));
				details.add(item);
			}
		}

		BaseClientFactory clientFactory = getFactory(serviceModuel, "FinanceService");
		try {
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			client.saveBatchRepayment(batchRepaymentItemList, getShiroUser().getPid());

		} catch (Exception e) {
			result = this.getFailedObj("保存批量对账失败，请重新加载数据或联系管理员");
			logger.error("批量对账保存失败", e);
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

	// add by yql
	/**
	 * 
	 * @Description: TODO多付金额转入客户余额处理
	 * @param pid
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 * @author: yql
	 * @date: 2015年3月23日 上午11:57:43
	 */
	@RequestMapping(value = "getAcctProjectBalance")
	public String getAcctProjectBalance(int loanId, int receivablesId, HttpServletResponse response, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = null;
		// 查询账户信息
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			// 查找客户名称，客户可用余额
			AcctProjectBalanceView acct = client.getAcctProjectBalanceByLoanId(loanId);
			AcctProjectBalanceView balance = client.getBalanceByReceId(receivablesId);
			model.put("acct", acct);
			model.put("balance", balance);
		} catch (Exception e) {
			logger.error("付金额转入客户余额处理失败",e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		return finance + "toBalanceAcct";
	}

	/**
	 * 多付金额转入客户余额处理
	 * 
	 * @Description: TODO
	 * @param dto
	 * @param response
	 * @author: yql
	 * @date: 2015年3月12日 下午4:41:23
	 */
	@RequestMapping(value = "saveAcctProjectBalance")
	@ResponseBody
	public void saveAcctProjectBalance(AcctProjectBalanceDTO dto, HttpServletResponse response) {

		int result = 0;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(serviceModuel, "FinanceService");

			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			if (dto != null) {
				dto.setBalanceType(1);
			}
			result = client.addAcctProjectBalance(dto);

		}catch (Exception e) {
			logger.error("多付金额转入客户余额处理失败",e);
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
	 * @Description: TODO多付金额转入客户余额处理跳转页面
	 * @param pid
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 * @author: yql
	 * @date: 2015年3月23日 上午11:57:43
	 */
	@RequestMapping(value = "getReturnAmtInfoList")
	public String getReturnAmtInfoList(int loanId, int receivablesId, HttpServletResponse response, ModelMap model) throws Exception {
		model.put("loanId", loanId);
		model.put("receivablesId", receivablesId);
		return finance + "returnAmtManage";
	}

	/**
	 * 
	 * @Description: TODO 多付金额退还处理
	 * @param dto
	 * @param response
	 * @author: yql
	 * @date: 2015年3月12日 下午4:41:23
	 */
	@RequestMapping(value = "getReturnAmtInfoListUrl")
	public void getReturnAmtInfoListUrl(int loanId, AcctProjectBalanceDTO dto, HttpServletResponse response) {

		BaseClientFactory clientFactory = null;
		List<LoanRefundView> list = null;
		try {
			clientFactory = getFactory(serviceModuel, "FinanceService");

			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			list = client.getLoanRefundList(loanId);
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("多付金额退还处理失败",e);
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
		outputJson(list, response);

	}

	/**
	 * 
	 * @Description: TODO多付金额转入客户余额处理
	 * @param pid
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 * @author: yql
	 * @date: 2015年3月23日 上午11:57:43
	 */
	@RequestMapping(value = "getReturnAmtInfo")
	public String getReturnAmtInfo(int loanId, int receivablesId, HttpServletResponse response, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = null;
		BaseClientFactory clientFactoryPro = null;
		// 查询账户信息
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");
			clientFactoryPro = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BeforloadOutputSerice");
			
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			BeforloadOutputSerice.Client clientPro = (BeforloadOutputSerice.Client) clientFactoryPro.getClient();
			// 可退余额
			AcctProjectBalanceView balance = client.getBalanceByReceId(receivablesId);
			// 客户账户余额
			AcctProjectBalanceView acct = client.getAcctProjectBalanceByLoanId(loanId);

			Project loanProject= new Project();
			loanProject.setPid(acct.getProjectId());
			LoanOutputInfoImpl loan = clientPro.getLoadOutputListImpl(loanProject);
			model.put("cusAcctBankView", loan!=null?loan:new LoanOutputInfoImpl());
			model.put("balance", balance);
			model.put("loanId", loanId);
			model.put("receivablesId", receivablesId);
			model.put("acct", acct);
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("多付金额转入客户余额处理失败",e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
			if (clientFactoryPro != null) {
				try {
					clientFactoryPro.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		return finance + "addReturnAmt";
	}

	/**
	 * 
	 * @Description: TODO 保存多付金额退还处理
	 * @param pid
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 * @author: yql
	 * @date: 2015年3月23日 上午11:57:43
	 */
	@RequestMapping(value = "saveReturnAmt")
	@ResponseBody
	public void saveReturnAmt(LoanRefundDTO loanRefundDTO, HttpServletResponse response, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = null;
		// 查询账户信息
		int result = 0;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			if (loanRefundDTO != null) {
				ShiroUser shiroUser = super.getShiroUser();
				loanRefundDTO.setRefundUserId(shiroUser.getPid());
			}
			result = client.addLoanRefund(loanRefundDTO);

		} catch (Exception e) {
			logger.error("保存多付金额退还处理失败",e);
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
	 * 未对账项目查询跳转
	 */
	@RequestMapping(value = "/getListUnReconciliation")
	public String getListUnReconciliation(ModelMap model) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		model.put("currDate", sdf.format(new Date()));
		return finance + "list_un_reconciliation";
	}

	/**
	 * 未对账项目查询数据
	 * 
	 * @param cusPerBaseDTO
	 * @param response
	 */
	@RequestMapping(value = "/getListUnReconciliationUrl")
	@ResponseBody
	public void getListUnReconciliationUrl(UnReconciliationCondition unReconciliationCondition, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<UnReconciliationView> unReconciliationList = null;
		BaseClientFactory clientFactory = null;
		try {
			String receiveStartDt=null;
			String receiveEndDt=null;
			clientFactory = getFactory(serviceModuel, "FinanceService");
			if(StringUtil.isEmpty(unReconciliationCondition.getReceiveStartDt())){
				receiveStartDt=DateUtil.format(new Date(), "yyyy-MM-dd");
				unReconciliationCondition.setReceiveStartDt(receiveStartDt);
			}
			if(StringUtil.isEmpty(unReconciliationCondition.getReceiveEndDt())){
				receiveEndDt=DateUtil.format(new Date(), "yyyy-MM-dd");
				unReconciliationCondition.setReceiveEndDt(receiveEndDt);
			}
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();
			unReconciliationList = client.getListUnReconciliation(unReconciliationCondition);
			int total = client.countUnReconciliation(unReconciliationCondition);
			map.put("rows", unReconciliationList);
			map.put("total", total);
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("未对账项目查询数据失败",e);
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
	 * @Description: TODO 批量处理退款，和转入余额
	 * @param pid
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 * @author: yql
	 * @date: 2015年3月23日 上午11:57:43
	 */
	@RequestMapping(value = "batchReturnBalanceAmt")
	@ResponseBody
	public void batchReturnBalanceAmt(String balance, String returnAmt, HttpServletResponse response, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = null;
		BaseClientFactory clientFactoryCus = null;
		// 查询账户信息
		int result = 0;
		try {
			clientFactoryCus = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusAcctService");
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");
			CusAcctService.Client clientCus = (CusAcctService.Client) clientFactoryCus.getClient();
			FinanceService.Client client = (FinanceService.Client) clientFactory.getClient();

			ShiroUser shiroUser = super.getShiroUser();
			// 批量处理退款
			if (returnAmt != null && !"".equals(returnAmt)) {
				String[] inputIds = returnAmt.split(",");
				for (int i = 0; i < inputIds.length; i++) {
					String inputIdstr = inputIds[i];
					int inputId = Integer.parseInt(inputIdstr);
					UnReconciliationView unReconciliationView = client.findUnReconciliationInfo(inputId);
					LoanRefundDTO loanRefundDTO = new LoanRefundDTO();
					loanRefundDTO.setActualRefundAmt(unReconciliationView.getUnReconciliationAmt());
					loanRefundDTO.setRefundDifferenceAmt(0.0);
					loanRefundDTO.setRefundDt(DateUtil.format(new Date(), "yyyy-MM-dd"));

					// 查询客户银行账户
					CusAcctBank cusAcctBank = new CusAcctBank();
					cusAcctBank.setAccUse(159);
					CusAcct cusAcct = new CusAcct();
					cusAcct.setPid(unReconciliationView.getAcctId());
					cusAcctBank.setCusAcct(cusAcct);
					CusAcctBankView cusAcctBankView = clientCus.getCusBankByAcctId(cusAcctBank);

					loanRefundDTO.setRefundBankId(cusAcctBankView.getLoanCardId());
					loanRefundDTO.setProjectId(unReconciliationView.getProjectId());
					loanRefundDTO.setPayableRefundAmt(unReconciliationView.getUnReconciliationAmt());
					loanRefundDTO.setRefundUserId(shiroUser.getPid());
					loanRefundDTO.setDateVersion(unReconciliationView.getDataVersion());
					loanRefundDTO.setReceivablesId(inputId);
					result = client.addLoanRefund(loanRefundDTO);
				}
			}
			// 批量处理转余额
			if (balance != null && !"".equals(balance)) {
				String[] inputIds = balance.split(",");
				for (int i = 0; i < inputIds.length; i++) {
					String inputIdstr = inputIds[i];
					int inputId = Integer.parseInt(inputIdstr);
					// 未对账的一条数据的详细信息
					UnReconciliationView unReconciliationView = client.findUnReconciliationInfo(inputId);

					AcctProjectBalanceDTO acctProjectBalanceDTO = new AcctProjectBalanceDTO();
					acctProjectBalanceDTO.setBalanceType(1);
					acctProjectBalanceDTO.setAcctId(unReconciliationView.getAcctId());// 客户id
					acctProjectBalanceDTO.setProjectId(unReconciliationView.getProjectId());// 项目id
					acctProjectBalanceDTO.setBalanceAmt(unReconciliationView.getUnReconciliationAmt());//
					acctProjectBalanceDTO.setBalanceDt(DateUtil.format(new Date(), "yyyy-MM-dd"));
					acctProjectBalanceDTO.setDateVersion(unReconciliationView.getDataVersion());
					acctProjectBalanceDTO.setReceivablesId(inputId);
					result = client.addAcctProjectBalance(acctProjectBalanceDTO);
				}
			}

		} catch (Exception e) {
			logger.error("批量处理退款，和转入余额失败",e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
			if (clientFactoryCus != null) {
				try {
					clientFactoryCus.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}

		}
		// 输出
		outputJson(result, response);
	}
	//打开费用减免页面
	/**
	 * 
	  * @Description: TODO
	  * @param loanId
	  * @param model
	  * @param response
	  * @param request
	  * @return
	  * @throws Exception
	  * @author: yequnli
	  * @date: 2015年9月15日 上午10:36:34
	 */
	
	@RequestMapping(value = "handFeewdtl")
	public void handFeewdtl(int loanId,ModelMap model, HttpServletResponse response, HttpServletRequest request) throws Exception {
		BaseClientFactory clientFactory = null;
		BaseClientFactory clientFactoryF = null;
		BaseClientFactory clientFactorySys=null;
		int result=0;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_REPAYMENT, "RepayFeewdtlService");
			RepayFeewdtlService.Client client = (RepayFeewdtlService.Client) clientFactory.getClient();
			//插入费用减免数据，插入费用减免明细表数据
			
			clientFactoryF = getFactory(BusinessModule.MODUEL_FINANCE, "FinanceService");
			FinanceService.Client clientF = (FinanceService.Client) clientFactoryF.getClient();
			
			clientFactorySys = this.getFactory(BusinessModule.MODUEL_SYSTEM, "SysConfigService");
			
			SysConfigService.Client clientSys = (SysConfigService.Client) clientFactorySys.getClient();
//			double shouldInterestP=Double.parseDouble(shouldInterestStr);
			Map<String,Object> repayFeewdtlDatDTO= new HashMap<String,Object>();
		
			LoanCycleNumView res=clientF.getLoanCycleNumAndAmt(loanId);
			if(res!=null ){
				 
			
				int planCycleNum=res.getPlanCycleNum();
				double shouldInterest=res.getOverdueAmt();
						//clientF.getLoanOverdueAmt(loanId);//到期未收金额
				
			    String  value=clientSys.getSysConfigValueByName("AMT_LIMIT");
				if(res.getPlanCycleNum()<=0){
					result=-1;//查询余额失败
				}else if(shouldInterest> Double.parseDouble(value)){
					result=-2;//剩余的金额大于5元，不可以执行减免操作
				}else{
					List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
					//repayFeewdtlDatDTO.put("repaymentId", value);//
					repayFeewdtlDatDTO.put("planCycleNum", planCycleNum);//期数 需查询最大的有效的期数，有利息的
					repayFeewdtlDatDTO.put("shouldOtherCost",0);//其他费用
					repayFeewdtlDatDTO.put("shouldMangCost",0);//管理费用
					repayFeewdtlDatDTO.put("shouldInterest",shouldInterest);//减免利息，页面传值
					repayFeewdtlDatDTO.put("overdueInterest",0);//预期利息
					repayFeewdtlDatDTO.put("overdueFine",0);//逾期罚息
					repayFeewdtlDatDTO.put("typeName","减免");//逾期罚息				
					list.add(repayFeewdtlDatDTO);
					int projectId=-1;
					String reason="系统平账减免";
					
					String feewdel=JSONUtils.toJSONString(list).toString();//json串
					result=client.insertLoanFeewdelInfo(reason, feewdel, projectId, loanId);
					if(result>0){//把还款计划表的数据改为已对账
						int rPid=res.getRpId();
						clientF.updateRepaymentPlan(rPid);
					}
				}
			}else{
				result=-1;//查询余额失败
			}
				
			
		}  catch (Exception e) {
			logger.error("打开财务费用减免页面失败----------", e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
					clientFactorySys.destroy();
					clientFactoryF.destroy();
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
	  * @Description: TODO 把未对账金额转入公司收入
	  * @param loanId
	  * @param model
	  * @param response
	  * @param request
	  * @return
	  * @throws Exception
	  * @author: yequnli
	  * @date: 2015年9月15日 上午10:36:34
	 */
	
	@RequestMapping(value = "insertLoanRecInto")
	public void insertLoanRecInto(int loanId,ModelMap model, HttpServletResponse response, HttpServletRequest request) throws Exception {
		BaseClientFactory clientFactory = null;
		BaseClientFactory clientFactorySys = null;
		int result=0;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE, "LoanRecIntoService");
			LoanRecIntoService.Client clientL = (LoanRecIntoService.Client) clientFactory.getClient();
			 clientFactorySys = this.getFactory(BusinessModule.MODUEL_SYSTEM, "SysConfigService");
				
				SysConfigService.Client clientSys = (SysConfigService.Client) clientFactorySys.getClient();
			//查询收款id，和金额
			List<AcctProjectBalanceView>  listb=clientL.getBalanceByLoanId(loanId);
			//插入财务对账余额转入收入表
			if(listb!=null && listb.size()>0){
				
				  String  value=clientSys.getSysConfigValueByName("AMT_LIMIT");
				for(AcctProjectBalanceView bView: listb){
					if(bView!=null){
						if(bView.getBalanceAmt()<=0){
							result=-1;//
						}else{
							if(bView.getBalanceAmt()>Double.parseDouble(value)){
								result=-2;//剩余的金额大于5元，不可以执行减免操作
							}else{
								LoanRecIntoDTO  loanRecInto = new LoanRecIntoDTO();
								loanRecInto.setActualIntoAmt(bView.getBalanceAmt());
								loanRecInto.setLoanId(loanId);
								loanRecInto.setReceivablesId(bView.getReceivablesId());
								loanRecInto.setUserId(getShiroUser().getPid());
								loanRecInto.setRemark("多余金额转入收入");
								result=clientL.insertLoanRecInto(loanRecInto);
								
							}
						}
						
					}else{
						result=-1;//没有可转入的余额
					}
					
					
				}
			}else{
				result=-1;//失败
			}
			
		}  catch (Exception e) {
			logger.error("打开财务费用减免页面失败----------", e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
					clientFactorySys.destroy();
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
	  * @Description: TODO 财务对账反核销 方法
	  * @param pid
	  * @param response
	  * @throws Exception
	  * @author: yequnli
	  * @date: 2015年10月20日 下午3:46:02
	 */
	@ResponseBody
	@RequestMapping(value = "antiVerification")
	public void antiVerification( int loanId,int inputId,HttpServletResponse response) throws Exception {
//	  Json result = this.getSuccessObj();
		BaseClientFactory clientFactory = null;
		int result=0;
	  try {
			clientFactory = getFactory(BusinessModule.MODUEL_FINANCE, "LoanRecIntoService");
			LoanRecIntoService.Client client = (LoanRecIntoService.Client) clientFactory.getClient();
			result=client.antiVerification(loanId, inputId, getShiroUser().getPid());
			
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
	// add by yql end
}
