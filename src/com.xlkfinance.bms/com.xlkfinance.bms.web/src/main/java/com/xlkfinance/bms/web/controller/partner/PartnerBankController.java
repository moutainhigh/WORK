/**
 * @Title: ProjectPartnerController.java
 * @Package com.xlkfinance.bms.web.controller
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: Administrator
 * @date: 2016年3月18日 下午3:56:31
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.partner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.partner.BizPartnerBankInfo;
import com.xlkfinance.bms.rpc.partner.BizPartnerBankInfoService;
import com.xlkfinance.bms.web.controller.BaseController;


/**
 * 资金机构银行信息
 * @author chenzhuzhen
 * @date 2017年3月8日 下午7:52:59
 */
@Controller
@RequestMapping("/partnerBankController")
public class PartnerBankController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(PartnerBankController.class);
	
	/**
	 * 资金机构银行列表页面
	 */
	@RequestMapping(value = "/partnerBankIndex")
	public String partnerBankIndex(){
		return "partner/partner_bank_list";
	}
	
	
	/**
	 * 查询资金机构银行列表
	 */
	@RequestMapping(value = "/partnerBankList")
	@ResponseBody
	public void partnerBankList(BizPartnerBankInfo bizPartnerBankInfo, HttpServletRequest request, HttpServletResponse response){
		logger.info("查询资金机构银行列表，入参："+bizPartnerBankInfo);
		Map<String, Object> map = new HashMap<String, Object>();
		List<BizPartnerBankInfo> result = null;
		int total = 0;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_PARTNER, "BizPartnerBankInfoService");
			BizPartnerBankInfoService.Client client =(BizPartnerBankInfoService.Client) clientFactory.getClient();
			result = client.selectPartnerBankList(bizPartnerBankInfo);
			total = client.selectPartnerBankTotal(bizPartnerBankInfo);
		} catch (Exception e) {
			logger.error("获取资金机构银行列表失败：" + e.getMessage());
		}finally {
			destroyFactory(clientFactory);
		}
		map.put("rows", result);
		map.put("total", total);
		outputJson(map, response);
	}
	
	
	
}
