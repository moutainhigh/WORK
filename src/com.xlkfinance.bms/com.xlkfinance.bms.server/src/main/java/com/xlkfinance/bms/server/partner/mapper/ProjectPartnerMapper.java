/**
 * @Title: ProjectPartnerMapper.java
 * @Package com.xlkfinance.bms.server.partner.mapper
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: Administrator
 * @date: 2016年3月16日 下午4:19:18
 * @version V1.0
 */
package com.xlkfinance.bms.server.partner.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.customer.CusPerson;
import com.xlkfinance.bms.rpc.inloan.HandleDynamicDTO;
import com.xlkfinance.bms.rpc.partner.ProjectPartner;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerDto;
import com.xlkfinance.bms.rpc.workflow.TaskVo;
/**
 * 合作机构项目Mapper
  * @ClassName: ProjectPartnerMapper
  * @author: Administrator
  * @date: 2016年3月16日 下午4:35:58
 */
@MapperScan("projectPartnerMapper")
public interface ProjectPartnerMapper<T, PK> extends BaseMapper<T, PK>  {

	/**
	 * 查询所有已提交申请的合作机构项目
	  * @param projectPartner
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月17日 下午2:17:49
	 */
	public List<ProjectPartner> findAllProjectPartner(ProjectPartner projectPartner);

	/**
	 * 查询所有已提交申请的合作机构项目总数
	  * @param projectPartner
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月28日 下午7:17:06
	 */
	public int findAllProjectPartnerCount(ProjectPartner projectPartner);
	
	/**
	 * 生成主键
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月28日 下午7:17:31
	 */
	public int getSeqBizProjectPartner();
	
	/**
	 * 查询所有已审批的贷前项目
	  * @param projectPartner
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月17日 下午2:18:09
	 */
	public List<ProjectPartner> findAllProjectInfo(ProjectPartner projectPartner);

	/**
	 * 查询所有已审批的贷前项目总数
	  * @param projectPartner
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月28日 下午7:17:43
	 */
	public int findAllProjectInfoCount(ProjectPartner projectPartner);
	
	/**
	 * 新增合作机构信息
	  * @param projectPartner
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月28日 下午7:16:55
	 */
	public int addProjectPartner(ProjectPartner projectPartner);
	
	/**
	 * 修改合作机构项目信息
	  * @param projectPartner
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月28日 下午7:17:55
	 */
	public int updateProjectPartner(ProjectPartner projectPartner);
	
	/**
	 * 通过项目ID查询申请的项目详情
	  * @param projectPartner
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月17日 下午3:53:54
	 */
	public ProjectPartnerDto findProjectPartner(int projectId);
	
	/**
	 * 查询合作机构项目详情
	  * @param projectPartner
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月17日 下午3:54:45
	 */
	public ProjectPartner findProjectPartnerInfo(ProjectPartner projectPartner);
	/**
	 * 关闭单据
	  * @param loanId
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月25日 下午3:36:16
	 */
	public int updatePartnerForClosed(String loanId);
	
	/**
	 * 修改放款信息
	  * @param loanId
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月25日 下午3:36:39
	 */
	public int updateLoanStatus(ProjectPartner projectPartner);
	/**
	 * 修改还款信息
	  * @param loanId
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月25日 下午3:37:27
	 */
	public int updateRepaymentInfo(ProjectPartner projectPartner);
	
	/**
	 * 修改息费信息
	  * @param loanId
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月25日 下午3:39:25
	 */
	public int updateXiFeeInfo(ProjectPartner projectPartner);
	
	/**
	 * 批量修改息费信息
	 * @param projectPartnerList
	 * @return
	 */
	public int updateXiFeeInfos(List<ProjectPartner> projectPartnerList);
	/**
	 * 查询审核记录列表
	 */
	public List<ProjectPartner> findProjectPartnerInfoList(ProjectPartner projectPartner);
	
	
	/**
	 * 查询机构合作表，并懒加载项目相关数据
	 * @param projectPartner
	 * @return
	 */
	public ProjectPartnerDto findProjectPartnerAndLazy(ProjectPartner projectPartner);
	
	/**
	 * 查询共同借款人
	 * @return
	 */
	public List<CusPerson> findPublicManByProjectId(Integer projectId);
	
	/**
	 * 查询审批工作流记录	
	 * @return
	 */
	public List<TaskVo> findForeLoanWorkFlowByProjectId(Integer projectId);
	
	/**
	 * 查询项目贷中办理动态状态记录
	 * @return
	 */
	public List<HandleDynamicDTO> findInLoanStatusByProjectId(Integer projectId);
	
 
	/**
	 * 查询机构己申请总额
	 * @param projectPartner
	 * @return
	 */
	public double findLoanAmountCountByPartnerNo(ProjectPartner projectPartner);
	
	
	/**
	 * 更新还款金额
	 * @param projectPartner
	 * @return
	 */
	public int updatePartnerRefundLoanMoney(ProjectPartner projectPartner);
	
	
	/**
	 * 批量资金项目信息
	 * @param projectPartnerList
	 * @return
	 */
	public int updateBatchProjectParner(List<ProjectPartner> projectPartnerList);
	
}
