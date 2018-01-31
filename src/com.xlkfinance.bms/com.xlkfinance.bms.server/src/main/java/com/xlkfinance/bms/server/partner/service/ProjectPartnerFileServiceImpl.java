/**
 * @Title: ProjectPartnerServiceImpl.java
 * @Package com.xlkfinance.bms.server.partner.service
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: Administrator
 * @date: 2016年3月18日 下午2:38:58
 * @version V1.0
 */
package com.xlkfinance.bms.server.partner.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.partner.CusCreditInfo;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerFile;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerFileService.Iface;
import com.xlkfinance.bms.server.partner.mapper.ProjectPartnerFileMapper;

/**
 * 项目合作机构附件业务类
 * @author chenzhuzhen
 * @date 2016年5月12日 上午11:06:46
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("projectPartnerFileServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.partner.ProjectPartnerFileService")
public class ProjectPartnerFileServiceImpl implements Iface {

	private Logger logger = LoggerFactory.getLogger(ProjectPartnerFileServiceImpl.class);

	@Resource(name = "projectPartnerFileMapper")
	private ProjectPartnerFileMapper projectPartnerFileMapper ;
 
	/**
	 * 添加项目合作机构附件
	 */
	@Override
	public int addProjectPartnerFile(ProjectPartnerFile projectPartnerFile)throws ThriftServiceException, TException {
		int pid = 0;
		try {
			pid = projectPartnerFileMapper.getSeqBizProjectPartnerFile();
			projectPartnerFile.setPid(pid);
			projectPartnerFileMapper.addProjectPartnerFile(projectPartnerFile);
		} catch (Exception e) {
			logger.error(">>>>>>addProjectPartnerFile error",e);
			throw new ThriftServiceException(ExceptionCode.SYS_EDIT_PWD, "新增合作机构附件失败！");
		}
		return pid;
	}

	/**
	 * 修改合作机构附件
	 */
	@Override
	public int updateProjectPartnerFile(ProjectPartnerFile projectPartnerFile) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			Timestamp time = new Timestamp(new Date().getTime());
			projectPartnerFile.setUpdateTime(time.toString());
			rows = projectPartnerFileMapper.updateProjectPartnerFile(projectPartnerFile);
		} catch (Exception e) {
			logger.error(">>>>>>updateProjectPartnerFile error",e);
			throw new ThriftServiceException(ExceptionCode.SYS_EDIT_PWD, "修改合作机构附件失败！");
		}
		return rows;
	}

	/**
	 * 查询合作机构附件列表
	 */
	@Override
	public List<ProjectPartnerFile> findAllProjectPartnerFile(ProjectPartnerFile projectPartnerFile)throws ThriftServiceException, TException {
		List<ProjectPartnerFile> resultList = projectPartnerFileMapper.findAllProjectPartnerFile(projectPartnerFile);
		if(resultList == null){
			resultList = new ArrayList<ProjectPartnerFile>();
		}
		return resultList;
	}

	/**
	 * 查询合作机构附件列表数量
	 */
	@Override
	public int findAllProjectPartnerFileCount(ProjectPartnerFile projectPartnerFile)throws ThriftServiceException, TException {
		return projectPartnerFileMapper.findAllProjectPartnerFileCount(projectPartnerFile);
	}
	
	
   /**
    *根据id查询
    *@author:czz
    *@time:2017-02-15 18:35:29
    */
   @Override
   public ProjectPartnerFile getById(int pid) throws ThriftServiceException, TException {
	   ProjectPartnerFile file = projectPartnerFileMapper.getById(pid);
      if (file==null) {
         return new ProjectPartnerFile();
      }
      return file;
   }

   
   /**
    * 清空项目机构所有附件上传第三方路径
    */
	@Override
	public int updateAllFileUploadEmpty(ProjectPartnerFile projectPartnerFile)
			throws ThriftServiceException, TException {
		int rows = 0;
		try {
			Timestamp time = new Timestamp(new Date().getTime());
			projectPartnerFile.setUpdateTime(time.toString());
			rows = projectPartnerFileMapper.updateAllFileUploadEmpty(projectPartnerFile);
		} catch (Exception e) {
			logger.error(">>>>>>updateAllFileUploadEmpty error",e);
			throw new ThriftServiceException(ExceptionCode.SYS_EDIT_PWD, "清空项目机构所有附件上传第三方路径失败！");
		}
		return rows;
	}

	 
	
}
