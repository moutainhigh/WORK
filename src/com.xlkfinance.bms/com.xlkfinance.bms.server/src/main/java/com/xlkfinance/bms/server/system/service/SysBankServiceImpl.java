/**
 * @Title: SysBankServiceImpl.java
 * @Package com.xlkfinance.bms.server.system.service
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: baogang
 * @date: 2016年5月11日 下午5:38:52
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
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.SysBankInfo;
import com.xlkfinance.bms.rpc.system.SysBankInfoDto;
import com.xlkfinance.bms.rpc.system.SysBankService.Iface;
import com.xlkfinance.bms.server.system.mapper.SysBankMapper;
/**
 * 系统银行Service
  * @ClassName: SysBankServiceImpl
  * @author: baogang
  * @date: 2016年5月12日 下午2:52:44
 */
@Service("sysBankServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.system.SysBankService")
public class SysBankServiceImpl implements Iface{

	/**
	 * 日志
	 */
	private Logger logger = LoggerFactory.getLogger(SysBankServiceImpl.class);
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "sysBankMapper")
	private SysBankMapper sysBankMapper;
	
	/**
	 * 添加银行信息
	 */
	@Override
	public int addSysBankInfo(SysBankInfo sysBankInfo) throws TException {
		int result = 0;
		try {
			sysBankMapper.addSysBankInfo(sysBankInfo);
			result = sysBankInfo.getPid();
		} catch (Exception e) {
			logger.error("添加银行信息失败:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return result;
	}

	/**
	 * 修改银行信息
	 */
	@Override
	public int updateSysBankInfo(SysBankInfo sysBankInfo) throws TException {
		int result = 0;
		try {
			result = sysBankMapper.updateByPrimaryKey(sysBankInfo);
		} catch (Exception e) {
			logger.error("修改银行信息失败:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return result;
	}

	/**
	 * 分页查询银行列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysBankInfo> querySysBankInfos(SysBankInfo sysBankInfo)
			throws TException {
		List<SysBankInfo> resultList = null;
		try {
			resultList = sysBankMapper.querySysBankInfos(sysBankInfo);
		} catch (Exception e) {
			logger.error("分页查询银行信息失败:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		if(resultList == null){
			resultList = new ArrayList<SysBankInfo>();
		}
		return resultList;
	}

	/**
	 * 查询银行总数
	 */
	@Override
	public int getSysBankCount(SysBankInfo sysBankInfo) throws TException {
		int result = 0;
		try {
			result = sysBankMapper.getSysBankCount(sysBankInfo);
		} catch (Exception e) {
			logger.error("查询银行总数信息失败:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return result;
	}

	/**
	 * 查询银行信息详情
	 */
	@Override
	public SysBankInfo querySysBankInfo(int pid) throws TException {
		SysBankInfo sysBankInfo = null;
		try {
			sysBankInfo = sysBankMapper.querySysBankInfo(pid);
			if(sysBankInfo == null){
				sysBankInfo = new SysBankInfo();
			}
		} catch (Exception e) {
			logger.error("查询银行详细信息失败:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return sysBankInfo;
	}

	/**
	 * 不分页查询银行信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysBankInfoDto> queryAllSysBankInfo(SysBankInfo sysBankInfo)
			throws TException {
		List<SysBankInfoDto> resultList = new ArrayList<SysBankInfoDto>();
		try {
			resultList = sysBankMapper.queryAllSysBankInfo(sysBankInfo);
		} catch (Exception e) {
			logger.error("不分页查询银行信息失败:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return resultList;
	}

	/**
	 * 批量删除银行信息
	 */
	@Override
	public int batchDelete(String ids) throws ThriftServiceException,
			TException {
		int result = 0;
		try {
			if(!StringUtil.isBlank(ids)){
				String[] pids = ids.split(",");
				result = sysBankMapper.batchDelete(pids);
			}
		} catch (Exception e) {
			logger.error("批量删除银行信息失败:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return result;
	}

}
 