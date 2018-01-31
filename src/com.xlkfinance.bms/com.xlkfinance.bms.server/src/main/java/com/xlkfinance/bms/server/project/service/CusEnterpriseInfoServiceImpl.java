package com.xlkfinance.bms.server.project.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.project.CusEnterpriseInfo;
import com.xlkfinance.bms.rpc.project.CusEnterpriseInfoService.Iface;
import com.xlkfinance.bms.server.project.mapper.CusEnterpriseInfoMapper;

/**
 *  @author： dulin <br>
 *  @time：2017-12-12 17:48:11 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 客户企业信息表<br>
 */
@Service("cusEnterpriseInfoServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.project.CusEnterpriseInfoService")
public class CusEnterpriseInfoServiceImpl implements Iface {

    @Resource(name = "cusEnterpriseInfoMapper")
    private CusEnterpriseInfoMapper<?, ?> cusEnterpriseInfoMapper;

    /**
     *根据条件查询所有
     *@author:dulin
     *@time:2017-12-12 17:48:11
     */
    @Override
    public List<CusEnterpriseInfo> getAll(CusEnterpriseInfo cusEnterpriseInfo) throws ThriftServiceException, TException {
       return cusEnterpriseInfoMapper.getAll(cusEnterpriseInfo);
    }

    /**
     *根据id查询
     *@author:dulin
     *@time:2017-12-12 17:48:11
     */
    @Override
    public CusEnterpriseInfo getById(int pid) throws ThriftServiceException, TException {
       CusEnterpriseInfo cusEnterpriseInfo = cusEnterpriseInfoMapper.getById(pid);
       if (cusEnterpriseInfo==null) {
          return new CusEnterpriseInfo();
       }
       return cusEnterpriseInfo;
    }

    /**
     *插入一条数据
     *@author:dulin
     *@time:2017-12-12 17:48:11
     */
    @Override
    public int insert(CusEnterpriseInfo cusEnterpriseInfo) throws ThriftServiceException, TException {
       return cusEnterpriseInfoMapper.insert(cusEnterpriseInfo);
    }

    /**
     *根据id更新数据
     *@author:dulin
     *@time:2017-12-12 17:48:11
     */
    @Override
    public int update(CusEnterpriseInfo cusEnterpriseInfo) throws ThriftServiceException, TException {
       return cusEnterpriseInfoMapper.update(cusEnterpriseInfo);
    }


}
