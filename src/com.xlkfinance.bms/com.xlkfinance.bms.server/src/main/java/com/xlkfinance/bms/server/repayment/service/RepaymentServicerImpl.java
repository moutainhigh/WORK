package com.xlkfinance.bms.server.repayment.service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.achievo.framework.util.DateUtil;
import com.google.common.collect.Lists;
import com.xlkfinance.bms.common.constant.WorkflowIdConstant;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
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
import com.xlkfinance.bms.rpc.repayment.PreApplyRepayBaseDTO;
import com.xlkfinance.bms.rpc.repayment.ProjectComplete;
import com.xlkfinance.bms.rpc.repayment.ProjectCompleteFile;
import com.xlkfinance.bms.rpc.repayment.RegAdvapplyFileview;
import com.xlkfinance.bms.rpc.repayment.RegAdvapplyRepayDTO;
import com.xlkfinance.bms.rpc.repayment.RegAdvapplyRepayView;
import com.xlkfinance.bms.rpc.repayment.RepayEconciliationDTO;
import com.xlkfinance.bms.rpc.repayment.RepayOverdueDTO;
import com.xlkfinance.bms.rpc.repayment.RepayOverdueView;
import com.xlkfinance.bms.rpc.repayment.RepayProjectBadDTO;
import com.xlkfinance.bms.rpc.repayment.RepaymentBaseDTO;
import com.xlkfinance.bms.rpc.repayment.RepaymentDivertView;
import com.xlkfinance.bms.rpc.repayment.RepaymentPlanBaseDTO;
import com.xlkfinance.bms.rpc.repayment.RepaymentService.Iface;
import com.xlkfinance.bms.rpc.repayment.RepaymentView;
import com.xlkfinance.bms.rpc.repayment.RepaymentViolationView;
import com.xlkfinance.bms.rpc.repayment.SaveAdvRepaymentBaseDTO;
import com.xlkfinance.bms.rpc.repayment.SettleFile;
import com.xlkfinance.bms.rpc.repayment.UploadinstAdvapplyBaseDTO;
import com.xlkfinance.bms.rpc.workflow.WorkflowService;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanRepaymentPlanMapper;
import com.xlkfinance.bms.server.finance.util.FinanceTypeEnum;
import com.xlkfinance.bms.server.repayment.mapper.RepaymentMapper;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDispose;

@SuppressWarnings("unchecked")
@Service("repaymentServicerImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.repayment.RepaymentService")
public class RepaymentServicerImpl extends WorkflowSpecialDispose implements Iface {
	private Logger logger = LoggerFactory.getLogger(RepaymentServicerImpl.class);

	@SuppressWarnings("rawtypes")
	@Resource(name = "repaymentMapper")
	private RepaymentMapper repaymentMapper;
	
	@Resource(name = "workflowServiceImpl")
	private WorkflowService.Iface workflowServiceImpl;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "loanRepaymentPlanMapper")
	private LoanRepaymentPlanMapper loanRepaymentPlanMapper;

	@Override
	public List<CostDerateTabulationView> getCostDerateTabulation(CostDerateTabulationDTO costDerateTabulationDTO) throws ThriftServiceException, TException {
		List<CostDerateTabulationView> list=repaymentMapper.getCostDerateTabulation(costDerateTabulationDTO);
		return list!=null?list:new ArrayList<CostDerateTabulationView>();
	}

	@Override
	public int getCostDerateTabulationCount(CostDerateTabulationDTO costDerateTabulationDTO) throws ThriftServiceException, TException {
		Integer  result = repaymentMapper.getCostDerateTabulationCount(costDerateTabulationDTO);
		return result!=null?result:0;
	}

	@Override
	public List<CostDerateView> getCostDerate(CostDerateDTO costDerateDTO) throws ThriftServiceException, TException {
		List<CostDerateView> result = repaymentMapper.getCostDerate(costDerateDTO);
		if(null == result){
			result = new ArrayList<CostDerateView>();
		}
		return result;
	}

	@Override
	public int getCostDerateCount(CostDerateDTO costDerateDTO) throws ThriftServiceException, TException {
		return repaymentMapper.getCostDerateCount(costDerateDTO);
	}

	@Override
	public List<AvailabilityChangeTabulationView> getAvailabilityTabulation(AvailabilityChangeTabulationDTO availabilityChangeTabulationDTO) throws ThriftServiceException, TException {
		List<AvailabilityChangeTabulationView> result = repaymentMapper.getAvailabilityTabulation(availabilityChangeTabulationDTO);
		if(null == result){
			result = new ArrayList<AvailabilityChangeTabulationView>();
		}
		return result;
	}

	@Override
	public int getAvailabilityTabulationCount(AvailabilityChangeTabulationDTO availabilityChangeTabulationDTO) throws ThriftServiceException, TException {
		return repaymentMapper.getAvailabilityTabulationCount(availabilityChangeTabulationDTO);
	}

	@Override
	public List<AvailabilityChangeView> getChangeList(AvailabilityChangeDTO availabilityChangeDTO) throws ThriftServiceException, TException {
		List<AvailabilityChangeView> result = repaymentMapper.getChangeList(availabilityChangeDTO);
		if(null == result){
			result = new ArrayList<AvailabilityChangeView>();
		}
		return result;
	}

	@Override
	public int getChangeTotaleCount(AvailabilityChangeDTO availabilityChangeDTO) throws ThriftServiceException, TException {
		return repaymentMapper.getChangeTotaleCount(availabilityChangeDTO);
	}

	@Override
	public List<RepaymentView> getRepaymentList(RepaymentBaseDTO repaymentBaseDTO) throws ThriftServiceException, TException {
		List<RepaymentView> result = repaymentMapper.getRepaymentList(repaymentBaseDTO);
		if(null == result){
			result = new ArrayList<RepaymentView>();
		}
		return result;
	}

	@Override
	public int getTempTotaleCount(RepaymentBaseDTO repaymentBaseDTO) throws ThriftServiceException, TException {
		return repaymentMapper.getTempTotaleCount(repaymentBaseDTO);
	}
	@Override
	public int queryRegAdvapplyFileCount(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO) throws ThriftServiceException, TException {
		return repaymentMapper.queryRegAdvapplyFileCount(uploadinstAdvapplyBaseDTO);
	}
	@Override
	public List<AdvRepaymentView> getAdvrepaymenturl(AdvRepaymentBaseDTO advRepaymentBaseDTO) throws ThriftServiceException, TException {
		List<AdvRepaymentView> result = repaymentMapper.getAdvrepaymenturl(advRepaymentBaseDTO);
		if(null == result){
			result = new ArrayList<AdvRepaymentView>();
		}
		return result;
	}

	@Override
	public int getAdvRepayTotaleCount(AdvRepaymentBaseDTO advRepaymentBaseDTO) throws ThriftServiceException, TException {
		return repaymentMapper.getAdvRepayTotaleCount(advRepaymentBaseDTO);
	}

	/*
	 * 放款收息表查询server *
	 */
	@Override
	public List<AdvapplyRepaymentView> getAdvapplyrepaymenturl(AdvapplyRepaymentBaseDTO advapplyRepaymentBaseDTO) throws TException {
		int maxhasreceive = 0;
		double dou = 0;
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		// 查询还款计划表
		List<AdvapplyRepaymentView> li = repaymentMapper.getAdvapplyrepaymenturl(advapplyRepaymentBaseDTO);
		// 查询对账的信息 ckBil
		List<RepayEconciliationDTO> ckBil = repaymentMapper.selectRepaymentReconciliation(advapplyRepaymentBaseDTO);
		// 查询违约
		List<RepaymentViolationView> violate = repaymentMapper.selectRepaymentViolation(advapplyRepaymentBaseDTO);
		// 查询挪用
		List<RepaymentDivertView> divert = repaymentMapper.selectRepaymentDivert(advapplyRepaymentBaseDTO);
		// 查询坏账
		List<RepayProjectBadDTO> projectBad = repaymentMapper.selectRepaymentProjectBad(advapplyRepaymentBaseDTO);
		if (li == null) {
			List<AdvapplyRepaymentView> list = new ArrayList<AdvapplyRepaymentView>();
			return list;
		}
		for (int i = 0; i < li.size(); i++) {
			// 对账完毕
			if (li.get(i).getIsReconciliation() == 1) {
				for (int j = 0; j < ckBil.size(); j++) {
					if (ckBil.get(j).getReconciliationCycleNum() > maxhasreceive) {
						maxhasreceive = ckBil.get(j).getReconciliationCycleNum();
					}
					if (ckBil.get(j).getReconciliationCycleNum() == li.get(i).getPlanCycleNum() && ckBil.get(j).getDtlType() == 5) {
						li.get(i).setOverdueLoanmt(ckBil.get(j).getDtlAmt());
					}
					if (ckBil.get(j).getReconciliationCycleNum() == li.get(i).getPlanCycleNum() && ckBil.get(j).getDtlType() == 6) {
						li.get(i).setOverdueFineAmt(ckBil.get(j).getDtlAmt());
					}
					if (ckBil.get(j).getReconciliationCycleNum() == li.get(i).getPlanCycleNum()) {
						li.get(i).setHasReceiveAmt(ckBil.get(j).getReconciliationAmt());
					}

				}
				for (int h = 0; h < ckBil.size(); h++) {
					if (li.get(i).getPlanCycleNum() == ckBil.get(h).getReconciliationCycleNum()) {
						// 已收总额
						li.get(i).setHasReceiveAmt(ckBil.get(h).getReconciliationAmt());
						break;
					}
				}

				dou = li.get(i).getTotal();
				for (int k = 0; k < ckBil.size(); k++) {
					if (li.get(i).getPlanCycleNum() == ckBil.get(k).getReconciliationCycleNum() && ckBil.get(k).getDtlAmt() == 5) {
						dou += ckBil.get(k).getDtlAmt();
						break;
					}
				}
				for (int k = 0; k < ckBil.size(); k++) {
					if (li.get(i).getPlanCycleNum() == ckBil.get(k).getReconciliationCycleNum() && ckBil.get(k).getDtlAmt() == 6) {
						dou += ckBil.get(k).getDtlAmt();
						break;
					}
				}
				// 应收总金额
				li.get(i).setShouldTotailAmt(dou);
				for (int k = 0; k < ckBil.size(); k++) {
					if (li.get(i).getPlanCycleNum() == ckBil.get(k).getReconciliationCycleNum()) {
						dou -= ckBil.get(k).getReconciliationAmt();
						break;
					}
				}
				// 未收合计
				li.get(i).setNoReceiveAmt(dou);

			}

			// 部分对账
			if (li.get(i).getIsReconciliation() == 3) {
				// 是否逾期
				try {
				} catch (Exception e) {
					e.printStackTrace();
					// TODO 调用逾期算法算出逾期利息和逾期罚息
					li.get(i).setOverdueFineAmt(1);
					li.get(i).setOverdueLoanmt(1);
					li.get(i).setShouldTotailAmt(li.get(i).getTotal() + 1 + 1);
					for (int l = 0; l < ckBil.size(); l++) {
						if (li.get(i).getPlanCycleNum() == ckBil.get(l).getReconciliationCycleNum()) {
							li.get(i).setHasReceiveAmt(ckBil.get(l).getReconciliationAmt());
							li.get(i).setNoReceiveAmt(li.get(i).getTotal() + 1 + 1 - ckBil.get(l).getReconciliationAmt());
							break;
						}
					}
				}
			}
			// 未对账
			if (li.get(i).getIsReconciliation() == 2) {
				li.get(i).setOverdueFineAmt(0);
				li.get(i).setOverdueLoanmt(0);
				li.get(i).setShouldTotailAmt(li.get(i).getTotal());
				li.get(i).setHasReceiveAmt(0);
				li.get(i).setNoReceiveAmt(li.get(i).getTotal());
			}
			// TODO 此处还要加上逾期利息
			li.get(i).setShouldTotailAmt(li.get(i).getTotal());
			li.get(i).setPlanRepayDt(li.get(i).getPlanRepayDt().split(" ")[0]);
		}
		// 循环即时还款表
		// 类型（提前还款=1、提前还款罚金=2、挪用罚息=3、违约罚金=4
		// 坏账
		if (projectBad.size() > 0) {
			for (int j = 0; j < projectBad.size(); j++) {
				AdvapplyRepaymentView aV = new AdvapplyRepaymentView();
				// -1代表坏账
				aV.setPlanCycleNum(-1);
				aV.setPlanRepayDt(projectBad.get(j).getShouldDt());
				aV.setShouldPrincipal(projectBad.get(j).getBadLossAmt());
				aV.setTotal(projectBad.get(j).getBadLossAmt());
				aV.setShouldTotailAmt(projectBad.get(j).getBadLossAmt());
				if (null != divert && divert.size() > 0) {
					if (divert.get(j).getIsReconciliation() == 1) {
						aV.setHasReceiveAmt(projectBad.get(j).getBadLossAmt());
					}
				}
				li.add(aV);
			}
		}
		if (divert.size() > 0) {
			for (int j = 0; j < divert.size(); j++) {

				AdvapplyRepaymentView aV = new AdvapplyRepaymentView();
				// -2代表挪用
				aV.setPlanCycleNum(-2);
				aV.setPlanRepayDt(divert.get(j).getDivertFinePayDt());
				aV.setShouldPrincipal(divert.get(j).getDivertFinePayAmt());
				aV.setTotal(divert.get(j).getDivertFinePayAmt());
				if (divert.get(j).getIsReconciliation() == 1) {
					aV.setHasReceiveAmt(divert.get(j).getDivertFinePayAmt());
				}
				try {
					if (null != divert.get(j).getDivertFinePayDt()) {
						long ddate = sf.parse(divert.get(j).getDivertFinePayDt()).getTime();
						long ndate = new Date().getTime();
						if (ddate > ndate) {
							int between_days = (int) ((ddate - ndate) / (1000 * 3600 * 24));
							double overTotail = between_days * divert.get(j).getDivertAmt() * divert.get(j).getDivertFine();
							aV.setOverdueLoanmt(overTotail);
							aV.setShouldTotailAmt(divert.get(j).getDivertFinePayAmt() + overTotail);
						} else {
							aV.setShouldTotailAmt(divert.get(j).getDivertFinePayAmt());
						}
					}

				} catch (ParseException e) {
					logger.debug("时间转化出错" + e.getMessage());
				}
				li.add(aV);
			}
		}
		if (violate.size() > 0) {
			for (int j = 0; j < violate.size(); j++) {
				AdvapplyRepaymentView aV = new AdvapplyRepaymentView();
				// -3代表违约
				aV.setPlanCycleNum(-3);
				aV.setPlanRepayDt(violate.get(j).getViolationDt());
				aV.setShouldPrincipal(violate.get(j).getViolationAmt());
				aV.setTotal(violate.get(j).getViolationAmt());
				if (null != divert && divert.size() > 0) {
					if (divert.get(j).getIsReconciliation() == 1) {
						aV.setHasReceiveAmt(violate.get(j).getViolationAmt());
					}
				}
				aV.setShouldTotailAmt(violate.get(j).getViolationAmt());
				try {
					long vdate = sf.parse(violate.get(j).getViolationDt()).getTime();
					long ndate = new Date().getTime();
					if (vdate > ndate) {
						int between_days = (int) ((vdate - ndate) / (1000 * 3600 * 24));
						double overTotail = (violate.get(j).getViolationAmt() - violate.get(j).getViolationAmt()) * between_days * violate.get(j).getViolationOtInterest();
						aV.setOverdueLoanmt(overTotail);
						aV.setShouldTotailAmt(violate.get(j).getViolationAmt() + overTotail);
					} else {
						aV.setShouldTotailAmt(violate.get(j).getViolationAmt());
					}

				} catch (ParseException e) {
					logger.debug("时间转化出错" + e.getMessage());
				}
				li.add(aV);
			}
		}
		return li;
	}

	@Override
	public int getAdvapplyRepayTotaleCount(AdvapplyRepaymentBaseDTO advapplyRepaymentBaseDTO) throws ThriftServiceException, TException {
		return repaymentMapper.getAdvapplyRepayTotaleCount(advapplyRepaymentBaseDTO);
	}

	@Override
	public int saveAdvRepayment(SaveAdvRepaymentBaseDTO saveAdvRepaymentBaseDTO) throws ThriftServiceException, TException {

		return repaymentMapper.saveAdvRepayment(saveAdvRepaymentBaseDTO);
	}

	// 提前还款的上传文件
	@Override
	public int uploadinstAdvapply(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO) throws ThriftServiceException, TException {
		repaymentMapper.uploadinstAdvapply(uploadinstAdvapplyBaseDTO);
		uploadinstAdvapplyBaseDTO.setFileId(uploadinstAdvapplyBaseDTO.getPId());
		repaymentMapper.uploadinstAdvapplyId(uploadinstAdvapplyBaseDTO);
		return uploadinstAdvapplyBaseDTO.getPId();

	}

	@Override
	public int executiveOperation(ExecutiveOperBaseDTO executiveOperBaseDTO) throws ThriftServiceException, TException {

		return repaymentMapper.executiveOperation(executiveOperBaseDTO);
	}

	@Override
	public int delectApplyRepay(ApplyfileuploadBaseDTO applyfileuploadBaseDTO) throws ThriftServiceException, TException {

		return repaymentMapper.delectApplyRepay(applyfileuploadBaseDTO);
	}

	@Override
	public int insertPreApplyRepay(PreApplyRepayBaseDTO preApplyRepayBaseDTO) throws ThriftServiceException, TException {
		repaymentMapper.insertPreApplyRepay(preApplyRepayBaseDTO);
		updateWorkflowStatus(preApplyRepayBaseDTO.getProjectId(),2);
		return preApplyRepayBaseDTO.getPId();
	}

	// 查询 原借款金额 原借款期限 原借款合同编号 原授信合同编号 原借款种类 原借款月利率
	@Override
	public RegAdvapplyRepayView queryRegAdvapplyRepay(RegAdvapplyRepayDTO regAdvapplyRepayDTO) throws ThriftServiceException, TException {
		List<RegAdvapplyRepayView> li = repaymentMapper.queryRegAdvapplyRepay(regAdvapplyRepayDTO);
		if ( null != li && li.size() > 0) {
			RegAdvapplyRepayView view = li.get(0);
			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
			String formatAmt = decimalFormat.format(view.getCreditAmt());
			view.setCreditAmtTemp(formatAmt);
			return view;
		}
		return new RegAdvapplyRepayView();
	}

	@Override
	public List<RegAdvapplyFileview> queryRegAdvapplyFile(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO) throws ThriftServiceException, TException {
		List<RegAdvapplyFileview> list = repaymentMapper.queryRegAdvapplyFilelist(uploadinstAdvapplyBaseDTO);
		if (null== list){
			return new ArrayList<RegAdvapplyFileview>();
		}
		return list;
	}

	// 删除上传文件
	@Override
	public int delectFilebyId(String pId) throws ThriftServiceException, TException {
		List<String> list = new ArrayList<String>();
		try {
			String[] s = pId.split(",");
			for (String string : s) {
				if(!"".equals(string)){
					list.add(string);
				}
			}
			return repaymentMapper.delectFilebyId(list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_CONTACT_DELETE, "");
		}
	}

	//结清业务查询
	public List<CusBusiness> getCusBusiness(CusBusiness cusBusiness) throws ThriftServiceException, TException {
		List<CusBusiness> list = null;
		list = repaymentMapper.getCusBusiness(cusBusiness);
		if (null == list) {
			return new ArrayList<CusBusiness>();
		}
		return list;
	}
	//结清业务查询总数
	public int getCusBusinessTotal(CusBusiness cusBusiness)
			throws ThriftServiceException, TException {
		return repaymentMapper.getCusBusinessTotal(cusBusiness);
	}
	//查看是否插入结清表
	public ProjectComplete getLoanSettleOne(int projectId) throws ThriftServiceException,
			TException {
		ProjectComplete projectComplete = repaymentMapper.getLoanSettleOne(projectId);
		return projectComplete==null?new ProjectComplete():projectComplete;
	}
	//保存结清
	public int saveLoanSettle(ProjectComplete projectComplete)
			throws ThriftServiceException, TException {
			repaymentMapper.saveLoanSettle(projectComplete);
			if(projectComplete.getProjectId()>0){
				loanRepaymentPlanMapper.delRepaymentNofreezeData(projectComplete.getProjectId());
			}
		return projectComplete.getPId();
	}
	//修改结清
	public int updateLoanSettle(ProjectComplete projectComplete)
			throws ThriftServiceException, TException {
		if(projectComplete.getProjectId()>0){
			loanRepaymentPlanMapper.delRepaymentNofreezeData(projectComplete.getProjectId());
		}
		return repaymentMapper.updateLoanSettle(projectComplete);
	}
	
	//生成结清文件
	public SettleFile getSettleFile(int projectId)
			throws ThriftServiceException, TException {
		SettleFile settleFile = new SettleFile();
		settleFile = repaymentMapper.getSettleFile(projectId);
		return settleFile==null?new SettleFile():settleFile;
	}
	
	//展期生成结清文件
	public SettleFile getSettleFileRepayment(int projectId)
			throws ThriftServiceException, TException {
		SettleFile settleFile = new SettleFile();
		settleFile = repaymentMapper.getSettleFileRepayment(projectId);
		return settleFile==null?new SettleFile():settleFile;
	}
	
	//结清文件上传
	public int saveProjectCompleteFile(ProjectCompleteFile projectCompleteFile)
			throws ThriftServiceException, TException {
		return repaymentMapper.saveProjectCompleteFile(projectCompleteFile);
	}
	
	//结清文件查询
	public List<ProjectCompleteFile> getProjectCompleteFile(int pid)
			throws ThriftServiceException, TException {
		List<ProjectCompleteFile> list = new ArrayList<ProjectCompleteFile>();
		list = repaymentMapper.getProjectCompleteFile(pid);
		return list!=null?list:new ArrayList<ProjectCompleteFile>();
	}
	//结清文件删除
	public boolean deleteProjectCompleteFile(String pidArray)
			throws ThriftServiceException, TException {
		boolean del = true;
		String[] pids = pidArray.split(",");
		for (String string : pids) {
			int pid = Integer.parseInt(string);
			del = repaymentMapper.deleteTempLate(pid);
		}
		return del;
	}
	//结清文件修改
	public boolean editProjectCompleteFile(
			ProjectCompleteFile projectCompleteFile)
			throws ThriftServiceException, TException {
		return repaymentMapper.editProjectCompleteFile(projectCompleteFile);
	}
	
	@Override
	public List<CusBusiness> getLoanSettle(CusBusiness cusBusiness) throws ThriftServiceException, TException {
		List<CusBusiness> list = null;
		list = repaymentMapper.getLoanSettle(cusBusiness);
		if (null == list) {
			return new ArrayList<CusBusiness>();
		}
		return list;
	}
	@Override
	public int getLoanSettleTotal(CusBusiness cusBusiness)
			throws ThriftServiceException, TException {
		return repaymentMapper.getLoanSettleTotal(cusBusiness);
	}

	@Override
	public int deleteLoanSettle(String pid) throws ThriftServiceException, TException {
		try {
			List<String> list = new ArrayList<String>();
			String[] s = pid.split(",");
			for (String string : s) {
				list.add(string);
			}
			return repaymentMapper.deleteLoanSettle(list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_CONTACT_DELETE, "");
		}
	}

	/**
	 * 描述： 逾期查询 创建人：gaoWen 创建时间：2015年3月2日 下午3:35:24
	 */
	@Override
	public List<RepayOverdueDTO> getRepaymentOverdueList(RepayOverdueView repayOverdueView) throws ThriftServiceException, TException {
		// 查询逾期
		repayOverdueView.setPage(1);
		repayOverdueView.setRows(1);
		repaymentMapper.getRepaymentOverdueList(repayOverdueView);
		if (null == repayOverdueView.getResults()) {
			return new ArrayList<RepayOverdueDTO>();
		}
		return repayOverdueView.getResults();
	}

	/**
	 * 描述： 删除项目操作 创建人：gaoWen 创建时间：2015年3月2日 下午3:35:24
	 */
	@Override
	public int deleteProjectbyId(String pId) throws ThriftServiceException, TException {
		List<String> list = new ArrayList<String>();
		try {
			String[] s = pId.split(",");
			for (String string : s) {
				list.add(string);
			}
			return repaymentMapper.deleteProjectbyId(list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_CONTACT_DELETE, "");
		}
	}

	// 还款管理/工作流的提前还款跳转
	@Override
	public RegAdvapplyRepayView getrepaydatilbyProcess(int preRepayId) throws ThriftServiceException, TException {
		List<RegAdvapplyRepayView> li = null;
		li = repaymentMapper.getrepaydatilbyProcess(preRepayId);
		if (null != li && li.size()>0 ) {
			RegAdvapplyRepayView view = li.get(0);
			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
			String formatAmt = decimalFormat.format(view.getCreditAmt());
			view.setCreditAmtTemp(formatAmt);
			return view;
		}
		return new RegAdvapplyRepayView();
	}
	// 还款管理/工作流的提前还款的详情查询
	@Override
	public RegAdvapplyRepayView getrepayadvdatilbyProcess(int preRepayId) throws ThriftServiceException, TException {
		List<RegAdvapplyRepayView> li = null;
		li = repaymentMapper.getrepayadvdatilbyProcess(preRepayId);
		if (li != null && li.size() > 0) {
			return li.get(0);
		}
		return new RegAdvapplyRepayView();
	}

	@Override
	public int updateadvApplyrepayment(PreApplyRepayBaseDTO preApplyRepayBaseDTO) throws ThriftServiceException, TException {
		int   st= repaymentMapper.updateadvApplyrepayment(preApplyRepayBaseDTO);
		return st;
	}

	// 还款计划表
	@Override
	public List<RepaymentPlanBaseDTO> selectRepaymentPlanBaseDTO(RepaymentPlanBaseDTO dto)
			throws ThriftServiceException, TException {
		
		dto.setPlanRepayDt(DateUtil.format(DateUtil.getToDay(), "yyyy-MM-dd"));
		repaymentMapper.selectRepaymentPlanBaseDTO(dto);
		List<RepaymentPlanBaseDTO> result = dto.getResults();
		
		if(null!=result && result.size()>0 && null!=result.get(0) ){
			int mark ;
			for(RepaymentPlanBaseDTO tempDto : result ){
				mark = -1;
				if(null == tempDto ){
					continue;
				}
				// dataType 1 即时表数据  2 还款计划表数据 3 费用减免数据
				if(tempDto.getDataType() == 1 ){
					// 期数名称 不等于null 
					if(null != tempDto.getPlanCycleName() && !"".equals(tempDto.getPlanCycleName())){
						// 判断期数能否转换为数字,转换成功 为 非展期数据,反之为展期数据
						try{
							mark = Integer.parseInt(tempDto.getPlanCycleName());
						}catch(Exception e){}
						
						if(mark > -1){
							tempDto.setPlanCycleName(FinanceTypeEnum.getName(tempDto.getOperType()));
							// 设置是否展期数据  1 展期 2 非展期
							tempDto.setIsExtension(2);
						}else{
							tempDto.setPlanCycleName("展"+tempDto.getPlanCycleName()+(FinanceTypeEnum.getName(tempDto.getOperType()).substring(0,1)));
							tempDto.setIsExtension(1);
						}
					}else{
						tempDto.setPlanCycleName(FinanceTypeEnum.getName(tempDto.getOperType()));
						tempDto.setIsExtension(2);
					}
					
					
				}else if (tempDto.getDataType() == 2){
					
					// 判断期数能否转换为数字,转换成功 为 非展期数据,反之为展期数据
					try{
						mark = Integer.parseInt(tempDto.getPlanCycleName());
					}catch(Exception e){}
					
					if(mark > -1){
						tempDto.setIsExtension(1);
						tempDto.setPlanCycleName(tempDto.getPlanCycleName());
					}else{
						tempDto.setIsExtension(2);
						tempDto.setPlanCycleName("展"+tempDto.getPlanCycleName());
					}
				}else if (tempDto.getDataType() == 3){
					try{
						mark = Integer.parseInt(tempDto.getPlanCycleName());
					}catch(Exception e){}
					
					if(mark > -1){
						tempDto.setIsExtension(1);
						tempDto.setPlanCycleName(tempDto.getPlanCycleName()+"-减");
					}else{
						tempDto.setIsExtension(2);
						tempDto.setPlanCycleName("展"+tempDto.getPlanCycleName()+"-减");
					}
				}
			}
			result.get(result.size()-1).setPlanRepayDt("合计");
		}else{
			return new ArrayList<RepaymentPlanBaseDTO>();
		}
		
		return result;
	}
	
	
	// 还款催收内容
	@Override
	public List<RepaymentPlanBaseDTO> selectRepayReminders(RepaymentPlanBaseDTO dto)
			throws ThriftServiceException, TException {
		
		String dayStr=DateUtil.format(DateUtil.getToDay(), "yyyy-MM-dd");
		dto.setPlanRepayDt(dayStr);
		
		Date currDate=DateUtil.format(dayStr, "yyyy-MM-dd");
		
		repaymentMapper.selectRepaymentPlanBaseDTO(dto);
		List<RepaymentPlanBaseDTO> result = dto.getResults();
		
		List<RepaymentPlanBaseDTO> list=Lists.newArrayList();
		boolean flag=false;
		if(null!=result && result.size()>0 && null!=result.get(0) ){
			for(int i=0;i<result.size();i++ ){
				RepaymentPlanBaseDTO tempDto = result.get(i);
				if(null == tempDto ){
					continue;
				}
				//拼写期数名称
				if(tempDto.getDataType() == 1 ){
					tempDto.setPlanCycleName(FinanceTypeEnum.getName(tempDto.getOperType()));
				}else if (tempDto.getDataType() == 2){
					tempDto.setPlanCycleName(tempDto.getPlanCycleNum()+"");
				}else if (tempDto.getDataType() == 3){
					tempDto.setPlanCycleName(tempDto.getPlanCycleName()+FinanceTypeEnum.getName(tempDto.getOperType()).substring(0,1));
				}
				//合计，跳出循环
				if(i==result.size()-1){
					break;
				}
				Date d=DateUtil.format(tempDto.getPlanRepayDt(), "yyyy-MM-dd");
				//判断还款日期是否大于当前时间
				if(d.getTime()<currDate.getTime())
				{
					if(tempDto.getIsReconciliation()!=1){
						list.add(tempDto);
					}
				}else{
					if(flag){
						break;
					}
					if(tempDto.getIsReconciliation()!=1){
						list.add(tempDto);
					}
					
					if(tempDto.getDataType() == 2){
						flag=true;
					}
				}
				
			}
			//result.get(result.size()-1).setPlanRepayDt("合计");
		}else{
			return new ArrayList<RepaymentPlanBaseDTO>();
		}
		
		return list;
	}
    /***
     * 改变提前申请状态
     */
	@Override
	public int changeReqstPro(int reqStatus,int preRepayId) throws ThriftServiceException, TException {
		AdvRepaymentBaseDTO  advRepaymentBaseDTO =new AdvRepaymentBaseDTO();
		advRepaymentBaseDTO.setRequestStatus(reqStatus);
		advRepaymentBaseDTO.setPreRepayId(preRepayId);
		return repaymentMapper.changeReqstPro(advRepaymentBaseDTO);
	}

	@Override
	public int deleteProjectbyPreRepayId(String preRepayId,String projectIds) throws ThriftServiceException, TException {
		String[] repayds = preRepayId.split(",");
		String[] proIds = projectIds.split(",");
		for (int i = 0; i < repayds.length; i++) {
			if(null!=repayds[i] && !"".equals(repayds[i].trim())){
				workflowServiceImpl.deleteProcessInstance(
						Integer.parseInt(repayds[i].trim()),
						Integer.parseInt(proIds[i].trim()),
						WorkflowIdConstant.PREPAYMENT_REQUEST_PROCESS);
			}
		}
		return repaymentMapper.deleteProjectbyPreRepayId(repayds);
	}
	 //判断是否存在没处理的提前还款流程
	@Override
	public int checkpreRepayByProjectId(int projectId) throws ThriftServiceException, TException {
		int ckstatue= repaymentMapper.checkpreRepayByProjectId(projectId);
	
		return ckstatue;
	}

	@Override
	public double getExtensionamtbyprojectId(int projectId) throws ThriftServiceException, TException {
		return repaymentMapper.getExtensionamtbyprojectId(projectId);
	}

	@Override
	public double getOverplusAmt(int loanId) throws ThriftServiceException, TException {
		return repaymentMapper.getOverplusAmt(loanId);
	}
	
	@Override
	public RepaymentPlanBaseDTO getReconciliationDtl(RepayEconciliationDTO dto) throws ThriftServiceException, TException {
		// 1 查询已经对账的数据
		List<RepayEconciliationDTO> list = repaymentMapper.getReconciliationDtl(dto);
		RepaymentPlanBaseDTO baseDto = new RepaymentPlanBaseDTO();
		
		// 2 根据对账类型将对账金额设置到对应的属性中
		if(null != list && list.size() > 0){
			for(RepayEconciliationDTO econciliationDTO : list){
				if(econciliationDTO.getDtlType() == FinanceTypeEnum.TYPE_30.getType()){	// 本金
					baseDto.setShouldPrincipal(econciliationDTO.getDtlAmt());
				}else if (econciliationDTO.getDtlType() == FinanceTypeEnum.TYPE_40.getType()){	// 管理费
					baseDto.setShouldMangCost(econciliationDTO.getDtlAmt());
				}else if (econciliationDTO.getDtlType() == FinanceTypeEnum.TYPE_50.getType()){	// 利息
					baseDto.setShouldInterest(econciliationDTO.getDtlAmt());
				}else if (econciliationDTO.getDtlType() == FinanceTypeEnum.TYPE_60.getType()){	// 其他费用
					baseDto.setShouldOtherCost(econciliationDTO.getDtlAmt());
				}else if (econciliationDTO.getDtlType() == -1000 ){		//逾期利息
					baseDto.setOverdueInterest(econciliationDTO.getDtlAmt());
				}else if (econciliationDTO.getDtlType() == -2000 ){		//逾期罚息
					baseDto.setOverdueFine(econciliationDTO.getDtlAmt());
				}else {		
					baseDto.setShouldOtherCost(econciliationDTO.getDtlAmt());
				}
			}
		}
		return baseDto;
	}

	
}