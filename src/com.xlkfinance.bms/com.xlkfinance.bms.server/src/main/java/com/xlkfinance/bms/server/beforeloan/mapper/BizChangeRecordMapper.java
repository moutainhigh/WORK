package com.xlkfinance.bms.server.beforeloan.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.beforeloan.BizChangeRecord;

/**
 *  @author： baogang <br>
 *  @time：2017-01-05 11:20:22 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 修改记录信息<br>
 */
@MapperScan("bizChangeRecordMapper")
public interface BizChangeRecordMapper<T, PK> extends BaseMapper<T, PK>{
    /**
     *根据条件查询所有
     *@author:baogang
     *@time:2017-01-05 11:20:22
     */
    public List<BizChangeRecord> getAll(BizChangeRecord bizChangeRecord);

    /**
     *根据id查询
     *@author:baogang
     *@time:2017-01-05 11:20:22
     */
    public BizChangeRecord getById(int pid);

    /**
     *插入一条数据
     *@author:baogang
     *@time:2017-01-05 11:20:22
     */
    public int insert(BizChangeRecord bizChangeRecord);

    /**
     *根据id更新数据
     *@author:baogang
     *@time:2017-01-05 11:20:22
     */
    public int update(BizChangeRecord bizChangeRecord);

}
