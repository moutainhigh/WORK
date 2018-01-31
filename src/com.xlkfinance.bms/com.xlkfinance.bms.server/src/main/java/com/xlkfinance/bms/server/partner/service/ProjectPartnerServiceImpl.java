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
package com.xlkfinance.bms.server.partner.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.customer.CusPerson;
import com.xlkfinance.bms.rpc.inloan.HandleDynamicDTO;
import com.xlkfinance.bms.rpc.partner.PartnerApprovalRecord;
import com.xlkfinance.bms.rpc.partner.ProjectPartner;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerDto;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerFile;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerRefund;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerService.Iface;
import com.xlkfinance.bms.rpc.workflow.TaskVo;
import com.xlkfinance.bms.server.partner.mapper.PartnerApprovalRecordMapper;
import com.xlkfinance.bms.server.partner.mapper.ProjectPartnerMapper;
import com.xlkfinance.bms.server.partner.mapper.ProjectPartnerRefundMapper;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("projectPartnerServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.partner.ProjectPartnerService")
public class ProjectPartnerServiceImpl implements Iface {
	
	private Logger logger = LoggerFactory.getLogger(ProjectPartnerServiceImpl.class);

	@Resource(name = "projectPartnerMapper")
	private ProjectPartnerMapper partnerMapper;

	@Resource(name = "partnerApprovalRecordMapper")
	private PartnerApprovalRecordMapper recordMapper;
	
	@Resource(name = "projectPartnerRefundMapper")
	private ProjectPartnerRefundMapper projectPartnerRefundMapper ;
	


	/**
	 * 查询所有已提交的审核项目
	 */
	@Override
	public List<ProjectPartner> findAllProjectPartner(
			ProjectPartner projectPartner) throws ThriftServiceException,
			TException {
		List<ProjectPartner> resultList = partnerMapper
				.findAllProjectPartner(projectPartner);
		if (resultList == null) {
			resultList = new ArrayList<ProjectPartner>();
		}
		return resultList;
	}

	/**
	 * 查询所有已提交的审核项目总数
	 */
	@Override
	public int findAllProjectPartnerCount(ProjectPartner projectPartner)
			throws ThriftServiceException, TException {
		int count = partnerMapper.findAllProjectPartnerCount(projectPartner);
		return count;
	}

	@Override
	public int addProjectPartner(ProjectPartner projectPartner)throws ThriftServiceException, TException {
		int pid = 0;
		try {
			pid = partnerMapper.getSeqBizProjectPartner();
			projectPartner.setPid(pid);
			partnerMapper.addProjectPartner(projectPartner);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_ADD, "新增合作机构项目失败！");
		}
		return pid;
	}

	@Override
	public int updateProjectPartner(ProjectPartner projectPartner)
			throws ThriftServiceException, TException {
		int rows = 0;
		try {
			Timestamp time = new Timestamp(new Date().getTime());
			projectPartner.setUpdateTime(time.toString());
			rows = partnerMapper.updateProjectPartner(projectPartner);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_ADD, "修改合作机构项目失败！");
		}
		return rows;
	}

	@Override
	public ProjectPartnerDto findProjectPartner(int projectId)
			throws ThriftServiceException, TException {
		ProjectPartnerDto projectPartnerDto = partnerMapper.findProjectPartner(projectId);
		if(projectPartnerDto == null){
			projectPartnerDto = new ProjectPartnerDto();
		}
		return projectPartnerDto;
	}

	/**
	 * 
	  * @param projectPartner
	  * @return
	  * @throws ThriftServiceException
	  * @throws TException
	  * @author: Administrator
	  * @date: 2016年3月18日 下午3:17:30
	 */
	@Override
	public List<ProjectPartner> findAllProjectInfo(ProjectPartner projectPartner)throws ThriftServiceException, TException {
		List<ProjectPartner> resultList = partnerMapper.findAllProjectInfo(projectPartner);
		return resultList;
	}

	@Override
	public int findAllProjectInfoCount(ProjectPartner projectPartner)throws ThriftServiceException, TException {
		return partnerMapper.findAllProjectInfoCount(projectPartner);
	}

	@Override
	public ProjectPartner findProjectPartnerInfo(ProjectPartner projectPartner)
			throws TException {
		ProjectPartner result = partnerMapper.findProjectPartnerInfo(projectPartner);
		if(result == null){
			result = new ProjectPartner();
		}
		return result;
	}

	@Override
	public List<PartnerApprovalRecord> findAllPartnerApprovalRecord(int partnerId) throws ThriftServiceException,TException {
		List<PartnerApprovalRecord> resultList = recordMapper.findAllPartnerApprovalRecord(partnerId);
		if(resultList == null){
			resultList = new ArrayList<PartnerApprovalRecord>();
		}
		return resultList;
	}

	@Override
	public int addPartnerApprovalRecord(PartnerApprovalRecord record)throws ThriftServiceException, TException {
		int rows = 0;
		try {
			rows = recordMapper.addPartnerApprovalRecord(record);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_ADD, "增加审批记录失败！");
		}
		return rows;
	}

	@Override
	public int updatePartnerForClosed(String loanId)
			throws ThriftServiceException, TException {
		if(StringUtil.isBlank(loanId)){
			return 0;
		}
		return partnerMapper.updatePartnerForClosed(loanId);
	}

	@Override
	public int updateLoanStatus(ProjectPartner partner) throws ThriftServiceException,
			TException {
		return partnerMapper.updateLoanStatus(partner);
	}

	@Override
	public int updateRepaymentInfo(ProjectPartner partner)throws ThriftServiceException, TException {
		return partnerMapper.updateRepaymentInfo(partner);
	}

	@Override
	public int updateXiFeeInfo(ProjectPartner partner) throws ThriftServiceException,
			TException {
		return partnerMapper.updateXiFeeInfo(partner);
	}
	
	/**
	 * 批量更新息费
	 */
	public int updateXiFeeInfos(List<ProjectPartner> projectPartnerList) throws com.xlkfinance.bms.rpc.common.ThriftServiceException, org.apache.thrift.TException{
		return partnerMapper.updateXiFeeInfos(projectPartnerList);
	}
	
	
	/**
	 * 更新机构审核记录
	 */
	public int updatePartnerApprovalRecord(PartnerApprovalRecord record) throws com.xlkfinance.bms.rpc.common.ThriftServiceException, org.apache.thrift.TException{
		try{
			return recordMapper.updatePartnerApprovalRecord(record);
		}catch(Exception e){
			logger.error("updatePartnerApprovalRecord error",e);
			throw e;
		}
	}

	/**
	 * 查询审核记录列表
	 */
	public List<ProjectPartner> findProjectPartnerInfoList(ProjectPartner projectPartner) throws ThriftServiceException,
			TException {
		return partnerMapper.findProjectPartnerInfoList(projectPartner);
	}
	
	
	
	//查询机构合作表，并懒加载项目相关数据
	public ProjectPartnerDto findProjectPartnerAndLazy(ProjectPartner projectPartner) throws ThriftServiceException,TException{
		ProjectPartnerDto result = partnerMapper.findProjectPartnerAndLazy(projectPartner);
		if(result == null){
			result = new ProjectPartnerDto();
		}
		return result;
	}
	//查询共同借款人
	public List<CusPerson> findPublicManByProjectId(int projectId) throws ThriftServiceException,TException{
		List<CusPerson> publicManList = partnerMapper.findPublicManByProjectId(projectId);
		if (publicManList == null ||  publicManList.size() == 0  || null == publicManList.get(0) ) {
			publicManList = new ArrayList<CusPerson>();
		}
		return publicManList;
	}
	//查询审批工作流记录	
	public List<TaskVo> findForeLoanWorkFlowByProjectId(int projectId) throws ThriftServiceException,TException{
		List<TaskVo> workFlowList = partnerMapper.findForeLoanWorkFlowByProjectId(projectId);
		if (workFlowList == null || workFlowList.size() == 0 || null == workFlowList.get(0)  ) {
			workFlowList = new ArrayList<TaskVo>();
		}
		return workFlowList;
	}
 
	//查询项目贷中办理动态状态记录
	public List<HandleDynamicDTO> findInLoanStatusByProjectId(int projectId) throws ThriftServiceException,TException{
		List<HandleDynamicDTO> list = partnerMapper.findInLoanStatusByProjectId(projectId);
		if (list == null || list.size() == 0 || null == list.get(0)) {
			list = new ArrayList<HandleDynamicDTO>();
		}
		return list;
	}
	
	
	//查询机构己申请总额
	public double findLoanAmountCountByPartnerNo(ProjectPartner projectPartner) throws ThriftServiceException,TException{
		double loanAmountCount = partnerMapper.findLoanAmountCountByPartnerNo(projectPartner);
		return loanAmountCount;
	}
	
	
	
	/**
	 * 添加机构还款
	 */
	@Override
	public int addPartnerRefund(ProjectPartnerRefund projectPartnerRefund)throws ThriftServiceException, TException {
		int pid = 0;
		try {
			pid = projectPartnerRefundMapper.getSeqBizProjectPartnerRefund();
			projectPartnerRefund.setPid(pid);
			Timestamp time = new Timestamp(new Date().getTime());
			projectPartnerRefund.setCreateTime(time.toString());
			projectPartnerRefund.setUpdateTime(time.toString());
			projectPartnerRefundMapper.addProjectPartnerRefund(projectPartnerRefund);
		} catch (Exception e) {
			logger.error(">>>>>>addPartnerRefund error",e);
			throw new ThriftServiceException(ExceptionCode.SYS_EDIT_PWD, "添加机构还款失败！");
		}
		return pid;
	}
	/**
	 * 修改机构还款
	 */
	@Override
	public int updatePartnerRefund(ProjectPartnerRefund projectPartnerRefund)throws ThriftServiceException, TException {
		int rows = 0;
		try {
			Timestamp time = new Timestamp(new Date().getTime());
			projectPartnerRefund.setUpdateTime(time.toString());
			rows = projectPartnerRefundMapper.updateProjectPartnerRefund(projectPartnerRefund);
		} catch (Exception e) {
			logger.error(">>>>>>updatePartnerRefund error",e);
			throw new ThriftServiceException(ExceptionCode.SYS_EDIT_PWD, "修改机构还款失败！");
		}
		return rows;
	}
	/**
	 * 添加机构还款-批量
	 */
	@Override
	public int addBatchPartnerRefund(List<ProjectPartnerRefund> partnerRefundList)throws ThriftServiceException, TException {
		int rows = 0;
		try {
			ProjectPartnerRefund updatetObj = null;
			Timestamp time = new Timestamp(new Date().getTime());
			for (int i = 0; i < partnerRefundList.size(); i++) {
				updatetObj = partnerRefundList.get(i);
				updatetObj.setPid(projectPartnerRefundMapper.getSeqBizProjectPartnerRefund());
				updatetObj.setCreateTime(time.toString());
				updatetObj.setUpdateTime(time.toString());
				projectPartnerRefundMapper.addProjectPartnerRefund(updatetObj);
				rows++;
			}
		} catch (Exception e) {
			logger.error(">>>>>>addBatchPartnerRefund error",e);
			throw new ThriftServiceException(ExceptionCode.SYS_EDIT_PWD, "添加机构还款-批量失败！");
		}
		return rows;
	}
	/**
	 * 修改机构还款-批量
	 */
	@Override
	public int updateBatchPartnerRefund(List<ProjectPartnerRefund> partnerRefundList)throws ThriftServiceException, TException {
		int rows = 0;
		try {
			Timestamp time = new Timestamp(new Date().getTime());
			ProjectPartnerRefund updatetObj = null;
			for (int i = 0; i < partnerRefundList.size(); i++) {
				updatetObj = partnerRefundList.get(i);
				updatetObj.setUpdateTime(time.toString());
				projectPartnerRefundMapper.updateProjectPartnerRefund(updatetObj);
				rows++;
			}
		} catch (Exception e) {
			logger.error(">>>>>>updatePartnerRefund error",e);
			throw new ThriftServiceException(ExceptionCode.SYS_EDIT_PWD, "修改机构还款-批量失败！");
		}
		return rows;
	}

	@Override
	public List<ProjectPartnerRefund> findAllProjectPartnerRefund(ProjectPartnerRefund projectPartnerRefund)throws ThriftServiceException, TException {
		List<ProjectPartnerRefund> resultList = projectPartnerRefundMapper.findAllProjectPartnerRefund(projectPartnerRefund);
		if(resultList == null){
			resultList = new ArrayList<ProjectPartnerRefund>();
		}
		return resultList;
	}

	@Override
	public int findAllProjectPartnerRefundCount(ProjectPartnerRefund projectPartnerRefund)throws ThriftServiceException, TException {
		return projectPartnerRefundMapper.findAllProjectPartnerRefundCount(projectPartnerRefund);
	}
	
	@Override
	public ProjectPartnerRefund findProjectPartnerRefund(ProjectPartnerRefund projectPartnerRefund)throws ThriftServiceException, TException {
		ProjectPartnerRefund result = projectPartnerRefundMapper.findProjectPartnerRefund(projectPartnerRefund);
		if(result == null){
			result = new ProjectPartnerRefund();
		}
		return result;
	}

	/**
	 * 更新还款金额
	 */
	@Override
	public int updatePartnerRefundLoanMoney(ProjectPartner projectPartner)throws ThriftServiceException, TException {
		
		int rows = 0;
		try {
			Timestamp time = new Timestamp(new Date().getTime());
			projectPartner.setUpdateTime(time.toString());
			rows = partnerMapper.updatePartnerRefundLoanMoney(projectPartner);
		} catch (Exception e) {
			logger.error(">>>>>>updatePartnerRefundLoanMoney error",e);
			throw new ThriftServiceException(ExceptionCode.SYS_EDIT_PWD, "更新还款金额失败！");
		}
		return rows;
	}

	/**
	 * 更新分期还款金额（可改为0）
	 */
	public int updatePartnerRefundMoney(ProjectPartnerRefund projectPartnerRefund)throws ThriftServiceException, TException {
		int rows = 0;
		try {
			Timestamp time = new Timestamp(new Date().getTime());
			projectPartnerRefund.setUpdateTime(time.toString());
			
			rows = projectPartnerRefundMapper.updatePartnerRefundMoney(projectPartnerRefund);
		} catch (Exception e) {
			logger.error(">>>>>>updatePartnerRefundMoney error",e);
			throw new ThriftServiceException(ExceptionCode.SYS_EDIT_PWD, "更新分期还款金额失败！");
		}
		return rows;
	}

	@Override
	public int updateBatchProjectParner(List<ProjectPartner> projectPartnerList)throws ThriftServiceException, TException {
		return partnerMapper.updateBatchProjectParner(projectPartnerList);
	}
	
	
}
