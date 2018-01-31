package com.xlkfinance.bms.server.project.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.project.BizProjectOverdue;

/**
 *  @author： baogang <br>
 *  @time：2017-12-12 15:40:07 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 逾期信息表<br>
 */
@MapperScan("bizProjectOverdueMapper")
public interface BizProjectOverdueMapper<T, PK> extends BaseMapper<T, PK>{
    /**
     *根据条件查询所有
     *@author:baogang
     *@time:2017-12-12 15:40:07
     */
    public List<BizProjectOverdue> getAll(BizProjectOverdue bizProjectOverdue);

    /**
     *根据id查询
     *@author:baogang
     *@time:2017-12-12 15:40:07
     */
    public BizProjectOverdue getById(int pid);

    /**
     *插入一条数据
     *@author:baogang
     *@time:2017-12-12 15:40:07
     */
    public int insert(BizProjectOverdue bizProjectOverdue);

    /**
     *根据id更新数据
     *@author:baogang
     *@time:2017-12-12 15:40:07
     */
    public int update(BizProjectOverdue bizProjectOverdue);

    /**
     * 
	 * updateOverdues:批量更新逾期信息. <br/>
	 * @author baogang
	 * @param list
	 *
     */
    public void updateOverdues(List<BizProjectOverdue> list);
    
    /**
     * 
	 * insertOverdues:批量新增逾期信息. <br/>
	 * @author baogang
	 * @param list
	 *
     */
    public void insertOverdues(List<BizProjectOverdue> list);
    
}
