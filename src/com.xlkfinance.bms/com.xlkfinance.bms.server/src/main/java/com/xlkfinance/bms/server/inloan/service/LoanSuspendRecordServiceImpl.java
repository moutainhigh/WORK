package com.xlkfinance.bms.server.inloan.service;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.beforeloan.ProjectForeclosure;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.inloan.LoanSuspendRecord;
import com.xlkfinance.bms.rpc.inloan.LoanSuspendRecordService.Iface;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectForeclosureMapper;
import com.xlkfinance.bms.server.inloan.mapper.LoanSuspendRecordMapper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-03-28 10:03:20 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 放款挂起记录表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Service("loanSuspendRecordServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.inloan.LoanSuspendRecordService")
public class LoanSuspendRecordServiceImpl implements Iface {
   private Logger logger = LoggerFactory.getLogger(LoanSuspendRecordServiceImpl.class);

   @Resource(name = "loanSuspendRecordMapper")
   private LoanSuspendRecordMapper loanSuspendRecordMapper;
   
   @Resource(name = "projectForeclosureMapper")
   private ProjectForeclosureMapper projectForeclosureMapper;

   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2017-03-28 10:03:20
    */
   @Override
   public List<LoanSuspendRecord> getAll(LoanSuspendRecord loanSuspendRecord) throws ThriftServiceException, TException {
      return loanSuspendRecordMapper.getAll(loanSuspendRecord);
   }

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2017-03-28 10:03:20
    */
   @Override
   public LoanSuspendRecord getById(int pid) throws ThriftServiceException, TException {
      LoanSuspendRecord loanSuspendRecord = loanSuspendRecordMapper.getById(pid);
      if (loanSuspendRecord==null) {
         return new LoanSuspendRecord();
      }
      return loanSuspendRecord;
   }

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2017-03-28 10:03:20
    */
   @Override
   public int insert(LoanSuspendRecord loanSuspendRecord) throws ThriftServiceException, TException {
      return loanSuspendRecordMapper.insert(loanSuspendRecord);
   }

   /**
    * 放款挂起
    *@author:liangyanjun
    *@time:2017年3月29日上午10:11:05
    */
    @Override
    @Transactional
    public int loanSuspendInfo(LoanSuspendRecord loanSuspendRecord) throws TException {
        try {
            int projectId = loanSuspendRecord.getProjectId();
            int suspendDay = loanSuspendRecord.getSuspendDay();//挂起天数
            loanSuspendRecordMapper.insert(loanSuspendRecord);
            //修改预计回款时间
            ProjectForeclosure projectForeclosure = projectForeclosureMapper.getForeclosureByProjectId(projectId);
            String oldPayDateStr = projectForeclosure.getPaymentDate();//旧的预计回款日期
            Date newPayDate = DateUtils.addDay(DateUtils.stringToDate(oldPayDateStr, "yyyy-MM-dd"), suspendDay);//新的预计回款日期=旧的回款日期+挂起天数
            String newPayDateStr = DateUtils.dateFormatByPattern(newPayDate, "yyyy-MM-dd");
            projectForeclosure.setPaymentDate(newPayDateStr);
            projectForeclosureMapper.updateByPrimaryKey(projectForeclosure);
            logger.info("修改预计回款日期：入参：projectId:"+ projectId+"，oldPayDate："+oldPayDateStr+",newPayDate:"+newPayDateStr);
        } catch (Exception e) {
            logger.error("放款挂起异常：入参：loanSuspendRecord:"+ loanSuspendRecord+"。错误：" + ExceptionUtil.getExceptionMessage(e));
            throw new RuntimeException(ExceptionUtil.getExceptionMessage(e));
        }
        return 0;
    }

}
