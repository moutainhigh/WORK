package com.xlkfinance.bms.server.inloan.service;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.inloan.BizDynamicEvaluateInfo;
import com.xlkfinance.bms.rpc.inloan.BizDynamicEvaluateService.Iface;
import com.xlkfinance.bms.rpc.inloan.BizEvaluateMap;
import com.xlkfinance.bms.server.inloan.mapper.BizDynamicEvaluateMapper;

/**   
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年3月29日上午10:13:43 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 业务办理动态评级管理  Service<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Service("BizDynamicEvaluateServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.inloan.BizDynamicEvaluateService")
public class BizDynamicEvaluateServiceImpl implements Iface {
   private Logger logger = LoggerFactory.getLogger(BizDynamicEvaluateServiceImpl.class);
   @Resource(name = "bizDynamicEvaluateMapper")
   private BizDynamicEvaluateMapper bizDynamicEvaluateMapper;

   /**
    * 根据用户id和办理动态id检查是否已经评分，返回值大于0，则已经评分
    *@author:liangyanjun
    *@time:2016年3月29日上午11:49:10
    *@param bizDynamicEvaluateInfo
    *@return
    */
   @Override
   public int checkIsEvaluate(BizDynamicEvaluateInfo bizDynamicEvaluateInfo) throws TException {
      return bizDynamicEvaluateMapper.checkIsEvaluate(bizDynamicEvaluateInfo);
   }

   /**
    * 添加评分
    *@author:liangyanjun
    *@time:2016年3月29日上午11:49:16
    *@param bizDynamicEvaluateInfo
    */
   @Override
   public boolean addEvaluate(BizDynamicEvaluateInfo bizDynamicEvaluateInfo) throws TException {
      bizDynamicEvaluateMapper.addEvaluate(bizDynamicEvaluateInfo);
      return true;
   }

   /**
    * 更新
    *@author:liangyanjun
    *@time:2016年3月29日下午12:07:20
    *@param bizDynamicEvaluateInfo
    */
   @Override
   public boolean updateEvaluate(BizDynamicEvaluateInfo bizDynamicEvaluateInfo) throws TException {
      bizDynamicEvaluateMapper.updateEvaluate(bizDynamicEvaluateInfo);
      return true;
   }
    /**
     * 查询办理动态的差评和点赞数量
     */
   @Override
   public BizEvaluateMap queryBizEvaluateMap(int handleDynamicId) throws TException {
      return bizDynamicEvaluateMapper.queryBizEvaluateMap(handleDynamicId);
   }

}
