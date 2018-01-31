package com.xlkfinance.bms.server.beforeloan.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.beforeloan.BizMortgageEvaluation;

/**
 *  @author： qqw <br>
 *  @time：2017-12-16 09:25:32 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 抵押物评估信息<br>
 */
@MapperScan("bizMortgageEvaluationMapper")
public interface BizMortgageEvaluationMapper<T, PK> extends BaseMapper<T, PK>{
    /**
     *根据条件查询所有
     *@author:qqw
     *@time:2017-12-16 09:25:32
     */
    public List<BizMortgageEvaluation> getAll(BizMortgageEvaluation bizMortgageEvaluation);

    /**
     *根据id查询
     *@author:qqw
     *@time:2017-12-16 09:25:32
     */
    public BizMortgageEvaluation getById(int pid);

    /**
     *插入一条数据
     *@author:qqw
     *@time:2017-12-16 09:25:32
     */
    public int insert(BizMortgageEvaluation bizMortgageEvaluation);

    /**
     *根据id更新数据
     *@author:qqw
     *@time:2017-12-16 09:25:32
     */
    public int update(BizMortgageEvaluation bizMortgageEvaluation);

}
