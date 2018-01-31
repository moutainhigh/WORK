package com.xlkfinance.bms.server.aom.project.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.achievo.framework.annotation.ThriftService;
import com.achievo.framework.util.DateUtil;
import com.qfang.xk.aom.rpc.org.OrgAssetsCooperationInfo;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyInf;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyService;
import com.qfang.xk.aom.rpc.project.BizProject;
import com.qfang.xk.aom.rpc.project.BizProjectDTO;
import com.qfang.xk.aom.rpc.project.BizProjectService.Iface;
import com.qfang.xk.aom.rpc.project.OrderRejectInfo;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.constant.BizDynamicConstant;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.BizOriginalLoan;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstate;
import com.xlkfinance.bms.rpc.beforeloan.DataInfo;
import com.xlkfinance.bms.rpc.beforeloan.ProjectForeInformation;
import com.xlkfinance.bms.rpc.beforeloan.ProjectForeclosure;
import com.xlkfinance.bms.rpc.beforeloan.ProjectGuarantee;
import com.xlkfinance.bms.rpc.beforeloan.ProjectProperty;
import com.xlkfinance.bms.rpc.beforeloan.ProjectPublicMan;
import com.xlkfinance.bms.rpc.beforeloan.ProjectSurveyReport;
import com.xlkfinance.bms.rpc.beforeloan.ProjectSurveyReportService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.customer.CusAcct;
import com.xlkfinance.bms.rpc.customer.CusAcctService;
import com.xlkfinance.bms.rpc.customer.CusBlacklistRefuse;
import com.xlkfinance.bms.rpc.customer.CusPerBaseDTO;
import com.xlkfinance.bms.rpc.customer.CusPerService;
import com.xlkfinance.bms.rpc.customer.CusRelation;
import com.xlkfinance.bms.rpc.inloan.BizDynamic;
import com.xlkfinance.bms.rpc.inloan.BizDynamicService;
import com.xlkfinance.bms.rpc.system.SysLookupVal;
import com.xlkfinance.bms.server.aom.org.mapper.OrgAssetsCooperationInfoMapper;
import com.xlkfinance.bms.server.aom.org.mapper.OrgCooperatCompanyApplyInfMapper;
import com.xlkfinance.bms.server.aom.project.mapper.BizProjectMapper;
import com.xlkfinance.bms.server.aom.project.mapper.OrderRejectInfoMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.BizOriginalLoanMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.BizProjectEstateMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.DataUploadMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectForeInformationMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectForeclosureMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectGuaranteeMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectPropertyMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectPublicManMapper;
import com.xlkfinance.bms.server.customer.mapper.CusAcctMapper;
import com.xlkfinance.bms.server.customer.mapper.CusBlacklistRefuseMapper;
import com.xlkfinance.bms.server.customer.mapper.CusPersonMapper;
import com.xlkfinance.bms.server.customer.mapper.CusRelationMapper;
import com.xlkfinance.bms.server.product.mapper.ProductMapper;
import com.xlkfinance.bms.server.system.mapper.SysLookupValMapper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-07-19 11:43:41 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 业务申请表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("bizProjectServiceImpl")
@ThriftService(service = "com.qfang.xk.aom.rpc.project.BizProjectService")
public class BizProjectServiceImpl implements Iface {
	private Logger logger = LoggerFactory.getLogger(BizProjectServiceImpl.class);

   	@Resource(name = "bizProjectMapper")
   	private BizProjectMapper bizProjectMapper;

   	@Resource(name="projectGuaranteeMapper")
   	private ProjectGuaranteeMapper projectGuaranteeMapper;
   
   	@Resource(name="projectForeclosureMapper")
   	private ProjectForeclosureMapper projectForeclosureMapper;
   
   	@Resource(name="projectPropertyMapper")
   	private ProjectPropertyMapper projectPropertyMapper;
   
   	@Resource(name = "orgAssetsCooperationInfoMapper")
   	private OrgAssetsCooperationInfoMapper orgAssetsCooperationInfoMapper;
   
   	@Resource(name = "dataUploadMapper")
   	private DataUploadMapper dataUploadMapper;
   
   	@Resource(name = "orgCooperatCompanyApplyServiceImpl")
	private OrgCooperatCompanyApplyService.Iface orgCooperatCompanyApplyServiceImpl;
   	
   	@Resource
	private OrgCooperatCompanyApplyInfMapper orgCooperatMapper;

	@Resource(name = "cusPersonMapper")
	private CusPersonMapper cusPersonMapper;
   	
	@Resource(name = "cusPerServiceImpl")
	private CusPerService.Iface perClient;
	
	@Resource(name = "productMapper")
	private ProductMapper productMapper;
	
    @Resource(name = "orderRejectInfoMapper")
    private OrderRejectInfoMapper orderRejectInfoMapper;
    
	@Resource(name = "sysLookupValMapper")
	private SysLookupValMapper sysLookupValMapper;
	
	@Resource(name="projectForeInformationMapper")
	private ProjectForeInformationMapper foreInformationMapper;
	
	@Resource(name = "cusRelationMapper")
	private CusRelationMapper cusRelationMapper;
	
	@Resource(name = "bizProjectEstateMapper")
	private BizProjectEstateMapper bizProjectEstateMapper;
	
	@Resource(name = "cusAcctServiceImpl")
	private CusAcctService.Iface cusAcctService;
	
	@Resource(name = "cusBlacklistRefuseMapper")
	private CusBlacklistRefuseMapper cusBlacklistRefuseMapper;
	
    @Resource(name = "bizOriginalLoanMapper")
    private BizOriginalLoanMapper bizOriginalLoanMapper;
	
    @Resource(name = "bizDynamicServiceImpl")
    private BizDynamicService.Iface bizDynamicService;
    
	@Resource(name = "projectPublicManMapper")
	private ProjectPublicManMapper projectPublicManMapper;
    
	@Resource(name = "cusAcctMapper")
	private CusAcctMapper cusAcctMapper;
	
	@Resource(name = "projectSurveyReportServiceImpl")
	private ProjectSurveyReportService.Iface projectSurveyReportService;
	
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-07-19 11:43:41
    */
   @Override
   public List<BizProject> getAll(BizProject bizProject) throws ThriftServiceException, TException {
      return bizProjectMapper.getAll(bizProject);
   }

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-07-19 11:43:41
    */
   @Override
   public BizProject getById(int pid) throws ThriftServiceException, TException {
      BizProject bizProject = bizProjectMapper.getById(pid);
      if (bizProject==null) {
         return new BizProject();
      }
      return bizProject;
   }

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-07-19 11:43:41
    */
   @Override
   public int insert(BizProject bizProject) throws ThriftServiceException, TException {
      return bizProjectMapper.insert(bizProject);
   }

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-07-19 11:43:41
    */
   @Override
   public int update(BizProject bizProject) throws ThriftServiceException, TException {
      return bizProjectMapper.update(bizProject);
   }

   /**
    * 分页查询业务数据
    */
	@Override
	public List<BizProjectDTO> getBizProjectByPage(BizProjectDTO bizProjectDTO)
			throws TException {
		List<BizProjectDTO> result = new ArrayList<BizProjectDTO>();
		try {
			result = bizProjectMapper.getBizProjectByPage(bizProjectDTO);
		} catch (Exception e) {
			logger.error("分页查询业务数据:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return result;
	}
	
	@Override
	public int getBizProjectCount(BizProjectDTO bizProjectDTO) throws TException {
		return bizProjectMapper.getBizProjectCount(bizProjectDTO);
	}
	
	/**
	 * 新增业务申请
	 */
	@Transactional
	@Override
	public String addBizProject(BizProject bizProject)
			throws ThriftServiceException, TException {
		String result = "";
		try {
			//判断用户是否重复提交了
			if(!checkProjectRepeat(bizProject)){
				throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "业务申请已提交，请勿重复提交!");
			}
			int requestStatus = bizProject.getRequestStatus();
			int orgId = bizProject.getOrgId();
			String orgName = "";
			if(orgId >0){
				OrgAssetsCooperationInfo orgInfo = orgAssetsCooperationInfoMapper.getById(orgId);
				orgName =orgInfo.getOrgName();
			}
			if(StringUtil.isBlank(orgName)){
				throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "机构不存在，无法提交!");
			}
			
			//物业及买卖方信息
			ProjectForeclosure projectForeclosure = bizProject.getProjectForeclosure();
			//金额以及费用信息
			ProjectGuarantee projectGuarantee = bizProject.getProjectGuarantee();
			
			String orgCustomerName = bizProject.getOrgCustomerName();
			String projectName = makeProjectName(orgName,orgCustomerName,orgId);
			String projectNumber = makeProjectNumber();
			bizProject.setProjectName(projectName);
			bizProject.setProjectNumber(projectNumber);
			
			//设置赎楼状态为1（未提交）
			bizProject.setForeclosureStatus(1);
			
			bizProject.setProjectType(AomConstant.PROJECT_TYPE_6);
			bizProject.setIsClosed(AomConstant.IS_CLOSED_2);
			bizProject.setIsAssigned(AomConstant.IS_ASSIGNED_1);//新订单默认未分配
			bizProjectMapper.insert(bizProject);
			//获取入库后主键值
			int projectId = bizProject.getPid();
			
			int foreclosureId = 0;//赎楼ID
			int propertyId = 0;//物业ID
			int guaranteeId = 0;//费用ID
			if(projectId >0){
				
				// 创建客户对象,修改客户状态
				CusAcct cusAcct = new CusAcct();
				cusAcct.setPid(bizProject.getAcctId());
				cusAcct.setCusStatus(4);
				// 修改当前客户的状态
				cusAcctMapper.updateCusStatus(cusAcct);
				
				// 判断是否选取共同借款人
				if (bizProject.getUserPids() != null && !bizProject.getUserPids().equals("")) {
					String[] userPids = bizProject.getUserPids().split(",");
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
				String houseIds = bizProject.getHouseIds();
				String houseName="";
				String housePropertyCard = "";
				double tranasctionMoney = 0.0;
				double evaluationPrice = 0.0;
				double area=0.0;
				double costMoney = 0.0;
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
					}
					houseName = houseName.substring(0, houseName.length()-1);
					housePropertyCard = housePropertyCard.substring(0, housePropertyCard.length()-1);
				}
				
				//原贷款ID
				String originalLoanIds = bizProject.getOriginalLoanIds();
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
				
				// 获取赎楼ID
				foreclosureId = projectForeclosureMapper.getSeqBizProjectForeclosure();
				projectForeclosure.setPid(foreclosureId);
				projectForeclosure.setProjectId(projectId);
				int loanDays = projectForeclosure.getLoanDays();
				String receDate = projectForeclosure.getReceDate();//希望放款日期
				if(!StringUtil.isBlank(receDate)){
				Date payDate = DateUtils.addDay(DateUtils.stringToDate(receDate, "yyyy-MM-dd"), loanDays - 1);
	            // 根据计划放款日期、贷款天数计算出计划还款日期,修改项目原先的还款日期与放款日期
	            projectForeclosure.setPaymentDate(DateUtils.dateFormatByPattern(payDate, "yyyy-MM-dd"));
				}
				// 赎楼信息入库
				projectForeclosureMapper.insertForeclosure(projectForeclosure);
				// 保存物业双方信息
				ProjectProperty projectProperty = bizProject.getProjectProperty();
				// 获取物业ID
				propertyId = projectPropertyMapper.getSeqBizProjectProperty();
				projectProperty.setPid(propertyId);
				projectProperty.setProjectId(projectId);
				//设置多物业信息
				projectProperty.setHouseName(houseName);
				projectProperty.setHousePropertyCard(housePropertyCard);
				projectProperty.setTranasctionMoney(tranasctionMoney);
				projectProperty.setArea(area);
				projectProperty.setCostMoney(costMoney);
				projectProperty.setEvaluationPrice(evaluationPrice);
				
				// 物业信息入库
				projectPropertyMapper.insertProperty(projectProperty);

				// 保存赎楼项目费用信息
				
				// 获取费用ID
				guaranteeId = projectGuaranteeMapper
						.getSeqBizProjectGuarantee();
				projectGuarantee.setPid(guaranteeId);
				//projectGuarantee.setFeeRate(bizProject.getLoanRate());//借款利率
				projectGuarantee.setProjectId(projectId);
				// 费用信息入库
				projectGuaranteeMapper.insertGuarantee(projectGuarantee);
			}
			//添加赎楼清单
			List<SysLookupVal> list = sysLookupValMapper.getSysLookupValByLookType("PROJECT_FORE_INFORMATION");
			if(list != null && list.size()>0){ 
				for(SysLookupVal lookUp : list){
					ProjectForeInformation foreInfo = new ProjectForeInformation();
					foreInfo.setForeId(lookUp.getPid());
					foreInfo.setProjectId(projectId);
					foreInformationMapper.insertProjectForeInformation(foreInfo);
				}
			}
			
			//保存特殊情况说明
			ProjectSurveyReport projectSurveyReport = new ProjectSurveyReport();
			projectSurveyReport.setSpecialDesc(bizProject.getSurveyResult());
			projectSurveyReport.setProjectId(projectId);
			projectSurveyReport.setStatus(1);
			projectSurveyReportService.addSurveyReport(projectSurveyReport);
			
			result = projectId+","+foreclosureId+","+propertyId+","+guaranteeId;
			//提交申请时修改可用额度
			if(requestStatus == AomConstant.BUSINESS_REQUEST_STATUS_2){
				updateAvailableLimit(bizProject);
			}
		} catch (Exception e) {
			logger.error("保存业务申请信息:" + ExceptionUtil.getExceptionMessage(e));
			throw new RuntimeException("保存业务申请信息失败,请重新操作!"+ExceptionUtil.getExceptionMessage(e));
		}
		return result;
	}
	
	/**
	 * 生成项目名称
	  * @param orgName
	  * @param customerName
	  * @param orgId
	  * @return
	  * @author: baogang
	  * @date: 2016年7月19日 下午5:32:26
	 */
	private String makeProjectName(String orgName,String customerName, int orgId) {
		// 首先附加 :机构名称加客户名称
		String projectName = customerName;
		// 获取当前的8位年月日,并附加到新的项目名称里面
		projectName += DateUtil.format(new Date(), "yyyyMMdd");
		// 获取当前日期的项目名称的最后2位自然数
		int maxNumber = bizProjectMapper.getMaxProjectName(orgId);
		// 判断是否是一位数，还是两位数
		if (maxNumber > 10) {
			projectName += maxNumber;
		} else {
			projectName += "0" + maxNumber;
		}
		return projectName;
	}
	
	/**
	 * 生成项目编号
	  * @return
	  * @author: baogang
	  * @date: 2016年7月19日 下午5:34:27
	 */
	private String makeProjectNumber() {
		// 首先附加 :产品缩写，赎楼贷
		String projectNumber = "SLD";
		
		// 获取当前的4位年 并附加到新的项目名称里面
		projectNumber += DateUtil.format(new Date(), "yyyy");
		// 获取当前日期的项目名称的最后5位自然数
		String maxNumber = bizProjectMapper.getMaxProjectNumber();
		// 判断是否为空
		if (maxNumber != null) {
			maxNumber = (Integer.parseInt(maxNumber) + 1) + "";
			if (maxNumber.length() == 1) {
				projectNumber += "0000" + maxNumber;
			} else if (maxNumber.length() == 2) {
				projectNumber += "000" + maxNumber;
			} else if (maxNumber.length() == 3) {
				projectNumber += "00" + maxNumber;
			} else if (maxNumber.length() == 4) {
				projectNumber += "0" + maxNumber;
			} else {
				projectNumber += maxNumber;
			}
		} else {
			projectNumber += "00001";
		}

		return projectNumber;
	}
	
	/**
	 * 防止用户重复提单
	  * @param bizProject
	  * @return
	  * @author: baogang
	  * @date: 2016年7月19日 下午5:12:04
	 */
	private boolean checkProjectRepeat(BizProject bizProject){
		boolean flag = true;
		BizProject query = new BizProject();
		query.setOrgCustomerName(bizProject.getOrgCustomerName());
		query.setOrgCustomerPhone(bizProject.getOrgCustomerPhone());
		query.setOrgCustomerCard(bizProject.getOrgCustomerCard());
		query.setPlanLoanMoney(bizProject.getProjectGuarantee().getLoanMoney());
		query.setPlanLoanDate(bizProject.getPlanLoanDate());
		List<BizProject> result = bizProjectMapper.getAll(query);
		if(result != null && result.size()>0){
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 查询业务申请详情信息
	 */
	@Override
	public BizProject getProjectByPid(int pid) throws TException {
		BizProject bizProject = new BizProject();
		try {
			bizProject = bizProjectMapper.getById(pid);
			if (bizProject!=null) {
				//查询附加信息
				ProjectForeclosure projectForeclosure = projectForeclosureMapper.getProjectForeclosureInfo(pid);
				ProjectGuarantee projectGuarantee = projectGuaranteeMapper.getGuaranteeByProjectId(pid);
				ProjectProperty projectProperty = projectPropertyMapper.getPropertyByProjectId(pid);
				
				bizProject.setProjectForeclosure(projectForeclosure);
				bizProject.setProjectProperty(projectProperty);
				bizProject.setProjectGuarantee(projectGuarantee);
				
				List<BizProjectEstate> estateList = bizProjectEstateMapper.getAllByProjectId(pid);
				bizProject.setEstateList(estateList);
				
				BizOriginalLoan bizOriginalLoan  = new BizOriginalLoan();
				bizOriginalLoan.setStatus(1);
				bizOriginalLoan.setProjectId(pid);
				List<BizOriginalLoan> originalLoanList = bizOriginalLoanMapper.getAllByCondition(bizOriginalLoan);
				bizProject.setOriginalLoanList(originalLoanList);
				
				ProjectSurveyReport surveyReport = projectSurveyReportService.getSurveyReportByProjectId(pid);
				if(surveyReport != null && surveyReport.getPid()>0){
					bizProject.setSurveyResult(surveyReport.getSpecialDesc());
				}
			}
			
		} catch (Exception e) {
			logger.error("查询业务申请详情信息:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return bizProject;
	}

	@Override
	public int updateProjectStatus(BizProject bizProject) throws TException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	/**
	 * 修改业务申请
	 */
	@Transactional
	@Override
	public String updateBizProject(BizProject bizProject) throws TException {
		String result = "";
		try {
			//获取主键值
			int projectId = bizProject.getPid();
			int requestStatus = bizProject.getRequestStatus();
			if(requestStatus == AomConstant.BUSINESS_REQUEST_STATUS_2){
				bizProject.setRequestDttm(DateUtils.getCurrentDateTime());
			}
			
			bizProjectMapper.update(bizProject);
			
			int foreclosureId = 0;//赎楼ID
			int propertyId = 0;//物业ID
			int guaranteeId = 0;//费用ID
			if(projectId >0){
				// 保存赎楼信息
				ProjectForeclosure projectForeclosure = bizProject.getProjectForeclosure();
				projectForeclosure.setProjectId(projectId);
				int loanDays = projectForeclosure.getLoanDays();
				String receDate = projectForeclosure.getReceDate();//希望放款日期
				if(!StringUtil.isBlank(receDate)){
				Date payDate = DateUtils.addDay(DateUtils.stringToDate(receDate, "yyyy-MM-dd"), loanDays - 1);
	            // 根据计划放款日期、贷款天数计算出计划还款日期,修改项目原先的还款日期与放款日期
	            projectForeclosure.setPaymentDate(DateUtils.dateFormatByPattern(payDate, "yyyy-MM-dd"));
				}
				
				// 获取赎楼ID
				ProjectForeclosure foreclosure = projectForeclosureMapper.getProjectForeclosureInfo(projectId);
				if(foreclosure != null && foreclosure.getPid()>0){
					foreclosureId = foreclosure.getPid();
					// 赎楼信息入库
					projectForeclosureMapper.updateByPrimaryKey(projectForeclosure);
				}else{
					foreclosureId = projectForeclosureMapper.getSeqBizProjectForeclosure();
					projectForeclosure.setPid(foreclosureId);
					projectForeclosureMapper.insertForeclosure(projectForeclosure);
				}
				
				// 保存物业双方信息
				ProjectProperty projectProperty = bizProject.getProjectProperty();
				projectProperty.setProjectId(projectId);
				// 获取物业ID
				ProjectProperty property = projectPropertyMapper.getPropertyByProjectId(projectId);
				if(property != null && property.getPid()>0){
					propertyId = property.getPid();
					// 物业信息入库
					projectPropertyMapper.updateByPrimaryKey(projectProperty);
				}else{
					propertyId = projectPropertyMapper.getSeqBizProjectProperty();
					projectProperty.setPid(propertyId);
					projectPropertyMapper.insertProperty(projectProperty);
				}


				// 保存赎楼项目费用信息
				ProjectGuarantee projectGuarantee = bizProject.getProjectGuarantee();
				projectGuarantee.setProjectId(projectId);
				// 获取费用ID
				ProjectGuarantee guarantee = projectGuaranteeMapper.getGuaranteeByProjectId(projectId);
				if(guarantee != null && guarantee.getPid()>0){
					guaranteeId = guarantee.getPid();
					// 费用信息入库
					projectGuaranteeMapper.updateByPrimaryKey(projectGuarantee);
				}else{
					guaranteeId = projectGuaranteeMapper.getSeqBizProjectGuarantee();
					projectGuarantee.setPid(guaranteeId);
					projectGuaranteeMapper.insertGuarantee(projectGuarantee);
				}
			}
			
			//保存特殊情况说明
			ProjectSurveyReport projectSurveyReport = projectSurveyReportService.getSurveyReportByProjectId(projectId);
			if(projectSurveyReport == null || projectSurveyReport.getPid() <=0){
				projectSurveyReport = new ProjectSurveyReport();
				projectSurveyReport.setProjectId(projectId);
				projectSurveyReport.setStatus(1);
				projectSurveyReport.setSpecialDesc(bizProject.getSurveyResult());
				projectSurveyReportService.addSurveyReport(projectSurveyReport);
			}else{
				projectSurveyReport.setSpecialDesc(bizProject.getSurveyResult());
				projectSurveyReportService.updateSurveyReport(projectSurveyReport);
			}
			
			result = projectId+","+foreclosureId+","+propertyId+","+guaranteeId;
			//提交申请时修改可用额度
			if(requestStatus == AomConstant.BUSINESS_REQUEST_STATUS_2){
				updateAvailableLimit(bizProject);
			}
		} catch (Exception e) {
			logger.error("修改业务申请详情信息:" + ExceptionUtil.getExceptionMessage(e));
			throw new RuntimeException("修改业务申请详情信息失败,请重新操作!"+ExceptionUtil.getExceptionMessage(e));
		}
		return result;
	}

	/**
	 * 关闭订单
	 */
	@Override
	public int updateProjectClosed(BizProject bizProject) throws TException {
		int result = 0;
		try {
			result = bizProjectMapper.updateProjectClosed(bizProject);
		} catch (Exception e) {
			logger.error("关闭订单:" + ExceptionUtil.getExceptionMessage(e));
			throw new RuntimeException("关闭订单失败,请重新操作!"+ExceptionUtil.getExceptionMessage(e));
		}
		return result;
	}

	/**
	 * 查询上传文件列表
	 */
	@Override
	public List<DataInfo> getProjectDataInfos(int projectId) throws TException {
		BizProject project = getById(projectId);
		List<String> projectTypes = new ArrayList<String>();
		projectTypes.add("1");
		//判断项目交易类型，13755为交易类型，13756为非交易类型
		if(project.getBusinessCategory() == 13755){
			projectTypes.add("4");//1与4表示数据字典中文件类型的数值，数值1和4的文件为交易类型的文件
		}else if(project.getBusinessCategory() == 13756){
			projectTypes.add("3");//1与3表示数据字典中文件类型的数值，数值1和3的文件为非交易类型的文件
		}
		DataInfo dataInfo = new DataInfo();
		dataInfo.setProjectId(projectId);
		dataInfo.setProjectTypes(projectTypes);
		dataInfo.setPage(-1);//不分页
		return dataUploadMapper.dataListByType(dataInfo);
	}

	/**
	 * 保存资料信息
	 */
	@Override
	public int saveDataFile(DataInfo dataInfo) throws ThriftServiceException,
			TException {
		int pid = dataInfo.getDataId();
		if(pid >0){
			dataUploadMapper.editData(dataInfo);
		}else{
			dataUploadMapper.saveData(dataInfo);
		}
		return dataInfo.getDataId();
	}

	/**
	 * 修改可用额度，提交申请后修改机构可用授信额度
	 */
	@Transactional
	@Override
	public int updateAvailableLimit(BizProject bizProject)
			throws ThriftServiceException, TException {
		int result = 0;
		try {
			int orgId = bizProject.getOrgId();
			OrgCooperatCompanyApplyInf companyApplyInfo = orgCooperatCompanyApplyServiceImpl.getByOrgId(orgId);
			double applyMoney  = bizProject.getProjectGuarantee().getLoanMoney();//借款金额
			double availableLimit = companyApplyInfo.getAvailableLimit();//现有可用额度
			double usedLimit = companyApplyInfo.getUsedLimit();//已用授信额度
			double activateCreditLimit = companyApplyInfo.getActivateCreditLimit();//现有启用额度
			logger.info("减少可用额度updateAvailableLimit，orgId:"+orgId+",项目ID："+bizProject.getPid()+",借款金额："+applyMoney+",启用授信额度："+activateCreditLimit+",可用授信额度："+availableLimit+",已用授信额度："+usedLimit);
			availableLimit = activateCreditLimit - usedLimit;
			if(availableLimit <0){
				throw new RuntimeException("启用额度不足或者可用额度不足,请联系后台管理员!");
			}
			companyApplyInfo.setAvailableLimit(availableLimit);
			result = orgCooperatMapper.update(companyApplyInfo);
			//修改未成功，回滚
			if(result == 0){
				// 抛出异常处理
				throw new RuntimeException("减少可用额度出错,请重新操作!");
			}
		} catch (Exception e) {
			logger.error("修改可用额度:" + ExceptionUtil.getExceptionMessage(e));
			throw new RuntimeException("修改可用额度失败,请重新操作!"+ExceptionUtil.getExceptionMessage(e));
		}
		return result;
	}

	@Override
	public int delDataFile(int dataId) throws TException {
		boolean flag = dataUploadMapper.delData(dataId);
		
		return flag == true ?1:0;
	}
	
	/**
	 * 修改驳回状态
	 */
	@Transactional
	@Override
	public int updateProjectReject(BizProject bizProject) throws TException {
		int result = 0;
		try {
			int pid = bizProject.getPid();
			int isReject = bizProject.getIsReject();
			//判断拒单类型为黑名单时，需要将该业务涉及的客户加入黑名单
			int rejectType = bizProject.getRejectType();
			//业务动态记录
			BizDynamic bizDynamic = new BizDynamic();
			bizDynamic.setProjectId(bizProject.getPid());
			bizDynamic.setHandleAuthorId(bizProject.getExamineUser());
			bizDynamic.setStatus(BizDynamicConstant.STATUS_3);
			bizDynamic.setRemark(bizProject.getExamineOpinion());
			bizDynamic.setModuelNumber(BizDynamicConstant.MODUEL_NUMBER_OTHER);
			
			bizDynamic.setFinishDate(DateUtils.getCurrentDateTime());
			//判断为拒单时将撤单状态改为已撤单，方便贷中以及收费处理数据，业务动态记录为拒单
			if(isReject == AomConstant.IS_REJECT_4){
				bizProject.setIsChechan(Constants.PROJECT_IS_CHECHAN);
				bizProject.setIsClosed(AomConstant.IS_CLOSED_1);
				
				bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_OTHER_20);//机构业务拒单
				bizDynamic.setDynamicName(BizDynamicConstant.DYNAMIC_NUMBER_OTHER_MAP.get(BizDynamicConstant.DYNAMIC_NUMBER_OTHER_20));
			}else if(isReject == AomConstant.IS_REJECT_1 || isReject == AomConstant.IS_REJECT_3){
				bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_OTHER_30);//机构业务驳回，此处不区分完全驳回与驳回补充资料
				bizDynamic.setDynamicName(BizDynamicConstant.DYNAMIC_NUMBER_OTHER_MAP.get(BizDynamicConstant.DYNAMIC_NUMBER_OTHER_30));
			}
			
			result = bizProjectMapper.updateProjectReject(bizProject);

			if(rejectType == AomConstant.REJECT_TYPE_1){//拒单类型黑名单
				//获取客户ID
				int acctId = bizProjectMapper.getById(pid).getAcctId();
				//查询该客户是否已是黑名单,
				CusPerBaseDTO cusPerBaseDTO = new CusPerBaseDTO();
				CusAcct cusAcct = new CusAcct();
				cusAcct.setPid(acctId);
				cusPerBaseDTO.setCusAcct(cusAcct);
				int blackFlag = cusBlacklistRefuseMapper.getBlackByCertNum(cusPerBaseDTO );
				//黑名单无此客户则添加，有此客户则不作操作
				if(blackFlag == 0){
					CusBlacklistRefuse cusBlacklistRefuse = new CusBlacklistRefuse();//拒单实体类
					cusBlacklistRefuse.setListType("2");//黑名单
					cusBlacklistRefuse.setRefuseReason(bizProject.getExamineOpinion());
					cusAcctService.addCusBlacklistRefuse(cusBlacklistRefuse , String.valueOf(acctId));
				}
			}
			
			//判断驳回状态为完全驳回或者拒单时,添加驳回记录，修改机构可用额度
			if(isReject == AomConstant.IS_REJECT_1 || isReject == AomConstant.IS_REJECT_4){
				OrderRejectInfo rejectInfo = new OrderRejectInfo();
				rejectInfo.setOrderId(pid);
				rejectInfo.setExamineOpinion(bizProject.getExamineOpinion());
				rejectInfo.setExamineUser(bizProject.getExamineUser());
				result = orderRejectInfoMapper.insert(rejectInfo);
				//修改可用额度
				BizProject oldProject = getProjectByPid(pid);
				int orgId = oldProject.getOrgId();
				OrgCooperatCompanyApplyInf companyApplyInfo = orgCooperatCompanyApplyServiceImpl.getByOrgId(orgId);
				double applyMoney  = oldProject.getProjectGuarantee().getLoanMoney();//借款金额
				double availableLimit = companyApplyInfo.getAvailableLimit();//现有可用额度
				double activateCreditLimit = companyApplyInfo.getActivateCreditLimit();//现有启用额度
				double usedLimit = companyApplyInfo.getUsedLimit();//已用授信额度
				logger.info("增加修改可用额度updateProjectReject，orgId:"+orgId+",项目ID："+pid+",借款金额："+applyMoney+",启用授信额度："+activateCreditLimit+",可用授信额度："+availableLimit+",已用授信额度："+usedLimit);
				availableLimit = activateCreditLimit - usedLimit;
				/*//如果恢复的可用额度大于总的信用额度，用总额度代替
				if(availableLimit > activateCreditLimit){
					availableLimit = activateCreditLimit;
				}*/
				companyApplyInfo.setAvailableLimit(availableLimit);
				result = orgCooperatMapper.update(companyApplyInfo);
				//修改未成功，回滚
				if(result == 0){
					logger.error("修改可用额度出错，orgId:"+orgId+",项目ID："+pid+",借款金额："+applyMoney+",启用授信额度："+activateCreditLimit+",可用授信额度："+availableLimit+",已用授信额度："+usedLimit);
					// 抛出异常处理
					throw new RuntimeException("增加可用额度出错,请重新操作!");
				}
				//驳回为补充资料驳回，只需要添加驳回记录
			}else if(isReject == AomConstant.IS_REJECT_3){
				OrderRejectInfo rejectInfo = new OrderRejectInfo();
				rejectInfo.setOrderId(pid);
				rejectInfo.setExamineOpinion(bizProject.getExamineOpinion());
				rejectInfo.setExamineUser(bizProject.getExamineUser());
				result = orderRejectInfoMapper.insert(rejectInfo);
			}

			if(result >0){
				bizDynamicService.addBizDynamic(bizDynamic);
			}
			
		} catch (Exception e) {
			logger.error("修改驳回状态:" + ExceptionUtil.getExceptionMessage(e));
			throw new RuntimeException("修改驳回状态出错,请重新操作!"+ExceptionUtil.getExceptionMessage(e));
		}
		
		return result;
	}

	/**
	 * 分配订单，分配的同时将此订单关联的客户与被分配用户建立关系
	 */
	@Override
	public int updateProjectAssigned(BizProject bizProject) throws TException {
		
		List<String> projectIds = bizProject.getProjectIds();
		if(projectIds != null && projectIds.size()>0){
			for(String projectId : projectIds){
				int pid = Integer.parseInt(projectId);
				BizProject projectInfo = getById(pid);
				int acctId = projectInfo.getAcctId();
				//判断当前登录用户是否与客户有关联，无关联添加一个关联
				CusRelation query = new CusRelation();
				query.setAcctId(acctId);
				query.setPmUserId(bizProject.getPmUserId());
				query.setOrgType(1);
				query.setOrgId(2);//默认是小科的用户
				List<CusRelation> result = cusRelationMapper.getAll(query);
				if(result == null || result.size()==0){
					cusRelationMapper.insert(query);
				}
			}
		}
		return bizProjectMapper.updateProjectAssigned(bizProject);
	}

	/**
	 *  项目经理查看自已维护机构订单功能
	 */
	@Override
	public List<BizProjectDTO> getProjectListByPage(BizProjectDTO bizProjectDTO)
			throws TException {
		List<BizProjectDTO> list = new ArrayList<BizProjectDTO>();
		try {
			// 修改申请开始时间格式
			if (null != bizProjectDTO.getRequestDttm() && !"".equals(bizProjectDTO.getRequestDttm())) {
				String beginString = bizProjectDTO.getRequestDttm();
				Date d = DateUtil.format(beginString, "yyyy-MM-dd");
				beginString = new StringBuffer().append(DateUtil.format(d, "yyyy-MM-dd").trim()).append(" 00:00:01").toString();
				bizProjectDTO.setRequestDttm(beginString);
			}
			// 修改申请结束时间格式
			if (null != bizProjectDTO.getEndRequestDttm() && !"".equals(bizProjectDTO.getEndRequestDttm())) {
				String endString = bizProjectDTO.getEndRequestDttm();
				Date d = DateUtil.format(endString, "yyyy-MM-dd");
				endString = new StringBuffer().append(DateUtil.format(d, "yyyy-MM-dd").trim()).append(" 23:59:59").toString();
				bizProjectDTO.setEndRequestDttm(endString);
			}
			list = bizProjectMapper.getProjectListByPage(bizProjectDTO);
		} catch (Exception e) {
			logger.error("项目经理查看自已维护机构订单功能:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return list;
	}

	/**
	 * 根据条件查询所有机构业务列表
	 */
	@Override
	public int getProjectListCount(BizProjectDTO bizProjectDTO)
			throws TException {
		int count = 0;
		try {
			// 修改申请开始时间格式
			if (null != bizProjectDTO.getRequestDttm() && !"".equals(bizProjectDTO.getRequestDttm())) {
				String beginString = bizProjectDTO.getRequestDttm();
				Date d = DateUtil.format(beginString, "yyyy-MM-dd");
				beginString = new StringBuffer().append(DateUtil.format(d, "yyyy-MM-dd").trim()).append(" 00:00:01").toString();
				bizProjectDTO.setRequestDttm(beginString);
			}
			// 修改申请结束时间格式
			if (null != bizProjectDTO.getEndRequestDttm() && !"".equals(bizProjectDTO.getEndRequestDttm())) {
				String endString = bizProjectDTO.getEndRequestDttm();
				Date d = DateUtil.format(endString, "yyyy-MM-dd");
				endString = new StringBuffer().append(DateUtil.format(d, "yyyy-MM-dd").trim()).append(" 23:59:59").toString();
				bizProjectDTO.setEndRequestDttm(endString);
			}
			count = bizProjectMapper.getProjectListCount(bizProjectDTO);
		} catch (Exception e) {
			logger.error("根据条件查询所有机构业务列表:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return count;
	}


}
