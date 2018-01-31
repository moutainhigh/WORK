/**
 * @Title: MisapprProcessMapper.java
 * @Package com.xlkfinance.bms.server.afterloan.mapper
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Heng.Wang
 * @date: 2015年1月31日 下午3:28:23
 * @version V1.0
 */
package com.xlkfinance.bms.server.afterloan.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.afterloan.BadDebtBean;
import com.xlkfinance.bms.rpc.afterloan.BadDebtBeas;
import com.xlkfinance.bms.rpc.afterloan.BadDebtBeasCriteria;
import com.xlkfinance.bms.rpc.afterloan.BizProjectViolation;
import com.xlkfinance.bms.rpc.afterloan.BreachDisposeBeas;
import com.xlkfinance.bms.rpc.afterloan.BreachDisposeCriteria;
import com.xlkfinance.bms.rpc.afterloan.LoanProRecCondition;
import com.xlkfinance.bms.rpc.afterloan.LoanProReconciliationDtlView;
import com.xlkfinance.bms.rpc.afterloan.MisapprProcess;
import com.xlkfinance.bms.rpc.afterloan.MisapprProcessCriteria;
import com.xlkfinance.bms.rpc.file.FileInfo;
import com.xlkfinance.bms.rpc.repayment.RegAdvapplyFileview;
import com.xlkfinance.bms.rpc.repayment.UploadinstAdvapplyBaseDTO;

@MapperScan("misapprProcessMapper")
public interface MisapprProcessMapper<T, PK> extends BaseMapper<T, PK>  {
	FileInfo getBizProjectViolationFile(int pid);
	int editFileAndViolation(FileInfo fileInfo);
	boolean deleteFile(int array);
	boolean deleteFileAndViolationFile(int array);
	boolean deleteFileAndViolation(int array);
	int createFile(FileInfo fileInfo);
	int createProjectViolationFile(FileInfo fileInfo);
	int getFileDataTotal(FileInfo fileInfo);
	List<FileInfo> getFileDataList(FileInfo fileInfo);
	BizProjectViolation searchBizProjectViolation(int pid);
	int updateBizProjectViolation(BizProjectViolation bizProjectViolation);
	int createBizProjectViolation(BizProjectViolation bizProjectViolation);
	int getLoanId(int pid);
	
	List<MisapprProcess> getMisappProcess(MisapprProcessCriteria misapprProcessCriteria);
	public int getMisappProcessCount(MisapprProcessCriteria misapprProcessCriteria);
	
	List<BreachDisposeBeas> getBreachDispose(BreachDisposeCriteria breachDisposeCriteria);
	
	int getBreachDisposeTotal(BreachDisposeCriteria breachDisposeCriteria);
	
	// modify by qcxian start =========
	List<BadDebtBeas> getBadDebt(BadDebtBeasCriteria badDebtBeasCriteria);
	
	BadDebtBean getBadDebtBean(int pid);
	int updateBadDebtBean(BadDebtBean badDebtBean); 
	
	// 根据主键获取违约对象
	BizProjectViolation getBizProjectViolationById(int pid);
	// modify by qcxian end =========
	int createBadDebtBean(BadDebtBean badDebtBean);
	// 更新状态
	int updateBadDebtBeanStatus(BadDebtBean badDebtBean);
	// 更新状态
	int deleteBadDebtBeanById(int pid);
	/**
	  * @Description: 删除违约处理数据  根据pid
	  * @param pid 
	  * @return int 删除的记录条数
	  * @author: JingYu.Dai
	  * @date: 2015年5月14日 下午5:41:28
	 */
	int deleteBreachDispose(int pid);
	
	/**
	  * @Description: 坏账上传文件
	  * @param uploadinstAdvapplyBaseDTO
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年8月4日 上午9:31:04
	 */
	int insertFileInfo(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO);
	/**
	  * @Description: 坏账上传文件关联
	  * @param uploadinstAdvapplyBaseDTO
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年8月4日 上午9:33:41
	  */
	int insertBaddealFileInfo(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO);
	/**
	  * @Description: 查询坏账上传文件
	  * @param badId
	  * @return  List<RegAdvapplyFileview>
	  * @author: JingYu.Dai
	  * @date: 2015年8月4日 上午9:44:44
	 */
	List<RegAdvapplyFileview> queryBaddealFile(int badId);
	
	/**
	  * @Description: TODO
	  * @param loanProRecCondition
	  * @return
	  * @author: JingYu.Dai
	  * @date: 2015年8月4日 上午9:45:04
	 */
	List<LoanProReconciliationDtlView> getProjectReconciliationList(LoanProRecCondition loanProRecCondition);
	// add by yql end 
	//修改违约申请状态
	int changeReqstViolation(BizProjectViolation bizProjectViolation);
	// 激活违约对应的呆账坏账记录
	int actionViolationBadBebStatus(int pid);
	public int searchAcctIdByProjectId(int projectId);
	
	/**
	 * 
	  * @Description: 查询违约信息  客户是否加入黑名单
	  * @param violationId
	  * @return
	  * @author: zhanghg
	  * @date: 2015年7月7日 下午6:07:04
	 */
	public int getViolationIsBacklist(int violationId);
}
