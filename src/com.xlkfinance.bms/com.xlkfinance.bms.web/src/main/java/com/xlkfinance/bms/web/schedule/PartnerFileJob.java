package com.xlkfinance.bms.web.schedule;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.web.api.partnerapi.impl.DaJuPartnerApiImpl;
import com.xlkfinance.bms.web.util.UuidUtil;

/**
 * 资金合作机构文件处理定时任务
 * @author chenzhuzhen
 * @date 2017年2月25日 下午4:54:34
 */

@Component
public class PartnerFileJob  {

	private Logger logger = LoggerFactory.getLogger(PartnerFileJob.class);
	
	@Resource(name = "daJuPartnerApiImpl")
	private DaJuPartnerApiImpl daJuPartnerApiImpl;		//大桔接口实现类
	

	/**
	 * 系统每天生成还扣款文件定时任务
	 */
	public void createPaymentFile() {
		logger.info("系统每天生成还扣款文件定时任务开始" + DateUtil.format(DateUtil.getToday()));
		Long startTime=System.currentTimeMillis();
		String uuid = UuidUtil.randomUUID();
		try {
			//执行生成文件
			daJuPartnerApiImpl.createPaymentFile(uuid);
 
			Long endTime=System.currentTimeMillis();
			logger.info("系统每天生成还扣款文件定时任务调度成功  ,耗费时间:  "+ ((endTime-startTime)/60000)+"分钟");
			
		} catch (Exception e) {
			logger.info("系统每天生成还扣款文件定时任务异常" + ExceptionUtil.getExceptionMessage(e));
		}  
		logger.info("系统每天生成还扣款文件定时任务结束" + DateUtil.format(DateUtil.getToday()));
	}
	
	
	
	/**
	 * 系统读取放款文件定时任务
	 */
	public void readLoanFile() {
		logger.info("系统读取放款文件定时任务任务开始" + DateUtil.format(DateUtil.getToday()));
		Long startTime=System.currentTimeMillis();
		String uuid = UuidUtil.randomUUID();
		try {
			//执行生成文件
			daJuPartnerApiImpl.readLoanFile(uuid);
 
			Long endTime=System.currentTimeMillis();
			logger.info("系统读取放款文件定时任务调度成功  ,耗费时间:  "+ ((endTime-startTime)/60000)+"分钟");
			
		} catch (Exception e) {
			logger.info("系统读取放款文件定时任务异常" + ExceptionUtil.getExceptionMessage(e));
		}  
		logger.info("系统读取放款文件定时任务结束" + DateUtil.format(DateUtil.getToday()));
	}
}
