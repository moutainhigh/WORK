package com.xlkfinance.bms.server.repayment.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
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
import com.xlkfinance.bms.rpc.repayment.OverInterest;
import com.xlkfinance.bms.rpc.repayment.PreApplyRepayBaseDTO;
import com.xlkfinance.bms.rpc.repayment.ProjectComplete;
import com.xlkfinance.bms.rpc.repayment.ProjectCompleteFile;
import com.xlkfinance.bms.rpc.repayment.RealtimePlan;
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
import com.xlkfinance.bms.rpc.repayment.RepaymentView;
import com.xlkfinance.bms.rpc.repayment.RepaymentViolationView;
import com.xlkfinance.bms.rpc.repayment.SaveAdvRepaymentBaseDTO;
import com.xlkfinance.bms.rpc.repayment.SettleFile;
import com.xlkfinance.bms.rpc.repayment.UploadinstAdvapplyBaseDTO;

@MapperScan("repaymentMapper")
public interface RepaymentMapper<T, PK> extends BaseMapper<T, PK> {

	
	public int getCostDerateTabulationCount(CostDerateTabulationDTO costDerateTabulationDTO);
	
	public List<CostDerateTabulationView> getCostDerateTabulation(
			CostDerateTabulationDTO costDerateTabulationDTO);
	
	
	public int getCostDerateCount(CostDerateDTO costDerateDTO);
	
	public List<CostDerateView> getCostDerate(
			CostDerateDTO costDerateDTO);
	
	public int getAvailabilityTabulationCount(AvailabilityChangeTabulationDTO availabilityChangeTabulationDTO);
	
	public List<AvailabilityChangeTabulationView> getAvailabilityTabulation(
			AvailabilityChangeTabulationDTO availabilityChangeTabulationDTO);
	
	
	public List<AvailabilityChangeView> getChangeList(
			AvailabilityChangeDTO availabilityChangeDTO);
	
	public int getChangeTotaleCount(AvailabilityChangeDTO availabilityChangeDTO);
	
	public List<RepaymentView> getRepaymentList(
			RepaymentBaseDTO repaymentBaseDTO);

	public int getTempTotaleCount(RepaymentBaseDTO repaymentBaseDTO);

	public int getAdvRepayTotaleCount(AdvRepaymentBaseDTO advRepaymentBaseDTO);

	public List<AdvRepaymentView> getAdvrepaymenturl(
			AdvRepaymentBaseDTO advRepaymentBaseDTO);

	public List<AdvapplyRepaymentView> getAdvapplyrepaymenturl(
			AdvapplyRepaymentBaseDTO advapplyRepaymentBaseDTO);

	public int getAdvapplyRepayTotaleCount(
			AdvapplyRepaymentBaseDTO advapplyRepaymentBaseDTO);

	public int saveAdvRepayment(SaveAdvRepaymentBaseDTO saveAdvRepaymentBaseDTO);

	public int executiveOperation(ExecutiveOperBaseDTO executiveOperBaseDTO);

	public int delectApplyRepay(ApplyfileuploadBaseDTO applyfileuploadBaseDTO);

	public int insertPreApplyRepay(PreApplyRepayBaseDTO preApplyRepayBaseDTO);

	public int uploadinstAdvapply(
			UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO);

	public void uploadinstAdvapplyId(
			UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO);

	public List<RepaymentPlanBaseDTO> selectRepaymentPlan(int loanId);
			

	public List<RegAdvapplyRepayView> queryRegAdvapplyRepay(
			RegAdvapplyRepayDTO regAdvapplyRepayDTO);

	public List<RegAdvapplyFileview> queryRegAdvapplyFilelist(
			UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO);
	
	public int queryRegAdvapplyFileCount(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO);

	public int delectFilebyId(List<String> list);

	public OverInterest selectRepaymentOverInterest(
			AdvapplyRepaymentBaseDTO advapplyRepaymentBaseDTO);

	public List<RealtimePlan> selectRepaymentrealtimePlan(
			AdvapplyRepaymentBaseDTO advapplyRepaymentBaseDTO);

	public List<RepayEconciliationDTO> selectRepaymentReconciliation(
			AdvapplyRepaymentBaseDTO advapplyRepaymentBaseDTO);

	// 违约
	public List<RepaymentViolationView> selectRepaymentViolation(
			AdvapplyRepaymentBaseDTO advapplyRepaymentBaseDTO);

	// 坏账
	public List<RepayProjectBadDTO> selectRepaymentProjectBad(
			AdvapplyRepaymentBaseDTO advapplyRepaymentBaseDTO);

	// 挪用
	public List<RepaymentDivertView> selectRepaymentDivert(
			AdvapplyRepaymentBaseDTO advapplyRepaymentBaseDTO);
	
	public List<CusBusiness> getCusBusiness(CusBusiness cusBusiness);
	
	public int getCusBusinessTotal(CusBusiness cusBusiness);
	public List<CusBusiness> getLoanSettle(CusBusiness cusBusiness);
	public ProjectComplete getLoanSettleOne(int projectId);
	public int saveLoanSettle(ProjectComplete projectComplete);
	public int updateLoanSettle(ProjectComplete projectComplete);
	//生成结清文件
	public SettleFile getSettleFile(int projectId);
	//展期生成结清文件
	public SettleFile getSettleFileRepayment(int projectId);
	
	public int saveProjectCompleteFile(ProjectCompleteFile projectCompleteFile);
	//pid=结清表pid
	public List<ProjectCompleteFile> getProjectCompleteFile(int pid);
	public boolean editProjectCompleteFile(ProjectCompleteFile projectCompleteFile);
	public boolean deleteTempLate(int pid);
	public int getLoanSettleTotal(CusBusiness cusBusiness);
	public int deleteLoanSettle(List<String> list);
	  /**
			 * 描述：   逾期查询
			 * 创建人：gaoWen   
			 * 创建时间：2015年3月2日 下午3:35:24   
			 */
	public List<RepayOverdueDTO> getRepaymentOverdueList(
			RepayOverdueView repayOverdueView);
	 /**
	 * 描述：   删除项目操作
	 * 创建人：gaoWen   
	 * 创建时间：2015年3月2日 下午3:35:24   
	 */
	public int deleteProjectbyId(List<String> list);
	// 还款管理/工作流的提前还款跳转
	public List<RegAdvapplyRepayView> getrepaydatilbyProcess(int preRepayId);
	// 还款管理/工作流的提前还款的详情查询
	public List<RegAdvapplyRepayView> getrepayadvdatilbyProcess(int preRepayId);

	public int updateadvApplyrepayment(PreApplyRepayBaseDTO preApplyRepayBaseDTO);
	
	/**
	 * 
	  * @Description: 获取还款计划表、贷款试算表
	  * @param loanId
	  * @return RepaymentPlanBaseDTO
	  * @author: Zhangyu.Hoo
	  * @date: 2015年3月27日 下午4:04:36
	 */
	public RepaymentPlanBaseDTO selectRepaymentPlanBaseDTO(RepaymentPlanBaseDTO dto);
	  /***
     * 改变提前申请状态
     */
	public int changeReqstPro(AdvRepaymentBaseDTO  advRepaymentBaseDTO);
	  /***
     * 删除提前申请
     */
	public int deleteProjectbyPreRepayId(String [] pids);
	 //判断是否存在没处理的提前还款流程
	public int checkpreRepayByProjectId(int projectId);

	public int updateAdvLoadFileInfo(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO);

	public int updateACgLoadFileInfo(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO);

	public int updateFeedLoadFileInfo(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO);

	public double getExtensionamtbyprojectId(int projectId);

	public Double getOverfineint(int loanId);
	
	/**
	 * 
	  * @Description: 获取贷款剩余本金
	  * @param loanId
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年5月18日 下午5:04:01
	 */
	public Double getOverplusAmt(int loanId);
	
	/**
	 * 
	  * @Description: 还款催收时,获取已经对账的金额明细
	  * @param dto
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年7月31日 下午3:36:39
	 */
	public List<RepayEconciliationDTO> getReconciliationDtl(RepayEconciliationDTO dto);
	/**
	 * 
	 * queryPrerepayCount:根据项目ID查询项目关联的提前还款信息总数. <br/>
	 * @author baogang
	 * @param projectId
	 * @return
	 *
	 */
	public int queryPrerepayCount(int projectId);
}
