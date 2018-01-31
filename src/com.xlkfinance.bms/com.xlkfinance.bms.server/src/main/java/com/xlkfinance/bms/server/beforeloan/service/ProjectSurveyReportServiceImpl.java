/**
 * @Title: SysConfigServiceImpl.java
 * @Package com.xlkfinance.bms.server.system.service
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月15日 下午4:52:19
 * @version V1.0
 */
package com.xlkfinance.bms.server.beforeloan.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.beforeloan.ProjectSurveyReport;
import com.xlkfinance.bms.rpc.beforeloan.ProjectSurveyReportService.Iface;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectSurveyReportMapper;

@SuppressWarnings("unchecked")
@Service("projectSurveyReportServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.beforeloan.ProjectSurveyReportService")
public class ProjectSurveyReportServiceImpl implements Iface {
	private Logger logger = LoggerFactory.getLogger(ProjectSurveyReportServiceImpl.class);

	@SuppressWarnings("rawtypes")
	@Resource(name = "projectSurveyReportMapper")
	private ProjectSurveyReportMapper projectSurveyReportMapper;
    /**
     * 新增尽责报告
     */
	@Override
	public int addSurveyReport(ProjectSurveyReport projectSurveyReport) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 获取PID
			int pid = projectSurveyReportMapper.getSeqBizProjectSurveyReport();
			// 赋值
			projectSurveyReport.setPid(pid);
			// 新增对象
			rows = projectSurveyReportMapper.insert(projectSurveyReport);
			if (rows >= 1) {
				rows = pid;
			} else {
				// 抛出异常处理
				throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "新增失败,请重新操作!");
			}
		} catch (Exception e) {
			logger.error("新增尽职调查报告:" + ExceptionUtil.getExceptionMessage(e));
			throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "新增失败,请重新操作!");
		}
		return rows;
	}
	/**   	
	 * 修改尽职调查报告
	 */
	@Override
	public int updateSurveyReport(ProjectSurveyReport projectSurveyReport) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 修改对象
			rows = projectSurveyReportMapper.updateByPrimaryKey(projectSurveyReport);
			if(rows>=1){
				rows = projectSurveyReport.getPid();
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("修改尽职调查报告:" + e.getMessage());
			}
			throw new ThriftServiceException(ExceptionCode.SYSUSER_UPDATE, "修改失败,请重新操作!");
		}
		return rows;
	}
    /**
     * 根据项目ID查尽责调查
     */
	@Override
	public ProjectSurveyReport getSurveyReportByProjectId(int projectId) throws ThriftServiceException, TException {
		ProjectSurveyReport projectSurveyReport = new ProjectSurveyReport();
		try {
			List<ProjectSurveyReport> list = new ArrayList<ProjectSurveyReport>();
			list = projectSurveyReportMapper.getSurveyReportByProjectId(projectId);
			// 判断是否成功
			if (list.size() > 0) {
				projectSurveyReport = list.get(0);
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询尽职调查报告:" + e.getMessage());
			}
		}
		return projectSurveyReport;
	}

}
