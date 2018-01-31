package com.qfang.xk.aom.web.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qfang.xk.aom.rpc.system.SmsValidateCodeInfoService;
import com.qfang.xk.aom.web.controller.BaseController;
import com.qfang.xk.aom.web.util.IpAddressUtil;
import com.qfang.xk.aom.web.util.PropertiesUtil;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.OrgSysLogTypeConstant;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年7月6日上午10:30:57 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 短信验证码管理<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Controller
@RequestMapping("/smsValidateCodeController")
public class SmsValidateCodeInfoController extends BaseController {
   private Logger logger = LoggerFactory.getLogger(SmsValidateCodeInfoController.class);

   /**
    * 发送短信验证码
    *@author:liangyanjun
    *@time:2016年7月7日下午2:35:27
    *@param request
    *@param phone
    *@param category
    *@return
    */
   @RequestMapping(value = "/ignore/sendCodeMsg", method = RequestMethod.POST)
   public void sendCodeMsg(HttpServletRequest req,HttpServletResponse resp, String phone, int category) {
      if (StringUtil.isBlank(phone)) {
         fillReturnJson(resp, false, "发送失败,请输入手机号码!");
         return;
      }
      SmsValidateCodeInfoService.Client service = (SmsValidateCodeInfoService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_SYSTEM,
            "SmsValidateCodeInfoService");
      try {
          String ipAddress=IpAddressUtil.getIpAddress(req);
         int msgCountLimit = PropertiesUtil.getIntValue("msgCountLimit");//一天内短信发送限制次数（维度：手机号码+发送类型）
         int msgCountLimitByIp = PropertiesUtil.getIntValue("msgCountLimitByIp");//一天内短信发送限制次数（维度：ip地址）
         int count = service.getTodayMsgCountByPhone(phone, category);
         int todayMsgCountByIp = service.getTodayMsgCountByIp(ipAddress);
         if (count>msgCountLimit||todayMsgCountByIp>msgCountLimitByIp) {
            fillReturnJson(resp, false, "你发送短信的次数过多，请明天再操作");
            return;
         }
         String msg = service.sendCodeMsg(phone, category,ipAddress);
         recordLog(OrgSysLogTypeConstant.LOG_TYPE_ADD, "发送验证码短信,参数：phone="+phone+";category="+category+";msg="+msg, req);
         fillReturnJson(resp, true, "发送成功");
      } catch (TException e) {
         fillReturnJson(resp, false, "发送失败,请联系管理员!");
         logger.error("发送短信验证码失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：telphone=" + phone + ",category=" + category);
         e.printStackTrace();
      }
   }
}
