package com.xlkfinance.bms.server.beforeloan.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.achievo.framework.annotation.ThriftService;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyService;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.BizChangeRecord;
import com.xlkfinance.bms.rpc.beforeloan.BizChangeRecordDTO;
import com.xlkfinance.bms.rpc.beforeloan.BizChangeRecordService.Iface;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectForeclosure;
import com.xlkfinance.bms.rpc.beforeloan.ProjectGuarantee;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.server.beforeloan.mapper.BizChangeRecordMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectForeclosureMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectGuaranteeMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectMapper;

/**
 *  @author： baogang <br>
 *  @time：2017-01-05 11:20:22 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 修改记录信息<br>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("bizChangeRecordServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.beforeloan.BizChangeRecordService")
public class BizChangeRecordServiceImpl implements Iface {
    @Resource(name = "bizChangeRecordMapper")
    private BizChangeRecordMapper bizChangeRecordMapper;
    
	@Resource(name = "projectMapper")
	private ProjectMapper projectMapper;
    
	@Resource(name="projectGuaranteeMapper")
	private ProjectGuaranteeMapper projectGuaranteeMapper;
	
	@Resource(name="projectForeclosureMapper")
	private ProjectForeclosureMapper projectForeclosureMapper;
	
	@Resource(name = "orgCooperatCompanyApplyServiceImpl")
	private OrgCooperatCompanyApplyService.Iface orgCooperatCompanyApplyServiceImpl;
	/**
     *根据条件查询所有
     *@author:baogang
     *@time:2017-01-05 11:20:22
     */
    @Override
    public List<BizChangeRecord> getAll(BizChangeRecord bizChangeRecord) throws ThriftServiceException, TException {
       return bizChangeRecordMapper.getAll(bizChangeRecord);
    }

    /**
     *根据id查询
     *@author:baogang
     *@time:2017-01-05 11:20:22
     */
    @Override
    public BizChangeRecord getById(int pid) throws ThriftServiceException, TException {
       BizChangeRecord bizChangeRecord = bizChangeRecordMapper.getById(pid);
       if (bizChangeRecord==null) {
          return new BizChangeRecord();
       }
       return bizChangeRecord;
    }

    /**
     *插入一条数据
     *@author:baogang
     *@time:2017-01-05 11:20:22
     */
    @Override
    public int insert(BizChangeRecord bizChangeRecord) throws ThriftServiceException, TException {
       return bizChangeRecordMapper.insert(bizChangeRecord);
    }

    /**
     *根据id更新数据
     *@author:baogang
     *@time:2017-01-05 11:20:22
     */
    @Override
    public int update(BizChangeRecord bizChangeRecord) throws ThriftServiceException, TException {
       return bizChangeRecordMapper.update(bizChangeRecord);
    }

    /**
     * 变更记录入库以及修改项目信息
     */
    @Transactional
	@Override
	public int insertChangeRecod(BizChangeRecordDTO recordDto)
			throws TException {
		int rows = 0;
		double newLoanMoney = recordDto.getNewLoanMoney();//变更后借款金额
		double newGuaranteeFee = recordDto.getNewGuaranteeFee();//变更后咨询费
		double newFeeRate = recordDto.getNewFeeRate();//变更后费率
		int newLoanDays = recordDto.getNewLoanDays();//变更后借款天数

		//借款金额变更数据
		BizChangeRecord loanMoneyRecord = copyObject(recordDto);
		loanMoneyRecord.setOldValue(String.valueOf(recordDto.getOldLoanMoney()));
		loanMoneyRecord.setNewValue(String.valueOf(newLoanMoney));
		loanMoneyRecord.setValueType(Constants.CHANGE_VALUE_TYPE_1);
		rows +=bizChangeRecordMapper.insert(loanMoneyRecord);
		//借款天数变更数据
		BizChangeRecord loanDaysRecord = copyObject(recordDto);
		loanDaysRecord.setOldValue(String.valueOf(recordDto.getOldLoanDays()));
		
		loanDaysRecord.setNewValue(String.valueOf(newLoanDays));
		loanDaysRecord.setValueType(Constants.CHANGE_VALUE_TYPE_2);
		rows +=bizChangeRecordMapper.insert(loanDaysRecord);
		//费率变更数据
		BizChangeRecord feeRateRecord = copyObject(recordDto);
		feeRateRecord.setOldValue(String.valueOf(recordDto.getOldFeeRate()));
		
		feeRateRecord.setNewValue(String.valueOf(newFeeRate));
		feeRateRecord.setValueType(Constants.CHANGE_VALUE_TYPE_3);
		rows +=bizChangeRecordMapper.insert(feeRateRecord);
		//咨询费变更数据
		BizChangeRecord guaranteeFeeRecord = copyObject(recordDto);
		guaranteeFeeRecord.setOldValue(String.valueOf(recordDto.getOldGuaranteeFee()));
		
		guaranteeFeeRecord.setNewValue(String.valueOf(newGuaranteeFee));
		guaranteeFeeRecord.setValueType(Constants.CHANGE_VALUE_TYPE_4);
		rows +=bizChangeRecordMapper.insert(guaranteeFeeRecord);
		
		int projectId = recordDto.getRelationId();
		Project projectInfo = projectMapper.getProjectInfoById(projectId);
		ProjectForeclosure foreclosure = projectForeclosureMapper.getForeclosureByProjectId(projectId);
		ProjectGuarantee guarantee = projectGuaranteeMapper.getGuaranteeByProjectId(projectId);
		//根据新的借款天数计算新的预计回款时间
		String receDate = foreclosure.getReceDate();
		if(!StringUtil.isBlank(receDate)){
		Date payDate = DateUtils.addDay(DateUtils.stringToDate(receDate, "yyyy-MM-dd"), newLoanDays - 1);
        // 根据计划放款日期、贷款天数计算出计划还款日期,修改项目原先的还款日期与放款日期
		foreclosure.setPaymentDate(DateUtils.dateFormatByPattern(payDate, "yyyy-MM-dd"));
		}
		projectInfo.setPlanLoanMoney(newLoanMoney);
		projectInfo.setLoanRate(newFeeRate);
		foreclosure.setLoanDays(newLoanDays);
		guarantee.setLoanMoney(newLoanMoney);
		guarantee.setGuaranteeFee(newGuaranteeFee);
		
		rows +=projectMapper.updateByPrimaryKey(projectInfo);
		rows +=projectForeclosureMapper.updateByPrimaryKey(foreclosure);
		rows +=projectGuaranteeMapper.updateByPrimaryKey(guarantee);
		
		//判断订单是否来自AOM端,订单来自AOM端需要修改机构的可用额度
		int projectType = projectInfo.getProjectType();
		if(projectType == 6){
			//如果申请金额变更，则修改该订单对应的机构可用额度
			if(newLoanMoney != recordDto.getOldLoanMoney()){
				orgCooperatCompanyApplyServiceImpl.updateOrgCreditLimit(projectId,newLoanMoney);
			}
		}
		
		return rows ;
	}
	
    /**
     * 将前台传输的数据转换成能入库的实体变量
     * @param recordDto
     * @return
     */
	private BizChangeRecord copyObject(BizChangeRecordDTO recordDto){
		BizChangeRecord bizChangeRecord = new BizChangeRecord();
		bizChangeRecord.setRelationId(recordDto.getRelationId());
		bizChangeRecord.setModifyReason(recordDto.getModifyReason());
		bizChangeRecord.setModifyUser(recordDto.getModifyUser());
		bizChangeRecord.setChangeType(Constants.CHANGE_OPERATE_TYPE_1);
		bizChangeRecord.setOperationIp(recordDto.getOperationIp());
		return bizChangeRecord;
	}
}
