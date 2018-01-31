/**
 * @Title: Workflow.java
 * @Package com.xlkfinance.bms.web.controller.task.factory
 * @Description: 流程特殊处理工厂包
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: JingYu.Dai
 * @date: 2015年4月3日 下午4:40:02
 * @version V1.0
 */
package com.xlkfinance.bms.server.workflow.factory;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.xlkfinance.bms.server.workflow.factory.impl.FddMakeLoansProcessWrokflow;

/**
 * @ClassName: IWorkflow
 * @Description: 工作流特殊处理接口
 * @author: JingYu.Dai
 * @date: 2015年4月3日 下午4:51:07
 */
@Component("workflowSpecialDisposeFactory")
public class WorkflowSpecialDisposeFactory {
	
	@Resource(name = "refundTailMoneyProcessWrokflow")
	private WorkflowSpecialDispose refundTailMoneyProcessWrokflow;
	
	@Resource(name = "refundFeeProcessWrokflow")
	private WorkflowSpecialDispose refundFeeProcessWrokflow;
	
	@Resource(name = "refundInterestFeeProcessWrokflow")
	private WorkflowSpecialDispose refundInterestFeeProcessWrokflow;
	
	@Resource(name = "creditLoanRequestProcessWrokflow")
	private WorkflowSpecialDispose creditLoanRequestProcessWrokflow;
	
	@Resource(name = "foreLoanRequestProcessWrokflow")
	private WorkflowSpecialDispose foreLoanRequestProcessWrokflow;
	
	@Resource(name="elementLendRequestProcessWorkflow")
	private WorkflowSpecialDispose elementLendRequestProcessWorkflow;
	
	@Resource(name = "creditExtensionRequestProcessWrokflow")
	private WorkflowSpecialDispose creditExtensionRequestProcessWrokflow;
	
	@Resource(name = "foreExtensionRequestProcessWrokflow")
	private WorkflowSpecialDispose foreExtensionRequestProcessWrokflow;
	
	@Resource(name = "refundCommissionProcessWrokflow")
	private WorkflowSpecialDispose refundCommissionProcessWrokflow;
	
	@Resource(name = "intermediateTransferProcessWrokflow")
	private WorkflowSpecialDispose intermediateTransferProcessWrokflow;
	
	@Resource(name = "cooperationRequestProcessWorkflow")
	private WorkflowSpecialDispose cooperationRequestProcessWorkflow;
	
	@Resource(name = "businessApplyRequestProcessWrokflow")
	private WorkflowSpecialDispose businessApplyRequestProcessWrokflow;
	
	@Resource(name = "makeLoansProcessWrokflow")
	private WorkflowSpecialDispose MakeLoansProcessWrokflow;
	
	@Resource(name = "cooperationUpdateProcessWrokflow")
	private WorkflowSpecialDispose cooperationUpdateProcessWrokflow;
	
	/**
	 * 房抵贷申请贷前流程审批
	 */
	@Resource(name = "mortgageLoanAppRequestProcessWorkflow")
	private WorkflowSpecialDispose mortgageLoanAppRequestProcessWorkflow;
	/**
	 * 消费贷申请贷前流程审批
	 */
	@Resource(name = "consumeLoanForeAppRequestProcess")
	private WorkflowSpecialDispose consumeLoanForeAppRequestProcess;
	
	@Resource(name = "fddMakeLoansProcessWrokflow")
	private FddMakeLoansProcessWrokflow fddMakeLoansProcessWrokflow;
	
	public WorkflowSpecialDispose factory(String workflowProcessDefkey,
			String workflowTaskDefKey, int projectId, int refId,
			String isVetoProject, String isReject,String message,int handleAuthorId) {

		// 实例化参数
		WorkflowSpecialBean bean = new WorkflowSpecialBean(workflowTaskDefKey,
				projectId, refId, isVetoProject, workflowProcessDefkey,
				isReject,message,handleAuthorId);

	//退尾款处理
	if ("refundTailMoneyProcess".equals(workflowProcessDefkey)||"WTrefundTailMoneyProcess".equals(workflowProcessDefkey)) {
		   refundTailMoneyProcessWrokflow.setBean(bean);
         return refundTailMoneyProcessWrokflow;
         //退手续费处理
      }else if ("refundFeeProcess".equals(workflowProcessDefkey)) {
         refundFeeProcessWrokflow.setBean(bean);
         return refundFeeProcessWrokflow;
         //退利息处理
      }else if ("refundInterestFeeProcess".equals(workflowProcessDefkey)) {
         refundInterestFeeProcessWrokflow.setBean(bean);
         return refundInterestFeeProcessWrokflow;
       //小科贷前流程
      }else if("creditLoanRequestProcess".equals(workflowProcessDefkey)){
    	  creditLoanRequestProcessWrokflow.setBean(bean);
    	  return creditLoanRequestProcessWrokflow;
    	 //万通审批
      }else if("foreLoanRequestProcess".equals(workflowProcessDefkey)){
    	  foreLoanRequestProcessWrokflow.setBean(bean);
    	  return foreLoanRequestProcessWrokflow;
    	  //要件借出审批
      }else if("elementLendRequestProcess".equals(workflowProcessDefkey)){
    	  elementLendRequestProcessWorkflow.setBean(bean);
    	  return elementLendRequestProcessWorkflow;
    	  //小科展期申请
      }else if("creditExtensionRequestProcess".equals(workflowProcessDefkey)){
    	  creditExtensionRequestProcessWrokflow.setBean(bean);
    	  return creditExtensionRequestProcessWrokflow;
    	 //万通展期申请审批
      }else if("foreExtensionRequestProcess".equals(workflowProcessDefkey)){
    	  foreExtensionRequestProcessWrokflow.setBean(bean);
    	  return foreExtensionRequestProcessWrokflow;
    	//退佣金
      }else if ("refundCommissionProcess".equals(workflowProcessDefkey)) {
          refundCommissionProcessWrokflow.setBean(bean);
          return refundCommissionProcessWrokflow;
       //中途划转
      }else if ("intermediateTransferProcess".equals(workflowProcessDefkey)) {
         intermediateTransferProcessWrokflow.setBean(bean);
          return intermediateTransferProcessWrokflow;
          //合作申请
       }else if ("cooperationRequestProcess".equals(workflowProcessDefkey)) {
    	   cooperationRequestProcessWorkflow.setBean(bean);
           return cooperationRequestProcessWorkflow;
           //业务申请
        }else if ("businessApplyRequestProcess".equals(workflowProcessDefkey)) {
        	businessApplyRequestProcessWrokflow.setBean(bean);
           return businessApplyRequestProcessWrokflow;
        //放款申请工作流
      } else if ("makeLoansProcess".equals(workflowProcessDefkey)) {
         MakeLoansProcessWrokflow.setBean(bean);
         return MakeLoansProcessWrokflow;
         //合作机构合作信息修改工作流
      }else if ("cooperationUpdateProcess".equals(workflowProcessDefkey)) {
          cooperationUpdateProcessWrokflow.setBean(bean);
          return cooperationUpdateProcessWrokflow;
       // 房抵贷申请贷前审批工作流
       } else if ("mortgageLoanForeAppRequestProcess".equals(workflowProcessDefkey)) {
    		mortgageLoanAppRequestProcessWorkflow.setBean(bean);
   			return mortgageLoanAppRequestProcessWorkflow;
          //房抵贷放款申请 
       }else if ("fddMakeLoansProcess".equals(workflowProcessDefkey)) {
          fddMakeLoansProcessWrokflow.setBean(bean);
          return fddMakeLoansProcessWrokflow;
       }else if ("consumeLoanForeAppRequestProcess".equals(workflowProcessDefkey)) {
    	   // 消费贷申请贷前审批工作流
    	   consumeLoanForeAppRequestProcess.setBean(bean);
  			return consumeLoanForeAppRequestProcess;
         //房抵贷放款申请 
      }
		return null;
	}
	
	public WorkflowSpecialDispose factory(String workflowProcessDefkey, int projectId, int refId) {
		// 实例化参数
		WorkflowSpecialBean bean = new WorkflowSpecialBean(projectId, refId, workflowProcessDefkey);

		//小科贷前流程
	if("creditLoanRequestProcess".equals(workflowProcessDefkey)){
	    	  creditLoanRequestProcessWrokflow.setBean(bean);
	    	  return creditLoanRequestProcessWrokflow;
	   }else if("foreLoanRequestProcess".equals(workflowProcessDefkey)){
	    	  foreLoanRequestProcessWrokflow.setBean(bean);
	    	  return foreLoanRequestProcessWrokflow;
	    	  //要件借出审批
	   }else if("elementLendRequestProcess".equals(workflowProcessDefkey)){
	    	  elementLendRequestProcessWorkflow.setBean(bean);
	    	  return elementLendRequestProcessWorkflow;
	    //退尾款处理
	    }else if ("refundTailMoneyProcess".equals(workflowProcessDefkey)) {
            refundTailMoneyProcessWrokflow.setBean(bean);
            return refundTailMoneyProcessWrokflow;
            //退手续费处理
         }else if ("refundFeeProcess".equals(workflowProcessDefkey)) {
            refundFeeProcessWrokflow.setBean(bean);
            return refundFeeProcessWrokflow;
            //退利息处理
         }else if ("refundInterestFeeProcess".equals(workflowProcessDefkey)) {
            refundInterestFeeProcessWrokflow.setBean(bean);
            return refundInterestFeeProcessWrokflow;
            //小科展期申请
         }else if("creditExtensionRequestProcess".equals(workflowProcessDefkey)){
       	  creditExtensionRequestProcessWrokflow.setBean(bean);
       	  return creditExtensionRequestProcessWrokflow;
       	 //万通展期申请审批
         }else if("foreExtensionRequestProcess".equals(workflowProcessDefkey)){
       	  foreExtensionRequestProcessWrokflow.setBean(bean);
       	  return foreExtensionRequestProcessWrokflow;
       	  //退佣金
         }else if ("refundCommissionProcess".equals(workflowProcessDefkey)) {
             refundCommissionProcessWrokflow.setBean(bean);
             return refundCommissionProcessWrokflow;
            //中途划转
	     }else if ("intermediateTransferProcess".equals(workflowProcessDefkey)) {
	    	   intermediateTransferProcessWrokflow.setBean(bean);
	         return intermediateTransferProcessWrokflow;
	         //合作申请
	     }else if ("cooperationRequestProcess".equals(workflowProcessDefkey)) {
	    	  cooperationRequestProcessWorkflow.setBean(bean);
	          return cooperationRequestProcessWorkflow;
	          //业务申请
	     }else if ("businessApplyRequestProcess".equals(workflowProcessDefkey)) {
	      		businessApplyRequestProcessWrokflow.setBean(bean);
	         return businessApplyRequestProcessWrokflow;
         // 放款申请工作流
      } else if ("makeLoansProcess".equals(workflowProcessDefkey)) {
         MakeLoansProcessWrokflow.setBean(bean);
         return MakeLoansProcessWrokflow;
      // 房抵贷申请贷前审批工作流
      } else if ("mortgageLoanForeAppRequestProcess".equals(workflowProcessDefkey)) {
  			mortgageLoanAppRequestProcessWorkflow.setBean(bean);
 			return mortgageLoanAppRequestProcessWorkflow;
       //房抵贷放款申请 
      }else if ("fddMakeLoansProcess".equals(workflowProcessDefkey)) {
         fddMakeLoansProcessWrokflow.setBean(bean);
         return fddMakeLoansProcessWrokflow;
      }
		return null;
	}
}
