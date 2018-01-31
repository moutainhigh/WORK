/**
 * @Title: LoanExtensionServiceImpl.java
 * @Package com.xlkfinance.bms.server.repayment.service
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Chong.Zeng
 * @date: 2015年1月23日 下午2:36:07
 * @version V1.0
 */
package com.xlkfinance.bms.server.repayment.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.beforeloan.Credit;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.repayment.LoanExtensionBaseDTO;
import com.xlkfinance.bms.rpc.repayment.LoanExtensionBaseView;
import com.xlkfinance.bms.rpc.repayment.LoanExtensionDTO;
import com.xlkfinance.bms.rpc.repayment.LoanExtensionFileDTO;
import com.xlkfinance.bms.rpc.repayment.LoanExtensionFileView;
import com.xlkfinance.bms.rpc.repayment.LoanExtensionService.Iface;
import com.xlkfinance.bms.rpc.repayment.LoanExtensionView;
import com.xlkfinance.bms.rpc.repayment.ProjectExtensionDTO;
import com.xlkfinance.bms.rpc.repayment.ProjectExtensionView;
import com.xlkfinance.bms.rpc.repayment.RepayNoReconciliationDTO;
import com.xlkfinance.bms.rpc.repayment.RepayNoReconciliationView;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.rpc.workflow.WorkflowService;
import com.xlkfinance.bms.server.beforeloan.mapper.CreditMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectMapper;
import com.xlkfinance.bms.server.repayment.mapper.LoanExtensionMapper;
import com.xlkfinance.bms.server.system.mapper.BizFileMapper;

@SuppressWarnings("unchecked")
@Service("loanExtensionService")
@ThriftService(service = "com.xlkfinance.bms.rpc.repayment.LoanExtensionService")
public class LoanExtensionServiceImpl implements Iface {
	private Logger logger = LoggerFactory.getLogger(LoanExtensionServiceImpl.class);
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "loanExtensionMapper")
	private LoanExtensionMapper loanExtensionMapper;
	
	@Resource(name = "workflowServiceImpl")
	private WorkflowService.Iface workflowServiceImpl;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "projectMapper")
	private ProjectMapper projectMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "creditMapper")
	private CreditMapper creditMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "bizFileMapper")
	private BizFileMapper bizFileMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "loanMapper")
	private LoanMapper loanMapper;
	
	/**
	  * @Description: 展期申请查询
	  * @param dto
	  * @return List<ExtensionRequestView>
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月2日 上午9:44:47
	  */
	@Override
	public List<LoanExtensionView> selectLoanExtensionList(LoanExtensionDTO dto){
		List<LoanExtensionView> result =  loanExtensionMapper.selectLoanExtensionList(dto);
		if(null == result){
			result = new ArrayList<LoanExtensionView>();
		}
		return result;
	}

	/**
	  * @Description: 展期申请列表查询
	  * @param dto
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月2日 下午4:05:15
	  */
	@Override
	public List<LoanExtensionBaseView> selectLoanExtensionBaseList(
			LoanExtensionBaseDTO dto) throws ThriftServiceException, TException {
		List<LoanExtensionBaseView> result =  loanExtensionMapper.selectLoanExtensionBaseList(dto);
		if(null == result){
			result = new ArrayList<LoanExtensionBaseView>();
		}
		return result;
	}

	/**
	  * @Description: 获取展期信息
	  * @param projectId
	  * @return pid
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月8日 下午6:05:18
	  */
	@Override
	public List<ProjectExtensionView> getProjectExtensionView(ProjectExtensionDTO dto)
			throws ThriftServiceException, TException {
		List<ProjectExtensionView> result = loanExtensionMapper.getProjectExtensionList(dto);
		if(null == result){
			result = new ArrayList<ProjectExtensionView>();
		}
		return result;
	}

	/**
	  * @Description: 展期期数,展期金额查询
	  * @param dto
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月8日 下午5:20:27
	  */
	@Override
	public List<RepayNoReconciliationView> getRepayNoReconciliationList(
			RepayNoReconciliationDTO dto) throws ThriftServiceException,
			TException {
		List<RepayNoReconciliationView> result = loanExtensionMapper.getRepayNoReconciliationList(dto);
		if(null == result){
			result = new ArrayList<RepayNoReconciliationView>();
		}
		return result;
	}

	/**
	  * @Description: 获取展期信息
	  * @param projectId
	  * @return pid
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月8日 下午6:05:18
	  */
	@Override
	public ProjectExtensionView getByProjectId(int projectId)
			throws ThriftServiceException, TException {
		ProjectExtensionDTO dto = new ProjectExtensionDTO();
		dto.setProjectId(projectId);
		List<ProjectExtensionView> list = loanExtensionMapper.getProjectExtensionList(dto);
		if(null!= list && list.size()>0){
			return list.get(0);
		}
		return new ProjectExtensionView();
	}

	/**
	  * @Description: 根据展期ID获取展期文件列表
	  * @param projectId
	  * @return List<LoanExtensionFileView>
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月9日 下午2:05:32
	  */
	@Override
	public List<LoanExtensionFileView> getExtensionFileList(LoanExtensionFileDTO dto)
			throws ThriftServiceException, TException {
		List<LoanExtensionFileView> result = loanExtensionMapper.getExtensionFileList(dto);
		if(null == result){
			result = new ArrayList<LoanExtensionFileView>();
		}
		return result;
	}

	/**
	  * @Description: 保存文件
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月9日 下午2:05:32
	  */
	@Override
	public int saveExtensionFile(LoanExtensionFileDTO dto,BizFile bizFile)
			throws ThriftServiceException, TException {
		logger.info("******************展期文件保存开始************************");
		// 保存文件信息
		bizFileMapper.saveBizFile(bizFile);
		// 获取文件Id
		dto.setFileId(bizFile.getPid());
		int count = loanExtensionMapper.insertFile(dto);
		logger.info("******************展期文件保存结束************************");
		return count;
	}

	/**
	  * @Description: 更新文件信息
	  * @param dto
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月9日 下午4:43:36
	  */
	@Override
	public int updateExtensionFile(LoanExtensionFileDTO dto)
			throws ThriftServiceException, TException {
		return loanExtensionMapper.updateFile(dto);
	}

	/**
	  * @Description: 删除文件信息
	  * @param dto
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月9日 下午4:43:55
	  */
	@Override
	public int batchDeleteFile(String pid)
			throws ThriftServiceException, TException {
		logger.info("******************展期文件批量删除开始:pid:"+pid+"************************");
		int count = 0 ;
		if(null!=pid){
			count = loanExtensionMapper.batchDeleteFile(pid.split(","));		
		}
		logger.info("******************展期文件批量删除结束:pid:"+pid+"\n deleteCount : "+count+"************************");
		return count;
	}
	
	/**
	 * @Description: 删除展期项目
	 * @param dto
	 * @return
	 * @author: Zhangyu.Hoo
	 * @date: 2015年4月9日 下午4:43:55
	 */
	@Override
	public int batchDelete(String pid,String projectIds) throws ThriftServiceException, TException {
		logger.info("******************展期批量删除开始:pid:"+pid+"\n projectIds :"+projectIds+"************************");
		int count = 0 ;
		try {
			if(null!=pid){
				String [] pids = pid.split(",");
				
				int oldProjectId = -1;
				int projectId = -1;
				ProjectExtensionDTO dto = new ProjectExtensionDTO();
				List<ProjectExtensionView> viewList=null;
				
				// 循环删除流程信息
				for(String projectIdStr : pids){
					if(!"".equals(projectIdStr.trim())){
						// 根据ID查询展期表,获取展期项目id 跟被展期项目ID
						oldProjectId = -1;
						projectId = -1;
						dto.setPid(Integer.parseInt(projectIdStr.trim()));
						viewList = this.getProjectExtensionView(dto);
						
						// 给项目ID,被展期项目ID赋值
						if(null!=viewList && viewList.size()>0 && null!=viewList.get(0)){
							oldProjectId = viewList.get(0).getExtensionProjectId();
							projectId = viewList.get(0).getProjectId();
						}
						updateWorkflowStatus(oldProjectId, 4);
						logger.info("************展期批量删除,删除流程信息开始:pid:"+projectId+"\n projectIds :"+oldProjectId+"***********");
						List<Project> projectList =projectMapper.getLoanProjectByProjectId(projectId);
						if(projectList != null && projectList.size()>0){
							Project project = projectList.get(0);
							if(project.getProjectSource() == 2 && project.getForeclosureStatus() >1){
								workflowServiceImpl.deleteProcessInstance(projectId,oldProjectId,"creditExtensionRequestProcess");
							}else if(project.getProjectSource() == 1 && project.getForeclosureStatus() >1){
								workflowServiceImpl.deleteProcessInstance(projectId,oldProjectId,"foreExtensionRequestProcess");
							}
						}
						logger.info("***********展期批量删除,删除流程信息结束:pid:"+projectId+"\n projectIds :"+oldProjectId+"***********");
					}
				}
				projectMapper.batchDelete(projectIds.split(","));
				count = loanExtensionMapper.batchDelete(pids);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("******************展期批量删除结束:pid:"+pid+"\n projectIds :"+projectIds+"************************");
		return count;
	}

	public int  updateWorkflowStatus(int projectId,int workflowStatus){
		Map<String,Integer> map = new HashMap<String, Integer>();
		Integer loanId = loanMapper.getLoanIdByProjectId(projectId);
		map.put("workflowStatus", workflowStatus);
		map.put("loadId", loanId);
		if(loanId != null && loanId>0){
			return loanMapper.updateWorkflowStatus(map);
		}
		return 0;
	}
	
	/**
	  * @Description: 分页查询总条数
	  * @param dto
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月11日 下午3:15:18
	  */
	@Override
	public int selectLoanExtensionListTotal(LoanExtensionDTO dto)
			throws ThriftServiceException, TException {
		return loanExtensionMapper.selectLoanExtensionListTotal(dto);
	}

	/**
	  * @Description: 分页总条数查询
	  * @param dto
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月11日 下午3:16:12
	  */
	@Override
	public int selectLoanExtensionBaseListTotal(LoanExtensionBaseDTO dto)
			throws ThriftServiceException, TException {
		return loanExtensionMapper.selectLoanExtensionBaseListTotal(dto);
	}

	/**
	  * @Description: 分页总条数
	  * @param dto
	  * @return int
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月14日 上午9:54:41
	 */
	@Override
	public int getExtensionFileListTotal(LoanExtensionFileDTO dto)
			throws ThriftServiceException, TException {
		return loanExtensionMapper.getExtensionFileListTotal(dto);
	}

	/**
	  * @Description: 根据被展期项目id 查询当前在流程中的展期表数据
	  * @param extensionProjectId
	  * @return List<ProjectExtensionView>
	  * @author: Zhangyu.Hoo
	  * @date: 2015年4月9日 下午2:05:06
	  */
	@Override
	public List<ProjectExtensionView>  getByExtensionProjectId(int extensionProjectId)
			throws ThriftServiceException, TException {
		return  loanExtensionMapper.getByExtensionProjectId(extensionProjectId);
	}

	/**
	  * @Description: 获取展期目标历史项目ID
	  * @param projectId
	  * @return hisProjectIds
	  * @author: Zhangyu.Hoo
	  * @date: 2015年5月14日 下午3:39:33
	  */
	@Override
	public String getHisProjectIds(int projectId) throws ThriftServiceException, TException {
		String result = loanExtensionMapper.getHisProjectIds(projectId);
		if(null == result){
			result = "";
		}
		return result;
	}
	
	/**
	 * @Description: 新增尽职调查详细信息时,修改授信信息
	 * @param credit  授信对象
	 * @author: Cai.Qing
	 * @date: 2015年2月10日 下午12:09:27
	 */
	public int updateCreditEndDate(int projectId) throws ThriftServiceException, TException{
		logger.info("******************展期归档,更新授信日期开始:"+projectId+"************************");
		// 获取展期源项目ID
		String oldProjectIds = loanExtensionMapper.getHisProjectIds(projectId);
		if(null!=oldProjectIds && !"".equals(oldProjectIds)){
			String [] oldProjectIdArr = oldProjectIds.split(",");
			String oldProjectId = oldProjectIdArr [oldProjectIdArr.length -1];
			// 查询展期项目信息
			List<Project> extension = projectMapper.getLoanProjectByProjectId(projectId);
			// 查询源项目信息
			List<Project> source = projectMapper.getLoanProjectByProjectId(Integer.parseInt(oldProjectId));
			
			//展	期结束日期大于授信结束日期，展期审核归档流程提交时，把授信项目授信结束日期更改成当前展期项目的展期结束日期 
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date extensionDate = null;
            Date creditDate = null;
			try {
				if(null!=extension && extension.size()>0 && null !=source && source.size()>0){
					extensionDate = df.parse(extension.get(0).getPlanRepayLoanDt());
					creditDate = df.parse(source.get(0).getCredtiEndDt());
				}
			} catch (ParseException e) {
			}
			// 比较日期
			if(null!= extensionDate && null!=creditDate){
				if (extensionDate.getTime() > creditDate.getTime()) {
					Credit credit = new Credit();
					credit.setCredtiEndDt(extension.get(0).getPlanRepayLoanDt());
					credit.setProjectId(Integer.parseInt(oldProjectId));
					creditMapper.updateByProjectId(credit);
					logger.info("******************展期归档,更新授信结束日期成功! updateDate:"+extension.get(0).getPlanOutLoanDt()+", projectId"+oldProjectId+"******************");
	            }
			}
		}
		logger.info("******************展期归档,更新授信日期结束:"+projectId+"******************");
		return 0 ;
	}

	/**
	 * 查询可展期项目列表
	 */
	@Override
	public List<LoanExtensionView> selectForeLoanExtensionList(
			LoanExtensionDTO dto) throws ThriftServiceException, TException {
		List<LoanExtensionView> result =  loanExtensionMapper.selectForeLoanExtensionList(dto);
		if(null == result){
			result = new ArrayList<LoanExtensionView>();
		}
		return result;
	}

	/**
	 * 查询可展期项目总数
	 */
	@Override
	public int selectForeLoanExtensionListTotal(LoanExtensionDTO dto)
			throws ThriftServiceException, TException {
		return loanExtensionMapper.selectForeLoanExtensionListTotal(dto);
	}

	/**
	 * 查询已申请的展期列表
	 */
	@Override
	public List<LoanExtensionBaseView> selectForeLoanExtensionBaseList(
			LoanExtensionBaseDTO dto) throws ThriftServiceException, TException {
		List<LoanExtensionBaseView> result =  loanExtensionMapper.selectForeLoanExtensionBaseList(dto);
		if(null == result){
			result = new ArrayList<LoanExtensionBaseView>();
		}
		return result;
	}

	/**
	 * 查询已展期的项目总数
	 */
	@Override
	public int selectForeLoanExtensionBaseListTotal(LoanExtensionBaseDTO dto)
			throws ThriftServiceException, TException {
	
	return loanExtensionMapper.selectForeLoanExtensionBaseListTotal(dto);
	}

	/**
	 * 通过用户ID查询展期总数
	 */
	@Override
	public int getCountForeExtensionByAcctId(int acctId) throws TException {
		
		return loanExtensionMapper.getCountForeExtensionByAcctId(acctId);
	}

	/**
	 * 根据项目ID查询该项目正在办理中的展期次数
	 */
	@Override
	public int getForeExtensionByPid(int projectId) throws TException {
		return loanExtensionMapper.getForeExtensionByPid(projectId);
	}
}
