/**
 * @Title: ElementLendServiceImpl.java
 * @Package com.xlkfinance.bms.server.beforeloan.service
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: Administrator
 * @date: 2016年2月26日 下午8:25:10
 * @version V1.0
 */
package com.xlkfinance.bms.server.beforeloan.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.DataInfo;
import com.xlkfinance.bms.rpc.beforeloan.ElementLend;
import com.xlkfinance.bms.rpc.beforeloan.ElementLendDetails;
import com.xlkfinance.bms.rpc.beforeloan.ElementLendService.Iface;
import com.xlkfinance.bms.rpc.beforeloan.ElementMobileDto;
import com.xlkfinance.bms.rpc.beforeloan.GridViewMobileDto;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.server.beforeloan.mapper.DataUploadMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ElementLendMapper;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDispose;

/**
 * 要件借出申请Service
 * 
 * @ClassName: ElementLendServiceImpl
 * @author: Administrator
 * @date: 2016年2月26日 下午8:34:31
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("elementLendServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.beforeloan.ElementLendService")
public class ElementLendServiceImpl extends WorkflowSpecialDispose implements Iface {

	@Resource(name = "elementLendMapper")
	private ElementLendMapper elementLendMapper;

	@Resource(name = "dataUploadMapper")
	private DataUploadMapper dataUploadMapper;

	@Override
	public List<GridViewDTO> getAllElementLend(ElementLend elementLend) throws ThriftServiceException, TException {
		List<GridViewDTO> listResult = elementLendMapper.getAllElementLend(elementLend);
		if (listResult.size() == 0) {
			return new ArrayList<GridViewDTO>();
		}
		return listResult;
	}

	@Override
	public int getAllElementLendCount(ElementLend elementLend) throws TException {
		return elementLendMapper.getTotalElementLends(elementLend);
	}

	@Override
	public ElementLend getElementLendById(int pid) throws TException {
		ElementLend elementLend = null;
		try {
			elementLend = elementLendMapper.getElementLendByPid(pid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return elementLend == null ? new ElementLend() : elementLend;
	}

	@Override
	public int addElementLend(ElementLend elementLend) throws ThriftServiceException, TException {
		int pid = 0;
		try {
			pid = elementLendMapper.getSeqBizElementLend();
			elementLend.setPid(pid);
			elementLendMapper.addElementLend(elementLend);
			String lendFileIds = elementLend.getLendFilesId();
			String lendTime = elementLend.getLendTime();
			//添加要件借出详情表
			if(!StringUtil.isBlank(lendFileIds)){
				String[] lendFiles = lendFileIds.split(",");
				for(String fileId : lendFiles){
					ElementLendDetails details = new ElementLendDetails();
					details.setLendId(pid);
					details.setLendTime(lendTime);
					details.setElementFileId(Integer.parseInt(fileId));
					details.setStatus(1);
					elementLendMapper.addElementLendDetails(details);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CONTRACT_PRODUCT_ADD, "新增要件借出信息失败！");
		}
		pid = elementLend.getPid();
		return pid;
	}

	@Override
	public int updateElementLend(ElementLend elementLend) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			rows = elementLendMapper.updateByPrimaryKey(elementLend);
			String lendFileIds = elementLend.getLendFilesId();
			String lendTime = elementLend.getLendTime();
			int lendState = elementLend.getLendState();
			
			ElementLendDetails condition = new ElementLendDetails();
			condition.setLendId(elementLend.getPid());
			if(lendState == 1 || lendState == 0){//申请中修改
				//先删除要件借出详情表
				elementLendMapper.deleteElementLendDetails(condition);
				//添加要件借出详情表
				if(!StringUtil.isBlank(lendFileIds)){
					String[] lendFiles = lendFileIds.split(",");
					for(String fileId : lendFiles){
						ElementLendDetails details = new ElementLendDetails();
						details.setLendId(elementLend.getPid());
						details.setLendTime(lendTime);
						details.setElementFileId(Integer.parseInt(fileId));
						details.setStatus(1);
						elementLendMapper.addElementLendDetails(details);
					}
				}
			}else{
				//修改归还时间
				String returnFiles = elementLend.getReturnFilesId();
				condition.setReturnTime(elementLend.getActualReturnTime());
				String[] returnFileIds = returnFiles.split(",");
				condition.setReturnFileIds(Arrays.asList(returnFileIds));
				condition.setStatus(2);//已归还
				elementLendMapper.updateElementLendDetails(condition);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CONTRACT_PRODUCT_ADD, "修改要件借出信息失败！");
		}
		return rows;
	}
	
	/**
	 * 修改要件借出申请和详情信息
	 */
	@Override
	public int batchUpdateElementLendDetails(ElementLend elementLend, List<ElementLendDetails> elementLendDetailsList) throws ThriftServiceException, TException {
		int rows = 0;
		try {
		  rows = elementLendMapper.updateByPrimaryKey(elementLend);
		 int i =  elementLendMapper.batchUpdateElementLendDetails(elementLendDetailsList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CONTRACT_PRODUCT_ADD, "修改要件借出申请和详情信息！");
		}
		
		return rows;
	}
	
	/**
	 * 修改要件借出申请和新增加详情信息
	 */
	@Override
	public int updateElementLendDetails(ElementLend elementLend, List<ElementLendDetails> elementLendDetailsList) throws ThriftServiceException, TException {
		int rows = 0;
		try {
		  rows = elementLendMapper.updateByPrimaryKey(elementLend);
		  elementLendMapper.batchUpdateElementLendDetails(elementLendDetailsList);
		  ElementLendDetails condition = new ElementLendDetails();
			condition.setLendId(elementLend.getPid());
			int lendState = elementLend.getLendState();
			if(lendState == 1 || lendState == 0){//申请中修改
				//先删除要件借出详情表
				elementLendMapper.deleteElementLendDetails(condition);
			  for(ElementLendDetails elementLendDetails:elementLendDetailsList){
				  elementLendMapper.addElementLendDetails(elementLendDetails);
			  }
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CONTRACT_PRODUCT_ADD, "修改要件借出申请和详情信息！");
		}
		
		return rows;
	}

	@Override
	public List<DataInfo> findProjectFiles(int projectId) throws TException {
		return dataUploadMapper.findProjectFiles(projectId);
	}

	@Override
	public int updateLendStateByPid(int pid, int lendState, String updateTime) throws ThriftServiceException, TException {

		return elementLendMapper.updateLendStateByPid(pid, lendState, updateTime);
	}

	@Override
	public List<ElementLendDetails> queryElementLendDetails(ElementLendDetails details) throws TException {
		List<ElementLendDetails> detailList = elementLendMapper.queryElementLendDetail(details);
		if(detailList == null || detailList.size()==0){
			detailList = new ArrayList<ElementLendDetails>();
		}
		return detailList;
	}

	@Override
	public List<GridViewMobileDto> queryElementList(ElementMobileDto elementMobileDto)
			throws ThriftServiceException, TException {
		List<GridViewMobileDto> listResult = elementLendMapper.queryElementList(elementMobileDto);
		if (listResult.size() == 0) {
			return new ArrayList<GridViewMobileDto>();
		}
		return listResult;
	}

	@Override
	public int getTotalElement(ElementMobileDto elementMobileDto) throws TException {
		return elementLendMapper.getTotalElement(elementMobileDto);
	}

	@Override
	public List<DataInfo> findProjectFilesByfileIds(int projectId,
			List<Integer> fileIds) throws TException {
		return dataUploadMapper.findProjectFilesByfileIds(projectId,fileIds);
	}

}
