/**
 * @Title: ElementLendMapper.java
 * @Package com.xlkfinance.bms.server.beforeloan.mapper
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: Administrator
 * @date: 2016年2月26日 下午5:24:23
 * @version V1.0
 */
package com.xlkfinance.bms.server.beforeloan.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.beforeloan.ElementLend;
import com.xlkfinance.bms.rpc.beforeloan.ElementLendDetails;
import com.xlkfinance.bms.rpc.beforeloan.ElementMobileDto;
import com.xlkfinance.bms.rpc.beforeloan.GridViewMobileDto;
import com.xlkfinance.bms.rpc.common.GridViewDTO;

public interface ElementLendMapper<T, PK> extends BaseMapper<T, PK> {
	/**
	 * 获取当前新的要件借出ID
	  * @Description: TODO
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月14日 下午4:54:49
	 */
	public int getSeqBizElementLend();
	
	/**
	 * 分页查询要件借出信息
	  * @Description: TODO
	  * @param product
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月14日 下午4:11:13
	 */
	public List<GridViewDTO> getAllElementLend(ElementLend elementLend);
	/**
	 * 查询要件借出总数
	  * @Description: TODO
	  * @param product
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月14日 下午4:16:09
	 */
	public int getTotalElementLends(ElementLend elementLend);
	
	/**
	 * 新增要件借出信息
	  * @Description: TODO
	  * @param product
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月14日 下午4:11:53
	 */
	public int addElementLend(ElementLend elementLend);
	
	/**
	 * 修改要件借出信息
	  * @Description: TODO
	  * @param product
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月14日 下午4:15:16
	 */
	public int updateByPrimaryKey(ElementLend elementLend);
	
	/**
	 * 查询要件借出详情
	  * @Description: TODO
	  * @param pid
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月14日 下午4:16:56
	 */
	public ElementLend getElementLendByPid(int pid);
	
	/**
	 * 修改要件借出状态
	  * @Description: TODO
	  * @param pid
	  * @param lendState
	  * @return
	  * @author: andrew
	  * @date: 2016年2月27日 上午11:07:58
	 */
	public int updateLendStateByPid(@Param(value = "pid")int pid,  @Param(value = "lendState")int lendState,@Param(value = "updateTime")String updateTime);

	/**
	 * 添加要件借出详情
	  * @param details
	  * @return
	  * @author: Administrator
	  * @date: 2016年4月12日 上午11:43:51
	 */
	public int addElementLendDetails(ElementLendDetails details);
	
	/**
	 * 修改要件借出详情
	  * @param details
	  * @return
	  * @author: Administrator
	  * @date: 2016年4月12日 上午11:48:19
	 */
	public int updateElementLendDetails(ElementLendDetails details);
	
	/**
	 * 查询要件借出详情
	  * @param details
	  * @return
	  * @author: Administrator
	  * @date: 2016年4月12日 上午11:48:54
	 */
	public List<ElementLendDetails> queryElementLendDetail(ElementLendDetails details);

	/**
	 * 删除要件借出详情
	  * @param details 
	  * @return
	  * @author: Administrator
	  * @date: 2016年4月12日 上午11:49:13
	 */
	public int deleteElementLendDetails(ElementLendDetails details);
	

	/**
	 * 
	  * @Description: 要件申请项目查询
	  * @param elementMobileDto
	  * @return
	  * @author: xiayt
	  * @date: 2016年6月2日 上午11:29:01
	 */
	public List<GridViewMobileDto> queryElementList(ElementMobileDto elementMobileDto);

	
	/**
	 * 要件申请项目查询
	  * @Description: TODO
	  * @param elementMobileDto
	  * @return
	  * @author: xiayt
	  * @date: 2016年6月2日 上午11:29:21
	 */
	public int getTotalElement(ElementMobileDto elementMobileDto);
	
	/**
	 * 
	  * @Description: 修改要件借出申请和详情信息
	  * @param elementLend
	  * @param elementLendDetailsList
	  * @return
	  * @author: xiayt
	  * @date: 2016年6月4日 下午5:05:08
	 */
	public int batchUpdateElementLendDetails( List<ElementLendDetails> elementLendDetailsList);
	
	/**
	 * 
	  * @Description: 修改要件借出申请和详情信息
	  * @param elementLend
	  * @param elementLendDetailsList
	  * @return
	  * @author: xiayt
	  * @date: 2016年6月4日 下午5:05:08
	 */
	public int updateElementLendDetails(ElementLend elementLend, List<ElementLendDetails> elementLendDetailsList);
	
	/**
	 * 
	  * @Description: 根据让键ID修改要件详情
	  * @param details
	  * @return
	  * @author: xiayt
	  * @date: 2016年6月8日 上午10:29:29
	 */
	public int updateElementLendDetailByKey(ElementLendDetails details);
}
