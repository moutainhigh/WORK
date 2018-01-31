/**
 * 
 */
package com.xlkfinance.bms.server.system.mapper;

import java.util.*;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.system.SysOrgInfo;

/**
 * @Title: SysOrgInfoMapper.java
 * @Package com.xlkfinance.bms.server.system.mapper
 * @Description: 用户数据权限操作，表：sys_org_info
 * @author huxinlong  
 * @date 2016年1月12日 下午5:53:17
 * @version V1.0  
 */
@MapperScan("sysOrgInfoMapper")
public interface SysOrgInfoMapper<T, PK> extends BaseMapper<T, PK> {

	
	/**
	 * 新增机构
	 * @param sysOrgInfo
	 */
	public void saveSysOrgInfo(SysOrgInfo sysOrgInfo);
	/**
	 * 删除机构
	 * @param sysOrgInfo
	 */
	public void deleteSysOrgInfo(SysOrgInfo sysOrgInfo);
	/**
	 * 修改机构
	 * @param sysOrgInfo
	 */
	public void updateSysOrgInfo(SysOrgInfo sysOrgInfo);
	/**
	 * 根据机构Id获取机构信息
	 * @param orgId
	 */
	public SysOrgInfo getSysOrgInfo(Integer orgId);
	

	/**
	 * 根据机构类型获取机构列表
	 * @param category
	 * @return
	 */
	public List<SysOrgInfo> getSysOrgInfoByCategory(Integer category);

	
	/**
	 * 根据父级机构编号获取其下所有子机构
	 * @param parentId
	 * @return
	 */
	public List<SysOrgInfo> listSysOrgByParentId(Integer parentId);
	
	
	/**
	 * 根据机构名称和父级机构编号获取机构信息
	 * @param parentId
	 * @param name
	 * @return
	 */
	public SysOrgInfo getSysOrgInfoByName(Map<String,String> parameters);
	
	/**
	 * 获取机构列表
	 * @param org
	 * @return
	 */
	public List<SysOrgInfo> listSysOrgByLayer(SysOrgInfo org);
	
	
	
	/**
	 * 获取父级机构列表
	 * @param org
	 * @return
	 */
	public List<SysOrgInfo> listSysParentOrg(SysOrgInfo org);
	
	/**
	 * 修改机构树
	 * @param sysOrgInfo
	 */
	public void updateApplicationTree(SysOrgInfo sysOrgInfo);
	
}
