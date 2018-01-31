/**
 * @Title: SysUserMapper.java
 * @Package com.xlkfinance.bms.server.system.mapper
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月11日 下午7:22:11
 * @version V1.0
 */
package com.xlkfinance.bms.server.system.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.system.SysLookup;

/**
 * 
 * @Title: SysLookupMapper.java
 * @Description: 数据字典配置 mapper
 * @author : Mr.Cai
 * @date : 2014年12月24日 下午4:21:52
 * @email: caiqing12110@vip.qq.com
 */
@MapperScan("sysLookupMapper")
public interface SysLookupMapper<T, PK> extends BaseMapper<T, PK> {

	/**
	 * 
	 * @Description: 条件查询所有的数据字典配置
	 * @param sysLookup
	 *            数据字典配置对象
	 * @return 数据字典配置集合
	 * @author: Mr.Cai
	 * @date: 2014年12月24日 下午4:23:38
	 */
	public List<SysLookup> getAllSysLookup(SysLookup sysLookup);

	/**
	  * @Description: 根据数据字典条件查询 数据总记录条数
	  * @param sysLookup
	  * @return int 
	  * @author: Dai.jingyu
	  * @date: 2015年3月13日 下午5:11:34
	  */
	public int getAllSysLookupSum(SysLookup sysLookup);
	/**
	  * @Description: 批量删除数据字典配置
	  * @param SysLookuPids pid列表
	  * @return int
	  * @author: Dai.jingyu
	  * @date: 2015年3月11日 下午2:51:26
	 */
	public int deleteLookup(List<Integer> SysLookuPids);

	/**
	 * 
	 * @Description: 获取最大的主键ID
	 * @return 主键ID
	 * @author: Mr.Cai
	 * @date: 2014年12月25日 下午2:08:28
	 */
	public int getMaxPid();
}
