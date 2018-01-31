/**
 * @Title: ProjectPartnerRefundMapper.java
 * @Package com.xlkfinance.bms.server.partner.mapper
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: chenzhuzhen
 * @date: 2016年7月25日 上午11:22:03
 * @version V1.0
 */
package com.xlkfinance.bms.server.partner.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerRefund;
/**
 * 项目合作机构还款
 * @author chenzhuzhen
 * @date 2016年7月25日 上午11:22:03
 * @param <T>
 * @param <PK>
 */
@MapperScan("projectPartnerRefundMapper")
public interface ProjectPartnerRefundMapper<T, PK> extends BaseMapper<T, PK> {
	
	/**生成主键*/
	public int getSeqBizProjectPartnerRefund();

	/**查询还款列表*/
	public List<ProjectPartnerRefund> findAllProjectPartnerRefund(ProjectPartnerRefund projectPartnerRefund);
	/**根据id查询还款信息*/
	public ProjectPartnerRefund findProjectPartnerRefund(ProjectPartnerRefund projectPartnerRefund);
	
	/**查询还款列表总数*/
	public int findAllProjectPartnerRefundCount(ProjectPartnerRefund projectPartnerRefund);
	
	/**添加还款*/
	public int addProjectPartnerRefund(ProjectPartnerRefund projectPartnerRefund);
	
	/**更新还款*/
	public int updateProjectPartnerRefund(ProjectPartnerRefund projectPartnerRefund);
	
	/**更新分期还款金额（可改为0）*/
	public int updatePartnerRefundMoney(ProjectPartnerRefund projectPartnerRefund);
	
	
}
