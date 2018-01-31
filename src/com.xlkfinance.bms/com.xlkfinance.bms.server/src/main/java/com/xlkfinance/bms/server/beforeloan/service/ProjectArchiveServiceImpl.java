package com.xlkfinance.bms.server.beforeloan.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectArchive;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectArchiveFile;
import com.xlkfinance.bms.rpc.beforeloan.ProjectArchiveFileDTO;
import com.xlkfinance.bms.rpc.beforeloan.ProjectArchiveService.Iface;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectArchiveMapper;

/**
 *   
 * 
 * @Title: ProjectArchiveServiceImpl.java
 * @Package com.xlkfinance.bms.server.beforeloan.service
 * @Description: 项目归档资料处理
 * @author Ant.Peng  
 * @date 2015年2月5日 下午8:14:27
 * @version V1.0  
 */
@SuppressWarnings("unchecked")
@Service("projectArchiveService")
@ThriftService(service = "com.xlkfinance.bms.rpc.beforeloan.ProjectArchiveService")
public class ProjectArchiveServiceImpl implements Iface {
	private Logger logger = LoggerFactory.getLogger(ProjectArchiveServiceImpl.class);

	@SuppressWarnings("rawtypes")
	@Resource(name = "projectArchiveMapper")
	private ProjectArchiveMapper projectArchiveMapper;

	/**
	 * 
	 * @Description: 保存单一项目归档资料
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public int saveBizProjectArchive(BizProjectArchive bizProjectArchive) throws ThriftServiceException, TException {
		try {
			projectArchiveMapper.saveBizProjectArchive(bizProjectArchive);
			return bizProjectArchive.getPid();
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("保存上传项目归档资料失败:" + e.getMessage());
			}
			throw new ThriftServiceException(ExceptionCode.CREDIT_ADD, "保存上传项目归档资料失败！");
		}
	}

	/**
	 * 
	 * @Description: 更新单一项目资料归档信息，不包括文件内容
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public int updateBizProjectArchive(BizProjectArchive bizProjectArchive) throws ThriftServiceException, TException {
		try {
			return projectArchiveMapper.updateBizProjectArchive(bizProjectArchive);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_UPDATE, "更新单一项目资料归档信息，不包括文件内容失败！");
		}
	}

	/**
	 * 
	 * @Description: 编辑单一项目,初始化资料归档信息，不包括文件内容
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public BizProjectArchive obtainBizProjectArchiveByPID(int pid) throws ThriftServiceException, TException {
		try {
			BizProjectArchive bizProjectArchive = projectArchiveMapper.obtainBizProjectArchiveByPID(pid);
			return bizProjectArchive == null ? new BizProjectArchive() : bizProjectArchive;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_QUERY, "编辑单一项目,初始化资料归档信息，不包括文件内容失败！");
		}
	}

	/**
	 * 
	 * @Description: 根据projectId获得该项目所有资料归档信息
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public List<BizProjectArchive> obtainBizProjectArchiveByProjectId(int projectId) throws ThriftServiceException, TException {
		try {
			List<BizProjectArchive> list = projectArchiveMapper.obtainBizProjectArchiveByProjectId(projectId);
			return list == null ? new ArrayList<BizProjectArchive>() : list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_QUERY, "根据projectId获得该项目所有资料归档信息失败！");
		}
	}

	/**
	 * 
	 * @Description: 保存单一项目归档资料对应的文件
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public int saveBizProjectArchiveFile(BizProjectArchiveFile bizProjectArchiveFile) throws ThriftServiceException, TException {
		try {
			return projectArchiveMapper.saveBizProjectArchiveFile(bizProjectArchiveFile);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_ADD, "保存单一项目归档资料对应的文件失败！");
		}
	}

	/**
	 * 
	 * @Description: 获得单一项目归档资料对应的所有文件信息，表数据
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public List<ProjectArchiveFileDTO> obtainBizProjectArchiveFileByArchiveId(int archiveId) throws ThriftServiceException, TException {
		try {
			List<ProjectArchiveFileDTO> list = projectArchiveMapper.obtainBizProjectArchiveFileByArchiveId(archiveId);
			return list == null ? new ArrayList<ProjectArchiveFileDTO>() : list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_QUERY, "获得单一项目归档资料对应的所有文件信息，表数据失败！");
		}
	}

	/**
	 * 
	 * @Description: 根据PID删除单一项目归档资料BIZ_PROJECT_ARCHIVE表数据
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public int deleteBizProjectArchiveByPid(int pid) throws ThriftServiceException, TException {
		try {
			return projectArchiveMapper.deleteBizProjectArchiveByPid(pid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_DELETE, "根据PID删除单一项目归档资料BIZ_PROJECT_ARCHIVE表数据失败！");
		}
	}

	/**
	 * 
	 * @Description: 根据BIZ_PROJECT_ARCHIVE的PID删除BIZ_PROJECT_ARCHIVE_FILE表数据
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public int deleteBizProjectArchiveFileByArchiveId(int archiveId) throws ThriftServiceException, TException {
		try {
			return projectArchiveMapper.deleteBizProjectArchiveFileByArchiveId(archiveId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_DELETE, "根据BIZ_PROJECT_ARCHIVE的PID删除BIZ_PROJECT_ARCHIVE_FILE表数据失败！");
		}
	}

	/**
	 * 
	 * @Description: 根据fileId删除项目归档资料BIZ_PROJECT_ARCHIVE_FILE表数据
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public int deleteBizProjectArchiveFileByFileId(int fileId) throws ThriftServiceException, TException {
		try {
			return projectArchiveMapper.deleteBizProjectArchiveFileByFileId(fileId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_DELETE, "根据fileId删除项目归档资料BIZ_PROJECT_ARCHIVE_FILE表数据失败！");
		}
	}

	/**
	 * 
	 * @Description: 根据fileId获得BIZ_FILE信息，BIZ_PROJECT_ARCHIVE_FILE信息，初始化文件附件
	 * @return
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public ProjectArchiveFileDTO obtainProjectArchiveFileDTOByFileId(int fileId) throws ThriftServiceException, TException {
		try {
			ProjectArchiveFileDTO projectArchiveFileDTO = projectArchiveMapper.obtainProjectArchiveFileDTOByFileId(fileId);
			return projectArchiveFileDTO == null ? new ProjectArchiveFileDTO() : projectArchiveFileDTO;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_QUERY, "根据fileId获得BIZ_FILE信息，BIZ_PROJECT_ARCHIVE_FILE信息，初始化文件附件失败！");
		}
	}

	/**
	 * 
	 * @Description: 编辑单一项目资料归档信息的文件附件内容
	 * @param
	 * @return
	 * @author: pengchuntao
	 * @throws IOException
	 * @date: 2014年2月5日
	 */
	@Override
	public int updateProjectArchiveFile(BizProjectArchiveFile bizProjectArchiveFile) throws ThriftServiceException, TException {
		try {
			return projectArchiveMapper.updateProjectArchiveFile(bizProjectArchiveFile);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_UPDATE, "编辑单一项目资料归档信息的文件附件内容失败！");
		}
	}

}
