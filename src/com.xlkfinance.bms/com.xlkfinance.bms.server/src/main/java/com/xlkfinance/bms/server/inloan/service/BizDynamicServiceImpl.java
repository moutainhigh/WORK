package com.xlkfinance.bms.server.inloan.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.constant.BizDynamicConstant;
import com.xlkfinance.bms.rpc.inloan.BizDynamic;
import com.xlkfinance.bms.rpc.inloan.BizDynamicService.Iface;
import com.xlkfinance.bms.server.inloan.mapper.BizDynamicMapper;

/**
 * 
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年5月3日上午11:02:12 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation：业务动态（项目总览） <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("bizDynamicServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.inloan.BizDynamicService")
public class BizDynamicServiceImpl implements Iface {
   @Resource(name = "bizDynamicMapper")
   private BizDynamicMapper bizDynamicMapper;
    /**
     * 根据条件查询业务动态信息（分页查询）
     *@author:liangyanjun
     *@time:2016年12月23日下午5:14:45
     */
   @Override
   public List<BizDynamic> queryBizDynamic(BizDynamic bizDynamic) throws TException {
      return bizDynamicMapper.queryBizDynamic(bizDynamic);
   }

   /**
    * 根据条件查询业务动态信息总数
    *@author:liangyanjun
    *@time:2016年12月23日下午5:15:11
    *
    */
   @Override
   public int getBizDynamicTotal(BizDynamic bizDynamic) throws TException {
      return bizDynamicMapper.getBizDynamicTotal(bizDynamic);
   }
    /**
     * 添加
     *@author:liangyanjun
     *@time:2016年12月23日下午5:15:15
     *
     */
   @Override
   public boolean addBizDynamic(BizDynamic bizDynamic) throws TException {
      bizDynamicMapper.addBizDynamic(bizDynamic);
      return true;
   }
   /**
    * 更新
    *@author:liangyanjun
    *@time:2016年12月23日下午5:15:15
    *
    */
   @Override
   public boolean updateBizDynamic(BizDynamic bizDynamic) throws TException {
      bizDynamicMapper.updateBizDynamic(bizDynamic);
      return true;
   }

   /**
    * 根据ID,项目ID,模块编号,删除业务动态
    *@author:liangyanjun
    *@time:2016年5月11日下午5:06:02
    */
   @Override
   public boolean delBizDynamic(BizDynamic bizDynamic) throws TException {
      bizDynamicMapper.delBizDynamic(bizDynamic);
      return true;
   }

   /**
    * 根据父级动态id级联删除子节点，不包括父节点
    *@author:liangyanjun
    *@time:2016年5月11日下午5:06:02
    */
   @Override
   public boolean delBizDynamicByCascade(BizDynamic bizDynamic) throws TException {
      bizDynamicMapper.delBizDynamicByCascade(bizDynamic);
      return true;
   }

   /**
    * 添加或更新：不存在则添加，存在则更新
    *@author:liangyanjun
    *@time:2016年5月12日上午10:29:04
    */
   @Override
   public boolean addOrUpdateBizDynamic(BizDynamic bizDynamic) throws TException {
      int projectId = bizDynamic.getProjectId();
      String moduelNumber = bizDynamic.getModuelNumber();
      String parentDynamicNumber = bizDynamic.getParentDynamicNumber();
      String dynamicNumber = bizDynamic.getDynamicNumber();
      int handleAuthorId = bizDynamic.getHandleAuthorId();
      String finishDate = bizDynamic.getFinishDate();
      String remark = bizDynamic.getRemark();
      int status = bizDynamic.getStatus();
      
      BizDynamic query = new BizDynamic();
      query.setProjectId(projectId);
      query.setModuelNumber(moduelNumber);
      query.setDynamicNumber(dynamicNumber);
      query.setParentDynamicNumber(parentDynamicNumber);
      List<BizDynamic> list = bizDynamicMapper.queryBizDynamic(query);
      
      if (list == null || list.isEmpty()) {
         bizDynamicMapper.addBizDynamic(bizDynamic);
      } else {
         BizDynamic updateBizDynamic = list.get(0);
         updateBizDynamic.setHandleAuthorId(handleAuthorId);
         updateBizDynamic.setFinishDate(finishDate);
         updateBizDynamic.setRemark(remark);
         updateBizDynamic.setStatus(status);
         bizDynamicMapper.updateBizDynamic(updateBizDynamic);
      }
      return true;
   }

   /**
    * 初始化全部数据
    *@author:liangyanjun
    *@time:2016年5月12日上午11:37:10
    */
   @Override
   public boolean initBizDynamic(int projectId) throws TException {
      Map<String, Map<String, String>> moduelNumberInloanMap = BizDynamicConstant.MODUEL_NUMBER_INLOAN_MAP;
      Set<String> moduelNumberInloanMapKeys = moduelNumberInloanMap.keySet();
      for (String moduelNumber : moduelNumberInloanMapKeys) {
         Map<String, String> dynamicNumberMap = moduelNumberInloanMap.get(moduelNumber);
         Set<String> dynamicNumberKeys = dynamicNumberMap.keySet();
         String parentDynamicNumber=null;
         for (String dynamicNumber : dynamicNumberKeys) {
            String dynamicName = dynamicNumberMap.get(dynamicNumber);
            BizDynamic bizDynamic = new BizDynamic();
            bizDynamic.setProjectId(projectId);
            bizDynamic.setModuelNumber(moduelNumber);
            bizDynamic.setParentDynamicNumber(parentDynamicNumber);
            bizDynamic.setDynamicNumber(dynamicNumber);
            bizDynamic.setDynamicName(dynamicName);
            bizDynamic.setStatus(BizDynamicConstant.STATUS_1);
            bizDynamicMapper.addBizDynamic(bizDynamic);
            parentDynamicNumber=dynamicNumber;
         }
      }
      return true;
   }

/**
 * 根据项目ID,模块编号,驳回的节点Id删除业务动态
 */
@Override
public int delBizDynamicByLastId(BizDynamic bizDynamic) throws TException {
		int rows = bizDynamicMapper.delBizDynamicByLastId(bizDynamic);
	return rows;
}

}
