package com.xlkfinance.bms.server.credits.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.beforeloan.CreditsDTO;
import com.xlkfinance.bms.rpc.beforeloan.CreditsLineDTO;



/**
 * 
 * @ClassName: CreditsDTOMapper
 * @Description: 额度管理mapper(针对额度查询列表)
 * @author: Cai.Qing
 * @date: 2015年3月19日 下午5:12:44
 */
@MapperScan("creditsDTOMapper")
public interface CreditsDTOMapper<T, PK> extends BaseMapper<T, PK> {

	

	public List<CreditsLineDTO> getCreditsLineProjectId(int projectId);

	/**
	 * 
	 * @Description: 授信项目额度查询
	 * @param creditsDTO
	 *            授信DTO
	 * @return 授信项目集合
	 * @author: Cai.Qing
	 * @date: 2015年3月19日 下午6:46:43
	 */
	public List<CreditsDTO> getAllCredits(CreditsDTO creditsDTO);

	/**
	 * 
	 * @Description: 获取授信项目信息
	 * @param creditsDTO
	 * @return 获取授信项目信息集合
	 * @author: Rain.Lv
	 * @date: 2015年3月23日 下午3:42:44
	 */
	public List<CreditsDTO> getCreditsInfo(CreditsDTO creditsDTO);

	/**
	 * 
	 * @Description: 查询授信项目额度个数
	 * @param creditsDTO
	 *            授信对象
	 * @return 授信的个数
	 * @author: Cai.Qing
	 * @date: 2015年3月31日 上午11:12:04
	 */
	public int getAllCreditsDTOCount(CreditsDTO creditsDTO);

	/**
	 * 
	 * @Description: 额度提取列表
	 * @param creditsLineDTO
	 * @return
	 * @author: xuweihao
	 * @date: 2015年3月31日 上午11:20:00
	 */
	public List<CreditsLineDTO> getCreditsLine(CreditsLineDTO creditsLineDTO);

	/**
	 * 
	 * @Description: 额度提取总数
	 * @param creditsLineDTO
	 * @return
	 * @author: xuweihao
	 * @date: 2015年3月31日 下午2:15:53
	 */
	public int getCreditsLineTotal(CreditsLineDTO creditsLineDTO);

	/**
	 * 
	 * @Description: 额度提取删除
	 * @param pidArray
	 * @return
	 * @author: xuweihao
	 * @date: 2015年4月3日 下午4:43:06
	 */
	public boolean deleteCreditsLine(int pid);

	/**
	 * 
	 * @Description: 根据授信ID和额度调整类型查询授信使用记录ID
	 * @param creditId
	 *            授信ID
	 * @param creditUseType
	 *            额度调整类型ID
	 * @return 授信使用记录ID
	 * @author: Cai.Qing
	 * @date: 2015年4月13日 上午11:39:17
	 */
	public int getCreditLimitRecordPid(@Param(value = "creditId") int creditId, @Param(value = "creditUseType") int creditUseType);

	/**
	 * 
	 * @Description: 根据项目ID查询授信合同的信息和共同借款人信息
	 * @param projectId
	 *            项目ID
	 * @return 授信合同信息和共同借款人集合
	 * @author: Cai.Qing
	 * @date: 2015年4月22日 下午2:30:34
	 */
	public List<CreditsDTO> getProjectAcctAndPublicManByProjectId(int projectId);
	
	/**
	  * @Description: 查询额度使用记录类型 ， 根据pid
	  * @param pid
	  * @return int (额度调整类型（1=授信、2=使用、3=冻结、4=解冻 、5=还款）)
	  * @author: JingYu.Dai
	  * @date: 2015年4月23日 上午10:01:35
	  */
	public int getCreditLimitTypeByPid(int pid);
	
	/**
	 * 
	  * @Description: TODO
	  * @param loanId
	  * @return
	  * @author: Rain.Lv
	  * @date: 2015年6月6日 下午2:28:21
	 */
	public int getCreditId(int loanId);
	/**
	 * 
	  * @Description: 通过项目ID获取可循环
	  * @param projectId
	  * @return
	  * @author: Rain.Lv
	  * @date: 2015年7月14日 上午10:59:06
	 */
	public int getIsHoopById(int projectId);
}
