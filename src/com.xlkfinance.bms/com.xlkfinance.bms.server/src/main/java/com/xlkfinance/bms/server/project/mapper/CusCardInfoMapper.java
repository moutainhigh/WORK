package com.xlkfinance.bms.server.project.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.project.CusCardInfo;

/**
 *  @author： dulin <br>
 *  @time：2017-12-12 19:20:55 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 客户银行卡信息表<br>
 */
@MapperScan("cusCardInfoMapper")
public interface CusCardInfoMapper<T, PK> extends BaseMapper<T, PK>{
    /**
     *根据条件查询所有
     *@author:dulin
     *@time:2017-12-12 19:20:55
     */
    public List<CusCardInfo> getAll(CusCardInfo cusCardInfo);

    /**
     *根据id查询
     *@author:dulin
     *@time:2017-12-12 19:20:55
     */
    public CusCardInfo getById(int pid);
    
    /**
	 * getCardInfoByProjectId:(获取该贷款单客户银行卡信息). <br/>
	 * @author dulin
	 * @param projectId
	 * @return
     */
    public CusCardInfo getCardInfoByProjectId(int projectId);

    /**
     *插入一条数据
     *@author:dulin
     *@time:2017-12-12 19:20:55
     */
    public int insert(CusCardInfo cusCardInfo);

    /**
     *根据id更新数据
     *@author:dulin
     *@time:2017-12-12 19:20:55
     */
    public int update(CusCardInfo cusCardInfo);

}
