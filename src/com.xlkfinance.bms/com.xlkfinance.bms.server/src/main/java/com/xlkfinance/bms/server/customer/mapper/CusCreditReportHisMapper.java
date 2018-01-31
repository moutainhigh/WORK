package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.customer.CusCreditReportHis;
import com.xlkfinance.bms.rpc.partner.CusCreditInfo;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： czz <br>
 * ★☆ @time：2017-06-06 17:04:54 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 客户征信报告记录<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("cusCreditReportHisMapper")
public interface CusCreditReportHisMapper<T, PK> extends BaseMapper<T, PK>{
	
	/**
	  * 生成主键
	 */
	public int getSeqPid();
	
	
   /**
    *根据条件查询所有
    *@author:czz
    *@time:2017-06-06 17:04:54
    */
   public List<CusCreditReportHis> getAll(CusCreditReportHis cusCreditReportHis);

   /**
    *根据id查询
    *@author:czz
    *@time:2017-06-06 17:04:54
    */
   public CusCreditReportHis getById(int pid);

   /**
    *插入一条数据
    *@author:czz
    *@time:2017-06-06 17:04:54
    */
   public int insert(CusCreditReportHis cusCreditReportHis);

   /**
    *根据id更新数据
    *@author:czz
    *@time:2017-06-06 17:04:54
    */
   public int update(CusCreditReportHis cusCreditReportHis);

   /**
    *根据id删除数据
    *@author:czz
    *@time:2017-06-06 17:04:54
    */
   public int deleteById(int pid);

   /**
    *根据id集合删除
    *@author:czz
    *@time:2017-06-06 17:04:54
    */
   public int deleteByIds(List<Integer> pids);
   
   
   /**
    * 查询客户征信报告记录（可分页）
    * @param cusCreditInfo
    * @return
    */
   List<CusCreditInfo> selectList(CusCreditReportHis cusCreditReportHis);
   

   /**
    * 查询客户征信报告记录数量
    */
   int selectTotal(CusCreditReportHis cusCreditReportHis);
   /**
    * 查询征信报告费用统计记录（可分页）
    * @param cusCreditInfo
    * @return
    */
   List<CusCreditInfo> selectCreditReportFeeList(CusCreditReportHis cusCreditReportHis);
   
   
   /**
    * 查询征信报告费用统计数量
    */
   int selectCreditReportFeeListTotal(CusCreditReportHis cusCreditReportHis);
   
   /**
    * 查询征信报告费用统计总和
    * @param cusCreditInfo
    * @return
    */
   CusCreditReportHis selectCreditReportFeeSum(CusCreditReportHis cusCreditReportHis);
}
