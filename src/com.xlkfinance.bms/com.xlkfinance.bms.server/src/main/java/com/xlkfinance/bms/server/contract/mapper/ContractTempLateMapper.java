/**
 * @ClassName: ContractTempLateMapper
 * @Description: 合同模板管理
 * @author: Chong.Zeng
 * @date: 2015年1月6日 下午3:42:09
 */
package com.xlkfinance.bms.server.contract.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.contract.ContractTempLate;
import com.xlkfinance.bms.rpc.contract.ContractTempLateParm;
import com.xlkfinance.bms.rpc.contract.TempLateParmDto;

@MapperScan("contractTempLateMapper")
public interface ContractTempLateMapper<T, PK> extends BaseMapper<T, PK> {
	/**
	 * @Description: 增加合同模板
	 * @param c
	 * @return
	 * @author: Chong.Zeng
	 * @date: 2015年1月6日 下午5:09:50
	 */
	public boolean addTempLate(ContractTempLate c);

	/**
	 * @Description: 查询所有模板信息
	 * @param c
	 * @return
	 * @author: Chong.Zeng
	 * @date: 2015年1月9日 上午10:11:56
	 */
	public List<ContractTempLate> pageTempLateList(ContractTempLate c);

	/**
	 * 
	 * @Description: 获取总记录数
	 * @param c
	 * @return
	 * @author: Chong.Zeng
	 * @date: 2015年1月9日 上午10:12:32
	 */
	public int pageTempLateCount(ContractTempLate c);

	/**
	 * @Description: 修改合同模板文件存储地址
	 * @param c
	 * @return
	 * @author: Chong.Zeng
	 * @date: 2015年1月9日 上午11:27:28
	 */
	public boolean updateTempLateById(ContractTempLate c);

	/**
	 * @Description: 修改模板状态
	 * @param pid
	 * @return
	 * @author: Chong.Zeng
	 * @date: 2015年1月9日 下午3:59:41
	 */
	public boolean deleteTempLate(int pid);

	/**
	 * @Description: 新增合同模板参数
	 * @param c
	 * @return
	 * @author: Chong.Zeng
	 * @date: 2015年1月10日 上午11:36:36
	 */
	public boolean addTempLateParm(ContractTempLateParm c);

	/**
	 * 
	 * @Description: 修改合同模板参数
	 * @param c
	 * @return
	 * @author: Chong.Zeng
	 * @date: 2015年1月10日 上午11:37:26
	 */
	public boolean updateTempLateParm(ContractTempLateParm c);

	/**
	 * 
	 * @Description: 根据模板ID删除模板匹配符
	 * @param c
	 * @return
	 * @author: Chong.Zeng
	 * @date: 2015年1月12日 下午3:57:42
	 */
	public boolean deleteTempLateParm(Integer pid);

	public boolean deleteTempLateParmByParmId(Integer pid);

	/**
	 * 
	 * @Description: 根据pid查询模板路径
	 * @param pid
	 * @return
	 * @author: Chong.Zeng
	 * @date: 2015年1月12日 下午6:15:35
	 */
	public String srearchTempLateUrl(Integer pid);

	/**
	 * @Description: 根据模板ID查询模板信息
	 * @param t
	 * @return List<TempLateParmDto>
	 * @author: Chong.Zeng
	 * @date: 2015年1月12日 下午6:19:31
	 */
	public List<TempLateParmDto> getTempParmList(TempLateParmDto t);

	/**
	 * @Description: 查询模板参数记录数
	 * @return int
	 * @author: Chong.Zeng
	 * @date: 2015年1月12日 下午7:04:53
	 */
	public int getTempTotaleCount(int contractTemplateId);

	/**
	 * @Description: 修改模板文件参数
	 * @param t
	 * @return boolean
	 * @author: Chong.Zeng
	 * @date: 2015年1月14日 下午1:49:41
	 */
	public boolean updateTempLateParmDto(TempLateParmDto t);

	/**
	 * 
	 * @Description: 根据模板ID查询模板信息
	 * @param pid
	 * @return
	 * @author: Chong.Zeng
	 * @date: 2015年1月29日 下午3:53:07
	 */
	public ContractTempLate getContractTempLateInfoById(int pid);

	/**
	 * 
	 * @Description: 根据合同ID模板ID查询合同的匹配符和参数值
	 * @param t
	 * @return
	 * @author: Chong.Zeng
	 * @date: 2015年2月2日 下午2:34:49
	 */
	public List<TempLateParmDto> getParmValueByCidAndPid(TempLateParmDto t);

	/**
	 * @Description: 根据模板ID查询该模板的所有匹配符
	 * @param tempLateId
	 * @return
	 * @author: Chong.Zeng
	 * @date: 2015年2月3日 下午2:16:12
	 */
	public List<TempLateParmDto> getTempLateParmListTid(int tempLateId);

	/**
	 * @Description: 根据模板ID查询该模板的非表格内的匹配符
	 * @param tempLateId
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2015年4月11日 上午11:30:01
	 */
	public List<TempLateParmDto> getTempLateParmListNotTable(int tempLateId);

	/**
	 * @Description: 根据模板ID查询该模板的表格内的匹配符
	 * @param tempLateId
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2015年4月11日 上午11:30:29
	 */
	public List<TempLateParmDto> getTempLateParmListIsTable(int tempLateId);

	/**
	 * @Description: 删除模板参数中的一个
	 * @param pid
	 * @return
	 * @author: xuweihao
	 * @date: 2015年3月13日 下午1:14:07
	 */
	public boolean delTempLateOneParm(int pid);

	/**
	 * @Description: 查询合同模板取值算法名称
	 * @param pid
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2015年3月21日 下午4:04:48
	 */
	public String getContractTempLateParFunById(int pid);

	/**
	 * 
	 * @Description: 根据合同模版ID查询合同生成规则
	 * @param templateId
	 *            合同模版ID
	 * @return 合同生成规则
	 * @author: Cai.Qing
	 * @date: 2015年5月18日 下午4:37:57
	 */
	public String getContractNumberFunByTemplateId(int templateId);

}
