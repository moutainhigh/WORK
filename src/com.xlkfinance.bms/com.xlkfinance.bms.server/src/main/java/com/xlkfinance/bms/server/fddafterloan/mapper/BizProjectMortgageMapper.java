package com.xlkfinance.bms.server.fddafterloan.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.fddafterloan.BizProjectMortgage;
import com.xlkfinance.bms.rpc.fddafterloan.BizProjectMortgageIndexPage;
/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-12-19 09:28:48 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 抵押管理信息<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("bizProjectMortgageMapper")
public interface BizProjectMortgageMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2017-12-19 09:28:48
    */
   public List<BizProjectMortgage> getAll(BizProjectMortgage bizProjectMortgage);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2017-12-19 09:28:48
    */
   public BizProjectMortgage getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2017-12-19 09:28:48
    */
   public int insert(BizProjectMortgage bizProjectMortgage);

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2017-12-19 09:28:48
    */
   public int update(BizProjectMortgage bizProjectMortgage);

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2017-12-19 09:28:48
    */
   public int deleteById(int pid);

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2017-12-19 09:28:48
    */
   public int deleteByIds(List<Integer> pids);

   public int getMortgageIndexPageTotal(BizProjectMortgageIndexPage query);

   public List<BizProjectMortgageIndexPage> queryMortgageIndexPage(BizProjectMortgageIndexPage query);
}
