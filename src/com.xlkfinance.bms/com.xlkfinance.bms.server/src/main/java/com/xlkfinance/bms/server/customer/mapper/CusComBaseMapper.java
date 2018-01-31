package com.xlkfinance.bms.server.customer.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.customer.CusAcct;
import com.xlkfinance.bms.rpc.customer.CusComBase;
import com.xlkfinance.bms.rpc.customer.CusDTO;
import com.xlkfinance.bms.rpc.customer.CusPerBase;
import com.xlkfinance.bms.rpc.customer.ExcelCusComBalanceSheet;
import com.xlkfinance.bms.rpc.customer.ExcelCusComIncomeReport;
import com.xlkfinance.bms.rpc.customer.ExportCusComBase;
import com.xlkfinance.bms.rpc.customer.QueryPersonDTO;

@MapperScan("cusComBaseMapper")
@SuppressWarnings("rawtypes")
public interface CusComBaseMapper<T, PK> extends BaseMapper<T, PK> {

	/**
	 * 个人客户旗下子公司
	 * 
	 * @param acctId
	 * @return
	 */
	public List<GridViewDTO> getCusUnderCom(CusPerBase cusPerBase);

	/**
	 * @Description: 查询所有用户（带条件）
	 * @param user
	 *            系统用户
	 * @return 系统用户集合
	 * @author: wangheng
	 * @date: 2014年12月20日
	 */
	public List<GridViewDTO> getCusComBases(CusComBase cusComBase);

	public int deleteCusComBase(List pid);

	public int selectCusComBaseById(int acctId);

	public int updateByComOwnId(HashMap<String, Object> map);

	/**
	 * 根据企业ID获取客户ID
	 * 
	 * @param comId
	 * @return
	 */
	public int selectAcctIdByComId(int comId);

	/**
	 * 删除旗下子公司
	 * 
	 * @param pid
	 * @return
	 */
	public int deleteUnderCom(List pid);

	/**
	 * 
	 * @Description: 查询企业信息
	 * @param cusDto
	 *            企业信息DTO
	 * @return 企业列表
	 * @author: Cai.Qing
	 * @date: 2015年1月15日 下午2:21:47
	 */
	public List<CusDTO> getEnterpriseList(CusDTO cusDto);

	/**
	 * 
	 * @Description: 根据当前用户ID查询下面的客户企业信息的总数
	 * @param cusDto
	 *            企业信息DTO
	 * @return 企业的总数
	 * @author: Cai.Qing
	 * @date: 2015年4月28日 上午1:59:30
	 */
	public int getEnterpriseListCount(CusDTO cusDto);

	public int getCertTypeOrCertNumber(Map<String, String> myMap);

	public CusDTO selectByPidPrimaryKey(int pid);

	/**
	 * 
	 * @Description: 根据客户ID查询企业信息
	 * @param acctId
	 *            客户ID
	 * @return 企业信息对象
	 * @author: Cai.Qing
	 * @date: 2015年5月25日 下午2:49:27
	 */
	public CusDTO getCusComByAcctId(int acctId);

	public int selectCusPerIdByCusAcctId(int cusAcctId);

	public int updatePmUserId(CusAcct cusAcct);

	public int getTotalCusComBases(CusComBase cusComBase);

	public int getTotalUnderCom(CusPerBase cusPerBase);

	public int validateBusLicCert(Map<String, String> map);

	public List<GridViewDTO> getBusinessAllInfo(QueryPersonDTO queryPersonDTO);

	public int validateIsPermissions(Map<String, Integer> myMap);

	/**
	 * 
	 * @Description:查询所有企业的法人相关信息
	 * @param cusDTO
	 *            企业对象
	 * @return 企业法人的集合
	 * @author: Cai.Qing
	 * @date: 2015年4月14日 下午4:36:11
	 */
	public List<CusDTO> getEnterpriseLegalPersonList(CusDTO cusDTO);

	/**
	 * 
	 * @Description: 查询所有企业的数量
	 * @param cusDTO
	 *            企业对象
	 * @return 数量
	 * @author: Cai.Qing
	 * @date: 2015年4月14日 下午4:37:04
	 */
	public int getEnterpriseLegalPersonCount(CusDTO cusDTO);

	public List<GridViewDTO> searchComExportByPid(List list);

	public String searcherBlackListRefuse(int pid);

	 public int getTotalCusAndPerBusiness(QueryPersonDTO queryPersonDTO);

	/**
	 * 
	 * @Description: 通过acctId查询公司
	 * @param acctId
	 * @return
	 * @author: xuweihao
	 * @date: 2015年5月19日 下午6:12:37
	 */
	public ExportCusComBase selectCompanyByAcctId(int acctId);

	/**
	 * 
	 * @Description: 查询公司的总人数
	 * @param acctId
	 * @return
	 * @author: xuweihao
	 * @date: 2015年5月20日 上午11:02:22
	 */
	public Integer getPersonTotal(int acctId);

	/**
	 * 
	 * @Description: 通过企业Id查询利润表
	 * @param comId
	 * @return
	 * @author: xuweihao
	 * @date: 2015年5月20日 下午2:51:19
	 */
	public List<ExcelCusComIncomeReport> excelCusComIncomeReportByComId(int comId);

	/**
	 * 
	 * @Description: 通过企业Id查询负债表
	 * @param comId
	 * @return
	 * @author: xuweihao
	 * @date: 2015年5月20日 下午6:08:16
	 */
	public List<ExcelCusComBalanceSheet> excelCusComBalanceSheetByComId(int comId);

	/**
	 * 
	 * @Description: 通过企业Id查询acctId客户Id
	 * @param comId
	 * @return
	 * @author: xuweihao
	 * @date: 2015年5月21日 上午2:51:42
	 */
	public Integer getAcctIdByComId(int comId);
	public int searcherCusTypeByAcctId(int pid);
	
	/**
	 * 根据机构ID查询企业基本信息
	  * @param orgId
	  * @return
	  * @author: baogang
	  * @date: 2016年7月7日 上午10:26:58
	 */
	public CusComBase getComBaseByOrgId(int orgId);

   /**
    *@author:liangyanjun
    *@time:2016年7月28日下午5:09:24
    *@param orgId
    */
   public void deleteByOrgId(int orgId);
}
