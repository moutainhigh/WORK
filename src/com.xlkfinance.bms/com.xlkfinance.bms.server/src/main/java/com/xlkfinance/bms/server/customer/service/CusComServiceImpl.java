package com.xlkfinance.bms.server.customer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.google.common.collect.Lists;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.customer.BalanceSheetEditDTO;
import com.xlkfinance.bms.rpc.customer.CashFlowItem;
import com.xlkfinance.bms.rpc.customer.CashFlowReport;
import com.xlkfinance.bms.rpc.customer.CashFlowReportEditDTO;
import com.xlkfinance.bms.rpc.customer.CusAcct;
import com.xlkfinance.bms.rpc.customer.CusComAssure;
import com.xlkfinance.bms.rpc.customer.CusComBalanceSheet;
import com.xlkfinance.bms.rpc.customer.CusComBalanceSheetMeta;
import com.xlkfinance.bms.rpc.customer.CusComBase;
import com.xlkfinance.bms.rpc.customer.CusComBaseDTO;
import com.xlkfinance.bms.rpc.customer.CusComContact;
import com.xlkfinance.bms.rpc.customer.CusComDebt;
import com.xlkfinance.bms.rpc.customer.CusComFinanceReport;
import com.xlkfinance.bms.rpc.customer.CusComFinanceReportOverviewDTO;
import com.xlkfinance.bms.rpc.customer.CusComGuaranteeType;
import com.xlkfinance.bms.rpc.customer.CusComIncomeReport;
import com.xlkfinance.bms.rpc.customer.CusComIncomeReportEditDTO;
import com.xlkfinance.bms.rpc.customer.CusComIncomeReportMeta;
import com.xlkfinance.bms.rpc.customer.CusComInvest;
import com.xlkfinance.bms.rpc.customer.CusComReward;
import com.xlkfinance.bms.rpc.customer.CusComService.Iface;
import com.xlkfinance.bms.rpc.customer.CusComShare;
import com.xlkfinance.bms.rpc.customer.CusComStaff;
import com.xlkfinance.bms.rpc.customer.CusComStaffDTO;
import com.xlkfinance.bms.rpc.customer.CusComTeam;
import com.xlkfinance.bms.rpc.customer.CusDTO;
import com.xlkfinance.bms.rpc.customer.CusPerCom;
import com.xlkfinance.bms.rpc.customer.ExcelCusComBalanceSheet;
import com.xlkfinance.bms.rpc.customer.ExcelCusComIncomeReport;
import com.xlkfinance.bms.rpc.customer.ExportCusComBase;
import com.xlkfinance.bms.rpc.customer.FinacialDTO;
import com.xlkfinance.bms.rpc.customer.QueryPersonDTO;
import com.xlkfinance.bms.server.customer.mapper.CashFlowItemMapper;
import com.xlkfinance.bms.server.customer.mapper.CashFlowMetaItemMapper;
import com.xlkfinance.bms.server.customer.mapper.CashFlowMetaReportMapper;
import com.xlkfinance.bms.server.customer.mapper.CashFlowReportMapper;
import com.xlkfinance.bms.server.customer.mapper.CusAcctMapper;
import com.xlkfinance.bms.server.customer.mapper.CusComAssureMapper;
import com.xlkfinance.bms.server.customer.mapper.CusComBalanceSheetMapper;
import com.xlkfinance.bms.server.customer.mapper.CusComBalanceSheetMetaMapper;
import com.xlkfinance.bms.server.customer.mapper.CusComBaseMapper;
import com.xlkfinance.bms.server.customer.mapper.CusComContactMapper;
import com.xlkfinance.bms.server.customer.mapper.CusComDebtMapper;
import com.xlkfinance.bms.server.customer.mapper.CusComFinanceReportMapper;
import com.xlkfinance.bms.server.customer.mapper.CusComIncomeReportMapper;
import com.xlkfinance.bms.server.customer.mapper.CusComIncomeReportMetaMapper;
import com.xlkfinance.bms.server.customer.mapper.CusComInvestMapper;
import com.xlkfinance.bms.server.customer.mapper.CusComRewardMapper;
import com.xlkfinance.bms.server.customer.mapper.CusComShareMapper;
import com.xlkfinance.bms.server.customer.mapper.CusComStaffMapper;
import com.xlkfinance.bms.server.customer.mapper.CusComTeamMapper;
import com.xlkfinance.bms.server.customer.mapper.CusPerBaseMapper;
import com.xlkfinance.bms.server.customer.mapper.CusPerComMapper;

/**
 * 类描述<br>
 * 企业客户实现类
 * 
 * @author zhanghg
 * @version v1.0
 */
@SuppressWarnings("unchecked")
@Service("cusComServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.customer.CusComService")
public class CusComServiceImpl implements Iface {
	private Logger logger = LoggerFactory.getLogger(CusComServiceImpl.class);

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusPerBaseMapper")
	private CusPerBaseMapper cusPerBaseMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusComBaseMapper")
	private CusComBaseMapper cusComBaseMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusAcctMapper")
	private CusAcctMapper cusAcctMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusComShareMapper")
	private CusComShareMapper cusComShareMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusComStaffMapper")
	private CusComStaffMapper cusComStaffMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusComContactMapper")
	private CusComContactMapper cusComContactMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusComTeamMapper")
	private CusComTeamMapper cusComTeamMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusComAssureMapper")
	private CusComAssureMapper cusComAssureMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusComDebtMapper")
	private CusComDebtMapper cusComDebtMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusComInvestMapper")
	private CusComInvestMapper cusComInvestMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusComRewardMapper")
	private CusComRewardMapper cusComRewardMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusComIncomeReportMetaMapper")
	private CusComIncomeReportMetaMapper cusComIncomeReportMetaMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusComIncomeReportMapper")
	private CusComIncomeReportMapper cusComIncomeReportMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusComFinanceReportMapper")
	private CusComFinanceReportMapper cusComFinanceReportMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cashFlowItemMapper")
	private CashFlowItemMapper cashFlowItemMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cashFlowReportMapper")
	private CashFlowReportMapper cashFlowReportMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusComBalanceSheetMetaMapper")
	private CusComBalanceSheetMetaMapper cusComBalanceSheetMetaMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusComBalanceSheetMapper")
	private CusComBalanceSheetMapper cusComBalanceSheetMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cashFlowMetaItemMapper")
	private CashFlowMetaItemMapper cashFlowMetaItemMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cashFlowMetaReportMapper")
	private CashFlowMetaReportMapper cashFlowMetaReportMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusPerComMapper")
	private CusPerComMapper cusPerComMapper;
	/**
	 * 获取企业客户的利润信息 表
	 */
	@Override
	public List<ExcelCusComBalanceSheet> getExcelCusComBalanceSheet(String pids) throws ThriftServiceException, TException {
		String arry[] = pids.split(",");
		List list = Lists.newArrayList();
		for (int i = 0; i < arry.length; i++) {
			list.add(arry[i]);
		}
		List<ExcelCusComBalanceSheet> listResult = cusComBalanceSheetMapper.getExcelCusComBalanceSheet(list);
		if(listResult.size()==0){
			return new ArrayList<ExcelCusComBalanceSheet>();
		}
		return listResult;
	}
	/**
	 * 导出企业客户的利润信息 表
	 */
	@Override
	public List<ExcelCusComIncomeReport> excelCusComIncomeReportByReportId(String pids) throws ThriftServiceException, TException {
		String arry[] = pids.split(",");
		List list = Lists.newArrayList();
		for (int i = 0; i < arry.length; i++) {
			list.add(arry[i]);
		}
		List<ExcelCusComIncomeReport> listResult = cusComIncomeReportMapper.excelCusComIncomeReportByReportId(list);
		if(listResult.size()==0){
			return new ArrayList<ExcelCusComIncomeReport>();
		}
		return listResult;
	}
	/**
	 * 保存客户信息
	 */
	@Override
	public Map addCusComBase(CusComBaseDTO cusComBaseDTO) throws ThriftServiceException, TException {
		// 保存客户信息
		int comId = 0;
		int acctId = 0;
		int comOwnId = cusComBaseDTO.getCusComBase().getComOwnId();// 法人的PID
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> maps = new HashMap<String, Object>();
		try {
			// 如果perCusId不为零，说明是保存个人客户旗下公司基础信息
			int perCusId = cusComBaseDTO.getCusAcct().getPid();
			if (cusComBaseDTO.getCusAcct().getPid() == 0) {
				 cusAcctMapper.insert(cusComBaseDTO.getCusAcct());
			 }
			// 保存企业客户基础信息
			cusComBaseDTO.getCusComBase().setCusAcct(cusComBaseDTO.getCusAcct());
			cusComBaseMapper.insert(cusComBaseDTO.getCusComBase());

			// 保存旗下公司关联
			if (perCusId != 0) {
				int comIds = cusComBaseDTO.getCusComBase().getPid();
				// 根据perCusId查找个人客户Id
				int cusPerId = cusComBaseMapper.selectCusPerIdByCusAcctId(perCusId);
				maps.put("comIds", comIds);
				maps.put("cusPerId", cusPerId);
				maps.put("status", 1);
				cusPerComMapper.insert(maps);
			}
			// 保存股东投资人员信息
			// cusComBaseDTO.getCusComShares();
			List<CusComShare> listCusCom = Lists.newArrayList();
			for (CusComShare cusComShare : cusComBaseDTO.getCusComShares()) {
				if (cusComShare != null && cusComShare.getShareName() != null && !"".equals(cusComShare.getShareName()) && cusComShare.getShareType() > 0) {
					cusComShare.setCusComBase(cusComBaseDTO.getCusComBase());
					cusComShare.setStatus(1);
					listCusCom.add(cusComShare);
				}
			}
			if (listCusCom.size() > 0) {
				cusComShareMapper.insertCusComShares(listCusCom);
			}
			// 将企业客户的acctId 更新到法人信息表里面
			acctId = cusComBaseDTO.getCusAcct().getPid();
			map.put("acctId", acctId);
			// map.put("comId", cusComBaseDTO.getCusComBase().getPid());
			map.put("comOwnId", comOwnId);
			cusComBaseMapper.updateByComOwnId(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_BASE_ADD, "新增企业客户基础信息失败！");
		}
		comId = cusComBaseDTO.getCusComBase().getPid();
		map.put("comId", comId);
		map.put("acctId", acctId);
		return map;
	}
	/**
	 * 修改企业客户信息
	 */
	@Override
	public Map updateCusComBase(CusComBaseDTO cusComBaseDTO) throws ThriftServiceException, TException {
		HashMap<String, Object> maps = new HashMap<String, Object>();
		try {
			cusComBaseMapper.updateByPrimaryKey(cusComBaseDTO.getCusComBase());
			// 保存客户经理pmUserId
			cusComBaseMapper.updatePmUserId(cusComBaseDTO.getCusAcct());
			List<CusComShare> listCusCom = Lists.newArrayList();// 新增操作的list
			List<CusComShare> listCusCom2 = Lists.newArrayList();// 修改操作list
			for (CusComShare cusComShare : cusComBaseDTO.getCusComShares()) {
				if (cusComShare.getPid() > 0) {
					cusComShare.setStatus(1);
					cusComShare.setCusComBase(cusComBaseDTO.getCusComBase());
					listCusCom2.add(cusComShare);
				} else {
					if (cusComShare.getShareName() != null && !"".equals(cusComShare.getShareName()) && cusComShare.getShareType() > 0) {
						cusComShare.setStatus(1);
						cusComShare.setCusComBase(cusComBaseDTO.getCusComBase());
						listCusCom.add(cusComShare);
					}
				}

			}
			
			
			if (listCusCom2.size() > 0) {
				cusComShareMapper.deleteCusComShares(listCusCom2);
				cusComShareMapper.updateCusComShares(listCusCom2);
			}
			
			
			if (listCusCom.size() > 0) {
				cusComShareMapper.insertCusComShares(listCusCom);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_BASE_UPDATE, "修改企业客户基础信息失败！");
		}
		maps.put("acctId", cusComBaseDTO.getCusAcct().getPid());
		maps.put("comId", cusComBaseDTO.getCusComBase().getPid());
		return maps;
	}
	/**
	 * 删除企业客户信息
	 */
	@Override
	public int deleteCusComBase(String pids) throws ThriftServiceException, TException {
		List<String> list = new ArrayList<String>();
		try {
			String[] s = pids.split(",");
			for (String string : s) {
				list.add(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_BASE_DELETE, "删除企业客户基础信息失败！");
		}
		return cusComBaseMapper.deleteCusComBase(list);
	}
	/**
	 * 查询客户信息列表
	 */
	@Override
	public List<GridViewDTO> getCusComBases(CusComBase cusComBase) throws TException {
		List<GridViewDTO> list = new ArrayList<GridViewDTO>();
		try {
			list = cusComBaseMapper.getCusComBases(cusComBase);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_BASE_QUERY, "查询企业客户基础信息失败！");
		}
		if(list.size()==0){
			return new ArrayList<GridViewDTO>();
		}
		return list;
	}
	/**
	 * 根据客户ID查企业客户
	 */
	@Override
	public CusComBaseDTO getCusComBase(int comId) throws TException {
		CusComBaseDTO cusComBaseDTO = new CusComBaseDTO();
		try {
			cusComBaseDTO.setCusComBase((CusComBase) cusComBaseMapper.selectByPrimaryKey(comId));
			// cusComBaseDTO.setCusAcct((CusAcct)
			// cusAcctMapper.selectPmUserIdByComId(cusComBaseDTO.getCusAcct().getPid()));
			cusComBaseDTO.setCusAcct((CusAcct) cusAcctMapper.selectByPrimaryKey(cusComBaseDTO.getCusComBase().getCusAcct().getPid()));
			// 查询企业基础信息ID
			cusComBaseDTO.setCusComShares(cusComShareMapper.selectShareById(comId));
		} catch (Exception e) {
			throw new ThriftServiceException(ExceptionCode.CUSCOM_BASE_QUERY, "查询企业客户基础信息失败！");
		}
		return cusComBaseDTO;
	}
	/**
	 * 添加员工结构
	 */
	@Override
	public int addCusComStaff(List<CusComStaff> cusComStaffs) throws ThriftServiceException, TException {
		int flag=0;
		try{
			flag = cusComStaffMapper.insertCusComStaffs(cusComStaffs);
		}
		catch(Exception e){
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_BASE_ADD, "添加企业客户员工结构信息失败！");
		}
		return flag;
	}
	/**
	 * 修改员工结构
	 */
	@Override
	public int updateCusComStaff(List<CusComStaff> cusComStaffs) throws ThriftServiceException, TException {
		int flag=0;
		try{
			flag = cusComStaffMapper.updateCusComStaffs(cusComStaffs);
		 }		
		 catch(Exception e){
			 e.printStackTrace();
			 throw new ThriftServiceException(ExceptionCode.CUSCOM_BASE_UPDATE, "修改企业客户员工结构信息失败！");
		 }
		return flag;
	}

	@Override
	public int addCusComContact(CusComContact cusComContact) throws ThriftServiceException, TException {

		return cusComContactMapper.insert(cusComContact);
	}

	@Override
	public int updateCusComContact(CusComContact cusComContact) throws ThriftServiceException, TException {
		return cusComContactMapper.updateByPrimaryKey(cusComContact);
	}

	@Override
	public int deleteCusComContact(String pid) throws ThriftServiceException, TException {
		try {
			List<String> list = new ArrayList<String>();
			String[] s = pid.split(",");
			for (String string : s) {
				list.add(string);
			}
			return cusComContactMapper.deleteComContact(list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_CONTACT_DELETE, "删除企业联系人信息失败！");
		}
	}
	/**
	 * 查询员工结构列表
	 */
	@Override
	public List<GridViewDTO> getCusComContacts(CusComContact comContact) throws TException {
		List<GridViewDTO> listResult = cusComContactMapper.getCusComContacts(comContact);
		if(listResult.size()==0){
			return new ArrayList<GridViewDTO>();
		}
		return listResult;
	}
	/**
	 * 根据ID查询员工结构列表
	 */
	@Override
	public CusComContact getCusComContact(int pid) throws TException {
		CusComContact cusComContact = (CusComContact) cusComContactMapper.selectByPrimaryKey(pid);
		if(null==cusComContact){
			return new CusComContact();
		}
		return cusComContact;
	}
	/**
	 * 新增管理团队信息
	 */
	@Override
	public int addCusComTeam(CusComTeam cusComTeam) throws ThriftServiceException, TException {
		return cusComTeamMapper.insert(cusComTeam);
	}
	/**
	 * 修改管理团队信息
	 */
	@Override
	public int updateCusComTeam(CusComTeam cusComTeam) throws ThriftServiceException, TException {
		return cusComTeamMapper.updateById(cusComTeam);
	}
	/**
	 * 删除管理团队信息
	 */
	@Override
	public int deleteCusComTeam(String pid) throws ThriftServiceException, TException {
		try {
			List<String> list = new ArrayList<String>();
			String[] s = pid.split(",");
			for (String string : s) {
				list.add(string);
			}
			return cusComTeamMapper.deleteCusComTeam(list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_TEAM_DELETE, "删除管理团队信息失败！");
		}
	}
	/**
	 * 查询管理团队信息列表
	 */
	@Override
	public List<GridViewDTO> getCusComTeams(CusComTeam cusComTeam) throws TException {
		List<GridViewDTO> listResult = cusComTeamMapper.getCusComTeams(cusComTeam);
		if(null==listResult){
			return new ArrayList<GridViewDTO>();
		}
		return cusComTeamMapper.getCusComTeams(cusComTeam);
	}
	/**
	 * 根据客户ID查管理团队信息
	 */
	@Override
	public CusComTeam getCusComTeam(int pid) throws TException {
		return (CusComTeam) cusComTeamMapper.selectCusComTeamById(pid);
	}
	/**
	 * 新增债务信息
	 */
	@Override
	public int addCusComDebt(CusComDebt cusComDebt) throws ThriftServiceException, TException {
		int flag = cusComDebtMapper.insert(cusComDebt);
		// 获取新的担保方式
		String[] warWay = cusComDebt.getWarWay().split(",");
		// 2.3循环添加担保方式
		for (int i = 0; i < warWay.length; i++) {
			// 2.3.1 创建 担保方式 对象
			CusComGuaranteeType cusComGuaranteeType = new CusComGuaranteeType();
			// 2.3.2 赋值
			cusComGuaranteeType.setComAssureId(cusComDebt.getPid());
			cusComGuaranteeType.setGuaranteeType(Integer.parseInt(warWay[i]));// 设置担保方式
			cusComGuaranteeType.setStatus(1);// 状态都默认为1,正常状态
			// 2.3.3 新增
			cusComDebtMapper.insertDebtGuarType(cusComGuaranteeType);
		}
		return flag;
	}

	@Override
	public int updateCusComDebt(CusComDebt cusComDebt) throws ThriftServiceException, TException {
		// 2.3.1 创建 担保方式 对象
		CusComGuaranteeType cusComGuaranteeTypes = new CusComGuaranteeType();
		cusComGuaranteeTypes = cusComGuaranteeTypes.setComAssureId(cusComDebt.getPid());
		int comAssureId = cusComGuaranteeTypes.getComAssureId();
		// 2.3.2 删除
		cusComDebtMapper.deleteDebtGuarType(comAssureId);
		// 获取新的担保方式
		String[] warWay = cusComDebt.getWarWay().split(",");
		// 2.3循环添加担保方式
		for (int i = 0; i < warWay.length; i++) {
			// 2.3.1 创建 担保方式 对象
			CusComGuaranteeType cusComGuaranteeType = new CusComGuaranteeType();
			cusComGuaranteeType.setComAssureId(cusComDebt.getPid());
			cusComGuaranteeType.setGuaranteeType(Integer.parseInt(warWay[i]));// 设置担保方式
			cusComGuaranteeType.setStatus(1);// 状态都默认为1,正常状态
			// 2.3.3 新增
			cusComDebtMapper.insertDebtGuarType(cusComGuaranteeType);
		}
		return cusComDebtMapper.updateByPrimaryKey(cusComDebt);
	}

	@Override
	public int deleteCusComDebt(String pids) throws ThriftServiceException, TException {
		try {
			List<String> list = new ArrayList<String>();
			String[] s = pids.split(",");
			for (String string : s) {
				list.add(string);
			}
			return cusComDebtMapper.deleteCusDebt(list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_DEPT_QUERY, "删除债权信息失败！");
		}

	}
	/**
	 * 查询债务人信息
	 */
	@Override
	public List<GridViewDTO> getCusComDebts(CusComDebt cusComDebt) throws TException {
		try {
			List<GridViewDTO> listResult = cusComDebtMapper.getCusComDebts(cusComDebt);
			if(null==listResult){
				return new ArrayList<GridViewDTO>();
			}
			return cusComDebtMapper.getCusComDebts(cusComDebt);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_DEPT_QUERY, "查询债权信息失败！");
		}
	}
	/**
	 * 根据ID查债务信息
	 */
	@Override
	public CusComDebt getCusComDebt(int pid) throws TException {

		try {
			CusComDebt cusComDebt = (CusComDebt) cusComDebtMapper.selectByPrimaryKey(pid);
			if(null==cusComDebt){
				return new CusComDebt();
			}
			return cusComDebt;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_DEPT_UPDATE, "修改债权信息失败！");
		}
	}
	/**
	 * 新增对外担保信息
	 */
	@Override
	public int addCusComAssure(CusComAssure cusComAssure) throws ThriftServiceException, TException {
		int flag = 0;
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
			flag = cusComAssureMapper.insert(cusComAssure);
			int cusComAssurePid = cusComAssure.getPid();
			int assWay = cusComAssure.getAssWay();
			map.put("cusComAssurePid", cusComAssurePid);
			map.put("assWay", assWay);
			cusComAssureMapper.insertCusComGuarantee(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_ASSURE_ADD, "增加对外担保信息失败！");
		}
		return flag;
	}
	/**
	 * 修改对外担保信息
	 */
	@Override
	public int updateCusComAssure(CusComAssure cusComAssure) throws ThriftServiceException, TException {
		int flag = 0;
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
			flag = cusComAssureMapper.updateByPrimaryKey(cusComAssure);
			int pid = cusComAssure.getPid();
			int assWay = cusComAssure.getAssWay();
			map.put("pid", pid);
			map.put("assWay", assWay);
			cusComAssureMapper.updateComGuaranteeType(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_ASSURE_UPDATE, "修改对外担保信息失败！");
		}
		return flag;
	}
	/**
	 * 删除对外担保信息
	 */
	@Override
	public int deleteCusComAssure(String pid) throws ThriftServiceException, TException {
		try {
			List<String> list = new ArrayList<String>();
			String[] s = pid.split(",");
			for (String string : s) {
				list.add(string);
			}
			return cusComAssureMapper.delete(list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_ASSURE_DELETE, "删除对外担保信息失败！");
		}

	}
	/**
	 * 查询对外担保信息
	 */
	@Override
	public List<GridViewDTO> getCusComAssures(CusComAssure cusComAssure) throws TException {

		try {
			List<GridViewDTO> listResult =cusComAssureMapper.getCusComAssures(cusComAssure);
			if(null==listResult){
			    return new ArrayList<GridViewDTO>();
			}
			return cusComAssureMapper.getCusComAssures(cusComAssure);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_ASSURE_QUERY, "查询对外担保信息失败！");
		}
	}
	/**
	 * 根据ID查询对外担保信息
	 */
	@Override
	public CusComAssure getCusComAssure(int pid) throws TException {
		try {
			CusComAssure cusComAssure = (CusComAssure) cusComAssureMapper.selectByPrimaryKey(pid);
			if(null==cusComAssure){
				return new CusComAssure();
			}
			return cusComAssure;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_ASSURE_UPDATE, "修改对外担保信息失败！");
		}
	}
	/**
	 * 新增对外投资信息
	 */
	@Override
	public int addCusComInvest(CusComInvest cusComInvest) throws ThriftServiceException, TException {

		try {
			return cusComInvestMapper.insert(cusComInvest);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_INVEST_ADD, "添加对外投资基础信息失败！");
		}
	}

	@Override
	public int updateCusComInvest(CusComInvest cusComInvest) throws ThriftServiceException, TException {
		try {
			return cusComInvestMapper.updateByPrimaryKey(cusComInvest);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_INVEST_UPDATE, "修改对外投资基础信息失败！");
		}
	}

	@Override
	public int deleteCusComInvest(String pid) throws ThriftServiceException, TException {
		try {
			List<String> list = new ArrayList<String>();
			String[] s = pid.split(",");
			for (String string : s) {
				list.add(string);
			}
			return cusComInvestMapper.delete(list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_INVEST_DELETE, "删除对外投资基础信息失败！");
		}
	}

	@Override
	public List<GridViewDTO> getCusComInvests(CusComInvest cusComInvest) throws TException {

		try {
			List<GridViewDTO> listResult =cusComInvestMapper.getCusComInvests(cusComInvest);
			if(null==listResult){
				return new ArrayList<GridViewDTO>();
			}
			return listResult;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_INVEST_QUERY, "查询对外投资基础信息失败！");
		}
	}
	/**
	 * 根据ID查对外投资信息
	 */
	@Override
	public CusComInvest getCusComInvest(int pid) throws TException {

		try {
			CusComInvest cusComInvest =(CusComInvest) cusComInvestMapper.selectByPrimaryKey(pid);
			if(null==cusComInvest){
				return new CusComInvest();
			}
			return cusComInvest;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_INVEST_QUERY, "修改对外投资基础信息失败！");
		}
	}
	/**
	 * 添加企业客户获奖信息
	 */
	@Override
	public int addCusComReward(CusComReward cusComReward) throws ThriftServiceException, TException {

		try {
			cusComReward.setStatus(1);
			return cusComRewardMapper.insert(cusComReward);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_ADD, "增加获奖信息失败！");
		}
	}
	/**
	 * 修改企业客户获奖信息
	 */
	@Override
	public int updateCusComReward(CusComReward cusComReward) throws ThriftServiceException, TException {

		try {
			return cusComRewardMapper.updateByPrimaryKey(cusComReward);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_UPDATE, "修改获奖信息失败！");
		}
	}
	/**
	 * 删除企业客户获奖信息
	 */
	@Override
	public int deleteCusComReward(String pid) throws ThriftServiceException, TException {
		try {
			List<String> list = new ArrayList<String>();
			String[] s = pid.split(",");
			for (String string : s) {
				list.add(string);
			}
			return cusComRewardMapper.delete(list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "删除获奖信息失败！");
		}

	}
	/**
	 * 查询企业客户获奖信息列表
	 */
	@Override
	public List<GridViewDTO> getCusComRewards(CusComReward cusComReward) throws TException {
		try {
			List<GridViewDTO> listResult = cusComRewardMapper.getCusComRewards(cusComReward);
			if(null==listResult){
				return new ArrayList<GridViewDTO>();
			}
			return listResult;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "查询获奖信息失败！");
		}
	}
	/**
	 * 查询企业客户获奖信息列表
	 */
	@Override
	public CusComReward getCusComReward(int pid) throws TException {

		try {
			CusComReward cusComReward = (CusComReward) cusComRewardMapper.selectByPrimaryKey(pid);
			if(null==cusComReward){
				return new CusComReward();
			}
			return cusComReward;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_UPDATE, "修改获奖信息失败！");
		}
	}
	/**
	 * 修改员工结构
	 */
	@Override
	public int updateCusComContacts(CusComContact cusComContact) throws ThriftServiceException, TException {
		try {
			return cusComContactMapper.updateCusComContacts(cusComContact);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_CONTACT_UPDATE, "修改企业联系人信息失败！");
		}

	}

	/**
	 * 
	 * @Description: 根据项目ID查询企业基本信息
	 * @param pid
	 *            项目ID
	 * @return 企业基本信息对象
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年5月25日 下午3:09:12
	 */
	@Override
	public CusDTO selectByPidPrimaryKey(int pid) throws ThriftServiceException, TException {
		CusDTO cusDTO = new CusDTO();
		try {
			cusDTO = cusComBaseMapper.selectByPidPrimaryKey(pid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_BASE_UPDATE, "根据项目ID查询企业基本信息！");
		}
		if(null==cusDTO){
			return new CusDTO();
		}
		return cusDTO;
	}

	/**
	 * 
	 * @Description: 根据客户ID查询企业信息
	 * @param acctId
	 *            客户ID
	 * @return 企业基本对象
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年5月25日 下午3:04:17
	 */
	@Override
	public CusDTO getCusComByAcctId(int acctId) throws TException {
		CusDTO cusDTO = new CusDTO();
		try {
			cusDTO = cusComBaseMapper.getCusComByAcctId(acctId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_BASE_UPDATE, "根据客户ID查询企业信息！");
		}
		if(null==cusDTO){
			return new CusDTO();
		}
		return cusDTO;
	}
	/**
	 * 根据客户ID修改企业基础信息
	 */
	@Override
	public int selectCusComBaseById(int acctId) throws ThriftServiceException, TException {
		try {
			return cusComBaseMapper.selectCusComBaseById(acctId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_BASE_UPDATE, "修改企业基础信息失败！");
		}
	}
	/**
	 * 根据客户ID查客户类型
	 */
	@Override
	public int selectCusTypeByAcctId(int acctId) throws ThriftServiceException, TException {
		return cusAcctMapper.selectCusTypeByAcctId(acctId);
	}
	/**
	 * 根据ID删除旗下公司
	 */
	@SuppressWarnings("rawtypes")
	public int deleteUnderCom(String pids) throws ThriftServiceException, TException {
		String[] arr = pids.split(",");
		List list = Lists.newArrayList();
		for (int i = 0; i < arr.length; i++) {
			list.add(arr[i]);
		}
		return cusComBaseMapper.deleteUnderCom(list);
	}
	/**
	 * 根据ID查债务信息
	 */
	@Override
	public CusComDebt getCusComDebtRight(int pid) throws TException {
		CusComDebt cusComDebt = (CusComDebt) cusComDebtMapper.selectByPrimaryKeys(pid);
		if(null==cusComDebt){
			return new CusComDebt();
		}
		return cusComDebt;
	}

	@Override
	public List<GridViewDTO> getCusComDebtss(CusComDebt cusComDebt) throws TException {
		List<GridViewDTO> listResult = cusComDebtMapper.getCusComDebtss(cusComDebt);
		if(null==listResult){
			return new ArrayList<GridViewDTO>();
		}
		return listResult;
	}

	@Override
	public CusComTeam updateCusComTeams(String pid) throws ThriftServiceException, TException {
		// TODO Auto-generated method stub
		return null;
	}
    /**
     * 新增员工结构
     */
	@Override
	public int addCusComStaffs(CusComStaffDTO cusComStaffDTO) throws ThriftServiceException, TException {
		// for (CusComStaff iterable_element : cusComStaffDTO.getStaffs()) {
		return cusComStaffMapper.insert(cusComStaffDTO);
		// }
		// return 0;
	}
	/**
     * 获取员工结构信息
     */
	@Override
	public List<CusComStaff> getCusComStaff(int comId) throws TException {
		List<CusComStaff> listResult = cusComStaffMapper.selectByComId(comId);
		if(null==listResult){
			return new ArrayList<CusComStaff>();
		}
		return listResult;
	}

	/**
	 * 
	 * @Description: 获得CUS_COM_INCOME_REPORT_META表所有项目
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public List<CusComIncomeReportMeta> selectAllCusComIncomeReportMeta() throws ThriftServiceException, TException {
		try {

			List<CusComIncomeReportMeta> list = cusComIncomeReportMetaMapper.selectAllCusComIncomeReportMeta();
			return list == null ? new ArrayList<CusComIncomeReportMeta>() : list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "查询企业财务报表-利润表项目表失败！");
		}
	}

	/**
	 * 
	 * @Description: 增加企业财务报表-利润表信息CUS_COM_INCOME_REPORT
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public int saveCusComIncomeReport(CusComIncomeReport cusComIncomeReport) throws ThriftServiceException, TException {

		try {
			return cusComIncomeReportMapper.saveCusComIncomeReport(cusComIncomeReport);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_ADD, "增加企业财务报表-利润表信息失败！");
		}
	}

	/**
	 * 
	 * @Description: 根据当前用户ID查询下面的客户企业信息
	 * @param cusDto
	 *            企业DTO
	 * @return 当前用户下的企业集合(包含他的下属的企业客户)
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月28日 上午1:56:03
	 */
	@Override
	public List<CusDTO> getEnterpriseList(CusDTO cusDto) throws TException {
		List<CusDTO> list = new ArrayList<CusDTO>();
		try {
			list = cusComBaseMapper.getEnterpriseList(cusDto);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询企业信息:" + e.getMessage());
			}
		}
		if(null==list){
			return new ArrayList<CusDTO>();
		}
		return list;
	}

	/**
	 * 
	 * @Description: 根据当前用户ID查询下面的客户企业信息的总数
	 * @param cusDto
	 *            企业信息DTO
	 * @return 企业客户的总数
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月28日 上午1:55:25
	 */
	@Override
	public int getEnterpriseListCount(CusDTO cusDto) throws TException {
		int count = 0;
		try {
			count = cusComBaseMapper.getEnterpriseListCount(cusDto);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询当前用户ID查询下面的客户企业信息的总数:" + e.getMessage());
			}
		}
		return count;
	}

	/**
	 * 
	 * @Description: 查询企业股东信息列表
	 * @param cusComId
	 *            企业ID
	 * @return 企业股东信息列表
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月28日 上午1:56:50
	 */
	@Override
	public List<CusComShare> getShareList(int cusComId) throws TException {
		List<CusComShare> list = new ArrayList<CusComShare>();
		try {
			list = cusComShareMapper.getShareList(cusComId);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询企业股东信息列表:" + e.getMessage());
			}
		}
		if(null==list){
			return new ArrayList<CusComShare>();
		}
		return list;
	}

	@Override
	public int updateCusComStaffs(CusComStaffDTO cusComStaffDTO) throws ThriftServiceException, TException {
		return cusComStaffMapper.updateCusComStaffs(cusComStaffDTO.getStaffs());
	}

	/**
	 * 
	 * @Description: 根据企业财务报表-利润表项目表LINE_NUM查询记录
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public CusComIncomeReportMeta selectCusComIncomeReportMetaByLineNum(int lineNum) throws ThriftServiceException, TException {
		try {
			CusComIncomeReportMeta cusComIncomeReportMeta = cusComIncomeReportMetaMapper.selectCusComIncomeReportMetaByLineNum(lineNum);
			if(null==cusComIncomeReportMeta){
				return new CusComIncomeReportMeta();
			}
			return cusComIncomeReportMeta;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "根据企业财务报表-利润表项目表LINE_NUM查询记录失败！");
		}
	}

	/**
	 * 
	 * @Description: 保存一条记录到CUS_COM_FINANCE_REPORT
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public int saveCusComFinanceReport(CusComFinanceReport cusComFinanceReport) throws ThriftServiceException, TException {
		try {
			return cusComFinanceReportMapper.saveCusComFinanceReport(cusComFinanceReport);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "保存一条记录到CUS_COM_FINANCE_REPORT失败！");
		}
	}

	/**
	 * 
	 * @Description: 根据企业ID(comId)查询CUS_COM_FINANCE_REPORT记录
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public CusComFinanceReport selectCusComFinanceReportByComId(int comId) throws ThriftServiceException, TException {
		try {
			CusComFinanceReport cusComFinanceReport = cusComFinanceReportMapper.selectCusComFinanceReportByComId(comId);
			if(null==cusComFinanceReport){
				return new CusComFinanceReport();
			}
			return cusComFinanceReport;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "根据企业ID(comId)查询CUS_COM_FINANCE_REPORT记录失败！");
		}
	}

	/**
	 * 
	 * @Description: 根据企业ID(comId),会计年度,会计期间-月份查询CUS_COM_FINANCE_REPORT记录
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public CusComFinanceReport selectCusComFinanceReportByComIdAndYearMonth(int comId, int accountingYear, int accountingMonth) throws ThriftServiceException, TException {
		try {
			Map map = new HashMap();
			map.put("comId", comId);
			map.put("accountingYear", accountingYear);
			map.put("accountingMonth", accountingMonth);

			CusComFinanceReport cusComFinanceReport = cusComFinanceReportMapper.selectCusComFinanceReportByComIdAndYearMonth(map);
			return cusComFinanceReport == null ? new CusComFinanceReport() : cusComFinanceReport;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "根据企业ID(comId)查询CUS_COM_FINANCE_REPORT记录失败！");
		}
	}
    /**
     * 查询现金流量报表
     */
	@Override
	public List<FinacialDTO> getAllCusComCaseFlow(CashFlowItem cashFlowItem) throws ThriftServiceException, TException {
		try {
			List<FinacialDTO> list = cashFlowItemMapper.getAllCusComCaseFlow(cashFlowItem);
			return list == null ? new ArrayList<FinacialDTO>() : list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "查询现金流量报表！");
		}

	}
	/**
     * 保存现金流量报表
     */
	@Override
	public int saveCusComCaseFlow(FinacialDTO finacialDTO) throws ThriftServiceException, TException {
		return cashFlowItemMapper.insertCashFlowReport(finacialDTO.getCashFlowReport());
	}
	/**
     * 根据客户id查询现金流量报表
     */
	@Override
	public CashFlowReport selectCusComCashFlowReportByComId(int comId) throws ThriftServiceException, TException {
		CashFlowReport cashFlowReport = cashFlowItemMapper.selectCusComCashFlowReportByComId(comId);
		if(null==cashFlowReport){
			return new CashFlowReport();
		}
		return cashFlowReport;
	}
	/**
     * 根据行号查询现金流量报表
     */
	@Override
	public CashFlowItem selectCusComCashFlowItemByLineNum(int lineNum) throws ThriftServiceException, TException {
		// return cashFlowItemMapper.selectCusComCashFlowItemByLineNum();
		return null;
	}

	/**
	 * 
	 * @Description: 保存IS_MAIN=0的现金流量表
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public int addCusComCashFlowReport(CashFlowReport cashFlowReport) throws ThriftServiceException, TException {
		try {
			return cashFlowReportMapper.addCusComCashFlowReport(cashFlowReport);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "保存IS_MAIN=0的现金流量表失败！");
		}
	}

	/**
	 * 现金流量项目报表
	 */
	@Override
	public List<FinacialDTO> getAllCusComCaseFlowMeta(CashFlowItem cashFlowItem) throws ThriftServiceException, TException {
		try {
			List<FinacialDTO> list = cashFlowMetaItemMapper.getAllCusComCaseFlowMeta(cashFlowItem);
			return list == null ? new ArrayList<FinacialDTO>() : list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "查询现金流量项目报表失败！");
		}
	}

	/**
	 * 保存现金流量补充材料表
	 */
	@Override
	public int addCusComCashFlowMetaReport(CashFlowReport cashFlowReport) throws ThriftServiceException, TException {
		try {
			return cashFlowMetaReportMapper.addCusComCashFlowMetaReport(cashFlowReport);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "保存现金流量补充材料表失败！");
		}
	}

	/**
	 * 
	 * @Description: 所有资产负债表报表会计科目表记录
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public List<CusComBalanceSheetMeta> selectAllCusComBalanceSheetMeta() throws ThriftServiceException, TException {
		try {
			List<CusComBalanceSheetMeta> dtoList = cusComBalanceSheetMetaMapper.selectAllCusComBalanceSheetMeta();
			return dtoList == null ? new ArrayList<CusComBalanceSheetMeta>() : dtoList;
		} catch (Exception e) {
			logger.error("所有资产负债表报表会计科目表记录！");
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_BASE_QUERY, "所有资产负债表报表会计科目表记录！");
		}
	}

	/**
	 * 
	 * @Description: 插入数据到资产负债表
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public int saveCusComBalanceSheet(CusComBalanceSheet cusComBalanceSheet) throws ThriftServiceException, TException {
		try {
			return cusComBalanceSheetMapper.saveCusComBalanceSheet(cusComBalanceSheet);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_ADD, "保存资产负债表信息失败！");
		}
	}

	/**
	 * 
	 * @Description: 根据企业财务报表-利润表项目表reportId查询记录
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public List<CusComIncomeReport> selectCusComIncomeReportByReportId(int reportId) throws ThriftServiceException, TException {
		try {
			List<CusComIncomeReport> list = cusComIncomeReportMapper.selectCusComIncomeReportByReportId(reportId);
			return list == null ? new ArrayList<CusComIncomeReport>() : list;
		} catch (Exception e) {
			logger.error("根据reportid查询利润表信息失败！");
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_BASE_QUERY, "根据reportid查询利润表信息失败！");
		}
	}

	/**
	 * 
	 * @Description: 查询所有左边资产负债表报表会计科目表记录
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public List<CusComBalanceSheetMeta> selectLeftCusComBalanceSheetMeta() throws ThriftServiceException, TException {
		try {
			List<CusComBalanceSheetMeta> dtoList = cusComBalanceSheetMetaMapper.selectLeftCusComBalanceSheetMeta();
			return dtoList == null ? new ArrayList<CusComBalanceSheetMeta>() : dtoList;
		} catch (Exception e) {
			logger.error("查询所有左边资产负债表报表会计科目表记录！");
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_BASE_QUERY, "查询所有左边资产负债表报表会计科目表记录！");
		}
	}

	/**
	 * 
	 * @Description: 查询所有右边资产负债表报表会计科目表记录
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public List<CusComBalanceSheetMeta> selectRightCusComBalanceSheetMeta() throws ThriftServiceException, TException {
		try {
			List<CusComBalanceSheetMeta> dtoList = cusComBalanceSheetMetaMapper.selectRightCusComBalanceSheetMeta();
			return dtoList == null ? new ArrayList<CusComBalanceSheetMeta>() : dtoList;
		} catch (Exception e) {
			logger.error("查询所有右边资产负债表报表会计科目表记录！");
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_BASE_QUERY, "查询所有右边资产负债表报表会计科目表记录！");
		}
	}

	/**
	 * 
	 * @Description: 根据企业财务报表-资产负债表reportId查询记录
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public List<CusComBalanceSheet> selectCusComBalanceSheetByReportId(int reportId) throws ThriftServiceException, TException {
		try {
			List<CusComBalanceSheet> cbsList = cusComBalanceSheetMapper.selectCusComBalanceSheetByReportId(reportId);
			return cbsList == null ? new ArrayList<CusComBalanceSheet>() : cbsList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_ADD, "根据reportid查询资产负债表信息失败！");
		}
	}

	/**
	 * 
	 * @Description: 根据报表年度范围查询CUS_COM_FINANCE_REPORT记录
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public List<CusComFinanceReportOverviewDTO> selectCusComFinanceReportOverviewDTOByAnnual(Map<String, Integer> myMap) throws ThriftServiceException, TException {
		try {
			// Map<String,Integer> map = new HashMap<String,Integer>();
			// map.put("startYear", startYear);
			// map.put("endYear", endYear);
			// map.put("comId", comId);
			List<CusComFinanceReportOverviewDTO> cusComFinanceReportList = cusComFinanceReportMapper.selectCusComFinanceReportOverviewDTOByAnnual(myMap);
			return cusComFinanceReportList == null ? new ArrayList<CusComFinanceReportOverviewDTO>() : cusComFinanceReportList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "根据企业ID(comId)查询CUS_COM_FINANCE_REPORT记录失败！");
		}
	}

	/**
	 * 
	 * @Description: 删除所有已选报表
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public int deleteCusComFinanceReportByPIDUseBatch(List<CusComFinanceReportOverviewDTO> financialReportList) throws TException {

		try {
			return cusComFinanceReportMapper.deleteCusComFinanceReportByPIDUseBatch(financialReportList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "删除所有选择报表行记录失败！");
		}

	}
	/**
	 * 删除财务报表
	 */
	@Override
	public int deleteCusComFinanceReportByPID(int pId) throws ThriftServiceException, TException {
		try {
			return cusComFinanceReportMapper.deleteCusComFinanceReportByPID(pId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "删除单行企业财务报表行记录失败！");
		}
	}
	/**
	 * 更新财务报表
	 */
	@Override
	public int updateCusComIncomeReport(CusComIncomeReport cusComIncomeReport) throws ThriftServiceException, TException {
		try {
			return cusComIncomeReportMapper.updateCusComIncomeReport(cusComIncomeReport);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "更新利润表记录失败！");
		}
	}
	/**
	 * 根据现金流量表ID删除财务报表
	 */
	@Override
	public int deleteCusComIncomeReportByReportID(int reportID) throws ThriftServiceException, TException {
		try {
			return cusComIncomeReportMapper.deleteCusComIncomeReportByReportID(reportID);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "删除利润表记录失败！");
		}
	}
	/**
	 * 更新现金流量表
	 */
	@Override
	public int updateCusComCashFlowReport(CashFlowReport cashFlowReport) throws ThriftServiceException, TException {
		try {
			return cashFlowReportMapper.updateCusComCashFlowReport(cashFlowReport);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "更新现金流量表记录失败！");
		}
	}
	/**
	 * 根据财务报表ID删除现金流量表
	 */
	@Override
	public int deleteCusComCashFlowReportByReportID(int reportID) throws ThriftServiceException, TException {
		try {
			return cashFlowReportMapper.deleteCusComCashFlowReportByReportID(reportID);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "删除现金流量表记录失败！");
		}
	}
	/**
	 * 根据财务报表ID修改现金流量表
	 */
	@Override
	public int updateCusComBalanceSheet(CusComBalanceSheet cusComBalanceSheet) throws ThriftServiceException, TException {
		try {
			return cusComBalanceSheetMapper.updateCusComBalanceSheet(cusComBalanceSheet);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "更新企业财务报表-资产负债表记录失败！");
		}
	}
	/**
	 * 根据财务报表ID删除现金流量表
	 */
	@Override
	public int deleteCusComBalanceSheetByReportID(int reportID) throws ThriftServiceException, TException {
		try {
			return cusComBalanceSheetMapper.deleteCusComBalanceSheetByReportID(reportID);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "删除企业财务报表-资产负债表记录失败！");
		}
	}
	/**
	 * 批量删除企业财务报表
	 */
	@Override
	public int deleteCusComBalanceSheetByReportIDUseBatch(List<CusComFinanceReportOverviewDTO> dtoList) throws TException {
		try {
			return cusComBalanceSheetMapper.deleteCusComBalanceSheetByReportIDUseBatch(dtoList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "批量删除企业财务报表-资产负债表记录失败！");
		}
	}
	/**
	 * 批量删除利润表记录
	 */
	@Override
	public int deleteCusComIncomeReportByReportIDUseBatch(List<CusComFinanceReportOverviewDTO> dtoList) throws TException {
		try {
			return cusComIncomeReportMapper.deleteCusComIncomeReportByReportIDUseBatch(dtoList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "批量删除利润表记录失败！");
		}
	}
	/**
	 * 批量删除现金流量表记录
	 */
	@Override
	public int deleteCusComCashFlowReportByReportIDUseBatch(List<CusComFinanceReportOverviewDTO> dtoList) throws TException {
		try {
			return cashFlowReportMapper.deleteCusComCashFlowReportByReportIDUseBatch(dtoList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "批量删除现金流量表记录失败！");
		}
	}

	/**
	 * 
	 * @Description: 进入编辑资产负债表左边数据时候，查询数据初始化界面
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public List<BalanceSheetEditDTO> initLeftEditCusComBalanceSheetByReportId(int reportId) throws ThriftServiceException, TException {
		try {
			List<BalanceSheetEditDTO> list = cusComBalanceSheetMapper.initLeftEditCusComBalanceSheetByReportId(reportId);
			return list == null ? new ArrayList<BalanceSheetEditDTO>() : list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "初始化编辑左边资产负债表记录失败！");
		}
	}

	/**
	 * 
	 * @Description: 进入编辑资产负债表右边数据时候，查询数据初始化界面
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public List<BalanceSheetEditDTO> initRightEditCusComBalanceSheetByReportId(int reportId) throws ThriftServiceException, TException {
		try {
			List<BalanceSheetEditDTO> list = cusComBalanceSheetMapper.initRightEditCusComBalanceSheetByReportId(reportId);
			return list == null ? new ArrayList<BalanceSheetEditDTO>() : list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "初始化编辑右边资产负债表记录失败！");
		}
	}

	/**
	 * 
	 * @Description: 进入编辑利润表时候，查询数据初始化界面
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public List<CusComIncomeReportEditDTO> initCusComIncomeReportByReportId(int reportId) throws ThriftServiceException, TException {

		try {
			List<CusComIncomeReportEditDTO> dtoList = cusComIncomeReportMapper.initCusComIncomeReportByReportId(reportId);
			return dtoList == null ? new ArrayList<CusComIncomeReportEditDTO>() : dtoList;
		} catch (Exception e) {
			logger.error("初始化利润表记录失败！");
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_BASE_QUERY, "初始化利润表记录失败！");
		}
	}

	/**
	 * 
	 * @Description: 进入编辑现金流量表时候，查询数据初始化界面
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public List<CashFlowReportEditDTO> initCashFlowReportByReportId(int reportId) throws ThriftServiceException, TException {
		try {
			List<CashFlowReportEditDTO> list = cashFlowReportMapper.initCashFlowReportByReportId(reportId);
			return list == null ? new ArrayList<CashFlowReportEditDTO>() : list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "进入编辑现金流量表时候，查询数据初始化界面失败！");
		}
	}

	/**
	 * 
	 * @Description: 进入编辑现金流量补充表时候，查询数据初始化界面
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public List<CashFlowReportEditDTO> initCashFlowReportSupplementByReportId(int reportId) throws ThriftServiceException, TException {
		try {
			List<CashFlowReportEditDTO> list = cashFlowReportMapper.initCashFlowReportSupplementByReportId(reportId);
			return list == null ? new ArrayList<CashFlowReportEditDTO>() : list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "进入编辑现金流量补充表时候，查询数据初始化界面失败！");
		}
	}

	/**
	 * 
	 * @Description: 通过reportId查询现金流量表，在新增时候作为判断使用，如果已经存在
	 *               某个企业某年某月的报表，只能编辑，不能再插入新数据
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public List<CashFlowReport> selectCashFlowReportByReportId(int reportId) throws ThriftServiceException, TException {
		try {
			List<CashFlowReport> list = cashFlowReportMapper.selectCashFlowReportByReportId(reportId);
			return list == null ? new ArrayList<CashFlowReport>() : list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "根据reportId查询现金流量表记录失败！");
		}
	}

	/**
	 * 
	 * @Description: 通过reportId查询现金流量补充表，在新增时候作为判断使用，如果已经存在
	 *               某个企业某年某月的报表，只能编辑，不能再插入新数据
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public List<CashFlowReport> selectCashFlowReportSupplementByReportId(int reportId) throws ThriftServiceException, TException {
		try {
			List<CashFlowReport> list = cashFlowReportMapper.selectCashFlowReportSupplementByReportId(reportId);
			return list == null ? new ArrayList<CashFlowReport>() : list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "根据reportId查询现金流量表记录失败！");
		}
	}

	/**
	 * 
	 * @Description: 根据报表年度范围查询CUS_COM_FINANCE_REPORT记录的总数
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public int obtainCusComFinanceReportOverviewCount(Map<String, Integer> myMap) throws ThriftServiceException, TException {
		try {
			return cusComFinanceReportMapper.obtainCusComFinanceReportOverviewCount(myMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "根据企业ID(comId)查询CUS_COM_FINANCE_REPORT记录总数失败！");
		}
	}
	/**
	 * 据证件类型和证件号码查询
	 */
	@Override
	public int validateCeryNumber(Map<String, String> myMap) throws ThriftServiceException, TException {
		int flag = 0;
		try {
			flag = cusComBaseMapper.getCertTypeOrCertNumber(myMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_DELETE, "根据证件类型和证件号码查询失败！");
		}
		return flag;
	}
	/**
	 * 根据acctId查找个人客户Id
	 */
	@Override
	public int selectPersonIdByAcctId(int acctId) throws ThriftServiceException, TException {
		// 根据acctId查找个人客户Id
		int cusPerId = cusComBaseMapper.selectCusPerIdByCusAcctId(acctId);
		return cusPerId;
	}
	/**
	 * 添加旗下子公司
	 */
	@Override
	public int addUnderCom(List<CusPerCom> lists) throws ThriftServiceException, TException {
		return cusPerComMapper.addUnderCom(lists);
	}
	/**
	 * 根据客户ID 查客户经理
	 */
	@Override
	public CusComBaseDTO selectPmUserIdByAcctId(int acctId) throws ThriftServiceException, TException {
		CusComBaseDTO cusComBaseDTO = new CusComBaseDTO();
		cusComBaseDTO = cusComBaseDTO.setCusAcct((CusAcct) cusAcctMapper.selectPmUserIdByComId(acctId));
		return cusComBaseDTO;
	}

	@Override
	public int getTotals(CusComTeam cusComTeam) throws ThriftServiceException, TException {
		return cusComTeamMapper.getTotal(cusComTeam);
	}

	@Override
	public int getTotal(CusComContact comContact) throws ThriftServiceException, TException {
		return cusComContactMapper.getTotal(comContact);
	}

	@Override
	public int getTotalDept(CusComDebt cusComDebt) throws ThriftServiceException, TException {
		return cusComDebtMapper.getTotalDept(cusComDebt);
	}

	@Override
	public int getTotalDepts(CusComDebt cusComDebt) throws ThriftServiceException, TException {
		return cusComDebtMapper.getTotalDepts(cusComDebt);
	}

	@Override
	public int getTotalAssure(CusComAssure comAssure) throws ThriftServiceException, TException {
		return cusComAssureMapper.getTotalAssure(comAssure);
	}

	@Override
	public int getTotalInvest(CusComInvest cusComInvest) throws ThriftServiceException, TException {
		return cusComInvestMapper.getTotalInvest(cusComInvest);
	}

	@Override
	public int getTotalReward(CusComReward comReward) throws ThriftServiceException, TException {
		return cusComRewardMapper.getTotalReward(comReward);
	}

	@Override
	public int getTotalCusComBases(CusComBase cusComBase) throws ThriftServiceException, TException {
		return cusComBaseMapper.getTotalCusComBases(cusComBase);
	}
    /**
     * 验证营业执照号码
     */
	@Override
	public int validateBusLicCert(Map<String, String> map) throws ThriftServiceException, TException {
		return cusComBaseMapper.validateBusLicCert(map);
	}
	 /**
     * 获取业务往来信息
     */
	@Override
	public List<GridViewDTO> getBusinessAllInfo(QueryPersonDTO queryPersonDTO) throws ThriftServiceException, TException {
		List<GridViewDTO> list =null;
		try {
			queryPersonDTO.setAcctId(0);
			int acctType = queryPersonDTO.getAcctType();
			int businessVar = queryPersonDTO.getBusinessVariet();
			if (acctType == 0) {
				queryPersonDTO.setAcctType(-1);
			}
			if (businessVar == 0) {
				queryPersonDTO.setBusinessVariet(-1);
			}
			list =	cusComBaseMapper.getBusinessAllInfo(queryPersonDTO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_BASE_QUERY, "查询业务往来信息失败！");
		}
		// 手动在查出来的数据前面加0
		for (int i = 0; i < list.size(); i++) {
			String value = list.get(i).getValue7().toString();
			boolean flag = value.startsWith(".");
			if (flag == true) {
				value = new StringBuffer("0").append(value).toString();
				list.get(i).setValue7(value);
			}
		}
		//去重担保方式
		//List<GridViewDTO> list = queryPersonDTO.getResults();
		List<String> tempList = new ArrayList<String>();
		String [] mo = null;
		StringBuffer sb = null;
		for(GridViewDTO viewDto : list){
			if(null!=viewDto.getValue5()){
				sb = new StringBuffer();
				mo = viewDto.getValue5().toString().split(",");
				for(String s : mo){
					if(!tempList.contains(s)){
						tempList.add(s);
						sb.append(s).append(",");
					}
				}
				viewDto.setValue5(sb.toString());
				tempList.clear();
			}
		}
		return list;

	}
	/**
	 * 黑名单用户不能查看详情
	 */
	@Override
	public int validateIsPermissions(Map<String, Integer> myMap) throws ThriftServiceException, TException {
		int flag = 0;
		try {
			flag = cusComBaseMapper.validateIsPermissions(myMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSCOM_REWARD_QUERY, "该黑名单用户不能查看详情！");
		}
		return flag;
	}

	/**
	 * 
	 * @Description: 查询所有企业的法人相关信息
	 * @param cusDTO
	 *            企业法人的DTO
	 * @return 企业法人集合
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月14日 下午5:01:40
	 */
	@Override
	public List<CusDTO> getEnterpriseLegalPersonList(CusDTO cusDTO) throws TException {
		List<CusDTO> list = new ArrayList<CusDTO>();
		try {
			list = cusComBaseMapper.getEnterpriseLegalPersonList(cusDTO);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询所有企业的法人相关信息:" + e.getMessage());
			}
		}
		return list;
	}

	/**
	 * 
	 * @Description: 查询所有的企业法人的数量
	 * @param cusDTO
	 *            企业法人DTO
	 * @return 企业法人的数量
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月14日 下午5:02:17
	 */
	@Override
	public int getEnterpriseLegalPersonCount(CusDTO cusDTO) throws TException {
		int rows = 0;
		try {
			rows = cusComBaseMapper.getEnterpriseLegalPersonCount(cusDTO);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询所有的企业法人的数量:" + e.getMessage());
			}
		}
		return rows;
	}

	@Override
	public List<CusComGuaranteeType> getGuaranteeTypeBycusComBasePid(int cusComBasePid) throws ThriftServiceException, TException {
		return cusComDebtMapper.getGuaranteeTypeBycusComBasePid(cusComBasePid);
	}
	/**
	 * 根据id查企业报表
	 */
	@Override
	public List<GridViewDTO> searchComExportByPid(String pids) throws ThriftServiceException, TException {
		String arry[] = pids.split(",");
		List list = Lists.newArrayList();
		for (int i = 0; i < arry.length; i++) {
			list.add(arry[i]);
		}
		List<GridViewDTO> listResult = cusComBaseMapper.searchComExportByPid(list);
		if(null==listResult){
			return new ArrayList<GridViewDTO>();
		}
		return listResult;
	}
	/**
	 * 查拒贷客户列表
	 */
	@Override
	public String searcherBlackListRefuse(int pid) throws ThriftServiceException, TException {
		return  cusComBaseMapper.searcherBlackListRefuse(pid);
	}
    /**
     * 根据iD导出现金流量表
     */
	@Override
	public List<ExcelCusComIncomeReport> excelCashFlowByReportId(String pids) throws ThriftServiceException, TException {
		String arry[] = pids.split(",");
		List list = Lists.newArrayList();
		for (int i = 0; i < arry.length; i++) {
			list.add(arry[i]);
		}
		List<ExcelCusComIncomeReport> listResult = cusComIncomeReportMapper.excelCashFlowByReportId(list);
		if(null==listResult){
			return new ArrayList<ExcelCusComIncomeReport>();
		}
		return listResult;
	}

	@Override
	public List<ExcelCusComIncomeReport> exportCashFlowMaterialByReportId(String pids) throws ThriftServiceException, TException {
		String arry[] = pids.split(",");
		List list = Lists.newArrayList();
		for (int i = 0; i < arry.length; i++) {
			list.add(arry[i]);
		}
		List<ExcelCusComIncomeReport> listResult = cusComIncomeReportMapper.exportCashFlowMaterialByReportId(list);
		if(null==listResult){
			return new ArrayList<ExcelCusComIncomeReport>();
		}
		return listResult;
	}


	/**
	 * 根据客户ID查客户类型
	 */
	public int searcherCusTypeByAcctId(int pid) throws ThriftServiceException,
			TException {
		return cusComBaseMapper.searcherCusTypeByAcctId(pid);
	}


	/**
	 * 根据客户iD查企业
	 */
	@Override
	public ExportCusComBase selectCompanyByAcctId(int acctId) throws ThriftServiceException, TException {
		ExportCusComBase cusComBase = new ExportCusComBase();
		cusComBase = cusComBaseMapper.selectCompanyByAcctId(acctId);
		return cusComBase == null ? new ExportCusComBase() : cusComBase;
	}

	@Override
	public int getPersonTotal(int acctId) throws ThriftServiceException, TException {
		Integer count = 0;
		count = cusComBaseMapper.getPersonTotal(acctId);
		return count == null ? 0 : count;
	}

	@Override
	public List<ExcelCusComIncomeReport> excelCusComIncomeReportByComId(int comId) throws ThriftServiceException, TException {
		List<ExcelCusComIncomeReport> list = new ArrayList<ExcelCusComIncomeReport>();
		list = cusComBaseMapper.excelCusComIncomeReportByComId(comId);
		return list == null ? new ArrayList<ExcelCusComIncomeReport>() : list;
	}
	/**
	 * 根据企业id查现金流量表
	 */
	@Override
	public List<ExcelCusComBalanceSheet> excelCusComBalanceSheetByComId(int comId) throws ThriftServiceException, TException {
		List<ExcelCusComBalanceSheet> list = new ArrayList<ExcelCusComBalanceSheet>();
		list = cusComBaseMapper.excelCusComBalanceSheetByComId(comId);
		return list == null ? new ArrayList<ExcelCusComBalanceSheet>() : list;
	}
	/**
	 * 根据企业id查客户
	 */
	@Override
	public int getAcctIdByComId(int comId) throws ThriftServiceException, TException {
		Integer pid = cusComBaseMapper.getAcctIdByComId(comId);
		return pid == null ? 0 : pid;
	}
	/**
	 * 查询业务往来总记录数信息
	 */
	 @Override
	 public int getTotalCusAndPerBusiness(QueryPersonDTO queryPersonDTO)
	 throws ThriftServiceException, TException {
		 int total=0;
		 try {
				queryPersonDTO.setAcctId(0);
				int acctType = queryPersonDTO.getAcctType();
				int businessVar = queryPersonDTO.getBusinessVariet();
				if (acctType == 0) {
					queryPersonDTO.setAcctType(-1);
				}
				if (businessVar == 0) {
					queryPersonDTO.setBusinessVariet(-1);
				}
			//	List<GridViewDTO> list =cusComBaseMapper.getBusinessAllInfo(queryPersonDTO);
				total=cusComBaseMapper.getTotalCusAndPerBusiness(queryPersonDTO);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ThriftServiceException(ExceptionCode.CUSCOM_BASE_QUERY, "查询业务往来总记录数信息失败！");
			}
		 
	 return total;
	 }
	 
	 /**
	  * 根据机构ID查询企业信息
	  */
	@Override
	public CusComBase getComBaseByOrgId(int orgId)throws ThriftServiceException, TException {
		CusComBase comBase = new CusComBase();
		if(orgId >0){
			comBase = cusComBaseMapper.getComBaseByOrgId(orgId);
			List<CusComShare> cusComShares = new ArrayList<CusComShare>();
			if(comBase != null && comBase.getPid()>0){
				cusComShares = cusComShareMapper.selectShareById(comBase.getPid());
			}
			comBase.setCusComShares(cusComShares);
		}
		return comBase;
	}

}
