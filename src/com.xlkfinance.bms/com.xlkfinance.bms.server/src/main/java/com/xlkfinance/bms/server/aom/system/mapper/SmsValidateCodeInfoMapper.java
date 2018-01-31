package com.xlkfinance.bms.server.aom.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.qfang.xk.aom.rpc.system.SmsValidateCodeInfo;

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
@MapperScan("smsValidateCodeInfoMapper")
public interface SmsValidateCodeInfoMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-07-07 09:17:53
    */
   public List<SmsValidateCodeInfo> getAll(SmsValidateCodeInfo smsValidateCodeInfo);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-07-07 09:17:53
    */
   public SmsValidateCodeInfo getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-07-07 09:17:53
    */
   public int insert(SmsValidateCodeInfo smsValidateCodeInfo);

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-07-07 09:17:53
    */
   public int update(SmsValidateCodeInfo smsValidateCodeInfo);

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2016-07-07 09:17:53
    */
   public int deleteById(int pid);

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2016-07-07 09:17:53
    */
   public int deleteByIds(List<Integer> pids);

   /**
    * 根据条件查询发送短信验证码数量
    *@author:liangyanjun
    *@time:2016年7月7日上午10:11:13
    *@param smsValidateCodeInfo
    *@return
    */
   public int getCount(SmsValidateCodeInfo smsValidateCodeInfo);

   /**
    * 根据号码和短信类型获取今天内发送短信的条数
    *@author:liangyanjun
    *@time:2016年7月7日上午11:11:21
    *@param telphone
    *@param category
    *@return
    */
   public int getTodayMsgCountByPhone(@Param(value = "telphone")String telphone, @Param(value = "category")int category);

    /** 
     * 根据ip查询今天发送的短信验证码数量
     * @author:liangyanjun
     * @time:2016年9月27日上午10:56:29
     * @param ipAddress
     * @return 
     * */
    public int getTodayMsgCountByIp(@Param(value = "ipAddress") String ipAddress);

}
