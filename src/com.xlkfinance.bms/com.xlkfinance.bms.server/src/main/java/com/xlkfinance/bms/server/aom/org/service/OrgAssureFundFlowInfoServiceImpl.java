/**
 * @Title: OrgAssureFundFlowInfoServiceImpl.java
 * @Package com.xlkfinance.bms.server.aom.org.service
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: baogang
 * @date: 2016年7月13日 下午5:10:00
 * @version V1.0
 */
package com.xlkfinance.bms.server.aom.org.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.achievo.framework.annotation.ThriftService;
import com.qfang.xk.aom.rpc.org.OrgAssureFundFlowDTO;
import com.qfang.xk.aom.rpc.org.OrgAssureFundFlowInfo;
import com.qfang.xk.aom.rpc.org.OrgAssureFundFlowInfoService.Iface;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyInf;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.server.aom.org.mapper.OrgAssureFundFlowInfoMapper;
import com.xlkfinance.bms.server.aom.org.mapper.OrgCooperatCompanyApplyInfMapper;
/**
 * 保证金变更Service
  * @ClassName: OrgAssureFundFlowInfoServiceImpl
  * @author: baogang
  * @date: 2016年7月25日 上午11:35:32
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("orgAssureFundFlowInfoServiceImpl")
@ThriftService(service = "com.qfang.xk.aom.rpc.org.OrgAssureFundFlowInfoService")
public class OrgAssureFundFlowInfoServiceImpl implements Iface{
	private Logger logger = LoggerFactory.getLogger(OrgAssureFundFlowInfoServiceImpl.class);
	
	@Resource(name="orgAssureFundFlowInfoMapper")
	private OrgAssureFundFlowInfoMapper orgAssureFundFlowInfoMapper;
	
	@Resource(name="orgCooperatCompanyApplyInfMapper")
	private OrgCooperatCompanyApplyInfMapper orgCooperatMapper;
	
	@Override
	public List<OrgAssureFundFlowInfo> getAll(
			OrgAssureFundFlowInfo orgAssureFundFlow)
			throws ThriftServiceException, TException {
		return orgAssureFundFlowInfoMapper.getAll(orgAssureFundFlow);
	}

	@Override
	public OrgAssureFundFlowInfo getById(int pid)
			throws ThriftServiceException, TException {
		return orgAssureFundFlowInfoMapper.getById(pid);
	}

	@Override
	public int insert(OrgAssureFundFlowInfo orgAssureFundFlow)
			throws ThriftServiceException, TException {
		return orgAssureFundFlowInfoMapper.insert(orgAssureFundFlow);
	}

	@Override
	public int update(OrgAssureFundFlowInfo orgAssureFundFlow)
			throws ThriftServiceException, TException {
		return orgAssureFundFlowInfoMapper.update(orgAssureFundFlow);
	}

	/**
	 * 分页查询保证金流水
	 */
	@Override
	public List<OrgAssureFundFlowDTO> getOrgAssureFundByPage(
			OrgAssureFundFlowDTO assureFundFlow) throws ThriftServiceException,
			TException {
		List<OrgAssureFundFlowDTO> result = new ArrayList<OrgAssureFundFlowDTO>();
		try {
			result = orgAssureFundFlowInfoMapper.getOrgAssureFundByPage(assureFundFlow);
		} catch (Exception e) {
			logger.error("分页查询保证金流水:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return result;
	}

	/**
	 * 查询保证金流水总数
	 */
	@Override
	public int getOrgAssureFundCount(OrgAssureFundFlowDTO assureFundFlow)
			throws ThriftServiceException, TException {
		return orgAssureFundFlowInfoMapper.getOrgAssureFundCount(assureFundFlow);
	}

	/**
	 * 修改保证金变更流水
	 */
	@Transactional
	@Override
	public int updateOrgFundFlow(OrgAssureFundFlowInfo orgAssureFundFlow)
			throws ThriftServiceException, TException {
		int pid = orgAssureFundFlow.getPid();
		try {
			if(pid >0){
				orgAssureFundFlowInfoMapper.update(orgAssureFundFlow);
			}else{
				orgAssureFundFlowInfoMapper.insert(orgAssureFundFlow);
			}
			pid = orgAssureFundFlow.getPid();
			
			int applyId = orgAssureFundFlow.getApplyId();//合作申请信息ID
			//申请信息
			OrgCooperatCompanyApplyInf orgCooperationApplyInfo = orgCooperatMapper.getById(applyId);
			
			int status = orgAssureFundFlow.getStatus();
			//保证金不同意变更修改保证金额调整状态为正常
			if(status == AomConstant.FUND_FLOW_STATUS_20){
				orgCooperationApplyInfo.setStatus(AomConstant.FUND_FLOW_APPLY_STATUS_1);
			//保证金审核中修改保证金额调整状态为待审核
			}else if(status ==AomConstant.FUND_FLOW_STATUS_1){
				orgCooperationApplyInfo.setStatus(AomConstant.FUND_FLOW_APPLY_STATUS_2);
			}else if(status == AomConstant.FUND_FLOW_STATUS_10){
				orgCooperationApplyInfo.setStatus(AomConstant.FUND_FLOW_APPLY_STATUS_3);
				orgCooperationApplyInfo.setAssureMoney(orgAssureFundFlow.curAssureMoney);
				orgCooperationApplyInfo.setCreditLimit(orgAssureFundFlow.getCurCreditLimit());//设置授信额度
				//设置可用授信额度 = 当前可用额度+（修改后的额度提升数值）
				double availableLimit = orgCooperationApplyInfo.getAvailableLimit() +(orgAssureFundFlow.getCurCreditLimit() - orgAssureFundFlow.getOldCreditLimit());
				if(availableLimit <0){
					// 抛出异常处理
					throw new ThriftServiceException(ExceptionCode.SYSUSER_UPDATE, "可用额度不能小于0,请重新操作!");
				}
				orgCooperationApplyInfo.setAvailableLimit(availableLimit);
			}
			int result = orgCooperatMapper.update(orgCooperationApplyInfo);
			//修改未成功，回滚
			if(result == 0){
				// 抛出异常处理
				throw new ThriftServiceException(ExceptionCode.SYSUSER_UPDATE, "保证金修改出错,请重新操作!");
			}
		} catch (Exception e) {
			logger.error("修改保证金变更流水:" + ExceptionUtil.getExceptionMessage(e));
			throw new RuntimeException("修改保证金变更流水,请重新操作!"+ExceptionUtil.getExceptionMessage(e));
		}
		return pid;
	}

	/**
	 * 获取申请中的保证金变更信息
	 *@author:liangyanjun
	 *@time:2016年7月18日下午4:00:59
	 */
   @Override
   public OrgAssureFundFlowInfo getApplyOrgFundFlow(int orgId) throws ThriftServiceException, TException {
      OrgAssureFundFlowInfo applyOrgFundFlow = orgAssureFundFlowInfoMapper.getApplyOrgFundFlow(orgId);
      if (applyOrgFundFlow==null) {
         return new OrgAssureFundFlowInfo();
      }
      return applyOrgFundFlow;
   }

}
