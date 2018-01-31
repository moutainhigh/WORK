/**
 * @Title: MisapprProcessServiceImpl.java
 * @Package com.xlkfinance.bms.server.afterloan.service
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Heng.Wang
 * @date: 2015年1月31日 下午3:21:43
 * @version V1.0
 */
package com.xlkfinance.bms.server.afterloan.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.constant.WorkflowIdConstant;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.rpc.afterloan.BadDebtBean;
import com.xlkfinance.bms.rpc.afterloan.BadDebtBeas;
import com.xlkfinance.bms.rpc.afterloan.BadDebtBeasCriteria;
import com.xlkfinance.bms.rpc.afterloan.BizProjectViolation;
import com.xlkfinance.bms.rpc.afterloan.BreachDisposeBeas;
import com.xlkfinance.bms.rpc.afterloan.BreachDisposeCriteria;
import com.xlkfinance.bms.rpc.afterloan.LoanProRecCondition;
import com.xlkfinance.bms.rpc.afterloan.LoanProReconciliationDtlView;
import com.xlkfinance.bms.rpc.afterloan.MisapprProcess;
import com.xlkfinance.bms.rpc.afterloan.MisapprProcessCriteria;
import com.xlkfinance.bms.rpc.afterloan.MisapprProcessService.Iface;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.customer.CusAcct;
import com.xlkfinance.bms.rpc.customer.CusBlacklistRefuse;
import com.xlkfinance.bms.rpc.file.FileInfo;
import com.xlkfinance.bms.rpc.finance.FinanceService;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.rpc.system.BizFileService;
import com.xlkfinance.bms.rpc.workflow.WorkflowService;
import com.xlkfinance.bms.server.afterloan.mapper.MisapprProcessMapper;
import com.xlkfinance.bms.server.customer.mapper.CusAcctMapper;
import com.xlkfinance.bms.server.customer.mapper.CusBlacklistRefuseMapper;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDispose;


@Service("misapprProcessServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.afterloan.MisapprProcessService")
public class MisapprProcessServiceImpl extends WorkflowSpecialDispose implements Iface  {

	private Logger logger = LoggerFactory.getLogger(MisapprProcessServiceImpl.class);
	
	@Resource(name = "misapprProcessMapper")
	private MisapprProcessMapper<?, ?> misapprProcessMapper;
 
	@Resource(name = "financeServiceImpl")
	private FinanceService.Iface financeService;
	
	@Resource(name = "bizFileServiceImpl")
	private BizFileService.Iface bizFileService; 
	
	@Resource(name = "workflowServiceImpl")
	private WorkflowService.Iface workflowServiceImpl;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "cusAcctMapper")
	private CusAcctMapper cusAcctMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "cusBlacklistRefuseMapper")
	private CusBlacklistRefuseMapper cusBlacklistRefuseMapper;
	
    /**
     * 坏账的查询列表
     */
	@Override
	public List<BadDebtBeas> getBadDebt(BadDebtBeasCriteria badDebtBeasCriteria)
			throws TException {
		try {
			 List<BadDebtBeas> list = misapprProcessMapper.getBadDebt(badDebtBeasCriteria);
			 return list==null?new ArrayList<BadDebtBeas>():list;
			
		} catch (Exception e) {
			logger.error("getBadDebt fail",e);
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE,"失败！");
		}
	}

	/**
	 * 违约查询列表
	 */
	@Override
	public List<MisapprProcess> getMisappProcess(MisapprProcessCriteria misapprProcessCriteria)
			throws TException {
		try {
			 List<MisapprProcess> list = misapprProcessMapper.getMisappProcess(misapprProcessCriteria);
			 return list==null?new ArrayList<MisapprProcess>():list;
			
		} catch (Exception e) {
			logger.error("getMisappProcess fail",e);
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE,"失败！");
		}
	}
	/**
	 * 查询违约总记录数
	 */
	@Override
	public int getMisappProcessCount(MisapprProcessCriteria misapprProcessCriteria) throws TException {
		Integer count = 0;
		try {
			count = misapprProcessMapper.getMisappProcessCount(misapprProcessCriteria);
		} catch (Exception e) {
			logger.error("getMisappProcessCount fail",e);
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE,"失败！");
		}
		return count;
	}

	/**
	 * 违约处理
	 */
	@Override
	public List<BreachDisposeBeas> getBreachDispose(BreachDisposeCriteria breachDisposeCriteria) throws TException {
		try {
			 List<BreachDisposeBeas> list = misapprProcessMapper.getBreachDispose(breachDisposeCriteria);
			 return list==null?new ArrayList<BreachDisposeBeas>():list;
		} catch (Exception e) {
			logger.error("getBreachDispose fail",e);
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE,"失败！");
		}
	}
	
	/**
	  * @Description: 违约处理总记录条数
	  * @param breachDisposeCriteria
	  * @return int 
	  * @throws TException
	  * @author: JingYu.Dai
	  * @date: 2015年5月14日 下午3:03:18
	 */
	@Override
	public int getBreachDisposeTotal(BreachDisposeCriteria breachDisposeCriteria) throws TException{
		 return misapprProcessMapper.getBreachDisposeTotal(breachDisposeCriteria);
	}
	/**
	 * 根据项目ｉｄ -->贷款ＩＤ　　　　 违规处理页面
	 */
	@Override
	public int getLoanId(int pid) throws ThriftServiceException, TException {
		return misapprProcessMapper.getLoanId(pid);
	}

	/**
	 * 创建违约
	 */
	@Override
	public int createBizProjectViolation(BizProjectViolation bizProjectViolation,BadDebtBean badDebtBean)
			throws ThriftServiceException, TException {
		
		// 如果选择的是项目终止，保存对应的呆账患者记录
		if(null != badDebtBean && bizProjectViolation.getIsTermInAtion()==1)
		{
			misapprProcessMapper.createBadDebtBean(badDebtBean);
			bizProjectViolation.setBadId(badDebtBean.getPid());
		}	
		
		 misapprProcessMapper.createBizProjectViolation(bizProjectViolation);
		//是否有启动的流程   状态(0：没有流程启动、1：利率变更、2：提前还款、3：费用减免、4：展期、5：违约、6：挪用)
		updateWorkflowStatus(bizProjectViolation.getProjectId(),5);
		 return bizProjectViolation.getPid();
	}
	/**
	 * 更新违约
	 */
	@Override
	public int updateBizProjectViolation(BizProjectViolation bizProjectViolation,BadDebtBean badDebtBean)
			throws ThriftServiceException, TException {
		// 如果不是项目终止,删除对应的坏账记录
		if(bizProjectViolation.getIsTermInAtion() == 0)
		{
			misapprProcessMapper.deleteBadDebtBeanById(bizProjectViolation.getBadId());
		}
		else
		{
			// 如果存在旧的坏账记录，先删除，在新增
			if(bizProjectViolation.getBadId() != 0)
			{
				misapprProcessMapper.deleteBadDebtBeanById(bizProjectViolation.getBadId());
			}	
			
			misapprProcessMapper.createBadDebtBean(badDebtBean);
			bizProjectViolation.setBadId(badDebtBean.getPid());
		}	
			
		return misapprProcessMapper.updateBizProjectViolation(bizProjectViolation);
	}

	/**
	 * 查询违约
	 */
	@Override
	public BizProjectViolation searchBizProjectViolation(int pid)
			throws ThriftServiceException, TException {
		BizProjectViolation b = new BizProjectViolation();
		b = misapprProcessMapper.searchBizProjectViolation(pid);
		return b==null?new BizProjectViolation():b;
	}

    /**
     * 创建违约项目
     */
	@Override
	public int createProjectViolationFile(FileInfo fileInfo)
			throws ThriftServiceException, TException {
		return misapprProcessMapper.createProjectViolationFile(fileInfo);
	}

    /**
     * 创建文件
     */
	@Override
	public int createFile(FileInfo fileInfo) throws ThriftServiceException,
			TException {
		return misapprProcessMapper.createFile(fileInfo);
	}
 
	
	/**
	 * 编辑违约文件
	 */
	@Override
	public int editFileAndViolation(FileInfo fileInfo)
			throws ThriftServiceException, TException {
		return misapprProcessMapper.editFileAndViolation(fileInfo);
	}
	
	// modify by qcxian start =================================================================================
	
	
		/**
		 * 获取坏账记录信息
		 */
		@Override
		public BadDebtBean getBadDebtBean(int pid) throws TException {
			return  misapprProcessMapper.getBadDebtBean(pid);
		}

 
	@Override
	public boolean deleteFileAndViolation(String pidArray)
			throws ThriftServiceException, TException {
		boolean del = true;
		String [] pids = pidArray.split(",");
		for (String string : pids) {
			int pid = Integer.parseInt(string);
			FileInfo fileVio=misapprProcessMapper.getBizProjectViolationFile(pid);
			misapprProcessMapper.deleteFile(fileVio.getFileId());
			//misapprProcessMapper.deleteFileAndViolation(fileVio.getViolationId());
			del = misapprProcessMapper.deleteFileAndViolationFile(pid);
		}
		return del;
	}

    /**
     * 删除文件
     */
	@Override
	public boolean deleteFile(int array) throws ThriftServiceException,
			TException {
		return misapprProcessMapper.deleteFileAndViolationFile(array);
	}
	
	/**
	  * @Description: 删除违约处理数据  根据pid
	  * @param pid 
	  * @return int 删除的记录条数
	  * @author: JingYu.Dai
	  * @date: 2015年5月14日 下午5:41:28
	  */
	@Override
	public int deleteBreachDispose(int pid,int projectId)throws ThriftServiceException, TException {
		workflowServiceImpl.deleteProcessInstance(pid,projectId,WorkflowIdConstant.BREACH_OF_CONTRACT_REQUEST_PROCESS);
		return misapprProcessMapper.deleteBreachDispose(pid);
	}


	@Override
	public FileInfo getBizProjectViolationFile(int pid)
			throws ThriftServiceException, TException {
		return misapprProcessMapper.getBizProjectViolationFile(pid);
	}
	
	
	
	// modify by qcxian start =================================================================================

	//  资料文件附件相关
	
	@Override
	public List<FileInfo> getFileDataList(FileInfo fileInfo)
			throws ThriftServiceException, TException {
		return misapprProcessMapper.getFileDataList(fileInfo);
	}

    /**
     * 查总记录数
     */
	@Override
	public int getFileDataTotal(FileInfo fileInfo)
			throws ThriftServiceException, TException {
		return misapprProcessMapper.getFileDataTotal(fileInfo);
	}

	/**
	 * 通过主键查询违约记录
	 */
	@Override
	public BizProjectViolation getBizProjectViolationById(int pid) throws TException {
		return misapprProcessMapper.getBizProjectViolationById(pid);
	}
    /**
     * 删除文件
     */
	@Override
	public boolean deleteFileData(int dataId,int fileId) throws TException {
		
		 bizFileService.deleteBizFileByPid(fileId);
		 // 删除业务文件表中的数据
		 misapprProcessMapper.deleteFileAndViolationFile(dataId);
		 
		 return true;
	}
    /**
     *  新增或者更改业务资料文件
     */
	@Override
	public int saveOrUpdateFileData(FileInfo fileInfo, BizFile bizFile) throws TException {
		int result = 0;
		    	
		    	// 修改
		    	if(fileInfo.getDataId()==0)
		    	{
		    		bizFileService.saveBizFile(bizFile);
		    		fileInfo.setFileId(bizFile.getPid());
		    		return misapprProcessMapper.createProjectViolationFile(fileInfo);
		    	}	
		    	else
		    	{
		    		bizFileService.updateBizFile(bizFile);
		    		//misapprProcessMapper.editFileAndViolation(fileInfo);
		    	}	
    	
    	return result;
	}
	/**
	 *  更新坏账信息
	 */
		@Override
		public int updateBadDebtBean(BadDebtBean badDebtBean,int loanId) throws TException {
			
			//  如果是完成状态，激活最新的版本
			if(badDebtBean.getReviewStatus()==1)
			{
				financeService.activateTheLatestPlan(loanId);
			}
			
			return misapprProcessMapper.updateBadDebtBean(badDebtBean);
		}

		/**
		 * 新增坏账信息
		 */
		@Override
		public int createBadDebtBean(BadDebtBean badDebtBean) throws TException {
			 misapprProcessMapper.createBadDebtBean(badDebtBean);
			 return badDebtBean.getPid();
		}
		/**
		 * 更新患者信息状态(默认创建的时候是0，违约流程审批通过后，需要)
		 */
		@Override
		public int updateBadDebtBeanStatus(BadDebtBean badDebtBean) throws TException {
			return misapprProcessMapper.updateBadDebtBeanStatus(badDebtBean);
		}

		// modify by qcxian end =================================================================================
		
		// add by yql start
		@Override
		public List<LoanProReconciliationDtlView> getProjectReconciliationList(
				LoanProRecCondition loanProRecCondition)
				throws ThriftServiceException, TException {
			List<LoanProReconciliationDtlView> list=misapprProcessMapper.getProjectReconciliationList(loanProRecCondition);
			return list!=null ?list : new ArrayList<LoanProReconciliationDtlView>();
		}
		//add by yql end

		//根据项目ID查客户
		@Override
		public int searchAcctIdByProjectId(int projectId)
				throws ThriftServiceException, TException {
			return  misapprProcessMapper.searchAcctIdByProjectId(projectId);
		}

        /**
         * 添加违约信息
         */
		@Override
		public int addBlacklistRefuse(int projectId, int treatyId)
				throws ThriftServiceException, TException {
			logger.info("misapprProcessServiceImpl addBlacklistRefuse projectId["+projectId+"] treatyId["+treatyId+"]");
			try {
				int isBalck=misapprProcessMapper.getViolationIsBacklist(treatyId);
				if(isBalck==1){
					int acctId =searchAcctIdByProjectId(projectId);
					CusAcct cusAcct = new CusAcct();
					cusAcct.setPid(acctId);
					cusAcct.setCusStatus(2);
					cusAcct.setStatus(1);
					
					//黑名单
					CusBlacklistRefuse cusBlacklistRefuse = new CusBlacklistRefuse();
					cusBlacklistRefuse.setCusAcct(cusAcct);
					cusBlacklistRefuse.setListType("2");
					cusBlacklistRefuse.setStatus(1);
					
					cusAcctMapper.updateCusStatuss(acctId); 
					cusBlacklistRefuseMapper.insertBlacklistRefuse(cusBlacklistRefuse);
				}
			} catch (Exception e) {
				logger.error("违约信息 客户加入黑名单  "+e.getMessage());
				e.printStackTrace();
			}
			return 0;
		}

}
