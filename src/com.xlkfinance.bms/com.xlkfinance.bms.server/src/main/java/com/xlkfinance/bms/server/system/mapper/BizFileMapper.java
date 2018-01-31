package com.xlkfinance.bms.server.system.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.system.BizFile;

/**   
* @Title: BizFileMapper.java 
* @Package com.xlkfinance.bms.server.system.mapper 
* @Description: 文件信息操作，表：BIZ_FILE
* @author pengchuntao   
* @date 2015年2月6日 下午5:53:17 
* @version V1.0   
*/ 
@MapperScan("bizFileMapper")
public interface BizFileMapper<T, PK> extends BaseMapper<T, PK> {
	public int saveBizFile(BizFile bizFile);
	public int deleteBizFileByPid(int pid);
	public int updateBizFile(BizFile bizFile);
	public List<BizFile> findAllBizFile(BizFile bizFile);
}
