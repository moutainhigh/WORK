package com.xlkfinance.bms.server.project.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.project.CusCredentials;
import com.xlkfinance.bms.rpc.project.CusCredentialsService.Iface;
import com.xlkfinance.bms.server.project.mapper.CusCredentialsMapper;

/**
 *  @author： dulin <br>
 *  @time：2017-12-12 19:18:43 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 客户资质信息表<br>
 */
@Service("cusCredentialsServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.project.CusCredentialsService")
@SuppressWarnings({"rawtypes","unchecked"})
public class CusCredentialsServiceImpl implements Iface {

    @Resource(name = "cusCredentialsMapper")
    private CusCredentialsMapper cusCredentialsMapper;

    /**
     *根据条件查询所有
     *@author:dulin
     *@time:2017-12-12 19:18:43
     */
    @Override
    public List<CusCredentials> getAll(CusCredentials cusCredentials) throws ThriftServiceException, TException {
       return cusCredentialsMapper.getAll(cusCredentials);
    }

    /**
     *根据id查询
     *@author:dulin
     *@time:2017-12-12 19:18:43
     */
    @Override
    public CusCredentials getById(int pid) throws ThriftServiceException, TException {
       CusCredentials cusCredentials = cusCredentialsMapper.getById(pid);
       if (cusCredentials==null) {
          return new CusCredentials();
       }
       return cusCredentials;
    }

    /**
     *插入一条数据
     *@author:dulin
     *@time:2017-12-12 19:18:43
     */
    @Override
    public int insert(CusCredentials cusCredentials) throws ThriftServiceException, TException {
       return cusCredentialsMapper.insert(cusCredentials);
    }

    /**
     *根据id更新数据
     *@author:dulin
     *@time:2017-12-12 19:18:43
     */
    @Override
    public int update(CusCredentials cusCredentials) throws ThriftServiceException, TException {
       return cusCredentialsMapper.update(cusCredentials);
    }


}
