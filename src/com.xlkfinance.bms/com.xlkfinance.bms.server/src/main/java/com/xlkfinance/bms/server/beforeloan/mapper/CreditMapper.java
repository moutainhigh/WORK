package com.xlkfinance.bms.server.beforeloan.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.beforeloan.Credit;

/**
 * 
 * @ClassName: CreditMapper
 * @Description: 项目授信Mapper
 * @author: Cai.Qing
 * @date: 2015年5月20日 下午9:28:01
 */
@MapperScan("creditMapper")
public interface CreditMapper<T, PK> extends BaseMapper<T, PK> {

	/**
	 * 
	 * @Description: 产生项目的同时需要新增的授信项目
	 * @param credit
	 *            授信项目对象
	 * @return 受影响的行数
	 * @author: Dai.jingyu
	 * @date: 2015年2月10日 上午6:42:59
	 */
	public int addCredit(Credit credit);

	/**
	 * @Description: 新增尽职调查详细信息时,修改授信信息
	 * @param credit  授信对象
	 * @author: Cai.Qing
	 * @date: 2015年2月10日 下午12:09:27
	 */
	public void updateByProjectId(Credit credit);

	/**
	 * 
	 * @Description: 获取授信主键
	 * @return 授信ID
	 * @author: Cai.Qing
	 * @date: 2015年3月25日 下午4:39:49
	 */
	public int getSeqBizCredit();

	/**
	 * 
	 * @Description: 根据项目ID查询授信ID
	 * @param projectId
	 *            项目ID
	 * @return 授信ID
	 * @author: Cai.Qing
	 * @date: 2015年3月26日 上午10:04:18
	 */
	public int getCreditIdByProjectId(Integer projectId);

	/**
	 * 
	 * @Description: 根据项目ID修改授信项目的状态为无效
	 * @param projectId
	 *            项目ID
	 * @author: Cai.Qing
	 * @date: 2015年5月20日 下午9:27:28
	 */
	public void updateCreditStatusByProjectId(Integer projectId);
}
