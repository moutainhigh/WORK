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
import com.xlkfinance.bms.rpc.system.SysLog;

/**
 * 
 * @Title: SysLogMapper.java 
 * @Description: 业务日志 mapper  
 * @author : Mr.Cai
 * @date : 2014年12月24日 下午1:54:20 
 * @email: caiqing12110@vip.qq.com
 */
@MapperScan("sysLogMapper")
public interface SysLogMapper<T, PK> extends BaseMapper<T, PK> {
	
	/**
	 * @Description: 条件查询业务日志
	 * @param sysLog 业务日志对象
	 * @return 业务日志集合
	 * @author: Mr.Cai
	 * @date: 2014年12月24日 下午2:20:38
	 */
	public List<SysLog> getAllSysLog(SysLog sysLog);
	
	/**
	  * @Description: 条件查询业务日志总记录条数
	  * @param sysLog 业务日志对象
	  * @return  int 总记录条数
	  * @author: Dai.jingyu
	  * @date: 2015年3月21日 下午4:55:03
	  */
	public int getAllSysLogTotal(SysLog sysLog);
	
	/**
	 * 
	 * @Description: 批量删除业务日志
	 * @param configPids
	 * @return
	 * @author: Mr.Cai
	 * @date: 2014年12月24日 下午2:43:30
	 */
	public int batchDelete(String [] logPids);
}
