package com.xlkfinance.bms.server.beforeloan.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.beforeloan.BizLoanApprovalInvoice;
import com.xlkfinance.bms.rpc.beforeloan.LoanApprovalInvoiceInfo;
import com.xlkfinance.bms.rpc.system.BizFile;

/**
 *   
 * 
 * @Title: BizProjectMeetingMapper.java
 * @Package com.xlkfinance.bms.server.beforeloan.mapper
 * @Description:线下决议会议纪要
 * @author Ant.Peng  
 * @date 2015年2月5日 下午8:31:11
 * @version V1.0  
 */
@MapperScan("loanApprovalPaperMapper")
public interface LoanApprovalPaperMapper<T, PK> extends BaseMapper<T, PK> {
    //保存放款审批单
	public int saveBizLoanApprovalInvoice(BizLoanApprovalInvoice bizLoanApprovalInvoice);

	public List<BizFile> obtainLoanApprovalPaperFile(int projectId);
	//根据项目ID查贷款项目ID
	public int searchBizLoanPidByProjectId(int projectId);
	//放款审批单的查询列表
	public LoanApprovalInvoiceInfo listApprovalInvoiceInfo(int projectId);
	//查放款审批单的总记录数
	public Integer obtainLoanApprovalPaperFileTotal(int projectId);
}
