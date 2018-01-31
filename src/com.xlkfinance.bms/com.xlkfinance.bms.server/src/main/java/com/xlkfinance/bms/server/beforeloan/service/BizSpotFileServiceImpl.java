package com.xlkfinance.bms.server.beforeloan.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.beforeloan.BizSpotFile;
import com.xlkfinance.bms.rpc.beforeloan.BizSpotFileService.Iface;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.server.beforeloan.mapper.BizSpotFileMapper;

/**
 *  @author： qqw <br>
 *  @time：2017-12-18 18:07:14 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 下户调查信息<br>
 */
@Service("bizSpotFileServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.beforeloan.BizSpotFileService")
public class BizSpotFileServiceImpl implements Iface {
    private Logger logger = LoggerFactory.getLogger(BizSpotFileServiceImpl.class);

    @Resource(name = "bizSpotFileMapper")
    private BizSpotFileMapper bizSpotFileMapper;

    /**
     *根据条件查询所有
     *@author:qqw
     *@time:2017-12-18 18:07:14
     */
    @Override
    public List<BizSpotFile> getAll(BizSpotFile bizSpotFile) throws ThriftServiceException, TException {
    	List<BizSpotFile> spotFiles = null;
    	spotFiles = bizSpotFileMapper.getAll(bizSpotFile);
    	
    	if(spotFiles == null || spotFiles.size() <=0) {
    		spotFiles = new ArrayList<BizSpotFile>();
    	}
    	return spotFiles;
    }

    /**
     *根据id查询
     *@author:qqw
     *@time:2017-12-18 18:07:14
     */
    @Override
    public BizSpotFile getById(int pid) throws ThriftServiceException, TException {
       BizSpotFile bizSpotFile = bizSpotFileMapper.getById(pid);
       if (bizSpotFile==null) {
          return new BizSpotFile();
       }
       return bizSpotFile;
    }

    /**
     *插入一条数据
     *@author:qqw
     *@time:2017-12-18 18:07:14
     */
    @Override
    public int insert(BizSpotFile bizSpotFile) throws ThriftServiceException, TException {
       return bizSpotFileMapper.insert(bizSpotFile);
    }

    /**
     *根据id更新数据
     *@author:qqw
     *@time:2017-12-18 18:07:14
     */
    @Override
    public int update(BizSpotFile bizSpotFile) throws ThriftServiceException, TException {
       return bizSpotFileMapper.update(bizSpotFile);
    }

	@Override
	public int getAllCount(BizSpotFile bizSpotFile) throws TException {
		return bizSpotFileMapper.getAllCount(bizSpotFile);
	}

	@Override
	public int deleteById(BizSpotFile bizSpotFile) throws TException {
		return bizSpotFileMapper.deleteById(bizSpotFile);
	}

	@Override
	public int deleteByIds(List<String> ids) throws TException {
		return bizSpotFileMapper.deleteByIds(ids);
	}


}
