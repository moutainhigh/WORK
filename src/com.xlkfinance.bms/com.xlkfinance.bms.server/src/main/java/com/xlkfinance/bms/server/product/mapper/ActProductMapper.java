/**
 * @Title: ActProductMapper.java
 * @Package com.xlkfinance.bms.server.product.mapper
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络
 * 
 * @author: Administrator
 * @date: 2016年1月13日 下午5:37:37
 * @version V1.0
 */
package com.xlkfinance.bms.server.product.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.product.ActProduct;
/**
 * 	产品流程关系
  * @ClassName: ActProductMapper
  * @Description: TODO
  * @author: Administrator
  * @date: 2016年1月13日 下午5:50:41
 */
@MapperScan("actProductMapper")
public interface ActProductMapper<T, PK> extends BaseMapper<T, PK> {
	/**
	 *	根据产品ID查询相关流程
	  * @Description: TODO
	  * @param productId
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月14日 下午4:11:13
	 */
	public List<ActProduct> getAllActProduct(ActProduct actProduct);
	
	/**
	 * 新增产品关联信息
	  * @Description: TODO
	  * @param product
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月14日 下午4:11:53
	 */
	public int addActProduct(ActProduct actProduct);
	
	/**
	 * 修改产品关联信息
	  * @Description: TODO
	  * @param actProduct
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月14日 下午4:15:16
	 */
	public int updateByPrimaryKey(ActProduct actProduct);
	
	/**
	 * 删除产品关联信息
	  * @Description: TODO
	  * @param pid
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月14日 下午4:16:56
	 */
	public int deleteActProductById(int pid);
}
