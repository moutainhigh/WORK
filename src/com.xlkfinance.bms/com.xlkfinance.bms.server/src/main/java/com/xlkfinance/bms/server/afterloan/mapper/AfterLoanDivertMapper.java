/**
 * @Title: AfterLoanDivertMapper.java
 * @Package com.xlkfinance.bms.server.afterloan.mapper
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: gW
 * @date: 2015年3月31日 下午6:40:05
 * @version V1.0
 */
package com.xlkfinance.bms.server.afterloan.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.afterloan.LoanDivertinfo;
import com.xlkfinance.bms.rpc.repayment.RegAdvapplyFileview;
import com.xlkfinance.bms.rpc.repayment.UploadinstAdvapplyBaseDTO;

@MapperScan("afterLoanDivertMapper")
public interface AfterLoanDivertMapper <T, PK> extends BaseMapper<T, PK> {
	/**
	 * 保存挪用处理info
	 */
	int insertLoanDivertService(LoanDivertinfo loanDivertinfo);
	/**
	 * 查询挪用处理info
	 */
	List<LoanDivertinfo> queryLoanDivertbyDivertId(int divertId);
	/**
	 * 更新挪用处理info
	 */
	int updateLoanDivertinfo(LoanDivertinfo loanDivertinfo);
	/**
	  * @Description: 插入挪用处理上传文件
	  * @param uploadinstAdvapplyBaseDTO
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:33:00
	 */
	int insertDvertfileInfo(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO);
	/**
	  * @Description: 插入挪用处理上传文件信息
	  * @param uploadinstAdvapplyBaseDTO
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:32:41
	 */
	int insertLoanDvertfileInfo(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO);
	/**
	  * @Description: 查询挪用处理上传文件信息
	  * @param divertId
	  * @return List<RegAdvapplyFileview>
	  * @author: JingYu.Dai
	  * @date: 2015年8月3日 下午3:34:30
	  */
	List<RegAdvapplyFileview> queryRegDivertapplyFile(int divertId);
	/**
	 *查询用处理项目info根据挪用id
	 */
	List<LoanDivertinfo> queryProjectDivertbyDivertId(int divertId);
	/**
	 * 删除挪用处理info
	 */
	int delectDivertbyId(int divertId);
	/**
	 *改变挪用的申请状态
	 */
	int changeReqstDivert(LoanDivertinfo loanDivertinfo);
	
	List<LoanDivertinfo> queryDivertinfobyprojectId(int projectId);
	
	/**
	  * @Description: 修改挪用资料文件描述信息
	  * @param uploadinstAdvapplyBaseDTO
	  * @return int
	  * @author: Zhangyu.Hoo
	  * @date: 2015年7月7日 下午4:15:31
	  */
	public int updateEmbezzleFile(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO) ;
}
