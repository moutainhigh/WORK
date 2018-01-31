/**
 * @Title: DataServiceImpl.java
 * @Package com.xlkfinance.bms.server.beforeloan.service
 * @Description: 资料上传模块
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: xuweihao
 * @date: 2015年3月9日 下午3:26:43
 * @version V1.0
 */
package com.xlkfinance.bms.server.beforeloan.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.beforeloan.DataInfo;
import com.xlkfinance.bms.rpc.beforeloan.DataUploadService.Iface;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.server.beforeloan.mapper.DataUploadMapper;


@SuppressWarnings("unchecked")
@Service("DataUploadServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.beforeloan.DataUploadService")
public class DataUploadServiceImpl implements Iface {

	private Logger logger = LoggerFactory.getLogger(DataUploadServiceImpl.class);
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "dataUploadMapper")
	private DataUploadMapper dataUploadMapper;
	/**
	 * 查询data资料
	 */
	@Override
	public List<DataInfo> dataList(DataInfo dataInfo)
			throws ThriftServiceException, TException {
		List<DataInfo> list = new ArrayList<DataInfo>();
		try{
			list = dataUploadMapper.dataList(dataInfo);
		}catch(Exception e){
				logger.error("查询data资料:" , e);
		}
		return list;
	}
    /**
     * 查数据总记录数
     */
	@Override
	public int getDataTotal(DataInfo dataInfo) throws ThriftServiceException,
			TException {
		int count = 0;
		try{
			count = dataUploadMapper.getDataTotal(dataInfo);
		}catch(Exception e){
				logger.error("查询data资料total:" , e);
		}
		return count;
	}
	/**
	 * 保存data资料
	 */
	@Override
	public int saveData(DataInfo dataInfo) throws ThriftServiceException,
			TException {
		int count = 0;
		try{
			count = dataUploadMapper.saveData(dataInfo);
		}catch(Exception e){
				logger.error("保存data资料:" , e);
		}
		return count;
	}
    /**
     * 删除数据资料
     */
	@Override
	public boolean deleteData(String pidArray) throws ThriftServiceException, TException {
		boolean del = true;
		String [] pids = pidArray.split(",");
		for (String string : pids) {
			int pid = Integer.parseInt(string);
			del = dataUploadMapper.delData(pid);
		}
		return del;
	}
	/**
	 * 编辑数据
	 */
	@Override
	public int editData(DataInfo dataInfo) throws ThriftServiceException,
			TException {
		int count = 0;
		try{
			count = dataUploadMapper.editData(dataInfo);
		}catch(Exception e){
				logger.error("保存data资料:" , e);
		}
		return count;
	}
    /**
     * 查找客户类型
     */
	@Override
	public int findUserType(int projectId) throws ThriftServiceException,
			TException {
		int userType = 0;
		try{
			userType = dataUploadMapper.findUserType(projectId);
		}catch(Exception e){
				logger.error("保存data资料:" , e);
		}
		return userType;
	}
	
	/**
	 * 批量保存data资料
	 */
	@Override
	public int saveDataList(List<DataInfo> dataInfoList)
			throws ThriftServiceException, TException {
		int count = 0;
		try{
			if(dataInfoList != null && dataInfoList.size()>0){
				for(DataInfo dataInfo : dataInfoList){
					if(dataInfo.getDataId() >0){
						count += dataUploadMapper.editData(dataInfo);
					}else{
						count += dataUploadMapper.saveData(dataInfo);
					}
				}
			}
		}catch(Exception e){
				logger.error("批量保存data资料:" , e);
		}
		return count;
	}
	/**
	 * 根据项目类型查询上传资料列表
	 */
	@Override
	public List<DataInfo> dataListByType(DataInfo dataInfo)
			throws ThriftServiceException, TException {
		// TODO Auto-generated method stub
		return dataUploadMapper.dataListByType(dataInfo);
	}
	/**
	 * 根据项目类型查询上传资料总数
	 */
	@Override
	public int getDataTotalByType(DataInfo dataInfo)
			throws ThriftServiceException, TException {
		// TODO Auto-generated method stub
		return dataUploadMapper.getDataTotalByType(dataInfo);
	}
	
}
