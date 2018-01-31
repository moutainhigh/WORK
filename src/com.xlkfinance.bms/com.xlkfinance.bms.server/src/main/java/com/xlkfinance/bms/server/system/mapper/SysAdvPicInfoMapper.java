/**
 * @Title: SysAdvPicInfoMapper.java
 * @Package com.xlkfinance.bms.server.system.mapper
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: baogang
 * @date: 2016年4月25日 上午10:11:49
 * @version V1.0
 */
package com.xlkfinance.bms.server.system.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.system.SysAdvPicInfo;
/**
 * 广告图片Mapper
  * @ClassName: SysAdvPicInfoMapper
  * @author: baogang
  * @date: 2016年4月25日 上午10:12:03
 */
@MapperScan("sysAdvPicInfoMapper")
public interface SysAdvPicInfoMapper<T, PK> extends BaseMapper<T, PK> {

	/**
	 * 添加广告信息
	  * @param sysAdvPicInfo
	  * @return
	  * @author: baogang
	  * @date: 2016年4月25日 上午11:24:47
	 */
	public int addSysAdvPicInfo(SysAdvPicInfo sysAdvPicInfo);
	
	/**
	 * 修改广告信息
	  * @param sysAdvPicInfo
	  * @return
	  * @author: baogang
	  * @date: 2016年4月25日 上午11:24:57
	 */
	public int updateByPrimaryKey(SysAdvPicInfo sysAdvPicInfo);
	
	/**
	 * 获取广告总数
	  * @param sysAdvPicInfo
	  * @return
	  * @author: baogang
	  * @date: 2016年4月25日 上午11:25:06
	 */
	public int getCountAdv(SysAdvPicInfo sysAdvPicInfo);
	
	/**
	 * 获取广告列表
	  * @param sysAdvPicInfo
	  * @return
	  * @author: baogang
	  * @date: 2016年4月25日 上午11:25:19
	 */
	public List<SysAdvPicInfo> querySysAdvPics(SysAdvPicInfo sysAdvPicInfo);
	
	/**
	 * 获取广告详情
	  * @param pid
	  * @return
	  * @author: baogang
	  * @date: 2016年4月25日 上午11:25:27
	 */
	public SysAdvPicInfo querySysAdvPicByPid(int pid);
}
