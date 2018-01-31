package com.xlkfinance.bms.web.controller.beforeloan;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.constant.SysLogTypeConstant;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.beforeloan.BizChangeRecordDTO;
import com.xlkfinance.bms.rpc.beforeloan.BizChangeRecordService;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleDTO;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleService.Client;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;
import com.xlkfinance.bms.web.util.IpAddressUtil;

/**
 * 修改记录controller层操作类
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/changeRecordController")
public class ChangeRecordController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(ChangeRecordController.class);
	
	/**
	 * 根据项目ID查询变更前的各项信息
	 * @param projectId
	 * @param response
	 */
	@RequestMapping(value="getProjectInfo", method = RequestMethod.POST)
	public void getProjectInfo(Integer projectId, HttpServletResponse response){
		// 创建对象
		BizChangeRecordDTO dto =new BizChangeRecordDTO();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			Project project = client.getLoanProjectByProjectId(projectId);
			dto.setRelationId(project.getPid());
			dto.setOldLoanMoney(project.getProjectGuarantee().getLoanMoney());
			dto.setOldFeeRate(project.getProjectGuarantee().getFeeRate());
			dto.setOldLoanDays(project.getProjectForeclosure().getLoanDays());
			dto.setOldGuaranteeFee(project.getProjectGuarantee().getGuaranteeFee());
		} catch (Exception e) {
			logger.error("根据项目ID查询变更前的各项信息:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(dto, response);
	}
	
	/**
	 * 保存变更记录
	 * @param dto
	 * @param response
	 */
	@RequestMapping(value="saveChangeRecord", method = RequestMethod.POST)
	public void saveChangeRecord(BizChangeRecordDTO dto,HttpServletResponse response,HttpServletRequest request){
		logger.info("保存变更记录，参数"+dto);
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizChangeRecordService");
			BizChangeRecordService.Client client = (BizChangeRecordService.Client) clientFactory.getClient();
			//查询放款状态（1：未申请 2：申请中3：放款中 4：放款成功 5：放款失败），只有未申请状态才可以推送
			Client service = (Client) getService(BusinessModule.MODUEL_INLOAN,"FinanceHandleService");
	        FinanceHandleDTO financeHandleDTO = service.getFinanceHandleByProjectId(dto.getRelationId());
	        int status = financeHandleDTO.getStatus();
			if(status == Constants.REC_STATUS_ALREADY_LOAN || status == Constants.REC_STATUS_LOAN_NO_FINISH || status == Constants.REC_STATUS_LOAN_APPLY){//status等于0或者1 表示放款数据不存在或者未进行放款申请
				fillReturnJson(response, false, "此业务正在放款申请中，不允许修改!");
	            return;
			}
			dto.setModifyUser(getShiroUser().getPid());//操作人
			dto.setOperationIp(IpAddressUtil.getIpAddress(request));//操作IP
			int rows = client.insertChangeRecod(dto);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_BEFORELOAN, SysLogTypeConstant.LOG_TYPE_ADD, "保存变更记录",dto.getRelationId());
				// 失败的话,做提示处理
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "保存成功！");
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "保存失败,请重新操作!");
			}
		} catch (Exception e) {
			logger.error("保存变更记录失败:" + ExceptionUtil.getExceptionMessage(e));
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "保存失败,请重新操作!");
		}finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(j, response);
	}
}
