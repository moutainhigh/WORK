package com.xlkfinance.bms.web.schedule;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.inloan.BizHandleService;
import com.xlkfinance.bms.rpc.inloan.HandleDifferWarnDTO;
import com.xlkfinance.bms.web.controller.BaseController;
/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年2月23日上午1:54:40 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 清除差异预警处理表数据<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Component
public class CleanHandleDifferWarnJob extends BaseController {

   private Logger logger = LoggerFactory.getLogger(CleanHandleDifferWarnJob.class);

//   @Scheduled(cron = "0 10 0 ? * *")
   // 每天 凌晨10分开始执行
   public void execute() {
      logger.info("清除差异预警处理表定时调度任务开始" + DateUtil.format(DateUtil.getToday()));

      BaseClientFactory clientFactory = null;
      try {
         clientFactory = getFactory(BusinessModule.MODUEL_INLOAN, "BizHandleService");
         BizHandleService.Client client = (BizHandleService.Client) clientFactory.getClient();
         //查询异预警处理表所有数据
         HandleDifferWarnDTO handleDifferWarnQuery = new HandleDifferWarnDTO();
         handleDifferWarnQuery.setPage(-1);
         List<HandleDifferWarnDTO> handleDifferWarnList = client.findAllHandleDifferWarn(handleDifferWarnQuery);
         if (handleDifferWarnList != null && handleDifferWarnList.size()>0) {
            //变量删除异预警处理表数据
            for (HandleDifferWarnDTO delDifferWarnDTO : handleDifferWarnList) {
            	
            	if(delDifferWarnDTO.getStatus() == Constants.NOT_HANDLE_WARN_STATUS && 
            			delDifferWarnDTO.getHandleType() == Constants.WARN_HANDLE_TYPE_3){
            		//系统查档的预警，不清空未处理的数据
            		continue;
            	}
            	
               client.delHandleDifferWarn(delDifferWarnDTO);
            }
            
         }
         logger.info("清除差异预警处理表定时调度成功: " + handleDifferWarnList + "  ,时间:  " + DateUtil.format(DateUtil.getToday()));
      } catch (Exception e) {
         logger.info("清除差异预警处理表定时调度任务异常" + ExceptionUtil.getExceptionMessage(e));
      } finally {
         if (clientFactory != null) {
            try {
               clientFactory.destroy();
            } catch (ThriftException e) {
            }
         }
      }

      logger.info("清除差异预警处理表定时调度任务结束" + DateUtil.format(DateUtil.getToday()));
   }
}
