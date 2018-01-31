package com.xlkfinance.bms.server.repayment.mapper;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.repayment.PreApplyRepayBaseDTO;
import com.xlkfinance.bms.rpc.repayment.RealtimePlan;

@MapperScan("realtimePlanMapper")
public interface RealtimePlanMapper<T, PK> extends BaseMapper<T, PK> {
	
	/**
	 * 
	  * @Description: 批量新增及时发生信息
	  * @param list
	  * @author: zhanghg
	  * @date: 2015年3月9日 下午5:00:40
	 */
	public void insertRealtimePlans(List<RealtimePlan> list);
	
	/**
	 * 
	  * @Description: 获取及时发生最大版本号
	  * @param loanId
	  * @return
	  * @author: zhanghg
	  * @date: 2015年3月9日 下午5:31:56
	 */
	public int getMaxVersion(int loanId);
	
	/**
	 * 
	  * @Description: 查询及时发生除当前提前还款外的其他信息
	  * @param map
	  * @return
	  * @author: zhanghg
	  * @date: 2015年3月9日 下午7:18:58
	 */
	public List<RealtimePlan> selectRealtimePlanByloanId(Map<String,String> map);
	
	/**
	 * 
	  * @Description: 查询当前提前还款的上一次提前还款 --
	  * @param dto
	  * @return
	  * @author: zhanghg
	  * @date: 2015年3月11日 下午6:56:26
	 */
	public PreApplyRepayBaseDTO getFirstRepayByPid(PreApplyRepayBaseDTO dto);
	
	/**
	 * 
	  * @Description: 查询即时发生表的未对账金额
	  * @param map
	  * @author: zhanghg
	  * @date: 2015年4月1日 上午10:59:16
	 */
	public void getRealtimeNoReconAmt(Map<String,Object> map);
	
	/**
	 * 
	  * @Description: 查询坏账 应收金额列表 或者 损失金额
	  * @param map （loanId=贷款ID,refId=引用Id,(operType=11  损失金额类型，operType ！=11 应收金额类型 )）
	  * @return
	  * @author: zhanghg
	  * @date: 2015年5月16日 下午4:41:42
	 */
	public List<RealtimePlan> getDebtReceiableAmt(Map<String,String> map);
	
	/**
	 * 
	  * @Description: 获取最后一次提前还款的本金余额
	  * @param loanId
	  * @return
	  * @author: zhanghg
	  * @date: 2015年5月19日 下午4:47:43
	 */
	public Double getRepayMinAmt(Integer loanId);
}
