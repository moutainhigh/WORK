/**
 * @Title: AfterLoanSupervisionServiceImpl.java
 * @Package com.xlkfinance.bms.server.afterloan.service
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: loe.luozhonghua
 * @date: 2015年3月23日 下午7:13:06
 * @version V1.0
 */
package com.xlkfinance.bms.server.afterloan.service;

import java.util.ArrayList;
import java.util.Date;
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
import com.achievo.framework.util.DateUtil;
import com.google.common.collect.Lists;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.rpc.afterloan.AfterLoanSupervisionService.Iface;
import com.xlkfinance.bms.rpc.afterloan.BizProjectRegFile;
import com.xlkfinance.bms.rpc.afterloan.BizProjectRegHistory;
import com.xlkfinance.bms.rpc.afterloan.BizProjectRegPlan;
import com.xlkfinance.bms.rpc.afterloan.ProjectInfo;
import com.xlkfinance.bms.rpc.afterloan.ResultBizProjectRegPlan;
import com.xlkfinance.bms.rpc.afterloan.SupervisionResultList;
import com.xlkfinance.bms.rpc.afterloan.SupervisionResultSearch;
import com.xlkfinance.bms.rpc.afterloan.SupervisionSearchBean;
import com.xlkfinance.bms.rpc.afterloan.ToDayUpdateDTO;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.file.FileInfo;
import com.xlkfinance.bms.rpc.finance.FinanceBusinessView;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.rpc.system.BizFileService;
import com.xlkfinance.bms.server.afterloan.mapper.AfterLoanSupervisionMapper;
import com.xlkfinance.bms.server.finance.mapper.FinanceBankMapper;
import com.xlkfinance.bms.server.system.mapper.SysUserMapper;
 
 

@Service("afterLoanSupervisionServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.afterloan.AfterLoanSupervisionService")
public class AfterLoanSupervisionServiceImpl implements Iface{

	
	private Logger logger = LoggerFactory.getLogger(AfterLoanSupervisionServiceImpl.class);
 
	@Resource(name = "afterLoanSupervisionMapper")
	private AfterLoanSupervisionMapper<?, ?> afterLoanSupervisionMapper;
	
	@Resource(name = "sysUserMapper")
	private SysUserMapper<?, ?> sysUserMapper;
	
	@Resource(name = "financeBankMapper")
	private FinanceBankMapper financeBankMapper;
	
	@Resource(name = "bizFileServiceImpl")
	private BizFileService.Iface bizFileService; 
	/**
	 * 添加 监管计划
	 */
	@Override
	public int createBizProRegPlan(BizProjectRegPlan bizProjectRegPlan)
			throws ThriftServiceException, TException {
		 	afterLoanSupervisionMapper.createBizProRegPlan(bizProjectRegPlan);
		 	return bizProjectRegPlan.getPid();
	}
    /**
     * 监管任务查询
     */
	@Override
	public List<ResultBizProjectRegPlan> supervisionTaskList(
			SupervisionSearchBean supervisionSearchBean)
			throws ThriftServiceException, TException {
		return  afterLoanSupervisionMapper.supervisionTaskList(supervisionSearchBean);
	}
    /**
     * 监管记录资料---文件上传
     */
	@Override
	public int createBizProjectRegFile(BizProjectRegFile bizProjectRegFile)
			throws ThriftServiceException, TException {
		return 0;
	}
    /**
     * 查询监管任务列表
     */
	@Override
	public ResultBizProjectRegPlan getSupervisionTask(int pid)
			throws ThriftServiceException, TException {
		return afterLoanSupervisionMapper.getSupervisionTask(pid);
	}
    /**
     *  打开监管页面，修改监管状态为监管
     */
	@Override
	public void updateBizProjectRegPlanStart(BizProjectRegPlan bizProjectRegPlan) throws TException {
		afterLoanSupervisionMapper.updateBizProjectRegPlanStart(bizProjectRegPlan);
	}
	/**
	 * 执行  监管  --添加 监管记录表
	 */
	@Override
	public int createBizProjectRegHistory(
			BizProjectRegHistory bizProjectRegHistory)
			throws ThriftServiceException, TException {
		return afterLoanSupervisionMapper.createBizProjectRegHistory(bizProjectRegHistory);
	}
    /**
     * 监管结果记录查询  pid--计划id
     */
	@Override
	public List<BizProjectRegHistory> getBizProjectRegHistoryList(int pid)
			throws TException {
		return afterLoanSupervisionMapper.getBizProjectRegHistoryList(pid);
	}
	 /**
     * 获取项目
     */	
	@Override
	public ResultBizProjectRegPlan getProject(int pid) throws TException {
		return afterLoanSupervisionMapper.getProject(pid);
	}
	 /**
     * 获取监管任务列表
     */
	@Override
	public List<ResultBizProjectRegPlan> getSupervisionTaskList(int pid)
			throws ThriftServiceException, TException {
		return afterLoanSupervisionMapper.getSupervisionTaskList(pid);
	}
	 /**
     * 更新监管计划
     */
	@Override
	public int updateBizProjectRegPlan(BizProjectRegPlan bizProjectRegPlan)
			throws ThriftServiceException, TException {
		return afterLoanSupervisionMapper.updateBizProjectRegPlan(bizProjectRegPlan);
	}
	/**
     * 更新监管计划监管状态
     */
	@Override
	public int updateBizProjectRegPlanRegulatoryStatus(BizProjectRegPlan bizProjectRegPlan)
			throws ThriftServiceException, TException {
		afterLoanSupervisionMapper.updateBizProjectRegPlanRegulatoryStatus(bizProjectRegPlan);
		if(bizProjectRegPlan.getRegulatoryStatus() == 3){
			return afterLoanSupervisionMapper.updateBizTaskStatus(bizProjectRegPlan.getPid());
		}
		return 0;
	}
	/**
     * 监管结果列表查询
     */
	@Override
	public List<SupervisionResultList> getSupervisionResultList(
			SupervisionResultSearch supervisionResultSearch)
			throws ThriftServiceException, TException {
		return afterLoanSupervisionMapper.getSupervisionResultList(supervisionResultSearch);
	}
	/**
     * 查询贷前贷款申请、额度提取申请且已经归档的项目
     */
	@Override
	public List<GridViewDTO> supervisionList(
			SupervisionSearchBean supervisionSearchBean)
			throws ThriftServiceException, TException {
		List<GridViewDTO> list = new ArrayList<GridViewDTO>();
		try {
			if(null!=supervisionSearchBean.getEndDttm() && !"".equals(supervisionSearchBean.getEndDttm())){
				supervisionSearchBean.setEndDttm(Integer.parseInt((supervisionSearchBean.getEndDttm())+1)+"");
			}
			list =afterLoanSupervisionMapper.supervisionList(supervisionSearchBean);
			
			if(null != list)
			{
				for(GridViewDTO gridViewDTO:list)
				{
					// 金额的计算
				 	FinanceBusinessView fView = null;
					Map<String, Object> param = new HashMap<String, Object>();
			        param.put("results",OracleTypes.CURSOR);
			        param.put("projectId",gridViewDTO.getPid());		
			        param.put("currentDate", DateUtil.fmtDateToStr(new Date(), "yyyy-MM-dd") );
				        
					financeBankMapper.getProGetPlanProject(param);
					List<FinanceBusinessView> resultList = (List<FinanceBusinessView>) param.get("results");
					fView= resultList.get(0);
					gridViewDTO.setValue9(String.valueOf(fView.getDueUnreceived())); // 到期未收金额
				}	
			}	
			
		} catch (Exception e) {
			logger.error("监管基础信息失败",e);
			throw new ThriftServiceException(ExceptionCode.AFTER_LOAD_QUERY, "监管基础信息失败！");
		}
		return list;
	}
	/**
	 * 總记录数
	 */
	@Override
	public int getTotalSupervisionList(
			SupervisionSearchBean supervisionSearchBean)
			throws ThriftServiceException, TException {
		if(null!=supervisionSearchBean.getEndDttm() && !"".equals(supervisionSearchBean.getEndDttm())){
			supervisionSearchBean.setEndDttm(Integer.parseInt((supervisionSearchBean.getEndDttm())+1)+"");
		}
		return afterLoanSupervisionMapper.getTotalSupervisionList(supervisionSearchBean);
	}
	/**
	 * 更新计划
	 */
	@Override
	public int updateBizProRegPlan(BizProjectRegPlan bizProjectRegPlan)
			throws ThriftServiceException, TException {
		return afterLoanSupervisionMapper.updateBizProjectRegPlan(bizProjectRegPlan);
	}
	/**
	 * 删除监管计划
	 */
	@Override
	public int deleteSupervisePlan(String pids) throws ThriftServiceException,
			TException {
		String [] arr=pids.split(",");
		List list=Lists.newArrayList();
		for(int i=0;i<arr.length;i++){
			list.add(arr[i]);
		}
		return afterLoanSupervisionMapper.deleteSupervisePlan(list);
	}
	/**
	 * 监管结果记录查询
	 */
	@Override
	public BizProjectRegHistory getBizProjectRegHistory(int pid)
			throws ThriftServiceException, TException {
		return afterLoanSupervisionMapper.getBizProjectRegHistory(pid);
	}
	/**
	 * 修改监管结果记录
	 */
	@Override
	public int updateBizProjectRegHistory(
			BizProjectRegHistory bizProjectRegHistory)
			throws ThriftServiceException, TException {
		return afterLoanSupervisionMapper.updateBizProjectRegHistory(bizProjectRegHistory);
	}
	/**
	 * 删除监管结果记录
	 */
	@Override
	public int deleteResultSuperviseHistory(int pid)
			throws ThriftServiceException, TException {
		return afterLoanSupervisionMapper.deleteResultSuperviseHistory(pid);
	}
	/**
	 * 查监管结果总记录
	 */
	@Override
	public int getTotalSupervisionTask(
			SupervisionSearchBean supervisionSearchBean)
			throws ThriftServiceException, TException {
		return afterLoanSupervisionMapper.getTotalSupervisionTask(supervisionSearchBean);
	}

	@Override
	public int getTotalSupervisionResult(
			SupervisionResultSearch supervisionResultSearch)
			throws ThriftServiceException, TException {
		return afterLoanSupervisionMapper.getTotalSupervisionResult(supervisionResultSearch);
	}
    /**
     * 获取项目信息
     */
	@Override
	public List<ProjectInfo> getProjectData(int pid) throws ThriftServiceException, TException {
		return afterLoanSupervisionMapper.getProjectData(pid);
	}
	/**
	 * 获取监管记录，根据项目ID或者计划ID来获取监管记录，主要用于页面的查看监管详情
	 */
	@Override
	public List<SupervisionResultList> getRegulatoryRecordList(SupervisionResultSearch supervisionResultSearch) throws TException {
		return afterLoanSupervisionMapper.getRegulatoryRecordList(supervisionResultSearch);
	}
	/**
     * 文件管理
     */	
	@Override
	public List<FileInfo> getFileDataList(FileInfo fileInfo) throws ThriftServiceException, TException {
		return afterLoanSupervisionMapper.getFileDataList(fileInfo);
	}
	/**
     * 查总记录数
     */	
	@Override
	public int getFileDataTotal(FileInfo fileInfo) throws TException {
		return afterLoanSupervisionMapper.getFileDataTotal(fileInfo);
	}
	/**
     * 删除文件
     */
	@Override
	public boolean deleteFileData(int dataId, int fileId) throws TException {
		 bizFileService.deleteBizFileByPid(fileId);
		 // 删除业务文件表中的数据
		 afterLoanSupervisionMapper.deleteBusinessFile(dataId);
		 return true;
	}
	/**
	 * 新增或者更改业务资料文件
	 */
	@Override
	public int saveOrUpdateFileData(FileInfo fileInfo, BizFile bizFile) throws TException {
		int result = 0;
    	// 修改
    	if(fileInfo.getDataId()==0)
    	{
    		bizFileService.saveBizFile(bizFile);
    		fileInfo.setFileId(bizFile.getPid());
    		return afterLoanSupervisionMapper.createBusinessFile(fileInfo);
    	}	
    	else
    	{
    		bizFileService.updateBizFile(bizFile);
    		//misapprProcessMapper.editFileAndViolation(fileInfo);
    	}	

           return result;     
	}
	/**
	 * 获取今日更新数据
	 */
	@Override
	public List<ToDayUpdateDTO> getToDayUpdateList(ToDayUpdateDTO toDayUpdateDTO)
			throws ThriftServiceException, TException {
		// 日期为空,默认为系统当前时间减一天
		if(null!=toDayUpdateDTO && null == toDayUpdateDTO.getToDate() ){
			toDayUpdateDTO.setToDate(DateUtil.format(DateUtil.addDay(DateUtil.getToDay(), -1), "yyyy-MM-dd"));
		}
		
		afterLoanSupervisionMapper.getToDayUpdateList(toDayUpdateDTO);
		List<ToDayUpdateDTO> list = toDayUpdateDTO.getResults();
		List<ToDayUpdateDTO> resultList = new ArrayList<ToDayUpdateDTO>();
		if(null!=list && list.size() > 0 ){
			double amtSum = 0 ;		// 金额合计
			int businessType = -1;	// 业务类型
			int rowNum = 0;			// 序号
			int count  = 0 ;		// 数据条数
			ToDayUpdateDTO tempDto = null;
			for(ToDayUpdateDTO dto : list){
				if(null!=dto){
					// 循环刚刚开始  
					if(businessType == -1){
						businessType = dto.getBusinessType();
					}
					// 设置小计
					if(businessType != dto.getBusinessType()){
						tempDto = new ToDayUpdateDTO();
						tempDto.setOutputAmt(amtSum);
						tempDto.setAcctType(3);
						resultList.add(tempDto);
						// 设置了小计后,金额 序号 业务类型标识 初始化
						amtSum = dto.getOutputAmt();
						rowNum = 0;
						businessType = dto.getBusinessType();
					}else{
						amtSum += dto.getOutputAmt();
					}
					// 设置序号
					rowNum++;
					dto.setRowNum(rowNum);
					resultList.add(dto);
					// 最后一条数据 ,计算合计
					if(count == (list.size()-1)){
						tempDto = new ToDayUpdateDTO();
						tempDto.setOutputAmt(amtSum);
						tempDto.setAcctType(3);
						resultList.add(tempDto);
					}
					count++;
				}
			}
		}
		return resultList;
	}

}
