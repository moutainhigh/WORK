/**
 * @Title: SysConfigServiceImpl.java
 * @Package com.xlkfinance.bms.server.system.service
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月15日 下午4:52:19
 * @version V1.0
 */
package com.xlkfinance.bms.server.beforeloan.service;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import org.springframework.transaction.annotation.Transactional;

import com.achievo.framework.annotation.ThriftService;
import com.achievo.framework.util.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qfang.xk.aom.rpc.fee.OrgFeeSettleDetail;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.constant.BizDynamicConstant;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.NumberUtils;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.AfterLoanArchive;
import com.xlkfinance.bms.rpc.beforeloan.BizOriginalLoan;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstate;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectRetreat;
import com.xlkfinance.bms.rpc.beforeloan.CalcOperDto;
import com.xlkfinance.bms.rpc.beforeloan.Credit;
import com.xlkfinance.bms.rpc.beforeloan.DataInfo;
import com.xlkfinance.bms.rpc.beforeloan.Loan;
import com.xlkfinance.bms.rpc.beforeloan.OrgCooperat;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectDto;
import com.xlkfinance.bms.rpc.beforeloan.ProjectForeInformation;
import com.xlkfinance.bms.rpc.beforeloan.ProjectForeclosure;
import com.xlkfinance.bms.rpc.beforeloan.ProjectGuarantee;
import com.xlkfinance.bms.rpc.beforeloan.ProjectGuaranteeType;
import com.xlkfinance.bms.rpc.beforeloan.ProjectProperty;
import com.xlkfinance.bms.rpc.beforeloan.ProjectPublicMan;
import com.xlkfinance.bms.rpc.beforeloan.ProjectRecord;
import com.xlkfinance.bms.rpc.beforeloan.ProjectRelation;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService.Iface;
import com.xlkfinance.bms.rpc.beforeloan.ProjectSurveyReport;
import com.xlkfinance.bms.rpc.beforeloan.ProjectSurveyReportService;
import com.xlkfinance.bms.rpc.beforeloan.RepaymentLoanInfo;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.customer.CusAcct;
import com.xlkfinance.bms.rpc.customer.CusDTO;
import com.xlkfinance.bms.rpc.inloan.ApplyFinanceHandleDTO;
import com.xlkfinance.bms.rpc.inloan.ApplyHandleInfoDTO;
import com.xlkfinance.bms.rpc.inloan.BizCapitalSelRecord;
import com.xlkfinance.bms.rpc.inloan.BizDynamic;
import com.xlkfinance.bms.rpc.inloan.BizDynamicService;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleDTO;
import com.xlkfinance.bms.rpc.inloan.RefundFeeDTO;
import com.xlkfinance.bms.rpc.inloan.RepaymentDTO;
import com.xlkfinance.bms.rpc.repayment.ProjectExtension;
import com.xlkfinance.bms.rpc.repayment.ProjectExtensionDTO;
import com.xlkfinance.bms.rpc.repayment.ProjectExtensionView;
import com.xlkfinance.bms.rpc.repayment.RepaymentPlanBaseDTO;
import com.xlkfinance.bms.rpc.system.SysLookupVal;
import com.xlkfinance.bms.rpc.workflow.WorkflowService;
import com.xlkfinance.bms.server.aom.fee.mapper.OrgFeeSettleDetailMapper;
import com.xlkfinance.bms.server.beforeloan.calc.CalPlanLineFactory;
import com.xlkfinance.bms.server.beforeloan.mapper.BizOriginalLoanMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.BizProjectEstateMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.BizProjectRetreatMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.CreditMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.DataUploadMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanRepaymentPlanMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanRepaymentPlanMidMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectAssureMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectForeInformationMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectForeclosureMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectGuaranteeMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectGuaranteeTypeMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectPropertyMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectPublicManMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectRelationMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectSurveyReportMapper;
import com.xlkfinance.bms.server.credits.mapper.CreditsDTOMapper;
import com.xlkfinance.bms.server.customer.mapper.CusAcctMapper;
import com.xlkfinance.bms.server.customer.mapper.CusPerBaseMapper;
import com.xlkfinance.bms.server.finance.mapper.FinanceTransactionMapper;
import com.xlkfinance.bms.server.inloan.mapper.BizCapitalSelRecordMapper;
import com.xlkfinance.bms.server.inloan.mapper.BizHandleMapper;
import com.xlkfinance.bms.server.inloan.mapper.FinanceHandleMapper;
import com.xlkfinance.bms.server.inloan.mapper.ForeclosureRepaymentMapper;
import com.xlkfinance.bms.server.inloan.mapper.RefundFeeMapper;
import com.xlkfinance.bms.server.mortgage.mapper.ProjectAssBaseMapper;
import com.xlkfinance.bms.server.product.mapper.ProductMapper;
import com.xlkfinance.bms.server.repayment.calc.CalPlanLine;
import com.xlkfinance.bms.server.repayment.mapper.LoanExtensionMapper;
import com.xlkfinance.bms.server.repayment.mapper.RepaymentMapper;
import com.xlkfinance.bms.server.system.mapper.SysConfigMapper;
import com.xlkfinance.bms.server.system.mapper.SysLookupValMapper;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDispose;
import com.xlkfinance.bms.server.workflow.mapper.WorkflowProjectMapper;

/**
 * 
 * @ClassName: ProjectServiceImpl
 * @Description: 项目 servicre 实现类
 * @author: Cai.Qing
 * @date: 2015年4月27日 下午10:47:11
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("projectServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.beforeloan.ProjectService")
public class ProjectServiceImpl extends WorkflowSpecialDispose implements Iface {
	private Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

	@Resource(name = "calPlanLineFactory")
	private CalPlanLineFactory calPlanLineFactory;

	@Resource(name = "repaymentCalPlanLineFactory")
	private com.xlkfinance.bms.server.repayment.calc.CalPlanLineFactory repaymentCalPlanLineFactory;

	@Resource(name = "projectRelationMapper")
	private ProjectRelationMapper projectRelationMapper;

	@Resource(name = "projectMapper")
	private ProjectMapper projectMapper;

	@Resource(name = "loanRepaymentPlanMidMapper")
	private LoanRepaymentPlanMidMapper loanRepaymentPlanMidMapper;

	@Resource(name = "loanRepaymentPlanMapper")
	private LoanRepaymentPlanMapper loanRepaymentPlanMapper;

	@Resource(name = "creditMapper")
	private CreditMapper creditMapper;

	@Resource(name = "loanMapper")
	private LoanMapper loanMapper;

	@Resource(name = "projectPublicManMapper")
	private ProjectPublicManMapper projectPublicManMapper;

	@Resource(name = "repaymentMapper")
	private RepaymentMapper repaymentMapper;

	@Resource(name = "loanExtensionMapper")
	private LoanExtensionMapper loanExtensionMapper;

	@Resource(name = "projectGuaranteeTypeMapper")
	private ProjectGuaranteeTypeMapper projectGuaranteeTypeMapper;

	@Resource(name = "projectAssBaseMapper")
	private ProjectAssBaseMapper projectAssBaseMapper;

	@Resource(name = "projectAssureMapper")
	private ProjectAssureMapper projectAssureMapper;

	@Resource(name = "sysConfigMapper")
	private SysConfigMapper sysConfigMapper;
	
	@Resource(name = "financeTransactionMapper")
	private FinanceTransactionMapper financeTransactionMapper;
	
	@Resource(name = "cusPerBaseMapper")
	private CusPerBaseMapper cusPerBaseMapper;

	@Resource(name="projectGuaranteeMapper")
	private ProjectGuaranteeMapper projectGuaranteeMapper;
	
	@Resource(name="projectForeclosureMapper")
	private ProjectForeclosureMapper projectForeclosureMapper;
	
	@Resource(name="projectPropertyMapper")
	private ProjectPropertyMapper projectPropertyMapper;
	
	@Resource(name="productMapper")
	private ProductMapper productMapper;
	
	@Resource(name="projectForeInformationMapper")
	private ProjectForeInformationMapper foreInformationMapper;

	@Resource(name = "projectSurveyReportMapper")
	private ProjectSurveyReportMapper projectSurveyReportMapper;

	@Resource(name = "creditsDTOMapper")
	private CreditsDTOMapper creditsDTOMapper;

	@Resource(name = "cusAcctMapper")
	private CusAcctMapper cusAcctMapper;

	@Resource(name = "workflowProjectMapper")
	private WorkflowProjectMapper workflowProjectMapper;

	@Resource(name = "workflowServiceImpl")
	private WorkflowService.Iface workflowServiceImpl;
	
	@Resource(name = "sysLookupValMapper")
	private SysLookupValMapper sysLookupValMapper;
	
	@Resource(name = "bizHandleMapper")
	private BizHandleMapper bizHandleMapper;
	
	@Resource(name = "orgFeeSettleDetailMapper")
	private OrgFeeSettleDetailMapper orgFeeSettleDetailMapper;
	
	@Resource(name = "refundFeeMapper")
   private RefundFeeMapper refundFeeMapper;
	
	@Resource(name = "foreclosureRepaymentMapper")
	private ForeclosureRepaymentMapper ForeclosureRepaymentMapper;
	
	@Resource(name = "projectSurveyReportServiceImpl")
	private ProjectSurveyReportService.Iface projectSurveyReportServiceImpl;
	
	@Resource(name = "bizProjectRetreatMapper")
	private BizProjectRetreatMapper bizProjectRetreatMapper;
	
	@Resource(name = "dataUploadMapper")
	private DataUploadMapper dataUploadMapper;
	
	@Resource(name = "bizProjectEstateMapper")
	private BizProjectEstateMapper bizProjectEstateMapper;
	
    @Resource(name = "bizOriginalLoanMapper")
    private BizOriginalLoanMapper bizOriginalLoanMapper;
    
    @Resource(name = "bizDynamicServiceImpl")
    private BizDynamicService.Iface bizDynamicService;
    
    @Resource(name = "bizCapitalSelRecordMapper")
    private BizCapitalSelRecordMapper bizCapitalSelRecordMapper;
    
    @Resource(name = "financeHandleMapper")
    private FinanceHandleMapper financeHandleMapper;
    
    @Resource(name = "projectServiceImpl")
    private ProjectService.Iface projectServiceImpl;
	/**
	 * 
	 * @Description: 条件查询贷前列表
	 * @param project
	 *            贷前条件对象
	 * @return 贷前申请列表集合
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月25日 下午6:43:01
	 */
	@Override
   public List<Project> getAllProject(Project project) throws TException {
		List<Project> list = new ArrayList<Project>();
		try {
			// 修改申请开始时间格式
			if (null != project.getBeginRequestDttm() && !"".equals(project.getBeginRequestDttm())) {
				String beginString = project.getBeginRequestDttm();
				Date d = DateUtil.format(beginString, "yyyy-MM-dd");
				beginString = new StringBuffer().append(DateUtil.format(d, "yyyy-MM-dd").trim()).append(" 00:00:01").toString();
				project.setBeginRequestDttm(beginString);
			}
			// 修改申请结束时间格式
			if (null != project.getEndRequestDttm() && !"".equals(project.getEndRequestDttm())) {
				String endString = project.getEndRequestDttm();
				Date d = DateUtil.format(endString, "yyyy-MM-dd");
				endString = new StringBuffer().append(DateUtil.format(d, "yyyy-MM-dd").trim()).append(" 23:59:59").toString();
				project.setEndRequestDttm(endString);
			}
			list = projectMapper.getAllProject(project);
		} catch (Exception e) {
			
			logger.error("查询贷前列表:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return list;
	}

	/**
	 * 
	 * @Description: 条件查询贷前的总数
	 * @param project
	 *            贷前条件对象
	 * @return 总数
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月25日 下午6:42:24
	 */
	@Override
   public int getAllProjectCount(Project project) throws TException {
		int count = 0;
		try {
			// 修改申请开始时间格式
			if (null != project.getBeginRequestDttm() && !"".equals(project.getBeginRequestDttm())) {
				String beginString = project.getBeginRequestDttm();
				Date d = DateUtil.format(beginString, "yyyy-MM-dd");
				beginString = new StringBuffer().append(DateUtil.format(d, "yyyy-MM-dd").trim()).append(" 00:00:01").toString();
				project.setBeginRequestDttm(beginString);
			}
			// 修改申请结束时间格式
			if (null != project.getEndRequestDttm() && !"".equals(project.getEndRequestDttm())) {
				String endString = project.getEndRequestDttm();
				Date d = DateUtil.format(endString, "yyyy-MM-dd");
				endString = new StringBuffer().append(DateUtil.format(d, "yyyy-MM-dd").trim()).append(" 23:59:59").toString();
				project.setEndRequestDttm(endString);
			}
			count = projectMapper.getAllProjectCount(project);
		} catch (Exception e) {
			logger.error("查询贷前列表:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return count;
	}

	/**
	 * 
	 * @Description: 根据项目ID查询项目的担保方法
	 * @param projectId
	 *            项目DI
	 * @return 担保对象集合
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月25日 下午6:41:52
	 */
	@Override
	public List<ProjectGuaranteeType> getGuaranteeTypeByProjectId(int projectId) throws TException {
		List<ProjectGuaranteeType> list = new ArrayList<ProjectGuaranteeType>();
		try {
			list = projectGuaranteeTypeMapper.getProjectGuaranteeTypeByProjectId(projectId);
		} catch (Exception e) {
			logger.error("查询担保方式:" + ExceptionUtil.getExceptionMessage(e));
		}
		return list;
	}

	/**
	 * 
	 * @Description: 根据项目ID查询贷款详细信息
	 * @param projectId
	 *            项目ID
	 * @return 贷款对象
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月25日 下午6:41:31
	 */
	@Override
	public Project getLoanProjectByProjectId(int projectId) throws TException {
		Project project = new Project();
		try {
			List<Project> list = new ArrayList<Project>();
			list = projectMapper.getLoanProjectByProjectId(projectId);
			if (list.size() > 0) {
				project = list.get(0);
			}else{
				project = projectMapper.getProjectInfoById(projectId);
			}
			ProjectForeclosure projectForeclosure = projectForeclosureMapper.getForeclosureByProjectId(projectId);
			if (projectForeclosure == null) {
				projectForeclosure = new ProjectForeclosure();
			}
			project.setProjectForeclosure(projectForeclosure);
			ProjectGuarantee projectGuarantee = projectGuaranteeMapper.getGuaranteeByProjectId(projectId);
			if (projectGuarantee == null) {
				projectGuarantee = new ProjectGuarantee();
			}
			project.setProjectGuarantee(projectGuarantee);
			ProjectProperty projectProperty = projectPropertyMapper.getPropertyByProjectId(projectId);
			
			if (projectProperty == null) {
				projectProperty = new ProjectProperty();
			}
			project.setProjectProperty(projectProperty);
			List<BizProjectEstate> estateList = bizProjectEstateMapper.getAllByProjectId(projectId);
			
			if (estateList == null) {
				estateList = new ArrayList<BizProjectEstate>();
			}
			project.setEstateList(estateList);
			BizOriginalLoan bizOriginalLoan  = new BizOriginalLoan();
			bizOriginalLoan.setStatus(1);
			bizOriginalLoan.setProjectId(projectId);
			List<BizOriginalLoan> originalLoanList = bizOriginalLoanMapper.getAllByCondition(bizOriginalLoan);
			if (originalLoanList == null) {
				originalLoanList = new ArrayList<BizOriginalLoan>();
			}
			project.setOriginalLoanList(originalLoanList);
		} catch (Exception e) {
			logger.error("根据项目ID查询项目详细信息:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return project;
	}

	/**
	 * 
	 * @Description: 根据项目ID查询项目名称和项目编号
	 * @param projectId
	 *            项目ID
	 * @return 贷款对象
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月25日 下午6:41:02
	 */
	@Override
	public Project getProjectNameOrNumber(int projectId) throws TException {
		Project project = new Project();
		try {
			List<Project> list = new ArrayList<Project>();
			list = projectMapper.getProjectNameOrNumber(projectId);
			if (list.size() > 0) {
				project = list.get(0);
			}
		} catch (Exception e) {
			
				logger.error("根据项目ID查询项目名称和项目编号:" + ExceptionUtil.getExceptionMessage(e));
			}
		return project;
	}

	/**
	 * 
	 * @Description: 批量删除贷前申请
	 * @param loanPids
	 *            贷前ID (1,2,3)
	 * @return 受影响的行数
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月25日 下午6:40:14
	 */
	@Override
	public int batchDelete(String loanPids) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 把传过来的数据劈开成一个数组作为参数传过去
			String[] strArr = loanPids.split(",");
			rows = projectMapper.batchDelete(strArr);
			// 循环删除需要删除的项目的工作流
			for (int i = 0; i < strArr.length; i++) {
				int projectId = Integer.parseInt(strArr[i]);
				String workflowProcess = "";
				
				Project project = new Project();
				project = projectMapper.getProjectInfoById(projectId);
				if(project.getProjectSource() == 2){//小科
					workflowProcess = "creditLoanRequestProcess";
				}else if(project.getProjectSource() == 1){//万通
					workflowProcess = "foreLoanRequestProcess";
				}
				// 调用方法
				workflowServiceImpl.deleteProcessInstance(projectId, projectId, workflowProcess);
				
				// 项目删除,删除对应的财务放款记录
				//financeTransactionMapper.deleteFinanceRecords(projectId);

				
				// 创建客户对象,修改客户状态
				CusAcct cusAcct = new CusAcct();
				cusAcct.setPid(project.getAcctId());
				cusAcct.setCusStatus(1);
				// 修改当前客户的状态
				cusAcctMapper.updateCusStatus(cusAcct);
			}
		} catch (Exception e) {
			logger.error("删除贷前申请:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return rows;
	}

	/**
	 * 
	 * @Description: 新增贷前申请
	 * @param project
	 *            贷款对象
	 * @return 贷款ID and 授信ID (1,2)
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月25日 下午6:39:33
	 */
	@Override
	public String addProject(Project project) throws ThriftServiceException, TException {
		// 需要返回的项目ID和授信ID
		String str = "";
		// 项目ID
		int projectId = 0;
		// 授信ID
		int creditId = 0;
		// 判断是否成功--受影响的行数
		int rows = 0;
		try {
			int productType = project.getProductType();//产品类型，不入库
			// 查询系统配置
			//SysConfig sysConfig = sysConfigMapper.getByConfigName("IS_WHETHER_OPENING_PROCESS");
			if(checkProjectAcct(project)>0){
				throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "当前客户存在贷款项目,不能再创建重复的项目!");
			}
			// 获取新的项目ID
			projectId = projectMapper.getSeqBizProject();
			// 赋值
			project.setPid(projectId);
			// 生成项目名称
			String projectName = makeProjectName(project.getAbbreviation(), project.getAcctId());
			// 生成项目编号
			String projectNumber = makeProjectNumber(productType);
			// 赋值项目ID和项目编号
			project.setProjectName(projectName);
			project.setProjectNumber(projectNumber);
			// 设置申请状态为1(申请中) 状态为1(正常) 申请时间为当前时间
			project.setRequestStatus(1);
			//设置赎楼状态为1（未提交）
			project.setForeclosureStatus(1);
			project.setStatus(1);
			project.setIsChechan(0);//未撤单
			// 获取系统时间,并设置
			Timestamp time = new Timestamp(new Date().getTime());
			project.setRequestDttm(time.toString());
			// 设置项目状态为-1,不进行录入
			project.setProjectType(2);
			// 新增对象
			rows = projectMapper.insert(project);

			// 新创建项目关系表对象
			ProjectRelation projectRelation = new ProjectRelation();
			// 项目关系表插入数据
			projectRelation.setProjectId(projectId);// 新项目ID
			projectRelation.setRefProjectId(projectId);// 贷前的关联项目ID就是本身
			projectRelation.setRefType(1);// 关联类型(1:授信,2:展期）
			projectRelation.setStatus(1);
			projectRelationMapper.insert(projectRelation);
			// 插入项目历史记录表
			ProjectRecord projectRecord = new ProjectRecord();
			projectRecord.setCompleteDttm(DateUtil.format(new Date()));
			projectRecord.setProcessType(1);
			projectRecord.setProcessUserId(project.getPmUserId());
			projectRecord.setProjectId(projectId);
			projectMapper.addProjectRecord(projectRecord);
			// 创建客户对象,修改客户状态
			CusAcct cusAcct = new CusAcct();
			cusAcct.setPid(project.getAcctId());
			cusAcct.setCusStatus(4);
			// 修改当前客户的状态
			cusAcctMapper.updateCusStatus(cusAcct);

			// 判断是否选取共同借款人
			if (project.getUserPids() != null && !project.getUserPids().equals("")) {
				String[] userPids = project.getUserPids().split(",");
				for (int i = 0; i < userPids.length; i++) {
					// 循环添加共同借款人
					ProjectPublicMan projectPublicMan = new ProjectPublicMan();
					projectPublicMan.setProjectId(projectId);// 将当前的项目ID赋值
					projectPublicMan.setPersonId(Integer.parseInt(userPids[i]));// 共同借款人PID
					projectPublicMan.setStatus(1);// 将状态设置为1正常
					// 新增共同借款人
					projectPublicManMapper.insert(projectPublicMan);
				}
			}
			//物业ID
			// 保存物业双方信息
			ProjectProperty projectProperty = project.getProjectProperty();
			String houseIds = project.getHouseIds();
			String houseName="";
			String housePropertyCard = "";
			double tranasctionMoney = 0.0;//成交价
			double evaluationPrice = 0.0;//评估价
			double area=0.0;//面积
			double costMoney = 0.0;//登记价
			double evaluationNet = 0.0;//评估净值
			if(!StringUtil.isBlank(houseIds)){
				List<Integer> houseIdList = StringUtil.StringToList(houseIds);
				//遍历提交保存的物业ID
				for(Integer houseId:houseIdList){
					BizProjectEstate projectEstate = bizProjectEstateMapper.getById(houseId);
					projectEstate.setHouseId(houseId);
					projectEstate.setProjectId(projectId);
					//将物业信息也项目进行关联
					bizProjectEstateMapper.update(projectEstate);
					
					houseName += projectEstate.getHouseName()+",";
					housePropertyCard += projectEstate.getHousePropertyCard()+",";
					tranasctionMoney += projectEstate.getTranasctionMoney();
					evaluationPrice += projectEstate.getEvaluationPrice();
					area +=projectEstate.getArea();
					costMoney +=projectEstate.getCostMoney();
					evaluationNet += projectEstate.getEvaluationNet();
				}
				houseName = houseName.substring(0, houseName.length()-1);
				housePropertyCard = housePropertyCard.substring(0, housePropertyCard.length()-1);
				
				projectProperty.setHouseName(houseName);
				projectProperty.setHousePropertyCard(housePropertyCard);
				projectProperty.setTranasctionMoney(tranasctionMoney);
				projectProperty.setArea(area);
				projectProperty.setCostMoney(costMoney);
				projectProperty.setEvaluationPrice(evaluationPrice);
				projectProperty.setEvaluationNet(evaluationNet);
				
				logger.info("赎楼贷前信息保存------物业信息入原数据库----，项目名称："+projectName+"---物业名称："+houseName+"----物业Ids:"+houseIds);
			}
			
			logger.info("赎楼贷前信息保存------物业信息入原数据库----，项目名称："+projectName+"---物业名称："+houseName+"----物业Ids:"+houseIds);
			
			//原贷款ID
			String originalLoanIds = project.getOriginalLoanIds();
			if(!StringUtil.isBlank(originalLoanIds)){
				List<Integer> originalLoanIdList = StringUtil.StringToList(originalLoanIds);
				//遍历提交保存的原贷款ID
				for(Integer originalLoanId:originalLoanIdList){
					BizOriginalLoan originalLoan = new BizOriginalLoan();
					originalLoan.setOriginalLoanId(originalLoanId);
					originalLoan.setProjectId(projectId);
					//将原贷款信息也项目进行关联
					bizOriginalLoanMapper.update(originalLoan);
				}
			}
			
			// 创建授信并新增
			Credit credit = new Credit();
			// 获取授信ID
			creditId = creditMapper.getSeqBizCredit();
			credit.setPid(creditId);// 授信ID
			credit.setProjectId(projectId);// 项目ID
			credit.setStatus(1);// 状态为1
			credit.setCreditStatus(3);// 设置授信为3,不进行录入
			rows += creditMapper.addCredit(credit);

			int foreclosureId = 0;//赎楼ID
			int propertyId = 0;//物业ID
			int guaranteeId = 0;//担保ID
			if(productType == 2){//赎楼类项目
				// 保存赎楼信息
				ProjectForeclosure projectForeclosure = project.getProjectForeclosure();

				// 获取赎楼ID
				foreclosureId = projectForeclosureMapper
						.getSeqBizProjectForeclosure();
				projectForeclosure.setPid(foreclosureId);
				projectForeclosure.setProjectId(projectId);
				int loanDays = projectForeclosure.getLoanDays();
				String receDate = projectForeclosure.getReceDate();
				if(!StringUtil.isBlank(receDate)){
				Date payDate = DateUtils.addDay(DateUtils.stringToDate(receDate, "yyyy-MM-dd"), loanDays - 1);
	            // 根据计划放款日期、贷款天数计算出计划还款日期,修改项目原先的还款日期与放款日期
	            projectForeclosure.setPaymentDate(DateUtils.dateFormatByPattern(payDate, "yyyy-MM-dd"));
				}
				// 赎楼信息入库
				projectForeclosureMapper.insertForeclosure(projectForeclosure);
				
				// 获取物业ID
				propertyId = projectPropertyMapper.getSeqBizProjectProperty();
				//设置多物业信息
				projectProperty.setPid(propertyId);
				projectProperty.setProjectId(projectId);
				// 物业信息入库
				projectPropertyMapper.insertProperty(projectProperty);
				//如果项目来源为万通，将填写的物业信息写入物业信息表中
				int projectSource = project.getProjectSource();
				if(projectSource == Constants.PROJECT_SOURCE_WT){
					BizProjectEstate projectEstate = new BizProjectEstate();
					projectEstate.setProjectId(projectId);
					projectEstate.setArea(projectProperty.getArea());
					projectEstate.setEvaluationPrice(projectProperty.getEvaluationPrice());
					projectEstate.setCostMoney(projectProperty.getCostMoney());
					projectEstate.setPurpose(projectProperty.getPurpose());
					projectEstate.setTranasctionMoney(projectProperty.getTranasctionMoney());
					projectEstate.setHouseName(projectProperty.getHouseName());
					projectEstate.setHousePropertyCard(projectProperty.getHousePropertyCard());
					bizProjectEstateMapper.insert(projectEstate);
					
					BizOriginalLoan originalLoan = new BizOriginalLoan();
					originalLoan.setProjectId(projectId);
					originalLoan.setOldLoanBank(projectForeclosure.getOldLoanBank());
					originalLoan.setOldOwedAmount(projectForeclosure.getOldOwedAmount());
					originalLoan.setOldLoanBankBranch(projectForeclosure.getOldLoanBankBranch());
					originalLoan.setOldLoanMoney(projectForeclosure.getOldLoanMoney());
					originalLoan.setOldLoanPerson(projectForeclosure.getOldLoanPerson());
					originalLoan.setOldLoanPhone(projectForeclosure.getOldLoanPhone());
					originalLoan.setOldLoanTime(projectForeclosure.getOldLoanTime());
					originalLoan.setEstateId(projectEstate.getHouseId());
					bizOriginalLoanMapper.insert(originalLoan);
				}
				
				// 保存赎楼项目费用信息
				ProjectGuarantee projectGuarantee = project.getProjectGuarantee();
				// 获取担保ID
				guaranteeId = projectGuaranteeMapper
						.getSeqBizProjectGuarantee();
				projectGuarantee.setPid(guaranteeId);
				projectGuarantee.setProjectId(projectId);
				// 费用信息入库
				projectGuaranteeMapper.insertGuarantee(projectGuarantee);
			}
			//添加赎楼清单
			List<SysLookupVal> result = sysLookupValMapper.getSysLookupValByLookType("PROJECT_FORE_INFORMATION");
			if(result != null && result.size()>0){
				for(SysLookupVal lookUp : result){
					ProjectForeInformation foreInfo = new ProjectForeInformation();
					foreInfo.setForeId(lookUp.getPid());
					foreInfo.setProjectId(projectId);
					foreInformationMapper.insertProjectForeInformation(foreInfo);
				}
			}
			
			
			if (rows >= 2) {
				str = projectId + "," + creditId+","+projectName+","+projectNumber+","+foreclosureId+","+propertyId+","+guaranteeId;
			} else {
				// 抛出异常处理
				throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "新增失败,请重新操作!");
			}
		} catch (ThriftServiceException e) {
			logger.error("新增贷前申请:" + ExceptionUtil.getExceptionMessage(e));
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("新增贷前申请:" + ExceptionUtil.getExceptionMessage(e));
			throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "新增失败,请重新操作!");
		}
		return str;
	}

	/**
	 * 
	 * @Description: 新增贷款详细信息 and 修改贷款信息 and 修改授信信息
	 * @param project
	 *            贷款对象
	 * @return 受影响的记录行数
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月25日 下午6:38:45
	 */
	@Override
	public int addInformation(Project project) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 根据是否循环,判断当前项目的循环类型
			if (project.getIsHoop() == 2) {
				// 如果等于不可循环,就需要把循环类型置为空or0
				project.setCirculateType(0);// 循环类型
			}
			// 1.修改项目信息
			projectMapper.updateByPrimaryKey(project);
			// 3.修改授信信息--根据项目ID
			// 3.1 创建授信对象
			Credit credit = new Credit();
			credit.setProjectId(project.getPid());
			credit.setCreditAmt(project.getCreditAmt());
			credit.setCredtiStartDt(project.getCredtiStartDt());
			credit.setCredtiEndDt(project.getCredtiEndDt());
			credit.setIsHoop(project.getIsHoop());
			// 根据项目状态判断授信状态
			if (project.getProjectType() == 2) {
				// 贷款项目,没有授信状态
				credit.setCreditStatus(0);
			} else {
				// 设置状态为1,表示有效,因为初始项目都是有效
				credit.setCreditStatus(1);
			}
			// 3.2 修改授信信息
			creditMapper.updateByProjectId(credit);

			// 4.新增贷款信息
			// 4.1 创建贷款对象
			Loan loan = new Loan();
			// 4.2 赋值
			if (project.getRepayOption() != 0 && project.getRepayOptionTest() != 0) {
				loan.setRepayOption(3);// 说明两个都选中
			} else if (project.getRepayOption() != 0 && project.getRepayOptionTest() == 0) {
				loan.setRepayOption(2);// 其中一个选择
			} else if (project.getRepayOption() == 0 && project.getRepayOptionTest() != 0) {
				loan.setRepayOption(4);// 其中一个选择
			} else {
				loan.setRepayOption(1);// 都不选默认为0
			}
			loan.setEachissueOption(project.getEachissueOption());

			loan.setRepayDate(project.getRepayDate());
			loan.setProjectId(project.getPid());
			loan.setCreditAmt(project.getLoanAmt());
			loan.setCurrency(project.getCurrency());
			loan.setDateMode(project.getDateMode());
			loan.setRepayFun(project.getRepayFun());
			loan.setRepayCycleType(project.getRepayCycleType());
			loan.setRepayCycle(project.getRepayCycle());
			loan.setPlanOutLoanDt(project.getPlanOutLoanDt());
			loan.setPlanRepayLoanDt(project.getPlanRepayLoanDt());
			// loan.setRepayOption(1);// 需要修改
			// loan.setRepayDate(15);// 需要修改
			loan.setMonthLoanInterest(project.getMonthLoanInterest());
			loan.setMonthLoanMgr(project.getMonthLoanMgr());
			loan.setMonthLoanOtherFee(project.getMonthLoanOtherFee());
			loan.setYearLoanInterest(project.getYearLoanInterest());
			loan.setYearLoanMgr(project.getYearLoanMgr());
			loan.setYearLoanOtherFee(project.getYearLoanOtherFee());
			loan.setDayLoanInterest(project.getDayLoanInterest());
			loan.setDayLoanMgr(project.getDayLoanMgr());
			loan.setDayLoanOtherFee(project.getDayLoanOtherFee());
			loan.setLiqDmgProportion(project.getLiqDmgProportion());
			loan.setOverdueLoanInterest(project.getOverdueLoanInterest());
			loan.setOverdueFineInterest(project.getOverdueFineInterest());
			loan.setMisFineInterest(project.getMisFineInterest());
			loan.setPrepayLiqDmgProportion(project.getPrepayLiqDmgProportion());
			loan.setFeesProportion(project.getFeesProportion());// 设置手续费
			loan.setCompleteDttm(DateUtil.format(new Date()));
			loan.setStatus(1);
			loan.setJudgeRepayCycle(project.getJudgeRepayCycle());// 判断还款期限是否相等(1:不相等
																	// -1:相等)
			int pid = -1;

			// 4.3 判断是执行什么操作
			if (project.getLoanId() == -1) {
				pid = loanMapper.getSeqBizLoan();
				loan.setPid(pid);
				loanMapper.insert(loan);
			} else {
				pid = project.getLoanId();
				loan.setPid(pid);
				loanMapper.updateByPrimaryKey(loan);
			}

			rows = pid;

			// 5.调用生成贷款试算信息的接口
			makeRepayMent(loan, 1, new CalcOperDto(0, 0, 0, 0, 0, "", 1, 0, "", 0));
		} catch (Exception e) {
			logger.error("新增授信和额度详细信息:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return rows;
	}

	/**
	 * 生成还款计划表
	 * 
	 * @param loan
	 *            贷款信息
	 * @param userId
	 *            用户Id
	 * @param calcOperDto
	 *            即时发生属性
	 * @param calcType
	 *            还款类型(贷款试算=1、提前还款=2，利率变更=3、挪用处理=4、违约处理=5)
	 */
	@Override
	public List<RepaymentPlanBaseDTO> makeRepayMent(Loan loan, int userId, CalcOperDto calcOperDto) throws ThriftServiceException, TException {

		CalPlanLine dcal = repaymentCalPlanLineFactory.factory(loan.getRepayFun());
		dcal.setOperDto(calcOperDto);
		List<RepaymentPlanBaseDTO> list = dcal.execute(loan, userId);
		if (calcOperDto.getRepayType() != 4 && calcOperDto.getRepayType() != 5 && calcOperDto.getRepayType() != 6 && list != null && list.size() > 0) {
			loanRepaymentPlanMidMapper.insertRepayments(list);

			// 还款计划表中间表 转换成还款计划表
			loanRepaymentPlanMapper.makeRepayments(loan.getPid());
		}
		return list;
	}

	/**
	 * 修改还款计划表
	 */
	@Override
	public int editRepayMent(List<RepaymentPlanBaseDTO> repayments) throws ThriftServiceException, TException {

		try {
			loanRepaymentPlanMapper.updateRepayments(repayments);
		} catch (Exception e) {
			logger.error("修改还款计划表:" + ExceptionUtil.getExceptionMessage(e));
		}
		return 0;
	}

	/**
	 * 查询还款计划表
	 */
	@Override
	public List<RepaymentPlanBaseDTO> getRepayMents(int loanId) throws ThriftServiceException, TException {
		try {
			List<RepaymentPlanBaseDTO> list = loanRepaymentPlanMapper.getRepayments(loanId);
			return list;
		} catch (Exception e) {
			logger.error("查询贷前列表:" + ExceptionUtil.getExceptionMessage(e));
		}
		return new ArrayList<RepaymentPlanBaseDTO>();
	}

	@Override
	public int updateRepaymentOtherCostName(RepaymentPlanBaseDTO repayment) throws ThriftServiceException, TException {

		try {
			loanRepaymentPlanMapper.updateRepaymentOtherCostName(repayment);
		} catch (Exception e) {
			logger.error("修改还款计划表其他费用名称:" + ExceptionUtil.getExceptionMessage(e));
		}
		return 0;
	}
	/**
	 * 查询还款计划表-贷款信息
	 */
	@Override
	public RepaymentLoanInfo getRepaymentLoanInfo(int loanId) throws ThriftServiceException, TException {

		RepaymentLoanInfo repaymentLoanInfo = null;
		try {
			repaymentLoanInfo = loanRepaymentPlanMapper.getRepaymentLoanInfo(loanId);
			
			if (null != repaymentLoanInfo) {
				//拼接贷款人信息。共同借款人一起在还款计划表显示
				
				StringBuffer b = new StringBuffer();
				b.append(repaymentLoanInfo.getAcctName());
				List<CusDTO> list =cusPerBaseMapper.getNoSpouseLists(repaymentLoanInfo.getProjectId());
				if(list!=null ){
					for(CusDTO cusDTO : list){						
						b.append(",");
						b.append(cusDTO.getChinaName());
					}
					repaymentLoanInfo.setAcctName(b.toString());
				}
				
				DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
				String formatAmt = decimalFormat.format(repaymentLoanInfo.getCreditAmt());
				repaymentLoanInfo.setFormatAmt(formatAmt);
			}
		} catch (Exception e) {
			logger.error("查询还款计划表-贷款信息:" + ExceptionUtil.getExceptionMessage(e));
		}
		return repaymentLoanInfo != null ? repaymentLoanInfo : new RepaymentLoanInfo();
	}

	/**
	 * 
	 * @Description: 根据项目ID查询贷款ID
	 * @param projectId
	 *            项目ID
	 * @return 贷款ID
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月25日 下午6:43:56
	 */
	@Override
	public int getLoanIdByProjectId(int projectId) throws TException {
		int loanId = 0;
		try {
			List<Project> list = projectMapper.getLoanProjectByProjectId(projectId);
			if(list !=null && list.size()>0){
				Project project = list.get(0);
				int productType = project.getProductType();
				if(productType != 2){
					loanId = loanMapper.getLoanIdByProjectId(projectId);
				}
			}
		} catch (Exception e) {
			logger.error("根据项目ID查询贷款ID:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return loanId;
	}

	/**
	 * 
	 * @Description: 根据项目ID查询贷款ID
	 * @param projectId
	 *            项目ID
	 * @return 贷款ID
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月25日 下午6:43:56
	 */
	@Override
	public int getProjectIdByLoanId(int loanId) throws TException {
		try {
			return loanMapper.getProjectIdByLoanId(loanId);
		} catch (Exception e) {
			logger.error("根据项目贷款查询项目ID:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
	}
	/**
	 * 根据项目ID查询贷款ID
	 */
	@Override
   public int getCreditIdByProjectId(int projectId) throws TException {
		int creditId = 0;
		try {
			creditId = creditMapper.getCreditIdByProjectId(projectId);
		} catch (Exception e) {
			logger.error("根据项目ID查询贷款ID:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return creditId;
	}

	/**
	 * 
	 * @Description: 修改项目调查结论信息
	 * @param project
	 *            项目对象
	 * @return
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: xuweihao
	 * @date: 2015年4月25日 下午6:59:49
	 */
	@Override
	public int addProcedures(Project project) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 创建新对象
			Project p = new Project();
			p.setPid(project.getPid());
			p.setIsAllowPrepay(project.getIsAllowPrepay());
			p.setIsReturnInterest(project.getIsReturnInterest());
			p.setSurveyResult(project.getSurveyResult());
			rows = projectMapper.updateProceduresByPrimaryKey(p);
		} catch (Exception e) {
			logger.error("新增贷审会意见信息:" + ExceptionUtil.getExceptionMessage(e));
		}
		return rows;
	}

	/**
	 * 
	 * @Description: 根据项目id修改项目状态
	 * @param pid
	 *            项目id
	 * @param requestStatus
	 * @return
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: xuweihao
	 * @date: 2015年4月25日 下午6:59:45
	 */
	@Override
   public int updateProjectStatusByPrimaryKey(int pid, int requestStatus) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			rows = projectMapper.updateProjectStatusByPrimaryKey(pid, requestStatus, "");
		} catch (Exception e) {
			logger.error("修改项目状态:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return rows;
	}

	/**
	 * 修改项目信息
	 */
	@Override
   public int updateProject(Project project) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			projectMapper.updateByPrimaryKey(project);
			projectGuaranteeMapper.updateByPrimaryKey(project.getProjectGuarantee());
			
			ProjectForeclosure projectForeclosure = project.getProjectForeclosure();
			int loanDays = projectForeclosure.getLoanDays();
			String receDate = projectForeclosure.getReceDate();
			if(!StringUtil.isBlank(receDate)){
				Date payDate = DateUtils.addDay(DateUtils.stringToDate(receDate, "yyyy-MM-dd"), loanDays - 1);
	            // 根据计划放款日期、贷款天数计算出计划还款日期,修改项目原先的还款日期与放款日期
	            projectForeclosure.setPaymentDate(DateUtils.dateFormatByPattern(payDate, "yyyy-MM-dd"));
			}
			projectForeclosureMapper.updateByPrimaryKey(projectForeclosure);
			double foreRate = project.getProjectProperty().getForeRate();
			if(foreRate >0){
				projectPropertyMapper.updateByPrimaryKey(project.getProjectProperty());
			}
		} catch (Exception e) {
			logger.error("修改贷前申请:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return rows;
	}

	/**
	 * 
	 * @Description: 生成项目名称
	 * @param abbrName
	 *            如果是企业=客户简称 如果是个人=个人中文名称
	 * @param acctId
	 *            客户ID
	 * @return 项目名称
	 * @author: Cai.Qing
	 * @date: 2015年3月30日 下午3:02:33
	 */
	public String makeProjectName(String abbrName, int acctId) {
		// 首先附加 :客户简称
		String projectName = abbrName;
		// 获取当前的8位年月日,并附加到新的项目名称里面
		projectName += DateUtil.format(new Date(), "yyyyMMdd");
		// 获取当前日期的项目名称的最后2位自然数
		int maxNumber = projectMapper.getMaxProjectName(acctId);
		// 判断是否是一位数，还是两位数
		if (maxNumber > 10) {
			projectName += maxNumber;
		} else {
			projectName += "0" + maxNumber;
		}
		return projectName;
	}

	/**
	 * 
	 * @Description: 生成项目编号
	 * @return 项目编号
	 * @author: Cai.Qing
	 * @date: 2015年2月9日 下午2:26:51
	 */
	public String makeProjectNumber(int productType) {
		// 首先附加 :客户简称
		String projectName = "XYD";
		if(productType == 1){
			projectName = "XYD";
		}else if(productType == 2){
			projectName = "SLD";
		}
		
		// 获取当前的4位年 并附加到新的项目名称里面
		projectName += DateUtil.format(new Date(), "yyyy");
		// 获取当前日期的项目名称的最后5位自然数
		String maxNumber = projectMapper.getMaxProjectNumber();
		// 判断是否为空
		if (maxNumber != null) {
			maxNumber = (Integer.parseInt(maxNumber) + 1) + "";
			if (maxNumber.length() == 1) {
				projectName += "0000" + maxNumber;
			} else if (maxNumber.length() == 2) {
				projectName += "000" + maxNumber;
			} else if (maxNumber.length() == 3) {
				projectName += "00" + maxNumber;
			} else if (maxNumber.length() == 4) {
				projectName += "0" + maxNumber;
			} else {
				projectName += maxNumber;
			}
		} else {
			projectName += "00001";
		}

		return projectName;
	}

	/**
	 * 回撤项目名称生成方法
	 * @param projectName
	 * @param acctId
	 * @return
	 */
	public String makeRetreatProjectName(String projectName, int acctId){
		// 获取当前的8位年月日,并附加到新的项目名称里面
		int indexValue = projectName.indexOf("回撤");
		if(indexValue>0){
			projectName = projectName.substring(0, projectName.length()-2);
		}else{
			projectName += "-回撤";
		}
		
		// 获取当前日期的项目名称的最后2位自然数
		int maxNumber = projectMapper.getMaxProjectName(acctId);
		// 判断是否是一位数，还是两位数
		if (maxNumber > 10) {
			projectName += maxNumber;
		} else {
			projectName += "0" + maxNumber;
		}
		return projectName;
	}
	
	/**
	 * 
	 * @Description: 归档项目查询
	 * @return 查询结果
	 * @author: HeZhiying
	 * @date: 2015年3月11日 下午2:26:51
	 */
	@Override
   public List<Project> getAfterLoadFileList(Project project) throws TException {
		List<Project> list = new ArrayList<Project>();
		try {
			list = projectMapper.getAfterLoadFileList(project);
		} catch (Exception e) {
			logger.error("查询归档项目列表:" + ExceptionUtil.getExceptionMessage(e));
		}
		return list;
	}

	/**
	 * 
	 * @Description: 归档项目查询总数
	 * @return 查询结果
	 * @author: HeZhiying
	 * @date: 2015年3月11日 下午2:26:51
	 */
	@Override
   public int getAfterLoadFileCount(Project project) throws TException {
		int count = 0;
		try {
			count = projectMapper.getAfterLoadFileCount(project);
		} catch (Exception e) {
			logger.error("查询归档项目列表总数:" + ExceptionUtil.getExceptionMessage(e));
		}
		return count;
	}

	/**
	 * 
	 * @Description: 修改项目归档状态
	 * @return 查询结果
	 * @author: HeZhiying
	 * @date: 2015年3月11日 下午2:26:51
	 */
	@Override
   public int updateAfterLoanArchive(AfterLoanArchive afterLoanArchive) throws TException {
		int rows = 0;
		try {
			rows = projectMapper.updateAfterLoanArchive(afterLoanArchive);
		} catch (Exception e) {
			logger.error("项目归档:" + ExceptionUtil.getExceptionMessage(e));
		}
		return rows;
	}

	/**
	 * 
	 * @Description: 新增项目归档信息
	 * @param afterLoanArchive
	 * @return
	 * @throws TException
	 * @author: xuweihao
	 * @date: 2015年4月26日 下午7:12:54
	 */
	@Override
   public int addAfterLoanArchive(AfterLoanArchive afterLoanArchive) throws TException {
		int rows = 0;
		try {
			rows = projectMapper.addAfterLoanArchive(afterLoanArchive);
		} catch (Exception e) {
			logger.error("新增项目归档:" + ExceptionUtil.getExceptionMessage(e));
		}
		return rows;
	}

	/**
	 * 
	 * @Description: 查询项目归档资料状态 是否全部是已归档
	 * @return 查询结果
	 * @author: HeZhiying
	 * @date: 2015年3月11日 下午2:26:51
	 */
	@Override
   public int getProjectFileStatus(int projectId) throws TException {
		int rows = 0;
		try {
			rows = projectMapper.getProjectFileStatus(projectId);
		} catch (Exception e) {
			logger.error("归档资料状态:" + ExceptionUtil.getExceptionMessage(e));
		}
		return rows;
	}

	/**
	 * @Description: 额度提款申请
	 * @param project
	 * @return
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Rain.Lv
	 * @date: 2015年4月8日 下午3:27:43
	 * 
	 */
	@Override
   public String addNewProject(Project project) throws ThriftServiceException, TException {
		// 需要返回的项目ID和授信ID
		String str = "";
		// 项目ID
		int projectId = 0;
		Project newProject = null;
		ProjectRelation projectRelation = new ProjectRelation();
		int oldProjectId = project.getCreditId();// 用授信Id代替原项目id projectId
		// 判断是否成功--受影响的行数
		int rows = 0;
		try {
		/*	// 判断当前客户是否存在其他的非授信的项目正在申请或审核中,存在就不能创建新的项目
			int loanCount = projectMapper.getCountProjectLoanByAcctId(project.setProjectType(3));//项目类型（授信=1、贷款=2、额度提取=3、贷款展期=4  授信and贷款=5）
			if (loanCount > 0) {
				// 抛出异常处理
				throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "当前客户存在其他贷款项目,不能再创建新项目!");
			}*/
			int productType = project.getProductType();//产品类型，不入库
			// 获取新的项目ID
			projectId = projectMapper.getSeqBizProject();
			// 赋值
			project.setPid(projectId);
			// 通过id得到项目名称
			newProject = (Project) projectMapper.getProjectNameOrNumber(oldProjectId).get(0);
			// 把以前的项目名称赋值
			String projectName = newProject.getProjectName();
			// 生成项目编号
			String projectNumber = makeProjectNumber(productType);
			// 赋值项目ID和项目编号
			project.setProjectName(projectName + "-提取" + DateUtil.format(new Date(), "yyMMdd"));
			project.setProjectNumber(projectNumber);
			// 设置申请状态为1(申请中) 状态为1(正常) 申请时间为当前时间
			project.setRequestStatus(1);
			project.setStatus(1);
			project.setRequestDttm(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			project.setProjectType(3);// 项目类型（授信=1、贷款=2、额度提取=3、贷款展期=4 授信and贷款=5）
			// 新增对象
			rows = projectMapper.insert(project);

			// 项目关系表插入数据
			projectRelation.setProjectId(projectId);// 新项目ID
			projectRelation.setRefProjectId(oldProjectId);// 关联项目Id（原项目ID）
			projectRelation.setRefType(1);// 关联类型(1:授信,2:展期）
			projectRelation.setStatus(1);
			projectRelationMapper.insert(projectRelation);
			// 插入项目历史记录表
			ProjectRecord projectRecord = new ProjectRecord();
			projectRecord.setCompleteDttm(DateUtil.format(new Date()));
			projectRecord.setProcessType(1);
			projectRecord.setProcessUserId(project.getPmUserId());
			projectRecord.setProjectId(projectId);
			projectMapper.addProjectRecord(projectRecord);

			// 判断是否选取共同借款人
			if (project.getCusType() == 1 && project.getUserPids() != null && !project.getUserPids().equals("")) {
				String[] userPids = project.getUserPids().split(",");
				for (int i = 0; i < userPids.length; i++) {
					// 循环添加共同借款人
					ProjectPublicMan projectPublicMan = new ProjectPublicMan();
					projectPublicMan.setProjectId(projectId);// 将当前的项目ID赋值
					projectPublicMan.setPersonId(Integer.parseInt(userPids[i]));// 共同借款人PID
					projectPublicMan.setStatus(1);// 将状态设置为1正常
					// 新增共同借款人
					projectPublicManMapper.insert(projectPublicMan);
				}
			}
			if (rows >= 1) {
				str = projectId + "," + oldProjectId;// 代替老项目ID
				this.saveProjectSurveyReportToNew(projectId, oldProjectId);
			} else {
				// 抛出异常处理
				throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "新增失败,请重新操作!");
			}
		} catch (ThriftServiceException e) {
			logger.error("额度提款申请:" + ExceptionUtil.getExceptionMessage(e));
			throw e;
		} catch (Exception e) {
			logger.error("额度提款申请:" + ExceptionUtil.getExceptionMessage(e));
			throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "新增失败,请重新操作!");
		}
		return str;
	}

	/**
	 * 
	 * @Description:查询贷款信息
	 * @return
	 * @author:Gw
	 */
	@Override
	public Loan getLoanInfobyProjectId(int projectId) throws ThriftServiceException, TException {
		List<Loan> list = loanMapper.getLoansByProId(projectId);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return new Loan();
	}

	/**
	 * 
	 * @Description: 展期申请
	 * @param project
	 * @return 生成新项目
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Rain.Lv
	 * @date: 2015年4月8日 下午3:22:49
	 */
	@Override
	public String addExtensionProject(Project project) throws ThriftServiceException, TException {
		logger.info("********************pmuserid: "+project.getPmUserId()+" 添加展期项目开始**********************");
		// 需要返回的项目ID和授信ID
		String str = "";
		// 项目ID
		int projectId = 0;
		Project newProject = null;
		int oldProjectId = 0;
		ProjectRelation projectRelation = new ProjectRelation();
		// 判断是否成功--受影响的行数
		int rows = 0;
		try {
			int productType = project.getProductType();//产品类型，不入库
			// 获取新的项目ID
			projectId = projectMapper.getSeqBizProject();
			// 赋值
			project.setPid(projectId);
			// 通过id得到项目名称
			oldProjectId = project.getCreditId();
			newProject = getLoanProjectByProjectId(oldProjectId);
			// 把以前的项目名称赋值
			String projectName = newProject.getProjectName();
			// 生成项目编号
			String projectNumber = makeProjectNumber(productType);
			// 赋值项目ID和项目编号
			project.setProjectName(projectName + "-展期" + DateUtil.format(new Date(), "yyyyMMdd"));
			project.setProjectNumber(projectNumber);
			project.setBusinessCatelog(newProject.getBusinessCatelog());// 业务类别
			project.setBusinessType(newProject.getBusinessType());// 业务品种
			project.setFlowCatelog(newProject.getFlowCatelog());// 流程类别
			project.setMyType(newProject.getMyType());// 我方类型
			project.setMyMain(newProject.getMyMain());// 我方主体
			project.setLoanInterestRecord(newProject.getLoanInterestRecord());// 贷款利息收取单位
			project.setLoanMgrRecord(newProject.getLoanMgrRecord());// 贷款管理费收取单位
			project.setIsAllowPrepay(newProject.getIsAllowPrepay());// 是否可提前还款
			project.setIsReturnInterest(newProject.getIsAllowPrepay());// 是否退还利息
			project.setLoanOtherFee(newProject.getLoanOtherFee());// 其它费用收取单位
			project.setCirculateType(newProject.getCirculateType());// 循环类型
			
			project.setProductId(newProject.getProductId());
			project.setBusinessSource(newProject.getBusinessSource());
			project.setBusinessCategory(newProject.getBusinessCategory());
			project.setAddress(newProject.getAddress());
			project.setBusinessContacts(newProject.getBusinessContacts());
			project.setContactsPhone(newProject.getContactsPhone());
			project.setInnerOrOut(newProject.getInnerOrOut());
			project.setManagers(newProject.getManagers());
			project.setManagersPhone(newProject.getManagersPhone());
			//设置赎楼状态为1（未提交）
			project.setForeclosureStatus(1);
			project.setIsChechan(0);//未撤单
			
			// 设置申请状态为1(申请中) 状态为1(正常) 申请时间为当前时间
			project.setRequestStatus(1);
			project.setStatus(1);
			project.setRequestDttm(DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
			project.setProjectType(4);// 项目类型（授信=1、贷款=2、额度提取=3、贷款展期=4 授信and贷款=5）
			// 新增对象
			rows = projectMapper.insertProjectInfo(project);
			logger.info("********************pmuserid: "+project.getPmUserId()+" 添加展期项目 projectName:"+project.getProjectName()+" projectNumber:"+project.getProjectNumber()+" **********************");

			// 项目关系表插入数据
			projectRelation.setProjectId(projectId);// 新项目ID
			projectRelation.setRefProjectId(project.getCreditId());// 关联项目Id（原项目ID）
			projectRelation.setRefType(2);// 关联类型(1:授信,2:展期）
			projectRelation.setStatus(1);
			projectRelationMapper.insert(projectRelation);

			// 插入项目历史记录表
			ProjectRecord projectRecord = new ProjectRecord();
			projectRecord.setCompleteDttm(DateUtil.format(new Date()));
			projectRecord.setProcessType(1);
			projectRecord.setProcessUserId(project.getPmUserId());
			projectRecord.setProjectId(projectId);
			projectMapper.addProjectRecord(projectRecord);

			// 判断是否选取共同借款人
			if (project.getCusType() == 1 && project.getUserPids() != null && !project.getUserPids().equals("")) {
				String[] userPids = project.getUserPids().split(",");
				for (int i = 0; i < userPids.length; i++) {
					// 循环添加共同借款人
					ProjectPublicMan projectPublicMan = new ProjectPublicMan();
					projectPublicMan.setProjectId(projectId);// 将当前的项目ID赋值
					projectPublicMan.setPersonId(Integer.parseInt(userPids[i]));// 共同借款人PID
					projectPublicMan.setStatus(1);// 将状态设置为1正常
					// 新增共同借款人
					projectPublicManMapper.insert(projectPublicMan);
					
				}
				logger.info("********************pmuserid: "+project.getPmUserId()+"添加展期项目,保存共有人 userid : "+project.getUserPids()+" **********************");
			}
			//物业信息列表,将原项目的物业信息保存到展期的物业信息中
			//物业相关信息
			ProjectProperty property = newProject.getProjectProperty();
			property.setPid(projectPropertyMapper.getSeqBizProjectProperty());
			property.setProjectId(projectId);
			List<BizProjectEstate> estateList = newProject.getEstateList();
			String houseId = "";
			String houseName="";
			String housePropertyCard = "";
			double tranasctionMoney = 0.0;//成交价
			double evaluationPrice = 0.0;//评估价
			double area=0.0;//面积
			double costMoney = 0.0;//登记价
			double evaluationNet = 0.0;//评估净值
			if(estateList != null && estateList.size()>0){
				for(BizProjectEstate estate :estateList){
					estate.setProjectId(projectId);
					bizProjectEstateMapper.insert(estate);
					houseId +=estate.getHouseId()+",";
					houseName += estate.getHouseName()+",";
					housePropertyCard += estate.getHousePropertyCard()+",";
					tranasctionMoney += estate.getTranasctionMoney();
					evaluationPrice += estate.getEvaluationPrice();
					area +=estate.getArea();
					costMoney +=estate.getCostMoney();
					evaluationNet += estate.getEvaluationNet();
				}
				houseId = houseId.substring(0, houseId.length()-1);
				houseName = houseName.substring(0, houseName.length()-1);
				housePropertyCard = housePropertyCard.substring(0, housePropertyCard.length()-1);

				//设置多物业
				property.setHouseName(houseName);
				property.setHousePropertyCard(housePropertyCard);
				property.setTranasctionMoney(tranasctionMoney);
				property.setArea(area);
				property.setCostMoney(costMoney);
				property.setEvaluationPrice(evaluationPrice);
				property.setEvaluationNet(evaluationNet);
			}
			//转移原贷款信息
			List<BizOriginalLoan> originalLoanList = newProject.getOriginalLoanList();
			if(originalLoanList != null && originalLoanList.size()>0){
				for(BizOriginalLoan loan : originalLoanList){
					loan.setProjectId(projectId);
					bizOriginalLoanMapper.insert(loan);
				}
			}

			if (rows >= 1) {

				str = projectId + "," + project.getCreditId();// 代替老项目ID
				// 保存展期表数据
				ProjectExtension extendsion = new ProjectExtension();
				extendsion.setProjectId(projectId);
				extendsion.setExtensionProjectId(oldProjectId);
				extendsion.setStatus(1);
				loanExtensionMapper.insert(extendsion);

				//this.saveProjectSurveyReportToNew(projectId, oldProjectId);
				//展期原因数据保存
				ProjectSurveyReport surveyReport = new ProjectSurveyReport();
				surveyReport.setPid(projectSurveyReportMapper.getSeqBizProjectSurveyReport());
				surveyReport.setProjectId(projectId);
				surveyReport.setStatus(1);
				projectSurveyReportMapper.insert(surveyReport);
				
				//赎楼相关信息
				ProjectForeclosure pf = newProject.getProjectForeclosure();
				pf.setPid(projectForeclosureMapper.getSeqBizProjectForeclosure());
				pf.setProjectId(projectId);
				projectForeclosureMapper.insertForeclosure(pf);
				
				projectPropertyMapper.insertProperty(property);
				
				//费用信息
				ProjectGuarantee guarantee = newProject.getProjectGuarantee();
				guarantee.setPid(projectGuaranteeMapper.getSeqBizProjectGuarantee());
				guarantee.setProjectId(projectId);
				projectGuaranteeMapper.insertGuarantee(guarantee);
				
				// 更新流程信息
				//updateWorkflowStatus(oldProjectId, 4);

			} else {
				// 抛出异常处理
				throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "新增失败,请重新操作!");
			}
		} catch (ThriftServiceException e) {
			logger.error("展期申请:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "新增失败,请重新操作!"+ExceptionUtil.getExceptionMessage(e));
		} catch (Exception e) {
			logger.error("展期申请:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "新增失败,请重新操作!"+ExceptionUtil.getExceptionMessage(e));
		}
		logger.info("********************pmuserid: "+project.getPmUserId()+" 添加展期项目结束**********************");
		return str;
	}

	/**
	 * 
	 * @Description: 将旧项目的尽现调查报告 保存到新的项目中
	 * @param projectId
	 * @param oldProjectId
	 * @author: Rain.Lv
	 * @date: 2015年4月17日 下午3:44:36
	 */
	private void saveProjectSurveyReportToNew(int projectId, int oldProjectId) {
		// 根据项目ID查询尽现调查报告
		List<ProjectSurveyReport> surveyReports = projectSurveyReportMapper.getSurveyReportByProjectId(oldProjectId);
		if (null != surveyReports && surveyReports.size() > 0) {
			ProjectSurveyReport surveyReport = surveyReports.get(0);
			surveyReport.setPid(projectSurveyReportMapper.getSeqBizProjectSurveyReport());
			surveyReport.setProjectId(projectId);
			surveyReport.setStatus(1);
			projectSurveyReportMapper.insert(surveyReport);
		}else{
			ProjectSurveyReport surveyReport = new ProjectSurveyReport();
			surveyReport.setPid(projectSurveyReportMapper.getSeqBizProjectSurveyReport());
			surveyReport.setProjectId(projectId);
			surveyReport.setStatus(1);
			projectSurveyReportMapper.insert(surveyReport);
		}
	}

	/**
	 * 新增信用类展期信息
	 */
	@Override
	public int addExtensionInformation(Project project) throws ThriftServiceException, TException {
		logger.info("********************pmuserid: "+project.getPmUserId()+"保存展期基本信息开始**********************");
		int rows = 0;
		Project tempProject = null;
		try {
			// 1.修改项目信息
			tempProject = new Project();
			tempProject.setPid(project.getPid());
			tempProject.setProjectName(project.getProjectName());
			tempProject.setLoanAmt(project.getExtensionAmt()); // 展期金额就是贷款金额
			// 根据是否循环,判断当前项目的循环类型
			if (project.getIsHoop() == 2) {
				// 如果等于不可循环,就需要把循环类型置为空or0
				project.setCirculateType(0);// 循环类型
			}
			projectMapper.updateProjectName(tempProject);

			// 2.新增担保方式 or 修改担保方式
			// 2.1 先删除当前项目下面的所有担保方式,再添加
			projectGuaranteeTypeMapper.deleteProjectGuaranteeTypeByProjectId(project.getPid());
			// 获取新的担保方式
			String[] assWay = project.getAssWay().split(",");
			// 2.3循环添加担保方式
			for (int i = 0; i < assWay.length; i++) {
				if (null != assWay[i] && !"".equals(assWay[i])) {
					// 2.3.1 创建 担保方式 对象
					ProjectGuaranteeType projectGuaranteeType = new ProjectGuaranteeType();
					// 2.3.2 赋值
					projectGuaranteeType.setProjectId(project.getPid());// 设置项目ID
					projectGuaranteeType.setGuaranteeType(Integer.parseInt(assWay[i]));// 设置担保方式
					projectGuaranteeType.setStatus(1);// 状态都默认为1,正常状态
					// 2.3.3 新增
					projectGuaranteeTypeMapper.insert(projectGuaranteeType);
				}
			}
			// 2.4 删除没有选中的担保方式的数据
			if (project.getAssWayText().indexOf("抵押") == -1) {
				// 如果没有选择抵押,就删除当前项目的所有抵押数据
				projectAssBaseMapper.deleteProjectAssBaseByProjectId(project.getPid(), 1);
			}
			if (project.getAssWayText().indexOf("质押") == -1) {
				// 如果没有选择质押,就删除当前项目的所有质押数据
				projectAssBaseMapper.deleteProjectAssBaseByProjectId(project.getPid(), 2);
			}
			if (project.getAssWayText().indexOf("保证") == -1) {
				// 如果没有选择保证,就删除当前项目的所有保证数据
				projectAssureMapper.deleteProjectAssureByProjectId(project.getPid());
			}

			// 3.新增贷款信息
			// 3.1 创建贷款对象
			Loan loan = getLoanExtension(project);
			int pid = -1;
			// 5.3 判断是执行什么操作
			if (project.getLoanId() == -1) {
				pid = loanMapper.getSeqBizLoan();
				loan.setPid(pid);
				loanMapper.insert(loan);
			} else {
				pid = project.getLoanId();
				loan.setPid(pid);
				loanMapper.updateByPrimaryKey(loan);
			}

			// 展期申请,修改展期表数据
			ProjectExtensionDTO dto = new ProjectExtensionDTO();
			dto.setProjectId(project.getPid());
			// 根据项目ID查询展期信息
			List<ProjectExtensionView> views = loanExtensionMapper.getProjectExtensionList(dto);
			ProjectExtension extension = new ProjectExtension();
			extension.setPid(views.get(0).getPid());
			extension.setExtensionAmt(project.getExtensionAmt());
			extension.setSelectCycleNum(project.getExtensionNum());
			loanExtensionMapper.updateByPrimaryKey(extension);

			rows = pid;

			// 6.调用生成贷款试算信息的接口
			makeRepayMent(loan, 1, new CalcOperDto(0, 0, 0, 0, 0, "", 7, 0, "", project.getExtensionNum()));
		} catch (Exception e) {
			logger.error("保存展期申请详细信息异常:" + ExceptionUtil.getExceptionMessage(e));
			throw  new ThriftServiceException(ExceptionCode.CREDIT_LIMIT_QUERY, "保存失败,请重新操作!"+ExceptionUtil.getExceptionMessage(e));
		}
		logger.info("********************pmuserid: "+project.getPmUserId()+"保存展期基本信息结束**********************");
		return rows;
	}

	/**
	 * 添加赎楼展期信息
	  * @param project
	  * @return
	  * @throws ThriftServiceException
	  * @throws TException
	  * @author: Administrator
	  * @date: 2016年3月5日 上午10:54:13
	 */
	@Override
	public int addForeExtensionInformation(Project project) throws ThriftServiceException, TException{
		logger.info("********************pmuserid: "+project.getPmUserId()+"保存展期基本信息开始**********************");
		int loanId = -1;
		Project tempProject = null;
		try {
			// 1.修改项目信息
			tempProject = new Project();
			int projectId = project.getPid();
			tempProject.setPid(projectId);
			tempProject.setProjectName(project.getProjectName());
			tempProject.setLoanAmt(project.getExtensionAmt()); // 展期金额就是贷款金额
			projectMapper.updateProjectName(tempProject);
			
			// 展期申请,修改展期表数据
			ProjectExtensionDTO dto = new ProjectExtensionDTO();
			dto.setProjectId(projectId);
			// 根据项目ID查询展期信息
			List<ProjectExtensionView> views = loanExtensionMapper.getProjectExtensionList(dto);
			ProjectExtension extension = new ProjectExtension();
			extension.setPid(views.get(0).getPid());
			extension.setExtensionAmt(project.getExtensionAmt());
			extension.setSelectCycleNum(project.getExtensionNum());
			extension.setExtensionDate(project.getExtensionDate());
			extension.setExtensionFee(project.getExtensionFee());
			extension.setExtensionRate(project.getExtensionRate());
			extension.setExtensionDays(project.getExtensionDays());
			loanExtensionMapper.updateByPrimaryKey(extension);
			
			Project newProject = getLoanProjectByProjectId(projectId);
			
			//保存特殊情况
			ProjectSurveyReport surveyReport= projectSurveyReportServiceImpl.getSurveyReportByProjectId(projectId);
			if(surveyReport != null && surveyReport.getPid() >0){
				surveyReport.setSpecialDesc(project.getSpecialDesc());
				projectSurveyReportServiceImpl.updateSurveyReport(surveyReport);
			}else{
				ProjectSurveyReport projectSurveyReport = new ProjectSurveyReport();
				projectSurveyReport.setProjectId(projectId);
				projectSurveyReport.setSpecialDesc(project.getSpecialDesc());
				projectSurveyReportServiceImpl.addSurveyReport(projectSurveyReport );
			}
			
			//保存赎楼信息
			ProjectForeclosure projectForeclosure = newProject.getProjectForeclosure();
			if(projectForeclosure != null){
					//修改赎楼信息
				projectForeclosure.setExtensionFee(project.getExtensionFee());
				//projectForeclosure.setPaymentDate(project.getExtensionDate());
				projectForeclosure.setExtensionRate(project.getExtensionRate());
				projectForeclosureMapper.updateByPrimaryKey(projectForeclosure);
			}else{
				projectForeclosure = new ProjectForeclosure();
				int foreId = projectForeclosureMapper.getSeqBizProjectForeclosure();
				projectForeclosure.setPid(foreId);
				projectForeclosure.setProjectId(projectId);
				projectForeclosure.setExtensionFee(project.getExtensionFee());
				projectForeclosure.setExtensionRate(project.getExtensionRate());
				//projectForeclosure.setPaymentDate(project.getExtensionDate());
				projectForeclosureMapper.insertForeclosure(projectForeclosure);
			}
			
			//保存赎楼项目费用信息
			ProjectGuarantee projectGuarantee = newProject.getProjectGuarantee();
			if(projectGuarantee != null){
				projectGuarantee.setLoanMoney(project.getExtensionAmt());
				projectGuaranteeMapper.updateByPrimaryKey(projectGuarantee);
			}else{
				projectGuarantee = new ProjectGuarantee();
				int guaranteeId = projectGuaranteeMapper.getSeqBizProjectGuarantee();
				projectGuarantee.setPid(guaranteeId);
				projectGuarantee.setProjectId(projectId);
				projectGuarantee.setLoanMoney(project.getExtensionAmt());
				projectGuaranteeMapper.insertGuarantee(projectGuarantee);
			}
		} catch (Exception e) {
			logger.error("保存展期申请详细信息异常:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
			throw  new ThriftServiceException(ExceptionCode.CREDIT_LIMIT_QUERY, "保存失败,请重新操作!"+ExceptionUtil.getExceptionMessage(e));
		}
		return loanId;
	}
	
	
	/**
	 * 
	 * @Description: 根据页面传递过来的信息创建 贷款对象
	 * @param project
	 * @return Loan
	 * @author: Zhangyu.Hoo
	 * @date: 2015年4月13日 下午4:52:51
	 */
	private Loan getLoanExtension(Project project) {
		Loan loan = new Loan();
		// 5.2 赋值
		if (project.getRepayOption() != 0 && project.getRepayOptionTest() != 0) {
			loan.setRepayOption(3);// 说明两个都选中
		} else if (project.getRepayOption() != 0 && project.getRepayOptionTest() == 0) {
			loan.setRepayOption(2);// 其中一个选择
		} else if (project.getRepayOption() == 0 && project.getRepayOptionTest() != 0) {
			loan.setRepayOption(4);// 其中一个选择
		} else {
			loan.setRepayOption(1);// 都不选默认为0
		}
		loan.setEachissueOption(project.getEachissueOption());

		loan.setRepayDate(project.getRepayDate());
		loan.setProjectId(project.getPid());
		loan.setCreditAmt(project.getExtensionAmt()); // 展期金额就是贷款金额
		loan.setCurrency(project.getCurrency());
		loan.setDateMode(project.getDateMode());
		loan.setRepayFun(project.getRepayFun());
		loan.setRepayCycleType(project.getRepayCycleType());
		loan.setRepayCycle(project.getRepayCycle());
		loan.setPlanOutLoanDt(project.getPlanOutLoanDt());
		loan.setPlanRepayLoanDt(project.getPlanRepayLoanDt());
		// loan.setRepayOption(1);// 需要修改
		// loan.setRepayDate(15);// 需要修改
		loan.setMonthLoanInterest(project.getMonthLoanInterest());
		loan.setMonthLoanMgr(project.getMonthLoanMgr());
		loan.setMonthLoanOtherFee(project.getMonthLoanOtherFee());
		loan.setYearLoanInterest(project.getYearLoanInterest());
		loan.setYearLoanMgr(project.getYearLoanMgr());
		loan.setYearLoanOtherFee(project.getYearLoanOtherFee());
		loan.setDayLoanInterest(project.getDayLoanInterest());
		loan.setDayLoanMgr(project.getDayLoanMgr());
		loan.setDayLoanOtherFee(project.getDayLoanOtherFee());
		loan.setLiqDmgProportion(project.getLiqDmgProportion());
		loan.setOverdueLoanInterest(project.getOverdueLoanInterest());
		loan.setOverdueFineInterest(project.getOverdueFineInterest());
		loan.setMisFineInterest(project.getMisFineInterest());
		loan.setPrepayLiqDmgProportion(project.getPrepayLiqDmgProportion());
		loan.setFeesProportion(project.getFeesProportion());// 设置手续费
		loan.setStatus(1);
		loan.setJudgeRepayCycle(project.getJudgeRepayCycle());// 判断还款期限是否相等(1:不相等
																// -1:相等)
		return loan;
	}

	/**
	 * 
	 * @Description: 新增贷款和项目详情(额度提取)
	 * @param project
	 *            项目对象
	 * @return 受影响的行数--实际是返回的贷款ID
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月7日 下午5:01:45
	 */
	@Override
	public int addCreditsInformation(Project project) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 1.修改项目信息
			// 1.1 项目状态不需要更改,因为额度提取就一个状态,就是额度提取
			project.setProjectType(-1);
			// 根据是否循环,判断当前项目的循环类型
			if (project.getIsHoop() == 2) {
				// 如果等于不可循环,就需要把循环类型置为空or0
				project.setCirculateType(0);// 循环类型
			}
			projectMapper.updateByPrimaryKey(project);

			// 2.新增担保方式 or 修改担保方式
			// 2.1 先删除当前项目下面的所有担保方式,再添加
			projectGuaranteeTypeMapper.deleteProjectGuaranteeTypeByProjectId(project.getPid());
			// 获取新的担保方式
			String[] assWay = project.getAssWay().split(",");
			// 2.3循环添加担保方式
			for (int i = 0; i < assWay.length; i++) {
				// 2.3.1 创建 担保方式 对象
				ProjectGuaranteeType projectGuaranteeType = new ProjectGuaranteeType();
				// 2.3.2 赋值
				projectGuaranteeType.setProjectId(project.getPid());// 设置项目ID
				projectGuaranteeType.setGuaranteeType(Integer.parseInt(assWay[i]));// 设置担保方式
				projectGuaranteeType.setStatus(1);// 状态都默认为1,正常状态
				// 2.3.3 新增
				projectGuaranteeTypeMapper.insert(projectGuaranteeType);
			}
			// 2.4 删除没有选中的担保方式的数据
			if (project.getAssWayText().indexOf("抵押") == -1) {
				// 如果没有选择抵押,就删除当前项目的所有抵押数据
				projectAssBaseMapper.deleteProjectAssBaseByProjectId(project.getPid(), 1);
			}
			if (project.getAssWayText().indexOf("质押") == -1) {
				// 如果没有选择质押,就删除当前项目的所有质押数据
				projectAssBaseMapper.deleteProjectAssBaseByProjectId(project.getPid(), 2);
			}
			if (project.getAssWayText().indexOf("保证") == -1) {
				// 如果没有选择保证,就删除当前项目的所有保证数据
				projectAssureMapper.deleteProjectAssureByProjectId(project.getPid());
			}
			
			
			// 3.新增贷款信息
			// 3.1 创建贷款对象
			Loan loan = new Loan();
			// 3.2 赋值
			if (project.getRepayOption() != 0 && project.getRepayOptionTest() != 0) {
				loan.setRepayOption(3);// 说明两个都选中
			} else if (project.getRepayOption() != 0 && project.getRepayOptionTest() == 0) {
				loan.setRepayOption(2);// 其中一个选择
			} else if (project.getRepayOption() == 0 && project.getRepayOptionTest() != 0) {
				loan.setRepayOption(4);// 其中一个选择
			} else {
				loan.setRepayOption(1);// 都不选默认为0
			}
			
			// 3.3修改授信信息--根据项目ID
			// 3.3 创建授信对象
			Credit credit = new Credit();
			credit.setProjectId(project.getOldProjectId());
			credit.setCreditAmt(project.getCreditAmt());
			credit.setCredtiStartDt(project.getCredtiStartDt());
			credit.setCredtiEndDt(project.getCredtiEndDt());
			credit.setIsHoop(-1);
			// 根据项目状态判断授信状态
			if (project.getProjectType() == 2) {
				// 贷款项目,没有授信状态
				credit.setCreditStatus(0);
			} else {
				// 设置状态为1,表示有效,因为初始项目都是有效
				credit.setCreditStatus(1);
			}
			// 3.2 修改授信信息
			creditMapper.updateByProjectId(credit);
						
			loan.setEachissueOption(project.getEachissueOption());

			loan.setRepayDate(project.getRepayDate());
			loan.setProjectId(project.getPid());
			loan.setCreditAmt(project.getLoanAmt());
			loan.setCurrency(project.getCurrency());
			loan.setDateMode(project.getDateMode());
			loan.setRepayFun(project.getRepayFun());
			loan.setRepayCycleType(project.getRepayCycleType());
			loan.setRepayCycle(project.getRepayCycle());
			loan.setPlanOutLoanDt(project.getPlanOutLoanDt());
			loan.setPlanRepayLoanDt(project.getPlanRepayLoanDt());
			loan.setMonthLoanInterest(project.getMonthLoanInterest());
			loan.setMonthLoanMgr(project.getMonthLoanMgr());
			loan.setMonthLoanOtherFee(project.getMonthLoanOtherFee());
			loan.setYearLoanInterest(project.getYearLoanInterest());
			loan.setYearLoanMgr(project.getYearLoanMgr());
			loan.setYearLoanOtherFee(project.getYearLoanOtherFee());
			loan.setDayLoanInterest(project.getDayLoanInterest());
			loan.setDayLoanMgr(project.getDayLoanMgr());
			loan.setDayLoanOtherFee(project.getDayLoanOtherFee());
			loan.setLiqDmgProportion(project.getLiqDmgProportion());
			loan.setOverdueLoanInterest(project.getOverdueLoanInterest());
			loan.setOverdueFineInterest(project.getOverdueFineInterest());
			loan.setMisFineInterest(project.getMisFineInterest());
			loan.setPrepayLiqDmgProportion(project.getPrepayLiqDmgProportion());
			loan.setFeesProportion(project.getFeesProportion());// 设置手续费
			loan.setStatus(1);
			loan.setJudgeRepayCycle(project.getJudgeRepayCycle());// 判断还款期限是否相等(1:不相等
																	// -1:相等)
			int pid = -1;
			// 3.3 判断是执行什么操作
			if (project.getLoanId() == -1) {
				pid = loanMapper.getSeqBizLoan();
				loan.setPid(pid);
				loanMapper.insert(loan);
			} else {
				pid = project.getLoanId();
				loan.setPid(pid);
				loanMapper.updateByPrimaryKey(loan);
			}
			rows = pid;

			// 4.调用生成贷款试算信息的接口
			makeRepayMent(loan, 1, new CalcOperDto(0, 0, 0, 0, 0, "", 1, 0, "", 0));
		} catch (Exception e) {
			logger.error("新增授信和额度详细信息:" + ExceptionUtil.getExceptionMessage(e));
		}
		return rows;
	}

	/**
	 * 
	 * @Description: 查询单条单后信息
	 * @param projectId
	 * @return AfterLoanArchive
	 * @throws TException
	 * @author: xuweihao
	 * @date: 2015年4月26日 下午6:44:59
	 */
	@Override
   public AfterLoanArchive getAfterLoadFileOne(int projectId) throws TException {
		AfterLoanArchive afterLoanArchive = new AfterLoanArchive();
		afterLoanArchive = projectMapper.getAfterLoadFileOne(projectId);
		return afterLoanArchive == null ? new AfterLoanArchive() : afterLoanArchive;
	}
	
	/**
	 * 
	  * @Description: 获取已过期的授信ID
	  * @param creditEndDt
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年8月10日 下午1:57:39
	 */
	@Override
	public String getCreaditInvalidIds(String creditEndDt){
		Map<String,String> map = new HashMap<String,String>();
		map.put("creditEndDt", creditEndDt);
		String result = projectMapper.getCreaditInvalidIds(map);
		if(result == null){
			result = "";
		}
		return result;
	}

	/**
	 * 
	 * @Description: 批量设置授信项目为无效
	 * @param pids
	 *            项目ID(1,2,3)
	 * @return 受影响的行数
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年5月3日 上午9:10:01
	 */
	@Override
	public int updateProjectInvalid(String pids) throws ThriftServiceException, TException {
		// 返回受影响的行数
		int rows = 0;
		try {
			// 把传过来的数据劈开成一个数组作为参数传过去
			String[] strArr = pids.split(",");
			rows = projectMapper.updateProjectInvalid(strArr);
		} catch (Exception e) {
			logger.error("批量设置授信项目为无效:" + ExceptionUtil.getExceptionMessage(e));
		}
		return rows;
	}

	/**
	 * 
	 * @Description: 设置授信项目为有效
	 * @param projectId
	 *            需要修改为有效的项目ID
	 * @return 受影响的行数
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年5月3日 上午9:10:08
	 */
	@Override
	public int setProjectEffective(int projectId) throws ThriftServiceException, TException {
		// 返回受影响的行数
		int rows = 0;
		try {
			// 根据项目ID查看当前客户是否存在有效的授信项目,如果存在就不能再把其他项目置为有效的授信项目
			// 因为一个客户只能存在一个有效的授信项目
			int count = projectMapper.getCountProjectCredit(projectId);
			if (count > 0) {
				// 抛出异常处理
				throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "当前客户存在有效的授信项目,不能再给其他项目设置为有效的授信项目!");
			}
			// 根据项目ID设置当前的项目为有效
			rows = projectMapper.setProjectEffective(projectId);
		} catch (ThriftServiceException e) {
			logger.error("批量设置授信项目为有效:" + ExceptionUtil.getExceptionMessage(e));
			throw e;
		} catch (Exception e) {
			logger.error("批量设置授信项目为有效:" + ExceptionUtil.getExceptionMessage(e));
		}
		return rows;
	}

	/**
	 * 根据ID查询项目基本信息（信用类）
	 */
	@Override
	public Project getProjectById(int projectId) throws ThriftServiceException, TException {
		Project project = new Project();
		project = projectMapper.getProjectById(projectId);
		return project == null ? new Project() : project;
	}

	/**
	 * 
	 * @Description: 查询授信开始结束时间
	 * @param projectId
	 * @return
	 * @throws TException
	 * @author: Rain.Lv
	 * @date: 2015年5月28日 下午5:59:47
	 */
	@Override
	public Project getProjectCredtiEndDt(int projectId) throws TException {
		Project project = null;
		try {
			project = projectMapper.getProjectCredtiEndDt(projectId);
		} catch (Exception e) {
			logger.error("查询授信开始结束时间:" + ExceptionUtil.getExceptionMessage(e));
		}
		return project == null ? new Project() : project;
	}

	/**
	 * 
	 * @Description: 新增共同借款人
	 * @param projectPublicMan
	 *            共同借款人对象
	 * @return 受影响的行数
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年6月16日 下午4:09:36
	 */
	@Override
	public int addProjectPublicMan(ProjectPublicMan projectPublicMan) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 获取共有人ID
			String[] arr = projectPublicMan.getUserPids().split(",");
			// 循环添加数据
			for (int i = 0; i < arr.length; i++) {
				// 根据项目ID和共同借款人ID查询是否当前项目存在当前共同借款人
				int count = projectPublicManMapper.getCountByPersonIdAndProjectId(Integer.parseInt(arr[i]), projectPublicMan.getProjectId());
				// 只有不存在,才进行添加操作
				if (count == 0) {
					ProjectPublicMan p = new ProjectPublicMan();
					p.setPersonId(Integer.parseInt(arr[i]));
					p.setProjectId(projectPublicMan.getProjectId());
					p.setStatus(1);
					// 把传过来的数据劈开成一个数组作为参数传过去
					rows = projectPublicManMapper.insert(p);
				}else{
					rows+=1;
				}
			}
		} catch (Exception e) {
			logger.error("选择共同借款人:" + ExceptionUtil.getExceptionMessage(e));
			throw new ThriftServiceException("选择共同借款人", "选择共同借款人失败,请重新操作!");
		}
		return rows;
	}

	/**
	 * 
	 * @Description: 批量删除共同借款人
	 * @param userPids
	 *            共同借款人ID(1,2,3)
	 * @return 受影响的行数
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年6月16日 下午4:14:12
	 */
	@Override
	public int batchDeleteProjectPublicMan(String userPids) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 把传过来的数据劈开成一个数组作为参数传过去
			String[] strArr = userPids.split(",");
			rows = projectPublicManMapper.batchDelete(strArr);
		} catch (Exception e) {
			logger.error("删除共同借款人:" + ExceptionUtil.getExceptionMessage(e));
			throw new ThriftServiceException("删除共同借款人", "删除共同借款人失败,请重新操作!");
		}
		return rows;
	}

	/**
	 * 检查同一借款人，同金额，同日期放款
	 */
	@Override
	public Project getCheckAcctProject(Project secProject)
			throws ThriftServiceException, TException {
		Project project=null;
		try {
			project=projectMapper.getCheckAcctProject(secProject);
		} catch (Exception e) {
			logger.error("检查同一借款人，同金额，同日期放款 :" + ExceptionUtil.getExceptionMessage(e));
			throw new ThriftServiceException("检查同一借款人，同金额，同日期放款 ", "检查同一借款人，同金额，同日期放款 ,请重新操作!");
		}
		return project;
	}

	/**
	 * 修改项目名称
	 */
	@Override
	public int updateProjectName(Project project) throws ThriftServiceException,
			TException {
		int rows = 0;
		try {
			// 1.修改项目信息
			rows = projectMapper.updateProjectName(project);

		} catch (Exception e) {
			logger.error("修改项目名称:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
			throw  new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "保存失败,请重新操作!"+ExceptionUtil.getExceptionMessage(e));
		}
		
		return rows;
	}

	/**
	 * 新增赎楼项目相关信息
	 */
	@Override
	public String saveForeProject(Project project) throws ThriftServiceException,
			TException {
		int foreclosureId = 0;//赎楼ID
		int propertyId = 0;//物业ID
		int guaranteeId = 0;//担保ID
		String str = "";
		try {
			//保存赎楼信息
			ProjectForeclosure projectForeclosure = project.getProjectForeclosure();
			
			if(projectForeclosure != null){
				foreclosureId = projectForeclosure.getPid();
				if(foreclosureId>0){
					//修改赎楼信息
					projectForeclosureMapper.updateByPrimaryKey(projectForeclosure);
				}else{
					projectForeclosure.setProjectId(project.getPid());
					projectForeclosureMapper.insertForeclosure(projectForeclosure);
				}
			}
			//保存物业双方信息
			ProjectProperty projectProperty = project.getProjectProperty();
			if(projectProperty != null){
				propertyId = projectProperty.getPid();
				if(propertyId >0){
					//修改物业信息
					projectPropertyMapper.updateByPrimaryKey(projectProperty);
				}else{
					projectProperty.setProjectId(project.getPid());
					projectPropertyMapper.insertProperty(projectProperty);
				}
				//如果项目来源为万通，将填写的物业信息写入物业信息表中，原贷款信息写入原贷款信息表中
				int projectSource = project.getProjectSource();
				if(projectSource == Constants.PROJECT_SOURCE_WT){
					BizProjectEstate projectEstate =  new BizProjectEstate();
					List<BizProjectEstate> list = bizProjectEstateMapper.getAllByProjectId(project.getPid());
					if(list != null && list.size()>0){
						projectEstate = list.get(0);
					}
					projectEstate.setProjectId(project.getPid());
					projectEstate.setArea(projectProperty.getArea());
					projectEstate.setEvaluationPrice(projectProperty.getEvaluationPrice());
					projectEstate.setCostMoney(projectProperty.getCostMoney());
					projectEstate.setPurpose(projectProperty.getPurpose());
					projectEstate.setTranasctionMoney(projectProperty.getTranasctionMoney());
					projectEstate.setHouseName(projectProperty.getHouseName());
					projectEstate.setHousePropertyCard(projectProperty.getHousePropertyCard());
					bizProjectEstateMapper.update(projectEstate);
					
					BizOriginalLoan originalLoan = new BizOriginalLoan();
					BizOriginalLoan query = new BizOriginalLoan();
					query.setProjectId(project.getPid());
					List<BizOriginalLoan> loanList = bizOriginalLoanMapper.getAll(query );
					if(loanList != null && loanList.size()>0){
						originalLoan = loanList.get(0);
					}
					
					originalLoan.setProjectId(project.getPid());
					originalLoan.setOldLoanBank(projectForeclosure.getOldLoanBank());
					originalLoan.setOldOwedAmount(projectForeclosure.getOldOwedAmount());
					originalLoan.setOldLoanBankBranch(projectForeclosure.getOldLoanBankBranch());
					originalLoan.setOldLoanMoney(projectForeclosure.getOldLoanMoney());
					originalLoan.setOldLoanPerson(projectForeclosure.getOldLoanPerson());
					originalLoan.setOldLoanPhone(projectForeclosure.getOldLoanPhone());
					originalLoan.setOldLoanTime(projectForeclosure.getOldLoanTime());
					bizOriginalLoanMapper.update(originalLoan);
				}
			}
			
			//保存赎楼项目费用信息
			ProjectGuarantee projectGuarantee = project.getProjectGuarantee();
			if(projectGuarantee != null){
				guaranteeId = projectGuarantee.getPid();
				if(guaranteeId>0){
					//修改费用信息
					projectGuaranteeMapper.updateByPrimaryKey(projectGuarantee);
				}else{
					projectGuarantee.setProjectId(project.getPid());
					projectGuaranteeMapper.insertGuarantee(projectGuarantee);
				}
			}
		} catch (Exception e) {
			logger.error("新增赎楼项目基本信息:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
			throw  new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "保存失败,请重新操作!"+ExceptionUtil.getExceptionMessage(e));
		}
		str = foreclosureId+","+propertyId+","+guaranteeId;
		
		return str;
	}

	/**
	 * 保存赎楼费用信息
	 */
	@Override
	public int addGuarantee(Project project) throws ThriftServiceException,
			TException {
		int result = 0;
		try {
			// 2.新增担保方式 or 修改担保方式
			// 2.1 先删除当前项目下面的所有担保方式,再添加
			projectGuaranteeTypeMapper.deleteProjectGuaranteeTypeByProjectId(project.getPid());
			// 2.2获取新的担保方式
			String[] assWay = project.getAssWay().split(",");
			// 2.3循环添加担保方式
			for (int i = 0; i < assWay.length; i++) {
				// 2.3.1 创建 担保方式 对象
				ProjectGuaranteeType projectGuaranteeType = new ProjectGuaranteeType();
				// 2.3.2 赋值
				projectGuaranteeType.setProjectId(project.getPid());// 设置项目ID
				projectGuaranteeType.setGuaranteeType(Integer.parseInt(assWay[i]));// 设置担保方式
				projectGuaranteeType.setStatus(1);// 状态都默认为1,正常状态
				// 2.3.3 新增
				projectGuaranteeTypeMapper.insert(projectGuaranteeType);
			}
			// 2.4 删除没有选中的担保方式的数据
			if (project.getAssWayText().indexOf("抵押") == -1) {
				// 如果没有选择抵押,就删除当前项目的所有抵押数据
				projectAssBaseMapper.deleteProjectAssBaseByProjectId(project.getPid(), 1);
			}
			if (project.getAssWayText().indexOf("质押") == -1) {
				// 如果没有选择质押,就删除当前项目的所有质押数据
				projectAssBaseMapper.deleteProjectAssBaseByProjectId(project.getPid(), 2);
			}
			if (project.getAssWayText().indexOf("保证") == -1) {
				// 如果没有选择保证,就删除当前项目的所有保证数据
				projectAssureMapper.deleteProjectAssureByProjectId(project.getPid());
			}
			result = project.getPid();
		} catch (Exception e) {
			logger.error("保存担保信息:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据ID查询biz_project表数据
	 */
   @Override
   public Project getProjectInfoById(int projectId) throws ThriftServiceException, TException {
      return projectMapper.getProjectInfoById(projectId);
   }

   /**
    * 分页查询赎楼业务列表
    */
   @Override
   public List<GridViewDTO> getAllForeProjects(Project project) throws TException {
	   List<GridViewDTO> list = new ArrayList<GridViewDTO>();
	   try {
			// 修改申请开始时间格式
			if (null != project.getBeginRequestDttm() && !"".equals(project.getBeginRequestDttm())) {
				String beginString = project.getBeginRequestDttm();
				Date d = DateUtil.format(beginString, "yyyy-MM-dd");
				beginString = new StringBuffer().append(DateUtil.format(d, "yyyy-MM-dd").trim()).append(" 00:00:01").toString();
				project.setBeginRequestDttm(beginString);
			}
			// 修改申请结束时间格式
			if (null != project.getEndRequestDttm() && !"".equals(project.getEndRequestDttm())) {
				String endString = project.getEndRequestDttm();
				Date d = DateUtil.format(endString, "yyyy-MM-dd");
				endString = new StringBuffer().append(DateUtil.format(d, "yyyy-MM-dd").trim()).append(" 23:59:59").toString();
				project.setEndRequestDttm(endString);
			}
			list = projectMapper.getAllForeProjects(project);
		} catch (Exception e) {
			logger.error("查询贷前列表:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
			}
		return list;
   }

   /**
    * 查询赎楼业务总数
    */
	@Override
	public int getAllForeProjectCount(Project project) throws TException {
		int count = 0;
		try {
			// 修改申请开始时间格式
			if (null != project.getBeginRequestDttm() && !"".equals(project.getBeginRequestDttm())) {
				String beginString = project.getBeginRequestDttm();
				Date d = DateUtil.format(beginString, "yyyy-MM-dd");
				beginString = new StringBuffer().append(DateUtil.format(d, "yyyy-MM-dd").trim()).append(" 00:00:01").toString();
				project.setBeginRequestDttm(beginString);
			}
			// 修改申请结束时间格式
			if (null != project.getEndRequestDttm() && !"".equals(project.getEndRequestDttm())) {
				String endString = project.getEndRequestDttm();
				Date d = DateUtil.format(endString, "yyyy-MM-dd");
				endString = new StringBuffer().append(DateUtil.format(d, "yyyy-MM-dd").trim()).append(" 23:59:59").toString();
				project.setEndRequestDttm(endString);
			}
			count = projectMapper.getForeProjectCount(project);
		} catch (Exception e) {
			logger.info("查询贷前列表:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
			
		}
		return count;
	}
	/**
	 * 项目撤单
	 *@author:liangyanjun
	 *@time:2016年5月28日上午9:47:24
	 *@param project
	 *@return
	 */
	@Override
	public boolean updateProjectChechan(Project project) throws ThriftServiceException,
			TException {
		try {
			int rows = projectMapper.updateProjectChechan(project);
			if(rows >0){
				BizDynamic bizDynamic = new BizDynamic();
				bizDynamic.setProjectId(project.getPid());
				bizDynamic.setHandleAuthorId(project.getChechanUserId());
				bizDynamic.setStatus(BizDynamicConstant.STATUS_3);
				bizDynamic.setRemark(project.getChechanCause());
				bizDynamic.setModuelNumber(BizDynamicConstant.MODUEL_NUMBER_OTHER);
				bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_OTHER_10);//撤单操作
				bizDynamic.setDynamicName(BizDynamicConstant.DYNAMIC_NUMBER_OTHER_MAP.get(BizDynamicConstant.DYNAMIC_NUMBER_OTHER_10));
				bizDynamic.setFinishDate(DateUtils.getCurrentDateTime());
				bizDynamicService.addBizDynamic(bizDynamic);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("项目撤单失败,入参："+project+"," + ExceptionUtil.getExceptionMessage(e));
			throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "修改失败,请重新操作!");
		}
	   return true;
	}
	
	/**
	 * 批量恢复赎楼项目撤单
	 */
	@Override
	public int setProjectChechan(String pids) throws ThriftServiceException,
			TException {
		// 返回受影响的行数
		int rows = 0;
		try {
			// 把传过来的数据劈开成一个数组作为参数传过去
			String[] strArr = pids.split(",");
			rows = projectMapper.setProjectChechan(strArr);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("批量恢复赎楼项目撤单:" + ExceptionUtil.getExceptionMessage(e));
			throw  new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "批量恢复赎楼项目撤单失败,请重新操作!"+ExceptionUtil.getExceptionMessage(e));
		}
		return rows;
	}
	
	/**
	 * 修改赎楼项目状态
	 */
	@Override
	public int updateProjectForeStatusByPid(int pid, int foreclosureStatus)
			throws ThriftServiceException, TException {
		int rows = 0;
		try {
			rows = projectMapper.updateProjectForeStatusByPid(pid, foreclosureStatus, "");
		} catch (Exception e) {
			logger.error("修改项目状态:" + ExceptionUtil.getExceptionMessage(e));
			throw  new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "修改项目状态失败,请重新操作!"+ExceptionUtil.getExceptionMessage(e));
		}
		return rows;
	}

	/**
	 * 获取贷款金额
	  * @param projectId
	  * @return
	  * @author: Administrator
	 * @throws TException 
	  * @date: 2016年2月19日 下午4:50:18
	 */
	@Override
	public String findProjectLoanMoney(int projectId) throws TException{
		String result ="0.00";
		try {
			double loanMoney=0.0;
			if(projectId >0){
				ProjectGuarantee projectGuarantee = projectGuaranteeMapper.getGuaranteeByProjectId(projectId);
				if(projectGuarantee != null){
					loanMoney = projectGuarantee.getLoanMoney();
				}
			}
			result = NumberUtils.formatDecimal(String.valueOf(loanMoney));
		} catch (Exception e) {
			logger.error("查询项目贷款金额出错:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return result;
	}
	
	/**
	 * 获取项目ID以及项目名称的列表，以前用于要件借出的下拉列表
	 */
	@Override
	public List<Project> findProjectInfo(Project project) throws TException {
			List<Project> list = new ArrayList<Project>();
			try {
				list = projectMapper.findProjectInfo(project);
			} catch (Exception e) {
				logger.error("查询项目列表出错:" + ExceptionUtil.getExceptionMessage(e));
				throw new TException(ExceptionUtil.getExceptionMessage(e));
			}
		return list;
	}

	/**
	 * 出账单打印功能，查询相关项目信息
	 */
	@Override
	public ProjectDto printProjectInfo(int projectId) throws TException {
		ProjectDto projectDto = projectMapper.printProjectInfo(projectId);
		
		projectDto.setLoanMoneyStr(formatNumber(projectDto.getLoanMoney()));
		projectDto.setPromiseMoneyStr(formatNumber(projectDto.getPromiseMoney()));
		projectDto.setPaymentMoneyStr(formatNumber(projectDto.getPaymentMoney()));
		
		int projectSource = projectDto.getProjectSource();
		double loanFee = projectDto.getLoanFee();
		double poundage = projectDto.getPoundage();
		double brokerage = projectDto.getBrokerage();
		double result = loanFee+poundage+brokerage;
		long value = Math.round(result*100);
		String chargeSituation;
      if (projectSource==Constants.PROJECT_SOURCE_WT) {
         chargeSituation = "咨询费+手续费=" + loanFee + " + " + poundage + " = " + value / 100.0;
      }else{
         chargeSituation = "咨询费+手续费+佣金=" + loanFee + " + " + poundage +" + " + brokerage + " = " + value / 100.0;
      }
		projectDto.setChargeSituation(chargeSituation);
		return projectDto;
	}

	/**
	 * 金额格式化
	 * @param money
	 * @return
	 */
	private String formatNumber(double money){
		//BigDecimal decimal = NumberUtils.parseDecimal(String.valueOf(money));
		NumberFormat nf = new DecimalFormat("#,###.##");
		String format = nf.format(money);
		return format;
	}
	
   /**
    * 修改收件状态
    *@author:liangyanjun
    *@time:2016年3月12日下午11:03:44
    *@param pid
    *@param foreclosureStatus
    *@return
    */
	@Override
	 public int updatecollectFileStatusByPid(int pid, int collectFileStatus){
	    return projectMapper.updatecollectFileStatusByPid(pid, collectFileStatus);
	 }

	/**
	 * 查询赎楼清单列表
	 */
	@Override
	public List<ProjectForeInformation> queryForeInformations(int projectId)
			throws TException {
		List<ProjectForeInformation> result = foreInformationMapper.queryForeInformations(projectId);
		if(result == null){
			result = new ArrayList<ProjectForeInformation>();
		}
		return result;
	}

	/**
	 * 添加项目赎楼清单信息
	 */
	@Override
	public int addProjectForeInformation(ProjectForeInformation projectForeInformation)
			throws ThriftServiceException, TException {
		int row = 0;
		try {
			
			row = foreInformationMapper.insertProjectForeInformation(projectForeInformation);
			
		} catch (Exception e) {
			logger.error("添加项目赎楼清单信息出错:" + ExceptionUtil.getExceptionMessage(e));
			 e.printStackTrace();
	         throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return row;
	}

	/**
	 * 修改项目赎楼清单信息
	 */
	@Override
	public int updateProjectForeInformation(List<ProjectForeInformation> projectForeInformations)
			throws ThriftServiceException, TException {
		int row = 0;
		try {
			if(projectForeInformations != null){
				int projectId = projectForeInformations.get(0).getProjectId();
				List<ProjectForeInformation> result = foreInformationMapper.queryForeInformations(projectId);
				if(result != null && result.size()>0){
					for(ProjectForeInformation pf:projectForeInformations){
						row += foreInformationMapper.updateProjectForeInformation(pf);
					}
				}else{
					for(ProjectForeInformation pf:projectForeInformations){
						row += foreInformationMapper.insertProjectForeInformation(pf);
					}
				}
			}
		} catch (Exception e) {
			logger.error("修改项目赎楼清单信息出错:" + ExceptionUtil.getExceptionMessage(e));
			 e.printStackTrace();
	         throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return row;
	}

	/**
	 * 保存审查员意见
	 */
	@Override
	public int updateAuditorOpinion(Project project) throws TException {
		int result = 0;
		try {
			if(project != null && project.getPid() >0){
				result= projectMapper.updateAuditorOpinionByPid(project.getPid(), project.getAuditorOpinion());
			}
		} catch (Exception e) {
			logger.error("修改审查员意见出错:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
	        throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return result;
	}
	
	/**
	 * 保存APP端项目信息（除第一页的信息）
	 */
	@Override
	@Transactional
	public int saveProjectInfoByMobile(Project project, String param)throws TException {
		int projectId = project.getPid();
		try {
			projectMapper.updateByPrimaryKey(project);//修改业务信息
			projectGuaranteeMapper.updateByPrimaryKey(project.getProjectGuarantee());//修改费用信息
			projectForeclosureMapper.updateByPrimaryKey(project.getProjectForeclosure());//修改赎楼信息
			projectPropertyMapper.updateByPrimaryKey(project.getProjectProperty());//修改物业信息

			//保存特殊情况
			ProjectSurveyReport surveyReport= projectSurveyReportServiceImpl.getSurveyReportByProjectId(projectId);
			if(surveyReport != null && surveyReport.getPid() >0){
				surveyReport.setSpecialDesc(project.getSpecialDesc());
				projectSurveyReportServiceImpl.updateSurveyReport(surveyReport);
			}else{
				ProjectSurveyReport projectSurveyReport = new ProjectSurveyReport();
				projectSurveyReport.setProjectId(projectId);
				projectSurveyReport.setSpecialDesc(project.getSpecialDesc());
				projectSurveyReportServiceImpl.addSurveyReport(projectSurveyReport );
			}
			
			JSONObject params = JSONObject.parseObject(param); 
			// 修改赎楼清单信息
			JSONArray foreJson = params.getJSONArray("foreInfos");
			List<ProjectForeInformation> foreInfoList = new ArrayList<ProjectForeInformation>();
			for (int i = 0; i < foreJson.size(); i++) {
				ProjectForeInformation info = new ProjectForeInformation();
				JSONObject foreMap = foreJson.getJSONObject(i);
				int pid = (Integer) foreMap.get("fore_id");
	
				int originalNumber = (Integer) foreMap.get("original_number");
				int copyNumber = (Integer) foreMap.get("copy_number");
				String remark = (String) foreMap.get("remark");
				info.setForeId(pid);
				info.setProjectId(projectId);
				info.setOriginalNumber(originalNumber);
				info.setCopyNumber(copyNumber);
				info.setRemark(remark);
				foreInfoList.add(info);
			}
			updateProjectForeInformation(foreInfoList);
			// 修改申请办理信息
			ApplyHandleInfoDTO query = new ApplyHandleInfoDTO();
			query.setProjectId(projectId);
			query.setPage(1);
			query.setRows(100);
			List<ApplyHandleInfoDTO> resultList = bizHandleMapper
					.findAllApplyHandleInfo(query);
			ApplyHandleInfoDTO applyHandleInfoDTO = null;
			if (resultList != null && resultList.size() > 0) {
				applyHandleInfoDTO = resultList.get(0);
			} else {
				applyHandleInfoDTO = new ApplyHandleInfoDTO();
				applyHandleInfoDTO.setProjectId(projectId);
			}
			String handleDate = (String) params.get("handleDate");
			String contactPerson = (String) params.get("contactPerson");
			String contactPhone = (String) params.get("contactPhone");
			String specialCase = (String) params.get("specialCase");
			String remark = (String) params.get("remark");
	
			applyHandleInfoDTO.setHandleDate(handleDate);
			applyHandleInfoDTO.setContactPerson(contactPerson);
			applyHandleInfoDTO.setContactPhone(contactPhone);
			applyHandleInfoDTO.setRemark(remark);
			applyHandleInfoDTO.setSpecialCase(specialCase);
			applyHandleInfoDTO.setCreaterId(project.getPmUserId());
			if (applyHandleInfoDTO.getPid() > 0) {
				bizHandleMapper.updateApplyHandleInfo(applyHandleInfoDTO);
			} else {
				bizHandleMapper.addApplyHandleInfo(applyHandleInfoDTO);
			}
		} catch (Exception e) {
			logger.error("保存项目信息出错:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
	        throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return projectId;
	}

	/**
	 * 根据客户ID，借款天数，借款金额以及物业名称等信息判断是否是重复的单
	 */
	@Override
	public int checkProjectAcct(Project project) throws ThriftServiceException,TException {
		int rows = 0;
		try {
			int acctId = project.getAcctId();
			int loanDays = project.getProjectForeclosure().getLoanDays();
			double loanMoney = project.getProjectGuarantee().getLoanMoney();
			double guaranteeFee = project.getProjectGuarantee().getGuaranteeFee();
			String houseName = project.getProjectProperty().getHouseName();
			if(acctId >0 && loanDays>0 && loanMoney>0 && guaranteeFee>0 && !StringUtil.isBlank(houseName) ){
				rows = projectMapper.getProjectAcctCount(acctId,loanDays,loanMoney,guaranteeFee,houseName);
			}
		} catch (Exception e) {
			logger.error("校验重复项目信息出错:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
	        throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return rows;
	}

	/**
	* 根据项目id 查询物业买卖双方信息
	*@author:liangyanjun
	*@time:2016年5月4日上午11:25:55
	*/
	@Override
	public ProjectProperty getPropertyByProjectId(int projectId) throws TException {
		ProjectProperty projectProperty = projectPropertyMapper.getPropertyByProjectId(projectId);
	    return projectProperty==null?new ProjectProperty():projectProperty;
	}
	
	/**
	 * 一键保存项目信息   
	 */
	@Override
	@Transactional
	public String saveProjectInfo(Project project)throws ThriftServiceException, TException {
		int projectId = project.getPid();
		String str="";
		try {
			if (projectId > 0) {
				projectMapper.updateByPrimaryKey(project);
				//删除共同关系人
				projectPublicManMapper.deleteByProjectId(projectId);
				
				// 判断是否选取共同借款人
				if (project.getUserPids() != null && !project.getUserPids().equals("")) {
					String[] userPids = project.getUserPids().split(",");
					for (int i = 0; i < userPids.length; i++) {
						// 循环添加共同借款人
						ProjectPublicMan projectPublicMan = new ProjectPublicMan();
						projectPublicMan.setProjectId(projectId);// 将当前的项目ID赋值
						projectPublicMan.setPersonId(Integer.parseInt(userPids[i]));// 共同借款人PID
						projectPublicMan.setStatus(1);// 将状态设置为1正常
						// 新增共同借款人
						projectPublicManMapper.insert(projectPublicMan);
					}
				}
				
				//保存赎楼信息
				String result = saveForeProject(project);
				
				//保存特殊情况
				ProjectSurveyReport surveyReport= projectSurveyReportServiceImpl.getSurveyReportByProjectId(projectId);
				if(surveyReport != null && surveyReport.getPid() >0){
					surveyReport.setSpecialDesc(project.getSpecialDesc());
					projectSurveyReportServiceImpl.updateSurveyReport(surveyReport);
				}else{
					ProjectSurveyReport projectSurveyReport = new ProjectSurveyReport();
					projectSurveyReport.setProjectId(projectId);
					projectSurveyReport.setSpecialDesc(project.getSpecialDesc());
					projectSurveyReportServiceImpl.addSurveyReport(projectSurveyReport );
				}
				str = projectId + ","+project.getCreditId()+","+project.getProjectName()+","+project.getProjectNumber()+","+result;
				
			} else {
				//检查重复
				if(checkProjectAcct(project) > 0){
					throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "不能提交重复的项目!");
				}else{
					str = addProject(project);
					projectId = project.getPid();
					//保存特殊情况
					ProjectSurveyReport projectSurveyReport = new ProjectSurveyReport();
					projectSurveyReport.setProjectId(projectId);
					projectSurveyReport.setSpecialDesc(project.getSpecialDesc());
					projectSurveyReportServiceImpl.addSurveyReport(projectSurveyReport );
				}
			}
		} catch (Exception e) {
			logger.error("保存项目信息出错:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
	        throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		
		return str;
	}

   /**
	* 修改退件完结状态
	*@author:liangyanjun
	*@time:2016年5月16日下午4:29:05
	*/
   @Override
   public int updaterefundFileStatusByPid(int pid, int refundFileStatus) throws ThriftServiceException, TException {
      return projectMapper.updaterefundFileStatusByPid(pid, refundFileStatus);
   }

   /**
    * 查询赎楼清单打印信息
    */
	@Override
	public ProjectDto printForeInfo(int projectId) throws TException {
		ProjectDto projectDto = projectMapper.printForeInfo(projectId);
		projectDto.setLoanMoneyStr(formatNumber(projectDto.getLoanMoney()));
		return projectDto;
	}

	/**
	 * 解保
	 * 备注：如果是机构提的单，则会涉及到息费数据的更新
	 *@author:liangyanjun
	 *@time:2016年8月12日下午4:02:07
	 */
   @Override
   @Transactional
   public int cancelGuarantee(int pid,String cancelGuaranteeDate) throws ThriftServiceException, TException {
      projectMapper.updateCancelGuaranteeByPid(pid,cancelGuaranteeDate);
      Project project = projectMapper.getProjectInfoById(pid);
      int projectType = project.getProjectType();
      if (projectType==AomConstant.PROJECT_TYPE_6) {//项目类型是机构的时候
         OrgFeeSettleDetail orgFeeSettleDetail = orgFeeSettleDetailMapper.getByProjectId(pid);
         if (orgFeeSettleDetail==null) {//为空说明是当月放款并且是当月解保
            return 1;
         }
         //不为空的时候，说明详情数据已经在月初生成，此时解保把回款金额，时间，解保时间更新到息费详情表
         //查询退费金额
         RefundFeeDTO refundFeeQuery=new RefundFeeDTO();
         refundFeeQuery.setProjectId(pid);
         List<RefundFeeDTO> refundFeeList = refundFeeMapper.findAllRefundFee(refundFeeQuery);
         double returnFee =0;
         for (RefundFeeDTO dto : refundFeeList) {
            if (dto.getType()==Constants.REFUND_FEE_TYPE_1) {
               continue;
            }
            returnFee=returnFee+dto.getConfirmMoney();//退费金额
         }
         RepaymentDTO repayment = ForeclosureRepaymentMapper.getRepaymentByProjectId(pid);
         orgFeeSettleDetail.setPaymentMoney(repayment.getRepaymentMoney());
         orgFeeSettleDetail.setPaymentDate(repayment.getNewRepaymentDate());
         orgFeeSettleDetail.setSolutionDate(cancelGuaranteeDate);
         orgFeeSettleDetail.setRefund(returnFee);
         orgFeeSettleDetailMapper.update(orgFeeSettleDetail);
      }
      return 1;
   }

   /**
    * 根据项目id获取赎楼费用信息
    *@author:liangyanjun
    *@time:2016年10月18日下午12:19:46
    *
    */
    @Override
    public ProjectGuarantee getGuaranteeByProjectId(int projectId) throws TException {
        ProjectGuarantee projectGuarantee = projectGuaranteeMapper.getGuaranteeByProjectId(projectId);
        if (projectGuarantee==null) {
            return new ProjectGuarantee();
        }
        return projectGuarantee;
    }

    /**
     * 根据项目id获取赎楼信息
     *@author:liangyanjun
     *@time:2016年10月18日下午2:51:02
     *
     */
    @Override
    public ProjectForeclosure getForeclosureByProjectId(int projectId) throws TException {
        ProjectForeclosure projectForeclosure = projectForeclosureMapper.getForeclosureByProjectId(projectId);
        if (projectForeclosure==null) {
            return new ProjectForeclosure();
        }
        return projectForeclosure;
    }
    
    /**
     * 回撤业务申请
     */
	@Override
	public int retreatProject(int oldProjectId,int retreatUserId) throws TException {
		int rows=0;
		try {
			Project project = projectMapper.getProjectInfoById(oldProjectId);//原项目信息
			
			int productType = project.getProductType();//产品类型，不入库
			// 获取新的项目ID
			int newProjectId = projectMapper.getSeqBizProject();
			// 赋值新的项目ID
			project.setPid(newProjectId);
			
			// 把以前的项目名称赋值
			String projectName = makeRetreatProjectName(project.getProjectName(),project.getAcctId());
			// 生成项目编号
			String projectNumber = makeProjectNumber(productType);
			// 赋值项目ID和项目编号
			project.setProjectName(projectName);
			project.setProjectNumber(projectNumber);
			//设置赎楼状态为1（未提交）
			project.setForeclosureStatus(1);
			project.setIsChechan(0);//未撤单
			
			// 设置申请状态为1(申请中) 状态为1(正常) 申请时间为当前时间
			project.setRequestStatus(1);
			project.setStatus(1);
			project.setRequestDttm(DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
			project.setProjectType(2);// 项目类型（授信=1、贷款=2、额度提取=3、贷款展期=4 授信and贷款=5）
			//新项目入库
			rows = projectMapper.insertProjectInfo(project);
			
			// 项目关系表插入数据
			ProjectRelation projectRelation = new ProjectRelation();
			projectRelation.setProjectId(newProjectId);// 新项目ID
			projectRelation.setRefProjectId(oldProjectId);// 关联项目Id（原项目ID）
			projectRelation.setRefType(3);// 关联类型(1:授信,2:展期,3:撤单）
			projectRelation.setStatus(1);
			projectRelationMapper.insert(projectRelation);

			// 插入项目历史记录表
			ProjectRecord projectRecord = new ProjectRecord();
			projectRecord.setCompleteDttm(DateUtil.format(new Date()));
			projectRecord.setProcessType(1);
			projectRecord.setProcessUserId(retreatUserId);
			projectRecord.setProjectId(newProjectId);
			projectMapper.addProjectRecord(projectRecord);

			//查询共同借款人信息
			List<ProjectPublicMan> publicManList =  projectPublicManMapper.getAllProjectPublicMan(oldProjectId);
			
			// 判断是否选取共同借款人
			if (publicManList != null && publicManList.size()>0) {
				for (int i = 0; i < publicManList.size(); i++) {
					// 循环添加共同借款人
					ProjectPublicMan projectPublicMan = publicManList.get(i);
					projectPublicMan.setProjectId(newProjectId);// 将当前的项目ID赋值
					// 新增共同借款人
					projectPublicManMapper.insert(projectPublicMan);
				}
				logger.info("********************pmuserid: "+retreatUserId+"添加回撤项目,保存共有人,projectName:+"+projectName);
			}

			if (rows >= 1) {

				// 保存回撤关系表数据
				BizProjectRetreat projectRetreat = new BizProjectRetreat();
				projectRetreat.setNewProjectId(newProjectId);
				projectRetreat.setOldProjectId(oldProjectId);
				projectRetreat.setRetreatUserId(retreatUserId);
				bizProjectRetreatMapper.insert(projectRetreat);

				//特殊情况说明数据保存
				ProjectSurveyReport surveyReport = projectSurveyReportServiceImpl.getSurveyReportByProjectId(oldProjectId);
				surveyReport.setPid(projectSurveyReportMapper.getSeqBizProjectSurveyReport());
				surveyReport.setProjectId(newProjectId);
				surveyReport.setStatus(1);
				projectSurveyReportMapper.insert(surveyReport);
				
				//赎楼相关信息
				ProjectForeclosure pf = projectForeclosureMapper.getForeclosureByProjectId(oldProjectId);
				pf.setPid(projectForeclosureMapper.getSeqBizProjectForeclosure());
				pf.setProjectId(newProjectId);
				projectForeclosureMapper.insertForeclosure(pf);
				
				//物业相关信息
				ProjectProperty property = projectPropertyMapper.getPropertyByProjectId(oldProjectId);
				property.setPid(projectPropertyMapper.getSeqBizProjectProperty());
				property.setProjectId(newProjectId);
				projectPropertyMapper.insertProperty(property);
				
				//费用信息
				ProjectGuarantee guarantee = projectGuaranteeMapper.getGuaranteeByProjectId(oldProjectId);
				guarantee.setPid(projectGuaranteeMapper.getSeqBizProjectGuarantee());
				guarantee.setProjectId(newProjectId);
				projectGuaranteeMapper.insertGuarantee(guarantee);
				//上传文件资料
				List<DataInfo> fileList = dataUploadMapper.findProjectFiles(oldProjectId);
				if(fileList != null && fileList.size()>0){
					for(int i=0;i<fileList.size();i++){
						DataInfo dataInfo = fileList.get(i);
						dataInfo.setProjectId(newProjectId);
						dataUploadMapper.saveData(dataInfo);
					}
				}
				//复制物业信息
				List<BizProjectEstate> estateList = bizProjectEstateMapper.getAllByProjectId(oldProjectId);
				if(estateList != null && estateList.size()>0){
					for(BizProjectEstate estate : estateList){
						estate.setProjectId(newProjectId);
						bizProjectEstateMapper.insert(estate);
					}
				}
				//复制原贷款银行信息
				BizOriginalLoan bizOriginalLoan  = new BizOriginalLoan();
				bizOriginalLoan.setStatus(1);
				bizOriginalLoan.setProjectId(oldProjectId);
				List<BizOriginalLoan> originalLoanList = bizOriginalLoanMapper.getAllByCondition(bizOriginalLoan);
				if(originalLoanList != null && originalLoanList.size()>0){
					for(BizOriginalLoan loan : originalLoanList){
						loan.setProjectId(newProjectId);
						bizOriginalLoanMapper.insert(loan);
					}
				}
				
				//添加赎楼清单
				List<SysLookupVal> result = sysLookupValMapper.getSysLookupValByLookType("PROJECT_FORE_INFORMATION");
				if(result != null && result.size()>0){
					for(SysLookupVal lookUp : result){
						ProjectForeInformation foreInfo = new ProjectForeInformation();
						foreInfo.setForeId(lookUp.getPid());
						foreInfo.setProjectId(newProjectId);
						foreInformationMapper.insertProjectForeInformation(foreInfo);
					}
				}
				
				
			} else {
				// 抛出异常处理
				throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "新增失败,请重新操作!");
			}
		} catch (Exception e) {
			logger.error("回撤业务申请:" + ExceptionUtil.getExceptionMessage(e));
			throw new RuntimeException("回撤业务申请!"+ExceptionUtil.getExceptionMessage(e));
		}
		return rows;
	}

	/**
	 * 万通推送小科业务功能
	 */
	@Override
	public int updateProjectNeed(Project project) throws TException {
		return projectMapper.updateProjectNeed(project);
	}

	/**
	 * 修改赎楼贷后监控状态：无异常=1，有异常=2,异常转正常=3
	 *@author:liangyanjun
	 *@time:2017年1月16日下午3:25:16
	 */
    @Override
    public int updateForeAfterMonitorStatus(int projectId, int foreAfterMonitorStatus) throws TException {
        projectMapper.updateForeAfterMonitorStatus(projectId,foreAfterMonitorStatus);
        return 1;
    }
	/**
	 * 修改赎楼贷后监控状态：新增=1，修改=2
	 *@author Jony
	 *@time:2017年1月16日下午3:25:16
	 */
	@Override
	public int updateForeAfterReportStatus(int projectId,
			int foreAfterReportStatus) throws TException {
		 projectMapper.updateForeAfterReportStatus(projectId,foreAfterReportStatus);
	        return 1;
	}

	@Override
	public OrgCooperat getOrgCooperatById(int projectId)
			throws ThriftServiceException, TException {
		OrgCooperat orgCooperat = projectMapper.getOrgCooperatById(projectId);
	        if (orgCooperat==null) {
	            return new OrgCooperat();
	        }
	        return orgCooperat;
	}

	@Override
	public int updateMonitorStatusAndReturnNormalRemark(int projectId,
			int foreAfterMonitorStatus, String returnNormalRemark){
			 projectMapper.updateMonitorStatusAndReturnNormalRemark(returnNormalRemark,projectId,foreAfterMonitorStatus);
			 	return 1;
	}
	
	/**
	 * 修改赎楼信息（资金机构）
	 */
    public int updateForeclosureByPartner(ProjectForeclosure projectForeclosure) throws TException {
    	return projectForeclosureMapper.updateForeclosureByPartner(projectForeclosure);
    }

    /**
     * 根据项目ID查询项目是否处于资金放款申请中或者放款成功
     */
	@Override
	public int getPartnerLoanCount(int projectId) throws TException {
		return projectMapper.getPartnerLoanCount(projectId);
	}
	/**
    * *根据项目区域code以及业务来源查询该项目是不是有业务,在进行合作城市删除时做校验
    * @param 
    * @return
    */
	@Override
	public List<Project> checkProjectByNoAndCode(Project project)
			throws TException {
		return projectMapper.checkProjectByNoAndCode(project);
	}

	/**
	 * 修改资金方（房抵贷）
	 *@author:liangyanjun
	 * @throws TException 
	 *@time:2017年12月14日上午9:54:57
	 */
   @Override
   public int updateCapitalNameInfo(Project project,int userId) throws TException{
      int projectId = project.getPid();
      //修改资方信息
      projectMapper.updateCapitalNameInfo(project);
      //插入资方记录
      BizCapitalSelRecord capitalSelRecord=new BizCapitalSelRecord();
      capitalSelRecord.setCapitalName(project.getCapitalName());
      capitalSelRecord.setProjectId(projectId);
      capitalSelRecord.setCreaterId(userId);
      bizCapitalSelRecordMapper.insert(capitalSelRecord);
      FinanceHandleDTO updateFinanceHandle = financeHandleMapper.getFinanceHandleByProjectId(projectId);
      int status = updateFinanceHandle.getStatus();//财务办理的收费状态：未收费=1，已收费=2，已放款=3，放款未完结=4，放款申请中=5
      //修改放款状态为放款申请中
      if (status==Constants.REC_STATUS_ALREADY_CHARGE||status==Constants.REC_STATUS_NO_CHARGE) {
         updateFinanceHandle.setStatus(Constants.REC_STATUS_LOAN_APPLY);
         financeHandleMapper.updateFinanceHandle(updateFinanceHandle);
      }
      //修改放款申请信息为“申请中”
      ApplyFinanceHandleDTO updateApplyFinanceHandle = financeHandleMapper.getByProjectIdAndRecPro(projectId, Constants.REC_PRO_3);
      if (updateApplyFinanceHandle.getApplyStatus()==Constants.APPLY_STATUS_1) {
         updateApplyFinanceHandle.setApplyStatus(Constants.APPLY_STATUS_2);
      }
      financeHandleMapper.updateApplyFinanceHandle(updateApplyFinanceHandle);
      return 1;
   }

}
