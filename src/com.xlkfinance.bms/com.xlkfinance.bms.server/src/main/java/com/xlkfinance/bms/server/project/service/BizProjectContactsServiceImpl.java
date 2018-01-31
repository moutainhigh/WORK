package com.xlkfinance.bms.server.project.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.project.BizProjectContacts;
import com.xlkfinance.bms.rpc.project.BizProjectContactsService.Iface;
import com.xlkfinance.bms.server.project.mapper.BizProjectContactsMapper;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;

/**
 *  @author： dongbiao <br>
 *  @time：2018-01-03 14:58:47 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 项目联系人信息<br>
 */
@Service("bizProjectContactsServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.project.BizProjectContactsService")
public class BizProjectContactsServiceImpl implements Iface {
    private Logger logger = LoggerFactory.getLogger(BizProjectContactsServiceImpl.class);

    @Resource(name = "bizProjectContactsMapper")
    private BizProjectContactsMapper bizProjectContactsMapper;

    /**
     *根据条件查询所有
     *@author:dongbiao
     *@time:2018-01-03 14:58:47
     */
    @Override
    public List<BizProjectContacts> getAll(BizProjectContacts bizProjectContacts) throws ThriftServiceException, TException {
       return bizProjectContactsMapper.getAll(bizProjectContacts);
    }

    /**
     *根据id查询
     *@author:dongbiao
     *@time:2018-01-03 14:58:47
     */
    @Override
    public BizProjectContacts getById(int pid) throws ThriftServiceException, TException {
       BizProjectContacts bizProjectContacts = bizProjectContactsMapper.getById(pid);
       if (bizProjectContacts==null) {
          return new BizProjectContacts();
       }
       return bizProjectContacts;
    }

    /**
     *插入一条数据
     *@author:dongbiao
     *@time:2018-01-03 14:58:47
     */
    @Override
    public int insert(BizProjectContacts bizProjectContacts) throws ThriftServiceException, TException {
       
    	
    	int result = 0;
	    try {
		    result = bizProjectContactsMapper.insert(bizProjectContacts);
		    if (result >= 1) {
			    result = bizProjectContacts.getPid();
			 } else {
				 // 抛出异常处理
				 throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "新增失败,请重新操作!");
			 }
		 } catch (Exception e) {
			 logger.error("新增物业信息:" + ExceptionUtil.getExceptionMessage(e));
			 throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "新增失败,请重新操作!");
		 }
       return result;
 
    }

    /**
     *根据id更新数据
     *@author:dongbiao
     *@time:2018-01-03 14:58:47
     */
    @Override
    public int update(BizProjectContacts bizProjectContacts) throws ThriftServiceException, TException {
       return bizProjectContactsMapper.update(bizProjectContacts);
    }

    /**
     *根据项目id查找项目联系人信息
     *@author:dongbiao
     *@time:2018-01-03 14:58:47
     */
	@Override
	public List<BizProjectContacts> getContactsByProjectId(int projectId) throws TException {
		// TODO Auto-generated method stub
	  return bizProjectContactsMapper.getContactsByProjectId(projectId);
	}

	  /**
     *批量删除联系人信息
     *@author:dongbiao
     *@time:2018-01-03 14:58:47
     */
	@Override
	public int deleteProjectContact(List<Integer> contactsId) throws TException {
		// TODO Auto-generated method stub
		return bizProjectContactsMapper.deleteByIds(contactsId);

	}

}
