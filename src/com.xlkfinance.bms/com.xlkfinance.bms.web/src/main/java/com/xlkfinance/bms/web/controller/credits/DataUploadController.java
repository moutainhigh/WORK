/**
 * @Title: SecondCreditsController.java
 * @Package com.xlkfinance.bms.web.controller.credits
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: xuweihao
 * @date: 2015年3月17日 上午10:34:30
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.credits;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.beforeloan.DataInfo;
import com.xlkfinance.bms.rpc.beforeloan.DataUploadService;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.beforeloan.RepaymentLoanInfo;
import com.xlkfinance.bms.rpc.repayment.RepaymentPlanBaseDTO;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.rpc.system.BizFileService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.controller.beforeloan.BeforeLoanController;
import com.xlkfinance.bms.web.shiro.ShiroUser;
import com.xlkfinance.bms.web.util.FileDownload;
import com.xlkfinance.bms.web.util.FileUtil;
import com.xlkfinance.bms.web.util.ParseDocAndDocxUtil;

@Controller
@RequestMapping("/dataUploadController")
public class DataUploadController extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(BeforeLoanController.class);
	
	
	/**
	 * 
	  * @Description: 跳转额度资料上传页面
	  * @return
	  * @author: xuweihao
	  * @date: 2015年3月18日 下午2:09:24
	 */
	@RequestMapping(value = "navigationDatum")
	public String navigationDatum() {
		return "credits/credits_add_datum";
	}
	
	/**
	 * 
	 * @Description: 借据及还款计划表
	 * @param contractCatelog
	 * @param model
	 * @return 还款计划表页面
	 * @author: zhanghg
	 * @date: 2015年2月10日 上午9:36:44
	 */
	@RequestMapping(value = "receiptRepaymentList")
	public String receiptRepaymentList(String contractCatelog, Integer loanId, Integer projectId, ModelMap model) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		try {
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			if (loanId != null && loanId > 0) {
				RepaymentLoanInfo repayment = client.getRepaymentLoanInfo(loanId);
				model.put("repayment", repayment);
			}
			model.put("loanId", loanId);
			model.put("projectId", projectId);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询还款计划表贷款信息:" + e.getMessage());
			}
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		model.put("contractCatelog", contractCatelog);
		return "credits/receipt_repayment_list";
	}
	
	
	
	
	/**
	 * 
	 * @Description: 贷款试算页面跳转
	 * @return
	 * @author: zhanghg
	 * @date: 2015年2月6日 上午10:43:13
	 */
	@RequestMapping(value = "creditsCalc")
	public String loanCalc(ModelMap model, @RequestParam(value = "loanId", required = false) Integer loanId) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		try {
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			RepaymentLoanInfo repayment = client.getRepaymentLoanInfo(loanId);
			model.put("repayment", repayment);
			model.put("loanId", loanId);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询还款计划表贷款信息:" + e.getMessage());
			}
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		return "credits/credits_calc";
	}

}
