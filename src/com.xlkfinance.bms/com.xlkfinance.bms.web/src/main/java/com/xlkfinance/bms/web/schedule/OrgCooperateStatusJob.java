package com.xlkfinance.bms.web.schedule;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.util.DateUtil;
import com.qfang.xk.aom.rpc.org.OrgAssetsCooperationInfo;
import com.qfang.xk.aom.rpc.org.OrgAssetsCooperationService;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyInf;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyService;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.OrgSysLogTypeConstant;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.web.controller.BaseController;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年8月24日下午4:37:53 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 机构合作状态更新定时调度任务:机构的合作结束时间超过今天，则把合作状态设置为已过期<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Component
public class OrgCooperateStatusJob extends BaseController {
   private Logger logger = LoggerFactory.getLogger(OrgCooperateStatusJob.class);

//   @Scheduled(cron = "0 01 0 ? * *")
   // 每天 凌晨01分开始执行
   public void execute() {
      logger.info("机构合作状态更新定时调度任务开始" + DateUtil.format(DateUtil.getToday()));
      BaseClientFactory orgCooperatFactory = null;
      BaseClientFactory orgFactory = null;
      try {
         orgCooperatFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgCooperatCompanyApplyService");
         OrgCooperatCompanyApplyService.Client orgCooperatClient = (OrgCooperatCompanyApplyService.Client) orgCooperatFactory.getClient();
         orgFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgAssetsCooperationService");
         OrgAssetsCooperationService.Client orgClient = (OrgAssetsCooperationService.Client) orgFactory.getClient();
         orgFactory = getAomFactory(BusinessModule.MODUEL_ORG, "OrgAssetsCooperationService");

         OrgCooperatCompanyApplyInf orgCooperate = new OrgCooperatCompanyApplyInf();
         List<OrgCooperatCompanyApplyInf> list = orgCooperatClient.getAll(orgCooperate);
         if (list == null || list.isEmpty()) {
            return;
         }
         for (OrgCooperatCompanyApplyInf obj : list) {
            if (StringUtil.isBlank(obj.getEndTime())) {
                continue;
            }
            Date endTime = DateUtils.stringToDate(obj.getEndTime(), "yyyy-MM-dd");
            // 超过今天，合作状态设置为已过期
            if (DateUtils.dayDifference(endTime, new Date()) > 0) {
               OrgAssetsCooperationInfo org = orgClient.getById(obj.getUserId());
               if (org.getCooperateStatus()!=AomConstant.ORG_COOPERATE_STATUS_3) {
                  org.setCooperateStatus(AomConstant.ORG_COOPERATE_STATUS_3);// 合作状态设置为已过期
                  orgClient.updateCooperateStatus(org);
                  recordAomLog(OrgSysLogTypeConstant.LOG_TYPE_UPDATE, org.getOrgName() + "机构合作状态已到期  参数：" + org, null);
               }
            }
         }
         logger.info("机构合作状态更新定时调度任务成功,时间:  " + DateUtil.format(DateUtil.getToday()));
      } catch (Exception e) {
         logger.info("机构合作状态更新定时调度任务异常" + ExceptionUtil.getExceptionMessage(e));
      } finally {
         destroyFactory(orgCooperatFactory, orgFactory);
      }

      logger.info("机构合作状态更新定时调度任务结束" + DateUtil.format(DateUtil.getToday()));
   }
}
