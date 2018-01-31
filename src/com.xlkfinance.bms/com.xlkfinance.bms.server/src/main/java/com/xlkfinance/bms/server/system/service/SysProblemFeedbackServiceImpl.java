package com.xlkfinance.bms.server.system.service;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.system.ProblemFeedback;
import com.xlkfinance.bms.rpc.system.SysProblemFeedbackService.Iface;
import com.xlkfinance.bms.server.system.mapper.SysProblemFeedbackMapper;

/**   
* @Title: BizFileServiceImpl.java 
* @Package com.xlkfinance.bms.server.system.service 
* @Description: 文件信息服务类，数据库表：BIZ_FILE  
* @author Ant.Peng   
* @date 2015年2月6日 下午6:06:47 
* @version V1.0   
*/
@Service("sysProblemFeedbackServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.system.SysProblemFeedbackService")
public class SysProblemFeedbackServiceImpl implements Iface {
   private Logger logger = LoggerFactory.getLogger(SysProblemFeedbackServiceImpl.class);

   @SuppressWarnings("rawtypes")
   @Resource(name = "sysProblemFeedbackMapper")
   private SysProblemFeedbackMapper feedbackMapper;

   @Override
   public int addProblemFeedback(ProblemFeedback problemFeedback) throws TException {
      feedbackMapper.addProblemFeedback(problemFeedback);
      return problemFeedback.getPid();
   }

}
