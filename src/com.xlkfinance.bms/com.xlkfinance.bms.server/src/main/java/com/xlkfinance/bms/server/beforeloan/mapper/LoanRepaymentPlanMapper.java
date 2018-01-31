/**
 * @Title: LoanRepaymentPlanMapper.java
 * @Package com.xlkfinance.bms.server.beforeloan.mapper
 * @Description: 还款计划 表-中间表
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: zhanghg
 * @date: 2015年2月2日 上午11:24:27
 * @version V1.0
 */
package com.xlkfinance.bms.server.beforeloan.mapper;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.beforeloan.RepaymentLoanInfo;
import com.xlkfinance.bms.rpc.repayment.RepaymentPlanBaseDTO;
@MapperScan("loanRepaymentPlanMapper")
public interface LoanRepaymentPlanMapper<T, PK> extends BaseMapper<T, PK> {
	
	/**
	 * 
	  * @Description: 获取最大版本号
	  * @param loanId
	  * @return
	  * @author: zhanghg
	  * @date: 2015年2月2日 上午11:26:42
	 */
	public int getMaxVersion(int loanId);
	
	
	/**
	 * 
	  * @Description: 修改还款计划表
	  * @param list
	  * @return
	  * @author: zhanghg
	  * @date: 2015年2月5日 下午8:51:38
	 */
	public int updateRepayments(List<RepaymentPlanBaseDTO> list);
	
	/**
	 * 
	  * @Description: 结清 清除还款计划表，及时发生表的无效数据
	  * @param loanId
	  * @author: zhanghg
	  * @date: 2015年7月16日 下午6:10:53
	 */
	public void delRepaymentNofreezeData(int projectId);
	
	
	/**
	 * 
	  * @Description: 查询还款计划表列表
	  * @param loanId
	  * @return
	  * @author: zhanghg
	  * @date: 2015年2月5日 下午8:57:29
	 */
	public List<RepaymentPlanBaseDTO> getRepayments(int loanId);
	
	
	/**
	 * 
	  * @Description: 修改还款计划表其他费用名称
	  * @param repayment
	  * @return
	  * @author: zhanghg
	  * @date: 2015年2月6日 下午5:52:14
	 */
	public int updateRepaymentOtherCostName(RepaymentPlanBaseDTO repayment);
	
	/**
	 * 
	  * @Description: 查询还款计划表-贷款信息
	  * @param loanId
	  * @return
	  * @author: zhanghg
	  * @date: 2015年2月6日 下午6:46:12
	 */
	public RepaymentLoanInfo getRepaymentLoanInfo(int loanId);
	
	/**
	 * 
	  * @Description: 生成还款计划表
	  * @param loanId
	  * @author: zhanghg
	  * @date: 2015年3月25日 下午6:03:36
	 */
	public void makeRepayments(int loanId);
	
	/**
	 * 
	  * @Description: 修改还款计划表，中间表，即时表冻结状态
	  * @param projectId
	  * @author: zhanghg
	  * @date: 2015年3月27日 上午11:15:39
	 */
	public void updateRepayFreezeStatus(int projectId);
	
	/**
	 * 
	  * @Description:  修改 坏账还款计划表，中间表，即时表冻结状态（包括展期项目）
	  * @param projectId
	  * @author: zhanghg
	  * @date: 2015年4月3日 下午5:30:57
	 */
	public void updateDebtFreezeStatus(int projectId);
	
	/**
	 * 
	  * @Description: 是否退还利息
	  * @param loanId
	  * @return
	  * @author: zhanghg
	  * @date: 2015年3月27日 下午4:00:26
	 */
	public int getIsReturnInterest(int loanId);
	
	/**
	 * 
	  * @Description: 根据贷款ID查询已对账金额
	  * @param map
	  * @author: zhanghg
	  * @date: 2015年3月31日 下午4:27:54
	 */
	public RepaymentPlanBaseDTO getRepayReconciliationAmt(Map<String, Object> map);
	
	/**
	 * 
	  * @Description: 批量新增还款计划表
	  * @param list
	  * @author: zhanghg
	  * @date: 2015年4月1日 下午4:03:15
	 */
	public void insertRepayments(List<RepaymentPlanBaseDTO> list);
	
	
	/**
	 * 
	  * @Description: 获取被展期的项目还款计划表
	  * @param projectId
	  * @return
	  * @author: zhanghg
	  * @date: 2015年5月16日 下午6:01:40
	 */
	public List<RepaymentPlanBaseDTO> getRepaymentsByExdProId(int projectId);
	
	/**
	 * 
	  * @Description: 获取还款计划表中间表最大版本有效数据
	  * @param loanId
	  * @return
	  * @author: zhanghg
	  * @date: 2015年6月17日 下午8:23:33
	 */
	public List<RepaymentPlanBaseDTO> getRepaymentMidsLoanId(int loanId);
	
	
	/**
	 * 
	  * @Description: 获取还款计划表最大版本号
	  * @param loanId
	  * @return
	  * @author: zhanghg
	  * @date: 2015年5月16日 下午6:11:27
	 */
	public int getMaxVersionMid(int loanId);
	
	/**
	 * 
	  * @Description: 根据展期项目Id获取被展项目还款计划表最大版本号
	  * @param projectId
	  * @return
	  * @author: zhanghg
	  * @date: 2015年5月25日 下午3:16:24
	 */
	public int getMaxVersionByExdProId(int projectId);

	/**
	 * 
	  * @Description: 根据pid更新对账状态
	  * @param dto
	  * @author: Zhangyu.Hoo
	  * @date: 2015年8月31日 下午2:15:11
	 */
	public void updateRepayFreezeStatusByPid(RepaymentPlanBaseDTO dto);

	/**
	 * 
	 * getRepaymentsByProjectId:根据项目ID查询还款计划表. <br/>
	 * @author baogang
	 * @param projectId
	 * @return
	 *
	 */
	public List<RepaymentPlanBaseDTO> getRepaymentsByProjectId(int projectId);
	
	/**
	 * 
	 * getRepaymentByDt:根据时间查询到期未还的还款计划表. <br/>
	 * @author baogang
	 * @param query
	 * @return
	 *
	 */
	public List<RepaymentPlanBaseDTO> getRepaymentByDt(RepaymentPlanBaseDTO query);
	/**
	 * 
	 * updateRepaymentPlan:批量修改还款计划表状态. <br/>
	 * @author baogang
	 *
	 */
	public void updateRepaymentPlan(RepaymentPlanBaseDTO dto);
}
