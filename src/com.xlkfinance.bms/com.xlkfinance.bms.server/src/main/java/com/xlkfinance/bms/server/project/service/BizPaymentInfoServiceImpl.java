package com.xlkfinance.bms.server.project.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.project.BizPaymentInfo;
import com.xlkfinance.bms.rpc.project.BizPaymentInfoService.Iface;
import com.xlkfinance.bms.server.project.mapper.BizPaymentInfoMapper;

/**
 *  @author： baogang <br>
 *  @time：2017-12-12 17:31:43 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 正常还款信息表<br>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("bizPaymentInfoServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.project.BizPaymentInfoService")
public class BizPaymentInfoServiceImpl implements Iface {

	@Resource(name = "bizPaymentInfoMapper")
    private BizPaymentInfoMapper bizPaymentInfoMapper;

    /**
     *根据条件查询所有
     *@author:baogang
     *@time:2017-12-12 17:31:43
     */
    @Override
    public List<BizPaymentInfo> getAll(BizPaymentInfo bizPaymentInfo) throws ThriftServiceException, TException {
       return bizPaymentInfoMapper.getAll(bizPaymentInfo);
    }

    /**
     *根据id查询
     *@author:baogang
     *@time:2017-12-12 17:31:43
     */
    @Override
    public BizPaymentInfo getById(int pid) throws ThriftServiceException, TException {
       BizPaymentInfo bizPaymentInfo = bizPaymentInfoMapper.getById(pid);
       if (bizPaymentInfo==null) {
          return new BizPaymentInfo();
       }
       return bizPaymentInfo;
    }

    /**
     *插入一条数据
     *@author:baogang
     *@time:2017-12-12 17:31:43
     */
    @Override
    public int insert(BizPaymentInfo bizPaymentInfo) throws ThriftServiceException, TException {
       return bizPaymentInfoMapper.insert(bizPaymentInfo);
    }

    /**
     *根据id更新数据
     *@author:baogang
     *@time:2017-12-12 17:31:43
     */
    @Override
    public int update(BizPaymentInfo bizPaymentInfo) throws ThriftServiceException, TException {
       return bizPaymentInfoMapper.update(bizPaymentInfo);
    }


}
