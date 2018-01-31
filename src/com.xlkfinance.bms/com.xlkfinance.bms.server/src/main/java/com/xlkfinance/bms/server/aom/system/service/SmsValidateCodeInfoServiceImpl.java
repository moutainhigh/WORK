package com.xlkfinance.bms.server.aom.system.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.qfang.xk.aom.rpc.system.SmsValidateCodeInfo;
import com.qfang.xk.aom.rpc.system.SmsValidateCodeInfoService.Iface;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.RamdomUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.server.aom.system.mapper.SmsValidateCodeInfoMapper;
import com.xlkfinance.bms.util.PropertiesUtil;
import com.xlkfinance.bms.util.SMSUtil;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-07-07 09:17:53 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 短信验证码信息表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Service("smsValidateCodeInfoServiceImpl")
@ThriftService(service = "com.qfang.xk.aom.rpc.system.SmsValidateCodeInfoService")
public class SmsValidateCodeInfoServiceImpl implements Iface {
   @Resource(name = "smsValidateCodeInfoMapper")
   private SmsValidateCodeInfoMapper smsValidateCodeInfoMapper;

   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-07-07 09:17:53
    */
   @Override
   public List<SmsValidateCodeInfo> getAll(SmsValidateCodeInfo smsValidateCodeInfo) throws ThriftServiceException, TException {
      return smsValidateCodeInfoMapper.getAll(smsValidateCodeInfo);
   }

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-07-07 09:17:53
    */
   @Override
   public SmsValidateCodeInfo getById(int pid) throws ThriftServiceException, TException {
      return smsValidateCodeInfoMapper.getById(pid);
   }

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-07-07 09:17:53
    */
   @Override
   public int insert(SmsValidateCodeInfo smsValidateCodeInfo) throws ThriftServiceException, TException {
      return smsValidateCodeInfoMapper.insert(smsValidateCodeInfo);
   }

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-07-07 09:17:53
    */
   @Override
   public int update(SmsValidateCodeInfo smsValidateCodeInfo) throws ThriftServiceException, TException {
      return smsValidateCodeInfoMapper.update(smsValidateCodeInfo);
   }

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2016-07-07 09:17:53
    */
   @Override
   public int deleteById(int pid) throws ThriftServiceException, TException {
      return smsValidateCodeInfoMapper.deleteById(pid);
   }

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2016-07-07 09:17:53
    */
   @Override
   public int deleteByIds(List<Integer> pids) throws ThriftServiceException, TException {
      return smsValidateCodeInfoMapper.deleteByIds(pids);
   }

   /**
    * 根据手机号码，验证码，短信类型，把短信验设置成无效状态
    *@author:liangyanjun
    *@time:2016年7月7日上午10:06:46
    */
   @Override
   public int disabledMsg(String telphone, String code, int category) throws TException {
      SmsValidateCodeInfo smsQuery = new SmsValidateCodeInfo();
      smsQuery.setTelphone(telphone);
      smsQuery.setCode(code);
      smsQuery.setCategory(category);
      List<SmsValidateCodeInfo> smsList = smsValidateCodeInfoMapper.getAll(smsQuery);
      for (SmsValidateCodeInfo sms : smsList) {
         sms.setStatus(AomConstant.STATUS_DISABLED);
         smsValidateCodeInfoMapper.update(sms);
      }
      return 1;
   }

   /**
    * 根据号码和短信类型获取今天内发送短信的条数
    *@author:liangyanjun
    *@time:2016年7月7日上午10:06:46
    */
   @Override
   public int getTodayMsgCountByPhone(String telphone, int category) throws TException {
      return smsValidateCodeInfoMapper.getTodayMsgCountByPhone(telphone, category);
   }

   /**
    * 验证验证码是否正确（true表示有效,false表示失效）
    *@author:liangyanjun
    *@time:2016年7月7日上午10:06:47
    */
   @Override
   public boolean validCode(String telphone, String code, int category) throws TException {
      SmsValidateCodeInfo smsQuery = new SmsValidateCodeInfo();
      smsQuery.setTelphone(telphone);
      smsQuery.setCode(code);
      smsQuery.setCategory(category);
      smsQuery.setStatus(AomConstant.STATUS_ENABLED);
      List<SmsValidateCodeInfo> smsList = smsValidateCodeInfoMapper.getAll(smsQuery);
      // 不存在返回false
      if (smsList == null || smsList.isEmpty()) {
         return false;
      }
      int validCodeTime = PropertiesUtil.getIntValue("validCode_time");
      SmsValidateCodeInfo sms = smsList.get(0);
      Date sendDate = DateUtils.stringToDate(sms.getSendDate());
      // 超过有效时间 返回false
      if (DateUtils.minutesDifference(sendDate, new Date()) > validCodeTime) {
         return false;
      }
      return true;
   }

   /**
    * 发送验证码
    *@author:liangyanjun
    *@time:2016年7月7日上午10:06:47
    */
   @Override
   public String sendCodeMsg(String telphone, int category,String ipAddress) throws TException {
      // 生成随机数验证码
      int bit = PropertiesUtil.getIntValue(AomConstant.RAMDOM_BIT);
      String code = null;
      String msg = null;
      int sendMsgFlag = PropertiesUtil.getIntValue("send_msg_flag");
      if (sendMsgFlag==AomConstant.SEND_MSG_FLAG_1) {
		// 发送短信
        code = RamdomUtil.getRandom(bit).toString();
    	msg = PropertiesUtil.getValueByBodel(AomConstant.SMS_TEMPLATE_ONE, code);
		SMSUtil.sendMessage(telphone, msg);
	  }else{
		code=PropertiesUtil.getValue("default_msg_code");
		msg = PropertiesUtil.getValueByBodel(AomConstant.SMS_TEMPLATE_ONE, code);
	  }
	// 插入一条数据库
      SmsValidateCodeInfo newSms = new SmsValidateCodeInfo();
      newSms.setTelphone(telphone);
      newSms.setCode(code);
      newSms.setCategory(category);
      newSms.setStatus(AomConstant.STATUS_ENABLED);
      newSms.setSendDate(DateUtils.getCurrentDateTime());
      newSms.setIpAddress(ipAddress);
      smsValidateCodeInfoMapper.insert(newSms);
      return msg;
   }

   /**
    * 根据条件查询发送短信验证码数量
    *@author:liangyanjun
    *@time:2016年7月7日上午10:10:19
    */
   @Override
   public int getCount(SmsValidateCodeInfo smsValidateCodeInfo) throws TException {
      return smsValidateCodeInfoMapper.getCount(smsValidateCodeInfo);
   }

   /**
    * 根据ip查询今天发送的短信验证码数量
    *@author:liangyanjun
    *@time:2016年9月27日上午10:55:51
    *
    */
    @Override
    public int getTodayMsgCountByIp(String ipAddress) throws TException {
        return smsValidateCodeInfoMapper.getTodayMsgCountByIp(ipAddress);
    }

}
