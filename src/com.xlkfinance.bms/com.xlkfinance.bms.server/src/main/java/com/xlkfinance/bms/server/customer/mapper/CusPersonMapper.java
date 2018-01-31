package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.customer.CusAcctPotential;
import com.xlkfinance.bms.rpc.customer.CusPerson;

@MapperScan("cusPersonMapper")
public interface CusPersonMapper<T, PK> extends BaseMapper<T, PK> {

	public CusPerson selectMainByPerId(int acctId);

	public CusPerson selectFamilyByPerId(int acctId);

	public List<GridViewDTO> getCusPersonByAcctIdAndStatus(CusPerson cusPerson);

	@SuppressWarnings("rawtypes")
	public int deleteCusPerson(List pids);

	public int getTotalCusPersons(CusPerson cusPerson);

	public List<GridViewDTO> getCusAcctPotentials(CusAcctPotential cusAcctPotential);

	/**
	 * 
	 * @Description: 条件查询所有人员信息
	 * @param cusPerson
	 *            人员对象
	 * @return 人员集合
	 * @author: Cai.Qing
	 * @date: 2015年4月9日 下午8:26:40
	 */
	public List<CusPerson> getAllCusPerson(CusPerson cusPerson);

	/**
	 * 
	 * @Description: 条件查询所有人员的总数
	 * @param cusPerson
	 *            人员对象
	 * @return 人员对象的数量
	 * @author: Cai.Qing
	 * @date: 2015年4月9日 下午9:03:31
	 */
	public int getAllCusPersonCount(CusPerson cusPerson);
	
	/**
	 * 根据证件号码查询客户信息
	  * @param cusPerson
	  * @return
	  * @author: baogang
	  * @date: 2016年8月8日 下午5:24:23
	 */
	public List<CusPerson> getCusPersonByNumber(CusPerson cusPerson);
	
	/**
	 * 查询客户配偶信息
	 * @param cusPerson
	 * @return
	 */
	public List<CusPerson> getCusPersonByMarr(CusPerson cusPerson);
	
	/**
	 * 更新客户信息-供资金机构使用
	 * @param cusPerson
	 * @return
	 */
	public int updateFromPartnerById(CusPerson cusPerson);
	/**
	 *查询客户关系人信息
	 * @param cusPerson
	 * @return
	 */
	public List<CusPerson> getRelationCusPerson(CusPerson cusPerson);
	//根据机构编码+身份证编码做唯一性检验
	public List<CusPerson> checkCusExist(Map<String, Integer> myMap);
}
