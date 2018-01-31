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
package com.xlkfinance.bms.server.mortgage.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssBaseOperationService.Iface;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssExtraction;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssHandle;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssKeeping;
import com.xlkfinance.bms.server.mortgage.mapper.ProjectAssBaseMapper;
import com.xlkfinance.bms.server.mortgage.mapper.ProjectAssExtractionMapper;
import com.xlkfinance.bms.server.mortgage.mapper.ProjectAssKeepingMapper;

@SuppressWarnings("unchecked")
@Service("projectAssBaseOperationServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.mortgage.ProjectAssBaseOperationService")
public class ProjectAssBaseOperationServiceImpl implements Iface {
	private Logger logger = LoggerFactory.getLogger(ProjectAssBaseOperationServiceImpl.class);

	@SuppressWarnings("rawtypes")
	@Resource(name = "projectAssBaseMapper")
	private ProjectAssBaseMapper projectAssBaseMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "projectAssExtractionMapper")
	private ProjectAssExtractionMapper projectAssExtractionMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "projectAssKeepingMapper")
	private ProjectAssKeepingMapper projectAssKeepingMapper;

	/**
	 * 
	 * @Description: 查询所有有效的抵质押物保管信息
	 * @param projectAssKeeping
	 *            保管对象
	 * @return 保管列表
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年6月29日 下午7:20:48
	 */
	@Override
	public List<ProjectAssKeeping> getAllProjectAssKeeping(ProjectAssKeeping projectAssKeeping) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @Description: 查询所有有效的抵质押物保管信息数量
	 * @param projectAssKeeping
	 *            保管对象
	 * @return 数量
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年6月30日 上午4:53:45
	 */
	@Override
	public int getAllProjectAssKeepingCount(ProjectAssKeeping projectAssKeeping) throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 
	 * @Description: 查询当前抵质押物的所有保管信息
	 * @param baseId
	 *            抵质押物ID
	 * @return 保管列表
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年6月30日 上午10:08:51
	 */
	@Override
	public List<ProjectAssKeeping> getListProjectAssKeepingByBaseId(int baseId) throws TException {
		List<ProjectAssKeeping> list = new ArrayList<ProjectAssKeeping>();
		try {
			list = projectAssKeepingMapper.getListProjectAssKeepingByBaseId(baseId);
		} catch (Exception e) {
			logger.error("查询当前抵质押物的所有保管信息:" + e.getMessage());
		}
		return list;
	}

	/**
	 * 
	 * @Description: 查询当前抵质押物的最新的保管信息
	 * @param baseId
	 *            抵质押物ID
	 * @return 保管信息对象
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年6月30日 上午4:54:17
	 */
	@Override
	public ProjectAssKeeping getProjectAssKeepingByBaseId(int baseId) throws TException {
		ProjectAssKeeping p = new ProjectAssKeeping();
		try {
			p = projectAssKeepingMapper.getProjectAssKeepingByBaseId(baseId);
		} catch (Exception e) {
			logger.error("查询当前抵质押物的最新的保管信息:" + e.getMessage());
		}
		return p;
	}

	/**
	 * 
	 * @Description: 批量删除抵质押物保管信息
	 * @param pids
	 *            保管PID(1,2,3)
	 * @return 受影响的行数
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年6月29日 下午7:21:14
	 */
	@Override
	public int batchDeleteProjectAssKeeping(String pids) throws ThriftServiceException, TException {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 
	 * @Description: 查询所有有效的抵质押物提取信息
	 * @param projectAssExtraction
	 *            提取信息对象
	 * @return 提取列表
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年6月29日 下午7:21:19
	 */
	@Override
	public List<ProjectAssExtraction> getAllProjectAssExtraction(ProjectAssExtraction projectAssExtraction) throws TException {
		// 创建提取信息List
		List<ProjectAssExtraction> list = new ArrayList<ProjectAssExtraction>();
		try {
			// 获取提取信息列表
			list = projectAssExtractionMapper.getAllProjectAssExtraction(projectAssExtraction);
		} catch (Exception e) {
			logger.error("查询所有有效的抵质押物提取信息:" + e.getMessage());
		}
		return list;
	}

	/**
	 * 
	 * @Description: 查询所有有效的提取信息数量
	 * @param projectAssExtraction
	 *            提取信息对象
	 * @return 数量
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年6月29日 下午7:48:19
	 */
	@Override
	public int getAllProjectAssExtractionCount(ProjectAssExtraction projectAssExtraction) throws TException {
		int count = 0;
		try {
			// 获取数量
			count = projectAssExtractionMapper.getAllProjectAssExtractionCount(projectAssExtraction);
		} catch (Exception e) {
			logger.error("查询所有有效的提取信息数量:" + e.getMessage());
		}
		return count;
	}

	/**
	 * 
	 * @Description: 查询当前抵质押物的所有提取信息
	 * @param baseId
	 *            抵质押物ID
	 * @return 提取信息列表
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年6月30日 上午10:10:14
	 */
	@Override
	public List<ProjectAssExtraction> getListProjectAssExtractionByBaseId(int baseId) throws TException {
		// 创建提取信息List
		List<ProjectAssExtraction> list = new ArrayList<ProjectAssExtraction>();
		try {
			// 获取提取信息列表
			list = projectAssExtractionMapper.getProjectAssExtractionByBaseId(baseId);
		} catch (Exception e) {
			logger.error("查询所有有效的抵质押物提取信息:" + e.getMessage());
		}
		return list;
	}

	/**
	 * 
	 * @Description: 批量删除抵质押物提取信息
	 * @param pids
	 *            提取信息PID(1,2,3)
	 * @return 受影响的行数
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年6月29日 下午7:21:23
	 */
	@Override
	public int batchDeleteProjectAssExtraction(String pids) throws ThriftServiceException, TException {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 
	 * @Description: 查询所有有效的抵质押物处理信息
	 * @param projectAssHandle
	 *            处理信息对象
	 * @return 处理列表
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年6月29日 下午7:21:26
	 */
	@Override
	public List<ProjectAssHandle> getAllProjectAssHandle(ProjectAssHandle projectAssHandle) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAllProjectAssHandleCount(ProjectAssHandle projectAssHandle) throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 
	 * @Description: 批量删除抵质押物处理信息
	 * @param pids
	 *            处理信息PID(1,2,3)
	 * @return 受影响的行数
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年6月29日 下午7:21:30
	 */
	@Override
	public int batchDeleteProjectAssHandle(String pids) throws ThriftServiceException, TException {
		// TODO Auto-generated method stub
		return 0;
	}
}
