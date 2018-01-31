package com.xlkfinance.bms.server.beforeloan.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.beforeloan.Loan;

@MapperScan("loanMapper")
public interface LoanMapper<T, PK> extends BaseMapper<T, PK> {

	/**
	 * 
	 * @Description: 获取贷款序列
	 * @return 最新的贷款序列
	 * @author: Cai.Qing
	 * @date: 2015年2月10日 下午12:41:03
	 */
	public int getSeqBizLoan();

	/**
	 * 
	 * @Description: 根据项目ID查找贷款对象
	 * @param projectId
	 *            项目ID
	 * @return 贷款ID
	 * @author: Cai.Qing
	 * @date: 2015年3月10日 下午12:00:59
	 */
	public Integer getLoanIdByProjectId(int projectId);
	
	/**
	 * @Description: 根据项目ID查找贷款对象
	 * @param loanId 贷款ID
	 * @return 贷款ID
	 * @author: Cai.Qing
	 * @date: 2015年3月10日 下午12:00:59
	 */
	public Integer getProjectIdByLoanId(int loanId);

	/**
	 * 
	 * @Description: 根据项目ID查询 贷款信息和被展期项目的贷款信息
	 * @param projectId
	 * @return
	 * @author: zhanghg
	 * @date: 2015年3月31日 上午11:19:02
	 */
	public List<Loan> getLoansByExtensionProId(int projectId);

	/**
	 * 
	 * @Description: 根据项目ID查询 贷款信息
	 * @param projectId
	 * @return
	 * @author:gW
	 * @date: 2015年3月31日 上午11:19:02
	 */
	public List<Loan> getLoansByProId(int projectId);

	/**
	 * 
	 * @Description: 修改贷款项目的状态
	 * @param projectId
	 *            项目ID
	 * @param requestStatus
	 *            需要修改的状态
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2015年4月15日 下午12:49:02
	 */
	public int updateLoanStatus(@Param(value = "projectId") int projectId, @Param(value = "requestStatus") int requestStatus);

	public void updateByloanId(Loan loan);

	/**
	  * @Description: 是否有启动的流程   状态(0：没有流程启动、1：利率变更、2：提前还款、3：费用减免、4：展期、5：违约、6：挪用)
	  * @param map 
	  * @return int 返回受影响的行数
	  * @author: JingYu.Dai
	  * @date: 2015年5月27日 上午10:58:05
	 */
	public int updateWorkflowStatus(Map<String ,Integer> map);
	
	/**
	  * @Description: 根据Pid查询流程状态
	  * @param pid 贷款信息表Id
	  * @return 流程状态  (0：没有流程启动、1：利率变更、2：提前还款、3：费用减免、4：展期、5：违约、6：挪用)
	  * @author: JingYu.Dai
	  * @date: 2015年5月27日 上午11:53:11
	 */
	public Integer getWorkflowStatusByPid(int pid);
	
	/**
	 * 
	  * @Description: 应还本金,其他费用,管理费,利息相加 = 0 ,更新对账状态为1 已对账,
	  * @param loanId
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年6月7日 下午6:00:38
	 */
	public int updateIsReconciliationStatus(int projectId);
}
