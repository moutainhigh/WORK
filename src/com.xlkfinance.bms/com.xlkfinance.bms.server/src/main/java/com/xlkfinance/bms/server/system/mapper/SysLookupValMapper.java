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

import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.system.SysLookupVal;

/**
 * 
 * @Title: SysLookupValMapper.java
 * @Description: 数据字典值 mapper
 * @author : Mr.Cai
 * @date : 2014年12月24日 下午5:21:52
 * @email: caiqing12110@vip.qq.com
 */
@MapperScan("sysLookupValMapper")
public interface SysLookupValMapper<T, PK> extends BaseMapper<T, PK> {

	/**
	 * @Description: 根据数据字典类型查询当前数据类型的值
	 * @param lookupType 数据字典类型
	 * @return 数据字典值集合
	 * @author: Cai.Qing
	 * @date: 2014年12月29日 上午11:24:40
	 */
	public List<SysLookupVal> getSysLookupValByLookType(String lookupType);

	/**
	 * 
	 * @Description: 抵质押物所需查询的详细信息数据
	 * @param lookupType
	 *            抵质押物类型值
	 * @return 抵质押物当前类型的详细信息集合
	 * @author: Cai.Qing
	 * @date: 2015年4月23日 上午1:55:01
	 */
	public List<SysLookupVal> getProjectAssDtlByLookType(String lookupType);

	/**
	 * 
	 * @Description: 获取当前数据字典下面的值集合
	 * @param lookupId 数据字典ID
	 * @return 数据字典值集合 List<SysLookupVal>
	 * @author: Cai.Qing
	 * @date: 2014年12月26日 上午11:56:02
	 */
	public List<SysLookupVal> getSysLookupValByLookupId(Map<String,Integer> map);
	
	/**
	  * @Description: 获取当前数据字典下面的值集合
	  * @param lookupId 数据字典ID
	  * @return int 总计录条数
	  * @author: JingYu.Dai
	  * @date: 2015年6月9日 上午11:45:37
	 */
	public int getSysLookupValByLookupIdTotal(int lookupId);

	/**
	 * 
	 * @Description: 批量删除数据字典值
	 * @param lookupValPids
	 *            lookupId 数组
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2014年12月24日 下午5:23:30
	 */
	public int batchDelete(String[] lookupValPids);

	/**
	 * 
	 * @Description: 删除数据字典值
	 * @param lookupValPids
	 *            数据字典pid 数组
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2014年12月26日 下午12:00:56
	 */
	public int deleteLookupVal(String[] lookupValPids);

	/**
	 * 
	 * @Description: 根据pid获取数据字典值
	 * @param pid
	 * @return
	 * @author: zhanghg
	 * @date: 2015年2月10日 下午12:48:33
	 */
	public String getSysLookupValByPid(int pid);

	/**
	 * 
	 * @Description: 根据客户类型查询资料类型
	 * @param projectId
	 * @return
	 * @author: xuweihao
	 * @date: 2015年3月30日 上午9:49:40
	 */
	public List<SysLookupVal> getDataTypeSysLookup(int projectId);

	/**
	 * 
	 * @Description: 根据lookupType ,lookupVal获取lookupValId
	 * @return
	 * @author: zhanghg
	 * @date: 2015年4月17日 下午2:07:26
	 */
	public int getPidIdByLookupVal(Map<String, String> map);
	
	/**
	 * 
	  * @Description: 通过id得到名称
	  * @param projectId
	  * @return
	  * @author: Rain.Lv
	  * @date: 2015年5月20日 下午2:39:55
	 */
	public String getSysLookupValByName(int projectId);
	
	
	/**
	 * 根据数据字典类型,和字典值     查询当前字典对象
	 * @return
	 * @throws TException
	 */
	public SysLookupVal getSysLookupValByChildType(Map<String,Object> queryMap) ;
	
	/**
	 * 根据项目类型查看上传资料列表
	 * @param projectTypes
	 * @return
	 */
	public List<SysLookupVal> getDataTypeByType(String[] projectTypes);

}
