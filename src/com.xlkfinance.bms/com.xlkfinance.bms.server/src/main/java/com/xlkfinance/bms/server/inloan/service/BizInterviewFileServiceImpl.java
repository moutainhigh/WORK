package com.xlkfinance.bms.server.inloan.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.inloan.BizInterviewFile;
import com.xlkfinance.bms.rpc.inloan.BizInterviewFileService.Iface;
import com.xlkfinance.bms.server.inloan.mapper.BizInterviewFileMapper;

/**
 *  @author： qqw <br>
 *  @time：2017-12-20 09:36:10 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 面签管理文件<br>
 */
@Service("bizInterviewFileServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.inloan.BizInterviewFileService")
public class BizInterviewFileServiceImpl implements Iface {
    private Logger logger = LoggerFactory.getLogger(BizInterviewFileServiceImpl.class);

    @Resource(name = "bizInterviewFileMapper")
    private BizInterviewFileMapper bizInterviewFileMapper;

    /**
     *根据条件查询所有
     *@author:qqw
     *@time:2017-12-20 09:36:10
     */
    @Override
    public List<BizInterviewFile> getAll(BizInterviewFile bizInterviewFile) throws ThriftServiceException, TException {
       return bizInterviewFileMapper.getAll(bizInterviewFile);
    }

    /**
     *根据id查询
     *@author:qqw
     *@time:2017-12-20 09:36:10
     */
    @Override
    public BizInterviewFile getById(int pid) throws ThriftServiceException, TException {
       BizInterviewFile bizInterviewFile = bizInterviewFileMapper.getById(pid);
       if (bizInterviewFile==null) {
          return new BizInterviewFile();
       }
       return bizInterviewFile;
    }

    /**
     *插入一条数据
     *@author:qqw
     *@time:2017-12-20 09:36:10
     */
    @Override
    public int insert(BizInterviewFile bizInterviewFile) throws ThriftServiceException, TException {
       return bizInterviewFileMapper.insert(bizInterviewFile);
    }

    /**
     *根据id更新数据
     *@author:qqw
     *@time:2017-12-20 09:36:10
     */
    @Override
    public int update(BizInterviewFile bizInterviewFile) throws ThriftServiceException, TException {
       return bizInterviewFileMapper.update(bizInterviewFile);
    }


}
