package com.xlkfinance.bms.server.aom.finance.service;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.qfang.xk.aom.rpc.finance.OrgAssureFundRecord;
import com.qfang.xk.aom.rpc.finance.OrgAssureFundRecordService.Iface;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyInf;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.SysLogTypeConstant;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.SysLog;
import com.xlkfinance.bms.rpc.system.SysLogService;
import com.xlkfinance.bms.server.aom.finance.mapper.OrgAssureFundRecordMapper;
import com.xlkfinance.bms.server.aom.org.mapper.OrgCooperatCompanyApplyInfMapper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-09-02 15:44:44 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 保证金记录<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Service("orgAssureFundRecordServiceImpl")
@ThriftService(service = "com.qfang.xk.aom.rpc.finance.OrgAssureFundRecordService")
public class OrgAssureFundRecordServiceImpl implements Iface {
   @Resource(name = "orgAssureFundRecordMapper")
   private OrgAssureFundRecordMapper orgAssureFundRecordMapper;

   @Resource(name = "orgCooperatCompanyApplyInfMapper")
   private OrgCooperatCompanyApplyInfMapper orgCooperatMapper;
   
   @Resource(name = "sysLogServiceImpl")
   private SysLogService.Iface sysLogServiceImpl;
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-09-02 15:44:44
    */
   @Override
   public List<OrgAssureFundRecord> getAll(OrgAssureFundRecord orgAssureFundRecord) throws ThriftServiceException, TException {
      return orgAssureFundRecordMapper.getAll(orgAssureFundRecord);
   }

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-09-02 15:44:44
    */
   @Override
   public OrgAssureFundRecord getById(int pid) throws ThriftServiceException, TException {
      OrgAssureFundRecord orgAssureFundRecord = orgAssureFundRecordMapper.getById(pid);
      if (orgAssureFundRecord==null) {
         return new OrgAssureFundRecord();
      }
      return orgAssureFundRecord;
   }

   /**
    *收取保证金
    *增加保证金会相应的修改机构的启用授信额度和实收保证金额
    *设置可用授信额度 = 当前可用额度+（修改后的额度提升数值）
    *@author:liangyanjun
    *@time:2016-09-02 15:44:44
    */
   @Override
   public int insert(OrgAssureFundRecord orgAssureFundRecord) throws ThriftServiceException, TException {
      int orgId = orgAssureFundRecord.getOrgId();//机构id
      double money = orgAssureFundRecord.getMoney();//收取保证金额
      int type = orgAssureFundRecord.getType();//保证金记录类型：收保证金=1，退保证金=2
      int updateId = orgAssureFundRecord.getUpdateId();//修改用户id
      OrgCooperatCompanyApplyInf query = new OrgCooperatCompanyApplyInf();
      query.setOrgId(orgId);
      List<OrgCooperatCompanyApplyInf> list = orgCooperatMapper.getAll(query);
      if (list != null && list.size() > 0) {
         OrgCooperatCompanyApplyInf applyInf = list.get(0);
         double oldAvailableLimit = applyInf.getAvailableLimit(); //修改之前的可用额度
         double oldActivateCreditLimit = applyInf.getActivateCreditLimit();//修改之前的启用额度
         double creditLimit = applyInf.getCreditLimit();//授信额度
         double realAssureMoney = applyInf.getRealAssureMoney();//已经收取的保证金额
         double assureMoneyProportion = applyInf.getAssureMoneyProportion();//保证金比例（百分比）
         // 保证金记录类型：收保证金=1，退保证金=2
         if (type == AomConstant.ASSURE_FUND_RECORD_TYPE_2) {
            realAssureMoney = realAssureMoney - money;
         } else {
             realAssureMoney = realAssureMoney + money;
         }
         double activateCreditLimit=realAssureMoney/(assureMoneyProportion/100);//（修改后）启用授信额度=实收保证金/(保证金比例/100)
         //判断启用授信额度是否大于授信额度，如果大于则说明数据有误
         if (activateCreditLimit>creditLimit) {
            throw new RuntimeException("启用授信额度大于授信额度，数据有误");
         }
         //设置可用授信额度 = 当前可用额度+（修改后的额度提升数值）
         double availableLimit = oldAvailableLimit +(activateCreditLimit - oldActivateCreditLimit);
         if (availableLimit>=activateCreditLimit) {//如果修改后的可用授信额度大于等于启用授信额度，则直接设置等于启用授信额度
             availableLimit=activateCreditLimit;
         }
         applyInf.setAvailableLimit(availableLimit);
         applyInf.setActivateCreditLimit(activateCreditLimit);
         applyInf.setRealAssureMoney(realAssureMoney);
         //执行更新实收保证金和启用授信额度
         orgCooperatMapper.update(applyInf);
         //添加系统日志
         SysLog sysLog = new SysLog();
         sysLog.setModules(BusinessModule.MODUEL_ORG_SYSTEM);
         sysLog.setLogType(SysLogTypeConstant.LOG_TYPE_UPDATE);
         sysLog.setUserId(updateId);
         sysLog.setLogMsg("修改机构实收保证金：orgId:"+orgId+",type："+type+",realAssureMoney:"+realAssureMoney+",money:"+money+",计算结果："+applyInf.getRealAssureMoney()+",其他参数：orgAssureFundRecord="+orgAssureFundRecord+",applyInf="+applyInf);
        sysLogServiceImpl.addSysLog(sysLog);
      }
      return orgAssureFundRecordMapper.insert(orgAssureFundRecord);
   }

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-09-02 15:44:44
    */
   @Override
   public int update(OrgAssureFundRecord orgAssureFundRecord) throws ThriftServiceException, TException {
      return orgAssureFundRecordMapper.update(orgAssureFundRecord);
   }

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2016-09-02 15:44:44
    */
   @Override
   public int deleteById(int pid) throws ThriftServiceException, TException {
      return orgAssureFundRecordMapper.deleteById(pid);
   }

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2016-09-02 15:44:44
    */
   @Override
   public int deleteByIds(List<Integer> pids) throws ThriftServiceException, TException {
      return orgAssureFundRecordMapper.deleteByIds(pids);
   }

   /**
    * 保证金记录列表(分页查询)
    *@author:liangyanjun
    *@time:2016年9月2日下午3:57:30
    */
   @Override
   public List<OrgAssureFundRecord> queryOrgAssureFundRecordPage(OrgAssureFundRecord query) throws TException {
      return orgAssureFundRecordMapper.queryOrgAssureFundRecordPage(query);
   }

   /**
    *@author:liangyanjun
    *@time:2016年9月2日下午3:57:30
    */
   @Override
   public int getOrgAssureFundRecordTotal(OrgAssureFundRecord query) throws TException {
      return orgAssureFundRecordMapper.getOrgAssureFundRecordTotal(query);
   }

}
