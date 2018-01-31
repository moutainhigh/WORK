package com.xlkfinance.bms.server.partner.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.partner.CusCreditInfo;
import com.xlkfinance.bms.rpc.report.HandleDifferWarnReport;
/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： czz <br>
 * ★☆ @time：2017-02-15 18:35:29 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 客户征信信息表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("cusCreditInfoMapper")
public interface CusCreditInfoMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:czz
    *@time:2017-02-15 18:35:29
    */
   public List<CusCreditInfo> getAll(CusCreditInfo cusCreditInfo);

   /**
    *根据id查询
    *@author:czz
    *@time:2017-02-15 18:35:29
    */
   public CusCreditInfo getById(int pid);

   /**
    *插入一条数据
    *@author:czz
    *@time:2017-02-15 18:35:29
    */
   public int insert(CusCreditInfo cusCreditInfo);

   /**
    *根据id更新数据
    *@author:czz
    *@time:2017-02-15 18:35:29
    */
   public int update(CusCreditInfo cusCreditInfo);

   /**
    *根据id删除数据
    *@author:czz
    *@time:2017-02-15 18:35:29
    */
   public int deleteById(int pid);

   /**
    *根据id集合删除
    *@author:czz
    *@time:2017-02-15 18:35:29
    */
   public int deleteByIds(List<Integer> pids);
   
   /**
    * 查询客户征信列表（可分页）
    * @param cusCreditInfo
    * @return
    */
   List<CusCreditInfo> selectCusCreditList(CusCreditInfo cusCreditInfo);
   

   /**
    * 查询客户征信数量
    */
   int selectCusCreditTotal(CusCreditInfo cusCreditInfo);
}
