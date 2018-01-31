package com.xlkfinance.bms.server.partner.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.partner.BizPartnerBankInfo;
import com.xlkfinance.bms.rpc.partner.CusCreditInfo;
/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： czz <br>
 * ★☆ @time：2017-03-08 19:18:05 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 资金机构银行信息表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("bizPartnerBankInfoMapper")
public interface BizPartnerBankInfoMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:czz
    *@time:2017-03-08 19:18:05
    */
   public List<BizPartnerBankInfo> getAll(BizPartnerBankInfo bizPartnerBankInfo);

   /**
    *根据id查询
    *@author:czz
    *@time:2017-03-08 19:18:05
    */
   public BizPartnerBankInfo getById(int pid);

   /**
    *插入一条数据
    *@author:czz
    *@time:2017-03-08 19:18:05
    */
   public int insert(BizPartnerBankInfo bizPartnerBankInfo);

   /**
    *根据id更新数据
    *@author:czz
    *@time:2017-03-08 19:18:05
    */
   public int update(BizPartnerBankInfo bizPartnerBankInfo);

   /**
    *根据id删除数据
    *@author:czz
    *@time:2017-03-08 19:18:05
    */
   public int deleteById(int pid);

   /**
    *根据id集合删除
    *@author:czz
    *@time:2017-03-08 19:18:05
    */
   public int deleteByIds(List<Integer> pids);
   
   /**
    * 查询列表（可分页）
    * @param cusCreditInfo
    * @return
    */
   List<BizPartnerBankInfo> selectPartnerBankList(BizPartnerBankInfo bizPartnerBankInfo);
   

   /**
    * 查询数量
    */
   int selectPartnerBankTotal(BizPartnerBankInfo bizPartnerBankInfo);
}
