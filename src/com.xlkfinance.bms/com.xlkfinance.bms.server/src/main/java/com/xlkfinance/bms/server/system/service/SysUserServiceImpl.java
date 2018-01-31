/**
 * @Title: SysUserServiceImpl.java
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.achievo.framework.util.PasswordUtil;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.EidtPassword;
import com.xlkfinance.bms.rpc.system.SysMenu;
import com.xlkfinance.bms.rpc.system.SysPermission;
import com.xlkfinance.bms.rpc.system.SysRole;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.rpc.system.SysUserService.Iface;
import com.xlkfinance.bms.server.system.mapper.SysMenuMapper;
import com.xlkfinance.bms.server.system.mapper.SysUserMapper;
import com.xlkfinance.bms.server.system.mapper.SystemUserRoleMapper;

@SuppressWarnings("unchecked")
@Service("sysUserServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.system.SysUserService")
public class SysUserServiceImpl implements Iface {
	private Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

	@SuppressWarnings("rawtypes")
	@Resource(name = "sysUserMapper")
	private SysUserMapper sysUserMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "sysMenuMapper")
	private SysMenuMapper sysMenuMapper;
	
	@SuppressWarnings("rawtypes")
    @Resource(name = "systemUserRoleMapper")
    private SystemUserRoleMapper systemUserRoleMapper;
   
	@Override
	public int addSysUser(SysUser sysUser) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 判断当前用户名是否存在
			List<SysUser> list = sysUserMapper.getSysUserByUserName(sysUser.getUserName());
			if (null == list || list.size() == 0) {
				// 赋值
				// 状态 初始为 1 正常
				sysUser.setStatus(1);
				// 获取密码
				String passwordEncrypt = PasswordUtil.encrypt(sysUser.getUserName(), sysUser.getPwd(), PasswordUtil.getStaticSalt());
				sysUser.setPwd(passwordEncrypt);
				rows = sysUserMapper.insert(sysUser);
			}else if(list.get(0).status == 0){
				throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "当前系统用户名存在,状态为不可用,请重新添加!");
			}else {
				throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "当前系统用户名存在,请重新添加!");
			}
		} catch (ThriftServiceException e) {
			logger.error("添加系统用户:" + e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("添加系统用户:" + e.getMessage());
			throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "添加失败,请重新添加!");
		}
		return rows;
	}

	@Override
	public int deleteByUserName(String userNames) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 把传过来的数据劈开成一个数组作为参数传过去
			String[] strArr = userNames.split(",");
			// 直接调用批量删除的方法
			rows = sysUserMapper.batchDelete(strArr);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.SYSUSER_DELETE, "删除失败,请重新删除.");
		}
		return rows;
	}

	@Override
	public int updateSysUser(SysUser sysUser) throws ThriftServiceException, TException {
		return sysUserMapper.updateByPrimaryKey(sysUser);
	}

	@Override
	public int updateSysUserPwd(SysUser sysUser) throws ThriftServiceException, TException {
		return sysUserMapper.updateSysUserPwd(sysUser);
	}

	@Override
	public SysUser querySysUserByName(String userName) throws TException {
		SysUser sysUser = sysUserMapper.selectSysUserByUserName(userName);
		return sysUser == null ? new SysUser() : sysUser;
	}

	@Override
	public List<SysUser> getSysUserList() throws TException {
		List<SysUser> list = sysUserMapper.selectAll();
		return list == null ? new ArrayList<SysUser>() : list;
	}

	@Override
	public List<SysUser> getSysUserByParameter(Map<String, String> parameter) throws TException {
		List<SysUser> list = sysUserMapper.selectByMap(parameter);
		return list == null ? new ArrayList<SysUser>() : list;
	}

	@Override
	public List<SysUser> getAllSysUser(SysUser sysUser) throws TException {
		List<SysUser> list = sysUserMapper.getAllSysUser(sysUser);
		return list == null ? new ArrayList<SysUser>() : list;
	}
	@Override
	public int getAllSysUserCount(SysUser sysUser) throws TException {
		return sysUserMapper.getAllSysUserCount(sysUser);
	}

	@Override
	public List<SysPermission> getSysUserPermissionByUserName(String userName) throws TException {
		List<SysPermission> list = sysUserMapper.selectSysUserPermissionByUserName(userName);
		return list == null ? new ArrayList<SysPermission>() : list;
	}

	@Override
	public int batchUpdate(String userNames) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 把传过来的数据劈开成一个数组作为参数传过去
			String[] strArr = userNames.split(",");
			// 直接调用批量删除的方法
			rows = sysUserMapper.batchUpdate(strArr);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.SYSUSER_DELETE, "删除失败,请重新删除!");
		}
		return rows;
	}

	@Override
	public List<SysMenu> getPermissionMenuByUserName(String userName) throws TException {
		List<SysMenu> list = sysMenuMapper.selectPermissionMenuByUserName(userName);
		return list == null ? new ArrayList<SysMenu>() : list;
	}

	@Override
	public List<SysMenu> getRootMenuByUserName(String userName) throws TException {
		List<SysMenu> list = sysMenuMapper.selectRootMenuByUser(userName);
		return list == null ? new ArrayList<SysMenu>() : list;
	}

	@Override
	public List<SysMenu> getChildMenuByUserName(String userName, int parentId) throws TException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("userName", userName);
		parameter.put("pid", parentId);

		List<SysMenu> list = sysMenuMapper.selectChildMenuByUser(parameter);
		return list == null ? new ArrayList<SysMenu>() : list;
	}

	/**
	 * 
	 * @Description: 根据系统用户ID查询系统用户集合
	 * @param pids
	 *            系统用户id数组
	 * @return 系统用户集合
	 * @author: Dai.JingYu
	 * @date: 2015年2月2日 下午18:35:16
	 */
	@Override
	public List<SysUser> getSysUserByPids(List<String> pids) throws TException {
		List<SysUser> list = null;
		String[] pidArray = convertList2Array(pids);
		if(null != pidArray){
			 list = sysUserMapper.getSysUserByPids(pidArray);
		}
		return list == null ? new ArrayList<SysUser>() : list;
	}
	/**
	  * @Description: 根据用户ID查询用户数据
	  * @param pid 用户ID
	  * @return SysUser
	  * @throws TException
	  * @author: Dai.jingyu
	  * @date: 2015年3月17日 上午11:50:56
	  */
	@Override
	public SysUser getSysUserByPid(int pid) throws TException{
		SysUser user = sysUserMapper.getSysUserByPid(String.valueOf(pid));
		return user==null?(new SysUser()):user;
	}
	private String[] convertList2Array(List<String> pids) {
		if(null != pids){
			StringBuffer sb = new StringBuffer(pids.size());
			for (String pid : pids) {
				sb.append(pid).append(" ");
			}
			return sb.toString().split(" +");
		}
		return null;
	}
	
	@Override
	public void eidtPassword(EidtPassword eidtPassword) throws ThriftServiceException, TException {
		try {
			int result = sysUserMapper.eidtPassword(eidtPassword);
			if(result != 1){
				throw new ThriftServiceException(ExceptionCode.SYS_EDIT_PWD,"密码修改失败！请确认旧密码的正确性！");
			}
		} catch (ThriftServiceException e) {
			throw new ThriftServiceException(ExceptionCode.SYS_EDIT_PWD,"密码修改失败！请确认旧密码的正确性！");
		} catch (Exception e) {
			throw new ThriftServiceException(ExceptionCode.SYS_EDIT_PWD, "密码修改失败!");
		}
	}

	@Override
	public List<SysUser> getUsersByRoleCode(String roleCode) throws TException {
		List<SysUser> list = sysUserMapper.getUsersByRoleCode(roleCode);
		return list == null ? new ArrayList<SysUser>() : list;
	}
	
	@Override
	public SysUser getuperiorSysUserByPid(int pid)throws TException{
		SysUser sysUser = sysUserMapper.getuperiorSysUserByPid(pid);
		return sysUser==null?(new SysUser()):sysUser;
	}
	
	/**
	  * @Description: 检查员工账号是否存在
	  * @param userName 员工账号
	  * @return boolean
	  * @author: JingYu.Dai
	  * @date: 2015年5月13日 下午5:28:58
	 */
	@Override
	public boolean checkUserNameIsExist(String userName){
		 SysUser sysUser = sysUserMapper.selectSysUserByUserName(userName); 
		 if(null != sysUser){
			 return false;
		 }
		 return true;
	}
	/**
	  * @Description: 检查员工工号是否存在
	  * @param memberId 员工工号
	  * @return boolean
	  * @author: JingYu.Dai
	  * @date: 2015年5月13日 下午5:28:58
	 */
	@Override
	public boolean checkMemberIdIsExist(String MemberId){
		 SysUser sysUser = sysUserMapper.getUserByMemberId(MemberId);
		 if(null != sysUser){
			 return false;
		 }
		 return true;
	}

	

	
	/* (non-Javadoc)
	 * @see com.xlkfinance.bms.rpc.system.SysUserService.Iface#getUsersByOrgId(java.util.List)
	 */
	@Override
	public List<Integer> getUsersByOrgId(List<Integer> orgIds)
			throws ThriftServiceException, TException {
		// TODO Auto-generated method stub
		//Integer[] arrayIds = new Integer[orgIds.size()];
		
		return sysUserMapper.getUsersByOrgId(orgIds);
	}
	
   @Override
   public List<SysUser> getUsersByOrgIdAndRoleCode(int orgId,String roleCode) throws ThriftServiceException, TException {
      Map<String,Object> params=new HashMap<String,Object>();
      params.put("orgId", orgId);
      params.put("roleCode", roleCode);
      return sysUserMapper.getUsersByOrgIdAndRoleCode(params);
   }

	@Override
	public List<SysUser> getAcctManagerByLogin(List<Integer> userIds) throws ThriftServiceException, TException {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userIds", userIds);
		
		return sysUserMapper.getAcctManagerByLogin(params);
	}

   @Override
   public SysUser getUserDetailsByPid(int pid) throws TException {
      SysUser sysUser = sysUserMapper.getUserDetailsByPid(pid);
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("pageNum", 1);
      map.put("pageSize", 100);
      map.put("userId", sysUser.getPid());
      List<SysRole> roles = systemUserRoleMapper.getRolesByUserId(map);
      sysUser.setRoles(roles);
      return sysUser;
   }

   /**
    * 根据用户名或手机号码获取用户信息
    *@author:liangyanjun
    *@time:2016年6月20日上午10:45:18
    *@param phone
    *@return
    */
   @Override
   public SysUser getSysUserByPhone(String phone) throws TException {
      SysUser sysUser = sysUserMapper.getSysUserByPhone(phone);
      if (sysUser == null) {
         return new SysUser();
      }
      return sysUser;
   }
   
   /**
    * 根据工号获取用户信息
    *@author:liangyanjun
    *@time:2016年6月20日下午5:16:50
    *@param memberId
    *@return
    */
   @Override
   public SysUser getSysUserByMemberId(String memberId) throws TException {
      System.out.println(memberId);
      SysUser sysUser = sysUserMapper.getSysUserByMemberId(memberId);
      if (sysUser == null) {
         return new SysUser();
      }
      return sysUser;
   }

   /**
    * 修改系统用户token
    *@author:liangyanjun
    *@time:2016年10月25日下午3:49:59
    *
    */
    @Override
    public int updateToken(int userId, String token) throws TException {
        return sysUserMapper.updateToken(userId,token);
    }

    /**
     * 根据角色编码以及机构ID查询该机构树下所有的角色人员
     */
	@Override
	public List<SysUser> getUserByOrg(int orgId, String roleCode)
			throws ThriftServiceException, TException {
		Map<String,Object> params=new HashMap<String,Object>();
	    params.put("orgId", orgId);
	    params.put("roleCode", roleCode);
	    return sysUserMapper.getUserByOrg(params);
	}

	@Override
	public int deleteByUserId(int userId) throws ThriftServiceException,
			TException {
		return sysUserMapper.deleteByUserId(userId);
	}

	@Override
	public List<SysUser> getAllSysUserNormal(SysUser sysUser) throws TException {
		List<SysUser> list = sysUserMapper.getAllSysUserNormal(sysUser);
		return list == null ? new ArrayList<SysUser>() : list;
	}

	@Override
	public int getAllSysUserNormalCount(SysUser sysUser) throws TException {
		return sysUserMapper.getAllSysUserNormalCount(sysUser);
	}
   
   
   
}
 