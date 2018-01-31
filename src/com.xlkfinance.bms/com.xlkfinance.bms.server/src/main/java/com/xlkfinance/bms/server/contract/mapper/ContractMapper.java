/**
 * @Title: ContractServiceMapper.java
 * @Package com.xlkfinance.bms.server.contract.mapper
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Chong.Zeng
 * @date: 2015年1月14日 下午7:23:35
 * @version V1.0
 */
package com.xlkfinance.bms.server.contract.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.contract.Contract;
import com.xlkfinance.bms.rpc.contract.ContractAccessorie;
import com.xlkfinance.bms.rpc.contract.ContractAttachment;
import com.xlkfinance.bms.rpc.contract.ContractProject;

/**
 * 
 * @ClassName: ContractMapper
 * @Description: 合同信息Mapper
 * @author: Cai.Qing
 * @date: 2015年4月19日 下午4:31:26
 */
@MapperScan("contractMapper")
public interface ContractMapper<T, PK> extends BaseMapper<T, PK> {
	/**
	 * 
	 * @Description: 分页查询所有合同
	 * @param c
	 * @return
	 * @author: Chong.Zeng
	 * @date: 2015年1月14日 下午7:31:04
	 */
	public List<Contract> pageContractList(Contract c);

	/**
	 * 
	 * @Description:查询所有合同记录数
	 * @param c
	 * @return
	 * @author: Chong.Zeng
	 * @date: 2015年1月14日 下午7:31:04
	 */
	public int pageContractCount(Contract c);

	/**
	 * 
	 * @Description: 查询合同项目信息
	 * @param c
	 * @return
	 * @author: Chong.Zeng
	 * @date: 2015年1月19日 上午10:16:07
	 */
	public List<ContractProject> getProJectInfoById(Contract c);

	/**
	 * 
	 * @Description: 查询合同附件信息
	 * @param c
	 * @return
	 * @author: Chong.Zeng
	 * @date: 2015年1月19日 下午5:11:42
	 */
	public List<ContractAccessorie> pageFileAccessorieList(Contract c);

	/**
	 * 
	 * @Description: 删除合同附件
	 * @param c
	 * @return
	 * @author: Chong.Zeng
	 * @date: 2015年1月19日 下午5:23:59
	 */
	public boolean deleteAccessorie(ContractAccessorie c);

	/**
	 * 
	 * @Description: 查询合同附件记录数
	 * @param c
	 * @return
	 * @author: Chong.Zeng
	 * @date: 2015年1月19日 下午5:40:25
	 */
	public int pageAccessorieCount(Contract c);

	/**
	 * 
	 * @Description: 编辑合同
	 * @param map
	 * @return
	 * @author: Chong.Zeng
	 * @date: 2015年2月2日 下午2:44:55
	 */
	public boolean updateContractParamByCidAndTid(Map<String, Object> map);

	/**
	 * 
	 * @Description: 新增合同信息
	 * @param c
	 * @return
	 * @author: zhanghg
	 * @date: 2015年2月9日 下午7:34:05
	 */
	public int addContract(Contract c);

	/**
	 * 
	 * @Description: 获取贷款合同信息 及份数
	 * @param contract
	 * @return
	 * @author: zhanghg
	 * @date: 2015年2月10日 上午11:58:10
	 */
	public List<Contract> getLoanContracts(Contract contract);

	/**
	 * 
	 * @Description: 获取贷款合同信息份数
	 * @param contract
	 * @return
	 * @author: hezhiying
	 * @date: 2015年2月10日 上午11:58:10
	 */
	public int getLoanContractsCount(Contract contract);

	/**
	 * 
	 * @Description:批量删除合同
	 * @param list
	 * @return
	 * @author: zhanghg
	 * @date: 2015年2月10日 下午3:22:32
	 */
	@SuppressWarnings("rawtypes")
	public int deleteContracts(List list);

	/**
	 * @Description: 动态查询合同生成表格所需要的参数
	 * @param parameterTableMap
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2015年3月21日 下午3:35:57
	 */
	public void getContractDynamicTableParameter(Map<String, Object> parameterTableMap);

	/**
	 * @Description: 动态查询借款合同生成所需要的参数
	 * @param parameterMap
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2015年3月21日 下午3:35:57
	 */
	public void getDebtorContractDynamicParameter(Map<String, Object> parameterMap);

	/**
	 * @Description: 动态查询抵押、质押合同生成所需要的参数
	 * @param parameterMap
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2015年3月21日 下午3:35:57
	 */
	public void getMortgagorContractDynamicParameter(Map<String, Object> parameterMap);

	/**
	 * @Description: 动态查询保证合同生成所需要的参数
	 * @param parameterMap
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2015年3月21日 下午3:35:57
	 */
	public void getSuretyContractDynamicParameter(Map<String, Object> parameterMap);

	/**
	 * 
	  * @Description: 动态查询利率变更合同生成所需要的参数
	  * @param parameterMap
	  * @author: zhanghg
	  * @date: 2015年6月18日 下午5:53:43
	 */
	public void getRateChgContractDynamicParameter(Map<String, Object> parameterMap);

	/**
	 * @Description: 动态查询展期合同生成所需要的参数
	 * @param parameterMap
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2015年3月21日 下午3:35:57
	 */
	public void getExtensionContractDynamicParameter(Map<String, Object> parameterMap);

	/**
	 * 
	 * @Description: 查询所有当前项目的合同信息
	 * @param projectId
	 *            项目ID
	 * @return 当前项目下的合同集合
	 * @author: Cai.Qing
	 * @date: 2015年4月18日 上午11:47:52
	 */
	public List<Contract> getAllContractListByProjectId(Integer projectId);

	/**
	 * 
	 * @Description: 修改合同编号
	 * @param contract
	 *            合同对象
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2015年4月18日 上午11:48:31
	 */
	public int updateContractNoByContractId(@Param(value = "pid") Integer pid, @Param(value = "contractNo") String contractNo);

	/**
	 * 
	 * @Description: 获取所有父合同
	 * @param projectId
	 * @return
	 * @author: zhanghg
	 * @date: 2015年4月23日 上午10:20:52
	 */
	public List<Contract> getParentContracts(int projectId);

	/**
	 * 
	 * @Description: 获取授信合同的数量
	 * @param projectId
	 * @return
	 * @author: zhanghg
	 * @date: 2015年5月12日 上午11:05:34
	 */
	public int getCreditContractCount(Map<String, Integer> map);
	
	/**
	 * 
	  * @Description: 获取借款合同数量
	  * @param map
	  * @return
	  * @author: zhanghg
	  * @date: 2015年5月25日 上午10:52:11
	 */
	public int getJkContractCount(Map<String, Integer> map);
	
	/**
	 * 
	 * @Description: 获取展期合同数量
	 * @param map
	 * @return
	 * @author: zhanghg
	 * @date: 2015年5月25日 上午10:52:11
	 */
	public int getZkContractCount(Map<String, Integer> map);

	/**
	 * 
	 * @Description: 根据合同ID查询合同信息
	 * @param contractId
	 *            合同ID
	 * @return 合同对象
	 * @author: Cai.Qing
	 * @date: 2015年5月18日 下午4:11:56
	 */
	public Contract getContractByContractId(int contractId);

	/**
	 * 
	 * @Description: 获取合同附件信息
	 * @param contractId
	 * @return
	 * @author: xuweihao
	 * @date: 2015年5月16日 上午11:13:16
	 */
	public ContractAttachment getContractAttachment(int contractId);

	/**
	 * 
	 * @Description: 新增合同附件
	 * @param contractAttachment
	 * @return
	 * @author: xuweihao
	 * @date: 2015年5月16日 下午3:13:40
	 */
	public int addContractAttachment(ContractAttachment contractAttachment);

	/**
	 * 
	 * @Description: 更新合同附件
	 * @param contractAttachment
	 * @return
	 * @author: xuweihao
	 * @date: 2015年5月16日 下午3:13:54
	 */
	public int editContractAttachment(ContractAttachment contractAttachment);

	/**
	 * 
	 * @Description: 新增合同附件资料
	 * @param accessorie
	 * @return
	 * @author: xuweihao
	 * @date: 2015年5月16日 下午4:35:10
	 */
	public int addAccessorie(ContractAccessorie accessorie);

	/**
	 * 
	 * @Description: 更新合同附件资料
	 * @param accessorie
	 * @return
	 * @author: xuweihao
	 * @date: 2015年5月16日 下午4:35:39
	 */
	public int editAccessorie(ContractAccessorie accessorie);
	
	/**
	 * 
	  * @Description: 修改合同文件地址名称
	  * @param contract
	  * @return
	  * @author: zhanghg
	  * @date: 2015年5月23日 下午4:39:18
	 */
	public int updateContractUrlOrName(Contract contract);
	
	/**
	 * 
	  * @Description: 获取子合同的合同名称
	  * @param contractId
	  * @return
	  * @author: zhanghg
	  * @date: 2015年5月25日 下午3:53:59
	 */
	public String getChildContract(int contractId);
	
	/**
	 * 
	  * @Description: 合同生成编号
	  * @param map
	  * @return
	  * @author: zhanghg
	  * @date: 2015年5月29日 下午5:11:08
	 */
	public List<Contract> getContractGenerateNumber(Map<String,Object> map);
	
	/**
	 * 
	  * @Description: 获取展期合同编号
	  * @param projectId
	  * @return String
	  * @author: Zhangyu.Hoo
	  * @date: 2015年6月1日 下午7:30:57
	 */
	public String getExtensionContractNum(int projectId);
}
