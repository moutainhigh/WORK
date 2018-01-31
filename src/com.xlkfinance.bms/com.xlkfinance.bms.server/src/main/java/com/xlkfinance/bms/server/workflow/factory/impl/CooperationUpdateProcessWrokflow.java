package com.xlkfinance.bms.server.workflow.factory.impl;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyInf;
import com.qfang.xk.aom.rpc.org.OrgCooperationUpdateApply;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.server.aom.org.mapper.OrgCooperatCompanyApplyInfMapper;
import com.xlkfinance.bms.server.aom.org.mapper.OrgCooperationUpdateApplyMapper;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialBean;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDispose;

/** ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017年1月9日下午3:12:42 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation：合作机构合作信息修改工作流 <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br> */
@Component("cooperationUpdateProcessWrokflow")
@Scope("prototype")
public class CooperationUpdateProcessWrokflow extends WorkflowSpecialDispose {

    private Logger logger = LoggerFactory.getLogger(CreditLoanRequestProcessWrokflow.class);

    @Resource(name = "orgCooperatCompanyApplyInfMapper")
    private OrgCooperatCompanyApplyInfMapper orgCooperatMapper;

    @Resource(name = "orgCooperationUpdateApplyMapper")
    private OrgCooperationUpdateApplyMapper orgCooperationUpdateApplyMapper;

    @Override
    public int updateProjectStatus() throws TException {
        int res = -1;
        WorkflowSpecialBean bean = super.getBean();
        int refId = bean.getRefId();
        int userId = bean.getHandleAuthorId();//操作人
        OrgCooperationUpdateApply orgCooperationUpdateApply = orgCooperationUpdateApplyMapper.getById(refId);
        int orgId = orgCooperationUpdateApply.getOrgId();
        orgCooperationUpdateApply.setUpdateId(userId);
        //驳回
        if (null != bean.getIsReject()) {
            orgCooperationUpdateApply.setApplyStatus(Constants.APPLY_STATUS_3);
            orgCooperationUpdateApplyMapper.update(orgCooperationUpdateApply);
        } else if (null != bean.getIsVetoProject() && !"".equals(bean.getIsVetoProject()) && "refuse".equals(bean.getIsVetoProject())) {
            //审核不通过
            orgCooperationUpdateApply.setApplyStatus(Constants.APPLY_STATUS_6);
            orgCooperationUpdateApplyMapper.update(orgCooperationUpdateApply);
        } else if ("task_CooperationRequest".equals(bean.getWorkflowTaskDefKey())) {
            //审核开始
            orgCooperationUpdateApply.setApplyStatus(Constants.APPLY_STATUS_4);
            orgCooperationUpdateApplyMapper.update(orgCooperationUpdateApply);
        } else if ("task_WindControlCommissionCheck".equals(bean.getWorkflowTaskDefKey())) {
            //审核结束
            orgCooperationUpdateApply.setApplyStatus(Constants.APPLY_STATUS_5);
            orgCooperationUpdateApplyMapper.update(orgCooperationUpdateApply);
            //修改合作信息
            OrgCooperatCompanyApplyInf updateApplyInf = orgCooperatMapper.getByUserId(orgId);
            double userCreditLimit=updateApplyInf.getUsedLimit();//已使用授信额度（鲍刚提供）
            double curAvailableLimit=orgCooperationUpdateApply.getActivateCreditLimit()-userCreditLimit;//当前的可用授信额度=新的启用授信额度-已使用授信额度（鲍刚提供）
            updateApplyInf.setCreditLimit(orgCooperationUpdateApply.getCreditLimit());//授信额度
            updateApplyInf.setAvailableLimit(curAvailableLimit);//可用授信额度
            if (orgCooperationUpdateApply.getActivateCreditLimit()>0) {//只有大于0才会设置启用授信额度
                updateApplyInf.setActivateCreditLimit(orgCooperationUpdateApply.getActivateCreditLimit());//启用授信额度
            }
            updateApplyInf.setRate(orgCooperationUpdateApply.getPlanRate());//预收费费率
            updateApplyInf.setActualFeeRate(orgCooperationUpdateApply.getActualFeeRate());//实际收费费率
            updateApplyInf.setFundSizeMoney(orgCooperationUpdateApply.getFundSizeMoney());//出款标准
            updateApplyInf.setAssureMoneyProportion(orgCooperationUpdateApply.getAssureMoneyProportion());//保证金比例
            updateApplyInf.setAssureMoney(orgCooperationUpdateApply.getAssureMoney());//保证金
            updateApplyInf.setSingleUpperLimit(orgCooperationUpdateApply.getSingleUpperLimit());//单笔上限
            updateApplyInf.setAssureMoneyRemark(orgCooperationUpdateApply.getAssureMoneyRemark());//保证金备注
            orgCooperatMapper.update(updateApplyInf);
            logger.info("修改合作信息:参数updateApplyInf="+updateApplyInf+",orgCooperationUpdateApply:"+orgCooperationUpdateApply);
        }

        return res;
    }

    /** 
     * 重置状态
     * @author:liangyanjun
     * @time:2017年1月9日下午3:41:36 */
    @Override
    public void resetProject() {
        WorkflowSpecialBean bean = super.getBean();
        int refId = bean.getRefId();
        OrgCooperationUpdateApply orgCooperationUpdateApply = orgCooperationUpdateApplyMapper.getById(refId);
        orgCooperationUpdateApply.setApplyStatus(Constants.APPLY_STATUS_1);
        orgCooperationUpdateApplyMapper.update(orgCooperationUpdateApply);
    }

}
