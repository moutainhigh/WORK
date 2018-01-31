/**
 * @Title: SysLookupServiceImpl.java
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.SysLookup;
import com.xlkfinance.bms.rpc.system.SysLookupService.Iface;
import com.xlkfinance.bms.rpc.system.SysLookupVal;
import com.xlkfinance.bms.server.system.mapper.SysLookupMapper;
import com.xlkfinance.bms.server.system.mapper.SysLookupValMapper;

/**
 * @ClassName: SysLookupServiceImpl
 * @Description: 数据字典Service
 * @author: Cai.Qing
 * @date: 2015年4月23日 下午2:39:36
 */
@SuppressWarnings("unchecked")
@Service("sysLookupServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.system.SysLookupService")
public class SysLookupServiceImpl implements Iface {

	@SuppressWarnings("rawtypes")
	@Resource(name = "sysLookupMapper")
	private SysLookupMapper sysLookupMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "sysLookupValMapper")
	private SysLookupValMapper sysLookupValMapper;

	/**
	 * @Description: 根据数据字典类型查询当前数据类型的值
	 * @param lookupType  数据字典类型
	 * @return 数据字典值集合
	 * @author: Cai.Qing
	 * @date: 2014年12月29日 上午11:24:40
	 */
	@Override
	public List<SysLookupVal> getSysLookupValByLookType(String lookupType) throws TException {
		List<SysLookupVal> list = new ArrayList<SysLookupVal>();
		try {
			list = sysLookupValMapper.getSysLookupValByLookType(lookupType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list==null?(new ArrayList<SysLookupVal>()):list;
	}

	/**
	 * @Description: 条件查询所有的数据字典配置
	 * @param sysLookup  数据字典配置对象
	 * @return 数据字典配置集合
	 * @author: Mr.Cai
	 * @date: 2014年12月24日 下午4:23:38
	 */
	@Override
	public List<SysLookup> getAllSysLookup(SysLookup sysLookup) throws TException {
		List<SysLookup> list = new ArrayList<SysLookup>();
		try {
			list = sysLookupMapper.getAllSysLookup(sysLookup);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list==null?(new ArrayList<SysLookup>()):list;
	}

	/**
	  * @Description: 根据数据字典条件查询 数据总记录条数
	  * @param sysLookup
	  * @return int 
	  * @author: Dai.jingyu
	  * @date: 2015年3月13日 下午5:11:34
	  */
	@Override
	public int getAllSysLookupSum(SysLookup sysLookup) throws TException {
		int count = 0;
		try {
			count = sysLookupMapper.getAllSysLookupSum(sysLookup);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	  * @Description: 添加数据字典
	  * @param sysLookup
	  * @return int 
	  * @author: Dai.jingyu
	  * @date: 2015年3月13日 下午5:11:34
	  */
	@Override
	public int addSysLookup(SysLookup sysLookup) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 判断是否为空
			if (null != sysLookup) {
				// 赋值状态，默认状态为 1 正常
				sysLookup.setStatus(1);
				sysLookup.setCreateDatetime(DateUtil.format(new Date()));
				// 1.先插入主表数据
				rows = sysLookupMapper.insert(sysLookup);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.SYSCONFIG_ADD, "新增数据字典失败,请重新操作!");
		}
		return rows;
	}

	/**
	  * @Description: 批量删除数据字典配置
	  * @param SysLookuPids pid列表
	  * @return int
	  * @author: Dai.jingyu
	  * @date: 2015年3月11日 下午2:51:26
	  */
	@Override
	public int deleteLookup(List<Integer> SysLookuPids) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 先删除从表信息，再删除主表信息
			rows = sysLookupMapper.deleteLookup(SysLookuPids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.SYSCONFIG_DELETE, "删除数据字典失败,请重新操作!");
		}
		return rows;
	}

	/**
	  * @Description: 根据主键更新对象
	  * @return sysLookup
	  * @author: Dai.jingyu
	  * @date: 2015年3月11日 下午2:51:26
	  */
	@Override
	public int updateSysLookup(SysLookup sysLookup) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 判断是否为空
			if (null != sysLookup) {
				// 修改数据
				rows = sysLookupMapper.updateByPrimaryKey(sysLookup);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.SYSCONFIG_UPDATE, "更新数据字典失败,请重新操作!");
		}
		return rows;
	}

	/**
	 * @Description: 获取当前数据字典下面的值集合
	 * @param lookupId 数据字典ID
	 * @return 数据字典值集合 List<SysLookupVal>
	 * @author: Cai.Qing
	 * @date: 2014年12月26日 上午11:56:02
	 */
	@Override
	public List<SysLookupVal> getSysLookupValByLookupId(int lookupId,int page,int rows) throws TException {
		List<SysLookupVal> list = new ArrayList<SysLookupVal>();
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("lookupId", lookupId);
		map.put("page", page);
		map.put("rows", rows);
		try {
			list = sysLookupValMapper.getSysLookupValByLookupId(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list==null?(new ArrayList<SysLookupVal>()):list;
	}

	/**
	  * @Description: 获取当前数据字典下面的值集合
	  * @param lookupId 数据字典ID
	  * @return int 总计录条数
	  * @author: JingYu.Dai
	  * @date: 2015年6月9日 上午11:45:37
	  */
	@Override
	public int getSysLookupValByLookupIdTotal(int lookupId) throws ThriftServiceException, TException{
		return sysLookupValMapper.getSysLookupValByLookupIdTotal(lookupId);
	}
	
	/**
	 * @Description: 删除数据字典值
	 * @param lookupValPids 数据字典pid 数组
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2014年12月26日 下午12:00:56
	 */
	@Override
	public int deleteLookupVal(String lookupPids) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			if (null != lookupPids) {
				// 劈开字符串
				String[] pids = lookupPids.split(",");
				rows = sysLookupValMapper.deleteLookupVal(pids);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.SYSCONFIG_UPDATE, "删除数据字典值失败,请重新操作!");
		}
		return rows;
	}

	
	/**
	 * @Description: 添加数据字典值
	 * @param lookupValPids 数据字典pid 数组
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2014年12月26日 下午12:00:56
	 */
	@Override
	public int addSysLookupVal(SysLookupVal sysLookupVal) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// sysLookupVal.set
			// 新增
			rows = sysLookupValMapper.insert(sysLookupVal);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.SYSCONFIG_UPDATE, "新增数据字典值失败,请重新操作!");
		}
		return rows;
	}

	/**
	 * @Description: 根据主键更新数据字典值
	 * @param lookupValPids 数据字典pid 数组
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2014年12月26日 下午12:00:56
	 */
	@Override
	public int updateSysLookupVal(SysLookupVal sysLookupVal) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			rows = sysLookupValMapper.updateByPrimaryKey(sysLookupVal);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.SYSCONFIG_UPDATE, "更新数据字典值失败,请重新操作!");
		}
		return rows;
	}

	/**
	 * @Description: 根据客户类型查询资料类型
	 * @param projectId
	 * @return List<SysLookupVal>
	 * @author: xuweihao
	 * @date: 2015年3月30日 上午9:49:40
	 */
	@Override
	public List<SysLookupVal> getDataTypeSysLookup(int projectId) throws TException {
		List<SysLookupVal> list = sysLookupValMapper.getDataTypeSysLookup(projectId);
		return list==null?(new ArrayList<SysLookupVal>()):list;
	}

	/**
	 * @Description: 抵质押物所需查询的详细信息数据
	 * @param lookupType 抵质押物类型值
	 * @return 抵质押物当前类型的详细信息集合
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月23日 上午1:53:37
	 */
	@Override
	public List<SysLookupVal> getProjectAssDtlByLookType(String lookupType) throws TException {
		List<SysLookupVal> list = new ArrayList<SysLookupVal>();
		try {
			list = sysLookupValMapper.getProjectAssDtlByLookType(lookupType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list==null?(new ArrayList<SysLookupVal>()):list;
	}

	/**
	  * @Description: 通过id得到名称
	  * @param pid
	  * @return String
	  * @throws TException
	  * @author: Rain.Lv
	  * @date: 2015年5月20日 下午3:00:08
	 */
	@Override
	public String getSysLookupValByName(int pid) throws TException {
		String lookUpValName = null;
		try {
			lookUpValName = sysLookupValMapper.getSysLookupValByName(pid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lookUpValName ==null?new String():lookUpValName;
	}
	
	/**
	 * 根据数据字典类型,和字典值     查询当前字典对象
	 * @param lookupType
	 * @param lookupVal
	 * @return
	 * @throws TException
	 */
	public SysLookupVal getSysLookupValByChildType(String lookupType,String lookupVal) throws TException {
		SysLookupVal sysLookupVal = null;
		try {
			
			Map<String,Object> queryMap = new HashMap<String,Object>();
			queryMap.put("lookupType", lookupType);
			queryMap.put("lookupVal", lookupVal);
			sysLookupVal = sysLookupValMapper.getSysLookupValByChildType(queryMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sysLookupVal ==null?new SysLookupVal():sysLookupVal;
	}
	
	/**
	 * 根据项目类型查看上传资料列表
	 */
	@Override
	public List<SysLookupVal> getDataTypeByType(String projectTypes)
			throws TException {
		List<SysLookupVal> list = new ArrayList<SysLookupVal>();	
		if (null != projectTypes) {
				// 劈开字符串
				String[] pids = projectTypes.split(",");
				list = sysLookupValMapper.getDataTypeByType(pids);
			}
		return list;
	}

	@Override
	public String getSysLookupValByPid(int pid) throws TException {
		// TODO Auto-generated method stub
		return sysLookupValMapper.getSysLookupValByPid(pid);
	}

}
