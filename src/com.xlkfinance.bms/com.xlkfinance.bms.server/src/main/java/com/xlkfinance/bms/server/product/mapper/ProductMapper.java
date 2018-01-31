/**
 * @Title: ProductMapper.java
 * @Package com.xlkfinance.bms.server.product.mapper
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络
 * 
 * @author: Administrator
 * @date: 2016年1月13日 下午5:37:05
 * @version V1.0
 */
package com.xlkfinance.bms.server.product.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.product.Product;
/**
 * 	产品管理
  * @ClassName: ProductMapper
  * @Description: TODO
  * @author: Administrator
  * @date: 2016年1月13日 下午5:47:59
 */
@MapperScan("productMapper")
public interface ProductMapper<T, PK> extends BaseMapper<T, PK> {
	
	/**
	 * 获取当前新的产品ID
	  * @Description: TODO
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月14日 下午4:54:49
	 */
	public int getSeqBizProduct();
	
	/**
	 * 分页查询产品信息
	  * @Description: TODO
	  * @param product
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月14日 下午4:11:13
	 */
	public List<GridViewDTO> getAllProduct(Product product);
	/**
	 * 查询产品总数
	  * @Description: TODO
	  * @param product
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月14日 下午4:16:09
	 */
	public int getTotalProducts(Product product);
	
	/**
	 * 新增产品信息
	  * @Description: TODO
	  * @param product
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月14日 下午4:11:53
	 */
	public int addProduct(Product product);
	
	/**
	 * 修改产品信息
	  * @Description: TODO
	  * @param product
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月14日 下午4:15:16
	 */
	public int updateByPrimaryKey(Product product);
	
	/**
	 * 查询产品详情
	  * @Description: TODO
	  * @param pid
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月14日 下午4:16:56
	 */
	public Product getProductByPid(int pid);

	/**
	 * 不分页查询产品列表
	  * @param product
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月22日 上午11:31:16
	 */
	public List<Product> getAllProductList(Product product);
	/**
	 * 查询产品类型（赎楼/现金）
	  * @param pid
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月22日 上午11:31:56
	 */
	public int getProductType(int pid);
	
	/**
	 * 通过项目ID查询产品信息
	  * @Description: TODO
	  * @param projectId
	  * @return
	  * @author: andrew
	  * @date: 2016年2月27日 下午2:17:55
	 */
	public Product findProductByProjectId(int projectId); 
	
	/**
	 * 查询机构产品信息
	  * @param product
	  * @return
	  * @author: baogang
	  * @date: 2016年8月10日 下午4:06:56
	 */
	public Product getOrgProduct(Product product);
}
