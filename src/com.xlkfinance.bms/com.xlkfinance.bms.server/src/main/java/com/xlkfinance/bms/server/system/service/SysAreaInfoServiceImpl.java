
package com.xlkfinance.bms.server.system.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.SysAreaInfo;
import com.xlkfinance.bms.rpc.system.SysAreaInfoService.Iface;
import com.xlkfinance.bms.server.system.mapper.SysAreaInfoMapper;


/**
 * 系统地区service
 * @author chenzhuzhen
 * @date 2016年6月30日 下午6:36:38
 */
@SuppressWarnings({"unchecked","rawtypes"})
@Service("sysAreaInfoServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.system.SysAreaInfoService")
public class SysAreaInfoServiceImpl implements Iface {

	private Logger logger = LoggerFactory.getLogger(SysConfigServiceImpl.class);
	
	@Resource(name = "sysAreaInfoMapper")
	private SysAreaInfoMapper sysAreaInfoMapper;

	/**
	 * 查询列表
	 */
	public List<SysAreaInfo> getSysAreaInfo(SysAreaInfo sysAreaInfo)throws TException {
		
		List<SysAreaInfo> resultList = sysAreaInfoMapper.getSysAreaInfo(sysAreaInfo);
		if (resultList == null) {
			resultList = new ArrayList<SysAreaInfo>();
		}
		return resultList;
	}

	/**
	 * 根据地区编号查询对象
	 */
	public SysAreaInfo getSysAreaInfoByCode(String areaCode) throws TException {
		SysAreaInfo sysAreaInfo = sysAreaInfoMapper.getSysAreaInfoByCode(areaCode);
		if(sysAreaInfo == null){
			sysAreaInfo = new SysAreaInfo();
		}
		return sysAreaInfo;
	}
	/**
	 * 根据用户Id
	 */
	@Override
	public SysAreaInfo getSysAreaInfoByUserId(int userId) throws TException {
		SysAreaInfo sysAreaInfo = sysAreaInfoMapper.getSysAreaInfoByUserId(userId);
		if(sysAreaInfo == null){
			sysAreaInfo = new SysAreaInfo();
		}
		return sysAreaInfo;
	}

	@Override
	public SysAreaInfo getSysAreaInfoByName(String areaName) throws TException {
		SysAreaInfo sysAreaInfo = sysAreaInfoMapper.getSysAreaInfoByName(areaName);
		if(sysAreaInfo == null){
			sysAreaInfo = new SysAreaInfo();
		}
		return sysAreaInfo;
	}

	/**
	 * 分页查询地区信息列表（根据传入条件查询省、市、 区信息）
	 * @see com.xlkfinance.bms.rpc.system.SysAreaInfoService.Iface#queryPagedAreaInfo(com.xlkfinance.bms.rpc.system.SysAreaInfo)
	 */
	@Override
	public List<SysAreaInfo> queryPagedAreaInfo (SysAreaInfo sysAreaInfo) throws TException {
		return sysAreaInfoMapper.queryPagedAreaInfo(sysAreaInfo);
	}

	/**
	 * 查询地区信息数量（根据传入条件查询省、市、 区信息）.
	 * @see com.xlkfinance.bms.rpc.system.SysAreaInfoService.Iface#countAreaInfo(com.xlkfinance.bms.rpc.system.SysAreaInfo)
	 */
	@Override
	public int countAreaInfo (SysAreaInfo sysAreaInfo) throws TException {
		Integer rows = sysAreaInfoMapper.countAreaInfo(sysAreaInfo);
		if (rows == null) {
			return 0;
		}
		return rows;
	}

	/**
	 * 保存地区信息.
	 * @see com.xlkfinance.bms.rpc.system.SysAreaInfoService.Iface#saveAreaInfo(com.xlkfinance.bms.rpc.system.SysAreaInfo)
	 */
	@Override
	public int saveAreaInfo (SysAreaInfo sysAreaInfo) throws ThriftServiceException, TException {
		// 判断是否为空
		if (sysAreaInfo == null ) {
			throw new ThriftServiceException(ExceptionCode.SYS_AREA_OPERATE, "地区信息不存在，保存失败!");
		}
		try {
			return sysAreaInfoMapper.saveAreaInfo(sysAreaInfo);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("保存省市区信息:" + e.getMessage());
			}
			throw new ThriftServiceException(ExceptionCode.SYS_AREA_OPERATE, "保存失败,请重新操作!");
		}
	}
	
	/**
	 * 根据地区编码删除地区信息.
	 * @see com.xlkfinance.bms.rpc.system.SysAreaInfoService.Iface#batchDelete(java.lang.String)
	 */
	@Override
	public int batchDelete (String areaCode) throws ThriftServiceException, TException {
		// 判断是否为空
		if (StringUtil.isBlank(areaCode)) {
			throw new ThriftServiceException(ExceptionCode.SYS_AREA_OPERATE, "地区编码不存在，删除失败!");
		}
		try {
			return sysAreaInfoMapper.batchDelete(areaCode.split(","));
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("删除省市区信息:" + e.getMessage());
			}
			throw new ThriftServiceException(ExceptionCode.SYS_AREA_OPERATE, "删除失败,请重新操作!");
		}
	}

}
