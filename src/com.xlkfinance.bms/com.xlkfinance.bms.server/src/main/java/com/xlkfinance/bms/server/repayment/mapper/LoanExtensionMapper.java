/**
 * @Title: SysLoanExtensionMapper.java
 * @Package com.xlkfinance.bms.server.repayment.mapper
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Chong.Zeng
 * @date: 2015年1月23日 下午2:37:37
 * @version V1.0
 */
package com.xlkfinance.bms.server.repayment.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.repayment.LoanExtensionBaseDTO;
import com.xlkfinance.bms.rpc.repayment.LoanExtensionBaseView;
import com.xlkfinance.bms.rpc.repayment.LoanExtensionDTO;
import com.xlkfinance.bms.rpc.repayment.LoanExtensionFileDTO;
import com.xlkfinance.bms.rpc.repayment.LoanExtensionFileView;
import com.xlkfinance.bms.rpc.repayment.LoanExtensionView;
import com.xlkfinance.bms.rpc.repayment.ProjectExtensionDTO;
import com.xlkfinance.bms.rpc.repayment.ProjectExtensionView;
import com.xlkfinance.bms.rpc.repayment.RepayNoReconciliationDTO;
import com.xlkfinance.bms.rpc.repayment.RepayNoReconciliationView;

@MapperScan("loanExtensionMapper")
public interface LoanExtensionMapper<T, PK> extends BaseMapper<T, PK> {
	
	/**
	  * @Description: 展期申请查询
	  * @param dto
	  * @return List<ExtensionRequestView>
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月2日 上午9:44:47
	  */
	public List<LoanExtensionView> selectLoanExtensionList(LoanExtensionDTO dto);
	
	/**
	  * @Description: 分页查询总条数
	  * @param dto
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月11日 下午3:15:18
	  */
	public int selectLoanExtensionListTotal(LoanExtensionDTO dto);
	
	/**
	  * @Description: 展期申请列表查询
	  * @param dto
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月2日 下午4:05:15
	  */
	public List<LoanExtensionBaseView> selectLoanExtensionBaseList(LoanExtensionBaseDTO dto);
	
	/**
	  * @Description: 分页总条数查询
	  * @param dto
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月11日 下午3:16:12
	  */
	public int selectLoanExtensionBaseListTotal(LoanExtensionBaseDTO dto);
	
	/**
	  * @Description: 展期期数,展期金额查询
	  * @param dto
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月8日 下午5:20:27
	  */
	public List<RepayNoReconciliationView> getRepayNoReconciliationList(RepayNoReconciliationDTO dto);

	/**
	  * @Description: 获取展期信息
	  * @param projectId
	  * @return pid
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月8日 下午6:05:18
	  */
	public List<ProjectExtensionView> getProjectExtensionList(ProjectExtensionDTO dto);
	
	/**
	 * 
	  * @Description: 根据项目ID查询展期信息
	  * @param projectId
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月9日 下午2:05:06
	 */
	public List<ProjectExtensionView> getByProjectId(int projectId);
	
	/**
	  * @Description: 根据被展期项目id 查询当前在流程中的展期表数据
	  * @param extensionProjectId
	  * @return List<ProjectExtensionView>
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月9日 下午2:05:06
	  */
	public List<ProjectExtensionView> getByExtensionProjectId(int extensionProjectId);
	
	/**
	  * @Description: 根据展期ID获取展期文件列表
	  * @param projectId
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月9日 下午2:05:32
	  */
	public List<LoanExtensionFileView> getExtensionFileList(LoanExtensionFileDTO dto);
	
	/**
	  * @Description: 分页总条数
	  * @param dto
	  * @return int
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月14日 上午9:54:41
	 */
	public int getExtensionFileListTotal(LoanExtensionFileDTO dto);
	
	/**
	 * 
	  * @Description: 保存文件信息
	  * @param dto
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月9日 下午4:43:32
	 */
	public int insertFile(LoanExtensionFileDTO dto);

	/**
	  * @Description: 更新文件信息
	  * @param dto
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月9日 下午4:43:36
	  */
	public int updateFile(LoanExtensionFileDTO dto);
	
	/**
	  * @Description: 删除文件信息
	  * @param dto
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月9日 下午4:43:55
	  */
	public int batchDeleteFile(String [] pids);
	
	/**
	 * @Description: 删除展期项目
	 * @param dto
	 * @return
	 * @author: Zhangyu.Hoo
	 * @date: 2015年4月9日 下午4:43:55
	 */
	public int batchDelete(String [] pids);
	
	/**
	  * @Description: 获取展期目标历史项目ID
	  * @param projectId
	  * @return hisProjectIds
	  * @author: Zhangyu.Hoo
	  * @date: 2015年5月14日 下午3:39:33
	  */
	public String getHisProjectIds(int projectId);
	
	/**
	 * 
	  * @Description: 根据项目id查询被展期数
	  * @param projectId
	  * @return
	  * @author: zhanghg
	  * @date: 2015年5月18日 上午11:42:55
	 */
	public int getCycNumByProjectId(int projectId);

	/**
	 * 赎楼展期查询
	  * @param dto
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月1日 下午6:42:44
	 */
	public List<LoanExtensionView> selectForeLoanExtensionList(LoanExtensionDTO dto);
	/**
	 * 赎楼展期总数
	  * @param dto
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月1日 下午6:45:43
	 */
	public int selectForeLoanExtensionListTotal(LoanExtensionDTO dto);

	/**
	 * 赎楼展期申请总数
	  * @param dto
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月1日 下午6:46:01
	 */
	public int selectForeLoanExtensionBaseListTotal(LoanExtensionBaseDTO dto);

	/**
	 * 赎楼展期申请查询
	  * @param dto
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月1日 下午6:46:17
	 */
	public List<LoanExtensionBaseView> selectForeLoanExtensionBaseList(
			LoanExtensionBaseDTO dto);
	/**
	 * 根据客户ID查询当前客户是否存在正在进行的展期申请 
	  * @param projectId
	  * @return
	  * @author: Administrator
	  * @date: 2016年4月7日 上午10:05:04
	 */
	public int getCountForeExtensionByAcctId(int acctId);
	
	/**
	 * 根据项目ID查询该项目正在办理中的展期次数
	 * @param projectId
	 * @return
	 */
	public int getForeExtensionByPid(int projectId);
}
