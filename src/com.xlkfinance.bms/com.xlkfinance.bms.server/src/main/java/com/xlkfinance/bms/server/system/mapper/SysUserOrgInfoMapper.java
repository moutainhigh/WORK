package com.xlkfinance.bms.server.system.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.system.SysDataAuthView;
import com.xlkfinance.bms.rpc.system.SysUserOrgInfo;
/**
 * @Desciption 数据权限信息
 * @author huxinlong
 * @version V1.0
 * @param <T>
 * @param <PK>
 */
@MapperScan("sysUserOrgInfoMapper")
public interface SysUserOrgInfoMapper<T, PK> extends BaseMapper<T, PK> {

	/**
	 * 新增数据权限
	 * @param sysUserOrgInfo
	 */
	public void saveSysUserOrgInfo(SysUserOrgInfo sysUserOrgInfo);
	
	/**
	 * 删除数据权限
	 * @param sysUserOrgInfo
	 */
	public void deleteSysUserOrgInfo(SysUserOrgInfo sysUserOrgInfo);
	
	/**
	 * 根据机构ids获取数据权限列表
	 * @param orgIds
	 * @return
	 */
	public List<SysUserOrgInfo> listUserByOrgId(Integer...orgIds);
	/**
	 * 根据userId获取数据权限列表
	 * @param userId
	 * @return
	 */
	public List<SysUserOrgInfo> listOrgByUserId(Integer userId);
	
	
	/**
	 * 根据机构编号获取用户数据权限列表
	 * @param orgIds
	 * @return
	 */
	public List<SysUserOrgInfo> listUserOrgInfoByOrgId(Integer... orgIds);
	
	/**
	 * 根据指定条件获取获取单条用户数据权限对象
	 * @param userOrg
	 * @return
	 */
	public SysUserOrgInfo getUserOrgInfo(SysUserOrgInfo userOrg);
	/**
	 * 根据指定条件获取获取单条用户数据权限对象
	 * @param userOrg
	 * @return
	 */
	public void updateUserOrgInfo(SysUserOrgInfo userOrg);
	
	/**
	 * 根据指定条件获取获取数据权限列表
	 * @param dataAuthView
	 * @return
	 */
	public List<SysDataAuthView> listDataAuth(SysDataAuthView dataAuthView);
	/**
	 * 统计数据权限记录数
	 * @param dataAuthView
	 * @return
	 */
	public int listDataAuthCount(SysDataAuthView dataAuthView);
	
	
	/**
	 * 批量删除数据权限信息
	 * @param integers
	 * @return
	 */
	public int batchDeleteDataAuth(Integer...integers);
	
}
