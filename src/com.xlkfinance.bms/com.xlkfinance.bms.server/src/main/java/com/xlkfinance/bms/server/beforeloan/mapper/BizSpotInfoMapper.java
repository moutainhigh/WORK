package com.xlkfinance.bms.server.beforeloan.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.beforeloan.BizSpotInfo;

/**
 *  @author： qqw <br>
 *  @time：2017-12-16 14:54:16 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 下户调查信息<br>
 */
@MapperScan("bizSpotInfoMapper")
public interface BizSpotInfoMapper<T, PK> extends BaseMapper<T, PK>{
    /**
     *根据条件查询所有
     *@author:qqw
     *@time:2017-12-16 14:54:16
     */
    public List<BizSpotInfo> getAll(BizSpotInfo bizSpotInfo);

    /**
     *根据id查询
     *@author:qqw
     *@time:2017-12-16 14:54:16
     */
    public BizSpotInfo getById(int pid);

    /**
     *插入一条数据
     *@author:qqw
     *@time:2017-12-16 14:54:16
     */
    public int insert(BizSpotInfo bizSpotInfo);

    /**
     *根据id更新数据
     *@author:qqw
     *@time:2017-12-16 14:54:16
     */
    public int update(BizSpotInfo bizSpotInfo);

}
