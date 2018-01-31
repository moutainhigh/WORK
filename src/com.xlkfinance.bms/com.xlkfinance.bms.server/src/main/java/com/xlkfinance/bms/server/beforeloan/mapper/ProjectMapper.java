/**
 * @Title: SysUserMapper.java
 * @Package com.xlkfinance.bms.server.system.mapper
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月11日 下午7:22:11
 * @version V1.0
 */
package com.xlkfinance.bms.server.beforeloan.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.beforeloan.AfterLoanArchive;
import com.xlkfinance.bms.rpc.beforeloan.LoanOutputInfo;
import com.xlkfinance.bms.rpc.beforeloan.OrgCooperat;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectDto;
import com.xlkfinance.bms.rpc.beforeloan.ProjectRecord;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.repayment.RegAdvapplyFileview;
import com.xlkfinance.bms.rpc.repayment.RepaymentPlanBaseDTO;
import com.xlkfinance.bms.rpc.repayment.UploadinstAdvapplyBaseDTO;

/**
 * 
 * @Title: ProjectMapper.java
 * @Description: 贷前申请 mapper
 * @author : Mr.Cai
 * @date: 2015年01月08日 下午3:10:38
 * @email: caiqing12110@vip.qq.com
 */
@MapperScan("projectMapper")
public interface ProjectMapper<T, PK> extends BaseMapper<T, PK> {

	/**
	 * 
	 * @Description: 获取当前项目的新项目ID
	 * @return 项目ID
	 * @author: Mr.Cai
	 * @date: 2015年2月10日 上午6:47:56
	 */
	public int getSeqBizProject();

	/**
	 * 
	 * @Description: 条件查询所有的贷前申请
	 * @param loanProject
	 *            贷前申请对象
	 * @return 贷前申请集合
	 * @author: Mr.Cai
	 * @date: 2015年01月08日 下午3:20:38
	 */
	public List<Project> getAllProject(Project project);

	/**
	 * 
	 * @Description: 贷前分页查询 总数
	 * @param loanProject
	 *            贷前申请对象
	 * @return 贷前申请集合
	 * @author: hezhiying
	 * @date: 2015年03月16日 下午3:20:38
	 */
	public int getAllProjectCount(Project project);

	/**
	 * 
	 * @Description: 批量删除贷前申请
	 * @param loanPids
	 *            贷前申请ID数组
	 * @return
	 * @author: Mr.Cai
	 * @date: 2015年01月08日 下午3:25:38
	 */
	public int batchDelete(String[] loanPids);

	/**
	 * 
	 * @Description:查询当前年份的项目编号的最后5位自然数
	 * @return 当前年份的项目编号最大的最后5位自然熟
	 * @author: Cai.Qing
	 * @date: 2015年2月8日 下午6:16:22
	 */
	public String getMaxProjectNumber();

	/**
	 * 
	 * @Description: 查询当前日期的项目名称最大的最后2位自然数
	 * @param acctId
	 *            客户ID
	 * @return 当前客户今天申请的贷款数量
	 * @author: Cai.Qing
	 * @date: 2015年3月30日 下午2:49:09
	 */
	public int getMaxProjectName(int acctId);

	/**
	 * 
	 * @Description: 手动对账-收款信息查询
	 * @param loanId
	 * @return
	 * @author: zhanghg
	 * @date: 2015年1月31日 下午2:35:30
	 */
	public Project getProjectCreLoans(int loanId);

	/**
	 * 
	 * @Description: 手动对账 -对账期数查询
	 * @param loanId
	 * @return
	 * @author: zhanghg
	 * @date: 2015年2月2日 上午9:53:13
	 */
	public List<RepaymentPlanBaseDTO> getNoReconciliations(int loanId);

	/**
	 * @Description: 贷款放款
	 * @author: gaoWen
	 * @param project
	 * @return
	 */
	public List<LoanOutputInfo> getLoadOutputList(Project project);

	/**
	 * 
	 * @Description: 根据项目ID查询项目详细信息
	 * @param projectId
	 *            项目ID
	 * @return 项目对象
	 * @author: Cai.Qing
	 * @date: 2015年2月12日 下午1:45:13
	 */
	public List<Project> getLoanProjectByProjectId(int projectId);

	/**
	 * 
	 * @Description: 根据项目ID查询项目名称和项目编号
	 * @param projectId
	 *            项目id
	 * @return 项目对象
	 * @author: Cai.Qing
	 * @date: 2015年2月12日 下午3:14:51
	 */
	public List<Project> getProjectNameOrNumber(int projectId);

	/**
	 * 
	 * @Description: 修改项目状态
	 * @param projectId
	 *            项目ID
	 * @param status
	 *            状态ID
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2015年3月12日 下午3:29:28
	 */
	public int updateProjectStatusByPrimaryKey(@Param(value = "pid") int pid, @Param(value = "requestStatus") int requestStatus, @Param(value = "endDttm") String EndDttm);

	/**
	 * 
	 * @Description: 条件查询所有的贷后 归档信息
	 * @param loanProject
	 *            贷前申请对象
	 * @return 贷后申请集合
	 * @author: HeZhiYing
	 * @date: 2015年3月11日 下午3:20:38
	 */
	public List<Project> getAfterLoadFileList(Project project);

	/**
	 * 
	 * @Description: 条件查询所有的贷后 归档信息
	 * @param loanProject
	 *            贷前申请对象
	 * @return 贷后申请集合
	 * @author: HeZhiYing
	 * @date: 2015年3月11日 下午3:20:38
	 */
	public int getAfterLoadFileCount(Project project);

	/**
	 * 
	 * @Description: 修改项目归档状态
	 * @return 查询结果
	 * @author: HeZhiying
	 * @date: 2015年3月11日 下午2:26:51
	 */
	public int updateAfterLoanArchive(AfterLoanArchive afterLoanArchive);

	/**
	 * 
	 * @Description: 新增项目归档信息
	 * @param afterLoanArchive
	 * @return
	 * @author: xuweihao
	 * @date: 2015年4月26日 下午7:11:37
	 */
	public int addAfterLoanArchive(AfterLoanArchive afterLoanArchive);

	/**
	 * 
	 * @Description: 查询项目归档资料状态 是否全部是已归档
	 * @return 查询结果
	 * @author: HeZhiying
	 * @date: 2015年3月11日 下午2:26:51
	 */
	public int getProjectFileStatus(int projectId);

	/**
	 * 
	 * @Description: 修改项目调查结论信息
	 * @param project
	 *            项目对象
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2015年3月17日 下午5:34:16
	 */
	public int updateProceduresByPrimaryKey(Project project);

	/**
	 * 
	 * @Description: 展期新增项目
	 * @param project
	 * @return
	 * @author: Rain.Lv
	 * @date: 2015年4月3日 下午2:55:20
	 */
	public int insertProjectInfo(Project project);

	/**
	 * 
	 * @Description: 修改项目名称
	 * @param project
	 * @return
	 * @author: Zhangyu.Hoo
	 * @date: 2015年4月8日 上午10:51:34
	 */
	public int updateProjectName(Project project);

	/**
	 * @Description: 插入file表
	 * @param project
	 * @return
	 * @author: gW
	 * @date: 2015年4月8日 上午10:51:34
	 */
	public int insertFileInfo(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO);

	/**
	 * @Description: 担保基本信息 插入BIZ_PROJECT_ASS_FILE表
	 * @param project
	 * @return int
	 * @author: gW
	 * @date: 2015年4月8日 上午10:51:34
	 */
	public int insertAssBaseFileInfo(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO);

	/**
	 * @Description: 担保基本信息 查询BIZ_PROJECT_ASS_FILE表
	 * @param project
	 * @return List<RegAdvapplyFileview>
	 * @author: gW
	 * @date: 2015年4月8日 上午10:51:34
	 */
	public List<RegAdvapplyFileview> queryAssBaseFile(int baseId);

	/**
	 * 
	 * @Description:添加流程记录
	 * @param projectRecord
	 * @return
	 * @author: xuweihao
	 * @date: 2015年4月22日 下午6:14:09
	 */
	public Integer addProjectRecord(ProjectRecord projectRecord);

	/**
	 * 
	 * @Description: 否决项目
	 * @param projectId
	 * @return
	 * @author: xuweihao
	 * @date: 2015年4月22日 下午9:19:12
	 */
	public Integer updateProjectRejected(int projectId);

	/**
	 * 
	 * @Description: 查询单条贷后信息
	 * @param projectId
	 * @return
	 * @author: xuweihao
	 * @date: 2015年4月26日 下午6:38:39
	 */
	public AfterLoanArchive getAfterLoadFileOne(int projectId);

	/**
	 * 
	 * @Description: 批量设置授信项目为无效
	 * @param pids
	 *            项目ID(1,2,3)
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2015年5月3日 上午9:18:32
	 */
	public int updateProjectInvalid(String[] pids);

	/**
	 * 
	 * @Description: 设置授信项目为有效
	 * @param pid
	 *            项目ID
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2015年5月3日 上午9:19:21
	 */
	public int setProjectEffective(int projectId);

	/**
	 * 
	 * @Description: 根据项目ID查询当前客户是否存在有效的授信项目
	 * @param projectId
	 *            项目ID
	 * @return 0 表示没有,大于0 表示存在
	 * @author: Cai.Qing
	 * @date: 2015年5月3日 上午9:57:54
	 */
	public int getCountProjectCredit(int projectId);

	/**
	 * 
	 * @Description: 根据客户ID查询当前客户是否存在有效的授信项目
	 * @param acctId
	 *            客户ID
	 * @return 0 表示没有, 大于0表示存在
	 * @author: Cai.Qing
	 * @date: 2015年5月3日 上午11:32:41
	 */
	public int getCountProjectCreditByAcctId(int acctId);

	/**
	 * 
	 * @Description: 根据客户ID查询当前客户是否存在正在(申请中 or 审核中 or 已放款 or 已归档)的贷款项目
	 * @param acctId
	 *            客户ID
	 * @return 0 表示没有, 大于0表示存在
	 * @author: Cai.Qing
	 * @date: 2015年6月2日 下午4:11:18
	 */
	public int getCountProjectLoanByAcctId(Project project);

	/**
	 * 
	 * @Description: 通过项目Id查询项目
	 * @param projectId
	 * @return
	 * @author: xuweihao
	 * @date: 2015年5月19日 下午4:29:09
	 */
	public Project getProjectById(int projectId);
	
	/**
	 * 
	  * @Description: 获取授信开始结束时间
	  * @param projectId
	  * @return
	  * @author: Rain.Lv
	  * @date: 2015年5月28日 下午5:36:04
	 */
	public Project getProjectCredtiEndDt(int projectId);
	/**
	 * 
	  * @Description: 通过新项目id获取老项目id
	  * @param projectId
	  * @return
	  * @author: Rain.Lv
	  * @date: 2015年5月29日 上午11:47:04
	 */
	public int getProjectOldProjectId(int projectId);
	
	/**
	 * 
	  * @Description: 获取已过期的授信ID
	  * @param creditEndDt
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年8月10日 下午1:57:39
	 */
	public String getCreaditInvalidIds(@Param("map") Map map);
	
	/**
	 * 
	  * @Description: 检查同一借款人，同金额，同日期放款
	  * @param creditEndDt
	  * @return
	  * @author: yql
	  * @date: 2015年8月17日 下午1:57:39
	 */
	public Project getCheckAcctProject(Project project);


   /**
    *@author:liangyanjun
    *@time:2016年2月3日下午2:11:38
    *@param projectId
    *@return
    */
   public Project getProjectInfoById(int projectId);
   
   /**
    * 赎楼项目查询(分页)
     * @param project
     * @return
     * @author: Administrator
     * @date: 2016年2月15日 下午2:26:50
    */
   public List<GridViewDTO> getAllForeProjects(Project project);
	
   /**
    * 查询赎楼项目总数
     * @param project
     * @return
     * @author: Administrator
     * @date: 2016年2月15日 下午2:50:14
    */
   public int getForeProjectCount(Project project);
   
   /**
    * 项目撤单
    *@author:liangyanjun
    *@time:2016年5月28日上午9:47:24
    *@param project
    *@return
    */
   public int updateProjectChechan(Project project);
   
   /**
    * 批量恢复赎楼项目撤单
     * @param pids
     * @return
     * @author: Administrator
     * @date: 2016年2月17日 下午5:12:57
    */
   public int setProjectChechan(String[] pids);
   /**
    * 修改项目赎楼状态
     * @param pid
     * @param foreclosureStatus1、待客户经理提交2、待部门经理审批3、待业务总监审批4、待审查员审批5、待风控初审6、待风控复审7、待风控终审8、待风控总监审批9、待总经理审批10、已审批11、已放款12、已归档
     * @param EndDttm
     * @return
     * @author: Administrator
     * @date: 2016年2月19日 上午10:27:57
    */
   public int updateProjectForeStatusByPid(@Param(value = "pid") int pid, @Param(value = "foreclosureStatus") int foreclosureStatus, @Param(value = "endDttm") String EndDttm);
   
   /**
    * 通过主键修改项目信息
     * @param project
     * @return
     * @author: Administrator
     * @date: 2016年2月26日 下午4:52:36
    */
   public int updateByPrimaryKey(Project project);
   
   /**
    * 查询项目下拉列表
     * @Description: TODO
     * @param project
     * @return
     * @author: andrew
     * @date: 2016年2月27日 上午12:23:47
    */
   public List<Project> findProjectInfo(Project project);
   
   /**
    * 根据客户ID查询当前客户是否存在正在进行的赎楼贷款项目，以业务办理为节点，业务办理已完成的则可以继续贷款
     * @param project
     * @return
     * @author: Administrator
     * @date: 2016年3月3日 下午5:59:41
    */
   public int getCountForeLoanByAcctId(int acctId);
   
   /**
    * 打印出账单
     * @param projectId
     * @return
     * @author: Administrator
     * @date: 2016年3月8日 下午2:48:18
    */
   public ProjectDto printProjectInfo(int projectId);
   
   /**
    * 修改收件状态
    *@author:liangyanjun
    *@time:2016年3月12日下午11:03:44
    *@param pid
    *@param foreclosureStatus
    *@return
    */
   public int updatecollectFileStatusByPid(@Param(value = "pid") int pid, @Param(value = "collectFileStatus") int collectFileStatus);
   /**
    * 修改退件完结状态
    *@author:liangyanjun
    *@time:2016年5月16日下午4:30:36
    *@param pid
    *@param refundFileStatus
    *@return
    */
   public int updaterefundFileStatusByPid(@Param(value = "pid") int pid, @Param(value = "refundFileStatus") int refundFileStatus);
   /**
    * 修改审查员意见
     * @param pid
     * @param auditorOpinion
     * @return
     * @author: baogang
     * @date: 2016年4月26日 上午11:16:04
    */
   public int updateAuditorOpinionByPid(@Param(value = "pid") int pid, @Param(value = "auditorOpinion") String auditorOpinion);
   
   /**
    * 根据客户Id，贷款金额,手续费金额，贷款天数以及房产地址查询
     * @param acctId
     * @param loanDays
     * @param loanMoney
     * @param houseName
     * @return
     * @author: baogang
     * @date: 2016年4月28日 下午3:32:04
    */
   public int getProjectAcctCount(@Param(value = "acctId") int acctId, @Param(value = "loanDays") int loanDays,@Param(value = "loanMoney") double loanMoney,@Param(value = "guaranteeFee") double guaranteeFee, @Param(value = "houseName") String houseName);

   /**
    * 打印赎楼清单资料
     * @param projectId
     * @return
     * @author: baogang
     * @date: 2016年5月20日 上午11:44:18
    */
   public ProjectDto printForeInfo(int projectId);

   /**
    * 修改解保时间
    *@author:liangyanjun
    *@time:2016年8月12日下午4:03:34
    *@param pid
    *@param cancelGuaranteeDate
    */
   public void updateCancelGuaranteeByPid(@Param(value = "pid")int pid, @Param(value = "cancelGuaranteeDate")String cancelGuaranteeDate);

   /**
    * 万通推送业务给小科
    * @param project
    * @return
    */
   public int updateProjectNeed(Project project);
   /**
    * 修改赎楼贷后监控状态：无异常=1，有异常=2
    *@author:liangyanjun
    *@time:2017年1月16日下午3:25:16
    */
   public void updateForeAfterMonitorStatus(@Param(value = "projectId")int projectId, @Param(value = "foreAfterMonitorStatus")int foreAfterMonitorStatus);
   
   /**
    * 修改赎楼贷后监控状态：新增=1，修改=2
    *@author:jony
    *@time:2017年1月16日下午3:25:16
    */
   public void updateForeAfterReportStatus(@Param(value = "projectId")int projectId, @Param(value = "foreAfterReportStatus")int foreAfterReportStatus);
   

   /**
    * 根据Id查询单笔上限等
    *@author:jony
    *@time:2017年1月16日下午3:25:16
    */
   
   public OrgCooperat getOrgCooperatById(@Param(value = "projectId")int  projectId);
   
   /**
    * 修改赎楼贷后监控状态及监控说明：无异常=1，有异常=2,异常转正常=3
    * 
    *@author:liangyanjun
    *@time:2017年1月16日下午3:25:16
    */
   public void updateMonitorStatusAndReturnNormalRemark(@Param(value = "returnNormalRemark")String returnNormalRemark,@Param(value = "projectId")int projectId, @Param(value = "foreAfterMonitorStatus")int foreAfterMonitorStatus);
   
   /**
    * 根据项目ID查询项目是否处于资金放款申请中或者放款成功
    * @param projectId
    * @return
    */
   public int getPartnerLoanCount(int projectId);
   
   /**
    * *根据项目区域code以及业务来源查询该项目是不是有业务
    * @param 
    * @return
    */
   public List<Project> checkProjectByNoAndCode(Project project);
   /**
    * 修改资金方（房抵贷）
    *@author:liangyanjun
    *@time:2017年12月14日上午9:54:57
    */
   public void updateCapitalNameInfo(Project project);
   
}
