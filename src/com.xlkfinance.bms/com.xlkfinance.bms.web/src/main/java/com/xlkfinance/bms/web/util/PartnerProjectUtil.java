package com.xlkfinance.bms.web.util;

import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectForeclosure;
import com.xlkfinance.bms.rpc.beforeloan.ProjectGuarantee;
import com.xlkfinance.bms.rpc.customer.CusPerson;
import com.xlkfinance.bms.rpc.partner.ProjectPartner;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerDto;

/**
 * 资金机构工具类
 * @author czz
 * 2017-03-28
 */
public class PartnerProjectUtil{
	
	
	/**
	 * 转换，并设置对应对象值
	 * @param dto	资金机构整合对象
	 * @param tempProject	项目信息
	 * @param tempGuarantee	贷款信息
	 * @param tempPartner	资金机构信息
	 * @param tempCusPerson 客户信息
	 * @return
	 */
	public static ProjectPartnerDto convertBean(ProjectPartnerDto dto,Project tempProject,ProjectGuarantee tempGuarantee,
			ProjectPartner tempPartner, CusPerson tempCusPerson){
		
		ProjectForeclosure tempForeclosure = dto.getProjectForeclosure();
 
		//物业信息
		dto.setProjectProperty(tempProject.getProjectProperty());
		
		//设置转换基本信息
		dto.setProjectName(tempProject.getProjectName());	//项目名称
		dto.setProjectId(tempProject.getPid());	
		
		//项目来源
		dto.setProjectSource(tempProject.getProjectSource());
		
		
		//1交易  2非交易   3 其它
		String businessCategoryStr ="";
		int businessType =3;
		if("交易现金赎楼".equals(tempProject.getBusinessTypeText())){
			businessType = 1 ;
			businessCategoryStr ="交易";
		}else if("非交易现金赎楼".equals(tempProject.getBusinessTypeText())){
			businessType = 2 ;
			businessCategoryStr ="非交易";
		}
		dto.setBusinessTypeStr(tempProject.getBusinessTypeText());
		dto.setBusinessType(businessType);
		dto.setBusinessCategoryStr(businessCategoryStr);
		dto.setBusinessCategory(tempProject.getBusinessCategory());
		
		//客户信息
		dto.setAcctId(tempProject.getAcctId());
		dto.setUserName(tempCusPerson.getChinaName());	
		dto.setSex(tempCusPerson.getSex());
		dto.setCertType(tempCusPerson.getCertType()+"");
		dto.setCardNo(tempCusPerson.getCertNumber());
		dto.setPhone(tempCusPerson.getTelephone());		//手机号码
		dto.setLiveAddr(tempCusPerson.getLiveAddr());
		
		//客户经理
		dto.setPmCustomerName(tempProject.getRealName());
		//业务联系人
		dto.setBusinessContacts(tempProject.getBusinessContacts());
		//经办人
		dto.setManagers(tempProject.getManagers());
		//借款天数
		dto.setApplyDate(tempForeclosure.getLoanDays());
		//借款金额
		dto.setApplyMoney(tempGuarantee.getLoanMoney());
		//申请放款时间
		dto.setApplyLoanDate(tempForeclosure.getReceDate());
		
		//查询资金项目，覆盖数据
		if(tempPartner != null && tempPartner.getPid() > 0){
			
			dto.setPid(tempPartner.getPid());
			dto.setLoanId(tempPartner.getLoanId());
			dto.setPartnerOrderCode(tempPartner.getPartnerOrderCode());
			dto.setPartnerNo(tempPartner.getPartnerNo());
			dto.setRequestFiles(tempPartner.getRequestFiles());//申请材料信息
			
			//借款天数
			if(tempPartner.getApplyDate() != 0){
				dto.setApplyDate(tempPartner.getApplyDate());
			}
			//申请放款日期
			if(!StringUtil.isBlank(tempPartner.getApplyLoanDate())){
				dto.setApplyLoanDate(tempPartner.getApplyLoanDate());
			}
			
			dto.setProvinceCode(tempPartner.getProvinceCode());
			dto.setCityCode(tempPartner.getCityCode());
			dto.setRemark(tempPartner.getRemark());
			
			//审核
			dto.setApproveMoney(tempPartner.getApproveMoney());
			dto.setReApplyReason(tempPartner.getReApplyReason());
			dto.setConfirmLoanDays(tempPartner.getConfirmLoanDays());
			dto.setConfirmLoanMoney(tempPartner.getConfirmLoanMoney());
			dto.setConfirmLoanReason(tempPartner.getConfirmLoanReason());
			dto.setLoanEffeDate(tempPartner.getLoanEffeDate());
			
			//放款状态
			dto.setLoanStatus(tempPartner.getLoanStatus());
			dto.setLoanJusticeFiles(tempPartner.getLoanJusticeFiles());
			dto.setLoanBlankFiles(tempPartner.getLoanBlankFiles());
			dto.setLoanOtherFiles(tempPartner.getLoanOtherFiles());
			dto.setLoanRemark(tempPartner.getLoanRemark());
			
			dto.setPartnerLoanDate(tempPartner.getPartnerLoanDate());
			dto.setPartnerLoanFile(tempPartner.getPartnerLoanFile());
			//平台申请到资方业务编号    
			dto.setPartnerPlatformOrderCode(tempPartner.getPartnerPlatformOrderCode());	
			
			//还款状态
			dto.setRepaymentRepurchaseType(tempPartner.getRepaymentRepurchaseType());
			dto.setRepaymentRepurchaseStatus(tempPartner.getRepaymentRepurchaseStatus()+"");
			dto.setRepaymentVoucherPath(tempPartner.getRepaymentVoucherPath());
			dto.setRepaymentRepurchaseRemark(tempPartner.getRepaymentRepurchaseRemark());
			dto.setXiFeeVoucherPath(tempPartner.getXiFeeVoucherPath());
			dto.setPartnerRealRefundDate(tempPartner.getPartnerRealRefundDate());
			
			dto.setRefundLoanAmount(tempPartner.getRefundLoanAmount());
			dto.setRefundXifee(tempPartner.getRefundXifee());
			dto.setPartnerInterests(tempPartner.getPartnerInterests());
			dto.setRefundDate(tempPartner.getRefundDate());
			dto.setPartnerRefundFile(tempPartner.getPartnerRefundFile());
			
			dto.setRefundTotalAmount(tempPartner.getRefundTotalAmount());
			dto.setRefundPenalty(tempPartner.getRefundPenalty());
			dto.setRefundFine(tempPartner.getRefundFine());
			dto.setRefundCompdinte(tempPartner.getRefundCompdinte());
			
			
			// 借款人银行信息
			dto.setPaymentBank(tempPartner.getPaymentBank());
			dto.setPaymentAcctName(tempPartner.getPaymentAcctName());
			dto.setPaymentAcctNo(tempPartner.getPaymentAcctNo());
			dto.setPaymentBankPhone(tempPartner.getPaymentBankPhone());
			dto.setLoanPeriodLimit(tempPartner.getLoanPeriodLimit());
			dto.setIsCreditLoan(tempPartner.getIsCreditLoan());
			dto.setPaymentBankLineNo(tempPartner.getPaymentBankLineNo());
			
			//打款银行信息
			dto.setPayBankName(tempPartner.getPayBankName());
			dto.setPayBankBranch(tempPartner.getPayBankBranch());
			dto.setPayAcctName(tempPartner.getPayAcctName());
			dto.setPayAcctNo(tempPartner.getPayAcctNo());
			dto.setPayProvinceCode(tempPartner.getPayProvinceCode());
			dto.setPayCityCode(tempPartner.getPayCityCode());
			
			//物业城市 
			dto.setHouseProvinceCode(tempPartner.getHouseProvinceCode());
			dto.setHouseCityCode(tempPartner.getHouseCityCode());
			
			//执行费率
			dto.setPartnerPushAccount(tempPartner.getPartnerPushAccount());
			dto.setPartnerGrossRate(tempPartner.getPartnerGrossRate());
 
		}
		
		
		
		
		return dto;
	}
	
		
}