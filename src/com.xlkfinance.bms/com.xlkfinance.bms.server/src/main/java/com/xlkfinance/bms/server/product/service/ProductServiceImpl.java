/**
 * @Title: ProductServiceImpl.java
 * @Package com.xlkfinance.bms.server.product.service
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络
 * 
 * @author: Administrator
 * @date: 2016年1月13日 下午5:41:28
 * @version V1.0
 */
package com.xlkfinance.bms.server.product.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.product.ActProduct;
import com.xlkfinance.bms.rpc.product.Product;
import com.xlkfinance.bms.rpc.product.ProductService.Iface;
import com.xlkfinance.bms.server.product.mapper.ActProductMapper;
import com.xlkfinance.bms.server.product.mapper.ProductMapper;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("productServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.product.ProductService")
public class ProductServiceImpl implements Iface {

	private Logger logger = LoggerFactory
			.getLogger(ProductServiceImpl.class);

	@Resource(name = "productMapper")
	private ProductMapper productMapper;

	@Resource(name = "actProductMapper")
	private ActProductMapper actProductMapper;
	
	@Override
	public List<GridViewDTO> getAllProduct(Product product)throws ThriftServiceException, TException {
		List<GridViewDTO> listResult = productMapper.getAllProduct(product);
		if(listResult.size()==0){
			return new ArrayList<GridViewDTO>();
		}
		return listResult;
	}

	@Override
	public int getAllProductCount(Product product) throws TException {
		return productMapper.getTotalProducts(product);
	}

	@Override
	public Product getProductById(int pid) throws TException {
		Product product = null;
		try {
			product = productMapper.getProductByPid(pid);
		} catch (Exception e) {
			e.printStackTrace();
			if (logger.isDebugEnabled()) {
				logger.debug("查询产品信息根据产品ID:" + e.getMessage());
			}
		}
		return product == null ? new Product() : product;
	}

	@Override
	public int addProduct(Product product) throws ThriftServiceException,
			TException {
		int pid =0;
		try {
			pid = productMapper.getSeqBizProduct();
			product.setPid(pid);
			productMapper.addProduct(product);
			
			List<ActProduct> actList = product.getActProducts();
			if(actList != null && actList.size()>0){
				for(ActProduct actProduct : actList){
					actProduct.setProductId(pid);
					actProductMapper.addActProduct(actProduct);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CONTRACT_PRODUCT_ADD, "新增产品信息失败！");
		}
		pid = product.getPid();
		return pid;
	}

	@Override
	public int updateProduct(Product product) throws ThriftServiceException,
			TException {
		int rows = 0;
		try {
			rows = productMapper.updateByPrimaryKey(product);
			List<ActProduct> actList = product.getActProducts();
			if(actList != null && actList.size()>0){
				for(ActProduct actProduct : actList){
					actProductMapper.updateByPrimaryKey(actProduct);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CONTRACT_PRODUCT_UPDATE, "修改产品信息失败！");
		}
		return rows;
	}

	@Override
	public int deleteProduct(String pids) throws ThriftServiceException,
			TException {
		return 0;
	}

	@Override
	public List<Product> getAllProductList(Product product)
			throws ThriftServiceException, TException {
		List<Product> list = productMapper.getAllProductList(product);
		if(list.size()==0){
			list = new ArrayList<Product>();
		}
		
		return list;
	}

	@Override
	public int getProductType(int pid) throws TException {
		return productMapper.getProductType(pid);
	}

	@Override
	public Product findProductByProjectId(int projectId) throws TException {
		return productMapper.findProductByProjectId(projectId);
	}
}
