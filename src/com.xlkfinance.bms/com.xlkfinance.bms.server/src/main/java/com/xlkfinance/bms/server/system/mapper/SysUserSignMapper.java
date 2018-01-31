package com.xlkfinance.bms.server.system.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.system.SysUserSign;

/**   
 * 
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年3月29日上午10:14:23 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("sysUserSignMapper")
public interface SysUserSignMapper<T, PK> extends BaseMapper<T, PK> {

   /**
    *@author:liangyanjun
    *@time:2016年3月29日上午10:15:35
    *@param userId
    *@return
    */
   int getSignDaysByUserId(int userId);

   /**
    *@author:liangyanjun
    *@time:2016年3月29日上午10:16:08
    *@param sysUserSign
    */
   void addSysUserSign(SysUserSign sysUserSign);

   /**
    * 根据用户id检查今天是否已经签到，返回值大于0，则已经签到
    *@author:liangyanjun
    *@time:2016年3月29日上午10:54:32
    *@param userId
    *@return
    */
   int checkToDayIsSign(int userId);
}
