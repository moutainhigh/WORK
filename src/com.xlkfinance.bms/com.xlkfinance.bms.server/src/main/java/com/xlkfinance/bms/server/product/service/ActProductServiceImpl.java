/**
 * @Title: ActProductServiceImpl.java
 * @Package com.xlkfinance.bms.server.product.service
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络
 * 
 * @author: Administrator
 * @date: 2016年1月13日 下午5:41:14
 * @version V1.0
 */
package com.xlkfinance.bms.server.product.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.product.ActProduct;
import com.xlkfinance.bms.rpc.product.ActProductService.Iface;
import com.xlkfinance.bms.server.product.mapper.ActProductMapper;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("actProductServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.product.ActProductService")
public class ActProductServiceImpl implements Iface{

	private Logger logger = LoggerFactory
			.getLogger(ActProductServiceImpl.class);
	
	@Resource(name = "actProductMapper")
	private ActProductMapper actProductMapper;
	
	@Override
	public List<ActProduct> getAllProduct(ActProduct actProduct)
			throws ThriftServiceException, TException {
		List<ActProduct> resultList = actProductMapper.getAllActProduct(actProduct);
		return resultList;
	}

	@Override
	public int addActProduct(ActProduct actProduct)
			throws ThriftServiceException, TException {
		try {
			actProductMapper.addActProduct(actProduct);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return 0;
	}

	@Override
	public int updateActProduct(ActProduct actProduct)
			throws ThriftServiceException, TException {
		int rows = 0;
		try {
			rows = actProductMapper.updateByPrimaryKey(actProduct);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return rows;
	}

	@Override
	public int deleteActProduct(int pid) throws ThriftServiceException,
			TException {
		try {
			actProductMapper.deleteActProductById(pid);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return 0;
	}

}
