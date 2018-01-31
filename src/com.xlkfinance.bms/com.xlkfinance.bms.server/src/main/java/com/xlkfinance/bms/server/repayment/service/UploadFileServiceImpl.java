/**
 * @Title: UploadFileServiceImpl.java
 * @Package com.xlkfinance.bms.server.repayment.service
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: achievo
 * @date: 2015年4月9日 下午4:05:24
 * @version V1.0
 */
package com.xlkfinance.bms.server.repayment.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.repayment.RegAdvapplyFileview;
import com.xlkfinance.bms.rpc.repayment.UploadFileService.Iface;
import com.xlkfinance.bms.rpc.repayment.UploadinstAdvapplyBaseDTO;
import com.xlkfinance.bms.server.afterloan.mapper.MisapprProcessMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectMapper;
import com.xlkfinance.bms.server.repayment.mapper.RepaymentMapper;


@SuppressWarnings("unchecked")
@Service("UploadFileServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.repayment.UploadFileService")
public class UploadFileServiceImpl   implements Iface{
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "projectMapper")
	private ProjectMapper projectMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "misapprProcessMapper")
	private MisapprProcessMapper misapprProcessMapper;
	
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "repaymentMapper")
	private RepaymentMapper repaymentMapper;

	/**
	 * @Description: 担保基本信息 插入BIZ_PROJECT_ASS_FILE表
	 * @param project
	 * @return int
	 * @author: gW
	 * @date: 2015年4月8日 上午10:51:34
	 */
	@Override
	public int uploadassBaseFile(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO) throws ThriftServiceException, TException {
		// 插入file表
		projectMapper.insertFileInfo(uploadinstAdvapplyBaseDTO);
		uploadinstAdvapplyBaseDTO.setFileId(uploadinstAdvapplyBaseDTO.getPId());
		int st = projectMapper.insertAssBaseFileInfo(uploadinstAdvapplyBaseDTO);
		return st;
	}

	/**
	 * @Description: 担保基本信息 查询BIZ_PROJECT_ASS_FILE表
	 * @param project
	 * @return List<RegAdvapplyFileview>
	 * @author: gW
	 * @date: 2015年4月8日 上午10:51:34
	 */
	@Override
	public List<RegAdvapplyFileview> queryAssBaseFile(int baseId) throws ThriftServiceException, TException {
		List<RegAdvapplyFileview>  list=projectMapper.queryAssBaseFile(baseId);
		return list;
	}
	/**
	  * @Description: 修改坏账信息
	  * @param uploadinstAdvapplyBaseDTO
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年8月4日 上午9:33:41
	  */
	@Override
	public int uploadBaddealFile(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO) throws ThriftServiceException, TException {
		misapprProcessMapper.insertFileInfo(uploadinstAdvapplyBaseDTO);
		uploadinstAdvapplyBaseDTO.setFileId(uploadinstAdvapplyBaseDTO.getPId());
		int st = misapprProcessMapper.insertBaddealFileInfo(uploadinstAdvapplyBaseDTO);
		return st;
	}

	/**
	  * @Description: 查询坏账上传文件
	  * @param badId
	  * @return  List<RegAdvapplyFileview>
	  * @author: JingYu.Dai
	  * @date: 2015年8月4日 上午9:44:44
	  */
	@Override
	public List<RegAdvapplyFileview> queryBaddealFile(int badId) throws ThriftServiceException, TException {
		List<RegAdvapplyFileview>  list=misapprProcessMapper.queryBaddealFile(badId);
		return list;
	}
    /**
	  * @Description: 更新提前还款上传信息
	  * @param uploadinstAdvapplyBaseDTO
	  * @return  int
	  * @author: JingYu.Dai
	  * @date: 2015年8月4日 上午9:44:44
	  */
	@Override
	public int updateAdvLoadFileInfo(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO) throws ThriftServiceException, TException {
		return repaymentMapper.updateAdvLoadFileInfo(uploadinstAdvapplyBaseDTO);
	}

	@Override
	public int updateACgLoadFileInfo(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO) throws ThriftServiceException, TException {
		return repaymentMapper.updateACgLoadFileInfo(uploadinstAdvapplyBaseDTO);
	}

	@Override
	public int updateFeedLoadFileInfo(UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO) throws ThriftServiceException, TException {
		return repaymentMapper.updateFeedLoadFileInfo(uploadinstAdvapplyBaseDTO);
	}

}
