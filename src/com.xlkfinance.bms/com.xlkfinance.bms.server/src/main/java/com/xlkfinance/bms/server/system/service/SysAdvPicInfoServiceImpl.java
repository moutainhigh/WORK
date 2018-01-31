/**
 * @Title: SysAdvPicInfoServiceImpl.java
 * @Package com.xlkfinance.bms.server.system.service
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: baogang
 * @date: 2016年4月25日 上午9:31:55
 * @version V1.0
 */
package com.xlkfinance.bms.server.system.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.system.SysAdvPicInfo;
import com.xlkfinance.bms.rpc.system.SysAdvPicService.Iface;
import com.xlkfinance.bms.server.system.mapper.SysAdvPicInfoMapper;

/**
 * 广告图片Service
  * @ClassName: SysAdvPicInfoServiceImpl
  * @author: baogang
  * @date: 2016年4月25日 上午9:33:36
 */
@Service("sysAdvPicInfoServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.system.SysAdvPicService")
public class SysAdvPicInfoServiceImpl implements Iface {
	 private Logger logger = LoggerFactory.getLogger(SysAdvPicInfoServiceImpl.class);

	 @Resource(name = "sysAdvPicInfoMapper")
	 private SysAdvPicInfoMapper sysAdvMapper;
	 
	@Override
	public int addSysAdvPicInfo(SysAdvPicInfo sysAdvPicInfo) throws TException {
		try {
			
			sysAdvMapper.addSysAdvPicInfo(sysAdvPicInfo);
		} catch (Exception e) {
			logger.error("添加广告图片:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return sysAdvPicInfo.getPid();
	}

	@Override
	public int updateSysAdvPicInfo(SysAdvPicInfo sysAdvPicInfo)
			throws TException {
		try {
			sysAdvMapper.updateByPrimaryKey(sysAdvPicInfo);
			
		} catch (Exception e) {
			logger.error("修改广告图片:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return sysAdvPicInfo.getPid();
	}

	@Override
	public List<SysAdvPicInfo> querySysAdvPics(SysAdvPicInfo sysAdvPicInfo)
			throws TException {
		List<SysAdvPicInfo> querySysAdvPics = sysAdvMapper.querySysAdvPics(sysAdvPicInfo);
		if(querySysAdvPics ==null){
			querySysAdvPics = new ArrayList<SysAdvPicInfo>();
		}
		return querySysAdvPics;
	}

	@Override
	public SysAdvPicInfo querySysAdvPic(int pid)
			throws TException {
		SysAdvPicInfo sysAdvPicInfo = sysAdvMapper.querySysAdvPicByPid(pid);
		if(sysAdvPicInfo == null){
			sysAdvPicInfo = new SysAdvPicInfo();
		}
		return sysAdvPicInfo;
	}

	@Override
	public int getSysAdvCount(SysAdvPicInfo sysAdvPicInfo) throws TException {
		
		return sysAdvMapper.getCountAdv(sysAdvPicInfo);
	}
}
