package com.xlkfinance.bms.server.customer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.achievo.framework.util.StringUtil;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.customer.CusComBalanceSheetCalculateDTO;
import com.xlkfinance.bms.rpc.customer.CusComCalculateService.Iface;
import com.xlkfinance.bms.rpc.customer.CusComCashFlowReportCalculateDTO;
import com.xlkfinance.bms.rpc.customer.CusComIncomeReportCalculateDTO;
import com.xlkfinance.bms.server.customer.mapper.CusComCalculateMapper;

/**
 * 类描述<br>
 * 
 * @author pengchuntao
 * @version v1.0
 */
@SuppressWarnings("unchecked")
@Service("cusComCalculateServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.customer.CusComCalculateService")
public class CusComCalculateServiceImpl implements Iface {
	@SuppressWarnings("rawtypes")
	@Resource(name = "cusComCalculateMapper")
	private CusComCalculateMapper cusComCalculateMapper;

	@Override
	public List<CusComBalanceSheetCalculateDTO> obtainLeftCusComBSLCalculatesByReportId(
			int reportId) throws ThriftServiceException, TException {
		
		try {
			List<CusComBalanceSheetCalculateDTO> bscList = cusComCalculateMapper.obtainLeftCusComBSLCalculatesByReportId(reportId);
			return bscList==null?new ArrayList<CusComBalanceSheetCalculateDTO>():bscList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_BASE_QUERY, "获得资产负债表左边资产类数据失败！");
		}
	}

	@Override
	public List<CusComBalanceSheetCalculateDTO> obtainRightCusComBSLCalculatesByReportId(
			int reportId) throws ThriftServiceException, TException {
		try {
			List<CusComBalanceSheetCalculateDTO> bscList = cusComCalculateMapper.obtainRightCusComBSLCalculatesByReportId(reportId);
			return bscList==null?new ArrayList<CusComBalanceSheetCalculateDTO>():bscList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_BASE_QUERY, "获得资产负债表右边负债类数据失败！");
		}
	}
	
	/**
	 * 
	 * @Description: 把负债类左边的资产类的计算值转换为Map来操作
	 * @return 
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	public Map<Integer,Double> obtainLeftCusComBSLCalculatesMap(int reportId){
		 Map<Integer,Double> leftBalanceSheetCalculatesMap = new HashMap<Integer,Double>();
		 try {
			List<CusComBalanceSheetCalculateDTO> dtoList = obtainLeftCusComBSLCalculatesByReportId(reportId);
			for (CusComBalanceSheetCalculateDTO cusComBalanceSheetCalculateDTO : dtoList) {
				int lineNum = cusComBalanceSheetCalculateDTO.getLineNum();
				double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
				leftBalanceSheetCalculatesMap.put(lineNum,endVal);
			}
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
		 
		return leftBalanceSheetCalculatesMap;
	}
	
	/**
	 * 
	 * @Description: 把负债类右边的负债类的计算值转换为Map来操作
	 * @return 
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	public Map<Integer,Double> obtainRightCusComBSLCalculatesMap(int reportId){
		 Map<Integer,Double> rightBalanceSheetCalculatesMap = new HashMap<Integer,Double>();
		 try {
			List<CusComBalanceSheetCalculateDTO> dtoList = obtainRightCusComBSLCalculatesByReportId(reportId);
			for (CusComBalanceSheetCalculateDTO cusComBalanceSheetCalculateDTO : dtoList) {
				int lineNum = cusComBalanceSheetCalculateDTO.getLineNum();
				double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
				rightBalanceSheetCalculatesMap.put(lineNum,endVal);
			}
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
		 
		return rightBalanceSheetCalculatesMap;
	}

	/**
	 * 
	 * @Description: 根据企业ID与年获得该企业最大期末的PID
	 * @return 
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public String obtainPIDForMaxMonth(int comId,int accountingYear) throws ThriftServiceException,
			TException {
		try {
			Map<String,Integer> map = new HashMap<String,Integer>();
			map.put("comId", comId);
			map.put("accountingYear", accountingYear);
			String result = cusComCalculateMapper.obtainPIDForMaxMonth(map);
			return StringUtil.isEmpty(result)?"-1:-1":result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_BASE_QUERY, "根据企业ID与年获得该企业最大期末的PID与最大月份失败！");
		}
	}

	/**
	 * 
	 * @Description: 根据企业ID获得该企业利润表的计算DTO
	 * @return 
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public List<CusComIncomeReportCalculateDTO> obtainIncomeReportCalculateByReportId(
			int reportId) throws ThriftServiceException, TException {
		try {
			return cusComCalculateMapper.obtainIncomeReportCalculateByReportId(reportId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_BASE_QUERY, "根据企业ID获得该企业利润表的计算DTO失败！");
		}
	}

	/**
	 * 
	 * @Description: 根据企业ID获得该企业利润表的计算DTO ，然后转换为Map ,key=LINE_NUM ,Value=THIS_YEAR_VAL
	 * @return 
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public Map<Integer, Double> obtainIncomeReportCalculateMap(int reportId)
			throws ThriftServiceException, TException {
		 Map<Integer,Double> incomeReportCalculateMap = new HashMap<Integer,Double>();
		 try {
			List<CusComIncomeReportCalculateDTO> dtoList = obtainIncomeReportCalculateByReportId(reportId);
			for (CusComIncomeReportCalculateDTO cusComIncomeReportCalculateDTO : dtoList) {
				int lineNum = cusComIncomeReportCalculateDTO.getLineNum();
				double endVal = cusComIncomeReportCalculateDTO.getThisYearVal();
				incomeReportCalculateMap.put(lineNum,endVal);
			}
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
		 
		return incomeReportCalculateMap;
	}

	/**
	 * 
	 * @Description: 根据企业ID获得该企业现金流量表的计算DTO
	 * @return 
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public List<CusComCashFlowReportCalculateDTO> obtainCashFlowCalculateByReportId(
			int reportId) throws ThriftServiceException, TException {
		try {
			return cusComCalculateMapper.obtainCashFlowCalculateByReportId(reportId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_BASE_QUERY, "根据企业ID获得该企业现金流量表的计算DTO失败！");
		}
	}

	/**
	 * 
	 * @Description: 根据企业ID获得该企业现金流量表的计算DTO ，然后转换为Map ,key=LINE_NUM ,Value=THIS_YEAR_VAL
	 * @return 
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public Map<Integer, Double> obtainCashFlowCalculateMap(int reportId)
			throws ThriftServiceException, TException {
		Map<Integer,Double> obtainCashFlowCalculateMap = new HashMap<Integer,Double>();
		 try {
			List<CusComCashFlowReportCalculateDTO> dtoList = obtainCashFlowCalculateByReportId(reportId);
			for (CusComCashFlowReportCalculateDTO cusComCashFlowReportCalculateDTO : dtoList) {
				int lineNum = cusComCashFlowReportCalculateDTO.getLineNum();
				double endVal = cusComCashFlowReportCalculateDTO.getThisYearVal();
				obtainCashFlowCalculateMap.put(lineNum,endVal);
			}
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
		 
		return obtainCashFlowCalculateMap;
	}

	/**
	 * 
	 * @Description: 根据企业ID获得该企业现金流量补充表的计算DTO
	 * @return 
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public List<CusComCashFlowReportCalculateDTO> obtainSupplementCashFlowCalculateByReportId(
			int reportId) throws ThriftServiceException, TException {
		try {
			return cusComCalculateMapper.obtainSupplementCashFlowCalculateByReportId(reportId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_BASE_QUERY, "根据企业ID获得该企业现金流量补充表的计算DTO失败！");
		}
	}

	/**
	 * 
	 * @Description: 根据企业ID获得该企业现金流量补充表的计算DTO ，然后转换为Map ,key=LINE_NUM ,Value=THIS_YEAR_VAL
	 * @return 
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public Map<Integer, Double> obtainSupplementCashFlowCalculateMap(
			int reportId) throws ThriftServiceException, TException {
		Map<Integer,Double> obtainCashFlowCalculateMap = new HashMap<Integer,Double>();
		 try {
			List<CusComCashFlowReportCalculateDTO> dtoList = obtainSupplementCashFlowCalculateByReportId(reportId);
			for (CusComCashFlowReportCalculateDTO cusComCashFlowReportCalculateDTO : dtoList) {
				int lineNum = cusComCashFlowReportCalculateDTO.getLineNum();
				double endVal = cusComCashFlowReportCalculateDTO.getThisYearVal();
				obtainCashFlowCalculateMap.put(lineNum,endVal);
			}
		} catch (ThriftServiceException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
		 
		return obtainCashFlowCalculateMap;
	}

}
