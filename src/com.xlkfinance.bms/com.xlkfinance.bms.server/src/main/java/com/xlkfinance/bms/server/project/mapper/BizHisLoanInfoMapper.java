package com.xlkfinance.bms.server.project.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.project.BizHisLoanInfo;

/**
 *  @author： dulin <br>
 *  @time：2017-12-12 19:22:40 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 贷款申请信息历史记录表<br>
 */
@MapperScan("bizHisLoanInfoMapper")
public interface BizHisLoanInfoMapper<T, PK> extends BaseMapper<T, PK>{
    /**
     *根据条件查询所有
     *@author:dulin
     *@time:2017-12-12 19:22:40
     */
    public List<BizHisLoanInfo> getAll(BizHisLoanInfo bizHisLoanInfo);

    /**
     *根据id查询
     *@author:dulin
     *@time:2017-12-12 19:22:40
     */
    public BizHisLoanInfo getById(int pid);
    
    /**
	 * getHisLoanInfoByProjectId:(获取该贷款单客户贷款费用申请信息). <br/>
	 * @author dulin
	 * @param projectId
	 * @return
     */
    public BizHisLoanInfo getHisLoanInfoByProjectId(int projectId);

    /**
     *插入一条数据
     *@author:dulin
     *@time:2017-12-12 19:22:40
     */
    public int insert(BizHisLoanInfo bizHisLoanInfo);

    /**
     *根据id更新数据
     *@author:dulin
     *@time:2017-12-12 19:22:40
     */
    public int update(BizHisLoanInfo bizHisLoanInfo);

}
