package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.customer.CusAcct;
import com.xlkfinance.bms.rpc.customer.CusDTO;
import com.xlkfinance.bms.rpc.customer.CusPerBase;
import com.xlkfinance.bms.rpc.customer.CusPerBaseDTO;
import com.xlkfinance.bms.rpc.customer.QueryPersonDTO;

/**
 * 
 * @ClassName: CusPerBaseMapper
 * @Description: 个人信息Mapper 接口
 * @author: Cai.Qing
 * @date: 2015年4月28日 上午12:50:52
 */
@SuppressWarnings("rawtypes")
@MapperScan("cusPerBaseMapper")
public interface CusPerBaseMapper<T, PK> extends BaseMapper<T, PK> {

	public List<GridViewDTO> getCusPerBases(CusPerBaseDTO cusPerBaseDTO);

	public int selectPerIdByAcctId(int acctId);

	/**
	 * 
	 * @Description: 查询个人信息
	 * @param cusDto
	 *            个人信息DTO
	 * @return 个人信息列表
	 * @author: Cai.Qing
	 * @date: 2015年1月15日 上午11:44:01
	 */
	public List<CusDTO> getPersonalList(CusDTO cusDto);

	/**
	 * 
	 * @Description: 查询个人信息Two
	 * @param cusDto
	 *            个人信息DTO
	 * @return 个人信息列表
	 * @author: Cai.Qing
	 * @date: 2015年3月2日 上午11:23:03
	 */
	public List<CusDTO> getPersonalListTwo(CusDTO cusDto);

	/**
	 * 
	 * @Description:根据条件查询贷前申请的总数
	 * @param cusDto
	 *            个人信息DTO
	 * @return 个人信息列表总数
	 * @author: Cai.Qing
	 * @date: 2015年4月28日 上午12:49:21
	 */
	public int getPersonalListTwoCount(CusDTO cusDto);

	/**
	 * 
	 * @Description: 根据pid查询个人信息
	 * @param cusDto
	 *            个人信息DTO
	 * @return 个人信息对象
	 * @author: Hezhiying
	 * @date: 2015年3月13日 上午11:23:03
	 */
	public CusDTO getPersonalListKeyPid(int pid);

	/**
	 * 
	 * @Description: 根据客户ID查询个人信息
	 * @param acctId
	 *            客户ID
	 * @return 个人信息 对象
	 * @author: Cai.Qing
	 * @date: 2015年5月25日 下午2:42:50
	 */
	public CusDTO getPersonalListByAcctId(int acctId);

	/**
	 * 
	 * @Description: 查询是个人客户 还是企业客户
	 * @param pid
	 *            个人信息DTO
	 * @return 个人信息列表
	 * @author: Hezhiying
	 * @date: 2015年3月13日 上午11:23:03
	 */
	public int getByAcctTypeKeyPid(int pid);

	/**
	 * 
	 * @Description: 查询个人非配偶关系人
	 * @param acctId
	 *            客户ID
	 * @return 非配偶关系列表
	 * @author: Cai.Qing
	 * @date: 2015年1月15日 上午11:44:26
	 */
	public List<CusDTO> getNoSpouseList(int acctId);

	public List<CusDTO> getNoSpouseLists(int projectId);

	public List<CusDTO> getNoSpouseListByPid(List list);
	/**
	 * 
	 * @Description: 撤销黑名单-修改客户级别
	 * @param cusPerBae
	 * @return
	 * @author: zhanghg
	 * @date: 2015年2月9日 上午10:34:37
	 */
	public int updateCusLevel(CusPerBase cusPerBae);

	/**
	 * 撤销黑名单-批量修改客户级别
	 * 
	 * @Description: TODO
	 * @param cusPerBaes
	 * @author: zhanghg
	 * @date: 2015年3月3日 下午5:08:51
	 */
	public void updateBatchCusLevel(List<CusPerBase> cusPerBaes);

	public int selectIsAcctManager(int currUserPid);

	public List<CusAcct> getUserName(int projectId);
	
	public List<CusAcct> getUserNames(int cusAcctManagerId);

	public int updatePmUserId(CusAcct cusAcct);

	public List<GridViewDTO> getPerBusiness(QueryPersonDTO queryPersonDTO);

	public int selectCredit(int projectId);

	public int getTotal(CusPerBaseDTO cusPerBaseDTO);

	public List<GridViewDTO> searchPerExportByPid(List list);

	public List<GridViewDTO> listUnderExcelExportList(List list);
	
	public int getPersonalListCount(CusDTO cusDto);
	
	public int selectProjectStatus(int acctID);

	public List<GridViewDTO> getAomCusPerBases(CusPerBaseDTO cusPerBaseDTO);

	public int getAomTotal(CusPerBaseDTO cusPerBaseDTO);
}
