/**
 * @Title: SysLogServiceImpl.java
 * @Package com.xlkfinance.bms.server.system.service
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月15日 下午4:52:19
 * @version V1.0
 */
package com.xlkfinance.bms.server.system.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.SysLog;
import com.xlkfinance.bms.rpc.system.SysLogService.Iface;
import com.xlkfinance.bms.server.system.mapper.SysLogMapper;


/**
 * @Title: SysLogMapper.java 
 * @Description: 业务日志 service
 * @author : Mr.Cai
 * @date : 2014年12月24日 下午1:54:20 
 * @email: caiqing12110@vip.qq.com
 */
@SuppressWarnings("unchecked")
@Service("sysLogServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.system.SysLogService")
public class SysLogServiceImpl implements Iface {

	@SuppressWarnings("rawtypes")
	@Resource(name = "sysLogMapper")
	private SysLogMapper sysLogMapper;

	/**
	 * @Description: 条件查询业务日志
	 * @param sysLog 业务日志对象
	 * @return 业务日志集合
	 * @author: Mr.Cai
	 * @date: 2014年12月24日 下午2:20:38
	 */
	@Override
	public List<SysLog> getAllSysLog(SysLog sysLog) throws TException {
		List<SysLog> list = new ArrayList<SysLog>();
		try {
			list = sysLogMapper.getAllSysLog(sysLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	  * @Description: 条件查询业务日志总记录条数
	  * @param sysLog 业务日志对象
	  * @return  int 总记录条数
	  * @author: Dai.jingyu
	  * @date: 2015年3月21日 下午4:55:03
	 */
	@Override
	public int getAllSysLogTotal(SysLog sysLog) throws TException {
		return sysLogMapper.getAllSysLogTotal(sysLog);
	}

	/**
	  * @Description: 新增系统日志
	  * @param sysLog 业务日志对象
	  * @return  int 总记录条数
	  * @author: Dai.jingyu
	  * @date: 2015年3月21日 下午4:55:03
	 */
	@Override
	public int addSysLog(SysLog sysLog) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 获取系统当前时间
			sysLog.setLogDateTime(DateUtil.format(new Date()));
			// 判断是否为空
			if (null != sysLog) {
				rows = sysLogMapper.insert(sysLog);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.SYSCONFIG_ADD, "新增失败,请重新操作!");
		}
		return rows;
	}

	/**
	  * @Description: 条件查询业务日志总记录条数
	  * @param sysLog 业务日志对象
	  * @return  int 总记录条数
	  * @author: Dai.jingyu
	  * @date: 2015年3月21日 下午4:55:03
	  */
	@Override
	public int batchDelete(String configPids) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 判断是否为空
			if (null != configPids) {
				String[] pids = configPids.split(",");
				rows = sysLogMapper.batchDelete(pids);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.SYSCONFIG_DELETE, "删除失败,请重新操作!");
		}
		return rows;
	}

	/**
	  * @Description: 根据主键更新对象业务日志
	  * @param sysLog 业务日志对象
	  * @return  int 
	  * @author: Dai.jingyu
	  * @date: 2015年3月21日 下午4:55:03
	  */
	@Override
	public int updateSysLog(SysLog sysLog) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 判断是否为空
			if (null != sysLog) {
				rows = sysLogMapper.updateByPrimaryKey(sysLog);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.SYSCONFIG_UPDATE, "更新失败,请重新操作!");
		}
		return rows;
	}

	@Override
	public List<SysLog> getSysLogList() throws TException {
		return null;
	}

	@Override
	public List<SysLog> getSysLogByParameter(Map<String, String> parameter) throws TException {
		return null;
	}

}
