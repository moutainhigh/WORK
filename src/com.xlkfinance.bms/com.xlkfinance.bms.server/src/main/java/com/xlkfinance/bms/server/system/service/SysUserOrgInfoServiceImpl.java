package com.xlkfinance.bms.server.system.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.SysDataAuthView;
import com.xlkfinance.bms.rpc.system.SysUserOrgInfo;
import com.xlkfinance.bms.rpc.system.SysUserOrgInfoService.Iface;
import com.xlkfinance.bms.server.system.mapper.SysUserOrgInfoMapper;
/**
 * @Description 数据权限
 * @author huxinlong
 *
 */
@SuppressWarnings("rawtypes")
@Service("sysUserOrgInfoService")
@ThriftService(service = "com.xlkfinance.bms.rpc.system.SysUserOrgInfoService")
public class SysUserOrgInfoServiceImpl implements Iface {

	
	@Resource(name="sysUserOrgInfoMapper")
	private SysUserOrgInfoMapper sysUserOrgInfoMapper;
	
	@Override
	public int saveUserOrgInfo(SysUserOrgInfo sysUserOrgInfo){
		try {
			// TODO Auto-generated method stub
			sysUserOrgInfoMapper.saveSysUserOrgInfo(sysUserOrgInfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int deleteUserOrgInfo(SysUserOrgInfo sysUserOrgInfo)
			throws ThriftServiceException, TException {
		// TODO Auto-generated method stub
		sysUserOrgInfoMapper.deleteSysUserOrgInfo(sysUserOrgInfo);
		return 0;
	}

	@Override
	public List<SysUserOrgInfo> listUserOrgInfo(int userId)
			throws ThriftServiceException, TException {
		// TODO Auto-generated method stub
		return sysUserOrgInfoMapper.listOrgByUserId(userId);
	}

	@Override
	public List<SysUserOrgInfo> listUserOrgInfoByOrgId(List<Integer> orgIds)
			throws ThriftServiceException, TException {
		// TODO Auto-generated method stub
		Integer[] orgId = new Integer[orgIds.size()];
		orgIds.toArray(orgId);
		return sysUserOrgInfoMapper.listUserOrgInfoByOrgId(orgId);
	}

	@Override
	public SysUserOrgInfo getUserOrgInfo(SysUserOrgInfo userOrg)
			throws TException {
		// TODO Auto-generated method stub
		SysUserOrgInfo userOrgInfo = sysUserOrgInfoMapper.getUserOrgInfo(userOrg);
		if (userOrgInfo==null) {
         return new SysUserOrgInfo();
      }
      return userOrgInfo;
	}

	@Override
	public int updateUserOrgInfo(SysUserOrgInfo userOrg) throws TException {
		// TODO Auto-generated method stub
		sysUserOrgInfoMapper.updateUserOrgInfo(userOrg);
		return 0;
	}

	@Override
	public List<SysDataAuthView> listDataAuth(SysDataAuthView dataAuthView)
			throws TException {
		// TODO Auto-generated method stub
		return sysUserOrgInfoMapper.listDataAuth(dataAuthView);
	}
	@Override
	public int listDataAuthCount(SysDataAuthView dataAuthView) throws org.apache.thrift.TException{
		return sysUserOrgInfoMapper.listDataAuthCount(dataAuthView);
	}

	@Override
	public int batchDeleteDataAuth(List<Integer> dataIds) throws TException {
		// TODO Auto-generated method stub
		Integer[] ids = new Integer[dataIds.size()];
		return sysUserOrgInfoMapper.batchDeleteDataAuth(dataIds.toArray(ids));
	}

	/* (non-Javadoc)
	 * 存在：true,不存在:false
	 * @see com.xlkfinance.bms.rpc.system.SysUserOrgInfoService.Iface#isExistUserOrgInfo(com.xlkfinance.bms.rpc.system.SysUserOrgInfo)
	 */
	@Override
	public boolean isExistUserOrgInfo(SysUserOrgInfo userOrg)
			throws ThriftServiceException, TException {
		// TODO Auto-generated method stub
		SysUserOrgInfo ug = sysUserOrgInfoMapper.getUserOrgInfo(userOrg);
		if(ug == null){
			return false;
		}		
		return true;
	}
}
