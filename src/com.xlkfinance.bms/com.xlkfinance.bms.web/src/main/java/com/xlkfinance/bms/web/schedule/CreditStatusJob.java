/**
 * @Title: 根据当前系统时间,定时查询授信表,如果授信日期已经到期则更新授信信息为无效
 * @Package com.xlkfinance.bms.server.schedule
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Zhangyu.Hoo
 * @date: 2015年8月10日 上午11:01:49
 * @version V1.0
 */
package com.xlkfinance.bms.web.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.web.controller.BaseController;

@Component
public class CreditStatusJob extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(CreditStatusJob.class);
	
//	@Scheduled(cron="0 15 0 ? * *")   //每天 凌晨15分开始执行
    public void execute() { 
		logger.info("更新授信状态定时调度任务开始"+DateUtil.format(DateUtil.getToday()));
		
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			// 1 根据系统当前时间,获取已经过期的授信ID
			String creditIds = client.getCreaditInvalidIds(DateUtil.format(DateUtil.getToDay()));
			
			// 2 更新状态
			if(null != creditIds && !"".equals(creditIds)){
				logger.info("需要更新状态为无效的授信ID : "+creditIds);
				
				int result = client.updateProjectInvalid(creditIds);
				
				logger.info("更新授信状态定时调度更新成功数目: "+result +"  ,时间:  "+DateUtil.format(DateUtil.getToday()));
			}
		}catch(Exception e){
			logger.info("更新授信状态定时调度任务异常"+e.getMessage());
		}finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
				}
			}
		}
        
        logger.info("更新授信状态定时调度任务结束"+DateUtil.format(DateUtil.getToday()));
    } 
}
