/**
 * @Title: AfterLoanDivertServiceImpl.java
 * @Package com.xlkfinance.bms.server.afterloan.service
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: gW
 * @date: 2015年3月31日 下午6:21:40
 * @version V1.0
 */
package com.xlkfinance.bms.server.afterloan.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.constant.WorkflowIdConstant;
import com.xlkfinance.bms.rpc.afterloan.LoanDivertService.Iface;
import com.xlkfinance.bms.rpc.afterloan.LoanDivertinfo;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.workflow.WorkflowService;
import com.xlkfinance.bms.server.afterloan.mapper.AfterLoanDivertMapper;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDispose;

@Service("afterLoanDivertServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.afterloan.LoanDivertService")
public class AfterLoanDivertServiceImpl extends WorkflowSpecialDispose  implements Iface{
	
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "afterLoanDivertMapper")
	private AfterLoanDivertMapper afterLoanDivertMapper;
	
	@Resource(name = "workflowServiceImpl")
	private WorkflowService.Iface workflowServiceImpl;
	
	   /**
		 * 保存挪用处理info
		 */
	@Override
	public int insertLoanDivertService(LoanDivertinfo loanDivertinfo) throws TException {
	afterLoanDivertMapper.insertLoanDivertService(loanDivertinfo);
	//是否有启动的流程   状态(0：没有流程启动、1：利率变更、2：提前还款、3：费用减免、4：展期、5：违约、6：挪用)
	updateWorkflowStatus(loanDivertinfo.getProjectId(),6);
	return loanDivertinfo.getPId();
	}
	/**
	 * 查询挪用处理info
	 */
	@Override
	public LoanDivertinfo queryLoanDivertServicebyDivertId(int divertId) throws TException {
		List<LoanDivertinfo>  list=afterLoanDivertMapper.queryLoanDivertbyDivertId(divertId);
		return list.get(0);
	}
	/**
	 * 查询挪用处理info
	 */
	@Override
	public int updateLoanDivertinfo(LoanDivertinfo loanDivertinfo) throws TException {
		int st=afterLoanDivertMapper.updateLoanDivertinfo(loanDivertinfo);
		return st;
	}
	@Override
	public LoanDivertinfo queryProjectDivertbyDivertId(int divertId) throws ThriftServiceException, TException {
		List<LoanDivertinfo>  list=afterLoanDivertMapper.queryProjectDivertbyDivertId(divertId);
		return  list.get(0);
	}
	/**
	 *删除挪用处理info
	 */
	@Override
	public int delectDivertbyId(int divertId,int projectId) throws ThriftServiceException, TException {
		workflowServiceImpl.deleteProcessInstance(divertId,projectId,WorkflowIdConstant.MISAPPROPRIATE_REQUEST_PROCESS);
		int  st=afterLoanDivertMapper.delectDivertbyId(divertId);
		return st;
	}
	//
	@Override
	public int changeReqstDivert(int reqStatus, int divertId) throws ThriftServiceException, TException {
		LoanDivertinfo loanDivertinfo =new LoanDivertinfo();
		loanDivertinfo.setReviewStatus(reqStatus);
		loanDivertinfo.setDivertId(divertId);
		return afterLoanDivertMapper.changeReqstDivert(loanDivertinfo);
	}
	
	public LoanDivertinfo queryDivertinfobyprojectId(int projectId) throws ThriftServiceException, TException {
	List<LoanDivertinfo>	list=	afterLoanDivertMapper.queryDivertinfobyprojectId(projectId);
	if (null!=list&&list.size()>0) {
		return list.get(0);
	}	
	return new LoanDivertinfo();
	}
	
	
}
