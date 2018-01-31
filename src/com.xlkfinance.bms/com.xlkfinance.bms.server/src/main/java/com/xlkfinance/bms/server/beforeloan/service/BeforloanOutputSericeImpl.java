/**
 * 
 */
package com.xlkfinance.bms.server.beforeloan.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.rpc.beforeloan.BeforloadOutputSerice.Iface;
import com.xlkfinance.bms.rpc.beforeloan.Loan;
import com.xlkfinance.bms.rpc.beforeloan.LoanOutputInfo;
import com.xlkfinance.bms.rpc.beforeloan.LoanOutputInfoImpl;
import com.xlkfinance.bms.rpc.beforeloan.LoanOutputInfoImplDTO;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.RepaymentLoanInfo;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.repayment.RepaymentPlanBaseDTO;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanOutputInfoMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanRepaymentPlanMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectMapper;
import com.xlkfinance.bms.server.repayment.calc.CalPlanLine;
import com.xlkfinance.bms.server.repayment.mapper.RepaymentMapper;
import com.xlkfinance.bms.server.system.mapper.SysLookupValMapper;


/**   
 *    
 * 
 * 类描述：   贷前财务放款
 * 创建人：gaoWen   
 * 创建时间：2015年2月7日 上午11:38:27   

 * 修改时间：2015年2月7日 上午11:38:27   
 * 修改备注：   
 * @version    
 *    
 */
@SuppressWarnings("unchecked")
@Service("BeforloadOutputSericeImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.beforeloan.BeforloadOutputSerice")
public class BeforloanOutputSericeImpl implements Iface  {
	private Logger logger = LoggerFactory.getLogger(BeforloanOutputSericeImpl.class);
	@SuppressWarnings("rawtypes")
	@Resource(name = "loanOutputInfoMapper")
	private LoanOutputInfoMapper loanOutputInfoMapper;
	@SuppressWarnings("rawtypes")
	@Resource(name = "sysLookupValMapper")
	private SysLookupValMapper sysLookupValMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "loanMapper")
	private LoanMapper loanMapper;
	//	loanMapper.updateByPrimaryKey(loan);
	@SuppressWarnings("rawtypes")
	@Resource(name = "repaymentMapper")
	private RepaymentMapper repaymentMapper;
	
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "loanRepaymentPlanMapper")
	private LoanRepaymentPlanMapper loanRepaymentPlanMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "projectMapper")
	private ProjectMapper projectMapper;
	
	
	@Resource(name = "repaymentCalPlanLineFactory")
	private com.xlkfinance.bms.server.repayment.calc.CalPlanLineFactory repaymentCalPlanLineFactory;

	
    /**
     * 查询贷款列表
     */
	@Override
	public List<LoanOutputInfo> getLoadOutputList(int projectId)
			throws ThriftServiceException, TException {
		List<LoanOutputInfo> list = new ArrayList<LoanOutputInfo>();
		try {
			
			list = loanOutputInfoMapper.getLoadOutputList(projectId);
			
		} catch (Exception e) {
				logger.error("查询贷款:" , e);
		}
		return list;
	}

	@Override
	public LoanOutputInfoImpl getLoadOutputListImpl(Project project)
			throws ThriftServiceException, TException {
		List<LoanOutputInfoImpl> list = new ArrayList<LoanOutputInfoImpl>();
		list=loanOutputInfoMapper.getLoadOutputListImpl(project);
		if(null!=list&&list.size()>0){
			return list.get(0);
		}
		return new LoanOutputInfoImpl();
	}

	@Override
	public int insertLoadOutputinfo(LoanOutputInfoImplDTO loanOutputInfoImplDTO)
			throws ThriftServiceException, TException {
		//设置交易类型为放款1代表放款2代表收款
		loanOutputInfoImplDTO.setFtType(1);
	int st=	loanOutputInfoMapper.insertLoadOutputinfo(loanOutputInfoImplDTO);
	if (st>0) {
		loanOutputInfoImplDTO.setRefId(loanOutputInfoImplDTO.getPId());	
	}
	int sta= loanOutputInfoMapper.insertLoadFTinfo(loanOutputInfoImplDTO);
	return sta;
	}
	
	/**
	 * 查询贷款列表
	 */
	@Override
	public List<LoanOutputInfo> getLoadOutputinfoList(Project project)
			throws ThriftServiceException, TException {
		List<LoanOutputInfo> list = new ArrayList<LoanOutputInfo>();
		try {
			
			list = loanOutputInfoMapper.getLoadOutputinfoList(project);
			
		} catch (Exception e) {
				logger.error("查询贷款:" , e);
		}
		return list;
	}

	@Override
	public LoanOutputInfoImpl getLoadOutputinfo(Project project)
			throws ThriftServiceException, TException {
		List<LoanOutputInfoImpl> list = new ArrayList<LoanOutputInfoImpl>();
		list=loanOutputInfoMapper.getLoadOutputinfo(project);
		if(list==null || list.size()==0){
			return new LoanOutputInfoImpl();
		}else{
			return list.get(0);
		}
	}
 
	@Override
	public int updateLoadOutputinfo(LoanOutputInfoImplDTO loanOutputInfoImplDTO)
			throws ThriftServiceException, TException {
		
		int sta=loanOutputInfoMapper.updateLoadOutputinfo(loanOutputInfoImplDTO);
		loanOutputInfoMapper.updateLoadFTinfo(loanOutputInfoImplDTO);
		return sta;
	}
	
	@Override
	public int deleteLoadOutputinfo(int pId)
			throws ThriftServiceException, TException {
		int sta=loanOutputInfoMapper.deleteLoadOutputinfo(pId);
			loanOutputInfoMapper.deleteLoadFTinfo(pId);
		return sta;
	}
	/**
	 * 修改还款计划表
	 */
	@Override
	public Loan updateRepaymentPlan(LoanOutputInfoImplDTO loanOutputInfoImplDTO) throws ThriftServiceException, TException {
		Loan	loan=new Loan();
		loan.setPid(loanOutputInfoImplDTO.getLoanId());
		loan.setPlanOutLoanDt(loanOutputInfoImplDTO.getPlanOutLoanDt());
		loan.setPlanRepayLoanDt(loanOutputInfoImplDTO.getPlanRepayLoanDt());
		loan.setRepayCycleDate(Integer.parseInt( loanOutputInfoImplDTO.getRepayCycleDate()));
		if("".equals(loanOutputInfoImplDTO.getCreditEndDate())){
			loan.setCreditEndDate("");
		}else{
			loan.setCreditEndDate(loanOutputInfoImplDTO.getCreditEndDate());
		}
		
		int projectId = projectMapper.getProjectOldProjectId(loanOutputInfoImplDTO.getPId());
		//如果projectId 有值的话，说明是从新项目去找它的老项目ID
		if(projectId>0){
			loan.setProjectId(projectId);
		}else{
			loan.setProjectId(loanOutputInfoImplDTO.getPId());
		}
		
		loanMapper.updateByloanId(loan);//并且修改老项目的授信结束时间 
		return new Loan();
	}
     //计算整月是一期最后还款日期
	@Override
	public String queryloanEndDtInfo(int loanId,String loanOutDt) throws ThriftServiceException, TException {
		RepaymentLoanInfo loanInfo=  loanRepaymentPlanMapper.getRepaymentLoanInfo(loanId);
		List<RepaymentPlanBaseDTO>	 list= repaymentMapper.selectRepaymentPlan(loanId);
       CalPlanLine calPlanLine=   repaymentCalPlanLineFactory.factory(loanInfo.getRepayFun());
	  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Calendar c2=Calendar.getInstance();
    Calendar calendar=null;
 //   String repayDt=null;
    String  PlanRepayDt=null;
     //最后一期是否满期0位满期      1-31位最后一期的天数
        int numday=0;
    
    int cycleNum =0;
    if (null!=list&&list.size()>0) {
    	cycleNum=	list.get(list.size()-1).getPlanCycleNum();
    	//获取最大期数要让他大于1  小于1的另算
        if (cycleNum>1) {
        	c2.setTime(DateUtil.format(loanInfo.getLoanOutDt(),"yyyy-MM-dd"));
        	c2.add(Calendar.MONTH, cycleNum);
        	c2.add(Calendar.DAY_OF_MONTH, -1);
        	 String time = format.format(c2.getTime());
        	//获取最后一期的证实还款日期
        	PlanRepayDt=list.get(list.size()-1).getPlanRepayDt();
        	//判断是否两个日期相等  不相等的话就是不是一整期  相等就是一整期
        	if ( DateUtil.format(time,"yyyy-MM-dd").toString().equals( DateUtil.format(PlanRepayDt,"yyyy-MM-dd").toString())) {
        		numday=0;
    		}else {
    			//Date date= DateUtil.format(PlanRepayDt,"yyyy-MM-dd");
    			numday=	DateUtil.getDaysInterval(DateUtil.format(list.get(list.size()-1).getPlanRepayDt(),"yyyy-MM-dd"), DateUtil.format(list.get(list.size()-2).getPlanRepayDt(),"yyyy-MM-dd"));
    		}
    	}else{
        	numday=	DateUtil.getDaysInterval(DateUtil.format(list.get(list.size()-1).getPlanRepayDt(),"yyyy-MM-dd"), DateUtil.format(loanOutDt,"yyyy-MM-dd"));
    	}
	}else {
		logger.error("=======================没有还款计划信息=============================");
		return 0+","+0;
	}
		return list.get(list.size()-1).getPlanCycleNum()+","+numday;
	}

	@Override
	public String queryloanRepayFun(int repayFunId) throws ThriftServiceException, TException {
		String lookupVal=sysLookupValMapper.getSysLookupValByPid(repayFunId);
		int index=1;
		if(lookupVal!=null && !"".equals(lookupVal)){
			index=Integer.parseInt(lookupVal);
		}
		switch (index) {
			case 1:
				return "-1";
			case 2:
				return "5";
			case 3:
				return "7";
			case 4:
				return "10";
			case 5:
				return "15";
			case 6:
				return "-1";
			case 7:
				return "-1";
			case 8:
				return "-1";
			case 9:
				return "-1";
		}
		return null;
	}
	
	
}
