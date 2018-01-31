/**
 * @Title: AfterLoanSupervisionMapper.java
 * @Package com.xlkfinance.bms.server.afterloan.mapper
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: loe.luozhonghua
 * @date: 2015年3月23日 下午8:26:04
 * @version V1.0
 */
package com.xlkfinance.bms.server.afterloan.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.afterloan.BizProjectRegHistory;
import com.xlkfinance.bms.rpc.afterloan.BizProjectRegPlan;
import com.xlkfinance.bms.rpc.afterloan.ProjectInfo;
import com.xlkfinance.bms.rpc.afterloan.ResultBizProjectRegPlan;
import com.xlkfinance.bms.rpc.afterloan.SupervisionResultList;
import com.xlkfinance.bms.rpc.afterloan.SupervisionResultSearch;
import com.xlkfinance.bms.rpc.afterloan.SupervisionSearchBean;
import com.xlkfinance.bms.rpc.afterloan.ToDayUpdateDTO;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.file.FileInfo;

/**
 * 
 * @ClassName: AfterLoanSupervisionMapper
 * @Description:贷后监控 持久层 mapper
 * @author: loe.luozhonghua
 * @date: 2015年3月23日 下午8:30:31
 */
@MapperScan("afterLoanSupervisionMapper")
public interface AfterLoanSupervisionMapper<T, PK> extends BaseMapper<T, PK> {
	List<ProjectInfo> getProjectData(Integer pid);
	/*
	 * List<Supervision> supervisionList( SupervisionSearchBean
	 * supervisionSearchBean);
	 */

	int createBizProRegPlan(BizProjectRegPlan bizProjectRegPlan);

	List<ResultBizProjectRegPlan> supervisionTaskList(
			SupervisionSearchBean supervisionSearchBean);

	ResultBizProjectRegPlan getSupervisionTask(Integer pid);

	int createBizProjectRegHistory(BizProjectRegHistory bizProjectRegHistory);
	// 把待监管状态的计划修改为监管中
	void updateBizProjectRegPlanStart(BizProjectRegPlan bizProjectRegPlan);

	List<BizProjectRegHistory> getBizProjectRegHistoryList(Integer pid);

	ResultBizProjectRegPlan getProject(Integer pid);

	List<ResultBizProjectRegPlan> getSupervisionTaskList(Integer pid);
     // 更改监管计划
	int updateBizProjectRegPlan(BizProjectRegPlan bizProjectRegPlan);
	// 更改监管计划的监管状态
	int updateBizProjectRegPlanRegulatoryStatus(BizProjectRegPlan bizProjectRegPlan);
	//更改任务状态
	int updateBizTaskStatus(Integer integer);

	//获取监管记录，根据项目ID或者计划ID来获取监管记录，主要用于页面的查看监管详情
	List<SupervisionResultList> getRegulatoryRecordList(
			SupervisionResultSearch supervisionResultSearch);
	
	List<SupervisionResultList> getSupervisionResultList(
			SupervisionResultSearch supervisionResultSearch);

	List<GridViewDTO> supervisionList(
			SupervisionSearchBean supervisionSearchBean);

	int updateBizProRegPlan(BizProjectRegPlan bizProjectRegPlan);

	int deleteSupervisePlan(List pids);

	BizProjectRegHistory getBizProjectRegHistory(int pid);

	int updateBizProjectRegHistory(BizProjectRegHistory bizProjectRegHistory);
	
	int deleteResultSuperviseHistory(int pid);
	
	int getTotalSupervisionList(
			SupervisionSearchBean supervisionSearchBean);
	
	int getTotalSupervisionTask(
			SupervisionSearchBean supervisionSearchBean);
	int getTotalSupervisionResult(
			SupervisionResultSearch supervisionResultSearch);
	
	// 资料上传
	int getFileDataTotal(FileInfo fileInfo);
	List<FileInfo> getFileDataList(FileInfo fileInfo);
	boolean deleteBusinessFile(int dataId);
	int createBusinessFile(FileInfo fileInfo);
	
	/**
	 * 
	  * @Description: 查询今日更新数据
	  * @param toDayUpdateDTO
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年5月27日 下午3:07:14
	 */
	List<ToDayUpdateDTO> getToDayUpdateList(ToDayUpdateDTO toDayUpdateDTO);
}
