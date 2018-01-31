package com.xlkfinance.bms.server.project.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.project.BizHisLoanInfo;
import com.xlkfinance.bms.rpc.project.BizHisLoanInfoService.Iface;
import com.xlkfinance.bms.server.project.mapper.BizHisLoanInfoMapper;

/**
 *  @author： dulin <br>
 *  @time：2017-12-12 19:22:40 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 贷款申请信息历史记录表<br>
 */
@Service("bizHisLoanInfoServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.project.BizHisLoanInfoService")
@SuppressWarnings({"rawtypes", "unchecked"})
public class BizHisLoanInfoServiceImpl implements Iface {
   
	@Resource(name = "bizHisLoanInfoMapper")
    private BizHisLoanInfoMapper bizHisLoanInfoMapper;

    /**
     *根据条件查询所有
     *@author:dulin
     *@time:2017-12-12 19:22:40
     */
	@Override
    public List<BizHisLoanInfo> getAll(BizHisLoanInfo bizHisLoanInfo) throws ThriftServiceException, TException {
       return bizHisLoanInfoMapper.getAll(bizHisLoanInfo);
    }

    /**
     *根据id查询
     *@author:dulin
     *@time:2017-12-12 19:22:40
     */
    @Override
    public BizHisLoanInfo getById(int pid) throws ThriftServiceException, TException {
       BizHisLoanInfo bizHisLoanInfo = bizHisLoanInfoMapper.getById(pid);
       if (bizHisLoanInfo==null) {
          return new BizHisLoanInfo();
       }
       return bizHisLoanInfo;
    }

    /**
     *插入一条数据
     *@author:dulin
     *@time:2017-12-12 19:22:40
     */
    @Override
    public int insert(BizHisLoanInfo bizHisLoanInfo) throws ThriftServiceException, TException {
       return bizHisLoanInfoMapper.insert(bizHisLoanInfo);
    }

    /**
     *根据id更新数据
     *@author:dulin
     *@time:2017-12-12 19:22:40
     */
    @Override
    public int update(BizHisLoanInfo bizHisLoanInfo) throws ThriftServiceException, TException {
       return bizHisLoanInfoMapper.update(bizHisLoanInfo);
    }


}
