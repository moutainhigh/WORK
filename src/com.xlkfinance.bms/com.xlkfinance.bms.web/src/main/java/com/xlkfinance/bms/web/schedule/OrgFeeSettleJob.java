/**
 * @Title: OrgFeeSettleJob.java
 * @Package com.xlkfinance.bms.web.schedule
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: baogang
 * @date: 2016年8月19日 上午10:11:01
 * @version V1.0
 */
package com.xlkfinance.bms.web.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.util.DateUtil;
import com.qfang.xk.aom.rpc.fee.OrgFeeSettleService;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.web.controller.BaseController;

/**
 * 费用结算定时任务
  * @ClassName: OrgFeeSettleJob
  * @author: baogang
  * @date: 2016年8月19日 上午10:11:08
 */
@Component
public class OrgFeeSettleJob extends BaseController{
	private Logger logger = LoggerFactory.getLogger(OrgFeeSettleJob.class);
	
//	@Scheduled(cron="0 0 1 1 * ?")   //每月1号  凌晨1点开始执行
    public void execute() { 
		logger.info("费用结算定时调度任务开始"+DateUtil.format(DateUtil.getToday()));
		
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getAomFactory(BusinessModule.MODUEL_ORG_FEE, "OrgFeeSettleService");
			OrgFeeSettleService.Client client = (OrgFeeSettleService.Client) clientFactory.getClient();
			//息费结算
			int result = client.initFeeSettle();
			//测试用，计算当月
			//int result =client.createLastMonthFeeSettle(0);
			logger.info("费用结算定时调度任务成功: " + result + "  ,时间:  " + DateUtil.format(DateUtil.getToday()));
		}catch(Exception e){
			logger.info("费用结算定时调度任务异常"+ExceptionUtil.getExceptionMessage(e));
		}finally {
			destroyFactory(clientFactory);
		}
        
        logger.info("费用结算定时调度任务结束"+DateUtil.format(DateUtil.getToday()));
    } 
}
