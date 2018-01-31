package com.xlkfinance.bms.server.project.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.project.CusHisCardInfo;

/**
 *  @author： dulin <br>
 *  @time：2017-12-12 19:25:02 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 客户银行卡历史记录表<br>
 */
@MapperScan("cusHisCardInfoMapper")
public interface CusHisCardInfoMapper<T, PK> extends BaseMapper<T, PK>{
    /**
     *根据条件查询所有
     *@author:dulin
     *@time:2017-12-12 19:25:02
     */
    public List<CusHisCardInfo> getAll(CusHisCardInfo cusHisCardInfo);

    /**
     *根据id查询
     *@author:dulin
     *@time:2017-12-12 19:25:02
     */
    public CusHisCardInfo getById(int pid);
    
    /**
	 * getHisCardInfoByProjectId:(获取该贷款单客户历史银行卡信息). <br/>
	 * @author dulin
	 * @param projectId
	 * @return
     */
    public CusHisCardInfo getHisCardInfoByProjectId(int projectId);

    /**
     *插入一条数据
     *@author:dulin
     *@time:2017-12-12 19:25:02
     */
    public int insert(CusHisCardInfo cusHisCardInfo);

    /**
     *根据id更新数据
     *@author:dulin
     *@time:2017-12-12 19:25:02
     */
    public int update(CusHisCardInfo cusHisCardInfo);

}
