package com.xlkfinance.bms.web.schedule;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.project.ProjectInfoService;
import com.xlkfinance.bms.rpc.project.ProjectInfoService.Client;
import com.xlkfinance.bms.web.controller.BaseController;

/**
 * 房抵貸逾期信息更新
 * @author baogang
 *
 */
@Component
public class OverdueJob extends BaseController {

	private Logger logger = LoggerFactory.getLogger(OverdueJob.class);

	public void execute() {
		logger.info("房抵贷逾期信息更新定时调度任务开始" + DateUtil.format(DateUtil.getToday()));
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_PROJECT,
					"ProjectInfoService");
			ProjectInfoService.Client client = (Client) clientFactory
					.getClient();

			String planRepayDt = DateUtils.dateToString(new Date(),
					"yyyy-MM-dd");
			client.makeProjectOverdue(planRepayDt);
			logger.info("房抵贷逾期信息更新定时调度任务结束"
					+ DateUtil.format(DateUtil.getToday()));
		} catch (Exception e) {
			logger.info("机构合作状态更新定时调度任务异常"
					+ ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}

	}
}
