/**
 * @Title: SysBankMapper.java
 * @Package com.xlkfinance.bms.server.system.mapper
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: baogang
 * @date: 2016年5月11日 下午6:30:43
 * @version V1.0
 */
package com.xlkfinance.bms.server.system.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.system.SysBankInfo;
import com.xlkfinance.bms.rpc.system.SysBankInfoDto;
@MapperScan("sysBankMapper")
public interface SysBankMapper<T, PK> extends BaseMapper<T, PK> {
	
	/**
	 * 新增银行信息
	  * @param sysBankInfo
	  * @return
	  * @author: baogang
	  * @date: 2016年5月11日 下午6:36:06
	 */
	public int addSysBankInfo (SysBankInfo sysBankInfo);
	
	/**
	 * 修改银行信息
	  * @param sysBankInfo
	  * @return
	  * @author: baogang
	  * @date: 2016年5月11日 下午6:36:13
	 */
	public int updateByPrimaryKey(SysBankInfo sysBankInfo);
	
	/**
	 * 分页查询银行信息
	  * @param sysBankInfo
	  * @return
	  * @author: baogang
	  * @date: 2016年5月11日 下午6:36:19
	 */
	public List<SysBankInfo> querySysBankInfos(SysBankInfo sysBankInfo);
	
	/**
	 * 查询银行总数
	  * @param sysBankInfo
	  * @return
	  * @author: baogang
	  * @date: 2016年5月11日 下午6:36:24
	 */
	public int getSysBankCount(SysBankInfo sysBankInfo);
	
	/**
	 * 查询银行详情
	  * @param pid
	  * @return
	  * @author: baogang
	  * @date: 2016年5月11日 下午6:36:28
	 */
	public SysBankInfo querySysBankInfo(int pid);
	
	/**
	 * 不分页查询银行列表
	  * @param sysBankInfo
	  * @return
	  * @author: baogang
	  * @date: 2016年5月11日 下午6:36:32
	 */
	public List<SysBankInfoDto> queryAllSysBankInfo(SysBankInfo sysBankInfo);
	
	/**
	 * 批量删除
	  * @param ids
	  * @return
	  * @author: baogang
	  * @date: 2016年5月12日 上午10:57:29
	 */
	public int batchDelete(String[] ids);
}
