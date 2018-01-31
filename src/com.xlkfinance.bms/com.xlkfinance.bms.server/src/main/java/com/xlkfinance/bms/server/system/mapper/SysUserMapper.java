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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.system.EidtPassword;
import com.xlkfinance.bms.rpc.system.SysPermission;
import com.xlkfinance.bms.rpc.system.SysUser;

@MapperScan("sysUserMapper")
public interface SysUserMapper<T, PK> extends BaseMapper<T, PK> {
	/**
	 * @Description: 根据用户名查询用户对象
	 * @param userName
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2014年12月15日 下午5:45:05
	 */
	public SysUser selectSysUserByUserName(String userName);

	/**
	 * @Description: 修改用户密码
	 * @param SysUser
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2014年12月15日 下午5:59:40
	 */
	public int updateSysUserPwd(SysUser sysUser);

	/**
	 * @Description: 根据用户名查询用户权限
	 * @param userName
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2014年12月16日 上午10:33:28
	 */
	public List<SysPermission> selectSysUserPermissionByUserName(String userName);

	/**
	 * @Description: 查询所有用户（带条件）
	 * @param user 系统用户
	 * @return 系统用户集合
	 * @author: Mr.Cai
	 * @date: 2014年12月16日 上午09:33:28
	 */
	public List<SysUser> getAllSysUser(SysUser user);

	/**
	  * @Description: 查询所有用户（带条件）总记录条数
	  * @param user
	  * @return int 
	  * @author: Dai.jingyu
	  * @date: 2015年3月14日 下午4:08:47
	  */
	public int getAllSysUserCount(SysUser user);
	/**
	 * 
	 * @Description: 批量删除系统用户，根据用户名
	 * @param userName
	 *            用户名数组
	 * @author: Mr.Cai
	 * @date: 2014年12月20日 下午2:56:26
	 */
	public int batchDelete(String... userNames);

	/**
	 * 
	 * @Description: 批量更新系统用户状态，根据用户名（逻辑删除）
	 * @param userName
	 *            用户名数组
	 * @author: Mr.Cai
	 * @date: 2014年12月20日 下午2:56:26
	 */
	public int batchUpdate(String... userNames);

	/**
	 * 
	 * @Description: 查询用户是否存在
	 * @param userName
	 *            用户名
	 * @return 系统用户对象
	 * @author: Cai.Qing
	 * @date: 2014年12月26日 下午6:11:49
	 */
	public List<SysPermission> getSysUserByUserName(String userName);

	/**
	 * 
	 * @Description: 根据系统用户ID查询系统用户集合
	 * @param pids
	 *            系统用户id数组
	 * @return 系统用户集合
	 * @author: Cai.Qing
	 * @date: 2015年1月27日 下午3:35:16
	 */
	public List<SysUser> getSysUserByPids(String... pids);

	/**
	 * @Description: 查一个用户，根据用户ID
	 * @param pid
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2015年2月8日 上午11:58:21
	 */
	public SysUser getSysUserByPid(String pid);
	
	/**
	  * @Description: 修改密码
	  * @param eidtPassword
	  * @author: Dai.jingyu
	  * @date: 2015年3月17日 下午6:22:26
	  */
	public int eidtPassword(EidtPassword eidtPassword);
	
	/***
	 * 查询催收用户
	  * @Description: TODO
	  * @param roleCode
	  * @return
	  * @author: mayinmei
	  * @date: 2015年3月18日 下午6:04:26
	 */
	public List<SysUser> getUsersByRoleCode(String roleCode);
	
	/**
	  * @Description: 根据用户ID查询用户直属上级
	  * @param pid 用户ID
	  * @return SysUser
	  * @author: JingYu.Dai
	  * @date: 2015年5月3日 上午8:49:12
	  */
	public SysUser getuperiorSysUserByPid(int pid);
	
	/**
	  * @Description: 根据员工工号查询用户
	  * @param memberId
	  * @return SysUser
	  * @author: JingYu.Dai
	  * @date: 2015年5月13日 下午4:37:59
	  */
	public SysUser getUserByMemberId(String memberId);
	
	
	/**
	 * @Description 根据机构编号获取用户id
	 * @param orgIds
	 * @return
	 */
	public List<Integer> getUsersByOrgId(List<Integer> orgIds);
	/**
	 * 根据机构编号和角色编码查询该机构下的用户
	 *@author:liangyanjun
	 *@time:2016年1月29日下午4:10:00
	 *@param params
	 *@return
	 */
	public List<SysUser> getUsersByOrgIdAndRoleCode(Map<String,Object> params);
	
	
	public List<SysUser> getAcctManagerByLogin(Map<String,Object> params);

   /**
    *@author:liangyanjun
    *@time:2016年3月24日下午3:11:52
    *@param pid
    *@return
    */
   public SysUser getUserDetailsByPid(int pid);
   
   
   /**
    * 根据机构编号和角色编号获取
    * 该机构下对应角色的用户
    * @author huxinlong
    * @param orgId 
    * @param roleId
    * 
    */
   	public List<SysUser> findBizUserByOrgIdAndRoleId(HashMap paramMap);

   /**
    * 根据手机号码获取用户信息
    *@author:liangyanjun
    *@time:2016年6月20日上午10:45:18
    *@param param
    *@return
    */
   public SysUser getSysUserByPhone(String phone);

   /**
    * 根据工号获取用户信息
    *@author:liangyanjun
    *@time:2016年6月20日下午5:16:50
    *@param memberId
    *@return
    */
   public SysUser getSysUserByMemberId(String memberId);

    /** 
     * 修改系统用户token
     * @author:liangyanjun
     * @time:2016年10月25日下午3:51:32
     * @param userId
     * @param token
     * @return */
   public int updateToken(@Param("userId") int userId, @Param("token") String token);
   
   /**
    * 根据角色编码以及机构ID查询该机构树下所有的角色人员
    * @param paramMap
    * @return
    */
   public List<SysUser> getUserByOrg(Map<String,Object> params);
   
   /** 
    * 离职人员删除该用户Id
    * @author:jony
    * @time:2016年10月25日下午3:51:32
    * @param userId
    * @param token
    * @return */
  public int deleteByUserId(@Param("userId") int userId);
  
	/**
	 * @Description: 查询所有用户（带条件）
	 * @param user 系统用户
	 * @return 系统用户集合
	 * @author: Mr.Cai
	 * @date: 2014年12月16日 上午09:33:28
	 */
	public List<SysUser> getAllSysUserNormal(SysUser user);

	/**
	  * @Description: 查询所有用户（带条件）总记录条数
	  * @param user
	  * @return int 
	  * @author: Dai.jingyu
	  * @date: 2015年3月14日 下午4:08:47
	  */
	public int getAllSysUserNormalCount(SysUser user);
	
	/**
	 * 跟据用户名查询用户列表
	 * @author dulin
	 * @param usernames
	 * @return
	 */
	public List<SysUser> queryUserByUserName(String[] usernames);
}
