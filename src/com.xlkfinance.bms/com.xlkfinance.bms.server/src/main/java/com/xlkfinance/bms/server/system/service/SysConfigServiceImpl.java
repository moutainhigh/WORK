/**
 * @Title: SysConfigServiceImpl.java
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
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.SysConfig;
import com.xlkfinance.bms.rpc.system.SysConfigService.Iface;
import com.xlkfinance.bms.rpc.system.SysLog;
import com.xlkfinance.bms.server.system.log.BusinessLogger;
import com.xlkfinance.bms.server.system.mapper.SysConfigMapper;


/**
 * @Title: SysConfigMapper.java
 * @Description: 系统配置 service
 * @author : Mr.Cai
 * @date : 2014年12月19日 下午4:21:52
 * @email: caiqing12110@vip.qq.com
 */
@SuppressWarnings("unchecked")
@Service("sysConfigServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.system.SysConfigService")
public class SysConfigServiceImpl implements Iface {
	private Logger logger = LoggerFactory.getLogger(SysConfigServiceImpl.class);

	@SuppressWarnings("rawtypes")
	@Resource(name = "sysConfigMapper")
	private SysConfigMapper sysConfigMapper;

	@Resource(name = "businessLogger")
	private BusinessLogger businessLogger;

	/**
	 * @Description: 条件查询所有的系统配置
	 * @param sysConfig 系统配置对象
	 * @return 系统配置集合
	 * @author: Mr.Cai
	 * @date: 2014年12月19日 下午4:20:38
	 */
	@Override
	public List<SysConfig> getAllSysConfig(SysConfig sysConfig) throws TException {
		List<SysConfig> list = new ArrayList<SysConfig>();
		try {
			list = sysConfigMapper.getAllSysConfig(sysConfig);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询系统配置:" + e.getMessage());
			}
		}
		return list;
	}
	
	@Override
	public int getAllSysConfigTotal(SysConfig sysConfig) throws TException{
		return sysConfigMapper.getAllSysConfigTotal(sysConfig);
	}

	/**
	 * @Description: 添加系统配置
	 * @param sysConfig 系统配置对象
	 * @return int
	 * @author: Mr.Cai
	 * @date: 2014年12月19日 下午4:20:38
	 */
	@Override
	public int addSysConfig(SysConfig sysConfig) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 判断是否为空
			if (null != sysConfig) {
				rows = sysConfigMapper.insert(sysConfig);
				SysLog sysLog = new SysLog();
				businessLogger.log(BusinessModule.MODUEL_SYSTEM, sysLog);
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("新增系统配置:" + e.getMessage());
			}
			throw new ThriftServiceException(ExceptionCode.SYSCONFIG_ADD, "新增失败,请重新操作!");
		}
		return rows;
	}

	/**
	 * @Description: 批量删除系统配置
	 * @param configPids
	 * @return int
	 * @author: Mr.Cai
	 * @date: 2014年12月23日 下午4:43:30
	 */
	@Override
	public int batchDelete(String configPids) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 判断是否为空
			if (null != configPids) {
				String[] pids = configPids.split(",");
				rows = sysConfigMapper.batchDelete(pids);
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("逻辑删除系统配置:" + e.getMessage());
			}
			throw new ThriftServiceException(ExceptionCode.SYSCONFIG_DELETE, "删除失败,请重新操作!");
		}
		return rows;
	}

	/**
	 * @Description: 根据主键更改数据系统配置
	 * @param configPids
	 * @return int
	 * @author: Mr.Cai
	 * @date: 2014年12月23日 下午4:43:30
	 */
	@Override
	public int updateSysConfig(SysConfig sysConfig) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 判断是否为空
			if (null != sysConfig) {
				rows = sysConfigMapper.updateByPrimaryKey(sysConfig);
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("修改系统配置:" + e.getMessage());
			}
			throw new ThriftServiceException(ExceptionCode.SYSCONFIG_UPDATE, "更新失败,请重新操作!");
		}
		return rows;
	}

	/**
	 * @Description: 根据名称获取系统配置
	 * @param configName 系统配置名称
	 * @return 系统配置对象
	 * @author: Cai.Qing
	 * @date: 2015年1月16日 下午2:46:40
	 */
	@Override
	public SysConfig getByConfigName(String configName) throws TException {
		SysConfig sysConfig = new SysConfig();
		try {
			sysConfig = sysConfigMapper.getByConfigName(configName);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("获取系统配置:" + e.getMessage());
			}
		}
		return sysConfig;
	}

	/**
	 * @Description: 根据list对象查询
	 * @param listSysConfig list对象
	 * @return 系统配置集合 List<SysConfig>
	 * @author: Cai.Qing
	 * @date: 2015年2月2日 下午2:50:04
	 */
	@Override
	public List<SysConfig> getListByListSysConfig(List<SysConfig> listSysConfig) throws TException {
		List<SysConfig> list = new ArrayList<SysConfig>();
		try {
			list = sysConfigMapper.getListByListSysConfig(listSysConfig);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("获取系统配置集合:" + e.getMessage());
			}
		}
		return list;
	}

	/**
	 * 根据系统参数名称获取对应的值
	  * @param name
	  * @return String
	  * @author: qiancong.Xian
	  * @date: 2015年7月7日 下午4:35:53
	  */
	@Override
	public String getSysConfigValueByName(String name) throws TException {
		try
		{
		return sysConfigMapper.getSysConfigValueByName(name);
		}
		catch(Exception e)
		{
			logger.error("根据系统参数名称["+name+"]查询的对应的值失败",e);
		}
		
		return "";
	}
}
