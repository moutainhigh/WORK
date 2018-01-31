package com.xlkfinance.bms.server.partner.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.partner.BizPartnerCustomerBank;
/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： czz <br>
 * ★☆ @time：2017-09-19 19:18:59 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 机构客户银行开户<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("bizPartnerCustomerBankMapper")
public interface BizPartnerCustomerBankMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:czz
    *@time:2017-09-19 19:18:59
    */
   public List<BizPartnerCustomerBank> getAll(BizPartnerCustomerBank bizPartnerCustomerBank);

   /**
    *根据id查询
    *@author:czz
    *@time:2017-09-19 19:18:59
    */
   public BizPartnerCustomerBank getById(int pid);

   /**
    *插入一条数据
    *@author:czz
    *@time:2017-09-19 19:18:59
    */
   public int insert(BizPartnerCustomerBank bizPartnerCustomerBank);

   /**
    *根据id更新数据
    *@author:czz
    *@time:2017-09-19 19:18:59
    */
   public int update(BizPartnerCustomerBank bizPartnerCustomerBank);

   /**
    *根据id删除数据
    *@author:czz
    *@time:2017-09-19 19:18:59
    */
   public int deleteById(int pid);

   /**
    *根据id集合删除
    *@author:czz
    *@time:2017-09-19 19:18:59
    */
   public int deleteByIds(List<Integer> pids);
   
   /**
    * 查询列表（可分页）
    * @param cusCreditInfo
    * @return
    */
   List<BizPartnerCustomerBank> selectList(BizPartnerCustomerBank bizPartnerCustomerBank);
   

   /**
    * 查询数量
    */
   int selectTotal(BizPartnerCustomerBank bizPartnerCustomerBank);
}
