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
import com.xlkfinance.bms.rpc.system.SysConfig;

/**
 * @Title: SysConfigMapper.java
 * @Description: 系统配置 mapper
 * @author : Mr.Cai
 * @date : 2014年12月19日 下午4:21:52
 * @email: caiqing12110@vip.qq.com
 */
@MapperScan("sysConfigMapper")
public interface SysConfigMapper<T, PK> extends BaseMapper<T, PK> {

	/**
	 * @Description: 条件查询所有的系统配置
	 * @param sysConfig 系统配置对象
	 * @return 系统配置集合
	 * @author: Mr.Cai
	 * @date: 2014年12月19日 下午4:20:38
	 */
	public List<SysConfig> getAllSysConfig(SysConfig sysConfig);
	
	/**
	 * 根据系统参数名称获取对应的值
	  * @param name
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年7月7日 下午4:35:53
	 */
	public String getSysConfigValueByName(String name);
	
	/**
	  * @Description: 条件查询所有的系统配置总记录条数
	  * @param sysConfig
	  * @return int 
	  * @author: JingYu.Dai
	  * @date: 2015年6月5日 下午3:19:32
	  */
	public int getAllSysConfigTotal(SysConfig sysConfig);

	/**
	 * 
	 * @Description: 批量删除系统配置
	 * @param configPids
	 * @return
	 * @author: Mr.Cai
	 * @date: 2014年12月23日 下午4:43:30
	 */
	public int batchDelete(String[] configPids);

	/**
	 * 
	 * @Description: 根据名称查询
	 * @param configName
	 *            系统配置名称数组
	 * @return 系统配置集合
	 * @author: Cai.Qing
	 * @date: 2015年1月16日 上午11:04:57
	 */
	public List<SysConfig> getSysConfigByConfigName(String[] configName);

	/**
	 * @Description: 根据名称获取系统配置
	 * @param configName 系统配置名称
	 * @return 系统配置对象
	 * @author: Cai.Qing
	 * @date: 2015年1月16日 下午2:46:40
	 */
	public SysConfig getByConfigName(String configName);

	/**
	 * @Description: 根据list对象查询
	 * @param listSysConfig list对象
	 * @return 系统配置集合
	 * @author: Cai.Qing
	 * @date: 2015年2月2日 下午2:50:04
	 */
	public List<SysConfig> getListByListSysConfig(List<SysConfig> listSysConfig);
}
