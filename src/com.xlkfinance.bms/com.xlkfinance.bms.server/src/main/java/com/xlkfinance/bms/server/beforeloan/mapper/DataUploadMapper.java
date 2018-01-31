/**
 * @Title: DataMapper.java
 * @Package com.xlkfinance.bms.server.beforeloan.mapper
 * @Description: 资料上传mapper
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: xuweihao
 * @date: 2015年3月9日 下午3:28:29
 * @version V1.0
 */
package com.xlkfinance.bms.server.beforeloan.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.beforeloan.DataInfo;

@MapperScan("dataUploadMapper")
public interface DataUploadMapper<T, PK> extends BaseMapper<T, PK>{
	//查询数据列表
	public List<DataInfo> dataList(DataInfo dataInfo);
	//查总记录数
	public int getDataTotal(DataInfo dataInfo);
	//保持资料
	public int saveData(DataInfo dataInfo);
	//删除资料
	public boolean delData(int dataId);
	//编辑资料
	public int editData(DataInfo dataInfo);
	//查询客户类型
	public int findUserType(int projectId);
	
	/**
	 * 查询项目相关文件列表
	  * @Description: TODO
	  * @param projectId
	  * @return
	  * @author: andrew
	  * @date: 2016年2月27日 上午10:51:25
	 */
	public List<DataInfo> findProjectFiles(int projectId);
	
	/**
	 * 根据项目类型以及项目ID查询上传资料列表
	 * @param dataInfo
	 * @return
	 */
	public List<DataInfo> dataListByType(DataInfo dataInfo);

	/**
	 * 根据项目类型以及项目ID查询上传资料总数
	 * @param dataInfo
	 * @return
	 */
	public int getDataTotalByType(DataInfo dataInfo);
	
	/**
	 * 查询项目相关文件列表
	  * @Description: TODO
	  * @param projectId
	  * @return
	  * @author: andrew
	  * @date: 2016年2月27日 上午10:51:25
	 */
	public List<DataInfo> findProjectFilesByfileIds(@Param("projectId")int projectId,@Param("fileIds")List<Integer> fileIds);
}
