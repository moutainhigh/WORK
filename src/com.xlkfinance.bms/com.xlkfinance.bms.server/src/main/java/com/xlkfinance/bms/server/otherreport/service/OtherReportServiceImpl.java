package com.xlkfinance.bms.server.otherreport.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.otherReport.ForeclosureCapDetailsReport;
import com.xlkfinance.bms.rpc.otherReport.ForeclosureOrgDetailsReport;
import com.xlkfinance.bms.rpc.otherReport.OtherReportService.Iface;
import com.xlkfinance.bms.server.otherreport.mapper.OtherReportMapper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： jony <br>
 * ★☆ @time：2016年3月1日上午11:14:38 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 报表service<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@SuppressWarnings("unchecked")
@Service("OtherReportService")
@ThriftService(service = "com.xlkfinance.bms.rpc.otherReport.OtherReportService")
public class OtherReportServiceImpl implements Iface {
   @Resource(name = "otherReportMapper")
   private OtherReportMapper otherReportMapper;
   
   /**
    * 资金方业务统计新增详情
    *@author:Jony
    *@time:2016年3月1日上午11:28:08
    *@param ForeclosureCapDetailsReport
    *@return
    */
	@Override
	public List<ForeclosureCapDetailsReport> queryForeclosureCapNewDetails(
			ForeclosureCapDetailsReport query) throws TException {
		 return otherReportMapper.queryForeclosureCapNewDetails(query);
	}
	   /**
	    * 资金方业务统计新增详情总数
	    *@author:Jony
	    *@time:2016年3月1日上午11:28:08
	    *@param ForeclosureCapDetailsReport
	    *@return
	    */
	@Override
	public int queryForeclosureCapNewDetailsTotal(ForeclosureCapDetailsReport query)
			throws TException {
		return otherReportMapper.queryForeclosureCapNewDetailsTotal (query);
	}
	   /**
	    * 资金方业务统计结清详情
	    *@author:Jony
	    *@time:2016年3月1日上午11:28:08
	    *@param ForeclosureCapDetailsReport
	    *@return
	    */
	@Override
	public List<ForeclosureCapDetailsReport> queryForeclosureCapSquareDetails(
			ForeclosureCapDetailsReport query) throws TException {
		return otherReportMapper.queryForeclosureCapSquareDetails (query);
	}
	   /**
	    * 资金方业务统计结清详情总数
	    *@author:Jony
	    *@time:2016年3月1日上午11:28:08
	    *@param ForeclosureCapDetailsReport
	    *@return
	    */
	@Override
	public int queryForeclosureCapSquareDetailsTotal(
			ForeclosureCapDetailsReport query) throws TException {
		return otherReportMapper.queryForeclosureCapSquareDetailsTotal (query);
	}
	   /**
	    * 资金方业务统计在途详情
	    *@author:Jony
	    *@time:2016年3月1日上午11:28:08
	    *@param ForeclosureCapDetailsReport
	    *@return
	    */
	@Override
	public List<ForeclosureCapDetailsReport> queryForeclosureCapIngDetails(
			ForeclosureCapDetailsReport query) throws TException {
		return otherReportMapper.queryForeclosureCapIngDetails (query);
	}
	 /**
	    * 资金方业务统计在途总数
	    *@author:Jony
	    *@time:2016年3月1日上午11:28:08
	    *@param ForeclosureCapDetailsReport
	    *@return
	    */
	@Override
	public int queryForeclosureCapIngDetailsTotal(
			ForeclosureCapDetailsReport query) throws TException {
		return otherReportMapper.queryForeclosureCapIngDetailsTotal (query);
	}
	
	 /**
	    * 合作机构业务统计新增详情
	    *@author:Jony
	    *@time:2016年3月1日上午11:28:08
	    *@param ForeclosureCapDetailsReport
	    *@return
	    */
	@Override
	public List<ForeclosureOrgDetailsReport> queryForeclosureOrgNewDetails(
			ForeclosureOrgDetailsReport query) throws TException {
		return otherReportMapper.queryForeclosureOrgNewDetails (query);
	}
	 /**
	    * 合作机构业务统计新增详情总数字
	    *@author:Jony
	    *@time:2016年3月1日上午11:28:08
	    *@param ForeclosureCapDetailsReport
	    *@return
	    */
	@Override
	public int queryForeclosureOrgNewDetailsTotal(
			ForeclosureOrgDetailsReport query) throws TException {
		return otherReportMapper.queryForeclosureOrgNewDetailsTotal (query);
	}
	/**
	    * 合作机构业务统计结清详情
	    *@author:Jony
	    *@time:2016年3月1日上午11:28:08
	    *@param ForeclosureCapDetailsReport
	    *@return
	    */
	@Override
	public List<ForeclosureOrgDetailsReport> queryForeclosureOrgSquareDetails(
			ForeclosureOrgDetailsReport query) throws TException {
		return otherReportMapper.queryForeclosureOrgSquareDetails (query);
	}
	/**
	    * 合作机构业务统计结清详情总数
	    *@author:Jony
	    *@time:2016年3月1日上午11:28:08
	    *@param ForeclosureCapDetailsReport
	    *@return
	    */
	@Override
	public int queryForeclosureOrgSquareDetailsTotal(
			ForeclosureOrgDetailsReport query) throws TException {
		return otherReportMapper.queryForeclosureOrgSquareDetailsTotal (query);
	}
	/**
	    * 合作机构业务统计在途详情
	    *@author:Jony
	    *@time:2016年3月1日上午11:28:08
	    *@param ForeclosureCapDetailsReport
	    *@return
	    */
	@Override
	public List<ForeclosureOrgDetailsReport> queryForeclosureOrgIngDetails(
			ForeclosureOrgDetailsReport query) throws TException {
		
		return otherReportMapper.queryForeclosureOrgIngDetails (query);
	}
	/**
	    * 合作机构业务统计在途详情总数
	    *@author:Jony
	    *@time:2016年3月1日上午11:28:08
	    *@param ForeclosureCapDetailsReport
	    *@return
	    */
	@Override
	public int queryForeclosureOrgIngDetailsTotal(
			ForeclosureOrgDetailsReport query) throws TException {
		return otherReportMapper.queryForeclosureOrgIngDetailsTotal (query);
	}
}
