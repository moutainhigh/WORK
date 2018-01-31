package com.xlkfinance.bms.server.inloan.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.inloan.BizDynamicEvaluateInfo;
import com.xlkfinance.bms.rpc.inloan.BizEvaluateMap;

/**   
 * 
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年3月29日上午10:14:23 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation：业务办理动态评级管理 <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("bizDynamicEvaluateMapper")
public interface BizDynamicEvaluateMapper<T, PK> extends BaseMapper<T, PK> {

   /**
    * 根据用户id和办理动态id检查是否已经评分，返回值大于0，则已经评分
    *@author:liangyanjun
    *@time:2016年3月29日上午11:49:10
    *@param bizDynamicEvaluateInfo
    *@return
    */
   int checkIsEvaluate(BizDynamicEvaluateInfo bizDynamicEvaluateInfo);

   /**
    * 添加评分
    *@author:liangyanjun
    *@time:2016年3月29日上午11:49:16
    *@param bizDynamicEvaluateInfo
    */
   void addEvaluate(BizDynamicEvaluateInfo bizDynamicEvaluateInfo);

   /**
    * 更新
    *@author:liangyanjun
    *@time:2016年3月29日下午12:07:20
    *@param bizDynamicEvaluateInfo
    */
   void updateEvaluate(BizDynamicEvaluateInfo bizDynamicEvaluateInfo);

   /**
    * 查询办理动态的差评和点赞数量
    *@author:liangyanjun
    *@time:2016年3月29日下午2:55:01
    *@param handleDynamicId
    *@return
    */
   BizEvaluateMap queryBizEvaluateMap(int handleDynamicId);

}
