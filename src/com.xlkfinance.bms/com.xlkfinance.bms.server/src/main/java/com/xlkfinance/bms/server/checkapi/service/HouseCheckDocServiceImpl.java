/**
 * @Title: ProjectPartnerServiceImpl.java
 * @Package com.xlkfinance.bms.server.partner.service
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: Administrator
 * @date: 2016年3月18日 下午2:38:58
 * @version V1.0
 */
package com.xlkfinance.bms.server.checkapi.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.rpc.checkapi.HouseCheckDoc;
import com.xlkfinance.bms.rpc.checkapi.HouseCheckDocDetails;
import com.xlkfinance.bms.rpc.checkapi.HouseCheckDocService.Iface;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.server.checkapi.mapper.HouseCheckDocDetailsMapper;
import com.xlkfinance.bms.server.checkapi.mapper.HouseCheckDocMapper;

/**
 * 自动查档日记业务实现类
 * @author chenzhuzhen
 * @date 2016年10月27日 下午4:54:28
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("houseCheckDocServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.checkapi.HouseCheckDocService")
public class HouseCheckDocServiceImpl implements Iface {
	
	private Logger logger = LoggerFactory.getLogger(HouseCheckDocServiceImpl.class);
	
	@Resource(name = "houseCheckDocMapper")
	private HouseCheckDocMapper houseCheckDocMapper;
	
	@Resource(name = "houseCheckDocDetailsMapper")
	private HouseCheckDocDetailsMapper houseCheckDocDetailsMapper;

	/**
	 * 查询查档记录列表
	 */
	@Override
	public List<HouseCheckDoc> getHouseCheckDocs(HouseCheckDoc houseCheckDoc)
			throws ThriftServiceException, TException {
		List<HouseCheckDoc> list = null;
		try {
			list  = houseCheckDocMapper.getHouseCheckDocs(houseCheckDoc);
		} catch (Exception e) {
			logger.error(">>>>>>getHouseCheckDocs error",e);
			throw new ThriftServiceException(ExceptionCode.MOBILE_SERVER_ERROR, "查询查档记录列表！");
		}
		if(list == null ){
			list = new ArrayList<HouseCheckDoc>();
		}
		return list;
	}

	/**
	 * 查询查档记录数量
	 */
	@Override
	public int getHouseCheckDocCount(HouseCheckDoc houseCheckDoc)
			throws ThriftServiceException, TException {
		int count = houseCheckDocMapper.getHouseCheckDocCount(houseCheckDoc);
		return count;
	}

	/**
	 *  新增查档记录
	 */
	@Override
	public int addHouseCheckDoc(HouseCheckDoc houseCheckDoc)
			throws ThriftServiceException, TException {
		Timestamp time = new Timestamp(new Date().getTime());
		int pid = houseCheckDoc.getPid();
		try {
			if(pid  == 0 ){
				pid = houseCheckDocMapper.getSeqHouseCheckDoc();
				houseCheckDoc.setPid(pid);
				houseCheckDocMapper.addHouseCheckDoc(houseCheckDoc);
			}else{
				//更新
				HouseCheckDoc updateHouseCheckDoc = new HouseCheckDoc();
				updateHouseCheckDoc.setPid(pid);
				updateHouseCheckDoc.setUpdateTime(time.toString());
				houseCheckDocMapper.updateHouseCheckDoc(updateHouseCheckDoc);
			}
			
			List<HouseCheckDocDetails> list = houseCheckDoc.getDetailsList();
			if(list != null && list.size()> 0 ){
				for (HouseCheckDocDetails houseCheckDocDetails : list) {
					int detailsPid = houseCheckDocDetailsMapper.getSeqHouseCheckDocDetails();
					houseCheckDocDetails.setPid(detailsPid);
					houseCheckDocDetails.setHouseCheckDocId(pid);
					houseCheckDocDetails.setCreateTime(time.toString());
					//添加查档详情
					houseCheckDocDetailsMapper.addHouseCheckDocDetails(houseCheckDocDetails);
				}
			}
			
		} catch (Exception e) {
			logger.error(">>>>>>addHouseCheckDoc error",e);
			throw new ThriftServiceException(ExceptionCode.CREDIT_ADD, "新增查档记录失败！");
		}
		return pid;
	}

	/**
	 * 修改查档记录
	 */
	@Override
	public int updateHouseCheckDoc(HouseCheckDoc houseCheckDoc)
			throws ThriftServiceException, TException {
		int rows = 0;
		try {
			rows = houseCheckDocMapper.updateHouseCheckDoc(houseCheckDoc);
		} catch (Exception e) {
			logger.error(">>>>>>updateHouseCheckDoc error",e);
			throw new ThriftServiceException(ExceptionCode.CREDIT_ADD, "修改查档记录失败！");
		}
		return rows;
	}

	
	/**
	 * 查询最后一条查档记录
	 */
	@Override
	public HouseCheckDocDetails getLastOneCheckDocDetails(Map<String, String> queryMap) throws ThriftServiceException,
			TException {
		HouseCheckDocDetails details = null;
		try {
			details  = houseCheckDocDetailsMapper.getLastOneCheckDocDetails(queryMap);
		} catch (Exception e) {
			logger.error(">>>>>>getLastOneCheckDocDetails error",e);
			throw new ThriftServiceException(ExceptionCode.MOBILE_SERVER_ERROR, "查询最后一条查档记录！");
		}
		
		if(details == null ){
			details = new HouseCheckDocDetails();
		}
		return details;
	}

	/**
	 * 通过主键查询
	 */
	@Override
	public HouseCheckDoc getHouseCheckDocById(int pid)
			throws ThriftServiceException, TException {
		HouseCheckDoc doc = houseCheckDocMapper.getHouseCheckDocById(pid);
		if(doc == null){
			doc = new HouseCheckDoc();
		}
		return doc;
	}

	/**
	 * 查询查档详情列表
	 */
	@Override
	public List<HouseCheckDocDetails> getCheckDocDetailsList(
			HouseCheckDocDetails houseCheckDocDetails) throws ThriftServiceException,TException {
		List<HouseCheckDocDetails> list =  houseCheckDocDetailsMapper.getCheckDocDetailsList(houseCheckDocDetails);
		if(list == null){
			list = new ArrayList<HouseCheckDocDetails>();
		}
		return list;
	}
	
	
}
