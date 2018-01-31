package com.xlkfinance.bms.server.project.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.project.CusEnterpriseInfo;

/**
 *  @author： dulin <br>
 *  @time：2017-12-12 17:49:25 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 客户企业信息表<br>
 */
@MapperScan("cusEnterpriseInfoMapper")
public interface CusEnterpriseInfoMapper<T, PK> extends BaseMapper<T, PK>{
    /**
     *根据条件查询所有
     *@author:dulin
     *@time:2017-12-12 17:49:25
     */
    public List<CusEnterpriseInfo> getAll(CusEnterpriseInfo cusEnterpriseInfo);

    /**
     *根据id查询
     *@author:dulin
     *@time:2017-12-12 17:49:25
     */
    public CusEnterpriseInfo getById(int pid);
    
    /**
	 * getEnterpriseInfoByProjectId:(获取该贷款单客户企业信息). <br/>
	 * @author dulin
	 * @param projectId
	 * @return
     */
    public CusEnterpriseInfo getEnterpriseInfoByProjectId(int projectId);

    /**
     *插入一条数据
     *@author:dulin
     *@time:2017-12-12 17:49:25
     */
    public int insert(CusEnterpriseInfo cusEnterpriseInfo);

    /**
     *根据id更新数据
     *@author:dulin
     *@time:2017-12-12 17:49:25
     */
    public int update(CusEnterpriseInfo cusEnterpriseInfo);

}
