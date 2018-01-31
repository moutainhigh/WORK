/**
 * @Title: TemplateFileMapper.java
 * @Package com.xlkfinance.bms.server.system.mapper
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: xuweihao
 * @date: 2015年4月16日 上午10:47:18
 * @version V1.0
 */
package com.xlkfinance.bms.server.system.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.system.TemplateFile;
import com.xlkfinance.bms.rpc.system.TemplateFileCount;
@MapperScan("templateFileMapper")
public interface TemplateFileMapper<T, PK> extends BaseMapper<T, PK> {

	/**
	 * 
	  * @Description: 模板列表
	  * @return
	  * @author: xuweihao
	  * @date: 2015年4月16日 上午10:48:10
	 */
	public List<TemplateFile> listTemplateFile(TemplateFileCount templateFileCount);
	
	

	/**
	 * 
	  * @Description: 模板列表分页
	  * @return
	  * @author: xuweihao
	  * @date: 2015年4月16日 上午10:48:10
	 */
	public int listTemplateFileCount(TemplateFileCount templateFileCount);
	/**
	 * 
	  * @Description: 保存模板信息
	  * @param templateFile
	  * @return
	  * @author: xuweihao
	  * @date: 2015年4月16日 下午2:14:53
	 */
	public int saveTemplateFile(TemplateFile templateFile);
	
	/**
	 * 
	  * @Description: 修改模板信息
	  * @param templateFile
	  * @return
	  * @author: xuweihao
	  * @date: 2015年4月16日 下午2:15:19
	 */
	public int updateTemplateFile(TemplateFile templateFile);
	
	/**
	 * 
	  * @Description: 删除模板
	  * @param pid
	  * @return
	  * @author: xuweihao
	  * @date: 2015年4月16日 下午2:35:51
	 */
	public int delTemplateFile(int pid);
	
	/**
	 * 
	  * @Description: 通过val找到对应模板
	  * @param fileLookupVal
	  * @return
	  * @author: xuweihao
	  * @date: 2015年4月17日 上午10:47:54
	 */
	public TemplateFile getTemplateFile(String fileLookupVal);
}
