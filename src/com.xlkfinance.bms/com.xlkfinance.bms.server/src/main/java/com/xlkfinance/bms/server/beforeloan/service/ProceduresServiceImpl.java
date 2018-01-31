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
package com.xlkfinance.bms.server.beforeloan.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.beforeloan.ProceduresService.Iface;
import com.xlkfinance.bms.rpc.beforeloan.ProjectApprovalInfo;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectApprovalInfoMapper;

/**
 * 
 * @ClassName: ProceduresServiceImpl
 * @Description: 贷前流程service 实现类
 * @author: Cai.Qing
 * @date: 2015年3月9日 下午2:07:27
 */
@SuppressWarnings("unchecked")
@Service("proceduresServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.beforeloan.ProceduresService")
public class ProceduresServiceImpl implements Iface {
	private Logger logger = LoggerFactory.getLogger(ProceduresServiceImpl.class);

	@SuppressWarnings("rawtypes")
	@Resource(name = "projectApprovalInfoMapper")
	private ProjectApprovalInfoMapper projectApprovalInfoMapper;

	/**
	 * 
	 * @Description: 查询落实审批信息
	 * @param projectId
	 *            项目ID
	 * @return 审批信息集合
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月21日 上午10:39:11
	 */
	@Override
	public List<ProjectApprovalInfo> getProjectApprovalLs(int projectId) throws ThriftServiceException, TException {
		List<ProjectApprovalInfo> list = new ArrayList<ProjectApprovalInfo>();
		try {
			list = projectApprovalInfoMapper.getProjectApprovalLs(projectId);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询落实审批信息 :" + e.getMessage());
			}
		}
		return list;
	}

	/**
	 * 
	 * @Description: 查询管理要求审批信息
	 * @param projectId
	 *            项目ID
	 * @return 审批信息集合
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月21日 上午10:37:54
	 */
	@Override
	public List<ProjectApprovalInfo> getProjectApprovalGl(int projectId) throws ThriftServiceException, TException {
		List<ProjectApprovalInfo> list = new ArrayList<ProjectApprovalInfo>();
		try {
			list = projectApprovalInfoMapper.getProjectApprovalGl(projectId);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询要求审批信息 :" + e.getMessage());
			}
		}
		return list;
	}

	/**
	 * 
	 * @Description: 新增贷审会信息
	 * @param projectApprovalInfo
	 *            审批信息对象
	 * @return 受影响的行数
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月21日 上午10:18:20
	 */
	@Override
	public int addProjectApprovalInfo(ProjectApprovalInfo projectApprovalInfo) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 新增时,赋值是否确认为否 (1:是 -1:否)
			projectApprovalInfo.setIsConfirm(-1);
			if(projectApprovalInfo.getInterestChgId()!=0){
				rows = projectApprovalInfoMapper.addProjectApprovalInfoRes(projectApprovalInfo);
			}else{
				rows = projectApprovalInfoMapper.insert(projectApprovalInfo);
			}
			
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("新增审批信息:" + e.getMessage());
			}
		}
		return rows;
	}

	/**
	 * 
	 * @Description: 修改贷审会信息
	 * @param projectApprovalInfo
	 *            审批信息对象
	 * @return 受影响的行数
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月21日 上午10:19:27
	 */
	@Override
	public int updProjectApprovalInfo(ProjectApprovalInfo projectApprovalInfo) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			if(projectApprovalInfo.getInterestChgId()!=0){//利率变更新增
				rows = projectApprovalInfoMapper.updateProjectApprovalInfoResInfo(projectApprovalInfo);
			}else{//贷前以及其它流程贷审会管理要求保存除利率变更外
				rows = projectApprovalInfoMapper.updateByPrimaryKey(projectApprovalInfo);
			}
			
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("修改审批信息:" + e.getMessage());
			}
		}
		return rows;
	}

	/**
	 * 
	 * @Description: 批量删除审批信息
	 * @param pids
	 *            审批信息Pids(1,2,3)
	 * @return 受影响的行数
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月21日 上午10:37:21
	 */
	@Override
	public int deleteProjectApprovalInfo(String pids) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			String[] pid = pids.split(",");
			rows = projectApprovalInfoMapper.batchDelete(pid);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("批量删除审批信息:" + e.getMessage());
			}
		}
		return rows;
	}

	/**
	 * 
	 * @Description:修改贷审会信息是否确认
	 * @param projectApprovalInfo
	 *            审批信息对象
	 * @return 受影响的行数
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月21日 上午10:58:30
	 */
	@Override
	public int updateIsConfirmPrimaryKey(ProjectApprovalInfo projectApprovalInfo) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 创建条件 Map
			Map<String, Object> map = new HashMap<String, Object>();
			// 赋值Map
			map.put("isConfirm", projectApprovalInfo.getIsConfirm());
			map.put("confirmUserId", projectApprovalInfo.getConfirmUserId());
			map.put("pids", projectApprovalInfo.getPids().split(","));
			// 调用方法更新
			rows = projectApprovalInfoMapper.updateIsConfirmPrimaryKey(map);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("修改贷审会信息是否确认:" + e.getMessage());
			}
		}
		return rows;
	}
	 /**
	  * 查询利率变更要求审批信息
	  */
	@Override
	public List<ProjectApprovalInfo> getProjectApprovalLsInfo(int interestChgId)
			throws ThriftServiceException, TException {
		List<ProjectApprovalInfo> list = new ArrayList<ProjectApprovalInfo>();
		try {
			list = projectApprovalInfoMapper.getProjectApprovalLsInfo(interestChgId);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询利率变更要求审批信息 :" + e.getMessage());
			}
		}
		return list;
	}
	/**
	 * 查询利率变更要求审批信息
	 */
	@Override
	public List<ProjectApprovalInfo> getProjectApprovalGlInfo(int interestChgId)
			throws ThriftServiceException, TException {
		List<ProjectApprovalInfo> list = new ArrayList<ProjectApprovalInfo>();
		try {
			list = projectApprovalInfoMapper.getProjectApprovalGlInfo(interestChgId);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询利率变更要求审批信息 :" + e.getMessage());
			}
		}
		return list;
	}
	/**
	 * 修改贷审会信息是否确认
	 */
	@Override
	public int updateIsConfirmPrimaryKeyInfo(
			ProjectApprovalInfo projectApprovalInfo)
			throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 创建条件 Map
			Map<String, Object> map = new HashMap<String, Object>();
			// 赋值Map
			map.put("isConfirm", projectApprovalInfo.getIsConfirm());
			map.put("confirmUserId", projectApprovalInfo.getConfirmUserId());
			map.put("pids", projectApprovalInfo.getPids().split(","));
			// 调用方法更新
			rows = projectApprovalInfoMapper.updateIsConfirmPrimaryKeyInfo(map);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("修改贷审会信息是否确认:" + e.getMessage());
			}
		}
		return rows;
	}

	/**
	 * 获取利率变更要求审批信息
	 */
	@Override
	public List<ProjectApprovalInfo> getProjectApprovalAll(int interestChgId)
			throws ThriftServiceException, TException {
		List<ProjectApprovalInfo> list = new ArrayList<ProjectApprovalInfo>();
		try {
			list = projectApprovalInfoMapper.getProjectApprovalAll(interestChgId);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("获取利率变更要求审批信息 :" + e.getMessage());
			}
		}
		return list;
	}
	/**
	 * 
	  * @Description: 批量删除利率变更贷审会信息
	  * @param pids
	  * @return
	  * @throws ThriftServiceException
	  * @throws TException
	  * @author: Rain.Lv
	  * @date: 2015年6月27日 下午5:19:32
	 */
	@Override
	public int deleteProjectApprovalResInfo(String pids)
			throws ThriftServiceException, TException {
		int rows = 0;
		try {
			String[] pid = pids.split(",");
			rows = projectApprovalInfoMapper.deleteProjectApprovalById(pid);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("批量删除利率变更审批信息:" + e.getMessage());
			}
		}
		return rows;
	}
	/**
	 * 删除原项目id保存的贷审会信息
	 */
	@Override
	public int deleteProjectApprovalByProjectId(int projectId)
			throws ThriftServiceException, TException {
		int rows = 0;
		try {
			rows = projectApprovalInfoMapper.deleteProjectApprovalByProjectId(projectId);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("删除原项目id保存的贷审会信息:" + e.getMessage());
			}
		}
		return rows;
	}
}
