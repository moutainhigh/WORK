package com.xlkfinance.bms.server.system.service;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.system.SysMailInfo;
import com.xlkfinance.bms.rpc.system.SysMailInfoService.Iface;
import com.xlkfinance.bms.server.system.mapper.SysMailInfoMapper;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-01-18 09:59:45 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 邮件信息表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Service("sysMailInfoServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.system.SysMailInfoService")
public class SysMailInfoServiceImpl implements Iface {
   private Logger logger = LoggerFactory.getLogger(SysMailInfoServiceImpl.class);

   @Resource(name = "sysMailInfoMapper")
   private SysMailInfoMapper sysMailInfoMapper;
   
   @Autowired
   private JavaMailSenderImpl javaMailSenderImpl;

   /**
    *@author:liangyanjun
    *@time:2016年4月22日下午4:42:30
    */
   @Override
   public int sendMail(SysMailInfo mail) throws TException {
      try {
         MimeMessage msg = javaMailSenderImpl.createMimeMessage();
         // 创建MimeMessageHelper对象，处理MimeMessage的辅助类
         MimeMessageHelper helper = new MimeMessageHelper(msg, "UTF-8");
         // 使用辅助类MimeMessage设定参数
         helper.setFrom(javaMailSenderImpl.getUsername());
         helper.setTo(mail.getRecMail());
         helper.setSubject(mail.getSubject());
         helper.setText(mail.getContent(), true);
         // 发送邮件
         javaMailSenderImpl.send(helper.getMimeMessage());
         mail.setSendMail(javaMailSenderImpl.getJavaMailProperties().getProperty("mail.smtp.from"));
         mail.setStatus(Constants.SUCCEED);
      } catch (Exception e) {
         mail.setStatus(Constants.FAILED);
         e.printStackTrace();
         logger.error("发送邮件异常：入参：mail:"+ mail +"。错误：" + ExceptionUtil.getExceptionMessage(e));
      }
      insert(mail);
      return 0;
   }
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2017-01-18 09:59:45
    */
   @Override
   public List<SysMailInfo> getAll(SysMailInfo sysMailInfo) throws ThriftServiceException, TException {
      return sysMailInfoMapper.getAll(sysMailInfo);
   }

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2017-01-18 09:59:45
    */
   @Override
   public SysMailInfo getById(int pid) throws ThriftServiceException, TException {
      SysMailInfo sysMailInfo = sysMailInfoMapper.getById(pid);
      if (sysMailInfo==null) {
         return new SysMailInfo();
      }
      return sysMailInfo;
   }

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2017-01-18 09:59:45
    */
   @Override
   public int insert(SysMailInfo sysMailInfo) throws ThriftServiceException, TException {
      return sysMailInfoMapper.insert(sysMailInfo);
   }

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2017-01-18 09:59:45
    */
   @Override
   public int update(SysMailInfo sysMailInfo) throws ThriftServiceException, TException {
      return sysMailInfoMapper.update(sysMailInfo);
   }

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2017-01-18 09:59:45
    */
   @Override
   public int deleteById(int pid) throws ThriftServiceException, TException {
      return sysMailInfoMapper.deleteById(pid);
   }

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2017-01-18 09:59:45
    */
   @Override
   public int deleteByIds(List<Integer> pids) throws ThriftServiceException, TException {
      return sysMailInfoMapper.deleteByIds(pids);
   }

}
