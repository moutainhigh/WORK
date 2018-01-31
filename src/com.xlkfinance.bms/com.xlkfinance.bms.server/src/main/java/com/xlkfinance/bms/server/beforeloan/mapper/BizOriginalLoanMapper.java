package com.xlkfinance.bms.server.beforeloan.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.beforeloan.BizOriginalLoan;

/**
 *  @author： baogang <br>
 *  @time：2017-02-06 11:18:33 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 原贷款信息<br>
 */
@MapperScan("bizOriginalLoanMapper")
public interface BizOriginalLoanMapper<T, PK> extends BaseMapper<T, PK>{
    /**
     *根据条件查询所有
     *@author:baogang
     *@time:2017-02-06 11:18:33
     */
    public List<BizOriginalLoan> getAll(BizOriginalLoan bizOriginalLoan);

    /**
     *根据id查询
     *@author:baogang
     *@time:2017-02-06 11:18:33
     */
    public BizOriginalLoan getById(int pid);

    /**
     *插入一条数据
     *@author:baogang
     *@time:2017-02-06 11:18:33
     */
    public int insert(BizOriginalLoan bizOriginalLoan);

    /**
     *根据id更新数据
     *@author:baogang
     *@time:2017-02-06 11:18:33
     */
    public int update(BizOriginalLoan bizOriginalLoan);

    /**
     * 通过查询条件查询原贷款信息列表
     * @param bizOriginalLoan
     * @return
     */
    public List<BizOriginalLoan> getAllByCondition(BizOriginalLoan bizOriginalLoan);
    
    /**
     * 批量删除原贷款信息
     * @param originalLoanIds
     * @return
     */
    public int delOriginalLoan(List<Integer> originalLoanIds);
}
