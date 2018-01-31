package com.xlkfinance.bms.server.beforeloan.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.rpc.beforeloan.BizLoanApprovalInvoice;
import com.xlkfinance.bms.rpc.beforeloan.LoanApprovalInvoiceInfo;
import com.xlkfinance.bms.rpc.beforeloan.LoanApprovalPaperService.Iface;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanApprovalPaperMapper;

/**   
* @Title: LoanApprovalPaperServiceImpl.java 
* @Package com.xlkfinance.bms.server.beforeloan.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author Ant.Peng   
* @date 2015年2月5日 下午8:14:27 
* @version V1.0   
*/ 
@SuppressWarnings("unchecked")
@Service("loanApprovalPaperServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.beforeloan.LoanApprovalPaperService")
public class LoanApprovalPaperServiceImpl implements Iface {
	private Logger logger = LoggerFactory.getLogger(LoanApprovalPaperServiceImpl.class);
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "loanApprovalPaperMapper")
	private LoanApprovalPaperMapper loanApprovalPaperMapper;

	@Override
	public int saveBizLoanApprovalInvoice(
			BizLoanApprovalInvoice bizLoanApprovalInvoice)
			throws ThriftServiceException, TException {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 
	 * @Description:  保存放款审批单
	 * @return 
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public int saveBizLoanApprovalInvoiceByProjectId(int projectId,
			BizLoanApprovalInvoice bizLoanApprovalInvoice)
			throws ThriftServiceException, TException {
		try {
			
			int loanId = loanApprovalPaperMapper.searchBizLoanPidByProjectId(projectId);
			bizLoanApprovalInvoice.setLoanId(loanId);
			
			return loanApprovalPaperMapper.saveBizLoanApprovalInvoice(bizLoanApprovalInvoice);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_ADD,"保存放款审批单信息失败！");
		}
	}

	/**
	 * 
	 * @Description:  查询放款审批单的附件信息
	 * @return 
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public List<BizFile> obtainLoanApprovalPaperFile(int projectId)
			throws ThriftServiceException, TException {
try {
		List<BizFile> bizFileList = loanApprovalPaperMapper.obtainLoanApprovalPaperFile(projectId);
		return bizFileList==null?new ArrayList<BizFile>():bizFileList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_QUERY,"查询放款审批单的附件信息失败！");
		}
	}
    /**
     * 查询生成放款审批单列表
     */
	@Override
	public LoanApprovalInvoiceInfo listApprovalInvoiceInfo(int projectId)
			throws ThriftServiceException, TException {
		try {
			LoanApprovalInvoiceInfo loanApprovalInvoiceInfo = loanApprovalPaperMapper.listApprovalInvoiceInfo(projectId);
			return loanApprovalInvoiceInfo;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_QUERY,"查询生成放款审批单的信息失败！");
		}
	}
	/**
	 * 查询放款审批单的总数
	 */
	@Override
	public int obtainLoanApprovalPaperFileTotal(int projectId)
			throws ThriftServiceException, TException {
		try {
			Integer count = loanApprovalPaperMapper.obtainLoanApprovalPaperFileTotal(projectId);
			return count==null?0:count;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_QUERY,"查询放款审批单的总数失败！");
		}
	}

	

}
