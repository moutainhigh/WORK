/**
 * @Title: TemplateFileServiceImpl.java
 * @Package com.xlkfinance.bms.server.customer.service
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: xuweihao
 * @date: 2015年4月16日 上午10:43:04
 * @version V1.0
 */
package com.xlkfinance.bms.server.system.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.TemplateFile;
import com.xlkfinance.bms.rpc.system.TemplateFileCount;
import com.xlkfinance.bms.rpc.system.TemplateFileService.Iface;
import com.xlkfinance.bms.server.system.mapper.TemplateFileMapper;

@SuppressWarnings({"unchecked","rawtypes"})
@Service("templateFileServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.system.TemplateFileService")
public class TemplateFileServiceImpl implements Iface {


	@Resource(name = "templateFileMapper")
	private TemplateFileMapper templateFileMapper;
	
	@Override
	public List<TemplateFile> listTemplateFile(TemplateFileCount templateFileCount) throws ThriftServiceException,
			TException {
		try {
			List<TemplateFile> list = templateFileMapper.listTemplateFile(templateFileCount);
			return list==null?new ArrayList<TemplateFile>():list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_ADD,"模板列表失败！");
		}
	}
	

	@Override
	public int listTemplateFileCount(TemplateFileCount templateFileCount)
			throws ThriftServiceException, TException {
		return templateFileMapper.listTemplateFileCount(templateFileCount);
	}


	@Override
	public int saveTemplateFile(TemplateFile templateFile)
			throws ThriftServiceException, TException {
		int count = 0;
		try {
			count = templateFileMapper.saveTemplateFile(templateFile);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_ADD,"模板保存失败！");
		}
	}

	@Override
	public int updateTemplateFile(TemplateFile templateFile)
			throws ThriftServiceException, TException {
		int count = 0;
		try {
			count = templateFileMapper.updateTemplateFile(templateFile);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_ADD,"模板修改失败！");
		}
	}

	@Override
	public int delTemplateFile(int pid)
			throws ThriftServiceException, TException {
		int count = 0;
		try {
			count = templateFileMapper.delTemplateFile(pid);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_ADD,"模板删除失败！");
		}
	}

	@Override
	public TemplateFile getTemplateFile(String fileLookupVal)
			throws ThriftServiceException, TException {
		TemplateFile templateFile = templateFileMapper.getTemplateFile(fileLookupVal);
		return templateFile==null?(new TemplateFile()):templateFile;
	}

}
