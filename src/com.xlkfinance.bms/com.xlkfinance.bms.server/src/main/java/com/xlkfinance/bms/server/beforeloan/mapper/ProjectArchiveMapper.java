package com.xlkfinance.bms.server.beforeloan.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectArchive;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectArchiveFile;
import com.xlkfinance.bms.rpc.beforeloan.ProjectArchiveFileDTO;

/**   
* @Title: ProjectArchiveMapper.java 
* @Package com.xlkfinance.bms.server.beforeloan.mapper 
* @Description:项目归档资料
* @author Ant.Peng   
* @date 2015年2月5日 下午8:31:11 
* @version V1.0   
*/ 
@MapperScan("projectArchiveMapper")
public interface ProjectArchiveMapper<T, PK> extends BaseMapper<T, PK> {
	//保存项目归档资料
	public int saveBizProjectArchive(BizProjectArchive bizProjectArchive);
	public int saveBizProjectArchiveFile(BizProjectArchiveFile bizProjectArchiveFile);
	public List<ProjectArchiveFileDTO> obtainBizProjectArchiveFileByArchiveId(int archiveId);
	public List<BizProjectArchive> obtainBizProjectArchiveByProjectId(int projectId);
	public int deleteBizProjectArchiveByPid(int pid);
	public int deleteBizProjectArchiveFileByArchiveId(int archiveId);
	public BizProjectArchive obtainBizProjectArchiveByPID(int pid);
	//修改项目归档
	public int updateBizProjectArchive(BizProjectArchive bizProjectArchive);
	public int deleteBizProjectArchiveFileByFileId(int fileId);
	public ProjectArchiveFileDTO obtainProjectArchiveFileDTOByFileId(int fileId);
	//修改项目归档文件
	public int updateProjectArchiveFile(BizProjectArchiveFile bizProjectArchiveFile);
}
