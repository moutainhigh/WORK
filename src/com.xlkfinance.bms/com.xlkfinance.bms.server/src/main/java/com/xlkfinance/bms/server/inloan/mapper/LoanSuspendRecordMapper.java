package com.xlkfinance.bms.server.inloan.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.inloan.LoanSuspendRecord;
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
@MapperScan("loanSuspendRecordMapper")
public interface LoanSuspendRecordMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2017-03-28 10:03:20
    */
   public List<LoanSuspendRecord> getAll(LoanSuspendRecord loanSuspendRecord);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2017-03-28 10:03:20
    */
   public LoanSuspendRecord getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2017-03-28 10:03:20
    */
   public int insert(LoanSuspendRecord loanSuspendRecord);

}
