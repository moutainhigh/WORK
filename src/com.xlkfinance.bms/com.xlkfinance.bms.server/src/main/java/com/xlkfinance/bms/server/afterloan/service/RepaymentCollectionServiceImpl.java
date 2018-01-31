/**
 * @Title: AssignmentDistributionServiceImpl.java
 * @Package com.xlkfinance.bms.server.afterloan.service
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Heng.Wang
 * @date: 2015年1月31日 下午3:21:43
 * @version V1.0
 */
package com.xlkfinance.bms.server.afterloan.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oracle.jdbc.OracleTypes;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
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
import com.xlkfinance.bms.rpc.afterloan.RepaymentCollectionService.Iface;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.server.afterloan.mapper.AssignmentDistributionMapper;
import com.xlkfinance.bms.server.afterloan.mapper.CollectionRecordMapper;


@Service("repaymentCollectionServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.afterloan.RepaymentCollectionService")
public class RepaymentCollectionServiceImpl implements Iface  {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "assignmentDistributionMapper")
	AssignmentDistributionMapper assignmentDistributionMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "collectionRecordMapper")
	CollectionRecordMapper collectionRecordMapper;


	/**
	 * 还款催收任务分配
	 */
	@Override
	public List<AssignmentDistribution> getAssignmentDistribution(
			AssignmentDistributionSearch assignmentDistributionSearch) throws TException {
		assignmentDistributionMapper.getAssignmentDistribution(assignmentDistributionSearch);
		List<AssignmentDistribution> result=assignmentDistributionSearch.getResults();
		if(null==result){
			result=new ArrayList<AssignmentDistribution>();
		}
		for( int i =0;i<result.size();i++){
			if(result.get(i).getAcctType().equals("1")){
				result.get(i).setAcctType("个人客户");
			}else{
				result.get(i).setAcctType("企业客户");
			}
			if(result.get(i).getUserId() == 0){
				result.get(i).setUserIdStr("");
			}else{
				result.get(i).setUserIdStr(result.get(i).getUserId()+"");
			}
		}
		logger.info("还款催收任务分配  查询结果集："+result+"size():"+result.size());
		return result;
	}



    /**
     * 新增催收计划
     */
	@Override
	public int insertReminderPlan(ProjectReminderPlanDto projectReminderPlanDto)
			throws ThriftServiceException, TException {
		return assignmentDistributionMapper.insertReminderPlan(projectReminderPlanDto);
	}
	/**
	 * 修改催收计划
	 */
	@Override
	public int updateReminderPlan(ProjectReminderPlanDto projectReminderPlanDto)
			throws ThriftServiceException, TException {
		return assignmentDistributionMapper.updateReminderPlan(projectReminderPlanDto);
	}



    /**
     * 还款催收查询
     */
	@Override
	public List<RepaymentCollection> getRepaymentCollectionList(
			RepaymentCollectionSearch repaymentCollection) throws TException {
		assignmentDistributionMapper.getRepaymentCollectionList(repaymentCollection);
		List<RepaymentCollection> list=repaymentCollection.getResults();
		if(null==list){
			 list=new ArrayList<RepaymentCollection>();
		}
		for( int i =0;i<list.size();i++){
			if(list.get(i).getAcctType().equals("1")){
				list.get(i).setAcctType("个人客户");
			}else{
				list.get(i).setAcctType("企业客户");
			}
		}
		return list;
	}
	
	/**
	 * 催收记录
	 */
	@Override
	public List<CollectionRecord> getCollectionRecord(
			CollectionRecord collectionRecord) throws TException {
		return collectionRecordMapper.getCollectionRecord(collectionRecord);
	}

	/**
	 * 查看催收记录
	 */
	@Override
	public List<CollectionRecordDto> getCollectionRecordList(Map<String,Integer> map) throws TException {
		List<CollectionRecordDto> list=null;
		list=assignmentDistributionMapper.getCollectionRecordList(map);
		if(null==list){
			return new ArrayList<CollectionRecordDto>();
		}
		return list;
	}



	/**
	 * 添加催收记录
	 */
	@Override
	public int insertCollectionRecord(CollectionRecord collectionRecord)
			throws ThriftServiceException, TException {
		return assignmentDistributionMapper.insertCollectionRecord(collectionRecord);
	}
	/**
	 * 根据项目编号查询客户信息
	 */
	@Override
	public CollectionCustomer getCollectionCustomer(int projectId)
			throws TException {
		CollectionCustomer collectionCustomer=assignmentDistributionMapper.getCollectionCustomer(projectId);
		if(null==collectionCustomer){
			collectionCustomer=new CollectionCustomer();
		}
		return collectionCustomer;
	}

	/**
	 * 查询客户联系人信息
	 */
	@Override
	public List<CollectionCusComContact> getCollectionCusComContact(int acctId)
			throws TException {
		List<CollectionCusComContact> list=null;
		list=assignmentDistributionMapper.getCollectionCusComContact(acctId);
		if(null==list){
			return new ArrayList<CollectionCusComContact>();
		}
		return list;
	}

	/**
	 * 查还款催收的总记录数
	 */
	public int getRepaymentCollectionTotal(RepaymentCollectionSearch repaymentCollection){
		assignmentDistributionMapper.getRepaymentCollectionTotal(repaymentCollection);
		return assignmentDistributionMapper.getRepaymentCollectionTotal(repaymentCollection);
	}
	
	public int getAssignmentDistributionTotal(AssignmentDistributionSearch assignmentDistributionSearch){
		assignmentDistributionMapper.getAssignmentDistributionTotal(assignmentDistributionSearch);
		return assignmentDistributionSearch.getRows();
	}

    /**
     *  根据项目ID查催收记录
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<CollectionRecord> getCollectionRecordsByProjectIds(String pids)
			throws TException {
		List<String> pidArr = new ArrayList<String>();
		String[] s = pids.split(",");
		for (String string : s) {
			pidArr.add(string);
		}
		List<CollectionRecord> list=collectionRecordMapper.getCollectionRecordsByProjectIds(pidArr);
		if(null==list){
			return new ArrayList<CollectionRecord>();
		}
		return list;
	}

	/**
	 * 
	  * @Description: 查询通知单序号
	  * @param year
	  * @return
	  * @throws ThriftServiceException
	  * @throws TException
	  * @author: xuweihao
	  * @date: 2015年5月14日 下午3:11:20
	 */
	public int getNoticeCurrentSeq(String year) throws ThriftServiceException,
			TException {
		Integer num = assignmentDistributionMapper.getNoticeCurrentSeq(year);
		ReminderNoticeBills reminderNoticeBills = new ReminderNoticeBills();
		if(num==null){
			reminderNoticeBills.setNoticeYear(year);
			assignmentDistributionMapper.insertNoticeCurrentSeq(reminderNoticeBills);
			return reminderNoticeBills.getNoticeCurrentSeq();
		}
		return num;
	}


	/**
	 * 
	  * @Description: 更新通知单序号
	  * @param year
	  * @return
	  * @throws ThriftServiceException
	  * @throws TException
	  * @author: xuweihao
	  * @date: 2015年5月14日 下午3:19:21
	 */
	public int updateNoticeCurrentSeq(String year)
			throws ThriftServiceException, TException {
		Integer num = assignmentDistributionMapper.updateNoticeCurrentSeq(year);
		return num==null?0:num;
	}



	/**
	 * 
	  * @Description: 通过项目Id,当前时间查询清单
	  * @param projectId
	  * @param nowDttm
	  * @return
	  * @throws ThriftServiceException
	  * @throws TException
	  * @author: xuweihao
	  * @date: 2015年5月15日 上午9:49:58
	 */
	@SuppressWarnings("unchecked")
	public List<ReminderNoticePart> getReminderNoticePart(int projectId,
			String nowDttm) throws ThriftServiceException, TException {
		List<ReminderNoticePart> list = new ArrayList<ReminderNoticePart>();
		list = assignmentDistributionMapper.getReminderNoticePart(projectId, nowDttm);
		return list==null?new ArrayList<ReminderNoticePart>():list;
	}


	/**
	 * 
	  * @Description: 通过项目Id查询项目信息
	  * @param projectId
	  * @return
	  * @throws ThriftServiceException
	  * @throws TException
	  * @author: xuweihao
	  * @date: 2015年5月15日 下午2:19:29
	 */
	public ReminderNotice getReminderNotice(int projectId)
			throws ThriftServiceException, TException {
		ReminderNotice reminderNotice = new ReminderNotice();
		reminderNotice = assignmentDistributionMapper.getReminderNotice(projectId);
		return reminderNotice==null?new ReminderNotice():reminderNotice;
	}




	/**
	 * 
	  * @Description: 还款催收清单存储过程
	  * @param reminderNoticePart
	  * @return
	  * @throws ThriftServiceException
	  * @throws TException
	  * @author: xuweihao
	  * @date: 2015年6月1日 下午4:22:33
	 */
	@SuppressWarnings("unchecked")
	public ReminderNoticePart getReminderNoticePartMoney(
			ReminderNoticePart reminderNoticePart,String nowDttm)
			throws ThriftServiceException, TException {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("aType", reminderNoticePart.getAType());
		map.put("pid", reminderNoticePart.getPid());
		map.put("nowDttm",nowDttm);
		map.put("result", OracleTypes.CURSOR);
		assignmentDistributionMapper.getReminderNoticePartMoney(map);
		List<ReminderNoticePart> noticePart = (List<ReminderNoticePart>)map.get("result");
		return noticePart.get(0);
	}




}
