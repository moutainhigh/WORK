/**
 * @Title: MisapprProcessMapper.java
 * @Package com.xlkfinance.bms.server.afterloan.mapper
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Heng.Wang
 * @date: 2015年1月31日 下午3:28:23
 * @version V1.0
 */
package com.xlkfinance.bms.server.afterloan.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.afterloan.AssignmentDistribution;
import com.xlkfinance.bms.rpc.afterloan.AssignmentDistributionSearch;
import com.xlkfinance.bms.rpc.afterloan.CollectionCusComContact;
import com.xlkfinance.bms.rpc.afterloan.CollectionCustomer;
import com.xlkfinance.bms.rpc.afterloan.CollectionRecord;
import com.xlkfinance.bms.rpc.afterloan.CollectionRecordDto;
import com.xlkfinance.bms.rpc.afterloan.ProjectReminderPlanDto;
import com.xlkfinance.bms.rpc.afterloan.ReminderNotice;
import com.xlkfinance.bms.rpc.afterloan.ReminderNoticeBills;
import com.xlkfinance.bms.rpc.afterloan.ReminderNoticePart;
import com.xlkfinance.bms.rpc.afterloan.RepaymentCollection;
import com.xlkfinance.bms.rpc.afterloan.RepaymentCollectionSearch;

@MapperScan("assignmentDistributionMapper")
public interface AssignmentDistributionMapper<T, PK> extends BaseMapper<T, PK>  {
	List<AssignmentDistribution> getAssignmentDistribution(AssignmentDistributionSearch assignmentDistributionSearch);
	
	/**
	 * 增加催收人员
	  * @Description: TODO
	  * @param projectReminderPlanDto
	  * @return
	  * @author: mayinmei
	  * @date: 2015年3月19日 下午8:35:55
	 */
	public int insertReminderPlan(ProjectReminderPlanDto projectReminderPlanDto);
	
	/**
	 * 编辑催收人员
	  * @Description: TODO
	  * @param projectReminderPlanDto
	  * @return
	  * @author: zhanghg
	  * @date: 2015年7月4日 下午4:46:43
	 */
	public int updateReminderPlan(ProjectReminderPlanDto projectReminderPlanDto);
	
	/**
	  * 还款催收查询
	  * @Description: TODO
	  * @param repaymentCollection
	  * @return
	  * @author: mayinmei
	  * @date: 2015年3月23日 下午2:16:49
	 */
	public List<RepaymentCollection> getRepaymentCollectionList(RepaymentCollectionSearch repaymentCollection);
	
	/**
	 * 还款催收记录查询
	  * @Description: TODO
	  * @param CollectionRecord
	  * @return
	  * @author: mayinmei
	  * @date: 2015年3月24日 下午8:07:34
	 */
	public List<CollectionRecordDto> getCollectionRecordList(Map<String, Integer> map);
	
	/***
	 * 增加催收记录
	  * @Description: TODO
	  * @param collectionRecord
	  * @return
	  * @author: mayinmei
	  * @date: 2015年3月25日 下午6:02:46
	 */
	public int insertCollectionRecord(CollectionRecord collectionRecord);
	
	/***
	 * 根据项目编号查询客户信息
	  * @Description: TODO
	  * @param projectId
	  * @return
	  * @author: mayinmei
	  * @date: 2015年3月26日 下午4:13:01
	 */
	public CollectionCustomer getCollectionCustomer(int projectId);
	
	/**
	 * 查询客户联系人
	  * @Description: TODO
	  * @param acctId
	  * @return
	  * @author: mayinmei
	  * @date: 2015年3月26日 下午7:55:43
	 */
	public List<CollectionCusComContact> getCollectionCusComContact(int acctId);
	
	public int getRepaymentCollectionTotal(RepaymentCollectionSearch repaymentCollection);
	
	public Integer getAssignmentDistributionTotal(AssignmentDistributionSearch assignmentDistributionSearch);
	
	/**
	 * 
	  * @Description: 查询通知单序号
	  * @param year
	  * @return
	  * @author: xuweihao
	  * @date: 2015年5月14日 下午3:07:55
	 */
	public Integer getNoticeCurrentSeq(String year);
	
	/**
	 * 
	  * @Description: 新增通知单序号
	  * @param year
	  * @return
	  * @author: xuweihao
	  * @date: 2015年5月14日 下午3:12:10
	 */
	public int insertNoticeCurrentSeq(ReminderNoticeBills reminderNoticeBills);
	
	/**
	 * 
	  * @Description: 修改通知单序号
	  * @param year
	  * @return
	  * @author: xuweihao
	  * @date: 2015年5月14日 下午3:17:51
	 */
	public int updateNoticeCurrentSeq(String year);
	
	/**
	 * 
	  * @Description: 通过项目Id,当前时间查询清单
	  * @param projectId
	  * @param nowDttm
	  * @return
	  * @author: xuweihao
	  * @date: 2015年5月15日 上午9:45:58
	 */
	public List<ReminderNoticePart> getReminderNoticePart(@Param("projectId") int projectId,@Param("nowDttm")String nowDttm);
	
	/**
	 * 
	  * @Description: 通过项目Id查询项目
	  * @param projectId
	  * @return
	  * @author: xuweihao
	  * @date: 2015年5月15日 上午11:54:25
	 */
	public ReminderNotice getReminderNotice(int projectId);
	
	/**
	 * 
	  * @Description: 通过项目Id,当前时间查询清单
	  * @param reminderNoticePart
	  * @param nowDttm
	  * @return
	  * @author: xuweihao
	  * @date: 2015年6月1日 下午4:27:51
	 */
	public ReminderNoticePart getReminderNoticePartMoney(Map<String,Object> map);
}
