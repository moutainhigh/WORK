/**
 * 
 */
package com.xlkfinance.bms.server.system.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.SysOrgInfo;
import com.xlkfinance.bms.rpc.system.SysOrgInfoService.Iface;
import com.xlkfinance.bms.server.system.mapper.SysOrgInfoMapper;

/**
 * @Description 系统机构服务实现类
 * @author huxinlong
 *
 */
@SuppressWarnings("unchecked")
@Service("sysOrgInfoService")
@ThriftService(service = "com.xlkfinance.bms.rpc.system.SysOrgInfoService")
public class SysOrgInfoServiceImpl implements Iface {

	private Logger logger = LoggerFactory.getLogger(SysOrgInfoServiceImpl.class);
	
	@Resource(name="sysOrgInfoMapper")
	private SysOrgInfoMapper sysOrgInfoMapper;
	/* (non-Javadoc)
	 * @see com.xlkfinance.bms.rpc.system.SysLogService.Iface#addSysLog(com.xlkfinance.bms.rpc.system.SysLog)
	 */

	@Override
	public int saveSysOrgInfo(SysOrgInfo sysOrgInfo)
			throws ThriftServiceException, TException {
		// TODO Auto-generated method stub
		sysOrgInfoMapper.saveSysOrgInfo(sysOrgInfo);
		
		return 0;
	}

	@Override
	public int updateSysOrgInfo(SysOrgInfo sysOrgInfo)
			throws ThriftServiceException, TException {
		// TODO Auto-generated method stub
		sysOrgInfoMapper.updateSysOrgInfo(sysOrgInfo);
		return 0;
	}

	@Override
	public int delSysOrgInfo(int orgId) throws ThriftServiceException,
			TException {
		// TODO Auto-generated method stub
		SysOrgInfo sysOrgInfo = new SysOrgInfo();
		sysOrgInfo.setId(orgId);
		sysOrgInfo.setStatus(0);
		sysOrgInfoMapper.deleteSysOrgInfo(sysOrgInfo);
		return 0;
	}

	@Override
	public SysOrgInfo getSysOrgInfo(int orgId) throws ThriftServiceException,
			TException {
		// TODO Auto-generated method stub
		return sysOrgInfoMapper.getSysOrgInfo(orgId);
	}

	@Override
	public List<SysOrgInfo> listSysOrgInfo(int parentId)
			throws ThriftServiceException, TException {
		// TODO Auto-generated method stub
		return sysOrgInfoMapper.listSysOrgByParentId(parentId);
	}

	@Override
	public List<SysOrgInfo> allSysOrgInfoList(int category)
			throws ThriftServiceException, TException {
		// TODO Auto-generated method stub
		return sysOrgInfoMapper.getSysOrgInfoByCategory(category);
	}

	@Override
	public SysOrgInfo getSysOrgInfoByName(Map<String,String> paras)
			throws ThriftServiceException, TException {
		// TODO Auto-generated method stub
		return sysOrgInfoMapper.getSysOrgInfoByName(paras);
	}

	@Override
	public List<SysOrgInfo> listSysOrgByLayer(SysOrgInfo org)
			throws ThriftServiceException, TException {
		// TODO Auto-generated method stub
		return sysOrgInfoMapper.listSysOrgByLayer(org);
	}

	@Override
	public List<SysOrgInfo> listSysParentOrg(SysOrgInfo org)
			throws ThriftServiceException, TException {
		// TODO Auto-generated method stub
		return sysOrgInfoMapper.listSysParentOrg(org);
	}

	/* 
	 * 存在:true,不存在:false
	 * (non-Javadoc)
	 * @see com.xlkfinance.bms.rpc.system.SysOrgInfoService.Iface#isExistSysOrgInfoByName(java.util.Map)
	 */
	@Override
	public boolean isExistSysOrgInfoByName(Map<String, String> parameter)
			throws ThriftServiceException, TException {
		// TODO Auto-generated method stub
		SysOrgInfo org = sysOrgInfoMapper.getSysOrgInfoByName(parameter);
		if(org != null){
			return true;
		}
		return false;
	}

	@Override
	public int updateApplicationTree(SysOrgInfo sysOrgInfo) throws ThriftServiceException, TException {
		sysOrgInfoMapper.updateApplicationTree(sysOrgInfo);
		return 0;
	}
}
