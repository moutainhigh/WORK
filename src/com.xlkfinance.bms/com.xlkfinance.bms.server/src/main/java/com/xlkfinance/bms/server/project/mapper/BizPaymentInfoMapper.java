package com.xlkfinance.bms.server.project.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.project.BizPaymentInfo;

/** 正常还款信息表
 *  @author： baogang <br>
 *  @time：2017-12-12 17:31:43 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 正常还款信息表<br>
 */
@MapperScan("bizPaymentInfoMapper")
public interface BizPaymentInfoMapper<T, PK> extends BaseMapper<T, PK>{
    /**
     *根据条件查询所有
     *@author:baogang
     *@time:2017-12-12 17:31:43
     */
    public List<BizPaymentInfo> getAll(BizPaymentInfo bizPaymentInfo);

    /**
     *根据id查询
     *@author:baogang
     *@time:2017-12-12 17:31:43
     */
    public BizPaymentInfo getById(int pid);

    /**
     *插入一条数据
     *@author:baogang
     *@time:2017-12-12 17:31:43
     */
    public int insert(BizPaymentInfo bizPaymentInfo);

    /**
     *根据id更新数据
     *@author:baogang
     *@time:2017-12-12 17:31:43
     */
    public int update(BizPaymentInfo bizPaymentInfo);

    /**
     * 
	 * updatePayments:批量修改正常还款信息表. <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * @author baogang
	 * @param list
	 * @return
	 *
     */
    public int updatePayments(List<BizPaymentInfo> list);
    
    /**
     * 
	 * insertPayments:批量新增正常还款信息. <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * @author baogang
	 * @param list
	 * @return
	 *
     */
    public int insertPayments(List<BizPaymentInfo> list);
    
}
