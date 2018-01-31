package com.xlkfinance.bms.server.repayment.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.constant.WorkflowIdConstant;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectApprovalInfo;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.repayment.InterestChgApplyView;
import com.xlkfinance.bms.rpc.repayment.RegAdvapplyFileview;
import com.xlkfinance.bms.rpc.repayment.RepayCgInterestDTO;
import com.xlkfinance.bms.rpc.repayment.RepayCgInterestView;
import com.xlkfinance.bms.rpc.repayment.RepayManageLoanService.Iface;
import com.xlkfinance.bms.rpc.repayment.UploadinstAdvapplyBaseDTO;
import com.xlkfinance.bms.rpc.workflow.WorkflowService;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectApprovalInfoMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectMapper;
import com.xlkfinance.bms.server.repayment.mapper.RepayManageLoanMapper;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDispose;


/**
  * @ClassName: 类描述： 创建人：gaoWen 创建时间：2015年3月12日 下午2:36:06
  * @Description: TODO
  * @author: JingYu.Dai
  * @date: 2015年8月3日 下午3:46:05
 */
@Service("repayManageLoanService")
@ThriftService(service = "com.xlkfinance.bms.rpc.repayment.RepayManageLoanService")
public class RepayManageLoanServiceImpl extends WorkflowSpecialDispose implements Iface  {
	
	@Resource(name = "repayManageLoanMapper")
	private RepayManageLoanMapper<?, ?> repayManageLoanMapper;
	
	@Resource(name = "workflowServiceImpl")
	private WorkflowService.Iface workflowServiceImpl;
	
	@Resource(name = "projectApprovalInfoMapper")
	private ProjectApprovalInfoMapper<?, ?> projectApprovalInfoMapper;
	
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "projectMapper")
	private ProjectMapper projectMapper;


	/**
	  * @Description: 描述：利率表更查询
	  * @param repayCgInterestDTO
	  * @return List<RepayCgInterestView> 
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:46:39
	  */
	@Override
	public List<RepayCgInterestView> selectLoanInterestDetail(
			RepayCgInterestDTO repayCgInterestDTO) throws TException {
		List<RepayCgInterestView> list=	repayManageLoanMapper.selectLoanInterestDetail(repayCgInterestDTO);
		if (null==list||list.size()<1) {
			return new ArrayList<RepayCgInterestView>();
		}
		return list;
	}
	
	/**
	  * @Description: 描述：保存利率表更申请
	  * @param uploadinstAdvapplyBaseDTO
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:47:42
	  */
	@Override
	public int uploadinstCgapply(
			UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO)
			throws ThriftServiceException, TException {
		//描述：保存文件
		repayManageLoanMapper.insertInstFileapply(uploadinstAdvapplyBaseDTO);
		uploadinstAdvapplyBaseDTO.setFileId(uploadinstAdvapplyBaseDTO.getPId());
		repayManageLoanMapper.insertInstCgapply(uploadinstAdvapplyBaseDTO);
		return uploadinstAdvapplyBaseDTO.getPId();
	}
	
	/**
	  * @Description:描述：利率表更查询申请状态中的
	  * @param repayCgInterestDTO
	  * @return List<RepayCgInterestView>
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:49:06
	 */
	@Override
	public List<RepayCgInterestView> selectLoanRequestInterestDetail(
			RepayCgInterestDTO repayCgInterestDTO)
			throws ThriftServiceException, TException {
		List<RepayCgInterestView> list=	repayManageLoanMapper.selectLoanRequestInterestDetail(repayCgInterestDTO);
		if (null==list||list.size()<1) {
			return new ArrayList<RepayCgInterestView>();
		}
		return list;
	
	}
	/**
	  * @Description:描述：增加利率变更申请中的利息
	  * @param repayCgInterestDTO
	  * @return List<RepayCgInterestView>
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:49:06
	  */
	@Override
	public int insertRepayCgapplyInfo(RepayCgInterestDTO repayCgInterestDTO)
			throws ThriftServiceException, TException {
		int interestId = 0;
		 List<ProjectApprovalInfo> palist = null;
		if(repayCgInterestDTO.getInterestChgId()>0){//大于0说明为空 进入修改方法
			repayManageLoanMapper.updateRepayCgapplyInfo(repayCgInterestDTO);
		}else{
			// 查出被利率变更的项目,将是否可提前还款、是否退还利息、项目调查结论 复制到利率表中
			@SuppressWarnings("unchecked")
			List<Project> list = projectMapper.getLoanProjectByProjectId(repayCgInterestDTO.getProjectId());
			if(null != list && list.size() > 0){
				repayCgInterestDTO.setIsAllowPrepay(list.get(0).getIsAllowPrepay());
				repayCgInterestDTO.setIsReturnInterest(list.get(0).getIsReturnInterest());
				repayCgInterestDTO.setSurveyResult(list.get(0).getSurveyResult());
			}
			
			 repayCgInterestDTO.setStatus(1);
			 repayCgInterestDTO.setRequestStatus(0);
			 repayManageLoanMapper.insertInstCgLoanapply(repayCgInterestDTO);
			 repayCgInterestDTO.setInterestChgId(repayCgInterestDTO.getPId());
			 repayManageLoanMapper.insertInstCgLoan(repayCgInterestDTO);
			 interestId = repayCgInterestDTO.getPId();
			 
			 //通过项目id把贷审会要求信息保存到新表中 利率变更与其它流程不同
			 palist = projectApprovalInfoMapper.getProjectApprovalInfo(repayCgInterestDTO.getProjectId());
			for (ProjectApprovalInfo projectApprovalInfo : palist) {
				projectApprovalInfo.setInterestChgId(interestId);
				projectApprovalInfoMapper.addProjectApprovalInfoRes(projectApprovalInfo);
			}
			 
			//是否有启动的流程   状态(0：没有流程启动、1：利率变更、2：提前还款、3：费用减免、4：展期、5：违约、6：挪用)
			 updateWorkflowStatus(repayCgInterestDTO.getProjectId(),1);
			}
		return interestId;
	}
	
	/**
	  * @Description: 描述：更新保存的利息变更表
	  * @param repayCgInterestDTO
	  * @return
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:51:59
	  */
	@Override
	public int updateRepayCgapplyInfo(RepayCgInterestDTO repayCgInterestDTO)
			throws ThriftServiceException, TException {
	int st= repayManageLoanMapper.updateRepayCgapplyInfo(repayCgInterestDTO);	
	return st;
	}
	
	/**
	  * @Description: 查询保存的文件
	  * @param uploadinstAdvapplyBaseDTO
	  * @return List<RegAdvapplyFileview>
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:59:18
	  */
	@Override
	public List<RegAdvapplyFileview> queryRepayCgapplyFile(
			UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO)
			throws ThriftServiceException, TException {
		List<RegAdvapplyFileview> list=	 repayManageLoanMapper.queryRepayCgapplyFile(uploadinstAdvapplyBaseDTO);	
		if (null==list||list.size()<1) {
			return new ArrayList<RegAdvapplyFileview>();
		}
		return list;	
	}
	
	/**
	  * @Description: 查询保存的文件总数
	  * @param uploadinstAdvapplyBaseDTO
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:59:50
	  */
	@Override
	public int queryRepayCgapplyFileCount(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO)
			throws ThriftServiceException, TException {
		return repayManageLoanMapper.queryRepayCgapplyFileCount(uploadinstAdvapplyBaseDTO);	
	}
	/**
	  * @Description: 查询利率变更申请中的利息根据利率变更id
	  * @param repayCgInterestDTO
	  * @return List<RepayCgInterestView>
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午4:00:27
	  */
	@Override
	public List<RepayCgInterestView> selectLoanInterestDetailbyProces(
			RepayCgInterestDTO repayCgInterestDTO)
			throws ThriftServiceException, TException {
		List<RepayCgInterestView> list=	 repayManageLoanMapper.selectLoanInterestDetailbyProces(repayCgInterestDTO);	
		if (null==list||list.size()<1) {
			return new ArrayList<RepayCgInterestView>();
		}
		return list;	
	}
	
	/**
	  * @Description: 查询利率变更申请中的利息根据利率变更id
	  * @param repayCgInterestDTO
	  * @return List<RepayCgInterestView>
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午4:00:55
	  */
	@Override
	public List<RepayCgInterestView> selectLoanRequestInterestDetailbyProces(
			RepayCgInterestDTO repayCgInterestDTO)
			throws ThriftServiceException, TException {
		List<RepayCgInterestView> list=	 repayManageLoanMapper.selectLoanRequestInterestDetailbyProces(repayCgInterestDTO);	
		if (null==list||list.size()<1) {
			return new ArrayList<RepayCgInterestView>();
		}
		return list;
	}
	/**
	  * @Description: 利率变更任务流完成时候利息变更生效
	  * @param repayCgInterestDTO
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午4:01:25
	  */
	@Override
	public int intRepayCgapplyInfoEnd(RepayCgInterestDTO repayCgInterestDTO)
			throws ThriftServiceException, TException {
		int st= repayManageLoanMapper.updateintRepayCgapplyInfoEnd(repayCgInterestDTO);	
		return st;
	}
	
	/**
	  * @Description: 修改利率变更的申请状态
	  * @param repayCgInterestDTO
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午4:01:53
	  */
	@Override
	public int changeReqstCg(int reqStatus, int interestChgId) throws ThriftServiceException, TException {
		RepayCgInterestDTO repayCgInterestDTO =new  RepayCgInterestDTO();
		repayCgInterestDTO.setRequestStatus(reqStatus);
		repayCgInterestDTO.setInterestChgId(interestChgId);
		return repayManageLoanMapper.changeReqstCg(repayCgInterestDTO);
	}
	
	/**
	  * @Description: 删除利率变更
	  * @param interestChgId
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午4:02:47
	  */
	@Override
	public int deleteProjectbyinterestChgId(String interestChgId,String projectId) throws ThriftServiceException, TException {
		String[] ics = interestChgId.split(",");
		String[] proIds = projectId.split(",");
		int delCount = 0;
		for (int i = 0; i < ics.length; i++) {
			workflowServiceImpl.deleteProcessInstance(Integer.parseInt(ics[i]), Integer.parseInt(proIds[i]),
					WorkflowIdConstant.INTEREST_CHANGE_REQUEST_PROCESS);// 删除流程
			delCount += repayManageLoanMapper
					.deleteProjectbyinterestChgId(Integer.parseInt(ics[i]));
		}
		return delCount;
	}
	@Override
	public int checkpreRepayByProjectId(int projectId) throws ThriftServiceException, TException {
		return repayManageLoanMapper.checkpreRepayByProjectId(projectId);
	}

	/**
	  * @Description: 利息变更申请书
	  * @param interestChgId
	  * @return InterestChgApplyView
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午4:02:47
	  */
	@Override
	public InterestChgApplyView makeCgApplyFile(int interestChgId) throws ThriftServiceException, TException {
	List<InterestChgApplyView> list=null;
	List<InterestChgApplyView> listGuarantee=null;
	InterestChgApplyView chginfo=null;
	
	list=repayManageLoanMapper.makeCgApplyFile(interestChgId);
		if(null!=list&&list.size()>0){
			chginfo=list.get(0);
			String [] requestDttm = chginfo.getRequestDttm().split(" ");//以空格区分得到天
			chginfo.setRequestDttm(requestDttm[0]);
			chginfo.setContractInterest(chginfo.getMonthLoanInterest()+chginfo.getMonthLoanMgr());
			listGuarantee=	repayManageLoanMapper.makeCgApplyGuarantee(list.get(0).getProjectId());
			if (null!=listGuarantee&&listGuarantee.size()>0) {
			for (int i = 0; i < listGuarantee.size(); i++) {
				if (null!=listGuarantee.get(i).getLookupDesc()&&listGuarantee.get(i).getLookupDesc().equals("质押")) {
					chginfo.setThPledgeGuarantee("■质押");
				}
				if (null!=listGuarantee.get(i).getLookupDesc()&&listGuarantee.get(i).getLookupDesc().equals("抵押")) {
					chginfo.setPledgeGuarantee("■抵押");			
								}
				if (null!=listGuarantee.get(i).getLookupDesc()&&listGuarantee.get(i).getLookupDesc().equals("保证")) {
					chginfo.setGuaranteeGuarantee("■保证");
				}
				if (null!=listGuarantee.get(i).getLookupDesc()&&listGuarantee.get(i).getLookupDesc().equals("信用")) {
					chginfo.setCreditGuarantee("■信用");
				}
			}
			
			}else {
				
					chginfo.setThPledgeGuarantee("□质押");
				
					chginfo.setPledgeGuarantee("□抵押");			
				
					chginfo.setGuaranteeGuarantee("□保证");
				
					chginfo.setCreditGuarantee("□信用");
				
			}
			if(chginfo.getCreditGuarantee()==null||chginfo.getCreditGuarantee().equals("")){
				chginfo.setCreditGuarantee("□信用");
			}
			if(chginfo.getGuaranteeGuarantee()==null||chginfo.getGuaranteeGuarantee().equals("")){
				chginfo.setGuaranteeGuarantee("□保证");
			}
			if(chginfo.getPledgeGuarantee()==null||chginfo.getPledgeGuarantee().equals("")){
				chginfo.setPledgeGuarantee("□抵押");
			}
			if(chginfo.getThPledgeGuarantee()==null||chginfo.getThPledgeGuarantee().equals("")){
				chginfo.setThPledgeGuarantee("□质押");
			}
			return chginfo;	
		}
		
		return new  InterestChgApplyView();	
				
	}
	
	/**
	  * @Description: 通过项目ID得出利率变更ID
	  * @param projectId
	  * @return InterestChgApplyView
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午4:05:31
	  */
	@Override
	public InterestChgApplyView queryInterestChgId(int projectId) throws TException {
		InterestChgApplyView interestChgId  = null;
		try {
			interestChgId= repayManageLoanMapper.queryInterestChgId(projectId);
		} catch (Exception e) {
			e.fillInStackTrace();
		}
		return interestChgId==null?new InterestChgApplyView():interestChgId;
	}
	
	/**
	  * @Description: 查询利率变更申请中的利息根据利率变更id并且是已归档
	  * @param repayCgInterestDTO
	  * @return List<RepayCgInterestView>
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午4:05:57
	  */
	@Override
	public List<RepayCgInterestView> selectLoanRequestInterestDetailbyProcesByStatus(
			RepayCgInterestDTO repayCgInterestDTO)
			throws ThriftServiceException, TException {
		List<RepayCgInterestView> list=	 repayManageLoanMapper.selectLoanRequestInterestDetailbyProcesByStatus(repayCgInterestDTO);	
		if (null==list||list.size()<1) {
			return new ArrayList<RepayCgInterestView>();
		}
		return list;
	}
	
	/**
	  * @Description: 通过利率id查出变更前的利率方便做展示
	  * @param repayCgInterestDTO
	  * @return
	  * @throws ThriftServiceException
	  * @throws TException
	  * @author: Rain.Lv
	  * @date: 2015年6月19日 下午3:17:35
	  */
	@Override
	public List<RepayCgInterestView> queryLoanRes(
			RepayCgInterestDTO repayCgInterestDTO)
			throws ThriftServiceException, TException {
		List<RepayCgInterestView> list=	 repayManageLoanMapper.queryLoanRes(repayCgInterestDTO);	
		if (null==list||list.size()<1) {
			return new ArrayList<RepayCgInterestView>();
		}
		return list;	
	}
	
	/**
	  * @Description: 保存利率变更贷审会意见信息
	  * @param repayCgInterestDTO
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年6月27日 上午11:16:46
	  */
	@Override
	public int saveProcedures(RepayCgInterestDTO repayCgInterestDTO)
			throws ThriftServiceException, TException {
		return repayManageLoanMapper.updateProceduresByPrimaryKey(repayCgInterestDTO);
	}
	@Override
	public RepayCgInterestView getRepayCgInterestByPid(int pid)
			throws ThriftServiceException, TException {
		return null;
	}
	
	/**
	 * 
	  * @Description: 将利率变更是否可提前还款、是否退还利息、项目调查结论同步至项目表中
	  * @author: Zhangyu.Hoo
	  * @date: 2015年6月27日 下午4:28:20
	 */
	public int syncProcedureToProject(RepayCgInterestDTO repayCgInterestDTO)throws ThriftServiceException, TException {
		List<RepayCgInterestView> list = repayManageLoanMapper.selectLoanInterestDetailbyProces(repayCgInterestDTO);
		Project project = new Project();
		int result = 0 ;
		if(null!=list && list.size() > 0){
			project.setPid(repayCgInterestDTO.getProjectId());
			project.setIsAllowPrepay(list.get(0).getIsAllowPrepay());
			project.setIsReturnInterest(list.get(0).getIsReturnInterest());
			project.setSurveyResult(list.get(0).getSurveyResult());
			result = projectMapper.updateProceduresByPrimaryKey(project);
		}
		return result;
	}

	 

}
