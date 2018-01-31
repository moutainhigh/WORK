package com.xlkfinance.bms.server.project.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.project.BizProjectOverdue;
import com.xlkfinance.bms.rpc.project.BizProjectOverdueService.Iface;
import com.xlkfinance.bms.server.project.mapper.BizProjectOverdueMapper;

/**
 *  @author： baogang <br>
 *  @time：2017-12-12 15:40:07 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 逾期信息表<br>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("bizProjectOverdueServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.project.BizProjectOverdueService")
public class BizProjectOverdueServiceImpl implements Iface {
	
	@Resource(name = "bizProjectOverdueMapper")
    private BizProjectOverdueMapper bizProjectOverdueMapper;

    /**
     *根据条件查询所有
     *@author:baogang
     *@time:2017-12-12 15:40:07
     */
	@Override
    public List<BizProjectOverdue> getAll(BizProjectOverdue bizProjectOverdue) throws ThriftServiceException, TException {
       return bizProjectOverdueMapper.getAll(bizProjectOverdue);
    }

    /**
     *根据id查询
     *@author:baogang
     *@time:2017-12-12 15:40:07
     */
    @Override
    public BizProjectOverdue getById(int pid) throws ThriftServiceException, TException {
       BizProjectOverdue bizProjectOverdue = bizProjectOverdueMapper.getById(pid);
       if (bizProjectOverdue==null) {
          return new BizProjectOverdue();
       }
       return bizProjectOverdue;
    }

    /**
     *插入一条数据
     *@author:baogang
     *@time:2017-12-12 15:40:07
     */
    @Override
    public int insert(BizProjectOverdue bizProjectOverdue) throws ThriftServiceException, TException {
       return bizProjectOverdueMapper.insert(bizProjectOverdue);
    }

    /**
     *根据id更新数据
     *@author:baogang
     *@time:2017-12-12 15:40:07
     */
    @Override
    public int update(BizProjectOverdue bizProjectOverdue) throws ThriftServiceException, TException {
       return bizProjectOverdueMapper.update(bizProjectOverdue);
    }

}
