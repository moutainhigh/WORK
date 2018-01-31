package com.xlkfinance.bms.server.project.service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
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
import org.springframework.util.CollectionUtils;

import com.achievo.framework.annotation.ThriftService;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.constant.MortgageDynamicConstant;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.MoneyFormatUtil;
import com.xlkfinance.bms.common.util.NumberUtils;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstate;
import com.xlkfinance.bms.rpc.beforeloan.BizSpotInfo;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectForeclosure;
import com.xlkfinance.bms.rpc.beforeloan.ProjectGuarantee;
import com.xlkfinance.bms.rpc.beforeloan.ProjectProperty;
import com.xlkfinance.bms.rpc.beforeloan.ProjectPublicMan;
import com.xlkfinance.bms.rpc.beforeloan.ProjectRecord;
import com.xlkfinance.bms.rpc.beforeloan.ProjectRelation;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.customer.CusAcct;
import com.xlkfinance.bms.rpc.fddafterloan.BizProjectMortgage;
import com.xlkfinance.bms.rpc.inloan.ApplyFinanceHandleDTO;
import com.xlkfinance.bms.rpc.inloan.BizDynamic;
import com.xlkfinance.bms.rpc.inloan.BizDynamicService;
import com.xlkfinance.bms.rpc.inloan.RepaymentDTO;
import com.xlkfinance.bms.rpc.inloan.RepaymentRecordDTO;
import com.xlkfinance.bms.rpc.project.BizCalLoanMoney;
import com.xlkfinance.bms.rpc.project.BizPaymentInfo;
import com.xlkfinance.bms.rpc.project.BizProjectContacts;
import com.xlkfinance.bms.rpc.project.BizProjectOverdue;
import com.xlkfinance.bms.rpc.project.CusCardInfo;
import com.xlkfinance.bms.rpc.project.CusCredentials;
import com.xlkfinance.bms.rpc.project.CusEnterpriseInfo;
import com.xlkfinance.bms.rpc.project.ProjectDTO;
import com.xlkfinance.bms.rpc.project.ProjectInfoService.Iface;
import com.xlkfinance.bms.rpc.repayment.PreApplyRepayBaseDTO;
import com.xlkfinance.bms.rpc.repayment.RepaymentDetailIndexDTO;
import com.xlkfinance.bms.rpc.repayment.RepaymentPlanBaseDTO;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.server.beforeloan.mapper.BizProjectEstateMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.BizSpotInfoMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanRepaymentPlanMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectForeclosureMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectGuaranteeMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectPropertyMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectPublicManMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectRelationMapper;
import com.xlkfinance.bms.server.customer.mapper.CusAcctMapper;
import com.xlkfinance.bms.server.fddafterloan.mapper.BizProjectMortgageMapper;
import com.xlkfinance.bms.server.inloan.mapper.FinanceHandleMapper;
import com.xlkfinance.bms.server.inloan.mapper.ForeclosureRepaymentMapper;
import com.xlkfinance.bms.server.project.calc.CalPlanLine;
import com.xlkfinance.bms.server.project.calc.CalculationPlanFactory;
import com.xlkfinance.bms.server.project.mapper.BizPaymentInfoMapper;
import com.xlkfinance.bms.server.project.mapper.BizProjectContactsMapper;
import com.xlkfinance.bms.server.project.mapper.BizProjectOverdueMapper;
import com.xlkfinance.bms.server.project.mapper.CusCardInfoMapper;
import com.xlkfinance.bms.server.project.mapper.CusCredentialsMapper;
import com.xlkfinance.bms.server.project.mapper.CusEnterpriseInfoMapper;
import com.xlkfinance.bms.server.project.mapper.ProjectInfoMapper;
import com.xlkfinance.bms.server.repayment.mapper.RepaymentMapper;
import com.xlkfinance.bms.server.system.mapper.SysUserMapper;
/**
 * 
 * ClassName: ProjectInfoServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2017年12月25日 下午4:59:32 <br/>
 * 房抵贷业务贷前业务逻辑Service
 * @author baogang
 * @version 
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("projectInfoServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.project.ProjectInfoService")
public class ProjectInfoServiceImpl implements Iface {

	private Logger logger = LoggerFactory.getLogger(ProjectInfoServiceImpl.class);
	
	@Resource(name = "projectInfoMapper")
	private ProjectInfoMapper projectInfoMapper;
	
	@Resource(name = "calculationPlanFactory")
	private CalculationPlanFactory calculationPlanFactory;
	
	@Resource(name = "bizProjectContactsMapper")
	private BizProjectContactsMapper bizProjectContactsMapper;
	
	@Resource(name = "loanRepaymentPlanMapper")
	private LoanRepaymentPlanMapper loanRepaymentPlanMapper;
	
	@Resource(name = "bizProjectOverdueMapper")
	private BizProjectOverdueMapper bizProjectOverdueMapper;
	
	@Resource(name = "bizSpotInfoMapper")
	private BizSpotInfoMapper bizSpotInfoMapper;
	
	
	
	@Resource(name = "projectMapper")
	private ProjectMapper projectMapper;
	
	@Resource(name="projectForeclosureMapper")
	private ProjectForeclosureMapper projectForeclosureMapper;
	
	@Resource(name = "bizProjectMortgageMapper")
	private BizProjectMortgageMapper bizProjectMortgageMapper;
	
	@Resource(name="projectPropertyMapper")
	private ProjectPropertyMapper projectPropertyMapper;
	
	@Resource(name = "projectPublicManMapper")
	private ProjectPublicManMapper projectPublicManMapper;
	
	@Resource(name = "projectRelationMapper")
	private ProjectRelationMapper projectRelationMapper;
	
	@Resource(name = "cusAcctMapper")
	private CusAcctMapper cusAcctMapper;
	
	@Resource(name="projectGuaranteeMapper")
	private ProjectGuaranteeMapper projectGuaranteeMapper;
	
	@Resource(name = "cusEnterpriseInfoMapper")
	private CusEnterpriseInfoMapper cusEnterpriseInfoMapper;
	
	@Resource(name = "cusCredentialsMapper")
	private CusCredentialsMapper cusCredentialsMapper;
	
	@Resource(name = "cusCardInfoMapper")
	private CusCardInfoMapper cusCardInfoMapper;
	
	@Resource(name = "repaymentMapper")
	private RepaymentMapper repaymentMapper;
	
	@Resource(name = "bizPaymentInfoMapper")
	private BizPaymentInfoMapper bizPaymentInfoMapper;
	
	//回款信息表
	@Resource(name = "foreclosureRepaymentMapper")
	private ForeclosureRepaymentMapper foreRepaymentMapper;
	
    @Resource(name = "financeHandleMapper")
    private FinanceHandleMapper financeHandleMapper;
    
	@Resource(name = "bizProjectEstateMapper")
	private BizProjectEstateMapper bizProjectEstateMapper;
	
	@Resource(name = "sysUserMapper")
	private SysUserMapper sysUserMapper;
    
    @Resource(name = "bizDynamicServiceImpl")
    private BizDynamicService.Iface bizDynamicService;
    
	@Override
	public List<ProjectDTO> getProjectByPage(ProjectDTO query) throws TException {
		List<ProjectDTO> list = new ArrayList<ProjectDTO>();
		// 修改申请开始时间格式
		if (null != query.getBeginRequestDttm() && !"".equals(query.getBeginRequestDttm())) {
			String beginString = query.getBeginRequestDttm();
			Date d = DateUtil.format(beginString, "yyyy-MM-dd");
			beginString = new StringBuffer().append(DateUtil.format(d, "yyyy-MM-dd").trim()).append(" 00:00:01").toString();
			query.setBeginRequestDttm(beginString);
		}
		// 修改申请结束时间格式
		if (null != query.getEndRequestDttm() && !"".equals(query.getEndRequestDttm())) {
			String endString = query.getEndRequestDttm();
			Date d = DateUtil.format(endString, "yyyy-MM-dd");
			endString = new StringBuffer().append(DateUtil.format(d, "yyyy-MM-dd").trim()).append(" 23:59:59").toString();
			query.setEndRequestDttm(endString);
		}
		list = projectInfoMapper.getProjectByPage(query);
		return list;
	}

	@Override
	public int getProjectCount(ProjectDTO query) throws TException {
		// 修改申请开始时间格式
		if (null != query.getBeginRequestDttm() && !"".equals(query.getBeginRequestDttm())) {
			String beginString = query.getBeginRequestDttm();
			Date d = DateUtil.format(beginString, "yyyy-MM-dd");
			beginString = new StringBuffer().append(DateUtil.format(d, "yyyy-MM-dd").trim()).append(" 00:00:01").toString();
			query.setBeginRequestDttm(beginString);
		}
		// 修改申请结束时间格式
		if (null != query.getEndRequestDttm() && !"".equals(query.getEndRequestDttm())) {
			String endString = query.getEndRequestDttm();
			Date d = DateUtil.format(endString, "yyyy-MM-dd");
			endString = new StringBuffer().append(DateUtil.format(d, "yyyy-MM-dd").trim()).append(" 23:59:59").toString();
			query.setEndRequestDttm(endString);
		}
		return projectInfoMapper.getProjectCount(query);
	}

	/**
	 * 
	 * TODO 生成还款计划表并入库
	 * @see com.xlkfinance.bms.rpc.project.ProjectInfoService.Iface#makeRepaymentPlan(com.xlkfinance.bms.rpc.beforeloan.Project, int)
	 */
	@Override
	public List<RepaymentPlanBaseDTO> makeRepaymentPlan (int projectId, int userId) throws ThriftServiceException, TException
	{	
		//查询放款信息
		ApplyFinanceHandleDTO finance = financeHandleMapper.getByProjectIdAndRecPro(projectId, 3);
		//项目信息
		Project project = getLoanProjectByProjectId(projectId);
		ProjectForeclosure projectForeclosure = project.getProjectForeclosure();
		ProjectGuarantee guarantee = project.getProjectGuarantee();//费用以及贷款金额
		String receDate = finance.getRecDate();//放款时间
		int loanTerm = guarantee.getLoanTerm();//借款期限，月为单位
		double loanMoney = finance.getRecMoney();
		guarantee.setLoanMoney(loanMoney );
		//设置放款金额为生成还款计划的金额
		project.setProjectGuarantee(guarantee);
		if(receDate != null){
			//计算最后一期回款时间
			Date paymentDate = DateUtils.addMonth(DateUtils.stringToDate(receDate, "yyyy-MM-dd"), loanTerm);
			projectForeclosure.setPaymentDate(DateUtils.dateFormatByPattern(paymentDate, "yyyy-MM-dd"));//设置最后一期回款时间
			projectForeclosure.setReceDate(receDate);//设置放款时间
			//根据实际放款时间推算实际的回款时间，变更回款时间
			projectForeclosureMapper.updateByPrimaryKey(projectForeclosure);
			
			project.setProjectForeclosure(projectForeclosure);
		}
		
		//现在只做还款方式为先息后本的项目，还款计划表测算只限实现第一种
		CalPlanLine dcal = calculationPlanFactory.factory(1);
		List<RepaymentPlanBaseDTO> list = dcal.execute(project, userId);
		if(list != null && list.size()>0){
			loanRepaymentPlanMapper.insertRepayments(list);
		}
		return list;
	}

	/**
	 * 
	 * TODO 根据项目ID查询还款计划表
	 * @see com.xlkfinance.bms.rpc.project.ProjectInfoService.Iface#getRepaymentsByProjectId(int)
	 */
	@Override
	public List<RepaymentPlanBaseDTO> getRepaymentsByProjectId (int projectId) throws TException
	{
		return loanRepaymentPlanMapper.getRepaymentsByProjectId(projectId);
	}
	
	/**
	 * 
	 * 生成逾期信息
	 * @see com.xlkfinance.bms.rpc.project.ProjectInfoService.Iface#makeProjectOverdue(java.lang.String)
	 */
	@Override
	public int makeProjectOverdue (String planRepayDt) throws TException
	{
		int row = 0;
		RepaymentPlanBaseDTO query = new RepaymentPlanBaseDTO();
		query.setPlanRepayDt(planRepayDt);//设置应还款日期为当前日期，
		//查询应还款日期小于当前日期且正常未还或者逾期未还的还款计划期数列表
		List<RepaymentPlanBaseDTO> list = loanRepaymentPlanMapper.getRepaymentByDt(query );
		//需要新增的逾期数据
		List<BizProjectOverdue> addList = new ArrayList<BizProjectOverdue>();
		//需要修改的逾期数据
		List<BizProjectOverdue> updateList = new ArrayList<BizProjectOverdue>();
		List<Integer> planIdList = new ArrayList<Integer>();
		//已逾期列表
		List<RepaymentDTO> overRepaymentList = new ArrayList<RepaymentDTO>();
		List<RepaymentDTO> repaymentList = new ArrayList<RepaymentDTO>();
		if(list != null && list.size()>0){
			for(RepaymentPlanBaseDTO dto : list){
				int thisStatus = dto.getThisStatus();
				Date planDate = DateUtils.stringToDate(dto.getPlanRepayDt(),"yyyy-MM-dd");
				//根据计划还款日期以及当前日期，计算逾期天数
				int overdueDays = DateUtils.dayDifference(planDate, DateUtils.stringToDate(planRepayDt,"yyyy-MM-dd"));
				dto.setOverdueDays(overdueDays);
				//转换后的逾期信息
				BizProjectOverdue overdue = copyOverdue(dto);
				if(overdue.getPid() >0){
					updateList.add(overdue);
				}else{
					addList.add(overdue);
				}
				
				//还款计划状态为正常未还时，需要修改还款计划状态为逾期未还
				if(thisStatus == Constants.REPAYMENT_PLAN_STATUS_1){
					planIdList.add(dto.getPId());
				}
				RepaymentDTO repaymentDto = new RepaymentDTO();
				repaymentDto.setProjectId(dto.getProjectId());
				repaymentDto.setPid(dto.getRepaymentId());
				//最后一期逾期表示该项目逾期，状态变更为已逾期
				if(dto.getPlanCycleNum() == dto.getLoanTerm()){
					overRepaymentList.add(repaymentDto);
				}else{
					repaymentList.add(repaymentDto);
				}
			}
			//批量更新逾期信息
			if(updateList.size()>0){
				bizProjectOverdueMapper.updateOverdues(updateList);
				row += 1;
			}
			//批量新增逾期信息
			if(addList.size()>0){
				bizProjectOverdueMapper.insertOverdues(addList);
				row += 1;
			}
			//变更还款信息，如果存在还款信息，则更新状态，如无还款信息则新增一条还款信息
			if(repaymentList.size()>0){
				for(RepaymentDTO dto : repaymentList){
					int projectId = dto.getProjectId();
					RepaymentDTO result = foreRepaymentMapper.getRepaymentByProjectId(projectId);
					if(result == null){
						result = new RepaymentDTO();
						result.setProjectId(projectId);
						result.setStatus(Constants.REPAYMENT_STATUS_3);
						foreRepaymentMapper.addRepayment(result);
					}else{
						result.setStatus(Constants.REPAYMENT_STATUS_3);
						foreRepaymentMapper.updateRepayment(result);
					}
				}
			}
			
			//变更还款信息，如果存在还款信息，则更新状态，如无还款信息则新增一条还款信息
			if(overRepaymentList.size()>0){
				for(RepaymentDTO dto : overRepaymentList){
					int projectId = dto.getProjectId();
					RepaymentDTO result = foreRepaymentMapper.getRepaymentByProjectId(projectId);
					if(result == null){
						result = new RepaymentDTO();
						result.setProjectId(projectId);
						result.setStatus(Constants.REPAYMENT_STATUS_4);
						foreRepaymentMapper.addRepayment(result);
					}else{
						result.setStatus(Constants.REPAYMENT_STATUS_4);
						foreRepaymentMapper.updateRepayment(result);
					}
				}
			}
			
			
			//修改还款计划表中的状态
			if(planIdList.size()>0){
				RepaymentPlanBaseDTO result = new RepaymentPlanBaseDTO();
				result.setPidList(planIdList);
				result.setThisStatus(Constants.REPAYMENT_PLAN_STATUS_3);
				loanRepaymentPlanMapper.updateRepaymentPlan(result);
				row += 1;
			}
		}
		return row;
	}
	
	/**
	 * 
	 * copyOverdue:还款计划表信息转换成逾期信息. <br/>
	 * @author baogang
	 * @param dto
	 * @return
	 *
	 */
	private BizProjectOverdue copyOverdue(RepaymentPlanBaseDTO dto){
		double shouldTotal = dto.getTotal();//应收合计
		double actualTotal = dto.getActualTotal();//实收合计
		//逾期金额
		double overdueMoney = shouldTotal-actualTotal;
		double principalBalance = dto.getPrincipalBalance();//本金余额
		double shouldPrincipal = dto.getShouldPrincipal();//应还本金
		double actualPrincipal = dto.getActualPrincipal();//实收本金
		int overdueDays = dto.getOverdueDays();//逾期天数
		double overdueRate = dto.getOverdueRate();//逾期日罚息率
		
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.HALF_UP);
		/*//当前剩余应还本金 = 本期逾期金额+本期应还剩余本金-已收逾期金额
		double principalTotal = overdueMoney+ principalBalance-dto.getActualOverdueMoney();
		if(principalTotal>(principalBalance+shouldPrincipal)){
			principalTotal = principalBalance+shouldPrincipal;
		}*/
		//应收罚息=（本期应收本金+本期本金余额-已收本金）*逾期天数*逾期日罚息率/100
		double shouldPenalty = Double.parseDouble(df.format((principalBalance + shouldPrincipal - actualPrincipal) * overdueDays * overdueRate / 100));
		
		BizProjectOverdue overdue = null;
		//逾期信息ID
		int overdueId = dto.getOverdueId();
		if(overdueId >0){
			overdue = bizProjectOverdueMapper.getById(dto.getOverdueId());
		}else{
			overdue = new BizProjectOverdue();
			overdue.setOverdueMoney(overdueMoney);//逾期金额
			overdue.setShouldOverdueMoney(overdueMoney);//逾期信息生成时，应收逾期金额=逾期金额
			overdue.setProjectId(dto.getProjectId());
			overdue.setLoanPlanId(dto.getPId());
			overdue.setCreaterId(20172);
		}
		overdue.setOverdueDay(overdueDays);//逾期天数
		overdue.setShouldPenalty(shouldPenalty);//应收罚息
		overdue.setOverdueRate(overdueRate);//逾期日罚息率
		
		return overdue;
	}

	/**
	 * 
	 * 财务明细列表展示
	 * @see com.xlkfinance.bms.rpc.project.ProjectInfoService.Iface#queryRepayment(com.xlkfinance.bms.rpc.repayment.RepaymentIndexDTO)
	 */
	@Override
	public List<RepaymentDetailIndexDTO> queryRepayment (RepaymentDetailIndexDTO query) throws TException
	{
		
		return projectInfoMapper.queryRepayment(query);
	}

	/**
	 * 
	 * TODO 查询财务回款页面逾期以及其他应收详情.
	 * @see com.xlkfinance.bms.rpc.project.ProjectInfoService.Iface#queryOverdueByProject(int)
	 */
	@Override
	public RepaymentDetailIndexDTO queryOverdueByProject (int projectId) throws TException
	{
		//查询项目逾期信息
		RepaymentDetailIndexDTO repaymentIndexDTO = projectInfoMapper.queryOverdueByProject(projectId);
		if(repaymentIndexDTO == null){
			repaymentIndexDTO = new RepaymentDetailIndexDTO();
		}
		RepaymentDetailIndexDTO query = new RepaymentDetailIndexDTO();
		query.setProjectId(projectId);
		int planCycleNum = 0;//未还的期数，用于显示在页面
		double shouldPenaltyTotal = repaymentIndexDTO.getShouldPenalty() - repaymentIndexDTO.getActualPenalty();//应收罚息总计= 逾期的应收罚息-已收取的罚息金额，只取第一个逾期未还状态的信息
		double shouldOverdueMoneyTotal = 0;//应收逾期金额总计
		double shouldInterestTotal = 0;//应收利息总计
		double shouldPrincipalTotal = 0;
		double fineRates = 0;
		
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.HALF_UP);
		
		//查询还款计划列表
		List<RepaymentDetailIndexDTO> repaymentList = projectInfoMapper.queryRepayment(query );
		if(repaymentList != null && repaymentList.size()>0){
			for(int i=0;i<repaymentList.size();i++){
				RepaymentDetailIndexDTO dto = repaymentList.get(i);
				int thisStatus = dto.getThisStatus();//本期还款状态
				//获取最近一期未还的期数
				if(thisStatus == Constants.REPAYMENT_PLAN_STATUS_1 || thisStatus == Constants.REPAYMENT_PLAN_STATUS_3){
					if(planCycleNum == 0){
						planCycleNum = dto.getPlanCycleNum();
					}
					
					//逾期未还进行累计逾期金额
					if(thisStatus == Constants.REPAYMENT_PLAN_STATUS_3){
						//累计逾期金额
						shouldOverdueMoneyTotal +=dto.shouldOverdueMoney-dto.getActualOverdueMoney();
						//最后一期逾期，剩余应还本金 = 应还本金余额+逾期金额 - 已还逾期金额
						if(i == repaymentList.size() -1){
							/*double principalBalance = dto.getPrincipalBalance() +dto.getShouldOverdueMoney()-dto.getActualOverdueMoney();
							if(principalBalance > dto.getShouldPrincipal()){
								principalBalance = dto.getShouldPrincipal();
							}*/
							repaymentIndexDTO.setPrincipalBalance(dto.getShouldPrincipal());
						}
						
					}
					//正常未还累计应还利息以及应还本金
					if(thisStatus == Constants.REPAYMENT_PLAN_STATUS_1){
						shouldInterestTotal +=dto.getShouldInterest()-dto.getActualInterest();
						shouldPrincipalTotal+=dto.getShouldPrincipal()-dto.getActualPrincipal();
					}
					
				}
				
				fineRates = dto.getFineRates();
			}
		}
		repaymentIndexDTO.setPlanCycleNum(planCycleNum);//最近一期未还的期数
		repaymentIndexDTO.setShouldPenaltyTotal(shouldPenaltyTotal);//
		repaymentIndexDTO.setShouldOverdueMoney(shouldOverdueMoneyTotal);//应收逾期费总计
		repaymentIndexDTO.setShouldInterest(shouldInterestTotal);//应收利息总计
		repaymentIndexDTO.setShouldPrincipal(shouldPrincipalTotal);//应收本金总计
		//提前还款费=应还剩余本金*提前还款费率
		double shouldPrepaymentFee = Double.parseDouble(df
				.format(shouldPrincipalTotal* fineRates/100));
		repaymentIndexDTO.setShouldPrepaymentFee(shouldPrepaymentFee);//提前还款费
		return repaymentIndexDTO;
			
	}
	
	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see com.xlkfinance.bms.rpc.project.ProjectInfoService.Iface#saveProjectInfo(com.xlkfinance.bms.rpc.beforeloan.Project)
	 */
	@Override
	public Map<String,String> saveProjectInfo (Project project) throws ThriftServiceException, TException
	{
		int projectId = project.getPid();
		Map<String, String> result = new HashMap<String, String>();
		try {
			if (projectId > 0) {
				// 提交流程前均可修改项目基础信息
				if (project.getForeclosureStatus() <= 1) {
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
				}
				// 总部复审节点前可修改企业信息和客户资质
				if (project.getForeclosureStatus() <= 3) {
					// 保存企业信息
					cusEnterpriseInfoMapper.update(project.getCusEnterpriseInfo());
					// 保存客户资质
					cusCredentialsMapper.update(project.getCusCredentials());
				}
				// 仅下户节点可修改银行卡信息
				if (project.getForeclosureStatus() == 3) {
					// 保存银行卡信息
					cusCardInfoMapper.update(project.getCusCardInfo());
				}
				// 终审节点审批同意前均可修改贷款申请信息
				if (project.getForeclosureStatus() <= 7) {
					// 仅评估节点可修改收费情况
					if (project.getForeclosureStatus() != 2) {
						project.getProjectGuarantee().setChargesType(0);
						project.getProjectGuarantee().setPoundage(-1);
					}
					if (project.getForeclosureStatus() <= 1) {
						// 设置贷款申请金额
						project.getProjectGuarantee().setGuaranteeMoney(project.getProjectGuarantee().getLoanMoney());
					}
					// 保存贷款申请费用信息入库
					projectGuaranteeMapper.updateByPrimaryKey(project.getProjectGuarantee());
				}
				result.put("projectId", String.valueOf(projectId));
				result.put("projectName", project.getProjectName());
				result.put("projectNumber", project.getProjectNumber());
				result.put("enterpriseId", String.valueOf(project.getCusEnterpriseInfo().getPid()));
				result.put("credentialsId", String.valueOf(project.getCusCredentials().getPid()));
				result.put("cusCardId", String.valueOf(project.getCusCardInfo().getPid()));
				result.put("guaranteeId", String.valueOf(project.getProjectGuarantee().getPid()));
				return result;
			} else {
				return addProject(project);
			}
		} catch (Exception e) {
			logger.error("保存项目信息出错:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
	}
	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see com.xlkfinance.bms.rpc.project.ProjectInfoService.Iface#saveConsumeProjectInfo(com.xlkfinance.bms.rpc.beforeloan.Project)
	 */
	//@Override
	public Map<String,String> saveConsumeProjectInfo (Project project) throws ThriftServiceException, TException
	{
		int projectId = project.getPid();
		Map<String, String> result = new HashMap<String, String>();
		try {
			if (projectId > 0) {
				// 提交流程前均可修改项目基础信息
				if (project.getForeclosureStatus() <= 1) {
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
				}
				// 保存客户资质
				cusCredentialsMapper.update(project.getCusCredentials());
				// 待提交节点可修改银行卡信息
				if (project.getForeclosureStatus() == Constants.CONSUME_LOAN_TO_SUBMIT) {
					// 保存银行卡信息
					cusCardInfoMapper.update(project.getCusCardInfo());
					
					ProjectProperty projectProperty=project.getProjectProperty();
					if(projectProperty!=null){
						//修改物业总租金
						projectPropertyMapper.updateByPrimaryKey(projectProperty);
					}
					
					//新增下户信息物业报告
					List<BizProjectEstate> projectEstateList=bizProjectEstateMapper.getNoSpotInfoByProjectId(projectId);
					if(projectEstateList !=null && projectEstateList.size()>0){
						for(BizProjectEstate projectEstate:projectEstateList){
							BizSpotInfo addSport=new BizSpotInfo();
							addSport.setProjectId(projectId);
							addSport.setEastateId(projectEstate.getHouseId());
							addSport.setCreateDate(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
							addSport.setCreaterId(project.getRecordClerkId());
							bizSpotInfoMapper.insert(addSport);
						}
					}
				}	

				// 保存贷款申请费用信息入库
				projectGuaranteeMapper.updateByPrimaryKey(project.getProjectGuarantee());
				result.put("projectId", String.valueOf(projectId));
				result.put("projectName", project.getProjectName());
				result.put("projectNumber", project.getProjectNumber());
				result.put("enterpriseId", String.valueOf(project.getCusEnterpriseInfo().getPid()));
				result.put("credentialsId", String.valueOf(project.getCusCredentials().getPid()));
				result.put("cusCardId", String.valueOf(project.getCusCardInfo().getPid()));
				result.put("guaranteeId", String.valueOf(project.getProjectGuarantee().getPid()));
				return result;
			} else {
				return addConsumeProject(project);
			}
		} catch (Exception e) {
			logger.error("保存项目信息出错:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
	}
	
	/**
	 * addProject:(新增房抵贷贷款信息). <br/>
	 * @author dulin
	 * @param project
	 * @return
	 * @throws TException 
	 */
	private Map<String,String> addProject(Project project) throws TException {
		// 需要返回的项目ID
		Map<String, String> result = new HashMap<String, String>();
		// 项目ID
		int projectId = 0;
		// 判断是否成功--受影响的行数
		int projectType = project.getProjectType();//项目类型，不入库
		int rows = 0;
		try {
			// 获取新的项目ID
			projectId = projectMapper.getSeqBizProject();
			// 赋值
			project.setPid(projectId);
			// 生成项目名称
			String projectName = makeProjectName(project.getAbbreviation(), project.getAcctId());
			// 生成项目编号
			String projectNumber = makeProjectNumber(projectType);
			// 赋值项目ID和项目编号
			project.setProjectName(projectName);
			project.setProjectNumber(projectNumber);
			// 设置申请状态为1(申请中) 状态为1(正常) 申请时间为当前时间
			project.setRequestStatus(1);
			//设置房抵贷项目状态为1（待提交）
			project.setForeclosureStatus(Constants.MORTGAGE_LOAN_TO_SUBMIT);
			project.setStatus(Constants.STATUS_ENABLED); // 默认本条项目数据有效
			project.setIsChechan(0);// 未撤单
			project.setRequestDttm(DateUtils.getCurrentDateTime());
			// 设置项目状态为房抵贷项目类型，8代表房抵贷类型
			project.setProjectType(Constants.MORTGAGE_LOAN_PROJECT_TYPE);
			// 新增房抵贷贷款项目基础信息入库
			rows = projectMapper.insert(project);
			
			// 添加一条待登记入库的抵押物信息
			BizProjectMortgage bizProjectMortgage = new BizProjectMortgage();
			bizProjectMortgage.setProjectId(projectId);
			bizProjectMortgage.setMortgageStatus(Constants.MORTGAGE_STATUS_1);
			bizProjectMortgage.setCreateDate(DateUtils.getCurrentDateTime());
			bizProjectMortgage.setCreaterId(project.getRecordClerkId()); // 录单用户
			bizProjectMortgageMapper.insert(bizProjectMortgage);
			
			// 房抵贷项目信息表插入一条房抵贷贷款项目id关联记录，用于后边放款、回款的日期记录
			ProjectForeclosure projectForeclosure = new ProjectForeclosure();
			projectForeclosure.setPid(projectForeclosureMapper.getSeqBizProjectForeclosure());
			projectForeclosure.setProjectId(projectId);
			projectForeclosureMapper.insertForeclosure(projectForeclosure);
			// 新增一条物业信息
			ProjectProperty projectProperty = new ProjectProperty();
			projectProperty.setPid(projectPropertyMapper.getSeqBizProjectProperty());
			projectProperty.setProjectId(projectId);
			projectPropertyMapper.insertProperty(projectProperty);
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
			if (!StringUtil.isBlank(project.getUserPids())) {
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
			// 判断是否选取抵押物信息
			String houseIds = project.getHouseIds();
			if(!StringUtil.isBlank(houseIds)){
				List<Integer> houseIdList = StringUtil.StringToList(houseIds);
				if (!CollectionUtils.isEmpty(houseIdList)) {
					//遍历提交保存的物业ID
					for(Integer houseId:houseIdList){
						BizProjectEstate projectEstate = bizProjectEstateMapper.getById(houseId);
						projectEstate.setHouseId(houseId);
						projectEstate.setProjectId(projectId);
						//将物业信息也项目进行关联
						bizProjectEstateMapper.update(projectEstate);
					}
				}
			}
			// 企业信息
			project.getCusEnterpriseInfo().setProjectId(projectId);
			cusEnterpriseInfoMapper.insert(project.getCusEnterpriseInfo());
			// 客户资质
			project.getCusCredentials().setProjectId(projectId);
			cusCredentialsMapper.insert(project.getCusCredentials());
			// 银行卡信息
			project.getCusCardInfo().setProjectId(projectId);
			project.getCusCardInfo().setAcctId(project.getAcctId());
			cusCardInfoMapper.insert(project.getCusCardInfo());
			// 保存贷款申请费用信息入库
			ProjectGuarantee projectGuarantee = project.getProjectGuarantee();
			// 获取贷款申请信息表主键id
			int guaranteeId = projectGuaranteeMapper.getSeqBizProjectGuarantee();
			projectGuarantee.setPid(guaranteeId);
			projectGuarantee.setProjectId(projectId);
			// 设置贷款申请金额
			projectGuarantee.setGuaranteeMoney(projectGuarantee.getLoanMoney());
			projectGuaranteeMapper.insertGuarantee(projectGuarantee);
			if (rows >= 1) {
				result.put("projectId", String.valueOf(projectId));
				result.put("projectName", projectName);
				result.put("projectNumber", projectNumber);
				result.put("enterpriseId", String.valueOf(project.getCusEnterpriseInfo().getPid()));
				result.put("credentialsId", String.valueOf(project.getCusCredentials().getPid()));
				result.put("cusCardId", String.valueOf(project.getCusCardInfo().getPid()));
				result.put("guaranteeId", String.valueOf(guaranteeId));
			} else {
				// 抛出异常处理
				throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "新增失败,请重新操作!");
			}
		} catch (ThriftServiceException e) {
			logger.error("新增贷前申请:" + ExceptionUtil.getExceptionMessage(e));
			throw e;
		} catch (Exception e) {
			logger.error("新增贷前申请:" + ExceptionUtil.getExceptionMessage(e));
			throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "新增失败,请重新操作!");
		}
		return result;
	}
	/**
	 * 新增消费贷贷贷款信息
	 * @return Map<String,String>
	 * @author: Leif
	 * @date: 2018年1月4日 下午7:15:09
	 */
	private Map<String,String> addConsumeProject(Project project) throws TException {
		// 需要返回的项目ID
		Map<String, String> result = new HashMap<String, String>();
		// 项目ID
		int projectId = 0;
		// 判断是否成功--受影响的行数
		int projectType = project.getProjectType();//产品类型，不入库
		int rows = 0;
		try {
			// 获取新的项目ID
			projectId = projectMapper.getSeqBizProject();
			// 赋值
			project.setPid(projectId);
			// 生成项目名称
			String projectName = makeProjectName(project.getAbbreviation(), project.getAcctId());
			// 生成项目编号
			String projectNumber = makeProjectNumber(projectType);
			// 赋值项目名称和项目编号
			project.setProjectName(projectName);
			project.setProjectNumber(projectNumber);
			// 设置申请状态为1(申请中) 状态为1(正常) 申请时间为当前时间
			project.setRequestStatus(1);
			//设置消费贷项目状态为1（待提交）
			project.setForeclosureStatus(Constants.CONSUME_LOAN_TO_SUBMIT);
			project.setStatus(Constants.STATUS_ENABLED); // 默认本条项目数据有效
			project.setIsChechan(0);// 未撤单
			project.setRequestDttm(DateUtils.getCurrentDateTime());
			// 设置项目状态10代表消费贷贷类型
			project.setProjectType(Constants.CONSUMER_LOAN_PROJECT_TYPE);
			// 新增消费贷贷款项目基础信息入库
			rows = projectMapper.insert(project);

			// 消费贷项目信息表插入一条消费贷贷款项目id关联记录，用于后边放款、回款的日期记录
			ProjectForeclosure projectForeclosure = new ProjectForeclosure();
			projectForeclosure.setPid(projectForeclosureMapper.getSeqBizProjectForeclosure());
			projectForeclosure.setProjectId(projectId);
			projectForeclosureMapper.insertForeclosure(projectForeclosure);
			
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
			if (!StringUtil.isBlank(project.getUserPids())) {
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
			
			// 物业信息关联项目
			if (!StringUtil.isBlank(project.getHouseIds()) && project.getProjectType()==Constants.CONSUMER_LOAN_PROJECT_TYPE) {
				String[] housePids = project.getHouseIds().split(",");
				for (int i = 0; i < housePids.length; i++) {
					// 循环修改物业信息
					BizProjectEstate projectEstate = new BizProjectEstate();
					projectEstate.setHouseId(Integer.parseInt(housePids[i]));
					projectEstate.setProjectId(projectId);// 将当前的项目ID赋值
					bizProjectEstateMapper.update(projectEstate);
				}
			}
						
			// 项目联系人关联项目
			if (!StringUtil.isBlank(project.getProContactIds())) {
				String[] contactPids = project.getProContactIds().split(",");
				for (int i = 0; i < contactPids.length; i++) {
					// 循环修改项目关系人信息
		            BizProjectContacts bizProjectContacts = new BizProjectContacts();
		            bizProjectContacts.setPid(Integer.parseInt(contactPids[i]));
		            bizProjectContacts.setProjectId(projectId);// 将当前的项目ID赋值
		            bizProjectContacts.setAcctId(project.getAcctId());
		            bizProjectContactsMapper.update(bizProjectContacts);
				}
			}
			// 客户资质
			project.getCusCredentials().setProjectId(projectId);
			cusCredentialsMapper.insert(project.getCusCredentials());
			// 银行卡信息
			project.getCusCardInfo().setProjectId(projectId);
			project.getCusCardInfo().setAcctId(project.getAcctId());
			cusCardInfoMapper.insert(project.getCusCardInfo());
			// 保存贷款申请费用信息入库
			ProjectGuarantee projectGuarantee = project.getProjectGuarantee();
			// 获取贷款申请信息表主键id
			int guaranteeId = projectGuaranteeMapper.getSeqBizProjectGuarantee();
			projectGuarantee.setPid(guaranteeId);
			projectGuarantee.setProjectId(projectId);
			
			projectGuaranteeMapper.insertGuarantee(projectGuarantee);
			
			//物业租金信息
			ProjectProperty projectProperty=project.getProjectProperty();
			if(projectProperty!=null){
				projectProperty.setProjectId(projectId);
				int propertyId = projectPropertyMapper.getSeqBizProjectProperty();
				projectProperty.setPid(propertyId);
				//修改物业总租金
				projectPropertyMapper.insertProperty(projectProperty);
			}
			
			//新增下户信息
			List<BizProjectEstate> projectEstateList=bizProjectEstateMapper.getAllByProjectId(projectId);
			if(projectEstateList !=null && projectEstateList.size()>0){
				for(BizProjectEstate projectEstate:projectEstateList){
					BizSpotInfo addSport=new BizSpotInfo();
					addSport.setProjectId(projectId);
					addSport.setEastateId(projectEstate.getHouseId());
					addSport.setCreateDate(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
					addSport.setCreaterId(project.getRecordClerkId());
					bizSpotInfoMapper.insert(addSport);
				}
			}
			
			if (rows >= 1) {
				result.put("projectId", String.valueOf(projectId));
				result.put("projectName", projectName);
				result.put("projectNumber", projectNumber);
				result.put("enterpriseId", String.valueOf(project.getCusEnterpriseInfo().getPid()));
				result.put("credentialsId", String.valueOf(project.getCusCredentials().getPid()));
				result.put("cusCardId", String.valueOf(project.getCusCardInfo().getPid()));
				result.put("guaranteeId", String.valueOf(guaranteeId));
			} else {
				// 抛出异常处理
				throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "新增失败,请重新操作!");
			}
		} catch (ThriftServiceException e) {
			logger.error("新增贷前申请:" + ExceptionUtil.getExceptionMessage(e));
			throw e;
		} catch (Exception e) {
			logger.error("新增贷前申请:" + ExceptionUtil.getExceptionMessage(e));
			throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "新增失败,请重新操作!");
		}
		return result;
	}
	/**
	 * @Description: 生成项目名称
	 * @param abbrName 如果是企业=客户简称 如果是个人=个人中文名称
	 * @param acctId 客户ID
	 * @return 项目名称
	 * @author: dulin
	 * @date: 2017年12月15日 下午3:02:33
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
	 * @author: dulin
	 * @date: 2017年12月15日 下午3:02:33
	 */
	public String makeProjectNumber(int projectType) {
		
		String projectName = new String();
		if(Constants.MORTGAGE_LOAN_PROJECT_TYPE == projectType){
			// 获取当前的4位年 并附加到新的项目名称里面
			projectName = Constants.MORTGAGE_LOAN_PRODUCT_PREFIX + DateUtil.format(new Date(), "yyyy");
		}else if(Constants.CONSUMER_LOAN_PROJECT_TYPE == projectType){
			// 获取当前的4位年 并附加到新的项目名称里面
			projectName = Constants.CONSUME_LOAN_PRODUCT_PREFIX + DateUtil.format(new Date(), "yyyy");
		}
		// 获取当前日期的项目名称的最后5位自然数
		String maxNumber = projectMapper.getMaxProjectNumber();
		// 判断是否为空
		if (maxNumber != null) {
			maxNumber = String.valueOf(Integer.parseInt(maxNumber) + 1);
			projectName += StringUtil.lpad(maxNumber, "0", 5 - maxNumber.length());
		} else {
			projectName += "00001";
		}
		return projectName;
	}

	/**
	 * 
	 * 保存抵押贷产品还款信息
	 * @see com.xlkfinance.bms.rpc.project.ProjectInfoService.Iface#saveRepaymentDetailInfo(com.xlkfinance.bms.rpc.repayment.RepaymentDetailIndexDTO)
	 */
	@Override
	public int saveRepaymentDetailInfo (RepaymentDetailIndexDTO result) throws TException
	{
		int projectId = result.getProjectId();
		RepaymentDetailIndexDTO query = new RepaymentDetailIndexDTO();
		query.setProjectId(projectId);
		//还款计划列表
		List<RepaymentDetailIndexDTO> repaymentList = projectInfoMapper.queryRepayment(query );
		//项目最大期数
		int maxPlanCycleNum = repaymentList.size();
		
		//实收罚息金额大于0并且小于等于应收罚息总额，则项目还款计划表中的状态不变，只记录还款历史为罚息一笔，累加抵消罚息金额
		//结算完罚息后，如果有余额，继续结算逾期金额，如果余额小于等于应收逾期总额，则只能抵消逾期金额，并变更已还完逾期金额的分期状态为逾期已还
		double actualPenalty = result.getActualPenalty();//实收罚息
		double actualOverdueMoney = result.getActualOverdueMoney();//实收逾期金额
		double actualInterest = result.getActualInterest();//实收利息
		double actualFine = result.getFine();//实收提前还款费
		double actualPrincipal = result.getActualPrincipal();//实收本金
		String actualDate = result.getRepaymentDate();//实收日期
		int userId = result.getCreaterId();//实收操作人
		
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.HALF_UP);
		
		int repaymentType = result.getRepaymentType();
		
		//逾期信息列表
		List<BizProjectOverdue> overdueList = new ArrayList<BizProjectOverdue>();
		//还款计划表列表
		List<RepaymentPlanBaseDTO> planList = new ArrayList<RepaymentPlanBaseDTO>();
		//需要修改的正常还款信息
		List<BizPaymentInfo> upPaymentList = new ArrayList<BizPaymentInfo>();
		//需要新增的政策还款信息
		List<BizPaymentInfo> addPaymentList = new ArrayList<BizPaymentInfo>();
		//需要新增的还款记录信息
		List<RepaymentRecordDTO> recordList = new ArrayList<RepaymentRecordDTO>();
		int rows = 0;
		for(RepaymentDetailIndexDTO dto :repaymentList){
			RepaymentPlanBaseDTO plan = new RepaymentPlanBaseDTO();
			plan.setPId(dto.getPid());
			//逾期未还的期数信息
			if(dto.getThisStatus() == Constants.REPAYMENT_PLAN_STATUS_3){
				BizProjectOverdue overdue = new BizProjectOverdue();
				int overdueId = dto.getOverdueId();
				overdue.setPid(overdueId);
				//以往实收罚息
				double penalty  = dto.getActualPenalty();
				
				//累计实收罚息
				penalty += actualPenalty;
				//实收罚息一定小于等于应收罚息
				if(penalty > dto.getShouldPenalty()){
					penalty = dto.getShouldPenalty();
				}
				overdue.setActualPenalty(penalty);//实收罚息
				
				if(actualPenalty >0){
					String remark = result.getRepaymentDate()+" 第"+result.getPlanCycleNum()+"期 实际收取罚息金额："+actualPenalty+"元，核销本期罚息金额：" + penalty+"元。";
					overdue.setRemark(remark);
					overdue.setActualPenaltyTime(actualDate);//实收日期
					//一次罚息收取只记录一次
					if(rows == 0){
						//记录还款历史记录信息
						RepaymentRecordDTO penaltyDto = conver(result);
						penaltyDto.setRepaymentType(Constants.REPAYMENT_PRO_4);
						penaltyDto.setPlanCycleNum(dto.getPlanCycleNum());
						penaltyDto.setRepaymentMoney(actualPenalty);
						recordList.add(penaltyDto);
					}
					
					rows += 1;
				}
				
				//实收逾期金额累计大于0
				if(actualOverdueMoney >0 ){
					double overdueMoney = dto.getActualOverdueMoney();//累计已收逾期金额
					double shouldOverdueMoney = dto.getShouldOverdueMoney();//本期应收逾期金额总计
					double overdueResult = Double.parseDouble(df.format(shouldOverdueMoney-overdueMoney));//本期剩余应收逾期金额
					
					double repaymentMoney = 0.00;//本期实际核销金额
					//实收逾期金额大于等于本期剩余应收逾期金额，表示本期逾期金额已全部还请，更改本期还款状态为逾期已还
					if(actualOverdueMoney >= overdueResult){
						repaymentMoney = overdueResult;
						overdueMoney = shouldOverdueMoney;
						plan.setThisStatus(Constants.REPAYMENT_PLAN_STATUS_4);//逾期已还
						plan.setIsReconciliation(1);//已对账
						planList.add(plan);
					//实收逾期金额小于本期剩余应收逾期金额，表示本期逾期金额未还清，累加本期已还逾期金额
					}else{
						repaymentMoney = actualOverdueMoney;
						overdueMoney +=actualOverdueMoney;
					}
					overdue.setActualOverdueMoney(overdueMoney);//实收逾期金额
					//减去本期已对账的逾期金额
					actualOverdueMoney = Double.parseDouble(df.format(actualOverdueMoney -overdueResult));
					overdue.setActualPenaltyTime(actualDate);//实收日期
					
					//记录还款历史记录信息
					RepaymentRecordDTO overdueDto = conver(result);
					overdueDto.setRepaymentType(Constants.REPAYMENT_PRO_3);
					overdueDto.setPlanCycleNum(dto.getPlanCycleNum());
					overdueDto.setRepaymentMoney(repaymentMoney);
					recordList.add(overdueDto);
				}
				if(!StringUtil.isBlank(overdue.getActualPenaltyTime())){
					overdue.setUpdateId(userId);
					overdueList.add(overdue);
				}
			}
			
			//正常未还的期数信息
			if(dto.getThisStatus() == Constants.REPAYMENT_PLAN_STATUS_1){
				//实收提前还款费大于0，说明此次收取为提前还款，提前还款原则上需要结清全部费用，分期还款状态由正常未还变更为提前还款，项目还款状态变更为已结清
				if(repaymentType == Constants.REPAYMENT_TYPE_2){
					plan.setThisStatus(Constants.REPAYMENT_PLAN_STATUS_5);
					plan.setIsReconciliation(1);//已对账
					planList.add(plan);
					
					//提前还款信息
					PreApplyRepayBaseDTO repayDto = new PreApplyRepayBaseDTO();
					repayDto.setProjectId(projectId);
					repayDto.setLoanPlanId(dto.getPid());
					repayDto.setPreRepayAmt(actualPrincipal);//实收剩余本金
					repayDto.setSurplus(actualPrincipal);//应收剩余本金=实收本金
					repayDto.setCreaterId(userId);
					repayDto.setFine(actualFine);//实收提前还款费
					repayDto.setFineRates(dto.getFineRates());//提前还款费率
					repayDto.setShouldPrepaymentFee(actualFine);//提前还款，应收提前还款费等于实收提前还款费
					repayDto.setRepayDate(actualDate);//收取日期
					//查询是否存在项目关联的提前还款
					int count = repaymentMapper.queryPrerepayCount(projectId);
					//如果不存在提前还款信息，则新增一条信息,记录回款记录
					if(count == 0){
						repaymentMapper.insertPreApplyRepay(repayDto);
						//提前还款费实际收取记录
						RepaymentRecordDTO fineDto = conver(result);
						fineDto.setRepaymentType(Constants.REPAYMENT_PRO_6);
						fineDto.setRepaymentMoney(actualFine);
						fineDto.setPlanCycleNum(dto.getPlanCycleNum());
						recordList.add(fineDto);
						//提前还款的本金收取记录
						RepaymentRecordDTO principalDto = conver(result);
						principalDto.setRepaymentType(Constants.REPAYMENT_PRO_5);
						principalDto.setRepaymentMoney(actualPrincipal);
						principalDto.setPlanCycleNum(dto.getPlanCycleNum());
						recordList.add(principalDto);
					}
					
					
				}else if(repaymentType == Constants.REPAYMENT_TYPE_1){
				//实收利息总计大于0，还款模式为正常还款，需要更新或者新增正常还款信息表,先对账利息信息
					BizPaymentInfo paymentInfo = new BizPaymentInfo();
					double actualTotal = dto.getActualInterest()+dto.getActualPrincipal();//实收总计
					int paymentId = dto.getPaymentId();
					paymentInfo.setPid(paymentId);
					paymentInfo.setProjectId(projectId);
					paymentInfo.setLoanPlanId(dto.getPid());
					paymentInfo.setActualPrincipalTime(actualDate);
					paymentInfo.setCreaterId(userId);
					paymentInfo.setUpdateId(userId);
					
					//实收利息大于0
					if(actualInterest >0){
						double interest = Double.parseDouble(df.format(dto.getShouldInterest()-dto.getActualInterest()));//应收利息 -实收利息=剩余应收利息
						double repaymentInterest = 0.00;//本次实际收取利息金额
						
						//实收利息金额小于应收剩余利息，本期利息为还完，本期状态不变，累加计算实收利息数据
						if(actualInterest<interest){
							repaymentInterest = actualInterest;
							paymentInfo.setActualInterest(actualInterest+dto.getActualInterest());
							actualTotal += actualInterest;
						//实收利息大于等于应收剩余利息，本期利息还完，累加计算实收利息数据
						}else{
							repaymentInterest = interest;
							actualTotal += interest;
							paymentInfo.setActualInterest(dto.getShouldInterest());
						}
						//本期计算完毕后减去本期剩余应收利息，大于0，下次继续，小于等于0，下一期无法核销利息
						actualInterest = Double.parseDouble(df.format(actualInterest-interest));
						if(repaymentInterest >0){
							//利息的金额收取记录
							RepaymentRecordDTO interestDto = conver(result);
							interestDto.setRepaymentType(Constants.REPAYMENT_PRO_2);
							interestDto.setRepaymentMoney(repaymentInterest);
							interestDto.setPlanCycleNum(dto.getPlanCycleNum());
							recordList.add(interestDto);
						}
						
					}
					//本金金额大于0
					if(actualPrincipal >0){
						double principal = Double.parseDouble(df.format(dto.getShouldPrincipal() - dto.getActualPrincipal()));//应收本金-实收本金 = 剩余应收本金
						double repaymentPrincipal = 0.00;//本次实际收取本金金额
						
						//实收本金金额小于应收剩余本金，本期本金未还完，本期状态不变，累加计算实收本金数据
						if(actualPrincipal <principal){
							repaymentPrincipal = actualPrincipal;
							paymentInfo.setActualPrincipal(actualPrincipal + dto.getActualPrincipal());
							actualTotal = actualTotal+actualPrincipal;
						}else{
							repaymentPrincipal = principal;
							actualTotal = actualTotal+principal;
							paymentInfo.setActualPrincipal(dto.getShouldPrincipal());
						}
						//本期计算完毕后减去本期剩余应收本金，大于0，下次继续，小于等于0，下一期无法核销本金
						actualPrincipal = Double.parseDouble(df.format(actualPrincipal-principal));
						if(repaymentPrincipal>0){
							//本金的收取记录
							RepaymentRecordDTO principalDto = conver(result);
							principalDto.setRepaymentType(Constants.REPAYMENT_PRO_1);
							principalDto.setRepaymentMoney(repaymentPrincipal);
							principalDto.setPlanCycleNum(dto.getPlanCycleNum());
							recordList.add(principalDto);
						}
					}
					paymentInfo.setActualTotal(actualTotal);//应收合计
					double shouldTotal = Double.parseDouble(df.format(dto.getShouldInterest()+dto.getShouldPrincipal()));//应收合计
					//实收合计大于等于应收合计的，表示本期已还完，变更状态为正常已还
					if(actualTotal >= shouldTotal){
						plan.setThisStatus(Constants.REPAYMENT_PLAN_STATUS_2);
						plan.setIsReconciliation(1);//已对账
						planList.add(plan);
					}
					//只有实收利息大于0或者实收本金大于0时才需要更新正常还款信息
					if(actualTotal >0){
						//正常还款Id大于0，变更操作，否则做新增操作
						if(paymentId >0){
							upPaymentList.add(paymentInfo);
						}else{
							addPaymentList.add(paymentInfo);
						}
					}
					
				}
				
			}
			
		}
		//批量处理逾期信息
		if(overdueList.size()>0){
			bizProjectOverdueMapper.updateOverdues(overdueList);
		}
		//批量处理还款计划信息
		if(planList.size()>0){
			projectInfoMapper.updateRepayments(planList);
		}
		//批量处理更新正常还款信息
		if(upPaymentList.size()>0){
			bizPaymentInfoMapper.updatePayments(upPaymentList);
		}
		//批量新增正常还款信息
		if(addPaymentList.size()>0){
			bizPaymentInfoMapper.insertPayments(addPaymentList);
		}
		
		int status = Constants.REPAYMENT_STATUS_1;//项目还款状态
		//再次查询还款计划表
		repaymentList = projectInfoMapper.queryRepayment(query );
		for(RepaymentDetailIndexDTO repaymentDetail : repaymentList){
			int planCycleNum = repaymentDetail.getPlanCycleNum();
			//最后一期逾期未还，则项目还款状态为已逾期
			if(planCycleNum == maxPlanCycleNum && repaymentDetail.getThisStatus() == Constants.REPAYMENT_PLAN_STATUS_3){
				status = Constants.REPAYMENT_STATUS_4;
				break;
			}
			//最后一期逾期已还、正常已还、提前还款，表示项目还款状态已结清
			if(planCycleNum == maxPlanCycleNum && (repaymentDetail.getThisStatus() == Constants.REPAYMENT_PLAN_STATUS_2 
					|| repaymentDetail.getThisStatus() == Constants.REPAYMENT_PLAN_STATUS_4
					|| repaymentDetail.getThisStatus() == Constants.REPAYMENT_PLAN_STATUS_5)){
				status = Constants.REPAYMENT_STATUS_2;
				break;
			}
			//如果项目中有一期逾期未还且不是最后一期，则项目处于逾期还款中
			if(repaymentDetail.getThisStatus() == Constants.REPAYMENT_PLAN_STATUS_3 && planCycleNum != maxPlanCycleNum){
				status = Constants.REPAYMENT_STATUS_3;
			}
			//项目期数均为正常未还，则项目还款状态为正常还款中
			if(repaymentDetail.getThisStatus() == Constants.REPAYMENT_PLAN_STATUS_1 && status != Constants.REPAYMENT_STATUS_3){
				status = Constants.REPAYMENT_STATUS_1;//项目还款状态
			}
		}
		
		
		//查询该项目的回款记录信息
		RepaymentDTO repaymentDTO = foreRepaymentMapper.getRepaymentByProjectId(projectId);
		if(repaymentDTO == null){
			repaymentDTO = new RepaymentDTO();
		}
		double repaymentMoney = repaymentDTO.getRepaymentMoney();
		repaymentDTO.setProjectId(projectId);
		repaymentDTO.setCreaterId(userId);
		repaymentDTO.setNewRepaymentDate(actualDate);
		//实收合计回款金额
		repaymentDTO.setRepaymentMoney(repaymentMoney+result.getRepaymentMoney());
		if(status >0){
			repaymentDTO.setStatus(status);//还款状态
		}
		
		int pid = repaymentDTO.getPid();
		//变更项目还款信息
		if(pid >0){
			foreRepaymentMapper.updateRepayment(repaymentDTO);
		}else{
			foreRepaymentMapper.addRepayment(repaymentDTO);
			pid = repaymentDTO.getPid();
		}
		//批量处理还款记录信息
		if(recordList != null && recordList.size()>0){
			List<RepaymentRecordDTO> list = converRecord(recordList,pid);
			foreRepaymentMapper.insertPaymentRecords(list);
		}
		
		//如果项目还款已结清，则修改项目赎楼状态为已结清
		if(status == Constants.REPAYMENT_STATUS_2){
			//修改赎楼项目状态
			projectMapper.updateProjectForeStatusByPid(projectId, Constants.MORTGAGE_LOAN_TO_OVER, DateUtil.format(new Date()));
			//记录回款信息动态记录
			BizDynamic bizDynamic = new BizDynamic();
			bizDynamic.setProjectId(projectId);
			bizDynamic.setHandleAuthorId(result.getCreaterId());
			bizDynamic.setStatus(MortgageDynamicConstant.STATUS_3);
			String bizDynamicRemark = "回款金额:" + MoneyFormatUtil.format(repaymentDTO.getRepaymentMoney());
			bizDynamic.setRemark(bizDynamicRemark);
			bizDynamic.setModuelNumber(MortgageDynamicConstant.MODUEL_NUMBER_FINANCE);
			bizDynamic.setDynamicNumber(MortgageDynamicConstant.DYNAMIC_NUMBER_FINANCE_7);
			bizDynamic.setDynamicName(MortgageDynamicConstant.DYNAMIC_NUMBER_FINANCE_MAP.get(MortgageDynamicConstant.DYNAMIC_NUMBER_FINANCE_7));
			bizDynamic.setFinishDate(DateUtils.getCurrentDateTime());
			bizDynamicService.addOrUpdateBizDynamic(bizDynamic);
		}
		
		return 0;
	}
	
	/**
	 * 
	 * converRecord:设置还款记录中关联的还款ID <br/>
	 * @author baogang
	 * @param recordList
	 * @param repaymentId
	 * @return
	 *
	 */
	private List<RepaymentRecordDTO> converRecord(List<RepaymentRecordDTO> recordList,int repaymentId){
		
		List<RepaymentRecordDTO> result = new ArrayList<RepaymentRecordDTO>();
		for(RepaymentRecordDTO record : recordList){
			record.setRepaymentId(repaymentId);
			result.add(record);
		}
		
		return result;
	}
	
	
	/**
	 * 
	 * conver:抽取公共的转换还款记录的信息. <br/>
	 * @author baogang
	 * @param result
	 * @param repaymentId
	 * @param repaymentMoney
	 * @return
	 *
	 */
	private RepaymentRecordDTO conver(RepaymentDetailIndexDTO result){
		String actualDate = result.getRepaymentDate();//实收日期
		int userId = result.getCreaterId();//实收操作人
		String accountNo = result.getAccountNo();
		String remark = result.getRemark();
		RepaymentRecordDTO dto = new RepaymentRecordDTO();
		dto.setAccountNo(accountNo);
		dto.setCreaterId(userId);
		dto.setRepaymentDate(actualDate);
		dto.setRemark(remark);
		dto.setProjectId(result.getProjectId());
		return dto;
	}
	
	@Override
	public Project getLoanProjectByProjectId (int projectId) throws ThriftServiceException, TException
	{
		Project project = new Project();
		try {
			project = projectMapper.getProjectInfoById(projectId);
			project.setProjectGuarantee(projectGuaranteeMapper.getGuaranteeByProjectId(projectId));
			project.setCusEnterpriseInfo(cusEnterpriseInfoMapper.getEnterpriseInfoByProjectId(projectId));
			project.setCusCredentials(cusCredentialsMapper.getCredentialsByProjectId(projectId));
			project.setCusCardInfo(cusCardInfoMapper.getCardInfoByProjectId(projectId));
			ProjectProperty property = projectPropertyMapper.getPropertyByProjectId(projectId);
			project.setProjectProperty(property);

			ProjectForeclosure projectForeclosure = projectForeclosureMapper.getForeclosureByProjectId(projectId);
			project.setProjectForeclosure(projectForeclosure);
			if (project.getProjectGuarantee() == null) {
				project.setProjectGuarantee(new ProjectGuarantee());
			}
			if (project.getCusEnterpriseInfo() == null) {
				project.setCusEnterpriseInfo(new CusEnterpriseInfo());
			}
			if (project.getCusCredentials() == null) {
				project.setCusCredentials(new CusCredentials());
			}
			if (project.getCusCardInfo() == null) {
				project.setCusCardInfo(new CusCardInfo());
			}
			if (project.getProjectForeclosure() == null) {
				project.setProjectForeclosure(new ProjectForeclosure());
			}
		} catch (Exception e) {
			logger.error("根据项目ID查询项目相关信息错误:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return project;
	}

	@Override
	public int saveOrUpdateRepayInfo (PreApplyRepayBaseDTO repayInfo) throws TException
	{
		int rows = 0;
		int projectId = repayInfo.getProjectId();
		//查询是否存在项目关联的提前还款
		int count = repaymentMapper.queryPrerepayCount(projectId);
		//如果不存在提前还款信息，则新增一条信息
		if(count == 0){
			rows = repaymentMapper.insertPreApplyRepay(repayInfo);
		}
		
		return rows;
	}

	/**
	 * 
	 * TODO 可贷金额计算
	 * @see com.xlkfinance.bms.rpc.project.ProjectInfoService.Iface#makeBizCalLoanMoney(com.xlkfinance.bms.rpc.project.BizCalLoanMoney)
	 */
	@Override
	public List<BizCalLoanMoney> makeBizCalLoanMoney (BizCalLoanMoney condition) throws TException
	{
		List<BizCalLoanMoney> list = new ArrayList<BizCalLoanMoney>();
		double rentMoney = condition.getRentMoney();//租金
		double feeRate = condition.getFeeRate();//月利率
		int loanTerm = condition.getLoanTerm();//借款期数
		double loanCoef = condition.getLoanCoef();//可贷系数
		int maxLoanTerm = 120;//最大租赁期限
		int loanTermNum =new Double((maxLoanTerm/6) - (loanTerm/6)+1).intValue();//120/6-12/6+1=20-2+1=19
		int rentTerm = maxLoanTerm;//租赁期限
		for(int num =1;num<=loanTermNum;num++ ){
			BizCalLoanMoney result = new BizCalLoanMoney();
			result.setRentTerm(rentTerm);//租赁期数
			result.setRentMoney(rentMoney);//租金
			result.setLoanTerm(loanTerm);//借款期数
			double rentalReturn = rentMoney*rentTerm;//租金回报 = 签约租金*租赁期限
			result.setRentalReturn(rentalReturn);
			result.setLoanCoef(loanCoef);//可贷系数
			double loanMoney = rentalReturn*loanCoef;//借款金额=租金回报*可贷系数
			result.setLoanMoney(loanMoney);//借款金额
			double monthlyRepaymentMoney = NumberUtils.PMT(feeRate/100, loanTerm, loanMoney);
			result.setMonthlyRepaymentMoney(monthlyRepaymentMoney );//月还款金额
			double repaymentMoneyTotal = monthlyRepaymentMoney*loanTerm;//总还款金额
			result.setRepaymentMoneyTotal(repaymentMoneyTotal );
			result.setPayMoney(monthlyRepaymentMoney - rentMoney);//借款人承担 = 月还款金额-租金
			result.setResultMoney(result.getRentalReturn()-repaymentMoneyTotal);//租金回报-总还款
			rentTerm = rentTerm-6;
			list.add(result);
		}
		return list;
	}
	
	@Override
	public int setNextUserInfo(String candidateUsers, int projectId) throws ThriftServiceException, TException {
		Project project = projectMapper.getProjectInfoById(projectId);
		String nextUserId = "";
		if (candidateUsers != null) {
			String[] candidateUserArr = candidateUsers.split(",");
			if (candidateUserArr != null && candidateUserArr.length > 0) {
				List<SysUser> userList = sysUserMapper.queryUserByUserName(candidateUserArr);
				if (!CollectionUtils.isEmpty(userList)) {
					for (int i = 0; i < userList.size(); i++) {
						SysUser user = userList.get(i);
						nextUserId += user.getRealName();
						if (i < userList.size() - 1) {
							nextUserId += ",";
						}
					}
				}
			}
		}
		project.setNextUserId(StringUtil.isBlank(nextUserId) ? "-" : nextUserId);
		projectMapper.updateByPrimaryKey(project);
		return 0;
	}
	/**
	 * 
	 * 获取消费贷还款计划表
	 * @see com.xlkfinance.bms.rpc.project.ProjectInfoService.Iface#queryRepaymentPlan(int, int)
	 */
	@Override
	public List<RepaymentPlanBaseDTO> queryRepaymentPlan (int projectId, int userId,String receDate) throws ThriftServiceException, TException
	{
		//项目信息
		Project project = getLoanProjectByProjectId(projectId);
		ProjectForeclosure foreclosure = project.getProjectForeclosure();
		ProjectGuarantee guarantee = project.getProjectGuarantee();
		double rentMoney = guarantee.getContractPrice();//物业最终签约价格
		
		//还款计划列表
		List<RepaymentPlanBaseDTO> list = loanRepaymentPlanMapper.getRepaymentsByProjectId(projectId);
		if(list == null || list.size()==0){
			//放款时间不为空是修改项目放款时间
			if(!StringUtil.isBlank(receDate)){
				foreclosure.setReceDate(receDate);
				project.setProjectForeclosure(foreclosure);
				projectForeclosureMapper.updateByPrimaryKey(foreclosure);
			}
			//现在消费贷只做还款方式为等额本息的项目，还款计划表测算只限实现第一种
			CalPlanLine dcal = calculationPlanFactory.factory(2);
			list = dcal.execute(project, userId);
			if(list != null && list.size()>0 && !StringUtil.isBlank(receDate)){
				loanRepaymentPlanMapper.insertRepayments(list);
			}
		}
		
		RepaymentPlanBaseDTO resultDto = new RepaymentPlanBaseDTO();
		resultDto.setPlanRepayDt("合计");
		double rentMoneyTotal = 0;
		double shouldTotal = 0;
		double shouldPrincipalTotal = 0;
		double shouldInterestTotal = 0;
		double shouldPaymentMoney = 0;
		List<RepaymentPlanBaseDTO> result = new ArrayList<RepaymentPlanBaseDTO>();
		if(list != null && list.size()>0){
			for(RepaymentPlanBaseDTO dto :list){
				dto.setRentMoney(rentMoney);
				dto.setShouldPaymentMoney(dto.getTotal()-dto.getRentMoney());
				rentMoneyTotal += dto.getRentMoney();
				shouldTotal += dto.getTotal();
				shouldPrincipalTotal += dto.getShouldPrincipal();
				shouldInterestTotal+= dto.getShouldInterest();
				shouldPaymentMoney += dto.getShouldPaymentMoney();
				result.add(dto);
			}
		}
		
		resultDto.setRentMoney(rentMoneyTotal);
		resultDto.setTotal(shouldTotal);
		resultDto.setShouldInterest(shouldInterestTotal);
		resultDto.setShouldPaymentMoney(shouldPaymentMoney);
		resultDto.setShouldPrincipal(shouldPrincipalTotal);
		result.add(resultDto);
		return result;
	}

}
