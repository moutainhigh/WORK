package com.xlkfinance.bms.server.project.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.project.CusCredentials;

/**
 *  @author： dulin <br>
 *  @time：2017-12-12 19:18:43 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 客户资质信息表<br>
 */
@MapperScan("cusCredentialsMapper")
public interface CusCredentialsMapper<T, PK> extends BaseMapper<T, PK>{
    /**
     *根据条件查询所有
     *@author:dulin
     *@time:2017-12-12 19:18:43
     */
    public List<CusCredentials> getAll(CusCredentials cusCredentials);

    /**
     *根据id查询
     *@author:dulin
     *@time:2017-12-12 19:18:43
     */
    public CusCredentials getById(int pid);
    
    /**
	 * getCredentialsByProjectId:(获取该贷款单客户资质信息). <br/>
	 * @author dulin
	 * @param projectId
	 * @return
     */
    public CusCredentials getCredentialsByProjectId(int projectId);

    /**
     *插入一条数据
     *@author:dulin
     *@time:2017-12-12 19:18:43
     */
    public int insert(CusCredentials cusCredentials);

    /**
     *根据id更新数据
     *@author:dulin
     *@time:2017-12-12 19:18:43
     */
    public int update(CusCredentials cusCredentials);

}
