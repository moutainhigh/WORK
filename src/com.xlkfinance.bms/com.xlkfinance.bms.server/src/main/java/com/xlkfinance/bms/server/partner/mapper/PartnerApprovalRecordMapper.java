/**
 * @Title: PartnerApprovalRecordMapper.java
 * @Package com.xlkfinance.bms.server.partner.mapper
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: Administrator
 * @date: 2016年3月16日 下午4:36:59
 * @version V1.0
 */
package com.xlkfinance.bms.server.partner.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.partner.PartnerApprovalRecord;
/**
 * 合作机构项目审批记录
  * @ClassName: PartnerApprovalRecordMapper
  * @author: Administrator
  * @date: 2016年3月16日 下午4:37:09
 */
@MapperScan("partnerApprovalRecordMapper")
public interface PartnerApprovalRecordMapper<T, PK> extends BaseMapper<T, PK> {

	public List<PartnerApprovalRecord> findAllPartnerApprovalRecord(int partnerId);
	
	public int addPartnerApprovalRecord(PartnerApprovalRecord record);
	
	/**
	 * 更新机构审核记录
	 */
	public int updatePartnerApprovalRecord(PartnerApprovalRecord record);
}
