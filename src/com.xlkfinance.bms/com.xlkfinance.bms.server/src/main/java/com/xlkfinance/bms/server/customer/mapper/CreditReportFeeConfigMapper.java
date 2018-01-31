package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.customer.CreditReportFeeConfig;
import com.xlkfinance.bms.rpc.customer.CusCreditReportHis;
import com.xlkfinance.bms.rpc.partner.CusCreditInfo;
/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： czz <br>
 * ★☆ @time：2018-01-15 15:49:52 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 征信报告费用配置表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("creditReportFeeConfigMapper")
public interface CreditReportFeeConfigMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:czz
    *@time:2018-01-15 15:49:52
    */
   public List<CreditReportFeeConfig> getAll(CreditReportFeeConfig creditReportFeeConfig);

   /**
    *根据id查询
    *@author:czz
    *@time:2018-01-15 15:49:52
    */
   public CreditReportFeeConfig getById(int pid);

   /**
    *插入一条数据
    *@author:czz
    *@time:2018-01-15 15:49:52
    */
   public int insert(CreditReportFeeConfig creditReportFeeConfig);

   /**
    *根据id更新数据
    *@author:czz
    *@time:2018-01-15 15:49:52
    */
   public int update(CreditReportFeeConfig creditReportFeeConfig);

   /**
    *根据id删除数据
    *@author:czz
    *@time:2018-01-15 15:49:52
    */
   public int deleteById(int pid);

   /**
    *根据id集合删除
    *@author:czz
    *@time:2018-01-15 15:49:52
    */
   public int deleteByIds(List<Integer> pids);
   
   
   /**
    * 查询征信报告费用配置记录（可分页）
    * @param cusCreditInfo
    * @return
    */
   List<CreditReportFeeConfig> selectList(CreditReportFeeConfig creditReportFeeConfig);
   

   /**
    * 查询征信报告费用配置数量
    */
   int selectTotal(CreditReportFeeConfig creditReportFeeConfig);
}
