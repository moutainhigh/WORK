package com.xlkfinance.bms.server.project.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.project.CusHisCardInfo;
import com.xlkfinance.bms.rpc.project.CusHisCardInfoService.Iface;
import com.xlkfinance.bms.server.project.mapper.CusHisCardInfoMapper;

/**
 *  @author： dulin <br>
 *  @time：2017-12-12 19:25:02 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 客户银行卡历史记录表<br>
 */
@Service("cusHisCardInfoServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.project.CusHisCardInfoService")
@SuppressWarnings({"rawtypes", "unchecked"})
public class CusHisCardInfoServiceImpl implements Iface {

    @Resource(name = "cusHisCardInfoMapper")
    private CusHisCardInfoMapper cusHisCardInfoMapper;

    /**
     *根据条件查询所有
     *@author:dulin
     *@time:2017-12-12 19:25:02
     */
    @Override
    public List<CusHisCardInfo> getAll(CusHisCardInfo cusHisCardInfo) throws ThriftServiceException, TException {
       return cusHisCardInfoMapper.getAll(cusHisCardInfo);
    }

    /**
     *根据id查询
     *@author:dulin
     *@time:2017-12-12 19:25:02
     */
    @Override
    public CusHisCardInfo getById(int pid) throws ThriftServiceException, TException {
       CusHisCardInfo cusHisCardInfo = cusHisCardInfoMapper.getById(pid);
       if (cusHisCardInfo==null) {
          return new CusHisCardInfo();
       }
       return cusHisCardInfo;
    }

    /**
     *插入一条数据
     *@author:dulin
     *@time:2017-12-12 19:25:02
     */
    @Override
    public int insert(CusHisCardInfo cusHisCardInfo) throws ThriftServiceException, TException {
       return cusHisCardInfoMapper.insert(cusHisCardInfo);
    }

    /**
     *根据id更新数据
     *@author:dulin
     *@time:2017-12-12 19:25:02
     */
    @Override
    public int update(CusHisCardInfo cusHisCardInfo) throws ThriftServiceException, TException {
       return cusHisCardInfoMapper.update(cusHisCardInfo);
    }


}
