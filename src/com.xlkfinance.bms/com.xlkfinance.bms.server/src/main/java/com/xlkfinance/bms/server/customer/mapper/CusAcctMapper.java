package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.customer.CusAcct;

@MapperScan("cusAcctMapper")
@SuppressWarnings("rawtypes")
public interface CusAcctMapper<T, PK> extends BaseMapper<T, PK> {

	public int deleteCusAcct(List pids);

	/**
	 * 修改客户状态
	 * 
	 * @param cusAcct
	 * @return
	 */
	public int updateCusStatus(CusAcct cusAcct);

	/**
	 * 
	 * @Description: 项目贷款申请，项目结清，项目否决 改变客户状态
	 * @param projectId
	 * @return
	 * @author: zhanghg
	 * @date: 2015年4月9日 下午5:28:37
	 */
	public int updateCusStatusByProSettle(int projectId);

	/**
	 * 
	 * @Description: 批量加入黑名单，拒贷
	 * @param cusAccts
	 * @return
	 * @author: zhanghg
	 * @date: 2015年3月3日 下午5:03:15
	 */
	public int updateBatchCusStatus(List<CusAcct> cusAccts);

	/**
	 * 
	 * @Description: 撤销黑名单，拒贷
	 * @param cusAccts
	 * @return
	 * @author: zhanghg
	 * @date: 2015年3月3日 下午5:03:15
	 */
	public int updateBackBatchCusStatus(List<CusAcct> cusAccts);

	/**
	 * 根据客户ID查询客户类型
	 * 
	 * @param acctId
	 * @return
	 */
	public int selectCusTypeByAcctId(int acctId);

	/**
	 * 根据客户ID获取客户名称
	 * @param pid
	 * @return String
	 */
	public String getAcctNameById(int pid);

	public CusAcct selectPmUserIdByComId(int acctId);

	/**
	 * 
	 * @Description: 查询所有有效的客户信息
	 * @param cusAcct
	 *            客户对象
	 * @return 客户集合
	 * @author: Cai.Qing
	 * @date: 2015年4月20日 下午3:19:41
	 */
	public List<CusAcct> getAllAcct(CusAcct cusAcct);

	/**
	 * 
	 * @Description: 查询所有有效的客户信息 总数
	 * @param cusAcct
	 *            客户对象
	 * @return 所有客户的总数
	 * @author: Cai.Qing
	 * @date: 2015年4月28日 上午3:00:24
	 */
	public int getAllAcctCount(CusAcct cusAcct);
	public int updateCusStatuss(int acctId);

   /**
    *@author:liangyanjun
    *@time:2016年9月11日下午7:11:29
    *@param id
    *@return
    */
   public CusAcct getAcctById(int id);
}
