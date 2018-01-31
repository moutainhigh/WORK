package com.xlkfinance.bms.server.project.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.project.CusCardInfo;
import com.xlkfinance.bms.rpc.project.CusCardInfoService.Iface;
import com.xlkfinance.bms.server.project.mapper.CusCardInfoMapper;

/**
 *  @author： dulin <br>
 *  @time：2017-12-12 19:20:55 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 客户银行卡信息表<br>
 */
@Service("cusCardInfoServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.project.CusCardInfoService")
@SuppressWarnings({"rawtypes","unchecked"})
public class CusCardInfoServiceImpl implements Iface {

    
	@Resource(name = "cusCardInfoMapper")
    private CusCardInfoMapper cusCardInfoMapper;

    /**
     *根据条件查询所有
     *@author:dulin
     *@time:2017-12-12 19:20:55
     */
	@Override
    public List<CusCardInfo> getAll(CusCardInfo cusCardInfo) throws ThriftServiceException, TException {
       return cusCardInfoMapper.getAll(cusCardInfo);
    }

    /**
     *根据id查询
     *@author:dulin
     *@time:2017-12-12 19:20:55
     */
    @Override
    public CusCardInfo getById(int pid) throws ThriftServiceException, TException {
       CusCardInfo cusCardInfo = cusCardInfoMapper.getById(pid);
       if (cusCardInfo==null) {
          return new CusCardInfo();
       }
       return cusCardInfo;
    }

    /**
     *插入一条数据
     *@author:dulin
     *@time:2017-12-12 19:20:55
     */
    @Override
    public int insert(CusCardInfo cusCardInfo) throws ThriftServiceException, TException {
       return cusCardInfoMapper.insert(cusCardInfo);
    }

    /**
     *根据id更新数据
     *@author:dulin
     *@time:2017-12-12 19:20:55
     */
    @Override
    public int update(CusCardInfo cusCardInfo) throws ThriftServiceException, TException {
       return cusCardInfoMapper.update(cusCardInfo);
    }
}
