/**
 * @Title: RepayFeewdtlServiceImpl.java
 * @Package com.xlkfinance.bms.server.repayment.service
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: achievo
 * @date: 2015年3月26日 下午5:18:12
 * @version V1.0
 */
package com.xlkfinance.bms.server.repayment.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.achievo.framework.util.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlkfinance.bms.common.constant.WorkflowIdConstant;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.finance.FinanceService;
import com.xlkfinance.bms.rpc.finance.ReconciliationItem;
import com.xlkfinance.bms.rpc.repayment.FeeWaiverApplicationDTO;
import com.xlkfinance.bms.rpc.repayment.RegAdvapplyFileview;
import com.xlkfinance.bms.rpc.repayment.RepayFeewdtlDatDTO;
import com.xlkfinance.bms.rpc.repayment.RepayFeewdtlDatView;
import com.xlkfinance.bms.rpc.repayment.RepayFeewdtlService.Iface;
import com.xlkfinance.bms.rpc.repayment.UploadinstAdvapplyBaseDTO;
import com.xlkfinance.bms.rpc.workflow.WorkflowService;
import com.xlkfinance.bms.server.afterloan.mapper.AfterLoanDivertMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanMapper;
import com.xlkfinance.bms.server.repayment.mapper.RepayFeewdtlMapper;
import com.xlkfinance.bms.server.repayment.mapper.RepaymentMapper;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDispose;

@SuppressWarnings("unchecked")
@Service("repayFeewdtlService")
@ThriftService(service = "com.xlkfinance.bms.rpc.repayment.RepayFeewdtlService")
public class RepayFeewdtlServiceImpl extends WorkflowSpecialDispose implements Iface {

	@SuppressWarnings("rawtypes")
	@Resource(name = "repaymentMapper")
	private RepaymentMapper repaymentMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "repayFeewdtlMapper")
	private RepayFeewdtlMapper repayFeewdtlMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "afterLoanDivertMapper")
	private AfterLoanDivertMapper afterLoanDivertMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "loanMapper")
	private LoanMapper loanMapper;
	
	@Resource(name = "workflowServiceImpl")
	private WorkflowService.Iface workflowServiceImpl;
	
	@Resource(name = "financeServiceImpl")
	private FinanceService.Iface financeServiceImpl;
	

	/**
	  * @Description: 插入费用减免信息
	  * @param repayfeew
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:23:55
	  */
	@Override
	public int insertLoanFeewdelInfo(String reason, String datViews, int projectId, int loanId) throws  TException {
		RepayFeewdtlDatDTO repayfeew = new RepayFeewdtlDatDTO();
		repayfeew.setLoanId(loanId);
		// 默认是等待申请
		repayfeew.setRequestStatus(1);
//		Date completeDttm=null;
		if(-1== projectId)
		{
			projectId = loanMapper.getProjectIdByLoanId(loanId);
			// 如果是财务对账那边过来的，直接生效
			repayfeew.setRequestStatus(4);
//			completeDttm= new Date();
			repayfeew.setCompleteDttm(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		}
		repayfeew.setProjectId(projectId);
		repayfeew.setReason(reason);
		repayFeewdtlMapper.insertLoanFeewdelInfo(repayfeew);
		
		JSONArray array = JSONArray.parseArray(datViews);
		//List<RepayFeewdtlDatDTO> feewdtlDatDTOs = new ArrayList<RepayFeewdtlDatDTO>();
		//更新的行数
		for (int i = 0; i < array.size(); i++) {
			if(i<array.size()){
				JSONObject json = array.getJSONObject(i);
				String typeName = json.getString("typeName");
				if("减免".equals(typeName)){
					RepayFeewdtlDatDTO feewdtlDatDTO = new RepayFeewdtlDatDTO();
					feewdtlDatDTO.setRepaymentId(convertInt(json.getString("repaymentId")));
					feewdtlDatDTO.setRepayId(repayfeew.getPId());
					feewdtlDatDTO.setPlanCycleNum(convertInt(json.getString("planCycleNum")));
					feewdtlDatDTO.setShouldOtherCost(convertDouble(json.getString("shouldOtherCost")));
					feewdtlDatDTO.setShouldMangCost(convertDouble(json.getString("shouldMangCost")));
					feewdtlDatDTO.setShouldInterest(convertDouble(json.getString("shouldInterest")));
					feewdtlDatDTO.setOverdueLoanInterestAmt(convertDouble(json.getString("overdueInterest")));
					feewdtlDatDTO.setOverdueFineInterestAmt(convertDouble(json.getString("overdueFine")));
					feewdtlDatDTO.setReason(reason);
					
					repayFeewdtlMapper.insertLoanFeewInfo(feewdtlDatDTO);
				}
			}
		}
		//是否有启动的流程   状态(0：没有流程启动、1：利率变更、2：提前还款、3：费用减免、4：展期、5：违约、6：挪用)
		updateWorkflowStatus(projectId,3);
		return repayfeew.getPId();
	}

	/**
	  * @Description: 保存上传文件
	  * @param uploadinstAdvapplyBaseDTO
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:25:20
	  */
	@Override
	public int uploadinstFeewapply(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO) throws ThriftServiceException, TException {
		repayFeewdtlMapper.insertFeewfileInfo(uploadinstAdvapplyBaseDTO);
		uploadinstAdvapplyBaseDTO.setFileId(uploadinstAdvapplyBaseDTO.getPId());
		int st = repayFeewdtlMapper.insertLoanFeewfileInfo(uploadinstAdvapplyBaseDTO);
		return st;
	}

	/**
	  * @Description: 查询文件列表
	  * @param map
	  * @return List<RegAdvapplyFileview>
	  * @author: JingYu.Dai
	  * @date: 2015年6月5日 上午10:33:59
	  */
	@Override
	public List<RegAdvapplyFileview> queryRegFeewapplyFile(int repayId,int page,int rows) throws ThriftServiceException, TException {
		Map<String ,Integer> map = new HashMap<String, Integer>();
		map.put("repayId", repayId);
		map.put("page", page);
		map.put("rows", rows);
		List<RegAdvapplyFileview> list = repayFeewdtlMapper.queryRegFeewapplyFile(map);
		if (null != list) {
			return list;
		}
		return new ArrayList<RegAdvapplyFileview>();
	}

	/**
	  * @Description: 查询文件列表总记录条数
	  * @param repayId
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年6月5日 上午10:35:08
	 */
	@Override
	public int queryRegFeewapplyFileTotal(int repayId) throws ThriftServiceException, TException {
		return repayFeewdtlMapper.queryRegFeewapplyFileTotal(repayId);
	}
	/**
	  * @Description: 初始化流程任务信息费用减免表信息
	  * @param repayId
	  * @return
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:27:11
	  */
	@Override
	public List<RepayFeewdtlDatView> queryRegFeewDealbyprocess(int repayId) throws ThriftServiceException, TException {
		List<RepayFeewdtlDatView> datViews = convert(repayId);
		return datViews==null?(new ArrayList<RepayFeewdtlDatView>()):datViews;
	}
	
	/**
	  * @Description: 初始化流程任务信息费用减免表信息
	  * @param repayId
	  * @return List<RepayFeewdtlDatView>
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:27:11
	  */
	private List<RepayFeewdtlDatView> convert(int repayId) throws TException{
		//DecimalFormat df = new DecimalFormat("#.00");
		List<RepayFeewdtlDatView> fwtlist = new ArrayList<RepayFeewdtlDatView>();
		List<RepayFeewdtlDatView> fwlist = repayFeewdtlMapper.queryRegFeewDealbyprocess(repayId);
		//Double overint=	repaymentMapper.getOverfineint(fwlist.get(0).getLoanId());
		if (null != fwlist && fwlist.size() > 0) {
			for (int i = 0; i < fwlist.size(); i++) {
				List<RepayFeewdtlDatView> flist = repayFeewdtlMapper.queryRegFeewLoanbyprocess(fwlist.get(i));
				if (null != flist && flist.size() > 0) {
					flist.get(0).setTypeName("应收");
					int days = DateUtil.getDaysIntervalStr(flist.get(0).getPlanRepayDt(), DateUtil.format(DateUtil.getToDay(), "yyyy-MM-dd HH:mm:ss"));
					if (days > 0) {
						ReconciliationItem reconciliationItem = financeServiceImpl
								.getReconciliationItem(1, flist.get(0).getPId(), DateUtil.format(new Date()));
						double ov = reconciliationItem.getInterest_yl()
								+ reconciliationItem.getMangCost_yl()
								+ reconciliationItem.getOtherCost_yl()
								+ reconciliationItem.getPrincipal_yl();
						//String ov = df.format(flist.get(0).getTotal() * overint/ 100 * days);
						flist.get(0).setOverdueInterest(ov);
						
					} else {
						flist.get(0).setOverdueInterest(0);
					}
					flist.get(0).setTotal(
						flist.get(0).getTotal()
						+ flist.get(0).getOverdueFine()
						+ flist.get(0).getOverdueInterest());
					fwtlist.add(flist.get(0));
					fwlist.get(i).setTypeName("减免");
					fwlist.get(i).setTotal(fwlist.get(i).getShouldMangCost()
						+ fwlist.get(i).getShouldOtherCost()
						+ fwlist.get(i).getShouldInterest()
						+ fwlist.get(i).getOverdueFine()
						+ fwlist.get(i).getOverdueInterest());
					fwtlist.add(fwlist.get(i));
					RepayFeewdtlDatView ftem = new RepayFeewdtlDatView();
					ftem.setTypeName("减免后应收");
					ftem.setShouldPrincipal(flist.get(0).getShouldPrincipal()
						- fwlist.get(i).getShouldPrincipal());
					ftem.setShouldMangCost(flist.get(0).getShouldMangCost()
						- fwlist.get(i).getShouldMangCost());
					ftem.setShouldOtherCost(flist.get(0).getShouldOtherCost()
						- fwlist.get(i).getShouldOtherCost());
					ftem.setShouldInterest(flist.get(0).getShouldInterest()
						- fwlist.get(i).getShouldInterest());
					ftem.setTotal(flist.get(0).getTotal()
						- fwlist.get(i).getTotal());
					ftem.setOverdueFine(flist.get(0).getOverdueFine()
						- fwlist.get(i).getOverdueFine());
					ftem.setOverdueInterest(flist.get(0).getOverdueInterest()
						- fwlist.get(i).getOverdueInterest());
					ftem.setLoanId(flist.get(0).getLoanId()
						- fwlist.get(i).getLoanId());
					ftem.setPlanCycleNum(flist.get(0).getPlanCycleNum());
					fwtlist.add(ftem);
				}
			}
			return fwtlist;
		}
		return new ArrayList<RepayFeewdtlDatView>();
	}
	/**
	  * @Description: 查询费用减免的原因
	  * @param repayId
	  * @return
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:29:13
	  */
	@Override
	public String queryRegFeewReasonbyprocess(int repayId) throws ThriftServiceException, TException {
		List<RepayFeewdtlDatView> li=	
				repayFeewdtlMapper.queryRegFeewReasonbyprocess(repayId);
	if(null != li&&li.get(0)!=null && li.size() > 0){
		if (null==li.get(0).getReason()||li.get(0).getReason().equals("")) {
			return "";
		}
		return li.get(0).getReason();
	}
	return "";
	}

	/**
	  * @Description: 更新减免的的信息
	  * @param feewdtlDatView
	  * @return
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:30:49
	  */
	@Override
	public int updateRegFeewDealbyprocess(String datViews,String reason,int repayId ) throws ThriftServiceException, TException {
		RepayFeewdtlDatDTO repayfeew = new RepayFeewdtlDatDTO();
		repayfeew.setReason(reason);
		repayfeew.setRepayId(repayId);
		repayFeewdtlMapper.updateRegFeewDealReason(repayfeew);
		JSONArray array = JSONArray.parseArray(datViews);
		//更新的行数
		int updateCount = 0;
		for (int i = 0; i < array.size(); i++) {
			if(i<array.size()){
				JSONObject json = array.getJSONObject(i);
				String typeName = json.getString("typeName");
				if("减免".equals(typeName)){
					RepayFeewdtlDatDTO feewdtlDatDTO = new RepayFeewdtlDatDTO();
					feewdtlDatDTO.setRepaymentId(convertInt(json.getString("repaymentId")));
					feewdtlDatDTO.setRepayId(repayId);
					feewdtlDatDTO.setPlanCycleNum(convertInt(json.getString("planCycleNum")));
					feewdtlDatDTO.setShouldOtherCost(convertDouble(json.getString("shouldOtherCost")));
					feewdtlDatDTO.setShouldMangCost(convertDouble(json.getString("shouldMangCost")));
					feewdtlDatDTO.setShouldInterest(convertDouble(json.getString("shouldInterest")));
					feewdtlDatDTO.setOverdueLoanInterestAmt(convertDouble(json.getString("overdueInterest")));
					feewdtlDatDTO.setOverdueFineInterestAmt(convertDouble(json.getString("overdueFine")));
					int pid = convertInt(json.getString("pId"));
					if(pid > 0){
						feewdtlDatDTO.setPId(pid);
						repayFeewdtlMapper.updateRegFeewDealinfo(feewdtlDatDTO);
					}else{
						repayFeewdtlMapper.insertLoanFeewInfo(feewdtlDatDTO);
					}
					//updateCount++;
				}
			}
		}
		return updateCount;
	}
	/**
	  * @Description: 将字符串转换成double
	  * @param str 要转换的字符串
	  * @return double
	  * @author: JingYu.Dai
	  * @date: 2015年5月23日 下午6:15:10
	 */
	private double convertDouble(String str) {
		double b = 0;
		try {
			b = Double.parseDouble(str);
		} catch (Exception e) {
			b = 0;
		}
		return b;
	}
	
	/**
	 * 
	  * @Description: 将字符串转换成int
	  * @param str 要转换的字符串
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年5月23日 下午6:18:41
	 */
	private int convertInt(String str){
		int b = 0;
		try {
			b = Integer.parseInt(str);
		} catch (Exception e) {
			b = 0;
		}
		return b;
	}
	/**
	  * @Description: 查询费用减免项目信息更具项目id
	  * @param projectId
	  * @return List<RepayFeewdtlDatView>
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:31:21
	 */
	@Override
	public RepayFeewdtlDatView queryRegFeewprojectinfobyproId(int projectId) throws ThriftServiceException, TException {
		List<RepayFeewdtlDatView> datView=	repayFeewdtlMapper.queryRegFeewprojectinfobyproId(projectId);
		if(null != datView && datView.size() > 0){
			return datView.get(0);
		}
		return new RepayFeewdtlDatView();
	}
	/**
	  * @Description: 查询费用减免项目信息更具费用减免id
	  * @param repayId
	  * @return List<RepayFeewdtlDatView>
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:32:03
	 */
	@Override
	public RepayFeewdtlDatView queryRegFeewprojectinfobyrepayId(int repayId) throws ThriftServiceException, TException {
		List<RepayFeewdtlDatView> datView=	repayFeewdtlMapper.queryRegFeewprojectinfobyrepayId(repayId);
		if(null != datView && datView.size() > 0){
			return datView.get(0);
		}
		return new RepayFeewdtlDatView();
	}
	/**
	  * @Description: 插入挪用处理上传文件信息
	  * @param uploadinstAdvapplyBaseDTO
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:32:41
	 */
	@Override
	public int uploadinstiDvertapply(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO) throws ThriftServiceException, TException {
		afterLoanDivertMapper.insertDvertfileInfo(uploadinstAdvapplyBaseDTO);
		uploadinstAdvapplyBaseDTO.setFileId(uploadinstAdvapplyBaseDTO.getPId());
		int st = afterLoanDivertMapper.insertLoanDvertfileInfo(uploadinstAdvapplyBaseDTO);
		return st;
	}
	
	/**
	  * @Description: 修改挪用资料文件描述信息
	  * @param uploadinstAdvapplyBaseDTO
	  * @return int
	  * @author: Zhangyu.Hoo
	  * @date: 2015年7月7日 下午4:15:31
	  */
	@Override
	public int updateEmbezzleFile(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO) throws ThriftServiceException, TException {
		int result = afterLoanDivertMapper.updateEmbezzleFile(uploadinstAdvapplyBaseDTO);
		return result;
	}
	
	/**
	  * @Description: 查询挪用处理上传文件信息
	  * @param divertId
	  * @return List<RegAdvapplyFileview>
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:34:30
	  */
	@Override
	public List<RegAdvapplyFileview> queryRegDivertapplyFile(int DivertId) throws ThriftServiceException, TException {
		List<RegAdvapplyFileview> list = afterLoanDivertMapper.queryRegDivertapplyFile(DivertId);
		if (null != list) {
			return list;
		}
		return new ArrayList<RegAdvapplyFileview>();
	}

	/**
	  * @Description:修改费用减免的状态
	  * @param repayFeewdtlDatView
	  * @return int 
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:34:56
	  */
	@Override
	public int changeReqstFeewdel(int reqStatus, int repayId) throws ThriftServiceException, TException {
		RepayFeewdtlDatView  repayFeewdtlDatView =new RepayFeewdtlDatView();
		repayFeewdtlDatView.setRequestStatus(reqStatus);
		repayFeewdtlDatView.setRepayId(repayId);
		return repayFeewdtlMapper.changeReqstFeewdel(repayFeewdtlDatView);
	}

	/**
	  * @Description: 批量删除费用减免 根据费用减免Id
	  * @param repayIds 费用减免Id集合
	  * @return int 受影响行数
	  * @author: JingYu.Dai
	  * @date: 2015年5月30日 下午4:34:30
	 */
	@Override
	public int deleteProjectbyFeewDealList(List<Integer> repayIds, List<Integer> projectIds) throws ThriftServiceException, TException {
		for (int i = 0; i < repayIds.size(); i++) {
			workflowServiceImpl.deleteProcessInstance(repayIds.get(i),projectIds.get(i),WorkflowIdConstant.FEE_WAIVERS_REQUEST_PROCESS);
		}
		return repayFeewdtlMapper.deleteProjectbyFeewDealList(repayIds);
	}

	/**
	  * @Description: 删除费用减免信息
	  * @param repayId
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:36:56
	  */
	@Override
	public int deleteProjectbyFeewDeal(int repayId,int projectId) throws ThriftServiceException, TException {
		workflowServiceImpl.deleteProcessInstance(repayId,projectId,WorkflowIdConstant.FEE_WAIVERS_REQUEST_PROCESS);
		return repayFeewdtlMapper.deleteProjectbyFeewDeal(repayId);
	}
	@Override
	public int checkFeewDealByProjectId(int projectId) throws ThriftServiceException, TException {
		return repayFeewdtlMapper.checkFeewDealByProjectId(projectId);
	}

	/**
	  * @Description: 更改费用减免信息
	  * @param repayfeew
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:39:44
	  */
	@Override
	public int updateInsertRegFeewDealbyprocess(String reason, String feewdel, int repayId,int projectId ,int loanId ) throws ThriftServiceException, TException {
		String[] feewde = null;
		RepayFeewdtlDatDTO repayfeew = new RepayFeewdtlDatDTO();
		repayfeew.setLoanId(loanId);
		repayfeew.setProjectId(projectId);
		repayfeew.setReason(reason);
		repayfeew.setPId(repayId);
		//更改费用减免详细信息
		repayFeewdtlMapper.updateLoanFeewdelInfo(repayfeew);
		//费用减免明细
		repayFeewdtlMapper.deleteFeewDealDatilbyrepayId(repayId);
		String[] feewdelinfo = feewdel.split("/");
		for (int i = 0; i < feewdelinfo.length; i++) {
			RepayFeewdtlDatDTO feewdtlDatView = new RepayFeewdtlDatDTO();
			feewde = feewdelinfo[i].split(",");
			feewdtlDatView.setRepaymentId(Integer.parseInt(feewde[0]));
			feewdtlDatView.setPlanCycleNum(Integer.parseInt(feewde[1]));
			feewdtlDatView.setShouldInterest(Double.parseDouble(feewde[3]));
			feewdtlDatView.setShouldOtherCost(Double.parseDouble(feewde[4]));
			feewdtlDatView.setShouldMangCost(Double.parseDouble(feewde[5]));
			feewdtlDatView.setOverdueFineInterestAmt(Double.parseDouble(feewde[7]));
			feewdtlDatView.setOverdueLoanInterestAmt(Double.parseDouble(feewde[6]));
			feewdtlDatView.setRepayId(repayId);
			//保存费用减免详细信息
			repayFeewdtlMapper.insertLoanFeewInfo(feewdtlDatView);
		}
		return repayfeew.getPId();
	}

	/**
	  * @Description: 费用减免申请书数据查询
	  * @param userId 用户ID
	  * @param repayId 费用减免ID
	  * @return FeeWaiverApplicationDTO
	  * @author: JingYu.Dai
	  * @throws TException 
	  * @date: 2015年5月19日 下午8:05:06
	 */
	@Override
	public FeeWaiverApplicationDTO findFeeWaiverApplication(int userId,int repayId) throws TException{
		Map<String,Integer> map = new HashMap<String, Integer>();
		map.put("userId", userId);
		map.put("repayId", repayId);
		List<RepayFeewdtlDatView> datViews = convert(repayId);
		FeeWaiverApplicationDTO applicationDTO = repayFeewdtlMapper.findFeeWaiverApplication(map);
		for (RepayFeewdtlDatView rf : datViews) {
			if(null != rf){
				if("应收".equals(rf.getTypeName())){
					applicationDTO.setRecAccrual(doubleFormat((rf.getShouldInterest()+applicationDTO.getRecAccrual())));
					applicationDTO.setRecManageFee(doubleFormat((rf.getShouldMangCost()+applicationDTO.getRecManageFee())));
					applicationDTO.setRecElseFee(doubleFormat((rf.getShouldOtherCost()+applicationDTO.getRecElseFee())));
					applicationDTO.setRecOveAcc(doubleFormat((rf.getOverdueInterest()+applicationDTO.getRecOveAcc())));
					applicationDTO.setRecLateCharge(doubleFormat((rf.getOverdueFine()+applicationDTO.getRecLateCharge())));
					applicationDTO.setRecTotal(doubleFormat((rf.getTotal()+applicationDTO.getRecTotal())));
				}else if("减免".equals(rf.getTypeName())){
					applicationDTO.setDerAccrual(doubleFormat(rf.getShouldInterest()+applicationDTO.getDerAccrual()));
					applicationDTO.setDerManageFee(doubleFormat((rf.getShouldMangCost()+applicationDTO.getDerManageFee())));
					applicationDTO.setDerElseFee(doubleFormat((rf.getShouldOtherCost()+applicationDTO.getDerElseFee())));
					applicationDTO.setDerOveAcc(doubleFormat((rf.getOverdueInterest()+applicationDTO.getDerOveAcc())));
					applicationDTO.setDerLateCharge(doubleFormat((rf.getOverdueFine()+applicationDTO.getDerLateCharge())));
					applicationDTO.setDerTotal(doubleFormat((rf.getTotal()+applicationDTO.getDerTotal())));
				}else if("减免后应收".equals(rf.getTypeName())){
					applicationDTO.setDerLastAccrual(doubleFormat(rf.getShouldInterest()+applicationDTO.getDerLastAccrual()));
					applicationDTO.setDerLastManageFee(doubleFormat((rf.getShouldMangCost()+applicationDTO.getDerLastManageFee())));
					applicationDTO.setDerLastElseFee(doubleFormat((rf.getShouldOtherCost()+applicationDTO.getDerLastElseFee())));
					applicationDTO.setDerLastOveAcc(doubleFormat((rf.getOverdueInterest()+applicationDTO.getDerLastOveAcc())));
					applicationDTO.setDerLastLateCharge(doubleFormat((rf.getOverdueFine()+applicationDTO.getDerLastLateCharge())));
					applicationDTO.setDerLastTotal(doubleFormat((rf.getTotal()+applicationDTO.getDerLastTotal())));
				}
			}
		}
		return applicationDTO;
	}
	
	/**
	  * @Description: 格式化double数据
	  * @return double
	  * @author: JingYu.Dai
	  * @date: 2015年6月5日 下午4:45:58
	 */
	private double doubleFormat(double d){
		DecimalFormat df = new DecimalFormat("#.00");
		return new Double(df.format(d));
	}
	
}